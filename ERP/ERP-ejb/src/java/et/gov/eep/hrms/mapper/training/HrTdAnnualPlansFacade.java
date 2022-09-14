/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualPlans;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualPlansFacade extends AbstractFacade<HrTdAnnualPlans> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdAnnualPlansFacade() {
        super(HrTdAnnualPlans.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public ArrayList<HrTdAnnualNeedRequests> searchApprovedByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        Query queryReq = em.createNamedQuery("HrTdAnnualNeedRequests.findApprovedByYear");
        try {
            queryReq.setParameter("year", hrTdAnnualNeedRequests.getBudgetYear());
            ArrayList<HrTdAnnualNeedRequests> trainList = new ArrayList(queryReq.getResultList());
            return trainList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdAnnualTrainingNeeds> findByCourse(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        try {
            Query query = em.createNamedQuery("HrTdAnnualTrainingNeeds.findByCourse");
            query.setParameter("trainingId", hrTdAnnualTrainingNeeds);
            System.out.println("course = = =" + (List<HrTdAnnualTrainingNeeds>) query.getResultList());
            return (List<HrTdAnnualTrainingNeeds>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdAnnualNeedRequests> getByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        Query queryReq = em.createNamedQuery("HrTdAnnualNeedRequests.findApprovedYear");
        try {
            queryReq.setParameter("budgetYear", hrTdAnnualNeedRequests.getBudgetYear());
            ArrayList<HrTdAnnualNeedRequests> trainList = new ArrayList(queryReq.getResultList());
            return trainList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrTdAnnualNeedRequests searchById(int request) {
        Query query = em.createNamedQuery("HrTdAnnualNeedRequests.findById");
        query.setParameter("id", request);
        try {
            HrTdAnnualNeedRequests selectedRequest = (HrTdAnnualNeedRequests) query.getResultList().get(0);
            return selectedRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String dateInDB;
        if (date != null && date.contains("-")) {
            dateFromUI = date.split("-");
            dateInDB = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
            return dateInDB;
        }
        return null;
    }

    public HrTdAnnualPlans getMaximumId() {
        Query query = em.createNamedQuery("HrTdAnnualPlans.findByMaximumId");
        HrTdAnnualPlans result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (HrTdAnnualPlans) query.getResultList().get(0);
            } else {
                return result;
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdAnnualPlans> findByYear(int budgetYear) {
        Query query = em.createNamedQuery("HrTdAnnualPlans.findByYear", HrTdAnnualPlans.class);
        query.setParameter("year", budgetYear);
        try {
            return (List<HrTdAnnualPlans>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualPlans> findPlanByYear(HrTdAnnualPlans budgetYear) {
        Query query = em.createNamedQuery("HrTdAnnualPlans.findByYear");
        query.setParameter("year", budgetYear.getYear());
        try {
            return (List<HrTdAnnualPlans>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrTdAnnualNeedRequests> findBudgetYear() {
        String budgetYear[] = dateFormat(StringDateManipulation.toDayInEc()).split("/");
        int year = Integer.parseInt(budgetYear[2]);
        int temp = year - 1;
        Query query = em.createNativeQuery("SELECT DISTINCT BUDGET_YEAR FROM HR_TD_ANNUAL_NEED_REQUESTS WHERE STATUS='3' AND BUDGET_YEAR >= '" + temp + "' ");
        try {
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualTrainingNeeds> searchTraningCourseByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        try {
            Query query = em.createNativeQuery("select * \n"
                    + "from HR_TD_ANNUAL_TRAINING_NEEDS tn Inner join HR_TD_ANNUAL_NEED_REQUESTS nr \n"
                    + "on nr.ID=tn.ANNUAL_NEED_REQUEST_ID\n"
                    + "where nr.STATUS='3' AND nr.BUDGET_YEAR='" + hrTdAnnualNeedRequests.getBudgetYear() + "' ", HrTdAnnualTrainingNeeds.class);
            return (List<HrTdAnnualTrainingNeeds>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualTraParticipants> viewParticipantDetail() {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + "FROM HR_TD_ANNUAL_TRA_PARTICIPANTS tp inner join HR_EMPLOYEES em\n"
                    + "on tp.EMP_ID = em.ID\n"
                    + "inner join  HR_TD_ANNUAL_TRAINING_NEEDS tn\n"
                    + "on tp.ANN_TRA_NEED_ID = tn.ID inner join HR_TD_ANNUAL_PLANS ap \n"
                    + "on ap.ANN_TRA_NEED_ID = tn.ID inner join HR_TD_ANNUAL_PLAN_GROUPS pg \n"
                    + "on pg.ANN_PLAN_ID = ap.ID where tn.STATUS = '3'  ", HrTdAnnualTraParticipants.class);
            return (List<HrTdAnnualTraParticipants>) query.getResultList();

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Object[]> readApprovedTraNeeds(int year) {
        List<Object[]> coursesList = null;
        String query = ("SELECT DISTINCT TRC.ID, trc.course_name "
                + "  FROM hr_td_annual_need_requests REQ, "
                + "  hr_td_annual_training_needs ATN, "
                + "  HR_TD_COURSES TRC  "
                + "  WHERE REQ.ID=atn.annual_need_request_id  "
                + "  AND atn.training_id = TRC.ID  "
                + "  AND atn.status ='1' "
                + "  AND req.budget_year =?");
        Query querystr = em.createNativeQuery(query);
        querystr.setParameter(1, year);
        try {
            coursesList = new ArrayList(querystr.getResultList());
            return coursesList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Object[]> readApprovedAnnTraNeedDetail(int year, int traId) {
        List<Object[]> needList = null;
        String queryNeed = "SELECT TRC.id,"
                + "  nvl(sum(ATN.NO_OF_NOMINEE), '0') TOTAL_PARTICIPANT,"
                + "  count(ATN.ID) NUM_REQUEST,"
                + "  LISTAGG(ATN.ID,',')"
                + "  WITHIN GROUP (ORDER BY ATN.ID) AS ANN_TRA_NEED_ID,"
                + "  LISTAGG(HR_DEPARTMENTS.DEP_NAME || '('||ATN.NO_OF_NOMINEE||')' , ',')"
                + "  WITHIN GROUP (ORDER BY HR_DEPARTMENTS.DEP_NAME ) AS DIRECTORATE "
                + "  FROM HR_TD_ANNUAL_NEED_REQUESTS REQ,"
                + "  hr_td_annual_training_needs ATN,"
                + "  HR_DEPARTMENTS, "
                + "  HR_TD_COURSES TRC "
                + "  WHERE REQ.ID=ATN.ANNUAL_NEED_REQUEST_ID "
                + "  AND REQ.DEPT_ID =HR_DEPARTMENTS.DEP_ID "
                + "  AND ATN.ID=TRC.ID "
                + "  AND REQ.BUDGET_YEAR=?"
                + "  AND ATN.ID=?"
                + "  GROUP BY TRC.id ";
        Query querystr = em.createNativeQuery(queryNeed);
        querystr.setParameter(1, year);
        querystr.setParameter(2, traId);
        try {
            needList = new ArrayList(querystr.getResultList());
            return needList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    //</editor-fold>
}
