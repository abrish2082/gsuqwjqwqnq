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
import et.gov.eep.hrms.mapper.payroll.HrPayrollPlPgDeptFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class HrPayrollPgPlDeptBean implements HrPayrollPgPlDeptBeanLocal {

    @EJB
    HrPayrollPlPgDeptFacade hrPayrollPlPgDeptFacade;

    @Override
    public FmsCostcSystemJunction readSystemCostCenter(HrDepartments hrDepartments) {
        return hrPayrollPlPgDeptFacade.readSystemCostCenter(hrDepartments);
    }

    @Override
    public List<Object[]> readPayLocPayGroup(int isLeft) {
        return hrPayrollPlPgDeptFacade.readPayLocPayGroup(isLeft);
    }

    @Override
    public FmsCostCenter findByDepartmentID(HrDepartments departmentId) {
        return hrPayrollPlPgDeptFacade.findByDepartmentID(departmentId);
    }

    public HrPayrollPlPg findByDeptID(HrDepartments departmentId) {
        return hrPayrollPlPgDeptFacade.findByDeptID(departmentId);
    }

    @Override
    public void save(HrPayrollPlPgDept payLocPayGroupDetail) {
        hrPayrollPlPgDeptFacade.create(payLocPayGroupDetail);
    }

    @Override
    public void update(HrPayrollPlPgDept payLocPayGroupDetail) {
        hrPayrollPlPgDeptFacade.edit(payLocPayGroupDetail);
    }

    @Override
    public HrPayrollPlPgDept readPayLocationPayGroupDetail(HrDepartments hrDepartmentMain) {
        return hrPayrollPlPgDeptFacade.readPayLocationPayGroupDetail(hrDepartmentMain);
    }

    @Override
    public HrPayrollPlPgDept findBydepOfPlPg(Integer departmentId) {
        return hrPayrollPlPgDeptFacade.findBydepOfPlPg(departmentId);
    }
}
