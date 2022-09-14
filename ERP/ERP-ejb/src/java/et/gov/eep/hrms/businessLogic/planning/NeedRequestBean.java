/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.planning;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitments;
import et.gov.eep.hrms.mapper.planning.HrHrpRecruitmentRequestFacade;
import et.gov.eep.hrms.mapper.planning.HrHrpRecruitmentsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

@Stateless
public class NeedRequestBean implements NeedRequestBeanLocal {

    @EJB
    HrHrpRecruitmentsFacade hrHrpRecruitmentsFacade;
@EJB
HrHrpRecruitmentRequestFacade hrHrpRecruitmentRequestFacade;
    @Override
    public void save(HrHrpRecruitments hrHrpRecruitments) {
        hrHrpRecruitmentsFacade.create(hrHrpRecruitments);
    }
    @Override
    public void edit(HrHrpRecruitments hrHrpRecruitments) {
        hrHrpRecruitmentsFacade.saveOrUpdate(hrHrpRecruitments);
    }
    @Override
    public void update(HrHrpRecruitmentRequest hrHrpRecruitmentRequest) {
       hrHrpRecruitmentRequestFacade.edit(hrHrpRecruitmentRequest);
    }

    public static String JANUARY = "January";
    public static String FEBRUARY = "February";
    public static String MARCH = "March";
    public static String APRIL = "April";
    public static String MAY = "May";
    public static String JUNE = "June";
    public static String JULY = "July";
    public static String AUGUST = "August";
    public static String SEPTEMBER = "September";
    public static String OCTOBER = "October";
    public static String NOVEMBER = "November";
    public static String DECEMBER = "December";

