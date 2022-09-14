/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.hrms.entity.employee.HrEmployees;

import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import et.gov.eep.hrms.mapper.leave.HrLeaveRequestFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kibrom
 */
@Stateless
public class LeaveRequestBean implements LeaveRequestBeanLocal {

    @EJB
    HrLeaveRequestFacade leaveSetingFacadeLocal;

    @Override
    public void saveOrUpdate(HrLeaveRequest hrLeaveRequest) {
        try {
            leaveSetingFacadeLocal.saveOrUpdate(hrLeaveRequest);
            //return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
       // return false;
    }


    @Override
    public ArrayList<HrLeaveRequest> populateEmpLeaveRequests(HrEmployees employee, HrLuLeaveTypes hrLeaveTypes) {
        try {
            return leaveSetingFacadeLocal.populateEmpLeaveRequests(employee, hrLeaveTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ArrayList<HrLeaveRequest> populatePendingRequests() {
        try {
            return leaveSetingFacadeLocal.populatePendingRequests();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ArrayList<HrLeaveRequest> populateHRPendingRequests() {
        try {
            return leaveSetingFacadeLocal.populateHRPendingRequests();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ArrayList<HrLeaveRequest> populateEmployeesOnLeave() {
        try {
            return leaveSetingFacadeLocal.populateEmployeesOnLeave();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ArrayList<HrLeaveRequest> findOrderedEmployeeLeave(HrEmployees employee) {
        try {
            return leaveSetingFacadeLocal.findOrderedEmployeeLeave(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public HrLeaveRequest findLocalAdos(int parseInt) {
        return leaveSetingFacadeLocal.findLocalAdos(parseInt);
    }

    @Override
    public HrLeaveRequest findUnApprovedLeave(HrEmployees employee, HrLuLeaveTypes hrLuLeaveTypes) {
        return leaveSetingFacadeLocal.findUnApprovedLeave(employee, hrLuLeaveTypes);
    }

}
