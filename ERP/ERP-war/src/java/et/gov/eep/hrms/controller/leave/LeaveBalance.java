package et.gov.eep.hrms.controller.leave;

import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.mapper.FmsLuBudgetYearFacade;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.HrLeaveBalanceBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveSettingBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveAccruedBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuAllowedLeave;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import et.gov.eep.hrms.entity.leave.HrLuLeaveYear;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.hrms.mapper.leave.HrLeaveBalanceFacade;
import et.gov.eep.hrms.mapper.leave.HrLuAllowedLeaveFacadeLocal;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveTypesFacade;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveYearFacade;
import et.gov.eep.hrms.mapper.transfer.HrEmpInternalHistoryFacade;
import groovyjarjarasm.asm.tree.JumpInsnNode;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author prg
 */
@Named(value = "leaveBalance")
@ViewScoped
@TransactionManagement(TransactionManagementType.BEAN)
public class LeaveBalance implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Injections">    
    @Inject
    HrLuAllowedLeave allowedLeave;
    @Inject
    private HrEmployees employee;
    @Inject
    private HrLeaveRequest leaveRequest;
    @Inject
    private HrLeaveSetting hrLeaveSetting;
    @Inject
    private HrLuLeaveTypes hrLuLeaveTypes;
    @Inject
    private HrLuLeaveTypes hrLuLeaveTypeForBalance;
    @Inject
    private HrLuLeaveYear luLeaveYear;
    @Inject
    private HrLeaveBalance leaveBalance;
    @Inject
    private FmsAccountingPeriod accountingPeriod;
    @Inject
    private FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    HrLeaveAccruedBalance hrLeaveAccruedBalance;
    @EJB
    HrLeaveBalanceBeanLocal balanceBeanLocal;
    @EJB
    HrLuLeaveYearFacade luLeaveYearFacade;
    @Inject
    SessionBean sessionBean;

    @Inject
    HrEmpInternalHistory hrEmpInternalHistory;
    @EJB
    HrEmployeeBeanLocal profileBeanLocal;
    @EJB
    HrLuLeaveTypesFacade luLeaveTypesFacade;
    @EJB
    LeaveSettingBeanLocal settingBeanLocal;
    @EJB
    LeaveRequestBeanLocal requestBeanLocal;
    @EJB
    HrEmpInternalHistoryFacade hrEmpInternalHistoryFacade;

    @EJB
    HrLeaveBalanceFacade hrLeaveBalanceFacade;

    @EJB
    private HrLuLeaveYearFacade leaveYearFacade;
    @EJB
    HrLuAllowedLeaveFacadeLocal allowedleaveFacade;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
    @EJB
    private FmsLuBudgetYearBeanLocal budgetYearBeanLocal;
    @EJB
    FmsLuBudgetYearFacade budgetYearFacadeLocal;
    //////////////////////////////////////////////////////////////////</editor-fold>//    

    //<editor-fold defaultstate="collapsed" desc="Initializations">    
    String budjetEnd;
    boolean balanceButonShow = false;
    boolean renderBalance = false;
    boolean balanceBtn = true;
    Date leave_start;
    Date leave_end;
    int update = 0;
    int totalLeaveDays = 0;
    int days = 0;
    int budjetYearFirst, budjetYearSecond, budjetYearThird;
    boolean isFirstBalance = false;
    List<HrLeaveRequest> requestsList = new ArrayList<>();
    List<HrLuAllowedLeave> HrLuAllowedLeaveList = new ArrayList<>();
    List<HrLeaveBalance> allbalanceList = new ArrayList<>();
    List<HrEmployees> hrEmployeeList = new ArrayList<>();
    List<HrLuLeaveTypes> lvList = new ArrayList<>();
    List<HrLuLeaveTypes> lvListBalnced = new ArrayList<>();
    List<HrLeaveBalance> balanceList = new ArrayList<>();
    List<HrEmpInternalHistory> empInternalhistoryList = new ArrayList<>();
    List<HrLeaveAccruedBalance> accruedLeaveBalanceList = new ArrayList<>();
    DataModel<HrLeaveRequest> leaveRequestDataModel;
    DataModel<HrLeaveBalance> balanceDataModel;
    DataModel<HrLeaveBalance> allbalanceDataModel;
    DataModel<HrLeaveAccruedBalance> accruedbalanceDataModel;
    List<FmsAccountingPeriod> accountingPeriods = new ArrayList<>();
    List<FmsLuBudgetYear> budgetYears = new ArrayList<>();
    List<FmsLuBudgetYear> budgetYearsList = new ArrayList<>();
    List<FmsLuBudgetYear> budgetYearsForAccruedLeave = new ArrayList<>();
    //List<HrLeaveBalance> leaveBalanceList = new ArrayList();

    private boolean lookForNotGeneratedAL = false;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String SaveOrUpdateButton = "Save";
    List<Integer> orderedBalances = new ArrayList();
    List<Integer> activeyears = new ArrayList();

