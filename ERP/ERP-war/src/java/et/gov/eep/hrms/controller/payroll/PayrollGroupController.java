/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import et.gov.eep.hrms.businessLogic.payroll.HrPayrollGroupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollGroups;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author user
 */
@Named(value = "payrollGroupController")
@ViewScoped
public class PayrollGroupController implements Serializable {

    /**
     * Creates a new instance of PayrollGroupController
     */
    public PayrollGroupController() {
    }
    private List<HrPayrollPeriods> payrollFrom;

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrPayrollGroupsBeanLocal hrPayrollGroupsFacadeLocal;
    @Inject
    HrPayrollGroups hrPayrollGroups;
    private List<HrPayrollGroups> listOfHrPayrollGroups;

    public List<HrPayrollGroups> getListOfHrPayrollGroups() {
        return listOfHrPayrollGroups;
    }
    @Inject
    HrPayrollPeriods hrPayrollTo;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public void handleChangeTo() {
        try {
            BigDecimal selectedCode = new BigDecimal(toCode);
            hrPayrollEarningDeductions.setCode(selectedCode);
        } catch (Exception e) {
        }
    }

    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransactionLocal;

    public void handleChangeFrom() {
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void setListOfHrPayrollGroups(List<HrPayrollGroups> listOfHrPayrollGroups) {
        this.listOfHrPayrollGroups = listOfHrPayrollGroups;
    }

    public void handleValueChangeTo(ValueChangeEvent event) {
        try {
            toCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(toCode);
            hrPayrollPeriods.setId(big);
            hrPayrollTo = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private List<HrPayrollPeriods> payrollTo;

    public List<HrPayrollPeriods> getPayrollTo() {

        return payrollTo;
    }

    public void setPayrollTo(List<HrPayrollPeriods> payrollTo) {
        this.payrollTo = payrollTo;
    }

    public HrPayrollGroups getHrPayrollGroups() {
        if (hrPayrollGroups == null) {
            hrPayrollGroups = new HrPayrollGroups();
        }
        return hrPayrollGroups;
    }

    public void setHrPayrollGroups(HrPayrollGroups hrPayrollGroups) {
        this.hrPayrollGroups = hrPayrollGroups;
    }
    @Inject
    HrPayrollPeriods hrPayrollFrom;

    @Inject
    HrPayrollPeriods hrPayrollPeriods;

    private String toCode;

    private String fromCode;

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

    public void handleValueChangeFrom1(ValueChangeEvent event) {
        try {
            toCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(toCode);
            hrPayrollPeriods.setId(big);
            hrPayrollFrom = hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void createGroup() {
        try {

            if (saveOrUpdate.equalsIgnoreCase("Update")) {
                hrPayrollGroupsFacadeLocal.edit(hrPayrollGroups);
                addMessage("Sucessfully Updated");

                load();
            } else {
                hrPayrollGroupsFacadeLocal.create(hrPayrollGroups);
                addMessage("Sucessfully Saved");

                load();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String saveOrUpdate = "Save";

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void editPayrollGroup() {
        try {
            saveOrUpdate = "Update";
            BigDecimal fCode = new BigDecimal(fromCode);
            BigDecimal tCode = new BigDecimal(toCode);
            hrPayrollFrom.setId(fCode);
            hrPayrollTo.setId(tCode);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void load() {
        listOfHrPayrollGroups = hrPayrollGroupsFacadeLocal.findAll();
        saveOrUpdate = "Save";
    }

    @PostConstruct
    public void _init() {
        payrollFrom = hrPayrollPeriodsLocal.findAll();//this is for back payment     
        payrollTo = hrPayrollPeriodsLocal.findAll();//this is for back payment       
        load();

    }

}
