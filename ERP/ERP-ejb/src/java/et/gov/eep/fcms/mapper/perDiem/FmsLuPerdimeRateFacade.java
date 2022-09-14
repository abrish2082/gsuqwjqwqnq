package et.gov.eep.fcms.mapper.perDiem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;

/**
 *
 * @author muller
 */
@Stateless
public class FmsLuPerdimeRateFacade extends AbstractFacade<FmsLuPerdimeRate> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuPerdimeRateFacade() {
        super(FmsLuPerdimeRate.class);
    }

    /*named query to select gradelist info from HrSalaryScaleRanges table by ID
     returen grade list info*/
    public HrSalaryScaleRanges getGrade(HrSalaryScaleRanges hrSalaryScaleRanges) {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findGrades");
        query.setParameter("id", hrSalaryScaleRanges.getGradeId());
        try {
            HrSalaryScaleRanges gradeList = (HrSalaryScaleRanges) query.getResultList().get(0);
            return gradeList;

        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select grade list info from FmsLuPerdimeRate table by jobGradeID
     returen grade list info*/
    public FmsLuPerdimeRate search(FmsLuPerdimeRate fmsLuPerdimeRate) {
        Query query = em.createNamedQuery("FmsLuPerdimeRate.findByJobGradeId");
        query.setParameter("jobGradeId", fmsLuPerdimeRate.getJobGradeId());
        try {
            FmsLuPerdimeRate gradeList = (FmsLuPerdimeRate) query.getResultList().get(0);
            return gradeList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select level list info from FmsLuAdditionalAmount table by LevelID
     returen level list info*/
    public FmsLuAdditionalAmount search1(FmsLuAdditionalAmount additionalAmount) {
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findByLevelId");
        query.setParameter("levelId", additionalAmount.getLevelId());
        try {
            FmsLuAdditionalAmount levelList = (FmsLuAdditionalAmount) query.getResultList().get(0);
            return levelList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select perdiem rate level info from FmsLuPerdimeRate table by ID
     returen perdiem rate level info*/
    public FmsLuPerdimeRate getData(FmsLuPerdimeRate fmsLuPerdimeRate) {
        Query q = em.createNamedQuery("FmsLuPerdimeRate.findById");
        q.setParameter("id", fmsLuPerdimeRate.getId());
        try {
            FmsLuPerdimeRate perdimeRateLevel = (FmsLuPerdimeRate) q.getResultList().get(0);
            return perdimeRateLevel;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select all list from FmsLuAdditionalAmount table 
     returen all list*/
    public List<FmsLuAdditionalAmount> listOfAdd(FmsLuAdditionalAmount additionalAmount) {
        List<FmsLuAdditionalAmount> levelLis;
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findAll");
        try {
            levelLis = (List<FmsLuAdditionalAmount>) query.getResultList();
            return levelLis;

        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select perdiem rate list from FmsLuPerdimeRate table by JobLevelID
     returen perdiem rate list*/
    public List<FmsLuPerdimeRate> searchbyGrade(FmsLuPerdimeRate fmsLuPerdimeRate) {
        Query q = em.createNamedQuery("FmsLuPerdimeRate.findByLevel");
        q.setParameter("jobLevelId", fmsLuPerdimeRate.getJobLevelId());
        try {
            List<FmsLuPerdimeRate> perdimeRatesList = (List<FmsLuPerdimeRate>) q.getResultList();
            return perdimeRatesList;
        } catch (Exception ex) {
            return null;
        }

    }

    /*named query to select all list from HrSalaryScaleRanges table
     returen all list*/
    public List<HrSalaryScaleRanges> findAll(HrSalaryScaleRanges hrSalaryScaleRanges) {
        try {
            Query query = em.createNamedQuery("HrSalaryScaleRanges.findByLevel");
            ArrayList<HrSalaryScaleRanges> request = new ArrayList<>(query.getResultList());

            return request;
        } catch (Exception e) {
            return null;
        }
    }

    /*named query to select perdiem rate list from FmsLuPerdimeRate table by JobLevelID
     returen perdiem rate list*/
    public List<FmsLuPerdimeRate> findPRByLevel(BigDecimal levelId) {
        Query q = em.createNamedQuery("FmsLuPerdimeRate.findByLevel");
        q.setParameter("jobLevelId", levelId);
        try {
            List<FmsLuPerdimeRate> perdimeRatesList = (List<FmsLuPerdimeRate>) q.getResultList();
            return perdimeRatesList;
        } catch (Exception ex) {
            return null;
        }

    }

    /*named query to select all list from HrLuJobLevels table
     returen all level list*/
    public List<HrLuJobLevels> getlevel(HrLuJobLevels hrLuJobLevels) {
        Query query = em.createNamedQuery("HrLuJobLevels.findAll");
        try {
            ArrayList<HrLuJobLevels> levelList = new ArrayList<>(query.getResultList());
            return levelList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select level list from HrSalaryScaleRanges table by levelID
     returen levle list*/
    public List<HrSalaryScaleRanges> getlistGrade(HrSalaryScaleRanges hrSalaryScaleRanges) {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findByLevel");
        query.setParameter("levelId", hrSalaryScaleRanges.getLevelId().getId());
        try {
            ArrayList<HrSalaryScaleRanges> levelList = new ArrayList<>(query.getResultList());
            return levelList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select level list from HrSalaryScaleRanges table by levelID
     returen levle list*/
    public List<HrSalaryScaleRanges> findByJobLevelId(HrLuJobLevels hrLuJobLevels) {
        Query query = em.createNamedQuery("HrSalaryScaleRanges.findByJobLevelId");
        query.setParameter("levelId", hrLuJobLevels);
        try {
            ArrayList<HrSalaryScaleRanges> levelList = new ArrayList<>(query.getResultList());
            return levelList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*native query to select GRADE,JOB_LEVEL,ID list
     from HR_LU_GRADES,HR_JOB_TYPES, HR_LU_JOB_LEVELS,HR_LU_GRADES table 
     using level ID
     returen selected list value*/
    public List<HrLuGrades> findByJobLevelId1(HrLuJobLevels hrLuJobLevels) {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT(hrJgr.GRADE),hrJl.JOB_LEVEL,hrJgr.ID FROM  HR_LU_GRADES hrJgr\n"
                    + "INNER JOIN HR_JOB_TYPES h \n"
                    + "ON h.JOB_GRADE_ID=hrJgr.ID\n"
                    + "INNER JOIN HR_LU_JOB_LEVELS hrJl\n"
                    + "ON h.JOB_LEVEL_ID= hrJl.ID\n"
                    + "WHERE h.JOB_LEVEL_ID= '" + hrLuJobLevels.getId() + "'", HrLuGrades.class);
            return (List<HrLuGrades>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

       /*native query to select all list
     from HR_SALARY_SCALE_RANGES,FMS_LU_PERDIME_RATE, table 
     using jobgrade ID
     returen selected list value*/
    public List<HrSalaryScaleRanges> findByJobLevelIdJoined(HrLuJobLevels hrLuJobLevels) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_SALARY_SCALE_RANGES h "
                    + "INNER JOIN FMS_LU_PERDIME_RATE f "
                    + "ON h.ID=f.JOB_GRADE_ID "
                    + "WHERE f.JOB_LEVEL_ID = '" + hrLuJobLevels.getId() + "'", HrSalaryScaleRanges.class);
            return (List<HrSalaryScaleRanges>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

       /*native query to select all list
     from HR_JOB_TYPES,FMS_LU_PERDIME_RATE, table 
     using joblevel ID
     returen selected list value*/
    public List<HrJobTypes> findByJobLevelIdJoined1(HrLuJobLevels hrLuJobLevels) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_JOB_TYPES h "
                    + "INNER JOIN FMS_LU_PERDIME_RATE f "
                    + "ON h.ID=f.JOB_GRADE_ID "
                    + "WHERE f.JOB_LEVEL_ID = '" + hrLuJobLevels.getId() + "'", HrJobTypes.class);
            return (List<HrJobTypes>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

      /*native query to select all list
     from FMS_LU_PERDIME_RATE,HR_SALARY_SCALE_RANGES, table 
     using joblevel ID and jobGrade id
     returen selected list value*/
    public List<FmsLuPerdimeRate> findPRByLevelAndGrade(BigDecimal levelId, HrLuGrades hrLuGrades) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM FMS_LU_PERDIME_RATE f "
                    + "INNER JOIN HR_SALARY_SCALE_RANGES h "
                    + "ON h.ID=f.JOB_GRADE_ID "
                    + "WHERE f.JOB_LEVEL_ID = '" + levelId + "'"
                    + "AND f.JOB_GRADE_ID = '" + hrLuGrades + "'", FmsLuPerdimeRate.class);
            return (List<FmsLuPerdimeRate>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
