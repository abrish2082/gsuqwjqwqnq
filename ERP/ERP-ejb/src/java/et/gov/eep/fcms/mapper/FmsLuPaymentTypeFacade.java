/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora1
 */
@Stateless
public class FmsLuPaymentTypeFacade extends AbstractFacade<FmsLuPaymentType> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuPaymentTypeFacade() {
        super(FmsLuPaymentType.class);
    }
        public List<FmsLuPaymentType> findByPaymentType(FmsLuPaymentType luPaymentType) {
        Query query = em.createNamedQuery("FmsLuPaymentType.findByPayment");
        query.setParameter("payment", luPaymentType.getPayment());
            System.out.println("payment" + luPaymentType.getPayment());
        try {
            List<FmsLuPaymentType> bondTypeList = new ArrayList(query.getResultList()) {};
            return bondTypeList;
        } catch (Exception ex) {
            // ex.printStackTrace();
            System.out.println("not fund");
            return null;
        }
    }
}
