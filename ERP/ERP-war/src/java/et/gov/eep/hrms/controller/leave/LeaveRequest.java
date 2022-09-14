/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.leave;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.commonApplications.utility.number.NumAndDateFormatter;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.HrleaveHolidaySetUpBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveSettingBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveHolidaySetup;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuAllowedLeave;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import et.gov.eep.hrms.entity.leave.HrLuLeaveYear;
import et.gov.eep.hrms.mapper.leave.HrLeaveBalanceFacade;
import et.gov.eep.hrms.mapper.leave.HrLuAllowedLeaveFacadeLocal;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveTypesFacade;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveYearFacade;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author beki
 */
@Named("leaveRequest")
@ViewScoped
public class LeaveRequest implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
            HrLuLeaveYearFacade luLeaveYearFacade;
    @EJB
            HrEmployeeBeanLocal profileBeanLocal;
    @EJB
            HrLuLeaveTypesFacade luLeaveTypesFacade;
    @EJB
            LeaveSettingBeanLocal settingBeanLocal;
    @EJB
            LeaveRequestBeanLocal requestBeanLocal;
    @EJB
            HrLeaveBalanceFacade hrLeaveBalanceFacade;
    @EJB
    private HrLuAllowedLeaveFacadeLocal leaveFacade;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
    @EJB
            HrleaveHolidaySetUpBeanLocal hrleaveHolidaySetUpBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Entity Enjection">
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
    private HrLuLeaveYear luLeaveYear;
    @Inject
    private HrLeaveBalance leaveBalance;
    @Inject
    private FmsAccountingPeriod accountingPeriod;
    @Inject
            FmsLuBudgetYear budgetYear;
    @Inject
            SessionBean sessionBean;
    @Inject
            WfHrProcessed wfHrProcessed;
//</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    boolean renderBalance = false;
    boolean balanceBtn = true;
    boolean confirmBtn = true;
    boolean renderCalcReturnDate = true;
    int updates = 0;
    double totalLeaveDays = 0;
    int noReqDays;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String saveOrUpdateButton = "Save";
    private boolean renderSave = false;
    private boolean renderNoOfDays = true;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List initialization">
    List<HrLeaveRequest> requestsList = new ArrayList<>();
    List<HrLuLeaveTypes> lvList = new ArrayList<>();
    List<HrLeaveBalance> balanceList = new ArrayList<>();
    DataModel<HrLeaveRequest> leaveRequestDataModel;
    DataModel<HrLeaveBalance> balanceDataModel;
    DataModel<WfHrProcessed> workflowDataModel;
    List<HrLeaveHolidaySetup> hrLeaveHolidaySetupsList;
