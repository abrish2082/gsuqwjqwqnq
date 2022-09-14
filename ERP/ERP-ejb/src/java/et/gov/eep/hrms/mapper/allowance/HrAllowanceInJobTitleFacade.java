/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.allowance;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrAllowanceInJobTitleFacade extends AbstractFacade<HrAllowanceInJobTitle> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrAllowanceInJobTitleFacade() {
        super(HrAllowanceInJobTitle.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named query">
    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> listOfEarningDedInTitle() {
        Query q = em.createNamedQuery("HrAllowanceInJobTitle.findAllAllowanceInJobTitle");
        return (List<HrPayrollEarningDeductions>) q.getResultList();
    }

    /**
     *
     * @param ed
     * @return
     */
    public List<HrAllowanceInJobTitle> findAllowncesByEdCode(HrPayrollEarningDeductions ed) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInJobTitle.findByEdId");
            q.setParameter("code", ed.getCode());
            return (List<HrAllowanceInJobTitle>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param ajt
     * @param ed
     * @param jt
     * @return
     */
    public HrAllowanceInJobTitle checkAllInJt(HrAllowanceInJobTitle ajt, HrPayrollEarningDeductions ed, HrJobTypes jt) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInJobTitle.checkAllInJobTitle");
            q.setParameter("paymentIn", ajt.getPaymentIn());
            q.setParameter("code", ed.getCode());
            q.setParameter("id", jt.getId());
            return (HrAllowanceInJobTitle) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param ajt
     * @param ed
     * @param adress
     * @return
     */
    public HrAllowanceInJobTitle checkAllowanceInJobTitle(HrAllowanceInJobTitle ajt, HrPayrollEarningDeductions ed, HrAddresses adress) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInJobTitle.cheackRepeatedJobTitle");
//            q.setParameter(1, ajt.getJobTitleId().getId());
//            q.setParameter(2, ed.getCode());
//            q.setParameter(3, adress.getAddressId());
            return (HrAllowanceInJobTitle) q.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public HrAllowanceInJobTitle checkAllowanceInJobTitleForUpdate(HrAllowanceInJobTitle ajt, HrPayrollEarningDeductions ed, HrAddresses adress) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInJobTitle.cheackRepeatedJobTitleForUpdate");
            q.setParameter(1, ajt.getJobTitleId().getId());
            q.setParameter(2, ed.getCode());
//            q.setParameter(3, adress.getAddressId());
//            q.setParameter(4, ajt.getId());
            q.setParameter(3, ajt.getId());
//            q.setParameter(4, ajt.getId());
            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrAllowanceInJobTitle) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * used to load all the payment types which are based on the employees job
     * title the types could be in birr,percent,or in kind which is optional
     *
     *
     * @param ed
     * @return
     */
    public List<HrAllowanceInJobTitle> findAllJobTypePaymentTypes(HrPayrollEarningDeductions ed) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInJobTitle.findAllownceInJotTitlePaymentTypes");
            q.setParameter("code", ed.getCode());
            return (List<HrAllowanceInJobTitle>) q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param aj
     * @return
     */
    public HrAllowanceInJobTitle findAllInJobTitle(HrAllowanceInJobTitle aj) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInJobTitle.findById");
            q.setParameter("id", aj.getId());

            return (HrAllowanceInJobTitle) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public HrAllowanceInJobTitle findByJobTitleId(HrJobTypes hrJobTypes) {
        try {
            Query q = em.createNamedQuery("HrAllowanceInJobTitle.findByJobTitleId");
            q.setParameter("jobTitleId", hrJobTypes);
            return (HrAllowanceInJobTitle) q.getSingleResult();
        } catch (Exception ex) {
//            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
