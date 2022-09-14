/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationLevels;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
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
public class HrEvaluationResultsFacade extends AbstractFacade<HrEvaluationResults> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvaluationResultsFacade() {
        super(HrEvaluationResults.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">    
    public List<String> searchByStatus(Integer result) {
        List<String> resultList = null;
        try {
            Query query = em.createNamedQuery("HrEvaluationSessions.findBySessionYear");
            query.setParameter("sessionYear", result);
            resultList = (List<String>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> readActiveSessions(String todayInEC) {
        List<String> resultList = null;
        try {
            Query query = em.createNamedQuery("HrEvaluationSessions.findActiveSession");
            query.setParameter("sessionYear", todayInEC);
            resultList = (List<String>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEvaluationCriteria> findAllCriteria() {
        Query query = em.createNamedQuery("HrEvaluationCriteria.findAll");
        try {
            List<HrEvaluationCriteria> criteria = new ArrayList(query.getResultList());
            return criteria;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrEvaluationLevels> findAllLevel() {
        Query query = em.createNamedQuery("HrEvaluationLevels.findAll");
        try {
            List<HrEvaluationLevels> level = new ArrayList(query.getResultList());
            return level;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEvaluationSessions> fetchSession(HrEvaluationSessions hrEvaluationSessions) {
        Query query = em.createNamedQuery("HrEvaluationSessions.findById");
        query.setParameter("id", hrEvaluationSessions.getId());
        try {
            ArrayList<HrEvaluationSessions> sessionInfo = new ArrayList(query.getResultList());
            return sessionInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvaluationCriteria findByCriteriaId(HrEvaluationCriteria hrEvaluationCriteria) {
        Query query = em.createNamedQuery("HrEvaluationCriteria.findById");
        query.setParameter("id", hrEvaluationCriteria.getId());
        try {
            return (HrEvaluationCriteria) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvaluationLevels findByLevelId(HrEvaluationLevels hrEvaluationLevels) {
        Query query = em.createNamedQuery("HrEvaluationLevels.findById");
        query.setParameter("id", hrEvaluationLevels.getId());
        try {
            return (HrEvaluationLevels) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvaluationResults> findAllResult() {
        Query query = em.createNamedQuery("HrEvaluationResults.findAll");
        try {
            ArrayList<HrEvaluationResults> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEvaluationResults> findByEmpId(HrEmployees empId) {
        Query query = em.createNamedQuery("HrEvaluationResults.findByEmpId");
        query.setParameter("empId", empId.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrEvaluationResults> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvaluationResults> findByName(HrEmployees empName) {
        Query query = em.createNamedQuery("HrEvaluationResults.findByName");
        query.setParameter("firstName", empName.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEvaluationResults> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvaluationResults> findByTwo(HrEmployees empId, HrEmployees empName) {
        Query query = em.createNamedQuery("HrEvaluationResults.findByTwo");
        query.setParameter("empId", empId.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", empName.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEvaluationResults> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEvaluationResults getSelectedResult(int result) {
        Query query = em.createNamedQuery("HrEvaluationResults.findById");
        query.setParameter("id", result);
        System.err.println("===" + query.getResultList().size());
        try {
            HrEvaluationResults selectresult = (HrEvaluationResults) query.getResultList().get(0);
            return selectresult;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEvaluationResults> findEmpEvalution(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEvaluationResults.findByEmployeeId", HrEvaluationResults.class);
            query.setParameter("empId", hrEmployees);
            return (List<HrEvaluationResults>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
