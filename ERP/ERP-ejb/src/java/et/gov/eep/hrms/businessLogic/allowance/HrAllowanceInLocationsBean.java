/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.mapper.allowance.HrAllowanceInLocationsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrAllowanceInLocationsBean implements HrAllowanceInLocationsBeanLocal {

    @EJB
    HrAllowanceInLocationsFacade hrAllowanceInLocationsFacade;

    @Override
    public List<HrAllowanceInLocations> findAllowncePaymentTypes(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrAllowanceInLocationsFacade.findAllowncePaymentTypes(hrPayrollEarningDeductions);
    }

    @Override
    public Object checkAllInLocation(HrAllowanceInLocations hrAllowanceInLocations) {
        return hrAllowanceInLocationsFacade.checkAllInLocation(hrAllowanceInLocations);
    }

    @Override
    public List<HrAllowanceInLocations> findAll() {
        return hrAllowanceInLocationsFacade.findAll();
    }

    @Override
    public void edit(HrAllowanceInLocations hrAllowanceInLocations) {
        hrAllowanceInLocationsFacade.edit(hrAllowanceInLocations);
    }

    @Override
    public Object checkAllInLocationForUpdate(HrAllowanceInLocations hrAllowanceInLocations) {
        return hrAllowanceInLocationsFacade.checkAllInLocationForUpdate(hrAllowanceInLocations);
    }

    @Override
    public List<HrPayrollEarningDeductions> listOfAllowncesInLocation() {
        return hrAllowanceInLocationsFacade.listOfAllowncesInLocation();
    }

    @Override
    public HrAllowanceInLocations findAllInLocationById(HrAllowanceInLocations hrAllowanceInLocations) {
        return hrAllowanceInLocationsFacade.findAllInLocationById(hrAllowanceInLocations);
    }

}
