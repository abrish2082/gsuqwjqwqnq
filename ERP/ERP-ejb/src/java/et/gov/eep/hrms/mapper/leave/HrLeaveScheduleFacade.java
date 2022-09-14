/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Desu
 */
@Stateless
public class HrLeaveScheduleFacade extends AbstractFacade<HrLeaveSchedule> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public HrLeaveScheduleFacade() {
        super(HrLeaveSchedule.class);
    }
    
    public HrEmployees findByName(HrEmployees employee) {
        Query query = em.createNamedQuery("HrEmployee.findLikeEmpId");
        query.setParameter("employeeId", employee.getEmpId() + '%');
        try {
            HrEmployees emps = (HrEmployees) query.getResultList().get(0);
            return emps;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public HrLeaveSchedule findById(int schId) {
        Query query = em.createNamedQuery("HrLeaveSchedule.findById");
        query.setParameter("schId", schId);
        try {
            HrLeaveSchedule sch = (HrLeaveSchedule) query.getResultList().get(0);
            return sch;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public HrEmployees getEmployeeInfo(HrEmployees employee) {
        Query query = em.createNamedQuery("HrEmployee.findByFirstName");
        
        query.setParameter("firstName", employee.getFirstName());
        try {
            HrEmployees empInfo = (HrEmployees) query.getResultList().get(0);
            return empInfo;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public HrLeaveSchedule findLeaveShedule(HrLeaveSchedule leaveSchedule) {
        try {
            Query query = em.createNamedQuery("HrLeaveSchedule.findByEmployeeIdAndYear", HrLeaveSchedule.class);
            query.setParameter("employeeId", leaveSchedule.getEmployeeId().getEmpId());
            query.setParameter("scheduleyear", leaveSchedule.getLeaveYearId().getLuBudgetYearId());
            if (query.getResultList().size() > 0) {
                HrLeaveSchedule sche = (HrLeaveSchedule) query.getResultList().get(0);
                System.err.println("sizeeeeeeeeeeeeebyemployeeeee" + query.getResultList().size());
                return sche;
            } else {
                HrLeaveSchedule sche = new HrLeaveSchedule();
                return sche;
            }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<HrLeaveSchedule> loadScheduleRequests() {
        Query query = em.createNamedQuery("HrLeaveSchedule.findAllRequests");
        try {
            if (query.getResultList().size() > 0) {
                ArrayList<HrLeaveSchedule> req = new ArrayList(query.getResultList());
                return req;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
        ArrayList<HrLeaveSchedule> req = new ArrayList<>();
        return req;
    }
    
    public HrLeaveSchedule findByBudgetYear(FmsLuBudgetYear fmsLuBudgetYear, HrEmployees employee) {
        try {
            Query query = em.createNamedQuery("HrLeaveSchedule.findByBugdetYearAndEmp", HrLeaveSchedule.class);
            query.setParameter("fmsLuBudgetYear", fmsLuBudgetYear);
            query.setParameter("employee", employee);
            HrLeaveSchedule hrLeaveSchedule = (HrLeaveSchedule) query.getResultList().get(0);
            return hrLeaveSchedule;
            
        } catch (Exception ex) {
            return null;
        }
    }
    
//</editor-fold>
}
