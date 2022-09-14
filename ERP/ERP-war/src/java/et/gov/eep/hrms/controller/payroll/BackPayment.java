/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
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
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.BackPaymentFilterEaringDeductionsLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollFilterBpBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainBackPayBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterEd;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "backPayment")
@ViewScoped

public class BackPayment implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @Inject
    HrPayrollPeriods hrPayrollFrom;
    @Inject
    HrPayrollPeriods hrPayrollTo;
    @Inject
    HrPayrollFilterBp hrPayrollFilterBp;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    HrPayrollBackPymntsEds hrPayrollBackPymntsEds;
    @Inject
    HrPayrollMaintainBackPay hrPayrollMaintainBackPay;
    @EJB
    BackPaymentFilterEaringDeductionsLocal backPaymentFilterEaringDeductions;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrPayrollFilterBpBeanLocal hrPayrollFilterBpFacadeLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransactionLocal;
    @EJB
    HrPayrollMaintainBackPayBeanLocal hrPayrollMaintainBackPayFacadeLocal;
    @EJB
    BackPaymentFilterEaringDeductionsLocal backPaymentFilterEaringDeductionsLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;

    private List<HrPayrollBackPymntsEds> listHrPayrollBackPymntsEds;
    private String iconRemove = "<";
    private String iconAdd = ">";
    private String earningDedCode;
    private String toCode;
    private String fromCode;
    private String edCode;
    private boolean isEarning;
    private boolean isDeduction;
    private List<HrEmployees> listOfEmployees;
    private List<HrEmployees> listOfFilteredEmployees;
    private List<HrEmployees> listOfEmpForPayment;
    private List<HrEmployees> filterBk;
    private List<HrPayrollFilterBp> selectedBKPayments;
    private List<HrPayrollBackPymntsEds> listOfBackPayment;
    private List<HrPayrollMonTransactions> listOfMonthTransaction;
    private List<HrPayrollMonTransactions> listOfTransAfter;
    private List<HrPayrollFilterBp> listsOFBk;
    private List<HrPayrollMonTransactions> monthlyTransactions;
    private List<HrPayrollPeriods> payrollFrom;
    private List<HrPayrollPeriods> payrollTo;
    private List<HrPayrollBackPymntsEds> listOfSavedED;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void _init() {
        listOfSavedED = backPaymentFilterEaringDeductionsLocal.findAll();
        payrollFrom = hrPayrollPeriodsLocal.findAll();//this is for back payment
        listOfMaintainedBp = hrPayrollMaintainBackPayFacadeLocal.findAll();
        listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
        listOfFilteredEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {

        if (hrPayrollEarningDeductions == null) {
            hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        }
        return hrPayrollEarningDeductions;
    }

    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
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

    public String getIconAdd() {
        return iconAdd;
    }

    public void setIconAdd(String iconAdd) {
        this.iconAdd = iconAdd;
    }

    public String getIconRemove() {
        return iconRemove;
    }

    public void setIconRemove(String iconRemove) {
        this.iconRemove = iconRemove;
    }

    public HrPayrollFilterBp getHrPayrollFilterBp() {
        if (hrPayrollFilterBp == null) {
            hrPayrollFilterBp = new HrPayrollFilterBp();
        }
        return hrPayrollFilterBp;
    }

    public void setHrPayrollFilterBp(HrPayrollFilterBp hrPayrollFilterBp) {
        this.hrPayrollFilterBp = hrPayrollFilterBp;
    }

    public List<HrPayrollFilterBp> getListsOFBk() {
        return listsOFBk;
    }

    public void setListsOFBk(List<HrPayrollFilterBp> listsOFBk) {
        this.listsOFBk = listsOFBk;
    }

    /**
     *
     * @return
     */
    public String getEarningDedCode() {
        return earningDedCode;
    }

    /**
     *
     * @param earningDedCode
     */
    public void setEarningDedCode(String earningDedCode) {
        this.earningDedCode = earningDedCode;
    }

    public HrPayrollPeriods getHrPayrollTo() {

        if (hrPayrollTo == null) {
            hrPayrollTo = new HrPayrollPeriods();
        }
        return hrPayrollTo;
    }

    public void setHrPayrollTo(HrPayrollPeriods hrPayrollTo) {
        this.hrPayrollTo = hrPayrollTo;
    }

    public HrPayrollPeriods getHrPayrollPeriods() {
        if (hrPayrollPeriods == null) {
            hrPayrollPeriods = new HrPayrollPeriods();
        }
        return hrPayrollPeriods;
    }

    public void setHrPayrollPeriods(HrPayrollPeriods hrPayrollPeriods) {
        this.hrPayrollPeriods = hrPayrollPeriods;
    }

    public HrPayrollPeriods getHrPayrollFrom() {
        if (hrPayrollFrom == null) {
            hrPayrollFrom = new HrPayrollPeriods();
        }
        return hrPayrollFrom;
    }

    public void setHrPayrollFrom(HrPayrollPeriods hrPayrollFrom) {
        this.hrPayrollFrom = hrPayrollFrom;
    }

    public List<HrPayrollMonTransactions> getListOfTransAfter() {

        return listOfTransAfter;
    }

    public void setListOfTransAfter(List<HrPayrollMonTransactions> listOfTransAfter) {
        this.listOfTransAfter = listOfTransAfter;
    }

    public List<HrPayrollMonTransactions> getListOfMonthTransaction() {
        return listOfMonthTransaction;
    }

    public void setListOfMonthTransaction(List<HrPayrollMonTransactions> listOfMonthTransaction) {
        this.listOfMonthTransaction = listOfMonthTransaction;
    }

    public List<HrPayrollMonTransactions> getMonthlyTransactions() {
        return monthlyTransactions;
    }

    public void setMonthlyTransactions(List<HrPayrollMonTransactions> monthlyTransactions) {
        this.monthlyTransactions = monthlyTransactions;
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public void loadMonTransaction() {
        listOfTransAfter = hrPayrollAllEmpEdSetupsFacade.loadEarnindDed(fromCode);
    }

    public List<HrPayrollPeriods> getPayrollTo() {
        return payrollTo;
    }

    public void setPayrollTo(List<HrPayrollPeriods> payrollTo) {
        this.payrollTo = payrollTo;
    }

    /**
     * Creates a new instance of BackPayment
     */
    public BackPayment() {
    }

    public boolean isIsEarning() {
        return isEarning;
    }

    public void setIsEarning(boolean isEarning) {
        this.isEarning = isEarning;
    }

    public void loadEmpForBkPayment() {

        listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
    }
    private List<HrPayrollFilterEd> listsss;

    public List<HrPayrollFilterEd> getListsss() {
        return listsss;
    }

    public void setListsss(List<HrPayrollFilterEd> listsss) {
        this.listsss = listsss;
    }

    public HrPayrollBackPymntsEds getHrPayrollBackPymntsEds() {
        if (hrPayrollBackPymntsEds == null) {
            hrPayrollBackPymntsEds = new HrPayrollBackPymntsEds();
        }
        return hrPayrollBackPymntsEds;
    }

    public void setHrPayrollBackPymntsEds(HrPayrollBackPymntsEds hrPayrollBackPymntsEds) {
        this.hrPayrollBackPymntsEds = hrPayrollBackPymntsEds;
    }

    public List<HrPayrollBackPymntsEds> getListOfSavedED() {
        return listOfSavedED;
    }

    public void setListOfSavedED(List<HrPayrollBackPymntsEds> listOfSavedED) {
        this.listOfSavedED = listOfSavedED;
    }

    private List<HrPayrollMaintainBackPay> listOfMaintainedBp;

    public List<HrPayrollMaintainBackPay> getListOfMaintainedBp() {

        return listOfMaintainedBp;
    }

    public void setListOfMaintainedBp(List<HrPayrollMaintainBackPay> listOfMaintainedBp) {
        this.listOfMaintainedBp = listOfMaintainedBp;
    }

    public List<HrPayrollBackPymntsEds> getListHrPayrollBackPymntsEds() {
        return listHrPayrollBackPymntsEds;
    }

    public void setListHrPayrollBackPymntsEds(List<HrPayrollBackPymntsEds> listHrPayrollBackPymntsEds) {
        this.listHrPayrollBackPymntsEds = listHrPayrollBackPymntsEds;
    }

    public List<HrPayrollFilterBp> getSelectedBKPayments() {
        return selectedBKPayments;
    }

    public void setSelectedBKPayments(List<HrPayrollFilterBp> selectedBKPayments) {
        this.selectedBKPayments = selectedBKPayments;
    }

    public List<HrEmployees> getFilterBk() {
        return filterBk;
    }

    public void setFilterBk(List<HrEmployees> filterBk) {
        this.filterBk = filterBk;
    }

    public List<HrEmployees> getListOfEmpForPayment() {
        return listOfEmpForPayment;
    }

    public void setListOfEmpForPayment(List<HrEmployees> listOfEmpForPayment) {
        this.listOfEmpForPayment = listOfEmpForPayment;
    }

    public void selectedSectorsChanged() {
//        System.out.println("Selected sectors are: " + listForSearch); // Look, JSF has already set it.
        // ...
    }

    public List<HrEmployees> getListOfFilteredEmployees() {
        return listOfFilteredEmployees;
    }

    public void setListOfFilteredEmployees(List<HrEmployees> listOfFilteredEmployees) {
        this.listOfFilteredEmployees = listOfFilteredEmployees;
    }

    public List<HrEmployees> getListOfEmployees() {
//        listOfEmployees = hrEmployeeBean.findAll();
        return listOfEmployees;
    }

    public void setListOfEmployees(List<HrEmployees> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        listOfEarningDeductions = backPaymentFilterEaringDeductions.loadUnusedEd();
        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    public List<HrPayrollBackPymntsEds> getListOfBackPayment() {
        listOfBackPayment = backPaymentFilterEaringDeductions.findAll();
        return listOfBackPayment;
    }

    public void setListOfBackPayment(List<HrPayrollBackPymntsEds> listOfBackPayment) {
        this.listOfBackPayment = listOfBackPayment;
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
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void handevalChangeFrom(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
            hrPayrollPeriods.setId(big);
            hrPayrollFrom = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleChangeFromForBkPayment() {
        try {
            BigDecimal selectedCode = new BigDecimal(fromCode);
            hrPayrollEarningDeductions.setCode(selectedCode);
            hrPayrollPeriods.setId(selectedCode);
            hrPayrollPeriods = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
            payrollTo = hrPayrollPeriodsLocal.payrollTo(returnYear(hrPayrollPeriods.getPaymentMadeForTheMonthOf()), returnMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf()));
        } catch (Exception e) {
        }

    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param event
     */
    public void handleEarningDedCode(ValueChangeEvent event) {
        try {
            earningDedCode = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleValueChange(ValueChangeEvent event) {
        try {
            BigDecimal big = new BigDecimal(event.getNewValue().toString());
            hrPayrollEarningDeductions.setCode(big);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleValueChangeOnRemove(ValueChangeEvent event) {
        try {
            BigDecimal big = new BigDecimal(event.getNewValue().toString());
            hrPayrollBackPymntsEds.setId(big);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleValueChangeFromDate(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
            hrPayrollPeriods.setId(big);
            hrPayrollFrom = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleValueChangeFromForEachEd(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
            hrPayrollPeriods.setId(big);
            hrPayrollFrom = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
            //load the transactions
            BigDecimal edCode = new BigDecimal(earningDedCode);
            hrPayrollEarningDeductions.setCode(edCode);
            listOfEmployees = hrPayrollMonthlyTransactionLocal.findPayedAmount(hrPayrollPeriods, hrPayrollEarningDeductions);
            listOfEmployees = hrEmployeeBean.findAll();
            listOfMonthTransaction = hrPayrollMonthlyTransactionLocal.findPayedAmountAndPayDetail(hrPayrollPeriods, hrPayrollEarningDeductions);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleValueChangeFrom1(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
            hrPayrollPeriods.setId(big);
            hrPayrollFrom = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleValueChangeFrom(ValueChangeEvent event) {
        try {
            edCode = null;
            edCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(edCode);
            hrPayrollBackPymntsEds.setId(big);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void loadEarnings() {
        try {
            if (isEarning) {
                isDeduction = false;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyEarnings();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void generateBackPayment() {
        try {
            backPaymentFilterEaringDeductionsLocal.generateBackPayment(fromCode, toCode, "");
            JsfUtil.addSuccessMessage("Successfully Generated!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testBackPay() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFilteredEmployees() {
        try {
            for (HrEmployees bp : filterBk) {
                hrPayrollFilterBpFacadeLocal.removeEmpFromPayment(bp);
            }
            listOfFilteredEmployees = null;
            listOfEmployees = null;
            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
            listOfFilteredEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void saveBK() {
        try {
            for (HrEmployees bp : listOfEmpForPayment) {
                hrPayrollFilterBpFacadeLocal.edit(hrPayrollFilterBp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filterEmployee() {
        try {
            for (HrEmployees bp : listOfEmpForPayment) {
                HrPayrollFilterBp hrEmp = new HrPayrollFilterBp();
                BigDecimal big = new BigDecimal(bp.getId());
                hrEmp.setEmpId(big);
                hrPayrollFilterBpFacadeLocal.edit(hrEmp);
            }
            listOfFilteredEmployees = null;
            listOfEmployees = null;
            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
            listOfFilteredEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            hrPayrollBackPymntsEds.setEarningDedId(hrPayrollEarningDeductions);
            backPaymentFilterEaringDeductions.create(hrPayrollBackPymntsEds);
            hrPayrollBackPymntsEds = null;
            hrPayrollEarningDeductions = null;
            if (hrPayrollBackPymntsEds == null) {
                hrPayrollBackPymntsEds = new HrPayrollBackPymntsEds();
            }
            if (hrPayrollEarningDeductions == null) {
                hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void remove() {
        try {
            backPaymentFilterEaringDeductions.remove(hrPayrollBackPymntsEds);
            hrPayrollBackPymntsEds = null;
            hrPayrollEarningDeductions = null;
            if (hrPayrollBackPymntsEds == null) {
                hrPayrollBackPymntsEds = new HrPayrollBackPymntsEds();
            }
            if (hrPayrollEarningDeductions == null) {
                hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String returnYear(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[2];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnMonth(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[1];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
//        return returnYM;
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        try {

            ArrayList<HrEmployees> employees = null;
            hrEmployees.setFirstName(hrEmployee);
            employees = hrEmployeeBean.SearchByFname(hrEmployees);
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;
    }

    public void earningDedBackPayment() {
        try {
            monthlyTransactions = hrPayrollMonthlyTransactionLocal.findAll();
        } catch (Exception e) {
        }
    }

    public void handleChangeFrom() {
        try {
            payrollTo = hrPayrollMonthlyTransactionLocal.usedPayrollDates();
        } catch (Exception e) {
        }
    }
//</editor-fold>
}
