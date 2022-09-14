/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.fixedasset;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
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
public class FmsDprBuildingFacade extends AbstractFacade<FmsDprBuilding> {
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
    public FmsDprBuildingFacade() {
        super(FmsDprBuilding.class);
    }
    
    public List<FmsDprBuilding> fetchBuilding(FmsDprBuilding id){
        Query query = em.createNamedQuery("FmsDprBuilding.findByDprBuildingId");
        query.setParameter("dprBuildingId", id.getDprBuildingId());
        try {
            return query.getResultList();
            }
        catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<FmsDprBuilding> findStatus1() {
        
        Query query = em.createNamedQuery("FmsDprBuilding.findByStatus");
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
