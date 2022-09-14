/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.mapper.allowance.HrAllowanceInJobTitleFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrAllowanceInJobTitleBean implements HrAllowanceInJobTitleBeanLocal {

    @EJB
    HrAllowanceInJobTitleFacade hrAllowanceInJobTitleFacade;

    @Override
    public List<HrAllowanceInJobTitle> findAllowncesByEdCode(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrAllowanceInJobTitleFacade.findAllowncesByEdCode(hrPayrollEarningDeductions);
    }

    @Override
    public Object checkAllowanceInJobTitle(HrAllowanceInJobTitle hrAllowanceInJobTitle, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAddresses hrAddressJobTitle) {
        return hrAllowanceInJobTitleFacade.checkAllowanceInJobTitle(hrAllowanceInJobTitle, hrPayrollEarningDeductions, hrAddressJobTitle);
    }

    @Override
    public void create(HrAllowanceInJobTitle hrAllowanceInJobTitle) {
        hrAllowanceInJobTitleFacade.create(hrAllowanceInJobTitle);
    }

    @Override
    public Object checkAllowanceInJobTitleForUpdate(HrAllowanceInJobTitle hrAllowanceInJobTitle, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAddresses hrAddressJobTitle) {
        return hrAllowanceInJobTitleFacade.checkAllowanceInJobTitleForUpdate(hrAllowanceInJobTitle, hrPayrollEarningDeductions, hrAddressJobTitle);
    }

    @Override
    public void edit(HrAllowanceInJobTitle hrAllowanceInJobTitle) {
        hrAllowanceInJobTitleFacade.edit(hrAllowanceInJobTitle);
    }

    @Override
    public List<HrPayrollEarningDeductions> listOfEarningDedInTitle() {
        return hrAllowanceInJobTitleFacade.listOfEarningDedInTitle();
    }

    @Override
    public HrAllowanceInJobTitle findAllInJobTitle(HrAllowanceInJobTitle hrAllowanceInJobTitle) {
        return hrAllowanceInJobTitleFacade.findAllInJobTitle(hrAllowanceInJobTitle);
    }

    @Override
    public List<HrAllowanceInJobTitle> findAllJobTypePaymentTypes(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrAllowanceInJobTitleFacade.findAllJobTypePaymentTypes(hrPayrollEarningDeductions);
    }

    @Override
    public HrAllowanceInJobTitle findByJobTitleId(HrJobTypes hrJobTypes) {
        return hrAllowanceInJobTitleFacade.findByJobTitleId(hrJobTypes);
    }

}
