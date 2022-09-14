/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.leave;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.HrLeaveBalanceBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveTransferBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveTransfer;
import et.gov.eep.hrms.entity.leave.HrLeaveTransferDetail;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import javax.xml.namespace.QName;
import securityBean.Constants;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

@Named(value = "leavTransferAppContrllers")
@ViewScoped
public class LeaveTransferApproveController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="ENtity Injection">
    @Inject
    SessionBean sessionBean;
    @Inject
    WfHrProcessed hrWfProcessed;
    @Inject
    private HrEmployees employee;
    @Inject
    private HrDepartments department;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    private HrLeaveTransfer hrLeaveTransfer;
    @Inject
    private HrLeaveTransferDetail leaveTransferDetail;
    @Inject
    private FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    private HrLeaveBalance hrLeaveBalance;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Initialization">
    @EJB
    WfHrProcessedBeanLocal hrWfProcessedBeanLocal;
    @EJB
    HrDepartmentsFacade departmentFacadeLocal;
    @EJB
    private HrDepartmentsBeanLocal departmentBeanLocal;
    @EJB
    private LeaveTransferBeanLocal leaveTransferBeanLocal;
    @EJB
    HrEmployeeBeanLocal profileBeanLocal;
    @EJB
    private HrLeaveBalanceBeanLocal leaveBalanceBeanLocal;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="list declaration">
    ArrayList<HrEmployees> registeredEmployee;
    List<HrDepartments> allDepartmentsList;
    private HrLeaveTransfer selectedCriteria;
    private DataModel<HrLeaveTransferDetail> dataModelEmployee;
    List<HrLeaveTransferDetail> leaveTransferDetailsList;
    List<HrLeaveTransfer> hrLeaveTransfers = new ArrayList<>();
    DataModel<WfHrProcessed> workflowDataModel;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    WorkFlow workFlow = new WorkFlow();
    String employeeFName;
    String saveOrUpdate = "Save";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String SaveOrUpdateButton = "Save";
    String approveType;
    int update = 0;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    List araListe = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void autoEmpId() {

        allDepartmentsList = departmentBeanLocal.getDepartementList();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        leaveRequesterList = leaveTransferBeanLocal.findRequestList();

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrLeaveTransfer getSelectedCriteria() {
        return selectedCriteria;
    }
    
    public void setSelectedCriteria(HrLeaveTransfer selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
    }
    
//<editor-fold defaultstate="collapsed" desc="workflow">
    public WfHrProcessed getHrWfProcessed() {
        if (hrWfProcessed == null) {
            hrWfProcessed = new WfHrProcessed();
        }
        return hrWfProcessed;
    }
    
    public void setHrWfProcessed(WfHrProcessed hrWfProcessed) {
        this.hrWfProcessed = hrWfProcessed;
    }
    
    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }
    
    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }
    
    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrLeaveTranfer.getHrWorkFlowLeaveList()));
    }
    
