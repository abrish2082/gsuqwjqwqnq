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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrAllowanceInJobTitleBeanLocal {

    public List<HrAllowanceInJobTitle> findAllowncesByEdCode(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public Object checkAllowanceInJobTitle(HrAllowanceInJobTitle hrAllowanceInJobTitle, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAddresses hrAddressJobTitle);

    public void create(HrAllowanceInJobTitle hrAllowanceInJobTitle);

    public Object checkAllowanceInJobTitleForUpdate(HrAllowanceInJobTitle hrAllowanceInJobTitle, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrAddresses hrAddressJobTitle);

    public void edit(HrAllowanceInJobTitle hrAllowanceInJobTitle);

    public List<HrPayrollEarningDeductions> listOfEarningDedInTitle();

    public HrAllowanceInJobTitle findAllInJobTitle(HrAllowanceInJobTitle hrAllowanceInJobTitle);

    public List<HrAllowanceInJobTitle> findAllJobTypePaymentTypes(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public HrAllowanceInJobTitle findByJobTitleId(HrJobTypes hrJobTypes);
    
}
