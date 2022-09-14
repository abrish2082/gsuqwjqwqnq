
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsLostFixedAssetBeanLocal {

    /**
     *
     * @param lostFxAssetInfo
     */
    void create(MmsLostFixedAsset lostFxAssetInfo);

    /**
     *
     * @param lostFxAssetInfo
     */
    void edit(MmsLostFixedAsset lostFxAssetInfo);

    /**
     *
     * @return
     */
    public MmsLostFixedAsset getLastLostItemId();

    /**
     *
     * @param lostitemno
     * @return
     */
    public ArrayList<MmsLostFixedAsset> searchByLostItemNo(MmsLostFixedAsset lostitemno);

    /**
     *
     * @param lostFixedAsset
     * @return
     */
    public MmsLostFixedAsset getLostInfoByItemNo(MmsLostFixedAsset lostFixedAsset);

    /**
     *
     * @param lostFxAssetInfo
     * @return
     */
    public List<MmsLostFixedAsset> searchLostItemByParameterPrefix(MmsLostFixedAsset lostFxAssetInfo);

    public MmsLostFixedAsset getSelectedRequest(Integer lostItemId);

    public MmsLostFixedAsset searchById(MmsLostFixedAsset LostObj);

    public List<MmsLostFixedAsset> searchLostItemByParameterPrefixAndLostPrep(MmsLostFixedAsset lostFxAssetEntity);

    public List<MmsLostFixedAsset> findLostListByWfStatus(int CHECK_APPROVE_VALUE);

    public List<MmsLostFixedAsset> findLostListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE);

    public List<MmsLostFixedAsset> searchAllLostInfoByPreparerId(Integer employeeName); 

    public List<MmsLostFixedAsset> findAllInfo();

    public List<MmsLostFixedAsset> getLostFixedAssetColumnNameLists();

    public List<MmsLostFixedAsset> searchLostItemInformation(MmsLostFixedAsset lostFxAssetEntity);
    
   public List<MmsLostFixedAsset> getLostListsByParameter(MmsLostFixedAsset lostFxAssetEntity);

    public List<ColumnNameResolver> getColumnNameList();
}
