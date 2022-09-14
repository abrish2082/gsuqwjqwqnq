/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import et.gov.eep.commonApplications.utility.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.BackPaymentFilterEaringDeductionsLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainBackPayBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;

/**
 *
 * @author user
 */
@Named(value = "generateIndivBkPayment")
@ViewScoped
public class GenerateIndivBkPayment implements Serializable {

    /**
     * Creates a new instance of GenerateIndivBkPayment
     */
    public GenerateIndivBkPayment() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">
    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @Inject
    HrPayrollPeriods hrPayrollFrom;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    HrPayrollPeriods hrPayrollTo;
    @EJB
    BackPaymentFilterEaringDeductionsLocal backPaymentFilterEaringDeductionsLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransactionLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrPayrollMaintainBackPayBeanLocal hrPayrollMaintainBackPayFacadeLocal;
    @EJB
    private HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;

    private String toCode;
    private String code;
    private String noDays;
    private String totalAmount;
    private String fromCode;
    private String indFromCode;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
    private List<HrPayrollMaintainBackPay> listOfMaintainedBp;
    private List<HrPayrollPeriods> payrollTo;
    private List<HrPayrollPeriods> payrollFrom;
    private List<HrPayrollMonTransactions> monthlyTransactions;
    private List<HrPayrollMonTransactions> listOfSelectedMonTransactions;

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstract">
    @PostConstruct
    public void _init() {
        payrollFrom = hrPayrollPeriodsLocal.findAll();//this is for back payment
        payrollTo = hrPayrollPeriodsLocal.findAll();//this is for back payment
        listOfEarningDeductions = null;
        listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyEarnings();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setterrs">

    public List<HrPayrollMonTransactions> getListOfSelectedMonTransactions() {
        return listOfSelectedMonTransactions;
    }

    public void setListOfSelectedMonTransactions(List<HrPayrollMonTransactions> listOfSelectedMonTransactions) {
        this.listOfSelectedMonTransactions = listOfSelectedMonTransactions;
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

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
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

    public List<HrPayrollMaintainBackPay> getListOfMaintainedBp() {

        return listOfMaintainedBp;
    }

    public void setListOfMaintainedBp(List<HrPayrollMaintainBackPay> listOfMaintainedBp) {
        this.listOfMaintainedBp = listOfMaintainedBp;
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

    public List<HrPayrollPeriods> getPayrollTo() {
        return payrollTo;
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public String getIndFromCode() {
        return indFromCode;
    }

    public void setIndFromCode(String indFromCode) {
        this.indFromCode = indFromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public List<HrPayrollMonTransactions> getMonthlyTransactions() {
        return monthlyTransactions;
    }

    public void setMonthlyTransactions(List<HrPayrollMonTransactions> monthlyTransactions) {
        this.monthlyTransactions = monthlyTransactions;
    }

    public String getNoDays() {
        return noDays;
    }

    public void setNoDays(String noDays) {
        this.noDays = noDays;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            listOfMaintainedBp = hrPayrollMaintainBackPayFacadeLocal.findEmployeesBackPayment(hrEmployees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        try {
            ArrayList<HrEmployees> employees = null;
            hrEmployees.setFirstName(hrEmployee);
            employees = hrEmployeeBean.SearchByFname(hrEmployees);
            monthlyTransactions = null;
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void handleValueChangeFromBk(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
            monthlyTransactions = null;
            hrPayrollPeriods.setId(big);
            hrPayrollFrom = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
            monthlyTransactions = hrPayrollMonthlyTransactionLocal.findEarnings(hrPayrollFrom, hrEmployees);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleInd(ValueChangeEvent event) {
        try {
            indFromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(indFromCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleChangeTo() {
        try {
            BigDecimal selectedCode = new BigDecimal(toCode);
            hrPayrollEarningDeductions.setCode(selectedCode);
        } catch (Exception e) {
        }
    }

    public void handleChangeToFrom() {
        try {

        } catch (Exception e) {
        }
    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public void generateEachEmpBackPayment() {
        try {
            backPaymentFilterEaringDeductionsLocal.deleteIndBk(String.valueOf(hrEmployees.getId()));
            for (HrPayrollMonTransactions bp : listOfSelectedMonTransactions) {
                backPaymentFilterEaringDeductionsLocal.generateIndividualBkPayment(String.valueOf(bp.getId()), fromCode, toCode, String.valueOf(bp.getEmpId().getId()), bp.getEarningDedCode().getCriterias());
            }
            JsfUtil.addSuccessMessage("Successfully Generated!");
            listOfMaintainedBp = hrPayrollMaintainBackPayFacadeLocal.findEmployeesBackPayment(hrEmployees);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateEachEmpBackPaymentWDays() {
        try {
            BigDecimal big = new BigDecimal(code);
            hrPayrollEarningDeductions.setCode(big);
            backPaymentFilterEaringDeductionsLocal.deleteIndBk(String.valueOf(hrEmployees.getId()));
            backPaymentFilterEaringDeductionsLocal.generateIndividualBkPaymentWithDays(String.valueOf(code), fromCode, toCode, String.valueOf(hrEmployees.getId()), hrPayrollEarningDeductionsLocal.findByCode(hrPayrollEarningDeductions).getCriterias(), noDays, totalAmount);
            JsfUtil.addSuccessMessage("Successfully Generated!");
            listOfMaintainedBp = hrPayrollMaintainBackPayFacadeLocal.findEmployeesBackPayment(hrEmployees);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            hrPayrollMaintainBackPayFacadeLocal.closeIndBackPayment(hrEmployees);
            JsfUtil.addSuccessMessage("Successfully Closed!");
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

    public void handleChanges() {

    }

    public void handleValueChangeTo(ValueChangeEvent event) {
        try {
            toCode = event.getNewValue().toString();
            System.out.print("The TO code is " + toCode);
            BigDecimal big = new BigDecimal(toCode);
            hrPayrollPeriods.setId(big);
            hrPayrollTo = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//</editor-fold>
}
