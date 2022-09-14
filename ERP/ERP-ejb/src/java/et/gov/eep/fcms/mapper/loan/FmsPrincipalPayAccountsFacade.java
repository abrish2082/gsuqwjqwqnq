/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayAccounts;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PO
 */
@Stateless
public class FmsPrincipalPayAccountsFacade extends AbstractFacade<FmsPrincipalPayAccounts> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsPrincipalPayAccountsFacade() {
        super(FmsPrincipalPayAccounts.class);
    }
    
}
