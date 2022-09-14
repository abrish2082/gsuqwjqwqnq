/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsAccounts;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora1
 */
@Stateless
public class FmsAccountsFacade extends AbstractFacade<FmsAccounts> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsAccountsFacade() {
        super(FmsAccounts.class);
    }

    public List<FmsAccounts> fmsAccountses(FmsAccounts fmsAccounts) {
        List<FmsAccounts> fmsAccountses;
        fmsAccountses = null;
        try {
            Query query = em.createNamedQuery("FmsAccounts.findByAccountsCode");
            query.setParameter("accountsCode", fmsAccounts.getAccountsCode() + "%");
            fmsAccountses = (List<FmsAccounts>) query.getResultList();
            return fmsAccountses;

        } catch (Exception e) {
            return null;
        }

    }
    
    public FmsAccounts fmsAccountsDitle(FmsAccounts fmsAccounts) {
        FmsAccounts fmsAccount;
        fmsAccount = null;
        try {
            Query query = em.createNamedQuery("FmsAccounts.findByAccountsCode");
            query.setParameter("accountsCode", fmsAccounts.getAccountsCode());
            fmsAccount = (FmsAccounts) query.getResultList().get(0);
            return fmsAccount;

        } catch (Exception e) {
            return null;
        }

    }
     public FmsAccounts fmsAccountsbyID(FmsAccounts fmsAccounts) {
        FmsAccounts fmsAccount;
        fmsAccount = null;
        try {
            Query query = em.createNamedQuery("FmsAccounts.findByAccountsId");
            query.setParameter("accountsId", fmsAccounts.getAccountsId());
            fmsAccount = (FmsAccounts) query.getResultList().get(0);
            return fmsAccount;

        } catch (Exception e) {
            return null;
        }

    }

     public FmsAccounts fmsAccountsbyCode(FmsAccounts fmsAccounts) {
        FmsAccounts fmsAccount;
        fmsAccount = null;
        try {
            Query query = em.createNamedQuery("FmsAccounts.findByAccountsCode");
            query.setParameter("accountsCode", fmsAccounts.getAccountsCode());
            fmsAccount = (FmsAccounts) query.getResultList().get(0);
            return fmsAccount;

        } catch (Exception e) {
            return null;
        }

    }

}
