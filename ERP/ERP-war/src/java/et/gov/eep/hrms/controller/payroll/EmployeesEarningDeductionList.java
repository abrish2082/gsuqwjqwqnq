/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainEdsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author user
 */
@Named(value = "employeesEarningDeductionList")
@ViewScoped
public class EmployeesEarningDeductionList implements Serializable {

    /**
     * Creates a new instance of EmployeesEarningDeductionList
     */
    public EmployeesEarningDeductionList() {
    }

    @EJB
    HrPayrollMaintainEdsBeanLocal hrPayrollMaintainEdsFacade;

    @EJB
    HrPayrollMaintainEds hrPayrollMaintainEds;

    public HrPayrollMaintainEds getHrPayrollMaintainEds() {
        if (hrPayrollMaintainEds == null) {
            hrPayrollMaintainEds = new HrPayrollMaintainEds();
        }
        return hrPayrollMaintainEds;
    }

    public void setHrPayrollMaintainEds(HrPayrollMaintainEds hrPayrollMaintainEds) {
        this.hrPayrollMaintainEds = hrPayrollMaintainEds;
    }

    private List<HrPayrollMaintainEds> listOfDeductions;

    public List<HrPayrollMaintainEds> getListOfDeductions() {
        return listOfDeductions;
    }

    public void setListOfDeductions(List<HrPayrollMaintainEds> listOfDeductions) {
        this.listOfDeductions = listOfDeductions;
    }

    @PostConstruct
    public void _int() {

        listOfDeductions = hrPayrollMaintainEdsFacade.findAll();
    }

}
