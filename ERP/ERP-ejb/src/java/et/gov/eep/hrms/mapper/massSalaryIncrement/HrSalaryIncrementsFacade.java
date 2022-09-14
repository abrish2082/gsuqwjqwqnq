/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.massSalaryIncrement;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrementDetails;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrements;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrSalaryIncrementsFacade extends AbstractFacade<HrSalaryIncrements> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSalaryIncrementsFacade() {
        super(HrSalaryIncrements.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness Immplementation">
    public List<HrLuGrades> readAllJobGrades() {
        Query query = em.createNativeQuery("SELECT HR_LU_GRADES.* "
                + " FROM HR_LU_GRADES "
                + " INNER JOIN HR_SALARY_SCALE_RANGES"
                + " ON HR_LU_GRADES.ID = HR_SALARY_SCALE_RANGES.GRADE_ID "
                + " ORDER BY HR_SALARY_SCALE_RANGES.LEVEL_ID", HrLuGrades.class);
        try {
            return (List<HrLuGrades>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrLuSalarySteps> readAllSalarySteps() {
        Query query = em.createNamedQuery("HrLuSalarySteps.findAll");
        try {
            List<HrLuSalarySteps> allSalarySteps = (List<HrLuSalarySteps>) query.getResultList();
            return allSalarySteps;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrSalaryIncrements> readSalaryIncrementRequests() {
        Query query = em.createNamedQuery("HrSalaryIncrements.findAll");
        try {
            List<HrSalaryIncrements> salaryIncrementRequests = (List<HrSalaryIncrements>) query.getResultList();
            return salaryIncrementRequests;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrEmployees> findallEmp(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmployees.findAll");
        try {
            List<HrEmployees> allemployees = (List<HrEmployees>) query.getResultList();
            return allemployees;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public HrJobTypes findByJobId(HrJobTypes hrJobTypes) {
        
        Query query = em.createNamedQuery("HrJobTypes.findById", HrJobTypes.class);
        query.setParameter("id", hrJobTypes.getId());
        try {
            System.out.println("this is from mapper-----------------------" + (HrJobTypes) query.getResultList());
            return (HrJobTypes) query.getResultList();
            
        } catch (Exception ex) {
            
            return null;
        }
        
    }
    
    public ArrayList<HrEmployees> searchEmp(String hrEmployee) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike", HrEmployees.class);
        query.setParameter("firstName", hrEmployee.toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public HrEmployees getByFName(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByFirstName");
            query.setParameter("firstName", hrEmployees.getFirstName());
            HrEmployees empList = (HrEmployees) (query.getResultList().get(0));
            return empList;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<Object[]> readEmployees(String depId, String jobCode,
            String gradeId, String stepNo, String employmentType, int salIncId,
            int status, int isLeft) {
        System.out.println("depid===" + depId);
        System.out.println("jobId===" + jobCode);
        System.out.println("grade ID===" + gradeId);
        System.out.println("stepId===" + stepNo);
        try {
            StoredProcedureQuery query = em.createStoredProcedureQuery("HR_MASS_SAL_INC_SELECT_EMP_PRC");
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(9, HrEmployees.class, ParameterMode.REF_CURSOR);
            query.setParameter(1, depId);
            query.setParameter(2, jobCode);
            query.setParameter(3, gradeId);
            query.setParameter(4, stepNo);
            query.setParameter(5, employmentType);
            query.setParameter(6, salIncId);
            query.setParameter(7, status);
            query.setParameter(8, isLeft);
            query.execute();
            List<Object[]> result = (List<Object[]>) query.getOutputParameterValue(9);
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public Double readsalary(int gradeId, int stepId) {
        Double salary = 0.0;
        Query selectSalary = em.createNativeQuery("SELECT HR_SALARY_SCALES.* "
                + " FROM HR_SALARY_SCALE_RANGES "
                + " INNER JOIN HR_SALARY_SCALES "
                + " ON HR_SALARY_SCALE_RANGES.ID = HR_SALARY_SCALES.SALARY_RANGE_ID "
                + " WHERE HR_SALARY_SCALE_RANGES.GRADE_ID = ? "
                + " AND HR_SALARY_SCALES.SALARY_STEP_ID = ?", HrSalaryScales.class);
        
        Query selectInitialSalary = em.createNativeQuery("SELECT HR_SALARY_SCALE_RANGES.* "
                + " FROM HR_SALARY_SCALE_RANGES "
                + " WHERE HR_SALARY_SCALE_RANGES.GRADE_ID = ?", HrSalaryScaleRanges.class);
        try {
            if (stepId == 0) {
                selectInitialSalary.setParameter(1, gradeId);
                List e = selectInitialSalary.getResultList();
                HrSalaryScaleRanges hrSalaryScaleRanges;
                if (!e.isEmpty()) {
                    hrSalaryScaleRanges = (HrSalaryScaleRanges) e.get(0);
                    salary = hrSalaryScaleRanges.getMinSalary();
                }
            } else {
                selectSalary.setParameter(1, gradeId);
                selectSalary.setParameter(2, stepId);
                HrSalaryScales hrSalaryScales;
                List e = selectSalary.getResultList();
                if (!e.isEmpty()) {
                    hrSalaryScales = (HrSalaryScales) e.get(0);
                    salary = hrSalaryScales.getSalary();
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return salary;
    }
    
    public static Connection getOracleConnection() throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@172.20.0.243:1521:eepdb";
        String username = "eep_erp";
        String password = "eep_erp";
        
        Class.forName(driver); // load Oracle driver
        
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Got Connection." + conn);
        
        return conn;
    }
    
    public boolean saveMassSalaryIncrementDetail(int salIncId, ArrayList<HrSalaryIncrementDetails> empList) throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@172.20.0.243:1521:eepdb";
        String username = "eep_erp";
        String password = "eep_erp";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        CallableStatement callStmt = null;
        int i = 0;
        try {
            System.out.println("connection created" + conn);
            callStmt = (CallableStatement) conn.prepareCall("{call HR_MASS_SAL_INC_SAVE_PRC(?,?)}");
            StructDescriptor structDescriptor = StructDescriptor.createDescriptor("EMPLOYEE_OBJ", conn);
            STRUCT[] empArray = new STRUCT[empList.size()];
            for (HrSalaryIncrementDetails entity : empList) {
                Object[] itemAtributes = new Object[]{
                    entity.getId(),
                    entity.getEmpId(),
                    entity.getIncrementBy(),
                    entity.getNewSalaryStep(),
                    entity.getNewSalary(),
                    entity.getStatus()};
                System.out.println("entity.getId()" + entity.getId());
                System.out.println("entity.getEmpId()" +entity.getEmpId());
                System.out.println(" entity.getIncrementBy()" +  entity.getIncrementBy());
                
                empArray[i++] = new STRUCT(structDescriptor, conn, itemAtributes);
            }
            ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("EMPLOYEE_OBJ_ARRAY", conn);
            ARRAY array_to_pass = new ARRAY(arrayDescriptor, conn, empArray);
            System.out.println("salIncId=="+salIncId);
            System.out.println("array_to_pass=="+array_to_pass);
            callStmt.setInt(1, salIncId);
            callStmt.setArray(2, array_to_pass);
            
            int checkSave = callStmt.executeUpdate();
            System.out.println("checkSave==="+checkSave);
            if (checkSave < 1) {
                System.out.println("checkSave==false=");
                return false;
            }
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    System.out.println("Rollback failed.");
                }
            }
            throw e;
        } finally {
            callStmt.close();
            conn.close();
            System.out.println("connection closed." + conn);
        }
    }
    
    public boolean approveMassSalaryIncrementDetail(int salIncId, int processType, ArrayList<HrSalaryIncrementDetails> empList) throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@172.20.0.243:1521:eepdb";
        String username = "eep_erp";
        String password = "eep_erp";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        CallableStatement callStmt = null;
        int i = 0;
        try {
            System.out.println("salIncId=="+salIncId);
            System.out.println("processType=="+processType);
            System.out.println("empList=="+empList.size());
            System.out.println("connection created" + conn);
            callStmt = (CallableStatement) conn.prepareCall("{call HR_MASS_SAL_INC_APPROVE(?,?,?)}");
            StructDescriptor structDescriptor = StructDescriptor.createDescriptor("EMPLOYEE_OBJ", conn);
            STRUCT[] empArray = new STRUCT[empList.size()];
            for (HrSalaryIncrementDetails entity : empList) {
                Object[] itemAtributes = new Object[]{
                    entity.getId(),
                    entity.getEmpId(),
                    entity.getIncrementBy(),
                    entity.getNewSalaryStep(),
                    entity.getNewSalary(),
                    entity.getStatus()};
                System.out.println("entity.getId()" + entity.getId());
                empArray[i++] = new STRUCT(structDescriptor, conn, itemAtributes);
            }
            ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("EMPLOYEE_OBJ_ARRAY", conn);
            ARRAY array_to_pass = new ARRAY(arrayDescriptor, conn, empArray);
            callStmt.setInt(1, salIncId);
            callStmt.setInt(2, processType);
            callStmt.setArray(3, array_to_pass);
            int checkSave = callStmt.executeUpdate();
            if (checkSave < 1) {
                return false;
            }
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    System.out.println("Rollback failed.");
                }
            }
            throw e;
        } finally {
            callStmt.close();
            conn.close();
            System.out.println("connection closed." + conn);
        }
    }
    
    public List<HrEmployees> findEmployeeBySalIncId(HrSalaryIncrements salaryIncrementId) {
        Query query = em.createNativeQuery(" select *  from HR_EMPLOYEES emp Inner join HR_SALARY_INCREMENT_DETAILS salInc \n"
                + "              on emp.ID=salinc.emp_id\n"
                + "              where salinc.salary_increment_id='" + salaryIncrementId + "'", HrEmployees.class);
        List<HrEmployees> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrEmployees> findEmployeeBySalIncIdAndDeptId(HrSalaryIncrements salaryIncrementId, Integer depId) {
        Query query = em.createNativeQuery(" select *  from HR_EMPLOYEES emp Inner join HR_SALARY_INCREMENT_DETAILS salInc \n"
                + "              on emp.ID=salinc.emp_id\n"
                + "              where salinc.salary_increment_id='" + salaryIncrementId + "' and emp.dept_id='" + depId + "'", HrEmployees.class);
        List<HrEmployees> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrEmployees> findEmployeeBySalIncIdAndDeptIdAndJobId(HrSalaryIncrements salaryIncrementId, Integer depId, Integer id) {
        Query query = em.createNativeQuery("  select *  from HR_EMPLOYEES emp Inner join HR_SALARY_INCREMENT_DETAILS salInc \n"
                + "             on emp.ID=salinc.emp_id\n"
                + "       where salinc.salary_increment_id='" + salaryIncrementId + "' \n"
                + "       and emp.dept_id='" + depId + "'\n"
                + "       and emp.job_id='" + id + "'", HrEmployees.class);
        List<HrEmployees> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrEmployees> findEmployeeBySalIncIdAndDeptIdAndJobIdAndStep(HrSalaryIncrements salaryIncrementId, Integer depId, Integer id, HrLuSalarySteps hrLuSalarySteps) {
        Query query = em.createNativeQuery("  select *  from HR_EMPLOYEES emp Inner join HR_SALARY_INCREMENT_DETAILS salInc \n"
                + "             on emp.ID=salinc.emp_id\n"
                + "       where salinc.salary_increment_id='" + salaryIncrementId + "' \n"
                + "       and emp.dept_id='" + depId + "'\n"
                + "       and emp.job_id='" + id + "' and emp.salary_step='" + hrLuSalarySteps.getId() + "'", HrEmployees.class);
        List<HrEmployees> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrEmployees> findEmployeeBySalIncIdAndStep(HrSalaryIncrements salaryIncrementId, Integer id) {
        Query query = em.createNativeQuery("  select *  from HR_EMPLOYEES emp Inner join HR_SALARY_INCREMENT_DETAILS salInc \n"
                + "             on emp.ID=salinc.emp_id\n"
                + "       where salinc.salary_increment_id='"+salaryIncrementId+"'\n"
                + "       and emp.salary_step='"+id+"'", HrEmployees.class);
        List<HrEmployees> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrEmployees> findEmployeeBySalIncIdAndGrade(HrSalaryIncrements salaryIncrementId, BigDecimal id) {
        Query query = em.createNativeQuery("  select *  from HR_EMPLOYEES emp Inner join HR_SALARY_INCREMENT_DETAILS salInc \n"
                + "             on emp.ID=salinc.emp_id\n"
                + "       where salinc.salary_increment_id='"+salaryIncrementId+"'\n"
                + "       and emp.grade_id='"+id.intValue()+"'", HrEmployees.class);
        List<HrEmployees> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrEmployees> findEmployeeBySalIncIdAndGradeandStep(HrSalaryIncrements salaryIncrementId, HrLuGrades hrLuGrades, HrLuSalarySteps hrLuSalarySteps) {
        Query query = em.createNativeQuery("  select *  from HR_EMPLOYEES emp Inner join HR_SALARY_INCREMENT_DETAILS salInc \n"
                + "             on emp.ID=salinc.emp_id\n"
                + "       where salinc.salary_increment_id='"+salaryIncrementId+"'\n"
                + "       and emp.grade_id='"+hrLuGrades.getId() +"' and emp.salary_step='"+hrLuSalarySteps.getId() +"'", HrEmployees.class);
        List<HrEmployees> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrSalaryIncrements> findAllrequests() {
        Query query = em.createNativeQuery(" select DISTINCT nr.id ,nr.effective_date,nr.reference_no,tn.status,tn.salary_increment_id\n" +
                "                               from HR_SALARY_INCREMENTS nr Inner join  HR_SALARY_INCREMENT_DETAILS tn \n" +
                "                                  \n" +
                "                             on nr.ID=tn.salary_increment_id\n" +
                "                           where  tn.status='0'  ", HrSalaryIncrements.class);
        List<HrSalaryIncrements> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
//</editor-fold>
}
