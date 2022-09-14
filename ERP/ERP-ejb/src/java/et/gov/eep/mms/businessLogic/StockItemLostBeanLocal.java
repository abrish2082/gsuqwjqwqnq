
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStockItemLost;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface StockItemLostBeanLocal {

    public List<MmsStockItemLost> searchAllLostInfoByPreparerId(Integer processedBy);

    public List<MmsStockItemLost> searchLostItemByParameterPrefixAndLostPrep(MmsStockItemLost stockLostEntity);

    public void edit(MmsStockItemLost stockLostEntity);

    public void create(MmsStockItemLost stockLostEntity);

    public MmsStockItemLost getLastStockItemNo();
    
    public List<MmsStockItemLost> getStockListsByParameter(MmsStockItemLost stockLostEntity);

    public List<ColumnNameResolver> getColumnNameList();

}
