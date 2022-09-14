/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
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
public class HrEvalCriteriaProbationsFacade extends AbstractFacade<HrEvalCriteriaProbations> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvalCriteriaProbationsFacade() {
        super(HrEvalCriteriaProbations.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrEvalCriteriaProbations> findByCriteriaName(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findByCriteriaNameLike");
        query.setParameter("criteriaName", hrEvalCriteriaProbations.getCriteriaName().toUpperCase() + '%');
        try {
            ArrayList<HrEvalCriteriaProbations> criteria = new ArrayList(query.getResultList());
            return criteria;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvalCriteriaProbations findByCriteriaNameObj(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findByCriteriaName");
        query.setParameter("criteriaName", hrEvalCriteriaProbations.getCriteriaName());
        try {
            HrEvalCriteriaProbations emp = (HrEvalCriteriaProbations) query.getResultList().get(0);
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrEvalCriteriaProbations getSelectedRequest(int criteria) {
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findById");
        query.setParameter("id", criteria);
        try {
            HrEvalCriteriaProbations selectedCriteria = (HrEvalCriteriaProbations) query.getResultList().get(0);
            return selectedCriteria;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEvalCriteriaProbations> findAllCriteria(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findAll");
        try {
            List<HrEvalCriteriaProbations> criteria = new ArrayList(query.getResultList());
            return criteria;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvalCriteriaProbations findCriteriaByName(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findByCriteriaName");
        query.setParameter("criteriaName", hrEvalCriteriaProbations.getCriteriaName());
        try {
            HrEvalCriteriaProbations criteria = (HrEvalCriteriaProbations) (query.getResultList());
            return criteria;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvalCriteriaProbations> searchCriteriaName(HrEvalCriteriaProbations criteriaName) {
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findByCriteriaName");
        query.setParameter("criteriaName", criteriaName.getCriteriaName().toUpperCase() + '%');
        try {
            ArrayList<HrEvalCriteriaProbations> criteria = new ArrayList<>(query.getResultList());
            return criteria;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isCriteriaExist(HrEvalCriteriaProbations evaluationCriteria) {
        boolean isExist;
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findByCriteria");
        query.setParameter("criteriaName", evaluationCriteria.getCriteriaName());
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

    public List<HrEvalCriteriaProbations> findByCriteria(String criteriaName) {
        Query query = em.createNamedQuery("HrEvalCriteriaProbations.findByCriteria");
        query.setParameter("criteriaName", criteriaName);
        try {
            return (List<HrEvalCriteriaProbations>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    public List<HrEvalCriteriaProbations> filteredEvaluationCriteriaProbation(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            statusQuery = " WHERE (STATUS='0' OR STATUS='1') ";
        }
        Query query = em.createNativeQuery("SELECT *"
                + "FROM HR_EVAL_CRITERIA_PROBATIONS "
                + statusQuery
                + "ORDER BY CRITERIA_NAME", HrEvalCriteriaProbations.class);
        try {
            return (List<HrEvalCriteriaProbations>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
