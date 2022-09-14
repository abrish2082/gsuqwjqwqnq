/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;

/**
 *
 * @author munir
 */
@Local
public interface HrPayrollPlPgBeanLocal {

    public List<HrPayrollPlPg> findAllPLPG();

    public FmsCostCenter readSystemCostCenter(HrDepartments hrDepartments);

    public HrPayrollPlPg readPayLocationPayGroupDetail(HrDepartments hrDepartments);

    public List<Object[]> readPayLocPayGroup(int isLeft);

    public void save(HrPayrollPlPg payLocPayGroupDetail);

    public void update(HrPayrollPlPg payLocPayGroupDetail);

    public HrPayrollPlPg findBydepOfPlPg(HrDepartments departmentId);

    public FmsCostCenter findByDepartmentID(HrDepartments departmentId);

    public HrPayrollPlPg findByDeptID(HrDepartments departmentId);

    public HrPayrollPlPg getSelectedRequest(int id);

    public List<HrPayrollPlPg> findAllPlPg();
}
