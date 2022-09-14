/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.planning;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class HrHrpRecruitmentsFacade extends AbstractFacade<HrHrpRecruitments> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    @Inject
    HrHrpRecruitments hrHrpRecruitments;

    public HrHrpRecruitmentsFacade() {
        super(HrHrpRecruitments.class);
    }

    public ArrayList<HrJobTypes> findAllJobTitles() {
        Query query = em.createNamedQuery("HrJobTypes.findAll");
        ArrayList<HrJobTypes> jobTitle = new ArrayList(query.getResultList());
        return jobTitle;

    }

    public HrJobTypes findByName(int jobid) {
        Query query = em.createNamedQuery("HrJobTypes.findById");
        query.setParameter("id", jobid);
        HrJobTypes j = (HrJobTypes) query.getResultList().get(0);
        return j;
    }

    public List<HrHrpRecruitments> findByDeptAndStatus(HrHrpRecruitments hrHrpRecruitments) {
        Query query1 = em.createNamedQuery("HrHrpRecruitments.findByDeptId", HrHrpRecruitments.class);
        Query query2 = em.createNamedQuery("HrHrpRecruitments.findByDeptIdStatus", HrHrpRecruitments.class);
        query1.setParameter("deptId", hrHrpRecruitments.getDeptId().getDepId());
        query2.setParameter("deptId", hrHrpRecruitments.getDeptId().getDepId());
        query2.setParameter("status", hrHrpRecruitments.getStatus());
        if (hrHrpRecruitments.getStatus() == 2) {
            List<HrHrpRecruitments> hrpid = new ArrayList(query1.getResultList());
            return hrpid;
        } else {
            List<HrHrpRecruitments> hrpid = new ArrayList(query2.getResultList());
            return hrpid;

        }

    }

    public List<HrHrpRecruitmentRequest> findByDeptYearDemand(HrHrpRecruitments hrHrpRecruitments) {
        Query query1 = em.createNamedQuery("HrHrpRecruitments.findByDeptIdYear", HrHrpRecruitments.class);
        query1.setParameter("deptId", hrHrpRecruitments.getDeptId().getDepId());
        query1.setParameter("planningYear", hrHrpRecruitments.getPlanningYear());

        HrHrpRecruitments hrpid = (HrHrpRecruitments) query1.getResultList().get(0);
        Query query2 = em.createNamedQuery("HrHrpRecruitmentRequest.findByRecruitmentId", HrHrpRecruitmentRequest.class);
        query2.setParameter("recruitmentId", hrpid.getId());
        List<HrHrpRecruitmentRequest> planList = new ArrayList(query2.getResultList());
        return planList;

    }

    public List<HrJobEducQualifications> findByJobId(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobEducQualifications.findByJobId", HrJobEducQualifications.class);
        query.setParameter("jobId", hrJobTypes.getId());
        List<HrJobEducQualifications> qualList = new ArrayList(query.getResultList());
        return qualList;

    }

    public HrHrpRecruitments getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrHrpRecruitments.findById");
        query.setParameter("id", request);
        HrHrpRecruitments selectedRequest = (HrHrpRecruitments) query.getResultList().get(0);
        return selectedRequest;
    }

    public HrHrpRecruitments findByear(Integer planningYear) {
        Query query = em.createNamedQuery("HrHrpRecruitments.findByYear");
        query.setParameter("planningYear", planningYear);
        HrHrpRecruitments selectedyeart = (HrHrpRecruitments) query.getResultList().get(0);
        return selectedyeart;
    }

    public List<HrHrpRecruitments> findallrecruitmentyears() {
        Query query = em.createNamedQuery("HrHrpRecruitments.findAllyears");
        List<HrHrpRecruitments> yearlList = new ArrayList(query.getResultList());
        return yearlList;

    }

    public List<HrHrpRecruitments> findallRequestByYear(Integer planningYear) {
        Query query = em.createNamedQuery("HrHrpRecruitments.findByYearandstatus");
        query.setParameter("planningYear", planningYear);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;

    }

    public List<HrHrpRecruitmentRequest> findRequestByBudgetYear(Integer planningYear) {
        Query query = em.createNativeQuery("select * \n"
                + "from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "on nr.ID=tn.RECRUITMENT_ID\n"
                + "where nr.PLANNING_YEAR='" + planningYear + "'and nr.STATUS=0 ", HrHrpRecruitmentRequest.class);
        List<HrHrpRecruitmentRequest> qualList = new ArrayList(query.getResultList());
        return qualList;

    }

    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndRequestType(Integer planningYear, String howToBeFilled) {
        Query query = em.createNativeQuery(" select * \n"
                + "                     from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                    on nr.ID=tn.RECRUITMENT_ID\n"
                + "                     where nr.PLANNING_YEAR='" + planningYear
                + "' and tn.HOW_TO_BE_FILLED='" + howToBeFilled + "' and nr.STATUS=1 and tn.STATUS=0", HrHrpRecruitmentRequest.class);
        List<HrHrpRecruitmentRequest> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> findRequestByBudgetYears(Integer planningYear) {
        Query query = em.createNamedQuery("HrHrpRecruitments.findByYearandstatus");
        query.setParameter("planningYear", planningYear);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;

    }

    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndRequestTypeAndDept(Integer planningYear, String howToBeFilled, Integer depId) {
        Query query = em.createNativeQuery("select * \n"
                + "                                       from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                                       on nr.ID=tn.RECRUITMENT_ID\n"
                + "                                         where nr.PLANNING_YEAR= '" + planningYear
                + "'\n"
                + "                    and tn.HOW_TO_BE_FILLED='" + howToBeFilled
                + "'  and nr.DEPT_ID='" + depId + "'  \n"
                + " and nr.STATUS=1 and tn.STATUS=0  ", HrHrpRecruitmentRequest.class);
        List<HrHrpRecruitmentRequest> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitmentRequest> findRequestByRequestTypeAndDept(String howToBeFilled, Integer depId) {
        Query query = em.createNativeQuery(" select * \n"
                + "                     from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                    on nr.ID=tn.RECRUITMENT_ID\n"
                + "                     where nr.DEPT_ID='" + depId
                + "' and tn.HOW_TO_BE_FILLED='" + howToBeFilled + "'and nr.STATUS=1 and tn.STATUS=0 ", HrHrpRecruitmentRequest.class);
        List<HrHrpRecruitmentRequest> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> findbyYearAndType(Integer planningYear, String howToBeFilled) {
        Query query = em.createNativeQuery(" select DISTINCT nr.id , nr.dept_id,nr.planning_year  \n"
                + "                     from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                    on nr.ID=tn.RECRUITMENT_ID\n"
                + "                     where nr.PLANNING_YEAR='" + planningYear
                + "' and tn.HOW_TO_BE_FILLED='" + howToBeFilled + "'and nr.STATUS=1 and tn.STATUS=0", HrHrpRecruitments.class);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;

    }

    public List<HrHrpRecruitments> findRequestByBudgetYearAndRequestTypeDept(Integer planningYear, String howToBeFilled, Integer depId) {
        Query query = em.createNativeQuery("select DISTINCT nr.id , nr.dept_id,nr.planning_year \n"
                + "                                       from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                                       on nr.ID=tn.RECRUITMENT_ID\n"
                + "                                         where nr.PLANNING_YEAR= '" + planningYear
                + "'\n"
                + "    and tn.HOW_TO_BE_FILLED='" + howToBeFilled
                + "'  and nr.DEPT_ID='" + depId + "' \n"
                + "and nr.STATUS=1 and tn.STATUS=0   ", HrHrpRecruitments.class);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndDepartment(Integer planningYear, Integer depId) {
        Query query = em.createNativeQuery("select * \n"
                + "                                       from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                                       on nr.ID=tn.RECRUITMENT_ID\n"
                + "                                         where nr.PLANNING_YEAR= '" + planningYear
                + "'\n"
                + "  and nr.DEPT_ID='" + depId + "'  and nr.STATUS=0 \n"
                + "   ", HrHrpRecruitmentRequest.class);
        List<HrHrpRecruitmentRequest> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitmentRequest> findRequestByDept(Integer depId) {
        Query query = em.createNativeQuery(" select * \n"
                + "                     from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                    on nr.ID=tn.RECRUITMENT_ID\n"
                + "                     where nr.DEPT_ID='" + depId
                + "' and nr.STATUS=0 \n", HrHrpRecruitmentRequest.class);
        List<HrHrpRecruitmentRequest> qualList = new ArrayList(query.getResultList());
        return qualList;

    }

    public List<HrHrpRecruitments> loadHrpRequests(int hrHrpRecruitmentStatus, int hrHrpRecruitmentRequestStatus,int UserId) {
        Query query = em.createNativeQuery("select DISTINCT nr.id,nr.REQUEST_DATE , nr.dept_id,nr.planning_year,tn.status\n"
                + "                                   from HR_HRP_RECRUITMENTS nr Inner join  HR_HRP_RECRUITMENT_REQUEST tn \n"
                + "                                   \n"
                + "                              on nr.ID=tn.RECRUITMENT_ID inner join  WF_HR_PROCESSED wf on nr.ID= wf.hrprecruitment_id \n"
                + "                             where  nr.status='"+hrHrpRecruitmentStatus +"' and tn.status='"+hrHrpRecruitmentRequestStatus +"' and wf.processed_by='"+UserId +"'" , HrHrpRecruitments.class);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> findAllrequestsTobeAnalayzed() {
        Query query = em.createNamedQuery("HrHrpRecruitments.findByStatus");
        query.setParameter("status", 0);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitmentRequest> findAllrequestsByRecruitmentId(Integer id) {
        Query query = em.createNamedQuery("HrHrpRecruitmentRequest.findByRecruitmentId");
        query.setParameter("recruitmentId", id);
        List<HrHrpRecruitmentRequest> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> FindCheckedReqForApproval() {
        Query query = em.createNativeQuery(" select DISTINCT nr.id , nr.dept_id,nr.planning_year,nr.request_date,nr.processed_by,nr.promotion_remark,nr.requester_id,nr.status\n" +
"                                  from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n" +
"                              on nr.ID=tn.RECRUITMENT_ID\n" +
"                               where tn.STATUS=0 and nr.STATUS=1", HrHrpRecruitments.class);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }


    public List<HrHrpRecruitments> findById(HrHrpRecruitments hrHrpRecruitments) {
        Query query = em.createNamedQuery("HrHrpRecruitments.findById");
        query.setParameter("id", hrHrpRecruitments.getId());
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> findRecritmentByHowTobeFiled(String howToBeFilled) {
        Query query = em.createNativeQuery("select DISTINCT nr.id , nr.dept_id,nr.planning_year \n"
                + "                                       from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                                       on nr.ID=tn.RECRUITMENT_ID\n"
                + "                                         where tn.HOW_TO_BE_FILLED='" + howToBeFilled + "' "
                + "and nr.STATUS=1 and tn.STATUS=0   ", HrHrpRecruitments.class);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> findRecritmentByHowTobeFiledAndDept(String howToBeFilled, Integer depId) {
        Query query = em.createNativeQuery("select DISTINCT nr.id , nr.dept_id,nr.planning_year \n"
                + "                                       from HR_HRP_RECRUITMENT_REQUEST tn Inner join HR_HRP_RECRUITMENTS nr \n"
                + "                                       on nr.ID=tn.RECRUITMENT_ID\n"
                + "                                         where tn.HOW_TO_BE_FILLED='" + howToBeFilled + "' and nr.DEPT_ID='" + depId + "' "
                + "and nr.STATUS=1 and tn.STATUS=0   ", HrHrpRecruitments.class);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> findByByDept(HrDepartments deptId) {
        Query query = em.createNamedQuery("HrHrpRecruitments.findByDeptId");
        query.setParameter("deptId", deptId.getDepId());
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

    public List<HrHrpRecruitments> findByYearAndDept(Integer planningYear, HrDepartments deptId) {
        Query query = em.createNamedQuery("HrHrpRecruitments.findByDepIdAndPlanYear");
        query.setParameter("deptId", deptId);
        query.setParameter("planningYear", planningYear);
        List<HrHrpRecruitments> qualList = new ArrayList(query.getResultList());
        return qualList;
    }

}
