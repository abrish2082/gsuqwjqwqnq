package et.gov.eep.fcms.mapper.perDiem;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;

/**
 *
 * @author muller
 */
@Stateless
public class FmsGoodWillingPaymentFacade extends AbstractFacade<FmsGoodWillingPayment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsGoodWillingPaymentFacade() {
        super(FmsGoodWillingPayment.class);
    }
    
 /*named query to select all goodwill info from FmsGoodWillingPayment table 
     returen goodwill info*/
    public FmsGoodWillingPayment searchAll(FmsGoodWillingPayment fmsGoodWillingPayment) {
        Query query = em.createNamedQuery("FmsGoodWillingPayment.findAll", FmsGoodWillingPayment.class);
        try {
            FmsGoodWillingPayment GoodWill = (FmsGoodWillingPayment) query.getResultList().get(0);
            return GoodWill;
        } catch (Exception ex) {
            return null;
        }
    }

}
