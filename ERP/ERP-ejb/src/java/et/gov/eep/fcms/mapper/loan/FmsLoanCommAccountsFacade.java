/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.loan.FmsLoanCommAccounts;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PO
 */
@Stateless
public class FmsLoanCommAccountsFacade extends AbstractFacade<FmsLoanCommAccounts> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLoanCommAccountsFacade() {
        super(FmsLoanCommAccounts.class);
    }
    
}
