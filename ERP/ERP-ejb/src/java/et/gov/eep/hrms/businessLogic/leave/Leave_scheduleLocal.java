/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Desu
 */
@Local
public interface Leave_scheduleLocal {

    boolean saveOrUpdate(HrLeaveSchedule hrleaveschedule);

    public List<HrEmployees> searchEmployeeByName(String reqName);

    public HrEmployees getSelectTerminationRequest(HrEmployees employee);

    public HrEmployees searchEmployeeByID(HrEmployees employee);

    public HrEmployees getEmployeeInfo(HrEmployees employee);   

    public boolean edit(HrLeaveSchedule leaveSchedule);

    public HrLeaveSchedule findByEmp(HrLeaveSchedule leaveSchedule);

    public ArrayList<HrLeaveSchedule> loadScheduleRequests();

    public HrLeaveSchedule findById(int schId);

    public HrLeaveSchedule findByBudgetYear(FmsLuBudgetYear fmsLuBudgetYear,HrEmployees employee);

   

   

   

}
