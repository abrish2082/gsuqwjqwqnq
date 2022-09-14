/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollGroups;
import et.gov.eep.hrms.mapper.payroll.HrPayrollGroupsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollGroupsBean implements HrPayrollGroupsBeanLocal {

    @EJB
    HrPayrollGroupsFacade hrPayrollGroupsFacade;

    @Override
    public void create(HrPayrollGroups hrPayrollGroups) {
        hrPayrollGroupsFacade.create(hrPayrollGroups);
    }

    @Override
    public void edit(HrPayrollGroups hrPayrollGroups) {
        hrPayrollGroupsFacade.edit(hrPayrollGroups);
    }

    @Override
    public void remove(HrPayrollGroups hrPayrollGroups) {
        hrPayrollGroupsFacade.remove(hrPayrollGroups);
    }

    @Override
    public HrPayrollGroups find(Object id) {
        return hrPayrollGroupsFacade.find(id);
    }

    @Override
    public List<HrPayrollGroups> findAll() {
        return hrPayrollGroupsFacade.findAll();
    }

    @Override
    public List<HrPayrollGroups> findRange(int[] range) {
        return hrPayrollGroupsFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollGroupsFacade.count();
    }

}
