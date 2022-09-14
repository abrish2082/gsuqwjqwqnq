/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author user
 */
@Named(value = "generateMonthlyPayroll")
@ViewScoped
public class GenerateMonthlyPayroll implements Serializable {

    /**
     * Creates a new instance of GenerateMonthlyPayroll
     */
    public GenerateMonthlyPayroll() {
    }
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;

    private String activePayrollDate;

    public String getActivePayrollDate() {

        return activePayrollDate;
    }

    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }

    @PostConstruct
    public void _init() {
        activePayrollDate = hrPayrollPeriodsLocal.activePayrollDate().getPreparedFor();
    }

}