//////////////////////////////////////////////////////////////////</editor-fold>//
    
    //<editor-fold defaultstate="collapsed" desc="Post Construct Load Budget Year">
    @PostConstruct
    public void loadbudjets() {

        try {
            FmsAccountingPeriod fap = new FmsAccountingPeriod();
            fap = accountingPeriodBeanLocal.getCurretActivePeriod();
            budgetYears = null;
            budgetYears = new ArrayList<>();
            budgetYears = budgetYearFacadeLocal.findAll();
            for (int i = 0; i < 16; i++) {
                allowedLeave = allowedleaveFacade.findAlloedLeave(i);
                HrLuAllowedLeaveList.add(allowedLeave);
                System.out.println("for" + allowedLeave.getNoOfYr() + " year expreince====" + allowedLeave.getAllowedDays() + " days");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    
    public boolean isLookForNotGeneratedAL() {
        return lookForNotGeneratedAL;
    }
    
    public void setLookForNotGeneratedAL(boolean lookForNotGeneratedAL) {
        this.lookForNotGeneratedAL = lookForNotGeneratedAL;
    }
    
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }
    
    public List<HrLuAllowedLeave> getHrLuAllowedLeaveList() {
        return HrLuAllowedLeaveList;
    }
     int notGeneratedAL = 0;

    public int getNotGeneratedAL() {
        return notGeneratedAL;
    }

    public void setNotGeneratedAL(int notGeneratedAL) {
        this.notGeneratedAL = notGeneratedAL;
    }
    public void setHrLuAllowedLeaveList(List<HrLuAllowedLeave> HrLuAllowedLeaveList) {
        this.HrLuAllowedLeaveList = HrLuAllowedLeaveList;
    }
    
    public List<HrEmpInternalHistory> getEmpInternalhistoryList() {
        if (hrEmpInternalHistory == null) {
            hrEmpInternalHistory = new HrEmpInternalHistory();
        }
        return empInternalhistoryList;
    }
    
    public HrLuAllowedLeave getAllowedLeave() {
        if (allowedLeave == null) {
            allowedLeave = new HrLuAllowedLeave();
        }
        return allowedLeave;
    }
    
    public void setAllowedLeave(HrLuAllowedLeave allowedLeave) {
        this.allowedLeave = allowedLeave;
    }
    
    public void setEmpInternalhistoryList(List<HrEmpInternalHistory> empInternalhistoryList) {
        this.empInternalhistoryList = empInternalhistoryList;
    }
    
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }
    
    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }
    
    public DataModel<HrLeaveAccruedBalance> getAccruedbalanceDataModel() {
        return accruedbalanceDataModel;
    }
    
    public void setAccruedbalanceDataModel(DataModel<HrLeaveAccruedBalance> accruedbalanceDataModel) {
        this.accruedbalanceDataModel = accruedbalanceDataModel;
    }
    
    public HrLeaveAccruedBalance getHrLeaveAccruedBalance() {
        return hrLeaveAccruedBalance;
    }
    
    public void setHrLeaveAccruedBalance(HrLeaveAccruedBalance hrLeaveAccruedBalance) {
        this.hrLeaveAccruedBalance = hrLeaveAccruedBalance;
    }
    
    public List<HrLeaveAccruedBalance> getAccruedLeaveBalanceList() {
        return accruedLeaveBalanceList;
    }
    
    public void setAccruedLeaveBalanceList(List<HrLeaveAccruedBalance> accruedLeaveBalanceList) {
        this.accruedLeaveBalanceList = accruedLeaveBalanceList;
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
            //createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        }
    }
    
    public boolean isBalanceButonShow() {
        return balanceButonShow;
    }
    
    public void setBalanceButonShow(boolean balanceButonShow) {
        this.balanceButonShow = balanceButonShow;
    }
    
    public DataModel<HrLeaveBalance> getAllbalanceDataModel() {
        if (allbalanceDataModel == null) {
            allbalanceDataModel = new ListDataModel<>();
        }
        return allbalanceDataModel;
    }
    
    public void setAllbalanceDataModel(DataModel<HrLeaveBalance> allbalanceDataModel) {
        this.allbalanceDataModel = allbalanceDataModel;
    }
    
    public List<HrLeaveBalance> getAllbalanceList() {
        if (allbalanceList == null) {
            allbalanceList = new ArrayList<>();
        }
        return allbalanceList;
    }
    
    public void setAllbalanceList(List<HrLeaveBalance> allbalanceList) {
        this.allbalanceList = allbalanceList;
    }
    
    public FmsLuBudgetYear getFmsLuBudgetYear() {
        if (fmsLuBudgetYear == null) {
            fmsLuBudgetYear = new FmsLuBudgetYear();
        }
        return fmsLuBudgetYear;
    }
    
    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }
    
    public HrLuLeaveTypes getHrLuLeaveTypeForBalance() {
        if (hrLuLeaveTypeForBalance == null) {
            hrLuLeaveTypeForBalance = new HrLuLeaveTypes();
        }
        return hrLuLeaveTypeForBalance;
    }
    
    public void setHrLuLeaveTypeForBalance(HrLuLeaveTypes hrLuLeaveTypeForBalance) {
        this.hrLuLeaveTypeForBalance = hrLuLeaveTypeForBalance;
    }
    
    public boolean isBalanceBtn() {
        return balanceBtn;
    }
    
    public void setBalanceBtn(boolean balanceBtn) {
        this.balanceBtn = balanceBtn;
    }
    
    public int getTotalLeaveDays() {
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
    
    public List<FmsLuBudgetYear> getBudgetYearsForAccruedLeave() {
        String today[] = StringDateManipulation.currentDateInGC().split("-");
        int recentYear = Integer.valueOf(today[0]);
        for (int i = 0; i < budgetYears.size(); i++) {
            String year[] = budgetYears.get(i).getBudgetYear().split("-");
            int IntYear = Integer.valueOf(year[1]);
            if (IntYear <= recentYear) {
                budgetYearsForAccruedLeave.add(budgetYears.get(i));
            }
            
        }
        
        if (budgetYearsForAccruedLeave == null) {
            budgetYearsForAccruedLeave = new ArrayList<>();
        }
        return budgetYearsForAccruedLeave;
    }
    
    public void setBudgetYearsForAccruedLeave(List<FmsLuBudgetYear> budgetYearsForAccruedLeave) {
        this.budgetYearsForAccruedLeave = budgetYearsForAccruedLeave;
    }
    
    public boolean checkToBalance() {
        boolean tobalance = false;
        if (hrLuLeaveTypeForBalance != null) {
            hrLeaveSetting = settingBeanLocal.findLeaveSettingByLeaveType(hrLuLeaveTypeForBalance);
            if (hrLeaveSetting != null) {
                if (hrLeaveSetting.getToBalance() == true) {
                    tobalance = true;
                }
            }
        }
        
        return tobalance;
    }
    
//    public SelectItem[] getLeavetypeList() {
//        lvList = null;
//        lvList = new ArrayList<>();
//        lvList = luLeaveTypesFacade.findAll();
//        lvListBalnced = null;
//        lvListBalnced = new ArrayList<>();
//        int lvlstSize = lvList.size();
//        for (int i = 0; i < lvlstSize; i++) {
//            hrLuLeaveTypeForBalance = lvList.get(i);
//            if (checkToBalance()) {
//                lvListBalnced.add(hrLuLeaveTypeForBalance);
//            }
//        }
//        return JsfUtil.getSelectItems(lvListBalnced, true);
//    }
    int xId;
    
    public SelectItem[] getLeavetYearList() {
        return JsfUtil.getSelectItems(budgetYears, true);
    }
    
    public void recreateLeaveRequestModel() {
        leaveRequestDataModel = null;
        leaveRequestDataModel = new ListDataModel(new ArrayList<>(requestsList));
    }
    
    public void recreateAllLeavebalanceModel() {
        allbalanceDataModel = null;
        allbalanceDataModel = new ListDataModel(new ArrayList<>(allbalanceList));
    }
    
    public void recreateAccruedLeavebalanceModel() {
        accruedbalanceDataModel = null;
        accruedbalanceDataModel = new ListDataModel(new ArrayList<>(accruedLeaveBalanceList));
        System.out.println("accruedbalanceDataModel==" + accruedbalanceDataModel.getRowCount());
    }
    
    public FmsAccountingPeriod getAccountingPeriod() {
        if (accountingPeriod == null) {
            accountingPeriod = new FmsAccountingPeriod();
        }
        return accountingPeriod;
    }
    
    public void setAccountingPeriod(FmsAccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Generate balance">  
    int empServiceInYears = 0;
    int monthired;
    int yearhired;

    public int generateEmpServiceInYears(HrEmployees empl) {
        try {
            String hired[] = empl.getHireDate().split("/");
            if (hired != null && budjetEnd != null) {
                int dayhired = Integer.parseInt(hired[0]);
                monthired = Integer.parseInt(hired[1]);
                yearhired = Integer.parseInt(hired[2]);
                empServiceInYears = StringDateManipulation.getDuration(monthired, dayhired, yearhired, budjetEnd);
            } else {
                JsfUtil.addFatalMessage("Hire Date budjet year is null");

            }
            return empServiceInYears;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }

    }
    int alForMonth = 0;

    public HrLuAllowedLeave allowedLeaveSetter() {
        System.out.println("-----generateEmpServiceInYears------" + generateEmpServiceInYears(employee));
        HrLuAllowedLeave allowedLeavedays = new HrLuAllowedLeave();
        if (generateEmpServiceInYears(employee) >= 1 && generateEmpServiceInYears(employee) < 16) {
            allowedLeavedays.setNoOfYr(generateEmpServiceInYears(employee));
            System.out.println(employee.getFirstName() + "    emp expreince===" + allowedLeavedays.getNoOfYr());
            allowedLeavedays = HrLuAllowedLeaveList.get(allowedLeavedays.getNoOfYr() - 1);
        } else if (generateEmpServiceInYears(employee) >= 16) {
            System.out.println("inside else if");
            allowedLeavedays = HrLuAllowedLeaveList.get(15);
        } else {
            allowedLeavedays.setNoOfYr(1);
            allowedLeavedays = HrLuAllowedLeaveList.get(allowedLeavedays.getNoOfYr() - 1);
            if (monthired < 10) {
                alForMonth = allowedLeavedays.getAllowedDays() / 10 - monthired;
                allowedLeavedays.setAllowedDays(alForMonth);
            }
        }

        return allowedLeavedays;

    }
//////////////////////////////////////////////////////////////////</editor-fold>//

    //<editor-fold defaultstate="collapsed" desc="Save Leave Balance Method">
    @Resource
    UserTransaction userTransaction = null;

    public void saveLeaveBalance() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveBalance", dataset)) {
                //<editor-fold defaultstate="collapsed" desc="check Prev year report">
                fmsLuBudgetYear = leaveBalance.getLeaveYearId();
                fmsLuBudgetYear = budgetYearBeanLocal.findByBudjetYearById(fmsLuBudgetYear);
                String prevYear[] = fmsLuBudgetYear.getBudgetYear().split("-");
                int pyy = Integer.valueOf(prevYear[0]);
                int ppyExtension = pyy - 1;
                String PrevY = ppyExtension + "-" + pyy;
                System.out.println("PrevY=====" + PrevY);
                hrLeaveAccruedBalance = balanceBeanLocal.findByBudgetYear(PrevY);
//            

//</editor-fold>
                hrEmployeeList.clear();
                allbalanceList.clear();
                hrEmployeeList = profileBeanLocal.findActiveEmployees();
                System.out.println("hrEmployeeList size====" + hrEmployeeList.size());
                if (accountingPeriod != null) {
                    if (!checkRedundancy()) {
                        userTransaction.begin();
                        try {
                            if (hrEmployeeList.size() > 0) {

                                for (int i = 0; i < hrEmployeeList.size(); i++) {
                                    leaveBalance = new HrLeaveBalance();
                                    employee = new HrEmployees();
                                    employee = hrEmployeeList.get(i);
                                    System.out.println("empId===" + employee.getId());
                                    if (!employee.getHireDate().isEmpty()) {
//                                        if (allowedLeave() != null) {
                                        leaveBalance.setEmployeeId(employee);
                                        allowedLeave = new HrLuAllowedLeave();
                                        if (yearhired <= 1983) {

                                            allowedLeave = allowedLeaveSetter();
                                            leaveBalance.setRemainingDays(allowedLeave.getAllowedDays() + 4);
                                            leaveBalance.setTotalDays(allowedLeave.getAllowedDays() + 4);
                                        } else {
                                            allowedLeave = allowedLeaveSetter();
                                            leaveBalance.setRemainingDays(allowedLeave.getAllowedDays());
                                            leaveBalance.setTotalDays(allowedLeave.getAllowedDays());
                                        }
                                         hrLuLeaveTypes.setId(3);
                                        leaveBalance.setLeaveTypeId(hrLuLeaveTypes);
                                        System.out.println("leaveBalance.getLeaveTypeId()=="+leaveBalance.getLeaveTypeId());
                                        leaveBalance.setLeaveYearId(fmsLuBudgetYear);
                                        leaveBalance.setStatus(1);
                                        leaveBalance.setTransferStatus(0);
                                        Date currDate = new Date();
                                        leaveBalance.setRecordDate(StringDateManipulation.toDayInEc());
                                        allbalanceList.add(leaveBalance);
                                        
                                        System.out.println("empId=after create==" + employee.getId());

                                        //doExpiry();
//                                        } else {
//                                            JsfUtil.addFatalMessage("Sorry AL Allowed Leave is " + allowedLeave().getAllowedDays());
//
//                                        }
                                    }

                                }
                                System.out.println("allbalanceList==="+allbalanceList);
                              if(allbalanceList.size()>0){
                                  for(int i=0;i<allbalanceList.size();i++){
                                      leaveBalance= new HrLeaveBalance();
                                      leaveBalance=allbalanceList.get(i);
                                      balanceBeanLocal.create(leaveBalance);
                                  }
                              }
                                userTransaction.commit();
                                recreateAllLeavebalanceModel();
                                System.gc();
                                JsfUtil.addSuccessMessage("Balance Generated!");
                            } else {
                                JsfUtil.addFatalMessage("Sorry, No Employee found.");

                            }
                        } catch (Exception ex) {
                            try {
                                System.out.println("Rollback failed 1 ");

                                userTransaction.rollback();
                                System.gc();
                                System.out.println("Rollback failed 2");
                                JsfUtil.addFatalMessage("Error occured while saving:" + ex.getMessage() + " Rollback Success ");
                            } catch (Exception e) {
                                System.out.println("Rollback failed " + e.getMessage());
                            }
                        }

                    } else {
                        JsfUtil.addFatalMessage("Balance found with the selected budjet year and leave type");
                    }
                } else {
                    JsfUtil.addFatalMessage("Sorry!!!This operation is suspended till a new budjet year is launched by the selected year.");
                }
                //}
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SaveAccruedLeaveBalance() {
        try {
            System.out.println("inside save");
            userTransaction.begin();
            for (int i = 0; i < accruedLeaveBalanceList.size(); i++) {

                hrLeaveAccruedBalance = accruedLeaveBalanceList.get(i);
                balanceBeanLocal.saveAccruedLeave(hrLeaveAccruedBalance);
            }
            userTransaction.commit();
            JsfUtil.addSuccessMessage("Data saved Successfully");
            accruedbalanceDataModel = new ArrayDataModel<>();
            fmsLuBudgetYear = new FmsLuBudgetYear();
            leaveBalance = new HrLeaveBalance();
            System.gc();
        } catch (Exception ex) {
            try {

                userTransaction.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Do Leave Expiry">
    public void doExpiry() {
        orderedBalances = balanceBeanLocal.populateDistnictBalance();
        if (orderedBalances.size() > 3) {

            hrLeaveBalanceFacade.expire();
            JsfUtil.addSuccessMessage("Balance Expiry Done!");
        }
//        if (orderedBalances.size() >= 3) {
//            hrLeaveBalanceFacade.expireNotTransferedBalances();
//            JsfUtil.addSuccessMessage("Balance Expiry For Transfer Done!");
//
//        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Searches">    
//    public void leaveTyp(ValueChangeEvent event) {
//        if (!event.getNewValue().toString().isEmpty()) {
//            hrLuLeaveTypes = (HrLuLeaveTypes) event.getNewValue();
//            if (hrLuLeaveTypes != null && fmsLuBudgetYear != null) {
//                // fillBalancetable();
//                allbalanceList.clear();
//                allbalanceList = balanceBeanLocal.populateLeaveBalanceByLeaveType(hrLuLeaveTypes, fmsLuBudgetYear);
//                if (allbalanceList.size() > 0) {
//                    balanceButonShow = false;
//                    recreateAllLeavebalanceModel();
//                } else {
//                    balanceButonShow = true;
//                }
//            }
//        }
//    }
    private double LeaveBalanceInBirr = 0;
    Integer remaininDays = 0;

    //<editor-fold defaultstate="collapsed" desc="get and set">
    public double getLeaveBalanceInBirr() {
        return LeaveBalanceInBirr;
    }

    public void setLeaveBalanceInBirr(double LeaveBalanceInBirr) {
        this.LeaveBalanceInBirr = LeaveBalanceInBirr;
    }

    public Integer getRemaininDays() {
        return remaininDays;
    }

    public void setRemaininDays(Integer remaininDays) {
        this.remaininDays = remaininDays;
    }

//</editor-fold>
    public HrEmpInternalHistory EmpHistory(int empId) {
        String record[] = StringDateManipulation.toDayInEc().split("-");
        int recordyear = Integer.parseInt(record[0]) - 1;
        String RecordDate = "30/10/" + recordyear;
        hrEmpInternalHistory = new HrEmpInternalHistory();
        hrEmpInternalHistory = hrEmpInternalHistoryFacade.findByEmpIdAndBudgerYear(empId, RecordDate, StringDateManipulation.toDayInEc());

        return hrEmpInternalHistory;
    }

    public int dateDiffrence(String date1, String date2) {
        String Y1[] = date1.split("-");
        int yy1 = Integer.valueOf(Y1[0]);
        int mm1 = Integer.valueOf(Y1[1]);
        int dd1 = Integer.valueOf(Y1[2]);
        String Y2[] = date2.split("-");
        int yy2 = Integer.valueOf(Y2[0]);
        int mm2 = Integer.valueOf(Y2[1]);
        int dd2 = Integer.valueOf(Y2[2]);
        int datediff = (((yy2 - yy1) * 365) + ((mm2 - mm1) * 30) + dd2 - dd1);
        return datediff;
    }

//    public void leaveTypeVCL(ValueChangeEvent event) throws InterruptedException {
//        if (!event.getNewValue().toString().isEmpty()) {
//            hrLuLeaveTypes = (HrLuLeaveTypes) event.getNewValue();
//            if (hrLuLeaveTypes != null && fmsLuBudgetYear != null) {
//                accruedLeaveBalanceList = balanceBeanLocal.findAccruedLeaveByLeaveYear(fmsLuBudgetYear);
//                if (accruedLeaveBalanceList.size() > 0) {
//                    recreateAccruedLeavebalanceModel();
//                    balanceButonShow = false;
//                } else {
//                    allbalanceList.clear();
//                    allbalanceList = balanceBeanLocal.populateLeaveBalanceByLeaveType(hrLuLeaveTypes, fmsLuBudgetYear);
//
//                    if (allbalanceList.size() > 0) {
//
//                        balanceButonShow = false;
//                        for (int i = 0; i < allbalanceList.size(); i++) {
//                            HrEmpInternalHistory emphistory = new HrEmpInternalHistory();
//                            double oldSalary;
//                            double salary = 0;
//
//                            if (EmpHistory(allbalanceList.get(i).getEmployeeId().getId()) != null) {
//                                emphistory = EmpHistory(allbalanceList.get(i).getEmployeeId().getId());
//                                oldSalary = emphistory.getOldSalary();
//                                double dailyLeaveAccrue = allbalanceList.get(i).getTotalDays() / 365;
//
//                                String recyear[] = StringDateManipulation.toDayInEc().split("-");
//                                int recordyear = Integer.parseInt(recyear[0]) - 1;
//                                String RecDate = recordyear + "/10/30";
//
//                                String processydate[] = emphistory.getProcessDate().split("/");
//                                String recorddate[] = RecDate.split("/");
//
//                                String process_date = processydate[2] + "-" + processydate[1] + "-" + processydate[0];
//                                String record_date = recorddate[0] + "-" + recorddate[1] + "-" + recorddate[2];
//                                int WorkingDaysInOldsal = dateDiffrence(record_date, process_date);
//                                String currentYear = String.valueOf(StringDateManipulation.currentYear);
//                                int workdaysInNewSal = dateDiffrence(process_date, currentYear + "-10-30");
//
//                                double LeaveAccruedAtOldSalary = dailyLeaveAccrue * WorkingDaysInOldsal;
//                                double LeaveAccruedAtNewSalary = dailyLeaveAccrue * workdaysInNewSal;;
//                                if (allbalanceList.get(i).getRemainingDays() > LeaveAccruedAtNewSalary) {
//                                    LeaveBalanceInBirr = LeaveAccruedAtOldSalary * (emphistory.getOldSalary() / 30) + LeaveAccruedAtNewSalary * (allbalanceList.get(i).getEmployeeId().getBasicSalary() / 30);
//                                    LeaveBalanceInBirr = Math.round(LeaveBalanceInBirr * 100) / 100;
//                                } else {
//                                    salary = allbalanceList.get(i).getEmployeeId().getBasicSalary();
//                                    remaininDays = allbalanceList.get(i).getRemainingDays();
//                                    LeaveBalanceInBirr = ((salary / 30) * remaininDays);
//                                    LeaveBalanceInBirr = Math.round(LeaveBalanceInBirr * 100) / 100;
//                                }
//                            } else {
//                                salary = allbalanceList.get(i).getEmployeeId().getBasicSalary();
//                                remaininDays = allbalanceList.get(i).getRemainingDays();
//                                LeaveBalanceInBirr = ((salary / 30) * remaininDays);
//                                LeaveBalanceInBirr = Math.round(LeaveBalanceInBirr * 100) / 100;
//                            }
//                            {
//
//                            }
//                            hrLeaveAccruedBalance.setEmployeeId(allbalanceList.get(i).getEmployeeId());
//                            hrLeaveAccruedBalance.setLeaveYear(allbalanceList.get(i).getLeaveYearId().getBudgetYear());
//                            hrLeaveAccruedBalance.setAccruedLeaveInDays(allbalanceList.get(i).getRemainingDays());
//                            hrLeaveAccruedBalance.setAccruedLeaveInBirr(LeaveBalanceInBirr);
//                            accruedLeaveBalanceList.add(hrLeaveAccruedBalance);
//                            hrLeaveAccruedBalance = new HrLeaveAccruedBalance();
//                            LeaveBalanceInBirr = 0;
//                            remaininDays = 0;
//                        }
//                        recreateAccruedLeavebalanceModel();
//                        balanceButonShow = true;
//                        System.gc();
//                        //Thread.sleep(10000); // to allow GC do its job
//                    }
//                }
//            }
//        }
//    }
    public ArrayList<HrEmployees> searchEmployee(String employeename) {
        employee = new HrEmployees();
        ArrayList<HrEmployees> registeredEmployee = null;
        employee.setFirstName(employeename);
        registeredEmployee = profileBeanLocal.searchEmployeeByName(employee);
        return registeredEmployee;

    }

    public boolean checkRedundancy() {
        return balanceBeanLocal.checkLeaveBalanceExistance(fmsLuBudgetYear, hrLuLeaveTypes);
    }
    private Integer totalAccruedLeaveLiability = 0;
    double LeaveAccruedAtOldSalary = 0;
    double LeaveAccruedAtNewSalary = 0;
    private String totalAccruedLeaveIndecimal;

//<editor-fold defaultstate="collapsed" desc="getter">
    public Integer getTotalAccruedLeaveLiability() {
        return totalAccruedLeaveLiability;
    }

    public void setTotalAccruedLeaveLiability(Integer totalAccruedLeaveLiability) {
        this.totalAccruedLeaveLiability = totalAccruedLeaveLiability;
    }

    public String getTotalAccruedLeaveIndecimal() {
        return totalAccruedLeaveIndecimal;
    }

    public void setTotalAccruedLeaveIndecimal(String totalAccruedLeaveIndecimal) {
        this.totalAccruedLeaveIndecimal = totalAccruedLeaveIndecimal;
    }

    public double getLeaveAccruedAtOldSalary() {
        return LeaveAccruedAtOldSalary;
    }

    public void setLeaveAccruedAtOldSalary(double LeaveAccruedAtOldSalary) {
        this.LeaveAccruedAtOldSalary = LeaveAccruedAtOldSalary;
    }

    public double getLeaveAccruedAtNewSalary() {
        return LeaveAccruedAtNewSalary;
    }

    public void setLeaveAccruedAtNewSalary(double LeaveAccruedAtNewSalary) {
        this.LeaveAccruedAtNewSalary = LeaveAccruedAtNewSalary;
    }

//</editor-fold>
    public void getLeaveYearVLC(ValueChangeEvent event) {
        String accountingEndDate = "";
        String budgetEndDate = "";
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear = null;
            fmsLuBudgetYear = (FmsLuBudgetYear) event.getNewValue();
            //fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearBeanLocal.findByBudjetYear(fmsLuBudgetYear);

            accountingPeriod = accountingPeriodBeanLocal.findAccountingPeriodByBudjetYear(fmsLuBudgetYear);
            accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
            accountingEndDate = accountingPeriod.getEndDate();
//            System.out.println("---tempDt----" + accountingEndDate);

            budjetEnd = GregorianCalendarManipulation.gregorianToEthiopian(accountingEndDate);
            if (fmsLuBudgetYear != null) {
                // fillBalancetable();
                allbalanceList.clear();
                allbalanceList = balanceBeanLocal.populateLeaveBalanceByLeaveTyp(fmsLuBudgetYear);
                if (allbalanceList.size() > 0) {
                    balanceButonShow = false;
                    recreateAllLeavebalanceModel();
                } else {
                    balanceButonShow = true;
                }
            }

        }
    }

    public void getLeaveYear(ValueChangeEvent event) throws InterruptedException {
        String accountingEndDate = "";
        String budgetEndDate = "";
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear = null;
            fmsLuBudgetYear = (FmsLuBudgetYear) event.getNewValue();
            fmsLuBudgetYear = budgetYearBeanLocal.findByBudjetYear(fmsLuBudgetYear);
            accountingPeriod = accountingPeriodBeanLocal.findAccountingPeriodByBudjetYear(fmsLuBudgetYear);
            accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
            accountingEndDate = accountingPeriod.getEndDate();
            budjetEnd = GregorianCalendarManipulation.gregorianToEthiopian(accountingEndDate);

        }
        if (fmsLuBudgetYear != null) {
            accruedLeaveBalanceList = balanceBeanLocal.findAccruedLeaveByLeaveYear(fmsLuBudgetYear);
            if (accruedLeaveBalanceList.size() > 0) {
                for (int i = 0; i < accruedLeaveBalanceList.size(); i++) {
                    totalAccruedLeaveLiability = totalAccruedLeaveLiability + (int) accruedLeaveBalanceList.get(i).getAccruedLeaveInBirr();
                }
                recreateAccruedLeavebalanceModel();
                balanceButonShow = false;
                totalAccruedLeaveIndecimal = insertCommas(new Integer(totalAccruedLeaveLiability));
                System.gc();
                Thread.sleep(10000);
            } else {
                allbalanceList.clear();
                allbalanceList = balanceBeanLocal.populateLeaveBalanceByLeaveTyp(fmsLuBudgetYear);
                if (allbalanceList.size() > 0) { 
                    balanceButonShow = false;
                    for (int i = 0; i < allbalanceList.size(); i++) {
                        HrEmpInternalHistory emphistory = new HrEmpInternalHistory();
                        LeaveAccruedAtOldSalary = 0;
                        LeaveAccruedAtNewSalary = 0;
                        double oldSalary;
                        double salary = 0;

                        if (EmpHistory(allbalanceList.get(i).getEmployeeId().getId()) != null) {
                            emphistory = EmpHistory(allbalanceList.get(i).getEmployeeId().getId());
                            oldSalary = emphistory.getOldSalary();
                            double dailyLeaveAccrue = allbalanceList.get(i).getTotalDays() / 365;

                            String recyear[] = StringDateManipulation.toDayInEc().split("-");
                            int recordyear = Integer.parseInt(recyear[0]) - 1;
                            String RecDate = recordyear + "/10/30";

                            String processydate[] = emphistory.getProcessDate().split("/");
                            String recorddate[] = RecDate.split("/");

                            String process_date = processydate[2] + "-" + processydate[1] + "-" + processydate[0];
                            String record_date = recorddate[0] + "-" + recorddate[1] + "-" + recorddate[2];
                            int WorkingDaysInOldsal = dateDiffrence(record_date, process_date);
                            String currentYear = String.valueOf(StringDateManipulation.currentYear);
                            int workdaysInNewSal = dateDiffrence(process_date, currentYear + "-10-30");

                            LeaveAccruedAtOldSalary = dailyLeaveAccrue * WorkingDaysInOldsal;
                            LeaveAccruedAtNewSalary = dailyLeaveAccrue * workdaysInNewSal;;
                            if (allbalanceList.get(i).getRemainingDays() > LeaveAccruedAtNewSalary) {
                                LeaveBalanceInBirr = LeaveAccruedAtOldSalary * (emphistory.getOldSalary() / 30) + LeaveAccruedAtNewSalary * (allbalanceList.get(i).getEmployeeId().getBasicSalary() / 30);
                                LeaveBalanceInBirr = Math.round(LeaveBalanceInBirr * 100) / 100;
                            } else {
                                salary = allbalanceList.get(i).getEmployeeId().getBasicSalary();
                                remaininDays = allbalanceList.get(i).getRemainingDays();
                                LeaveBalanceInBirr = ((salary / 30) * remaininDays);
                                LeaveBalanceInBirr = Math.round(LeaveBalanceInBirr * 100) / 100;
                            }
                        } else {
                            salary = allbalanceList.get(i).getEmployeeId().getBasicSalary();
                            remaininDays = allbalanceList.get(i).getRemainingDays();
                            LeaveBalanceInBirr = ((salary / 30) * remaininDays);
                            LeaveBalanceInBirr = Math.round(LeaveBalanceInBirr * 100) / 100;
                        }

                        totalAccruedLeaveLiability = totalAccruedLeaveLiability + (int) LeaveBalanceInBirr;
                        hrLeaveAccruedBalance.setEmployeeId(allbalanceList.get(i).getEmployeeId());
                        hrLeaveAccruedBalance.setLeaveYear(allbalanceList.get(i).getLeaveYearId().getBudgetYear());
                        hrLeaveAccruedBalance.setAccruedLeaveInDays(allbalanceList.get(i).getRemainingDays());
                        hrLeaveAccruedBalance.setAccruedLeaveInBirr(LeaveBalanceInBirr);
                        accruedLeaveBalanceList.add(hrLeaveAccruedBalance);
                        hrLeaveAccruedBalance = new HrLeaveAccruedBalance();
                        LeaveBalanceInBirr = 0;
                        remaininDays = 0;
                    }
                    recreateAccruedLeavebalanceModel();
                    balanceButonShow = true;

                    totalAccruedLeaveIndecimal = insertCommas(new Integer(totalAccruedLeaveLiability));
                    System.gc();
                    Thread.sleep(10000); //to allow GC do its job

                } else {
                    JsfUtil.addFatalMessage("No Leave balance Available In " + fmsLuBudgetYear.getBudgetYear());
                }
            }
        }
    }

    private static String insertCommas(Integer number) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(number);
    }

    public void LeaveYearVCL(ValueChangeEvent event) {
        allbalanceList = new ArrayList<>();
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear = (FmsLuBudgetYear) event.getNewValue();
            allbalanceList = balanceBeanLocal.populateLeaveBalanceByLeaveType(hrLuLeaveTypes, fmsLuBudgetYear);
        }
    }

//    private void notGeneratedAL(ValueChangeEvent valueChangeEvents) {
//        boolean vl = valueChangeEvents.getNewValue().equals(true);
//        if (valueChangeEvents.getNewValue().equals(true)) {
//            int countOfEmpAlNotGenerated = profileBeanLocal.countOfEmpAlNotGenerated();
//            System.out.println(" count------"+countOfEmpAlNotGenerated);
//      //   return false;
//    }
//////////////////////////////////////////////////////////////////</editor-fold>//
   
   


}
