/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.mapper.payroll.HrPayrollPlPgFacade;

/**
 *
 * @author munir
 */
@Stateless
public class HrPayrollPlPgBean implements HrPayrollPlPgBeanLocal {

    @EJB
    HrPayrollPlPgFacade hrPayrollPlPgFacade;

    @Override
    public FmsCostCenter readSystemCostCenter(HrDepartments hrDepartments) {
        return hrPayrollPlPgFacade.readSystemCostCenter(hrDepartments);
    }

    @Override
    public List<HrPayrollPlPg> findAllPLPG() {
        return hrPayrollPlPgFacade.findAllPLPG();
    }

    @Override
    public List<Object[]> readPayLocPayGroup(int isLeft) {
        return hrPayrollPlPgFacade.readPayLocPayGroup(isLeft);
    }

    @Override
    public void save(HrPayrollPlPg payLocPayGroupDetail) {
        hrPayrollPlPgFacade.create(payLocPayGroupDetail);
    }

    @Override
    public void update(HrPayrollPlPg payLocPayGroupDetail) {
        hrPayrollPlPgFacade.edit(payLocPayGroupDetail);
    }

    @Override
    public HrPayrollPlPg findBydepOfPlPg(HrDepartments departmentId) {
        return hrPayrollPlPgFacade.findBydepOfPlPg(departmentId);
    }

    @Override
    public FmsCostCenter findByDepartmentID(HrDepartments departmentId) {
        return hrPayrollPlPgFacade.findByDepartmentID(departmentId);
    }

    public HrPayrollPlPg findByDeptID(HrDepartments departmentId) {
        return hrPayrollPlPgFacade.findByDeptID(departmentId);
    }

    @Override
    public HrPayrollPlPg readPayLocationPayGroupDetail(HrDepartments hrDepartments) {
        return hrPayrollPlPgFacade.readPayLocationPayGroupDetail(hrDepartments);
    }

    @Override
    public HrPayrollPlPg getSelectedRequest(int id) {
        return hrPayrollPlPgFacade.getSelectedRequest(id);
    }

    @Override
    public List<HrPayrollPlPg> findAllPlPg() {
        return hrPayrollPlPgFacade.findAllPlPg();
    }
}
