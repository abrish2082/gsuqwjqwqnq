/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.fcms.entity.loan.FmsLoanCommitment;
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
public class FmsLoanCommitmentFacade extends AbstractFacade<FmsLoanCommitment> {
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
    public FmsLoanCommitmentFacade() {
        super(FmsLoanCommitment.class);
    }
    
    /**
     *
     * @param fmsLoanCommitment
     * @return
     */
    public ArrayList<FmsLoanCommitment> fetchCommitment(FmsLoanCommitment fmsLoanCommitment){
        Query query = em.createNamedQuery("FmsLoanCommitment.findByLoanId");
        query.setParameter("loanId", fmsLoanCommitment.getLoanId());
        try {
           ArrayList <FmsLoanCommitment> loanInfo = new ArrayList (query.getResultList());
            return loanInfo;
            }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
