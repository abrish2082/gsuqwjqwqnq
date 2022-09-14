/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.employee;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.businessLogic.employee.HrEmpContractsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpContracts;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.lookup.HrLuReligions;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import et.gov.eep.hrms.entity.lookup.HrLuMemberships;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import java.math.BigDecimal;
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
public class HrEmployeesFacade extends AbstractFacade<HrEmployees> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {

        return em;
    }

    /**
     *
     */
    public HrEmployeesFacade() {
        super(HrEmployees.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Native Queries of Bussiness Implementation ">
    /**
     *
     * @param hrEmployees
     * @return
     */
    public ArrayList<HrEmployees> SearchByFname(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike", HrEmployees.class);
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrEmployees
     * @return
     */
    public ArrayList<HrEmployees> SearchByFnameCont(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLikeCont");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        query.setParameter("employmentType", "Contract");
        try {
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmployees> getEmployeesName() {
        Query query = em.createNamedQuery("HrEmployees.findAll", HrEmployees.class);
        try {

            ArrayList<HrEmployees> employeeList = new ArrayList(query.getResultList());
            return employeeList;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }
    }

    public ArrayList<HrEmployees> findEmployeeByDept(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmployees.findByEmpDeptId");
        query.setParameter("deptId", hrEmployees.getDeptId().getDepId());
        try {
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrEmployees
     * @return
     */
    public HrEmployees getByFirstName(HrEmployees hrEmployees) {
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createNamedQuery("HrEmployees.findByFirstName", HrEmployees.class);
        query.setParameter("firstName", hrEmployees.getFirstName());
        try {
            HrEmployees empList = (HrEmployees) (query.getResultList().get(0));
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrEmployees
     * @return
     */
    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByEmpIdLike", HrEmployees.class);
            query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

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
     * @param hrEmployees
     * @return
     */
    public ArrayList<HrEmployees> SearchByEmpIdCont(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByEmpIdLikeCont", HrEmployees.class);
            query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
            query.setParameter("employmentType", "Contract");
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmployees> findByFnameAndEmpId(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByFnameEmpIdLike", HrEmployees.class);
            query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
            query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrEmployees
     * @return
     */
    public HrEmployees getByEmpId(HrEmployees hrEmployees) {
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createNamedQuery("HrEmployees.findByEmpId", HrEmployees.class);
        query.setParameter("empId", hrEmployees.getEmpId());
        try {
            HrEmployees emp = (HrEmployees) (query.getResultList().get(0));
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees getById(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmployees.findById", HrEmployees.class);
        query.setParameter("id", hrEmployees.getId());
        try {
            HrEmployees emp = (HrEmployees) (query.getResultList().get(0));
            System.out.println("------emp FCD2-----" + emp);
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmployees> employeeNames() {
        Query query = em.createNamedQuery("HrEmployees.findAll", HrEmployees.class);
        try {

            ArrayList<HrEmployees> employeeList = new ArrayList(query.getResultList());
            return employeeList;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }
    }

    /**
     *
     * @param hrLuReligion
     * @return
     */
    public HrLuReligions findReligion(HrLuReligions hrLuReligion) {
        Query query = em.createNamedQuery("HrLuReligions.findByReligion");
        query.setParameter("religion", hrLuReligion.getReligion());
        try {
            HrLuReligions relname = (HrLuReligions) query.getResultList().get(0);
            return relname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrDepartments
     * @return
     */
    public HrDepartments findDepartment(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrDepartments.findByDepName", HrDepartments.class);
        query.setParameter("depName", hrDepartments.getDepName());
        try {
            HrDepartments depname = (HrDepartments) query.getResultList().get(0);
            return depname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrLuBanks
     * @return
     */
    public HrLuBanks findhrluBanks(HrLuBanks hrLuBanks) {
        Query query = em.createNamedQuery("HrLuBanks.findByBankName");
        query.setParameter("bankName", hrLuBanks.getBankName());
        try {
            HrLuBanks bankname = (HrLuBanks) query.getResultList().get(0);
            return bankname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrLuBankBranches findBankBranchs(HrLuBankBranches hrLuBankBranches) {
        Query query = em.createNamedQuery("HrLuBankBranches.findByBranchName");
        query.setParameter("branchName", hrLuBankBranches.getBranchName());
        try {
            HrLuBankBranches branch = (HrLuBankBranches) query.getResultList().get(0);
            return branch;
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
    public HrJobTypes findSalarySteps(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitle", HrJobTypes.class);
        query.setParameter("jobTitle", hrJobTypes.getJobTitle());
        try {
            HrJobTypes hrJobType = (HrJobTypes) query.getResultList().get(0);
            return hrJobType;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrLuNationalities
     * @return
     */
    public HrLuNationalities findNationality(HrLuNationalities hrLuNationalities) {
        Query query = em.createNamedQuery("HrLuNationalities.findByNationality");
        query.setParameter("nationality", hrLuNationalities.getNationality());
        try {
            HrLuNationalities Nationalityname = (HrLuNationalities) query.getResultList().get(0);
            return Nationalityname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrLuTitles
     * @return
     */
    public HrLuTitles findTitle(HrLuTitles hrLuTitles) {
        Query query = em.createNamedQuery("HrLuTitles.findByTitle");
        query.setParameter("title", hrLuTitles.getTitle());
        try {
            HrLuTitles Titlename = (HrLuTitles) query.getResultList().get(0);
            return Titlename;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // get AllEmployees
    public List<HrEmployees> findAll() {
        return super.findAll();
    }

    //for sadik
    /**
     *
     * @param employees
     * @return
     */
    public ArrayList<HrEmployees> searchEmployeeInfo(HrEmployees employees) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike", HrEmployees.class);
        query.setParameter("firstName", employees.getFirstName().toUpperCase() + '%');
        // query.setParameter("1", employees.getDeptId());
        try {
            ArrayList<HrEmployees> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param JobTypes
     * @return
     */
    public HrJobTypes searchJobTitleInfo(HrJobTypes JobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitle", HrJobTypes.class);
        query.setParameter("jobTitle", JobTypes.getJobTitle());
        try {
            HrJobTypes jobTypes = (HrJobTypes) (query.getResultList().get(0));

            return jobTypes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrLuGrades
     * @return
     */
    public HrLuGrades searchHrLuGradesInfo(HrLuGrades hrLuGrades) {
        Query query = em.createNamedQuery("HrLuGrades.findByGrade", HrLuGrades.class);
        query.setParameter("grade", hrLuGrades.getGrade());
        try {
            HrLuGrades grade = (HrLuGrades) query.getResultList().get(0);
            return grade;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrLuSalarySteps
     * @return
     */
    public HrLuSalarySteps searchHrLuStepsInfo(HrLuSalarySteps hrLuSalarySteps) {
        Query query = em.createNamedQuery("HrLuSalarySteps.findBySalaryStep", HrLuSalarySteps.class);
        query.setParameter("salaryStep", hrLuSalarySteps.getSalaryStep());
        try {
            HrLuSalarySteps steps = (HrLuSalarySteps) query.getResultList().get(0);
            return steps;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //searchSalaryScaleInfo(hrSalaryScales)
    /**
     *
     * @param hrDepartments
     * @return
     */
    public ArrayList<HrJobTypes> getJobTytleInfo(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrJobTypes.findByDeptId", HrDepartments.class);
        query.setParameter("depId", hrDepartments);
        // query.setParameter("1", employees.getDeptId());
        try {
            ArrayList<HrJobTypes> hrJobTypesInformations = new ArrayList(query.getResultList());
            return hrJobTypesInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrSalaryScaleRanges
     * @return
     */
    public ArrayList<HrSalaryScales> getSalaryScaleInfo(HrSalaryScaleRanges hrSalaryScaleRanges) {
        Query query = em.createNamedQuery("HrSalaryScales.findBySalaryRangeId", HrSalaryScaleRanges.class);
        query.setParameter("salaryRangeId", hrSalaryScaleRanges.getId());
        try {
            ArrayList<HrSalaryScales> hrSalaryScaleInfo = new ArrayList(query.getResultList());
            return hrSalaryScaleInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrLuBanks
     * @return
     */
    public ArrayList<HrLuBankBranches> getBankBranchInfo(HrLuBanks hrLuBanks) {
        Query query = em.createNamedQuery("HrLuBankBranches.findByBankId", HrLuBanks.class);
        query.setParameter("bankId", hrLuBanks.getId());
        try {
            ArrayList<HrLuBankBranches> hrBranchInfo = new ArrayList(query.getResultList());
            return hrBranchInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuGrades> findAllGrade() {
        Query query = em.createNamedQuery("HrLuGrades.findAll");
        try {
            ArrayList<HrLuGrades> grades = new ArrayList(query.getResultList());
            return grades;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuSalarySteps> findAllSteps() {
        Query query = em.createNamedQuery("HrLuSalarySteps.findAll");
        try {
            ArrayList<HrLuSalarySteps> steps = new ArrayList(query.getResultList());
            return steps;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param hrDepartment
     * @return
     */
    public HrDepartments findDepartmentbyName(HrDepartments hrDepartment) {
        Query query = em.createNamedQuery("HrDepartments.findByDepName", HrDepartments.class);
        query.setParameter("depName", hrDepartment.getDepName());
        try {
            System.err.println("dadfa=" + query.getResultList().size());
            HrDepartments deptname = (HrDepartments) query.getResultList().get(0);
            return deptname;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<HrDepartments> findAllDepartment() {
        Query query = em.createNamedQuery("HrDepartments.findAll");
        try {
            ArrayList<HrDepartments> Dept = new ArrayList(query.getResultList());
            return Dept;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuTitles> findAllHrLuTitles() {
        Query query = em.createNamedQuery("HrLuTitles.findAll");
        try {
            ArrayList<HrLuTitles> Title = new ArrayList(query.getResultList());
            return Title;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<HrJobTypes> findAllJobTitles() {
        Query query = em.createNamedQuery("HrJobTypes.findAll");
        try {
            ArrayList<HrJobTypes> Title = new ArrayList(query.getResultList());
            return Title;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param hrSalaryScales
     * @return
     */
    public HrSalaryScales findByRangeAndStep(HrSalaryScales hrSalaryScales) {
        try {
            Query query = em.createNamedQuery("HrSalaryScales.findBySalaryByRangeAndStep", HrSalaryScales.class);
            query.setParameter("salaryRangeId", hrSalaryScales.getSalaryRangeId());
            query.setParameter("salaryRangeId", hrSalaryScales.getSalaryStepId());

            HrSalaryScales salary = (HrSalaryScales) query.getResultList().get(0);
            return salary;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuBanks> findAllBanks() {
        Query query = em.createNamedQuery("HrLuBanks.findAll");
        try {
            ArrayList<HrLuBanks> banks = new ArrayList(query.getResultList());
            return banks;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuEducLevels> findEducationLeves() {
        Query query = em.createNamedQuery("HrLuEducLevels.findAll");
        try {
            ArrayList<HrLuEducLevels> levels = new ArrayList(query.getResultList());
            return levels;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrLuEducLevels findbyluEduLevel(HrLuEducLevels hrLuEducLevels) {
        Query query = em.createNamedQuery("HrLuEducLevels.findByEducLevel", HrLuEducLevels.class);
        query.setParameter("educLevel", hrLuEducLevels.getEducLevel());
        try {
            HrLuEducLevels educLevels = (HrLuEducLevels) query.getResultList().get(0);
            return educLevels;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrLuEducTypes findbyluEduType(HrLuEducTypes hrLuEducTypes) {
        Query query = em.createNamedQuery("HrLuEducTypes.findByEducType", HrLuEducTypes.class);
        query.setParameter("educType", hrLuEducTypes.getEducType());
        try {
            HrLuEducTypes educType = (HrLuEducTypes) query.getResultList().get(0);
            return educType;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuEducTypes> findEducationTypes() {
        Query query = em.createNamedQuery("HrLuEducTypes.findAll");
        try {
            ArrayList<HrLuEducTypes> types = new ArrayList(query.getResultList());
            return types;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuLanguages> findAllHrLuLanguages() {
        Query query = em.createNamedQuery("HrLuLanguages.findAll");
        try {
            ArrayList<HrLuLanguages> list = new ArrayList(query.getResultList());
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrEmployees searchById(HrEmployees employee) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstName");
        query.setParameter("firstName", employee.getFirstName());
        try {
            HrEmployees receiptNoteInfo = (HrEmployees) query.getResultList().get(0);
            return receiptNoteInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param range
     * @param steps
     * @return
     */
    public HrSalaryScales checkStepIdRep(HrSalaryScaleRanges range, HrLuSalarySteps steps) {
        try {
            Query query = em.createQuery("SELECT h FROM HrSalaryScales h where h.salaryRangeId.id=?1 and h.salaryStepId.id=?2", HrLuSalarySteps.class);
            query.setParameter(1, range.getId());
            query.setParameter(2, steps.getId());
            HrSalaryScales salary = (HrSalaryScales) query.getSingleResult();
            return salary;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public HrEmployees checkEmpId(String empId) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByEmpId", HrEmployees.class);
            query.setParameter("empId", empId);
            if (query.getResultList().size() > 0) {
                HrEmployees testEmp = (HrEmployees) query.getResultList().get(0);
                return testEmp;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HrEmployees> findActiveEmployees() {
        Query query = em.createNamedQuery("HrEmployees.findActiveEmployees");

        try {
            ArrayList<HrEmployees> emps = new ArrayList(query.getResultList());
            return emps;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrEmployees findById(HrEmployees empId) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByEmpId");
            query.setParameter("empId", empId.getEmpId());

            return (HrEmployees) query.getSingleResult();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
        // return null;
    }

    public HrEmployees searchIMGById(HrEmployees id) {
        try {
            System.out.println("inside faced");
            Query query = em.createNamedQuery("HrEmployees.findById");
            query.setParameter("id", id.getId());

            return (HrEmployees) query.getSingleResult();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
        // return null;
    }

    public ArrayList<HrEmployees> searchByEmpById(HrEmployees employee) {

        try {
            Query query = em.createNamedQuery("HrEmployees.findByEmpIdLike");
            query.setParameter("empId", employee.getEmpId().toUpperCase() + '%');

            ArrayList<HrEmployees> emps = new ArrayList(query.getResultList());
            return emps;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEmployees> populateEmpUnderDepartement(HrDepartments hd) {
        ArrayList<HrEmployees> employeeInfo = null;
        Query query = em.createNamedQuery("HrEmployees.findByEmpDeptId");
        query.setParameter("deptId", hd.getDepId());
        try {

            employeeInfo = new ArrayList(query.getResultList());
            return employeeInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public List<HrEmployees> findEmployeesByName(String employee) {

        try {
            System.out.println(employee);
            Query query = em.createNamedQuery("HrEmployees.findLikeFirstName");
            query.setParameter("firstName", employee + "%");

            return new ArrayList(query.getResultList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrEmployees findByName(HrEmployees employee) {

        try {

            Query query = em.createNamedQuery("HrEmployees.findByFirstName");
            query.setParameter("firstName", employee.getFirstName());
            HrEmployees empl = (HrEmployees) query.getResultList().get(0);
            return empl;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //mahi
    public ArrayList<HrLuMemberships> findAllMembershipses() {
        Query query = em.createNamedQuery("HrLuMemberships.findAll", HrLuMemberships.class);
        try {

            ArrayList<HrLuMemberships> member = new ArrayList(query.getResultList());
            return member;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public HrLuMemberships getChangedMembershipesObject(BigDecimal id) {
        Query query = em.createNamedQuery("HrLuMemberships.findById", HrLuMemberships.class);
        query.setParameter("id", id);
        try {

            HrLuMemberships member = (HrLuMemberships) query.getSingleResult();
            return member;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public HrLuLanguages findLanguage(HrLuLanguages hrLuLanguages) {
        Query query = em.createNamedQuery("HrLuLanguages.findByLanguageName");
        query.setParameter("languageName", hrLuLanguages.getLanguageName());
        try {
            HrLuLanguages language = (HrLuLanguages) query.getResultList().get(0);
            return language;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public ArrayList<HrEmployees> searchEmployeeById(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmployees.findByFnameEmpIdLike", HrEmployees.class);
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees searchByEmpById(String empid, HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByEmpId");
            query.setParameter("empid", hrEmployees.getEmpId());
            return (HrEmployees) query.getResultList();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> searchByEmpById(String empid) {
        Query query = em.createNativeQuery("select * from hr_employees where hr_employees.emp_id=?", HrEmployees.class);
        query.setParameter("1", empid);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int saveCandidate(HrCandidiates hrCandidate, HrEmployees hrEmployees) {
        int checkSave = 0;
        Query updateQuery = em.createNativeQuery("UPDATE HR_CANDIDIATES SET STATUS=? "
                + " WHERE ID=?");
        try {
            if (hrCandidate != null & hrEmployees != null) {
                updateQuery.setParameter(1, hrCandidate.getStatus());
                updateQuery.setParameter(2, hrCandidate.getId());
                if (updateQuery.executeUpdate() > 0) {
                    this.saveOrUpdate(hrEmployees);
                    checkSave = 1;
                }
            }
            return checkSave;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    HrEmpContractsBeanLocal hrEmpContractsBeanLocal;

    public int saveEmployee(HrEmpContracts hrEmpContracts, HrEmployees hrEmployees) {
        int checkSave = 0;
        try {
            if (hrEmpContracts != null & hrEmployees != null) {
                saveOrUpdate(hrEmployees);
                if (saveOrUpdate(hrEmployees) == hrEmployees) {
                    hrEmpContractsBeanLocal.SaveOrUpdate(hrEmpContracts);
                    checkSave = 1;
                }
            }
            return checkSave;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<HrEmployees> FindActiveEmplByDepIdAndJobId(Integer depId, Integer id) {
        Query query = em.createNamedQuery("HrEmployees.findActiveEmployeesByDeptJobs");
        query.setParameter("depId", depId);
        query.setParameter("jobid", id);
        try {
            ArrayList<HrEmployees> emps = new ArrayList(query.getResultList());
            return emps;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
//</editor-fold>

    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_EMPLOYEES')\n"
                + "and column_name  IN('EMP_ID','FIRST_NAME','MIDDLE_NAME','LAST_NAME','DOB','HIRE_DATE','ACCOUNT_NO') ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            System.out.println("RetrivedColumns" + RetrivedColumns);
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            System.out.println("RetrivedColumns.size()==" + RetrivedColumns.size());
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    System.out.println("RetrivedColumns.get(i)===" + RetrivedColumns.get(i));
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
        System.out.println("col==" + col);
        return col;
    }

    public ArrayList<HrEmployees> SearchEmpContract(HrEmployees hrEmployees, String col_Name_FromTable) {
        System.out.println("col_Name_FromTable==" + col_Name_FromTable);
        System.out.println("hrEmployees.getCol_Value()==" + hrEmployees.getCol_Value());
        if (col_Name_FromTable != null && hrEmployees.getCol_Value() != null) {
           
            Query query = em.createNativeQuery("SELECT * FROM HR_EMPLOYEES\n"
                    + "where " + col_Name_FromTable.toLowerCase() + " LIKE'" + hrEmployees.getCol_Value() + "%' ", HrEmployees.class);
              query.setParameter("employmentType", "Contract");
            try {
                ArrayList<HrEmployees> employeeInformations = new ArrayList(query.getResultList());
                return employeeInformations;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
