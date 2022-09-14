
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsNonFixedAssetReturnBeanLocal {

    public MmsNonFixedAssetReturn getLastReturnId();

    public void create(MmsNonFixedAssetReturn nonFxdRetEntity);

    public void edit(MmsNonFixedAssetReturn nonFxdRetEntity);

    public ArrayList<MmsNonFixedAssetReturn> searchByReturnNo(MmsNonFixedAssetReturn nonFxdRetEntity);

    public List<MmsNonFixedAssetReturn> searchNFReturnByParameterPrefix(MmsNonFixedAssetReturn nonFxdRetEntity);

    public List<MmsNonFixedAssetReturn> findAllInfo();

    public MmsNonFixedAssetReturn getSelectedRequest(BigDecimal nfaId);

    public List<MmsNonFixedAssetReturn> findNFAListByWfStatus(int CHECK_APPROVE_VALUE);

    public List<MmsNonFixedAssetReturn> findNFAListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE);

    public List<MmsSivDetail> getItemCodeLists(MmsStoreInformation storeInfoEntity);
    
     public List<MmsNonFixedAssetReturn> getNonreturnListsByParameter(MmsNonFixedAssetReturn nonFxdRetEntity);

     public List<ColumnNameResolver> getColumnNameList();


}
