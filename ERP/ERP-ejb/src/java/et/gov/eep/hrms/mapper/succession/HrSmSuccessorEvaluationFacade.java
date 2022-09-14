/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrSmSuccessorEvaluationFacade extends AbstractFacade<HrSmSuccessorEvaluation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmSuccessorEvaluationFacade() {
        super(HrSmSuccessorEvaluation.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrSmSuccessorEvaluation readkmpDetail(Integer id) {

        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findById");
        query.setParameter("id", id);
        HrSmSuccessorEvaluation selectedSession = (HrSmSuccessorEvaluation) query.getResultList().get(0);
        return selectedSession;

    }

    public List<HrSmSuccessorEvaluation> findJobTitle(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findBykmpId", HrSmSuccessorEvaluation.class);
        query.setParameter("jobTitle", hrJobTypes.getJobTitle().toUpperCase() + '%');
        return (List<HrSmSuccessorEvaluation>) query.getResultList();

    }

    public List<HrSmSuccessorEvaluation> findEmployeename(HrEmployees hrEmployees) {

        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findByname", HrSmSuccessorEvaluation.class);
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        return (List<HrSmSuccessorEvaluation>) query.getResultList();

    }

    public List<HrSmSuccessorEvaluation> findbyposition(HrSmKmp hrSmKmp) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findSuccesorBykmpId", HrSmSuccessorEvaluation.class);
        query.setParameter("kmpId", hrSmKmp.getId());
        return (List<HrSmSuccessorEvaluation>) query.getResultList();
    }

    public List<HrSmSuccessorEvaluation> findallEvaluatedsuccessors(HrSmSuccessorEvaluation HrSmSuccessorEvaluation) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findAllEvaluated", HrSmSuccessorEvaluation.class);
        return (List<HrSmSuccessorEvaluation>) query.getResultList();
    }

    public List<HrSmSuccessorEvaluation> findJobTitleforApproval(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findBykmpIdForApproval", HrSmSuccessorEvaluation.class);
        query.setParameter("jobTitleforAproval", hrJobTypes.getJobTitle().toUpperCase() + '%');
        return (List<HrSmSuccessorEvaluation>) query.getResultList();

    }

    public List<HrSmSuccessorEvaluation> findEmployeenameforApproval(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findBynameForApproval", HrSmSuccessorEvaluation.class);
        query.setParameter("firstNameForapproval", hrEmployees.getFirstName().toUpperCase() + '%');
        return (List<HrSmSuccessorEvaluation>) query.getResultList();

    }

    public List<HrSmSuccessorEvaluation> findallToBeEvaluated(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findAllToBeEvaluated", HrSmSuccessorEvaluation.class);
        return (List<HrSmSuccessorEvaluation>) query.getResultList();
    }

    public List<HrSmSuccessorEvaluation> findbypositionToEvaluate(HrSmKmp hrSmKmp) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findSuccesorBykmpIdToAprove", HrSmSuccessorEvaluation.class);
        query.setParameter("kmpId", hrSmKmp.getId());
        return (List<HrSmSuccessorEvaluation>) query.getResultList();

    }

    public List<HrSmSuccessorEvaluation> loadEvaluationList(int status) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findByStatus");
        query.setParameter("status", status);
        try {
            return (List<HrSmSuccessorEvaluation>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
