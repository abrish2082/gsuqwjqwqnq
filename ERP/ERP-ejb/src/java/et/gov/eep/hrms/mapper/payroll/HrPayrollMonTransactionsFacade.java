/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author user
 */
@Stateless//extends AbstractFacade<HrPayrollMaintanBackPymt> implements HrPayrollMaintanBackPymtFacadeLocal 
public class HrPayrollMonTransactionsFacade extends AbstractFacade<HrPayrollMonTransactions> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollMonTransactionsFacade() {
        super(HrPayrollMonTransactions.class);
    }

    public int finalizeLeaveAdvance(HrPayrollPeriods hrPayrollPeriods) {
        Query q = em.createNamedQuery("HrPayrollMonTransactions.closeLeaveAdvance");
        int updateCount = q.executeUpdate();
        return updateCount;

    }

    public List<HrPayrollMonTransactions> loadSummery() {
        try {
            StoredProcedureQuery query = this.em.createStoredProcedureQuery("HR_PAYROLL_LOAD_ED_SUM", HrPayrollMonTransactions.class);
            query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
            query.execute();
            List<HrPayrollMonTransactions> empLists = (List<HrPayrollMonTransactions>) query.getResultList();
            return empLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollPeriods> returnLeaveAdvance(HrEmployees hrEmployees) {
        try {
            Query q = em.createNamedQuery("HrPayrollMonTransactions.findLeaveAdvanceOnlyLA");
            q.setParameter("id", hrEmployees.getId());
            return (List<HrPayrollPeriods>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollPeriods> usedPayrollDates() {
        try {
            Query q = em.createNamedQuery("HrPayrollMonTransactions.loadUsedPayrollDates");
            return (List<HrPayrollPeriods>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> findPayedAmount(HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions ed) {
        try {
            Query q = em.createNamedQuery("HrPayrollMonTransactions.findThePayedAmount");
            q.setParameter("id", hrPayrollPeriods.getId());
            q.setParameter("code", ed.getCode());
            return (List<HrEmployees>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMonTransactions> findPayedAmountAndPayDetail(HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions ed) {
        try {
            Query q = em.createNamedQuery("HrPayrollMonTransactions.findThePayedAmountDet");
            q.setParameter("id", hrPayrollPeriods.getId());
            q.setParameter("code", ed.getCode());
            return (List<HrPayrollMonTransactions>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMonTransactions> findEarnings(HrPayrollPeriods hrPayrollPeriods, HrEmployees emp) {
        try {
            Query q = em.createNamedQuery("HrPayrollMonTransactions.findEarningsPay");
            q.setParameter("id", hrPayrollPeriods.getId());
            q.setParameter("empId", emp);
            return (List<HrPayrollMonTransactions>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollMonTransactions> fetchCourtOrderPayments() {
        try {
            Query query = em.createNativeQuery("SELECT MT.AMOUNT ,MT.ID , MT.EMP_ID,CO.EARNING_DED_CODE\n"
                    + "from HR_PAYROLL_COURT_CASE_INFO CO INNER JOIN HR_PAYROLL_MON_TRANSACTIONS MT\n"
                    + "ON MT.EARNING_DED_CODE=CO.EARNING_DED_CODE AND MT.EMP_ID=CO.EMP_ID AND MT.AMOUNT > 10000 ", HrPayrollMonTransactions.class);

            return (List<HrPayrollMonTransactions>) query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public HrPayrollMonTransactions getSelectedPayment(Long id) {
        try {
            Query query = em.createNamedQuery("HrPayrollMonTransactions.findById");
            query.setParameter("id", id);
            HrPayrollMonTransactions payment = (HrPayrollMonTransactions) query.getResultList().get(0);
            return payment;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<HrPayrollMonTransactions> fetchCourtOrderPaymentsforCash() {
        try {
            Query query = em.createNativeQuery("SELECT MT.AMOUNT ,MT.ID , MT.EMP_ID,CO.EARNING_DED_CODE\n"
                    + "from HR_PAYROLL_COURT_CASE_INFO CO INNER JOIN HR_PAYROLL_MON_TRANSACTIONS MT\n"
                    + "ON MT.EARNING_DED_CODE=CO.EARNING_DED_CODE AND MT.EMP_ID=CO.EMP_ID AND MT.AMOUNT <= 10000 ", HrPayrollMonTransactions.class);

            return (List<HrPayrollMonTransactions>) query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<HrPayrollMonTransactions> getPayrollMonthlyTrn(HrPayrollMonTransactions hrPayrollMonTransactions) {
        try {
            Query query = em.createNativeQuery("SELECT hrped.GENERAL_LEDGER_ID AS GL_CODE, "
                    + "hrped.CODE AS PAYROLL_ID, "
                    + "hrpmt.ID AS PAYROLL_MONTHLY_TRAN_ID,"
                    + "SUM(hrpmt.AMOUNT) AS Amount "
                    + "FROM HR_PAYROLL_EARNING_DEDUCTIONS hrped "
                    + "INNER JOIN HR_PAYROLL_MON_TRANSACTIONS hrpmt "
                    + "ON hrped.CODE= hrpmt.EARNING_DED_CODE "
                    + "INNER JOIN HR_EMPLOYEES hrEmp "
                    + "ON hrEmp.ID = hrpmt.EMP_ID "
                    + "WHERE hrped.DRCR = 'Debit' "
                    + "GROUP BY hrped.GENERAL_LEDGER_ID, hrped.CODE, hrpmt.ID ", HrPayrollMonTransactions.class);
            return (List<HrPayrollMonTransactions>) query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
