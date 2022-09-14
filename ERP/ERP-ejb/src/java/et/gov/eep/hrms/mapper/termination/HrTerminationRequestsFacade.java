/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.termination;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.termination.HrTerminationTypes;
import java.util.ArrayList;
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
public class HrTerminationRequestsFacade extends AbstractFacade<HrTerminationRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTerminationRequestsFacade() {
        super(HrTerminationRequests.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public ArrayList<HrTerminationTypes> searchByTerminationName(HrTerminationTypes hrTerminationType) {
        Query query = em.createNamedQuery("HrTerminationTypes.findByTerminationNameLike");
        query.setParameter("terminationName", hrTerminationType.getTerminationName().toUpperCase() + '%');
        try {
            ArrayList<HrTerminationTypes> terminationNameList = new ArrayList(query.getResultList());
            return terminationNameList;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrTerminationTypes getByTerminationName(HrTerminationTypes hrTerminationType) {
        Query query = em.createNamedQuery("HrTerminationTypes.findByTerminationName");
        query.setParameter("terminationName", hrTerminationType.getTerminationName());
        try {
            HrTerminationTypes terminationName = (HrTerminationTypes) query.getResultList().get(0);
            return terminationName;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrTerminationTypes checkTerminationName(HrTerminationTypes hrTerminationTypes) {
        Query query = em.createNamedQuery("HrTerminationTypes.findByTerminationName", HrTerminationTypes.class);
        query.setParameter("terminationName", hrTerminationTypes.getTerminationName());
        try {
            HrTerminationTypes name = (HrTerminationTypes) query.getResultList().get(0);
            return name;
        } catch (Exception exce) {
            exce.printStackTrace();
            return null;
        }
    }

    public HrTerminationRequests getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrTerminationRequests.findById");
        query.setParameter("id", request);
        try {
            HrTerminationRequests selectedRequest = (HrTerminationRequests) query.getResultList().get(0);
            return selectedRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTerminationRequests> findPreparedList() {
        Query query = em.createNamedQuery("HrTerminationRequests.findPreparedList");
        try {
            List<HrTerminationRequests> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrTerminationRequests> loadTerminationList(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        Query query = em.createNativeQuery("SELECT * FROM HR_TERMINATION_REQUESTS "
                + queryStatus
                + "ORDER BY REQUEST_DATE DESC", HrTerminationRequests.class);
        try {
            return (List<HrTerminationRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTerminationRequests> loadTerminationReqList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TERMINATION_REQUESTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "' OR"
                    + " rq.status='" + status.getStatus3() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrTerminationRequests.class);
            return (List<HrTerminationRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTerminationRequests> loadTerminationList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TERMINATION_REQUESTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrTerminationRequests.class);
            return (List<HrTerminationRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTerminationRequests> searchByStatus(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            statusQuery = " WHERE (STATUS='0')";
        } else if (status == 1) {
            statusQuery = " WHERE (STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            statusQuery = " WHERE (STATUS='2' OR STATUS='4')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_TERMINATION_REQUESTS "
                + statusQuery
                + "ORDER BY REQUEST_DATE DESC", HrTerminationRequests.class);
        try {
            return (List<HrTerminationRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTerminationRequests> loadaApprove(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TERMINATION_REQUESTS termination"
                    + " WHERE termination.STATUS ='" + status.getStatus1() + "' AND"
                    + " termination.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY termination.REQUEST_DATE DESC", HrTerminationRequests.class);
            return (List<HrTerminationRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTerminationRequests> loadReject(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TERMINATION_REQUESTS termination"
                    + " WHERE (termination.status ='" + status.getStatus1() + "' OR"
                    + " termination.status ='" + status.getStatus2() + "') AND"
                    + " termination.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY termination.REQUEST_DATE DESC", HrTerminationRequests.class);
            return (List<HrTerminationRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>

    public int terminationCountPerYear(String budgetYear) {
        System.out.println("get==="+budgetYear);
        Query query=em.createNativeQuery("select * from HR_TERMINATION_REQUESTS where TERMINATION_DATE Like '%"+budgetYear+"'",HrTerminationRequests.class);
        System.out.println("query.executeUpdate()=="+query.getResultList().size());
        return query.getResultList().size();
    }
}
