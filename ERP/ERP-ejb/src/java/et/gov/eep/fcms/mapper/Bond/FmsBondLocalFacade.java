package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondLocal;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondLocalFacade extends AbstractFacade<FmsBondLocal> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondLocalFacade() {
        super(FmsBondLocal.class);
    }

    /*named query for finding Bond local from Bond local table by serial number's first letter or number*/
    public FmsBondLocal searchFmsBondSerialinfo(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findBySerialNoLike");
        query.setParameter("serialNo", BondLocal.getSerialNo() + '%');
        try {
            FmsBondLocal BondList = (FmsBondLocal) query.getResultList().get(0);
            return BondList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by serial number's first letter or number*/
    public List<FmsBondLocal> findBySerialNo(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findBySerialNoLike");
        query.setParameter("serialNo", BondLocal.getSerialNo() + '%');
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by Buyer FullName first letter*/
    public ArrayList<FmsBondLocal> searchFmsBondname(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findByBuyerFullNameLike");
        query.setParameter("buyerFullName", BondLocal.getBuyerFullName() + '%');
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by serial number's first letter or number*/
    public ArrayList<FmsBondLocal> searchFmsBondSerial(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findBySerialNoLike");
        query.setParameter("serialNo", BondLocal.getSerialNo() + '%');
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by applicationDate first number*/
    public ArrayList<FmsBondLocal> searchFmsBonddate(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findByApplicationDateLike");
        query.setParameter("applicationDate", BondLocal.getApplicationDate());
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by Buyer FullName first letter and serial number's first letter or number*/
    public ArrayList<FmsBondLocal> searchFmsBond(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findBondLike");
        query.setParameter("serialNo", BondLocal.getSerialNo() + '%');
        query.setParameter("buyerFullName", BondLocal.getBuyerFullName() + '%');

        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by valueBirr*/
    public ArrayList<FmsBondLocal> findByValueBirr(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findByValueBirr");
        query.setParameter("valueBirr", BondLocal.getValueBirr());
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by 
     Buyer FullName, serial number and value birr*/
    public ArrayList<FmsBondLocal> findBySerialNameAndPrincipal(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findByfindBySerialNamePrincipal");
        query.setParameter("serialNo", BondLocal.getSerialNo());
        query.setParameter("buyerFullName", BondLocal.getBuyerFullName());
        query.setParameter("valueBirr", BondLocal.getValueBirr());
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
    /*named query for finding Bond local list from Bond local table by serial numberand value birr*/

    public ArrayList<FmsBondLocal> findBySerialAndPrincipal(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findBySerialAndPrincipal");
        query.setParameter("serialNo", BondLocal.getSerialNo());
        query.setParameter("valueBirr", BondLocal.getValueBirr());
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query for finding Bond local list from Bond local table by Buyer FullName and value birr*/
    public ArrayList<FmsBondLocal> findByPrincipalAndName(FmsBondLocal BondLocal) {
        Query query = em.createNamedQuery("FmsBondLocal.findByPrincipalAndName");
        query.setParameter("buyerFullName", BondLocal.getBuyerFullName());
        query.setParameter("valueBirr", BondLocal.getValueBirr());
        try {
            ArrayList<FmsBondLocal> BondList = new ArrayList(query.getResultList());
            return BondList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
