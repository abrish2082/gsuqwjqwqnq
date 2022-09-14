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
import et.gov.eep.hrms.businessLogic.planning.NeedRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
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
 * @author Ob
 */
@Named(value = "demandAndSupplyAnalysisController")
@ViewScoped
public class DemandAndSupplyAnalysisController implements Serializable {

    public DemandAndSupplyAnalysisController() {
    }

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrHrpRecruitmentRequest hrHrpRecruitmentRequest;
    @Inject
            HrHrpRecruitments hrHrpRecruitments;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            HrLuEducLevels hrLuEducLevels;
    @Inject
            HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean SessionBean;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            NeedRequestBeanLocal needRequestBeanLocal;
    @EJB
            HrDepartmentsIntegrationBeanLocal departmentBeanLocal;
    @EJB
            HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
            WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List Declaration">
    DataModel<HrHrpRecruitmentRequest> recruitmentRequestDataModel = new ListDataModel<>();
    DataModel<HrJobEducQualifications> jobEducQualificationsDataModel = new ListDataModel<>();
    DataModel<HrHrpRecruitments> recruitmentDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    private List<HrDepartments> allDepartments;
    private static List<HrDepartments> departments;
    List<HrHrpRecruitmentRequest> RequestList;
    List<HrHrpRecruitments> RecruitmentList;
    List<WfHrProcessed> WorkflowList;
    private List<SelectItem> budgetYears;
    List<HrDepartments> deptlist;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String addorUpdate = "Add";
    private String icone = "ui-icon-document";
    private String modifybtn = "";
    private boolean searchPage = false;
    private boolean newPage = true;
    private boolean btnmodify = false;
    private boolean pnlrWfIcon = false;
    private boolean filldToModify;
    Integer status = 0;
    private String statusType;
    private TreeNode root;
    private TreeNode selectedNode;
    Set<String> checkEvaluation = new HashSet<>();
    Set<String> checkHowToBefilled = new HashSet<>();
    private int requestNotificationCounter = 0;
    UserStatus userStatus = new UserStatus();
    WorkFlow workFlow = new WorkFlow();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        deptlist = hrRecruitmentRequestsBeanLocal.findaadepts();
        hrHrpRecruitments.setDeptId(hrDepartments);
        setBudgetYears(readBudgetYears());
        setFullName(SessionBean.getUserName());
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

    public List<HrDepartments> getDeptlist() {
        return deptlist;
    }

    public void setDeptlist(List<HrDepartments> deptlist) {
        this.deptlist = deptlist;
    }

    public HrHrpRecruitmentRequest getHrHrpRecruitmentRequest() {
        if (hrHrpRecruitmentRequest == null) {
            hrHrpRecruitmentRequest = new HrHrpRecruitmentRequest();
        }
        return hrHrpRecruitmentRequest;
    }

    public void setHrHrpRecruitmentRequest(HrHrpRecruitmentRequest hrHrpRecruitmentRequest) {
        this.hrHrpRecruitmentRequest = hrHrpRecruitmentRequest;
    }

    public boolean isBtnmodify() {
        return btnmodify;
    }

    public void setBtnmodify(boolean btnmodify) {
        this.btnmodify = btnmodify;
    }

    public HrRecruitmentRequestsBeanLocal getHrRecruitmentRequestsBeanLocal() {
        return hrRecruitmentRequestsBeanLocal;
    }

    public void setHrRecruitmentRequestsBeanLocal(HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal) {
        this.hrRecruitmentRequestsBeanLocal = hrRecruitmentRequestsBeanLocal;
    }

    public String getModifybtn() {
        return modifybtn;
    }

    public void setModifybtn(String modifybtn) {
        this.modifybtn = modifybtn;
    }

    public boolean isFilldToModify() {
        return filldToModify;
    }

    public void setFilldToModify(boolean filldToModify) {
        this.filldToModify = filldToModify;
    }

    public List<HrHrpRecruitmentRequest> getRequestList() {
        return RequestList;
    }

