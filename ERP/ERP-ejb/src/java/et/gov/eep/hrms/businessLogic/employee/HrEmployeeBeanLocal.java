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
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.lookup.HrLuReligions;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrEmployeeBeanLocal {

    public void save(HrEmployees hrEmployees);

    List<HrEmployees> findAll();

    public void Delete(HrEmployees hrEmployees);

    public void edit(HrEmployees hrEmployees);

    public void EditEmpFile(HrEmployees hrEmployees);

    public ArrayList<HrEmployees> SearchByFname(HrEmployees hrEmployees);
     public ArrayList<HrEmployees> SearchByFnameCont(HrEmployees hrEmployees);

    public ArrayList<HrEmployees> findEmployeeByDept(HrEmployees hrEmployees);

    public HrEmployees getByFirstName(HrEmployees hrEmployees);

    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees);
     public ArrayList<HrEmployees> SearchByEmpIdCont(HrEmployees hrEmployees);

//    public ArrayList<HrEmployees> findByFnameDept(HrEmployees hrEmployees);
    public ArrayList<HrEmployees> findByFnameAndEmpId(HrEmployees hrEmployees);

    public HrEmployees getByEmpId(HrEmployees hrEmployees);
    
    public HrEmployees getById(HrEmployees hrEmployees);

    public HrLuReligions findReligion(HrLuReligions hrLuReligion);

    public HrDepartments findDepartment(HrDepartments hrDepartments);

    public HrLuBanks findBanks(HrLuBanks hrLuBanks);

    public HrLuBankBranches findBankBranchs(HrLuBankBranches hrLuBankBranches);

    public HrLuNationalities findNationality(HrLuNationalities hrLuNationalities);

    public HrLuTitles findTitles(HrLuTitles hrLuTitles);

    HrJobTypes searchJobTitleInfo(HrJobTypes JobTypes);

    public ArrayList<HrJobTypes> searchJobInfo(HrDepartments hrDepartments);

    public ArrayList<HrLuBankBranches> searchBankBranchInfo(HrLuBanks HrLuBanks);

    public ArrayList<HrSalaryScales> searchSalaryStepsInfo(HrSalaryScaleRanges hrSalaryScaleRanges);

    public HrLuGrades searchHrLuGradesInfo(HrLuGrades hrLuGrades);

    public List<HrLuGrades> findAllGrades();

    public HrLuSalarySteps searchHrLuSalaryStepsInfo(HrLuSalarySteps hrLuSalarySteps);

    public List<HrLuSalarySteps> findAllSteps();

    public HrDepartments findDepartmentbyName(HrDepartments hrDepartment);

    public ArrayList<HrDepartments> getDepartementList();

    public HrSalaryScales findByRangeAndStep(HrSalaryScales hrSalaryScales);

    public ArrayList<HrLuBanks> findAllBanks();

    public List<HrDepartments> findAllDepartment();

    public ArrayList<HrLuTitles> findAllHrLuTitles();

    public ArrayList<HrJobTypes> findAllJobTitles();

    public ArrayList<HrLuLanguages> findAllHrLuLanguages();

    public ArrayList<HrLuEducLevels> findEducationLeves();

    public HrLuEducLevels findbyluEduLevel(HrLuEducLevels hrLuEducLevels);

    public ArrayList<HrLuEducTypes> findEducationTypes();

    public HrLuEducTypes findbyluEduType(HrLuEducTypes hrLuEducTypes);

    public void EditSkill(HrEmpSkill hrEmpSkill);

    List<HrJobTypes> listOfJobType(int departmentId);

    public HrSalaryScales checkStepIdRep(HrSalaryScaleRanges range, HrLuSalarySteps steps);

    public HrEmployees checkEmpId(String empId);

    public ArrayList<HrEmployees> searchEmployeeByName(HrEmployees hrEmployees);

    public List<HrEmployees> findActiveEmployees();

    public HrEmployees findById(HrEmployees employeeFirst);
    
     public HrEmployees searchById(HrEmployees hrEmployeesObj);
    

    //public ArrayList<HrEmployees> searchByEmpById(HrEmployees employee);

   //public HrEmployees findByName(HrEmployees employeeFirst);

    public ArrayList<HrEmployees> searchByEmpById(HrEmployees employee);

    public ArrayList<HrEmployees> populateEmpUnderDepartement(HrDepartments hd);

    public void saveOrUpdate(HrEmployees hrEmployees);
    public void saveOrUpdate1(HrEmployees hrEmployees);
    
    public List<HrLuMemberships> findAllMembershipses();
    
     public HrLuMemberships getChangedMembershipesObject(BigDecimal id);
     public HrLuLanguages findLanguage(HrLuLanguages hrLuLanguages);

    public ArrayList<HrEmployees> searchEmployeeById(HrEmployees hrEmployees);

    public HrEmployees SearchByEmpId(String empid,HrEmployees hrEmployees);

    public List<HrEmployees> SearchByEmpId(String empid);

    public int saveCandidate(HrCandidiates canidate, HrEmployees employee);
    
    public List<HrEmployees> FindActiveEmplByDepIdAndJobId(Integer depId, Integer id);

    public List<ColumnNameResolver> findColumns();

    public  ArrayList<HrEmployees> SearchEmpContract(HrEmployees hrEmployees, String col_Name_FromTable);
}
