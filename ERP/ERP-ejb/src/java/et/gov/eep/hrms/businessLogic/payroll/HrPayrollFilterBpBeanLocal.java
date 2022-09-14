/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollFilterBpBeanLocal {

    public void create(HrPayrollFilterBp hrPayrollFilterBp);

    public void edit(HrPayrollFilterBp hrPayrollFilterBp);

    public void remove(HrPayrollFilterBp hrPayrollFilterBp);

    public HrPayrollFilterBp find(Object id);

    public List<HrPayrollFilterBp> findAll();

    public List<HrPayrollFilterBp> findRange(int[] range);

    public int count();

    public int removeEmpFromPayment(HrEmployees emp);

    public List<HrEmployees> select();
}
