package et.gov.eep.fcms.mapper.Ifrs;

;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinanInstrumentType;

/**
 *
 * @author mz
 */


@Stateless
public class FmsLuFinanInstrumentTypeFacade extends AbstractFacade<FmsLuFinanInstrumentType> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuFinanInstrumentTypeFacade() {
        super(FmsLuFinanInstrumentType.class);
    }

    /*named query to select all from FmsLuFinanInstrumentType table*/
    public ArrayList<FmsLuFinanInstrumentType> searchFinInstType(FmsLuFinanInstrumentType finInstType) {
        Query query = em.createNamedQuery("FmsLuFinanInstrumentType.findAll");
        try {
            ArrayList<FmsLuFinanInstrumentType> finInstTypeList = new ArrayList(query.getResultList());
            return finInstTypeList;
        } catch (Exception ex) {
            return null;
        }
    }

}
