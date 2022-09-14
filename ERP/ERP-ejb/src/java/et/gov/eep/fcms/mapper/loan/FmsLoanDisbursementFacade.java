/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.fcms.entity.loan.FmsLoanDisbursement;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.util.ArrayList;
import javax.persistence.Query;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsLoanDisbursementFacade extends AbstractFacade<FmsLoanDisbursement> {
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
    public FmsLoanDisbursementFacade() {
        super(FmsLoanDisbursement.class);
    }
    
    /**
     *
     * @param loanId
     * @return
     */
    public ArrayList<FmsLoanDisbursement> fetch(FmsLoanDisbursement loanId){
        Query query = em.createNamedQuery("FmsLoanDisbursement.findByLoanId");
        query.setParameter("loanId", loanId.getLoanId());
        try {
           ArrayList <FmsLoanDisbursement> loanInfo = new ArrayList (query.getResultList());
            return loanInfo;
            }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
