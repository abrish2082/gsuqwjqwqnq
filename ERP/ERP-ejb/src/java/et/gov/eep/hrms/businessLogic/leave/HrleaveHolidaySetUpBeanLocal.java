/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.leave.HrLeaveHolidaySetup;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrleaveHolidaySetUpBeanLocal {

    public void saveOrUpdate(HrLeaveHolidaySetup hrLeaveHolidaySetup);

    public List<HrLeaveHolidaySetup> findAll();

    public List<HrLeaveHolidaySetup> findByBudgetyYear(FmsLuBudgetYear budgetYear);

    public int isBtwn(Date startDate, Date time);

   
}
