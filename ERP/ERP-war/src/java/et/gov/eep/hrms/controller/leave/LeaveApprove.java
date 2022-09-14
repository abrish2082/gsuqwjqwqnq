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
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.HrLeaveBalanceBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveSettingBeanLocal;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import et.gov.eep.hrms.entity.leave.HrLuLeaveYear;
import et.gov.eep.hrms.mapper.leave.HrLeaveBalanceFacade;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveTypesFacade;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveYearFacade;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.EventEntry;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import securityBean.Constants;
import securityBean.WorkFlow;
import webService.AAA;
import webService.IAdministration;

/**
 *
 * @author prg
 */
@Named("leaveApp")
@ViewScoped
public class LeaveApprove implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Enjecttion">
    @Inject
    WfHrProcessed hrWfProcessed;
    @Inject
    SessionBean sessionBean;
    @Inject
    WorkFlow workFlow;
    @Inject
    private HrEmployees employee;
    @Inject
    private HrLeaveRequest leaveRequest;
    @Inject
    private HrLeaveSetting hrLeaveSetting;
    @Inject
    private HrLuLeaveTypes hrLuLeaveTypes;
    @Inject
    private HrLuLeaveYear luLeaveYear;
    @Inject
    private HrLeaveBalance leaveBalance;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
    WfHrProcessedBeanLocal hrWfProcessedBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrLuLeaveYearFacade luLeaveYearFacade;
    @EJB
    HrEmployeeBeanLocal profileBeanLocal;
    @EJB
    HrLeaveBalanceBeanLocal balanceBeanLocal;
    @EJB
    HrLuLeaveTypesFacade luLeaveTypesFacade;
    @EJB
    LeaveSettingBeanLocal settingBeanLocal;
    @EJB
    LeaveRequestBeanLocal requestBeanLocal;
    @EJB
    HrLeaveBalanceFacade hrLeaveBalanceFacade;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="List And dataModel declaration">
    List<HrLeaveRequest> requestsList = new ArrayList<>();
    List<HrLuLeaveTypes> lvList = new ArrayList<>();
    List<HrLeaveBalance> balanceList = new ArrayList<>();
    DataModel<HrLeaveRequest> leaveRequestDataModel;
    DataModel<WfHrProcessed> workflowDataModel;
    DataModel<HrLeaveBalance> balanceDataModel;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    boolean renderBalance = false;
    boolean balanceBtn = true;
    Date leave_start;
    Date leave_end;
    int update = 0;
    int reqSize = 0;
    double totalLeaveDays = 0;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String SaveOrUpdateButton = "Save";
    private boolean renderAlBalance = false;
    String selectedValue = "";
