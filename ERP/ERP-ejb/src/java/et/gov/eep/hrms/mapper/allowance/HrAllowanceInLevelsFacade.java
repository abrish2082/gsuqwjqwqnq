/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.allowance;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
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
@Stateless
public class HrAllowanceInLevelsFacade extends AbstractFacade<HrAllowanceInLevels> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrAllowanceInLevelsFacade() {
        super(HrAllowanceInLevels.class);
    }
    // <editor-fold defaultstate="collapsed" desc="Named query">

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> returnEarningDeductionsInJobLevel() {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLevels.findJobLevelCodes");
            return (List<HrPayrollEarningDeductions>) q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param hrPayrollEarningDeductions
     * @return
     */
    public List<HrAllowanceInLevels> returnAllowanceInJobLevelAmountTypes(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLevels.findJobLevelInCodeAndType");
            q.setParameter("code", hrPayrollEarningDeductions.getCode());
            return (List<HrAllowanceInLevels>) q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public HrAllowanceInLevels returnEarningDeductionsInJobLevel1() {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLevels.checkAllowance");
            HrAllowanceInLevels AL = (HrAllowanceInLevels) q.getSingleResult();
            return (HrAllowanceInLevels) q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrAllowanceInLevels
     * @return
     */
    public HrAllowanceInLevels findAllownaceInJobLevel(HrAllowanceInLevels hrAllowanceInLevels) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLevels.findById");
            q.setParameter("id", hrAllowanceInLevels.getId());
            HrAllowanceInLevels AL = (HrAllowanceInLevels) q.getSingleResult();
            return (HrAllowanceInLevels) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param ed
     * @param jl
     * @param jobLevels
     * @param ad
     * @return
     */
    public HrAllowanceInLevels checkAllInJobLevel(HrPayrollEarningDeductions ed, HrAllowanceInLevels jl, HrLuJobLevels jobLevels, HrAddresses ad) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLevels.checkAllowance");
            q.setParameter("code", ed.getCode());
//            q.setParameter("allowanceIn", jl.getPaymentIn());
            q.setParameter("id", jobLevels.getId());
//            q.setParameter("addressId", ad.getAddressId());
            return (HrAllowanceInLevels) q.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrAllowanceInLevels checkAllInJobLevelForUpdate(HrPayrollEarningDeductions ed, HrAllowanceInLevels jl, HrLuJobLevels jobLevels, HrAddresses ad) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInLevels.checkAllowanceForUpdate");
            q.setParameter(1, jl.getId());
            q.setParameter(2, jobLevels.getId());
//            q.setParameter(3, ad.getAddressId());
            q.setParameter(3, ed.getCode());
            return (HrAllowanceInLevels) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    /**
     *
     * @param month
     * @param pp
     * @param ed
     * @return
     */
    public boolean saveAllowanceInJobTitle(String month, HrPayrollPeriods pp, HrPayrollEarningDeductions ed) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_MAINTAIN_ALL_IN_JOB_TITLE");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EARNING_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("EARNING_DED_CODE", ed.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param month
     * @param pp
     * @param ed
     * @return
     */
    public boolean saveAllowanceInJobLevel(String month, HrPayrollPeriods pp, HrPayrollEarningDeductions ed) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_MAINTAIN_ALL_IN_JOB_LEVEL");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EARNING_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("EARNING_DED_CODE", ed.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    /**
     *
     * @param month
     * @param pp
     * @param ed
     * @return
     */
    public boolean saveAllowanceInLocation(String month, HrPayrollPeriods pp, HrPayrollEarningDeductions ed) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("HR_MAINTAIN_ALL_IN_LOCATION");
            storedProcedureQuery.registerStoredProcedureParameter("ALLOANCE_NAME", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PAYMENT_MONTH", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("EARNING_DED_CODE", Number.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("ALLOANCE_NAME", "helloGechesa");
            storedProcedureQuery.setParameter("PAYMENT_MONTH", pp.getPaymentMadeForTheMonthOf());
            storedProcedureQuery.setParameter("EARNING_DED_CODE", ed.getCode());
            return storedProcedureQuery.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
