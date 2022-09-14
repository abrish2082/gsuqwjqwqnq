/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.planning;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDeptJobsBeanLocal;
import et.gov.eep.hrms.businessLogic.planning.NeedRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitments;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
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
 * @author Baya
 */
@Named(value = "needRequestController")
@ViewScoped
public class NeedRequestController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injetion">
    @Inject
            HrHrpRecruitmentRequest hrHrpRecruitmentRequest;
    
    @Inject
            HrHrpRecruitments hrHrpRecruitments;
    
    @Inject
            HrEmployees hrEmployees;
    
    @Inject
            HrDepartments hrDepartments;
    
    @Inject
            HrDepartments srcDepartments;
    
    @Inject
            HrJobTypes hrJobTypes;
    
    @Inject
            HrLuGrades hrLuGrades;
    
    @Inject
            WfHrProcessed WfHrProcessed;
    
    @Inject
            HrSalaryScaleRanges hrSalaryScaleRanges;
    
    @Inject
            SessionBean SessionBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            NeedRequestBeanLocal needRequestBeanLocal;
    @EJB
            HrDepartmentsIntegrationBeanLocal departmentBeanLocal;
    @EJB
            HrDeptJobsBeanLocal hrDeptJobsBeanLocal;
    @EJB
    private HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
            WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Vraiable declaration">
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String addorUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean pnlpromotion = true;
    private boolean pnlrecruitment = false;
    private boolean pnlrWfIcon = false;
    private boolean pnlupdate = true;
    private boolean btnNewRender = false;
    private Integer noOfPosition;
    private int ApproveNotificationCounter = 0;
    Integer status = 0;
    Integer gradTotal;
    String promorionRemark;
    String RecruitmentRemark;
    private String allDpts;
    Integer totalActiveEmployesofDepJob = 0;
    Integer TotalEmpFromStaffPlanPerDepJob = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="LIst and Datamodel declaration">
    List<HrJobTypes> allJobTypes;
    List<HrEmployees> ActiveEmpPerDeptJob;
    List<SelectItem> filterByStatus = new ArrayList<>();
    private List<SelectItem> budgetYears;
    List<HrJobTypes> jobTitleList;
    List<HrHrpRecruitments> plannyearList;
    List<HrHrpRecruitments> RecruitmentList;
    List<HrDepartments> deptlist;
    List<HrHrpRecruitmentRequest> RequestList;
    DataModel<HrHrpRecruitmentRequest> recruitmentRequestDataModel;
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    DataModel<HrHrpRecruitments> recruitmentDataModel;
    Set<String> checkEvaluation = new HashSet<>();
    Set<String> checkHowToBefilled = new HashSet<>();
    UserStatus userStatus = new UserStatus();
    WorkFlow workFlow = new WorkFlow();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post construct">
    public NeedRequestController() {
    }
    @PostConstruct
    public void init() {
        hrHrpRecruitmentRequest.setHowToBeFilled("Internal Recruitment");
        setAllDpts("all");
        deptlist = hrRecruitmentRequestsBeanLocal.findaadepts();
        setFullName(SessionBean.getUserName());
        setBudgetYears(readBudgetYears());
        plannyearList = needRequestBeanLocal.findallrecruitmentyears();
        loadTree();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="authentication code (it should be removed)">
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public boolean isPnlrWfIcon() {
        return pnlrWfIcon;
    }

    public void setPnlrWfIcon(boolean pnlrWfIcon) {
        this.pnlrWfIcon = pnlrWfIcon;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public boolean isPnlupdate() {
        return pnlupdate;
    }

    public void setPnlupdate(boolean pnlupdate) {
        this.pnlupdate = pnlupdate;
    }

    public Set<String> getCheckHowToBefilled() {
        return checkHowToBefilled;
    }

    public void setCheckHowToBefilled(Set<String> checkHowToBefilled) {
        this.checkHowToBefilled = checkHowToBefilled;
    }

    public String getPromorionRemark() {
        return promorionRemark;
    }

    public void setPromorionRemark(String promorionRemark) {
        this.promorionRemark = promorionRemark;
    }

    public String getRecruitmentRemark() {
        return RecruitmentRemark;
    }

    public void setRecruitmentRemark(String RecruitmentRemark) {
        this.RecruitmentRemark = RecruitmentRemark;
    }

    public Integer getGradTotal() {
        return gradTotal;
    }

    public void setGradTotal(Integer gradTotal) {
        this.gradTotal = gradTotal;
    }

    public HrHrpRecruitmentRequest getHrHrpRecruitmentRequest() {
        return hrHrpRecruitmentRequest;
    }

    public void setHrHrpRecruitmentRequest(HrHrpRecruitmentRequest hrHrpRecruitmentRequest) {
        this.hrHrpRecruitmentRequest = hrHrpRecruitmentRequest;
    }

    public List<HrHrpRecruitments> getRecruitmentList() {
        return RecruitmentList;
    }

    public void setRecruitmentList(List<HrHrpRecruitments> RecruitmentList) {
        this.RecruitmentList = RecruitmentList;
    }

    public static List<HrDepartments> getDepartments() {
        return departments;
    }

    public boolean isPnlpromotion() {
        return pnlpromotion;
    }

    public void setPnlpromotion(boolean pnlpromotion) {
        this.pnlpromotion = pnlpromotion;
    }

    public boolean isPnlrecruitment() {
        return pnlrecruitment;
    }

    public void setPnlrecruitment(boolean pnlrecruitment) {
        this.pnlrecruitment = pnlrecruitment;
    }

    public static void setDepartments(List<HrDepartments> departments) {
        NeedRequestController.departments = departments;
    }

    public String getAllDpts() {
        return allDpts;
    }

    public List<HrDepartments> getDeptlist() {
        return deptlist;
    }

    public void setDeptlist(List<HrDepartments> deptlist) {
        this.deptlist = deptlist;
    }

    public void setAllDpts(String allDpts) {
        this.allDpts = allDpts;
    }

    public List<HrHrpRecruitments> getPlannyearList() {
        return plannyearList;
    }

    public void setPlannyearList(List<HrHrpRecruitments> plannyearList) {
        this.plannyearList = plannyearList;
    }

    public HrHrpRecruitments getHrHrpRecruitments() {
        if (hrHrpRecruitments == null) {
            hrHrpRecruitments = new HrHrpRecruitments();
        }
        return hrHrpRecruitments;
    }

    public void setHrHrpRecruitments(HrHrpRecruitments hrHrpRecruitments) {
        this.hrHrpRecruitments = hrHrpRecruitments;
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public Integer getNoOfPosition() {
        return noOfPosition;
    }

    public void setNoOfPosition(Integer noOfPosition) {
        this.noOfPosition = noOfPosition;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrDepartments getSrcDepartments() {
        return srcDepartments;
    }

    public void setSrcDepartments(HrDepartments srcDepartments) {
        this.srcDepartments = srcDepartments;
    }

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }

    public List<HrJobTypes> getJobTitleList() {
        return needRequestBeanLocal.findAllJobTitles();
    }

    public void setJobTitleList(List<HrJobTypes> jobTitleList) {
        this.jobTitleList = jobTitleList;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public List<HrHrpRecruitmentRequest> getRequestList() {
        return RequestList;
    }

    public void setRequestList(List<HrHrpRecruitmentRequest> RequestList) {
        this.RequestList = RequestList;
    }

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        return hrSalaryScaleRanges;
    }

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    public DataModel<HrHrpRecruitmentRequest> getRecruitmentRequestDataModel() {
        return recruitmentRequestDataModel;
    }

    public void setRecruitmentRequestDataModel(DataModel<HrHrpRecruitmentRequest> recruitmentRequestDataModel) {
        this.recruitmentRequestDataModel = recruitmentRequestDataModel;
    }

    public DataModel<HrHrpRecruitments> getRecruitmentDataModel() {

        return recruitmentDataModel;
    }

    public void setRecruitmentDataModel(DataModel<HrHrpRecruitments> recruitmentDataModel) {
        this.recruitmentDataModel = recruitmentDataModel;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }

    public Set<String> getCheckEvaluation() {
        return checkEvaluation;
    }

    public void setCheckEvaluation(Set<String> checkEvaluation) {
        this.checkEvaluation = checkEvaluation;
    }

    public List<HrJobTypes> getAllJobTypes() {
        return allJobTypes;
    }

    public void setAllJobTypes(List<HrJobTypes> allJobTypes) {
        this.allJobTypes = allJobTypes;
    }

    public List<SelectItem> getFilterByStatus() {
        filterByStatus = needRequestBeanLocal.filterByStatus();
        return filterByStatus;

    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public WfHrProcessed getWfHrProcessed() {
        return WfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed WfHrProcessed) {
        this.WfHrProcessed = WfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkFlowDataModel() {

        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public int getApproveNotificationCounter() {
        RecruitmentList = needRequestBeanLocal.FindCheckedReqForApproval();
        recruitmentDataModel = new ListDataModel(new ArrayList(RecruitmentList));
        ApproveNotificationCounter = RecruitmentList.size();
        return ApproveNotificationCounter;
    }

    public void setApproveNotificationCounter(int ApproveNotificationCounter) {
        this.ApproveNotificationCounter = ApproveNotificationCounter;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="department dialoge">
    private TreeNode root;
    private TreeNode selectedNode;
    private List<HrDepartments> allDepartments;
    private static List<HrDepartments> departments;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<HrDepartments> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<HrDepartments> allDepartments) {
        this.allDepartments = allDepartments;
    }
    //</editor-fold>

    public void loadTree() {
        allDepartments = departmentBeanLocal.findAllDepartmentInfo();
        root = new DefaultTreeNode("Root", null);
        populateNodes(allDepartments, 0, root);
    }

    public void populateNodes(List<HrDepartments> depts, int id, TreeNode node) {
        departments = new ArrayList<>();
        if (getAllDepartments() != null) {
            for (HrDepartments k : getAllDepartments()) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    departments.add(k);
                    populateNodes(departments, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments.setDepId(deptId);
        hrDepartments = departmentBeanLocal.findByDepartmentId(hrDepartments);
        hrEmployees.setDeptId(hrDepartments);
        hrHrpRecruitments.setDeptId(hrDepartments);
        allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
    }
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Main methods">
    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                clearPage();
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                btnNewRender = false;
                break;
            case "Search":
                //clearPage();
                btnNewRender = false;
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        clearPage();
        searchPage = false;
        newPage = true;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
        btnNewRender = false;
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year > 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year), String.valueOf(year - 1) + "/" + String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }

    public void jobTitleListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrJobTypes.setId(Integer.valueOf(event.getNewValue().toString()));
            hrJobTypes = needRequestBeanLocal.findByName(hrJobTypes.getId());
            hrHrpRecruitmentRequest.setJobId(hrJobTypes);
            hrSalaryScaleRanges = hrJobTypes.getJobGradeId();

            if (hrDepartments.getDepId() != null && hrJobTypes.getId() != null) {
                ActiveEmpPerDeptJob = hrEmployeeBean.FindActiveEmplByDepIdAndJobId(hrDepartments.getDepId(), hrJobTypes.getId());
                totalActiveEmployesofDepJob = ActiveEmpPerDeptJob.size();
                System.out.println("totalActiveEmployesofDepJob======" + totalActiveEmployesofDepJob);

                TotalEmpFromStaffPlanPerDepJob = hrDeptJobsBeanLocal.numEmployeeNeeded(hrDepartments, hrJobTypes);
                System.out.println("TotalEmpFromStaffPlanPerDepJob======" + TotalEmpFromStaffPlanPerDepJob);

                noOfPosition = TotalEmpFromStaffPlanPerDepJob - totalActiveEmployesofDepJob;
                if (noOfPosition < 0) {
                    noOfPosition = 0;
                }

                System.out.println("noOfPosition======" + noOfPosition);
            }
        }
    }

    public void recreateDataModel() {
        recruitmentRequestDataModel = null;
        recruitmentRequestDataModel = new ListDataModel(new ArrayList(hrHrpRecruitments.getHrHrpRecruitmentRequestList()));
    }

    public void addRequest() {
        if (updateStatus == 0) {

            if (checkEvaluation.contains(hrJobTypes.getJobTitle()) && checkHowToBefilled.contains(hrHrpRecruitmentRequest.getHowToBeFilled())) {
                JsfUtil.addFatalMessage("  is already added!. Try again.");
            } else if (getNoOfPosition() < hrHrpRecruitmentRequest.getNumberRequestedPosition()) {
                JsfUtil.addFatalMessage("number Position Requested shouldn't Exceed Total Number Of Position");
            } else {
                hrHrpRecruitmentRequest.setStatus(0);
                hrHrpRecruitmentRequest.setJobId(hrJobTypes);
                hrHrpRecruitments.addNeedRequest(hrHrpRecruitmentRequest);
                checkEvaluation.add(hrJobTypes.getJobTitle());
                checkHowToBefilled.add(hrHrpRecruitmentRequest.getHowToBeFilled());
                noOfPosition = noOfPosition - hrHrpRecruitmentRequest.getNumberRequestedPosition();
                setNoOfPosition(noOfPosition);
                addorUpdate = "add";
                recreateDataModel();
                clearRecruitmentRequest();
                JsfUtil.addSuccessMessage("Added Successfully");
            }
        } else {
            if (checkEvaluation.contains(hrHrpRecruitmentRequest.getJobId().getJobTitle()) && checkHowToBefilled.contains(hrHrpRecruitmentRequest.getHowToBeFilled())) {
                JsfUtil.addFatalMessage("  is already added!. Try again.");
            } else if (getNoOfPosition() < hrHrpRecruitmentRequest.getNumberRequestedPosition()) {
                JsfUtil.addFatalMessage("number Position Requested shouldn't Exceed Total Number Of Position");
            } else {
                hrHrpRecruitmentRequest.setStatus(0);
                hrHrpRecruitments.addNeedRequest(hrHrpRecruitmentRequest);
                checkEvaluation.add(hrJobTypes.getJobTitle());
                checkHowToBefilled.add(hrHrpRecruitmentRequest.getHowToBeFilled());
                noOfPosition = noOfPosition - hrHrpRecruitmentRequest.getNumberRequestedPosition();
                setNoOfPosition(noOfPosition);
                recreateDataModel();
                clearRecruitmentRequest();
                addorUpdate = "add";
                JsfUtil.addSuccessMessage("Added Successfully");
            }

        }
    }

    public void editDataTable() {
        hrHrpRecruitmentRequest = recruitmentRequestDataModel.getRowData();
        hrJobTypes = hrHrpRecruitmentRequest.getJobId();
        hrJobTypes.getJobGradeId();
        addorUpdate = "Modify";
        noOfPosition = noOfPosition + hrHrpRecruitmentRequest.getNumberRequestedPosition();
    }

    public List<SelectItem> getMonths() {
        return needRequestBeanLocal.Months();
    }

    public void clearRecruitmentRequest() {
        hrHrpRecruitmentRequest = new HrHrpRecruitmentRequest();

    }

    public void wfSave() {
        WfHrProcessed.setHrpRecruitmentId(hrHrpRecruitments);
        System.out.println("hrprecrutment Id==" + WfHrProcessed.getHrpRecruitmentId());
        System.out.println("processed On==" + hrHrpRecruitments.getRequestDate());
        WfHrProcessed.setProcessedBy(SessionBean.getUserId());
        WfHrProcessed.setDecision(hrHrpRecruitments.getStatus());
        wfHrProcessedBeanLocal.create(WfHrProcessed);
        WfHrProcessed = new WfHrProcessed();

    }

    public void saveHRNeedRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "SaveHRNeedRequest", dataset)) {
                workFlow.setUserStatus(Constants.PREPARE_VALUE);
                hrHrpRecruitments.setStatus(workFlow.getUserStatus());
                if (updateStatus == 0) {
                    System.out.println("processed On=1=" + hrHrpRecruitments.getRequestDate());
                    needRequestBeanLocal.save(hrHrpRecruitments);
                    WfHrProcessed.setProcessedOn(hrHrpRecruitments.getRequestDate());
                    wfSave();
                    WfHrProcessed = new WfHrProcessed();
                    JsfUtil.addSuccessMessage("Data Saved Successfully");
                    clearPage();
                } else {
                    needRequestBeanLocal.edit(hrHrpRecruitments);
                    JsfUtil.addSuccessMessage("Data Modified Successfully");
                    clearPage();

                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);

                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMassSalIncDetailInfo");
                eventEntry.setProgram(program);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Unable to Modify data");
            updateStatus = 0;
            saveorUpdateBundle = "Save";
        }
    }

    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(hrHrpRecruitments.getWfHrProcessedList()));
    }

    public void clearPage() {
        hrHrpRecruitments = new HrHrpRecruitments();
        hrHrpRecruitmentRequest = new HrHrpRecruitmentRequest();
        recruitmentRequestDataModel = new ArrayDataModel<>();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        noOfPosition = null;
        gradTotal = null;
        saveorUpdateBundle = "save";
        addorUpdate = "add";
        updateStatus = 0;
        pnlupdate = true;
    }
    
    public void resultdisplayChanged(SelectEvent event) {
        recruitmentDataModel = null;
        hrHrpRecruitments = (HrHrpRecruitments) event.getObject();
        hrHrpRecruitments.setId(hrHrpRecruitments.getId());
        hrHrpRecruitments = needRequestBeanLocal.getSelectedRequest(hrHrpRecruitments.getId());
        hrDepartments = hrHrpRecruitments.getDeptId();
        allJobTypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
        Integer noOfPositionRequested = 0;
        hrHrpRecruitmentRequest.setHowToBeFilled("");
        for (int i = 0; i < hrHrpRecruitments.getHrHrpRecruitmentRequestList().size(); i++) {
            noOfPositionRequested = noOfPositionRequested + hrHrpRecruitments.getHrHrpRecruitmentRequestList().get(i).getNumberRequestedPosition();
        }
        noOfPosition = 0;
        saveorUpdateBundle = "Update";
        newPage = true;
        searchPage = false;
        pnlrWfIcon = true;
        updateStatus = 1;
        if (hrHrpRecruitments.getStatus() > Constants.PREPARE_VALUE) {
            pnlupdate = false;
        }
        newOrSearch = "Search";
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        btnNewRender = true;
        recreateDataModel();
        recreateDataModelwf();

    }

    public void sorHrpStatus(ValueChangeEvent event) {
        hrHrpRecruitments.setStatus(Integer.parseInt(event.getNewValue().toString()));
        recruitmentDataModel = new ListDataModel(new ArrayList(needRequestBeanLocal.findByDeptAndStatus(hrHrpRecruitments)));
    }

    public void yearVlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            if (hrDepartments.getDepId() == null) {
                hrHrpRecruitments.setPlanningYear(Integer.valueOf(event.getNewValue().toString()));
                RecruitmentList = needRequestBeanLocal.findbyYearAndType(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled());
                RequestList = needRequestBeanLocal.findRequestByBudgetYearAndRequestType(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled());
                System.out.println("rec List==" + RecruitmentList);
                System.out.println("req List==" + RequestList);
            } else {
                hrHrpRecruitments.setPlanningYear(Integer.valueOf(event.getNewValue().toString()));
                RecruitmentList = needRequestBeanLocal.findRequestByBudgetYearAndRequestTypeDept(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
                RequestList = needRequestBeanLocal.findRequestByBudgetYearAndRequestTypeAndDept(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
                System.out.println("rec List=2=" + RecruitmentList);
                System.out.println("req List=2=" + RequestList);
            }
            recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
            gradTotal = 0;
            for (int i = 0; i < RequestList.size(); i++) {
                gradTotal = gradTotal + RequestList.get(i).getNumberRequestedPosition();
            }

        }

    }

    public void saveAnnualRecruitmentPromotion() {
        {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveAnnualRecruitmentPromotion", dataset)) {
                    for (int i = 0; i < RequestList.size(); i++) {
                        hrHrpRecruitmentRequest = RequestList.get(i);
                        hrHrpRecruitmentRequest.setStatus(Constants.APPROVE_VALUE);
//                        hrHrpRecruitments = hrHrpRecruitmentRequest.getRecruitmentId();
                        hrHrpRecruitments.setStatus(1);
                        System.out.println("status approval-====" + hrHrpRecruitmentRequest.getStatus());
                        if (hrHrpRecruitmentRequest.getHowToBeFilled().equals("Promotion")) {
                            hrHrpRecruitments.setPromotionRemark(promorionRemark);
                        } else {
                            hrHrpRecruitments.setRecruitmentRemark(RecruitmentRemark);
                        }
                        // needRequestBeanLocal.edit(hrHrpRecruitments);
                        needRequestBeanLocal.update(hrHrpRecruitmentRequest);

                        needRequestBeanLocal.edit(hrHrpRecruitments);

                    }
                    String comment = WfHrProcessed.getCommentGiven();
                    System.out.println("Rec List " + RecruitmentList);
                    for (int i = 0; i < RecruitmentList.size(); i++) {
                        if (RecruitmentList.size() > 0) {
                            WfHrProcessed.setCommentGiven(comment);
                            hrHrpRecruitments = RecruitmentList.get(i);
                            System.out.println("hrHrpRecruitments==" + hrHrpRecruitments.getId());
                            wfSave();
                            System.out.println("worklow save");

                        }
                        hrHrpRecruitmentRequest = new HrHrpRecruitmentRequest();
                        WfHrProcessed = new WfHrProcessed();
                    }

                    JsfUtil.addSuccessMessage("Approved Successfully");
                    clearPage();
                } else {
                    JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                    EventEntry eventEntry = new EventEntry();
                    eventEntry.setSessionId(SessionBean.getSessionID());
                    eventEntry.setUserId(SessionBean.getUserId());
                    QName qualifiedName = new QName("", "project");
                    JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                    eventEntry.setUserLogin(test);

                    JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                    eventEntry.setUserLogin(userName);
                    JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                    eventEntry.setModule(module);
                    JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMassSalIncDetailInfo");
                    eventEntry.setProgram(program);
//..... add more information by calling fields defined in the object 
                    security.addEventLog(eventEntry, dataset);
                }
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Unable to Modify data");
            }

        }
    }

    public void HowToBeFilledLitnerVLC(ValueChangeEvent event) {
        hrHrpRecruitmentRequest.setHowToBeFilled((event.getNewValue().toString()));
        if (hrHrpRecruitmentRequest.getHowToBeFilled().equals("Promotion")) {
            pnlpromotion = true;
            pnlrecruitment = false;
        }
        if (hrHrpRecruitmentRequest.getHowToBeFilled().equals("Recruitment")) {
            pnlpromotion = false;
            pnlrecruitment = true;
        }
        if (hrDepartments.getDepId() != null && hrHrpRecruitments.getPlanningYear() != null) {
            hrHrpRecruitmentRequest.setHowToBeFilled((event.getNewValue().toString()));
            RecruitmentList = needRequestBeanLocal.findRequestByBudgetYearAndRequestTypeDept(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
            RequestList = needRequestBeanLocal.findRequestByBudgetYearAndRequestTypeAndDept(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());

        } else if (hrHrpRecruitments.getPlanningYear() == null && hrDepartments.getDepId() == null) {
            hrHrpRecruitmentRequest.setHowToBeFilled(event.getNewValue().toString());
            RecruitmentList = needRequestBeanLocal.findRecritmentByHowTobeFiled(hrHrpRecruitmentRequest.getHowToBeFilled());
            RequestList = hrRecruitmentRequestsBeanLocal.findbyHowTbeFilld(hrHrpRecruitmentRequest.getHowToBeFilled());
        } else if (hrDepartments.getDepId() != null && hrHrpRecruitments.getPlanningYear() == null) {
            hrHrpRecruitmentRequest.setHowToBeFilled((event.getNewValue().toString()));
            RecruitmentList = needRequestBeanLocal.findRecritmentByHowTobeFiledAndDept(hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
            RequestList = needRequestBeanLocal.findRequestByRequestTypeAndDept(hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
        } else {
            hrHrpRecruitmentRequest.setHowToBeFilled((event.getNewValue().toString()));
            RecruitmentList = needRequestBeanLocal.findbyYearAndType(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled());
            RequestList = needRequestBeanLocal.findRequestByBudgetYearAndRequestType(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled());
        }
        System.out.println("rec List==" + RecruitmentList);
        System.out.println("req List==" + RequestList);
        recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
        gradTotal = 0;
        for (int i = 0; i < RequestList.size(); i++) {
            gradTotal = gradTotal + RequestList.get(i).getNumberRequestedPosition();
        }
    }

    public void SearchyearVlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrHrpRecruitments.setPlanningYear(Integer.valueOf(event.getNewValue().toString()));
            RecruitmentList = needRequestBeanLocal.findRequestByBudgetYears(hrHrpRecruitments.getPlanningYear());
            recruitmentDataModel = new ListDataModel(new ArrayList(RecruitmentList));

        }
    }

    public void DepartmentVLC(ValueChangeEvent event) {
        if (hrHrpRecruitments.getPlanningYear() == null) {
            hrDepartments.setDepId((Integer.valueOf(event.getNewValue().toString())));
            RecruitmentList = needRequestBeanLocal.findRecritmentByHowTobeFiledAndDept(hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
            RequestList = needRequestBeanLocal.findRequestByRequestTypeAndDept(hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
        } else {
            hrDepartments.setDepId((Integer.valueOf(event.getNewValue().toString())));
            RecruitmentList = needRequestBeanLocal.findRequestByBudgetYearAndRequestTypeDept(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
            RequestList = needRequestBeanLocal.findRequestByBudgetYearAndRequestTypeAndDept(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitmentRequest.getHowToBeFilled(), hrDepartments.getDepId());
        }
        System.out.println("rec List==" + RecruitmentList);
        System.out.println("req List==" + RequestList);
        recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
        gradTotal = 0;
        for (int i = 0; i < RequestList.size(); i++) {
            gradTotal = gradTotal + RequestList.get(i).getNumberRequestedPosition();
        }

    }

    private void populateTable() {
        try {
            int hrHrpRecruitmentStatus;
            int hrHrpRecruitmentRequestStatus;
            if (status == 0) {
                hrHrpRecruitmentStatus = 0;
                hrHrpRecruitmentRequestStatus = 0;
            } else if (status == 1) {
                hrHrpRecruitmentStatus = 1;
                hrHrpRecruitmentRequestStatus = 0;
            } else {
                hrHrpRecruitmentStatus = 1;
                hrHrpRecruitmentRequestStatus = 3;
            }

            RecruitmentList = needRequestBeanLocal.loadHrpRequests(hrHrpRecruitmentStatus, hrHrpRecruitmentRequestStatus, SessionBean.getUserId());
            System.out.println("rec list==" + RecruitmentList);
            recruitmentDataModel = new ListDataModel(new ArrayList(RecruitmentList));
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

    public void selecthrHrpRecruitmentRequestId(SelectEvent event) {
        hrHrpRecruitments = new HrHrpRecruitments();
        hrHrpRecruitments = (HrHrpRecruitments) event.getObject();
        hrHrpRecruitments.setId(hrHrpRecruitments.getId());
        System.out.println("hrHrpRecruitments.getId()===" + hrHrpRecruitments.getId());
        RecruitmentList = needRequestBeanLocal.findbyId(hrHrpRecruitments);
        System.out.println("RecruitmentList==" + RecruitmentList);
        RequestList = needRequestBeanLocal.findRequestRecruitmentId(hrHrpRecruitments.getId());
        workFlowDataModel = new ListDataModel(new ArrayList(hrHrpRecruitments.getWfHrProcessedList()));
        gradTotal = 0;
        for (int i = 0; i < RequestList.size(); i++) {
            gradTotal = gradTotal + RequestList.get(i).getNumberRequestedPosition();
        }
        System.out.println("wflist==" + workFlowDataModel);
        recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
        pnlrWfIcon = true;
        System.out.println("RecruitmentList=@ the end=" + RecruitmentList);
    }
    //</editor-fold>

}
