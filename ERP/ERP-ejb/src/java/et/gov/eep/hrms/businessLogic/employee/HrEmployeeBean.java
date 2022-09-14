/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.employee.HrEmpSkill;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import et.gov.eep.hrms.entity.lookup.HrLuMemberships;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.lookup.HrLuReligions;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import et.gov.eep.hrms.mapper.employee.HrEmpSkillFacade;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.hrms.mapper.recruitment.HrRecruitmentRequestsFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrEmployeeBean implements HrEmployeeBeanLocal {

    @EJB
    HrEmployeesFacade hrEmployeesFacade;

    @EJB
    HrEmpSkillFacade hrEmpSkillFacade;

    @EJB
    HrRecruitmentRequestsFacade hrRecruitmentRequestsFacade;

    @Override
    public void save(HrEmployees hrEmployees) {
        hrEmployeesFacade.create(hrEmployees);
    }

    @Override
    public void Delete(HrEmployees hrEmployees) {
        hrEmployeesFacade.remove(hrEmployees);
    }

    @Override
    public List<HrEmployees> findAll() {
        return hrEmployeesFacade.findAll();
    }

    @Override
    public void edit(HrEmployees hrEmployees) {
        hrEmployeesFacade.edit(hrEmployees);
    }

    @Override
    public ArrayList<HrEmployees> SearchByFname(HrEmployees hrEmployees) {
        return hrEmployeesFacade.SearchByFname(hrEmployees);
    }
     @Override
    public ArrayList<HrEmployees> SearchByFnameCont(HrEmployees hrEmployees) {
        return hrEmployeesFacade.SearchByFnameCont(hrEmployees);
    }

    @Override
    public HrEmployees getByFirstName(HrEmployees hrEmployees) {
        return hrEmployeesFacade.getByFirstName(hrEmployees);
    }

    @Override
    public ArrayList<HrEmployees> findEmployeeByDept(HrEmployees hrEmployees) {
        return hrEmployeesFacade.findEmployeeByDept(hrEmployees);
    }

    @Override
    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees) {
        return hrEmployeesFacade.SearchByEmpId(hrEmployees);
    }
      @Override
    public ArrayList<HrEmployees> SearchByEmpIdCont(HrEmployees hrEmployees) {
        return hrEmployeesFacade.SearchByEmpIdCont(hrEmployees);
    }
    
