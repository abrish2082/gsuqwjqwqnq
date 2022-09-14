/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.organization;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrJobTypesFacade extends AbstractFacade<HrJobTypes> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrJobTypesFacade() {
        super(HrJobTypes.class);
    }

    /**
     * REMOVE THIS METHOD AND REPLACE BY findJobTitle B/C HrJobTypes.findById IS
     * CALLED TWICE IN BOTH METHODS
     *
     * @param id
     * @return
     */
    public HrJobTypes searchJobTypesById(int id) {
        Query query = em.createNamedQuery("HrJobTypes.findById");
        query.setParameter("id", id);
        return (HrJobTypes) query.getSingleResult();
    }

    /**
     *
     * @param jobType
     * @return
     */
    public List<HrJobTypes> returnJobTypes(String jobType) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitle");
        query.setParameter("jobTitle", jobType + "%");
        return query.getResultList();
    }

    /**
     * @param jobTitle
     * @return
     */
    public List<HrJobTypes> searchByJobTitle(String jobTitle) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitleLike", HrJobTypes.class);
        query.setParameter("jobTitle", jobTitle.toUpperCase() + '%');
        try {
            List<HrJobTypes> x = (List<HrJobTypes>) query.getResultList();
            return (List<HrJobTypes>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrJobTypes
     * @return
     */
    public HrJobTypes findAllJobDetail(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findById");
        query.setParameter("id", hrJobTypes.getId());
        try {
            return (HrJobTypes) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrJobTypes
     * @return
     */
    public boolean checkDuplicateJob(HrJobTypes hrJobTypes) {
        boolean isDuplicaton;
        Query query = em.createNamedQuery("HrJobTypes.findDuplicateJobTitleOrJobCode");
        query.setParameter("jobTitle", hrJobTypes.getJobTitle());
        query.setParameter("jobCode", hrJobTypes.getJobCode());
        try {
            if (query.getResultList().size() > 0) {
                isDuplicaton = true;
            } else {
                isDuplicaton = false;
            }
            return isDuplicaton;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<HrJobTypes> listOfJobType(int departmentId) {
        String queryStr = "SELECT j.id,j.job_code,j.job_title FROM hr_job_types j inner join  hr_dept_jobs d on j.id=d.job_id"
                + " where d.DEPT_ID=" + departmentId;
        Query query = em.createNativeQuery(queryStr, HrJobTypes.class);
        List<HrJobTypes> results = query.getResultList();
        return results;
    }

    public List<HrJobTypes> readAllJobTypes() {
        Query query = em.createNamedQuery("HrJobTypes.findAll", HrJobTypes.class);
        List<HrJobTypes> results = query.getResultList();
        return results;
    }

    public String totalNoEmpAllowedForJob(int jobId) {
        Query query = em.createNativeQuery("SELECT sum(NO_EMP_NEEDED) as num FROM hr_dept_jobs "
                + " where JOB_ID = ?1");
        query.setParameter(1, jobId);
        String result = "0";
        try {
            if (query.getSingleResult() != null) {
                result = query.getSingleResult().toString();
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        clearCach();
        return result;

    }

    public String noEmpAllowedForJob(int jobId) {
        Query query = em.createNativeQuery("SELECT NO_EMP_NEEDED FROM hr_job_types "
                + " where ID = ?1");
        query.setParameter(1, jobId);
        String result = "0";
        try {
            if (query.getSingleResult() != null) {
                result = query.getSingleResult().toString();
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        clearCach();
        return result;
    }

    public void clearCach() {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    /**
     *
     * @param hrJobTypes
     * @return
     */
    public ArrayList<HrJobTypes> searchByJobCode(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobCodeLike", HrJobTypes.class);
        query.setParameter("jobCode", hrJobTypes.getJobCode().toUpperCase() + '%');
        try {
            ArrayList<HrJobTypes> jobTypes = new ArrayList(query.getResultList());
            return jobTypes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrJobTypes
     * @return
     */
    public HrJobTypes getByJobId(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobCode", HrJobTypes.class);
        query.setParameter("jobCode", hrJobTypes.getJobCode());
        try {
            HrJobTypes jobList = (HrJobTypes) (query.getResultList().get(0));
            return jobList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrJobTypes
     * @return
     */
    public ArrayList<HrJobTypes> searchByJobTitle(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitleLike", HrJobTypes.class);
        query.setParameter("jobTitle", hrJobTypes.getJobTitle().toUpperCase() + '%');
        try {
            ArrayList<HrJobTypes> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrJobTypes findByJobTitle(HrJobTypes jobTitle) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitle", HrJobTypes.class);
        query.setParameter("jobTitle", jobTitle.getJobTitle());
        try {
            HrJobTypes jobType = (HrJobTypes) (query.getResultList().get(0));
            return jobType;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrJobTypes findBYJobId(Integer jobId) {
        Query query = em.createNamedQuery("HrJobTypes.findById");
        query.setParameter("id", jobId);
        try {
            return (HrJobTypes) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_JOB_TYPES')\n"
                + "and column_name NOT IN('ID','JOB_SUMMARY','JOB_DESCRIPTION','NO_EMP_NEEDED','REMARK','KEY_RESULT_AREA','TYPE','ATTITUDES','DEP_ID','JOB_LEVEL_ID','JOB_GRADE_ID') ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public ArrayList<HrJobTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String Col_Value) {
        if (columnNameResolver.getCol_Name_FromTable() != null && Col_Value != null) {
            Query query = em.createNativeQuery("SELECT * FROM HR_JOB_TYPES\n"
                    + "where " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + Col_Value + "%'  ", HrJobTypes.class);
            try {
                ArrayList<HrJobTypes> employeeInformations = new ArrayList(query.getResultList());
                return employeeInformations;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    }
