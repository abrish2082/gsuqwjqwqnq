package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondType;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondTypeFacade extends AbstractFacade<FmsBondType> {

    @PersistenceContext(unitName = "ERP-ejbPU")

    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondTypeFacade() {
        super(FmsBondType.class);
    }
/*named query for finding Bond type list id from Bond type table by BondTyperId first letter or number*/
    public ArrayList<FmsBondType> searchFmsBondTypeId(FmsBondType Type) {
        Query query = em.createNamedQuery("FmsBondType.findByBondTypeIdLike");
        query.setParameter("BondTypeId", Type.getBondTypeId() + '%');
        try {
            ArrayList<FmsBondType> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            return null;
        }
    }
    
/*named query for finding Bond type list id from Bond type table by BondType's first letter or number*/
    public ArrayList<FmsBondType> searchFmsBondType(FmsBondType Type) {
        Query query = em.createNamedQuery("FmsBondType.findByBondType");
        query.setParameter("BondType", Type.getBondType() + '%');
        try {
            ArrayList<FmsBondType> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            return null;
        }
    }
    
/*named query for finding Bond type id from Bond type table by BondTyperId*/
    public FmsBondType getFmsBondTypeInfo(FmsBondType Type) {
        Query query = em.createNamedQuery("FmsBondType.findByBondTypeId");
        query.setParameter("BondTypeId", Type.getBondTypeId());
        try {
            FmsBondType BondTypeInfo = (FmsBondType) query.getResultList().get(0);
            return BondTypeInfo;

        } catch (Exception ex) {
            return null;
        }
    }

}
