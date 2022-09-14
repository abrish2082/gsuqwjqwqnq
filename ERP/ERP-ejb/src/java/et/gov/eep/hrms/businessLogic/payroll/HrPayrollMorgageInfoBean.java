/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMorgageInfo;
import et.gov.eep.hrms.mapper.payroll.HrPayrollMorgageInfoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollMorgageInfoBean implements HrPayrollMorgageInfoBeanLocal {

    @EJB
    HrPayrollMorgageInfoFacade hrPayrollMorgageInfoFacade;

    @Override
    public void create(HrPayrollMorgageInfo hrPayrollMorgageInfo) {
        hrPayrollMorgageInfoFacade.create(hrPayrollMorgageInfo);
    }

    @Override
    public void edit(HrPayrollMorgageInfo hrPayrollMorgageInfo) {
        hrPayrollMorgageInfoFacade.edit(hrPayrollMorgageInfo);
    }

    @Override
    public void remove(HrPayrollMorgageInfo hrPayrollMorgageInfo) {
        hrPayrollMorgageInfoFacade.remove(hrPayrollMorgageInfo);
    }

    @Override
    public HrPayrollMorgageInfo find(Object id) {
        return hrPayrollMorgageInfoFacade.find(id);
    }

    @Override
    public List<HrPayrollMorgageInfo> findAll() {
        return hrPayrollMorgageInfoFacade.findAll();
    }

    @Override
    public List<HrPayrollMorgageInfo> findRange(int[] range) {
        return hrPayrollMorgageInfoFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollMorgageInfoFacade.count();
    }

    @Override
    public int updateMortageStatus(HrEmployees emp, HrPayrollEarningDeductions ed) {
        return hrPayrollMorgageInfoFacade.updateMortageStatus(emp, ed);
    }

    @Override
    public List<HrPayrollMorgageInfo> findByEmpId(HrEmployees emp) {
        return hrPayrollMorgageInfoFacade.findByEmpId(emp);
    }

}
