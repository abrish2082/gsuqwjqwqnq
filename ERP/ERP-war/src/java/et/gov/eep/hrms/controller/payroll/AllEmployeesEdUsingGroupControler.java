/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuPayrollAePGroupBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrSalaryScaleRangesBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainEdsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.hrms.mapper.organization.HrJobTypesFacade;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "allEmployeesEdUsingGroupControler")
@ViewScoped
public class AllEmployeesEdUsingGroupControler implements Serializable {

    /**
     * Creates a new instance of AllEmployeesEdUsingGroupControler
     */
    public AllEmployeesEdUsingGroupControler() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrLuPayrollAePGroup hrLuPayrollAePGroup;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    HrPayrollEarningDeductions earningDeductionToSearch;
    @Inject
    HrPayrollMaintainEds hrPayrollMaintainEds;
    @Inject
    HrPayrollMaintainEds checkForUpdate;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;
    @EJB
    HrLuPayrollAePGroupBeanLocal hrLuPayrollAePGroupBeanLocal;
    @EJB
    HrJobTypesFacade hrJobTypesFacade;
    @EJB
    HrAllowanceInLevelsBeanLocal hrAllowanceInLevelsBeanLocal;
    @EJB
    HrPayrollMaintainEdsBeanLocal hrPayrollMaintainEdsFacade;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsFacade1;
    @EJB
    HrSalaryScaleRangesBeanLocal hrSalaryScaleRangesBeanLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;

