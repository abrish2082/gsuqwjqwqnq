/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvalResultProbations;
import et.gov.eep.hrms.entity.organization.HrDepartments;
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
public class HrEvalResultProbationsFacade extends AbstractFacade<HrEvalResultProbations> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEvalResultProbationsFacade() {
        super(HrEvalResultProbations.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrEvalResultProbations> findByName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByName");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEvalResultProbations> probation = new ArrayList<>(query.getResultList());
            return probation;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEvalResultProbations> findAllResult() {
        Query query = em.createNamedQuery("HrEvalResultProbations.findAll");
        try {
            ArrayList<HrEvalResultProbations> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HrEvalResultProbations getSelectedResult(int result) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findById");
        query.setParameter("id", result);
        try {
            HrEvalResultProbations selectresult = (HrEvalResultProbations) query.getResultList().get(0);
            return selectresult;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvalResultProbations> findAllRecommendation() {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByRecommendation");
        try {
            ArrayList<HrEvalResultProbations> category = new ArrayList(query.getResultList());
            return category;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEvalResultProbations> findAllEvaluationDecision(HrEvalResultProbations hrEvalResultProbations) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByRecommendation");
        query.setParameter("recommendation", hrEvalResultProbations.getRecommendation());
        try {
            ArrayList<HrEvalResultProbations> category = new ArrayList(query.getResultList());
            return category;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<HrEvalResultProbations> findAll() {
        Query query = em.createNamedQuery("HrEvalResultProbations.findAll");
        try {
            ArrayList<HrEvalResultProbations> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HrDepartments findByDepartmentId(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", hrDepartments.getDepId());
        try {
            HrDepartments getDepartment = (HrDepartments) query.getResultList().get(0);
            return getDepartment;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrEmployees> findEmployee(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findEmployee", HrEmployees.class);
        query.setParameter("deptId", hrEmployees.getDeptId().getDepId());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvalResultProbations> findByDepName(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByDepName");
        query.setParameter("depName", hrDepartments.getDepName());
        try {
            ArrayList<HrEvalResultProbations> bsc = new ArrayList<>(query.getResultList());
            return bsc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrDepartments> findAllDepartment() {
        Query query = em.createNamedQuery("HrDepartments.findAll");
        try {
            ArrayList<HrDepartments> department = new ArrayList(query.getResultList());
            return department;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEmployees> searchEmpName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findEmpName");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvalResultProbations> findByEmpId(HrEmployees empId) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByEmpId");
        query.setParameter("empId", empId.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrEvalResultProbations> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvalResultProbations> findByFName(HrEmployees empName) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByFName");
        query.setParameter("firstName", empName.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEvalResultProbations> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEvalResultProbations> findByEmpIdAndName(HrEmployees empId, HrEmployees empName) {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByEmpIdAndName");
        query.setParameter("empId", empId.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", empName.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEvalResultProbations> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEvalResultProbations> findPreparedList() {
        Query query = em.createNamedQuery("HrEvalResultProbations.findByPrepared");
        try {
            List<HrEvalResultProbations> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
