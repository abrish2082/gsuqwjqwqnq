/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollMaintainEdsBeanLocal {

    public void create(HrPayrollMaintainEds hrPayrollMaintainEds);

    public void edit(HrPayrollMaintainEds hrPayrollMaintainEds);

    public void remove(HrPayrollMaintainEds hrPayrollMaintainEds);

    public HrPayrollMaintainEds find(Object id);

    public List<HrPayrollMaintainEds> findAll();

    public List<HrPayrollMaintainEds> findRange(int[] range);

    public int count();

    public HrPayrollMaintainEds cheackRepeatedEarningOrDed(String ed, HrEmployees emp);

    public List<HrPayrollMaintainEds> loadEarningDeductions(String type, int id);

    public HrPayrollMaintainEds cheackRepeatedAllEmpEarningOrDed(HrPayrollMaintainEds ed);

    public HrPayrollMaintainEds returnSavedInfo(HrLuPayrollAePGroup ed);

    public List<HrPayrollMaintainEds> removeGroupedEarningDeductions(HrPayrollMaintainEds ed);
}
