
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStockDisposal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsStockDisposalBeanLocal {

    public List<MmsStockDisposal> searchStockDisposalByParameterPrefix(MmsStockDisposal stockDispEntity);

    public MmsStockDisposal getLastStockDisposalId();

    public void create(MmsStockDisposal stockDispEntity);

    public void edit(MmsStockDisposal stockDispEntity);

    public ArrayList<MmsStockDisposal> searchByStockDispNo(MmsStockDisposal stockDispEntity);

    public List<MmsStockDisposal> findAllStoreName();

    public List<MmsStockDisposal> findAllInfo();

    public List<MmsStockDisposal> searchByStoreName1(MmsStockDisposal stockDispEntity);

    public MmsStockDisposal getSelectedRequest(Integer stockId);

    public List<MmsStockDisposal> searchStockDisposalByParameterPrefixAndDispPrep(MmsStockDisposal stockDispEntity);

    public List<MmsStockDisposal> findDispListByWfStatus(int PREPARE_VALUE);
    public List<MmsStockDisposal> findDispListByWfStatus1(int stkStatus);
    public List<MmsStockDisposal> searchAllDispInfoByPreparerId(Integer preparedBy);
    
    public List<MmsStockDisposal> getStockDisposalListsByParameter(MmsStockDisposal stockDispEntity);

    public List<ColumnNameResolver> getColumnNameList();
}
