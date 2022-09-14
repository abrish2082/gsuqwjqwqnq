/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrAllowanceInLocationsBeanLocal {

    public List<HrAllowanceInLocations> findAllowncePaymentTypes(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public Object checkAllInLocation(HrAllowanceInLocations hrAllowanceInLocations);

    public List<HrAllowanceInLocations> findAll();

    public void edit(HrAllowanceInLocations hrAllowanceInLocations);

    public Object checkAllInLocationForUpdate(HrAllowanceInLocations hrAllowanceInLocations);

    public List<HrPayrollEarningDeductions> listOfAllowncesInLocation();

    public HrAllowanceInLocations findAllInLocationById(HrAllowanceInLocations hrAllowanceInLocations);
    
}
