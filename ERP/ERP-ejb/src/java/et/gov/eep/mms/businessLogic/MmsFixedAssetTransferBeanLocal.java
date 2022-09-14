
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsFixedAssetTransferBeanLocal {

    /**
     *
     * @param TransferEntity
     * @return
     */
    public ArrayList<MmsFixedAssetTransfer> searchByTransferNo(MmsFixedAssetTransfer TransferEntity);

    /**
     *
     * @param TransferEntity
     * @return
     */
    public List<MmsFixedAssetTransfer> searchTransferByParameterPrefix(MmsFixedAssetTransfer TransferEntity);

    /**
     *
     * @return
     */
    public MmsFixedAssetTransfer getLastTransferId();

    /**
     *
     * @param TransferEntity
     */
    public void create(MmsFixedAssetTransfer TransferEntity);

    /**
     *
     * @param TransferEntity
     */
    public void edit(MmsFixedAssetTransfer TransferEntity);

    public MmsFixedAssetTransfer getSelectedRequest(BigDecimal transferId);

    public List<MmsFixedAssetTransfer> searchTransferByParameterPrefixAndTrPrep(MmsFixedAssetTransfer TransferEntity);

    public List<MmsFixedAssetTransfer> findTrListByWfStatus(int CHECK_REJECT_VALUE);

    public List<MmsFixedAssetTransfer> findTrListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE);

    public List<MmsFixedAssetTransfer> searchAlltraInfoByPreparerId(Integer preparedBy);
   
     public List<MmsFixedAssetTransfer> getTransferListsByParameter(MmsFixedAssetTransfer TransferEntity);

    public List<ColumnNameResolver> getColumnNameList();

    
}
