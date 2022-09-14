/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.attendace;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonus;
import et.gov.eep.hrms.entity.attendance.HrAttendances;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrAttendancesFacade extends AbstractFacade<HrAttendances> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrAttendancesFacade() {
        super(HrAttendances.class);
    }

    public HrAttendances findByabsentdate(String hrattendancesdata) {
        Query query = em.createNamedQuery("HrAttendances.findByPreparedOn");
        query.setParameter("preparedOn", hrattendancesdata);
        try {
            HrAttendances hrattendance = (HrAttendances) query.getResultList().get(0);
            return hrattendance;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAttendances> loadFiltereddata(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='2' OR STATUS='1' OR STATUS='3' OR STATUS='4')";
        } else if (status == 4) {
            queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
        }
        else if (status == 3) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_ATTENDANCES "
                + queryStatus
                + "ORDER BY REPORTED_DATE DESC", HrAttendances.class);
        try {
            return (List<HrAttendances>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAttendances> findAll(HrAttendances hrattendances) {
        Query query = em.createNativeQuery("select distinct reported_date  from hr_attendances");
        try {
            return (List<HrAttendances>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList findabsentdated(HrAttendances hrattendances) {
        Query queryReq = em.createNamedQuery("HrAttendances.findByPreparedOn");
        try {
            queryReq.setParameter("preparedOn", hrattendances.getPreparedOn());
            ArrayList<HrAttendances> absentList = new ArrayList(queryReq.getResultList());
            return absentList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrAttendances> findrequestlist() {
       Query query = em.createNamedQuery("HrAttendances.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<HrAttendances>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
}
