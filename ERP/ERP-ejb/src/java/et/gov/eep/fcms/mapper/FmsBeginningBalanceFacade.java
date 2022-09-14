/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsBeginningBalance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class FmsBeginningBalanceFacade extends AbstractFacade<FmsBeginningBalance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBeginningBalanceFacade() {
        super(FmsBeginningBalance.class);
    }

//    public FmsBeginningBalance beginningBalance(FmsBeginningBalance beginningBalance) {
//        Query query = em.createNamedQuery("FmsBeginningBalance.findByGlCode_Period");
//        query.setParameter("glCode", beginningBalance.getGlCode());
//        query.setParameter("acountigPeriodId", beginningBalance.getAccountigPeriodId());
//
//        System.out.println("serian =================" + beginningBalance.getGlCode());
//        try {
//            FmsBeginningBalance fmsBeginningBalance = (FmsBeginningBalance) (query.getResultList().get(0));
//            return fmsBeginningBalance;
//        } catch (Exception ex) {
//            // ex.printStackTrace();
//            System.out.println("not fund");
//            return null;
//        }
//    }
    public FmsBeginningBalance beginningBalancesearch(FmsBeginningBalance beginningBalance) {
        Query query = em.createNamedQuery("FmsBeginningBalance.findByGlCode_Period");
        query.setParameter("generalLedgerId", beginningBalance.getSlCode().getGeneralLedgerId());
        query.setParameter("accountigPeriodId", beginningBalance.getAccountigPeriodId());
        try {
            FmsBeginningBalance fmsBeginningBalance = (FmsBeginningBalance) (query.getResultList().get(0));
            return fmsBeginningBalance;
        } catch (Exception ex) {
            // ex.printStackTrace();
            return null;
        }
    }
}
