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
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.BackPaymentFilterEaringDeductionsLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollBackGroup_logicLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollFilterBpBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainBackPayBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "generateGroupBackPayment")
@ViewScoped
public class GenerateGroupBackPayment implements Serializable {

    /**
     * Creates a new instance of GenerateGroupBackPayment
     */
    public GenerateGroupBackPayment() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups;
    @Inject
    HrPayrollBackPymntsEds hrPayrollBackPymntsEds;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @Inject
    HrPayrollMaintainBackPay hrPayrollMaintainBackPay;
    @Inject
    HrEmployees backPayedEmployees;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    BackPaymentFilterEaringDeductionsLocal backPaymentFilterEaringDeductions;
    @EJB
    HrPayrollBackGroup_logicLocal hrPayrollBackGroup_logicLocal;
    @EJB
    BackPaymentFilterEaringDeductionsLocal backPaymentFilterEaringDeductionsLocal;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransactionLocal;
    @EJB
    HrPayrollMaintainBackPayBeanLocal hrPayrollMaintainBackPayFacadeLocal;
    @EJB
    HrPayrollFilterBpBeanLocal hrPayrollFilterBpFacadeLocal;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;

    private String toCode;
    private String fromCode;
    private String iconRemove = "<";
    private String iconAdd = ">";
    private List<HrPayrollPeriods> payrollFrom;
    private List<HrPayrollMaintainBackPay> istOfDetailMaintanedBackPays;
    private List<HrEmployees> listOfEmployees;
    private List<HrEmployees> listOfFilteredEmployees;
    private List<HrEmployees> listOfEmpForPayment;
    private List<HrEmployees> filterBk;
    private List<HrPayrollFilterBp> selectedBKPayments;
    private List<HrPayrollBackPaymentGroups> listOfPayrollGrops;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
    private List<HrPayrollEarningDeductions> listOfSelectedEarnings;
    private List<HrPayrollBackPymntsEds> listOfSavedED;
    private List<HrPayrollBackPymntsEds> listOfBackPayment;
    private List<HrPayrollBackPymntsEds> listOfFiltedBackPayment;
    private List<HrPayrollBackPymntsEds> listHrPayrollBackPymntsEds;
    private List<HrEmployees> listOfBackPayedEmployees;
    private List<HrPayrollPeriods> payrollTo;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void _init() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            payrollFrom = hrPayrollPeriodsLocal.findAll();//this is for back payment
            payrollTo = hrPayrollPeriodsLocal.findAll();//this is for back payment
            listOfSavedED = backPaymentFilterEaringDeductionsLocal.findAll();
            listOfMaintainedBp = hrPayrollMaintainBackPayFacadeLocal.findAll();
            listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
            listOfFilteredEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
            //back payment gruoup
            listOfPayrollGrops = hrPayrollBackGroup_logicLocal.findAll();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public List<HrPayrollMaintainBackPay> getIstOfDetailMaintanedBackPays() {
        return istOfDetailMaintanedBackPays;
    }

    public void setIstOfDetailMaintanedBackPays(List<HrPayrollMaintainBackPay> istOfDetailMaintanedBackPays) {
        this.istOfDetailMaintanedBackPays = istOfDetailMaintanedBackPays;
    }

    public HrEmployees getBackPayedEmployees() {
        if (backPayedEmployees == null) {
            backPayedEmployees = new HrEmployees();
        }
        return backPayedEmployees;
    }

    public void setBackPayedEmployees(HrEmployees backPayedEmployees) {
        this.backPayedEmployees = backPayedEmployees;
    }

    public List<HrPayrollPeriods> getPayrollTo() {
        return payrollTo;
    }

    public void setPayrollTo(List<HrPayrollPeriods> payrollTo) {
        this.payrollTo = payrollTo;
    }

    public HrPayrollBackPaymentGroups getHrPayrollBackPaymentGroups() {
        if (hrPayrollBackPaymentGroups == null) {
            hrPayrollBackPaymentGroups = new HrPayrollBackPaymentGroups();
        }
        return hrPayrollBackPaymentGroups;
    }

    public void setHrPayrollBackPaymentGroups(HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups) {
        this.hrPayrollBackPaymentGroups = hrPayrollBackPaymentGroups;
    }

    public List<HrPayrollBackPaymentGroups> getListOfPayrollGrops() {
        return listOfPayrollGrops;
    }

