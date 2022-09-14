/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import et.gov.eep.hrms.mapper.payroll.HrPayrollFilterBpFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollFilterBpBean implements HrPayrollFilterBpBeanLocal {

    @EJB
    HrPayrollFilterBpFacade hrPayrollFilterBpFacade;

    @Override
    public void create(HrPayrollFilterBp hrPayrollFilterBp) {
        hrPayrollFilterBpFacade.create(hrPayrollFilterBp);
    }

    @Override
    public void edit(HrPayrollFilterBp hrPayrollFilterBp) {
       hrPayrollFilterBpFacade.edit(hrPayrollFilterBp);
    }

    @Override
    public void remove(HrPayrollFilterBp hrPayrollFilterBp) {
       hrPayrollFilterBpFacade.remove(hrPayrollFilterBp);
    }

    @Override
    public HrPayrollFilterBp find(Object id) {
        return hrPayrollFilterBpFacade.find(id);
    }

    @Override
    public List<HrPayrollFilterBp> findAll() {
       return hrPayrollFilterBpFacade.findAll();
    }

    @Override
    public List<HrPayrollFilterBp> findRange(int[] range) {
       return hrPayrollFilterBpFacade.findRange(range);
    }

    @Override
    public int count() {
       return hrPayrollFilterBpFacade.count();
    }

    @Override
    public int removeEmpFromPayment(HrEmployees emp) {
        return hrPayrollFilterBpFacade.removeEmpFromPayment(emp);
    }

    @Override
    public List<HrEmployees> select() {
        return hrPayrollFilterBpFacade.select();
    }

}
