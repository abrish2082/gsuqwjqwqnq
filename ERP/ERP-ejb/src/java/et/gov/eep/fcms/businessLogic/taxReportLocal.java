/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local 
public interface taxReportLocal {

    /**
     *
     * @return
     */
    List<HrPayrollEarningDeductions> taxList();

    /**
     *
     * @return
     */
    List<HrPayrollPeriods> payrollPeriodsList();

    /**
     *
     * @param year
     * @param taxName
     * @return
     */
    public List taxPentionReport(String year,String taxName);
}
