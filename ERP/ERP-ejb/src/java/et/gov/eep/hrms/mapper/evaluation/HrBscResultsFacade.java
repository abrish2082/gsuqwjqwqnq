/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.entity.evaluation.HrBscResults;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class HrBscResultsFacade extends AbstractFacade<HrBscResults> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrBscResultsFacade() {
        super(HrBscResults.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrBscSessions> readActiveSession(String toDayInEC) {
        Query query = em.createNamedQuery("HrBscSessions.findActiveSession", HrBscSessions.class);
        query.setParameter("toDayInEC", toDayInEC);
        try {
            return (List<HrBscSessions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrBscSessions findById(HrBscSessions hrBscSessions) {
        Query query = em.createNamedQuery("HrBscSessions.findById");
        query.setParameter("id", hrBscSessions.getId());
        try {
            return (HrBscSessions) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public HrBsc selectBSC(int department, int sessionId) {
        Query query = em.createNativeQuery("SELECT"
                + " HR_BSC.ID,"
                + " HR_BSC.PREPARED_BY,"
                + " HR_BSC.PREPARED_ON,"
                + " HR_EMPLOYEES.FIRST_NAME,"
                + " HR_EMPLOYEES.MIDDLE_NAME,"
                + " HR_EMPLOYEES.LAST_NAME"
                + " FROM HR_BSC"
                + " INNER JOIN HR_EMPLOYEES"
                + " ON HR_BSC.PREPARED_BY=HR_EMPLOYEES.ID"
                + " WHERE HR_BSC.DEPT_ID=?"
                + " AND HR_BSC.SESSION_ID=?", HrBsc.class);
        query.setParameter(1, department);
        query.setParameter(2, sessionId);
        try {
            if (query.getResultList().size() > 0) {
                HrBsc bsc = (HrBsc) query.getResultList().get(0);
                return bsc;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrBscResults> readEmployees(int department, int sessionId, int searchCondition) {
        Query query;
        if (searchCondition == 0) {//not evaluated
            System.out.println("--in not evaluated");
            query = em.createNativeQuery("SELECT "
                    //                    + " '0' BSC_ID, "
                    //                    + " '0' RESULT_ID, "
                    //                    + " '0' RESULT, "
                    //                    + " ' ' REMARK, "
                    + " HR_EMPLOYEES.ID, "
                    + " HR_EMPLOYEES.EMP_ID, "
                    + " HR_EMPLOYEES.FIRST_NAME, "
                    + " HR_EMPLOYEES.MIDDLE_NAME, "
                    + " HR_EMPLOYEES.LAST_NAME, "
                    + " HR_EMPLOYEES.SEX, "
                    + " HR_JOB_TYPES.JOB_TITLE, "
                    + " HR_LU_GRADES.GRADE "
                    + " FROM HR_EMPLOYEES "
                    + " INNER JOIN HR_JOB_TYPES "
                    + " ON HR_EMPLOYEES.JOB_ID=HR_JOB_TYPES.ID "
                    + " INNER JOIN HR_SALARY_SCALE_RANGES "
                    + " ON HR_JOB_TYPES.JOB_GRADE_ID=HR_SALARY_SCALE_RANGES.ID "
                    + " INNER JOIN HR_LU_GRADES "
                    + " ON HR_SALARY_SCALE_RANGES.GRADE_ID=HR_LU_GRADES.ID "
                    + " WHERE HR_EMPLOYEES.ID NOT IN "
                    + " (SELECT HR_EMPLOYEES.ID "
                    + " FROM HR_BSC_RESULTS "
                    + " INNER JOIN HR_BSC "
                    + " ON HR_BSC_RESULTS.BSC_ID=HR_BSC.ID "
                    + " INNER JOIN HR_EMPLOYEES "
                    + " ON HR_EMPLOYEES.ID=HR_BSC_RESULTS.EMP_ID "
                    + " WHERE HR_BSC.DEPT_ID=? "
                    + " AND HR_BSC.SESSION_ID=?) "
                    + " AND HR_EMPLOYEES.DEPT_ID=? "
                    + " ORDER BY FIRST_NAME,MIDDLE_NAME,LAST_NAME", HrBscResults.class);
        } else if (searchCondition == 1) {//only evaluated
            System.out.println("--in only evaluated");
            query = em.createNativeQuery("SELECT "
                    + " HR_BSC.ID BSC_ID, "
                    + " HR_BSC_RESULTS.ID RESULT_ID, "
                    + " HR_BSC_RESULTS.RESULT, "
                    + " HR_BSC_RESULTS.REMARK, "
                    + " HR_EMPLOYEES.ID, "
                    + " HR_EMPLOYEES.EMP_ID, "
                    + " HR_EMPLOYEES.FIRST_NAME, "
                    + " HR_EMPLOYEES.MIDDLE_NAME, "
                    + " HR_EMPLOYEES.LAST_NAME, "
                    + " HR_EMPLOYEES.SEX, "
                    + " HR_JOB_TYPES.JOB_TITLE, "
                    + " HR_LU_GRADES.GRADE "
                    + " FROM HR_EMPLOYEES "
                    + " INNER JOIN HR_JOB_TYPES"
                    + " ON HR_EMPLOYEES.JOB_ID=HR_JOB_TYPES.ID"
                    + " INNER JOIN HR_SALARY_SCALE_RANGES "
                    + " ON HR_JOB_TYPES.JOB_GRADE_ID=HR_SALARY_SCALE_RANGES.ID "
                    + " INNER JOIN HR_LU_GRADES "
                    + " ON HR_SALARY_SCALE_RANGES.GRADE_ID=HR_LU_GRADES.ID "
                    + " INNER JOIN HR_BSC_RESULTS "
                    + " ON HR_BSC_RESULTS.EMP_ID=HR_EMPLOYEES.ID"
                    + " INNER JOIN HR_BSC "
                    + " ON HR_BSC_RESULTS.BSC_ID=HR_BSC.ID "
                    + " AND HR_BSC.DEPT_ID=? "
                    + " AND HR_BSC.SESSION_ID=? "
                    + " WHERE HR_EMPLOYEES.DEPT_ID=? "
                    + " ORDER BY FIRST_NAME,MIDDLE_NAME,LAST_NAME", HrBscResults.class);
        } else {//both evaluated and non evaluated
            System.out.println("--in both evaluated and non evaluated");
            query = em.createNativeQuery("SELECT "
                    + " HR_BSC.ID BSC_ID, "
                    + " HR_BSC_RESULTS.ID RESULT_ID, "
                    + " HR_BSC_RESULTS.RESULT, "
                    + " HR_BSC_RESULTS.REMARK, "
                    + " HR_EMPLOYEES.ID, "
                    + " HR_EMPLOYEES.EMP_ID, "
                    + " HR_EMPLOYEES.FIRST_NAME, "
                    + " HR_EMPLOYEES.MIDDLE_NAME, "
                    + " HR_EMPLOYEES.LAST_NAME, "
                    + " HR_EMPLOYEES.SEX, "
                    + " HR_JOB_TYPES.JOB_TITLE,"
                    + " HR_LU_GRADES.GRADE "
                    + " FROM HR_EMPLOYEES "
                    + " INNER JOIN HR_JOB_TYPES"
                    + " ON HR_EMPLOYEES.JOB_ID=HR_JOB_TYPES.ID"
                    + " INNER JOIN HR_SALARY_SCALE_RANGES "
                    + " ON HR_JOB_TYPES.JOB_GRADE_ID=HR_SALARY_SCALE_RANGES.ID "
                    + " INNER JOIN HR_LU_GRADES "
                    + " ON HR_SALARY_SCALE_RANGES.GRADE_ID=HR_LU_GRADES.ID "
                    + " LEFT JOIN HR_BSC_RESULTS "
                    + " ON HR_BSC_RESULTS.EMP_ID=HR_EMPLOYEES.ID "
                    + " LEFT JOIN HR_BSC "
                    + " ON HR_BSC_RESULTS.BSC_ID=HR_BSC.ID "
                    + " AND HR_BSC.DEPT_ID=? "
                    + " AND HR_BSC.SESSION_ID=? "
                    + " WHERE HR_EMPLOYEES.DEPT_ID=? "
                    + " ORDER BY FIRST_NAME,MIDDLE_NAME,LAST_NAME", HrBscResults.class);
        }
        query.setParameter(1, department);
        query.setParameter(2, sessionId);
        query.setParameter(3, department);
        try {
            return (List<HrBscResults>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

}
