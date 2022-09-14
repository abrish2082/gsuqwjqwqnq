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
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;

/**
 *
 * @author userPCAdmin
 */
@Stateless
public class FmsLuVouchersTypeFacade extends AbstractFacade<FmsLuVouchersType> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

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
     */
    public FmsLuVouchersTypeFacade() {
        super(FmsLuVouchersType.class);
    }

    /**
     *
     * @param vouchersType
     * @return luVouchersType
     */
    public FmsLuVouchersType searchPaymentType(FmsLuVouchersType vouchersType) {
        Query query = em.createNamedQuery("FmsLuVouchersType.findByTypeName");
        query.setParameter("typeName", vouchersType.getTypeName());
        try {
            if (query.getResultList().size() > 0) {
                FmsLuVouchersType luVouchersType = (FmsLuVouchersType) query.getResultList().get(0);
                return luVouchersType;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
