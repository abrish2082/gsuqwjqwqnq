/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.recruitment;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrCandidateEmpHistories;
import et.gov.eep.hrms.entity.recruitment.HrCandidateSelected;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import et.gov.eep.hrms.entity.recruitment.HrExamPercentages;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author munir
 */
@Stateless
public class HrCandidateSelectedFacade extends AbstractFacade<HrCandidateSelected> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrCandidateSelectedFacade() {
        super(HrCandidateSelected.class);
    }

    public List<HrAdvertisements> bachCodes(String type) {
        Query query = em.createNativeQuery("SELECT ID, "
                + " BATCH_CODE "
                + " FROM HR_ADVERTISEMENTS "
                + " WHERE UPPER(ADVERT_TYPE) = ? "
                + " ORDER BY ID DESC", HrAdvertisements.class);
        query.setParameter(1, type.toUpperCase());
        try {
            return (List<HrAdvertisements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAdvertisedJobs> advertizedJobs(int advertId) {
        Query query = em.createNativeQuery("SELECT "
                + "  HR_ADVERTISED_JOBS.ID,"
                + "  HR_ADVERTISED_JOBS.JOB_ID,"
                + "  HR_ADVERTISED_JOBS.RECRUITMENT_TYPE,"
                + "  HR_ADVERTISED_JOBS.EMP_NEEDED,"
                + "  HR_ADVERTISED_JOBS.RECRUIT_REQUEST_ID,"
                + "  HR_JOB_TYPES.JOB_TITLE"
                + " FROM  HR_ADVERTISED_JOBS, "
                + "       HR_JOB_TYPES "
                + " WHERE HR_ADVERTISED_JOBS.JOB_ID=HR_JOB_TYPES.ID "
                + "       AND HR_ADVERTISED_JOBS.ADVERT_ID = ?", HrAdvertisedJobs.class);
        query.setParameter(1, advertId);
        try {
            return (List<HrAdvertisedJobs>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="shortlist candidate">
    public List<Object[]> readCandidiates(String advertJobId, String sql) {
        Query query = em.createNativeQuery("SELECT HR_CANDIDIATES.ID, "
                + " HR_CANDIDIATES.FIRST_NAME "
                + " ||' ' "
                + " ||HR_CANDIDIATES.MIDDLE_NAME "
                + " ||' ' "
                + " ||HR_CANDIDIATES.LAST_NAME FULL_NAME, "
                + " HR_CANDIDIATES.STATUS, "
                + " HR_CANDIDIATES.SEX,"
                + " HR_CANDIDIATES.DATE_OF_BIRTH,  "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_BY, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_ON, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_RECOMMENDATION, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_REMARK, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_APPROVED_BY, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_APPROVED_ON, "
                + "  NVL(HR_GET_CANDIDATE_EXPERIANCE(HR_CANDIDIATES.ID),0) EXPERIENCE, "
                + "  MAX(HR_LU_EDUC_LEVELS.RANK) EDUC_RANK, "
                + "  '<UL class=\\listagg\\>' "
                + "  ||LISTAGG('<LI>' "
                + "  ||HR_LU_EDUC_LEVELS.EDUC_LEVEL "
                + "  ||' in ' "
                + "  ||HR_LU_EDUC_TYPES.EDUC_TYPE "
                + "  ||'</LI>','') WITHIN GROUP(ORDER BY HR_LU_EDUC_LEVELS.RANK DESC) "
                + "  ||'</UL>' AS EDUC_QUAL, "
                + "  '<UL class=\\listagg\\>' "
                + "  ||LISTAGG('<LI>' "
                + "  ||HR_CANDIDATE_EMP_HISTORIES.INSTITUTION "
                + "  ||' as ' "
                + "  ||HR_CANDIDATE_EMP_HISTORIES.JOB_TITLE "
                + "  ||' [' "
                + "  ||HR_CANDIDATE_EMP_HISTORIES.START_DATE "
                + "  ||' to ' "
                + "  ||HR_CANDIDATE_EMP_HISTORIES.END_DATE "
                + "  ||'](' "
                + "  ||ROUND(HR_DATE_DIFFRENCE_IN_YEARS(HR_CANDIDATE_EMP_HISTORIES.START_DATE,HR_CANDIDATE_EMP_HISTORIES.END_DATE)) "
                + "  ||':' "
                + "  ||ROUND(HR_DATE_DIFFRENCE_IN_MONTHS(HR_CANDIDATE_EMP_HISTORIES.START_DATE,HR_CANDIDATE_EMP_HISTORIES.END_DATE)) "
                + "  ||')</LI>','') WITHIN GROUP(ORDER BY HR_CANDIDATE_EMP_HISTORIES.START_DATE) "
                + "  ||'</UL>' AS EXPERIENCE_DESC, "
                + "  HR_CANDIDIATE_EDUCATIONS.EDUC_RESULT, "
                + "  HR_ADVERTISEMENTS.BATCH_CODE, "
                + "  HR_JOB_TYPES.JOB_CODE "
                + " FROM HR_CANDIDIATES "
                + " LEFT JOIN HR_CANDIDATE_SELECTED "
                + " ON HR_CANDIDIATES.ID=HR_CANDIDATE_SELECTED.CANDIDATE_ID "
                + " LEFT JOIN HR_CANDIDIATE_EDUCATIONS "
                + " ON HR_CANDIDIATES.ID=HR_CANDIDIATE_EDUCATIONS.CANDIDIATE_ID "
                + " LEFT JOIN HR_CANDIDATE_EMP_HISTORIES "
                + " ON HR_CANDIDIATES.ID=HR_CANDIDATE_EMP_HISTORIES.CANDIDATE_ID "
                + " LEFT JOIN HR_LU_EDUC_LEVELS "
                + " ON HR_LU_EDUC_LEVELS.ID = HR_CANDIDIATE_EDUCATIONS.EDUC_LEVEL_ID "
                + " LEFT JOIN HR_LU_EDUC_TYPES "
                + " ON HR_LU_EDUC_TYPES.ID = HR_CANDIDIATE_EDUCATIONS.EDUC_TYPE_ID "
                + " LEFT JOIN HR_ADVERTISED_JOBS "
                + " ON HR_ADVERTISED_JOBS.ID = HR_CANDIDIATES.ADV_JOB_ID "
                + " LEFT JOIN HR_ADVERTISEMENTS "
                + " ON HR_ADVERTISEMENTS.ID = HR_ADVERTISED_JOBS.ADVERT_ID "
                + " LEFT JOIN HR_JOB_TYPES "
                + " ON HR_JOB_TYPES.ID = HR_ADVERTISED_JOBS.JOB_ID "
                + " WHERE HR_CANDIDIATES.ADV_JOB_ID = '" + advertJobId + "' " + sql
                + " GROUP BY HR_CANDIDIATES.ID, "
                + "  HR_CANDIDIATES.FIRST_NAME, "
                + "  HR_CANDIDIATES.MIDDLE_NAME, "
                + "  HR_CANDIDIATES.LAST_NAME, "
                + "  HR_CANDIDIATES.STATUS , "
                + "  HR_CANDIDIATES.SEX, "
                + "  HR_CANDIDIATES.DATE_OF_BIRTH, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_BY, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_ON, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_RECOMMENDATION, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_REMARK, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_APPROVED_BY, "
                + "  HR_CANDIDATE_SELECTED.SHORTLIST_APPROVED_ON, "
                + "  HR_CANDIDIATE_EDUCATIONS.EDUC_RESULT, "
                + "  HR_ADVERTISEMENTS.BATCH_CODE, "
                + "  HR_JOB_TYPES.JOB_CODE");
        try {
            return (List<Object[]>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    // @TransactionAttribute(REQUIRED)
    public int shortListCandidates(List<HrCandidateSelected> recommendations) {
        int checkSave = 0;
        Query updateQuery = em.createNativeQuery("UPDATE HR_CANDIDIATES SET STATUS=? "
                + " WHERE ID=?");

//        Query insertQuery = em.createNativeQuery("INSERT INTO HR_CANDIDATE_SELECTED "
//                + "(SHORTLIST_RECOMMENDATION,SHORTLIST_REMARK,STATUS, "
//                + "SHORTLIST_ON,SHORTLIST_BY,CANDIDATE_ID) "
//                + "VALUES(?,?,?,?,?,?)");
        try {
            for (HrCandidateSelected hrCandidateSelected : recommendations) {
                System.out.println("in first");
                updateQuery.setParameter(1, hrCandidateSelected.getStatus());
                updateQuery.setParameter(2, hrCandidateSelected.getCandidateId());
                if (updateQuery.executeUpdate() > 0) {
                    this.create(hrCandidateSelected);
                    checkSave = 1;
//                    if (insertQuery.executeUpdate() > 0) {
//                        checkSave = 1;
//                    }
                }
            }
            return checkSave;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="filter candidate">
    public List<HrCandidateSelected> readApprovedCandidates(String advertJobId, String sql) {
        Query query = em.createNativeQuery("SELECT rownum, "
                + " HR_CANDIDIATES.ID, "
                + " HR_CANDIDIATES.FIRST_NAME, "
                + " HR_CANDIDIATES.MIDDLE_NAME, "
                + " HR_CANDIDIATES.LAST_NAME, "
                + " HR_CANDIDIATES.STATUS, "
                + " HR_CANDIDIATES.SEX, "
                + " HR_CANDIDIATES.DATE_OF_BIRTH, "
                + " nvl(HR_CANDIDATE_SELECTED.EXAM_RESULT,'0') as EXAM_RESULT, "
                + " nvl(HR_CANDIDATE_SELECTED.INTERVIEW_RESULT,'0') as INTERVIEW_RESULT, "
                + " nvl(HR_CANDIDATE_SELECTED.PRACTICAL_RESULT,'0') as PRACTICAL_RESULT, "
                + " nvl(HR_CANDIDATE_SELECTED.OTHER_RESULT,'0') as OTHER_RESULT, "
                + " HR_CANDIDATE_SELECTED.CANDIDATE_ID, "
                + " HR_CANDIDATE_SELECTED.SHORTLIST_RECOMMENDATION, "
                + " HR_CANDIDATE_SELECTED.SHORTLIST_REMARK, "
                + " HR_CANDIDATE_SELECTED.SHORTLIST_APPROVED_BY, "
                + " HR_CANDIDATE_SELECTED.SHORTLIST_APPROVED_ON, "
                + " HR_CANDIDATE_SELECTED.FILITER_RECOMMENDATION, "
                + " HR_CANDIDATE_SELECTED.FILITER_REMARK, "
                + " HR_CANDIDATE_SELECTED.FILITER_BY, "
                + " HR_CANDIDATE_SELECTED.FILITER_ON, "
                + " HR_CANDIDATE_SELECTED.FILITER_APPROVED_BY, "
                + " HR_CANDIDATE_SELECTED.FILITER_APPROVED_ON "
                + " FROM HR_CANDIDIATES,HR_CANDIDATE_SELECTED "
                + " WHERE  HR_CANDIDIATES.ADV_JOB_ID = ? "
                + " AND HR_CANDIDIATES.STATUS !='0' AND HR_CANDIDIATES.STATUS !='1' "
                + " AND HR_CANDIDIATES.ID=HR_CANDIDATE_SELECTED.CANDIDATE_ID "
                + "  " + sql + " "
                + " ORDER BY (EXAM_RESULT+INTERVIEW_RESULT+PRACTICAL_RESULT+OTHER_RESULT) DESC", HrCandidateSelected.class);
        query.setParameter(1, advertJobId);
        try {
            List<HrCandidateSelected> approvedCandidates = (List<HrCandidateSelected>) query.getResultList();
            for (HrCandidateSelected shortlistedCandidate : approvedCandidates) {
                HrCandidiates candidate = readCandidate(shortlistedCandidate.getCandidateId());
                shortlistedCandidate.setFirstName(candidate.getFirstName());
                shortlistedCandidate.setMiddleName(candidate.getMiddleName());
                shortlistedCandidate.setLastName(candidate.getLastName());
                shortlistedCandidate.setSex(candidate.getSex());
                shortlistedCandidate.setDateOfBirth(candidate.getDateOfBirth());
                shortlistedCandidate.setExprience(readCandidateExperience(candidate.getId()));
            }
            return approvedCandidates;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrCandidiates readCandidate(BigDecimal candidateId) {
        Query query = em.createNativeQuery("SELECT HR_CANDIDIATES.* "
                + "FROM HR_CANDIDIATES "
                + "WHERE HR_CANDIDIATES.ID= '" + candidateId + "'", HrCandidiates.class);
        try {
            return (HrCandidiates) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int readCandidateExperience(BigDecimal candidateId) {
        int experience = 0;
        Query query = em.createNativeQuery("SELECT ID, START_DATE, END_DATE FROM HR_CANDIDATE_EMP_HISTORIES "
                + "WHERE HR_CANDIDATE_EMP_HISTORIES.CANDIDATE_ID= '" + candidateId + "'", HrCandidateEmpHistories.class);
        try {
            List<HrCandidateEmpHistories> candidateExperiences = query.getResultList();
            for (HrCandidateEmpHistories exp : candidateExperiences) {
//                int difference = StringDateManipulation.datesDifferenceInMonths(exp.getStartDate(), exp.getEndDate());
                String startMonth[] = exp.getStartDate().split("/");
                int isSartMonth = Integer.parseInt(startMonth[1]);
                String startYear[] = exp.getStartDate().split("/");
                int isStartYear = Integer.parseInt(startYear[2]);
                String startDate[] = exp.getStartDate().split("/");
                int isStartDate = Integer.parseInt(startDate[0]);
                String endDay[] = exp.getEndDate().split("/");
                int isEndDay = Integer.parseInt(endDay[0]);
                String endMonth[] = exp.getEndDate().split("/");
                int isEndMonth = Integer.parseInt(endMonth[1]);
                String endYear[] = exp.getEndDate().split("/");
                int isEndYear = Integer.parseInt(endYear[2]);
                int difference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
                experience += difference;
            }
            return experience;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public HrExamPercentages selectExamPercentage(int advertId) {
        Query query = em.createNativeQuery("SELECT ID,"
                + "  nvl(WRITTEN_PERCENTAGE,'0') as WRITTEN_PERCENTAGE,"
                + "  nvl(PRACTICAL_PERCENTAGE,'0') as PRACTICAL_PERCENTAGE,"
                + "  nvl(INTERVIEW_PERCENTAGE,'0') as INTERVIEW_PERCENTAGE,"
                + "  nvl(OTHER_PERCENTAGE,'0') as OTHER_PERCENTAGE,"
                + "  nvl(CGPA_PERCENTAGE,'0') as CGPA_PERCENTAGE,"
                + "  BATCH_CODE "
                + "  FROM "
                + "  HR_EXAM_PERCENTAGES "
                + "  WHERE BATCH_CODE=?", HrExamPercentages.class);
        query.setParameter(1, advertId);
        try {
            return (HrExamPercentages) (query.getResultList().get(0));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int saveCandidateAssessmentResult(HrCandidateSelected candidateAssessmentResult) {
        int checkSave = 0;
        Query updateQuery = em.createNativeQuery("UPDATE HR_CANDIDATE_SELECTED SET "
                + " EXAM_RESULT=?,"
                + " INTERVIEW_RESULT=?,"
                + " PRACTICAL_RESULT=?,"
                + " OTHER_RESULT=?,"
                + " FILITER_RECOMMENDATION=?,"
                + " FILITER_REMARK=?"
                + " WHERE CANDIDATE_ID=?");
        try {
            updateQuery.setParameter(1, candidateAssessmentResult.getExamResult());
            updateQuery.setParameter(2, candidateAssessmentResult.getInterviewResult());
            updateQuery.setParameter(3, candidateAssessmentResult.getPracticalResult());
            updateQuery.setParameter(4, candidateAssessmentResult.getOtherResult());
            updateQuery.setParameter(5, candidateAssessmentResult.getFiliterRecommendation());
            updateQuery.setParameter(6, candidateAssessmentResult.getFiliterRemark());
            updateQuery.setParameter(7, candidateAssessmentResult.getCandidateId());
            if (updateQuery.executeUpdate() > 0) {
                checkSave = 1;
            }
            return checkSave;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int saveFilteredCandidate(List<HrCandidateSelected> recommendations) {
        int checkSave = 0;
        Query updateStatus = em.createNativeQuery("UPDATE HR_CANDIDIATES "
                + " SET STATUS=? "
                + " WHERE ID=?");

        Query updateDetail = em.createNativeQuery("UPDATE HR_CANDIDATE_SELECTED "
                + " SET STATUS=?, "
                + " FILITER_BY=?, "
                + " FILITER_ON=? "
                + " WHERE CANDIDATE_ID=?");
        try {
            for (HrCandidateSelected hrCandidateSelected : recommendations) {
                updateStatus.setParameter(1, hrCandidateSelected.getStatus());
                updateStatus.setParameter(2, hrCandidateSelected.getCandidateId());
                if (updateStatus.executeUpdate() > 0) {
                    updateDetail.setParameter(1, hrCandidateSelected.getStatus());
                    updateDetail.setParameter(2, hrCandidateSelected.getFiliterBy());
                    updateDetail.setParameter(3, hrCandidateSelected.getFiliterOn());
                    updateDetail.setParameter(4, hrCandidateSelected.getCandidateId());
                    if (updateDetail.executeUpdate() > 0) {
                        checkSave = 1;
                    }
                }
            }
            return checkSave;
        } catch (Exception ex) {
            ex.printStackTrace();           
            return 0;
        }
    }
//</editor-fold>
}
