/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanPaymentSummary;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XX
 */
@Stateless
public class FmsLoanPaymentSummaryFacade extends AbstractFacade<FmsLoanPaymentSummary> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLoanPaymentSummaryFacade() {
        super(FmsLoanPaymentSummary.class);
    }

    public ArrayList<FmsLoanPaymentSummary> fetchLoanPaymentSummary(FmsLoan fmsLoan) {
        Query query = em.createNamedQuery("FmsLoanPaymentSummary.findByLoanFk");
        query.setParameter("loanFk", fmsLoan);
        try {
            ArrayList<FmsLoanPaymentSummary> loanPaymentInfo = new ArrayList(query.getResultList());
            return loanPaymentInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteLoanPaymentSummary(FmsLoan fmsLoan) {
        Query query = em.createNativeQuery("DELETE FROM FMS_LOAN_PAYMENT_SUMMARY f WHERE f.LOAN_FK = '" + fmsLoan.getLoanId() + "'");
        query.executeUpdate();
    }
}
