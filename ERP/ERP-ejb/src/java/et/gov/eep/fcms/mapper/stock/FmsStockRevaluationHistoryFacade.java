package et.gov.eep.fcms.mapper.stock;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.stock.FmsStockRevaluationHistory;

/**
 *
 * @author memube
 */
@Stateless
public class FmsStockRevaluationHistoryFacade extends AbstractFacade<FmsStockRevaluationHistory> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsStockRevaluationHistoryFacade() {
        super(FmsStockRevaluationHistory.class);
    }

}
