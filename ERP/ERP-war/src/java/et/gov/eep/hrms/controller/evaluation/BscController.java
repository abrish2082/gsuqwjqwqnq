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
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped; 
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.evaluation.BscEvaBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.entity.evaluation.HrBscResults;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;

/**
 *
 * @author INSA
 */
@Named(value = "bscController")
@ViewScoped
public class BscController implements Serializable {

    @EJB
    BscEvaBeanLocal bscBeanLocal;

    @EJB
    HrEmployeeBeanLocal employeeBeanLocal;

    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;

    @EJB
    HrDepartmentsIntegrationBeanLocal departmentBeanLocal;

    @Inject
    HrBsc hrBsc;

    @Inject
    HrBscResults hrBscResults;

    @Inject
    HrBscSessions hrBscSessions;

    @Inject
    HrEmployees hrEmployees;

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
    SessionBean sessionBeanLocal;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;

    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String addorUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean btnNewRender = false;
    String prepDate;

    private List<HrBsc> bscList;
    List<HrBscSessions> activeSessions;
    List<HrEmployees> employeeList = new ArrayList<>();
    DataModel<HrBsc> bscDataModel;
    DataModel<HrBscResults> bscResultDataModel;
    Set<String> checkEvaluation = new HashSet<>();

    public BscController() {
    }

