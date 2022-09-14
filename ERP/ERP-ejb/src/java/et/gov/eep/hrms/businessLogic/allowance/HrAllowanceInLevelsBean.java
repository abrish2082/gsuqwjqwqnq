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
import et.gov.eep.hrms.mapper.allowance.HrAllowanceInLevelsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrAllowanceInLevelsBean implements HrAllowanceInLevelsBeanLocal {

    @EJB
    HrAllowanceInLevelsFacade hrAllowanceInLevelsFacade;

    @Override
    public List<HrAllowanceInLevels> returnAllowanceInJobLevelAmountTypes(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrAllowanceInLevelsFacade.returnAllowanceInJobLevelAmountTypes(hrPayrollEarningDeductions);
    }

    @Override
    public Object checkAllInJobLevel(HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAllowanceInLevels hrAllowanceInLevels, HrLuJobLevels hrLuJobLevels, HrAddresses hrAddressJobLevel) {
        return hrAllowanceInLevelsFacade.checkAllInJobLevel(hrPayrollEarningDeductions, hrAllowanceInLevels, hrLuJobLevels, hrAddressJobLevel);
    }

    @Override
    public void edit(HrAllowanceInLevels hrAllowanceInLevels) {
        hrAllowanceInLevelsFacade.edit(hrAllowanceInLevels);
    }

    @Override
    public List<HrAllowanceInLevels> findAll() {
        return hrAllowanceInLevelsFacade.findAll();
    }

    @Override
    public Object checkAllInJobLevelForUpdate(HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAllowanceInLevels hrAllowanceInLevels, HrLuJobLevels hrLuJobLevels, HrAddresses hrAddressJobLevel) {
        return hrAllowanceInLevelsFacade.checkAllInJobLevelForUpdate(hrPayrollEarningDeductions, hrAllowanceInLevels, hrLuJobLevels, hrAddressJobLevel);
    }

    @Override
    public List<HrPayrollEarningDeductions> returnEarningDeductionsInJobLevel() {
        return hrAllowanceInLevelsFacade.returnEarningDeductionsInJobLevel();
    }

    @Override
    public void saveAllowanceInJobTitle(String activePayrollDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        hrAllowanceInLevelsFacade.saveAllowanceInJobTitle(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductions);
    }

    @Override
    public void saveAllowanceInJobLevel(String activePayrollDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        hrAllowanceInLevelsFacade.saveAllowanceInJobLevel(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductions);
    }

    @Override
    public void saveAllowanceInLocation(String activePayrollDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        hrAllowanceInLevelsFacade.saveAllowanceInLocation(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductions);
    }

    @Override
    public HrAllowanceInLevels findAllownaceInJobLevel(HrAllowanceInLevels hrAllowanceInLevels) {
        return hrAllowanceInLevelsFacade.findAllownaceInJobLevel(hrAllowanceInLevels);
    }
}
