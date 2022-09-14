/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdEmpTrainings;
import et.gov.eep.hrms.entity.training.HrTdUnplanTrainingRequest;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdi_Pc
 */
@Stateless
public class HrTdEmpTrainingsFacade extends AbstractFacade<HrTdEmpTrainings> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdEmpTrainingsFacade() {
        super(HrTdEmpTrainings.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrTdAnnualTrainingNeeds> findbyID(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        try {
            Query query = em.createNamedQuery("HrTdAnnualTrainingNeeds.findByAnnualNeedRequestId", HrTdAnnualTrainingNeeds.class);
            query.setParameter("annualNeedRequestId", hrTdAnnualNeedRequests.getId());
            return (List<HrTdAnnualTrainingNeeds>) query.getResultList();
        } catch (Exception e) {

            return null;
        }
    }

    public List<HrTdUnplanTrainingRequest> findbyAll() {
        try {
            Integer status = 1;
            Query query = em.createNamedQuery("HrTdUnplanTrainingRequest.findBYActiveStatus");
            query.setParameter("status1", status);
            return (List<HrTdUnplanTrainingRequest>) query.getResultList();
        } catch (Exception e) {

            return null;
        }
    }

    public List<HrTdAnnualTrainingNeeds> findByAnnTraId(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        try {
            Query query = em.createNamedQuery("HrTdAnnualTrainingNeeds.findAll1", HrTdAnnualTrainingNeeds.class);
            query.setParameter("annualNeedRequestId", hrTdAnnualTrainingNeeds);
            return (List<HrTdAnnualTrainingNeeds>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdAnnualTrainingNeeds> findByAnnTraId() {
        Query query = em.createNamedQuery("HrTdAnnualTrainingNeeds.findAll1");
        try {
            ArrayList<HrTdAnnualTrainingNeeds> courseList = new ArrayList(query.getResultList());
            return courseList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrTdEmpTrainings> findPlannedParticipant(HrTdEmpTrainings hrTdEmpTrainings) {
        try {
            Query query = em.createNamedQuery("HrTdEmpTrainings.findByPlanned", HrTdEmpTrainings.class);
            return (List<HrTdEmpTrainings>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdEmpTrainings> findUnPlannedParticipant(HrTdEmpTrainings hrTdEmpTrainings) {
        try {

            Query query = em.createNamedQuery("HrTdEmpTrainings.findByUnplanned", HrTdEmpTrainings.class);
            return (List<HrTdEmpTrainings>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdEmpTrainings> findByEmpId(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrTdEmpTrainings.findByEmpId", HrTdEmpTrainings.class);
            query.setParameter("empId", hrEmployees);
            return (List<HrTdEmpTrainings>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdEmpTrainings> findByEmpIds(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrTdEmpTrainings.findByEmpIds", HrTdEmpTrainings.class);
            query.setParameter("empIds", hrEmployees);
            return (List<HrTdEmpTrainings>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrTdAnnualNeedRequests> findBudgetYear() {
        Query query = em.createNativeQuery("SELECT DISTINCT BUDGET_YEAR FROM HR_TD_ANNUAL_NEED_REQUESTS WHERE STATUS= '3'  ORDER BY BUDGET_YEAR DESC");
        try {
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdUnplanTrainingRequest> findUBudgetYear() {
        Query query = em.createNativeQuery("SELECT DISTINCT BUDGET_YEAR FROM HR_TD_UNPLAN_TRAINING_REQUEST WHERE STATUS = 3 ORDER BY BUDGET_YEAR DESC");
        try {
            return (List<HrTdUnplanTrainingRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
}
