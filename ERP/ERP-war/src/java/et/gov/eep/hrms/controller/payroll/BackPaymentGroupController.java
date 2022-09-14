/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollBackGroup_logicLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named(value = "backPaymentGroupController")
@ViewScoped
public class BackPaymentGroupController implements Serializable {

    /**
     * Creates a new instance of BackPaymentGroupController
     */
    public BackPaymentGroupController() {
    }
    //<editor-fold defaultstate="collapsed" desc="Variables & Objects">
    @Inject
    HrPayrollPeriods payrollFrom;
    @Inject
    HrPayrollPeriods payrollTo;
    @Inject
    HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    HrPayrollBackGroup_logicLocal hrPayrollBackGroup_logicLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;

    private String saveOrUpdate = "Save";
    private List<HrPayrollBackPaymentGroups> listOfPayrollGrops;
    private List<HrPayrollPeriods> listOfPayrollPeriodsFrom;
    private List<HrPayrollPeriods> listOfPayrollPeriodsTo;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void _init() {
        try {
            listOfPayrollGrops = null;
            listOfPayrollGrops = new ArrayList<HrPayrollBackPaymentGroups>();
            listOfPayrollPeriodsFrom = hrPayrollPeriodsLocal.findAll();
            listOfPayrollPeriodsTo = hrPayrollPeriodsLocal.findAll();
            listOfPayrollGrops = hrPayrollBackGroup_logicLocal.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public HrPayrollBackPaymentGroups getHrPayrollBackPaymentGroups() {
        if (hrPayrollBackPaymentGroups == null) {
            hrPayrollBackPaymentGroups = new HrPayrollBackPaymentGroups();
        }
        return hrPayrollBackPaymentGroups;
    }

    public void setHrPayrollBackPaymentGroups(HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups) {
        this.hrPayrollBackPaymentGroups = hrPayrollBackPaymentGroups;
    }

    public HrPayrollPeriods getPayrollFrom() {
        if (payrollFrom == null) {
            payrollFrom = new HrPayrollPeriods();
        }
        return payrollFrom;
    }

    public void setPayrollFrom(HrPayrollPeriods payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public HrPayrollPeriods getPayrollTo() {
        if (payrollTo == null) {
            payrollTo = new HrPayrollPeriods();
        }
        return payrollTo;
    }

    public void setPayrollTo(HrPayrollPeriods payrollTo) {
        this.payrollTo = payrollTo;
    }

    public List<HrPayrollBackPaymentGroups> getListOfPayrollGrops() {
        if (listOfPayrollGrops == null) {
            listOfPayrollGrops = new ArrayList<HrPayrollBackPaymentGroups>();
        }
        return listOfPayrollGrops;
    }

    public void setListOfPayrollGrops(List<HrPayrollBackPaymentGroups> listOfPayrollGrops) {
        this.listOfPayrollGrops = listOfPayrollGrops;
    }

    public List<HrPayrollPeriods> getListOfPayrollPeriodsFrom() {
        return listOfPayrollPeriodsFrom;
    }

    public void setListOfPayrollPeriodsFrom(List<HrPayrollPeriods> listOfPayrollPeriodsFrom) {
        this.listOfPayrollPeriodsFrom = listOfPayrollPeriodsFrom;
    }

    public List<HrPayrollPeriods> getListOfPayrollPeriodsTo() {
        return listOfPayrollPeriodsTo;
    }

    public void setListOfPayrollPeriodsTo(List<HrPayrollPeriods> listOfPayrollPeriodsTo) {
        this.listOfPayrollPeriodsTo = listOfPayrollPeriodsTo;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void handleValueFromCode(ValueChangeEvent event) {
        try {
            BigDecimal big = new BigDecimal(event.getNewValue().toString());
            System.out.print("The From value is " + big);
            payrollFrom.setId(big);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleValueToCode(ValueChangeEvent event) {
        try {
            BigDecimal big = new BigDecimal(event.getNewValue().toString());
            payrollTo.setId(big);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void edit() {
        saveOrUpdate = "Update";
        payrollFrom = hrPayrollBackPaymentGroups.getPayrolFrom();
        payrollTo = hrPayrollBackPaymentGroups.getPayrollTo();
    }

    public void handleChangeTo() {
        try {
            System.out.print("From id " + payrollFrom.getId());
            HrPayrollPeriods xx = new HrPayrollPeriods();
            HrPayrollPeriods payFr = new HrPayrollPeriods();
            payFr.setId(payrollFrom.getId());
            hrPayrollBackPaymentGroups.setPayrolFrom(payFr);
        } catch (Exception e) {
        }
    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public void handleValueChangeCounterGeneralLedger(ValueChangeEvent event) {
        try {
            HrPayrollPeriods payFr = new HrPayrollPeriods();
            BigDecimal big = new BigDecimal(event.getNewValue().toString());
            payFr.setId(big);
            hrPayrollBackPaymentGroups.setPayrolFrom(payFr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createGroupBackPaymentGroup() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "createGroupBackPaymentGroup", dataset)) {
                if (saveOrUpdate.equalsIgnoreCase("Save")) {
                    hrPayrollBackPaymentGroups.setPayrolFrom(payrollFrom);
                    hrPayrollBackPaymentGroups.setPayrollTo(payrollTo);
                    hrPayrollBackGroup_logicLocal.create(hrPayrollBackPaymentGroups);
                    JsfUtil.addSuccessMessage("Successfully Saved");
                    listOfPayrollGrops = hrPayrollBackGroup_logicLocal.findAll();
                    hrPayrollBackPaymentGroups = null;
                    payrollFrom = null;
                    payrollTo = null;
                } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
//                hrPayrollBackPaymentGroups.setPayrolFrom(payrollFrom);
//                hrPayrollBackPaymentGroups.setPayrollTo(payrollTo);
////                System.out.print("The payroll from is "+hrPayrollBackPaymentGroups.getPayrolFrom().getPaymentMadeForTheMonthOf());
//                    System.out.print("#### 2" + hrPayrollBackPaymentGroups.getPayrolFrom().getId());
//                    System.out.print("#### 1 " + hrPayrollBackPaymentGroups.getGroupName());
//                    System.out.print("Payroll From 1" + payrollFrom.getId());
//                    System.out.print("Payroll To 2" + payrollTo.getId());
//                    HrPayrollBackPaymentGroups xx = new HrPayrollBackPaymentGroups();
//                    BigDecimal big = new BigDecimal("181");
//                    HrPayrollPeriods aa = new HrPayrollPeriods();
//                    aa.setId(big);
//                    xx.setPayrolFrom(aa);
//                    xx.setPayrolFrom(aa);
                    HrPayrollBackPaymentGroups xx = new HrPayrollBackPaymentGroups();
                    xx.setPayrolFrom(payrollFrom);
                    xx.setPayrollTo(payrollTo);
                    hrPayrollBackGroup_logicLocal.edit(xx);
                    System.out.print("#### After from " + hrPayrollBackPaymentGroups.getPayrolFrom().getId());
                    System.out.print("#### After to  " + hrPayrollBackPaymentGroups.getPayrollTo().getId());
                    JsfUtil.addSuccessMessage("Successfully Updated");
                    saveOrUpdate = "Update";
                    listOfPayrollGrops = hrPayrollBackGroup_logicLocal.findAll();
                    hrPayrollBackPaymentGroups = null;
                    payrollFrom = null;
                    payrollTo = null;
                }

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
        } catch (EJBException ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>
}
