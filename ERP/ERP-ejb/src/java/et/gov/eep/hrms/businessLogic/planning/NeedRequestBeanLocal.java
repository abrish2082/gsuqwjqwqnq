
package et.gov.eep.hrms.businessLogic.planning;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;


@Local
public interface NeedRequestBeanLocal {

    public void save(HrHrpRecruitments hrHrpRecruitments);

    public void edit(HrHrpRecruitments hrHrpRecruitments);

    public ArrayList<HrJobTypes> findAllJobTitles();

    public HrJobTypes findByName(int jobid);

    public List<SelectItem> Months();
    
    public List<HrHrpRecruitments> findByDeptAndStatus(HrHrpRecruitments hrHrpRecruitments);
    
    public List<HrJobEducQualifications> findByJobId(HrJobTypes hrJobTypes);

    public HrHrpRecruitments getSelectedRequest(int request);

    public List<HrHrpRecruitmentRequest> findByDeptYearDemand(HrHrpRecruitments hrHrpRecruitments);

   public List<HrHrpRecruitments> findByYear(Integer planningYear);

    public List<HrHrpRecruitments> findallrecruitmentyears();

    public List<HrHrpRecruitmentRequest> findRequestByBudgetYear(Integer planningYear);
    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndRequestType(Integer planningYear, String howToBeFilled);

    public List<HrHrpRecruitments> findRequestByBudgetYears(Integer planningYear);

    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndRequestTypeAndDept(Integer planningYear, String howToBeFilled, Integer depId);

    public List<HrHrpRecruitmentRequest> findRequestByRequestTypeAndDept(String howToBeFilled, Integer depId);

    public List<HrHrpRecruitments> findbyYearAndType(Integer planningYear, String howToBeFilled);

    public List<HrHrpRecruitments> findRequestByBudgetYearAndRequestTypeDept(Integer planningYear, String howToBeFilled, Integer depId);

    public void update(HrHrpRecruitmentRequest hrHrpRecruitmentRequest);

    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndDepartment(Integer planningYear, Integer depId);

    public List<HrHrpRecruitmentRequest> findRequestByDept(Integer depId);

    public List<SelectItem> filterByStatus();

    public List<HrHrpRecruitments> loadHrpRequests(int hrHrpRecruitmentStatus,int hrHrpRecruitmentRequestStatus,int UserId);

    public List<HrHrpRecruitments> findAllrequestsTobeAnalayzed();

    public List<HrHrpRecruitmentRequest> findRequestRecruitmentId(Integer id);

    public List<HrHrpRecruitments> FindCheckedReqForApproval();

    public List<HrHrpRecruitments> findbyId(HrHrpRecruitments hrHrpRecruitments);

    public List<HrHrpRecruitments> findRecritmentByHowTobeFiled(String howToBeFilled);

    public List<HrHrpRecruitments> findRecritmentByHowTobeFiledAndDept(String howToBeFilled, Integer depId);

    public List<HrHrpRecruitments> findByYearAndDept(Integer planningYear, HrDepartments deptId);

    public List<HrHrpRecruitments> findByByDept(HrDepartments deptId);

   
    

}
