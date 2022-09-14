/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.fcms.entity.FmsPettyCashPaymentVoucher;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.util.ArrayList;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AB
 */
@Stateless
public class FmsPettyCashPaymentVoucherFacade extends AbstractFacade<FmsPettyCashPaymentVoucher> {
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
    public FmsPettyCashPaymentVoucherFacade() {
        super(FmsPettyCashPaymentVoucher.class);
    }

    /**
     *
     * @param pettyCashPaymentEnty
     * @return
     */
    public FmsPettyCashPaymentVoucher getSearchPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty)
    {
        Query query = em.createNamedQuery("FmsPettyCashPaymentVoucher.findByVOUCHERID", FmsPettyCashPaymentVoucher.class);
        query.setParameter("voucherId", pettyCashPaymentEnty.getFmsvoucherVOUCHERID());
        try {
            FmsPettyCashPaymentVoucher fmsChequePaymentVoucher = (FmsPettyCashPaymentVoucher) query.getResultList().get(0);
            return fmsChequePaymentVoucher;
        } catch (Exception ex) {

            return null;
        }
        
    }
    
    /**
     *
     * @param pettyCashPaymentEnty
     * @return
     */
    public FmsPettyCashPaymentVoucher getPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        Query query = em.createNamedQuery("FmsPettyCashPaymentVoucher.findByPettycashpaymentvoucherID");
        query.setParameter("pettycashpaymentvoucherID", pettyCashPaymentEnty.getPettycashpaymentvoucherID());
        try {
            FmsPettyCashPaymentVoucher fmsPettyPaymentVoucher = (FmsPettyCashPaymentVoucher) query.getResultList().get(0);
            return fmsPettyPaymentVoucher;
        } catch (Exception ex) {

            return null;
        }

    }

    /**
     *
     * @param pettyCashPaymentEnty
     * @return
     */
    public FmsPettyCashPaymentVoucher getApprovedPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        Query query = em.createNamedQuery("FmsPettyCashPaymentVoucher.findByVOUCHERID");       
        query.setParameter("fmsVOUCHERID", pettyCashPaymentEnty.getFmsVOUCHERID());
           System.out.println("haday "+query.getResultList().size());
        try {
            FmsPettyCashPaymentVoucher fmsPettyPaymentVoucher = (FmsPettyCashPaymentVoucher) query.getResultList().get(0);
            return fmsPettyPaymentVoucher;
        } catch (Exception ex) {

            return null;
        }
    }

    /**
     *
     * @param approveStatus
     * @return
     */
    public List<String> getPrepareList(int approveStatus) {

        List<String> ChequePaymentList = null;
        try {
            Query query = em.createNamedQuery("FmsPettyCashPaymentVoucher.findByStatus", FmsPettyCashPaymentVoucher.class);
            query.setParameter("status", approveStatus);
            ChequePaymentList = (List<String>) query.getResultList();
            return ChequePaymentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     *
     * @return
     */
    public List<FmsPettyCashPaymentVoucher> findpityCash() {
            int status=1;
        Query query = em.createNamedQuery("FmsPettyCashPaymentVoucher.findByStatus");
        query.setParameter("status", status);
        try {         
            List<FmsPettyCashPaymentVoucher> VoucherList = new ArrayList(query.getResultList());

            return VoucherList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
       
        
        }
    }
        
    /**
     *
     * @param voucher
     * @return
     */
    public FmsPettyCashPaymentVoucher getPittyCash(FmsPettyCashPaymentVoucher voucher) {
       //accessing e 
            int status=1;
        Query query = em.createNamedQuery("FmsPettyCashPaymentVoucher.getByStatus");
        query.setParameter("fmsVOUCHERID", voucher.getFmsVOUCHERID());
        query.setParameter("status", status);
       
        try {
            FmsPettyCashPaymentVoucher bgtDetailList = (FmsPettyCashPaymentVoucher)query.getResultList().get(0);           
            return bgtDetailList;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
        
        
        
}
