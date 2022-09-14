/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdUnplanTraParticipant;
import et.gov.eep.hrms.entity.training.HrTdUnplanTrainingRequest;
import java.math.BigDecimal;
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
public class HrTdUnplanTrainingRequestFacade extends AbstractFacade<HrTdUnplanTrainingRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdUnplanTrainingRequestFacade() {
        super(HrTdUnplanTrainingRequest.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrDepartments> findByDeptId(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId", HrDepartments.class);
        query.setParameter("depId", hrDepartments.getDepId());
        try {
            return (List<HrDepartments>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdCourses> findById(HrLuTrainingCategory hrLuTrainingCategory) {
        try {
            Query query = em.createNamedQuery("HrLuTrainingCategory.findByIdc", HrLuTrainingCategory.class);
            query.setParameter("id", hrLuTrainingCategory.getId());
            return (List<HrTdCourses>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public HrTdUnplanTrainingRequest getSelectedRequest(Integer id) {
        Query query = em.createNamedQuery("HrTdUnplanTrainingRequest.findById");
        query.setParameter("id", id);
        try {
            HrTdUnplanTrainingRequest selectedTraining = (HrTdUnplanTrainingRequest) query.getResultList().get(0);
            return selectedTraining;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrTdUnplanTrainingRequest findbyTrainingId(HrTdCourses trainingId) {
        try {

            Query query = em.createNamedQuery("HrTdUnplanTrainingRequest.findByTrainingId", HrTdUnplanTrainingRequest.class);
            query.setParameter("trainingId", trainingId);
            HrTdUnplanTrainingRequest trainingid = (HrTdUnplanTrainingRequest) query.getResultList().get(0);
            return trainingid;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdUnplanTrainingRequest> findRequestForApproval() {
        Query query = em.createNamedQuery("HrTdUnplanTrainingRequest.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<HrTdUnplanTrainingRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrTdCourses> findByBudgetYear(int budgetYear) {
        Integer budgetYearMinusOne = budgetYear - 1;
        String newBudgetYear = String.valueOf(budgetYearMinusOne);
        String temp1 = ("01/11/" + newBudgetYear);
        String temp2 = ("30/10/" + budgetYear);
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT TR.ID AS ID,\n"
                    + "   TR.COURSE_NAME      AS COURSE_NAME,\n"
                    + "    TR.DESCRIPTION      AS DESCRIPTION,\n"
                    + "   TR.CATEGORY_ID      AS CATEGORY_ID\n"
                    + "   FROM HR_TD_UNPLAN_TRAINING_REQUEST rq\n"
                    + "     INNER JOIN HR_TD_COURSES TR\n"
                    + "     ON TR.ID          =rq.TRAINING_ID\n"
                    + "   WHERE rq.STATUS   =3\n"
                    + "    AND rq.BUDGET_YEAR = '" + budgetYear + "'\n"
                    + " AND TO_DATE(rq.START_DATE,'dd/mm/YYYY') BETWEEN  TO_DATE('" + temp1 + "','dd/mm/YYYY') AND  TO_DATE('" + temp2 + "','dd/mm/YYYY')", "traning");
            return (List<HrTdCourses>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdCourses> SearchTraningNeedByBudgetYear1(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        Integer budgetYear = Integer.valueOf(hrTdAnnualNeedRequests.getBudgetYear());
        Integer budgetYearMinusOne = budgetYear - 1;
        String newBudgetYear = String.valueOf(budgetYearMinusOne);
        String temp1 = ("01/11/" + newBudgetYear);
        String temp2 = ("30/10/" + hrTdAnnualNeedRequests.getBudgetYear());
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT TR.ID AS ID,\n"
                    + "  TR.COURSE_NAME      AS COURSE_NAME,\n"
                    + "  TR.DESCRIPTION      AS DESCRIPTION,\n"
                    + "  TR.CATEGORY_ID      AS CATEGORY_ID\n"
                    + "FROM HR_TD_ANNUAL_TRAINING_NEEDS tn\n"
                    + "INNER JOIN HR_TD_ANNUAL_NEED_REQUESTS nr\n"
                    + "ON nr.ID =tn.ANNUAL_NEED_REQUEST_ID\n"
                    + "INNER JOIN HR_TD_COURSES TR\n"
                    + "ON TR.ID          =tn.TRAINING_ID\n"
                    + "WHERE nr.STATUS   ='3'\n"
                    + "AND nr.BUDGET_YEAR='" + budgetYear + "'\n"
                    + "AND  TO_DATE(tn.TENTATIVE_START_DATE,'dd/mm/YYYY')  BETWEEN TO_DATE('" + temp1 + "','dd/mm/YYYY') AND  TO_DATE('" + temp2 + "','dd/mm/YYYY')", "traning");
            return (List<HrTdCourses>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdUnplanTraParticipant> searchTrainerByCourse(BigDecimal courseId) {
        try {
            Query query = em.createNativeQuery("SELECT * \n "
                    + "FROM HR_TD_UNPLAN_TRA_PARTICIPANT tp\n "
                    + "INNER JOIN HR_TD_UNPLAN_TRAINING_REQUEST tr\n "
                    + "ON tr.ID            =tp.UNP_TRA_REQ_ID\n "
                    + "WHERE tp.STATUS = 0 AND tr.STATUS = '3' AND tr.TRAINING_ID='" + courseId + "' ", HrTdUnplanTraParticipant.class);
            return (List<HrTdUnplanTraParticipant>) query.getResultList();

        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualTraParticipants> searchTrainerByCourseid(BigDecimal courseIds, int year) {
        try {
            Query query = em.createNativeQuery("SELECT a.*\n"
                    + "FROM HR_TD_ANNUAL_TRA_PARTICIPANTS a\n"
                    + "INNER JOIN HR_TD_ANNUAL_TRAINING_NEEDS b\n"
                    + "ON b.ID = a.ANN_TRA_NEED_ID\n"
                    + "INNER JOIN HR_TD_ANNUAL_NEED_REQUESTS c\n"
                    + "ON c.ID            = b.ANNUAL_NEED_REQUEST_ID\n"
                    + "WHERE a.STATUS = 0 AND c.BUDGET_YEAR='" + year + "'\n"
                    + "AND b.TRAINING_ID  ='" + courseIds + "' ", HrTdAnnualTraParticipants.class);
            return (List<HrTdAnnualTraParticipants>) query.getResultList();

        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdUnplanTrainingRequest> loadTrainingRequest(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            queryStatus = " WHERE(STATUS='0')";
        } else if (status == 1) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
        } else if (status == 3) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='2' OR STATUS='3' OR STATUS='4' OR STATUS='1')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_TD_UNPLAN_TRAINING_REQUEST "
                + queryStatus
                + "ORDER BY START_DATE DESC", HrTdUnplanTrainingRequest.class);
        try {
            return (List<HrTdUnplanTrainingRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdUnplanTrainingRequest> loadTrainingRequestList(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_TD_UNPLAN_TRAINING_REQUEST tr\n"
                    + " WHERE tr.STATUS='" + status.getStatus1() + "'", HrTdUnplanTrainingRequest.class);
            return (List<HrTdUnplanTrainingRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdUnplanTrainingRequest> loadTrainingRequestListForTwo(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_TD_UNPLAN_TRAINING_REQUEST tr\n"
                    + " WHERE (tr.status='" + status.getStatus1() + "' OR\n"
                    + " tr.status='" + status.getStatus2() + "')", HrTdUnplanTrainingRequest.class);
            return (List<HrTdUnplanTrainingRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
}
