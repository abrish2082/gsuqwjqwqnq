/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsLocationInfoFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsLocationInfoBean implements MmsLocationInfoBeanLocal {

    @EJB
    MmsLocationInfoFacade cellLocationInfoFacade;

    /**
     *
     * @param mmsLocationInfo
     */
    @Override
    public void create(MmsLocationInfo mmsLocationInfo) {
        cellLocationInfoFacade.create(mmsLocationInfo);
    }

    /**
     *
     * @param mmsLocationInfo
     */
    @Override
    public void edit(MmsLocationInfo mmsLocationInfo) {
        cellLocationInfoFacade.edit(mmsLocationInfo);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     *
     * @param mmsLocationInfo
     * @return
     */
    @Override
    public MmsLocationInfo getMmsCellInformation(MmsLocationInfo mmsLocationInfo) {
        return cellLocationInfoFacade.getMmsShelfCellInfo(mmsLocationInfo);
    }

    /**
     *
     * @param mmsLocationInfo
     * @return
     */
    @Override
    public MmsLocationInfo getMmsCellInformationForWarehouse(MmsLocationInfo mmsLocationInfo) {
        return cellLocationInfoFacade.getMmsShelfCellInfoForWarehouse(mmsLocationInfo);
    }

    @Override
    public List<MmsLocationInfo> searchCellByShelfId(MmsShelfInfo mmsShelfInfoEntity) {
        return cellLocationInfoFacade.searchCellByShelfId(mmsShelfInfoEntity);
    }

    @Override
    public List<MmsLocationInfo> searchByParameterStoreAndShelf(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity) {
        return cellLocationInfoFacade.searchByParameterStoreAndShelf(storeInfoEntity, mmsShelfInfoEntity);
    }

    @Override
    public List<MmsLocationInfo> searchByAllParameters(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity, MmsLocationInfo mmsCellInfoEntity) {
        return cellLocationInfoFacade.searchByAllParameters(storeInfoEntity, mmsShelfInfoEntity, mmsCellInfoEntity);
    }

    @Override
    public List<MmsLocationInfo> searchCellByRackAndShelfId(MmsShelfInfo mmsShelfInfoEntity, MmsLocationInfo cellInfo) {
        return cellLocationInfoFacade.searchCellByRackAndShelfId(mmsShelfInfoEntity, cellInfo);
    }

    @Override
    public List<MmsLocationInfo> findAllItemInfo() {
        return cellLocationInfoFacade.findAll();
    }

    @Override
    public void saveOrUpdate(MmsLocationInfo mmsLocationInfo) {
        cellLocationInfoFacade.saveOrUpdate(mmsLocationInfo);
    }

    @Override
    public List<String> getMmsShelfColumnNameList() {
       return cellLocationInfoFacade.getMmsShelfColumnNameList();
    }

    @Override
    public List<MmsLocationInfo> getShelfListsByParameter(ColumnNameResolver columnNameResolver, MmsLocationInfo mmsCellInfoEntity, String columnValue) {
        return cellLocationInfoFacade.getShelfListsByParameter(columnNameResolver, mmsCellInfoEntity, columnValue);
    }

}
