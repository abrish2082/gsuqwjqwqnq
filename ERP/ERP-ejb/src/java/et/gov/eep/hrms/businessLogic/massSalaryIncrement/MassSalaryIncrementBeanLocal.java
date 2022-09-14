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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Behailu
 */
@Local
public interface MassSalaryIncrementBeanLocal {

    public List<HrLuGrades> readAllJobGrades();

    public List<HrLuSalarySteps> readAllSalarySteps();

    public List<HrSalaryIncrements> readSalaryIncrementRequests();

    public List<Object[]> readEmployees(String depId, String jobCode, String gradeId,
            String stepNo, String employmentType, int salaryIncrementId,
            int status, int isLeft);

    public Double readsalary(int gradeId, int stepId);

    public boolean saveMassSalaryIncrementDetail(int salIncId, ArrayList<HrSalaryIncrementDetails> empList);

    public boolean approveMassSalaryIncrementDetail(int salIncId, int processType, ArrayList<HrSalaryIncrementDetails> empList);

    public void save(HrSalaryIncrements hrSalaryIncrements);

    public List<HrEmployees> findallEmp(HrEmployees hrEmployees);

    public HrJobTypes findByJobId(HrJobTypes hrJobTypes);

    public ArrayList<HrEmployees> searchEmp(String hrEmployee);

    public HrEmployees getByFName(HrEmployees hrEmployees);

    public List<HrEmployees> findEmployeeBySalIncId(HrSalaryIncrements salaryIncrementId);

    public List<HrEmployees> findEmployeeBySalIncIdAndDeptId(HrSalaryIncrements salaryIncrementId, Integer depId);

    public List<HrEmployees> findEmployeeBySalIncIdAndDeptIdAndJobId(HrSalaryIncrements salaryIncrementId, Integer depId, Integer id);

    public List<HrEmployees> findEmployeeBySalIncIdAndDeptIdAndJobIdAndStep(HrSalaryIncrements salaryIncrementId, Integer depId, Integer id, HrLuSalarySteps hrLuSalarySteps);

    public List<HrEmployees> findEmployeeBySalIncIdAndStep(HrSalaryIncrements salaryIncrementId, Integer id);

    public List<HrEmployees> findEmployeeBySalIncIdAndGrade(HrSalaryIncrements salaryIncrementId, BigDecimal id);

    public List<HrEmployees> findEmployeeBySalIncIdAndGradeandStep(HrSalaryIncrements salaryIncrementId, HrLuGrades hrLuGrades, HrLuSalarySteps hrLuSalarySteps);

   public List<HrSalaryIncrements> findAllrequests();

}
