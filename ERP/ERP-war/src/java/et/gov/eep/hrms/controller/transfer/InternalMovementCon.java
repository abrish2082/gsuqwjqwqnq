/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.transfer;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPgPlDeptBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.InternalMovementBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.hrms.entity.transfer.HrInternalMovment;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
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
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author INSA
 */
@Named(value = "internalMovementCon")
@ViewScoped
public class InternalMovementCon implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrInternalMovment hrEmpInternalHistory;
    @Inject
            HrEmpInternalHistory assignmentHistory;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrEmployees hrEmp;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrDepartments department;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrJobTypes hrJobs;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            HrLuGrades luGrades;
    @Inject
            HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
            HrLuSalarySteps hrLuSalarySteps;
    @Inject
            HrSalaryScales hrSalaryScales;
    @Inject
            HrPayrollPlPg hrPayrollPlPg;
    @Inject
            HrPayrollPlPgDept hrPayrollPlPgDept;
    @Inject
            FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
            SessionBean sessionBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB declaration">
    @EJB
            InternalMovementBeanLocal internalMovementBeanLocal;
    @EJB
            HrDepartmentsFacade hrDepartmentsFacade;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
            FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @Inject
            HrPayrollPgPlDeptBeanLocal hrPayrollPgPlDeptBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    HrEmployees empId;
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    boolean dsbButton = false;
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Employee";
    private String icone = "ui-icon-plus";
    String payLocation;
    String payGroup;
    String costCenter;
    String system;
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    DataModel<HrInternalMovment> recreateRequestsListDataModel;
    DataModel<HrEmpInternalHistory> historyDataModel;
    private List<HrInternalMovment> selectedRequest;
    private List<HrEmpInternalHistory> selectedAssignment;
    String employee;
    SelectItem[] listOfJob;
    int fromDeptId;
    String fromDeptName = null;
    int fromJobId;
    String fromJobTitle = null;
    String fromGrade = null;
    String fromSalaryStep = null;
    double fromSalary;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    public InternalMovementCon() {
    }

    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main methods">
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
    
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrInternalMovment getHrEmpInternalHistory() {
        if (hrEmpInternalHistory == null) {
            hrEmpInternalHistory = new HrInternalMovment();
        }
        return hrEmpInternalHistory;
    }
    
    public void setHrEmpInternalHistory(HrInternalMovment hrEmpInternalHistory) {
        this.hrEmpInternalHistory = hrEmpInternalHistory;
    }
    
    public HrEmpInternalHistory getAssignmentHistory() {
        return assignmentHistory;
    }
    
    public void setAssignmentHistory(HrEmpInternalHistory assignmentHistory) {
        this.assignmentHistory = assignmentHistory;
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
    
    public HrEmployees getHrEmp() {
        if (hrEmp == null) {
            hrEmp = new HrEmployees();
        }
        return hrEmp;
    }
    
    public void setHrEmp(HrEmployees hrEmp) {
        this.hrEmp = hrEmp;
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
    
    public HrDepartments getDepartment() {
        if (department == null) {
            department = new HrDepartments();
        }
        return department;
    }
    
    public void setDepartment(HrDepartments department) {
        this.department = department;
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
    
    public HrJobTypes getHrJobs() {
        if (hrJobs == null) {
            hrJobs = new HrJobTypes();
        }
        return hrJobs;
    }
    
    public void setHrJobs(HrJobTypes hrJobs) {
        this.hrJobs = hrJobs;
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
    
    public HrLuSalarySteps getHrLuSalarySteps() {
        if (hrLuSalarySteps == null) {
            hrLuSalarySteps = new HrLuSalarySteps();
        }
        return hrLuSalarySteps;
    }
    
    public void setHrLuSalarySteps(HrLuSalarySteps hrLuSalarySteps) {
        this.hrLuSalarySteps = hrLuSalarySteps;
    }
    
    public HrSalaryScales getHrSalaryScales() {
        if (hrSalaryScales == null) {
            hrSalaryScales = new HrSalaryScales();
        }
        return hrSalaryScales;
    }
    
    public void setHrSalaryScales(HrSalaryScales hrSalaryScales) {
        this.hrSalaryScales = hrSalaryScales;
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
    
    public HrLuGrades getLuGrades() {
        if (luGrades == null) {
            luGrades = new HrLuGrades();
        }
        return luGrades;
    }
    
    public DataModel<HrInternalMovment> getRecreateRequestsListDataModel() {
        if (recreateRequestsListDataModel == null) {
            recreateRequestsListDataModel = new ListDataModel(new ArrayList<>(internalMovementBeanLocal.findAllRequest()));
        }
        return recreateRequestsListDataModel;
    }
    
    public void setRecreateRequestsListDataModel(DataModel<HrInternalMovment> recreateRequestsListDataModel) {
        this.recreateRequestsListDataModel = recreateRequestsListDataModel;
    }
    
    public DataModel<HrEmpInternalHistory> getHistoryDataModel() {
        if (historyDataModel == null) {
            historyDataModel = new ListDataModel(new ArrayList<>(internalMovementBeanLocal.findAllAssignment()));
        }
        return historyDataModel;
    }
    
    public void setHistoryDataModel(DataModel<HrEmpInternalHistory> historyDataModel) {
        this.historyDataModel = historyDataModel;
    }
    
    public List<HrInternalMovment> getSelectedRequest() {
        if (selectedRequest == null) {
            selectedRequest = new ArrayList<>();
        }
        return selectedRequest;
    }
    
    public void setSelectedRequest(List<HrInternalMovment> selectedRequest) {
        this.selectedRequest = selectedRequest;
    }
    
    public void setLuGrades(HrLuGrades luGrades) {
        this.luGrades = luGrades;
    }
    
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }
    
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
    
    public boolean isDsbButton() {
        return dsbButton;
    }
    
    public void setDsbButton(boolean dsbButton) {
        this.dsbButton = dsbButton;
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
    
    public String getPayLocation() {
        return payLocation;
    }
    
    public void setPayLocation(String payLocation) {
        this.payLocation = payLocation;
    }
    
    public String getPayGroup() {
        return payGroup;
    }
    
    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }
    
    public String getCostCenter() {
        return costCenter;
    }
    
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
    
    public String getSystem() {
        return system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }
    
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }
    
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }
    
    public boolean isChooseCreatePage() {
        return chooseCreatePage;
    }
    
    public void setChooseCreatePage(boolean chooseCreatePage) {
        this.chooseCreatePage = chooseCreatePage;
    }
    
    public boolean isChooseMainPage() {
        return chooseMainPage;
    }
    
    public void setChooseMainPage(boolean chooseMainPage) {
        this.chooseMainPage = chooseMainPage;
    }
    
    public TreeNode getRoot() {
        return root;
    }
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public TreeNode getRoot2() {
        return root2;
    }
    
    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    public String getEmployee() {
        return employee;
    }
    
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    
    public SelectItem[] getListOfJob() {
        return listOfJob;
    }
    
    public void setListOfJob(SelectItem[] listOfJob) {
        this.listOfJob = listOfJob;
    }
    
    List<HrJobTypes> jobTypes;
    
    public List<HrJobTypes> getJobTypes() {
        return jobTypes;
    }
    
    public void setJobTypes(List<HrJobTypes> jobTypes) {
        this.jobTypes = jobTypes;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Save Update">
    public void insertToHistory() {
        assignmentHistory = new HrEmpInternalHistory();
        String assignmentId = String.valueOf(hrEmpInternalHistory.getId());
        String currentDate = StringDateManipulation.toDayInEc();
        assignmentHistory.setProcessType("Assignment");
        assignmentHistory.setEmpId(empId);
        assignmentHistory.setOldDepId(fromDeptId);
        assignmentHistory.setOldDepName(fromDeptName);
        assignmentHistory.setOldJobId(fromJobId);
        assignmentHistory.setOldJobName(fromJobTitle);
        assignmentHistory.setOldGrade(fromGrade);
        assignmentHistory.setOldSalaryStep(fromSalaryStep);
        assignmentHistory.setOldSalary(fromSalary);
        assignmentHistory.setProcessId(assignmentId);
        assignmentHistory.setProcessDate(currentDate);
        internalMovementBeanLocal.saveOrUpdate(assignmentHistory);
    }
    
    public void editEmployee() {
        hrEmployees.setDeptId(hrDepartments);
        hrEmployees.setFirstName(hrEmployees.getFirstName());
        System.out.println("fffname" + hrEmployees.getFirstName());
        hrEmployees.setEmpId(hrEmployees.getEmpId());
        System.out.println("emmp" + hrEmployees.getEmpId());
        hrEmployeeBean.edit(hrEmployees);
    }
    
    public void saveInternalMovement() {
        boolean checkReferenceNo = internalMovementBeanLocal.checkReferenceNo(hrEmpInternalHistory);
        if (hrEmpInternalHistory.getDepId() == null) {
            JsfUtil.addFatalMessage("Select employee department");
        } else {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBeanLocal.getUserName(), "saveInternalMovement", dataset)) {
                    
                    if (updateStatus == 0 && checkReferenceNo == false) {
                        hrEmployees.setDeptId(hrDepartments);
                        hrEmployeeBean.edit(hrEmployees);
                        hrEmpInternalHistory.setStatus(0);
                        internalMovementBeanLocal.save(hrEmpInternalHistory);
                        insertToHistory();
                        clearPage();
                        JsfUtil.addSuccessMessage("Successfully saved for the final state");
                    } else if (updateStatus == 0 && checkReferenceNo == true) {
                        JsfUtil.addFatalMessage("Reference No. can't be dublicate. Try again!");
                    } else {
                        internalMovementBeanLocal.edit(hrEmpInternalHistory);
                        insertToHistory();
                        //  editEmployee();
                        clearPage();
                        JsfUtil.addSuccessMessage("Successfully modified");
                    }
                    
                } else {
                    JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                    EventEntry eventEntry = new EventEntry();
                    eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                    eventEntry.setUserId(sessionBeanLocal.getUserId());
                    QName qualifiedName = new QName("", "project");
                    JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                    eventEntry.setUserLogin(test);
                    security.addEventLog(eventEntry, dataset);
                }
                
            } catch (Exception ex) {
                updateStatus = 0;
                saveorUpdateBundle = "Save";
            }
        }
    }
    
    public void saveInternalMovement1() {
        String transferId = String.valueOf(hrEmpInternalHistory.getId());
        String currentDate = StringDateManipulation.toDayInEc();
        String oldDepName = null;
        try {
            if (fromDeptName != null) {
                oldDepName = fromDeptName;
            }
//            if (internalMovementBeanLocal.saveTo(transferId, empId, currentDate, fromDeptId, fromDeptName, fromJobId, fromJobTitle, fromGrade, fromSalary,
//                    oldDepName, currentDate, 1) == 1) {
//                JsfUtil.addSuccessMessage("The request has been successfully approved for the final state.");
//                clearPage();
//            } else {
//            JsfUtil.addFatalMessage("Error occured while approving the request for the final state.");
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void clearPage() {
        hrEmpInternalHistory = null;
        hrEmployees = null;
        hrDepartments = null;
        department = null;
        hrJobTypes = null;
        hrJobs = null;
        hrLuGrades = null;
        luGrades = null;
        hrLuSalarySteps = null;
        hrSalaryScaleRanges = null;
        hrSalaryScales = null;
        system = null;
        costCenter = null;
        payLocation = null;
        payGroup = null;
    }
    
    public void resetInternalMovement() {
        hrEmpInternalHistory = new HrInternalMovment();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        department = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrJobs = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
        luGrades = new HrLuGrades();;
        hrSalaryScales = new HrSalaryScales();
        hrSalaryScaleRanges = new HrSalaryScaleRanges();
        system = null;
        costCenter = null;
        payLocation = null;
        payGroup = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        dsbButton = false;
    }
    // </editor-fold>
    
    public List<SelectItem> internalMovementTypes() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.NEW_PLACEMENT), "New Placement"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.PROMOTION), "Promotion"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.TRANSFER), "Transfer"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.ACTING), "Acting"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.INCREMENT), "Salary Scale Increment"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.JOB_ROTATION), "Job Rotation"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.ASSIGNMENT), "Assignment (BPR)"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.SALARY_SCALE_CHANGE), "Change on Salary Scale"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.DISCIPLINARY_CASE), "Disciplinary Cases"));
        selectItems.add(new SelectItem(String.valueOf(hrEmpInternalHistory.PROBATION), "Probation Period"));
        return selectItems;
    }
    
    public void internaMovementInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Internal Movement";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Request";
            setIcone("ui-icon-plus");
        }
    }
    
    public ArrayList<HrEmpInternalHistory> findAssignmentHisByID(String emp) {
        ArrayList<HrEmpInternalHistory> assignmentEmp = null;
        hrEmployees.setEmpId(emp);
        assignmentEmp = internalMovementBeanLocal.findAssignmentByID(hrEmployees);
        return assignmentEmp;
    }
    
    public void getAssignmentHisByID(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            assignmentHistory = internalMovementBeanLocal.getAssignmentByID(hrEmployees);
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while geting data");
        }
    }
    
    public ArrayList<HrEmpInternalHistory> findAssignmentHisByName(String emp) {
        ArrayList<HrEmpInternalHistory> assignmentEmp = null;
        hrEmployees.setFirstName(emp);
        assignmentEmp = internalMovementBeanLocal.findAssignmentByName(hrEmployees);
        return assignmentEmp;
    }
    
    public void getAssignmentHisByName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            assignmentHistory = internalMovementBeanLocal.getAssignmentByName(hrEmployees);
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while geting data");
        }
    }
    
    public void searchAssignmentHis() {
        try {
            if (hrEmp.getEmpId().isEmpty() && hrEmp.getFirstName().isEmpty()) {
                selectedAssignment = internalMovementBeanLocal.findAllAssignment();
                historyDataModel = new ListDataModel(selectedAssignment);
            } else if (hrEmp.getEmpId() != null && hrEmp.getFirstName().isEmpty()) {
                selectedAssignment = internalMovementBeanLocal.findAssignmentByID(hrEmp);
                historyDataModel = new ListDataModel(selectedAssignment);
            } else if (hrEmp.getEmpId().isEmpty() && hrEmp.getFirstName() != null) {
                selectedAssignment = internalMovementBeanLocal.findAssignmentByName(hrEmp);
                historyDataModel = new ListDataModel(selectedAssignment);
            } else if (hrEmp.getEmpId() != null && hrEmp.getFirstName() != null) {
                selectedAssignment = internalMovementBeanLocal.findByTwo(hrEmp);
                historyDataModel = new ListDataModel(selectedAssignment);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while geting data");
        }
    }
    
    public void populateInternalMovement(SelectEvent event) {
        hrEmpInternalHistory = (HrInternalMovment) event.getObject();
        hrEmployees = hrEmpInternalHistory.getEmpId();
        hrDepartments = hrEmpInternalHistory.getDepId();
        department = hrEmployees.getDeptId();
        jobTypes = internalMovementBeanLocal.listOfJobType(hrDepartments.getDepId());
        hrJobTypes = hrEmpInternalHistory.getJobId();
        hrJobs = hrEmployees.getJobId();
        luGrades = hrEmpInternalHistory.getGradeId().getGradeId();
        hrSalaryScaleRanges = hrEmpInternalHistory.getGradeId();
        hrSalaryScales = hrEmpInternalHistory.getSalaryId();
        hrLuSalarySteps = hrEmpInternalHistory.getSalaryStepId();
//        hrLuGrades = hrEmployees.getGradeId().getGradeId();
        hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrDepartments.getDepId());
        if (hrPayrollPlPgDept != null) {
            setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
            setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
        } else {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(hrDepartments.getDepId());
        if (fmsCostcSystemJunction != null) {
            if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
                setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
            } else {
                setCostCenter("");
            }
            if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
                setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
            } else {
                setSystem("");
            }
        } else {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        disableBtnCreate = false;
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Internal Movement";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        dsbButton = true;
    }
    
    public void getRequesterInfo(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            hrEmpInternalHistory = internalMovementBeanLocal.findRequesterById(hrEmployees);
            department = hrEmployees.getDeptId();
            jobTypes = internalMovementBeanLocal.listOfJobType(hrDepartments.getDepId());
            hrJobs = hrEmployees.getJobId();
            hrLuGrades.setGrade(hrSalaryScaleRanges.getGradeId().getGrade());
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            saveorUpdateBundle = "Update";
            disableBtnCreate = false;
            if (hrEmpInternalHistory.getEmpId().getFirstName().equalsIgnoreCase(hrEmployees.toString())) {
                if (createOrSearchBundle.equals("New")) {
                    chooseCreatePage = true;
                    chooseMainPage = false;
                    createOrSearchBundle = "Search";
                    headerTitle = "Acting Assignment Request";
                    setIcone("ui-icon-search");
                } else {
                    chooseCreatePage = false;
                    chooseMainPage = true;
                    createOrSearchBundle = "New";
                    headerTitle = "Search Employee";
                    setIcone("ui-icon-plus");
                }
            }
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = internalMovementBeanLocal.getSelectDepartement(key);
        hrDepartments.setDepName(hrDepartments.getDepName());
        hrEmpInternalHistory.setDepId(hrDepartments);
        jobTypes = internalMovementBeanLocal.listOfJobType(hrDepartments.getDepId());
        hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrDepartments.getDepId());
        if (hrPayrollPlPgDept != null) {
            setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
            setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
        } else {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(hrDepartments.getDepId());
        if (fmsCostcSystemJunction != null) {
            if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
                setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
            } else {
                setCostCenter("");
            }
            if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
                setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
            } else {
                setSystem("");
            }
        } else {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
    }
    
    public List<SelectItem> getInternalMovementTypes() {
        return internalMovementTypes();
    }
    
    public ArrayList<HrEmployees> searchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(hrEmployees);
        return employees;
    }
    
    public void getByEmpName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            hrEmpInternalHistory.setEmpId(hrEmployees);
            department = hrEmployees.getDeptId();
            hrJobs = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            empId = hrEmployees;
            fromDeptId = hrEmployees.getDeptId().getDepId();
            fromDeptName = String.valueOf(hrEmployees.getDeptId().getDepName());
            fromJobId = hrEmployees.getJobId().getId();
            fromJobTitle = hrEmployees.getJobId().getJobTitle();
            fromGrade = hrEmployees.getGradeId().getGradeId().getGrade();
            fromSalaryStep = hrEmployees.getSalaryStep().getSalaryStep().toString();
            fromSalary = hrEmployees.getBasicSalary();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getSalarySteps(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuSalarySteps.setSalaryStep(Integer.valueOf(event.getNewValue().toString()));
            hrLuSalarySteps = internalMovementBeanLocal.findSalaryStep(hrLuSalarySteps);
            hrEmpInternalHistory.setSalaryStepId(hrLuSalarySteps);
        }
    }
    
    public SelectItem[] getListOfSalaryStep() {
        return JsfUtil.getSelectItems(internalMovementBeanLocal.findAllHrLuSalarySteps(), true);
    }
    
    public void jobChangedListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrJobTypes.setId(Integer.valueOf(event.getNewValue().toString()));
            hrJobTypes = internalMovementBeanLocal.findByName(hrJobTypes.getId());
            hrEmpInternalHistory.setJobId(hrJobTypes);
            hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
            hrEmpInternalHistory.setGradeId(hrSalaryScaleRanges);
            luGrades = hrEmpInternalHistory.getGradeId().getGradeId();
        }
    }
    
    public void getInternalMovementInfo(ValueChangeEvent event) {
        String reqest[] = event.getNewValue().toString().split("--");
        updateStatus = 1;
        int id = Integer.parseInt(reqest[0]);
        hrEmpInternalHistory = internalMovementBeanLocal.getInternalMovementInfo(id);
        saveorUpdateBundle = "Update";
        disableBtnCreate = false;
        hrEmployees = hrEmpInternalHistory.getEmpId();
        if (hrEmpInternalHistory.getEmpId().getFirstName().equalsIgnoreCase(hrEmployees.toString()) && (createOrSearchBundle.equals("New"))) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Internal Movemnet";
            setIcone("ui-icon-search");
        } else {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Employee";
            setIcone("ui-icon-plus");
        }
        hrDepartments = hrEmpInternalHistory.getDepId();
        department = hrEmployees.getDeptId();
        jobTypes = internalMovementBeanLocal.listOfJobType(hrDepartments.getDepId());
        hrJobTypes = hrEmpInternalHistory.getJobId();
        hrJobs = hrEmployees.getJobId();
        luGrades = hrEmpInternalHistory.getGradeId().getGradeId();
        hrSalaryScaleRanges = hrEmpInternalHistory.getGradeId();
        hrSalaryScales = hrEmpInternalHistory.getSalaryId();
        hrLuSalarySteps = hrEmpInternalHistory.getSalaryStepId();
        hrLuGrades.setGrade(hrSalaryScaleRanges.getGradeId().getGrade());
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
    }
    
    public void salaryScaleChanged(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuSalarySteps.setSalaryStep(Integer.valueOf(event.getNewValue().toString()));
            hrLuSalarySteps = internalMovementBeanLocal.searchHrLuSalaryStepsInfo(hrLuSalarySteps);
            hrSalaryScales.setSalaryRangeId(hrSalaryScaleRanges);
            hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
            hrSalaryScales = internalMovementBeanLocal.checkStepIdRep(hrSalaryScaleRanges, hrLuSalarySteps);
            hrEmpInternalHistory.setSalaryStepId(hrLuSalarySteps);
            hrEmpInternalHistory.setSalaryId(hrSalaryScales);
        }
    }
    private boolean basicSalary;
    
    public void salaryStep_vlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String salStep = event.getNewValue().toString();
            if (salStep.equalsIgnoreCase("0")) {
                hrEmployees.setBasicSalary(Double.parseDouble(salStep));
                hrEmployees.setSalaryStep(null);
                basicSalary = false;
            } else if (salStep.equalsIgnoreCase("-1")) {
                hrEmployees.setBasicSalary(hrSalaryScaleRanges.getMaxSalary());
                hrEmployees.setSalaryStep(null);
                basicSalary = true;
            } else if (salStep.equalsIgnoreCase("-2")) {
                hrEmployees.setBasicSalary(hrSalaryScaleRanges.getMinSalary());
                hrEmployees.setSalaryStep(null);
                basicSalary = true;
            } else {
                basicSalary = true;
                hrLuSalarySteps.setSalaryStep(Integer.parseInt(event.getNewValue().toString()));
                hrLuSalarySteps = hrEmployeeBean.searchHrLuSalaryStepsInfo(hrLuSalarySteps);
                hrSalaryScales.setSalaryRangeId(hrSalaryScaleRanges);
                hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
                hrSalaryScales = hrEmployeeBean.checkStepIdRep(hrSalaryScaleRanges, hrLuSalarySteps);
                hrEmpInternalHistory.setSalaryStepId(hrLuSalarySteps);
                hrEmpInternalHistory.setSalaryId(hrSalaryScales);
            }
        } else {
            JsfUtil.addErrorMessage("Please Select Salary Step");
        }
    }
    
    public void salaryScale(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuSalarySteps.setSalaryStep(Integer.valueOf(event.getNewValue().toString()));
            hrLuSalarySteps = hrEmployeeBean.searchHrLuSalaryStepsInfo(hrLuSalarySteps);
            hrSalaryScales.setSalaryRangeId(hrSalaryScaleRanges);
            hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
            hrSalaryScales = hrEmployeeBean.checkStepIdRep(hrSalaryScaleRanges, hrLuSalarySteps);
            hrEmployees.setSalaryStep(hrLuSalarySteps);
        }
    }
    
    public SelectItem[] getSalarySetpsByJobTitle() {
        if (hrJobTypes.getJobTitle() != null) {
            hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
            SelectItem[] steps = JsfUtil.getSelectItems(hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select job title ---");
            return items;
        }
    }
    
    public ArrayList<HrSalaryScales> getSalarySetps() {
        ArrayList<HrSalaryScales> salarySteps = null;
        if (hrDepartments != null && hrJobTypes != null) {
            salarySteps = new ArrayList<>();
            if (hrJobTypes.getJobGradeId() != null) {
                hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
                salarySteps = hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges);
            }
        }
        return salarySteps;
    }
    
    public List<String> getAllRequests() {
        return internalMovementBeanLocal.searchByStatus(0);
    }
    
    public void searchAllReqeust() {
        if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = internalMovementBeanLocal.findAllRequest();
            recreateRequestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = internalMovementBeanLocal.findByEmpId(hrEmployees);
            recreateRequestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName() != null) {
            selectedRequest = internalMovementBeanLocal.findByEmpName(hrEmployees);
            recreateRequestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName() != null) {
            selectedRequest = internalMovementBeanLocal.findByAll(hrEmployees);
            recreateRequestsListDataModel = new ListDataModel(selectedRequest);
        }
    }
//</editor-fold>
}
