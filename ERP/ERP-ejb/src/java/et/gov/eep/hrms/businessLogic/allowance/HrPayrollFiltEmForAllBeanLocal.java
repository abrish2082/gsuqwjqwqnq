/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.allowance.HrPayrollFiltEmForAll;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrPayrollFiltEmForAllBeanLocal {

    public Object checkFilteredEmployees(HrEmployees hrEmployees, HrPayrollEarningDeductions hrPayrollEdForAllowance);

    public void create(HrPayrollFiltEmForAll hrPayrollFiltEmForAll);

    public void edit(HrPayrollFiltEmForAll hrPayrollFiltEmForAll);

    public List<HrPayrollFiltEmForAll> filterEmpByEd(HrPayrollEarningDeductions hrPayrollEdForAllowance);
    
}
