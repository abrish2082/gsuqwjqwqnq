/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.evaluation.ApproveProbationBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationResultProbationBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaResultDetailProbation;
import et.gov.eep.hrms.entity.evaluation.HrEvalResultProbations;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;

/**
 *
 * @author Ob
 */
@Named(value = "approveProbationController")
@ViewScoped
public class ApproveProbationController implements Serializable {

    @Inject
    HrEvalResultProbations hrEvalResultProbations;
    @Inject
    HrEmployees evalutor;
    @Inject
    HrEvaResultDetailProbation hrEvaResultDetailProbation;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrEmployees srcEmployees;
    @Inject
    HrDepartments srcDepartments;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    WorkFlow workFlow;

    @EJB
    HrDepartmentsIntegrationBeanLocal departmentBeanLocal;
    @EJB
    EvaluationResultProbationBeanLocal evaluationResultProbationBeanLocal;
    @EJB
    private ApproveProbationBeanLocal approveProbationBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    String selectedValue = "";
    String approveDate;
    int status = 0, prepareBy;

    DataModel<HrEvalResultProbations> probationResultDataModel;
    DataModel<HrEvaResultDetailProbation> resultListDataModel;
    private List<HrEvalResultProbations> viewProbationResult;
    private List<HrEvalResultProbations> resultList;
    UserStatus userStatus = new UserStatus();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrEvalResultProbations> probationList;

    @PostConstruct
    public void init() {
        loadTree();
        probationList = approveProbationBeanLocal.findPreparedList();
        setApproveDate(StringDateManipulation.toDayInEc());
    }

    public ApproveProbationController() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">      
    public DataModel<HrEvalResultProbations> getProbationResultDataModel() {
        return probationResultDataModel;
    }

    public void setProbationResultDataModel(DataModel<HrEvalResultProbations> probationResultDataModel) {
        this.probationResultDataModel = probationResultDataModel;
    }

    public DataModel<HrEvaResultDetailProbation> getResultListDataModel() {
        return resultListDataModel;
    }

