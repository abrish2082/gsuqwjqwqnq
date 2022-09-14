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
import et.gov.eep.mms.mapper.MmsManageLocationDtlFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class ManageLocationDtlBean implements ManageLocationDtlBeanLocal {

    @EJB
    MmsManageLocationDtlFacade manageLocationDtlFacade;

    @Override
    public List<MmsManageLocationDtl> searchLocationInfoByStore(MmsStoreInformation storeEntity) {
        return manageLocationDtlFacade.searchLocationInfoByStore(storeEntity);
    }

    @Override
    public List<MmsManageLocationDtl> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity, MmsStoreInformation storeEntity) {
        return manageLocationDtlFacade.searchByStoreAndItemCode(itemRegistrationEntity, storeEntity);
    }

    @Override
    public List<MmsManageLocationDtl> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity) {
        return manageLocationDtlFacade.searchByStoreAndItemName(itemRegistrationEntity);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void edit(MmsManageLocationDtl ManageLocationDtl) {
        manageLocationDtlFacade.edit(ManageLocationDtl);
    }

    @Override
    public List<MmsManageLocationDtl> searchLocationInfoByCellId(MmsManageLocationDtl location) {
        return manageLocationDtlFacade.searchLocationInfoByCellId(location);
    }

    @Override
    public List<MmsManageLocationDtl> findLocListById(MmsManageLocationDtl manLocDtlEntity) {
        return manageLocationDtlFacade.findLocListById(manLocDtlEntity);
    }

    @Override
    public List<MmsManageLocationDtl> getManLocationByLocId(MmsManageLocationDtl manLocDtl) {
        return manageLocationDtlFacade.getManLocationByLocId(manLocDtl);
    }

    @Override
    public List<MmsManageLocationDtl> getManLocationByLocId(MmsManageLocationDtl manLocDtl, MmsLocationInfo locInfo) {
        System.out.println("......location...." + locInfo.getLocationId());
        return manageLocationDtlFacade.getManLocationByLocId(manLocDtl, locInfo);
    }

    @Override
    public MmsManageLocationDtl findByDtlId(MmsManageLocationDtl manLocDtlEntity) {
        return manageLocationDtlFacade.findByDtlId(manLocDtlEntity);
    }

    @Override
    public List<MmsManageLocationDtl> getByStoreAndMaterialId(MmsItemRegistration itemRegistrationEntity, MmsStoreInformation storeInfoEntity) {
        return manageLocationDtlFacade.getLocationByStoreAndMatId(itemRegistrationEntity, storeInfoEntity);
    }

    @Override
    public List<MmsManageLocationDtl> findallInfo() {
        return manageLocationDtlFacade.findAll();
    }

    @Override
    public MmsManageLocationDtl getItemInfo(MmsItemRegistration itemRegEntity, MmsStoreInformation storeInfoEntity) {
        return manageLocationDtlFacade.getItemInfo(itemRegEntity, storeInfoEntity);
    }

    @Override
    public List<MmsManageLocationDtl> getMangLocListsByParameter(ColumnNameResolver columnNameResolver, MmsManageLocation mmsManageLocationEntity, String columnValue) {
        return manageLocationDtlFacade.getMangLocListsByParameter(columnNameResolver, mmsManageLocationEntity, columnValue);
    }
}
