
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.FmsStockLedgerCardModel;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInventoryBalanceSheet;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsInventoryBalanceSheetFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsInventoryBalancingBean implements MmsInventoryBalancingBeanLocal {

    @EJB
    MmsInventoryBalanceSheetFacade balanceSheetFacade;

    /**
     *
     * @param InventoryBalance
     */
    @Override
    public void create(MmsInventoryBalanceSheet InventoryBalance) {
        balanceSheetFacade.create(InventoryBalance);
    }

    /**
     *
     * @param InventoryBalance
     */
    @Override
    public void edit(MmsInventoryBalanceSheet InventoryBalance) {
        balanceSheetFacade.edit(InventoryBalance);
    }

    @Override
    public MmsInventoryBalanceSheet getLastId() {
        return balanceSheetFacade.getLastBalanceSheetId();
    }

    @Override
    public List<MmsInventoryBalanceSheet> searchByStoreId(MmsStoreInformation storeInfoEntity) {
        return balanceSheetFacade.searchByParameterStoreId(storeInfoEntity);
    }
@Override
    public List<MmsInventoryBalanceSheet> searchByStoreIdAndProcessedBy(MmsStoreInformation storeInfoEntity,MmsInventoryBalanceSheet inv) {
        return balanceSheetFacade.searchByParameterStoreIdAndProcessedBy(storeInfoEntity,inv);
    }

    @Override
    public List<FmsStockLedgerCardModel> findByStoreIdAndStatus(MmsStoreInformation storeInfoEntity) {
        return balanceSheetFacade.findByStoreIdAndStatus(storeInfoEntity);
    }

    @Override
    public List<MmsInventoryBalanceSheet> searchByParmeterStoreAndInventoryNo(MmsStoreInformation storeInfoEntity, MmsInventoryCounting inventoryCountEntity) {
        return balanceSheetFacade.searchByParameterStoreIdAndInventoryNo(storeInfoEntity, inventoryCountEntity);
    }

    @Override
    public List<MmsInventoryBalanceSheet> searchByParameterStoreAndBudgetYear(MmsStoreInformation storeInfoEntity, MmsInventoryBalanceSheet invBalanceEntity) {
        return balanceSheetFacade.searchByParameterStoreIdAndBudgetYear(storeInfoEntity, invBalanceEntity);
    }

    @Override
    public List<MmsInventoryBalanceSheet> searchByParameterStoreAndBudgetYearAndProcessedBy(MmsStoreInformation storeInfoEntity, MmsInventoryBalanceSheet invBalanceEntity) {
        return balanceSheetFacade.searchByParameterStoreIdAndBudgetYearAndProcessedBy(storeInfoEntity, invBalanceEntity);
    }

    @Override
    public List<MmsInventoryBalanceSheet> searchByAllParameters(MmsStoreInformation storeInfoEntity, MmsInventoryCounting inventoryCountEntity, MmsInventoryBalanceSheet invBalanceEntity) {

        return balanceSheetFacade.searchByAllParameters(storeInfoEntity, inventoryCountEntity, invBalanceEntity);
    }

    @Override
    public List<MmsInventoryBalanceSheet> findInventoryBalanceSheetListByWfStatus(int status) {
        return balanceSheetFacade.findInventoryBalanceSheetsNumberListByWfStatus(status);
    }
@Override
    public List<MmsInventoryBalanceSheet> getBalanceSheetListsByParameter(MmsInventoryBalanceSheet invBalanceEntity) {
        return balanceSheetFacade.getBalanceSheetListsByParameter(invBalanceEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return balanceSheetFacade.getColumnNameList();
    }
}
