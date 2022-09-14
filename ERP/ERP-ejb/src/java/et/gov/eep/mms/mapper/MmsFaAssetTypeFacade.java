/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaAssetType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsFaAssetTypeFacade extends AbstractFacade<MmsFaAssetType> {

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
    public MmsFaAssetTypeFacade() {
        super(MmsFaAssetType.class);
    }

 

    public MmsFaAssetType findAssetTypeByAssetTypeId2(MmsFaAssetType assetTypeEntity) {
           Query query = em.createNamedQuery("MmsFaAssetType.findByAssetId");
        query.setParameter("assetId", assetTypeEntity.getAssetId());
        try {
            MmsFaAssetType getAssetType = (MmsFaAssetType) query.getResultList().get(0);
            return getAssetType;
        } catch (Exception ex) {
            return null;
        }
    }

}
