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
 * @author user
 */
@Named(value = "leaveTransferController")
@ViewScoped
public class LeaveTransferController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            SessionBean sessionBean;
    @Inject
            WfHrProcessed wfHrProcessed;
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

//<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            HrDepartmentsFacade departmentFacadeLocal;
    @EJB
            WfHrProcessedBeanLocal hrWfProcessedBeanLocal;
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

//<editor-fold defaultstate="collapsed" desc="List declaration">
    ArrayList<HrEmployees> registeredEmployee;
    List<HrDepartments> allDepartmentsList;
    private HrLeaveTransfer selectedCriteria;
    private DataModel<HrLeaveTransferDetail> dataModelEmployee;
    DataModel<WfHrProcessed> workflowDataModel;
    List araListe = new ArrayList<>();
//</editor-fold>
     
//<editor-fold defaultstate="collapsed" desc="variable declaration">
    String saveOrUpdate = "Save";
    String approveType;
    int update = 0;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String SaveOrUpdateButton = "Save";
    private boolean renderDecision = true;
    private boolean renderSave = true;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void autoEmpId() {
        
        allDepartmentsList = departmentBeanLocal.getDepartementList();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getter and settre">
    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
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
    
    public boolean isRenderSave() {
        return renderSave;
    }
    
    public void setRenderSave(boolean renderSave) {
        this.renderSave = renderSave;
    }
    
    public boolean isRenderDecision() {
        return renderDecision;
    }
    
    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }
    
    public HrLeaveTransfer getSelectedCriteria() {
        return selectedCriteria;
    }
    
    public void setSelectedCriteria(HrLeaveTransfer selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
    }
    
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
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            renderDecision = false;
            renderSave = true;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            renderDecision = true;
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
    
    public LeaveTransferController() {
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
    
    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }
    
    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
    
    public HrLeaveTransfer getHlt() {
        if (hrLeaveTranfer == null) {
            hrLeaveTranfer = new HrLeaveTransfer();
        }
        return hrLeaveTranfer;
    }
    
    public void setHlt(HrLeaveTransfer hrLeaveTranfer) {
        this.hrLeaveTranfer = hrLeaveTranfer;
    }
    
    public String getApproveType() {
        return approveType;
    }
    
    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }
    
    public void clearLeaveTransferRequest() {
        wfHrProcessed = null;
        employee = null;
        hrLeaveTransfer = null;
        dataModelEmployee = null;
        saveOrUpdate = "Save";
        update = 0;
        department = null;
        hrJobTypes = null;
        
    }
    List<HrLeaveTransfer> leaveRequesterList = new ArrayList<>();
    List<HrLeaveTransferDetail> leaveTransferDetailsList;
    
    public List<HrLeaveTransferDetail> getLeaveTransferDetailsList() {
        return leaveTransferDetailsList;
    }
    
    public void setLeaveTransferDetailsList(List<HrLeaveTransferDetail> leaveTransferDetailsList) {
        this.leaveTransferDetailsList = leaveTransferDetailsList;
    }
    
    public List<HrLeaveTransfer> getLeaveRequesterList() {
        return leaveRequesterList;
    }
    
    public void setLeaveRequesterList(List<HrLeaveTransfer> leaveRequesterList) {
        this.leaveRequesterList = leaveRequesterList;
    }
    
    public List<SelectItem> getFilterByStatus() {
        return leaveTransferBeanLocal.filterByStatus();
    }
    
    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int id = Integer.parseInt(event.getNewValue().toString());
            leaveRequesterList = leaveTransferBeanLocal.findByStatus(id);
