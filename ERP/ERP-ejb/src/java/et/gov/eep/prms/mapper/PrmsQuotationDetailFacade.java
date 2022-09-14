/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsQuotationDetailFacade extends AbstractFacade<PrmsQuotationDetail> {

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
    public PrmsQuotationDetailFacade() {
        super(PrmsQuotationDetail.class);
    }

    public List<PrmsPurchaseRequestDet> getRequestLists(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        ArrayList<PrmsPurchaseRequestDet> accountses = null;
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByPrNo");
        query.setParameter("materialCodeId", prmsPurchaseRequestDet.getMaterialCodeId());

        try {
            accountses = new ArrayList(query.getResultList());
            System.out.println("=======Account List=========" + accountses);
            return accountses;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequestDet> getPrNoItem() {
        List<PrmsPurchaseRequestDet> details = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByItemNameItem", PrmsPurchaseRequestDet.class);
            details = (List<PrmsPurchaseRequestDet>) query.getResultList();
            System.out.println("-----------------------getPrNoItem------------" + details.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return details;
    }

    public List<PrmsPurchaseRequestDet> getPrNoService() {
        List<PrmsPurchaseRequestDet> details = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByItemNameItem", PrmsPurchaseRequestDet.class);
            details = (List<PrmsPurchaseRequestDet>) query.getResultList();
            System.out.println("--------------------getPrNoService-----------------" + details.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return details;
    }

    public PrmsPurchaseRequestDet getPrLsts(String prList) {
        System.out.println("yyyyin==========" + prList);
        PrmsPurchaseRequestDet supplierName = null;
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findById", PrmsPurchaseRequestDet.class);
        query.setParameter("id", prList);
        try {
            if (query.getResultList().size() > 0) {
                supplierName = (PrmsPurchaseRequestDet) query.getResultList().get(0);
            }
            System.out.println("yyyyout==========" + supplierName);
            return supplierName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
     public List<PrmsQuotationDetail> FindByForienId(PrmsSupplyProfile eepVendorReg) {
         System.out.println("in"+eepVendorReg.getVendorName());
         try {
            Query query = em.createNamedQuery("PrmsQuotationDetail.FindByForienID", PrmsSupplyProfile.class);
            query.setParameter("supplyId", eepVendorReg.getVendorName());
            System.out.println("result LIST========"+query.getResultList());
            return (List<PrmsQuotationDetail>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}



