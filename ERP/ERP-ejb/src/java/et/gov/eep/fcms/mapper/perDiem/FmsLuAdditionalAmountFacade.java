package et.gov.eep.fcms.mapper.perDiem;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItems;

/**
 *
 * @author muller
 */
@Stateless
public class FmsLuAdditionalAmountFacade extends AbstractFacade<FmsLuAdditionalAmount> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuAdditionalAmountFacade() {
        super(FmsLuAdditionalAmount.class);
    }
    
 /*named query to select allowance info from FmsLuAdditionalAmount table by levelId 
     returen levelId info*/
    public FmsLuAdditionalAmount searchLevel(FmsLuAdditionalAmount additionalAmount) {
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findByLevelId", FmsLuAdditionalAmount.class);
        query.setParameter("levelId", additionalAmount.getLevelId());
        try {
            FmsLuAdditionalAmount Level = (FmsLuAdditionalAmount) query.getResultList().get(0);
            return Level;
        } catch (Exception ex) {
            return null;
        }
    }

     /*named query to select allowance info from FmsLuAdditionalAmount table by levelId 
     returen levelId info*/
    public FmsLuAdditionalAmount search1(FmsLuAdditionalAmount additionalAmount) {
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findByLevelId");
        query.setParameter("levelId", additionalAmount.getLevelId());

        try {
            FmsLuAdditionalAmount levelList = (FmsLuAdditionalAmount) query.getResultList().get(0);
            return levelList;

        } catch (Exception ex) {
            return null;
        }
    }

     /*named query to select level list info from FmsGoodWillingPayment table by levelId 
     returen levelId info*/
    public FmsGoodWillingPayment getAddtionalData(FmsGoodWillingPayment fmsGoodWillingPayment) {
        Query query = em.createNamedQuery("FmsGoodWillingPayment.findAll");
        try {
            FmsGoodWillingPayment levelList = (FmsGoodWillingPayment) query.getResultList().get(0);
            return levelList;
        } catch (Exception ex) {
            return null;
        }
    }

      /*named query to select id info from FmsLuAdditionalAmount table by Id 
     returen leveldata info*/
    public FmsLuAdditionalAmount getdata(FmsLuAdditionalAmount additionalAmount) {
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findById");
        query.setParameter("id", additionalAmount.getId());

        try {
            FmsLuAdditionalAmount levelData = (FmsLuAdditionalAmount) query.getResultList().get(0);
            return levelData;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select id info from FmsLuAdditionalAmount table by Id 
     returen country info*/
    public FmsGoodWillingPayment getByGWId(FmsGoodWillingPayment fmsGoodWillingPayment) {
        Query query = em.createNamedQuery("FmsGoodWillingPayment.findById");
        query.setParameter("id", fmsGoodWillingPayment.getId());
        try {
            FmsGoodWillingPayment selectCounty = (FmsGoodWillingPayment) query.getResultList().get(0);
            return selectCounty;
        } catch (Exception ex) {
            return null;
        }
    }
  /*named query to select all additional amount list from FmsLuAdditionalAmount table 
     returen level list*/
    public List<FmsLuAdditionalAmount> listOfAdd(FmsLuAdditionalAmount additionalAmount) {
        List<FmsLuAdditionalAmount> levelLis;
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findAll");
        try {
            levelLis = (List<FmsLuAdditionalAmount>) query.getResultList();
            return levelLis;

        } catch (Exception ex) {
            return null;
        }
    }

      /*named query to select all additional amount level list from FmsLuAdditionalAmount table by levelID's first letter or name match 
     returen level list*/
    public List<FmsLuAdditionalAmount> searchLevelByParameter(FmsLuAdditionalAmount additionalAmount) {
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findByAllParameters");
        query.setParameter("levelId", additionalAmount.getLevelId().toUpperCase() + '%');
        try {
            ArrayList<FmsLuAdditionalAmount> levelList = new ArrayList(query.getResultList());
            return levelList;
        } catch (Exception ex) {
            return null;
        }
    }
     public List<FmsLuAdditionalAmount> getFmsLuAdditionalAmountListsByParameter(FmsLuAdditionalAmount additionalAmount) {
        List<FmsLuAdditionalAmount> colValueLists = new ArrayList<>();
        if (additionalAmount.getColumnName() != null && additionalAmount.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM FMS_LU_ADDITIONAL_AMOUNT\n"
                 + "   WHERE " + additionalAmount.getColumnName().toLowerCase() + "='" + additionalAmount.getColumnValue() + "'"
                    + "", FmsLuAdditionalAmount.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("FmsLuAdditionalAmount.findByLunchAdditional");
            query.setParameter("LunchAdditional", additionalAmount.getLunchAdditional());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getFmsLuAdditionalAmountColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('FMS_LU_ADDITIONAL_AMOUNT')\n"
                + "   and COLUMN_NAME NOT IN ('ID')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    System.out.println("RetrivedColumns.get(i)===" + RetrivedColumns.get(i));
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
        
    }
     public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
}



