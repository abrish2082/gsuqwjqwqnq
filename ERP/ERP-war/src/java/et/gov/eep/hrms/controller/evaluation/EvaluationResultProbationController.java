/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.evaluation;

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
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import webService.UserStatus;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationProbationBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationResultProbationBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluatonResultDetailProbationBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaResultDetailProbation;
import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
import et.gov.eep.hrms.entity.evaluation.HrEvalResultProbations;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;

/**
 *
 * @author Ob
 */
@Named(value = "evaluationResultProbationController")
@ViewScoped
public class EvaluationResultProbationController implements Serializable {

    @Inject
    HrEvalResultProbations hrEvalResultProbations;
    @Inject
    HrEvalCriteriaProbations hrEvalCriteriaProbations;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrEmployees srcEmployees;
    @Inject
    HrEmployees evalutor;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrDepartments srcDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    HrEvaResultDetailProbation hrEvaResultDetailProbation;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    EvaluationResultProbationBeanLocal evaluationResultProbationBeanLocal;
    @EJB
    EvaluationProbationBeanLocal evaluationProbationBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    EvaluatonResultDetailProbationBeanLocal evaluatonResultDetailProbationBeanLocal;
    @EJB
    HrDepartmentsIntegrationBeanLocal departmentBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    List<HrEvaResultDetailProbation> listEvaluationResultDetail = new ArrayList<>();
    private List<HrEvalCriteriaProbations> listOfCriteria;
    DataModel<HrEvaResultDetailProbation> probationDetailDataModel = new ListDataModel<>();
    private List<HrEvalResultProbations> viewProbationResult;
    List<HrEmployees> employeeList = new ArrayList<>();
    List<HrDepartments> listOfDepartments;
    DataModel<HrEvalResultProbations> probationResultDataModel;
    DataModel<HrEvaResultDetailProbation> resultListDataModel;
    DataModel<HrEvalCriteriaProbations> probationCriteriaDataModel = new ListDataModel<>();
    private List<HrEvalResultProbations> resultList;
    private List<HrEvalCriteriaProbations> criteriaList = new ArrayList<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    UserStatus userStatus = new UserStatus();

    private String newOrSearch = "New";
    int updateStatus = 0;
    private String saveorUpdate = "Save";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean btnNewRender = false;
    Set<String> checkEvaluation = new HashSet<>();
    String evalDate;
    private boolean criteria = true;
    private boolean resultDetail = false;

    @PostConstruct
    public void init() {
        criteriaList = evaluatonResultDetailProbationBeanLocal.findAllCriteria();
        listEvaluationResultDetail = evaluatonResultDetailProbationBeanLocal.findAll();
        loadTree();
        setEvalDate(StringDateManipulation.toDayInEc());
    }

    public EvaluationResultProbationController() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">    
    public HrEvalResultProbations getHrEvalResultProbations() {
        if (hrEvalResultProbations == null) {
            hrEvalResultProbations = new HrEvalResultProbations();
        }
        return hrEvalResultProbations;
    }

    public void setHrEvalResultProbations(HrEvalResultProbations hrEvalResultProbations) {

        this.hrEvalResultProbations = hrEvalResultProbations;
    }

    public List<HrEvaResultDetailProbation> getListEvaluationResultDetail() {
        return listEvaluationResultDetail;
    }

    public void setListEvaluationResultDetail(List<HrEvaResultDetailProbation> listEvaluationResultDetail) {
        this.listEvaluationResultDetail = listEvaluationResultDetail;
    }

    public List<HrEvalCriteriaProbations> getListOfCriteria() {
        listOfCriteria = evaluationProbationBeanLocal.findAllCriteria();
        return listOfCriteria;
    }

