package et.gov.eep.hrms.mapper.recruitment;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import java.util.ArrayList;
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
public class HrRecruitmentRequestsFacade extends AbstractFacade<HrRecruitmentRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrRecruitmentRequestsFacade() {
        super(HrRecruitmentRequests.class);
    }

    public HrDepartments getSelectDepartement(int departmentid) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", departmentid);
        try {
            HrDepartments selectdepartment = (HrDepartments) query.getResultList().get(0);
            return selectdepartment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrRecruitmentRequests getSelectRequest(int request) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findById");
        query.setParameter("id", request);
        try {
            HrRecruitmentRequests selectrequest = (HrRecruitmentRequests) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrRecruitmentRequests getSelectRequestToApprove(int request) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByIdNotApproved");
        query.setParameter("id", request);
        System.err.println("===" + query.getResultList().size());
        try {
            HrRecruitmentRequests selectrequest = (HrRecruitmentRequests) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrJobTypes> listOfJobType(int departmentId) {
        String queryStr = "SELECT j.id,j.job_code,j.job_title FROM hr_job_types j inner join  hr_dept_jobs d on j.id=d.job_id"
                + " where d.DEPT_ID=" + departmentId;
        Query query = em.createNativeQuery(queryStr, HrJobTypes.class);
        List<HrJobTypes> results = query.getResultList();
        return results;
    }

    @Override
    public ArrayList<HrRecruitmentRequests> findAll() {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findAll");
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrRecruitmentRequests> findListByDate(HrRecruitmentRequests hrRecruitmentRequests) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByRequestDate");
        query.setParameter("requestDate", hrRecruitmentRequests.getRequestDate());
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrRecruitmentRequests> findList() {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findAllRequest");
        //   query.setParameter("status", hrRecruitmentRequests.getStatus());        
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrRecruitmentRequests findListByDate1(HrRecruitmentRequests hrRecruitmentRequests) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByRequestDate");
        query.setParameter("requestDate", hrRecruitmentRequests.getRequestDate());
        try {
            HrRecruitmentRequests request = (HrRecruitmentRequests) query.getResultList().get(0);
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrRecruitmentRequests> findListReqToApproveByDate(HrRecruitmentRequests hrRecruitmentRequests) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByRequestDateStatusZero");
        query.setParameter("requestDate", hrRecruitmentRequests.getRequestDate());
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrRecruitmentRequests> findListByDepartment(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByRequestDeptId");
        query.setParameter("deptId", hrDepartments.getDepName());
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrRecruitmentRequests> findByDetAndDate(HrDepartments hrDepartments, HrRecruitmentRequests hrRecruitmentRequests) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByRequestDeptIdAndDate");
        query.setParameter("deptId", hrDepartments.getDepName());
        query.setParameter("requestDate", hrRecruitmentRequests.getRequestDate());
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrJobTypes findByName(int jobid) {
        Query query = em.createNamedQuery("HrJobTypes.findById");
        query.setParameter("id", jobid);
        try {
            HrJobTypes jTypes = (HrJobTypes) query.getResultList().get(0);
            return jTypes;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> findBatchCode() {
        Query query = em.createNamedQuery("HrRecruitmentRequests.batchCode");
        try {
            ArrayList<String> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrRecruitmentRequests> getRequstesByBachCode(String bachCode) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByRecruitBatchCode");
        query.setParameter("recruitBatchCode", bachCode);
        System.out.println("bachCode-------" + bachCode);
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrRecruitmentRequests getRequstesByBachCodes(String bachCode) {

        try {
            Query query = em.createNamedQuery("HrRecruitmentRequests.findByRecruitBatchCode");
            query.setParameter("recruitBatchCode", bachCode);
            System.out.println("bachCode-------" + bachCode);
            return (HrRecruitmentRequests) query.getSingleResult();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
    }

    public String totalNoAllowedJob(HrDepartments department, HrJobTypes job) {
        Query query = em.createNativeQuery("select sum(no_emp_needed) as num from hr_dept_jobs WHERE dept_id=?1  AND job_id=?2");
        query.setParameter(1, department.getDepId().intValue());
        String totNumEmp = "0";
        if (job != null) {
            query.setParameter(2, job.getId().intValue());
            if (query.getSingleResult() != null) {
                totNumEmp = query.getSingleResult().toString();
            } else {
                totNumEmp = "0";
            }
        }
        return totNumEmp;
    }

    public int totalNoEmployeesInDepJob(HrDepartments department, HrJobTypes job) {
        Query query = em.createNativeQuery("select count(job_id) from hr_employees where dept_id=?1 and job_id=?2");
        query.setParameter(1, department.getDepId().intValue());
        query.setParameter(2, job.getId().intValue());
        int totNumEmp = Integer.parseInt(query.getSingleResult().toString());
        return totNumEmp;
    }

    public List<HrRecruitmentRequests> findListByDateAndDeptId(HrRecruitmentRequests hrRecruitmentRequests) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByRequestDateAndDeptId");
        query.setParameter("requestDate", hrRecruitmentRequests.getRequestDate());
        query.setParameter("deptId", hrRecruitmentRequests.getDeptId().getDepId());
        try {
            ArrayList<HrRecruitmentRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrHrpRecruitmentRequest> findByHowToBeFilled(String howToBeFilled) {
        Query query = em.createNamedQuery("HrHrpRecruitmentRequest.findByHowToBeFilled", HrHrpRecruitmentRequest.class);
        query.setParameter("howToBeFilled", howToBeFilled.toUpperCase() + '%');
        try {
            return (List<HrHrpRecruitmentRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrRecruitmentRequests getByRecruitmentReqstID(String recruitmentReqstID) {
        try {
            Query query = em.createNamedQuery("HrRecruitmentRequests.findById");
            query.setParameter("id", Integer.valueOf(recruitmentReqstID));
            return (HrRecruitmentRequests) query.getResultList();
        } catch (Exception e) {
          //  e.printStackTrace();
            return null;
        }
    }

    public List<HrRecruitmentRequests> loadRecruitmentList(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='1' OR STATUS='2' OR STATUS='3')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_RECRUITMENT_REQUESTS "
                + queryStatus
                + "ORDER BY REQUEST_DATE DESC", HrRecruitmentRequests.class);
        try {
            return (List<HrRecruitmentRequests>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrRecruitmentRequests> loadRecLists(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            queryStatus = " WHERE(STATUS='0')";
        } else if (status == 1) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_RECRUITMENT_REQUESTS "
                + queryStatus
                + "ORDER BY REQUEST_DATE DESC", HrRecruitmentRequests.class);
        try {
            return (List<HrRecruitmentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrRecruitmentRequests> loadRequestList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RECRUITMENT_REQUESTS recuitment"
                    + " WHERE recuitment.STATUS ='" + status.getStatus1() + "' AND"
                    + " recuitment.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY recuitment.REQUEST_DATE DESC", HrRecruitmentRequests.class);
            return (List<HrRecruitmentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrRecruitmentRequests> loadRecuitmentList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RECRUITMENT_REQUESTS recuitment"
                    + " WHERE (recuitment.status ='" + status.getStatus1() + "' OR"
                    + " recuitment.status ='" + status.getStatus2() + "') AND"
                    + " recuitment.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY recuitment.REQUEST_DATE DESC", HrRecruitmentRequests.class);
            return (List<HrRecruitmentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrRecruitmentRequests> loadToApproveInvoiceList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RECRUITMENT_REQUESTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "' OR"
                    + " rq.status='" + status.getStatus3() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrRecruitmentRequests.class);
            return (List<HrRecruitmentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRecruitmentRequests> loadToModifyInvoiceList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_RECRUITMENT_REQUESTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrRecruitmentRequests.class);
            return (List<HrRecruitmentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRecruitmentRequests> findRecPreparedList() {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByWfPrepared");
        try {
            List<HrRecruitmentRequests> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRecruitmentRequests> findRecCheckedList() {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByWfChecked");
        try {
            ArrayList<HrRecruitmentRequests> checkedList = new ArrayList(query.getResultList());
            return checkedList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRecruitmentRequests> findWfToModify() {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByWfToBeModify");
        try {
            ArrayList<HrRecruitmentRequests> reqList = new ArrayList(query.getResultList());
            return reqList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRecruitmentRequests> findWfToApprove() {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByWfToBeApprove");
        try {
            ArrayList<HrRecruitmentRequests> reqList = new ArrayList(query.getResultList());
            return reqList;
        } catch (Exception ex) {
            return null;
        }
    }

}
