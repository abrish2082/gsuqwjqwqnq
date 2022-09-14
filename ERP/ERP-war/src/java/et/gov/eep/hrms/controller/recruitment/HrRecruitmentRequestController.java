/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.recruitment;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.mapper.organization.HrDeptJobsFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
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
@Named(value = "hrRecruitmentRequestController")
@ViewScoped
public class HrRecruitmentRequestController implements Serializable {

    /**
     * Creates a new instance of HrRecruitmentRequestController
     */
  
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
            HrRecruitmentRequests selectedRequest;
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
            WorkFlow workflow;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
            HrDeptJobsFacade hrDeptJobsFacade;
    @EJB
            HrDepartmentsBeanLocal hrDepartmentsFacade;
    @EJB
            HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
            WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List and datamodel declaration">
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private List<HrRecruitmentRequests> recruitmentList;
    DataModel<HrRecruitmentRequests> recruitmentListDataModel;
    DataModel<HrRecruitmentRequests> recruitmentRequestsListDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    Set<String> checkRecruitment = new HashSet<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    SelectItem[] listOfJob;
    private List<HrRecruitmentRequests> recruitmentRequestList;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    String totalNoAllowedJob;
    boolean recType;
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "Add New";
    private String headerTitle = "Search Requests";
    private String headerTitleRequest = " Registration Request";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    StringDateManipulation stringDateManipulation = new StringDateManipulation();
    private String SaveOrUpdateButton = "Save";
    private String serchRequestDate = "";
    private boolean disableBtn = false;
    private int selectedStatus;
    int status1 = 0, prepareBy;
    UserStatus userStatus = new UserStatus();
    Status status = new Status();
    private boolean btnNewRender = false;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post construct">
    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAllDepartmentInfo();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        recruitmentRequests.setRecruitmentType("Permanent");
        recruitmentRequests.setRequestDate(StringDateManipulation.toDayInEc());
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
//        getAllRequests();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public String getSerchRequestDate() {
        return serchRequestDate;
    }

    public void setSerchRequestDate(String serchRequestDate) {
        this.serchRequestDate = serchRequestDate;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getHeaderTitleRequest() {

        return headerTitleRequest;
    }

    public void setHeaderTitleRequest(String headerTitleRequest) {
        this.headerTitleRequest = headerTitleRequest;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    /**
     *
     * @param hrJobTypes
     */
    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    List<HrJobTypes> allJobTypes;

    public List<HrJobTypes> getAllJobTypes() {
        return allJobTypes;
    }

    public void setAllJobTypes(List<HrJobTypes> allJobTypes) {
        this.allJobTypes = allJobTypes;
    }

    /**
     *
     * @return
     */
    public HrJobEducQualifications getHrJobEducQualifications() {
        if (hrJobEducQualifications == null) {
            hrJobEducQualifications = new HrJobEducQualifications();
        }
        return hrJobEducQualifications;
    }

    /**
     *
     * @param hrJobEducQualifications
     */
    public void setHrJobEducQualifications(HrJobEducQualifications hrJobEducQualifications) {
        this.hrJobEducQualifications = hrJobEducQualifications;
    }

    /**
     *
     * @return
     */
    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    /**
     *
     * @param hrEmployees
     */
    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    /**
     *
     * @return
     */
    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    /**
     *
     * @param hrDepartments
     */
    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    /**
     *
     * @return
     */
    public HrDeptJobs getHrDeptJobs() {
        if (hrDeptJobs == null) {
            hrDeptJobs = new HrDeptJobs();
        }
        return hrDeptJobs;
    }

    /**
     *
     * @param hrDeptJobs
     */
    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getDeptList() {
        return JsfUtil.getSelectItems(hrDepartmentsFacade.getDeptID(), true);
    }

    /**
     *
     * @return
     */
    public HrRecruitmentRequests getRecruitmentRequests() {
        if (recruitmentRequests == null) {
            recruitmentRequests = new HrRecruitmentRequests();
        }
        return recruitmentRequests;
    }

    /**
     *
     * @param recruitmentRequests
     */
    public void setRecruitmentRequests(HrRecruitmentRequests recruitmentRequests) {
        this.recruitmentRequests = recruitmentRequests;
    }

    public HrRecruitmentRequests getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(HrRecruitmentRequests selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    /**
     *
     * @return
     */
    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    /**
     *
     * @param SaveOrUpdateButton
     */
    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    /**
     *
     * @return
     */
    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    /**
     *
     * @param hrSalaryScaleRanges
     */
    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    /**
     *
     * @return
     */
    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades == null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }

    /**
     *
     * @param hrLuGrades
     */
    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public List<SelectItem> getFilterByStatus() {
        return hrRecruitmentRequestsBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    /**
     *
     * @return
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     *
     * @param root
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     *
     * @return
     */
    public TreeNode getRoot2() {
        return root2;
    }

    /**
     *
     * @param root2
     */
    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    /**
     *
     * @return
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     *
     * @param selectedNode
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    /**
     *
     * @return
     */
    public String getTotalNoAllowedJob() {
        return totalNoAllowedJob;
    }

    /**
     *
     * @param totalNoAllowedJob
     */
    public void setTotalNoAllowedJob(String totalNoAllowedJob) {
        this.totalNoAllowedJob = totalNoAllowedJob;
    }

    public DataModel<HrRecruitmentRequests> getRecruitmentRequestsListDataModel() {
        return recruitmentRequestsListDataModel;
    }

    public void setRecruitmentRequestsListDataModel(DataModel<HrRecruitmentRequests> recruitmentRequestsListDataModel) {
        this.recruitmentRequestsListDataModel = recruitmentRequestsListDataModel;
    }

    public boolean getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main Methods">
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

    public void newButtonAction() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        disableBtn = false;
        createOrSearchBundle = "Search";
        headerTitle = "Recruitment Requests";
        btnNewRender = false;
        clearRecruitment();
    }

    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("Add New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            disableBtn = false;
             btnNewRender = false;
            createOrSearchBundle = "Search";
            headerTitle = "Recruitment Requests";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            recruitmentRequestsListDataModel = null;
            createOrSearchBundle = "Add New";
            headerTitle = "Search Requests";
            setIcone("ui-icon-plus");
             btnNewRender = false;
        }
    }

    /**
     *
     * @param liste
     * @param id
     * @param node
     */
    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }

    public ArrayList<HrEmployees> searchEmpByID(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setEmpId(hrEmployee);
        employe = hrEmployeeBeanLocal.SearchByEmpId(hrEmployees);
        return employe;
    }

    public void getEmpByID(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = hrEmployeeBeanLocal.getByEmpId(hrEmployees);
            recruitmentRequests.setRequesterId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            setDeptNameToSave(hrDepartments.getDepName());
            recruitmentRequests.setDeptId(hrDepartments);
            allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Can't get value");
        }
    }

    /**
     *
     * @param hrEmployee
     * @return
     */
    public ArrayList<HrEmployees> SearchByEmpId(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(hrEmployee);
        employees = hrEmployeeBeanLocal.SearchByEmpId(hrEmployees);
        return employees;
    }

    /**
     *
     * @param event
     */
    public void getEmployeeByEmpId(SelectEvent event) {
        hrEmployees.setEmpId(event.getObject().toString());
//        hrEmployees = hrEmployeeBeanLocal.getByEmpId(hrEmployees);
        recruitmentRequests.setRequesterId(hrEmployees);
        hrDepartments = hrEmployees.getDeptId();
        setDeptNameToSave(hrDepartments.getDepName());
        recruitmentRequests.setDeptId(hrDepartments);
        allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
    }

    public void clear() {
        recruitmentRequests = null;
        hrEmployees = null;
        hrDepartments = null;
        hrJobTypes = null;

    }

    String DeptNameSearch;
    String DeptNameToSave;

    public String getDeptNameSearch() {
        return DeptNameSearch;
    }

    public void setDeptNameSearch(String DeptNameSearch) {
        this.DeptNameSearch = DeptNameSearch;
    }

    public String getDeptNameToSave() {
        return DeptNameToSave;
    }

    public void setDeptNameToSave(String DeptNameToSave) {
        this.DeptNameToSave = DeptNameToSave;
    }

    String jobtt;

    /**
     *
     * @return
     */
    public String getJobtt() {
        return jobtt;
    }

    /**
     *
     * @param jobtt
     */
    public void setJobtt(String jobtt) {
        this.jobtt = jobtt;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getListOfJob() {
        return listOfJob;
    }

    /**
     *
     * @param listOfJob
     */
    public void setListOfJob(SelectItem[] listOfJob) {
        this.listOfJob = listOfJob;
    }

    private DataModel<HrJobEducQualifications> hrJobQualificationModel;

    /**
     *
     * @return
     */
    public DataModel<HrJobEducQualifications> getHrJobQualificationModel() {
        if (hrJobQualificationModel == null) {
            hrJobQualificationModel = new ListDataModel<>();
        }
        return hrJobQualificationModel;
    }

    /**
     *
     * @param hrJobQualificationModel
     */
    public void setHrJobQualificationModel(DataModel<HrJobEducQualifications> hrJobQualificationModel) {
        this.hrJobQualificationModel = hrJobQualificationModel;
    }

    /**
     *
     */
    public void recreateModelDetail() {
        hrJobQualificationModel = null;
        hrJobQualificationModel = new ListDataModel(new ArrayList<>(hrJobTypes.getHrJobEducQualificationsList()));
    }

    int updateStatus = 0;

    List<HrRecruitmentRequests> requestList = new ArrayList<>();

    public DataModel<HrRecruitmentRequests> getRecruitmentListDataModel() {
        if (recruitmentListDataModel == null) {
            recruitmentListDataModel = new ListDataModel<>();
        }
        return recruitmentListDataModel;
    }

    public void setRecruitmentListDataModel(DataModel<HrRecruitmentRequests> recruitmentListDataModel) {
        this.recruitmentListDataModel = recruitmentListDataModel;
    }

    /**
     *
     * @return
     */
    public void recreateHrRecruitmentRequests() {
        recruitmentRequestsListDataModel = null;
        recruitmentRequestsListDataModel = new ListDataModel(new ArrayList<>(hrRecruitmentRequestsBeanLocal.getRecruitmentRequesttList()));
    }

    /**
     *
     * @param event
     */
    int maxEmpValue;

    public int getMaxEmpValue() {
        return maxEmpValue;
    }

    public void setMaxEmpValue(int maxEmpValue) {
        this.maxEmpValue = maxEmpValue;
    }

    int noCurrentEmployee;

    public int getNoCurrentEmployee() {
        return noCurrentEmployee;
    }

    public void setNoCurrentEmployee(int noCurrentEmployee) {
        this.noCurrentEmployee = noCurrentEmployee;
    }

    public void onNodeSelectSearch(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        setDeptNameSearch(hrDepartments.getDepName());
        recruitmentRequests.setDeptId(hrDepartments);
        RequestContext context1 = RequestContext.getCurrentInstance();
        context1.execute("PF('digDepSearch').hide();");
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        if (!selectedNode.getData().toString().isEmpty()) {
            int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
            hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
            setDeptNameToSave(hrDepartments.getDepName());
            recruitmentRequests.setDeptId(hrDepartments);
            allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
            RequestContext context2 = RequestContext.getCurrentInstance();
            context2.execute("PF('dlgDept').hide();");
        }
    }

    public void populateRecruitment(SelectEvent event) {
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
        createOrSearchBundle = "Search";
        recruitmentRequests = (HrRecruitmentRequests) event.getObject();
        hrDepartments = recruitmentRequests.getDeptId();
        setDeptNameToSave(hrDepartments.getDepName());
        checkmployee();
        allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
        hrJobTypes = recruitmentRequests.getJobId();
        hrDeptJobs.setJobId(hrJobTypes);
        hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
        if (recruitmentRequests.getStatus().equals(Constants.PREPARE_VALUE) || recruitmentRequests.getStatus().equals(Constants.CHECK_REJECT_VALUE)
                || recruitmentRequests.getStatus().equals(Constants.APPROVE_REJECT_VALUE)) {
            disableBtn = false;
        } else {
            disableBtn = true;
        }
        if (hrSalaryScaleRanges != null && !hrSalaryScaleRanges.getGradeId().equals("")) {
            hrLuGrades = hrSalaryScaleRanges.getGradeId();
        } else {
            hrLuGrades = new HrLuGrades();
        }
        totalNoAllowedJob = hrRecruitmentRequestsBeanLocal.totalNoAllowedJob(hrDepartments, hrJobTypes);
        noCurrentEmployee = hrRecruitmentRequestsBeanLocal.totalNoEmployeesInDepJob(hrDepartments, hrJobTypes);
        maxEmpValue = Integer.parseInt(totalNoAllowedJob) - noCurrentEmployee;
        recreateRecruitmentModel();
        recreateModelDetail();
        workflowDataModel();
        btnaddvisibility = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
         btnNewRender = true;
        createOrSearchBundle = "Search";
        headerTitle = "Recruitment Registration Request";
        setIcone("ui-icon-search");
    }

    public void checkmployee() {
        if (recruitmentRequests.getRequesterId() != null) {
            hrEmployees = recruitmentRequests.getRequesterId();
        } else {
            hrEmployees = new HrEmployees();
        }
    }

    public void jobChangedListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrJobTypes.setId(Integer.valueOf(event.getNewValue().toString()));
            hrJobTypes = hrRecruitmentRequestsBeanLocal.findByName(hrJobTypes.getId());
            recruitmentRequests.setJobId(hrJobTypes);
            hrJobEducQualifications.setJobId(hrJobTypes);
            hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
            hrDeptJobs.setJobId(hrJobTypes);
            if (!hrSalaryScaleRanges.getGradeId().equals(null) || !hrSalaryScaleRanges.getGradeId().equals("")) {
                hrLuGrades = hrSalaryScaleRanges.getGradeId();
            } else {
                hrLuGrades = new HrLuGrades();
            }
            totalNoAllowedJob = hrRecruitmentRequestsBeanLocal.totalNoAllowedJob(hrDepartments, hrJobTypes);
            noCurrentEmployee = hrRecruitmentRequestsBeanLocal.totalNoEmployeesInDepJob(hrDepartments, hrJobTypes);
            maxEmpValue = Integer.parseInt(totalNoAllowedJob) - noCurrentEmployee;
            recreateModelDetail();
        }
    }

    public void addRecruitment() {
        if (checkRecruitment.contains(hrJobTypes.getJobTitle() + recruitmentRequests.getRecruitmentType())) {
            JsfUtil.addFatalMessage("Recruitment  " + hrJobTypes.getJobTitle() + "in" + recruitmentRequests.getRecruitmentType() + "  is Duplicated");
        } else {
            recruitmentRequests.setStatus(0);
            recruitmentRequests.setRequesterId(hrEmployees);
            recruitmentRequests.setJobId(hrJobTypes);
            requestList.add(recruitmentRequests);
            checkRecruitment.add(hrJobTypes.getJobTitle() + recruitmentRequests.getRecruitmentType());
            recreateRecruitmentModel();
            clearRecruitment();
        }
    }

    public void clearRecruitment() {
        recruitmentRequests = new HrRecruitmentRequests();
        hrEmployees = new HrEmployees();
        DeptNameToSave = null;
        hrJobTypes = new HrJobTypes();
        recreateModelDetail();
        hrLuGrades = new HrLuGrades();
        hrSalaryScaleRanges = new HrSalaryScaleRanges();
        noCurrentEmployee = 0;
        maxEmpValue = 0;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void recreateRecruitmentModel() {
        recruitmentListDataModel = null;
        recruitmentListDataModel = new ListDataModel(requestList);
    }

    public void saveRecruitmentRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveRecruitmentRequest", dataset)) {

                BigInteger bi = recruitmentRequests.getNumOfEmpsRequested();
                int noEmp = Integer.parseInt(bi.toString());
                if (DeptNameToSave == null) {
                    JsfUtil.addFatalMessage("Department can't be null select department");
                } else {
                    if (noEmp <= maxEmpValue) {
                        if (updateStatus == 0) {
                            recruitmentRequests.setStatus(Constants.PREPARE_VALUE);
                            recruitmentRequests.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                            recruitmentRequests.setRequestDate(wfHrProcessed.getProcessedOn());
                            recruitmentRequests.setRemark(wfHrProcessed.getCommentGiven());
                            wfHrProcessed.setDecision(recruitmentRequests.getStatus());
                            wfHrProcessed.setRecruitmentId(recruitmentRequests);
                            wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                            hrRecruitmentRequestsBeanLocal.save(recruitmentRequests);
                            wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                            recruitmentListDataModel = null;
                            clearRecruitment();
                            JsfUtil.addSuccessMessage("Successfully submitted");
                        } else {
                            hrRecruitmentRequestsBeanLocal.edit(recruitmentRequests);
                            JsfUtil.addSuccessMessage("Successfully updated");
                            clearRecruitment();
                        }
                    } else {
                        JsfUtil.addFatalMessage("Number of employee should be less than or equal to" + maxEmpValue);
                    }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveRecruitmentRequest");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }

    public void resetRecruitmentRequest() {
        recruitmentRequests = new HrRecruitmentRequests();
        hrEmployees = null;
        DeptNameToSave = null;
        hrJobTypes = new HrJobTypes();
        recreateModelDetail();
        hrLuGrades = new HrLuGrades();
        hrSalaryScaleRanges = new HrSalaryScaleRanges();
        noCurrentEmployee = 0;
        maxEmpValue = 0;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
        disableBtn = false;
    }

    public ArrayList<HrRecruitmentRequests> getAllRequests() {
        recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.getRecruitmentRequesttList()));
        return hrRecruitmentRequestsBeanLocal.getRecruitmentRequesttList();
    }

    public ArrayList<HrRecruitmentRequests> findListByDate() {
        if (recruitmentRequests.getRequestDate() != null && hrDepartments.getDepName().isEmpty()) {
            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListReqToApproveByDate(recruitmentRequests)));
        } else if (hrDepartments.getDepName() != null) {
            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListByDepartment(hrDepartments)));
        } else {
            JsfUtil.addFatalMessage("Please Insert Request Date Or Department");
            return null;
        }
        return null;
    }

    public ArrayList<HrRecruitmentRequests> findListByDept() {
        if (hrDepartments.getDepName() != null) {
            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListByDepartment(hrDepartments)));
        }
        return null;
    }

    public ArrayList<HrRecruitmentRequests> getAllRequestsByStatus() {
        recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findList()));
        return null;
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

    public ArrayList<HrRecruitmentRequests> findListByDeptDate() {
        if (recruitmentRequests.getRequestDate() != null && recruitmentRequests.getDeptId() == null) {
            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListByDate(recruitmentRequests)));
        } else if (recruitmentRequests.getRequestDate() == null && recruitmentRequests.getDeptId() != null) {
            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListByDepartment(hrDepartments)));
            return null;
        } else {
            JsfUtil.addFatalMessage("Please Insert Request Date Or Department");
            return null;
        }
        return null;
    }

    public SelectItem[] getstatus() {
        return JsfUtil.getSelectItems(filterByStatus(), true);
    }

    public List<SelectItem> filterByStatus() {
//        List<SelectItem> selectItems = new ArrayList<>();
        return hrRecruitmentRequestsBeanLocal.filterByStatus();

//        ArrayList<String> statusList = new ArrayList<>();
//        statusList.add("Request");
//        statusList.add("Approved");
//        statusList.add("Rejected");
//        statusList.add("Resubmitted");
//        return statusList;
    }

    public ArrayList<HrRecruitmentRequests> findByStatus(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            recruitmentRequests.setStatus(Integer.valueOf(event.getNewValue().toString()));
//            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findByStatus(recruitmentRequests.getStatus())));
        }

