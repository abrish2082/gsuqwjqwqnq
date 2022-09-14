/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrAllowanceInLevelsBeanLocal {

    public List<HrAllowanceInLevels> returnAllowanceInJobLevelAmountTypes(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public Object checkAllInJobLevel(HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAllowanceInLevels hrAllowanceInLevels, HrLuJobLevels hrLuJobLevels, HrAddresses hrAddressJobLevel);

    public void edit(HrAllowanceInLevels hrAllowanceInLevels);

    public List<HrAllowanceInLevels> findAll();

    public Object checkAllInJobLevelForUpdate(HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAllowanceInLevels hrAllowanceInLevels, HrLuJobLevels hrLuJobLevels, HrAddresses hrAddressJobLevel);

    public List<HrPayrollEarningDeductions> returnEarningDeductionsInJobLevel();

    public void saveAllowanceInJobTitle(String activePayrollDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public void saveAllowanceInJobLevel(String activePayrollDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public void saveAllowanceInLocation(String activePayrollDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public HrAllowanceInLevels findAllownaceInJobLevel(HrAllowanceInLevels hrAllowanceInLevels);
    
}
