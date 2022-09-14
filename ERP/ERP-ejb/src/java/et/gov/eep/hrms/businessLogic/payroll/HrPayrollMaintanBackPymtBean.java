/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollMaintanBackPymt;
import et.gov.eep.hrms.mapper.payroll.HrPayrollMaintanBackPymtFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollMaintanBackPymtBean implements HrPayrollMaintanBackPymtBeanLocal {

    @EJB
    HrPayrollMaintanBackPymtFacade hrPayrollMaintanBackPymtFacade;

    @Override
    public void create(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt) {
        hrPayrollMaintanBackPymtFacade.create(hrPayrollMaintanBackPymt);
    }

    @Override
    public void edit(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt) {
        hrPayrollMaintanBackPymtFacade.edit(hrPayrollMaintanBackPymt);
    }

    @Override
    public void remove(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt) {
        hrPayrollMaintanBackPymtFacade.remove(hrPayrollMaintanBackPymt);
    }

    @Override
    public HrPayrollMaintanBackPymt find(Object id) {
        return hrPayrollMaintanBackPymtFacade.find(id);
    }

    @Override
    public List<HrPayrollMaintanBackPymt> findAll() {
        return hrPayrollMaintanBackPymtFacade.findAll();
    }

    @Override
    public List<HrPayrollMaintanBackPymt> findRange(int[] range) {
        return hrPayrollMaintanBackPymtFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollMaintanBackPymtFacade.count();
    }

}
