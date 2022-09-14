/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollMaintainBackPayBeanLocal {

    public void create(HrPayrollMaintainBackPay hrPayrollMaintainBackPay);

    public void edit(HrPayrollMaintainBackPay hrPayrollMaintainBackPay);

    public void remove(HrPayrollMaintainBackPay hrPayrollMaintainBackPay);

    public HrPayrollMaintainBackPay find(Object id);

    public List<HrPayrollMaintainBackPay> findAll();

    public List<HrPayrollMaintainBackPay> findRange(int[] range);

    public int count();

    public int closeIndBackPayment(HrEmployees emp);

    public List<HrPayrollMaintainBackPay> findEmployeesBackPayment(HrEmployees emp);

    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentInd();

    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentGroup();

    public List<HrPayrollMaintainBackPay> findEmployeesBackPaymentGroupEach(HrEmployees emp, HrPayrollBackPaymentGroups gr);
}
