
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.entity.MmsStockItemLost;
import et.gov.eep.mms.entity.MmsStoreInformation;
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
public class MmsStockItemLostFacade extends AbstractFacade<MmsStockItemLost> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsStockItemLostFacade() {
        super(MmsStockItemLost.class);
    }

    public List<MmsStockItemLost> searchLostItemByParameterPrefix(MmsStockItemLost stockLostEntity) {
        Query query = em.createNamedQuery("MmsStockItemLost.findByAllParameters");
        query.setParameter("lostItemNo", '%' + stockLostEntity.getLostItemNo() + '%');

        try {
            ArrayList<MmsStockItemLost> lostItemList = new ArrayList(query.getResultList());
            return lostItemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsStockItemLost getSelectedRequest(BigDecimal lostStockId) {
        Query query = em.createNamedQuery("MmsStockItemLost.findByLostItemId");
        query.setParameter("lostStockId", lostStockId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsStockItemLost selectrequest = (MmsStockItemLost) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsStockItemLost getLastLostItemId() {
        Query query = em.createNamedQuery("MmsStockItemLost.findBylostItemIdMaximum");
        MmsStockItemLost result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsStockItemLost) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsStockItemLost> searchLostStockByLostNoAndStoreId(MmsStockItemLost stockLostEntity, MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsStockItemLost.findByStockNoAndStoreId", MmsStockItemLost.class);
        query.setParameter("lostItemNo", stockLostEntity.getLostItemNo() + '%');
        query.setParameter("storeId", storeInfoEntity);
        try {
            ArrayList<MmsStockItemLost> listofstock = new ArrayList(query.getResultList());
            return listofstock;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStockItemLost> searchLostItemByParameterPrefixAndLostPrep(MmsStockItemLost stockLostEntity) {
        System.out.println("------inside--");
        Query query = em.createNamedQuery("MmsStockItemLost.findByAllParametersAndLotPrep");
        query.setParameter("lostItemNo", stockLostEntity.getLostItemNo());
        query.setParameter("processedBy", stockLostEntity.getProcessedBy());
        System.out.println("========list===="+stockLostEntity);
        try {
            ArrayList<MmsStockItemLost> lostItemList = new ArrayList(query.getResultList());
            System.out.println("--------"+lostItemList);
            return lostItemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsStockItemLost> findSrListByWfStatus(int CHECK_APPROVE_VALUE) {
        Query query = em.createNamedQuery("MmsStockItemLost.findStkListByWfStatus", MmsStockItemLost.class);
        query.setParameter("stockStatus", CHECK_APPROVE_VALUE);
        try {
            ArrayList<MmsStockItemLost> listofstk = new ArrayList(query.getResultList());
            return listofstk;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStockItemLost> findSrListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE) {
        Query query = em.createNamedQuery("MmsStockItemLost.findStkListForCheckerByWfStatus", MmsStockItemLost.class);
        query.setParameter("prepared", PREPARE_VALUE);
        query.setParameter("approverReject", APPROVE_REJECT_VALUE);

        try {
            ArrayList<MmsStockItemLost> listofstk = new ArrayList(query.getResultList());
            return listofstk;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStockItemLost> searchAllLostInfoByPreparerId(Integer processedBy) {
        System.out.println("=========++++");
        Query query = em.createNamedQuery("MmsStockItemLost.findAllByPreparerId", MmsStockItemLost.class);

        query.setParameter("processedBy", processedBy);
        System.out.println("======@facade====" + processedBy);
        try {
            ArrayList<MmsStockItemLost> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
     public List<MmsStockItemLost> getStockListsByParameter(MmsStockItemLost stockLostEntity) {
        List<MmsStockItemLost> colValueLists = new ArrayList<>();
        if (stockLostEntity.getColumnName() != null  && stockLostEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_STOCK_ITEM_LOST\n"
                     + "   WHERE " + stockLostEntity.getColumnName().toLowerCase() + "='" + stockLostEntity.getColumnValue() + "'"
                    + " ", MmsStockItemLost.class);
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
            Query query = em.createNamedQuery("MmsStockItemLost.findByAcceptedBy");
            query.setParameter("AcceptedBy", stockLostEntity.getAcceptedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('MMS_STOCK_ITEM_LOST')\n"
                + "   and column_name NOT IN ('EVIDENCE_ATTACHED','REASON_OF_WRITTEN','STORE_ID','STATUS','REMARK','STOCK_STATUS','COMMENT_GIVEN','REFERENCE_NUMBER','COMMITTEE_MEMBERS','COMMITTEE_ID','ACCEPTED2_DATE','APPROVER2_DATE','REG_DATE')\n"
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