    public void setResultListDataModel(DataModel<HrEvaResultDetailProbation> resultListDataModel) {
        this.resultListDataModel = resultListDataModel;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public List<HrEvalResultProbations> getProbationList() {
        if (probationList == null) {
            probationList = new ArrayList<>();
        }
        return probationList;
    }

    public void setProbationList(List<HrEvalResultProbations> probationList) {
        this.probationList = probationList;
    }

    public List<HrEvalResultProbations> getViewProbationResult() {
        return viewProbationResult;
    }

    public void setViewProbationResult(List<HrEvalResultProbations> viewProbationResult) {
        this.viewProbationResult = viewProbationResult;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public HrEmployees getEvalutor() {
        return evalutor;
    }

    public void setEvalutor(HrEmployees evalutor) {
        this.evalutor = evalutor;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public ApproveProbationBeanLocal getApproveProbationBeanLocal() {
        return approveProbationBeanLocal;
    }

    public void setApproveProbationBeanLocal(ApproveProbationBeanLocal approveProbationBeanLocal) {
        this.approveProbationBeanLocal = approveProbationBeanLocal;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrEmployees getSrcEmployees() {
        return srcEmployees;
    }

    public void setSrcEmployees(HrEmployees srcEmployees) {
        this.srcEmployees = srcEmployees;
    }

    public HrDepartments getSrcDepartments() {
        return srcDepartments;
    }

    public void setSrcDepartments(HrDepartments srcDepartments) {
        this.srcDepartments = srcDepartments;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrEvalResultProbations getHrEvalResultProbations() {
        return hrEvalResultProbations;
    }

    public void setHrEvalResultProbations(HrEvalResultProbations hrEvalResultProbations) {
        this.hrEvalResultProbations = hrEvalResultProbations;
    }

    public HrEvaResultDetailProbation getHrEvaResultDetailProbation() {
        return hrEvaResultDetailProbation;
    }

    public void setHrEvaResultDetailProbation(HrEvaResultDetailProbation hrEvaResultDetailProbation) {
        this.hrEvaResultDetailProbation = hrEvaResultDetailProbation;
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

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }
    //</editor-fold>

    public void searchPage_vlc() {
        searchPage = true;
        newPage = false;
        probationResultDataModel = null;
    }

    public void resultInfo() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void displayProbationResult() {
        if (srcDepartments.getDepName() != null) {
            viewProbationResult = approveProbationBeanLocal.findByDepName(srcDepartments);
            probationResultDataModel = new ListDataModel(viewProbationResult);
        } else {
            viewProbationResult = approveProbationBeanLocal.findAllResult();
            probationResultDataModel = new ListDataModel(viewProbationResult);
        }
    }

    public void populateProbationResult(SelectEvent event) {
        resultListDataModel = null;
        hrEvalResultProbations = (HrEvalResultProbations) event.getObject();
        hrEvalResultProbations = approveProbationBeanLocal.getSelectedResult(hrEvalResultProbations.getId());
        setHrEmployees(hrEvalResultProbations.getEmpId());
        newPage = true;
        searchPage = false;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
        recreateDataModel();
    }

    public void recreateDataModel() {
        resultListDataModel = null;
        resultListDataModel = new ListDataModel(new ArrayList(hrEvalResultProbations.getHrEvaResultDetailProbationList()));
    }

    public void workflowDataModel() {
        workflowDataModel = null;
        for (int i = 0; i < hrEvalResultProbations.getWfHrProcessedList().size(); i++) {
            if (hrEvalResultProbations.getWfHrProcessedList().get(i).getDecision() == 0) {
                hrEvalResultProbations.getWfHrProcessedList().get(i).setChangedStstus("Request");
            } else if (hrEvalResultProbations.getWfHrProcessedList().get(i).getDecision() == 1 || hrEvalResultProbations.getWfHrProcessedList().get(i).getDecision() == 3) {
                hrEvalResultProbations.getWfHrProcessedList().get(i).setChangedStstus("Permanet");
            } else if (hrEvalResultProbations.getWfHrProcessedList().get(i).getDecision() == 2 || hrEvalResultProbations.getWfHrProcessedList().get(i).getDecision() == 4) {
                hrEvalResultProbations.getWfHrProcessedList().get(i).setChangedStstus("Terminated");
            }
        }
        workflowDataModel = new ListDataModel(new ArrayList(hrEvalResultProbations.getWfHrProcessedList()));
    }

    public SelectItem[] getListOfProbation() {
        return JsfUtil.getSelectItems(approveProbationBeanLocal.findAllEvaluationDecision(hrEvalResultProbations), true);
    }

    public SelectItem[] getRecommendationList() {
        if (hrEvalResultProbations.getRecommendation() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(approveProbationBeanLocal.findAllEvaluationDecision(hrEvalResultProbations), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select ---");
            return items;
        }
    }

    public void handleSelectDecision(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

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

    public void onNodeSelectSearch(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        srcDepartments.setDepId(deptId);
        srcDepartments = evaluationResultProbationBeanLocal.findByDepartmentId(srcDepartments);
        hrEmployees.setDeptId(srcDepartments);
    }
//</editor-fold>

    public void editProbationResult() {
        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_VALUE);
            hrEvalResultProbations.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
            hrEvalResultProbations.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
            hrEvalResultProbations.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
            hrEvalResultProbations.setStatus(workFlow.getUserStatus());
        }
    }

    public void saveProbationApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveProbationApprove", dataset)) {
                try {
                    if (hrEvalResultProbations.getEmpId().getEmpStatus() == 1) {
                        JsfUtil.addFatalMessage(hrEvalResultProbations.getEmpId().getFirstName() + " is already permanent employee!. Try again.");
                    } else if (hrEvalResultProbations.getEmpId().getEmpStatus() == 3) {
                        JsfUtil.addFatalMessage(hrEvalResultProbations.getEmpId().getFirstName() + " Probation period is already terminated!. Try again.");
                    } else if (hrEvalResultProbations.getRecommendation().equalsIgnoreCase("Make Permanent")) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        hrEvalResultProbations.setStatus(workFlow.getUserStatus());
                        hrEmployees.setEmpStatus(1);
                        JsfUtil.addSuccessMessage(hrEvalResultProbations.getEmpId().getFirstName() + " is now permanent employee.");
                    } else if (hrEvalResultProbations.getRecommendation().equalsIgnoreCase("Terminate")) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        hrEvalResultProbations.setStatus(workFlow.getUserStatus());
                        hrEmployees.setEmpStatus(3);
                        JsfUtil.addSuccessMessage("Probation period is terminated for " + hrEvalResultProbations.getEmpId().getFirstName());
                    }
                    wfHrProcessed.setProbationId(hrEvalResultProbations);
                    wfHrProcessed.setDecision(hrEvalResultProbations.getStatus());
                    wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                    wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    approveProbationBeanLocal.edit(hrEvalResultProbations);
                    approveProbationBeanLocal.edit(hrEmployees);
                    probationList = approveProbationBeanLocal.findPreparedList();
                    clearPage();
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Error occure while save update");
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveProbationApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearPage() {
        hrEvalResultProbations = new HrEvalResultProbations();
        evalutor = new HrEmployees();
        hrEmployees = new HrEmployees();
        hrLuGrades = new HrLuGrades();
        resultListDataModel = null;
    }

    public void searchProbationResult() {
        if (srcEmployees.getEmpId().isEmpty() && srcEmployees.getFirstName().isEmpty()) {
            resultList = evaluationResultProbationBeanLocal.findAll();
            probationResultDataModel = new ListDataModel(resultList);
        } else if (srcEmployees.getEmpId() != null && srcEmployees.getFirstName().isEmpty()) {
            resultList = evaluationResultProbationBeanLocal.findByEmpId(srcEmployees);
            probationResultDataModel = new ListDataModel(resultList);
        } else if (srcEmployees.getEmpId().isEmpty() && srcEmployees.getFirstName() != null) {
            resultList = evaluationResultProbationBeanLocal.findByFName(srcEmployees);
            probationResultDataModel = new ListDataModel(resultList);
        } else if (srcEmployees.getEmpId() != null && srcEmployees.getFirstName() != null) {
            resultList = evaluationResultProbationBeanLocal.findByEmpIdAndName(srcEmployees, srcEmployees);
            probationResultDataModel = new ListDataModel(resultList);
        }
    }

}
