/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import et.gov.eep.hrms.entity.leave.HrLeaveScheduleTransfer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrLeaveScheduleTransferBeanLocal {

    public void saveOrUpdate(HrLeaveScheduleTransfer hrLeaveBalance);

    public void edit(HrLeaveScheduleTransfer hrLeaveBalance);

    public void remove(HrLeaveScheduleTransfer hrLeaveBalance);

    public List<HrLeaveScheduleTransfer> findAll();

    public HrLeaveScheduleTransfer find(Object id);

    public boolean isTransferRequestExist(HrLeaveSchedule hrleaveschdule);

    public List<HrLeaveScheduleTransfer> findAllRequests();


   
    
}