    public HrEvalCriteriaProbations changeListener(ValueChangeEvent event) {
        try {
            hrEvalCriteriaProbations.setCriteriaName(event.getNewValue().toString());
            hrEvalCriteriaProbations = evaluationProbationBeanLocal.findCriteriaByName(hrEvalCriteriaProbations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hrEvalCriteriaProbations;
    }

    public void setListOfCriteria(List<HrEvalCriteriaProbations> listOfCriteria) {
        this.listOfCriteria = listOfCriteria;
    }

    public DataModel<HrEvaResultDetailProbation> getProbationDetailDataModel() {
        return probationDetailDataModel;
    }

    public void setProbationDetailDataModel(DataModel<HrEvaResultDetailProbation> probationDetailDataModel) {
        this.probationDetailDataModel = probationDetailDataModel;
    }

    public DataModel<HrEvalCriteriaProbations> getProbationCriteriaDataModel() {
        return probationCriteriaDataModel;
    }

    public void setProbationCriteriaDataModel(DataModel<HrEvalCriteriaProbations> probationCriteriaDataModel) {
        this.probationCriteriaDataModel = probationCriteriaDataModel;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public HrEvalCriteriaProbations getHrEvalCriteriaProbations() {
        if (hrEvalCriteriaProbations == null) {
            hrEvalCriteriaProbations = new HrEvalCriteriaProbations();
        }
        return hrEvalCriteriaProbations;
    }

    public void setHrEvalCriteriaProbations(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        this.hrEvalCriteriaProbations = hrEvalCriteriaProbations;
    }

    public HrEvaResultDetailProbation getHrEvaResultDetailProbation() {
        if (hrEvaResultDetailProbation == null) {
            hrEvaResultDetailProbation = new HrEvaResultDetailProbation();
        }
        return hrEvaResultDetailProbation;
    }

    public void setHrEvaResultDetailProbation(HrEvaResultDetailProbation hrEvaResultDetailProbation) {
        this.hrEvaResultDetailProbation = hrEvaResultDetailProbation;
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

    public HrEmployees getSrcEmployees() {
        if (srcEmployees == null) {
            srcEmployees = new HrEmployees();
        }
        return srcEmployees;
    }

    public void setSrcEmployees(HrEmployees srcEmployees) {
        this.srcEmployees = srcEmployees;
    }

    public HrEmployees getEvalutor() {
        if (evalutor == null) {
            evalutor = new HrEmployees();
        }
        return evalutor;
    }

    public void setEvalutor(HrEmployees evalutor) {
        this.evalutor = evalutor;
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

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public List<HrEvalCriteriaProbations> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<HrEvalCriteriaProbations> criteriaList) {
        this.criteriaList = criteriaList;
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

    public String getSaveorUpdate() {
        return saveorUpdate;
    }

    public void setSaveorUpdate(String saveorUpdate) {
        this.saveorUpdate = saveorUpdate;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getEvalDate() {
        return evalDate;
    }

    public void setEvalDate(String evalDate) {
        this.evalDate = evalDate;
    }

    public Set<String> getCheckEvaluation() {
        return checkEvaluation;
    }

    public void setCheckEvaluation(Set<String> checkEvaluation) {
        this.checkEvaluation = checkEvaluation;
    }

    public List<HrEvalResultProbations> getViewProbationResult() {
        return viewProbationResult;
    }

    public void setViewProbationResult(List<HrEvalResultProbations> viewProbationResult) {
        this.viewProbationResult = viewProbationResult;
    }

    public List<HrEmployees> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<HrEmployees> employeeList) {
        this.employeeList = employeeList;
    }

    public List<HrDepartments> getListOfDepartments() {
        return evaluationResultProbationBeanLocal.findAllDepartment();
    }

    public void setListOfDepartments(List<HrDepartments> listOfDepartments) {
        this.listOfDepartments = listOfDepartments;
    }

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

    public boolean isResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(boolean resultDetail) {
        this.resultDetail = resultDetail;
    }

    public boolean isCriteria() {
        return criteria;
    }

    public void setCriteria(boolean criteria) {
        this.criteria = criteria;
    }
    //</editor-fold>

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

    public void newButtonAction() {
        resetProbationResult();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    public void displayProbationResult() {
        if (srcDepartments.getDepName() != null) {
            viewProbationResult = evaluationResultProbationBeanLocal.findByDepName(srcDepartments);
            probationResultDataModel = new ListDataModel(viewProbationResult);
        } else {
            viewProbationResult = evaluationResultProbationBeanLocal.findAll();
            probationResultDataModel = new ListDataModel(viewProbationResult);
        }
    }

    public void employeeListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrEmployees.setFirstName(event.getNewValue().toString());
            hrEmployees = hrEmployeeBeanLocal.getByFirstName(hrEmployees);
            setHrEmployees(hrEmployees);
            hrEvalResultProbations.setEmpId(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
        }
    }

    public ArrayList<HrEmployees> searchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employe = new ArrayList<>();
        hrEmployees.setFirstName(hrEmployee);
        employe = hrEmployeeBean.SearchByFname(hrEmployees);
        return employe;
    }

    public void getByEmpName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            hrEvalResultProbations.setEmpId(hrEmployees);
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrEmployees> searchEmpName(String hrEmployee) {
        ArrayList<HrEmployees> employe = new ArrayList<>();
        hrEmployees.setFirstName(hrEmployee);
        employe = evaluationResultProbationBeanLocal.searchEmpName(hrEmployees);
        return employe;
    }

    public ArrayList<HrEmployees> searchEvaluatorName(String hrEmp) {
        ArrayList<HrEmployees> evaluatorEmp = new ArrayList<>();
        evalutor.setFirstName(hrEmp);
        evaluatorEmp = hrEmployeeBean.SearchByFname(evalutor);
        return evaluatorEmp;
    }

    public void getEvaluatorName(SelectEvent event) {
        try {
            evalutor.setFirstName(event.getObject().toString());
            evalutor = hrEmployeeBean.getByFirstName(evalutor);
            hrEvalResultProbations.setEvaluatorId(evalutor);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void edit() {
        hrEvaResultDetailProbation = probationDetailDataModel.getRowData();
        hrEvalCriteriaProbations = hrEvaResultDetailProbation.getEvaCriteriaId();
    }

    public void addProbationDetail() {
        if (hrEvalCriteriaProbations != null) {
            hrEvaResultDetailProbation.setEvaCriteriaId(hrEvalCriteriaProbations);
        }
        if (checkEvaluation.contains(hrEvaResultDetailProbation.getEvaCriteriaId().getCriteriaName())) {
            JsfUtil.addFatalMessage("Duplicate Criteria Name!. Try again.");
        } else {
            hrEvalResultProbations.addProbationDetail(hrEvaResultDetailProbation);
            hrEvaResultDetailProbation.setEvaCriteriaId(hrEvalCriteriaProbations);
            checkEvaluation.add(hrEvaResultDetailProbation.getEvaCriteriaId().getCriteriaName());
            recreateDetailDataModel();
            reset();
        }
    }

    public void resultListner(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            for (int i = 0; i < criteriaList.size(); i++) {
                hrEvalCriteriaProbations = new HrEvalCriteriaProbations();
                hrEvaResultDetailProbation = new HrEvaResultDetailProbation();
                System.out.println("size =" + criteriaList.size());
                hrEvalCriteriaProbations = criteriaList.get(i);
                hrEvaResultDetailProbation.setEvaCriteriaId(hrEvalCriteriaProbations);
                hrEvaResultDetailProbation.setProbationResult(hrEvalCriteriaProbations.getResult());
                hrEvaResultDetailProbation.setProbationResult(hrEvalCriteriaProbations.getReason());
                hrEvalResultProbations.addProbationDetail(hrEvaResultDetailProbation);
                hrEvaResultDetailProbation = null;
            }

            for (HrEvaResultDetailProbation det : hrEvalResultProbations.getHrEvaResultDetailProbationList()) {
                det.setEvaResultId(hrEvalResultProbations);
                hrEvalResultProbations.addProbationDetail(det);
            }
            hrEvalCriteriaProbations = probationCriteriaDataModel.getRowData();
            hrEvaResultDetailProbation.setEvaCriteriaId(hrEvalCriteriaProbations);
            hrEvaResultDetailProbation.setProbationResult(hrEvalCriteriaProbations.getResult());
            hrEvaResultDetailProbation.setProbationResult(hrEvalCriteriaProbations.getReason());
            hrEvalResultProbations.addProbationDetail(hrEvaResultDetailProbation);

        }
    }

    public void saveProbationResult() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveProbationResult", dataset)) {
                try {
                    if (updateStatus == 0) {
                        hrEvalResultProbations.setStatus(Constants.PREPARE_VALUE);
                        hrEvalResultProbations.setEvaluationDate(evalDate);
                        if (criteriaList.size() > 0) {
                            for (int i = 0; i < criteriaList.size(); i++) {
                                hrEvalCriteriaProbations = new HrEvalCriteriaProbations();
                                hrEvaResultDetailProbation = new HrEvaResultDetailProbation();
                                hrEvalCriteriaProbations = criteriaList.get(i);
                                hrEvaResultDetailProbation.setEvaCriteriaId(hrEvalCriteriaProbations);
                                hrEvaResultDetailProbation.setProbationResult(hrEvalCriteriaProbations.getResult());
                                hrEvaResultDetailProbation.setDescription(hrEvalCriteriaProbations.getReason());
                                hrEvalResultProbations.addProbationDetail(hrEvaResultDetailProbation);
                            }
                            hrEvalResultProbations.setStatus(Constants.PREPARE_VALUE);
                            evaluationResultProbationBeanLocal.create(hrEvalResultProbations);
                            wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                            wfHrProcessed.setDecision(hrEvalResultProbations.getStatus());
                            wfHrProcessed.setProbationId(hrEvalResultProbations);
                            wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Saved successfully");
                            resetProbationResult();
                        }
                    } else {
                        evaluationResultProbationBeanLocal.edit(hrEvalResultProbations);
                        JsfUtil.addSuccessMessage("Modified successfully");
                        resetProbationResult();
                    }
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Error occurs while save  update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveProbationResult");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetProbationResult() {
        hrEvalResultProbations = new HrEvalResultProbations();
        evalutor = new HrEmployees();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
        hrEvaResultDetailProbation = null;
//        probationDetailDataModel = null;
        updateStatus = 0;
        saveorUpdate = "Save";
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

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments.setDepId(deptId);
        hrDepartments = evaluationResultProbationBeanLocal.findByDepartmentId(hrDepartments);
        hrEmployees.setDeptId(hrDepartments);
        employeeList = evaluationResultProbationBeanLocal.findEmployee(hrEmployees);
        hrEvalResultProbations.setDeptId(hrDepartments);
    }

    public void onNodeSelectSearch(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        srcDepartments.setDepId(deptId);
        srcDepartments = evaluationResultProbationBeanLocal.findByDepartmentId(srcDepartments);
        hrEmployees.setDeptId(srcDepartments);
    }
//</editor-fold>

    public void populateProbationResult(SelectEvent event) {
        resultListDataModel = null;
        hrEvalResultProbations = (HrEvalResultProbations) event.getObject();
//        hrEvalResultProbations = evaluationResultProbationBeanLocal.getSelectedResult(hrEvalResultProbations.getId());
        evalDate = hrEvalResultProbations.getEvaluationDate();
        setHrEmployees(hrEvalResultProbations.getEmpId());
        evalutor = hrEvalResultProbations.getEvaluatorId();
        recreateDetailDataModel();
        workflowDataModel();
        updateStatus = 1;
        saveorUpdate = "Update";
        newPage = true;
        searchPage = false;
        btnNewRender = true;
        criteria = false;
        resultDetail = true;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
    }

    public void reset() {
        hrEvaResultDetailProbation = null;
        hrEvalCriteriaProbations = null;
    }

    public void recreateDetailDataModel() {
        probationDetailDataModel = null;
        probationDetailDataModel = new ListDataModel(new ArrayList(hrEvalResultProbations.getHrEvaResultDetailProbationList()));
    }

    public void recreateCriteriaDataModel() {
        probationCriteriaDataModel = null;
        probationCriteriaDataModel = new ListDataModel(new ArrayList(criteriaList));
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
