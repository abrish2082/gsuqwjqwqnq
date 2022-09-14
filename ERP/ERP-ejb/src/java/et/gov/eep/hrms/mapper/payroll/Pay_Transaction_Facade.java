/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.FmsPayrollTransaction;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Musie
 */
@Stateless
public class Pay_Transaction_Facade extends AbstractFacade<FmsPayrollTransaction> {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "ERP-ejbPU")
    EntityManager em;

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
    public Pay_Transaction_Facade() {
        super(FmsPayrollTransaction.class);
    }

    /**
     *
     * @return
     */
    public List<FmsPayrollTransaction> getListPayrollPreparerDate() {
        try {
            String string = "select DISTINCT NEW et.gov.insa.erp.ibfms.entity.FmsPayrollTransaction( p.payrollperiodDate, p.employmenttype) from FmsPayrollTransaction p" + " where p.status = :status";
            TypedQuery<FmsPayrollTransaction> query = em.createQuery(string, FmsPayrollTransaction.class);
            query.setParameter("status", "0");
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param empid
     * @param payrollcodedate
     * @return
     */
    public List<FmsPayrollTransaction> searchByEmpIDName(String empid, String payrollcodedate) {
        try {
            Query query = em.createNamedQuery("FmsPayrollTransaction.findByEmpId");
            query.setParameter("empId", empid.toLowerCase() + "%");
            query.setParameter("payrollperiodDate", payrollcodedate);
            query.setParameter("status", "0");
            return (List<FmsPayrollTransaction>) query.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param paidDate
     * @return
     */
    public List<FmsPayrollTransaction> getListPayTraction(String paidDate) {
        try {
            Query query = em.createNamedQuery("FmsPayrollTransaction.findByPayrollperiodDate", FmsPayrollTransaction.class);
            query.setParameter("payrollperiodDate", paidDate);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
     
    /**
     *
     * @param empId
     * @return
     */
    public FmsPayrollTransaction findPayTractionByEmpId(String empId) {
        try {
            Query query = em.createNamedQuery("FmsPayrollTransaction.findPayTractionByEmpId", FmsPayrollTransaction.class);
            query.setParameter("empId", empId.toLowerCase());
            return (FmsPayrollTransaction) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
      
    /**
     *
     * @return
     */
    public List findPayrollperiodDate() {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT PAYROLLPERIOD_DATE FROM FMS_PAYROLL_TRANSACTION ");
            return  query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
