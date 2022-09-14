/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.fixedasset;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprOfficeAssetFacade extends AbstractFacade<FmsDprOfficeAsset> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    public FmsDprOfficeAssetFacade() {
        super(FmsDprOfficeAsset.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<FmsDprOfficeAsset> fetchOfficeAsset(FmsDprOfficeAsset id) {
        Query query = em.createNamedQuery("FmsDprOfficeAsset.findByDprWindId");
        query.setParameter("dprOfficeId", id.getDprOfficeId());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<FmsDprOfficeAsset> findStatus1() {

        Query query = em.createNamedQuery("FmsDprOfficeAsset.findByStatus");
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public FmsDprOfficeAsset findByTagandStatus(FmsDprOfficeAsset fmsDprOfficeAsset) {
        Query query = em.createNamedQuery("FmsDprOfficeAsset.findByTagNoAndStatus");
        query.setParameter("tagNo", fmsDprOfficeAsset.getTagNo());
        query.setParameter("status", 1);
        FmsDprOfficeAsset result;
        try {

            result = (FmsDprOfficeAsset) query.getResultList().get(0);
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
