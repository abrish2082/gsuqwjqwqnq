
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStockItemLost;
import et.gov.eep.mms.mapper.MmsStockItemLostFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class StockItemLostBean implements StockItemLostBeanLocal {

    @EJB
    private MmsStockItemLostFacade stockItemLostFacede;

    @Override
    public List<MmsStockItemLost> searchAllLostInfoByPreparerId(Integer processedBy) {
        return stockItemLostFacede.searchAllLostInfoByPreparerId(processedBy);
    }

    @Override
    public List<MmsStockItemLost> searchLostItemByParameterPrefixAndLostPrep(MmsStockItemLost stockLostEntity) {
       return stockItemLostFacede.searchLostItemByParameterPrefixAndLostPrep(stockLostEntity);
    }

    @Override
    public void edit(MmsStockItemLost stockLostEntity) {
      stockItemLostFacede.edit(stockLostEntity);
    }

    @Override
    public void create(MmsStockItemLost stockLostEntity) {
     stockItemLostFacede.create(stockLostEntity);
    }

    @Override
    public MmsStockItemLost getLastStockItemNo() {
        return stockItemLostFacede.getLastLostItemId();
    }
    @Override
    public List<MmsStockItemLost> getStockListsByParameter(MmsStockItemLost stockLostEntity) {
        return stockItemLostFacede.getStockListsByParameter(stockLostEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return stockItemLostFacede.getColumnNameList();
    }
}
