/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;
import et.gov.eep.hrms.mapper.payroll.HrPayrollTaxRatesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollTaxRatesBean implements HrPayrollTaxRatesBeanLocal {

    @EJB
    HrPayrollTaxRatesFacade hrPayrollTaxRatesFacade;

    @Override
    public void create(HrPayrollTaxRates hrPayrollTaxRates) {
        hrPayrollTaxRatesFacade.create(hrPayrollTaxRates);
    }

    @Override
    public void edit(HrPayrollTaxRates hrPayrollTaxRates) {
        hrPayrollTaxRatesFacade.edit(hrPayrollTaxRates);
    }

    @Override
    public void remove(HrPayrollTaxRates hrPayrollTaxRates) {
        hrPayrollTaxRatesFacade.remove(hrPayrollTaxRates);
    }

    @Override
    public HrPayrollTaxRates find(Object id) {
        return hrPayrollTaxRatesFacade.find(id);
    }

    @Override
    public List<HrPayrollTaxRates> findAll() {
        return hrPayrollTaxRatesFacade.findAll();
    }

    @Override
    public List<HrPayrollTaxRates> findRange(int[] range) {
        return hrPayrollTaxRatesFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollTaxRatesFacade.count();
    }

    @Override
    public boolean checkOverlap(HrPayrollTaxRates tr) {
        return hrPayrollTaxRatesFacade.checkOverlap(tr);
    }

    @Override
    public boolean checkOverlapUpdate(HrPayrollTaxRates tr) {
        return hrPayrollTaxRatesFacade.checkOverlapUpdate(tr);
    }

    @Override
    public boolean checkUnlimited() {
        return hrPayrollTaxRatesFacade.checkUnlimited();
    }

    @Override
    public boolean checkUnlimitedUpdate(HrPayrollTaxRates pr) {
        return hrPayrollTaxRatesFacade.checkUnlimitedUpdate(pr);
    }

    @Override
    public HrPayrollTaxRates findBySalaryRange(Double AccruedLeaveAmount) {
        return hrPayrollTaxRatesFacade.findBySalaryRange(AccruedLeaveAmount);
    }

}
