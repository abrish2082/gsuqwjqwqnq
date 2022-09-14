/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.compliantHandling;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel; 
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.HrLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.compliantHandling.HrAppealRequestFileUploadBeanLocal;
import et.gov.eep.hrms.businessLogic.compliantHandling.HrAppealRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.DisplineRequestBeanLocal;
import et.gov.eep.hrms.entity.compliantHandling.HrAppealRequestFileUpload;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;

/**
 *
 * @author user
 */
@Named("appealRequestControllers")
@ViewScoped
public class AppealRequestController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variable">   
//    @Inject
//    HrAppealRequestFileUpload hrAppealRequestFileUpload;
    @Inject
    HrAppealRequestFileUpload appealRequestFileUpload;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrAppeals hrAppeals;
    @Inject
    HrDisciplineRequests disciplineRequests;
    @Inject
    HrPromotionRequests hrPromotionRequests;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrLuDmArchive hrLuDmArchive;
    @Inject
    DataUpload dataUpload;

    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    HrAppealRequestFileUploadBeanLocal hrAppealRequestFileUploadBeanLocal;
    @EJB
    HrAppealRequestsBeanLocal appealRequestsBeanLocal;
    @EJB
    private HREmployeesBeanLocal hrEmployeeBean;
    @EJB
    DisplineRequestBeanLocal requestBeanLocal;
    @EJB
    HrLuDmArchiveBeanLocal hrLuDmArchiveBeanLocal;

    private int selectedCase = 0;
    private int applicantId = 0;
    private int status = 0;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String icone = "ui-icon-plus";
    private String fullName;
    private boolean isFileSelected = false;

    private boolean isUpdated = false;
    private String saveOrUpdate = "Save";

    private ArrayList<SelectItem> caseList = new ArrayList<>();
    private ArrayList<SelectItem> appealCategory = new ArrayList<>();
    private List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrAppeals> appealDataModel;
    DataModel<WfHrProcessed> hrWorkFlowDataModel = new ListDataModel<>();

    WorkFlow workFlow = new WorkFlow();
    //List<SelectItem> filterByStatus = new ArrayList<>();
    int selectedStatus;
