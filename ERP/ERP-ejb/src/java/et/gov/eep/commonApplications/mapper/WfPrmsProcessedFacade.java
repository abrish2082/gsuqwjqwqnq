/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora1
 */
@Stateless
public class WfPrmsProcessedFacade extends AbstractFacade<WfPrmsProcessed> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WfPrmsProcessedFacade() {
        super(WfPrmsProcessed.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    // get WorkFlow info by Purchase Request Id
    public WfPrmsProcessed getWorkFlowInfoByPRId(PrmsPurchaseRequest eepPurchaseRequest) {
        Query q = em.createNamedQuery("WfPrmsProcessed.findByPurchaseReqId", WfPrmsProcessed.class);
        q.setParameter("purchaseReqId", eepPurchaseRequest.getId());
        try {
            WfPrmsProcessed wfPrmsProcessed = new WfPrmsProcessed();
            if (q.getResultList().size() > 0) {
                wfPrmsProcessed = (WfPrmsProcessed) q.getResultList().get(0);
            }
            return wfPrmsProcessed;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

}
