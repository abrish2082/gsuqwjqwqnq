/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import javax.persistence.Query;

/**
 *
 * @author prg
 */
@Stateless
public class HrLeaveRequestFacade extends AbstractFacade<HrLeaveRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public HrLeaveRequestFacade() {
        super(HrLeaveRequest.class);
    }
    
    public ArrayList<HrLeaveRequest> populateEmpLeaveRequests(HrEmployees employee, HrLuLeaveTypes hrLeaveTypes) {
        
        try {
            Query query = em.createNamedQuery("HrLeaveRequest.searchEmpRequest");
            query.setParameter("empId", employee.getEmpId());
            query.setParameter("leaveId", hrLeaveTypes.getId());
            ArrayList<HrLeaveRequest> requests = new ArrayList(query.getResultList());
            return requests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveRequest> populatePendingRequests() {
        
        try {
            Query query = em.createNamedQuery("HrLeaveRequest.findAllPendingRequest");
            ArrayList<HrLeaveRequest> emprequests = new ArrayList(query.getResultList());
            return emprequests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveRequest> populateHRPendingRequests() {
        
        try {
            Query query = em.createNamedQuery("HrLeaveRequest.findAllHRPendingRequest");
            ArrayList<HrLeaveRequest> emprequests = new ArrayList(query.getResultList());
            return emprequests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveRequest> populateEmployeesOnLeave() {
        
        try {
            Query query = em.createNamedQuery("HrLeaveRequest.findAllEmpOnLeave");
            ArrayList<HrLeaveRequest> emprequests = new ArrayList(query.getResultList());
            return emprequests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveRequest> findOrderedEmployeeLeave(HrEmployees employee) {
        
        try {
            Query query = em.createNamedQuery("HrLeaveRequest.findOrderedEmployeeLeave");
            query.setParameter("emp", employee.getId());
            ArrayList<HrLeaveRequest> hstry = new ArrayList(query.getResultList());
            if (hstry != null) {
                return hstry;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public HrLeaveRequest findLocalAdos(Integer id) {
        return super.find(id);
    }
    
    public HrLeaveRequest findUnApprovedLeave(HrEmployees employee, HrLuLeaveTypes hrLuLeaveTypes) {
        
        try {
            Query query = em.createNamedQuery("HrLeaveRequest.findByUnApproveLeave");
            query.setParameter("empObj", employee);
            query.setParameter("leaveType", hrLuLeaveTypes);
            query.setParameter("deptStatu", 0);
            return (HrLeaveRequest) query.getSingleResult();
            
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
//</editor-fold>
}
