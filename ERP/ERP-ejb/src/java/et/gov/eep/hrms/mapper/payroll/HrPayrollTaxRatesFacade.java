/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollTaxRatesFacade extends AbstractFacade<HrPayrollTaxRates> {

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
    public HrPayrollTaxRatesFacade() {
        super(HrPayrollTaxRates.class);
    }

    public boolean checkOverlap(HrPayrollTaxRates tr) {
        try {
            Query q = em.createNamedQuery("HrPayrollTaxRates.checkOverlap");
            q.setParameter("fromAmount", tr.getFromAmount());
            q.setParameter("toAmount", tr.getToAmount());
//             q.setParameter("toNegAmt", -1);
            if (q.getResultList().isEmpty()) {
                return false;
            } else {

                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean checkOverlapUpdate(HrPayrollTaxRates tr) {
        try {
            Query q = em.createNamedQuery("HrPayrollTaxRates.checkOverlapUpdate");
            q.setParameter("fromAmount", tr.getFromAmount());
            q.setParameter("toAmount", tr.getToAmount());
            q.setParameter("id", tr.getActiveYearId().getId());

            if (q.getResultList().isEmpty()) {
                return false;
            } else {

                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean checkUnlimitedUpdate(HrPayrollTaxRates pr) {
        try {
            Query q = em.createNamedQuery("HrPayrollTaxRates.checkRepetedUnlimitedUpdate");
            q.setParameter("toAmount", -1);
            q.setParameter("rateId", pr.getRateId());
            if (q.getResultList().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean checkUnlimited() {
        try {
            Query q = em.createNamedQuery("HrPayrollTaxRates.checkRepetedUnlimited");
            q.setParameter("toAmount", -1);

            if (q.getResultList().isEmpty()) {
                return false;
            } else {

                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public HrPayrollTaxRates findBySalaryRange(Double AccruedLeaveAmount) {
        try {
            Query q = em.createNamedQuery("HrPayrollTaxRates.findBySalaryRange");
            q.setParameter("amount", AccruedLeaveAmount);
            HrPayrollTaxRates tax = (HrPayrollTaxRates) q.getSingleResult();
            if (tax !=null) {
                return tax;
            } else {

                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
