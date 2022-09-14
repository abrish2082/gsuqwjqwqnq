/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;


import et.gov.eep.hrms.entity.payroll.FmsPayrollPension;
import et.gov.eep.hrms.mapper.payroll.Payroll_Penstion_Facade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hanoc
 */
@Stateless
public class Payroll_Penstion {

    @EJB
    private Payroll_Penstion_Facade payroll_Penstion_Facade;

    /**
     *
     * @return
     */
    public List<FmsPayrollPension> searchPayroll_Penstion() {
        return payroll_Penstion_Facade.searchPayroll_Penstion();
    }

    /**
     *
     * @param hrPayrollPension
     */
    public void saveOrUpdate(FmsPayrollPension hrPayrollPension) {
        payroll_Penstion_Facade.saveOrUpdate(hrPayrollPension);
    }

    /**
     *
     * @return
     */
    public List findAll() {
        return payroll_Penstion_Facade.findAll();
    }

}
