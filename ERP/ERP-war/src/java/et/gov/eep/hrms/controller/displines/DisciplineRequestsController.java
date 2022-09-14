/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.displines;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
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
import et.gov.eep.fcms.controller.adminstrator.SysCCMapperController;
import et.gov.eep.hrms.businessLogic.displine.DisplineRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineFileUploadBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineOffenceTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplinePenlitysBeansLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenlitys;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.displine.HrEmpDisciplines;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.mapper.displine.HrDisciplineOffenceTypesFacade;

/**
 *
 * @author user
 */
@Named(value = "disciplineRequestsControllers")
@ViewScoped
public class DisciplineRequestsController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @EJB
    private HREmployeesBeanLocal hrEmployeeBean;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    DisplineRequestBeanLocal requestBeanLocal;
    @EJB
    HrDisciplineOffenceTypesBeanLocal offenceTypesBeanLocal;
    @EJB
    HrDisciplinePenlitysBeansLocal disciplinePenlitysBeansLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    HrLuDmArchiveBeanLocal hrLuDmArchiveBeanLocal;
    @EJB
    HrDisciplineFileUploadBeanLocal hrDisciplineFileUploadBeanLocal;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    HrDisciplineRequests disciplineRequests;
    @Inject
    HrDisciplineRequests hrDisciplineRequests;
    @Inject
    HrDisciplinePenlitys disciplinePenlitys;
    @Inject
    HrDisciplineOffenceTypes offenceTypes;
    @Inject
    HrEmployees hrEmployeesAccuser, hrEmployee;
    @Inject
    HrEmployees hrEmployeesRequester;
    @Inject
    HrDepartments departmentsOfAccuser, departmentsOfAccused;
    @Inject
    HrDisciplineFileUpload hrDisciplineFileUpload;
    @Inject
    HrJobTypes hrJobTypesOfAccuser, hrJobTypesOfAccused;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrLuDmArchive hrLuDmArchive;
    @Inject
    DataUpload dataUpload;
    @Inject
    HrDisciplineFileUpload disciplineFileUpload;

    private HrDisciplineRequests selectedRow;
    DataModel<HrDisciplineRequests> disciplineRequestDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> hrWorkFlowDataModel = new ListDataModel<>();

    List<HrDisciplineRequests> requestsesList;
    List<HrDisciplineRequests> hrdisRequestList;
    List<HrDisciplineRequests> requestsesApprovedList;
    List<HrDisciplineOffenceTypes> offenceTypesesList;
    private List<HrDisciplineRequests> disciplineRequestList;
    List<HrLuDmArchive> hrLuDmArchivesList = new ArrayList<>();
    List<HrDisciplineFileUpload> hrDisciplineFileUploadList;

    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String saveOrUpdateButton = "Save";
    private String fullName;
    //private final static int itemListValues = 3;

    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private boolean btnNewRender = false;
    int selectedStatus;
    int update = 0;

    WorkFlow workFlow = new WorkFlow();
    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrDisciplineRequests> penalityDataModel;
    DataModel<HrDisciplineRequests> penalityRequestDataModel;
    DataModel<HrEmpDisciplines> hrDisciplineHistoryModel = new ListDataModel<>();
    List<HrEmpDisciplines> ListOfDisciplineHistory = new ArrayList();
    Status status = new Status();
    private boolean disableCrud = false;
    String preparedDate = "";
