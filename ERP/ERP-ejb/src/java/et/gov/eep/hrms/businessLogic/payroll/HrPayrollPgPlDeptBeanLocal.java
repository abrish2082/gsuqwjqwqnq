/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface HrPayrollPgPlDeptBeanLocal {

    public FmsCostcSystemJunction readSystemCostCenter(HrDepartments hrDepartments);

    public HrPayrollPlPgDept readPayLocationPayGroupDetail(HrDepartments hrDepartmentMain);

    public List<Object[]> readPayLocPayGroup(int isLeft);

    public void save(HrPayrollPlPgDept payLocPayGroupDetail);

    public void update(HrPayrollPlPgDept payLocPayGroupDetail);

    public HrPayrollPlPgDept findBydepOfPlPg(Integer departmentId);

    public FmsCostCenter findByDepartmentID(HrDepartments departmentId);

    public HrPayrollPlPg findByDeptID(HrDepartments departmentId);

}
