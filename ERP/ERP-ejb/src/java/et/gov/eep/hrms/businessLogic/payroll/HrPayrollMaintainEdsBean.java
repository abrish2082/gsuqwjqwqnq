/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.mapper.payroll.HrPayrollMaintainEdsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollMaintainEdsBean implements HrPayrollMaintainEdsBeanLocal {

    @EJB
    HrPayrollMaintainEdsFacade hrPayrollMaintainEdsFacade;

    @Override
    public void create(HrPayrollMaintainEds hrPayrollMaintainEds) {
        hrPayrollMaintainEdsFacade.create(hrPayrollMaintainEds);
    }

    @Override
    public void edit(HrPayrollMaintainEds hrPayrollMaintainEds) {
        hrPayrollMaintainEdsFacade.edit(hrPayrollMaintainEds);
    }

    @Override
    public void remove(HrPayrollMaintainEds hrPayrollMaintainEds) {
       hrPayrollMaintainEdsFacade.remove(hrPayrollMaintainEds);
    }

    @Override
    public HrPayrollMaintainEds find(Object id) {
        return hrPayrollMaintainEdsFacade.find(id);
    }

    @Override
    public List<HrPayrollMaintainEds> findAll() {
        return hrPayrollMaintainEdsFacade.findAll();
    }

    @Override
    public List<HrPayrollMaintainEds> findRange(int[] range) {
        return hrPayrollMaintainEdsFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollMaintainEdsFacade.count();
    }

    @Override
    public HrPayrollMaintainEds cheackRepeatedEarningOrDed(String ed, HrEmployees emp) {
        return hrPayrollMaintainEdsFacade.cheackRepeatedEarningOrDed(ed, emp);
    }

    @Override
    public List<HrPayrollMaintainEds> loadEarningDeductions(String type, int id) {
        return hrPayrollMaintainEdsFacade.loadEarningDeductions(type, id);
    }

    @Override
    public HrPayrollMaintainEds cheackRepeatedAllEmpEarningOrDed(HrPayrollMaintainEds ed) {
        return hrPayrollMaintainEdsFacade.cheackRepeatedAllEmpEarningOrDed(ed);
    }

    @Override
    public HrPayrollMaintainEds returnSavedInfo(HrLuPayrollAePGroup ed) {
        return hrPayrollMaintainEdsFacade.returnSavedInfo(ed);
    }

    @Override
    public List<HrPayrollMaintainEds> removeGroupedEarningDeductions(HrPayrollMaintainEds ed) {
        return hrPayrollMaintainEdsFacade.removeGroupedEarningDeductions(ed);
    }

}
