/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationLevels;
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
public class HrEvaluationLevelsFacade extends AbstractFacade<HrEvaluationLevels> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvaluationLevelsFacade() {
        super(HrEvaluationLevels.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrEvaluationLevels getSelectedLevel(int level) {
        Query query = em.createNamedQuery("HrEvaluationLevels.findById");
        query.setParameter("id", level);
        try {
            HrEvaluationLevels selectedLevel = (HrEvaluationLevels) query.getResultList().get(0);
            return selectedLevel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrEvaluationLevels> filteredEvaluationLevel(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            statusQuery = " WHERE (STATUS='0' OR STATUS='1') ";
        }
        Query query = em.createNativeQuery("SELECT ID, EVALUATION_LEVEL, "
                + "MINIMUM_POINT, MAXIMUM_POINT, STATUS "
                + "FROM HR_EVALUATION_LEVELS "
                + statusQuery
                + "ORDER BY MINIMUM_POINT DESC", HrEvaluationLevels.class);
        try {
            return (List<HrEvaluationLevels>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
}
