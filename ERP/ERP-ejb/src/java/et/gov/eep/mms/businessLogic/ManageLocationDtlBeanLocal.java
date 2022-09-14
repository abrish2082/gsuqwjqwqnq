/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface ManageLocationDtlBeanLocal {

    void edit(MmsManageLocationDtl ManageLocationDtl);

    public List<MmsManageLocationDtl> searchLocationInfoByStore(MmsStoreInformation storeEntity);

    public List<MmsManageLocationDtl> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity, MmsStoreInformation storeInfoEntity);

    public List<MmsManageLocationDtl> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity);

    public List<MmsManageLocationDtl> searchLocationInfoByCellId(MmsManageLocationDtl location);

    public List<MmsManageLocationDtl> findLocListById(MmsManageLocationDtl manLocDtlEntity);

    public List<MmsManageLocationDtl> getManLocationByLocId(MmsManageLocationDtl manLocDtl);

    public List<MmsManageLocationDtl> getManLocationByLocId(MmsManageLocationDtl manLocDtl, MmsLocationInfo locInfo);

    public MmsManageLocationDtl findByDtlId(MmsManageLocationDtl manLocDtlEntity);

    public List<MmsManageLocationDtl> getByStoreAndMaterialId(MmsItemRegistration itemRegistrationEntity, MmsStoreInformation storeInfoEntity);

    public List<MmsManageLocationDtl> findallInfo();

    public MmsManageLocationDtl getItemInfo(MmsItemRegistration itemRegEntity, MmsStoreInformation storeInfoEntity);

    public List<MmsManageLocationDtl> getMangLocListsByParameter(ColumnNameResolver columnNameResolver, MmsManageLocation mmsManageLocationEntity, String columnValue);

   
}
