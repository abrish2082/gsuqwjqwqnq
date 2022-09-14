/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.hrms.entity.leave.HrLuHolidayNames;
import et.gov.eep.hrms.mapper.leave.HrLuHolidayNamesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLuHolidayNamesBean implements HrLuHolidayNamesBeanLocal {

    @EJB
    HrLuHolidayNamesFacade hrLuHolidayNamesFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<HrLuHolidayNames> findAllHolidays() {
       return hrLuHolidayNamesFacade.findAll();
    }
}
