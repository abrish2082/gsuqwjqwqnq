/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollPensionRate;
import et.gov.eep.hrms.mapper.payroll.HrPayrollPensionRateFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollPensionRateBean implements HrPayrollPensionRateBeanLocal {

    @EJB
    HrPayrollPensionRateFacade hrPayrollPensionRateFacade;

    @Override
    public void create(HrPayrollPensionRate hrPayrollPensionRate) {
        hrPayrollPensionRateFacade.create(hrPayrollPensionRate);
    }

    @Override
    public void edit(HrPayrollPensionRate hrPayrollPensionRate) {
        hrPayrollPensionRateFacade.edit(hrPayrollPensionRate);
    }

    @Override
    public void remove(HrPayrollPensionRate hrPayrollPensionRate) {
        hrPayrollPensionRateFacade.remove(hrPayrollPensionRate);
    }

    @Override
    public HrPayrollPensionRate find(Object id) {
        return hrPayrollPensionRateFacade.find(id);
    }

    @Override
    public List<HrPayrollPensionRate> findAll() {
        return hrPayrollPensionRateFacade.findAll();
    }

    @Override
    public List<HrPayrollPensionRate> findRange(int[] range) {
        return hrPayrollPensionRateFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollPensionRateFacade.count();
    }

}
