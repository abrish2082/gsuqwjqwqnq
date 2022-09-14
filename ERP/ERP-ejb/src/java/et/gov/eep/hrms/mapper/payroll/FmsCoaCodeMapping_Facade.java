/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;


import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsCoaCodeMapping;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Hanoc
 */
@Stateless
public class FmsCoaCodeMapping_Facade extends AbstractFacade<FmsCoaCodeMapping> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    EntityManager em;

    /**
     *
     */
    public FmsCoaCodeMapping_Facade() {
        super(FmsCoaCodeMapping.class);
    }

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
     * @param type
     * @return
     */
    public List<FmsCoaCodeMapping> getFmsCoaCodeMappingBytype(String type) {
        List<FmsCoaCodeMapping> luServiceTypes;
        try {
            Query query = em.createNamedQuery("FmsCoaCodeMapping.findByType", FmsCoaCodeMapping.class);
            query.setParameter("type", type);
            luServiceTypes = (List<FmsCoaCodeMapping>) query.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }

    }

}
