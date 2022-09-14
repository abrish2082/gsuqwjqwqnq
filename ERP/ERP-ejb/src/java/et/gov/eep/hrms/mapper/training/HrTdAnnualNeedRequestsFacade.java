/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
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
public class HrTdAnnualNeedRequestsFacade extends AbstractFacade<HrTdAnnualNeedRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdAnnualNeedRequestsFacade() {
        super(HrTdAnnualNeedRequests.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrTdTrainerProfiles> hrTdTrainerProfilesList() {
        List<HrTdTrainerProfiles> trainerProfilesList = null;
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findAll");
        try {
            trainerProfilesList = (List<HrTdTrainerProfiles>) query.getResultList();
            return trainerProfilesList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrTdAnnualNeedRequests> searchByBugdetYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        Query queryReq = em.createNamedQuery("HrTdAnnualNeedRequests.findByBudgetYear");
        try {
            queryReq.setParameter("budgetYear", hrTdAnnualNeedRequests.getBudgetYear());
            ArrayList<HrTdAnnualNeedRequests> trainList = new ArrayList(queryReq.getResultList());
            return trainList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isRequstExist(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        boolean isExist;
        Query query = em.createNamedQuery("HrTdAnnualNeedRequests.checkDuplicate", HrTdAnnualNeedRequests.class);
        query.setParameter("depName", hrTdAnnualNeedRequests.getDeptId().getDepName());
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

    public List<HrTdAnnualNeedRequests> findRequestForApproval() {
        Query query = em.createNamedQuery("HrTdAnnualNeedRequests.findByStatus");
        query.setParameter("status", "1");
        try {
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualNeedRequests> findRequestForChecker() {
        Query query = em.createNamedQuery("HrTdAnnualNeedRequests.findByStatus");
        query.setParameter("status", "0");
        try {
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrTdAnnualNeedRequests> searchAnnualNeed(int status, int budgetYear) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            statusQuery = " WHERE (STATUS='0' OR STATUS='1') ";
        }
        if (budgetYear > 0) {
            statusQuery += " AND CRITERIA.CATEGORY_ID='" + budgetYear + "' ";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_TD_ANNUAL_NEED_REQUESTS "
                + statusQuery
                + "ORDER BY PREPARED_ON DESC", HrTdAnnualNeedRequests.class);
        try {
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualNeedRequests> loadTrainingRequestList(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_TD_ANNUAL_NEED_REQUESTS tr\n"
                    + " WHERE tr.STATUS='" + status.getStatus1() + "'", HrTdAnnualNeedRequests.class);
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualNeedRequests> loadTrainingRequestListForTwo(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_TD_ANNUAL_NEED_REQUESTS tr\n"
                    + " WHERE (tr.status='" + status.getStatus1() + "' OR\n"
                    + " tr.status='" + status.getStatus2() + "')", HrTdAnnualNeedRequests.class);
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdAnnualNeedRequests> loadTrainingRequestForChecker(int statusChecker) {
        String queryStatus = " WHERE STATUS='" + statusChecker + "' ";
        if (statusChecker == 0) {
            queryStatus = " WHERE(STATUS='0')";
        } else if (statusChecker == 1) {
            queryStatus = " WHERE(STATUS='1' )";
        } else if (statusChecker == 2) {
            queryStatus = " WHERE(STATUS='2')";
        } else if (statusChecker == 3) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='1' OR  STATUS='2')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_TD_ANNUAL_NEED_REQUESTS "
                + queryStatus
                + "ORDER BY PREPARED_ON DESC", HrTdAnnualNeedRequests.class);
        try {
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<HrTdAnnualNeedRequests> loadTrainingRequest(int status1) {
        String queryStatus = " WHERE STATUS='" + status1 + "' ";
        if (status1 == 0) {
            queryStatus = " WHERE(STATUS='1')";
        } else if (status1 == 1) {
            queryStatus = " WHERE(STATUS='3')";
        } else if (status1 == 2) {
            queryStatus = " WHERE(STATUS='4')";
        } else if (status1 == 3) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3' OR STATUS='4')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_TD_ANNUAL_NEED_REQUESTS "
                + queryStatus
                + "ORDER BY PREPARED_ON DESC", HrTdAnnualNeedRequests.class);
        try {
            return (List<HrTdAnnualNeedRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
