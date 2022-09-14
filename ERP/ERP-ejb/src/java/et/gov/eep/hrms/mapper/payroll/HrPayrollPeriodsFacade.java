/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
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
public class HrPayrollPeriodsFacade extends AbstractFacade<HrPayrollPeriods> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollPeriodsFacade() {
        super(HrPayrollPeriods.class);
    }

    public int updatePayrollPeriod() {
        Query q = em.createNamedQuery("HrPayrollPeriods.update");
        int updateCount = q.executeUpdate();
        return updateCount;

    }

    public int finalizePayroll(HrPayrollPeriods hrPayrollPeriods) {
        Query q = em.createNamedQuery("HrPayrollPeriods.finalizePayrollPeriod");
        q.setParameter("id", hrPayrollPeriods.getId());
        int updateCount = q.executeUpdate();
        return updateCount;

    }

    public int activateOnlyOneMonth() {
        Query q = em.createNamedQuery("HrPayrollPeriods.activeOnlyOneMonth");

        int updateCount = q.executeUpdate();
        return updateCount;

    }

    public HrPayrollPeriods findPayrollPeriod(HrPayrollPeriods hrPayrollPeriods) {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.findById");
            q.setParameter("id", hrPayrollPeriods.getId());

            if (q.getResultList().isEmpty()) {
                return null;
            } else {

                return (HrPayrollPeriods) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollPeriods checkPayrollDate(HrPayrollPeriods hrPayrollPeriods) {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.findByMonth");
            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollPeriods) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollPeriods activePayrollDate() {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.findActivePayrollDate");

            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollPeriods) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollPeriods> loadPayrollDates() {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.loadPayrollDates");
            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (List<HrPayrollPeriods>) q.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollPeriods> loadListOfActivePayrollDates() {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.findActivePayrollDate");

            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (List<HrPayrollPeriods>) q.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollPeriods> loadListOfInactivePayrollDates() {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.loadInactiveDates");

            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (List<HrPayrollPeriods>) q.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollPeriods lastFinalizedPayroll() {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.lastFinalPayrll");

            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollPeriods) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollPeriods lastPayrollDate() {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.lastPayrollDate");

            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollPeriods) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollPeriods checkDateRepetition(String date) {
        try {
            Query q = em.createNamedQuery("HrPayrollPeriods.checkRepeatedDate");
            q.setParameter("paymentMadeForTheMonthOf", date);
            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                HrPayrollPeriods xx = (HrPayrollPeriods) q.getSingleResult();

                return (HrPayrollPeriods) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollPeriods> payrollFrom(String year, String month) {
        try {
            Query q = em.createQuery("SELECT h FROM HrPayrollPeriods h where (SUBSTRING(h.paymentMadeForTheMonthOf,7,4)>='" + year + "' AND SUBSTRING(h.paymentMadeForTheMonthOf,4,2)>'" + month + "') OR (SUBSTRING(h.paymentMadeForTheMonthOf,7,4)>'" + year + "' AND SUBSTRING(h.paymentMadeForTheMonthOf,4,2)>='01') order by SUBSTRING(h.paymentMadeForTheMonthOf,4,2) asc ");
            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (List<HrPayrollPeriods>) q.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollPeriods> payrollTo(String year, String month) {
        try {
            Query q = em.createQuery("SELECT h FROM HrPayrollPeriods h where (SUBSTRING(h.paymentMadeForTheMonthOf,7,4)>='" + year + "' AND SUBSTRING(h.paymentMadeForTheMonthOf,4,2)>'" + month + "') OR (SUBSTRING(h.paymentMadeForTheMonthOf,7,4)>='" + year + "' AND SUBSTRING(h.paymentMadeForTheMonthOf,4,2)>='" + month + "') order by SUBSTRING(h.paymentMadeForTheMonthOf,4,2) asc");
            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (List<HrPayrollPeriods>) q.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollPeriods loadMaxDateForPayroll() {
        try {
            Query q = em.createQuery("SELECT h FROM HrPayrollPeriods h where ((FUNCTION('TO_NUMBER', SUBSTRING(h.paymentMadeForTheMonthOf,7,4))*360)+(FUNCTION('TO_NUMBER', SUBSTRING(h.paymentMadeForTheMonthOf,4,2))*30))=SELECT MAX(((FUNCTION('TO_NUMBER', SUBSTRING(S.paymentMadeForTheMonthOf,7,4))*360)+(FUNCTION('TO_NUMBER', SUBSTRING(S.paymentMadeForTheMonthOf,4,2))*30))) FROM HrPayrollPeriods S");
            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollPeriods) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    
    public void test(HrPayrollPeriods hrPayrollPeriods) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public HrPayrollPeriods findPayrollPeriodById(int id) {
        try {
            Query query = em.createNamedQuery("HrPayrollPeriods.findById");
            query.setParameter("id", id);
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollPeriods) query.getResultList().get(0);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
