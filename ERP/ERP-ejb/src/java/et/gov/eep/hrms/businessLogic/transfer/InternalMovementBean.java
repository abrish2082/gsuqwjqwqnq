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
import et.gov.eep.hrms.mapper.transfer.HrEmpInternalHistoryFacade;
import et.gov.eep.hrms.mapper.transfer.HrInternalMovmentFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class InternalMovementBean implements InternalMovementBeanLocal {

    @EJB
    HrInternalMovmentFacade hrEmpInternalHistoryFacade;

    @EJB
    HrEmpInternalHistoryFacade internalHistoryFacade;

    @Override
    public void save(HrInternalMovment hrEmpInternalHistory) {
        hrEmpInternalHistoryFacade.create(hrEmpInternalHistory);
    }

    @Override
    public void create(HrEmpInternalHistory internalHistory) {
        internalHistoryFacade.create(internalHistory);
    }

    @Override
    public void edit(HrInternalMovment hrEmpInternalHistory) {
        hrEmpInternalHistoryFacade.edit(hrEmpInternalHistory);
    }

    @Override
    public HrInternalMovment findRequesterById(HrEmployees id) {
        return hrEmpInternalHistoryFacade.findRequesterById(id);
    }

    @Override
    public HrDepartments getSelectDepartement(int departmentid) {
        return hrEmpInternalHistoryFacade.getSelectDepartement(departmentid);
    }

    @Override
    public List<HrJobTypes> listOfJobType(int departmentId) {
        return hrEmpInternalHistoryFacade.listOfJobType(departmentId);
    }

    @Override
    public HrJobTypes findByName(int jobid) {
        return hrEmpInternalHistoryFacade.findByName(jobid);
    }

    @Override
    public HrLuSalarySteps searchHrLuSalaryStepsInfo(HrLuSalarySteps hrLuSalarySteps) {
        return hrEmpInternalHistoryFacade.searchHrLuStepsInfo(hrLuSalarySteps);
    }

    @Override
    public HrSalaryScales checkStepIdRep(HrSalaryScaleRanges range, HrLuSalarySteps steps) {
        return hrEmpInternalHistoryFacade.checkStepIdRep(range, steps);
    }

    @Override
    public HrLuSalarySteps findSalaryStep(HrLuSalarySteps hrLuSalarySteps) {
        return hrEmpInternalHistoryFacade.findSalaryStep(hrLuSalarySteps);
    }

    @Override
    public ArrayList<HrLuSalarySteps> findAllHrLuSalarySteps() {
        return hrEmpInternalHistoryFacade.findAllHrLuSalarySteps();
    }

    @Override
    public ArrayList<HrSalaryScales> searchSalaryStepsInfo(HrSalaryScaleRanges hrSalaryScaleRanges) {
        return hrEmpInternalHistoryFacade.getSalaryScaleInfo(hrSalaryScaleRanges);
    }

    @Override
    public List<String> searchByStatus(Integer hrInternalMovement) {
        return hrEmpInternalHistoryFacade.searchByStatus(hrInternalMovement);
    }

    @Override
    public HrInternalMovment getInternalMovementInfo(int internalMovement) {
        return hrEmpInternalHistoryFacade.getInternalMovementInfo(internalMovement);
    }

    @Override
    public ArrayList<HrInternalMovment> getInternalMovementList() {
        return hrEmpInternalHistoryFacade.findAll();
    }

    @Override
    public HrInternalMovment getSelectedRequest(int requestid) {
        return hrEmpInternalHistoryFacade.getSelectedRequest(requestid);
    }

    @Override
    public List<HrInternalMovment> findAllRequest() {
        return hrEmpInternalHistoryFacade.findAllRequest();
    }

    @Override
    public List<HrInternalMovment> findByEmpId(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.findByEmpId(hrEmployees);
    }

    @Override
    public ArrayList<HrInternalMovment> findByEmpName(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.findByEmpName(hrEmployees);
    }

    @Override
    public ArrayList<HrInternalMovment> findByAll(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.findByAll(hrEmployees);
    }

    @Override
    public void saveOrUpdate(HrEmpInternalHistory hrEmpInternalHistory) {
        internalHistoryFacade.saveOrUpdate(hrEmpInternalHistory);
    }

    @Override
    public boolean checkReferenceNo(HrInternalMovment hrInternalMovment) {
        return hrEmpInternalHistoryFacade.checkReferenceNo(hrInternalMovment);

    }

    @Override
    public int saveTo(String toDeptId, HrEmployees employeeId,
            String assignmentId, int processType, String processDate, String fromDeptId, String fromDeptName, String fromJobTitleId, String fromJobTitle, String fromGrade, String fromSalaryStep,
            String fromSalary, int empId, int deptId, int JobId, int gradeId, int salaryStepId, int salaryId, int assignmentType, String referenceNo, String effectiveDate, String remark) {
        return hrEmpInternalHistoryFacade.saveTo(toDeptId, employeeId, assignmentId, processType, processDate, fromDeptId, fromDeptName, fromJobTitleId, fromJobTitle,
                fromGrade, fromSalaryStep, fromSalary, empId, deptId, JobId, gradeId, salaryStepId, salaryId, assignmentType, referenceNo, effectiveDate, remark);
    }

    @Override
    public List<HrEmpInternalHistory> findAllAssignment() {
        return hrEmpInternalHistoryFacade.findAllAssignment();
}

    @Override
    public ArrayList<HrEmpInternalHistory> findAssignmentByID(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.findAssignmentByID(hrEmployees);
    }

    @Override
    public HrEmpInternalHistory getAssignmentByID(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.getAssignmentById(hrEmployees);
    }

    @Override
    public ArrayList<HrEmpInternalHistory> findAssignmentByName(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.findAssignmentByName(hrEmployees);
    }

    @Override
    public HrEmpInternalHistory getAssignmentByName(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.getAssignmentByName(hrEmployees);
    }
    @Override
    public ArrayList<HrEmpInternalHistory> findByTwo(HrEmployees hrEmployees) {
        return hrEmpInternalHistoryFacade.findByTwo(hrEmployees);
    }
}
