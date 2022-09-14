
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsStockLedgerCardModel;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInventoryBalanceSheet;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsInventoryBalanceSheetFacade extends AbstractFacade<MmsInventoryBalanceSheet> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsInventoryBalanceSheetFacade() {
        super(MmsInventoryBalanceSheet.class);
    }

    /**
     *
     * @param information
     * @return
     */
    public ArrayList<MmsInventoryCounting> searchInventoryIdInformation(MmsInventoryCounting information) {
        Query query = em.createNamedQuery("MmsInventoryCounting.findByInventoryCountId", MmsInventoryCounting.class);
        query.setParameter("inventoryCountId", information.getInventoryCountId());
        try {
            ArrayList<MmsInventoryCounting> InventioryInformations = new ArrayList(query.getResultList());
            return InventioryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsInventoryBalanceSheet getLastBalanceSheetId() {
        MmsInventoryBalanceSheet result = null;
        Query query = em.createNamedQuery("MmsInventoryBalanceSheet.findByIBSIdMaximum", MmsInventoryBalanceSheet.class);

        try {
            if (query.getResultList().size() > 0) {
                result = (MmsInventoryBalanceSheet) query.getResultList().get(0);
                System.out.println("=========result @ facade======" + result);
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryBalanceSheet> searchByParameterStoreId(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsInventoryBalanceSheet.findByParameterStoreId", MmsInventoryBalanceSheet.class);
        query.setParameter("storeId", storeInfoEntity);
        try {
            ArrayList<MmsInventoryBalanceSheet> InvBalanceInformations = new ArrayList(query.getResultList());
            return InvBalanceInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryBalanceSheet> searchByParameterStoreIdAndProcessedBy(MmsStoreInformation storeInfoEntity, MmsInventoryBalanceSheet inv) {
        Query query = em.createNamedQuery("MmsInventoryBalanceSheet.findByParameterStoreIdAndProcessedBy", MmsInventoryBalanceSheet.class);
        query.setParameter("storeId", storeInfoEntity.getStoreId());
        query.setParameter("processedBy", inv.getProcessedBy());
        try {
            ArrayList<MmsInventoryBalanceSheet> InvBalanceInformations = new ArrayList(query.getResultList());
            return InvBalanceInformations;
        } catch (Exception ex) {
            return null;
        }
    }
    public List<FmsStockLedgerCardModel> findByStoreIdAndStatus(MmsStoreInformation storeInfoEntity) {
        List<FmsStockLedgerCardModel> slcList = new ArrayList<>();
        try {
            Query query1 = em.createNativeQuery("SELECT mIbS.*,slc.*  FROM MMS_INVENTORY_BALANCE_SHEET mIbS \n"
                    + "                                        INNER JOIN FMS_STOCK_LEDGER_CARD slc \n"
                    + "                                        ON mIbS.MATERIAL_ID = slc.MATERIAL_ID \n"
                    + "                                        WHERE slc.ID= (SELECT MAX(slcc.ID) FROM FMS_STOCK_LEDGER_CARD slcc "
                    + "                                        WHERE mIbS.STATUS = 3  "
                    + "                                        AND slcc.MATERIAL_ID=slc.MATERIAL_ID AND mIbS.STORE_ID='" + storeInfoEntity.getStoreId() + "')");
            List<Object[]> result = query1.getResultList();
            if (result.size() > 0) {
                FmsStockLedgerCardModel slcObj;
                for (Object[] obj : result) {
                    slcObj = new FmsStockLedgerCardModel();
                    slcObj.setIbsID(Integer.parseInt(String.valueOf(obj[0])));
                    slcObj.setIbsBudgetYear(String.valueOf(obj[1]));
                    slcObj.setIbsCountingValue(Long.parseLong(String.valueOf(obj[2])));
                    slcObj.setIbsDifference(Long.parseLong(String.valueOf(obj[3])));
                    slcObj.setIbsOldValue(Long.parseLong(String.valueOf(obj[4])));
                    slcObj.setIbsRemark((String.valueOf(obj[5])));
                    slcObj.setIbsInvCountID(Integer.parseInt(String.valueOf(obj[6])));
                    slcObj.setIbsMatID(Integer.parseInt(String.valueOf(obj[7])));
                    slcObj.setIbsStoreID(Integer.parseInt(String.valueOf(obj[8])));
                    slcObj.setIbsApprovedDate(String.valueOf(obj[9]));
                    slcObj.setIbsStatus(Integer.parseInt(String.valueOf(obj[10])));
                    slcObj.setIbsProcessedBy(Integer.parseInt(String.valueOf(obj[11])));
                    slcObj.setIbsCommentGiven(String.valueOf(obj[12]));
                    slcObj.setIbsProcessedOn(String.valueOf(obj[13]));
                    slcObj.setSlcID(Integer.parseInt(String.valueOf(obj[14])));
                    slcObj.setSlcCurrentPrice(Double.parseDouble(String.valueOf(obj[15])));
                    slcObj.setSlcCurrentQty(Integer.parseInt(String.valueOf(obj[16])));
                    slcObj.setSlcTotalQuanty(Integer.parseInt(String.valueOf(obj[17])));
                    slcObj.setSlcMatCode(String.valueOf(obj[18]));
                    slcObj.setSlcMatID(Double.parseDouble(String.valueOf(obj[19])));
                    slcObj.setSlcMatName(String.valueOf(obj[20]));
                    slcObj.setSlcPrevQty(Integer.parseInt(String.valueOf(obj[21])));
                    slcObj.setSlcRefNo(String.valueOf(obj[22]));
                    slcObj.setSlcRefType(String.valueOf(obj[23]));
                    slcObj.setSlcStoreNo(String.valueOf(obj[24]));
                    slcObj.setSlcWtAvPrice(Double.parseDouble(String.valueOf(obj[25])));
                    slcObj.setSlcCurrentPrice(Double.parseDouble(String.valueOf(obj[15])));
                    slcList.add(slcObj);
                }
            } else {
                
            }
            return slcList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryBalanceSheet> searchByParameterStoreIdAndInventoryNo(MmsStoreInformation storeInfoEntity, MmsInventoryCounting inventoryCount) {
        System.out.println("=============store id @ facade=======" + storeInfoEntity.getStoreId());
        try {
            Query query1 = em.createNativeQuery("SELECT ibs.*  "
                    + "FROM mms_inventory_balance_sheet ibs        "
                    + "INNER JOIN mms_inventory_counting ic "
                    + "ON ibs.inv_count_id= ic.inventory_count_id "
                    + "WHERE ibs.store_id ='" + storeInfoEntity + "' "
                    + "AND ibs.processedBy='" + inventoryCount.getProcessedBy() + "'"
                    + "AND ic.inventory_count_no Like '" + inventoryCount.getInventoryCountNo() + "%'",
                    MmsInventoryBalanceSheet.class);
            return (List<MmsInventoryBalanceSheet>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsInventoryBalanceSheet> searchByParameterStoreIdAndBudgetYear(MmsStoreInformation storeInfoEntity, MmsInventoryBalanceSheet invBalanceSheet) {
        Query query = em.createNamedQuery("MmsInventoryBalanceSheet.findByStoreIdAndBudgetYear", MmsInventoryBalanceSheet.class);
        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("budgetYear", invBalanceSheet.getBudgetYear() + '%');
        try {
            ArrayList<MmsInventoryBalanceSheet> InventioryInformations = new ArrayList(query.getResultList());
            return InventioryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryBalanceSheet> searchByParameterStoreIdAndBudgetYearAndProcessedBy(MmsStoreInformation storeInfoEntity, MmsInventoryBalanceSheet invBalanceSheet) {
        Query query = em.createNamedQuery("MmsInventoryBalanceSheet.findByStoreIdAndBudgetYearAndProcessedBy", MmsInventoryBalanceSheet.class);
        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("budgetYear", invBalanceSheet.getBudgetYear() + '%');
        query.setParameter("processedBy", invBalanceSheet.getProcessedBy());
        try {
            ArrayList<MmsInventoryBalanceSheet> InventioryInformations = new ArrayList(query.getResultList());
            return InventioryInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryBalanceSheet> searchByAllParameters(MmsStoreInformation storeInfoEntity, MmsInventoryCounting invcount, MmsInventoryBalanceSheet invBalanceSheet) {
        try {
            Query query1 = em.createNativeQuery("SELECT ibs.*  "
                    + "FROM mms_inventory_balance_sheet ibs        "
                    + "INNER JOIN mms_inventory_counting ic "
                    + "ON ibs.inv_count_id= ic.inventory_count_id "
                    + "WHERE ibs.store_id ='" + storeInfoEntity + "' "
                    + "AND ic.inventory_count_no Like '" + invcount.getInventoryCountNo() + "%' "
                    + "AND ibs.processedBy='" + invBalanceSheet.getProcessedBy() + "'"
                    + "AND ibs.budgetYear Like'" + invBalanceSheet.getBudgetYear() + "%'",
                    MmsInventoryBalanceSheet.class);
            return (List<MmsInventoryBalanceSheet>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryBalanceSheet> findInventoryBalanceSheetsNumberListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsInventoryBalanceSheet.findByStatus", MmsInventoryBalanceSheet.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsInventoryBalanceSheet> listofinventory = new ArrayList(query.getResultList());
            return listofinventory;
        } catch (Exception e) {
            return null;
        }
    }
   public List<MmsInventoryBalanceSheet> getBalanceSheetListsByParameter(MmsInventoryBalanceSheet invBalanceEntity) {
        List<MmsInventoryBalanceSheet> colValueLists = new ArrayList<>();
        if (invBalanceEntity.getColumnName() != null
                && invBalanceEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_INVENTORY_BALANCE_SHEET\n"
                    + "   WHERE " + invBalanceEntity.getColumnName().toLowerCase() + "='" + invBalanceEntity.getColumnValue() + "'"
                    + "", MmsInventoryBalanceSheet.class);
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
            Query query = em.createNamedQuery("MmsInventoryBalanceSheet.findByProcessedBy");
            query.setParameter("ProcessedBy", invBalanceEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_INVENTORY_BALANCE_SHEET')\n"
                + "   and COLUMN_NAME NOT IN ('COUNTING_VALUE','OLD_VALUE','REMARK','INV_COUNT_ID','STORE_ID','STATUS','COMMENT_GIVEN','DIFFRENCE')\n"
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


