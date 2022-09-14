/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRateStatus;
import et.gov.eep.hrms.mapper.payroll.HrPayrollTaxRateStatusFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollTaxRateStatusBean implements HrPayrollTaxRateStatusBeanLocal {

    @EJB
    HrPayrollTaxRateStatusFacade hrPayrollTaxRateStatusFacade;

    @Override
    public void create(HrPayrollTaxRateStatus hrPayrollTaxRateStatus) {
        hrPayrollTaxRateStatusFacade.create(hrPayrollTaxRateStatus);
    }

    @Override
    public void edit(HrPayrollTaxRateStatus hrPayrollTaxRateStatus) {
        hrPayrollTaxRateStatusFacade.edit(hrPayrollTaxRateStatus);
    }

    @Override
    public void remove(HrPayrollTaxRateStatus hrPayrollTaxRateStatus) {
        hrPayrollTaxRateStatusFacade.remove(hrPayrollTaxRateStatus);
    }

    @Override
    public HrPayrollTaxRateStatus find(Object id) {
        return hrPayrollTaxRateStatusFacade.find(id);
    }

    @Override
    public List<HrPayrollTaxRateStatus> findAll() {
        return hrPayrollTaxRateStatusFacade.findAll();
    }

    @Override
    public List<HrPayrollTaxRateStatus> findRange(int[] range) {
        return hrPayrollTaxRateStatusFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollTaxRateStatusFacade.count();
    }

}
