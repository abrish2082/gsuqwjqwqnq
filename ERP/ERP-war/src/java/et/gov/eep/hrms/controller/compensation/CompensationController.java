/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.compensation;

import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.commonApplications.utility.number.NumAndDateFormatter;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInJobTitleBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollTaxRatesBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.Payroll_TaxRate_Logic;
import et.gov.eep.hrms.businessLogic.termination.ClearanceBeanLocal;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;
import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.mapper.leave.HrLeaveBalanceFacade;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 *
 * @author kumar
 */
@Named("compensationControllers")
@ViewScoped
public class CompensationController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB declaration">
    @EJB
    HrLeaveBalanceFacade hrLeaveBalanceFacade;
    @EJB
    HrAllowanceInJobTitleBeanLocal hrAllowanceInJobTitleBeanLocal;
    @EJB
    ClearanceBeanLocal clearanceBeanLocal;
    @EJB
    private Payroll_TaxRate_Logic taxRate_Logic;
    @EJB
    HrPayrollTaxRatesBeanLocal taxRateFacade;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Entity Enjections">
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
    HrPayrollTaxRates taxRate;
    @Inject
    HrTerminationRequests hrTerminationRequest;
    @Inject
    private HrClearance hrClearance;
    @Inject
    HrAllowanceInJobTitle hrAllowanceInJobTitle;
    @Inject
    private HrEmployees hrEmployees;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List Initialization">
    List<HrPayrollTaxRates> taxRateList = new ArrayList<>();
    private List<HrTerminationRequests> terminationRequestList;
    private List<HrTerminationRequests> terminnReqstForCompenstion = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    String serviceYear;
    String terminationDate = "";
    Double compValue = 0.0;
    Double taxForSeverance = 0.0;
    Double netPay = 0.0;
    int currentMonthDateSalary;
    String termination_Date = "";
    int modulusSalaryRate = 0;
