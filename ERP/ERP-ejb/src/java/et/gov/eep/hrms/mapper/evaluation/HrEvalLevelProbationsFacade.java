/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrEvalLevelProbations;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ob
 */
@Stateless
public class HrEvalLevelProbationsFacade extends AbstractFacade<HrEvalLevelProbations> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvalLevelProbationsFacade() {
        super(HrEvalLevelProbations.class);
    }
    //<editor-fold defaultstate="collapsed" desc="Named query">

    public List<HrEvalLevelProbations> findByLevelName(HrEvalLevelProbations hrEvalLevelProbations) {
        Query query = em.createNamedQuery("HrEvalLevelProbations.findByEvaluationLevelLike");
        query.setParameter("evaluationLevel", hrEvalLevelProbations.getEvaluationLevel().toUpperCase() + '%');
        try {
            ArrayList<HrEvalLevelProbations> level = new ArrayList(query.getResultList());
            return level;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvalLevelProbations findByLevelNameObj(HrEvalLevelProbations hrEvalLevelProbations) {
        Query query = em.createNamedQuery("HrEvalLevelProbations.findByEvaluationLevelLike");
        query.setParameter("evaluationLevel", hrEvalLevelProbations.getEvaluationLevel());
        try {
            HrEvalLevelProbations emp = (HrEvalLevelProbations) query.getResultList().get(0);
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrEvalLevelProbations getSelectedRequest(int level) {
        Query query = em.createNamedQuery("HrEvalLevelProbations.findById");
        query.setParameter("id", level);
        try {
            HrEvalLevelProbations selectedlevel = (HrEvalLevelProbations) query.getResultList().get(0);
            return selectedlevel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    public List<HrEvalLevelProbations> filteredEvaluationLevelProbation(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            statusQuery = " WHERE (STATUS='0' OR STATUS='1') ";
        }
        Query query = em.createNativeQuery("SELECT *"
                + "FROM HR_EVAL_LEVEL_PROBATIONS "
                + statusQuery
                + "ORDER BY EVALUATION_LEVEL", HrEvalLevelProbations.class);
        try {
            return (List<HrEvalLevelProbations>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
}
