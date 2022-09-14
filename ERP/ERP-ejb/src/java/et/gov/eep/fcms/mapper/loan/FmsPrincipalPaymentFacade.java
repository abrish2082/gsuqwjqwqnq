/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.fcms.entity.loan.FmsPrincipalPayment;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.loan.FmsLoan;


/**
 *
 * @author Binyam 
 */
@Stateless
public class FmsPrincipalPaymentFacade extends AbstractFacade<FmsPrincipalPayment> {
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
    public FmsPrincipalPaymentFacade() {
        super(FmsPrincipalPayment.class);
    }
    
    /**
     *
     * @param loanId
     * @return
     */
    public ArrayList<FmsPrincipalPayment> fetchPaySchedule(FmsPrincipalPayment loanId){
        Query query = em.createNamedQuery("FmsPrincipalPayment.findByLoanId");
        query.setParameter("loanId", loanId.getLoanId());
        try {
           ArrayList <FmsPrincipalPayment> loanInfo = new ArrayList (query.getResultList());
            return loanInfo;
            }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     *
     * @param loanId
     * @return
     */
    public ArrayList<FmsPrincipalPayment> scheduleLister(FmsPrincipalPayment loanId){
        Query query = em.createNamedQuery("FmsPrincipalPayment.findByLoanId");
        query.setParameter("loanId", loanId.getLoanId());
        try {
           ArrayList <FmsPrincipalPayment> loanInfo = new ArrayList (query.getResultList());
            return loanInfo;
            }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<FmsPrincipalPayment> fetchPricipalPayment(FmsLoan fmsLoan){
        Query query = em.createNamedQuery("FmsPrincipalPayment.findByLoanId");
        query.setParameter("loanId", fmsLoan);
        try {
           ArrayList <FmsPrincipalPayment> loanInfo = new ArrayList (query.getResultList());
            return loanInfo;
            }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
