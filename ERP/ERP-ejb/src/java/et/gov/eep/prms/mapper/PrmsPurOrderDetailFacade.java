/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import javax.persistence.Query;
/**
 *
 * @author user
 */
@Stateless
public class PrmsPurOrderDetailFacade extends AbstractFacade<PrmsPurOrderDetail> {
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
    public PrmsPurOrderDetailFacade() {
        super(PrmsPurOrderDetail.class);
    }

    public PrmsPurOrderDetail findInfoByMaterialId(MmsItemRegistration itemRegistrationEntity) {
        
       Query query = em.createNamedQuery("PrmsPurOrderDetail.findByMaterialId", PrmsPurOrderDetail.class);
      query.setParameter("itemId", itemRegistrationEntity);
        //Query query1 = em.createNativeQuery("SELECT * From PrmsPurOrderDetail where ITEM_ID='"+itemRegistrationEntity.getMaterialId()+"'",PrmsPurOrderDetail.class);

        try {
            PrmsPurOrderDetail importationInfo = (PrmsPurOrderDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }
    
}
