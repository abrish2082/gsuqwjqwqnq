/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
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
public class PrmsBidSubmissionDetailFacade extends AbstractFacade<PrmsBidSubmissionDetail> {

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
    public PrmsBidSubmissionDetailFacade() {
        super(PrmsBidSubmissionDetail.class);
    }

// <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public List<PrmsBidSubmissionDetail> getSubmList(String bidNo) {
        List<PrmsBidSubmissionDetail> bidSumList = null;
        try {
            Query query = em.createNamedQuery("PrmsBidSubmissionDetail.findByBidNo", PrmsBidSubmissionDetail.class);
            query.setParameter("refNo", bidNo);
            bidSumList = (List<PrmsBidSubmissionDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidSumList;
    }
    // </editor-fold>

}