//    int userId = SessionBean.getUserId();
//    DataModel<HrDisciplineRequests> penalityDataModel;
//    DataModel<HrDisciplineRequests> penalityRequestDataModel;
    Status workFlowStatus = new Status();
    String processedDate = "";

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PostConstruct Method">
    @PostConstruct
    public void init() {
        setAppealCategory(caseList());
        setProcessedDate(dateFormat(StringDateManipulation.toDayInEc()));
        setFullName("Logged in user name");
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gettters & setters">
    public String getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(String processedDate) {
        this.processedDate = processedDate;
    }

    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public HrAppealRequestFileUpload getAppealRequestFileUpload() {
        if (appealRequestFileUpload == null) {
            appealRequestFileUpload = new HrAppealRequestFileUpload();
        }
        return appealRequestFileUpload;
    }

    public void setAppealRequestFileUpload(HrAppealRequestFileUpload appealRequestFileUpload) {
        this.appealRequestFileUpload = appealRequestFileUpload;
    }

    public HrLuDmArchive getHrLuDmArchive() {
        if (hrLuDmArchive == null) {
            hrLuDmArchive = new HrLuDmArchive();
        }
        return hrLuDmArchive;
    }

    public void setHrLuDmArchive(HrLuDmArchive hrLuDmArchive) {
        this.hrLuDmArchive = hrLuDmArchive;
    }

    public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }

    public DataModel<WfHrProcessed> getHrWorkFlowDataModel() {
        return hrWorkFlowDataModel;
    }

    public void setHrWorkFlowDataModel(DataModel<WfHrProcessed> hrWorkFlowDataModel) {
        this.hrWorkFlowDataModel = hrWorkFlowDataModel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public HrAppeals getHrAppeals() {
        if (hrAppeals == null) {
            hrAppeals = new HrAppeals();
        }
        return hrAppeals;
    }

    public void setHrAppeals(HrAppeals hrAppeals) {
        this.hrAppeals = hrAppeals;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public ArrayList<SelectItem> getAppealCategory() {
        return appealCategory;
    }

    public void setAppealCategory(ArrayList<SelectItem> appealCategory) {
        this.appealCategory = appealCategory;
    }

    public ArrayList<SelectItem> getCaseList() {
        return caseList;
    }

    public void setCaseList(ArrayList<SelectItem> caseList) {
        this.caseList = caseList;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public boolean isDisableBtnSave() {
        return disableBtnSave;
    }

    public void setDisableBtnSave(boolean disableBtnSave) {
        this.disableBtnSave = disableBtnSave;
    }

    public List<SelectItem> getFilterByStatus() {
        return appealRequestsBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public DataModel<HrAppeals> getAppealDataModel() {
        appealDataModel = new ListDataModel(appealRequestsBeanLocal.loadAppealList(status));
        return appealDataModel;
    }

    public void setAppealDataModel(DataModel<HrAppeals> appealDataModel) {
        this.appealDataModel = appealDataModel;
    }
   //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding">
    private HtmlSelectOneMenu somCase = new HtmlSelectOneMenu();
    private HtmlOutputLabel lblProcessId = new HtmlOutputLabel();
    private HtmlOutputLabel lblProcessDescOne = new HtmlOutputLabel();
    private HtmlOutputLabel lblProcessDescTwo = new HtmlOutputLabel();
    private HtmlOutputLabel lblProcessDescThree = new HtmlOutputLabel();

    private boolean txtProcessId;
    private boolean txtProcessDescOne;
    private boolean txtProcessDescTwo;
    private boolean txtProcessDescThree;

    String txtProcessIdVal;
    String txtProcessDescOneVal;
    String txtProcessDescTwoVal;
    String txtProcessDescThreeVal;

    private boolean disableEmp;
    private boolean disableAppealCategory;
    private boolean disableCase;
    private boolean disableGround;
    private boolean disableAppealDate;

    private boolean disableBtnSave = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding getters & setters">
    public HtmlSelectOneMenu getSomCase() {
        return somCase;
    }

    public void setSomCase(HtmlSelectOneMenu somCase) {
        this.somCase = somCase;
    }

    public HtmlOutputLabel getLblProcessId() {
        return lblProcessId;
    }

    public void setLblProcessId(HtmlOutputLabel lblProcessId) {
        this.lblProcessId = lblProcessId;
    }

    public HtmlOutputLabel getLblProcessDescOne() {
        return lblProcessDescOne;
    }

    public void setLblProcessDescOne(HtmlOutputLabel lblProcessDescOne) {
        this.lblProcessDescOne = lblProcessDescOne;
    }

    public HtmlOutputLabel getLblProcessDescTwo() {
        return lblProcessDescTwo;
    }

    public void setLblProcessDescTwo(HtmlOutputLabel lblProcessDescTwo) {
        this.lblProcessDescTwo = lblProcessDescTwo;
    }

    public HtmlOutputLabel getLblProcessDescThree() {
        return lblProcessDescThree;
    }

    public void setLblProcessDescThree(HtmlOutputLabel lblProcessDescThree) {
        this.lblProcessDescThree = lblProcessDescThree;
    }

    public String getTxtProcessIdVal() {
        return txtProcessIdVal;
    }

    public void setTxtProcessIdVal(String txtProcessIdVal) {
        this.txtProcessIdVal = txtProcessIdVal;
    }

    public String getTxtProcessDescOneVal() {
        return txtProcessDescOneVal;
    }

    public void setTxtProcessDescOneVal(String txtProcessDescOneVal) {
        this.txtProcessDescOneVal = txtProcessDescOneVal;
    }

    public String getTxtProcessDescTwoVal() {
        return txtProcessDescTwoVal;
    }

    public void setTxtProcessDescTwoVal(String txtProcessDescTwoVal) {
        this.txtProcessDescTwoVal = txtProcessDescTwoVal;
    }

    public String getTxtProcessDescThreeVal() {
        return txtProcessDescThreeVal;
    }

    public void setTxtProcessDescThreeVal(String txtProcessDescThreeVal) {
        this.txtProcessDescThreeVal = txtProcessDescThreeVal;
    }

    public boolean isTxtProcessId() {
        return txtProcessId;
    }

    public void setTxtProcessId(boolean txtProcessId) {
        this.txtProcessId = txtProcessId;
    }

    public boolean isTxtProcessDescOne() {
        return txtProcessDescOne;
    }

    public void setTxtProcessDescOne(boolean txtProcessDescOne) {
        this.txtProcessDescOne = txtProcessDescOne;
    }

    public boolean isTxtProcessDescTwo() {
        return txtProcessDescTwo;
    }

    public void setTxtProcessDescTwo(boolean txtProcessDescTwo) {
        this.txtProcessDescTwo = txtProcessDescTwo;
    }

    public boolean isTxtProcessDescThree() {
        return txtProcessDescThree;
    }

    public void setTxtProcessDescThree(boolean txtProcessDescThree) {
        this.txtProcessDescThree = txtProcessDescThree;
    }

    public boolean isDisableAppealDate() {
        return disableAppealDate;
    }

    public void setDisableAppealDate(boolean disableAppealDate) {
        this.disableAppealDate = disableAppealDate;
    }

    public boolean isDisableCase() {
        return disableCase;
    }

    public void setDisableCase(boolean disableCase) {
        this.disableCase = disableCase;
    }

    public boolean isDisableGround() {
        return disableGround;
    }

    public void setDisableGround(boolean disableGround) {
        this.disableGround = disableGround;
    }

    public boolean isDisableAppealCategory() {
        return disableAppealCategory;
    }

    public void setDisableAppealCategory(boolean disableAppealCategory) {
        this.disableAppealCategory = disableAppealCategory;
    }

    public boolean isDisableEmp() {
        return disableEmp;
    }

    public void setDisableEmp(boolean disableEmp) {
        this.disableEmp = disableEmp;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search">
    public void newSearch() {
        saveOrUpdate = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        clearComponents();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    private void populateTable() {
        try {
            List<HrAppeals> readAllAppeal = appealRequestsBeanLocal.loadAppealList(status);
            appealDataModel = new ListDataModel(readAllAppeal);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void populateAppeal(SelectEvent events) {
        if (events.getObject() != null) {
            hrAppeals = (HrAppeals) events.getObject();
            setComponentsRender(true);
            somCase.setDisabled(true);
            hrEmployees = hrAppeals.getApplicantId();
            processedDate = wfHrProcessed.getProcessedOn();
            selectedCase = Integer.valueOf(hrAppeals.getCaseCategory());
            disciplineRequests.setId(Integer.valueOf(hrAppeals.getCaseId()));
            if (hrAppeals.getStatus().equals(0)) {
                disableButtons(false);
                setDisableComponents(false);
            } else {
                disableButtons(true);
                setDisableComponents(true);
            }
            if (hrAppeals.getCaseId() != null) {
                if (selectedCase == hrAppeals.PROMOTION) {
                    readPromotionDetail(Integer.valueOf(hrAppeals.getCaseId()), true);
                } else if (selectedCase == hrAppeals.DISCIPLINE) {
                    readDisciplineDetail(Integer.valueOf(hrAppeals.getCaseId()), true);
                } else {
                    setCaseList(null);
                    setComponentsRender(false);
                }
                somCase.setValue(hrAppeals.getCaseId());
            } else {
                getCaseList().clear();
                setComponentsRender(false);
            }
            recreateDmsDataModel();
            recreatWorkFlowDataModel();
            renderPnlManPage = false;
            renderPnlCreateAdditional = true;
            btnNewRender = true;
            icone = "ui-icon-search";
            createOrSearchBundle = "Search";
            saveOrUpdate = "Update";
            isUpdated = true;
        }
    }
    List<HrAppeals> appealRequestList;

    public List<HrAppeals> getAppealRequestList() {
        if (appealRequestList == null) {
            appealRequestList = new ArrayList<>();
        }
        return appealRequestList;
    }

    public void setAppealRequestList(List<HrAppeals> appealRequestList) {
        this.appealRequestList = appealRequestList;
    }

    public SelectItem[] getPopulateFilterByStatus() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load Request List");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load Approved List");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load Rejected List");
        return items;
    }

    private void populateRequestTable(Status _status) {
        try {
            appealRequestList = appealRequestsBeanLocal.loadPenalityRequestList(_status);
//            for (int i = 0; i < appealRequestList.size(); i++) {
//                if (appealRequestList.get(i).getStatus().equals(0)) {
//                    appealRequestList.get(i).setChangedStatus("Request");
//                } else if (appealRequestList.get(i).getStatus().equals(2)) {
//                    appealRequestList.get(i).setChangedStatus("Checker Reject");
//                } else if (appealRequestList.get(i).getStatus().equals(4)) {
//                    appealRequestList.get(i).setChangedStatus("Approver Reject");
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populateRequestTableForTwoStatus(Status _status) {
        try {
            appealRequestList = appealRequestsBeanLocal.loadPenalityRequestListForTwo(_status);
//            for (int i = 0; i < appealRequestList.size(); i++) {
//                if (appealRequestList.get(i).getStatus().equals(1)) {
//                    appealRequestList.get(i).setChangedStatus("Checker Approved");
//                } else if (appealRequestList.get(i).getStatus().equals(3)) {
//                    appealRequestList.get(i).setChangedStatus("Approved");
//                }
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerAppealRequest(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                workFlowStatus.setStatus1(Constants.PREPARE_VALUE);
                populateRequestTable(workFlowStatus);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                workFlowStatus.setStatus1(Constants.APPROVE_VALUE);
                workFlowStatus.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateRequestTableForTwoStatus(workFlowStatus);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                workFlowStatus.setStatus1(Constants.APPROVE_REJECT_VALUE);
                workFlowStatus.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateRequestTableForTwoStatus(workFlowStatus);
            }

        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main">
    public ArrayList<SelectItem> caseList() {
        ArrayList<SelectItem> appealList = new ArrayList<>();
        appealList.add(new SelectItem(null, "-- Select Case --"));
        appealList.add(new SelectItem(String.valueOf(hrAppeals.PROMOTION), "Promotion"));
        appealList.add(new SelectItem(String.valueOf(hrAppeals.DISCIPLINE), "Discipline"));
        appealList.add(new SelectItem(String.valueOf(hrAppeals.OTHER), "Other"));
        return appealList;
    }

    public ArrayList<HrEmployees> SearchByFname(String employee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(employee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployees);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        hrEmployees = new HrEmployees();
        hrEmployees.setFirstName((event.getObject().toString()));
        hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
        applicantId = hrEmployees.getId();
    }

    public void clearComponents() {
        hrEmployees = new HrEmployees();
        txtProcessIdVal = null;
        txtProcessDescOneVal = null;
        txtProcessDescTwoVal = null;
        txtProcessDescThreeVal = null;
        setComponentsRender(false);
        getCaseList().clear();
        selectedCase = 0;
        hrAppeals = new HrAppeals();
        wfHrProcessed = new WfHrProcessed();
//        hrAppealRequestFileUpload = new HrAppealRequestFileUpload();
        setFullName(null);
        wfHrProcessed.setCommentGiven(null);
        isUpdated = false;
        saveOrUpdate = "Save";
        setDisableBtnSave(false);
    }

    private void clearBindingComponents() {
        setTxtProcessIdVal(null);
        setTxtProcessDescOneVal(null);
        setTxtProcessDescTwoVal(null);
        setTxtProcessDescThreeVal(null);
    }

    private void setComponentsRender(boolean isRendered) {
        somCase.setDisabled(!isRendered);
        lblProcessId.setRendered(isRendered);
        lblProcessDescOne.setRendered(isRendered);
        lblProcessDescTwo.setRendered(isRendered);
        lblProcessDescThree.setRendered(isRendered);
        setTxtProcessId(isRendered);
        setTxtProcessDescOne(isRendered);
        setTxtProcessDescTwo(isRendered);
        setTxtProcessDescThree(isRendered);
    }

    private void setDisableComponents(boolean isDisabled) {
        setDisableEmp(isDisabled);
        setDisableAppealCategory(isDisabled);
        setDisableCase(isDisabled);
        setDisableGround(isDisabled);
        setDisableAppealDate(isDisabled);
    }

    private void disableButtons(boolean isDisabled) {
        setDisableBtnSave(isDisabled);
    }
    List<HrDisciplineRequests> loadAllAppronedList = new ArrayList<>();

    public void somAppealCategory_processValueChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            setComponentsRender(true);
            selectedCase = Integer.valueOf(event.getNewValue().toString());
            clearBindingComponents();
            if (selectedCase == hrAppeals.PROMOTION) {
                setCaseList(appealRequestsBeanLocal.readPromotionCases(applicantId));
                lblProcessId.setValue("Promotion ID:");
                lblProcessDescOne.setValue("Advert Start Date:");
                lblProcessDescTwo.setValue("Advert End Date:");
                lblProcessDescThree.setValue("Status:");
            } else if (selectedCase == hrAppeals.DISCIPLINE) {
                String curentDate[] = dateFormat(StringDateManipulation.toDayInEc()).split("/");
                int currentDay = Integer.parseInt(curentDate[0]);
                int currentMonth = Integer.parseInt(curentDate[1]);
                int currentYear = Integer.parseInt(curentDate[2]);
                loadAllAppronedList = appealRequestsBeanLocal.findAllApprovedList(applicantId);
                for (int i = 0; i < loadAllAppronedList.size(); i++) {
                    String approvedDate[] = loadAllAppronedList.get(i).getApprovedOn().split("/");
                    int approvedDay = Integer.parseInt(approvedDate[0]);
                    int approvedMonth = Integer.parseInt(approvedDate[1]);
                    int approvedYear = Integer.parseInt(approvedDate[2]);
                    int dayBetween = (currentDay - approvedDay) + ((currentMonth - approvedMonth) * 30) + ((currentYear - approvedYear) * 360);
                    if (dayBetween <= 10) {
                        setCaseList(appealRequestsBeanLocal.readApprovedDisciplineCases(applicantId));
                        lblProcessId.setValue("Discipline ID:");
                        lblProcessDescOne.setValue("Offence Date:");
                        lblProcessDescTwo.setValue("Report Date:");
                        lblProcessDescThree.setValue("Decision:");
                    } else {
                        JsfUtil.addFatalMessage("Appeal request should be processed within 10 days");
                    }
                }

            } else {
                setCaseList(null);
                setComponentsRender(false);
            }
        }
    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String dateInDB;
        if (date != null && date.contains("-")) {
            dateFromUI = date.split("-");
            dateInDB = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
            return dateInDB;
        }
        return null;
    }

    private void readDisciplineDetail(int disciplineId, boolean loadCaseList) {
        disciplineRequests = appealRequestsBeanLocal.findByDisciplineId(disciplineId);
        if (disciplineRequests != null) {
            lblProcessId.setValue("Discipline ID:");
            lblProcessDescOne.setValue("Offence Date:");
            lblProcessDescTwo.setValue("Report Date:");
            lblProcessDescThree.setValue("Decision:");
            setTxtProcessIdVal(String.valueOf(disciplineRequests.getId()));
            setTxtProcessDescOneVal(String.valueOf(disciplineRequests.getReportedDate()));
            setTxtProcessDescTwoVal(String.valueOf(disciplineRequests.getOffenceDate()));
            setTxtProcessDescThreeVal(String.valueOf(disciplineRequests.getDecisionOnPenality()));
        }
        if (loadCaseList) {
            getCaseList().clear();
            getCaseList().add(new SelectItem(String.valueOf(disciplineId), disciplineRequests.getOffenceTypeId().getOffenceName()));
        }
    }

    private void readPromotionDetail(int promotionId, boolean loadCaseList) {
        hrPromotionRequests = appealRequestsBeanLocal.findByPromotionId(promotionId);
        if (hrPromotionRequests != null) {
            lblProcessId.setValue("Promotion ID:");
            lblProcessDescOne.setValue("Advert Start Date:");
            lblProcessDescTwo.setValue("Advert End Date:");
            lblProcessDescThree.setValue("Status:");
            setTxtProcessIdVal(String.valueOf(hrPromotionRequests.getId()));
            setTxtProcessDescOneVal(String.valueOf(hrPromotionRequests.getAdvertJobId().getAdvertId().getAdvertDateFrom()));
            setTxtProcessDescTwoVal(String.valueOf(hrPromotionRequests.getAdvertJobId().getAdvertId().getAdvertDateTo()));
            setTxtProcessDescThreeVal(String.valueOf(hrPromotionRequests.getStatus()));
        }
        if (loadCaseList) {
            getCaseList().clear();
            getCaseList().add(new SelectItem(String.valueOf(promotionId), hrPromotionRequests.getAdvertJobId().getJobId().getJobTitle()));
        }
    }

    public void caseDetails(ValueChangeEvent event) {
        if (event.getNewValue() != null && selectedCase > 0) {
            String id = event.getNewValue().toString();
            if (selectedCase == hrAppeals.PROMOTION) {
                readPromotionDetail(Integer.valueOf(id), false);
            } else if (selectedCase == hrAppeals.DISCIPLINE) {
                readDisciplineDetail(Integer.valueOf(id), false);
            } else {
            }
            setDisableComponents(false);
        }
    }

    private HrAppeals appealRequestDetail() {
        HrAppeals appeals = new HrAppeals();
        appeals.setId(hrAppeals.getId());
        appeals.setApplicantId(hrEmployees);
        appeals.setCaseId(txtProcessIdVal);
        appeals.setCaseCategory(String.valueOf(selectedCase));
        appeals.setGround(hrAppeals.getGround());
        appeals.setAppealDate(hrAppeals.getAppealDate());
        appeals.setStatus(Constants.PREPARE_VALUE);
        appeals.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
        appeals.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        return appeals;
    }

    public void appealRequestDetails() {
        hrAppeals.setApplicantId(hrEmployees);
        hrAppeals.setCaseId(txtProcessIdVal);
        hrAppeals.setCaseCategory(String.valueOf(selectedCase));
        hrAppeals.setAppealDate(hrAppeals.getAppealDate());
        hrAppeals.setStatus(Constants.PREPARE_VALUE);
        hrAppeals.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
        hrAppeals.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
    }

    public void saveAppealRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAppealRequest", dataset)) {
                HrDisciplineRequests disciplineReq = new HrDisciplineRequests();
                HrAppealRequestFileUpload appealFileUpload = new HrAppealRequestFileUpload();
                if (!isUpdated) {
                    for (int j = 0; j < byteList.size(); j++) {
                        hrLuDmArchive.setFileName(fileNameList.get(j));
                        hrLuDmArchive.setFileType(fileTypeList.get(j));
                        hrLuDmArchive.setUploadFile(byteListFinal.get(j));
                        hrLuDmArchiveBeanLocal.create(hrLuDmArchive);
                        appealFileUpload.setDocumentId(hrLuDmArchive);
                        appealFileUpload.setAppealCaseId(hrAppeals);
                        hrAppeals.getHrAppealRequestFileUploadList().add(appealFileUpload);
                        hrLuDmArchive = new HrLuDmArchive();
                        appealFileUpload = new HrAppealRequestFileUpload();
                    }
                    appealRequestDetails();
                    wfHrProcessed.setAppealId(hrAppeals);
                    wfHrProcessed.setDecision(hrAppeals.getStatus());
                    wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                    wfHrProcessed.setProcessedOn(processedDate);
                    appealRequestsBeanLocal.saveAppealRequest(hrAppeals);
                    wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    System.out.println("hello end of save");
                    disciplineRequests.setId(Integer.valueOf(hrAppeals.getCaseId()));
                    disciplineReq = requestBeanLocal.findByIds(disciplineRequests);
                    if (disciplineReq != null) {
                        disciplineReq.setStatus(String.valueOf(Constants.APPEAL_ON_DISCIPLINE_STATUS));
                        requestBeanLocal.edit(disciplineReq);
                    }
                    JsfUtil.addSuccessMessage("Appeal request has been successfully submitted");
                    clearComponents();
                } else {
                    for (int j = 0; j < byteList.size(); j++) {
                        hrLuDmArchive.setFileName(fileNameList.get(j));
                        hrLuDmArchive.setFileType(fileTypeList.get(j));
                        hrLuDmArchive.setUploadFile(byteListFinal.get(j));
                        hrLuDmArchiveBeanLocal.edit(hrLuDmArchive);
                        appealFileUpload.setDocumentId(hrLuDmArchive);
                        appealFileUpload.setAppealCaseId(hrAppeals);
                        hrAppeals.getHrAppealRequestFileUploadList().add(appealFileUpload);
                        appealFileUpload = new HrAppealRequestFileUpload();
                        hrLuDmArchive = new HrLuDmArchive();
                    }
                    appealRequestsBeanLocal.updateAppealRequest(hrAppeals);
                    JsfUtil.addSuccessMessage("Appeal request has been successfully updated");
                    clearComponents();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAppealRequest");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            isUpdated = false;
            saveOrUpdate = "Save";
        }
    }

    public void resetAppealRequest() {
        clearComponents();
        setDisableComponents(false);
        disableButtons(false);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DMS service ">
    StreamedContent file;
    byte[] byteFile;
    List<byte[]> byteList = new ArrayList<>();
    List<byte[]> byteListFinal = new ArrayList<>();
    List<String> fileNameList = new ArrayList<>();
    List<String> fileTypeList = new ArrayList<>();
    String fileName;
    String fileType;
    String fileNameWithExtension = "";
    DataModel<HrAppealRequestFileUpload> appealFileUploadDataModel;

    public DataModel<HrAppealRequestFileUpload> getAppealFileUploadDataModel() {
        return appealFileUploadDataModel;
    }

    public void setAppealFileUploadDataModel(DataModel<HrAppealRequestFileUpload> appealFileUploadDataModel) {
        this.appealFileUploadDataModel = appealFileUploadDataModel;
    }

    public void recreateDmsDataModel() {
        appealFileUploadDataModel = null;
        appealFileUploadDataModel = new ListDataModel<>(hrAppeals.getHrAppealRequestFileUploadList());
    }

    public void uploadListener(FileUploadEvent event) {
        try {
            InputStream inputStream = null;
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            inputStream = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStream);
            if (byteFile != null) {
                byteList.add(byteFile);
            }
            for (int countIndex = 0; countIndex < byteList.size(); countIndex++) {
                fileNameList.add(fileName);
                fileTypeList.add(fileType);
                byteListFinal.add(byteFile);
            }
            JsfUtil.addSuccessMessage("Upload Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRowSelect(SelectEvent event) {
        appealRequestFileUpload = (HrAppealRequestFileUpload) event.getObject();
        isFileSelected = true;
        hrLuDmArchive = appealRequestFileUpload.getDocumentId();
        System.out.println("so pass this value " + hrLuDmArchive);
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected == true) {
            dataUpload.getHrmsFileRefNumber(hrLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Please Select Row File U want to Download");
        }
        return file;
    }

    public void remove() {
        appealRequestFileUpload.setId(appealRequestFileUpload.getId());
        hrAppealRequestFileUploadBeanLocal.remove(appealRequestFileUpload);
        hrAppeals.getHrAppealRequestFileUploadList().remove(appealRequestFileUpload);
        recreateDmsDataModel();
        JsfUtil.addSuccessMessage("Document Seccesfully Deleted!!! ");

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Work Flow ">
    public void recreatWorkFlowDataModel() {
        hrWorkFlowDataModel = null;
        for (int i = 0; i < hrAppeals.getHrWorkFlowProccedList().size(); i++) {
            if (hrAppeals.getHrWorkFlowProccedList().get(i).getDecision() == 0) {
                hrAppeals.getHrWorkFlowProccedList().get(i).setChangedStstus("Request");
            } else if (hrAppeals.getHrWorkFlowProccedList().get(i).getDecision() == 1 || hrAppeals.getHrWorkFlowProccedList().get(i).getDecision() == 3) {
                hrAppeals.getHrWorkFlowProccedList().get(i).setChangedStstus("Approved");
            } else if (hrAppeals.getHrWorkFlowProccedList().get(i).getDecision() == 2 || hrAppeals.getHrWorkFlowProccedList().get(i).getDecision() == 4) {
                hrAppeals.getHrWorkFlowProccedList().get(i).setChangedStstus("Rejected");
            }
        }
        hrWorkFlowDataModel = new ListDataModel(new ArrayList(hrAppeals.getHrWorkFlowProccedList()));
    }

    public void saveWorkFlowInformation() {
        wfHrProcessed.setAppealId(hrAppeals);
        wfHrProcessed.setDecision(hrAppeals.getStatus());
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }
    // </editor-fold>
}
