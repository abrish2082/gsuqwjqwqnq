package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.fcms.entity.Bond.FmsBondLibor;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.commonApplications.mapper.AbstractFacade;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondLiborFacade extends AbstractFacade<FmsBondLibor> {

    //inject entities
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    FmsBondLibor fmsBondLibor;
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondLiborFacade() {
        super(FmsBondLibor.class);
    }

    /*named query for finding libor value from Bond libor table using day(between start and end date) */
    public FmsBondLibor searchday(FmsBondLibor BondLibor) {
        Query query = em.createNamedQuery("FmsBondLibor.findByEndDateAndStartDate");
        FmsBondLibor libor = null;
        query.setParameter("day", BondLibor.getDay());
        try {
            if (query.getResultList().size() > 0) {
                libor = (FmsBondLibor) query.getResultList();
            }
            return libor;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query for finding libor value from Bond libor table using currency and day(start and end date) */
    public FmsBondLibor searchdays(Date day, String currency) {
        Query query = em.createNamedQuery("FmsBondLibor.findByEndDateAndStart");
        FmsBondLibor libor = new FmsBondLibor();
        FmsLuCurrency fmsLuCurrency = new FmsLuCurrency();
        fmsLuCurrency.setCurrencyId(currency);
        query.setParameter("day", day);
        query.setParameter("BondCurrency", fmsLuCurrency);
        try {
            if (query.getResultList().size() > 0) {
                libor = (FmsBondLibor) query.getResultList().get(0);
                return libor;
            } else {
                libor = null;
                return libor;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query for finding libor value from Bond libor table using Libor number */
    public FmsBondLibor searchLiborNo(FmsBondLibor BondLibor) {
        Query query = em.createNamedQuery("FmsBondLibor.findByLiborNo");
        query.setParameter("liborNo", BondLibor.getLiborNo());
        try {
            FmsBondLibor LiborList = (FmsBondLibor) query.getResultList().get(0);
            return LiborList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    /*named query for finding libor list from Bond libor table using startdate */
    public ArrayList<FmsBondLibor> searchStartdate(Date day) {
        Query query = em.createNamedQuery("FmsBondLibor.findByStartDate");
        query.setParameter("startDate", day);
        try {
            ArrayList<FmsBondLibor> LiborList = new ArrayList(query.getResultList());
            return LiborList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /*named query for finding libor list from Bond libor table using currency */

    public ArrayList<FmsBondLibor> searchCrance(FmsBondLibor BondLibor) {
        Query query = em.createNamedQuery("FmsBondLibor.findByCurrency");
        query.setParameter("BondCurrency", BondLibor.getFmsLuCurrency());
        try {
            ArrayList<FmsBondLibor> LiborList = new ArrayList(query.getResultList());
            return LiborList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query for finding libor value from Bond libor table using libor rate */
    public ArrayList<FmsBondLibor> searchLiborRate(FmsBondLibor BondLibor) {
        Query query = em.createNamedQuery("FmsBondLibor.findByLiborRate");
        query.setParameter("liborRate", BondLibor.getLiborRate());
        try {
            ArrayList<FmsBondLibor> LiborList = new ArrayList(query.getResultList());
            return LiborList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query for finding libor value from Bond libor table using currency and day(start and end date) */
    public ArrayList<FmsBondLibor> searchByDateAndCurrncy(FmsBondLibor BondLibor) {
        Query query = em.createNamedQuery("FmsBondLibor.findByEndDateAndStart");
        query.setParameter("day", BondLibor.getDay());
        query.setParameter("BondCurrency", BondLibor.getFmsLuCurrency());
        try {

            ArrayList<FmsBondLibor> LiborList = new ArrayList(query.getResultList());
            return LiborList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query for finding libor value from Bond libor table using day(between start and end date) */
    public ArrayList<FmsBondLibor> searchLibor(FmsBondLibor BondLibor) {
        Query query = em.createNamedQuery("FmsBondLibor.findByEndDateAndStartDate");
        query.setParameter("day", BondLibor.getDay());
        try {

            ArrayList<FmsBondLibor> LiborList = new ArrayList(query.getResultList());
            return LiborList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