    public void setListOfPayrollGrops(List<HrPayrollBackPaymentGroups> listOfPayrollGrops) {
        this.listOfPayrollGrops = listOfPayrollGrops;
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

    public List<HrEmployees> getListOfFilteredEmployees() {
        return listOfFilteredEmployees;
    }

    public void setListOfFilteredEmployees(List<HrEmployees> listOfFilteredEmployees) {
        this.listOfFilteredEmployees = listOfFilteredEmployees;
    }

    public List<HrEmployees> getListOfEmployees() {
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

    public List<HrEmployees> getListOfBackPayedEmployees() {

        if (listOfBackPayedEmployees == null) {
            listOfBackPayedEmployees = new ArrayList<HrEmployees>();
        }
        return listOfBackPayedEmployees;
    }

    public void setListOfBackPayedEmployees(List<HrEmployees> listOfBackPayedEmployees) {
        this.listOfBackPayedEmployees = listOfBackPayedEmployees;
    }

    public HrPayrollMaintainBackPay getHrPayrollMaintainBackPay() {
        if (hrPayrollMaintainBackPay == null) {
            hrPayrollMaintainBackPay = new HrPayrollMaintainBackPay();
        }
        return hrPayrollMaintainBackPay;
    }

    public void setHrPayrollMaintainBackPay(HrPayrollMaintainBackPay hrPayrollMaintainBackPay) {
        this.hrPayrollMaintainBackPay = hrPayrollMaintainBackPay;
    }

    public List<HrPayrollBackPymntsEds> getListHrPayrollBackPymntsEds() {
        return listHrPayrollBackPymntsEds;
    }

    public void setListHrPayrollBackPymntsEds(List<HrPayrollBackPymntsEds> listHrPayrollBackPymntsEds) {
        this.listHrPayrollBackPymntsEds = listHrPayrollBackPymntsEds;
    }

    public List<HrPayrollEarningDeductions> getListOfSelectedEarnings() {
        return listOfSelectedEarnings;
    }

    public void setListOfSelectedEarnings(List<HrPayrollEarningDeductions> listOfSelectedEarnings) {
        this.listOfSelectedEarnings = listOfSelectedEarnings;
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

    public List<HrPayrollBackPymntsEds> getListOfBackPayment() {
        listOfBackPayment = backPaymentFilterEaringDeductions.findAll();
        return listOfBackPayment;
    }

    public List<HrPayrollBackPymntsEds> getListOfFiltedBackPayment() {
        return listOfFiltedBackPayment;
    }

    public void setListOfFiltedBackPayment(List<HrPayrollBackPymntsEds> listOfFiltedBackPayment) {
        this.listOfFiltedBackPayment = listOfFiltedBackPayment;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void filterEmployeeForBackPytm() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "filterEmployeeForBackPytm", dataset)) {
                for (HrEmployees bp : listOfEmpForPayment) {
                    HrPayrollFilterBp hrEmp = new HrPayrollFilterBp();
                    HrEmployees emp = new HrEmployees();
                    BigDecimal big = new BigDecimal(bp.getId());
                    emp.setId(bp.getId());
                    hrEmp.setFromMonth("Gechesa");
                    hrEmp.setEmpId(big);
                    hrPayrollFilterBpFacadeLocal.edit(hrEmp);
                }
                listOfFilteredEmployees = null;
                listOfEmployees = null;
                listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
                listOfFilteredEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
                listOfBackPayedEmployees = null;
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

    public void removeFilteredEmployeesForBackPytnt() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "removeFilteredEmployeesForBack", dataset)) {
                for (HrEmployees bp : filterBk) {
                    hrPayrollFilterBpFacadeLocal.removeEmpFromPayment(bp);
                }
                listOfFilteredEmployees = null;
                listOfEmployees = null;
                listOfEmployees = hrPayrollAllEmpEdSetupsFacade.loadEmployees();
                listOfFilteredEmployees = hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
                listOfBackPayedEmployees = null;//
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
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

    public void saveEarningDeductionForBackPaymt() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                for (HrPayrollEarningDeductions ed : listOfSelectedEarnings) {
                    hrPayrollBackPymntsEds = new HrPayrollBackPymntsEds();
                    hrPayrollBackPymntsEds.setEarningDedId(ed);
                    backPaymentFilterEaringDeductions.create(hrPayrollBackPymntsEds);
                }
                hrPayrollBackPymntsEds = null;
                hrPayrollEarningDeductions = null;
                if (hrPayrollBackPymntsEds == null) {
                    hrPayrollBackPymntsEds = new HrPayrollBackPymntsEds();
                }
                if (hrPayrollEarningDeductions == null) {
                    hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
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

    public void removeEarningDeductionForBackPymt() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                for (HrPayrollBackPymntsEds bk : listOfFiltedBackPayment) {
                    backPaymentFilterEaringDeductions.remove(bk);
                }
                hrPayrollBackPymntsEds = null;
                hrPayrollEarningDeductions = null;
                if (hrPayrollBackPymntsEds == null) {
                    hrPayrollBackPymntsEds = new HrPayrollBackPymntsEds();
                }
                if (hrPayrollEarningDeductions == null) {
                    hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
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

    public void generateBackPayment() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                hrPayrollBackPaymentGroups = hrPayrollBackGroup_logicLocal.findById(hrPayrollBackPaymentGroups);
                backPaymentFilterEaringDeductionsLocal.generateBackPayment(String.valueOf(hrPayrollBackPaymentGroups.getPayrolFrom().getId()), String.valueOf(hrPayrollBackPaymentGroups.getPayrollTo().getId()), String.valueOf(hrPayrollBackPaymentGroups.getId()));
                JsfUtil.addSuccessMessage("Successfully Generated!");
                listOfBackPayedEmployees = null;
                listOfBackPayedEmployees = new ArrayList<HrEmployees>();
                listOfBackPayedEmployees = hrPayrollAllEmpEdSetupsFacade.loadMantanedEmployees(String.valueOf(hrPayrollBackPaymentGroups.getId()));
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

    public void handleGroupchange() {
        try {
            listOfBackPayedEmployees = null;
            listOfBackPayedEmployees = new ArrayList<HrEmployees>();
            listOfBackPayedEmployees = hrPayrollAllEmpEdSetupsFacade.loadMantanedEmployees(String.valueOf(hrPayrollBackPaymentGroups.getId()));
        } catch (Exception e) {
        }

    }

    public void handleValueChangePayrollGroup(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleValueChangeFrom1(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleValueChangeTo(ValueChangeEvent event) {
        try {
            toCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(toCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public void handleChangeTo() {
        try {
        } catch (Exception e) {
        }

    }

    public void displayOnDialog() {
        try {
            istOfDetailMaintanedBackPays = hrPayrollMaintainBackPayFacadeLocal.findEmployeesBackPaymentGroupEach(backPayedEmployees, hrPayrollBackPaymentGroups);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>
}
