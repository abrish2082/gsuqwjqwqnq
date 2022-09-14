/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.termination;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class HrRetirementRequestFacade extends AbstractFacade<HrRetirementRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrRetirementRequestFacade() {
        super(HrRetirementRequest.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrRetirementRequest getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrRetirementRequest.findById");
        query.setParameter("id", request);
        try {
            HrRetirementRequest selectedRequest = (HrRetirementRequest) query.getResultList().get(0);
            return selectedRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrRetirementRequest> findPreparedList() {
        Query query = em.createNamedQuery("HrRetirementRequest.findPreparedList");
        try {
            List<HrRetirementRequest> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrRetirementRequest> loadRetirementList(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        Query query = em.createNativeQuery("SELECT * FROM HR_RETIREMENT_REQUEST "
                + queryStatus
                + "ORDER BY REQUEST_DATE DESC", HrRetirementRequest.class);
        try {
            return (List<HrRetirementRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRetirementRequest> loadRetirementReqList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RETIREMENT_REQUEST rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "' OR"
                    + " rq.status='" + status.getStatus3() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrRetirementRequest.class);
            return (List<HrRetirementRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRetirementRequest> loadRetirementList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RETIREMENT_REQUEST rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrRetirementRequest.class);
            return (List<HrRetirementRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRetirementRequest> searchByStatus(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            statusQuery = " WHERE (STATUS='0')";
        } else if (status == 1) {
            statusQuery = " WHERE (STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            statusQuery = " WHERE (STATUS='2' OR STATUS='4')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_RETIREMENT_REQUEST "
                + statusQuery
                + "ORDER BY REQUEST_DATE DESC", HrRetirementRequest.class);
        try {
            return (List<HrRetirementRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRetirementRequest> loadaApprove(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RETIREMENT_REQUEST retirment"
                    + " WHERE retirment.STATUS ='" + status.getStatus1() + "' AND"
                    + " retirment.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY retirment.REQUEST_DATE DESC", HrRetirementRequest.class);
            return (List<HrRetirementRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRetirementRequest> loadReject(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RETIREMENT_REQUEST retirment"
                    + " WHERE (retirment.status ='" + status.getStatus1() + "' OR"
                    + " retirment.status ='" + status.getStatus2() + "') AND"
                    + " retirment.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY retirment.REQUEST_DATE DESC", HrRetirementRequest.class);
            return (List<HrRetirementRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
}
