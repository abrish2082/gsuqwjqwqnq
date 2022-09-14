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
import et.gov.eep.fcms.entity.admin.FmsAccountType;

/**
 *
 * @author memube
 */
@Stateless
public class FmsAccountTypeFacade extends AbstractFacade<FmsAccountType> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsAccountTypeFacade() {
        super(FmsAccountType.class);
    }

    //find by account type return dup
    public boolean findDupByAcctType(FmsAccountType fmsAccountType) {
        boolean dup;
        try {
            Query query = em.createNamedQuery("FmsAccountType.findByType");
            query.setParameter("type", fmsAccountType.getType());
            if (query.getResultList().size() > 0) {
                dup = true;
            } else {
                dup = false;
            }
            return dup;
        } catch (Exception ex) {
            return false;
        }
    }
}
