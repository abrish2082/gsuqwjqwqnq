/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.leave.HrLeaveHolidaySetup;
import et.gov.eep.hrms.mapper.leave.HrLeaveHolidaySetupFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrleaveHolidaySetUpBean implements HrleaveHolidaySetUpBeanLocal {

    @EJB
    HrLeaveHolidaySetupFacade hrLeaveHolidaySetupFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void saveOrUpdate(HrLeaveHolidaySetup hrLeaveHolidaySetup) {
        hrLeaveHolidaySetupFacade.saveOrUpdate(hrLeaveHolidaySetup);
    }

    @Override
    public List<HrLeaveHolidaySetup> findAll() {
        return hrLeaveHolidaySetupFacade.findAll();
    }

    @Override
    public List<HrLeaveHolidaySetup> findByBudgetyYear(FmsLuBudgetYear budgetYear) {
        return hrLeaveHolidaySetupFacade.findByBudgetyYear(budgetYear);
    }

    @Override
    public int isBtwn(Date startDate, Date time) {
        return hrLeaveHolidaySetupFacade.isBtwn(startDate, time);
    }

}