    @Override
    public List<SelectItem> Months() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("", "--- Select One ---"));
        selectItems.add(new SelectItem(String.valueOf(JANUARY), "January"));
        selectItems.add(new SelectItem(String.valueOf(FEBRUARY), "February"));
        selectItems.add(new SelectItem(String.valueOf(MARCH), "March"));
        selectItems.add(new SelectItem(String.valueOf(APRIL), "April"));
        selectItems.add(new SelectItem(String.valueOf(MAY), "May"));
        selectItems.add(new SelectItem(String.valueOf(JUNE), "June"));
        selectItems.add(new SelectItem(String.valueOf(JULY), "July"));
        selectItems.add(new SelectItem(String.valueOf(AUGUST), "August"));
        selectItems.add(new SelectItem(String.valueOf(SEPTEMBER), "September"));
        selectItems.add(new SelectItem(String.valueOf(OCTOBER), "October"));
        selectItems.add(new SelectItem(String.valueOf(NOVEMBER), "November"));
        selectItems.add(new SelectItem(String.valueOf(DECEMBER), "December"));
        return selectItems;
    }

    @Override
    public ArrayList<HrJobTypes> findAllJobTitles() {
        return hrHrpRecruitmentsFacade.findAllJobTitles();
    }

    @Override
    public HrJobTypes findByName(int jobid) {
        return hrHrpRecruitmentsFacade.findByName(jobid);
    }

    @Override
    public List<HrHrpRecruitments> findByDeptAndStatus(HrHrpRecruitments hrHrpRecruitments) {
        return hrHrpRecruitmentsFacade.findByDeptAndStatus(hrHrpRecruitments);
    }

    @Override
    public List<HrHrpRecruitmentRequest> findByDeptYearDemand(HrHrpRecruitments hrHrpRecruitments) {
        return hrHrpRecruitmentsFacade.findByDeptYearDemand(hrHrpRecruitments);
    }

    @Override
    public List<HrJobEducQualifications> findByJobId(HrJobTypes hrJobTypes) {
        return hrHrpRecruitmentsFacade.findByJobId(hrJobTypes);
    }

    @Override
    public HrHrpRecruitments getSelectedRequest(int request) {
        return hrHrpRecruitmentsFacade.getSelectedRequest(request);
    }

  

    @Override
    public List<HrHrpRecruitments> findallrecruitmentyears() {
        return hrHrpRecruitmentsFacade.findallrecruitmentyears();
    }

    @Override
    public List<HrHrpRecruitments> findByYear(Integer planningYear) {
       return hrHrpRecruitmentsFacade.findallRequestByYear(planningYear);
    }

    @Override
    public List<HrHrpRecruitmentRequest> findRequestByBudgetYear(Integer planningYear) {
     return hrHrpRecruitmentsFacade.findRequestByBudgetYear(planningYear);
    }


    @Override
    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndRequestType(Integer planningYear, String howToBeFilled) {
         return hrHrpRecruitmentsFacade.findRequestByBudgetYearAndRequestType(planningYear,howToBeFilled);
    }

    @Override
    public List<HrHrpRecruitments> findRequestByBudgetYears(Integer planningYear) {
      return hrHrpRecruitmentsFacade.findRequestByBudgetYears(planningYear);
    }

    @Override
    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndRequestTypeAndDept(Integer planningYear, String howToBeFilled, Integer depId) {
        return hrHrpRecruitmentsFacade.findRequestByBudgetYearAndRequestTypeAndDept(planningYear,howToBeFilled,depId);
    }

    @Override
    public List<HrHrpRecruitmentRequest> findRequestByRequestTypeAndDept(String howToBeFilled, Integer depId) {
      return hrHrpRecruitmentsFacade.findRequestByRequestTypeAndDept(howToBeFilled,depId);

    }

    @Override
    public List<HrHrpRecruitments> findbyYearAndType(Integer planningYear, String howToBeFilled) {
        return hrHrpRecruitmentsFacade.findbyYearAndType(planningYear,howToBeFilled);
    }

    @Override
    public List<HrHrpRecruitments> findRequestByBudgetYearAndRequestTypeDept(Integer planningYear, String howToBeFilled, Integer depId) {
       return hrHrpRecruitmentsFacade.findRequestByBudgetYearAndRequestTypeDept(planningYear,howToBeFilled,depId);
    }

    @Override
    public List<HrHrpRecruitmentRequest> findRequestByBudgetYearAndDepartment(Integer planningYear, Integer depId) {
        return hrHrpRecruitmentsFacade.findRequestByBudgetYearAndDepartment(planningYear,depId);
      
    }

    @Override
    public List<HrHrpRecruitmentRequest> findRequestByDept(Integer depId) {
        return hrHrpRecruitmentsFacade.findRequestByDept(depId);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
         selectItems.add(new SelectItem(String.valueOf("1"), "Load Checked List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved List"));
        return selectItems;
    }

    @Override
    public List<HrHrpRecruitments> loadHrpRequests(int hrHrpRecruitmentStatus,int hrHrpRecruitmentRequestStatus,int UserId) {
       return hrHrpRecruitmentsFacade.loadHrpRequests(hrHrpRecruitmentStatus,hrHrpRecruitmentRequestStatus,UserId);
    }

    @Override
    public List<HrHrpRecruitments> findAllrequestsTobeAnalayzed() {
        return hrHrpRecruitmentsFacade.findAllrequestsTobeAnalayzed();
    }

    @Override
    public List<HrHrpRecruitmentRequest> findRequestRecruitmentId(Integer id) {
        return hrHrpRecruitmentsFacade.findAllrequestsByRecruitmentId(id);
    }

    @Override
    public List<HrHrpRecruitments> FindCheckedReqForApproval() {
      return hrHrpRecruitmentsFacade.FindCheckedReqForApproval();
    }

    @Override
    public List<HrHrpRecruitments> findbyId(HrHrpRecruitments hrHrpRecruitments) {
      return hrHrpRecruitmentsFacade.findById(hrHrpRecruitments);
    }

    @Override
    public List<HrHrpRecruitments> findRecritmentByHowTobeFiled(String howToBeFilled) {
        return hrHrpRecruitmentsFacade.findRecritmentByHowTobeFiled(howToBeFilled);
    }

    @Override
    public List<HrHrpRecruitments> findRecritmentByHowTobeFiledAndDept(String howToBeFilled, Integer depId) {
        return hrHrpRecruitmentsFacade.findRecritmentByHowTobeFiledAndDept(howToBeFilled,depId);
    }

    @Override
    public List<HrHrpRecruitments> findByYearAndDept(Integer planningYear, HrDepartments deptId) {
        return hrHrpRecruitmentsFacade.findByYearAndDept(planningYear,deptId);
    }

    @Override
    public List<HrHrpRecruitments> findByByDept(HrDepartments deptId) {
      return hrHrpRecruitmentsFacade.findByByDept(deptId);
    }

  

    

   
   

}
