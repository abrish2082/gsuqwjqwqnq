/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author kibrom
 */
@Local
public interface LeaveRequestBeanLocal {

    public void saveOrUpdate(HrLeaveRequest leaveRequest);

 //   public boolean edit(HrLeaveRequest leaveRequest);

    public ArrayList<HrLeaveRequest> populateEmpLeaveRequests(HrEmployees employee, HrLuLeaveTypes hrLeaveTypes);

    public ArrayList<HrLeaveRequest> populatePendingRequests();

    public ArrayList<HrLeaveRequest> populateHRPendingRequests();
    public ArrayList<HrLeaveRequest> populateEmployeesOnLeave();

    public ArrayList<HrLeaveRequest> findOrderedEmployeeLeave(HrEmployees hremployee);

    public HrLeaveRequest findLocalAdos(int parseInt);

    public HrLeaveRequest findUnApprovedLeave(HrEmployees employee, HrLuLeaveTypes hrLuLeaveTypes);

   
}
