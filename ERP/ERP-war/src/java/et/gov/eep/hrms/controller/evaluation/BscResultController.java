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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.committee.HrCommitteeMembersBeaLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.BSCResultBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.BscEvaBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.entity.evaluation.HrBscResults;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;

/**
 *
 * @author munir
 */
@Named(value = "bscResultController")
@ViewScoped
public class BscResultController implements Serializable {

    @EJB
    BscEvaBeanLocal bscBeanLocal;

    @EJB
    BSCResultBeanLocal bscResultBeanLocal;

    @EJB
    HrDepartmentsIntegrationBeanLocal hrDepartmentsIntegrationBeanLocal;

    @EJB
    HREmployeesBeanLocal hrEmployeesBeanLocal;

    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;

    @EJB
    HrCommitteeMembersBeaLocal hrCommitteeMembersBeaLocal;

    @Inject
    HrBsc hrBsc;

    @Inject
    HrBscResults hrBscResults;

    @Inject
    HrBscSessions hrBscSessions;

    @Inject
    HrDepartments hrDepartments;

    @Inject
    HrEmployees hrEmployees;

    @Inject
    HrEmployees evalutor;

    @Inject
    HrJobTypes hrJobTypes;

    @Inject
    HrLuGrades hrLuGrades;

    int updateStatus = 0;
    private String saveorUpdate = "Save";
    private String addorUpdate = "Add";
    boolean isEvaluatedCkbSelected;
    boolean isNonEvaluatedCkbSelected;
    int selectedRowIndex = -1;

    List<HrBscSessions> activeSessions;
    private DataModel<HrBscResults> bscResultDataModel;
    Set<String> checkEvaluation = new HashSet<>();

    public BscResultController() {
    }

    @PostConstruct
    public void init() {
        String currentDate = StringDateManipulation.toDayInEc();
        setActiveSessions(bscResultBeanLocal.readActiveSession(currentDate));
        loadTree();
    }

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public HrEmployees getEvalutor() {
        return evalutor;
    }

