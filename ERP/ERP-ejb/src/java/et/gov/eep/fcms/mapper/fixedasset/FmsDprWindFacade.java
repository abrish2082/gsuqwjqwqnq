/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.fixedasset;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprWind;
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
public class FmsDprWindFacade extends AbstractFacade<FmsDprWind> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    public FmsDprWindFacade() {
        super(FmsDprWind.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @param id
     * @return 
     */
    public List<FmsDprWind> fetchWind(FmsDprWind id) {
        Query query = em.createNamedQuery("FmsDprWind.findByDprWindId");
        query.setParameter("dprWindId", id.getDprWindId());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<FmsDprWind> findStatus1() {

        Query query = em.createNamedQuery("FmsDprWind.findByStatus");
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