//            hrLeaveTranfer = leaveTransferBeanLocal.findById(id);
            update = 1;
            
        }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="main methods">
    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrLeaveTranfer.getHrWorkFlowLeaveList()));
    }
    
    public void populateLeaveTransferRequest(SelectEvent events) {
        hrLeaveTranfer = null;
        hrLeaveTranfer = (HrLeaveTransfer) events.getObject();
        employee = hrLeaveTranfer.getEmpId();
        hrJobTypes = employee.getJobId();
        department = employee.getDeptId();
        leaveTransferDetailsList = hrLeaveTranfer.getHrLeaveTransferDetailList();
        dataModelEmployee = new ListDataModel(new ArrayList<>(leaveTransferDetailsList));
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        recreateWorkflowDataModel();
//        if (hrLeaveTranfer.getStatus().equalsIgnoreCase("1")) {
//            hrLeaveTranfer.setStatus("Approved");
//            renderDecision = false;
//        } else if (hrLeaveTranfer.getStatus().equalsIgnoreCase("2")) {
//            hrLeaveTranfer.setStatus("Rejected");
//            renderDecision = false;
//        } else if (hrLeaveTranfer.getStatus().equalsIgnoreCase("3")) {
//            hrLeaveTranfer.setStatus("Resubmited");
//            renderDecision = false;
//        } else {
//            hrLeaveTranfer.setStatus("Waiting");
//            renderDecision = true;
//        }
        
    }
    
    public void saveAndUpdateBalance() {
        
        leaveTransferBeanLocal.edit(hrLeaveTranfer);
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsLuBudgetYear = accountingPeriodBean.getCurretActivePeriod().getLuBudgetYearId();
        
        for (int i = 0; i < hrLeaveTranfer.getHrLeaveTransferDetailList().size(); i++) {
            HrEmployees emp = new HrEmployees();
            
            if (hrLeaveTranfer.getHrLeaveTransferDetailList().get(i).isStatus() == true) {
                emp = hrLeaveTranfer.getHrLeaveTransferDetailList().get(i).getEmpId();
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
        JsfUtil.addSuccessMessage("Saved Successfully.");
        clearLeaveTransferRequest();
    }
    
    public void saveLeaveTransferRequest() {
        
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "cfg/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveTransferRequest", dataset)) {
//                 put ur code here...!
                FmsLuBudgetYear fmsLuBudgetYearr = new FmsLuBudgetYear();
                
//
                //  hrWfProcessed.setProcessedBy(SessionBean.getUserId());
                hrLeaveTranfer.setEmpId(employee);
                System.out.println("employee-2--" + hrLeaveTranfer.getEmpId());
                fmsLuBudgetYearr = accountingPeriodBean.getCurretActivePeriod().getLuBudgetYearId();
                System.out.println("fmsLuBudgetYearr-2--" + fmsLuBudgetYearr);
                hrLeaveTranfer.setBudgetYear(fmsLuBudgetYearr);
                hrLeaveTranfer.setStatus(0);
                wfHrProcessed.setDecision(hrLeaveTranfer.getStatus());
                System.out.println("---hrWfProcessed-1--" + wfHrProcessed);
                wfHrProcessed.setLeaveTransferId(hrLeaveTranfer);
                hrWfProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                leaveTransferBeanLocal.saveOrUpdate(hrLeaveTranfer);
                
                if (update == 0) {
                    JsfUtil.addSuccessMessage("Saved Successfully.");
                    
                } else {
                    JsfUtil.addSuccessMessage("Update Successful.");
                }
                clearLeaveTransferRequest();
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
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
            JsfUtil.addFatalMessage("Something occured,unable to saved");
        }
        
    }
    
    String tempo = "";
    String selectedValue = "";
    
    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
            if (selectedValue.equalsIgnoreCase("approved")) {
                hrLeaveTranfer.setStatus(1);
                
            } else {
                hrLeaveTranfer.setStatus(-1);
                
            }
        }
    }
    
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
        if (employee != null) {
            department = employee.getDeptId();
            hrJobTypes = employee.getJobId();
        }
        
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsLuBudgetYear = accountingPeriodBean.getCurretActivePeriod().getLuBudgetYearId();
        if (employee != null && fmsLuBudgetYear != null) {
            int budgetyearID = fmsLuBudgetYear.getLuBudgetYearId();
            hrLeaveTranfer = leaveTransferBeanLocal.findByRequester(employee, budgetyearID);
        }
        
        if (hrLeaveTranfer != null) {
            leaveTransferDetailsList = hrLeaveTranfer.getHrLeaveTransferDetailList();
            dataModelEmployee = new ListDataModel(new ArrayList<>(leaveTransferDetailsList));
            
        }
        
    }
    boolean reanderDeptbtn = true;
    
    public SelectItem[] getAllRequests() {
        List<String> reqList = null;
        reqList = leaveTransferBeanLocal.getApproveList();
        return JsfUtil.getSelectItems(reqList, true);
    }
    
//    public void requestIdChanged(ValueChangeEvent event) {
//        String requestAttributes[] = event.getNewValue().toString().split("--");
//        int id = Integer.parseInt(requestAttributes[0]);
//        hrLeaveTranfer = leaveTransferBeanLocal.findById(id);
//        update = 1;
//        reanderDeptbtn = false;
//        employeeFName = hrLeaveTranfer.getEmpId().getFirstName() + " " + hrLeaveTranfer.getEmpId().getMiddleName() + " " + hrLeaveTranfer.getEmpId().getLastName();
//
//        recreateFamilyModelDetail();
//        saveOrUpdate = "Update";
//
//    }
    public void requestIdChangedApproved(ValueChangeEvent event) {
        String requestAttributes[] = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(requestAttributes[0]);
        hrLeaveTranfer = leaveTransferBeanLocal.findById(id);
        update = 1;
        reanderDeptbtn = false;
        recreateFamilyModelDetail();
        // saveOrUpdate = "Update";
        
    }
//</editor-fold>
}