//</editor-fold>
    
    
    @PostConstruct
    public void init(){
        System.out.println("sessionBean.getEmpId()==="+sessionBean.getEmpId());
        System.out.println("sessionBean.getFirstName()=="+sessionBean.getFirstName());
        
    }

    //    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
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

    public boolean isRenderNoOfDays() {
        return renderNoOfDays;
    }

    public void setRenderNoOfDays(boolean renderNoOfDays) {
        this.renderNoOfDays = renderNoOfDays;
    }

    public boolean isRenderSave() {
        return renderSave;
    }

    public void setRenderSave(boolean renderSave) {
        this.renderSave = renderSave;
    }

    public FmsLuBudgetYear getBudgetYear() {
        if (budgetYear == null) {
            budgetYear = new FmsLuBudgetYear();
        }
        return budgetYear;
    }

    public void setBudgetYear(FmsLuBudgetYear budgetYear) {
        this.budgetYear = budgetYear;
    }

    public List<HrLuLeaveTypes> getLvList() {
        return lvList;
    }

    public void setLvList(List<HrLuLeaveTypes> lvList) {
        this.lvList = lvList;
    }

    public boolean isRenderCalcReturnDate() {
        return renderCalcReturnDate;
    }

    public void setRenderCalcReturnDate(boolean renderCalcReturnDate) {
        this.renderCalcReturnDate = renderCalcReturnDate;
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
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.saveOrUpdateButton = SaveOrUpdateButton;
    }

    public boolean isConfirmBtn() {
        return confirmBtn;
    }

    public void setConfirmBtn(boolean confirmBtn) {
        this.confirmBtn = confirmBtn;
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

    public void setTotalLeaveDays(double totalLeaveDays) {
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

    public FmsAccountingPeriod getAccountingPeriod() {
        if (accountingPeriod == null) {
            accountingPeriod = new FmsAccountingPeriod();
        }
        return accountingPeriod;
    }

    public void setAccountingPeriod(FmsAccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public SelectItem[] getLeavetypeList() {
        return JsfUtil.getSelectItems(lvList, true);
    }

    public void recreateLeaveRequestModel() {
        leaveRequestDataModel = null;
        leaveRequestDataModel = new ListDataModel(new ArrayList<>(requestsList));
    }

    NumAndDateFormatter df = new NumAndDateFormatter();

    public void recreateBalanceDataModel() {

        balanceDataModel = null;
        balanceDataModel = new ListDataModel(new ArrayList<>(balanceList));
        String todayDate[] = StringDateManipulation.toDayInEc().split("-");
        int workedMonth = Integer.valueOf(todayDate[1]) - 1;
        int x = balanceList.size();
        int lastIndex = balanceList.size() - 1;
        if (x > 0) {
            totalLeaveDays = 0;
            for (int i = 0; i < lastIndex; i++) {
                totalLeaveDays = totalLeaveDays + balanceList.get(i).getRemainingDays();
            }
            HrLeaveBalance allowedLeaveDayss = balanceList.get(lastIndex);
            if (workedMonth == 11) {
                totalLeaveDays = totalLeaveDays + allowedLeaveDayss.getTotalDays();
            } else {
                totalLeaveDays = totalLeaveDays + df.decimalFormat(allowedLeaveDayss.getTotalDays() / workedMonth);
            }
        }
    }
////</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main methods">
    
    public String calculateWorkingLastDate(Date startDate, int duration, boolean hlyDy, boolean sat, boolean sun) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = startCal;
        Date hollyDate;
        try {
            String strngDate = dateformatter.format(date.getTime());
            if (hlyDy && sat && sun) {
                System.err.println("check Hollyday=======" + " :" + hlyDy);
                System.err.println("check Saturday========" + " :" + sat);
                System.err.println("check Sunday========" + " :" + sun);
                
                for (int i = 0; i < duration; i++) {
                    
                    strngDate = dateformatter.format(date.getTime());
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                }
                
                /*Holiday calculation*/
//                  int noholydays = hrLuHolidayFacade.isBtwn(startDate, startCal.getTime());
                int noholydays = hrleaveHolidaySetUpBeanLocal.isBtwn(startDate, startCal.getTime());
                for (int i = 0; i < noholydays; i++) {
                    
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                        
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
            } else if (hlyDy && sun && !sat) {
                for (int i = 0; i < duration; i++) {
                    System.err.println("check Hollyday=======" + " :" + hlyDy);
                    System.err.println("check Saturday========" + " :" + sat);
                    System.err.println("check Sunday========" + " :" + sun);
                    
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
                /*Holiday calculation*/
                int noholydays = hrleaveHolidaySetUpBeanLocal.isBtwn(startDate, startCal.getTime());
                System.err.println("No fo holidays=========" + noholydays);
                for (int i = 0; i < noholydays; i++) {
                    
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                        
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
            } else if (hlyDy && sat && !sun) {
                System.err.println("check Hollyday=======" + " :" + hlyDy);
                System.err.println("check Saturday========" + " :" + sat);
                System.err.println("check Sunday========" + " :" + sun);
                for (int i = 0; i < duration; i++) {
                    
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                        
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
                
                /*Holiday calculation*/
                int noholydays = hrleaveHolidaySetUpBeanLocal.isBtwn(startDate, startCal.getTime());
                System.err.println("No fo holidays=========" + noholydays);
                for (int i = 0; i < noholydays; i++) {
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                        
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
            } else if (sat && sun && !hlyDy) {
                System.err.println("check Hollyday=======" + " :" + hlyDy);
                System.err.println("check Saturday========" + " :" + sat);
                System.err.println("check Sunday========" + " :" + sun);
                for (int i = 0; i < duration; i++) {
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
                
            } else if (sat && !sun && !hlyDy) {
                System.err.println("check Hollyday=======" + " :" + hlyDy);
                System.err.println("check Saturday========" + " :" + sat);
                System.err.println("check Sunday========" + " :" + sun);
                for (int i = 0; i < duration; i++) {
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
            } else if (sun && !sat && !hlyDy) {
                System.err.println("check Hollyday=======" + " :" + hlyDy);
                System.err.println("check Saturday========" + " :" + sat);
                System.err.println("check Sunday========" + " :" + sun);
                for (int i = 0; i < duration; i++) {
                    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        
                        startCal.add(Calendar.DAY_OF_MONTH, 1);
                        strngDate = dateformatter.format(date.getTime());
                    }
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
            } else if (!sun && !sat && hlyDy) {
                System.err.println("check Hollyday=======" + " :" + hlyDy);
                System.err.println("check Saturday========" + " :" + sat);
                System.err.println("check Sunday========" + " :" + sun);
                /*Holiday calculation*/
                int noholydays = hrleaveHolidaySetUpBeanLocal.isBtwn(startDate, startCal.getTime());
                System.err.println("No fo holidays=========" + noholydays);
                for (int i = 0; i < noholydays; i++) {
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
                
            } else {
                System.err.println("check Hollyday=======" + " :" + hlyDy);
                System.err.println("check Saturday========" + " :" + sat);
                System.err.println("check Sunday========" + " :" + sun);
                for (int i = 0; i < duration; i++) {
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    date = startCal;
                    strngDate = dateformatter.format(date.getTime());
                }
            }
            
            strngDate = dateformatter.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dateformatter.format(startCal.getTime());
    }
    
    boolean includeSaturay;
    boolean includeSunday;
    int maxiNumberOfDays = 0;
    
    public int getMaxiNumberOfDays() {
        return maxiNumberOfDays;
    }
    
    public void setMaxiNumberOfDays(int maxiNumberOfDays) {
        this.maxiNumberOfDays = maxiNumberOfDays;
    }
    
    public boolean checkToBalance() {
        boolean tobalance = false;
        try {
            if (hrLuLeaveTypes != null) {
                hrLeaveSetting = settingBeanLocal.findLeaveSettingByLeaveType(hrLuLeaveTypes);
                
                if (hrLeaveSetting != null) {
                    includeSaturay = hrLeaveSetting.isIncludeSat();
                    includeSunday = hrLeaveSetting.isIncludeSun();
                    if (hrLeaveSetting.getToBalance() == true) {
                        tobalance = true;
                        renderNoOfDays = false;
                    } else {
                        renderNoOfDays = true;
                        setMaxiNumberOfDays(hrLeaveSetting.getMaxNumOfDays());
//                        leaveRequest.setRequestedDays(hrLeaveSetting.getMaxNumOfDays());
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return tobalance;
    }
    
    public void loadBalance() {
        if (employee != null && hrLuLeaveTypes != null) {
            renderBalance = checkToBalance();
            /*loading leave active year*/
            loadLeaveYear();
            /*loading leave balance list*/
            if (true == renderBalance) {
                balanceBtn = false;
                balanceList = hrLeaveBalanceFacade.populateLeaveBalance(employee, hrLuLeaveTypes);
                recreateBalanceDataModel();
            } else {
                balanceList = null;
                balanceList = new ArrayList<>();
                balanceList = hrLeaveBalanceFacade.populateLeaveBalance(employee, hrLuLeaveTypes);
                recreateBalanceDataModel();
            }
            
        }
    }
    
    public void loadRequest() {
        /*populating the request lists */
        try {
            if (employee != null && luLeaveYear != null && hrLuLeaveTypes != null) {
                requestsList = requestBeanLocal.populateEmpLeaveRequests(employee, hrLuLeaveTypes);
                
                recreateLeaveRequestModel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void leaveTyp(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                hrLuLeaveTypes = (HrLuLeaveTypes) event.getNewValue();
                budgetYear = accountingPeriod.getLuBudgetYearId();
                if (employee != null && hrLuLeaveTypes != null) {
                    leaveRequest = requestBeanLocal.findUnApprovedLeave(employee, hrLuLeaveTypes);
                    System.out.println("--wfHrProcessed--" + wfHrProcessed);
                    if (leaveRequest != null) {
                        saveOrUpdateButton = "Update";
                        updates = 1;
                        wfHrProcessed = wfHrProcessedBeanLocal.findByLeaveId(leaveRequest);
                        System.out.println("--wfHrProcessed-" + wfHrProcessed);
                    } else {
                        leaveRequest = new HrLeaveRequest();
                        wfHrProcessed = new WfHrProcessed();
                    }
                }
                checkToBalance();
                loadBalance();
                loadRequest();
                leaveRequest.setLeaveTypesId(hrLuLeaveTypes);
                // }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<HrEmployees> searchEmployee(String employeename) {
        employee = new HrEmployees();
        List<HrEmployees> registeredEmployee = null;
        employee.setEmpId(employeename);
        registeredEmployee = profileBeanLocal.SearchByEmpId(employee);
        return registeredEmployee;
        
    }
    
    public void getEmployeeInfo(SelectEvent event) {
        
//
        String empId = event.getObject().toString();
        HrEmployees employeeObj = new HrEmployees();
        employeeObj.setEmpId(empId);
        employee = profileBeanLocal.getByEmpId(employeeObj);
//        System.out.println("emp Status  " + employee.getEmpStatus());
//        if (employee.getEmpStatus() == 1) {
        populateLeaveList();
        loadBalance();
        loadRequest();
    }
    
    public void populateLeaveList() {
        if (employee != null) {
            List<HrLeaveSetting> leaveSettingList = new ArrayList<>();
            lvList = null;
            lvList = new ArrayList<>();
            String gender;
            gender = employee.getSex();
            leaveSettingList = settingBeanLocal.filterByGender(gender);
            int lSize = leaveSettingList.size();
            for (int i = 0; i < lSize; i++) {
                HrLuLeaveTypes obj = new HrLuLeaveTypes();
                obj = leaveSettingList.get(i).getLeaveTypeId();
                lvList.add(obj);
                obj = null;
            }
        }
    }
    
    public String clearLeaveRequest() {
        renderSave = false;
        saveOrUpdateButton = "Save";
        hrLeaveSetting = null;
        hrLuLeaveTypes = null;
        leaveRequest = null;
        employee = null;
        balanceDataModel = null;
        totalLeaveDays = 0;
        leaveRequestDataModel = null;
        
        renderNoOfDays = true;
        return null;
    }
    
    public void loadLeaveYear() {
        accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
        leaveRequest.setLeaveYear(accountingPeriod);
        
    }
    
    SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
    
    public void calculateReturnDateLeaveRequest() {
        
        try {
            if (leaveRequest.getRequestedDays() != null && leaveRequest.getRequestedDays() != 0) {
                noReqDays = leaveRequest.getRequestedDays();
                
                if ("Half Day".equalsIgnoreCase(leaveRequest.getHalfOrFull())) {
                    noReqDays = noReqDays * 2;
                }
                String splt[] = leaveRequest.getLeaveStart().split("/");
                
                int leaveDate = Integer.parseInt(splt[0]);
                
                int leaveMonth = Integer.parseInt(splt[1]) - 1;
                
                int leaveYear = Integer.parseInt(splt[2]);
                
                Calendar leaveStartCal = Calendar.getInstance();
                leaveStartCal.set(leaveYear, leaveMonth, leaveDate);
                
                String startDate = dateformatter.format(leaveStartCal.getTime());
                String leaveStartDateInGC = GregorianCalendarManipulation.ethiopianToGregorian(startDate);
                
                leaveStartCal.setTime(dateformatter.parse(leaveStartDateInGC));
                /* get holiday dates*/
                hrLeaveSetting = settingBeanLocal.findLeaveSettingByLeaveType(hrLuLeaveTypes);
                Date startDates = leaveStartCal.getTime();
                String stringGcDate = calculateWorkingLastDate(startDates, noReqDays, hrLeaveSetting.isIncludeHollyday(), hrLeaveSetting.isIncludeSat(), hrLeaveSetting.isIncludeSun());
                
                String strinEndDate = GregorianCalendarManipulation.gregorianToEthiopian(stringGcDate);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = dateformatter.parse(strinEndDate);
                String leaveEnd = df.format(date);
                leaveRequest.setLeaveEnd(leaveEnd);
            } else {
                JsfUtil.addFatalMessage("Invalid Number of Date!");
            }
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Invalid Date Format!");
        }
        
    }
    //<editor-fold defaultstate="collapsed" desc="save leave request method">
    
    Constants constants = new Constants();
    
    public void saveLeaveRequest() {
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveRequest", dataset)) {
//                 put ur code here...!
                
                leaveRequest.setEmpId(employee);
                if (leaveRequest.getLeaveEnd() == null) {
                    calculateReturnDateLeaveRequest();
                }
                if (employee.getEmpStatus() == 1) {
                    if ((leaveRequest.getRequestedDays() <= totalLeaveDays && leaveRequest.getRequestedDays() > 0) || (leaveRequest.getRequestedDays() <= maxiNumberOfDays && leaveRequest.getRequestedDays() > 0)) {
                        
                        leaveRequest.setStatus1(Constants.PREPARE_VALUE);
                        leaveRequest.setUnUsedDays(0);
                        leaveRequest.setApprovedDays(0);
                        leaveRequest.setEmpConfirm("Ok");
                        leaveRequest.setReturnStatus(0);
                        leaveRequest.setStatus2(0);
                        wfHrProcessed.setLeaveId(leaveRequest);
                        wfHrProcessed.setDecision(leaveRequest.getStatus1());
                        
                        requestBeanLocal.saveOrUpdate(leaveRequest);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        
                        if (updates == 0) {
                            JsfUtil.addSuccessMessage("Saved Successful.");
                        } else {
                            JsfUtil.addSuccessMessage("Updated Successful.");
                        }
                        
                        clearLeaveRequest();
                    } else {
                        JsfUtil.addFatalMessage("There is no sufficient allowed days for the requested leave!");
                    }
                } else if (employee.getEmpStatus() == 2) {
                    JsfUtil.addFatalMessage(employee.getEmpId() + " is already on leave!");
                } else if (employee.getEmpStatus() == 3) {
                    JsfUtil.addFatalMessage(employee.getEmpId() + " is resigned!");
                } else if (employee.getEmpStatus() == 4) {
                    JsfUtil.addFatalMessage(employee.getEmpId() + " is retired!");
                } else if (employee.getEmpStatus() == 5) {
                    JsfUtil.addFatalMessage(employee.getEmpId() + " is traitor!");
                } else if (employee.getEmpStatus() == 6) {
                    JsfUtil.addFatalMessage(employee.getEmpId() + " is on probation period!");
                } else {
                    JsfUtil.addFatalMessage(employee.getEmpId() + " Employee status is unknown!");
                }
                
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
            JsfUtil.addFatalMessage("Error Occured");
        }
    }
//</editor-fold>
    
    public void handleSelectSearch(SelectEvent event) {
        String leacecode = event.getObject().toString();
        hrLeaveSetting.setPaymentCode(leacecode);
        hrLeaveSetting = settingBeanLocal.findLeaveTypeByCode(hrLeaveSetting);
        hrLuLeaveTypes = hrLeaveSetting.getLeaveTypeId();
        updates = 0;
    }
    
    public static Integer getDuration(int month, int day, int year) throws Exception {
        /* input calendar date */
        month--; // following the 0-based rule
        Calendar cal = new GregorianCalendar(year, month, day);
        
        /* date today */
        java.util.Date today = new java.util.Date();
        
        /* year now */
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        int intYear = Integer.parseInt(sdfYear.format(today));
        
        /* month now */
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        int intMonth = Integer.parseInt(sdfMonth.format(today));
        intMonth--; // following the 0-based rule
        
        /* day now */
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        int intDay = Integer.parseInt(sdfDay.format(today));
        
        /* calendar date now */
        Calendar now = new GregorianCalendar(intYear, intMonth, intDay);
        
        /* years duration */
        int yyyy = intYear - year;
        
        /* array of days in 12 months */
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        /*
        * an indicator if given date has already occurred. (-1) - if given date
        * is greater than the current date; 0 - if given date has not yet
        * occurred or if it's equal to the current date.
        */
        int factor = 0;
        
        int mm = 0; // month duration
        
        int dd = 0; // day duration
        
        /* determine if given date is greater than or equal to the current date */
        if ((month > intMonth) || (month == intMonth && day > intDay)) {
            factor = -1;
            yyyy += factor;
        }
        
        /* throw if any of the following exceptions occur */
        if (month > 12) {
            throw new Exception("Invalid input month");
        } else if (day > months[month]) {
            throw new Exception("Invalid input day");
        } else if (yyyy < 0) {
            throw new Exception("Invalid input date");
        }
        
        /*
        * if the given date has already passed or if it's equal to the current
        * date
        */
        if (factor == 0) {
            // compute for days in between the given and the current date
            dd = now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
            /* determine if the current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
            * compute for day duration and month duration between the given
            * date and the given day of the current month
            */
            for (int i = month; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
            * if computed month duration is more than 12, finalize values for
            * year and month duration
            */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        } else { // if the given date is greater than the current date
            intYear--; // derive previous year
            /* set Calendar date for December 31 of the previous year */
            Calendar prev = new GregorianCalendar(intYear, 11, 31);
            /*
            * compute the days in between the given date last year and the
            * current date
            */
            dd = (prev.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) + now.get(Calendar.DAY_OF_YEAR);
            /* determine if previous year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
            * compute for day duration and month duration between the given
            * date and the given day in December of the previous year
            */
            for (int i = month; i <= 11; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            intYear++; // set the value back to the current year
            /* determine if current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
            * compute for day duration and month duration between the given day
            * in January of the current year and the current date
            */
            for (int i = 0; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
            * if computed month duration is more than 12, finalize values for
            * year and month duration
            */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        }
        
        /* computed duration in years, months and days */
        //return yyyy + " Year(s), " + mm + " Month(s), and " + dd + " Day(s) ";
        return yyyy;
    }
    
    private HrLeaveRequest selectedCriteria;
    
    public HrLeaveRequest getSelectedCriteria() {
        return selectedCriteria;
    }
    
    public void setSelectedCriteria(HrLeaveRequest selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
    }
    
    public void populateLeaveRequest(SelectEvent events) {
        leaveRequest = new HrLeaveRequest();
        leaveRequest = (HrLeaveRequest) events.getObject();
        if (leaveRequest.getStatus1() == 1) {
            renderSave = true;
        } else {
            renderSave = false;
        }
        updates = 1;
        saveOrUpdateButton = "Update";
        recreateWorkflowDataModel();
    }
    
    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(leaveRequest.getHrWorkFlowLeaveList()));
        System.out.println("---workflowDataModel--" + workflowDataModel);
    }
//</editor-fold>
}
