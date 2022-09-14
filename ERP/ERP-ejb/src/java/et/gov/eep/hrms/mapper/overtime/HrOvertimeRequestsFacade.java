/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.overtime;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRequests;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.status;

/**
 *
 * @author meles
 */
@Stateless
public class HrOvertimeRequestsFacade extends AbstractFacade<HrOvertimeRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrOvertimeRequestsFacade() {
        super(HrOvertimeRequests.class);
    }

    public ArrayList findBymonth(HrOvertimeRequests hrOvertimeRequests) {
        Query queryReq = em.createNamedQuery("HrOvertimeRequests.findByMonth");
        try {
            queryReq.setParameter("month", hrOvertimeRequests.getMonth());
            ArrayList<HrOvertimeRequests> absentList = new ArrayList(queryReq.getResultList());
            return absentList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrOvertimeRequests> loadFiltereddata(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='2' OR STATUS='3' OR STATUS='4')";
        } else if (status == 4) {
            queryStatus = " WHERE(OR STATUS='2' OR STATUS='4')";
        } else if (status == 3) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_OVERTIME_REQUESTS "
                + queryStatus
                + "ORDER BY PREPARED_ON DESC", HrOvertimeRequests.class);
        try {
            return (List<HrOvertimeRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrOvertimeRequests> loadFiltereddata(Status status1, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_OVERTIME_REQUESTS rq\n"
                    + " WHERE (rq.status='" + status1.getStatus1() + "')", HrOvertimeRequests.class);
            System.out.println("-----result----" + query.getResultList());
            return (List<HrOvertimeRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrOvertimeRequests> populateTableApprove(Status status1, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_OVERTIME_REQUESTS rq\n"
                    + " WHERE (rq.status='" + status1.getStatus1() + "' OR"
                    + " rq.status='" + status1.getStatus2() + "')", HrOvertimeRequests.class);
            System.out.println("-----result----" + query.getResultList());
            return (List<HrOvertimeRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrOvertimeRequests> findrequestlist() {
        Query query = em.createNamedQuery("HrOvertimeRequests.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<HrOvertimeRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrOvertimeRequests> populateTableReject(Status status1, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_OVERTIME_REQUESTS rq\n"
                    + " WHERE (rq.status='" + status1.getStatus2() + "' OR"
                    + " rq.status='" + status1.getStatus3() + "')", HrOvertimeRequests.class);
            System.out.println("-----result----" + query.getResultList());
            return (List<HrOvertimeRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