    public void setEvalutor(HrEmployees evalutor) {
        this.evalutor = evalutor;
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

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrBsc getHrBsc() {
        return hrBsc;
    }

    public void setHrBsc(HrBsc hrBsc) {
        this.hrBsc = hrBsc;
    }

    public HrBscResults getHrBscResults() {
        return hrBscResults;
    }

    public void setHrBscResults(HrBscResults hrBscResults) {
        this.hrBscResults = hrBscResults;
    }

    public HrBscSessions getHrBscSessions() {
        return hrBscSessions;
    }

    public void setHrBscSessions(HrBscSessions hrBscSessions) {
        this.hrBscSessions = hrBscSessions;
    }

    public DataModel<HrBscResults> getBscResultDataModel() {
        if (bscResultDataModel == null) {
            bscResultDataModel = new ListDataModel<>();
        }
        return bscResultDataModel;
    }

    public void setBscResultDataModel(DataModel<HrBscResults> bscResultDataModel) {
        this.bscResultDataModel = bscResultDataModel;
    }

    public List<HrBscSessions> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(List<HrBscSessions> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public boolean isIsEvaluatedCkbSelected() {
        return isEvaluatedCkbSelected;
    }

    public void setIsEvaluatedCkbSelected(boolean isEvaluatedCkbSelected) {
        this.isEvaluatedCkbSelected = isEvaluatedCkbSelected;
    }

    public boolean isIsNonEvaluatedCkbSelected() {
        return isNonEvaluatedCkbSelected;
    }

    public void setIsNonEvaluatedCkbSelected(boolean isNonEvaluatedCkbSelected) {
        this.isNonEvaluatedCkbSelected = isNonEvaluatedCkbSelected;
    }

    public String getSaveorUpdate() {
        return saveorUpdate;
    }

    public void setSaveorUpdate(String saveorUpdate) {
        this.saveorUpdate = saveorUpdate;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public Set<String> getCheckEvaluation() {
        return checkEvaluation;
    }

    public void setCheckEvaluation(Set<String> checkEvaluation) {
        this.checkEvaluation = checkEvaluation;
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
        allDepartments = hrDepartmentsIntegrationBeanLocal.findAllDepartmentInfo();
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
        hrDepartments = hrDepartmentsIntegrationBeanLocal.getdepartmentInformation(hrDepartments);
        hrDepartments = hrBsc.getDeptId();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">
    public void recreateDataModel(List<HrBscResults> bscResults) {
        bscResultDataModel = null;
        bscResultDataModel = new ListDataModel(new ArrayList<>(bscResults));
    }

    public void bscSession_vcl(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrBscSessions.setId(Integer.parseInt(event.getNewValue().toString()));
            hrBscSessions = bscResultBeanLocal.findById(hrBscSessions);
            hrBsc.setSessionId(hrBscSessions);
        }
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = bscBeanLocal.searchEmp(hrEmployee);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        hrEmployees.setFirstName((event.getObject().toString()));
        hrEmployees = bscBeanLocal.getByFName(hrEmployees);
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
    }

    public void btnRowSelector() {
        selectedRowIndex = getBscResultDataModel().getRowIndex();
        displayBscResult();
    }

    private void displayBscResult() {
        if (selectedRowIndex > -1) {
            hrBscResults = getBscResultDataModel().getRowData();
        }
    }

    public String btnPrev_action() {
        if (selectedRowIndex > -1) {
        }
        selectedRowIndex--;
        displayBscResult();
        return null;
    }

    public String btnNext_action() {
        if (selectedRowIndex > -1) {
        }
        selectedRowIndex++;
        displayBscResult();
        return null;
    }

    public void addToTable() {
        selectedRowIndex = -1;
    }

    public String btnload_action() {
        loadEmployees(true);
        return null;
    }

    private void loadEmployees(boolean load) {
        try {
            if (hrDepartments.getDepId() != null && hrBscSessions.getId() != null) {
                int searchCondition = 0;
                int department = hrDepartments.getDepId();
                int sessionId = hrBscSessions.getId();
                HrBsc hrBsc = bscResultBeanLocal.selectBSC(department, sessionId);
                if (hrBsc != null) {
                } else {
                }
                if (isEvaluatedCkbSelected && isNonEvaluatedCkbSelected) {
                    searchCondition = 2;
                } else if (isEvaluatedCkbSelected && !isNonEvaluatedCkbSelected) {
                    searchCondition = 1;
                }
                if (load) {
                    List<HrBscResults> bscResults = new ArrayList<>();
                    bscResults = bscResultBeanLocal.readEmployees(department, sessionId, searchCondition);
                    recreateDataModel(bscResults);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addBSCResult() {
        hrBscResults.setEmpId(hrEmployees);
        hrBsc.addBSCResult(hrBscResults);
        recreateDataModel();
        clearBSCResult();
    }

    public void recreateDataModel() {
        bscResultDataModel = null;
        bscResultDataModel = new ListDataModel(new ArrayList(hrBsc.getHrBscResultsList()));
    }

    public void clearBSCResult() {
        hrBscResults = new HrBscResults();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
    }

    public void editDataTable() {
        hrBscResults = bscResultDataModel.getRowData();
        hrEmployees = hrBscResults.getEmpId();
        hrJobTypes = hrEmployees.getJobId();
        hrDepartments = hrEmployees.getDeptId();
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
        addorUpdate = "Add Changes";
    }

    public ArrayList<HrEmployees> searchEvaluatorName(String hrEmp) {
        ArrayList<HrEmployees> evaluatorEmp = null;
        evalutor.setFirstName(hrEmp);
        evaluatorEmp = hrEmployeeBean.SearchByFname(evalutor);
        return evaluatorEmp;
    }

    public void getEvaluatorName(SelectEvent event) {
        try {
            evalutor.setFirstName(event.getObject().toString());
            evalutor = hrEmployeeBean.getByFirstName(evalutor);
            hrBsc.setPreparedBy(evalutor);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void save() {
        if (updateStatus == 0) {
            try {
                bscBeanLocal.save(hrBsc);
                JsfUtil.addSuccessMessage("Saved Successfully");
                clearPage();
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Error occured while saving");
            }
        } else {
            try {
                bscBeanLocal.edit(hrBsc);
                JsfUtil.addSuccessMessage("Modified Successfully");
                updateStatus = 0;
                saveorUpdate = "Save";
                clearPage();
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Error occured while updating");
                updateStatus = 0;
                saveorUpdate = "Save";
            }
        }
    }

    public void clearPage() {
        hrBsc = null;
        hrBscResults = null;
        bscResultDataModel = null;
        hrBscSessions = null;
        hrEmployees = null;
        hrDepartments = null;
        hrEmployees = null;
        hrJobTypes = null;
        bscResultDataModel = null;
        evalutor = null;
    }
//</editor-fold>

}
