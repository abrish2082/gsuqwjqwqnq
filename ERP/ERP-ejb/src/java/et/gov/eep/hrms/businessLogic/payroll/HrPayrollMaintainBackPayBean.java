/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay;
import et.gov.eep.hrms.mapper.payroll.HrPayrollMaintainBackPayFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollMaintainBackPayBean implements HrPayrollMaintainBackPayBeanLocal {

    @EJB
    HrPayrollMaintainBackPayFacade hrPayrollMaintainBackPayFacade;

    @Override
    public void create(HrPayrollMaintainBackPay hrPayrollMaintainBackPay) {
        hrPayrollMaintainBackPayFacade.create(hrPayrollMaintainBackPay);
    }

    @Override
    public void edit(HrPayrollMaintainBackPay hrPayrollMaintainBackPay) {
        hrPayrollMaintainBackPayFacade.edit(hrPayrollMaintainBackPay);
    }

    @Override
    public void remove(HrPayrollMaintainBackPay hrPayrollMaintainBackPay) {
        hrPayrollMaintainBackPayFacade.remove(hrPayrollMaintainBackPay);
    }

    @Override
    public HrPayrollMaintainBackPay find(Object id) {
        return hrPayrollMaintainBackPayFacade.find(id);
    }

    @Override
    public List<HrPayrollMaintainBackPay> findAll() {
        return hrPayrollMaintainBackPayFacade.findAll();
    }

    @Override
    public List<HrPayrollMaintainBackPay> findRange(int[] range) {
        return hrPayrollMaintainBackPayFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollMaintainBackPayFacade.count();
    }

    @Override
    public int closeIndBackPayment(HrEmployees emp) {
        return hrPayrollMaintainBackPayFacade.closeIndBackPayment(emp);
    }

    @Override
    public List<HrPayrollMaintainBackPay> findEmployeesBackPayment(HrEmployees emp) {
        return hrPayrollMaintainBackPayFacade.findEmployeesBackPayment(emp);
    }

    @Override
    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentInd() {
        return hrPayrollMaintainBackPayFacade.findEmployeesBackPaymentInd();
    }

    @Override
    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentGroup() {
        return hrPayrollMaintainBackPayFacade.findEmployeesBackPaymentGroup();
    }

    @Override
    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentGroupEach(HrEmployees emp, HrPayrollBackPaymentGroups gr) {
        return hrPayrollMaintainBackPayFacade.findEmployeesBackPaymentGroupEach(emp, gr);
    }
}
