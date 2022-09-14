
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.FmsStockLedgerCardModel;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInventoryBalanceSheet;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kimmyo
 */
@Local
public interface MmsInventoryBalancingBeanLocal {

    /**
     *
     * @param InventoryBalance
     */
    void create(MmsInventoryBalanceSheet InventoryBalance);

    /**
     *
     * @param InventoryBalance
     */
    void edit(MmsInventoryBalanceSheet InventoryBalance);

    public MmsInventoryBalanceSheet getLastId();

    public List<MmsInventoryBalanceSheet> searchByStoreId(MmsStoreInformation storeInfoEntity);

    public List<FmsStockLedgerCardModel> findByStoreIdAndStatus(MmsStoreInformation storeInfoEntity);

    public List<MmsInventoryBalanceSheet> searchByParmeterStoreAndInventoryNo(MmsStoreInformation storeInfoEntity, MmsInventoryCounting inventoryCountEntity);

    public List<MmsInventoryBalanceSheet> searchByParameterStoreAndBudgetYear(MmsStoreInformation storeInfoEntity, MmsInventoryBalanceSheet invBalanceEntity);

    public List<MmsInventoryBalanceSheet> searchByAllParameters(MmsStoreInformation storeInfoEntity, MmsInventoryCounting inventoryCountEntity, MmsInventoryBalanceSheet invBalanceEntity);

    public List<MmsInventoryBalanceSheet> searchByStoreIdAndProcessedBy(MmsStoreInformation storeInfoEntity,MmsInventoryBalanceSheet inv);

    public List<MmsInventoryBalanceSheet> searchByParameterStoreAndBudgetYearAndProcessedBy(MmsStoreInformation storeInfoEntity, MmsInventoryBalanceSheet invBalanceEntity);

    public List<MmsInventoryBalanceSheet> findInventoryBalanceSheetListByWfStatus(int status);
    
    public List<MmsInventoryBalanceSheet> getBalanceSheetListsByParameter(MmsInventoryBalanceSheet invBalanceEntity);

    public List<ColumnNameResolver> getColumnNameList();

}
