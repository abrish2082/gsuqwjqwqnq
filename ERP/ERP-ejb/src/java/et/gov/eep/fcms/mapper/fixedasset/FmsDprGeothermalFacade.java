/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.fixedasset;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;
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
public class FmsDprGeothermalFacade extends AbstractFacade<FmsDprGeothermal> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;


        public FmsDprGeothermalFacade() {
        super(FmsDprGeothermal.class);
    }
        
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    public List<FmsDprGeothermal> findStatus1() {
        
        Query query = em.createNamedQuery("FmsDprGeothermal.findByStatus");
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<FmsDprGeothermal> fetchGeothermal(FmsDprGeothermal id){
        Query query = em.createNamedQuery("FmsDprGeothermal.findByDprGeothermalId");
        query.setParameter("dprGeothermalId", id.getDprGeothermalId());
        try {
            return query.getResultList();
            }
        catch (Exception ex) {
            throw ex;
        }
    }
}
