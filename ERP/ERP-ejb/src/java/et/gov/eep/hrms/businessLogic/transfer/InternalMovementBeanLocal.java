/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.transfer;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.hrms.entity.transfer.HrInternalMovment;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface InternalMovementBeanLocal {

    public HrDepartments getSelectDepartement(int departmentid);

    public List<HrJobTypes> listOfJobType(int departmentId);

    public HrJobTypes findByName(int jobid);

    public HrLuSalarySteps searchHrLuSalaryStepsInfo(HrLuSalarySteps hrLuSalarySteps);

    public HrSalaryScales checkStepIdRep(HrSalaryScaleRanges range, HrLuSalarySteps steps);

    public HrLuSalarySteps findSalaryStep(HrLuSalarySteps hrLuSalarySteps);

    public ArrayList<HrLuSalarySteps> findAllHrLuSalarySteps();

    public ArrayList<HrSalaryScales> searchSalaryStepsInfo(HrSalaryScaleRanges hrSalaryScaleRanges);

    public void save(HrInternalMovment hrEmpInternalHistory);

    public void create(HrEmpInternalHistory internalHistory);

    public void edit(HrInternalMovment hrEmpInternalHistory);

    public List<String> searchByStatus(Integer hrInternalMovement);

    public HrInternalMovment getInternalMovementInfo(int internalMovement);

    public HrInternalMovment findRequesterById(HrEmployees id);

    public ArrayList<HrInternalMovment> getInternalMovementList();

    public HrInternalMovment getSelectedRequest(int requestid);

    public List<HrInternalMovment> findAllRequest();

    public List<HrInternalMovment> findByEmpId(HrEmployees hrEmployees);

    public ArrayList<HrInternalMovment> findByEmpName(HrEmployees hrEmployees);

    public ArrayList<HrInternalMovment> findByAll(HrEmployees hrEmployees);

    public void saveOrUpdate(HrEmpInternalHistory hrEmpInternalHistory);

    public boolean checkReferenceNo(HrInternalMovment hrInternalMovment);

    public int saveTo(String toDeptId, HrEmployees employeeId, String assignmentId, int processType, String processDate,
            String fromDeptId, String fromDeptName, String fromJobTitleId, String fromJobTitle, String fromGrade, String fromSalaryStep, String fromSalary, 
            int empId, int deptId, int JobId, int gradeId, int salaryStepId, int salaryId, int assignmentType, String referenceNo, String effectiveDate, String remark);

    public List<HrEmpInternalHistory> findAllAssignment();

    public ArrayList<HrEmpInternalHistory> findAssignmentByID(HrEmployees hrEmployees);

    public HrEmpInternalHistory getAssignmentByID(HrEmployees hrEmployees);

    public ArrayList<HrEmpInternalHistory> findAssignmentByName(HrEmployees hrEmployees);

    public HrEmpInternalHistory getAssignmentByName(HrEmployees hrEmployees);

    public ArrayList<HrEmpInternalHistory> findByTwo(HrEmployees hrEmployees);

}
