package et.gov.eep.fcms.mapper.Ifrs;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentDetail;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
/**
 *
 * @author mz
 */
@Stateless
public class FinancialInstrumentDetailFacade extends AbstractFacade<FinancialInstrumentDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FinancialInstrumentDetailFacade() {
        super(FinancialInstrumentDetail.class);
    }
/*named query to select FinancialInstrumentDetail value from FinancialInstrumentDetail table using financialInstrumentId */
    public FinancialInstrumentDetail getFinancialInstRegDetInfo(FinancialInstrumentRegister finInstrumentRegister) {
        Query query = em.createNamedQuery("FinancialInstrumentDetail.findByFinancialInstrumentId");
        query.setParameter("financialInstrumentId", finInstrumentRegister);
        FinancialInstrumentDetail instrumentDetInfo = new FinancialInstrumentDetail();
        try {
            if (query.getResultList().size() > 0) {
                instrumentDetInfo = (FinancialInstrumentDetail) query.getResultList().get(0);
            }
            return instrumentDetInfo;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

    }

}
