/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.FmsPayrollTaxRates;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;
import et.gov.eep.hrms.mapper.payroll.Payroll_TaxRate_Facade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Musie
 */
@Stateless
public class Payroll_TaxRate_Logic {

    @EJB
    private Payroll_TaxRate_Facade payroll_TaxRate_Facade;

    /**
     *
     * @return
     */
    public List<FmsPayrollTaxRates> getListofTaxRate() {
        return payroll_TaxRate_Facade.getListofTaxRate();
    }

    /**
     *
     * @param fmsPayrollTaxRates
     */
    public void saveOrUpdate(FmsPayrollTaxRates fmsPayrollTaxRates) {
        payroll_TaxRate_Facade.saveOrUpdate(fmsPayrollTaxRates);
    }

//    public List<HrPayrollTaxRates> findAll() {
//        return payroll_TaxRate_Facade.findAll();
//    }

}
