/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsShelfInfoFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsShelfInfoBean implements MmsShelfInfoBeanLocal {
    @EJB
    MmsShelfInfoFacade mmsShelfInfofacade;

    /**
     *
     * @param mmsShelfInfoEntity
     */
    @Override
    public void create(MmsShelfInfo mmsShelfInfoEntity) {
       mmsShelfInfofacade.create(mmsShelfInfoEntity);
    }

    /**
     *
     * @param mmsShelfInfoEntity
     */
    @Override
    public void edit(MmsShelfInfo mmsShelfInfoEntity) {
        mmsShelfInfofacade.edit(mmsShelfInfoEntity);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     *
     * @param shelfInformation
     * @return
     */
    
    @Override
    public ArrayList<MmsShelfInfo> searchShelfInformation(MmsShelfInfo shelfInformation) {
        return mmsShelfInfofacade.getWarehouseNameList(shelfInformation);
    }

    @Override
    public MmsShelfInfo getMmsShelfInformation(MmsShelfInfo shelfInfo) {
       return mmsShelfInfofacade.getShelfInformation(shelfInfo);
    }

    @Override
    public List<MmsShelfInfo> searchByParameterStore(MmsShelfInfo shelfInfo) {
       return mmsShelfInfofacade.searchByParameterStore(shelfInfo);
    }

    @Override
    public List<MmsShelfInfo> searchWarehouseShelfInformation(MmsShelfInfo shelfInformation) {
        return  mmsShelfInfofacade.getShadeShelfList(shelfInformation);
    }

    @Override
    public List<MmsShelfInfo> searchStoreShelf(MmsShelfInfo shelfInformation) {
       return mmsShelfInfofacade.getStoreShelfList(shelfInformation);
    }

    @Override
    public List<MmsShelfInfo> searchByParameterShelfAndStore(MmsShelfInfo mmsShelfInfoEntity, MmsStoreInformation store) {
       return mmsShelfInfofacade.searchByParameterShelfAndStore(mmsShelfInfoEntity,store);
    }

    @Override
    public List<MmsShelfInfo> findAll() {
       return mmsShelfInfofacade.findAll();
    }

    @Override
    public List<MmsShelfInfo> searchRackForStoreByStoreId(MmsStoreInformation storeInfoEntity) {
    return mmsShelfInfofacade.searchRackForStoreByStoreId(storeInfoEntity);
    }
    
     @Override
    public List<MmsShelfInfo> searchRackForClosedShadeByStoreId(MmsStoreInformation storeInfoEntity) {
    return mmsShelfInfofacade.searchRackForClosedShadeByStoreId(storeInfoEntity);
    }
    

    @Override
    public List<MmsShelfInfo> searchClosedShadeShelfByStoreIdAndShadeName(MmsStoreInformation storeInfoEntity, MmsShelfInfo shadeName,int option) {
       return mmsShelfInfofacade.searchClosedShadeShelfByStoreIdAndShadeName(storeInfoEntity, shadeName, option);
    }
    
    

    @Override
    public ArrayList<MmsShelfInfo> getWarhouseListByStoreId(MmsShelfInfo shelfInformation) {
return mmsShelfInfofacade.getWarehouseList(shelfInformation);
    }

    @Override
    public MmsShelfInfo getRackInformationByShelfId(MmsShelfInfo rackinfo,MmsStoreInformation store) {
        return mmsShelfInfofacade.getStoreShelfInfoByStoreAndShelfIds(rackinfo,store);
    }

    @Override
    public MmsShelfInfo getClosedShadeRackInformationByShelfId(MmsShelfInfo rackinfo, MmsStoreInformation store) {
        return mmsShelfInfofacade.getClosedShedShelfInfoByStoreAndShelfIds(rackinfo, store);
    }

    @Override
    public List<MmsShelfInfo> searchStoreOutdoor(MmsStoreInformation storeInformation) {
        return mmsShelfInfofacade.getStoreOutdoorCodeList(storeInformation);
    }

    @Override
    public ArrayList<MmsShelfInfo> getClosedShadeListByStoreId(MmsStoreInformation storeId) {
        return mmsShelfInfofacade.getClosedShadeList(storeId);
    }
    
     @Override
    public List<MmsShelfInfo> searchOutdoorCodes(MmsStoreInformation storeInfoEntity, MmsShelfInfo shelf) {
       return mmsShelfInfofacade.searchOutdoorCodes(storeInfoEntity, shelf);
    }

    @Override
    public List<MmsShelfInfo> searchStoreRacks(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity) {
          return mmsShelfInfofacade.searchStoreRacks(storeInfoEntity, mmsShelfInfoEntity);
    }

    @Override
    public List<MmsShelfInfo> searchClosedShadeRackCodes(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity) {
       return mmsShelfInfofacade.searchClosedShadeRackCodes(storeInfoEntity,mmsShelfInfoEntity);
    }

    @Override
    public List<String> getMmsShelfInfoColumnNameList() {
       return mmsShelfInfofacade.getMmsShelfInfoColumnNameList();
    }

    

    @Override
    public List<MmsShelfInfo> getShelfListsByParameter(ColumnNameResolver columnNameResolver, MmsShelfInfo mmsShelfInfoEntity, String columnValue) {
        return mmsShelfInfofacade.getShelfListsByParameter(columnNameResolver,mmsShelfInfoEntity,columnValue);
    }
    
   
}
