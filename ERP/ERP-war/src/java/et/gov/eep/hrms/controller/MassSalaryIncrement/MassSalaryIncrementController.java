/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.MassSalaryIncrement;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil; 
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.massSalaryIncrement.MassSalaryIncrementBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrementDetails;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrements;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
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
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

@Named("massSalaryIncrementControllers")
@ViewScoped
public class MassSalaryIncrementController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Enity Enjection">
    @Inject
            HrSalaryIncrementDetails hrSalaryIncrementDetails;
    @Inject
            HrSalaryIncrements hrSalaryIncrements;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            HrLuSalarySteps hrLuSalarySteps;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean SessionBean;
    @Inject
            WorkFlow workFlow ;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
     @EJB
             MassSalaryIncrementBeanLocal massSalaryIncrementBeanLocal;
     @EJB
     private HrEmployeeBeanLocal hrEmployeeBean;
     @EJB
             HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
     @EJB
             HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
     @EJB
             HrDepartmentsFacade hrDepartmentsFacade;
     @EJB
             WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    private String icone = "ui-icon-document";
    private int loggedInUserId;
    private int selectedRowIndex = -1;
    private boolean renderPnlManPage = true;
    private boolean pnlrWfIcon = false;
    private boolean PerpareOnlyRadio = false;
    private boolean savebtn = false;
    private boolean approvebtn = false;
    private boolean selectIncremetStep = false;
    private Integer status;
    private int requestNotificationCounter = 0;
    private boolean pnlnotification=false;
    private String selectedValue = HrSalaryIncrementDetails.ALLEMPLOYEES;
    private String allEmployees = HrSalaryIncrementDetails.ALLEMPLOYEES;
    private String included = HrSalaryIncrementDetails.INCLUDED;
    private String nonIncluded = HrSalaryIncrementDetails.NON_INCLUDED;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List&DataModel declaration">
    List<HrEmployees> allEmployeesList = new ArrayList<>();
    List<HrJobTypes> allJobtypes = new ArrayList<>();
    List<HrSalaryIncrements> salaryIncrementRequests = new ArrayList<>();
    List<HrSalaryIncrements> salaryIncrementRequestList = new ArrayList<>();
    List<HrLuGrades> allJobGrades = new ArrayList<>();
    List<HrLuSalarySteps> allSalarySteps = new ArrayList<>();
    List<HrLuSalarySteps> incrementNumbers = new ArrayList<>();
    List<MassSalaryIncrementTableList> empList = new ArrayList<>();
    List<WfHrProcessed> WorkflowList;
    List<HrSalaryIncrementDetails> requesstlist = new ArrayList<>();
    DataModel<MassSalaryIncrementTableList> empListDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="postConstruct">
    public MassSalaryIncrementController() {
    }
    
    @PostConstruct
    public void init() {
        loadTree();
        setAllJobGrades(massSalaryIncrementBeanLocal.readAllJobGrades());
        setAllSalarySteps(massSalaryIncrementBeanLocal.readAllSalarySteps());
        setIncrementNumbers(massSalaryIncrementBeanLocal.readAllSalarySteps());
        setSalaryIncrementRequestList(massSalaryIncrementBeanLocal.readSalaryIncrementRequests());
        setFullName(SessionBean.getUserName());
        checkUserStatus();
        //hrSalaryIncrements.setPreparedBy(loggedInUserId);
        hrSalaryIncrements.setPreparedOn(currentdate());
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
    
    //<editor-fold defaultstate="collapsed" desc="inner class">
    public class MassSalaryIncrementTableList implements Serializable {

        private Integer id; //HrSalaryIncrementDetails or HR_SALARY_INCREMENT_DETAILS table id
        private Integer salIncId; //Foreign key of HrSalaryIncrementDetails entity class or HR_SALARY_INCREMENT_DETAILS table         
        private Integer idEmp; //primary key of an employee table which is equivalent to empId
        private String empId;
        private String empName;
        private Integer deptId;
        private String deptName;
        private Integer jobId;
        private String jobTitle;
        private Integer gradeId;
        private String grade;
        private Integer prevSalaryStep;
        private Integer prevSalaryStepId;
        private Integer newSalaryStep;
        private Double prevSalary;
        private Double newSalary;
        private Integer incrementBy;
        private Integer status;
        private boolean approved;
        private String preparedBy;
        private String preparedOn;
        private String referenceNo;
        private String remark;

        public MassSalaryIncrementTableList() {
        }

        public MassSalaryIncrementTableList(Integer id, Integer idEmp, String empId, String empName, String deptName,
                String jobTitle, String grade, Integer gradeId, Integer prevSalaryStep, Integer prevSalaryStepId,
                Double prevSalary, Integer incrementBy, Integer newSalaryStep, Double newSalary, Integer status) {
            this.id = id;
            this.idEmp = idEmp;
            this.empId = empId;
            this.empName = empName;
            this.deptName = deptName;
            this.jobTitle = jobTitle;
            this.grade = grade;
            this.gradeId = gradeId;
            this.prevSalaryStep = prevSalaryStep;
            this.prevSalaryStepId = prevSalaryStepId;
//            this.incrementList = assignIncrementNumbers(prevSalaryStep);
            this.prevSalary = prevSalary;
            this.incrementBy = incrementBy;
            this.newSalaryStep = newSalaryStep;
            this.newSalary = newSalary;
            this.status = status;
            this.approved = true;
        }

        //<editor-fold defaultstate="collapsed" desc="getters & setters">
        public Integer getIdEmp() {
            return idEmp;
        }

        public void setIdEmp(Integer idEmp) {
            this.idEmp = idEmp;
        }

        public Integer getSalIncId() {
            return salIncId;
        }

        public void setSalIncId(Integer salIncId) {
            this.salIncId = salIncId;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public Integer getDeptId() {
            return deptId;
        }

        public void setDeptId(Integer deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getJobId() {
            return jobId;
        }

        public void setJobId(Integer jobId) {
            this.jobId = jobId;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public Integer getGradeId() {
            return gradeId;
        }

        public void setGradeId(Integer gradeId) {
            this.gradeId = gradeId;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public Integer getPrevSalaryStepId() {
            return prevSalaryStepId;
        }

        public void setPrevSalaryStepId(Integer prevSalaryStepId) {
            this.prevSalaryStepId = prevSalaryStepId;
        }

        public Integer getPrevSalaryStep() {
            return prevSalaryStep;
        }

        public void setPrevSalaryStep(Integer prevSalaryStep) {
            this.prevSalaryStep = prevSalaryStep;
        }

        public Integer getNewSalaryStep() {
            return newSalaryStep;
        }

        public void setNewSalaryStep(Integer newSalaryStep) {
            this.newSalaryStep = newSalaryStep;
        }

        public Double getPrevSalary() {
            return prevSalary;
        }

        public void setPrevSalary(Double prevSalary) {
            this.prevSalary = prevSalary;
        }

        public Double getNewSalary() {
            return newSalary;
        }

        public void setNewSalary(Double newSalary) {
            this.newSalary = newSalary;
        }

        public Integer getIncrementBy() {
            return incrementBy;
        }

        public void setIncrementBy(Integer incrementBy) {
            this.incrementBy = incrementBy;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public boolean isApproved() {
            return approved;
        }

        public void setApproved(boolean approved) {
            this.approved = approved;
        }

        public String getPreparedBy() {
            return preparedBy;
        }

        public void setPreparedBy(String preparedBy) {
            this.preparedBy = preparedBy;
        }

        public String getPreparedOn() {
            return preparedOn;
        }

        public void setPreparedOn(String preparedOn) {
            this.preparedOn = preparedOn;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
//</editor-fold>

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public int getRequestNotificationCounter() {
        salaryIncrementRequests = massSalaryIncrementBeanLocal.findAllrequests();
        requestNotificationCounter = salaryIncrementRequests.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public boolean isPnlnotification() {
        return pnlnotification;
    }

    public void setPnlnotification(boolean pnlnotification) {
        this.pnlnotification = pnlnotification;
    }
    

    public List<HrSalaryIncrementDetails> getRequesstlist() {
        return requesstlist;
    }

    public void setRequesstlist(List<HrSalaryIncrementDetails> requesstlist) {
        this.requesstlist = requesstlist;
    }
//    

    public String getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(String allEmployees) {
        this.allEmployees = allEmployees;
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public String getNonIncluded() {
        return nonIncluded;
    }

    public void setNonIncluded(String nonIncluded) {
        this.nonIncluded = nonIncluded;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public HrSalaryIncrementDetails getHrSalaryIncrementDetails() {
        return hrSalaryIncrementDetails;
    }

    public void setHrSalaryIncrementDetails(HrSalaryIncrementDetails hrSalaryIncrementDetails) {
        this.hrSalaryIncrementDetails = hrSalaryIncrementDetails;
    }

    public HrSalaryIncrements getHrSalaryIncrements() {
        return hrSalaryIncrements;
    }

    public void setHrSalaryIncrements(HrSalaryIncrements hrSalaryIncrements) {
        this.hrSalaryIncrements = hrSalaryIncrements;
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public MassSalaryIncrementBeanLocal getMassSalaryIncrementBeanLocal() {
        return massSalaryIncrementBeanLocal;
    }

    public void setMassSalaryIncrementBeanLocal(MassSalaryIncrementBeanLocal massSalaryIncrementBeanLocal) {
        this.massSalaryIncrementBeanLocal = massSalaryIncrementBeanLocal;
    }

    public HrEmployeeBeanLocal getHrEmployeeBean() {
        return hrEmployeeBean;
    }

    public void setHrEmployeeBean(HrEmployeeBeanLocal hrEmployeeBean) {
        this.hrEmployeeBean = hrEmployeeBean;
    }

    public HrDepartmentsBeanLocal getHrDepartmentsBeanLocal() {
        return hrDepartmentsBeanLocal;
    }

    public void setHrDepartmentsBeanLocal(HrDepartmentsBeanLocal hrDepartmentsBeanLocal) {
        this.hrDepartmentsBeanLocal = hrDepartmentsBeanLocal;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public List<HrEmployees> getAllEmployeesList() {
        return allEmployeesList;
    }

    public void setAllEmployeesList(List<HrEmployees> allEmployeesList) {
        this.allEmployeesList = allEmployeesList;
    }

    public HrRecruitmentRequestsBeanLocal getHrRecruitmentRequestsBeanLocal() {
        return hrRecruitmentRequestsBeanLocal;
    }

    public void setHrRecruitmentRequestsBeanLocal(HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal) {
        this.hrRecruitmentRequestsBeanLocal = hrRecruitmentRequestsBeanLocal;
    }

    public List<HrJobTypes> getAllJobtypes() {
        return allJobtypes;
    }

    public void setAllJobtypes(List<HrJobTypes> allJobtypes) {
        this.allJobtypes = allJobtypes;
    }

    public HrDepartmentsFacade getHrDepartmentsFacade() {
        return hrDepartmentsFacade;
    }

    public void setHrDepartmentsFacade(HrDepartmentsFacade hrDepartmentsFacade) {
        this.hrDepartmentsFacade = hrDepartmentsFacade;
    }

    public List<HrSalaryIncrements> getSalaryIncrementRequests() {
        return salaryIncrementRequests;
    }

    public void setSalaryIncrementRequests(List<HrSalaryIncrements> salaryIncrementRequests) {
        this.salaryIncrementRequests = salaryIncrementRequests;
    }

    public List<HrSalaryIncrements> getSalaryIncrementRequestList() {
        return salaryIncrementRequestList;
    }

    public void setSalaryIncrementRequestList(List<HrSalaryIncrements> salaryIncrementRequestList) {
        this.salaryIncrementRequestList = salaryIncrementRequestList;
    }

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrLuSalarySteps getHrLuSalarySteps() {
        return hrLuSalarySteps;
    }

    public void setHrLuSalarySteps(HrLuSalarySteps hrLuSalarySteps) {
        this.hrLuSalarySteps = hrLuSalarySteps;
    }

    public List<HrLuGrades> getAllJobGrades() {
        return allJobGrades;
    }

    public void setAllJobGrades(List<HrLuGrades> allJobGrades) {
        this.allJobGrades = allJobGrades;
    }

    public List<HrLuSalarySteps> getAllSalarySteps() {
        return allSalarySteps;
    }

    public void setAllSalarySteps(List<HrLuSalarySteps> allSalarySteps) {
        this.allSalarySteps = allSalarySteps;
    }

    public List<HrLuSalarySteps> getIncrementNumbers() {
        return incrementNumbers;
    }

    public void setIncrementNumbers(List<HrLuSalarySteps> incrementNumbers) {
        this.incrementNumbers = incrementNumbers;
    }

    public List<MassSalaryIncrementTableList> getEmpList() {
        return empList;
    }

    public void setEmpList(List<MassSalaryIncrementTableList> empList) {
        this.empList = empList;
    }

    public DataModel<MassSalaryIncrementTableList> getEmpListDataModel() {
        return empListDataModel;
    }

    public void setEmpListDataModel(DataModel<MassSalaryIncrementTableList> empListDataModel) {
        this.empListDataModel = empListDataModel;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public List<WfHrProcessed> getWorkflowList() {
        return WorkflowList;
    }

    public void setWorkflowList(List<WfHrProcessed> WorkflowList) {
        this.WorkflowList = WorkflowList;
    }

    public DataModel<WfHrProcessed> getWorkFlowDataModel() {
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public boolean isPnlrWfIcon() {
        return pnlrWfIcon;
    }

    public void setPnlrWfIcon(boolean pnlrWfIcon) {
        this.pnlrWfIcon = pnlrWfIcon;
    }

    public boolean isSavebtn() {
        return savebtn;
    }

    public void setSavebtn(boolean savebtn) {
        this.savebtn = savebtn;
    }

    public boolean isApprovebtn() {
        return approvebtn;
    }

    public void setApprovebtn(boolean approvebtn) {
        this.approvebtn = approvebtn;
    }

    public boolean isSelectIncremetStep() {
        return selectIncremetStep;
    }

    public void setSelectIncremetStep(boolean selectIncremetStep) {
        this.selectIncremetStep = selectIncremetStep;
    }

    public boolean isPerpareOnlyRadio() {
        return PerpareOnlyRadio;
    }

    public void setPerpareOnlyRadio(boolean PerpareOnlyRadio) {
        this.PerpareOnlyRadio = PerpareOnlyRadio;
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

    public static List<HrDepartments> getDepartments() {
        return departments;
    }

    public static void setDepartments(List<HrDepartments> departments) {
        MassSalaryIncrementController.departments = departments;
    }

    //</editor-fold>
    public void loadTree() {
        allDepartments = hrDepartmentsBeanLocal.findAllDepartment();
        root = new DefaultTreeNode("Root", null);
        populateNode(allDepartments, 0, root);
    }

    public void populateNode(List<HrDepartments> depts, int id, TreeNode node) {
        departments = new ArrayList<>();
        if (getAllDepartments() != null) {
            for (HrDepartments k : getAllDepartments()) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    departments.add(k);
                    populateNode(departments, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments.setDepId(deptId);
        hrDepartments = hrDepartmentsBeanLocal.findByDepartmentId(hrDepartments);
        allJobtypes = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main">
    public void checkUserStatus() {
        if (workFlow.isPrepareStatus() == true) {
            PerpareOnlyRadio = true;
            savebtn = true;
            approvebtn = false;
            selectIncremetStep = true;
            pnlnotification=false;
        } else {
            PerpareOnlyRadio = false;
            setSelectedValue(included);
            savebtn = false;
            approvebtn = true;
            selectIncremetStep = false;
             pnlnotification=true;
        }
    }
public String currentdate(){
    String[] cDate=StringDateManipulation.toDayInEc().split("-");
    String Today=cDate[2]+"/"+cDate[1]+"/"+cDate[0];
    return Today;
}
    public void recreateDataModel(List<MassSalaryIncrementTableList> empList) {
        empListDataModel = null;
        empListDataModel = new ListDataModel(new ArrayList<>(empList));
    }
 public void massSal_processMyEvent(SelectEvent event) {
        hrSalaryIncrements = (HrSalaryIncrements) event.getObject();
        hrSalaryIncrements.setId(hrSalaryIncrements.getId());
        setSelectedValue(included);
        btnLoad_action();
         WorkflowList = wfHrProcessedBeanLocal.MassSalWFList(hrSalaryIncrements.getId());
         workFlowDataModel = new ListDataModel(WorkflowList);
         pnlrWfIcon = true;
 }
    public void somIncrement_processValueChange(ValueChangeEvent vce) {
        try {

            if (vce.getNewValue() != null) {
                int incrementBy = Integer.valueOf(vce.getNewValue().toString());
                int _newSalaryStep = 0;
                for (MassSalaryIncrementTableList manager : empList) {
                    if (manager.isApproved()) {
                        _newSalaryStep = Integer.valueOf(manager.getPrevSalaryStep()) + incrementBy;
                        if (_newSalaryStep <= 18) {
                            manager.setIncrementBy(incrementBy);
                            manager.setNewSalaryStep(_newSalaryStep);
                        } else {
                            manager.setIncrementBy(18 - Integer.valueOf(manager.getPrevSalaryStep()));// we have to read salarystep id of the previous salaryStep before calling readSalary(int gradeId, stepId) 
                            manager.setNewSalaryStep(18);
                        }
                        manager.setNewSalary(massSalaryIncrementBeanLocal.readsalary(manager.getGradeId(), manager.getNewSalaryStep()));
                        double newsal = (manager.getPrevSalary() + (manager.getPrevSalary() * 5) / 100);
                        System.out.println("old sal=====" + manager.getPrevSalary());
                        System.out.println("new salary==" + newsal);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void salIncVLC(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                workFlowDataModel = null;
                hrSalaryIncrements = new HrSalaryIncrements();
                hrSalaryIncrements.setId(Integer.valueOf(event.getNewValue().toString()));
                System.out.println("sal in ID===" + hrSalaryIncrements);
                WorkflowList = wfHrProcessedBeanLocal.MassSalWFList(hrSalaryIncrements.getId());
                workFlowDataModel = new ListDataModel(WorkflowList);
                pnlrWfIcon = true;
                System.out.println("WorkflowList==" + WorkflowList);

            }
        } catch (Exception e) {
            e.printStackTrace();;

        }
    }

    public void btnLoad_action() {
        empList = new ArrayList<>();
        readEmployees();
        System.out.println("size==" + empList.size());
        MassSalaryIncrementTableList massSalaryIncrementTableList = new MassSalaryIncrementTableList();

    }

    private void readEmployees() {
        System.out.println("jobID@controller=1=" + hrJobTypes.getId());
        String depId = null;
        String jobId = null;
        String gradeId = null;
        String salaryStep = null;
        String employmentType = "Permanent";
        int salaryIncId = 0;
        int status = 0;
        int allEmp = Integer.valueOf(getSelectedValue());

        if (hrDepartments.getDepId() != null) {
            depId = hrDepartments.getDepId().toString();
        }
        if (hrJobTypes.getId() != null) {
            jobId = hrJobTypes.getId().toString();
            System.out.println("jobID@controller==" + jobId);
        }
        if (hrLuGrades.getId() != null) {
            gradeId = hrLuGrades.getId().toString();
        }
        if (hrLuSalarySteps.getId() != null) {
            salaryStep = hrLuSalarySteps.getId().toString();
        }
        if (hrSalaryIncrements.getId() != null) {
            salaryIncId = Integer.valueOf(hrSalaryIncrements.getId().toString());
        }

        List<Object[]> empListResult = massSalaryIncrementBeanLocal.readEmployees(depId, jobId, gradeId,
                salaryStep, employmentType, salaryIncId, status, allEmp);

        if (!empListResult.isEmpty() && empListResult.size() > 0) {
            for (Object[] emp : empListResult) {
                MassSalaryIncrementTableList addToTable = new MassSalaryIncrementTableList(
                        Integer.valueOf(emp[10].toString()), //salIncDetailId
                        Integer.valueOf(emp[0].toString()), //a primary key of employee table which is equivalent to empId
                        String.valueOf(emp[1]), //empId
                        String.valueOf(emp[2]), //empName
                        String.valueOf(emp[3]), //deptName
                        String.valueOf(emp[4]), //jobTitle
                        String.valueOf(emp[5]), //grade
                        Integer.valueOf(emp[6].toString()), //gradeId                       
                        Integer.valueOf(emp[7].toString()), //prevSalaryStep
                        emp[8] != null ? Integer.valueOf(emp[8].toString()) : null, //prevSalaryStepId
                        emp[9] != null ? Double.parseDouble(emp[9].toString()) : null, //prevSalary 
                        emp[11] != null ? Integer.valueOf(emp[11].toString()) : null, //incrementBy
                        emp[12] != null ? Integer.valueOf(emp[12].toString()) : null, //newSalaryStep
                        emp[13] != null ? Double.parseDouble(emp[13].toString()) : null, //newSalary
                        Integer.valueOf(emp[14].toString())); //status
                empList.add(addToTable);
            }
        }
        recreateDataModel(empList);
    }

    private HtmlSelectBooleanCheckbox ckbAll = new HtmlSelectBooleanCheckbox();
    private HtmlSelectBooleanCheckbox ckb = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbAll() {
        return ckbAll;
    }

    public void setCkbAll(HtmlSelectBooleanCheckbox hsbc) {
        this.ckbAll = hsbc;
    }

    public HtmlSelectBooleanCheckbox getCkb() {
        return ckb;
    }

    public void setCkb(HtmlSelectBooleanCheckbox ckb) {
        this.ckb = ckb;
    }

    private HtmlSelectOneMenu somIncrement = new HtmlSelectOneMenu();

    public HtmlSelectOneMenu getSomIncrement() {
        return somIncrement;
    }

    public void setSomIncrement(HtmlSelectOneMenu somIncrement) {
        this.somIncrement = somIncrement;
    }

    private boolean ckbAllSelected;
    private boolean ckbselected;

    public boolean isCkbAllSelected() {
        return ckbAllSelected;
    }

    public void setCkbAllSelected(boolean ckbAllSelected) {
        this.ckbAllSelected = ckbAllSelected;
    }

    public boolean isCkbselected() {
        return ckbselected;
    }

    public void setCkbselected(boolean ckbselected) {
        this.ckbselected = ckbselected;
    }

    public void ckb_processValueChange() {

        boolean selected = ckb.isSelected();
        HrEmployees employee = new HrEmployees();
        employee.setApproved(selected);
    }

    public void ckbAll_processValueChange() {
        Iterator iterator = empList.iterator();
        boolean selected = ckbAll.isSelected();
        while (iterator.hasNext()) {
            MassSalaryIncrementTableList employee = (MassSalaryIncrementTableList) iterator.next();
            employee.setApproved(selected);
        }
    }

    public void ckbAllemployeeslist_processValueChange() {
        readEmployees();
    }

    private ArrayList<HrSalaryIncrementDetails> saveMassSalaryIncrementDetail() {
        try {
            ArrayList<HrSalaryIncrementDetails> selectedEmployees = new ArrayList<>();
            if (empList.size() > 0) {
                for (MassSalaryIncrementTableList manager : empList) {
                    if (manager.isApproved() && (manager.getPrevSalaryStep() != manager.getNewSalaryStep() || manager.getNewSalary() != manager.getPrevSalary())) {
                        HrSalaryIncrementDetails employeeList = new HrSalaryIncrementDetails();
                        employeeList.setId(manager.getId());
                        employeeList.setEmpId(manager.getIdEmp());
                        employeeList.setIncrementBy(manager.getIncrementBy());
                        employeeList.setNewSalaryStep(Integer.valueOf(manager.getNewSalaryStep().toString()));
                        employeeList.setNewSalary(manager.getNewSalary());
                        employeeList.setStatus(0);
                        selectedEmployees.add(employeeList);
                    }
                }
                System.out.println("sizeof" + selectedEmployees.size());
            }
            return selectedEmployees;
        } catch (Exception ex) {
            return null;
        }
    }

    public void savewf() {
        wfHrProcessed.setProcessedBy(SessionBean.getUserId());
        wfHrProcessed.setHrSalaryIncrementId(hrSalaryIncrements);
        wfHrProcessed.setDecision(status);
        wfHrProcessedBeanLocal.create(wfHrProcessed);
        wfHrProcessed = new WfHrProcessed();
    }

    public String saveMassSalIncDetailInfo() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveMassSalIncDetailInfo", dataset)) {
                if (empList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Data To be Saved");
                } else {
                    if (!empList.isEmpty() && hrSalaryIncrements.getId() != null) {
                        int salIncId = Integer.valueOf(hrSalaryIncrements.getId().toString());
                        boolean checkSave = massSalaryIncrementBeanLocal.saveMassSalaryIncrementDetail(
                                salIncId, saveMassSalaryIncrementDetail());

                        if (checkSave) {
                            status = Constants.PREPARE_VALUE;
                            savewf();
                            JsfUtil.addSuccessMessage("Salary increment request was "
                                    + "submitted successfully for approval");

                            empListDataModel = null;
                            empList.clear();
                        } else {
                            JsfUtil.addErrorMessage("Error occurs while submitting "
                                    + "salary increment request");
                        }
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                //====================================================
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
            JsfUtil.addFatalMessage("Unable to Save data");
        }

        return null;
    }

    private ArrayList<HrSalaryIncrementDetails> approveMassSalaryIncrementDetail() {
        try {

            if (empList.isEmpty()) {
                JsfUtil.addFatalMessage("No Data To be Saved");
            } else {
                ArrayList<HrSalaryIncrementDetails> selectedEmployees = new ArrayList<>();
                if (empList.size() > 0) {
                    for (MassSalaryIncrementTableList manager : empList) {
                        if (manager.isApproved() && (manager.getPrevSalaryStep() != manager.getNewSalaryStep() || manager.getNewSalary() != manager.getPrevSalary())) {
                            HrSalaryIncrementDetails employeeList = new HrSalaryIncrementDetails();
                            employeeList.setId(manager.getId());
                            employeeList.setEmpId(manager.getIdEmp());
                            employeeList.setIncrementBy(manager.getIncrementBy());
                            employeeList.setNewSalaryStep(Integer.valueOf(manager.getNewSalaryStep().toString()));
                            employeeList.setNewSalary(manager.getNewSalary());
                            employeeList.setStatus(1);
                            selectedEmployees.add(employeeList);
                        }
                    }
                    System.out.println("sizeof" + selectedEmployees.size());
                }
                return selectedEmployees;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String approvMassSalIncDetailInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "approvMassSalIncDetailInfo", dataset)) {

                if (empList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Data To be Saved");
                } else {
                    if (!empList.isEmpty() && hrSalaryIncrements.getId() != null) {
                        int salIncId = Integer.valueOf(hrSalaryIncrements.getId().toString());
                        boolean checkSave = massSalaryIncrementBeanLocal.approveMassSalaryIncrementDetail(
                                salIncId, HrSalaryIncrements.INCREMENT, approveMassSalaryIncrementDetail());
                        if (checkSave) {
                            status = Constants.APPROVE_VALUE;
                            savewf();
                            JsfUtil.addSuccessMessage("Salary increment request was "
                                    + "approved successfully");
                            empListDataModel = null;
                            empList.clear();
                        } else {
                            JsfUtil.addErrorMessage("Error occurs while approving salary increment request");
                        }
                    }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "approvMassSalIncDetailInfo");
                eventEntry.setProgram(program);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }

        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Unable to Save data");
        }

        return null;
    }

//<editor-fold defaultstate="collapsed" desc="mass salary increment popup">
    public void saveMassSalIncInfo() {
        try {
            hrSalaryIncrements.setPreparedBy(SessionBean.getUserId());
            hrSalaryIncrements.setStatus(0);
            massSalaryIncrementBeanLocal.save(hrSalaryIncrements);
            JsfUtil.addSuccessMessage("Successfully Saved");
            hrSalaryIncrements = new HrSalaryIncrements();
            hrEmployees = new HrEmployees();
            setSalaryIncrementRequests(massSalaryIncrementBeanLocal.readSalaryIncrementRequests());
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("error occured while saving");
        }
    }

    public void clean() {
        try {
            hrSalaryIncrements = new HrSalaryIncrements();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("unable to clear");
        }
    }
//</editor-fold>

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(hrEmployees);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        hrEmployees.setFirstName((event.getObject().toString()));
        hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
    }
//</editor-fold>

}
