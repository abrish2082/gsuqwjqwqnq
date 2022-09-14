
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.mapper.MmsLostFixedAssetFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsLostFixedAssetBean implements MmsLostFixedAssetBeanLocal {

    @EJB
    MmsLostFixedAssetFacade lostFxAssetFacade;
   
    /**
     *
     * @param lostFxAssetInfo
     */
    @Override
    public void create(MmsLostFixedAsset lostFxAssetInfo) {
        lostFxAssetFacade.create(lostFxAssetInfo);
    }

    /**
     *
     * @param lostFxAssetInfo
     */
    @Override
    public void edit(MmsLostFixedAsset lostFxAssetInfo) {
        lostFxAssetFacade.edit(lostFxAssetInfo);
    }
   
    /**
     *
     * @return
     */
    @Override
    public MmsLostFixedAsset getLastLostItemId() {
       return lostFxAssetFacade.getLastLostItemId();
    }

    /**
     *
     * @param lostitemno
     * @return
     */
    @Override
    public ArrayList<MmsLostFixedAsset> searchByLostItemNo(MmsLostFixedAsset lostitemno) {
        return lostFxAssetFacade.searchByLostItemNo(lostitemno);
    }

    /**
     *
     * @param lostFixedAsset
     * @return
     */
    @Override
    public MmsLostFixedAsset getLostInfoByItemNo(MmsLostFixedAsset lostFixedAsset) {
       return lostFxAssetFacade.getLostItemInfoByItemNo(lostFixedAsset);
    }

    /**
     *
     * @param lostFxAssetInfo
     * @return
     */
    @Override
    public List<MmsLostFixedAsset> searchLostItemByParameterPrefix(MmsLostFixedAsset lostFxAssetInfo) {
        return lostFxAssetFacade.searchLostItemByParameterPrefix(lostFxAssetInfo);
    }

    @Override
    public MmsLostFixedAsset getSelectedRequest(Integer lostItemId) {
       return lostFxAssetFacade.getSelectedRequest(lostItemId);
    }
     @Override
    public MmsLostFixedAsset searchById(MmsLostFixedAsset LostObj) {
       return lostFxAssetFacade.searchById(LostObj);
    }

    @Override
    public List<MmsLostFixedAsset> searchLostItemByParameterPrefixAndLostPrep(MmsLostFixedAsset lostFxAssetEntity) {
        return lostFxAssetFacade.searchLostItemByParameterPrefixAndLostPrep(lostFxAssetEntity);
    }

    @Override
    public List<MmsLostFixedAsset> findLostListByWfStatus(int CHECK_APPROVE_VALUE) {
        return lostFxAssetFacade.findLostListByWfStatus(CHECK_APPROVE_VALUE);
    }

    @Override
    public List<MmsLostFixedAsset> findLostListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE) {
       return lostFxAssetFacade.findLostListForCheckerByWfStatus(PREPARE_VALUE,APPROVE_REJECT_VALUE);
    }

    @Override
    public List<MmsLostFixedAsset> searchAllLostInfoByPreparerId(Integer employeeName) {
    return lostFxAssetFacade.searchAllLostInfoByPreparerId(employeeName);
    }

    @Override
    public List<MmsLostFixedAsset> findAllInfo() {
       return lostFxAssetFacade.findAll();   
    }

    @Override
    public List<MmsLostFixedAsset> getLostFixedAssetColumnNameLists() {
        return lostFxAssetFacade.getLostFixedAssetColumnNameLists();
    }

    @Override
    public List<MmsLostFixedAsset> searchLostItemInformation(MmsLostFixedAsset lostFxAssetEntity) {
        return lostFxAssetFacade.searchLostItemInformation(lostFxAssetEntity);
    }
    @Override
    public List<MmsLostFixedAsset> getLostListsByParameter(MmsLostFixedAsset lostFxAssetEntity) {
        return lostFxAssetFacade.getLostListsByParameter(lostFxAssetEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return lostFxAssetFacade.getColumnNameList();
    }
}

