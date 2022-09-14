/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface MmsShelfInfoBeanLocal {

    /**
     *
     * @param mmsShelfInfoEntity
     */
    public void create(MmsShelfInfo mmsShelfInfoEntity);

    /**
     *
     * @param mmsShelfInfoEntity
     */
    public void edit(MmsShelfInfo mmsShelfInfoEntity);

    /**
     *
     * @param shelfInformation
     * @return
     */
    public ArrayList<MmsShelfInfo> searchShelfInformation(MmsShelfInfo shelfInformation);
    
    public MmsShelfInfo getMmsShelfInformation(MmsShelfInfo shelfInfo);
    public List<MmsShelfInfo> searchByParameterStore(MmsShelfInfo shelfInfo);
     public List<MmsShelfInfo> searchWarehouseShelfInformation(MmsShelfInfo shelfInformation);
     public List<MmsShelfInfo> searchStoreShelf(MmsShelfInfo shelfInformation);
       public List<MmsShelfInfo> searchStoreOutdoor(MmsStoreInformation storeInformation);

    public List<MmsShelfInfo> searchByParameterShelfAndStore(MmsShelfInfo mmsShelfInfoEntity, MmsStoreInformation store);

    public List<MmsShelfInfo> findAll();

    public List<MmsShelfInfo> searchRackForStoreByStoreId(MmsStoreInformation storeInfoEntity);
            public List<MmsShelfInfo> searchClosedShadeShelfByStoreIdAndShadeName(MmsStoreInformation storeInfoEntity,MmsShelfInfo shadeName,int option);
     public ArrayList<MmsShelfInfo> getWarhouseListByStoreId(MmsShelfInfo shelfInformation);
     public ArrayList<MmsShelfInfo> getClosedShadeListByStoreId(MmsStoreInformation storeId);
     public MmsShelfInfo getRackInformationByShelfId(MmsShelfInfo rackinfo,MmsStoreInformation store);
     public MmsShelfInfo getClosedShadeRackInformationByShelfId(MmsShelfInfo rackinfo,MmsStoreInformation store);

    public List<MmsShelfInfo> searchRackForClosedShadeByStoreId(MmsStoreInformation storeInfoEntity);

  

    public List<MmsShelfInfo> searchOutdoorCodes(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity);

    public List<MmsShelfInfo> searchStoreRacks(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity);

    public List<MmsShelfInfo> searchClosedShadeRackCodes(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity);

    public List<String> getMmsShelfInfoColumnNameList();


    public List<MmsShelfInfo> getShelfListsByParameter(ColumnNameResolver columnNameResolver, MmsShelfInfo mmsShelfInfoEntity, String columnValue);
}
