/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.lookup;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuRegions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrLuRegionsFacade extends AbstractFacade<HrLuRegions> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLuRegionsFacade() {
        super(HrLuRegions.class);
    }

    public HrLuRegions findById(HrLuRegions hrLuRegions) {
        Query query = em.createNamedQuery("HrLuRegions.findById", HrLuRegions.class);
        query.setParameter("id", hrLuRegions.getId());
        try {
            return (HrLuRegions) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
