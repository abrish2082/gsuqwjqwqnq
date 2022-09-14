/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsAccountUseTempData;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author memube
 */
@Stateless
public class FmsAccountUseTempDataFacade extends AbstractFacade<FmsAccountUseTempData> {
 
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsAccountUseTempDataFacade() {
        super(FmsAccountUseTempData.class);
    }

    public List<FmsAccountUseTempData> getBillingTransaction() {
        Query query = em.createNamedQuery("FmsAccountUseTempData.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<FmsAccountUseTempData>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
}