    private String paymentType;
    private String respectiveAmount = "0";
    private List<HrPayrollPeriods> payrollFrom;
    private String code = null;
    private String appliedFrom;
    private String carryForward;
    private String salaryScalseCode;
    private String jobTitleId;
    private String activePayrollDate;
    private boolean isEarning;
    private boolean isDeduction;
    private boolean disChkEarnings = false;
    private boolean disChkDeduction = false;
    private boolean disEarningDeduction = false;
    private boolean disAppliedFrom = false;
    private boolean disAppliedTo = false;
    private boolean disTotal = false;
    private boolean disPaymentIn = false;
    private boolean disMonPaymet = false;
    private boolean disStatus = false;
    private boolean disCarryForward = false;
    private boolean disRespectiveValue = false;
    private boolean disRemark = false;
    private List<HrSalaryScaleRanges> listOfSalaryScaleRanges;
    private List<HrJobTypes> listOfJobTypes;
    private String numberOfMonth;
    private List<HrEmployees> listOfEmployees;
    private List<HrEmployees> listOfGroupedEmployees;
    private List<HrEmployees> listOfEmpForPayment;
    private List<HrEmployees> listOfUngroupedSelectedEmployees;
    private List<HrEmployees> listOfSelectedGroupedEmployees;
    private List<HrPayrollMaintainEds> listOfSelectedGroupEmp;
    private List<HrPayrollMaintainEds> listOfGroupedAllEmpEd;
    private List<HrPayrollMaintainEds> listOfUngroupedEmployees;
    private List<HrPayrollMaintainEds> listOfSelectedUngroupedEmployees;
    private List<HrEmployees> filterBk;
    private List<HrPayrollFilterBp> selectedBKPayments;
    private List<HrLuPayrollAePGroup> listOfPayrollGroups;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void _init() {
        listOfPayrollGroups = hrLuPayrollAePGroupBeanLocal.findAll();
        if (hrPayrollPeriodsLocal.activePayrollDate() != null) {
            hrPayrollPeriods = hrPayrollPeriodsLocal.activePayrollDate();
            activePayrollDate = hrPayrollPeriods.getPaymentMadeForTheMonthOf();
        } else {
            activePayrollDate = "[No Active Date is Defined]";
        }
        hrPayrollMaintainEds.setRespectivePaymentTypeAmt(BigInteger.ZERO);
        hrPayrollMaintainEds.setMonthlyAmount(0.0);
        payrollFrom = hrPayrollPeriodsLocal.findAll();//this is for back payment
//        listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployees(hrLuPayrollAePGroup);
//        listOfGroupedEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    /**
     *
     * @return
     */
    public List<HrSalaryScaleRanges> getListOfSalaryScaleRanges() {
        listOfSalaryScaleRanges = hrSalaryScaleRangesBeanLocal.findAll();
        return listOfSalaryScaleRanges;
    }

    /**
     *
     * @param listOfSalaryScaleRanges
     */
    public void setListOfSalaryScaleRanges(List<HrSalaryScaleRanges> listOfSalaryScaleRanges) {
        this.listOfSalaryScaleRanges = listOfSalaryScaleRanges;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getSalaryScalseCode() {
        return salaryScalseCode;
    }

    public void setSalaryScalseCode(String salaryScalseCode) {
        this.salaryScalseCode = salaryScalseCode;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public List<HrJobTypes> getListOfJobTypes() {
        listOfJobTypes = hrJobTypesFacade.findAll();
        return listOfJobTypes;
    }

    public void setListOfJobTypes(List<HrJobTypes> listOfJobTypes) {
        this.listOfJobTypes = listOfJobTypes;
    }

    public String getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(String numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public List<HrPayrollMaintainEds> getListOfUngroupedEmployees() {
        return listOfUngroupedEmployees;
    }

    public void setListOfUngroupedEmployees(List<HrPayrollMaintainEds> listOfUngroupedEmployees) {
        this.listOfUngroupedEmployees = listOfUngroupedEmployees;
    }

    public List<HrPayrollMaintainEds> getListOfSelectedUngroupedEmployees() {
        return listOfSelectedUngroupedEmployees;
    }

    public void setListOfSelectedUngroupedEmployees(List<HrPayrollMaintainEds> listOfSelectedUngroupedEmployees) {
        this.listOfSelectedUngroupedEmployees = listOfSelectedUngroupedEmployees;
    }

    public List<HrPayrollMaintainEds> getListOfGroupedAllEmpEd() {
        return listOfGroupedAllEmpEd;
    }

    public void setListOfGroupedAllEmpEd(List<HrPayrollMaintainEds> listOfGroupedAllEmpEd) {
        this.listOfGroupedAllEmpEd = listOfGroupedAllEmpEd;
    }

    public List<HrPayrollMaintainEds> getListOfSelectedGroupEmp() {
        return listOfSelectedGroupEmp;
    }

    public void setListOfSelectedGroupEmp(List<HrPayrollMaintainEds> listOfSelectedGroupEmp) {
        this.listOfSelectedGroupEmp = listOfSelectedGroupEmp;
    }

    public List<HrEmployees> getListOfSelectedGroupedEmployees() {
        return listOfSelectedGroupedEmployees;
    }

    public void setListOfSelectedGroupedEmployees(List<HrEmployees> listOfSelectedGroupedEmployees) {
        this.listOfSelectedGroupedEmployees = listOfSelectedGroupedEmployees;
    }

    public List<HrEmployees> getListOfUngroupedSelectedEmployees() {
        return listOfUngroupedSelectedEmployees;
    }

    public void setListOfUngroupedSelectedEmployees(List<HrEmployees> listOfUngroupedSelectedEmployees) {
        this.listOfUngroupedSelectedEmployees = listOfUngroupedSelectedEmployees;
    }

    public List<HrEmployees> getFilterBk() {
        return filterBk;
    }

    public void setFilterBk(List<HrEmployees> filterBk) {
        this.filterBk = filterBk;
    }

    public List<HrPayrollFilterBp> getSelectedBKPayments() {
        return selectedBKPayments;
    }

    public void setSelectedBKPayments(List<HrPayrollFilterBp> selectedBKPayments) {
        this.selectedBKPayments = selectedBKPayments;
    }

    public List<HrEmployees> getListOfEmpForPayment() {
        return listOfEmpForPayment;
    }

    public void setListOfEmpForPayment(List<HrEmployees> listOfEmpForPayment) {
        this.listOfEmpForPayment = listOfEmpForPayment;
    }

    public List<HrEmployees> getListOfGroupedEmployees() {
        return listOfGroupedEmployees;
    }

    public void setListOfGroupedEmployees(List<HrEmployees> listOfGroupedEmployees) {
        this.listOfGroupedEmployees = listOfGroupedEmployees;
    }

    public List<HrEmployees> getListOfEmployees() {
        return listOfEmployees;
    }

    public void setListOfEmployees(List<HrEmployees> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }

    public HrLuPayrollAePGroup getHrLuPayrollAePGroup() {
        if (hrLuPayrollAePGroup == null) {
            hrLuPayrollAePGroup = new HrLuPayrollAePGroup();
        }
        return hrLuPayrollAePGroup;
    }

    public void setHrLuPayrollAePGroup(HrLuPayrollAePGroup hrLuPayrollAePGroup) {
        this.hrLuPayrollAePGroup = hrLuPayrollAePGroup;
    }

    public List<HrLuPayrollAePGroup> getListOfPayrollGroups() {
        return listOfPayrollGroups;
    }

    public void setListOfPayrollGroups(List<HrLuPayrollAePGroup> listOfPayrollGroups) {
        this.listOfPayrollGroups = listOfPayrollGroups;
    }

    public boolean isIsEarning() {
        return isEarning;
    }

    public void setIsEarning(boolean isEarning) {
        this.isEarning = isEarning;
    }

    public boolean isIsDeduction() {
        return isDeduction;
    }

    public void setIsDeduction(boolean isDeduction) {
        this.isDeduction = isDeduction;
    }

    public String getActivePayrollDate() {
        return activePayrollDate;
    }

    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }

    public HrPayrollMaintainEds getCheckForUpdate() {
        if (checkForUpdate == null) {
            checkForUpdate = new HrPayrollMaintainEds();
        }
        return checkForUpdate;
    }

    public void setCheckForUpdate(HrPayrollMaintainEds checkForUpdate) {
        this.checkForUpdate = checkForUpdate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    private String lableValue = "Birr:";

    public String getLableValue() {
        return lableValue;
    }

    public void setLableValue(String lableValue) {
        this.lableValue = lableValue;
    }

    public String getRespectiveAmount() {
        return respectiveAmount;
    }

    public void setRespectiveAmount(String respectiveAmount) {
        this.respectiveAmount = respectiveAmount;
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public HrPayrollMaintainEds getHrPayrollMaintainEds() {

        if (hrPayrollMaintainEds == null) {
            hrPayrollMaintainEds = new HrPayrollMaintainEds();
        }
        return hrPayrollMaintainEds;
    }

    public void setHrPayrollMaintainEds(HrPayrollMaintainEds hrPayrollMaintainEds) {
        this.hrPayrollMaintainEds = hrPayrollMaintainEds;
    }

    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {
        if (hrPayrollEarningDeductions == null) {
            hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        }
        return hrPayrollEarningDeductions;
    }

    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void handleChange(ValueChangeEvent event) {
        code = returnId(event.getNewValue().toString());

    }

    public String getCarryForward() {
        return carryForward;
    }

    public void setCarryForward(String carryForward) {
        this.carryForward = carryForward;
    }

    public String getAppliedFrom() {
        return appliedFrom;
    }

    public void setAppliedFrom(String appliedFrom) {
        this.appliedFrom = appliedFrom;
    }
    private Double total;

    private Double monthlyPayment;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    public boolean isDisRemark() {
        return disRemark;
    }

    public void setDisRemark(boolean disRemark) {
        this.disRemark = disRemark;
    }

    public boolean isDisChkEarnings() {
        return disChkEarnings;
    }

    public void setDisChkEarnings(boolean disChkEarnings) {
        this.disChkEarnings = disChkEarnings;
    }

    public boolean isDisChkDeduction() {
        return disChkDeduction;
    }

    public void setDisChkDeduction(boolean disChkDeduction) {
        this.disChkDeduction = disChkDeduction;
    }

    public boolean isDisEarningDeduction() {
        return disEarningDeduction;
    }

    public void setDisEarningDeduction(boolean disEarningDeduction) {
        this.disEarningDeduction = disEarningDeduction;
    }

    public boolean isDisAppliedFrom() {
        return disAppliedFrom;
    }

    public void setDisAppliedFrom(boolean disAppliedFrom) {
        this.disAppliedFrom = disAppliedFrom;
    }

    public boolean isDisAppliedTo() {
        return disAppliedTo;
    }

    public void setDisAppliedTo(boolean disAppliedTo) {
        this.disAppliedTo = disAppliedTo;
    }

    public boolean isDisPaymentIn() {
        return disPaymentIn;
    }

    public void setDisPaymentIn(boolean disPaymentIn) {
        this.disPaymentIn = disPaymentIn;
    }

    public boolean isDisMonPaymet() {
        return disMonPaymet;
    }

    public void setDisMonPaymet(boolean disMonPaymet) {
        this.disMonPaymet = disMonPaymet;
    }

    public boolean isDisStatus() {
        return disStatus;
    }

    public void setDisStatus(boolean disStatus) {
        this.disStatus = disStatus;
    }

    public boolean isDisCarryForward() {
        return disCarryForward;
    }

    public void setDisCarryForward(boolean disCarryForward) {
        this.disCarryForward = disCarryForward;
    }

    public boolean isDisRespectiveValue() {
        return disRespectiveValue;
    }

    public void setDisRespectiveValue(boolean disRespectiveValue) {
        this.disRespectiveValue = disRespectiveValue;
    }

    public boolean isDisTotal() {
        return disTotal;
    }

    public void setDisTotal(boolean disTotal) {
        this.disTotal = disTotal;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void loadActiveDate() {
        if (hrPayrollPeriodsLocal.activePayrollDate() != null) {
            hrPayrollPeriods = hrPayrollPeriodsLocal.activePayrollDate();
            activePayrollDate = hrPayrollPeriods.getPaymentMadeForTheMonthOf();
        }
    }

    public void loadEarnings() {
        try {

            if (isEarning) {
                isDeduction = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpEarnings();
                loadActiveDate();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void loadDeductions() {
        try {
            if (isDeduction) {
                isEarning = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpDeductions();
                loadActiveDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleChangeGrade() {
        try {

            // hrPayrollMaintainEds = null;
            appliedFrom = null;
            code = null;
            total = 0.0;
            if (hrPayrollMaintainEdsFacade.returnSavedInfo(hrLuPayrollAePGroup) != null) {
                hrPayrollMaintainEds = hrPayrollMaintainEdsFacade.returnSavedInfo(hrLuPayrollAePGroup);
                if (hrPayrollMaintainEds.getEarningDeductionCode().getType().equalsIgnoreCase("Earning")) {
                    isEarning = true;
                    isDeduction = false;
                    listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpEarnings();
                } else {
                    isDeduction = true;
                    isEarning = false;
                    listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpDeductions();
                }
                appliedFrom = hrPayrollMaintainEds.getStartDate();
                code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
                total = hrPayrollMaintainEds.getTotal();
                numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
//            code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
//            appliedFrom = hrPayrollMaintainEds.getStartDate();
//            total = hrPayrollMaintainEds.getTotal();
//            numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
                disChkEarnings = true;
                disChkDeduction = true;
                disEarningDeduction = true;
                disAppliedFrom = true;
                disAppliedTo = true;
                disTotal = true;
                disPaymentIn = true;
                disMonPaymet = true;
                disStatus = true;
                disCarryForward = true;
                disRespectiveValue = true;
                disRemark = true;
            } else {
                disChkEarnings = false;
                disChkDeduction = false;
                disEarningDeduction = false;
                disAppliedFrom = false;
                disAppliedTo = false;
                disTotal = false;
                disPaymentIn = false;
                disMonPaymet = false;
                disStatus = false;
                disCarryForward = false;
                disRespectiveValue = false;
                disRemark = false;
            }
            System.out.print("The salary scale code is " + salaryScalseCode);
            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployeesUsingGrade(salaryScalseCode);
            listOfGroupedAllEmpEd = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
//            listOfGroupedEmployees = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleChangePosition() {
        try {
            // hrPayrollMaintainEds = null;
            appliedFrom = null;
            code = null;
            total = 0.0;
            if (hrPayrollMaintainEdsFacade.returnSavedInfo(hrLuPayrollAePGroup) != null) {
                hrPayrollMaintainEds = hrPayrollMaintainEdsFacade.returnSavedInfo(hrLuPayrollAePGroup);
                if (hrPayrollMaintainEds.getEarningDeductionCode().getType().equalsIgnoreCase("Earning")) {
                    isEarning = true;
                    isDeduction = false;
                    listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpEarnings();
                } else {
                    isDeduction = true;
                    isEarning = false;
                    listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpDeductions();
                }
                appliedFrom = hrPayrollMaintainEds.getStartDate();
                code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
                total = hrPayrollMaintainEds.getTotal();
                numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
//            code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
//            appliedFrom = hrPayrollMaintainEds.getStartDate();
//            total = hrPayrollMaintainEds.getTotal();
//            numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
                disChkEarnings = true;
                disChkDeduction = true;
                disEarningDeduction = true;
                disAppliedFrom = true;
                disAppliedTo = true;
                disTotal = true;
                disPaymentIn = true;
                disMonPaymet = true;
                disStatus = true;
                disCarryForward = true;
                disRespectiveValue = true;
                disRemark = true;
            } else {
                disChkEarnings = false;
                disChkDeduction = false;
                disEarningDeduction = false;
                disAppliedFrom = false;
                disAppliedTo = false;
                disTotal = false;
                disPaymentIn = false;
                disMonPaymet = false;
                disStatus = false;
                disCarryForward = false;
                disRespectiveValue = false;
                disRemark = false;
            }
            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployeesUsingPosition(jobTitleId);
            listOfGroupedAllEmpEd = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
//            listOfGroupedEmployees = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleChangePayrollGroups() {
        try {
            // hrPayrollMaintainEds = null;
            appliedFrom = null;
            code = null;
            total = 0.0;
            if (hrPayrollMaintainEdsFacade.returnSavedInfo(hrLuPayrollAePGroup) != null) {
                hrPayrollMaintainEds = hrPayrollMaintainEdsFacade.returnSavedInfo(hrLuPayrollAePGroup);
                if (hrPayrollMaintainEds.getEarningDeductionCode().getType().equalsIgnoreCase("Earning")) {
                    isEarning = true;
                    isDeduction = false;
                    listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpEarnings();
                } else {
                    isDeduction = true;
                    isEarning = false;
                    listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadAllEmpDeductions();
                }
                appliedFrom = hrPayrollMaintainEds.getStartDate();
                code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
                total = hrPayrollMaintainEds.getTotal();
                numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
//            code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
//            appliedFrom = hrPayrollMaintainEds.getStartDate();
//            total = hrPayrollMaintainEds.getTotal();
//            numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
                disChkEarnings = true;
                disChkDeduction = true;
                disEarningDeduction = true;
                disAppliedFrom = true;
                disAppliedTo = true;
                disTotal = true;
                disPaymentIn = true;
                disMonPaymet = true;
                disStatus = true;
                disCarryForward = true;
                disRespectiveValue = true;
                disRemark = true;
            } else {
                disChkEarnings = false;
                disChkDeduction = false;
                disEarningDeduction = false;
                disAppliedFrom = false;
                disAppliedTo = false;
                disTotal = false;
                disPaymentIn = false;
                disMonPaymet = false;
                disStatus = false;
                disCarryForward = false;
                disRespectiveValue = false;
                disRemark = false;
            }
            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployees(hrLuPayrollAePGroup);
            listOfGroupedAllEmpEd = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
//            listOfGroupedEmployees = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
        } catch (Exception e) {
        }
    }

    public void handleChangeSalScale(ValueChangeEvent event) {
        salaryScalseCode = event.getNewValue().toString();
        System.out.print("the salary scale is " + salaryScalseCode);
    }

    public void handlePaymentTypeChange() {
        try {
            total = 0.0;
            hrPayrollMaintainEds.setMonthlyAmount(0.0);
            hrPayrollMaintainEds.setRespectivePaymentTypeAmt(BigInteger.ZERO);
        } catch (Exception e) {
        }
    }

    public void setPaymentLabel(ValueChangeEvent event) {
        try {
            paymentType = event.getNewValue().toString();
            if (event.getNewValue().toString().equalsIgnoreCase("Birr")) {
                lableValue = "Birr:";
                total = 0.0;
            } else if (event.getNewValue().toString().equalsIgnoreCase("Percent")) {
                lableValue = "%";
//                total = Double.valueOf(200);
                total = 0.0;
            } else if (event.getNewValue().toString().equalsIgnoreCase("Hour")) {
                lableValue = "Hour:";
                total = 0.0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void calculateED() {
        try {
//            if (hrEmployees.getId() == null) {
//                addMessage("Search employee!");
//                total = 0.0;
//            } else
            if (isEarning == false && isDeduction == false) {
                addMessage("Atleaset Select Earning or Deduction!");
                total = 0.0;
            } else if (code == null) {
                addMessage("Select Earning/Deduction");
                total = 0.0;
            } else if (appliedFrom == null) {
                addMessage("Select Payment Starting month");
                total = 0.0;
            } else {
//                hrPayrollMaintainEds.setRespectivePaymentTypeAmt(BigInteger.ZERO);
//                if (numberOfMonth.equalsIgnoreCase("Unknown")) {
                if (hrPayrollMaintainEds.getNumberOfMonth().equalsIgnoreCase("Unknown")) {
                    System.out.print("Called");

                    if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Birr")) {
                        hrPayrollMaintainEds.setMonthlyAmount(total);
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent")) {

                        hrPayrollMaintainEds.setMonthlyAmount(total * (Double.valueOf(respectiveAmount) / 100));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Hour")) {
                        hrPayrollMaintainEds.setMonthlyAmount(total);
                    }
                } else {
                    System.out.print("Entered on else part ");
                    numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
                    System.out.print("The month on key up is " + numberOfMonth);

                    if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Birr")) {
                        hrPayrollMaintainEds.setMonthlyAmount(total / Double.valueOf(numberOfMonth));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent")) {
                        hrPayrollMaintainEds.setMonthlyAmount((total / Double.valueOf(numberOfMonth)) * (Double.valueOf(respectiveAmount) / 100));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Hour")) {
                        hrPayrollMaintainEds.setMonthlyAmount(total / Double.valueOf(numberOfMonth));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void groupEmployees() {
        try {
            System.out.println("The size is" + listOfEmpForPayment.size());
            for (HrEmployees bp : listOfEmpForPayment) {
//                HrPayrollFilterBp hrEmp = new HrPayrollFilterBp();
//                HrEmployees emp = new HrEmployees();
//                BigDecimal big = new BigDecimal(bp.getId());
//                emp.setId(bp.getId());
//                hrEmp.setFromMonth("Gechesa");
//                hrEmp.setEmpId(big);
//                hrPayrollFilterBpFacadeLocal.edit(hrEmp);
            }
//            listOfFilteredEmployees = null;
//            listOfEmployees = null;
//            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
//            listOfFilteredEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
//            listOfBackPayedEmployees = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String returnId(String splitedValue) {
        try {
            String id = null;
            String conc[];
            conc = splitedValue.split("-");
            id = conc[0];
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void handleValueChangeFrom1(ValueChangeEvent event) {
        try {
            appliedFrom = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleValueChangeFromCarryForward(ValueChangeEvent event) {
        try {
            carryForward = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleNoMonthChange(ValueChangeEvent event) {
        try {

            numberOfMonth = event.getNewValue().toString();
            numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();

            System.out.print("The month is " + numberOfMonth);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handlePaymentMonth() {
        try {
            total = 0.0;
            hrPayrollMaintainEds.setMonthlyAmount(0.0);

        } catch (Exception e) {
        }
    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public void handleChanges() {

    }

    public void clear() {
        hrPayrollMaintainEds = null;
        appliedFrom = null;
        total = 0.0;
        code = null;
        isEarning = false;
        isDeduction = false;
        respectiveAmount = "0";
    }

    public void groupAllEmployeesEarningDed() {
        try {
//
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "groupAllEmployeesEarningDed", dataset)) {
                if ("Save".equalsIgnoreCase("Save")) {
                    if (listOfUngroupedSelectedEmployees.isEmpty()) {
                        JsfUtil.addFatalMessage("Select an employee");
                    } else if (code == null || code.isEmpty()) {
                        JsfUtil.addFatalMessage("Select Earning/Deduction");
                    } else if (appliedFrom == null || appliedFrom.isEmpty()) {
                        JsfUtil.addFatalMessage("Select Starting month!");
                    } else {
                        HrEmployees emp1 = new HrEmployees();
                        if (total <= 0) {
                            JsfUtil.addFatalMessage("The total Amount Should be greater than Zero!");
                        } else {
                            for (HrEmployees emp : listOfUngroupedSelectedEmployees) {
                                HrPayrollEarningDeductions eddd = new HrPayrollEarningDeductions();
                                eddd.setCode(BigDecimal.valueOf(Double.valueOf(code)));
                                HrPayrollMaintainEds alempEd = new HrPayrollMaintainEds();
                                alempEd.setEarningDeductionCode(eddd);
                                alempEd.setStartDate(appliedFrom);
                                alempEd.setTotal(total);
                                alempEd.setRemainingMonth(hrPayrollMaintainEds.getNumberOfMonth());
                                alempEd.setGroupId(String.valueOf(hrLuPayrollAePGroup.getId()));
                                alempEd.setEmpId(emp);
                                alempEd.setRemark(hrPayrollMaintainEds.getRemark());
                                alempEd.setEndDate(hrPayrollMaintainEds.getEndDate());
                                alempEd.setMonthlyAmount(hrPayrollMaintainEds.getMonthlyAmount());
                                alempEd.setPaymentIn(hrPayrollMaintainEds.getPaymentIn());
                                alempEd.setRespectivePaymentTypeAmt(hrPayrollMaintainEds.getRespectivePaymentTypeAmt());
                                alempEd.setStatus(hrPayrollMaintainEds.getStatus());
                                alempEd.setCarryForward(hrPayrollMaintainEds.getCarryForward());
                                hrPayrollMaintainEdsFacade.edit(alempEd);
                                hrPayrollMaintainEdsFacade.edit(hrPayrollMaintainEds);
                            }
                            addMessage("Successfully Grouped");
                            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployees(hrLuPayrollAePGroup);
                            listOfGroupedAllEmpEd = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
                            clear();
                        }
                    }
//                if ("Save".equalsIgnoreCase("Save")) {
//                    if (listOfUngroupedSelectedEmployees.isEmpty()) {
//                        addMessage("Select an employee");
//                    } else {
//                        HrEmployees emp1 = new HrEmployees();
//                        int i = 1;
//                        for (HrEmployees emp : listOfUngroupedSelectedEmployees) {
////                        hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(code)));
//                            HrPayrollEarningDeductions eddd = new HrPayrollEarningDeductions();
//                            eddd.setCode(BigDecimal.valueOf(Double.valueOf(code)));
//                            HrPayrollMaintainEds alempEd = new HrPayrollMaintainEds();
//                            alempEd.setEarningDeductionCode(eddd);
////                        alempEd.setEarningDeductionCode(hrPayrollEarningDeductions);
//                            alempEd.setStartDate(appliedFrom);
//                            alempEd.setTotal(total);
//                            alempEd.setRemainingMonth(hrPayrollMaintainEds.getNumberOfMonth());
//                            alempEd.setGroupId(String.valueOf(hrLuPayrollAePGroup.getId()));
//                            alempEd.setEmpId(emp);
//                            alempEd.setRemark(hrPayrollMaintainEds.getRemark());
//                            alempEd.setEndDate(hrPayrollMaintainEds.getEndDate());
//                            alempEd.setMonthlyAmount(hrPayrollMaintainEds.getMonthlyAmount());
//                            alempEd.setPaymentIn(hrPayrollMaintainEds.getPaymentIn());
//                            alempEd.setRespectivePaymentTypeAmt(hrPayrollMaintainEds.getRespectivePaymentTypeAmt());
//                            alempEd.setStatus(hrPayrollMaintainEds.getStatus());
//                            alempEd.setCarryForward(hrPayrollMaintainEds.getCarryForward());
//                            hrPayrollMaintainEdsFacade.edit(alempEd);
//                            hrPayrollMaintainEdsFacade.edit(hrPayrollMaintainEds);
//                        }
//                        addMessage("Successfully Grouped");
//                        listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployees(hrLuPayrollAePGroup);
//                        listOfGroupedAllEmpEd = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
//                    }
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occured while grouping employees!");

        }
    }

    public void ungroupEmployees() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "ungroupEmployees", dataset)) {
                if ("Save".equalsIgnoreCase("Save")) {
                    if (listOfSelectedGroupEmp.isEmpty()) {
                        addMessage("Make sure to select an employee");
                    } else {
                        for (HrPayrollMaintainEds ed : listOfSelectedGroupEmp) {
                            hrPayrollMaintainEdsFacade.remove(ed);
                        }
                        addMessage("Successfully Ungrouped");
                        listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployees(hrLuPayrollAePGroup);
                        listOfGroupedAllEmpEd = hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
                        if (listOfGroupedAllEmpEd.isEmpty()) {
                            clear();
                        }
                    }
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
//</editor-fold>
}
