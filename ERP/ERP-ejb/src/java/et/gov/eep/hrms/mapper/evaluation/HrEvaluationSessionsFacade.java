/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
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
public class HrEvaluationSessionsFacade extends AbstractFacade<HrEvaluationSessions> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvaluationSessionsFacade() {
        super(HrEvaluationSessions.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query"> 
    public List<HrEvaluationSessions> readActiveSession(String toDayInEC) {
        Query query = em.createNamedQuery("HrEvaluationSessions.findActiveSession", HrEvaluationSessions.class);
        query.setParameter("toDayInEC", toDayInEC);
        try {
            return (List<HrEvaluationSessions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrEvaluationSessions readEvaluationSessionDetail(int evaSessionId) {
        Query query = em.createNamedQuery("HrEvaluationSessions.findById");
        query.setParameter("id", evaSessionId);
        try {
            HrEvaluationSessions selectedSession = (HrEvaluationSessions) query.getResultList().get(0);
            return selectedSession;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEvaluationSessions> findByYear(int year) {
        Query query = em.createNamedQuery("HrEvaluationSessions.findBySessionYear", HrEvaluationSessions.class);
        query.setParameter("sessionYear", year);
        try {
            return (List<HrEvaluationSessions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrEvaluationSessions findById(HrEvaluationSessions hrEvaluationSessions) {
        Query query = em.createNamedQuery("HrEvaluationSessions.findById");
        query.setParameter("id", hrEvaluationSessions.getId());
        try {
            return (HrEvaluationSessions) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isExist(HrEvaluationSessions evaSession) {
        boolean isExist;
        Query query = em.createNamedQuery("HrEvaluationSessions.checkDuplicate");
        query.setParameter("year", evaSession.getSessionYear());
        query.setParameter("term", evaSession.getTerm());
        try {
            if (query.getResultList().size() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query"> 
    public List<HrEvaluationSessions> filteredEvaluationSession(int year, int searchCondition) {
        Query query;
        String statusQuery = " ";
        if (year > 0) {
            statusQuery += "WHERE HR_EVALUATION_SESSIONS.SESSION_YEAR='" + year + "' ";
        }
        if (searchCondition == 0) {
            query = em.createNativeQuery("SELECT * FROM (SELECT * FROM HR_EVALUATION_SESSIONS "
                    //                    + statusQuery
                    + " ORDER BY START_DATE DESC)"
                    + " WHERE ROWNUM <=100", HrEvaluationSessions.class);
        } else {
            query = em.createNativeQuery(" SELECT "
                    + " HR_EVALUATION_SESSIONS.ID, "
                    + " HR_EVALUATION_SESSIONS.SESSION_YEAR, "
                    + " HR_EVALUATION_SESSIONS.TERM, "
                    + " HR_EVALUATION_SESSIONS.START_DATE, "
                    + " HR_EVALUATION_SESSIONS.END_DATE, "
                    + " HR_EMPLOYEES.FIRST_NAME, "
                    + " HR_EMPLOYEES.MIDDLE_NAME, "
                    + " HR_EMPLOYEES.LAST_NAME, "
                    + " HR_EVALUATION_SESSIONS.PREPARED_ON, "
                    + " HR_EVALUATION_SESSIONS.REMARK "
                    + " FROM HR_EVALUATION_SESSIONS "
                    + " INNER JOIN HR_EMPLOYEES "
                    + " ON HR_EVALUATION_SESSIONS.PREPARED_BY = HR_EMPLOYEES.ID "
                    //                    + statusQuery
                    + "ORDER BY START_DATE DESC", HrEvaluationSessions.class);
        }
        try {
            return (List<HrEvaluationSessions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
