package et.gov.eep.fcms.mapper.perDiem;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.perDiem.FmsLuCountry;

/**
 *
 * @author muller
 */
@Stateless
public class FmsLuCountryFacade extends AbstractFacade<FmsLuCountry> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuCountryFacade() {
        super(FmsLuCountry.class);
    }

    /*named query to select country name info from FmsLuCountry table by country name 
     returen country name info*/
    public FmsLuCountry seachBycountry(FmsLuCountry fmsLuCountry) {
        Query query = em.createNamedQuery("FmsLuCountry.findByCountryName");
        query.setParameter("countryName", fmsLuCountry.getCountryName());
        try {
            FmsLuCountry countryname = (FmsLuCountry) query.getResultList().get(0);
            return countryname;
        } catch (Exception ex) {

        }
        return null;

    }
    
/*named query to select country name info from FmsLuCountry table by country name 
     returen country name info*/
    public FmsLuCountry getAllCountry(FmsLuCountry fmsLuCountry) {
        Query query = em.createNamedQuery("FmsLuCountry.findByCountryName", FmsLuCountry.class);
        query.setParameter("countryName", fmsLuCountry.getCountryName());
        try {
            FmsLuCountry fmsLuCountry1 = (FmsLuCountry) query.getResultList().get(0);
            return fmsLuCountry1;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select country name info from FmsLuCountry table by country name 
     returen country name info*/
    public FmsLuCountry SearchC(FmsLuCountry fmsLuCountry) {
        Query query = em.createNamedQuery("FmsLuCountry.findByCountry", FmsLuCountry.class);
        query.setParameter("countryName", fmsLuCountry.getCountryName());
        try {
            FmsLuCountry fmsLuCountry1 = (FmsLuCountry) query.getResultList().get(0);
            return fmsLuCountry1;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select country info from FmsLuCountry table by address name 
     returen country info*/
    public FmsLuCountry getSelectedCountry(int key) {
        Query query = em.createNamedQuery("HrAddresses.findByAddressName");
        query.setParameter("parentId", key);
        try {
            FmsLuCountry selectCounty = (FmsLuCountry) query.getResultList().get(0);
            return selectCounty;
        } catch (Exception ex) {

            return null;
        }
    }
    
 /*named query to select country info from FmsLuCountry table by country  ID
     returen country info*/
    public FmsLuCountry getDataById(FmsLuCountry fmsLuCountry) {
        Query query = em.createNamedQuery("FmsLuCountry.findByCountryId");
        query.setParameter("countryId", fmsLuCountry.getCountryId());
        try {
            FmsLuCountry selectCounty = (FmsLuCountry) query.getResultList().get(0);
            return selectCounty;
        } catch (Exception ex) {
            return null;
        }
    }

     /*named query to select country list from FmsLuCountry table by country name's first letter match
     returen country list*/
    public List<FmsLuCountry> searchCountryByName(ComLuCountry comLuCountry) {
        Query query = em.createNamedQuery("FmsLuCountry.findByCountryNameLike");
        query.setParameter("country", comLuCountry.getCountry().toUpperCase() + "%");
        try {
            ArrayList<FmsLuCountry> countryList = new ArrayList(query.getResultList());
            return countryList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select country list from FmsLuCountry table by country name's first letter match
     returen country list*/
    public List<FmsLuCountry> SearchCountry(FmsLuCountry fmsLuCountry) {
        List<FmsLuCountry> countryList = null;
        try {
            Query query = em.createNamedQuery("FmsLuCountry.findByCountryName");
            query.setParameter("countryName", fmsLuCountry.getCountryName() + "%");
            countryList = (List<FmsLuCountry>) query.getResultList();
            return countryList;

        } catch (Exception e) {
            return null;
        }
    }

    /*named query to select country list from FmsLuCountry table by country name
     returen boolean value*/
    public boolean getCountryDup(FmsLuCountry fmsLuCountry) {
        boolean duplication;
        Query query = em.createNamedQuery("FmsLuCountry.findByCountryName", FmsLuCountry.class);
        query.setParameter("countryName", fmsLuCountry.getCountryName());
        try {
            if (query.getResultList().size() > 0) {
                duplication = true;
            } else {
                duplication = false;
            }
            return duplication;
        } catch (Exception ex) {
            return false;
        }
    }

    /*native query to select all list
     from FMS_LU_COUNTRY, COM_LU_Country table 
     using county Id
     returen boolean value*/
    public boolean searchCountryByID(ComLuCountry comLuCountry) {
        boolean dup;
        try {
            Query query = em.createNativeQuery("SELECT * "
                    + "FROM FMS_LU_COUNTRY fluc "
                    + "INNER JOIN COM_LU_Country clc "
                    + "ON fluc.COM_LU_COUNTRY_ID=clc.ID "
                    + "WHERE clc.ID = '" + comLuCountry.getId() + "'", ComLuCountry.class);
            if (query.getResultList().size() > 0) {
                dup = true;
            } else {
                dup = false;
            }
            return dup;
        } catch (Exception ex) {
            return false;
        }
    }
}
