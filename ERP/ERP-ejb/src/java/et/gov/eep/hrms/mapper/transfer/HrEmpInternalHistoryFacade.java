/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.transfer;

//import et.gov.eep.commonApplications.JsfUtil;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class HrEmpInternalHistoryFacade extends AbstractFacade<HrEmpInternalHistory> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEmpInternalHistoryFacade() {
        super(HrEmpInternalHistory.class);
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

    public HrEmpInternalHistory findRequesterById(HrEmployees id) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findByEmpId", HrEmpInternalHistory.class);
        query.setParameter("id", id.getId());
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            HrEmpInternalHistory requester = (HrEmpInternalHistory) query.getResultList().get(0);
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
            System.out.println("----- salary is from Mapper -----");
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
            Query query = em.createNamedQuery("HrEmpInternalHistory.findByStatus");
            query.setParameter("status", hrInternalMovement);
            internalMovementList = (List<String>) query.getResultList();
            return internalMovementList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmpInternalHistory getInternalMovementInfo(int internalMovement) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findById");
        query.setParameter("id", internalMovement);
        try {
            HrEmpInternalHistory movementInfo = (HrEmpInternalHistory) query.getResultList().get(0);
            return movementInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<HrEmpInternalHistory> findAll() {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAll");
        try {
            ArrayList<HrEmpInternalHistory> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HrEmpInternalHistory getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findById", HrEmpInternalHistory.class);
        query.setParameter("id", request);
        try {
            HrEmpInternalHistory selectrequest = (HrEmpInternalHistory) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmpInternalHistory> findAllRequest() {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAllRequest", HrEmpInternalHistory.class);
        try {
            ArrayList<HrEmpInternalHistory> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEmpInternalHistory> findByEmpId(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findByEmployeeId", HrEmpInternalHistory.class);
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrEmpInternalHistory> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmpInternalHistory> findByEmpName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findByName", HrEmpInternalHistory.class);
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEmpInternalHistory> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrEmpInternalHistory> findByAll(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findByTwo", HrEmpInternalHistory.class);
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEmpInternalHistory> request = new ArrayList<>(query.getResultList());
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

    public HrEmpInternalHistory findByEmpIdAndBudgerYear(int empId, String recordDate, String toDayInEc) {
    
  
        Query query = em.createNativeQuery("select empHi.ID,empHi.EMP_ID,empHi.OLD_SALARY,empHi.PROCESS_DATE,empHi.OLD_SALARY_STEP from HR_EMP_INTERNAL_HISTORY empHi  \n"
                + " WHERE empHi.EMP_ID='" +empId + "' \n"
                + "         and  TO_DATE(empHi.PROCESS_DATE,'DD/MM/YYYY') BETWEEN TO_DATE('" + recordDate + "', 'DD/MM/YYYY')\n"
                + "             AND TO_DATE('" + toDayInEc + "', 'YYYY-MM-DD')", HrEmpInternalHistory.class);

        try {
            HrEmpInternalHistory hrEmpInternalHistory= new HrEmpInternalHistory();
            if(query.getResultList()!=null){
             hrEmpInternalHistory = (HrEmpInternalHistory) (query.getSingleResult());
            }
            return hrEmpInternalHistory;
            
        } catch (NoResultException  ex) {
            return null;
        }

    }

    public List<HrEmpInternalHistory> findByEmpLBudgerYear(String RecordDate, String toDayInEc) {
       System.out.println("currentDate==" + toDayInEc);
        System.out.println("recordDate==" + RecordDate);
//        Query query = em.createNativeQuery("select empHi.ID,empHi.EMP_ID,empHi.OLD_SALARY,empHi.PROCESS_DATE,empHi.OLD_SALARY_STEP from HR_EMPLOYEES emp INNER JOIN HR_EMP_INTERNAL_HISTORY "
//                + "empHi on emp.ID =empHi.EMP_ID WHERE emp.EMP_ID='"+ empId +"' "
//                + "and  TO_DATE(empHi.PROCESS_DATE,'DD/MM/YYYY') BETWEEN TO_DATE('"+ recordDate +"', 'DD/MM/YYYY')\n"
//                + "AND TO_DATE('"+ toDayInEc +"', 'YYYY-MM-DD')", HrEmpInternalHistory.class);
        Query query = em.createNamedQuery("select empHi.ID,empHi.EMP_ID,empHi.OLD_SALARY,empHi.PROCESS_DATE,empHi.OLD_SALARY_STEP from  HR_EMP_INTERNAL_HISTORY empHi\n"
                + "WHERE TO_DATE(empHi.PROCESS_DATE,'DD/MM/YYYY') BETWEEN TO_DATE('" + RecordDate + "', 'DD/MM/YYYY')\n"
                + "             AND TO_DATE('" + toDayInEc + "', 'YYYY-MM-DD')");

    
            System.out.println("query.getResultList()===+" + query.getResultList());
             ArrayList<HrEmpInternalHistory> request = new ArrayList<>(query.getResultList());
            return request;
        }
        
        
    

    public List<HrEmpInternalHistory> findByEmpId(HrEmpInternalHistory hrEmpInternalHistory, String empID) {
        Query query = em.createNamedQuery("HrEmpInternalHistory.findAllByEmpId", HrEmpInternalHistory.class);
        query.setParameter("empId", empID);
        try {
            ArrayList<HrEmpInternalHistory> request = new ArrayList<>(query.getResultList());
            if ((query.getResultList().isEmpty()) || (query.getResultList() == null)) {
                request = new ArrayList<>();
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return request = new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