//</editor-fold>
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }
    
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }
    
    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }
    
    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }
    
    public String getIcone() {
        return icone;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
    
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }
    
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
    
    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }
    
    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }
    
    public void createNewAdditionalAmount() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-document");
        }
    }
    
    public boolean isReanderDeptbtn() {
        return reanderDeptbtn;
    }
    
    public void setReanderDeptbtn(boolean reanderDeptbtn) {
        this.reanderDeptbtn = reanderDeptbtn;
    }
    
    public LeaveTransferApproveController() {
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
    
    public TreeNode getRoot2() {
        return root2;
    }
    
    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }
    
    public String getEmployeeFName() {
        return employeeFName;
    }
    
    public void setEmployeeFName(String employeeFName) {
        this.employeeFName = employeeFName;
    }
    
    public DataModel<HrLeaveTransferDetail> getDataModelEmployee() {
        if (dataModelEmployee == null) {
            dataModelEmployee = new ListDataModel<>();
        }
        return dataModelEmployee;
    }
    
    public HrEmployees getEmployee() {
        if (employee == null) {
            employee = new HrEmployees();
        }
        return employee;
    }
    
    public void setEmployee(HrEmployees employee) {
        this.employee = employee;
    }
    
    public void setDataModelEmployee(DataModel<HrLeaveTransferDetail> dataModelEmployee) {
        this.dataModelEmployee = dataModelEmployee;
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
    
    public HrLeaveTransfer getHrLeaveTransfer() {
        if (hrLeaveTransfer == null) {
            hrLeaveTransfer = new HrLeaveTransfer();
        }
        return hrLeaveTransfer;
    }
    
    public void setHrLeaveTransfer(HrLeaveTransfer hrLeaveTransfer) {
        this.hrLeaveTransfer = hrLeaveTransfer;
    }
    
    public HrLeaveTransferDetail getLeaveTransferDetail() {
        if (leaveTransferDetail == null) {
            leaveTransferDetail = new HrLeaveTransferDetail();
        }
        return leaveTransferDetail;
    }
    
    public void setLeaveTransferDetail(HrLeaveTransferDetail leaveTransferDetail) {
        this.leaveTransferDetail = leaveTransferDetail;
    }
    
    public List<HrLeaveTransferDetail> getLeaveTransferDetailsList() {
        return leaveTransferDetailsList;
    }
    
    public void setLeaveTransferDetailsList(List<HrLeaveTransferDetail> leaveTransferDetailsList) {
        this.leaveTransferDetailsList = leaveTransferDetailsList;
    }
    
    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }
    
    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
    
    public HrLeaveTransfer getHrLeaveTranfer() {
        if (hrLeaveTranfer == null) {
            hrLeaveTranfer = new HrLeaveTransfer();
        }
        return hrLeaveTranfer;
    }
    
    public void setHrLeaveTranfer(HrLeaveTransfer hrLeaveTranfer) {
        this.hrLeaveTranfer = hrLeaveTranfer;
    }
    
    public String getApproveType() {
        return approveType;
    }
    
    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main methods">
    public void clearLeaveTransferApprove() {
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        renderPnlCreateAdditional = false;
        employee = null;
        hrLeaveTransfer = null;
        employeeFName = null;
        dataModelEmployee = null;
//        saveOrUpdate = "Save";
        update = 0;
        department = null;
        hrJobTypes = null;
        hrLeaveTranfer = null;
        hrWfProcessed = null;
    }
    
    public void saveLeaveTransferApprove() {
        try {
//
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveTransferApprove", dataset)) {
                //    put ur code here...!
                if (hrLeaveTranfer.getEmpId() != null) {
                    if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        hrLeaveTransfer.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        hrLeaveTransfer.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        hrLeaveTransfer.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        hrLeaveTransfer.setStatus(workFlow.getUserStatus());
                    }
                    fmsLuBudgetYear = new FmsLuBudgetYear();
                    hrWfProcessed = new WfHrProcessed();
                    
                    hrWfProcessed.setProcessedBy(sessionBean.getUserId());
                    hrWfProcessed.setDecision(hrLeaveTransfer.getStatus());
                    hrWfProcessed.setLeaveTransferId(hrLeaveTransfer);
                    fmsLuBudgetYear = accountingPeriodBean.getCurretActivePeriod().getLuBudgetYearId();
                    
                    for (int i = 0; i < leaveTransferDetailsList.size(); i++) {
                        HrEmployees emp = new HrEmployees();
                        
                        if (leaveTransferDetailsList.get(i).isStatus() == true) {
                            emp = leaveTransferDetailsList.get(i).getEmpId();
                            hrLeaveBalance = leaveBalanceBeanLocal.findEmployeeBalance(emp, fmsLuBudgetYear, 1);
                            if (hrLeaveBalance != null) {
                                hrLeaveBalance.setTransferStatus(1);
                                leaveBalanceBeanLocal.saveOrUpdate(hrLeaveBalance);
                                hrLeaveBalance = null;
                                emp = null;
                                hrLeaveBalance = new HrLeaveBalance();
                            }
                        }
                    }
                    hrWfProcessedBeanLocal.saveOrUpdate(hrWfProcessed);
                    
                    leaveTransferBeanLocal.saveOrUpdate(hrLeaveTranfer);
                    
                    JsfUtil.addSuccessMessage("Submitted Successfully.");
                    clearLeaveTransferApprove();
                    
                } else {
                    JsfUtil.addFatalMessage("Can not approve empty data.");
                }
                
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void populateLeaveTransferApprove(SelectEvent events) {
        hrLeaveTranfer = null;
        hrLeaveTranfer = (HrLeaveTransfer) events.getObject();
        leaveTransferDetailsList = hrLeaveTranfer.getHrLeaveTransferDetailList();
        dataModelEmployee = new ListDataModel(new ArrayList<>(leaveTransferDetailsList));
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        recreateWorkflowDataModel();
    }
    
    public void populateLeaveTransferRqst(ValueChangeEvent events) {
        hrLeaveTranfer = null;
        hrLeaveTranfer = (HrLeaveTransfer) events.getNewValue();
        leaveTransferDetailsList = hrLeaveTranfer.getHrLeaveTransferDetailList();
        dataModelEmployee = new ListDataModel(new ArrayList<>(leaveTransferDetailsList));
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        recreateWorkflowDataModel();
    }
//    public void save() {
//        System.out.println("employee---" + employee);
//
//        try {
//            System.err.println("the no of details========" + hrLeaveTranfer.getHrLeaveTransferDetailList().size());
//            hrLeaveTranfer.setEmpId(employee);
//            System.out.println("employee-2--" + hrLeaveTranfer.getEmpId());
//            FmsLuBudgetYear fmsLuBudgetYearr = new FmsLuBudgetYear();
//            fmsLuBudgetYearr = accountingPeriodBean.getCurretActivePeriod().getLuBudgetYearId();
//            hrLeaveTranfer.setBudgetYear(fmsLuBudgetYearr);
//            hrLeaveTranfer.setStatus("0");
//            leaveTransferBeanLocal.saveOrUpdate(hrLeaveTranfer);
//            if (update == 0) {
//                JsfUtil.addSuccessMessage("Saved Successfully.");
//                clearPage();
//            } else {
//                JsfUtil.addSuccessMessage("Update Successful.");
//                clearPage();
//            }
//        } catch (Exception e) {
//            JsfUtil.addFatalMessage("Something occured,unable to saved");
//        }
//
//    }
    String tempo = "";
    
    String selectedValue = "";
    
    public void departmentSelected(ValueChangeEvent event) {
        
        try {
            String tempo = event.getNewValue().toString();
            leaveTransferDetail = dataModelEmployee.getRowData();
            if ("true".equals(tempo)) {
                leaveTransferDetail.setStatus(true);
            } else {
                leaveTransferDetail.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void addToTable() {
        hrLeaveTransfer.addToRequest(leaveTransferDetail);
        recreateFamilyModelDetail();
    }
    
    public ArrayList<HrEmployees> searchEmployee(HrDepartments hd) {
        registeredEmployee = profileBeanLocal.populateEmpUnderDepartement(hd);
        return registeredEmployee;
    }
    HrLeaveTransfer hrLeaveTranfer = new HrLeaveTransfer();
    
    public void deptChanged(NodeSelectEvent event) {
        String key2 = selectedNode.getData().toString().split("--")[1];
        System.err.println("department==========   " + key2);
        hrLeaveTranfer = null;
        hrLeaveTranfer = new HrLeaveTransfer();
        department.setDepName(key2);
        department = departmentFacadeLocal.findDepartmentbyName(department);
        registeredEmployee = null;
        registeredEmployee = new ArrayList<>();
        registeredEmployee = searchEmployee(department);
        
        for (int i = 0; i < registeredEmployee.size(); i++) {
            HrLeaveTransferDetail hrLeaveTranferd = new HrLeaveTransferDetail();
            hrLeaveTranferd.setEmpId(registeredEmployee.get(i));
            hrLeaveTranfer.addToRequest(hrLeaveTranferd);
            
            hrLeaveTranferd = null;
            
        }
        recreateFamilyModelDetail();
        
    }
    
    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        for (HrDepartments k : allDepartmentsList) {
            if (k.getParentId() != null && k.getParentId() == id) {
                TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                araListe.add(k);
                recursive(araListe, k.getDepId(), childNode);
            }
        }
        
    }
    
    public void recreateFamilyModelDetail() {
        dataModelEmployee = null;
        dataModelEmployee = new ListDataModel(new ArrayList<>(hrLeaveTranfer.getHrLeaveTransferDetailList()));
    }
    
    public ArrayList<HrEmployees> searchEmployeeById(String employeeId) {
        ArrayList<HrEmployees> registeredEmployees = null;
        employee.setEmpId(employeeId);
        registeredEmployees = profileBeanLocal.searchByEmpById(employee);
        return registeredEmployees;
    }
    
    public void handleSelectById(SelectEvent event) {
        String emplId = event.getObject().toString();
        employee.setEmpId(emplId);
        employee = profileBeanLocal.findById(employee);
        department = employee.getDeptId();
        hrJobTypes = employee.getJobId();
        employeeFName = employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName();
    }
    boolean reanderDeptbtn = true;
    
    public List<SelectItem> getFilterByStatus() {
        return leaveTransferBeanLocal.filterByStatus();
    }
    
    public SelectItem[] getAllRequests() {
        List<String> reqList = null;
        reqList = leaveTransferBeanLocal.getApproveList();
        return JsfUtil.getSelectItems(reqList, true);
    }
    List<HrLeaveTransfer> leaveRequesterList = new ArrayList<>();
    
    public List<HrLeaveTransfer> getLeaveRequesterList() {
        return leaveRequesterList;
    }
    
    public void setLeaveRequesterList(List<HrLeaveTransfer> leaveRequesterList) {
        this.leaveRequesterList = leaveRequesterList;
    }
    
    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            // String requestAttributes[] = ;
            int id = Integer.parseInt(event.getNewValue().toString());
            System.out.println(" --id--" + id);
            leaveRequesterList = leaveTransferBeanLocal.findByStatus(id);
            update = 1;
        }
    }
    
    public void requestIdChanged(ValueChangeEvent event) {
        String requestAttributes[] = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(requestAttributes[0]);
        hrLeaveTranfer = leaveTransferBeanLocal.findById(id);
        update = 1;
        reanderDeptbtn = false;
        employeeFName = hrLeaveTranfer.getEmpId().getFirstName() + " " + hrLeaveTranfer.getEmpId().getMiddleName() + " " + hrLeaveTranfer.getEmpId().getLastName();
        
        recreateFamilyModelDetail();
        saveOrUpdate = "Update";
        
    }
    
    public void requestIdChangedApproved(ValueChangeEvent event) {
        String requestAttributes[] = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(requestAttributes[0]);
        hrLeaveTranfer = leaveTransferBeanLocal.findById(id);
        update = 1;
        reanderDeptbtn = false;
        //   employeeFName = hrLeaveTranfer.getEmpId().getFirstName() + " " + hrLeaveTranfer.getEmpId().getMiddleName() + " " + hrLeaveTranfer.getEmpId().getLastName();
        
        recreateFamilyModelDetail();
        // saveOrUpdate = "Update";
        
    }
//</editor-fold>

}