//        if (event.getNewValue().toString() != null) {
//              int sts = recruitmentRequests.getStatus();;
//            recreateHrRecruitmentRequests(hrRecruitmentRequestsBeanLocal.findByStatus(sts));
//        }
        return null;
    }

    int reqStatus = 0;

    private void populateTable() {
        try {
            List<HrRecruitmentRequests> readFilteredTransfer = hrRecruitmentRequestsBeanLocal.loadRecruitmentList(reqStatus);
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

    public SelectItem[] getStatusList() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load rejected list");
        return items;
    }

    private void populateRequest(Status _status) {
        try {
            recruitmentRequestList = hrRecruitmentRequestsBeanLocal.loadRequestList(_status, sessionBeanLocal.getUserId());
            recruitmentRequestsListDataModel = new ListDataModel<>(recruitmentRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populateApproveRecuitment(Status _status) {
        try {
            recruitmentRequestList = hrRecruitmentRequestsBeanLocal.loadRecuitmentList(_status, sessionBeanLocal.getUserId());
            recruitmentRequestsListDataModel = new ListDataModel<>(recruitmentRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                populateRequest(status);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateApproveRecuitment(status);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateApproveRecuitment(status);
            }
        }
    }

//    public ArrayList<HrRecruitmentRequests> findListByDepartment() {
//        System.out.println("findListByDepartment====================================="+ recruitmentRequests.getRequestDate() + recruitmentRequests.getDeptId().getDepName());
//        if (recruitmentRequests.getRequestDate() != null && recruitmentRequests.getDeptId() == null) {
//            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListByDate(recruitmentRequests)));
//        } else if (recruitmentRequests.getRequestDate() == null && recruitmentRequests.getDeptId() != null) {
//            recruitmentRequestsListDataModel = new ListDataModel(new ArrayList(hrRecruitmentRequestsBeanLocal.findListByDepartment(recruitmentRequests)));
//            return null;
//        } else {
//            JsfUtil.addFatalMessage("Please Insert Request Date Or Department");
//            return null;
//        }
//        return null;
//    }
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Request Selected", ((HrRecruitmentRequests) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Request Unselected", ((HrRecruitmentRequests) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
//</editor-fold>

}
