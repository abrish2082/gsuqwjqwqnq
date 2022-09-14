/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrSmSuccessionPlanDetailsFacade extends AbstractFacade<HrSmSuccessionPlanDetails> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmSuccessionPlanDetailsFacade() {
        super(HrSmSuccessionPlanDetails.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrSmSuccessionPlanDetails findbyapprovedid(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        Query query = em.createNamedQuery("HrSmSuccessionPlanDetails.succession", HrSmSuccessionPlanDetails.class);
        query.setParameter("firstname", hrSmSuccessionPlanDetails.getSuccessionPlanId().getSuccessorEvaluationId().getEmpId().getFirstName());
        try {
            List<HrSmSuccessionPlanDetails> getAllKMP = (List<HrSmSuccessionPlanDetails>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrSmSuccessionPlanDetails findAll(HrSmSuccessionPlans hrSmSuccessionPlans) {
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findById");
        query.setParameter("id", hrSmSuccessionPlans.getId());
        try {
            HrSmSuccessionPlanDetails empList = (HrSmSuccessionPlanDetails) (query.getResultList().get(0));
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void findallactivesatus(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        Query query = em.createNamedQuery("HrSmSuccessionPlanDetails.findallactivesatus", HrSmSuccessionPlanDetails.class);
        query.setParameter("sid", 1);
        try {
            HrSmSuccessionPlanDetails empList = (HrSmSuccessionPlanDetails) (query.getResultList());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HrSmSuccessionPlanDetails getSelectedLevel(int level) {
        Query query = em.createNamedQuery("HrSmSuccessionPlanDetails.findById");
        query.setParameter("id", level);
        try {
            HrSmSuccessionPlanDetails selectedLevel = (HrSmSuccessionPlanDetails) query.getResultList().get(0);
            return selectedLevel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List findByStatus(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails, String status) {
        Query query = em.createNamedQuery("HrSmSuccessionPlanDetails.findByStatus");
        query.setParameter("status", status);
        try {
            List<HrSmSuccessionPlanDetails> SuccessionPlanDetailList = new ArrayList(query.getResultList());
            return SuccessionPlanDetailList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrSmSuccessionPlanDetails> filteredEvaluationLevel(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 5) {
            statusQuery = " WHERE (STATUS='0' OR STATUS='1' OR STATUS='2' OR STATUS='3' OR STATUS='4') ";
        }
        Query query = em.createNativeQuery("SELECT "
                + " HR_SM_SUCCESSION_PLAN_DETAILS.ID,"
                + " HR_SM_SUCCESSION_PLAN_DETAILS.STATUS,"
                + " HR_TD_COURSES.COURSE_NAME "
                + " FROM HR_SM_SUCCESSION_PLAN_DETAILS "
                + " INNER JOIN HR_TD_COURSES "
                + " ON HR_SM_SUCCESSION_PLAN_DETAILS.TRAINING_ID=HR_TD_COURSES.ID "
                + statusQuery
                + "ORDER BY  ID DESC", HrSmSuccessionPlanDetails.class);
        try {
            return (List<HrSmSuccessionPlanDetails>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
}
