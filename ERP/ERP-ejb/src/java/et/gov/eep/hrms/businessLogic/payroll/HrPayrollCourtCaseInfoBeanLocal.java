/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollCourtCaseInfo;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollCourtCaseInfoBeanLocal {

    public void create(HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo);

    public void edit(HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo);

    public void remove(HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo);

    public HrPayrollCourtCaseInfo find(Object id);

    public List<HrPayrollCourtCaseInfo> findAll();

    public List<HrPayrollCourtCaseInfo> findRange(int[] range);

    public int count();

    public int updateFamilyStatus(HrEmployees emp, HrPayrollEarningDeductions ed);

    public List<HrPayrollCourtCaseInfo> findByEmpId(HrEmployees emp);

}
