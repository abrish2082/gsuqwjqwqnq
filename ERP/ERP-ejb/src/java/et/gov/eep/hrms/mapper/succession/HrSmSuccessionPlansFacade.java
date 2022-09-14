/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrSmSuccessionPlansFacade extends AbstractFacade<HrSmSuccessionPlans> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmSuccessionPlansFacade() {
        super(HrSmSuccessionPlans.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrSmSuccessorEvaluation getSelectedRequest(Integer request) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findById");
        query.setParameter("id", request);
        try {
            HrSmSuccessorEvaluation selectrequest = (HrSmSuccessorEvaluation) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrSmSuccessionPlans getSelectedPlanRequest(Integer request) {
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findById");
        query.setParameter("id", request);
        try {
            HrSmSuccessionPlans selectrequest = (HrSmSuccessionPlans) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<HrTdCourses> findAllCourseName() {
        Query query = em.createNamedQuery("HrTdCourses.findAll");
        try {
            ArrayList<HrTdCourses> courseName = new ArrayList(query.getResultList());
            return courseName;
        } catch (Exception ex) {
        }
        return null;
    }

    public ArrayList<HrLuTrainingCategory> findAllCoursecatagory() {
        Query query = em.createNamedQuery("HrLuTrainingCategory.findAll");
        try {
            ArrayList<HrLuTrainingCategory> Coursecatagorys = new ArrayList(query.getResultList());
            return Coursecatagorys;
        } catch (Exception ex) {
        }
        return null;
    }

    public ArrayList<HrTdCourses> getTrainingCategoryInfo(HrLuTrainingCategory hrLuTrainingCategory) {
        Query query = em.createNamedQuery("HrTdCourses.findByCatagoyId", HrLuTrainingCategory.class);
        query.setParameter("categoryId", hrLuTrainingCategory.getId());
        try {
            ArrayList<HrTdCourses> categoryInfo = new ArrayList(query.getResultList());
            return categoryInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrLuTrainingCategory findCategoryName(HrLuTrainingCategory hrLuTrainingCategory) {
        Query query = em.createNamedQuery("HrLuTrainingCategory.findByCategoryName");
        query.setParameter("categoryName", hrLuTrainingCategory.getCategoryName());
        try {
            HrLuTrainingCategory catName = (HrLuTrainingCategory) query.getResultList().get(0);
            return catName;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdCourses> findCourseName(HrTdCourses hrTdCourses) {
        try {
            Query query = em.createNamedQuery("HrTdCourses.findByCatagoyId", HrTdCourses.class);
            query.setParameter("categoryId", hrTdCourses.getCategoryId());
            return (List<HrTdCourses>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<HrSmSuccessorEvaluation> findAprovedSuccessor() {
        try {
            Query query = em.createNamedQuery("HrSmSuccessionPlans.findAllAproved");
            return (List<HrSmSuccessorEvaluation>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<HrTdCourses> findByCourseName(HrTdCourses hrTdCourses) {
        try {
            Query query = em.createNamedQuery("HrSmSuccessionPlans.findByCourseName");
            query.setParameter("courseName", hrTdCourses.getCourseName());
            return (List<HrTdCourses>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<HrTdCourses> findByCatagory(HrTdCourses hrTrainingCourses) {
        List<HrTdCourses> luTrainingCategory = null;
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findByCatagoyId");
        query.setParameter("categoryId", hrTrainingCourses.getCategoryId().getId());
        try {
            luTrainingCategory = (List<HrTdCourses>) query.getResultList();
            return luTrainingCategory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrSmSuccessionPlans readkmpDetail(Integer id) {

        Query query = em.createNamedQuery("HrSmSuccessionPlans.findById");
        query.setParameter("id", id);
        try {
            HrSmSuccessionPlans selectedSession = (HrSmSuccessionPlans) query.getResultList().get(0);
            return selectedSession;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public List<HrSmSuccessionPlans> findPlanApproved() {
        try {
            Query query = em.createNamedQuery("HrSmSuccessionPlans.findAllPlannAproved", HrSmSuccessionPlans.class);
            return (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<HrSmSuccessionPlans> findstatusactive() {
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findBystates", HrSmSuccessionPlans.class);
        query.setParameter("sid", 1);
        try {
            List<HrSmSuccessionPlans> getAllKMP = (List<HrSmSuccessionPlans>) query.getResultList();
            return getAllKMP;
        } catch (Exception ex) {
            return null;
        }
    }

    public void findaall() {
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findBystates", HrSmSuccessionPlans.class);
        query.setParameter("sid", 1);
        try {
            List<HrSmSuccessionPlans> getAllKMP = (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HrSmSuccessionPlans findByPlanId(int planId) {
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findByPlanId", HrSmSuccessionPlans.class);
        query.setParameter("PlanId", planId);
        try {
            List<HrSmSuccessionPlans> getAllKMP = (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrSmSuccessionPlans findAll(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        Query query = em.createNamedQuery("HrSmSuccessionPlanDetails.findByPlanId", HrSmSuccessionPlanDetails.class);
        query.setParameter("planid", hrSmSuccessionPlanDetails.getSuccessionPlanId());
        try {
            List<HrSmSuccessionPlans> getAllKMP = (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrSmSuccessionPlans> findFistName(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findByFistName");
        query.setParameter("firstName", hrSmSuccessorEvaluation.getEmpId().getFirstName());
        try {
            ArrayList<HrSmSuccessionPlans> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmSuccessionPlanDetails> findemp(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrSmSuccessionPlanDetails.findbyEmpId");
        query.setParameter("empid", hrEmployees.getId());
        try {
            HrSmSuccessionPlanDetails catName = (HrSmSuccessionPlanDetails) query.getResultList().get(0);
            return (List<HrSmSuccessionPlanDetails>) catName;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrSmSuccessionPlans> findAllRequests() {
        Query query = em.createNamedQuery("HrSmSuccessionPlans.findByStatus");
        query.setParameter("status", "0");
        try {
            return (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrSmSuccessionPlans> loadSuccessorLists(int status) {
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
        Query query = em.createNativeQuery("SELECT * FROM HR_SM_SUCCESSION_PLANS "
                + queryStatus
                + "ORDER BY PREPARED_ON DESC", HrSmSuccessionPlans.class);
        try {
            return (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrSmSuccessionPlans> loadSuccessorList(int status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + "FROM HR_SM_SUCCESSION_PLANS sp\n"
                    + "INNER JOIN WF_HR_PROCESSED wf\n"
                    + "ON sp.id = wf.succession_planning_id WHERE sp.status='" + status + "' "
                    + "and wf.processed_by  ='" + userId + "' ", HrSmSuccessionPlans.class);
            return (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrSmSuccessionPlans> loadSuccessionRequestList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_SM_SUCCESSION_PLANS rq\n"
                    + " WHERE rq.status='" + status.getStatus1() + "' AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrSmSuccessionPlans.class);
            return (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrSmSuccessionPlans> loadSuccessionAprovedList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_SM_SUCCESSION_PLANS rr\n"
                    + " WHERE (rr.status='" + status.getStatus1() + "' OR"
                    + " rr.status='" + status.getStatus2() + "') AND\n"
                    + "rr.PREPARED_BY  ='" + userId + "'", HrSmSuccessionPlans.class);
            return (List<HrSmSuccessionPlans>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
