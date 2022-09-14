/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFamiliesInfo;
import et.gov.eep.hrms.mapper.payroll.HrPayrollFamiliesInfoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollFamiliesInfoBean implements HrPayrollFamiliesInfoBeanLocal {

    @EJB
    HrPayrollFamiliesInfoFacade hrPayrollFamiliesInfoFacade;

    @Override
    public void create(HrPayrollFamiliesInfo hrPayrollFamiliesInfo) {
        hrPayrollFamiliesInfoFacade.create(hrPayrollFamiliesInfo);
    }

    @Override
    public void edit(HrPayrollFamiliesInfo hrPayrollFamiliesInfo) {
        hrPayrollFamiliesInfoFacade.edit(hrPayrollFamiliesInfo);
    }

    @Override
    public void remove(HrPayrollFamiliesInfo hrPayrollFamiliesInfo) {
        hrPayrollFamiliesInfoFacade.remove(hrPayrollFamiliesInfo);
    }

    @Override
    public HrPayrollFamiliesInfo find(Object id) {
        return hrPayrollFamiliesInfoFacade.find(id);
    }

    @Override
    public List<HrPayrollFamiliesInfo> findAll() {
        return hrPayrollFamiliesInfoFacade.findAll();
    }

    @Override
    public List<HrPayrollFamiliesInfo> findRange(int[] range) {
        return hrPayrollFamiliesInfoFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollFamiliesInfoFacade.count();
    }

    @Override
    public int updateFamilyStatus(HrEmployees emp, HrPayrollEarningDeductions ed) {
        return hrPayrollFamiliesInfoFacade.updateFamilyStatus(emp, ed);
    }

    @Override
    public List<HrPayrollFamiliesInfo> findByEmpId(HrEmployees emp) {
        return hrPayrollFamiliesInfoFacade.findByEmpId(emp);
    }

}
