/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.complaintHandling;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrAppealsFacade extends AbstractFacade<HrAppeals> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrAppealsFacade() {
        super(HrAppeals.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">

    public HrDisciplineRequests findByDisciplineId(int disciplineId) {
        Query query = em.createNamedQuery("HrDisciplineRequests.findById");
        query.setParameter("id", disciplineId);
        try {
            HrDisciplineRequests getDisciplineRequests = (HrDisciplineRequests) query.getResultList().get(0);
            return getDisciplineRequests;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrPromotionRequests findByPromotionId(int promotionId) {
        Query query = em.createNamedQuery("HrPromotionRequests.findById");
        query.setParameter("id", promotionId);
        try {
            HrPromotionRequests getPromotionRequests = (HrPromotionRequests) query.getResultList().get(0);
            return getPromotionRequests;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAppeals> loadAppealList(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            queryStatus = " WHERE(STATUS='0')";
        } else if (status == 1) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
        } else if (status == 3) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='1' OR STATUS='2' OR STATUS='3' OR STATUS='4' )";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_APPEALS "
                + queryStatus
                + "ORDER BY PREPARED_ON DESC", HrAppeals.class);
        try {
            return (List<HrAppeals>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAppeals> loadPenalityRequestList(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_APPEALS rq\n"
                    + " WHERE rq.status='" + status.getStatus1() + "' ", HrAppeals.class);
            return (List<HrAppeals>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAppeals> loadPenalityRequestListForTwo(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_APPEALS rq\n"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR\n"
                    + " rq.status='" + status.getStatus2() + "') ", HrAppeals.class);
            return (List<HrAppeals>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAppeals> findRequestForApproval() {
        Query query = em.createNamedQuery("HrAppeals.findByStatus");
        query.setParameter("statusRequest", 0);
        try {
            return (List<HrAppeals>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> findAllApprovedList(int applicantId) {
        Query query = em.createNativeQuery("SELECT rq.*"
                + " FROM HR_DISCIPLINE_REQUESTS rq where rq.STATUS = 3 AND rq.OFFENDER_ID='" + applicantId + "' ", HrDisciplineRequests.class);
        try {
            return (List<HrDisciplineRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrPromotionRequests> readPromotionCases(int applicantId) {
        Query query = em.createNativeQuery("SELECT PROMOTION.* "
                + " FROM HR_PROMOTION_REQUESTS PROMOTION, "
                + " HR_ADVERTISED_JOBS ADV_JOB, "
                + " HR_JOB_TYPES JOB "
                + " WHERE PROMOTION.ADVERT_JOB_ID = ADV_JOB.ID "
                + " AND ADV_JOB.JOB_ID=JOB.ID "
                + " AND PROMOTION.REQUESTER_ID =" + applicantId, HrPromotionRequests.class);

        List<HrPromotionRequests> promotionCases = (List<HrPromotionRequests>) query.getResultList();
        return promotionCases;
    }

    public List<HrDisciplineRequests> readApprovedDisciplineCases(int applicantId) {
        Query query = em.createNativeQuery("SELECT request.*, "
                + " offence.OFFENCE_NAME "
                + " FROM HR_DISCIPLINE_REQUESTS request "
                + " LEFT JOIN HR_DISCIPLINE_OFFENCE_TYPES offence "
                + " ON request.OFFENCE_TYPE_ID = offence.ID "
                + " LEFT JOIN HR_DISCIPLINE_OFFENCE_PENALITY decider "
                + " ON request.REPITITION_OF_OFFENCE=decider.REPETITION "
                + " AND request.OFFENCE_TYPE_ID =decider.OFFENCE_TYPE_ID "
                + " WHERE request.OFFENDER_ID =  " + applicantId
                + " AND request.STATUS = 3", HrDisciplineRequests.class);

        List<HrDisciplineRequests> disciplineCases = (List<HrDisciplineRequests>) query.getResultList();
        return disciplineCases;
    }
    //</editor-fold>
}
