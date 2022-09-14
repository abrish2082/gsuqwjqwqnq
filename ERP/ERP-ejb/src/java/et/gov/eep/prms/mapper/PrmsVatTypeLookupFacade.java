/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;

@Stateless
public class PrmsVatTypeLookupFacade extends AbstractFacade<PrmsLuVatTypeLookup> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsVatTypeLookupFacade() {
        super(PrmsLuVatTypeLookup.class);
    }

    public List<PrmsLuVatTypeLookup> findVatType() {
        Query query = em.createNamedQuery("PrmsLuVatTypeLookup.findAll");
        try {
            List<PrmsLuVatTypeLookup> vatType = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                vatType = query.getResultList();
            }
            return vatType;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