    @PostConstruct
    public void init() {
        String currentDate = StringDateManipulation.toDayInEc();
        setActiveSessions(bscBeanLocal.readActiveSession(currentDate));
        loadTree();
        setFullName("Logged in user name");
        setPrepDate(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="authentication code (it should be removed)">
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrBsc getHrBsc() {
        if (hrBsc == null) {
            hrBsc = new HrBsc();
        }
        return hrBsc;
    }

    public void setHrBsc(HrBsc hrBsc) {
        this.hrBsc = hrBsc;
    }

    public HrBscResults getHrBscResults() {
        if (hrBscResults == null) {
            hrBscResults = new HrBscResults();
        }
        return hrBscResults;
    }

    public void setHrBscResults(HrBscResults hrBscResults) {
        this.hrBscResults = hrBscResults;
    }

    public HrBscSessions getHrBscSessions() {
        if (hrBscSessions == null) {
            hrBscSessions = new HrBscSessions();
        }
        return hrBscSessions;
    }

    public void setHrBscSessions(HrBscSessions hrBscSessions) {
        this.hrBscSessions = hrBscSessions;
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

    public List<HrBsc> getBscList() {
        if (bscList == null) {
            bscList = new ArrayList<>();
        }
        return bscList;
    }

    public void setBscList(List<HrBsc> bscList) {
        this.bscList = bscList;
    }

    public List<HrBscSessions> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(List<HrBscSessions> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public List<HrEmployees> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<HrEmployees> employeeList) {
        this.employeeList = employeeList;
    }

    public DataModel<HrBsc> getBscDataModel() {
        if (bscDataModel == null) {
            bscDataModel = new ArrayDataModel<>();
        }
        return bscDataModel;
    }

    public void setBscDataModel(DataModel<HrBsc> bscDataModel) {
        this.bscDataModel = bscDataModel;
    }

    public DataModel<HrBscResults> getBscResultDataModel() {
        if (bscResultDataModel == null) {
            bscResultDataModel = new ArrayDataModel<>();
        }
        return bscResultDataModel;
    }

    public void setBscResultDataModel(DataModel<HrBscResults> bscResultDataModel) {
        this.bscResultDataModel = bscResultDataModel;
    }

    public Set<String> getCheckEvaluation() {
        return checkEvaluation;
    }

    public void setCheckEvaluation(Set<String> checkEvaluation) {
        this.checkEvaluation = checkEvaluation;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getPrepDate() {
        return prepDate;
    }

    public void setPrepDate(String prepDate) {
        this.prepDate = prepDate;
    }
    //</editor-fold>

    public void bscSession_vcl(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrBscSessions.setId(Integer.parseInt(event.getNewValue().toString()));
            hrBscSessions = bscBeanLocal.findById(hrBscSessions);
            hrBsc.setSessionId(hrBscSessions);
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

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments.setDepId(deptId);
        hrDepartments = bscBeanLocal.findByDepartmentId(hrDepartments);
        hrEmployees.setDeptId(hrDepartments);
        employeeList = bscBeanLocal.findEmployee(hrEmployees);
        hrBsc.setDeptId(hrDepartments);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('dlg1').hide();");
    }

    public void onNodeSelectSearch(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        srcDepartments.setDepId(deptId);
        srcDepartments = bscBeanLocal.findByDepartmentId(srcDepartments);
        hrEmployees.setDeptId(srcDepartments);
    }
//</editor-fold>

    public void bscResultInfo() {
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
        clearBscResult();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    //<editor-fold defaultstate="collapsed" desc="main">
    public SelectItem[] getEmployee() {
        if (hrBsc.getDeptId() != null) {
            hrDepartments.setDepId(hrBsc.getDeptId().getDepId());
            hrEmployees.setDeptId(hrDepartments);
            return JsfUtil.getSelectItems(bscBeanLocal.findEmployee(hrEmployees), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select ---");
            return items;
        }
    }

    public void employeeListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrEmployees.setFirstName(event.getNewValue().toString());
            hrEmployees = hrEmployeeBeanLocal.getByFirstName(hrEmployees);
            setHrEmployees(hrEmployees);
            hrBscResults.setEmpId(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
        }
    }

    public ArrayList<HrEmployees> searchEvaluatorName(String hrEmp) {
        ArrayList<HrEmployees> evaluatorEmp = null;
        evalutor.setFirstName(hrEmp);
        evaluatorEmp = employeeBeanLocal.SearchByFname(evalutor);
        return evaluatorEmp;
    }

    public void getEvaluatorName(SelectEvent event) {
        try {
            evalutor.setFirstName(event.getObject().toString());
            evalutor = employeeBeanLocal.getByFirstName(evalutor);
            hrBsc.setPreparedBy(evalutor);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void addBSCResult() {
        if (hrBscResults.getBscResult() > 100 || hrBscResults.getBscResult() < 0) {
            JsfUtil.addFatalMessage("Result must be between 1 and 100!. Try again.");
        } else if (checkEvaluation.contains(hrBscResults.getEmpId().getFirstName())) {
            JsfUtil.addFatalMessage(hrBscResults.getEmpId().getFirstName() + "  is already Evaluated!. Try again.");
        } else {
            hrBscResults.setEmpId(hrEmployees);
            hrBsc.addBSCResult(hrBscResults);
            checkEvaluation.add(hrBscResults.getEmpId().getFirstName());
            recreateDataModel();
            clearBSCResult();
            JsfUtil.addSuccessMessage("Successfully Add");
        }
    }

    public void recreateDataModel() {
        bscResultDataModel = null;
        bscResultDataModel = new ListDataModel(new ArrayList(hrBsc.getHrBscResultsList()));
    }

    public void clearBSCResult() {
        hrBscResults = null;
        hrEmployees = null;
        hrJobTypes = new HrJobTypes();
        hrLuGrades = null;
    }

    public void editDataTable() {
        hrBscResults = bscResultDataModel.getRowData();
        hrEmployees = hrBscResults.getEmpId();
        hrJobTypes = hrEmployees.getJobId();
        hrDepartments = hrEmployees.getDeptId();
        hrEmployees.setDeptId(hrDepartments);
        employeeList = bscBeanLocal.findEmployee(hrEmployees);
        setHrEmployees(hrBscResults.getEmpId());
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
        addorUpdate = "Update";
    }

    public void saveResult() {
        if ((!(getBscResultDataModel().getRowCount() > 0))) {
            JsfUtil.addFatalMessage("Data table shoud be filled");
        } else {
            try {
                if (updateStatus == 0) {
                    bscBeanLocal.save(hrBsc);
                    JsfUtil.addSuccessMessage("Saved Successfully");
                    clearBscResult();
                } else if (updateStatus == 1) {
                    bscBeanLocal.edit(hrBsc);
                    JsfUtil.addSuccessMessage("Modified Successfully");
                    updateStatus = 0;
                    saveorUpdateBundle = "Save";
                    clearBscResult();
                }
            } catch (Exception ex) {
                JsfUtil.addFatalMessage("Error Occured in Saving data");
            }
        }
    }

    public void saveBscResult() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveBscResult", dataset)) {
                if (hrBsc.getDeptId() == null) {
                    JsfUtil.addFatalMessage("Department Can't be empty!. Try again.");
                } else if ((!(getBscResultDataModel().getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Data table shoud be filled");
                } else {
                    try {
                        hrBsc.setPreparedOn(prepDate);
                        bscBeanLocal.saveOrUpdate(hrBsc);
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Saved Successfully");
                            clearBscResult();
                        } else if (updateStatus == 1) {
                            JsfUtil.addSuccessMessage("Updated Successfully");
                            clearBscResult();
                            saveorUpdateBundle = "Save";
                        }
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Error occure while save update");
                    }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveBscResult");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearBscResult() {
        hrBsc = new HrBsc();
        hrBscResults = new HrBscResults();
        hrBscSessions = new HrBscSessions();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
        bscResultDataModel = new ArrayDataModel<>();
        evalutor = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        addorUpdate = "Add";
    }

    public void bscResultList() {
        if (srcDepartments.getDepName() != null) {
            bscList = bscBeanLocal.findByDepName(srcDepartments);
            bscDataModel = new ListDataModel(bscList);
        } else {
            bscList = bscBeanLocal.findAllBscResult();
            bscDataModel = new ListDataModel(bscList);
        }
    }

    public void bscResultDisplayChanged(SelectEvent event) {
        hrBsc = (HrBsc) event.getObject();
        prepDate = hrBsc.getPreparedOn();
        hrBsc = bscBeanLocal.getSelectedResult(hrBsc.getId());
        setHrEmployees(hrBscResults.getEmpId());
        setEvalutor(hrBsc.getPreparedBy());
        hrDepartments = hrBsc.getDeptId();
        hrBscSessions = hrBsc.getSessionId();
        updateStatus = 1;
        newPage = true;
        searchPage = false;
        btnNewRender = true;
        newOrSearch = "Search";
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        recreateDataModel();
    }
    //</editor-fold>

}
