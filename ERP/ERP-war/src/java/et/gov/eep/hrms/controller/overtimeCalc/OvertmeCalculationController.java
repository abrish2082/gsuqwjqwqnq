/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.overtimeCalc;

import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainEdsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
@Named(value = "overtmeCalculationController")
@ViewScoped
public class OvertmeCalculationController implements Serializable {

    /**
     * Creates a new instance of OvertmeCalculationController
     */
    public OvertmeCalculationController() {
    }
    //<editor-fold defaultstate="collapsed" desc="Declaration">
    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    private HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
            HrPayrollMaintainEdsBeanLocal hrPayrollMaintainEdsFacade;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrPayrollPeriods activePayrollCode;
    @Inject
            HrPayrollPeriods hrPayrollPeriods;
    @Inject
            HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
            HrPayrollEarningDeductions earningDeductionToSearch;
    @Inject
            HrPayrollMaintainEds hrPayrollMaintainEds;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable Declaration">
    private String activePayrollDate;
    private String code;
//</editor-fold>
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    /**
     *
     * @return
     */
    public HrPayrollPeriods getActivePayrollCode() {
        if (activePayrollCode == null) {
            activePayrollCode = new HrPayrollPeriods();
        }
        return activePayrollCode;
    }
    
    /**
     *
     * @param activePayrollCode
     */
    public void setActivePayrollCode(HrPayrollPeriods activePayrollCode) {
        this.activePayrollCode = activePayrollCode;
    }
    
    private List<HrEmployees> listOfEmployees;
    
    /**
     *
     * @return
     */
    public List<HrEmployees> getListOfEmployees() {
        listOfEmployees = hrEmployeeBean.findAll();
        return listOfEmployees;
    }
    
    /**
     *
     * @param listOfEmployees
     */
    public void setListOfEmployees(List<HrEmployees> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }
    
    private List<HrPayrollEarningDeductions> listOfotTypes;
    
    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getListOfotTypes() {
        listOfotTypes = hrPayrollEarningDeductionsLocal.listOfOtTypes();
        return listOfotTypes;
    }
    
    /**
     *
     * @param listOfotTypes
     */
    public void setListOfotTypes(List<HrPayrollEarningDeductions> listOfotTypes) {
        this.listOfotTypes = listOfotTypes;
    }
    
    /**
     *
     * @return
     */
    public String getActivePayrollDate() {
        return activePayrollDate;
    }
    
    /**
     *
     * @param activePayrollDate
     */
    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }
    
    /**
     *
     */
    @PostConstruct
    public void _init() {
        try {
            if (hrPayrollPeriodsLocal.activePayrollDate() != null) {
                hrPayrollPeriods = hrPayrollPeriodsLocal.activePayrollDate();
                activePayrollDate = hrPayrollPeriodsLocal.activePayrollDate().getPaymentMadeForTheMonthOf();
            } else {
                activePayrollDate = "[No Active Date is Defined]";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     *
     * @param summary
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }
    
    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     *
     * @param event
     */
    public void handleChange(ValueChangeEvent event) {
        code = event.getNewValue().toString();
        
    }
    
    /**
     *
     * @return
     */
    public HrPayrollMaintainEds getHrPayrollMaintainEds() {
        
        if (hrPayrollMaintainEds == null) {
            hrPayrollMaintainEds = new HrPayrollMaintainEds();
        }
        return hrPayrollMaintainEds;
    }
    
    /**
     *
     * @param hrPayrollMaintainEds
     */
    public void setHrPayrollMaintainEds(HrPayrollMaintainEds hrPayrollMaintainEds) {
        this.hrPayrollMaintainEds = hrPayrollMaintainEds;
    }
    
    /**
     *
     * @return
     */
    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {
        return hrPayrollEarningDeductions;
    }
    
    /**
     *
     * @param hrPayrollEarningDeductions
     */
    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
    }
    
    /**
     *
     * @return
     */
    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }
    
    /**
     *
     * @param hrEmployees
     */
    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }
    
    /**
     *
     */
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void transferToPayroll() {
        try {
            hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(code)));
            hrPayrollMaintainEds.setEarningDeductionCode(hrPayrollEarningDeductions);
            hrPayrollMaintainEds.setStartDate(activePayrollDate);
            hrPayrollMaintainEds.setEmpId(hrEmployees);
            hrPayrollMaintainEdsFacade.edit(hrPayrollMaintainEds);
            addMessage("The amount is transfered to the payrolll");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     *
     * @param hrEmployee
     * @return
     */
    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(hrEmployees);
        return employees;
    }
    
    /**
     *
     * @param event
     */
    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>
}
