/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.compliantHandling;

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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.compliantHandling.HrAppealRequestFileUploadBeanLocal;
import et.gov.eep.hrms.businessLogic.compliantHandling.HrAppealRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.DisplineRequestBeanLocal;
import et.gov.eep.hrms.entity.compliantHandling.HrAppealRequestFileUpload;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;

/**
 *
 * @author munir
 */
@Named(value = "appealApproveController")
@ViewScoped
public class AppealApproveController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, business logic & variables">
    @Inject
    HrAppealRequestFileUpload hrAppealRequestFileUpload;
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
    WorkFlow workFlow;
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
    DisplineRequestBeanLocal requestBeanLocal;

    private int selectedCase = 0;
    private int applicantId = 0;
    private int status = 0;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String fullName;
    private boolean isFileSelected = false;

    private ArrayList<SelectItem> caseList = new ArrayList<>();
    private ArrayList<SelectItem> appealCategory = new ArrayList<>();
    private List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrAppeals> appealDataModel;
    DataModel<WfHrProcessed> hrWorkFlowDataModel = new ListDataModel<>();

    List<HrAppeals> requestList = new ArrayList<>();
    String processedDate = "";
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PostConstruct Method">
    
    public AppealApproveController() {
    }
    @PostConstruct
    public void init() {
        setAppealCategory(caseList());
        setProcessedDate(dateFormat(StringDateManipulation.toDayInEc()));
        requestList = appealRequestsBeanLocal.findRequestForApproval();
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

    public HrLuDmArchive getHrLuDmArchive() {
        if (hrLuDmArchive == null) {
            hrLuDmArchive = new HrLuDmArchive();
        }
        return hrLuDmArchive;
    }

    public void setHrLuDmArchive(HrLuDmArchive hrLuDmArchive) {
        this.hrLuDmArchive = hrLuDmArchive;
    }

    public HrAppealRequestFileUpload getHrAppealRequestFileUpload() {
        if (hrAppealRequestFileUpload == null) {
            hrAppealRequestFileUpload = new HrAppealRequestFileUpload();
        }
        return hrAppealRequestFileUpload;
    }

    public void setHrAppealRequestFileUpload(HrAppealRequestFileUpload hrAppealRequestFileUpload) {
        this.hrAppealRequestFileUpload = hrAppealRequestFileUpload;
    }

    public List<HrAppeals> getRequestList() {
        if (requestList == null) {
            requestList = new ArrayList<>();
        }
        return requestList;
    }

    public void setRequestList(List<HrAppeals> requestList) {
        this.requestList = requestList;
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

    private boolean disableSomDecision;
    private boolean disableTxtDecisionRemark;
    private boolean disableTxtApproveDate;

    private boolean disableBtnSave;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding getters & setters">
    public boolean isDisableBtnSave() {
        return disableBtnSave;
    }

    public void setDisableBtnSave(boolean disableBtnSave) {
        this.disableBtnSave = disableBtnSave;
    }

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

    public boolean isDisableSomDecision() {
        return disableSomDecision;
    }

    public void setDisableSomDecision(boolean disableSomDecision) {
        this.disableSomDecision = disableSomDecision;
    }

    public boolean isDisableTxtDecisionRemark() {
        return disableTxtDecisionRemark;
    }

    public void setDisableTxtDecisionRemark(boolean disableTxtDecisionRemark) {
        this.disableTxtDecisionRemark = disableTxtDecisionRemark;
    }

    public boolean isDisableTxtApproveDate() {
        return disableTxtApproveDate;
    }

    public void setDisableTxtApproveDate(boolean disableTxtApproveDate) {
        this.disableTxtApproveDate = disableTxtApproveDate;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search">
    public void btnSearch() {
        renderPnlManPage = true;
        renderPnlCreateAdditional = false;
    }

    public List<SelectItem> getFilterByStatusForApprove() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Reject List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load All List"));
        return selectItems;
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
            populateAppealReq();
        }
    }

    public void populateAppealReq() {
        setRenderComponents(true);
        somCase.setDisabled(true);
        hrEmployees = hrAppeals.getApplicantId();
        selectedCase = Integer.valueOf(hrAppeals.getCaseCategory());
        if (hrAppeals.getStatus() == 0) {
            setDisableComponents(false);
            disableButtons(false);
        } else {
            setDisableComponents(true);
            disableButtons(true);
        }
        if (hrAppeals.getCaseId() != null) {
            if (selectedCase == hrAppeals.PROMOTION) {
                readPromotionDetail(Integer.valueOf(hrAppeals.getCaseId()), true);
            } else if (selectedCase == hrAppeals.DISCIPLINE) {
                readDisciplineDetail(Integer.valueOf(hrAppeals.getCaseId()), true);
            } else {
                setCaseList(null);
                setRenderComponents(false);
            }
            somCase.setValue(hrAppeals.getCaseId());
        } else {
            getCaseList().clear();
            setRenderComponents(false);
        }
        recreateDmsDataModel();
        recreatWorkFlowDataModel();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        icone = "ui-icon-search";
        createOrSearchBundle = "Search";
    }

    public void populateNotifcation(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                hrAppeals = (HrAppeals) event.getNewValue();
                populateAppealReq();
                recreatWorkFlowDataModel();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('widNotf').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again");
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

    public void clearComponents() {
        hrEmployees = new HrEmployees();
        txtProcessIdVal = null;
        txtProcessDescOneVal = null;
        txtProcessDescTwoVal = null;
        txtProcessDescThreeVal = null;
        setRenderComponents(false);
        getCaseList().clear();
        selectedCase = 0;
        hrAppeals = new HrAppeals();
        hrAppealRequestFileUpload = new HrAppealRequestFileUpload();
        setFullName(null);
        wfHrProcessed.setCommentGiven(null);
        workFlow = new WorkFlow();
    }

    private void setRenderComponents(boolean isRendered) {
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

    private void setDisableComponents(boolean isDisable) {
        setDisableSomDecision(isDisable);
        setDisableTxtDecisionRemark(isDisable);
        setDisableTxtApproveDate(isDisable);
    }

    private void disableButtons(boolean isDisabled) {
        setDisableBtnSave(isDisabled);
    }

    private void readDisciplineDetail(int disciplineId, boolean loadCaseList) {
        disciplineRequests = appealRequestsBeanLocal.findByDisciplineId(disciplineId);
        if (disciplineRequests != null) {
            lblProcessId.setValue("Discipline ID");
            lblProcessDescOne.setValue("Offence Date");
            lblProcessDescTwo.setValue("Report Date");
            lblProcessDescThree.setValue("Decision");
            setTxtProcessIdVal(String.valueOf(disciplineRequests.getId()));
            setTxtProcessDescOneVal(String.valueOf(disciplineRequests.getReportedDate()));
            setTxtProcessDescTwoVal(String.valueOf(disciplineRequests.getOffenceDate()));
            setTxtProcessDescThreeVal(String.valueOf(disciplineRequests.getDecisionOnPenality()));
        }
        if (loadCaseList) {
//            getCaseList().clear();
            getCaseList().add(new SelectItem(String.valueOf(disciplineId), disciplineRequests.getOffenceTypeId().getOffenceName()));
        }
    }

    private void readPromotionDetail(int promotionId, boolean loadCaseList) {
        hrPromotionRequests = appealRequestsBeanLocal.findByPromotionId(promotionId);
        if (hrPromotionRequests != null) {
            lblProcessId.setValue("Promotion ID");
            lblProcessDescOne.setValue("Advert Start Date");
            lblProcessDescTwo.setValue("Advert End Date");
            lblProcessDescThree.setValue("Status");
            setTxtProcessIdVal(String.valueOf(hrPromotionRequests.getId()));
            setTxtProcessDescOneVal(String.valueOf(hrPromotionRequests.getAdvertJobId().getAdvertId().getAdvertDateFrom()));
            setTxtProcessDescTwoVal(String.valueOf(hrPromotionRequests.getAdvertJobId().getAdvertId().getAdvertDateTo()));
            setTxtProcessDescThreeVal(String.valueOf(hrPromotionRequests.getStatus()));
        }
        if (loadCaseList) {
//            getCaseList().clear();
            getCaseList().add(new SelectItem(String.valueOf(promotionId), hrPromotionRequests.getAdvertJobId().getJobId().getJobTitle()));
        }
    }

    private HrAppeals appealRequestDetail() {
        HrAppeals appeals = new HrAppeals();
        appeals.setId(hrAppeals.getId());
        appeals.setStatus(1);
        return appeals;
    }

    public void saveAppealApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAppealApprove", dataset)) {
                saveWorkFlowInformation();
                JsfUtil.addSuccessMessage("The request has been successfully approved for the final state.");
                clearComponents();
                setDisableComponents(true);
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAppealApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Error occured while approving the request for the final state.");
        }
    }

    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DMS service ">
    StreamedContent file;
    DataModel<HrAppealRequestFileUpload> fileUploadDataModel;

    public DataModel<HrAppealRequestFileUpload> getFileUploadDataModel() {
        return fileUploadDataModel;
    }

    public void setFileUploadDataModel(DataModel<HrAppealRequestFileUpload> fileUploadDataModel) {
        this.fileUploadDataModel = fileUploadDataModel;
    }
    List<HrDisciplineFileUpload> documentList;

    public void recreateDmsDataModel() {
        fileUploadDataModel = null;
        fileUploadDataModel = new ListDataModel<>(hrAppeals.getHrAppealRequestFileUploadList());
    }

    public void onRowSelect(SelectEvent event) {
        hrAppealRequestFileUpload = (HrAppealRequestFileUpload) event.getObject();
        isFileSelected = true;
        hrLuDmArchive = hrAppealRequestFileUpload.getDocumentId();
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected == true) {
            file = dataUpload.getHrmsFileRefNumber(hrLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Please Select Row File U want to Download");
        }
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public void remove() {
        hrAppealRequestFileUpload.setId(hrAppealRequestFileUpload.getId());
        hrAppealRequestFileUploadBeanLocal.remove(hrAppealRequestFileUpload);
        hrAppeals.getHrAppealRequestFileUploadList().remove(hrAppealRequestFileUpload);
        recreateDmsDataModel();
        JsfUtil.addSuccessMessage("Document Seccesfully Deleted!!! ");

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Work Flow ">
    String selectedValue = "";

    public void handleSelectedOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

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
        System.out.println("=========inside work flow============");
        System.out.println("=======================" + workFlow.isApproveStatus());
        if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
            System.out.println("=======inside approved===============");
            workFlow.setUserStatus(Constants.APPROVE_VALUE);
            hrAppeals.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
            hrAppeals.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
            System.out.println("=======inside checker ===============");
            System.out.println("=======================" + workFlow.isCheckStatus());
            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
            hrAppeals.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
            hrAppeals.setStatus(workFlow.getUserStatus());
        }
        appealRequestsBeanLocal.approveAppeal(hrAppeals);
        wfHrProcessed.setDecision(hrAppeals.getStatus());
        wfHrProcessed.setAppealId(hrAppeals);
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        wfHrProcessed.setProcessedOn(processedDate);
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }

    // </editor-fold>
}
