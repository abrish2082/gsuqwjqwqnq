/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.hrms.mapper.payroll.HrPayrollAllEmpEdSetupsFacade;
import et.gov.eep.hrms.mapper.payroll.HrPayrollEarningDeductionsFacade;
import et.gov.eep.hrms.mapper.payroll.HrPayrollPeriodsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class taxReport implements taxReportLocal {

    @EJB
    HrPayrollPeriodsFacade PayrollPeriod;
    @EJB
    HrPayrollEarningDeductionsFacade payrollEarningDeductionsFacade;
    @EJB
    HrPayrollAllEmpEdSetupsFacade pentionReportFacade;

    /**
     *
     * @return
     */
    @Override
    public List<HrPayrollEarningDeductions> taxList() {
        return payrollEarningDeductionsFacade.taxTypeList();

    }

    /**
     *
     * @return
     */
    @Override
    public List<HrPayrollPeriods> payrollPeriodsList() {
        return null;
//        return PayrollPeriod.payrollPeriodList();
    }

    /**
     *
     * @param year
     * @param taxName
     * @return
     */
    @Override
    public List taxPentionReport(String year, String taxName) {
        return pentionReportFacade.monthlyPentionReport(year, taxName);
    }
}
