/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup;
import et.gov.eep.hrms.entity.payroll.HrPayrollAllEmpEdSetups;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DataReadQuery;
import org.eclipse.persistence.queries.StoredProcedureCall;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollAllEmpEdSetupsFacade extends AbstractFacade<HrPayrollAllEmpEdSetups> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollAllEmpEdSetupsFacade() {
        super(HrPayrollAllEmpEdSetups.class);
    }

    public HrPayrollAllEmpEdSetups checkAllEmpEd(HrPayrollEarningDeductions ed) {
        try {
            Query q = em.createNamedQuery("HrPayrollAllEmpEdSetups.checkRepeatitionWtPaymentType");

            q.setParameter("code", ed.getCode());

            return (HrPayrollAllEmpEdSetups) q.getSingleResult();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollAllEmpEdSetups checkAllEmpEd(HrPayrollEarningDeductions ed, HrPayrollAllEmpEdSetups type) {
        try {
            Query q = em.createNamedQuery("HrPayrollAllEmpEdSetups.checkRepeatition");

            q.setParameter("code", ed.getCode());
            q.setParameter("paymentIn", type.getPaymentIn());
            return (HrPayrollAllEmpEdSetups) q.getSingleResult();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean saveAllEmpEd(String activeDate, HrPayrollMaintainEds hrPayrollMaintainEds, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_ALL_E_ED");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("START_DATE", Date.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("END_DATE", Date.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("MONTHLY_LOAN_PAYMENT", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EARNING_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ACTIVE_DATE", activeDate);
            storedProcedureQuery.setParameter("START_DATE", hrPayrollMaintainEds.getStartDate());
            storedProcedureQuery.setParameter("END_DATE", hrPayrollMaintainEds.getEndDate());
            storedProcedureQuery.setParameter("TOTAL", hrPayrollMaintainEds.getTotal());
            storedProcedureQuery.setParameter("MONTHLY_LOAN_PAYMENT", hrPayrollMaintainEds.getMonthlyAmount());
            storedProcedureQuery.setParameter("EARNING_DED_CODE", hrPayrollEarningDeductions.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveEarnings(HrPayrollPeriods payrollCode) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_EARNINGS");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_DATE", payrollCode.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTotalEarnings(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_TOT_EAR");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_EARNING_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("TOTAL_EARNING_CODE", hrPayrollEarningDeductions.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTotalTaxableEarnings(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {

            
            System.out.println("payrollCode.getId()=="+payrollCode.getId());
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_T_T_EAR");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_EARNING_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("TOTAL_EARNING_CODE", hrPayrollEarningDeductions.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTotalDedFirstMadeBeforeTax(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_T_D_B_TAX");//deduction before tax
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("TOTAL_DED_CODE", hrPayrollEarningDeductions.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndEarning(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees empId) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_EARNINGS");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_DATE", payrollCode.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            storedProcedureQuery.setParameter("EMPL_ID", empId.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTotIndEarning(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrEmployees empId) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_T_EARNING");
            storedProcedureQuery.registerStoredProcedureParameter("EMPLOYEES_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_EARNING_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("EMPLOYEES_ID", empId.getId());
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("TOTAL_EARNING_CODE", hrPayrollEarningDeductions.getCode());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTotIndDeductions(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrEmployees empId) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_T_DED");
            storedProcedureQuery.registerStoredProcedureParameter("EMPLOYEES_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("EMPLOYEES_ID", empId.getId());
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("TOTAL_DED_CODE", hrPayrollEarningDeductions.getCode());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndSalary(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees empId, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_SALARY");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("GROSS_SALARY_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("GROSS_SALARY_CODE", hrPayrollEarningDeductions.getCode());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            storedProcedureQuery.setParameter("EMPL_ID", empId.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveLeaveAdvance(HrPayrollPeriods prevPayrollCode, HrEmployees hrEmployees,
            HrPayrollPeriods from, HrPayrollPeriods to, String beginingOfDed, HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions netPayCode,
            HrPayrollEarningDeductions penAddCode,
            HrPayrollEarningDeductions penDedCode,
            HrPayrollEarningDeductions grossSalCode,
            HrPayrollEarningDeductions totEarCode,
            HrPayrollEarningDeductions totalDedCode,
            HrPayrollEarningDeductions totTaxEarn,
            HrPayrollEarningDeductions leaveAdvCode) {
        System.out.print("The employee id is " + hrEmployees.getId());
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_LEAV_ADV");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMP_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_FROM", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_TO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("BEGINING_OF_DED", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TAX_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NET_PAY_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PEN_ADD_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PEN_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("GROSS_SAL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOT_EAR_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOT_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOT_TAX_EAR", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("LEAVE_ADV_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("GROSS_SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_CODE", prevPayrollCode.getId());
            storedProcedureQuery.setParameter("EMP_ID", hrEmployees.getId());
            storedProcedureQuery.setParameter("PAYROLL_FROM", from.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_TO", to.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("BEGINING_OF_DED", beginingOfDed);
            storedProcedureQuery.setParameter("TAX_CODE", taxCode.getCode());
            storedProcedureQuery.setParameter("NET_PAY_CODE", netPayCode.getCode());
            storedProcedureQuery.setParameter("PEN_ADD_CODE", penAddCode.getCode());
            storedProcedureQuery.setParameter("PEN_DED_CODE", penDedCode.getCode());
            storedProcedureQuery.setParameter("GROSS_SAL_CODE", grossSalCode.getCode());
            storedProcedureQuery.setParameter("TOT_EAR_CODE", totEarCode.getCode());
            storedProcedureQuery.setParameter("TOT_DED_CODE", totalDedCode.getCode());
            storedProcedureQuery.setParameter("TOT_TAX_EAR", totTaxEarn.getCode());
            storedProcedureQuery.setParameter("LEAVE_ADV_DED_CODE", leaveAdvCode.getCode());
            storedProcedureQuery.setParameter("GROSS_SALARY", hrEmployees.getBasicSalary());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List loadLeaveAdvancePyment(HrEmployees empId) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_LA_REPORT");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("EMPLOYEE_ID", empId.getId());
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadMonthlyPayroll(String payrollId) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_MON_REPORT");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadMonthlyPayrollWithSelectedEd(String payrollId, String listOFEd) {
        System.out.print("The list of ed is " + listOFEd);
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LO_MON_REP_W_SEL_ED");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            call.addNamedArgumentValue("LIST_OF_ED", listOFEd);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadOneThirdSal(String payrollId) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_ONE_THIRD");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadMortage(String payrollId) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_MORTAGE");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadCourtCase(String payrollId) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_COURT");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadFamily(String payrollId) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_FAMILY");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadEachEmployeesPaySlip(HrPayrollPeriods payrollId, HrEmployees empID) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_EMP_PAY_SLIP");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId.getId());
            call.addNamedArgumentValue("EMPLOYEE_ID", empID.getId());
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadEdSummery(String payrollId, String sqlQuery) {
        try {
            System.out.print("The" + sqlQuery);
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_ED_SUMMERY");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            call.addNamedArgumentValue("LIST_OF_ED_CODES", sqlQuery);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List loadEarningDeductions(String payrollId, String edCode) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("HR_PAYROLL_LOAD_EAR_DED_REP");
            call.useNamedCursorOutputAsResultSet("MONTH_PAY");
            call.addNamedArgumentValue("PAYROLL_ID", payrollId);
            call.addNamedArgumentValue("ED_CODE", edCode);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMonTransactions> loadEarnindDed(String payrollId) {
        try {
            StoredProcedureQuery q = em.createStoredProcedureQuery("HR_PAYROLL_LOAD_BACK_PAY_INFO");
            q.registerStoredProcedureParameter("MONTH_PAY", HrPayrollMonTransactions.class, ParameterMode.REF_CURSOR);
            q.execute();
            if (q.execute()) {
                List<HrPayrollMonTransactions> emp = (List<HrPayrollMonTransactions>) q.getResultList();
                System.out.println("%%%%%%%%%%%%%%%%dent : " + emp.size());
                for (HrPayrollMonTransactions student : emp) {
                    System.out.println("Student : " + student.getAmount() + " " + student.getAmount());
                }
                return emp;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean saveDeductions(HrPayrollPeriods payrollCode
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_DEDUCTIONS");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_DATE", payrollCode.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTotalDed(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_TOTAL_DED");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("TOTAL_DED_CODE", hrPayrollEarningDeductions.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndDeductions(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees emp) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_DEDUCTIONS");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_DATE", payrollCode.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            storedProcedureQuery.setParameter("EMPL_ID", emp.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteMonTrans(HrPayrollPeriods payrollCode) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_DEL_ALL_MON_TRANS");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteIndMonTrans(HrPayrollPeriods payrollCode, HrEmployees empId) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_DEL_IND_MON_TRANS");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPLOYEE_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("EMPLOYEE_ID", empId.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveMonthlySalary(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions grossSalaryCode
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_SALARY");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("GROSS_SALARY_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("GROSS_SALARY_CODE", grossSalaryCode.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndMonthlySalary(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions grossSalaryCode, int numberOfDays, double salary, HrEmployees emp) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_SALARY");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("GROSS_SALARY_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("GROSS_SALARY_CODE", grossSalaryCode.getCode());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            storedProcedureQuery.setParameter("EMPL_ID", emp.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "No");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTax(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions totTabaleEar
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_TAX");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TAX_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_TAXABLE_EARNINGS", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", hrPayrollPeriods.getId());
            storedProcedureQuery.setParameter("TAX_CODE", taxCode.getCode());
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_DATE", hrPayrollPeriods.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("TOTAL_TAXABLE_EARNINGS", totTabaleEar.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndTaxAndPension(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions totTabaleEar, HrPayrollEarningDeductions penAddCode, HrPayrollEarningDeductions penDedCode, int numberOfDays, double salary, HrEmployees emp) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_TP");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TAX_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_TAXABLE_EARNINGS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PENSION_ADDITION_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PENSION_DEDUCTION_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPLOYEES_ID_NUM", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", hrPayrollPeriods.getId());
            storedProcedureQuery.setParameter("TAX_CODE", taxCode.getCode());
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_DATE", hrPayrollPeriods.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("TOTAL_TAXABLE_EARNINGS", totTabaleEar.getCode());
            storedProcedureQuery.setParameter("PENSION_ADDITION_CODE", penAddCode.getCode());
            storedProcedureQuery.setParameter("PENSION_DEDUCTION_CODE", penDedCode.getCode());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            storedProcedureQuery.setParameter("EMPLOYEES_ID_NUM", emp.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveTaxAndPension(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions totTabaleEar, HrPayrollEarningDeductions penAddCode, HrPayrollEarningDeductions penDedCode
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_EMP_TP");
            storedProcedureQuery.registerStoredProcedureParameter("ACTIVE_PAYROLL_DATE", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TAX_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_TAXABLE_EARNINGS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PENSION_ADDITION_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PENSION_DEDUCTION_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", hrPayrollPeriods.getId());
            storedProcedureQuery.setParameter("TAX_CODE", taxCode.getCode());
            storedProcedureQuery.setParameter("ACTIVE_PAYROLL_DATE", hrPayrollPeriods.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("TOTAL_TAXABLE_EARNINGS", totTabaleEar.getCode());
            storedProcedureQuery.setParameter("PENSION_ADDITION_CODE", penAddCode.getCode());
            storedProcedureQuery.setParameter("PENSION_DEDUCTION_CODE", penDedCode.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveNetPay(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions netPaycode
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_NET_PAY");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NET_PAY_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", hrPayrollPeriods.getId());
            storedProcedureQuery.setParameter("NET_PAY_CODE", netPaycode.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndNeyPay(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions netPaycode, int numberOfDays, double salary, HrEmployees emp
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_NET_PAY");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NET_PAY_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", hrPayrollPeriods.getId());
            storedProcedureQuery.setParameter("NET_PAY_CODE", netPaycode.getCode());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            storedProcedureQuery.setParameter("EMPL_ID", emp.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean savePension(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions pensionAddCode, HrPayrollEarningDeductions pensionDedCode
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_PENSION");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PENSION_ADDITION_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PENSION_DEDUCTION_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_CODE", payrollCode.getId());
            storedProcedureQuery.setParameter("PENSION_ADDITION_CODE", pensionAddCode.getCode());
            storedProcedureQuery.setParameter("PENSION_DEDUCTION_CODE", pensionDedCode.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //MAINTAIN ALLOWNCES
    public boolean saveAllowanceInJobLevel(String month, HrPayrollPeriods pp
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_AL_JL");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndAllowanceInJobLevel(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOFDays
    ) {
        try {

//           ALLOANCE_NAME IN VARCHAR2,
//           PAYMENT_MONTH IN VARCHAR2,
//           PAYROLL_ID IN NUMBER,
//           NUM_DAYS NUMBER,
//           SALARY NUMBER,
//           EMPL_ID NUMBER,
//           IS_LEAVE_AVANCE VARCHAR 
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_AL_JL");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOFDays);
            storedProcedureQuery.setParameter("SALARY", emp.getBasicSalary());
            storedProcedureQuery.setParameter("EMPL_ID", emp.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //MAINTAIN ALLOWNCES
    public boolean saveIndAllowanceInJobLevel(String month, HrPayrollPeriods pp, int numberOfDays, double salary
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_AL_JL");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveAllowanceInJobTitle(String month, HrPayrollPeriods pp
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_AL_JT");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndAllowanceInJobTitle(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOfDays
    ) {
        try {
//            ALLOANCE_NAME IN VARCHAR2,
//            PAYMENT_MONTH IN VARCHAR2,
//            PAYROLL_ID IN NUMBER,
//            NUM_DAYS NUMBER,
//                    SALARY NUMBER,
//EMPL_ID NUMBER,
//        IS_LEAVE_AVANCE VARCHAR
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_AL_JT");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", emp.getBasicSalary());
            storedProcedureQuery.setParameter("EMPL_ID", emp.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndAllowanceInJobTitle(String month, HrPayrollPeriods pp, int numberOfDays, double salary
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_AL_JT");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveAllowanceInJobLocation(String month, HrPayrollPeriods pp
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAINTAIN_AL_LOC");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean saveIndAllowanceInJobLocation(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOFDays
    ) {
        try {

            // (ALLOANCE_NAME IN VARCHAR2,
//            PAYMENT_MONTH IN VARCHAR2,
//            PAYROLL_ID IN NUMBER,
//            NUM_DAYS NUMBER,
//                    SALARY NUMBER,
//                    EMPL_ID NUMBER,
//                            IS_LEAVE_AVANCE VARCHAR) AS 
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_AL_LOC");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("IS_LEAVE_AVANCE", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOFDays);
            storedProcedureQuery.setParameter("SALARY", emp.getBasicSalary());
            storedProcedureQuery.setParameter("EMPL_ID", emp.getId());
            storedProcedureQuery.setParameter("IS_LEAVE_AVANCE", "NO");
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean saveIndAllowanceInJobLocation(String month, HrPayrollPeriods pp, int numberOfDays, double salary
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_MAIN_IND_AL_LOC");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean calcAndSavepymtRemMonht(String month, HrPayrollPeriods pp
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_CACL_PYT_REM_MONTH");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean savePayrollGeneratorsInfo(String month, int empId, String payrollId
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_CACL_PYT_REM_MONTH");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", month);
            storedProcedureQuery.setParameter("PAYROLL_ID", Integer.valueOf(payrollId));
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean calcIndAndSavepymtRemMonht(String month, HrPayrollPeriods pp, HrEmployees emp
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_IND_CACL_PYT_REM_M");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMPL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("EMPL_ID", pp.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean calcIndAndSavepymtRemMonht(String month, HrPayrollPeriods pp, int numberOfDays, double salary
    ) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_CA_IND_PYT_REM_MON");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", Number.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("SALARY", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("NUM_DAYS", numberOfDays);
            storedProcedureQuery.setParameter("SALARY", salary);
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updatePayedEd(String month, HrPayrollPeriods pp) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_UPDATE_PAYED_ED");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean activateNextPayroll(String month, HrPayrollPeriods pp) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_UPDATE_PAYED_ED");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            storedProcedureQuery.setParameter("ACTIVE_PAY_DATE", month);
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean generatePayrollSummery(String month, HrPayrollPeriods pp) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_GENERATE_P_SUM");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_ID", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_ID", pp.getId());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List monthlyPentionReport(String year, String taxName) {
        try {
            StoredProcedureCall call = new StoredProcedureCall();
            call.setProcedureName("FMS_TAX_REPORT_TOTAL");
            call.addNamedInOutputArgument("TAX_TYPE", taxName, String.class);
            call.addNamedInOutputArgument("REPORT_MONTH", year, String.class);
            DataReadQuery databaseQuery = new DataReadQuery();
            databaseQuery.setCall(call);
            Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
            List reportDatas = query.getResultList();
            return reportDatas;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> loadEmployees() {
        try {
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_EMPLOYEES", HrEmployees.class);
            query.registerStoredProcedureParameter(1, HrEmployees.class, ParameterMode.REF_CURSOR);
            query.execute();
            List<HrEmployees> empLists = (List<HrEmployees>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> loadUngroupedEmployees(HrLuPayrollAePGroup hrLuPayrollAePGroup) {
        try {
            em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //look for the employee in the L2 cache first
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_UNGROUPED_EMP", HrEmployees.class);
            query.registerStoredProcedureParameter(1, HrEmployees.class, ParameterMode.REF_CURSOR);
            query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            System.out.print("The id is " + hrLuPayrollAePGroup.getId());
            query.setParameter(2, String.valueOf(hrLuPayrollAePGroup.getId()));
            query.execute();
            List<HrEmployees> empLists = (List<HrEmployees>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> loadUngroupedEmployeesUsingPosition(String jobPosition) {
        try {
            em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //look for the employee in the L2 cache first
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_EMP_BY_POS", HrEmployees.class);
            query.registerStoredProcedureParameter(1, HrEmployees.class, ParameterMode.REF_CURSOR);
            query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            System.out.println("The position id is " + jobPosition);
            query.setParameter(2, jobPosition);
            query.execute();
            List<HrEmployees> empLists = (List<HrEmployees>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> loadUngroupedEmployeesUsingGrade(String gradeId) {
        try {
            em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //look for the employee in the L2 cache first
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_EMP_BY_GRADE", HrEmployees.class);
            query.registerStoredProcedureParameter(1, HrEmployees.class, ParameterMode.REF_CURSOR);
            query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
//            System.out.print("The id is " + hrLuPayrollAePGroup.getId());
            query.setParameter(2, gradeId);
            query.execute();
            List<HrEmployees> empLists = (List<HrEmployees>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMaintainEds> loadGroupedEmployees(HrLuPayrollAePGroup hrLuPayrollAePGroup) {
        try {
            em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //look for the employee in the L2 cache first
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_GROUPED_EMP", HrPayrollMaintainEds.class);
            query.registerStoredProcedureParameter(1, HrPayrollMaintainEds.class, ParameterMode.REF_CURSOR);
            query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

            query.setParameter(2, String.valueOf(hrLuPayrollAePGroup.getId()));
            query.execute();
            List<HrPayrollMaintainEds> empLists = (List<HrPayrollMaintainEds>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrEmployees> loadMantanedEmployees(String groupId) {
        try {
            em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //look for the employee in the L2 cache first
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_MINTA_BK", HrEmployees.class);
            query.registerStoredProcedureParameter("MONTH_PAY", void.class, ParameterMode.REF_CURSOR);
            query.registerStoredProcedureParameter("GROUP_ID", String.class, ParameterMode.IN);
            query.setParameter("GROUP_ID", groupId);

            List<HrEmployees> empLists = (List<HrEmployees>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrEmployees> loadSummery() {
        try {
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_MINTA_BK", HrEmployees.class);
            query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
            query.execute();
            List<HrEmployees> empLists = (List<HrEmployees>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> listOfFilteredEmployees() {
        try {
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_FIL_EMP_FOR_BK", HrEmployees.class);
            query.registerStoredProcedureParameter(1, HrEmployees.class, ParameterMode.REF_CURSOR);
            query.execute();
            List<HrEmployees> empLists = (List<HrEmployees>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollFilterBp> listOfEmpForBk() {
        try {
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_EMPLOYEES", HrEmployees.class);
            query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
            query.execute();
            List<HrPayrollFilterBp> empLists = (List<HrPayrollFilterBp>) query.getResultList();
            return empLists;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
