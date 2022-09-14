/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.recruitment;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.hrms.mapper.organization.HrDeptJobsFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import webService.UserStatus;

/**
 *
 * @author user
 */
@Named(value = "hrRecruitmentReqApproveController")
@ViewScoped
public class HrRecruitmentReqApproveController implements Serializable {

    public HrRecruitmentReqApproveController() {
    }

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrDeptJobs hrDeptJobs;
    @Inject
            HrRecruitmentRequests recruitmentRequests;
    @Inject
            HrRecruitmentRequests selectedRequests;
    @Inject
            HrJobEducQualifications hrJobEducQualifications;
    @Inject
            HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean sessionBeanLocal;
    @Inject
            WorkFlow workFlow;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            HrDeptJobsFacade hrDeptJobsFacade;
    @EJB
            HrDepartmentsFacade hrDepartmentsFacade;
    @EJB
            HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
            HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
            WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List Initialization">
    private List<HrRecruitmentRequests> recruitmentList;
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    DataModel<HrRecruitmentRequests> recruitmentRequestsListDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrRecruitmentRequests> recruitmentRqstList;
    SelectItem[] listOfJob;
    List<SelectItem> filterByStatus = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private TreeNode selectedNode;
    private int update = 0;
    String totalNoAllowedJob;
    int countEmpInJob;
    int NoCurrentEmployee;
    int maxEmpValue;
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "Add New";
    private String headerTitle = "Search Requests";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlUpdate = false;
    private boolean renderPnlManPage = true;
    private String SaveOrUpdateButton = "Save";
    private String serchRequestDate = "";
    private boolean disableBtn = false;
    String selectedValue = "";
    private int selectedStatus;
    int status1 = 0, prepareBy;
    UserStatus userStatus = new UserStatus();
    Status status = new Status();
    private int reqStatus = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post construct">
    @PostConstruct
    public void init() {
        recruitmentRequestsListDataModel = new ListDataModel(hrRecruitmentRequestsBeanLocal.loadRecLists(reqStatus));
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        recruitmentRqstList = hrRecruitmentRequestsBeanLocal.findRecPreparedList();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlUpdate() {
        return renderPnlUpdate;
    }

    public void setRenderPnlUpdate(boolean renderPnlUpdate) {
        this.renderPnlUpdate = renderPnlUpdate;
    }
    private List<HrRecruitmentRequests> selectedRequest;

    public List<HrRecruitmentRequests> getSelectedRequest() {
        return selectedRequest;
    }

    public String getSerchRequestDate() {
        return serchRequestDate;
    }

    public void setSerchRequestDate(String serchRequestDate) {
        this.serchRequestDate = serchRequestDate;
    }

    public void setSelectedRequest(List<HrRecruitmentRequests> selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public DataModel<HrRecruitmentRequests> getRecruitmentRequestsListDataModel() {
        return recruitmentRequestsListDataModel;
    }

    public void setRecruitmentRequestsListDataModel(DataModel<HrRecruitmentRequests> recruitmentRequestsListDataModel) {
        this.recruitmentRequestsListDataModel = recruitmentRequestsListDataModel;
    }

    public void recreateHrRecruitmentRequests() {
        recruitmentRequestsListDataModel = null;
        recruitmentRequestsListDataModel = new ListDataModel(new ArrayList<>(hrRecruitmentRequestsBeanLocal.getRecruitmentRequesttList()));
    }

    public ArrayList<HrRecruitmentRequests> findListByDate() {
        recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListReqToApproveByDate(recruitmentRequests)));
        return null;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    List<HrJobTypes> allJobTypes;

    public List<HrJobTypes> getAllJobTypes() {
        return allJobTypes;
    }

    public void setAllJobTypes(List<HrJobTypes> allJobTypes) {
        this.allJobTypes = allJobTypes;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrJobEducQualifications getHrJobEducQualifications() {
        if (hrJobEducQualifications == null) {
            hrJobEducQualifications = new HrJobEducQualifications();
        }
        return hrJobEducQualifications;
    }

    public void setHrJobEducQualifications(HrJobEducQualifications hrJobEducQualifications) {
        this.hrJobEducQualifications = hrJobEducQualifications;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrDeptJobs getHrDeptJobs() {
        if (hrDeptJobs == null) {
            hrDeptJobs = new HrDeptJobs();
        }
        return hrDeptJobs;
    }

    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
    }

    public SelectItem[] getDeptList() {
        return JsfUtil.getSelectItems(hrDepartmentsFacade.getDeptID(), true);
    }

    public HrRecruitmentRequests getRecruitmentRequests() {
        if (recruitmentRequests == null) {
            recruitmentRequests = new HrRecruitmentRequests();
        }
        return recruitmentRequests;
    }

    public void setRecruitmentRequests(HrRecruitmentRequests recruitmentRequests) {
        this.recruitmentRequests = recruitmentRequests;
    }

    public HrRecruitmentRequests getSelectedRequests() {
        return selectedRequests;
    }

    public void setSelectedRequests(HrRecruitmentRequests selectedRequests) {
        this.selectedRequests = selectedRequests;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges != null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades != null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public List<HrRecruitmentRequests> getRecruitmentRqstList() {
        if (recruitmentRqstList == null) {
            recruitmentRqstList = new ArrayList<>();
        }
        return recruitmentRqstList;
    }

    public void setRecruitmentRqstList(List<HrRecruitmentRequests> recruitmentRqstList) {
        this.recruitmentRqstList = recruitmentRqstList;
    }

    public String getTotalNoAllowedJob() {
        return totalNoAllowedJob;
    }

    public void setTotalNoAllowedJob(String totalNoAllowedJob) {
        this.totalNoAllowedJob = totalNoAllowedJob;
    }

    public int getCountEmpInJob() {
        return countEmpInJob;
    }

    public void setCountEmpInJob(int countEmpInJob) {
        this.countEmpInJob = countEmpInJob;
    }

    public int getNoCurrentEmployee() {
        return NoCurrentEmployee;
    }

    public void setNoCurrentEmployee(int NoCurrentEmployee) {
        this.NoCurrentEmployee = NoCurrentEmployee;
    }

    public int getMaxEmpValue() {
        return maxEmpValue;
    }

    public void setMaxEmpValue(int maxEmpValue) {
        this.maxEmpValue = maxEmpValue;
    }

    String jobtt;

    public String getJobtt() {
        return jobtt;
    }

    public void setJobtt(String jobtt) {
        this.jobtt = jobtt;
    }

    public ArrayList<HrRecruitmentRequests> getAllRequests() {
        recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findList()));
        return null;
    }

    public SelectItem[] getListOfJob() {
        return listOfJob;
    }

    public void setListOfJob(SelectItem[] listOfJob) {
        this.listOfJob = listOfJob;
    }

    public List<SelectItem> getFilterByStatus() {
        return hrRecruitmentRequestsBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    private DataModel<HrJobEducQualifications> hrJobQualificationModel;

    public DataModel<HrJobEducQualifications> getHrJobQualificationModel() {
        if (hrJobQualificationModel == null) {
            hrJobQualificationModel = new ListDataModel<>();
        }
        return hrJobQualificationModel;
    }

    public void setHrJobQualificationModel(DataModel<HrJobEducQualifications> hrJobQualificationModel) {
        this.hrJobQualificationModel = hrJobQualificationModel;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public void recreateModelDetail() {
        hrJobQualificationModel = null;
        hrJobQualificationModel = new ListDataModel(new ArrayList<>(hrJobTypes.getHrJobEducQualificationsList()));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main methods">
    public void workflowDataModel() {
        workflowDataModel = null;
        for (int i = 0; i < recruitmentRequests.getWfHrProcessedList().size(); i++) {
            if (recruitmentRequests.getWfHrProcessedList().get(i).getDecision() == 0) {
                recruitmentRequests.getWfHrProcessedList().get(i).setChangedStstus("Request");
            } else if (recruitmentRequests.getWfHrProcessedList().get(i).getDecision() == 1 || recruitmentRequests.getWfHrProcessedList().get(i).getDecision() == 3) {
                recruitmentRequests.getWfHrProcessedList().get(i).setChangedStstus("Approved");
            } else if (recruitmentRequests.getWfHrProcessedList().get(i).getDecision() == 2 || recruitmentRequests.getWfHrProcessedList().get(i).getDecision() == 4) {
                recruitmentRequests.getWfHrProcessedList().get(i).setChangedStstus("Rejected");
            }
        }
        workflowDataModel = new ListDataModel(new ArrayList(recruitmentRequests.getWfHrProcessedList()));
    }
    
    public ArrayList<HrEmployees> SearchByEmpId(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(hrEmployee);
        employees = hrEmployeeBeanLocal.SearchByEmpId(hrEmployees);
        return employees;
    }
    
    public void getEmployeeByEmpId(SelectEvent event) {
        hrEmployees.setEmpId(event.getObject().toString());
        hrEmployeeBeanLocal.getByEmpId(hrEmployees);
        recruitmentRequests.setRequesterId(hrEmployees);
    }
    
    public void btnSearch() {
        renderPnlUpdate = false;
        renderPnlManPage = true;
    }
    
    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("Add New")) {
            renderPnlUpdate = true;
            renderPnlManPage = false;
            disableBtn = false;
            createOrSearchBundle = "Search";
            headerTitle = "Recruitment Registration Request";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlUpdate = false;
            renderPnlManPage = true;
            recruitmentRequestsListDataModel = null;
            createOrSearchBundle = "Add New";
            headerTitle = "Search Recruitment Request";
            setIcone("ui-icon-plus");
        }
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
//        this.setListOfJob(JsfUtil.getSelectItems(hrRecruitmentRequestsBeanLocal.listOfJobType(key), true));
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        recruitmentRequests.setDeptId(hrDepartments);
        allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
        
    }
    
    public void onNodeSelectSearch(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        recruitmentRequests.setDeptId(hrDepartments);
        
    }
    
    public void searchRecruitment() {
        if (hrDepartments.getDepName() == null && serchRequestDate.isEmpty()) {
            recruitmentList = hrRecruitmentRequestsBeanLocal.findList();
            recruitmentRequestsListDataModel = new ListDataModel(recruitmentList);
        } else if (!serchRequestDate.isEmpty() && hrDepartments.getDepName() == null) {
            recruitmentRequests.setRequestDate(serchRequestDate);
            recruitmentList = hrRecruitmentRequestsBeanLocal.findListReqToApproveByDate(recruitmentRequests);
            recruitmentRequestsListDataModel = new ListDataModel(recruitmentList);
        } else if (serchRequestDate.isEmpty() && hrDepartments.getDepName() != null) {
            recruitmentList = hrRecruitmentRequestsBeanLocal.findListByDepartment(hrDepartments);
            recruitmentRequestsListDataModel = new ListDataModel(recruitmentList);
        } else if (!serchRequestDate.isEmpty() && (hrDepartments.getDepName() != null)) {
            recruitmentList = hrRecruitmentRequestsBeanLocal.findByDetAndDate(hrDepartments, recruitmentRequests);
            recruitmentRequestsListDataModel = new ListDataModel(recruitmentList);
        }
    }
    
    public void clear() {
        recruitmentRequests = null;
        hrEmployees = null;
        hrDepartments = null;
        hrJobTypes = null;
        countEmpInJob = 0;
        maxEmpValue = 0;
        totalNoAllowedJob = null;
        totalNoAllowedJob = null;
        hrJobQualificationModel = null;
        wfHrProcessed = new WfHrProcessed();
        workflowDataModel = null;
        NoCurrentEmployee = 0;
        
    }
    
    public void populateRecruitment() {
        hrDepartments = recruitmentRequests.getDeptId();
        hrEmployees = recruitmentRequests.getRequesterId();
        hrJobTypes = recruitmentRequests.getJobId();
        recruitmentRequests.setJobId(hrJobTypes);
        countEmpInJob = hrDeptJobsFacade.findByJobId(hrJobTypes);
        totalNoAllowedJob = hrRecruitmentRequestsBeanLocal.totalNoAllowedJob(hrDepartments, hrJobTypes);
        NoCurrentEmployee = hrRecruitmentRequestsBeanLocal.totalNoEmployeesInDepJob(hrDepartments, hrJobTypes);
        maxEmpValue = Integer.parseInt(totalNoAllowedJob) - NoCurrentEmployee;
        hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
        if (hrSalaryScaleRanges != null && !hrSalaryScaleRanges.getGradeId().equals("")) {
            hrLuGrades = hrSalaryScaleRanges.getGradeId();
        } else {
            hrLuGrades = new HrLuGrades();
        }
        if (recruitmentRequests.getStatus().equals(Constants.PREPARE_VALUE)) {
            disableBtn = false;
        } else {
            disableBtn = true;
        }
        btnaddvisibility = false;
        renderPnlUpdate = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        recreateModelDetail();
        workflowDataModel();
        update = 1;
        SaveOrUpdateButton = "Update";
    }
    
    public void populateDatatable(SelectEvent event) {
        recruitmentRequests = (HrRecruitmentRequests) event.getObject();
        populateRecruitment();
    }
    
    public void populateNotifcation(SelectEvent event) {
        try {
            if (null != event.getObject()) {
                recruitmentRequests = (HrRecruitmentRequests) event.getObject();
                populateRecruitment();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('widNotf').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again");
        }
    }
    
    public void handleDecision(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }
    
    public void saveRecruitmentApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveRecruitmentApprove", dataset)) {
                
                BigInteger noReqEmp = recruitmentRequests.getNumOfEmpsRequested();
                BigInteger noAppEmp = recruitmentRequests.getNumOfEmpsApproved();
                if (Integer.parseInt(noAppEmp.toString()) <= Integer.parseInt(noReqEmp.toString())) {
                    if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        recruitmentRequests.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        recruitmentRequests.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        recruitmentRequests.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        recruitmentRequests.setStatus(workFlow.getUserStatus());
                    }
                    wfHrProcessed.setRecruitmentId(recruitmentRequests);
                    wfHrProcessed.setDecision(recruitmentRequests.getStatus());
                    wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                    hrRecruitmentRequestsBeanLocal.edit(recruitmentRequests);
                    wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    recruitmentRqstList = hrRecruitmentRequestsBeanLocal.findRecPreparedList();
                    JsfUtil.addSuccessMessage("Successfully submitted");
                    clear();
                } else {
                    JsfUtil.addFatalMessage("Please insert No. of approved employees less than or equal to " + recruitmentRequests.getNumOfEmpsRequested());
                }
                
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveRecruitmentApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }
    
    public void filiterByStatus_VLC(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            workFlow = new WorkFlow();
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                status.setStatus3(Constants.APPROVE_REJECT_VALUE);
                recruitmentRqstList = hrRecruitmentRequestsBeanLocal.loadToApproveInvoiceList(status, sessionBeanLocal.getUserId());
                recruitmentRequestsListDataModel = new ListDataModel<>(recruitmentRqstList);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                recruitmentRqstList = hrRecruitmentRequestsBeanLocal.loadToModifyInvoiceList(status, sessionBeanLocal.getUserId());
                recruitmentRequestsListDataModel = new ListDataModel<>(recruitmentRqstList);
            }
        }
    }
    
    private void populateTable() {
        try {
            List<HrRecruitmentRequests> readFilteredTransfer = hrRecruitmentRequestsBeanLocal.loadRecLists(reqStatus);
            recruitmentRequestsListDataModel = new ListDataModel(readFilteredTransfer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
//</editor-fold>
}
