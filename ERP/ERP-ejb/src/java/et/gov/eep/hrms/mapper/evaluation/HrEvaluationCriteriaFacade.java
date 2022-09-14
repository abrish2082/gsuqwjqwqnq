/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.lookup.HrLuEvaluationCategory;
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
public class HrEvaluationCriteriaFacade extends AbstractFacade<HrEvaluationCriteria> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvaluationCriteriaFacade() {
        super(HrEvaluationCriteria.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public ArrayList<HrLuEvaluationCategory> findAllEvaluationCategory() {
        Query query = em.createNamedQuery("HrLuEvaluationCategory.findAll");
        try {
            ArrayList<HrLuEvaluationCategory> category = new ArrayList(query.getResultList());
            return category;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrLuEvaluationCategory findCategoryName(HrLuEvaluationCategory hrLuEvaluationCategory) {
        Query query = em.createNamedQuery("HrLuEvaluationCategory.findByCategoryName");
        query.setParameter("categoryName", hrLuEvaluationCategory.getCategoryName());
        try {
            HrLuEvaluationCategory category = (HrLuEvaluationCategory) query.getResultList().get(0);
            return category;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvaluationCriteria readEvaluationCriteriaDetail(int evalCriteriaId) {
        Query query = em.createNamedQuery("HrEvaluationCriteria.findById");
        query.setParameter("id", evalCriteriaId);
        try {
            HrEvaluationCriteria selectedCriteria = (HrEvaluationCriteria) query.getResultList().get(0);
            return selectedCriteria;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvaluationCriteria checkCriteriaName(HrEvaluationCriteria hrEvaluationCriteria) {
        Query query = em.createNamedQuery("HrEvaluationCriteria.findByCriteriaName");
        query.setParameter("criteriaName", hrEvaluationCriteria.getCriteriaName());
        try {
            HrEvaluationCriteria criteria = (HrEvaluationCriteria) query.getResultList().get(0);
            return criteria;
        } catch (Exception exce) {
            exce.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvaluationCriteria> searchCriteriaName(HrEvaluationCriteria criteriaName) {
        Query query = em.createNamedQuery("HrEvaluationCriteria.findByCriteriaNameLike");
        query.setParameter("criteriaName", criteriaName.getCriteriaName().toUpperCase() + '%');
        try {
            ArrayList<HrEvaluationCriteria> criteria = new ArrayList<>(query.getResultList());
            return criteria;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEvaluationCriteria> findByName(String criteriaName) {
        Query query = em.createNamedQuery("HrEvaluationCriteria.findByCriteriaName");
        query.setParameter("criteriaName", criteriaName);
        try {
            return (List<HrEvaluationCriteria>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean isCriteriaExist(HrEvaluationCriteria evaluationCriteria) {
        boolean isExist;
        Query query = em.createNamedQuery("HrEvaluationCriteria.findByCriteriaName");
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrEvaluationCriteria> filteredEvaluationCriteria(int status, int category) {
        String statusQuery = " WHERE CRITERIA.STATUS='" + status + "' ";
        if (status == 2) {
            statusQuery = " WHERE (CRITERIA.STATUS='0' OR CRITERIA.STATUS='1') ";
        }
        if (category > 0) {
            statusQuery += " AND CRITERIA.CATEGORY_ID='" + category + "' ";
        }
        Query query = em.createNativeQuery("SELECT CRITERIA.ID, CRITERIA.CRITERIA_NAME, "
                + "CRITERIA.DESCRIPTION, CRITERIA.WEIGHT, "
                + "CRITERIA.CATEGORY_ID, CATEGORY.CATEGORY_NAME, CRITERIA.STATUS "
                + "FROM HR_EVALUATION_CRITERIA CRITERIA "
                + "INNER JOIN HR_LU_EVALUATION_CATEGORY CATEGORY "
                + "ON CRITERIA.CATEGORY_ID = CATEGORY.ID "
                + statusQuery
                + "ORDER BY CATEGORY.CATEGORY_NAME,CRITERIA.CRITERIA_NAME", HrEvaluationCriteria.class);
        try {
            return (List<HrEvaluationCriteria>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