    public void setRequestList(List<HrHrpRecruitmentRequest> RequestList) {
        this.RequestList = RequestList;
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

    public List<HrHrpRecruitments> getRecruitmentList() {
        return RecruitmentList;
    }

    public void setRecruitmentList(List<HrHrpRecruitments> RecruitmentList) {
        this.RecruitmentList = RecruitmentList;
    }

    public Set<String> getCheckEvaluation() {
        return checkEvaluation;
    }

    public void setCheckEvaluation(Set<String> checkEvaluation) {
        this.checkEvaluation = checkEvaluation;
    }

    public Set<String> getCheckHowToBefilled() {
        return checkHowToBefilled;
    }

    public void setCheckHowToBefilled(Set<String> checkHowToBefilled) {
        this.checkHowToBefilled = checkHowToBefilled;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DataModel<HrHrpRecruitments> getRecruitmentDataModel() {
        return recruitmentDataModel;
    }

    public void setRecruitmentDataModel(DataModel<HrHrpRecruitments> recruitmentDataModel) {
        this.recruitmentDataModel = recruitmentDataModel;
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

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
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

    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades == null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    public HrLuEducLevels getHrLuEducLevels() {
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
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

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }

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

    public static List<HrDepartments> getDepartments() {
        return departments;
    }

    public static void setDepartments(List<HrDepartments> departments) {
        DemandAndSupplyAnalysisController.departments = departments;
    }

    public DataModel<HrJobEducQualifications> getJobEducQualificationsDataModel() {
        return jobEducQualificationsDataModel;
    }

    public void setJobEducQualificationsDataModel(DataModel<HrJobEducQualifications> jobEducQualificationsDataModel) {
        this.jobEducQualificationsDataModel = jobEducQualificationsDataModel;
    }

    public DataModel<HrHrpRecruitmentRequest> getRecruitmentRequestDataModel() {
        return recruitmentRequestDataModel;
    }

    public void setRecruitmentRequestDataModel(DataModel<HrHrpRecruitmentRequest> RecruitmentRequestDataModel) {
        this.recruitmentRequestDataModel = RecruitmentRequestDataModel;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkFlowDataModel() {
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public List<WfHrProcessed> getWorkflowList() {
        return WorkflowList;
    }

    public void setWorkflowList(List<WfHrProcessed> WorkflowList) {
        this.WorkflowList = WorkflowList;
    }

    public int getRequestNotificationCounter() {
        recruitmentDataModel = new ListDataModel(new ArrayList(needRequestBeanLocal.findAllrequestsTobeAnalayzed()));
        requestNotificationCounter = recruitmentDataModel.getRowCount();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main">
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

    public void btnNewOrSearch() {
        searchPage = false;
        newPage = true;
        setIcone("ui-icon-search");

    }

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
        recruitmentRequestDataModel = new ListDataModel(new ArrayList(needRequestBeanLocal.findByDeptYearDemand(hrHrpRecruitments)));

    }

    public void edit() {
        hrHrpRecruitmentRequest = recruitmentRequestDataModel.getRowData();
    }

    public void addRequest() {
        if (updateStatus == 0) {

            if (checkEvaluation.contains(hrHrpRecruitmentRequest.getJobId().getJobTitle()) && checkHowToBefilled.contains(hrHrpRecruitmentRequest.getHowToBeFilled())) {
                JsfUtil.addFatalMessage("  is already added!. Try again.");
            } else {
                hrHrpRecruitmentRequest.setStatus(0);
                hrHrpRecruitments.addNeedRequest(hrHrpRecruitmentRequest);
                checkEvaluation.add(hrHrpRecruitmentRequest.getJobId().getJobTitle());
                checkHowToBefilled.add(hrHrpRecruitmentRequest.getHowToBeFilled());
                recreateDataModel();
                clearRecruitmentRequest();
                btnmodify = false;
                filldToModify = false;
                JsfUtil.addSuccessMessage("Added Successfully");
            }
        } else {
            if (checkEvaluation.contains(hrHrpRecruitmentRequest.getJobId().getJobTitle()) && checkHowToBefilled.contains(hrHrpRecruitmentRequest.getHowToBeFilled())) {
                JsfUtil.addFatalMessage("  is already added!. Try again.");
            } else {
                hrHrpRecruitmentRequest.setStatus(0);
                hrHrpRecruitments.addNeedRequest(hrHrpRecruitmentRequest);
                checkEvaluation.add(hrHrpRecruitmentRequest.getJobId().getJobTitle());
                checkHowToBefilled.add(hrHrpRecruitmentRequest.getHowToBeFilled());
                recreateDataModel();
                clearRecruitmentRequest();
                JsfUtil.addSuccessMessage("Added Successfully");
                btnmodify = false;
            }

        }
    }

    public void clearRecruitmentRequest() {
        hrHrpRecruitmentRequest = new HrHrpRecruitmentRequest();
        hrJobTypes = new HrJobTypes();

    }

    public void saveWf() {
        wfHrProcessed.setHrpRecruitmentId(hrHrpRecruitments);
        wfHrProcessed.setProcessedOn(hrHrpRecruitments.getRequestDate());
        System.out.println("hrprecrutment Id==" + wfHrProcessed.getHrpRecruitmentId());
        System.out.println("processed On==" + hrHrpRecruitments.getRequestDate());
        wfHrProcessed.setProcessedBy(SessionBean.getUserId());
        wfHrProcessed.setDecision(hrHrpRecruitments.getStatus());
        wfHrProcessedBeanLocal.create(wfHrProcessed);
        wfHrProcessed = new WfHrProcessed();
    }

    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(WorkflowList));
        if (workFlowDataModel == null) {
            workFlowDataModel = new ListDataModel(new ArrayList<>());
        }
    }

    public void saveDemandAndSupplyAnalaysis() {

        try {
            System.out.println("RecruitmentList= inside save 1=" + RecruitmentList);
//
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveDemandAndSupplyAnalaysis", dataset)) {

                if (RequestList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Data To be Saved");
                } else {
                    System.out.println("RecruitmentList= inside save 2=" + RecruitmentList);
                    workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                    for (int i = 0; i < RequestList.size(); i++) {
                        HrHrpRecruitments hhrObj = new HrHrpRecruitments();
                        hhrObj = RequestList.get(i).getRecruitmentId();
                        hhrObj.setStatus(workFlow.getUserStatus());
                        hrHrpRecruitmentRequest = RequestList.get(i);
                        //hrHrpRecruitmentRequest.setStatus(workFlow.getUserStatus());
                        needRequestBeanLocal.update(hrHrpRecruitmentRequest);
                        needRequestBeanLocal.edit(hhrObj);
                        hhrObj = null;
                    }

                    String commment = wfHrProcessed.getCommentGiven();
                    System.out.println("RecruitmentList==" + RecruitmentList);
                    for (int i = 0; i < RecruitmentList.size(); i++) {
                        hrHrpRecruitments = RecruitmentList.get(i);
                        wfHrProcessed.setCommentGiven(commment);
                        System.out.println("comment" + commment);
                        saveWf();

                    }
                    wfHrProcessed = new WfHrProcessed();
                    modifybtn = "";
                    JsfUtil.addSuccessMessage("Data saved Successfully");
                    jobEducQualificationsDataModel = null;
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
            JsfUtil.addErrorMessage("Unable to Modify data");
            updateStatus = 0;
            saveorUpdateBundle = "Save";
        }

    }

    public void approve() {
        {
            try {
                for (int i = 0; i < RequestList.size(); i++) {
                    hrHrpRecruitments.setId(RequestList.get(i).getRecruitmentId().getId());
                    hrHrpRecruitments.setStatus(2);
                    needRequestBeanLocal.edit(hrHrpRecruitments);
                }

                JsfUtil.addSuccessMessage("Data Modified Successfully");
                clearPage();
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Unable to Modify data");
                updateStatus = 0;
                saveorUpdateBundle = "Save";
            }
        }
    }

    public void clearPage() {
        hrHrpRecruitmentRequest = null;
        hrHrpRecruitments = null;
        hrJobTypes = null;
        hrDepartments = null;
        hrEmployees = null;
        recruitmentRequestDataModel = null;
        RequestList = null;
    }

    public void selecthrHrpRecruitmentRequest(SelectEvent event) {
        hrHrpRecruitmentRequest = (HrHrpRecruitmentRequest) event.getObject();
        hrJobTypes = hrHrpRecruitmentRequest.getJobId();
        hrHrpRecruitments = hrHrpRecruitmentRequest.getRecruitmentId();;
        hrJobTypes.getJobLevelId();
        hrJobTypes.getHrJobEducQualificationsList();

        hrDepartments = hrHrpRecruitments.getDeptId();
        jobEducQualificationsDataModel = new ListDataModel(new ArrayList(needRequestBeanLocal.findByJobId(hrJobTypes)));
        updateStatus = 1;
        btnmodify = true;
        filldToModify = true;
        modifybtn = "Modify";
    }

    public void selecthrHrpRecruitmentRequestId(SelectEvent event) {
        hrHrpRecruitments = (HrHrpRecruitments) event.getObject();
        System.out.println("hrHrpRecruitments.getId()===" + hrHrpRecruitments.getId());
        RecruitmentList = needRequestBeanLocal.findbyId(hrHrpRecruitments);
        System.out.println("RecruitmentList==" + RecruitmentList);
        RequestList = needRequestBeanLocal.findRequestRecruitmentId(hrHrpRecruitments.getId());
        WorkflowList = new ArrayList(hrHrpRecruitments.getWfHrProcessedList());
        System.out.println("wflist==" + WorkflowList);
        recruitmentRequestDataModel = new ListDataModel(RequestList);
        pnlrWfIcon = true;
        System.out.println("RecruitmentList=@ the end=" + RecruitmentList);
    }

    public void typeStatus(HrHrpRecruitmentRequest hrHrpRecruitmentRequest) {
        if (hrHrpRecruitmentRequest.getStatus() == 1) {
            statusType = "Promotion";
        } else if (hrHrpRecruitmentRequest.getStatus() == 2) {
            statusType = "Transfere/Rotation";
        } else {
            statusType = "Recruitment";
        }
    }

    public void resultdisplayChanged(SelectEvent event) {
        hrHrpRecruitments = (HrHrpRecruitments) event.getObject();
        hrHrpRecruitments.setId(hrHrpRecruitments.getId());
        hrHrpRecruitments = needRequestBeanLocal.getSelectedRequest(hrHrpRecruitments.getId());
        hrDepartments = hrHrpRecruitments.getDeptId();
        saveorUpdateBundle = "Update";
        newPage = true;
        searchPage = false;
        updateStatus = 1;

        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        recreateDataModel();
    }

    public void recreateDataModel() {
        recruitmentRequestDataModel = null;
        recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
    }

    public List<SelectItem> getMonths() {
        return needRequestBeanLocal.Months();
    }

    public void yearVlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            if (hrDepartments.getDepId() == null) {
                hrHrpRecruitments.setPlanningYear(Integer.valueOf(event.getNewValue().toString()));
                RecruitmentList = needRequestBeanLocal.findByYear(hrHrpRecruitments.getPlanningYear());
                RequestList = needRequestBeanLocal.findRequestByBudgetYear(hrHrpRecruitments.getPlanningYear());
                recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
                for (int i = 0; i < RecruitmentList.size(); i++) {
                    WorkflowList = new ArrayList(RecruitmentList.get(i).getWfHrProcessedList());
                    System.out.println("wf list " + WorkflowList);
                }
                System.out.println("master size==" + RecruitmentList.size());
            } else {
                hrHrpRecruitments.setPlanningYear(Integer.valueOf(event.getNewValue().toString()));
                RecruitmentList = needRequestBeanLocal.findByYear(hrHrpRecruitments.getPlanningYear());
                RequestList = needRequestBeanLocal.findRequestByBudgetYearAndDepartment(hrHrpRecruitments.getPlanningYear(), hrDepartments.getDepId());
                recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
            }
            System.out.println("RecruitmentList===by year====" + RecruitmentList);
            pnlrWfIcon = true;
        }
    }

    public void SearchyearVlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrHrpRecruitments.setPlanningYear(Integer.valueOf(event.getNewValue().toString()));
            recruitmentDataModel = new ListDataModel(new ArrayList(needRequestBeanLocal.findRequestByBudgetYears(hrHrpRecruitments.getPlanningYear())));
        }
    }

    public void DepartmentVLC(ValueChangeEvent event) {
        if (hrHrpRecruitments.getPlanningYear() == null) {
            hrDepartments.setDepId(Integer.valueOf(event.getNewValue().toString()));
            RecruitmentList = needRequestBeanLocal.findByByDept(hrHrpRecruitments.getDeptId());
            RequestList = needRequestBeanLocal.findRequestByDept(hrDepartments.getDepId());
            recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
        } else {
            hrDepartments.setDepId(Integer.valueOf(event.getNewValue().toString()));
            RecruitmentList = needRequestBeanLocal.findByYearAndDept(hrHrpRecruitments.getPlanningYear(), hrHrpRecruitments.getDeptId());
            RequestList = needRequestBeanLocal.findRequestByBudgetYearAndDepartment(hrHrpRecruitments.getPlanningYear(), hrDepartments.getDepId());
            recruitmentRequestDataModel = new ListDataModel(new ArrayList(RequestList));
        }
        System.out.println("RecruitmentList===by dept====" + RecruitmentList);
        pnlrWfIcon = true;
    }
//</editor-fold>

}
