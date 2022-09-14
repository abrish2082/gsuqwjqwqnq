/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.massSalaryIncrement;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrementDetails;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrements;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.massSalaryIncrement.HrSalaryIncrementsFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Behailu
 */
@Stateless
public class MassSalaryIncrementBean implements MassSalaryIncrementBeanLocal {

    @EJB
    HrSalaryIncrementsFacade hrSalaryIncrementsFacade;

    //<editor-fold defaultstate="collapsed" desc="Interface implmentations">
    @Override
    public List<HrLuGrades> readAllJobGrades() {
        return hrSalaryIncrementsFacade.readAllJobGrades();
    }
    
    @Override
    public List<HrLuSalarySteps> readAllSalarySteps() {
        return hrSalaryIncrementsFacade.readAllSalarySteps();
    }
    
    @Override
    public List<HrSalaryIncrements> readSalaryIncrementRequests() {
        return hrSalaryIncrementsFacade.readSalaryIncrementRequests();
    }
    
    @Override
    public List<Object[]> readEmployees(String depId, String jobCode,
            String gradeId, String stepNo, String employmentType, int salaryIncrementId,
            int status, int isLeft) {
        return hrSalaryIncrementsFacade.readEmployees(depId, jobCode, gradeId,
                stepNo, employmentType, salaryIncrementId, status, isLeft);
    }
    
    @Override
    public Double readsalary(int gradeId, int stepId) {
        return hrSalaryIncrementsFacade.readsalary(gradeId, stepId);
    }
    
    @Override
    public boolean saveMassSalaryIncrementDetail(int salIncId, ArrayList<HrSalaryIncrementDetails> empList) {
        try {
            return hrSalaryIncrementsFacade.saveMassSalaryIncrementDetail(salIncId, empList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean approveMassSalaryIncrementDetail(int salIncId, int processType, ArrayList<HrSalaryIncrementDetails> empList) {
        try {
            return hrSalaryIncrementsFacade.approveMassSalaryIncrementDetail(salIncId, processType, empList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void save(HrSalaryIncrements hrSalaryIncrements) {
        hrSalaryIncrementsFacade.create(hrSalaryIncrements);
    }
    
    @Override
    public List<HrEmployees> findallEmp(HrEmployees hrEmployees) {
        return hrSalaryIncrementsFacade.findallEmp(hrEmployees);
    }
    
    @Override
    public HrJobTypes findByJobId(HrJobTypes hrJobTypes) {
        return hrSalaryIncrementsFacade.findByJobId(hrJobTypes);
    }
    
    @Override
    public ArrayList<HrEmployees> searchEmp(String hrEmployee) {
        return hrSalaryIncrementsFacade.searchEmp(hrEmployee);
    }
    
    @Override
    public HrEmployees getByFName(HrEmployees hrEmployees) {
        return hrSalaryIncrementsFacade.getByFName(hrEmployees);
    }
    
    @Override
    public List<HrEmployees> findEmployeeBySalIncId(HrSalaryIncrements salaryIncrementId) {
        return hrSalaryIncrementsFacade.findEmployeeBySalIncId(salaryIncrementId);
    }
    
    @Override
    public List<HrEmployees> findEmployeeBySalIncIdAndDeptId(HrSalaryIncrements salaryIncrementId, Integer depId) {
        return hrSalaryIncrementsFacade.findEmployeeBySalIncIdAndDeptId(salaryIncrementId,depId);
    }
    
    @Override
    public List<HrEmployees> findEmployeeBySalIncIdAndDeptIdAndJobId(HrSalaryIncrements salaryIncrementId, Integer depId, Integer id) {
        return hrSalaryIncrementsFacade.findEmployeeBySalIncIdAndDeptIdAndJobId(salaryIncrementId,depId,id);
    }
    
    @Override
    public List<HrEmployees> findEmployeeBySalIncIdAndDeptIdAndJobIdAndStep(HrSalaryIncrements salaryIncrementId, Integer depId, Integer id, HrLuSalarySteps hrLuSalarySteps) {
        return hrSalaryIncrementsFacade.findEmployeeBySalIncIdAndDeptIdAndJobIdAndStep(salaryIncrementId,depId,id,hrLuSalarySteps);
    }
    
    @Override
    public List<HrEmployees> findEmployeeBySalIncIdAndStep(HrSalaryIncrements salaryIncrementId, Integer id) {
        return hrSalaryIncrementsFacade.findEmployeeBySalIncIdAndStep(salaryIncrementId,id);
    }
    
    @Override
    public List<HrEmployees> findEmployeeBySalIncIdAndGrade(HrSalaryIncrements salaryIncrementId, BigDecimal id) {
        return hrSalaryIncrementsFacade.findEmployeeBySalIncIdAndGrade(salaryIncrementId,id);
    }
    
    @Override
    public List<HrEmployees> findEmployeeBySalIncIdAndGradeandStep(HrSalaryIncrements salaryIncrementId, HrLuGrades hrLuGrades, HrLuSalarySteps hrLuSalarySteps) {
        return hrSalaryIncrementsFacade.findEmployeeBySalIncIdAndGradeandStep(salaryIncrementId,hrLuGrades,hrLuSalarySteps);
    }
    
    @Override
    public List<HrSalaryIncrements> findAllrequests() {
        return hrSalaryIncrementsFacade.findAllrequests();
    }
//</editor-fold>
}
