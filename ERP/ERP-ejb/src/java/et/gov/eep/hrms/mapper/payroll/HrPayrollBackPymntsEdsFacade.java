/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
/**
 *
 * @author user
 */
@Stateless
public class HrPayrollBackPymntsEdsFacade extends AbstractFacade<HrPayrollBackPymntsEds> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollBackPymntsEdsFacade() {
        super(HrPayrollBackPymntsEds.class);
    }

    public List<HrPayrollEarningDeductions> loadUnusedEd() {
        Query q = em.createNamedQuery("HrPayrollBackPymntsEds.loadEarningForBk");
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (List<HrPayrollEarningDeductions>) q.getResultList();
        }
    }

    public boolean generateBackPayment(String payrollFrom, String payrollTo,String groupId) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_GENERATE_GROUP_BK");
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_FROM", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_TO", String.class, ParameterMode.IN);
             storedProcedureQuery.registerStoredProcedureParameter("GROUP_ID", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("PAYROLL_FROM", payrollFrom);
            storedProcedureQuery.setParameter("PAYROLL_TO", payrollTo);
             storedProcedureQuery.setParameter("GROUP_ID", groupId);
            
            
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean generateIndividualBkPayment(String earningDedCode, String payrollFrom, String payrollTo, String empId, String criterias) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_GENER_IND_BK_PYMT");
            storedProcedureQuery.registerStoredProcedureParameter("EARNING_DED_ID", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_FROM", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_TO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMP_ID", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("CRITERIAS", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("EARNING_DED_ID", earningDedCode);
            storedProcedureQuery.setParameter("PAYROLL_FROM", payrollFrom);
            storedProcedureQuery.setParameter("PAYROLL_TO", payrollTo);
            storedProcedureQuery.setParameter("EMP_ID", empId);
            storedProcedureQuery.setParameter("CRITERIAS", criterias);

            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
      public boolean generateIndividualBkPaymentWithDays(String earningDedCode, String payrollFrom, String payrollTo, String empId, String criterias,String noDays,String totAmount) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_GENER_IND_BK_WDAYS");
            storedProcedureQuery.registerStoredProcedureParameter("EARNING_DED_ID", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_FROM", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYROLL_TO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EMP_ID", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("CRITERIAS", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("NUM_DAYS", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("TOTAL_AMT", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("EARNING_DED_ID", earningDedCode);
            storedProcedureQuery.setParameter("PAYROLL_FROM", payrollFrom);
            storedProcedureQuery.setParameter("PAYROLL_TO", payrollTo);
            storedProcedureQuery.setParameter("EMP_ID", empId);
            storedProcedureQuery.setParameter("CRITERIAS", criterias);
            storedProcedureQuery.setParameter("NUM_DAYS", criterias);
            storedProcedureQuery.setParameter("TOTAL_AMT", criterias);

            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteIndBk(String empId) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_DELETE_IN_BK_PYMT");
            storedProcedureQuery.registerStoredProcedureParameter("EMPLOYEES_ID", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("EMPLOYEES_ID", empId);
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
      public boolean deleteGrBk() {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_PAYROLL_DELETE_GR_BK_PYMT");
                       return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
