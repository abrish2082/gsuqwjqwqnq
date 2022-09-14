/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.fcms.entity.FmsWithHoldingVoucher;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.util.ArrayList;
import javax.persistence.Query;

/**
 *
 * @author AB
 */
@Stateless
public class FmsWithHoldingVoucherFacade extends AbstractFacade<FmsWithHoldingVoucher> {

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
    public FmsWithHoldingVoucherFacade() {
        super(FmsWithHoldingVoucher.class);
    }

    /**
     *
     * @param withHoldingVoucher
     * @return
     */
    public List<FmsWithHoldingVoucher> searchWithHoldVoucheid(FmsWithHoldingVoucher withHoldingVoucher) {

        List<FmsWithHoldingVoucher> WithHoldingList = null;
        try {
            Query query = em.createNamedQuery("FmsWithHoldingVoucher.findByVoucherId", FmsWithHoldingVoucher.class);
            query.setParameter("voucherId", withHoldingVoucher.getFmsVOUCHERID() + "%");
            WithHoldingList = (List<FmsWithHoldingVoucher>) query.getResultList();
            return WithHoldingList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param withHoldingVoucher
     * @return
     */
    public FmsWithHoldingVoucher getSearchWithHoldingVoucherID(FmsWithHoldingVoucher withHoldingVoucher) {
        Query query = em.createNamedQuery("FmsWithHoldingVoucher.findByVoucherId", FmsWithHoldingVoucher.class);
        query.setParameter("voucherId", withHoldingVoucher.getFmsVOUCHERID());
        try {
            FmsWithHoldingVoucher withHoldingVoucher1 = (FmsWithHoldingVoucher) query.getResultList().get(0);
            return withHoldingVoucher1;
        } catch (Exception ex) {

            return null;
        }

    }

    public List<FmsChequePaymentVoucher> getchequePaymentNoList() {
        Query q = em.createNamedQuery("FmsChequePaymentVoucher.findAll");
        List<FmsChequePaymentVoucher> cheqPayNoList = new ArrayList<>();
        cheqPayNoList = q.getResultList();
        return cheqPayNoList;
    }



}
