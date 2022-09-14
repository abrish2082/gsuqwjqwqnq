
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
import et.gov.eep.mms.mapper.MmsFixedAssetTransferFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFixedAssetTransferBean implements MmsFixedAssetTransferBeanLocal {

    @EJB
    MmsFixedAssetTransferFacade transferFacade;
    
    /**
     *
     * @param TransferEntity
     * @return
     */
    public ArrayList<MmsFixedAssetTransfer> searchByTransferNo(MmsFixedAssetTransfer TransferEntity) {
        return transferFacade.searchByTransferNo(TransferEntity);
    }

    /**
     *
     * @param TransferEntity
     * @return
     */
    @Override
    public List<MmsFixedAssetTransfer> searchTransferByParameterPrefix(MmsFixedAssetTransfer TransferEntity) {
         return transferFacade.searchTransferByParameterPrefix(TransferEntity);
    }

    /**
     *
     * @return
     */
    @Override
    public MmsFixedAssetTransfer getLastTransferId() {
        return transferFacade.getLastTransferId();
    }

    /**
     *
     * @param TransferEntity
     */
    @Override
    public void create(MmsFixedAssetTransfer TransferEntity) {
        transferFacade.create(TransferEntity);
    }

    /**
     *
     * @param TransferEntity
     */
    @Override
    public void edit(MmsFixedAssetTransfer TransferEntity) {
        transferFacade.edit(TransferEntity);
    }

    @Override
    public MmsFixedAssetTransfer getSelectedRequest(BigDecimal transferId) {
       return transferFacade.getSelectedRequest(transferId);
    }

    @Override
    public List<MmsFixedAssetTransfer> searchTransferByParameterPrefixAndTrPrep(MmsFixedAssetTransfer TransferEntity) {
        return transferFacade.searchTransferByParameterPrefixAndTrPrep(TransferEntity);
    }

    @Override
    public List<MmsFixedAssetTransfer> findTrListByWfStatus(int CHECK_REJECT_VALUE) {
       return transferFacade.findTrListByWfStatus(CHECK_REJECT_VALUE);
    }

    @Override
    public List<MmsFixedAssetTransfer> findTrListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE) {
         return transferFacade.findTrListForCheckerByWfStatus(PREPARE_VALUE,APPROVE_REJECT_VALUE);
    }
    
    @Override
    public List<MmsFixedAssetTransfer> searchAlltraInfoByPreparerId(Integer preparedBy) {
       return transferFacade.searchAlltraInfoByPreparerId(preparedBy);
}
    @Override
    public List<MmsFixedAssetTransfer> getTransferListsByParameter(MmsFixedAssetTransfer TransferEntity) {
        return transferFacade.getTransferListsByParameter(TransferEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return transferFacade.getColumnNameList();
    }
    
}
