/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollPensionRates;
import et.gov.eep.hrms.mapper.payroll.HrPayrollPensionRatesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollPensionRatesBean implements HrPayrollPensionRatesBeanLocal {

    @EJB
    HrPayrollPensionRatesFacade hrPayrollPensionRatesFacade;

    @Override
    public void create(HrPayrollPensionRates hrPayrollPensionRates) {
        hrPayrollPensionRatesFacade.saveOrUpdate(hrPayrollPensionRates);
    }

    @Override
    public void edit(HrPayrollPensionRates hrPayrollPensionRates) {
       hrPayrollPensionRatesFacade.edit(hrPayrollPensionRates);
    }

    @Override
    public void remove(HrPayrollPensionRates hrPayrollPensionRates) {
     hrPayrollPensionRatesFacade.remove(hrPayrollPensionRates);
    }

    @Override
    public HrPayrollPensionRates find(Object id) {
       return hrPayrollPensionRatesFacade.find(id);
    }

    @Override
    public List<HrPayrollPensionRates> findAll() {
       return hrPayrollPensionRatesFacade.findAll();
    }

    @Override
    public List<HrPayrollPensionRates> findRange(int[] range) {
       return hrPayrollPensionRatesFacade.findRange(range);
    }

    @Override
    public int count() {
       return hrPayrollPensionRatesFacade.count();
    }

    @Override
    public int updatePensionRates() {
        return hrPayrollPensionRatesFacade.updatePensionRates();
    }

}
