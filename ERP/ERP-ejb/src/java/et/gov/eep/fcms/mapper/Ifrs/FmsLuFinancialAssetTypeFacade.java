package et.gov.eep.fcms.mapper.Ifrs;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinancialAssetType;

/**
 *
 * @author mz
 */
@Stateless
public class FmsLuFinancialAssetTypeFacade extends AbstractFacade<FmsLuFinancialAssetType> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuFinancialAssetTypeFacade() {
        super(FmsLuFinancialAssetType.class);
    }

    /*named query to select all from FmsLuFinancialAssetType table*/
    public ArrayList<FmsLuFinancialAssetType> searchFinAssetType(FmsLuFinancialAssetType finAssetType) {
        Query query = em.createNamedQuery("FmsLuFinancialAssetType.findAll");
        try {
            ArrayList<FmsLuFinancialAssetType> finAssetTypeList = new ArrayList(query.getResultList());
            return finAssetTypeList;
        } catch (Exception ex) {
            return null;
        }
    }

}
