
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStockDisposal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsStockDisposalFacade extends AbstractFacade<MmsStockDisposal> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsStockDisposalFacade() {
        super(MmsStockDisposal.class);
    }

    public List<MmsStockDisposal> searchStockDisposalByParameterPrefix(MmsStockDisposal stockDispEntity) {
        Query query = em.createNamedQuery("MmsStockDisposal.findByAllParameters");
        query.setParameter("stockNo", '%' + stockDispEntity.getStockNo() + '%');
        try {
            ArrayList<MmsStockDisposal> DisposalList = new ArrayList(query.getResultList());
            return DisposalList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsStockDisposal getLastStockDisposalId() {

        Query query1 = em.createNamedQuery("MmsStockDisposal.findByStockIdMaximum");

        MmsStockDisposal result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsStockDisposal) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsStockDisposal> searchByStockDispNo(MmsStockDisposal stockDispEntity) {
        Query query = em.createNamedQuery("MmsStockDisposal.findByStockNoLike");
        query.setParameter("stockNo", stockDispEntity.getStockNo() + '%');
        try {
            ArrayList<MmsStockDisposal> listofStockDispNo = new ArrayList(query.getResultList());
            return listofStockDispNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStockDisposal> searchByStoreName1(MmsStockDisposal stockDispEntity) {
        Query query = em.createNamedQuery("MmsStockDisposal.findByAllParameters2");
        query.setParameter("storeId", '%' + stockDispEntity.getStoreId().getStoreName() + '%');
        try {
            ArrayList<MmsStockDisposal> DisposalList = new ArrayList(query.getResultList());
            return DisposalList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsStockDisposal getSelectedRequest(Integer stockId) {
        Query query = em.createNamedQuery("MmsStockDisposal.findByStockId");
        query.setParameter("stockId", stockId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsStockDisposal selectrequest = (MmsStockDisposal) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsStockDisposal> searchStockDisposalByParameterPrefixAndDispPrep(MmsStockDisposal stockDispEntity) {
        Query query = em.createNamedQuery("MmsStockDisposal.findByAllParametersAndDispPrep");
         query.setParameter("stockNo", '%' + stockDispEntity.getStockNo() + '%');
        try {
            ArrayList<MmsStockDisposal> DisposalList = new ArrayList(query.getResultList());
            return DisposalList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsStockDisposal> findDispListByWfStatus(int PREPARE_VALUE) {
        Query query = em.createNamedQuery("MmsStockDisposal.findDispListByWfStatus", MmsStockDisposal.class);
        query.setParameter("stkStatus", PREPARE_VALUE);
        try {
            ArrayList<MmsStockDisposal> listofstk = new ArrayList(query.getResultList());
            return listofstk;
        } catch (Exception e) {
            return null;
        }
    }
 public List<MmsStockDisposal> findDispListByWfStatus1(int stkStatus) {
        Query query = em.createNamedQuery("MmsStockDisposal.findDispListByWfStatus1", MmsStockDisposal.class);
        query.setParameter("stkStatus", stkStatus);
        try {
            ArrayList<MmsStockDisposal> listofstk = new ArrayList(query.getResultList());
            return listofstk;
        } catch (Exception e) {
            return null;
        }
    }
  public List<MmsStockDisposal> searchAllDispInfoByPreparerId(Integer preparedBy) {
        Query query = em.createNamedQuery("MmsStockDisposal.findAllByPreparerId", MmsStockDisposal.class);

        query.setParameter("preparedBy", preparedBy);
        System.out.println("======@facade====" + preparedBy);
        try {
            ArrayList<MmsStockDisposal> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
  public List<MmsStockDisposal> getStockDisposalListsByParameter(MmsStockDisposal stockDispEntity) {
         List<MmsStockDisposal> colValueLists = new ArrayList<>();
        if (stockDispEntity.getColumnName() != null  && stockDispEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_STOCK_DISPOSAL\n"
                    + "   WHERE " + stockDispEntity.getColumnName().toLowerCase() + "='" + stockDispEntity.getColumnValue() + "'"
                    + "and " + stockDispEntity.getPreparedBy()+ "='" + stockDispEntity.getPreparedBy()+ "'", MmsStockDisposal.class);
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
            Query query = em.createNamedQuery("MmsStockDisposal.findByPreparedBy");
            query.setParameter("preparedBy", stockDispEntity.getPreparedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_STOCK_DISPOSAL')\n"
                + "   and COLUMN_NAME NOT IN ('APPROVER_REMARK','DISPOSAL_STATUS','PREPARER_REMARK','STORE_ID','STK_STATUS','REFERENCE_NUMBER','APP_DATE','PROP_DATE')\n"
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



