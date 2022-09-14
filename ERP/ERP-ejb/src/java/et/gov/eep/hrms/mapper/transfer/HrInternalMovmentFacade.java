/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.transfer;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ob
 */
@Stateless
public class HrInternalMovmentFacade extends AbstractFacade<HrInternalMovment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrInternalMovmentFacade() {
        super(HrInternalMovment.class);
    }

    public HrDepartments getSelectDepartement(int departmentid) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId", HrDepartments.class);
        query.setParameter("depId", departmentid);
        try {
            HrDepartments selectdepartment = (HrDepartments) query.getResultList().get(0);
            return selectdepartment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrJobTypes findByName(int jobid) {
        Query query = em.createNamedQuery("HrJobTypes.findById");
        query.setParameter("id", jobid);
        try {
            HrJobTypes j = (HrJobTypes) query.getResultList().get(0);
            return j;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrInternalMovment findRequesterById(HrEmployees id) {
        Query query = em.createNamedQuery("HrInternalMovment.findByEmpId", HrInternalMovment.class);
        query.setParameter("id", id.getId());
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            HrInternalMovment requester = (HrInternalMovment) query.getResultList().get(0);
            return requester;
        }
    }

    public List<HrJobTypes> listOfJobType(int departmentId) {
        String queryStr
                = "SELECT j.id,j.job_code,j.job_title FROM hr_job_types j inner join  hr_dept_jobs d on j.id=d.job_id where d.DEPT_ID=" + departmentId;
        Query query = em.createNativeQuery(queryStr, HrJobTypes.class);
        List<HrJobTypes> results = query.getResultList();
        return results;
    }

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

    public HrLuSalarySteps findSalaryStep(HrLuSalarySteps hrLuSalarySteps) {
        Query query = em.createNamedQuery("HrLuSalarySteps.findBySalaryStep");
        query.setParameter("salaryStep", hrLuSalarySteps.getSalaryStep());
        try {
            HrLuSalarySteps SalaryStep = (HrLuSalarySteps) query.getResultList().get(0);
            return SalaryStep;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrLuSalarySteps> findAllHrLuSalarySteps() {
        Query query = em.createNamedQuery("HrLuSalarySteps.findAll");
        try {
            ArrayList<HrLuSalarySteps> Salary = new ArrayList(query.getResultList());
            return Salary;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

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

    public List<String> searchByStatus(Integer hrInternalMovement) {
        List<String> internalMovementList = null;
        try {
            Query query = em.createNamedQuery("HrInternalMovment.findByStatus");
            query.setParameter("status", hrInternalMovement);
            internalMovementList = (List<String>) query.getResultList();
            return internalMovementList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrInternalMovment getInternalMovementInfo(int internalMovement) {
        Query query = em.createNamedQuery("HrInternalMovment.findById");
        query.setParameter("id", internalMovement);
        try {
            HrInternalMovment movementInfo = (HrInternalMovment) query.getResultList().get(0);
            return movementInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<HrInternalMovment> findAll() {
        Query query = em.createNamedQuery("HrInternalMovment.findAll");
        try {
            ArrayList<HrInternalMovment> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HrInternalMovment getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrInternalMovment.findById", HrInternalMovment.class);
        query.setParameter("id", request);
        try {
            HrInternalMovment selectrequest = (HrInternalMovment) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrInternalMovment> findAllRequest() {
        Query query = em.createNamedQuery("HrInternalMovment.findAllRequest", HrInternalMovment.class);
        try {
            ArrayList<HrInternalMovment> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrInternalMovment> findByEmpId(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrInternalMovment.findByEmployeeId", HrInternalMovment.class);
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrInternalMovment> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrInternalMovment> findByEmpName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrInternalMovment.findByName", HrInternalMovment.class);
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrInternalMovment> request = new ArrayList<>(query.getResultList());
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrInternalMovment> findByAll(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrInternalMovment.findByTwo", HrInternalMovment.class);
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrInternalMovment> request = new ArrayList<>(query.getResultList());
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean checkReferenceNo(HrInternalMovment hrInternalMovment) {
        boolean isExist;
        Query query = em.createNamedQuery("HrInternalMovment.findByReferenceNo");
        query.setParameter("referenceNo", hrInternalMovment.getReferenceNo());
        try {
            if (query.getResultList().size() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }

    public int saveTo(String toDeptId, HrEmployees employeeId,//Employee
            String assignmentId, int processType, String processDate, String fromDeptId, String fromDeptName, String fromJobTitleId, String fromJobTitle,//Internal history
            String fromGrade, String fromSalaryStep, String fromSalary,//Internal history
            int empId, int deptId, int JobId, int gradeId, int salaryStepId, int salaryId, int assignmentType, String referenceNo, String effectiveDate, String remark) {//Assignment
        int checkAllSave = 0;
        Query updateEmployee = em.createNativeQuery("UPDATE HR_EMPLOYEES "
                + " SET DEPT_ID ='" + toDeptId + "'"
                + " WHERE ID ='" + employeeId + "'");
        Query insertToHistory = em.createNativeQuery("INSERT INTO HR_EMP_INTERNAL_HISTORY "
                + " ("
                + " EMP_ID,PROCESS_ID,PROCESS_TYPE,PROCESS_DATE"
                + " OLD_DEP_ID,OLD_DEP_NAME,OLD_JOB_ID,OLD_JOB_NAME,OLD_GRADE,OLD_SALARY_STEP,OLD_SALARY"
                + " ) "
                + " VALUES"
                + " ("
                + " '" + employeeId + "',"
                + " '" + assignmentId + "',"
                + " '" + processType + "', '" + processDate + "',"
                + " '" + fromDeptId + "', '" + fromDeptName + "',"
                + " '" + fromJobTitleId + "', '" + fromJobTitle + "',"
                + " '" + fromGrade + "', '" + fromSalaryStep + "',"
                + " '" + fromSalary + "'"
                + " )");
//        Query insertToAssignment = em.createNativeQuery("INSERT INTO HR_INTERNAL_MOVMENT "
//                + " ("
//                + " EMP_ID,DEP_ID,JOB_ID,GRADE_ID,SALARY_STEP_ID,SALARY_ID"
//                + " PROCESS_TYPE,REFERENCE_NO,PROCESS_DATE,REMARK"
//                + " ) "
//                + " VALUES"
//                + " ("
//                + " '" + employeeId + "',"
//                + " '" + deptId + "', '" + JobId + "',"
//                + " '" + gradeId + "', '" + salaryStepId + "',"
//                + " '" + salaryId + "', '" + assignmentType + "',"
//                + " '" + referenceNo + "', '" + effectiveDate + "',"
//                + " '" + remark + "'"
//                + " )");
        try {
            if (updateEmployee.executeUpdate() > 0) {
                if (insertToHistory.executeUpdate() > 0) {
                    checkAllSave = 1;
//                    if (insertToAssignment.executeUpdate() > 0) {
//                        checkAllSave = 1;
//                    }
                }
            }
            return checkAllSave;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public ArrayList<HrEmpInternalHistory> findAllAssignment() {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAllAssignment", HrEmpInternalHistory.class);
        try {
            ArrayList<HrEmpInternalHistory> assignmentHis = new ArrayList<>(query.getResultList());
            return assignmentHis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEmpInternalHistory> findAssignmentByID(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAssignmentByIdLike", HrEmpInternalHistory.class);
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrEmpInternalHistory> findEmpId = new ArrayList(query.getResultList());
            return findEmpId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmpInternalHistory getAssignmentById(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAssignmentById", HrEmpInternalHistory.class);
        query.setParameter("empId", hrEmployees.getEmpId());
        try {
            HrEmpInternalHistory getEmpId = (HrEmpInternalHistory) (query.getResultList().get(0));
            return getEmpId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmpInternalHistory> findAssignmentByName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAssignmentByNameLike");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEmpInternalHistory> findEmpName = new ArrayList(query.getResultList());
            return findEmpName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmpInternalHistory getAssignmentByName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAssignmentByName");
        query.setParameter("firstName", hrEmployees.getFirstName());
        try {
            HrEmpInternalHistory getEmpName = (HrEmpInternalHistory) (query.getResultList().get(0));
            return getEmpName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmpInternalHistory> findByTwo(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findByIdAndName", HrEmpInternalHistory.class);
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEmpInternalHistory> emp = new ArrayList<>(query.getResultList());
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
