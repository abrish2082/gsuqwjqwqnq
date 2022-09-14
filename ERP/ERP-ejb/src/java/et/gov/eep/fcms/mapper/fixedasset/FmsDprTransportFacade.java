/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.fixedasset;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransport;
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
public class FmsDprTransportFacade extends AbstractFacade<FmsDprTransport> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    
    public FmsDprTransportFacade() {
        super(FmsDprTransport.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    public List<FmsDprTransport> fetchTransport(FmsDprTransport id){
        Query query = em.createNamedQuery("FmsDprTransport.findByDprTransportId");
        query.setParameter("dprTransportId", id.getDprTransportId());
        try {
            return query.getResultList();
            }
        catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<FmsDprTransport> findStatus1() {
        
        Query query = em.createNamedQuery("FmsDprTransport.findByStatus");
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
}