//    @Override
//    public ArrayList<HrEmployees> findByFnameDept(HrEmployees hrEmployees ){
//        return hrEmployeesFacade.findByFnameDept(hrEmployees);
//    }

    @Override
    public ArrayList<HrEmployees> findByFnameAndEmpId(HrEmployees hrEmployees) {
        return hrEmployeesFacade.findByFnameAndEmpId(hrEmployees);
    }

    @Override
    public HrEmployees checkEmpId(String empId) {
        return hrEmployeesFacade.checkEmpId(empId);
    }

    @Override
    public HrEmployees getByEmpId(HrEmployees hrEmployees) {
        return hrEmployeesFacade.getByEmpId(hrEmployees);
    }

    @Override
    public HrEmployees getById(HrEmployees hrEmployees) {
        return hrEmployeesFacade.getById(hrEmployees);
    }

    @Override
    public HrLuReligions findReligion(HrLuReligions hrLuReligion) {
        return hrEmployeesFacade.findReligion(hrLuReligion);
    }

    @Override
    public HrDepartments findDepartment(HrDepartments hrDepartments) {
        return hrEmployeesFacade.findDepartment(hrDepartments);
    }

    @Override
    public HrLuTitles findTitles(HrLuTitles hrLuTitles) {
        return hrEmployeesFacade.findTitle(hrLuTitles);
    }

    @Override
    public HrLuNationalities findNationality(HrLuNationalities hrLuNationalities) {
        return hrEmployeesFacade.findNationality(hrLuNationalities);
    }

    @Override
    public HrJobTypes searchJobTitleInfo(HrJobTypes JobTypes) {
        return hrEmployeesFacade.searchJobTitleInfo(JobTypes);
    }

    @Override
    public ArrayList<HrJobTypes> searchJobInfo(HrDepartments hrDepartments) {
        return hrEmployeesFacade.getJobTytleInfo(hrDepartments);
    }

    @Override
    public ArrayList<HrSalaryScales> searchSalaryStepsInfo(HrSalaryScaleRanges hrSalaryScaleRanges) {
        return hrEmployeesFacade.getSalaryScaleInfo(hrSalaryScaleRanges);
    }

    @Override
    public HrLuGrades searchHrLuGradesInfo(HrLuGrades hrLuGrades) {
        return hrEmployeesFacade.searchHrLuGradesInfo(hrLuGrades);
    }

    @Override
    public List<HrLuGrades> findAllGrades() {
        return hrEmployeesFacade.findAllGrade();
    }

    @Override
    public HrLuSalarySteps searchHrLuSalaryStepsInfo(HrLuSalarySteps hrLuSalarySteps) {
        return hrEmployeesFacade.searchHrLuStepsInfo(hrLuSalarySteps);
    }

    @Override
    public List<HrLuSalarySteps> findAllSteps() {
        return hrEmployeesFacade.findAllSteps();
    }

    @Override
    public HrDepartments findDepartmentbyName(HrDepartments hrDepartment) {
        return hrEmployeesFacade.findDepartmentbyName(hrDepartment);
    }

    public ArrayList<HrLuBanks> findDepartment() {
        return hrEmployeesFacade.findAllBanks();
    }

    @Override
    public ArrayList<HrDepartments> getDepartementList() {
        return hrEmployeesFacade.findAllDepartment();

    }

    @Override
    public HrSalaryScales findByRangeAndStep(HrSalaryScales hrSalaryScales) {
        return hrEmployeesFacade.findByRangeAndStep(hrSalaryScales);
    }

    @Override
    public ArrayList<HrLuBanks> findAllBanks() {
        return hrEmployeesFacade.findAllBanks();
    }

    @Override
    public HrLuBanks findBanks(HrLuBanks hrLuBanks) {;
        return hrEmployeesFacade.findhrluBanks(hrLuBanks);
    }

    @Override
    public HrLuBankBranches findBankBranchs(HrLuBankBranches hrLuBankBranches) {
        return hrEmployeesFacade.findBankBranchs(hrLuBankBranches);
    }

    @Override
    public ArrayList<HrLuBankBranches> searchBankBranchInfo(HrLuBanks hrLuBanks) {
        return hrEmployeesFacade.getBankBranchInfo(hrLuBanks);
    }

    @Override
    public void EditEmpFile(HrEmployees hrEmployees) {
        hrEmployeesFacade.edit(hrEmployees);
    }

    @Override
    public List<HrDepartments> findAllDepartment() {
        return hrEmployeesFacade.findAllDepartment();
    }

    @Override
    public ArrayList<HrLuTitles> findAllHrLuTitles() {
        return hrEmployeesFacade.findAllHrLuTitles();
    }

    @Override
    public ArrayList<HrJobTypes> findAllJobTitles() {
        return hrEmployeesFacade.findAllJobTitles();
    }

    @Override
    public ArrayList<HrLuEducLevels> findEducationLeves() {
        return hrEmployeesFacade.findEducationLeves();
    }

    @Override
    public HrLuEducLevels findbyluEduLevel(HrLuEducLevels hrLuEducLevels) {
        return hrEmployeesFacade.findbyluEduLevel(hrLuEducLevels);
    }

    @Override
    public ArrayList<HrLuEducTypes> findEducationTypes() {
        return hrEmployeesFacade.findEducationTypes();
    }

    @Override
    public HrLuEducTypes findbyluEduType(HrLuEducTypes hrLuEducTypes) {;
        return hrEmployeesFacade.findbyluEduType(hrLuEducTypes);
    }

    @Override
    public ArrayList<HrLuLanguages> findAllHrLuLanguages() {
        return hrEmployeesFacade.findAllHrLuLanguages();
    }

    @Override
    public void EditSkill(HrEmpSkill hrEmpSkill) {
        hrEmpSkillFacade.edit(hrEmpSkill);
    }

    @Override
    public List<HrJobTypes> listOfJobType(int departmentId) {
        return hrRecruitmentRequestsFacade.listOfJobType(departmentId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HrSalaryScales checkStepIdRep(HrSalaryScaleRanges range, HrLuSalarySteps steps) {
        return hrEmployeesFacade.checkStepIdRep(range, steps);
    }

    @Override
    public ArrayList<HrEmployees> searchEmployeeByName(HrEmployees hrEmployees) {
        return hrEmployeesFacade.searchEmployeeInfo(hrEmployees);
    }

    @Override
    public List<HrEmployees> findActiveEmployees() {
        return hrEmployeesFacade.findActiveEmployees();
    }

    @Override
    public HrEmployees findById(HrEmployees employeeFirst) {
        return hrEmployeesFacade.findById(employeeFirst);
    }
    @Override
    public HrEmployees searchById(HrEmployees hrEmployeesObj) {
        return hrEmployeesFacade.searchIMGById(hrEmployeesObj);
    }
    

//    @Override
//    public ArrayList<HrEmployees> searchByEmpById(HrEmployees employee) {
//    return hrEmployeesFacade.SearchByFname(employee);}
//    @Override
//    public HrEmployees findByName(HrEmployees employeeFirst) {
//    return hrEmployeesFacade.findByName(employeeFirst); }
    @Override
    public ArrayList<HrEmployees> searchByEmpById(HrEmployees employee) {
        return hrEmployeesFacade.searchByEmpById(employee);
    }

    @Override
    public ArrayList<HrEmployees> populateEmpUnderDepartement(HrDepartments hd) {
        return hrEmployeesFacade.populateEmpUnderDepartement(hd);
    }

    @Override
    public void saveOrUpdate(HrEmployees hrEmployees) {
        hrEmployeesFacade.saveOrUpdate(hrEmployees);
    }
       @Override
    public void saveOrUpdate1(HrEmployees hrEmployees) {
        hrEmployeesFacade.saveOrUpdate(hrEmployees);
    }
    //mahi
    
     @Override
    public List<HrLuMemberships> findAllMembershipses() {
        return hrEmployeesFacade.findAllMembershipses();
    }
    
     @Override
    public HrLuMemberships getChangedMembershipesObject(BigDecimal id) {
        return hrEmployeesFacade.getChangedMembershipesObject(id);
    }
     public HrLuLanguages findLanguage(HrLuLanguages hrLuLanguages){
        return hrEmployeesFacade.findLanguage(hrLuLanguages);
    }

    @Override
    public ArrayList<HrEmployees> searchEmployeeById(HrEmployees hrEmployees) {
       return hrEmployeesFacade.searchByEmpById(hrEmployees);
    }

    @Override
    public HrEmployees SearchByEmpId(String empid ,HrEmployees hrEmployees) {
       return hrEmployeesFacade.searchByEmpById(empid,hrEmployees);
    }

    @Override
    public List<HrEmployees> SearchByEmpId(String empid) {
       return hrEmployeesFacade.searchByEmpById(empid);
    }
    
    @Override
    public int saveCandidate(HrCandidiates canidate, HrEmployees employee) {
        return hrEmployeesFacade.saveCandidate(canidate, employee);
        
    }
    @Override
    public List<HrEmployees> FindActiveEmplByDepIdAndJobId(Integer depId, Integer id) {
        return hrEmployeesFacade.FindActiveEmplByDepIdAndJobId(depId, id);
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
       return hrEmployeesFacade.findColumns();
    }

  

    @Override
    public ArrayList<HrEmployees> SearchEmpContract(HrEmployees hrEmployees, String col_Name_FromTable) {
        return hrEmployeesFacade.SearchEmpContract(hrEmployees,col_Name_FromTable);
    }

    

  
}
