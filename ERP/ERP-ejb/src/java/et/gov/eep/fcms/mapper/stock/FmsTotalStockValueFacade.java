package et.gov.eep.fcms.mapper.stock;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.stock.FmsTotalStockValue;

/**
 *
 * @author Terminal2
 */
@Stateless
public class FmsTotalStockValueFacade extends AbstractFacade<FmsTotalStockValue> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsTotalStockValueFacade() {
        super(FmsTotalStockValue.class);
    }
    
    /*named query to select itemInformationsfrom FmsTotalStockValue table by material code 
     passing parameter of matcode
     returen itemInformations*/
    public FmsTotalStockValue getWeightedAverageValueByMatCode(String matcode) {
        Query query = em.createNamedQuery("FmsTotalStockValue.findByMaterialCode", FmsTotalStockValue.class);
        query.setParameter("materialCode", matcode);
        try {

            FmsTotalStockValue itemInformations = (FmsTotalStockValue) query.getSingleResult();
            return itemInformations;

        } catch (Exception ex) {
            return null;
        }
    }

}
