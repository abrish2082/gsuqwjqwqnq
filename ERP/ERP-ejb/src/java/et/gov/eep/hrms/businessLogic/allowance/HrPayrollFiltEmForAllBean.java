/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.allowance.HrPayrollFiltEmForAll;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.mapper.allowance.HrPayrollFiltEmForAllFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrPayrollFiltEmForAllBean implements HrPayrollFiltEmForAllBeanLocal {

    @EJB
    HrPayrollFiltEmForAllFacade hrPayrollFiltEmForAllFacade;

    @Override
    public Object checkFilteredEmployees(HrEmployees hrEmployees, HrPayrollEarningDeductions hrPayrollEdForAllowance) {
        return hrPayrollFiltEmForAllFacade.checkFilteredEmployees(hrEmployees, hrPayrollEdForAllowance);
    }

    @Override
    public void create(HrPayrollFiltEmForAll hrPayrollFiltEmForAll) {
        hrPayrollFiltEmForAllFacade.create(hrPayrollFiltEmForAll);
    }

    @Override
    public void edit(HrPayrollFiltEmForAll hrPayrollFiltEmForAll) {
        hrPayrollFiltEmForAllFacade.edit(hrPayrollFiltEmForAll);
    }

    @Override
    public List<HrPayrollFiltEmForAll> filterEmpByEd(HrPayrollEarningDeductions hrPayrollEdForAllowance) {
        return hrPayrollFiltEmForAllFacade.filterEmpByEd(hrPayrollEdForAllowance);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
