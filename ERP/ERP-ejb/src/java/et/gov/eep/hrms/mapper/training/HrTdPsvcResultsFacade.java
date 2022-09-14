/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdPsvcCourses;
import et.gov.eep.hrms.entity.training.HrTdPsvcResults;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcTrainees;
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
public class HrTdPsvcResultsFacade extends AbstractFacade<HrTdPsvcResults> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdPsvcResultsFacade() {
        super(HrTdPsvcResults.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrTdPsvcCourses> findByTrainingCourse() {
        Query query = em.createNamedQuery("HrTdPsvcCourses.findAll");
        try {
            ArrayList<HrTdPsvcCourses> courseList = new ArrayList(query.getResultList());
            return courseList;
        } catch (Exception ex) {
        }
        return null;
    }

    public List<HrTdPsvcTraineeDetails> findTrainers() {
        Query query = em.createNamedQuery("HrTdPsvcTraineeDetails.findAll");
        try {
            ArrayList<HrTdPsvcTraineeDetails> courseList = new ArrayList(query.getResultList());
            return courseList;
        } catch (Exception ex) {
        }
        return null;
    }

    public List<HrTdPsvcTraineeDetails> findByTrainerName(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails) {
        Query query = em.createNamedQuery("HrTdPsvcResults.findByTraineeId", HrTdPsvcTraineeDetails.class);
        query.setParameter("traineeId", hrTdPsvcTraineeDetails.getId());
        try {
            List<HrTdPsvcTraineeDetails> uniList = new ArrayList(query.getResultList());
            return uniList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrTdPsvcResults> findBysemisiter() {
        Query query = em.createNamedQuery("HrTdPsvcResults.findAll");
        try {
            List<HrTdPsvcResults> levellist = (List<HrTdPsvcResults>) query.getResultList();
            return levellist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList findBySemister(HrTdPsvcResults hrTdPsvcResults) {
        Query queryReq = em.createNamedQuery("HrTdPsvcResults.findBySemister");
        try {
            queryReq.setParameter("semister", hrTdPsvcResults.getSemister());
            ArrayList<HrTdPsvcResults> absentList = new ArrayList(queryReq.getResultList());
            return absentList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdPsvcTrainees> findByYear(Integer years) {
        Query query = em.createNamedQuery("HrTdPsvcTrainees.findByYear", HrTdPsvcTrainees.class);
        query.setParameter("yearOfTraining", years);
        try {
            List<HrTdPsvcTrainees> Bachcodelist = (List<HrTdPsvcTrainees>) query.getResultList();
            return Bachcodelist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public HrTdPsvcTrainees findBYId(Integer id) {
        Query query = em.createNamedQuery("HrTdPsvcTrainees.findById");
        query.setParameter("id", id);
        try {
            HrTdPsvcTrainees levellist = (HrTdPsvcTrainees) query.getResultList();
            return levellist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrTdPsvcTrainees> findByAllyear() {
        Query query = em.createNativeQuery("SELECT DISTINCT YEAR_OF_TRAINING FROM HR_TD_PSVC_TRAINEES ORDER BY YEAR_OF_TRAINING DESC");
        try {
            List<HrTdPsvcTrainees> levellist = (List<HrTdPsvcTrainees>) query.getResultList();
            return levellist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrTdPsvcTraineeDetails> searchByBachCode(String code) {
        try {
            Query query = em.createNativeQuery("select * \n"
                    + "from HR_TD_PSVC_TRAINEE_DETAILS sd Inner join HR_TD_PSVC_TRAINEES hs \n"
                    + "on hs.ID=sd.TRAINEE_ID\n"
                    + "where hs.BATCH_CODE='" + code + "' ", HrTdPsvcTraineeDetails.class);
            return (List<HrTdPsvcTraineeDetails>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdPsvcResults> findByYearBach(String code) {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT ss.ID,ss.FROM_DATE,ss.SEMISTER,"
                    + "ss.TO_DATE,ss.TRAINEE_DETAIL_ID "
                    + "FROM HR_TD_PSVC_RESULTS ss\n"
                    + "inner join HR_TD_PSVC_RESULT_DETAILS ssd\n"
                    + "on ss.ID=ssd.TRAINEE_RESULT_ID\n"
                    + "where ss.TRAINEE_DETAIL_ID in(\n"
                    + "SELECT ptd.ID\n"
                    + "FROM HR_TD_PSVC_TRAINEES pt\n"
                    + "INNER JOIN HR_TD_PSVC_TRAINEE_DETAILS ptd\n"
                    + "ON ptd.ID=pt.ID\n"
                    + "where pt.BATCH_CODE='" + code + "' )", HrTdPsvcResults.class);
            return (List<HrTdPsvcResults>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
}
