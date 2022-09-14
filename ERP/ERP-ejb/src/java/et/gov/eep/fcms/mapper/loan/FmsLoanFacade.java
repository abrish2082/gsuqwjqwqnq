/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.fcms.entity.loan.FmsLoan;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.util.List;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsLoanFacade extends AbstractFacade<FmsLoan> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLoanFacade() {
        super(FmsLoan.class);
    }

    public ArrayList<FmsLoan> searchFmsLoanByLoanNo(FmsLoan loanNNo) {
        Query query = em.createNamedQuery("FmsLoan.findByLoanNoLIKE");
        query.setParameter("loanNo", loanNNo.getLoanNo().toUpperCase() + '%');
        try {
            ArrayList<FmsLoan> FmsLoanlist = new ArrayList(query.getResultList());
            return FmsLoanlist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public FmsLoan getFmsLoanInfo(FmsLoan loanNo) {
        Query query = em.createNamedQuery("FmsLoan.findByLoanNo");
        query.setParameter("loanNo", loanNo.getLoanNo());
        try {
            FmsLoan loanInfo = (FmsLoan) query.getResultList().get(0);
            return loanInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public FmsLoan fetchFmsLoanInfo(FmsLoan loanId) {
        Query query = em.createNamedQuery("FmsLoan.findByLoanId");
        query.setParameter("loanId", loanId.getLoanId());
        try {
            FmsLoan loanInfo = (FmsLoan) query.getResultList().get(0);
            return loanInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
