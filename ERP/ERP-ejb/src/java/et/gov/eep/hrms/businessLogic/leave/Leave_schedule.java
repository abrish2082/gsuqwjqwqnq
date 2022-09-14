/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.hrms.mapper.leave.HrLeaveScheduleFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

import javax.ejb.Stateless;

/**
 *
 * @author Desu
 */
@Stateless
public class Leave_schedule implements Leave_scheduleLocal {

    @EJB
    private HrLeaveScheduleFacade hrLeaveScheduleFacade;
    @EJB
    private HrEmployeesFacade hrEmployeeFacadeLocal;
    @EJB
    HrEmployeesFacade employeeFacadeLocal;

    @Override

    public List<HrEmployees> searchEmployeeByName(String reqName) {
        return hrEmployeeFacadeLocal.findEmployeesByName(reqName);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public boolean saveOrUpdate(HrLeaveSchedule hrleaveschedule) {
        try {
            hrLeaveScheduleFacade.saveOrUpdate(hrleaveschedule);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;

        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param employee
     * @return
     */
    //  public ArrayList<HrEmployee> getSelectTerminationRequest(HrEmployee employee) {
    //return hrLeaveScheduleFacade.findByName(employee);//To change body of generated methods, choose Tools | Templates.
    // }
    @Override
    public HrEmployees searchEmployeeByID(HrEmployees employee) {
        return employeeFacadeLocal.findById(employee); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HrEmployees getSelectTerminationRequest(HrEmployees employee) {
        return employeeFacadeLocal.findByName(employee); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<HrLeaveSchedule> loadScheduleRequests() {
        return hrLeaveScheduleFacade.loadScheduleRequests();
    }

    @Override
    public HrEmployees getEmployeeInfo(HrEmployees employee) {
       
        return null;
    }

    @Override
    public HrLeaveSchedule findByEmp(HrLeaveSchedule leaveSchedule) {
        return hrLeaveScheduleFacade.findLeaveShedule(leaveSchedule);
    }
 
    @Override
    public HrLeaveSchedule findById(int schId) {
        return hrLeaveScheduleFacade.findById(schId);
    }
    
    @Override
    public boolean edit(HrLeaveSchedule leaveSchedule) {
        try {
            hrLeaveScheduleFacade.edit(leaveSchedule);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public HrLeaveSchedule findByBudgetYear(FmsLuBudgetYear fmsLuBudgetYear,HrEmployees employee) {
     return hrLeaveScheduleFacade.findByBudgetYear(fmsLuBudgetYear,employee);
    }
}
