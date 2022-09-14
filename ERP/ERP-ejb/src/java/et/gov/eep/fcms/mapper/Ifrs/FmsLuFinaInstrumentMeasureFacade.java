package et.gov.eep.fcms.mapper.Ifrs;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinaInstrumentMeasure;

/**
 *
 * @author mz
 */
@Stateless
public class FmsLuFinaInstrumentMeasureFacade extends AbstractFacade<FmsLuFinaInstrumentMeasure> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuFinaInstrumentMeasureFacade() {
        super(FmsLuFinaInstrumentMeasure.class);
    }
    /*named query to select all from FmsLuFinaInstrumentMeasure table*/

    public ArrayList<FmsLuFinaInstrumentMeasure> searchfinIntMeasureList(FmsLuFinaInstrumentMeasure finInstMeasure) {
        Query query = em.createNamedQuery("FmsLuFinaInstrumentMeasure.findAll");
        try {
            ArrayList<FmsLuFinaInstrumentMeasure> finIntMeasureList = new ArrayList(query.getResultList());
            return finIntMeasureList;
        } catch (Exception ex) {
            return null;
        }
    }
    /*native query to select all fininstMeasureList value from FmsLuFinaInstrumentMeasure table with requierements */

    public List<FmsLuFinaInstrumentMeasure> defualtAndPL() {
        Query query = em.createNativeQuery("select * from FMS_LU_FINA_INSTRUMENT_MEASURE where NAME='FVTPL'OR NAME='Amortized Cost'", FmsLuFinaInstrumentMeasure.class);
        List<FmsLuFinaInstrumentMeasure> fininstMeasureList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            fininstMeasureList = query.getResultList();
        }
        return fininstMeasureList;
    }

}
