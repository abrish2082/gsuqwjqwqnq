/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollCourtCaseInfo;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.mapper.payroll.HrPayrollCourtCaseInfoFacade;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollCourtCaseInfoBean implements HrPayrollCourtCaseInfoBeanLocal {

    @EJB
    HrPayrollCourtCaseInfoFacade hrPayrollCourtCaseInfoFacade;

    @Override
    public void create(HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo) {
        hrPayrollCourtCaseInfoFacade.saveOrUpdate(hrPayrollCourtCaseInfo);
    }

    @Override
    public void edit(HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo) {
        hrPayrollCourtCaseInfoFacade.edit(hrPayrollCourtCaseInfo);
    }

    @Override
    public void remove(HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo) {
        hrPayrollCourtCaseInfoFacade.remove(hrPayrollCourtCaseInfo);
    }

    @Override
    public HrPayrollCourtCaseInfo find(Object id) {
        return hrPayrollCourtCaseInfoFacade.find(id);
    }

    @Override
    public List<HrPayrollCourtCaseInfo> findAll() {
        return hrPayrollCourtCaseInfoFacade.findAll();
    }

    @Override
    public List<HrPayrollCourtCaseInfo> findRange(int[] range) {
        return hrPayrollCourtCaseInfoFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollCourtCaseInfoFacade.count();
    }

    @Override
    public int updateFamilyStatus(HrEmployees emp, HrPayrollEarningDeductions ed) {
        return hrPayrollCourtCaseInfoFacade.updateFamilyStatus(emp, ed);
    }

    @Override
    public List<HrPayrollCourtCaseInfo> findByEmpId(HrEmployees emp) {
        return hrPayrollCourtCaseInfoFacade.findByEmpId(emp);
    }

}
