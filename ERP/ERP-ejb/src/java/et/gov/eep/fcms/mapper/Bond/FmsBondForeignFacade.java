package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.businessLogic.ComLuCountryBeanLocal;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.fcms.entity.perDiem.FmsLuCountry;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondForeignFacade extends AbstractFacade<FmsBondForeign> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondForeignFacade() {
        super(FmsBondForeign.class);
    }
    @EJB
    ComLuCountryBeanLocal countryBeanLocal;

    /*named query to select Bond foreign list from Bond foreign  using serial number*/
    public FmsBondForeign FmsBondIdinfo(FmsBondForeign foreign) {
        Query query = em.createNamedQuery("FmsBondForeign.findBySerialNo");
        query.setParameter("serialNo", foreign.getSerialNo() + '%');
        try {
            FmsBondForeign BondTypeList = (FmsBondForeign) (query.getResultList().get(0));
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using buyers full name*/
    public FmsBondForeign getByBuyersNames(FmsBondForeign foreign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByBuyerFullName", FmsBondForeign.class);
        query.setParameter("buyerFullName", foreign.getBuyerFullName());
        try {
            FmsBondForeign BondTypeList = (FmsBondForeign) (query.getResultList().get(0));
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using status*/
    public FmsBondForeign SearchStatus(FmsBondForeign foreign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByStatus");
        query.setParameter("status", foreign.getStatus());
        try {
            FmsBondForeign BondTypeList = (FmsBondForeign) (query.getResultList().get(0));
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using serial number*/
    public ArrayList<FmsBondForeign> searchName(FmsBondForeign foreign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByBuyerFullNamelike");
        query.setParameter("buyerFullName", foreign.getBuyerFullName() + '%');
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList<>();
            BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using serial number and buyers name*/
    public ArrayList<FmsBondForeign> searchFmsBondId(FmsBondForeign foreign) {
        Query query = em.createNamedQuery("FmsBondForeign.findBySerialNoAndName");
        query.setParameter("serialNo", foreign.getSerialNo() + '%');
        query.setParameter("buyerFullName", foreign.getBuyerFullName() + '%');
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList<>();
            BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using Bond issued country by passing Bond foreign object*/
    public ArrayList<FmsBondForeign> searchCountry(FmsBondForeign foreign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByCountryBondIssuedlike");
        query.setParameter("countryBondIssued", foreign.getCountryBondIssued().getCountry() + '%');
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using Bond issued country by passing country name*/
    public ArrayList<FmsBondForeign> searchCountry(FmsLuCountry fmsLuCountry) {
        Query query = em.createNamedQuery("FmsBondForeign.findByCountryBondIssuedlike");
        query.setParameter("countryBondIssued", fmsLuCountry.getCountryName() + '%');
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using serial number, buyer name, principal amount and currency*/
    public ArrayList<FmsBondForeign> findBySerialNameAmountAndCurency(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByfindBySerialNameAmountAndCurency");
        query.setParameter("serialNo", BondForeign.getSerialNo());
        query.setParameter("buyerFullName", BondForeign.getBuyerFullName());
        query.setParameter("BondCurrency", BondForeign.getFmsLuCurrency());
        query.setParameter("principalamount", BondForeign.getPrincipalamount());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*named query to select Bond foreign list from Bond foreign using serial number, principal amount and currency*/
    public ArrayList<FmsBondForeign> findBySerialAmountAndCurency(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findBySerialAmountAndCurency");
        query.setParameter("serialNo", BondForeign.getSerialNo());
        query.setParameter("BondCurrency", BondForeign.getFmsLuCurrency());
        query.setParameter("principalamount", BondForeign.getPrincipalamount());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*named query to select Bond foreign list from Bond foreign using serial number*/
    public ArrayList<FmsBondForeign> findByNameAmountAndCurency(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByNameAmountAndCurency");
        query.setParameter("buyerFullName", BondForeign.getBuyerFullName());
        query.setParameter("BondCurrency", BondForeign.getFmsLuCurrency());
        query.setParameter("principalamount", BondForeign.getPrincipalamount());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*named query to select Bond foreign list from Bond foreign using principal amount and currency by passing Bond foreign*/
    public ArrayList<FmsBondForeign> findByAmountAndCurency(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByAmountAndCurency");
        query.setParameter("BondCurrency", BondForeign.getFmsLuCurrency());
        query.setParameter("principalamount", BondForeign.getPrincipalamount());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*named query to select Bond foreign list from Bond foreign using Bond currency by passing Bond foregin*/
    public ArrayList<FmsBondForeign> findByBondCurrency(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByBondCurrency");
        query.setParameter("BondCurrency", BondForeign.getFmsLuCurrency());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*named query to select Bond foreign list from Bond foreign using principal amount by passing Bond foreign object*/
    public ArrayList<FmsBondForeign> findByAmount(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByAmount");
        query.setParameter("principalamount", BondForeign.getPrincipalamount());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*named query to select Bond foreign list from Bond foreign using buyer name and currency*/
    public ArrayList<FmsBondForeign> findByNameAndCurrency(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findByNameAndCurrency");
        query.setParameter("buyerFullName", BondForeign.getBuyerFullName());
        query.setParameter("BondCurrency", BondForeign.getFmsLuCurrency());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using serial number and currency*/
    public ArrayList<FmsBondForeign> findBySerialAndCurrency(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findBySerialAndCurrency");
        query.setParameter("serialNo", BondForeign.getSerialNo());
        query.setParameter("BondCurrency", BondForeign.getFmsLuCurrency());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using serial number and principal amount*/
    public ArrayList<FmsBondForeign> findBySerialAndAmount(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findBySerialAndAmount");
        query.setParameter("serialNo", BondForeign.getSerialNo());
        query.setParameter("principalamount", BondForeign.getPrincipalamount());
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign list from Bond foreign using country Bond issued name first latter*/
    public List<FmsBondForeign> searchCountryName() {
        Query query = em.createNamedQuery("FmsBondForeign.findByCountryBondIssuedlike");
        query.setParameter("countryBondIssued", +'%');
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /*named query to select Bond foreign list from Bond foreign using serial number which match the first letter/number*/
    public List<FmsBondForeign> findBySerialNo(FmsBondForeign BondForeign) {
        Query query = em.createNamedQuery("FmsBondForeign.findBySerialNo");
        query.setParameter("serialNo", BondForeign.getSerialNo() + '%');
        try {
            ArrayList<FmsBondForeign> BondTypeList = new ArrayList(query.getResultList());
            return BondTypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*Native query to select Bond issued country from Bond foreign by passing Application date(from date to toDate)*/
    public ArrayList<FmsBondForeign> getByCountryId(Date fromDate, Date toDate) {
        Query query = em.createNativeQuery("SELECT country_Bond_issued FROM FMS_BOND_FOREIGN WHERE application_date BETWEEN '" + fromDate.toString() + "' AND '" + toDate.toString() + "'");
        try {
            ArrayList<FmsBondForeign> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*Native query to select all Bond foreign list from Bond foreign by passing Application date(fromIssueDate to toIssueDate) 
     and country Bond issued id*/
    public List<FmsBondForeign> getByCountryId1(String fromIssueDate, String toIssueDate, ComLuCountry luCountry) {

        try {
            Query query = em.createNativeQuery("select * from FMS_BOND_FOREIGN B where B.COUNTRY_BOND_ISSUED ='" + luCountry.getId() + "' "
                    + "AND B.APPLICATION_DATE BETWEEN '" + fromIssueDate + "' AND '" + toIssueDate + "'", FmsBondForeign.class);

            return (List<FmsBondForeign>) query.getResultList();

        } catch (Exception ex) {

            return null;
        }

    }

    /*Native query to select Bond issued country from Bond foreign */
    public List<FmsBondForeign> searchFmsBondgroup() {
        try {
            Query query1 = em.createNativeQuery("SELECT country_Bond_issued,COUNT(serial_no),SUM(amount) FROM FMS_BOND_FOREIGN GROUP BY country_Bond_issued");
            List<Object[]> results = query1.getResultList();
            ArrayList<FmsBondForeign> applicationsList = new ArrayList();
            FmsBondForeign BondForeign = null;
            ComLuCountry countryBondIssued;
            List<FmsBondForeign> searchFmsBondgroup = new ArrayList<>();

            for (Object[] result : results) {
                BondForeign = new FmsBondForeign();
                countryBondIssued = new ComLuCountry();
                countryBondIssued.setId(Integer.parseInt(result[0].toString()));
                countryBondIssued = countryBeanLocal.luCountry(countryBondIssued);
                BondForeign.setCountryBondIssued(countryBondIssued);
                BondForeign.setSumofamount(Double.parseDouble(result[2].toString()));
                BondForeign.setCountpeople(Integer.parseInt(result[1].toString()));
                searchFmsBondgroup.add(BondForeign);
            }

            return searchFmsBondgroup;
        } catch (Exception ex) {
            return null;
        }
    }

}
