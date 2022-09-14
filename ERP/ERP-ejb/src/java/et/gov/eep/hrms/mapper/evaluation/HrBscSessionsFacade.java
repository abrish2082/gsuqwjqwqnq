/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
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
public class HrBscSessionsFacade extends AbstractFacade<HrBscSessions> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrBscSessionsFacade() {
        super(HrBscSessions.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrBscSessions getSelectedRequest(int session) {
        Query query = em.createNamedQuery("HrBscSessions.findById");
        query.setParameter("id", session);
        try {
            HrBscSessions selectedSession = (HrBscSessions) query.getResultList().get(0);
            return selectedSession;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrBscSessions> findByYear(int year) {
        Query query = em.createNamedQuery("HrBscSessions.findByBscYear", HrBscSessions.class);
        query.setParameter("bscYear", year);
        try {
            return (List<HrBscSessions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrBscSessions> filteredBscSession(int year, int searchCondition) {
        Query query;
        String statusQuery = " ";
        if (year > 0) {
            statusQuery += "WHERE HR_BSC_SESSIONS.BSC_YEAR='" + year + "' ";
        }
        if (searchCondition == 0) {
            query = em.createNativeQuery("SELECT * FROM (SELECT * FROM HR_BSC_SESSIONS"
                    + " ORDER BY START_DATE DESC)"
                    + " WHERE ROWNUM <=10", HrBscSessions.class);
        } else {
            query = em.createNativeQuery("SELECT"
                    + "HR_BSC_SESSIONS.ID,"
                    + "HR_BSC_SESSIONS.BSC_YEAR,"
                    + "HR_BSC_SESSIONS.TERM,"
                    + "HR_BSC_SESSIONS.START_DATE,"
                    + "HR_BSC_SESSIONS.END_DATE,"
                    + "HR_EMPLOYEES.FIRST_NAME,"
                    + "HR_EMPLOYEES.MIDDLE_NAME,"
                    + "HR_EMPLOYEES.LAST_NAME, "
                    + "HR_BSC_SESSIONS.PREPARED_ON,"
                    + "HR_BSC_SESSIONS.REMARK"
                    + "FROM HR_BSC_SESSIONS"
                    + "INNER JOIN HR_EMPLOYEES"
                    + "ON HR_BSC_SESSIONS.PREPARED_BY = HR_EMPLOYEES.ID"
                    + "ORDER BY START_DATE DESC", HrBscSessions.class);
        }
        try {
            return (List<HrBscSessions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
