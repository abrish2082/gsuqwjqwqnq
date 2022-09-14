/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.FmsPayrollTaxRates;
import et.gov.eep.hrms.mapper.payroll.FmsPayrollTaxRatesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class FmsPayrollTaxRatesBean implements FmsPayrollTaxRatesBeanLocal {

    @EJB
    FmsPayrollTaxRatesFacade FmsPayrollTaxRatesFacade;

    @Override
    public void create(FmsPayrollTaxRates fmsPayrollTaxRates) {
        FmsPayrollTaxRatesFacade.create(fmsPayrollTaxRates);
    }

    @Override
    public void edit(FmsPayrollTaxRates fmsPayrollTaxRates) {
        FmsPayrollTaxRatesFacade.edit(fmsPayrollTaxRates);
    }

    @Override
    public void remove(FmsPayrollTaxRates fmsPayrollTaxRates) {
        FmsPayrollTaxRatesFacade.remove(fmsPayrollTaxRates);
    }

    @Override
    public FmsPayrollTaxRates find(Object id) {
        return FmsPayrollTaxRatesFacade.find(id);
    }

    @Override
    public List<FmsPayrollTaxRates> findAll() {
        return FmsPayrollTaxRatesFacade.findAll();
    }

    @Override
    public List<FmsPayrollTaxRates> findRange(int[] range) {
        return FmsPayrollTaxRatesFacade.findRange(range);
    }

    @Override
    public int count() {
        return FmsPayrollTaxRatesFacade.count();
    }

}
