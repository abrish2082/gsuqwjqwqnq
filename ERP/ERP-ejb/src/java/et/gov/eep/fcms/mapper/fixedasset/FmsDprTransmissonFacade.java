/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.fixedasset;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
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
public class FmsDprTransmissonFacade extends AbstractFacade<FmsDprTransmisson> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

        public FmsDprTransmissonFacade() {
        super(FmsDprTransmisson.class);
    }
        
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public List<FmsDprTransmisson> fetchTransmission(FmsDprTransmisson id) {
        Query query = em.createNamedQuery("FmsDprTransmisson.findByDprTransmissionId");
        query.setParameter("dprTransmissionId", id.getDprTransmissionId());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<FmsDprTransmisson> searchByTrId(FmsDprTransmisson transDprEntity) {

        Query query = em.createNamedQuery("FmsDprTransmisson.findBytrAssetIdLike");
        query.setParameter("trAssetId", transDprEntity.getDprTransmissionId() + '%');
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }

    }

    public List<FmsDprTransmisson> findStatus1() {

        Query query = em.createNamedQuery("FmsDprTransmisson.findByStatus");
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