//    int modulusConstantValue = 0;
    Double modulusConstantValue = 0d;//Getnet change it becaue of change in data type to douple in constant amount
    int normalSalaryRate = 0;
    Double normalConstantValue = 0d;  //Getnet change it becaue of change in data type to douple in constant amount
    Double currentMonthSalary = 0.0;
    Double annulLeavePymt = 0.0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post Construct">
    @PostConstruct
    public void _init() {
        chechMinExprience();
        taxRateList = taxRateFacade.findAll();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter & setter">
    public HrAllowanceInJobTitle getHrAllowanceInJobTitle() {
        return hrAllowanceInJobTitle;
    }

    /**
     *
     * @param hrAllowanceInJobTitle
     */
    public void setHrAllowanceInJobTitle(HrAllowanceInJobTitle hrAllowanceInJobTitle) {
        this.hrAllowanceInJobTitle = hrAllowanceInJobTitle;
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
    
    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }
    
    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }
    
    public HrTerminationRequests getHrTerminationRequest() {
        if (hrTerminationRequest == null) {
            hrTerminationRequest = new HrTerminationRequests();
        }
        return hrTerminationRequest;
    }
    
    public void setHrTerminationRequest(HrTerminationRequests hrTerminationRequest) {
        this.hrTerminationRequest = hrTerminationRequest;
    }
    
    public List<HrPayrollTaxRates> getTaxRateList() {
        return taxRateList;
    }
    
    public void setTaxRateList(List<HrPayrollTaxRates> taxRateList) {
        this.taxRateList = taxRateList;
    }
    
    public HrPayrollTaxRates getTaxRate() {
        if (taxRate == null) {
            taxRate = new HrPayrollTaxRates();
        }
        return taxRate;
    }
    
    public void setTaxRate(HrPayrollTaxRates taxRate) {
        this.taxRate = taxRate;
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
    
    public HrClearance getHrClearance() {
        if (hrClearance == null) {
            hrClearance = new HrClearance();
        }
        return hrClearance;
    }
    
    public void setHrClearance(HrClearance hrClearance) {
        this.hrClearance = hrClearance;
    }
    
    public List<HrTerminationRequests> getTerminnReqstForCompenstion() {
        return terminnReqstForCompenstion;
    }
    
    public void setTerminnReqstForCompenstion(List<HrTerminationRequests> terminnReqstForCompenstion) {
        this.terminnReqstForCompenstion = terminnReqstForCompenstion;
    }
    
    public List<HrTerminationRequests> getTerminationRequestList() {
        
        return terminationRequestList;
    }
    
    public void setTerminationRequestList(List<HrTerminationRequests> terminationRequestList) {
        this.terminationRequestList = terminationRequestList;
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter for variable">

    public Double getAnnulLeavePymt() {
        return annulLeavePymt;
    }
    
    public void setAnnulLeavePymt(Double annulLeavePymt) {
        this.annulLeavePymt = annulLeavePymt;
    }
    
    public String getServiceYear() {
        return serviceYear;
    }
    
    public void setServiceYear(String serviceYear) {
        this.serviceYear = serviceYear;
    }
    
    public Double getNetPay() {
        return netPay;
    }
    
    public void setNetPay(Double netPay) {
        this.netPay = netPay;
    }
    
    public Double getCompValue() {
        return compValue;
    }
    
    public Double getTaxForSeverance() {
        return taxForSeverance;
    }
    
    public void setTaxForSeverance(Double taxForSeverance) {
        this.taxForSeverance = taxForSeverance;
    }
    
    public void setCompValue(Double compValue) {
        this.compValue = compValue;
    }
    
    public int getCurrentMonthDateSalary() {
        return currentMonthDateSalary;
    }
    
    public void setCurrentMonthDateSalary(int currentMonthDateSalary) {
        this.currentMonthDateSalary = currentMonthDateSalary;
    }
    
    public String getTermination_Date() {
        return termination_Date;
    }
    
    public void setTermination_Date(String termination_Date) {
        this.termination_Date = termination_Date;
    }
    
    public Double getCurrentMonthSalary() {
        return currentMonthSalary;
    }
    
    public void setCurrentMonthSalary(Double currentMonthSalary) {
        this.currentMonthSalary = currentMonthSalary;
    }

//</editor-fold>
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main methods ">
    public void chechMinExprience() {
        
        try {
            terminationRequestList = clearanceBeanLocal.findApprovedTermination();
            for (int i = 0; i < terminationRequestList.size(); i++) {
                HrTerminationRequests get = terminationRequestList.get(i);
                terminationDate = get.getTerminationDate();
                
                String hireDate = get.getEmpId().getHireDate();
                int fiveYearExprience = 365 * 5;
                int datdiff = StringDateManipulation.datesDifferenceInDays(hireDate, terminationDate);
                
                if (datdiff >= fiveYearExprience) {
                    terminnReqstForCompenstion.add(get);
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    NumAndDateFormatter df = new NumAndDateFormatter();
    
    Double netPayForSeverance = 0.0;
    
    public Double getNetPayForSeverance() {
        return netPayForSeverance;
    }
    
    public void setNetPayForSeverance(Double netPayForSeverance) {
        this.netPayForSeverance = netPayForSeverance;
    }
    
    public void handleTermination(ValueChangeEvent event) throws ParseException {
        hrTerminationRequest = (HrTerminationRequests) event.getNewValue();
        if (hrTerminationRequest != null) {
            hrEmployees = hrTerminationRequest.getEmpId();
            
            termination_Date = hrTerminationRequest.getTerminationDate();
            
            String terminationsDate[] = termination_Date.split("-");
            
            String hireYearMonthDate[] = hrEmployees.getHireDate().split("-");
            /* to calc service year */
            int _hireYear = Integer.parseInt(hireYearMonthDate[0]);
            int _hireMonth = Integer.parseInt(hireYearMonthDate[1]);
            int _hireDate = Integer.parseInt(hireYearMonthDate[2]);
            
            int terminationYear = Integer.parseInt(terminationsDate[0]);
            int terminationMonth = Integer.parseInt(terminationsDate[1]);
            int terminationDate = Integer.parseInt(terminationsDate[2]);
            
            currentMonthDateSalary = Integer.parseInt(terminationsDate[2]);
            int expMonth, expDate, expYear;
            
            if (terminationDate > _hireDate) {
                expDate = terminationDate - _hireDate;
            } else {
                terminationDate += 30;
                expDate = terminationDate - _hireDate;
                terminationMonth = terminationMonth - 1;
                
            }
            if (terminationMonth > _hireMonth) {
                expMonth = terminationMonth - _hireMonth;
                expYear = terminationYear - _hireYear;
            } else {
                terminationMonth = terminationMonth + 12;
                expMonth = terminationMonth - _hireMonth;
                terminationYear = terminationYear - 1;
                expYear = terminationYear - _hireYear;
            }
            /* to calc employee service year  */
            serviceYear = String.valueOf(expYear) + " Years " + String.valueOf(expMonth) + " Months And " + String.valueOf(expDate) + " Days";
            /* to calc total serevance  */
            compValue = df.decimalFormat(hrEmployees.getBasicSalary() + (expYear - 1) * hrEmployees.getBasicSalary() / 3
                    + (expMonth * hrEmployees.getBasicSalary() / 3) / 12
                    + ((hrEmployees.getBasicSalary() * expDate) / 3) / 365);
            
            Double salaryModulus = compValue % hrEmployees.getBasicSalary();
            int howManyMonth = (int) (compValue / hrEmployees.getBasicSalary());

            /* to get tax rate & constant value for normal salary */
            for (int i = 0; i < taxRateList.size(); i++) {
                taxRate = taxRateList.get(i);
                if (hrEmployees.getBasicSalary() < taxRate.getToAmount() && hrEmployees.getBasicSalary() >= taxRate.getFromAmount()) {
                    normalSalaryRate = taxRateList.get(i).getRateInPerercent();
                    normalConstantValue = taxRateList.get(i).getConstantAmount();
                    i = taxRateList.size() + 1;
                    
                }
            }
            /* to get tax rate & constant value for modulus */
            for (int j = 0; j < taxRateList.size(); j++) {
                taxRate = taxRateList.get(j);
                if (salaryModulus < taxRate.getToAmount() && salaryModulus > taxRate.getFromAmount()) {
                    modulusSalaryRate = taxRate.getRateInPerercent();
                    modulusConstantValue = taxRate.getConstantAmount();
                    j = taxRateList.size() + 1;
                    
                }
            }
            /* to calc tax*/
            taxForSeverance = df.decimalFormat(howManyMonth * ((hrEmployees.getBasicSalary() * normalSalaryRate / 100) - (normalConstantValue)) + (salaryModulus * (modulusSalaryRate) / 100) - (modulusConstantValue));
            /* to calc net payment*/
            
            calcMonthlySalary();
            calAllowance();
            loadBalance();
            alWorkingToLeaveDay();
            netPayForSeverance = df.decimalFormat((compValue - taxForSeverance));
            netPay = df.decimalFormat(netPayForSeverance + annulLeavePymt + netPayForLastMonth);
            
        } else {
            compValue = 0.0;
            serviceYear = "";
            termination_Date = "";
            taxForSeverance = 0.0;
            netPay = 0.0;
            totalLeaveDays = 0;
            currentMonthSalary = 0.0;
            hrAllowanceInJobTitle = null;
            hrEmployees = null;
            hrTerminationRequest = null;
            netPayForSeverance = 0.0;
            annulLeavePymt = 0.0;
            taxforAL = 0.0;
            pension = 0.0;
            alEarning = 0.0;
            leaveDaysForAl = 0;
            netPayForLastMonth = 0.0;
            taxforLastMonth = 0.0;
            pensionForLastMonth = 0.0;
            
        }
        
    }

//<editor-fold defaultstate="collapsed" desc="Reset">
    public void reset() {
        compValue = 0.0;
        serviceYear = "";
        termination_Date = "";
        taxForSeverance = 0.0;
        netPay = 0.0;
        totalLeaveDays = 0;
        currentMonthSalary = 0.0;
        hrAllowanceInJobTitle = null;
        hrEmployees = null;
        hrTerminationRequest = null;
        netPayForSeverance = 0.0;
        annulLeavePymt = 0.0;
        taxforAL = 0.0;
        pension = 0.0;
        alEarning = 0.0;
        leaveDaysForAl = 0;
        netPayForLastMonth = 0.0;
        taxforLastMonth = 0.0;
        pensionForLastMonth = 0.0;
    }
//</editor-fold>
    
    public void calAllowance() {
        hrJobTypes = hrEmployees.getJobId();
        hrAllowanceInJobTitle = hrAllowanceInJobTitleBeanLocal.findByJobTitleId(hrJobTypes);
        if (hrAllowanceInJobTitle != null) {
            Double allowanceAmount = Double.valueOf(hrAllowanceInJobTitle.getAmount());
        }
        
    }

//<editor-fold defaultstate="collapsed" desc="Load Al balance">
    double totalLeaveDays = 0;
    
    public double getTotalLeaveDays() {
        return totalLeaveDays;
    }
    
    public void setTotalLeaveDays(double totalLeaveDays) {
        this.totalLeaveDays = totalLeaveDays;
    }
    
    List<HrLeaveBalance> balanceList = new ArrayList<>();
    
    public List<HrLeaveBalance> getBalanceList() {
        return balanceList;
    }
    
    public void setBalanceList(List<HrLeaveBalance> balanceList) {
        this.balanceList = balanceList;
    }

//    public void loadLeaveYear() {
//        accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
//    }
    public void loadBalance() {
        if (hrEmployees != null) {
//            loadLeaveYear();
            balanceList = hrLeaveBalanceFacade.populateLeaveBalanceForComp(hrEmployees);
            recreateBalanceDataModel();
        }
    }
    
    public void recreateBalanceDataModel() {
        int x = balanceList.size();
        if (x > 0) {
            totalLeaveDays = 0;
            for (int i = 0; i < x; i++) {
                totalLeaveDays = totalLeaveDays + balanceList.get(i).getRemainingDays();
            }
        } else {
            totalLeaveDays = 0;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inner variables ">
    int leaveDaysForAl = 0;
    Double taxforAL = 0.0;
    Double pension = 0.0;
    Double alEarning = 0.0;
    Double taxforLastMonth = 0.0;
    Double pensionForLastMonth = 0.0;
    Double netPayForLastMonth = 0.0;

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public Double getAlEarning() {
        return alEarning;
    }
    
    public void setAlEarning(Double alEarning) {
        this.alEarning = alEarning;
    }
    
    public Double getPension() {
        return pension;
    }

    public Double getNetPayForLastMonth() {
        return netPayForLastMonth;
    }
    
    public void setNetPayForLastMonth(Double netPayForLastMonth) {
        this.netPayForLastMonth = netPayForLastMonth;
    }
    
    public Double getPensionForLastMonth() {
        return pensionForLastMonth;
    }
    
    public void setPensionForLastMonth(Double pensionForLastMonth) {
        this.pensionForLastMonth = pensionForLastMonth;
    }
    
    public Double getTaxforLastMonth() {
        return taxforLastMonth;
    }
    
    public void setTaxforLastMonth(Double taxforLastMonth) {
        this.taxforLastMonth = taxforLastMonth;
    }
    
    public void setPension(Double pension) {
        this.pension = pension;
    }
    
    public Double getTaxforAL() {
        return taxforAL;
    }
    
    public void setTaxforAL(Double taxforAL) {
        this.taxforAL = taxforAL;
    }
    
    public int getLeaveDaysForAl() {
        return leaveDaysForAl;
    }
    
    public void setLeaveDaysForAl(int leaveDaysForAl) {
        this.leaveDaysForAl = leaveDaysForAl;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Annual Leave of Working To LeaveDay">
    public Date alWorkingToLeaveDay() throws ParseException {
        int countWeekend = 0;
        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        
        String date = terminationDate;
        try {
            
            String dateOfTermtionInGC = GregorianCalendarManipulation.ethiopianToGregorian(terminationDate);
            
            Date dateInGc = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfTermtionInGC);
            
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(dateInGc);
            for (int i = 0; i < totalLeaveDays; i++) {
                
                if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    countWeekend = countWeekend + 1;
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                } else {
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    
                }
                
            }
            startCal.add(Calendar.DAY_OF_MONTH, countWeekend);
            Date endDate = startCal.getTime();
            String enddDate = dateformatter.format(endDate);
            String strinEndDate = GregorianCalendarManipulation.gregorianToEthiopian(enddDate);
            leaveDaysForAl = StringDateManipulation.datesDifferenceInDays(terminationDate, strinEndDate);
//            int alToMonth = 0;
//            int aLToDate = 0;
            if (leaveDaysForAl != 0) {
                int aLToDate = leaveDaysForAl % 30;
                int alToMonth = leaveDaysForAl / 30;
                Double aLpaymenforMonth = alToMonth * hrEmployees.getBasicSalary();
                Double aLPaymentforDate = aLToDate * hrEmployees.getBasicSalary() / 30;
                alEarning = df.decimalFormat(aLpaymenforMonth + aLPaymentforDate);

                /* to get tax rate & constant value for modulus */
                for (int k = 0; k < taxRateList.size(); k++) {
                    taxRate = taxRateList.get(k);
                    if (aLPaymentforDate < taxRate.getToAmount() && aLPaymentforDate > taxRate.getFromAmount()) {
                        int salaryRateForALDate = taxRate.getRateInPerercent();
//                        int constantValueForALDate = taxRate.getConstantAmount();
                        Double constantValueForALDate = taxRate.getConstantAmount();           //Getnet change it becaue of change in data type to douple in constant amount
                        taxforAL = alToMonth * ((hrEmployees.getBasicSalary() * normalSalaryRate / 100) - (normalConstantValue)) + (aLToDate * (salaryRateForALDate) / 100) - (constantValueForALDate);
                        
                        k = taxRateList.size() + 1;
                        
                    }
                }
                /* pension for AL,*/
                pension = df.decimalFormat((alToMonth * (7 * hrEmployees.getBasicSalary() / 100))
                        + ((7 * hrEmployees.getBasicSalary()) / 100 * aLToDate) / 30);
                annulLeavePymt = df.decimalFormat((alEarning - taxforAL) - pension);
            }
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Invalid Date Format!");
            e.printStackTrace();
        }
        
        return null;
    }
//</editor-fold>
    
    public void calcMonthlySalary() {
        currentMonthSalary = df.decimalFormat(currentMonthDateSalary * hrEmployees.getBasicSalary() / 30);
        int currentMonthSalaryRate = 0;
//        int currentMonthConstantValue = 0;//Getnet changes it due to change in the constant amount in the payroll
        Double currentMonthConstantValue = 0d;
        for (int j = 0; j < taxRateList.size(); j++) {
            taxRate = taxRateList.get(j);
            if (currentMonthSalary < taxRate.getToAmount() && currentMonthSalary > taxRate.getFromAmount()) {
                currentMonthSalaryRate = taxRate.getRateInPerercent();
                currentMonthConstantValue = taxRate.getConstantAmount();
                j = taxRateList.size() + 1;
                
            }
        }
        taxforLastMonth = df.decimalFormat(((currentMonthSalary * currentMonthSalaryRate) / 100) - currentMonthConstantValue);
        pensionForLastMonth = df.decimalFormat((7 * hrEmployees.getBasicSalary() / 100 * currentMonthDateSalary / 30));
        netPayForLastMonth = df.decimalFormat((currentMonthSalary - taxforLastMonth) - pensionForLastMonth);
    }
//</editor-fold>
}