// </editor-fold> 

    @PostConstruct
    public void init() {
        seachByOffenceCode();
        wfHrProcessed.setProcessedOn(dateFormat(StringDateManipulation.toDayInEc()));
        preparedDate = dateFormat(StringDateManipulation.toDayInEc());
    }

    // <editor-fold defaultstate="collapsed" desc="Getters setters">
    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public HrEmployees getHrEmployee() {
        if (hrEmployee == null) {
            hrEmployee = new HrEmployees();
        }
        return hrEmployee;
    }

    public void setHrEmployee(HrEmployees hrEmployee) {
        this.hrEmployee = hrEmployee;
    }

    public DataModel<HrEmpDisciplines> getHrDisciplineHistoryModel() {
        if (hrDisciplineHistoryModel == null) {
            hrDisciplineHistoryModel = new ArrayDataModel<>();
        }
        return hrDisciplineHistoryModel;
    }

    public void setHrDisciplineHistoryModel(DataModel<HrEmpDisciplines> hrDisciplineHistoryModel) {
        this.hrDisciplineHistoryModel = hrDisciplineHistoryModel;
    }

    public List<HrEmpDisciplines> getListOfDisciplineHistory() {
        if (ListOfDisciplineHistory == null) {
            ListOfDisciplineHistory = new ArrayList<>();
        }
        return ListOfDisciplineHistory;
    }

    public void setListOfDisciplineHistory(List<HrEmpDisciplines> ListOfDisciplineHistory) {
        this.ListOfDisciplineHistory = ListOfDisciplineHistory;
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

    public boolean isDisableCrud() {
        return disableCrud;
    }

    public void setDisableCrud(boolean disableCrud) {
        this.disableCrud = disableCrud;
    }

    public DataModel<WfHrProcessed> getHrWorkFlowDataModel() {
        return hrWorkFlowDataModel;
    }

    public void setHrWorkFlowDataModel(DataModel<WfHrProcessed> hrWorkFlowDataModel) {
        this.hrWorkFlowDataModel = hrWorkFlowDataModel;
    }

    public HrDisciplineFileUpload getHrDisciplineFileUpload() {
        if (hrDisciplineFileUpload == null) {
            hrDisciplineFileUpload = new HrDisciplineFileUpload();
        }
        return hrDisciplineFileUpload;
    }

    public void setHrDisciplineFileUpload(HrDisciplineFileUpload hrDisciplineFileUpload) {
        this.hrDisciplineFileUpload = hrDisciplineFileUpload;
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

    public List<HrDisciplineRequests> getHrdisRequestList() {
        return hrdisRequestList;
    }

    public void setHrdisRequestList(List<HrDisciplineRequests> hrdisRequestList) {
        this.hrdisRequestList = hrdisRequestList;
    }

    public List<HrDisciplineRequests> getRequestsesApprovedList() {
        return requestsesApprovedList;
    }

    public void setRequestsesApprovedList(List<HrDisciplineRequests> requestsesApprovedList) {
        this.requestsesApprovedList = requestsesApprovedList;
    }

    public List<HrDisciplineRequests> getRequestsesList() {
        return requestsesList;
    }

    public void setRequestsesList(List<HrDisciplineRequests> requestsesList) {
        this.requestsesList = requestsesList;
    }

    public void searchDisplineRequestApproved() {

        requestsesApprovedList = requestBeanLocal.findRequestsApproved();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isCheckBoxReqst() {
        return checkBoxReqst;
    }

    public void setCheckBoxReqst(boolean checkBoxReqst) {
        this.checkBoxReqst = checkBoxReqst;
    }

    public boolean isCheckBoxApprove() {
        return checkBoxApprove;
    }

    public void setCheckBoxApprove(boolean checkBoxApprove) {
        this.checkBoxApprove = checkBoxApprove;
    }

    public boolean isRenderPnlApproved() {
        return renderPnlApproved;
    }

    public void setRenderPnlApproved(boolean renderPnlApproved) {
        this.renderPnlApproved = renderPnlApproved;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
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

    public HrDisciplineRequests getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrDisciplineRequests selectedRow) {
        this.selectedRow = selectedRow;
    }

    public HrEmployees getHrEmployeesAccuser() {
        if (hrEmployeesAccuser == null) {
            hrEmployeesAccuser = new HrEmployees();
        }
        return hrEmployeesAccuser;
    }

    public void setHrEmployees(HrEmployees hrEmployeesAccuser) {
        this.hrEmployeesAccuser = hrEmployeesAccuser;
    }

    public DataModel<HrDisciplineRequests> getDisciplineRequestDataModel() {
        return disciplineRequestDataModel;
    }

    public void setDisciplineRequestDataModel(DataModel<HrDisciplineRequests> disciplineRequestDataModel) {
        this.disciplineRequestDataModel = disciplineRequestDataModel;
    }

    public HrEmployees getHrEmployeesRequester() {
        if (hrEmployeesRequester == null) {
            hrEmployeesRequester = new HrEmployees();
        }
        return hrEmployeesRequester;
    }

    public void setHrEmployeesRequester(HrEmployees hrEmployeesRequester) {
        this.hrEmployeesRequester = hrEmployeesRequester;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypesOfAccuser == null) {
            hrJobTypesOfAccuser = new HrJobTypes();
        }
        return hrJobTypesOfAccuser;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypesOfAccuser) {
        this.hrJobTypesOfAccuser = hrJobTypesOfAccuser;
    }

    public HrJobTypes getHrJobTypesOfAccused() {
        if (hrJobTypesOfAccused == null) {
            hrJobTypesOfAccused = new HrJobTypes();
        }
        return hrJobTypesOfAccused;
    }

    public void setHrJobTypesOfAccused(HrJobTypes hrJobTypesOfAccused) {
        this.hrJobTypesOfAccused = hrJobTypesOfAccused;
    }

    public HrDepartments getDepartments() {
        if (departmentsOfAccuser == null) {
            departmentsOfAccuser = new HrDepartments();
        }
        return departmentsOfAccuser;
    }

    public void setDepartments(HrDepartments departmentsOfAccuser) {
        this.departmentsOfAccuser = departmentsOfAccuser;
    }

    public HrDepartments getDepartmentsOfAccused() {
        if (departmentsOfAccused == null) {
            departmentsOfAccused = new HrDepartments();
        }
        return departmentsOfAccused;
    }

    public void setDepartmentsOfAccused(HrDepartments departmentsOfAccused) {
        this.departmentsOfAccused = departmentsOfAccused;
    }

    public HrDisciplineRequests getDisciplineRequests() {
        if (disciplineRequests == null) {
            disciplineRequests = new HrDisciplineRequests();
        }
        return disciplineRequests;
    }

    public void setDisciplineRequests(HrDisciplineRequests disciplineRequests) {
        this.disciplineRequests = disciplineRequests;
    }

    public HrDisciplineRequests getHrDisciplineRequests() {
        if (hrDisciplineRequests == null) {
            hrDisciplineRequests = new HrDisciplineRequests();
        }
        return hrDisciplineRequests;
    }

    public void setHrDisciplineRequests(HrDisciplineRequests hrDisciplineRequests) {
        this.hrDisciplineRequests = hrDisciplineRequests;
    }

    public HrDisciplineOffenceTypes getOffenceTypes() {
        if (offenceTypes == null) {
            offenceTypes = new HrDisciplineOffenceTypes();
        }
        return offenceTypes;
    }

    public void setOffenceTypes(HrDisciplineOffenceTypes offenceTypes) {
        this.offenceTypes = offenceTypes;
    }

    public HrDisciplinePenlitys getDisciplinePenlitys() {
        if (disciplinePenlitys == null) {
            disciplinePenlitys = new HrDisciplinePenlitys();
        }
        return disciplinePenlitys;
    }

    public void setDisciplinePenlitys(HrDisciplinePenlitys disciplinePenlitys) {
        this.disciplinePenlitys = disciplinePenlitys;
    }

    public List<HrDisciplineOffenceTypes> getOffenceTypesesList() {
        return offenceTypesesList;
    }

    public void setOffenceTypesesList(List<HrDisciplineOffenceTypes> offenceTypesesList) {
        this.offenceTypesesList = offenceTypesesList;
    }
    List<HrDisciplinePenlitys> disciplinePenlityses;

    public List<HrDisciplinePenlitys> getDisciplinePenlityses() {
        return disciplinePenlityses;
    }

    public void setDisciplinePenlityses(List<HrDisciplinePenlitys> disciplinePenlityses) {
        this.disciplinePenlityses = disciplinePenlityses;
    }

    public List<HrDisciplineRequests> getDisciplineRequestList() {
        if (disciplineRequestList == null) {
            disciplineRequestList = new ArrayList<>();
        }
        return disciplineRequestList;
    }

    public void setDisciplineRequestList(List<HrDisciplineRequests> disciplineRequestList) {
        this.disciplineRequestList = disciplineRequestList;
    }

    public List<HrDisciplineFileUpload> getHrDisciplineFileUploadList() {
        if (hrDisciplineFileUploadList == null) {
            hrDisciplineFileUploadList = new ArrayList<>();
        }
        return hrDisciplineFileUploadList;
    }

    public void setHrDisciplineFileUploadList(List<HrDisciplineFileUpload> hrDisciplineFileUploadList) {
        this.hrDisciplineFileUploadList = hrDisciplineFileUploadList;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            update = 0;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlApproved = true;
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            update = 1;
        }
    }

    public void newButtonAction() {
        resetDisciplineRequests();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        btnNewRender = false;
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
            disciplineRequestList = requestBeanLocal.loadPenalityRequestList(_status);
//            for (int i = 0; i < disciplineRequestList.size(); i++) {
//                if (disciplineRequestList.get(i).getStatus().equalsIgnoreCase("0")) {
//                    disciplineRequestList.get(i).setChangedStatus("Request");
//                } else if (disciplineRequestList.get(i).getStatus().equalsIgnoreCase("2")) {
//                    disciplineRequestList.get(i).setChangedStatus("Checker Reject");
//                } else if (disciplineRequestList.get(i).getStatus().equalsIgnoreCase("4")) {
//                    disciplineRequestList.get(i).setChangedStatus("Approver Reject");
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populateRequestTableForTwoStatus(Status _status) {
        try {
            //status2
            disciplineRequestList = requestBeanLocal.loadPenalityRequestListForTwo(_status);
//            for (int i = 0; i < disciplineRequestList.size(); i++) {
//                if (disciplineRequestList.get(i).getStatus().equalsIgnoreCase("1")) {
//                    disciplineRequestList.get(i).setChangedStatus("Checker Approved");
//                } else if (disciplineRequestList.get(i).getStatus().equalsIgnoreCase("3")) {
//                    disciplineRequestList.get(i).setChangedStatus("Approved");
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerDisciplineRequest(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                populateRequestTable(status);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateRequestTableForTwoStatus(status);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateRequestTableForTwoStatus(status);
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

    public void populate(SelectEvent events) {
        disciplineRequests = (HrDisciplineRequests) events.getObject();
        hrEmployeesRequester = disciplineRequests.getRequesterId();
        departmentsOfAccused = hrEmployeesRequester.getDeptId();
        hrJobTypesOfAccused = hrEmployeesRequester.getJobId();
        hrEmployeesAccuser = disciplineRequests.getOffenderId();
        departmentsOfAccuser = hrEmployeesAccuser.getDeptId();
        hrJobTypesOfAccuser = hrEmployeesAccuser.getJobId();
        offenceTypes = disciplineRequests.getOffenceTypeId();
        preparedDate = disciplineRequests.getPreparedOn();
        if (disciplineRequests.getStatus().equalsIgnoreCase(String.valueOf(Constants.APPROVE_VALUE)) || disciplineRequests.getStatus().equalsIgnoreCase(String.valueOf(Constants.CHECK_APPROVE_VALUE))) {
            disableCrud = true;
        } else {
            disableCrud = false;
        }
        recreateDmsDataModel();
        recreatWorkFlowDataModel();
        phaseOutTimeCalculation();
        repititation = disciplineRequests.getRepititionOfOffence();
        saveOrUpdateButton = "Update";
        update = 1;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
        if (offenceTypes != null) {
            disciplinePenlityses = disciplinePenlitysBeansLocal.findByOffenceId(offenceTypes);
        }

    }
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Main">

    public void penalityDetails(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            disciplinePenlitys = null;
            disciplinePenlitys = (HrDisciplinePenlitys) event.getNewValue();
        }
    }

    public void seachByOffenceCode() {
        offenceTypesesList = offenceTypesBeanLocal.findByOffenceCode();
    }

    public ArrayList<HrEmployees> SearchByFname(String employee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployeesAccuser.setFirstName(employee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployeesAccuser);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        hrEmployeesAccuser.setFirstName((event.getObject().toString()));
        hrEmployeesAccuser = hrEmployeeBean.getByFirstName(hrEmployeesAccuser);
        departmentsOfAccuser = hrEmployeesAccuser.getDeptId();
        hrJobTypesOfAccuser = hrEmployeesAccuser.getJobId();
        disciplineRequests.setOffenderId(hrEmployeesAccuser);
    }

    public ArrayList<HrEmployees> SearchByFnameAccused(String employee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployeesRequester.setFirstName(employee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployeesRequester);
        return employees;
    }

    public void getByFirstNameAccused(SelectEvent event) {
        hrEmployeesRequester.setFirstName((event.getObject().toString()));
        hrEmployeesRequester = hrEmployeeBean.getByFirstName(hrEmployeesRequester);
        departmentsOfAccused = hrEmployeesRequester.getDeptId();
        hrJobTypesOfAccused = hrEmployeesRequester.getJobId();
        disciplineRequests.setRequesterId(hrEmployeesRequester);
    }

    public DataModel<HrDisciplineRequests> findListByFname() {
        if (hrEmployeesRequester.getFirstName() != null) {
            disciplineRequestDataModel = new ListDataModel(new ArrayList(requestBeanLocal.searchEmployeeByName(hrEmployeesRequester)));
        }
        return null;
    }

    public void offenceIdChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            offenceTypes = (HrDisciplineOffenceTypes) event.getNewValue();
            disciplineRequests.setOffenceTypeId(offenceTypes);
            disciplineRequests.setPreparedOn(StringDateManipulation.toDayInEc());
            phaseOutTimeCalculation();
        }

    }
    Integer repititation = 0;
    Integer phaseOutTime = 0;

    public Integer getRepititation() {
        return repititation;
    }

    public void setRepititation(Integer repititation) {
        this.repititation = repititation;
    }

    public Integer getPhaseOutTime() {
        return phaseOutTime;
    }

    public void setPhaseOutTime(Integer phaseOutTime) {
        this.phaseOutTime = phaseOutTime;
    }

    public void phaseOutTimeCalculation() {
        disciplineRequests.setOffenceDate(disciplineRequests.getOffenceDate());
        HrDisciplineRequests disciplineReq = new HrDisciplineRequests();
        disciplineRequests.setOffenceTypeId(disciplineRequests.getOffenceTypeId());
        disciplineRequests.setOffenderId(hrEmployeesAccuser);
        disciplineReq = requestBeanLocal.findDisciplineReqByOffenderIdAndOffenceType(disciplineRequests);
        HrDisciplineRequests dr = new HrDisciplineRequests();
        if (disciplineReq != null) {
            String offenceDate[] = disciplineReq.getOffenceDate().split("/");
            int previusDay = Integer.parseInt(offenceDate[0]);
            int previusMonth = Integer.parseInt(offenceDate[1]);
            int previusYear = Integer.parseInt(offenceDate[2]);
            String currentDate[] = dateFormat(StringDateManipulation.toDayInEc()).split("/");
            int currentDay = Integer.parseInt(currentDate[0]);
            int currentMonth = Integer.parseInt(currentDate[1]);
            int currentYear = Integer.parseInt(currentDate[2]);
            int dayBetween = (currentDay - previusDay) + ((currentMonth - previusMonth) * 30) + ((currentYear - previusYear) * 360);
            int monthBetween = dayBetween / 30;
            int newMonthDifferece = Integer.parseInt(offenceTypes.getPhaseoutPeriod()) - monthBetween;
            if (newMonthDifferece > 0) {
                phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod()) + monthBetween;
                repititation = disciplineReq.getRepititionOfOffence() + 1;
            } else {
                phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod());
                repititation = 1;
            }
        } else {
            phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod());
            repititation = 1;
        }
    }

    public HrDisciplineFileUpload getDisciplineFileUpload() {
        if (disciplineFileUpload == null) {
            disciplineFileUpload = new HrDisciplineFileUpload();
        }
        return disciplineFileUpload;
    }

    public void setDisciplineFileUpload(HrDisciplineFileUpload disciplineFileUpload) {
        this.disciplineFileUpload = disciplineFileUpload;
    }

    public void saveDisciplineRequests() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveDisciplineRequests", dataset)) {
                HrDisciplineRequests disciplineReqData = new HrDisciplineRequests();
                disciplineRequests.setOffenceTypeId(disciplineRequests.getOffenceTypeId());
                disciplineRequests.setOffenderId(hrEmployeesAccuser);
                disciplineReqData = requestBeanLocal.findDisciplineReqRecordByOffenderIdAndOffenceType(disciplineRequests);
                HrDisciplineFileUpload fileUpload = new HrDisciplineFileUpload();
                HrLuDmArchive luDmArchiveLocal = new HrLuDmArchive();
                if (hrEmployeesRequester.equals(hrEmployeesAccuser)) {
                    JsfUtil.addFatalMessage("The Requester and The Offender Can not be the same");
                } else if (update == 0) {
                    if (disciplineReqData != null) {
                        JsfUtil.addFatalMessage("There is a discipline case for this user please approve first!!!");
                    } else {
                        disciplineRequests.setRepititionOfOffence(repititation);
                        disciplineRequests.setOffenceTypeId(offenceTypes);
                        for (int j = 0; j < byteList.size(); j++) {
                            luDmArchiveLocal = new HrLuDmArchive();
                            luDmArchiveLocal.setFileName(fileNameList.get(j));
                            luDmArchiveLocal.setFileType(fileTypeList.get(j));
                            luDmArchiveLocal.setUploadFile(byteListFinal.get(j));
                            hrLuDmArchiveBeanLocal.create(luDmArchiveLocal);
                            fileUpload.setDocumentId(luDmArchiveLocal);
                            fileUpload.setDisciplineCaseId(disciplineRequests);
                            disciplineRequests.getHrDisciplineFileUploadList().add(fileUpload);
                            fileUpload = new HrDisciplineFileUpload();
                            luDmArchiveLocal = new HrLuDmArchive();
                        }
                        workFlow.setUserStatus(Constants.PREPARE_VALUE);
                        disciplineRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
                        disciplineRequests.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        disciplineRequests.setPreparedOn(preparedDate);
                        wfHrProcessed.setDecision(Integer.valueOf(disciplineRequests.getStatus()));
                        wfHrProcessed.setDisciplineId(disciplineRequests);
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        wfHrProcessed.setProcessedOn(disciplineRequests.getPreparedOn());
                        requestBeanLocal.saveOrUpdate(disciplineRequests);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        JsfUtil.addSuccessMessage("Saved Successfully");
                        resetDisciplineRequests();
                    }
                } else {
                    for (int j = 0; j < byteList.size(); j++) {
                        luDmArchiveLocal = new HrLuDmArchive();
                        luDmArchiveLocal.setFileName(fileNameList.get(j));
                        luDmArchiveLocal.setFileType(fileTypeList.get(j));
                        luDmArchiveLocal.setUploadFile(byteListFinal.get(j));
                        hrLuDmArchiveBeanLocal.edit(luDmArchiveLocal);
                        fileUpload.setDocumentId(luDmArchiveLocal);
                        fileUpload.setDisciplineCaseId(disciplineRequests);
                        disciplineRequests.getHrDisciplineFileUploadList().add(fileUpload);
                        fileUpload = new HrDisciplineFileUpload();
                        luDmArchiveLocal = new HrLuDmArchive();
                    }
                    disciplineRequests.setRepititionOfOffence(repititation);
                    requestBeanLocal.edit(disciplineRequests);
                    JsfUtil.addSuccessMessage("Updated Successfully");
                    resetDisciplineRequests();
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveDisciplineRequests");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void resetDisciplineRequests() {
        disciplineRequests = new HrDisciplineRequests();
        hrEmployeesAccuser = new HrEmployees();
        hrEmployeesRequester = new HrEmployees();
        offenceTypes = new HrDisciplineOffenceTypes();
        hrJobTypesOfAccuser = new HrJobTypes();
        hrJobTypesOfAccused = new HrJobTypes();
        departmentsOfAccused = new HrDepartments();
        departmentsOfAccuser = new HrDepartments();
        setPhaseOutTime(null);
        setRepititation(null);
        setFullName(null);
        wfHrProcessed = new WfHrProcessed();
        saveOrUpdateButton = "Save";
        update = 0;
        wfHrProcessed.setCommentGiven(null);
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
    DataModel<HrDisciplineFileUpload> fileUploadDataModel;
    private boolean isFileSelected = false;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileNameWithExtension() {
        return fileNameWithExtension;
    }

    public void setFileNameWithExtension(String fileNameWithExtension) {
        this.fileNameWithExtension = fileNameWithExtension;
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    public List<String> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<String> fileTypeList) {
        this.fileTypeList = fileTypeList;
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
            fileNameWithExtension = fileName + "." + fileType;
            JsfUtil.addSuccessMessage("Upload Successfully");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRowSelect(SelectEvent event) {
        disciplineFileUpload = (HrDisciplineFileUpload) event.getObject();
        isFileSelected = true;
        hrLuDmArchive = hrDisciplineFileUpload.getDocumentId();
    }

    public DataModel<HrDisciplineFileUpload> getFileUploadDataModel() {
        return fileUploadDataModel;
    }

    public void setFileUploadDataModel(DataModel<HrDisciplineFileUpload> fileUploadDataModel) {
        this.fileUploadDataModel = fileUploadDataModel;
    }

    public void recreateDmsDataModel() {
        fileUploadDataModel = null;
        fileUploadDataModel = new ListDataModel<>(disciplineRequests.getHrDisciplineFileUploadList());
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected == true) {
            dataUpload.getHrmsFileRefNumber(hrLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Please Select Row File U want to Download");
        }
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public void remove() {
        hrDisciplineFileUpload.setId(hrDisciplineFileUpload.getId());
        requestBeanLocal.remove(hrDisciplineFileUpload);
        disciplineRequests.getHrDisciplineFileUploadList().remove(hrDisciplineFileUpload);
        recreateDmsDataModel();
        JsfUtil.addSuccessMessage("Document Seccesfully Deleted!!! ");

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Work Flow ">
    public void recreatWorkFlowDataModel() {
        hrWorkFlowDataModel = null;
        for (int i = 0; i < disciplineRequests.getHrWorkFlowProccedList().size(); i++) {
            if (disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 0) {
                disciplineRequests.getHrWorkFlowProccedList().get(i).setChangedStstus("Request");
            } else if (disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 1 || disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 3) {
                disciplineRequests.getHrWorkFlowProccedList().get(i).setChangedStstus("Approved");
            } else if (disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 2 || disciplineRequests.getHrWorkFlowProccedList().get(i).getDecision() == 4) {
                disciplineRequests.getHrWorkFlowProccedList().get(i).setChangedStstus("Rejected");
            }
        }
        hrWorkFlowDataModel = new ListDataModel(new ArrayList(disciplineRequests.getHrWorkFlowProccedList()));
    }

    public void saveWorkFlowInformation() {
        workFlow.setUserStatus(Constants.PREPARE_VALUE);
        disciplineRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
        disciplineRequests.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
        wfHrProcessed.setDecision(Integer.valueOf(disciplineRequests.getStatus()));
        wfHrProcessed.setDisciplineId(disciplineRequests);
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        requestBeanLocal.saveOrUpdate(disciplineRequests);
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Discipline History ">
    public ArrayList<HrEmployees> searchEmployeeById(String empId) {
        ArrayList<HrEmployees> employee = null;
        hrEmployee.setEmpId(empId);
        employee = hrEmployeeBeanLocal.SearchByEmpId(hrEmployee);
        return employee;
    }

    public void getById(SelectEvent event) {
        try {
            hrEmployee.setEmpId(event.getObject().toString());
            hrEmployee = hrEmployeeBeanLocal.getByEmpId(hrEmployee);
            hrEmployee.setId(hrEmployee.getId());
            Integer offenderId = hrEmployee.getId();
            ListOfDisciplineHistory = requestBeanLocal.findDisciplinByOffenderId(offenderId);
            hrDisciplineHistoryModel = new ListDataModel(ListOfDisciplineHistory);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    //</editor-fold>

    public String navigateToOffenceTypePage() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("offenceType", offenceTypes);
        return "displineOffenseType";
    }
        }
