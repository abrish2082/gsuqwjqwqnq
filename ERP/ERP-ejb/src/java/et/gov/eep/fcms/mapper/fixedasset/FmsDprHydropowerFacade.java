/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.fixedasset;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
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
public class FmsDprHydropowerFacade extends AbstractFacade<FmsDprHydropower> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    public FmsDprHydropowerFacade() {
        super(FmsDprHydropower.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<FmsDprHydropower> fetchDprHydropowers(FmsDprHydropower id) {
        Query query = em.createNamedQuery("FmsDprHydropower.findByDprHydropowerId");
        query.setParameter("dprHydropowerId", id.getDprHydropowerId());
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<FmsDprHydropower> findStatus1() {

        Query query = em.createNamedQuery("FmsDprHydropower.findByStatus");
        query.setParameter("status", 1);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
