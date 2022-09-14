
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.admin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.pms.entity.PmsWorkAuthorization;

/**
 *
 * @author user
 */
@Stateless
public class FmsSystemJObJunctionFacade extends AbstractFacade<FmsSystemJobJunction> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    public FmsSystemJObJunctionFacade() {
        super(FmsSystemJobJunction.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

     /**
     *
     * @param fmsLuSystem
     * @paramb pmsWorkAuthorization
     * @return authorized fmsLuSystem
     */
    public FmsSystemJobJunction findBySSPMSId(FmsLuSystem fmsLuSystem, PmsWorkAuthorization pmsWorkAuthorization) {
        Query query = em.createNamedQuery("FmsSystemJobJunction.findBySSPMSId", FmsSystemJobJunction.class);
        query.setParameter("fmsLuSystem", fmsLuSystem);
        query.setParameter("workAuthoFk", pmsWorkAuthorization);
        try {
            FmsSystemJobJunction result = (FmsSystemJobJunction) query.getSingleResult();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

}