//</editor-fold>

    //    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }
    
    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }
    
    public WfHrProcessed getHrWfProcessed() {
        if (hrWfProcessed == null) {
            hrWfProcessed = new WfHrProcessed();
        }
        return hrWfProcessed;
    }
    
    public void setHrWfProcessed(WfHrProcessed hrWfProcessed) {
        this.hrWfProcessed = hrWfProcessed;
    }
    
    public boolean isRenderAlBalance() {
        return renderAlBalance;
    }
    
    public void setRenderAlBalance(boolean renderAlBalance) {
        this.renderAlBalance = renderAlBalance;
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
    
    public int getReqSize() {
        return reqSize;
    }
    
    public void setReqSize(int reqSize) {
        this.reqSize = reqSize;
    }
    
    public List<HrLeaveRequest> getRequestsList() {
        return requestsList;
    }
    
    public void setRequestsList(List<HrLeaveRequest> requestsList) {
        this.requestsList = requestsList;
    }
    
    public boolean isBalanceBtn() {
        return balanceBtn;
    }
    
    public void setBalanceBtn(boolean balanceBtn) {
        this.balanceBtn = balanceBtn;
    }
    
    public double getTotalLeaveDays() {
        return totalLeaveDays;
    }
    
    public void setTotalLeaveDays(int totalLeaveDays) {
        this.totalLeaveDays = totalLeaveDays;
    }
    
    public HrLeaveBalance getLeaveBalance() {
        if (leaveBalance == null) {
            leaveBalance = new HrLeaveBalance();
        }
        return leaveBalance;
    }
    
    public void setLeaveBalance(HrLeaveBalance leaveBalance) {
        this.leaveBalance = leaveBalance;
    }
    
    public DataModel<HrLeaveRequest> getLeaveRequestDataModel() {
        if (leaveRequestDataModel == null) {
            leaveRequestDataModel = new ListDataModel<>();
        }
        return leaveRequestDataModel;
    }
    
    public DataModel<HrLeaveBalance> getBalanceDataModel() {
        if (balanceDataModel == null) {
            balanceDataModel = new ListDataModel<>();
        }
        return balanceDataModel;
    }
    
    public void setBalanceDataModel(DataModel<HrLeaveBalance> balanceDataModel) {
        this.balanceDataModel = balanceDataModel;
    }
    
    public void setLeaveRequestDataModel(DataModel<HrLeaveRequest> leaveRequestDataModel) {
        this.leaveRequestDataModel = leaveRequestDataModel;
    }
    
    public HrLuLeaveYear getLuLeaveYear() {
        if (luLeaveYear == null) {
            luLeaveYear = new HrLuLeaveYear();
        }
        return luLeaveYear;
    }
    
    public void setLuLeaveYear(HrLuLeaveYear luLeaveYear) {
        this.luLeaveYear = luLeaveYear;
    }
    
    public List<HrLeaveBalance> getBalanceList() {
        if (balanceList == null) {
            balanceList = new ArrayList<>();
        }
        return balanceList;
    }
    
    public void setBalanceList(List<HrLeaveBalance> balanceList) {
        this.balanceList = balanceList;
    }
    
    public Date getLeave_start() {
        return leave_start;
    }
    
    public void setLeave_start(Date leave_start) {
        this.leave_start = leave_start;
    }
    
    public boolean isRenderBalance() {
        return renderBalance;
    }
    
    public void setRenderBalance(boolean renderBalance) {
        this.renderBalance = renderBalance;
    }
    
    public HrLeaveRequest getLeaveRequest() {
        if (leaveRequest == null) {
            leaveRequest = new HrLeaveRequest();
        }
        return leaveRequest;
    }
    
    public void setLeaveRequest(HrLeaveRequest leaveRequest) {
        this.leaveRequest = leaveRequest;
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
    
    public HrLeaveSetting getHrLeaveSetting() {
        if (hrLeaveSetting == null) {
            hrLeaveSetting = new HrLeaveSetting();
        }
        return hrLeaveSetting;
    }
    
    public void setHrLeaveSetting(HrLeaveSetting hrLeaveSetting) {
        this.hrLeaveSetting = hrLeaveSetting;
    }
    
    public HrLuLeaveTypes getHrLuLeaveTypes() {
        if (hrLuLeaveTypes == null) {
            hrLuLeaveTypes = new HrLuLeaveTypes();
        }
        return hrLuLeaveTypes;
    }
    
    public void setHrLuLeaveTypes(HrLuLeaveTypes hrLuLeaveTypes) {
        this.hrLuLeaveTypes = hrLuLeaveTypes;
    }
    
    public SelectItem[] getLeavetypeList() {
        return JsfUtil.getSelectItems(lvList, true);
    }
    
    public Date getLeave_end() {
        return leave_end;
    }
    
    public void setLeave_end(Date leave_end) {
        this.leave_end = leave_end;
    }
    
    public void recreateLeaveRequestModel() {
        leaveRequestDataModel = null;
        leaveRequestDataModel = new ListDataModel(new ArrayList<>(requestsList));
    }

////</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Post Construct ">
    @PostConstruct
    public void fill_HR_Requests() {
        requestsList.clear();
        requestsList = requestBeanLocal.populateHRPendingRequests();
        reqSize = requestsList.size();
        recreateLeaveRequestModel();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="populate Leave Request">
    public void populateLeaveHrRequest(ValueChangeEvent changeEvent) {
//        leaveRequest = leaveRequestDataModel.getRowData();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('ovrPnNotf').hide();");
        
        leaveRequest = (HrLeaveRequest) changeEvent.getNewValue();
        hrLuLeaveTypes = leaveRequest.getLeaveTypesId();
        employee = leaveRequest.getEmpId();
        
        if (leaveRequest.getLeaveTypesId().getId() == 1) {
            renderAlBalance = true;
        }
        
        lvList = null;
        lvList = new ArrayList<>();
        lvList.add(hrLuLeaveTypes);

        /*loading leave balance list*/
        renderBalance = checkToBalance();
        viewBalance();
        recreateWorkflowDataModel();
    }
    
    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(leaveRequest.getHrWorkFlowLeaveList()));
        System.out.println("---workflowDataModel---" + workflowDataModel);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="view Balance">
    public void viewBalance() {
        totalLeaveDays = 0;
        balanceList = null;
        balanceList = new ArrayList<>();
        balanceList = hrLeaveBalanceFacade.populateLeaveBalance(employee, hrLuLeaveTypes);
        recreateBalanceDataModel();
    }
    
    public void recreateBalanceDataModel() {
        System.err.println("balanceList--" + balanceList.size());
        balanceDataModel = null;
        balanceDataModel = new ListDataModel(new ArrayList<>(balanceList));
        int x = balanceList.size();
        if (x > 0) {
            totalLeaveDays = 0;
            for (int i = 0; i < x; i++) {
                totalLeaveDays = totalLeaveDays + balanceList.get(i).getRemainingDays();
            }
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Balance Leave">
    public void balanceLeave() {
        System.err.println(balanceList);
        Integer noLvDay = leaveRequest.getApprovedDays();
        for (int i = 0; i < balanceList.size(); i++) {
            HrLeaveBalance lvBalance = new HrLeaveBalance();
            lvBalance = balanceList.get(i);
            Integer deposit = lvBalance.getRemainingDays();
            if (deposit >= noLvDay) {
                deposit = deposit - noLvDay;
                lvBalance.setRemainingDays(deposit);
                noLvDay = 0;
            } else if (deposit < noLvDay) {
                noLvDay = noLvDay - deposit;
                deposit = 0;
                lvBalance.setRemainingDays(0);
                
            }
            balanceBeanLocal.saveOrUpdate(lvBalance);
            
            lvBalance = null;
        }
        
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Check For Balance">
    public boolean checkToBalance() {
        boolean tobalance = false;
        if (hrLuLeaveTypes != null) {
            hrLeaveSetting = settingBeanLocal.findLeaveSettingByLeaveType(hrLuLeaveTypes);
            if (hrLeaveSetting != null) {
                if (hrLeaveSetting.getToBalance() == true) {
                    tobalance = true;
                } else {
                    
                    leaveRequest.setRequestedDays(hrLeaveSetting.getMaxNumOfDays());
                    
                }
            }
        }
        
        return tobalance;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Employee Search">
    public ArrayList<HrEmployees> searchEmployee(String employeename) {
        employee = new HrEmployees();
        ArrayList<HrEmployees> registeredEmployee = null;
        employee.setFirstName(employeename);
        registeredEmployee = profileBeanLocal.SearchByFname(employee);
        return registeredEmployee;
        
    }
    
    public void getEmployeeInfo(SelectEvent event) {
        String emplName = event.getObject().toString();
        HrEmployees employeeFirst = new HrEmployees();
        employeeFirst.setFirstName(emplName);
        employee = profileBeanLocal.getByFirstName(employee);
        leaveRequest.setEmpId(employee);
        populateLeaveList();
        
    }
    
    public void populateLeaveList() {
        if (employee != null) {
            List<HrLeaveSetting> l = new ArrayList<>();
            lvList = null;
            lvList = new ArrayList<>();
            String gender;
            gender = employee.getSex();
            l = settingBeanLocal.filterByGender(gender);
            int lSize = l.size();
            for (int i = 0; i < lSize; i++) {
                HrLuLeaveTypes obj = new HrLuLeaveTypes();
                obj = l.get(i).getLeaveTypeId();
                lvList.add(obj);
                obj = null;
            }
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clear Leave HrDecision Method">
    public String clearLeaveHrDecision() {
        hrWfProcessed = null;
        hrLeaveSetting = null;
        hrLuLeaveTypes = null;
        leaveRequest = null;
        employee = null;
        balanceDataModel = null;
        totalLeaveDays = 0;
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="save Leave Hr Decision Method">
    public void decisin(ValueChangeEvent changeEvent) {
        if (null != changeEvent.getNewValue()) {
            selectedValue = changeEvent.getNewValue().toString();
            if (Integer.valueOf(changeEvent.getNewValue().toString()) == 1) {
                leaveRequest.setReturnStatus(2);
            } else {
                leaveRequest.setReturnStatus(0);
            }
        }
        
    }
    
    public void saveLeaveApproveHr() {
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveApproveHr", dataset)) {
//                 put ur code here...!
                employee = leaveRequest.getEmpId();
                System.out.println("emp statone" + employee.getEmpStatus());
                
                System.out.println("Status2ff" + leaveRequest.getStatus2());
                
                System.out.println("renderBalance" + renderBalance);
                
                if (leaveRequest.getStatus2() == 1) {
                    employee.setEmpStatus(2);
                    hrEmployeeBean.saveOrUpdate(employee);
                    
                    if (renderBalance) {
                        balanceLeave();
                    }
                    
                    System.out.println("emp statThree" + employee.getEmpStatus());
                }
                if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                    workFlow.setUserStatus(Constants.APPROVE_VALUE);
                    leaveRequest.setStatus1(workFlow.getUserStatus());
                } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                    workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                    leaveRequest.setStatus1(workFlow.getUserStatus());
                } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                    workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                    leaveRequest.setStatus1(workFlow.getUserStatus());
                } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                    workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                    leaveRequest.setStatus1(workFlow.getUserStatus());
                }
                hrWfProcessed.setProcessedBy(sessionBean.getUserId());
                hrWfProcessed.setDecision(leaveRequest.getStatus1());
                hrWfProcessed.setLeaveId(leaveRequest);
                
                hrWfProcessedBeanLocal.saveOrUpdate(hrWfProcessed);
//                if (requestBeanLocal.saveOrUpdate(leaveRequest)) {
                requestBeanLocal.saveOrUpdate(leaveRequest);
                requestsList = null;
                requestsList = new ArrayList<>();
                requestsList = requestBeanLocal.populatePendingRequests();
                reqSize = requestsList.size();
                recreateLeaveRequestModel();
                clearLeaveHrDecision();
                JsfUtil.addSuccessMessage("Update Successful.");
//                }
//
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
            JsfUtil.addErrorMessage("Somthing Occured on Save");
            e.printStackTrace();
            
        }
        
    }
//</editor-fold>

}
