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
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsManageLocationFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsManageLocationBean implements MmsManageLocationBeanLocal {

    @EJB
    MmsManageLocationFacade manageLocationFacade;

    /**
     *
     * @param ManageLocation
     */
    @Override
    public void create(MmsManageLocation ManageLocation) {
        manageLocationFacade.create(ManageLocation);
    }

    /**
     *
     * @param ManageLocation
     */
    @Override
    public void edit(MmsManageLocation ManageLocation) {
        manageLocationFacade.edit(ManageLocation);
    }

    /**
     *
     * @param ManageLocation
     */
    @Override
    public void remove(MmsManageLocation ManageLocation) {
        manageLocationFacade.remove(ManageLocation);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     *
     * @param itemRegistration
     * @return
     */
    @Override
    public MmsManageLocation getManageLocationInfo(MmsItemRegistration itemRegistration) {
        return manageLocationFacade.getManageLocInfo(itemRegistration);
    }

    @Override
    public List<MmsManageLocation> findAllItemInfo() {
        return manageLocationFacade.findAll();
    }

//    @Override
//    public MmsManageLocation findLocById(MmsManageLocation managedLocationEntity) {
//        return manageLocationFacade.findLocById(managedLocationEntity);
//    }
//    
//    @Override
//    public List<MmsManageLocation> findLocListById(MmsManageLocation managedLocationEntity) {
//        return manageLocationFacade.findLocListById(managedLocationEntity);
//    }
    @Override
    public MmsManageLocation searchLocationInfoByStore(MmsStoreInformation storeEntity) {
        return manageLocationFacade.searchLocationInfoByStore(storeEntity);

    }

    @Override
    public List<MmsManageLocation> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity) {
        return manageLocationFacade.searchByStoreAndItemCode(itemRegistrationEntity);
    }

    @Override
    public List<MmsManageLocation> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity) {
        return manageLocationFacade.searchByStoreAndItemName(itemRegistrationEntity);
    }

    @Override
    public MmsManageLocation findById(MmsManageLocation managedLocationEntity) {
        return manageLocationFacade.findById(managedLocationEntity);
    }

    @Override
    public MmsManageLocation findId(MmsManageLocation managedLocationEntity) {
        return manageLocationFacade.findId(managedLocationEntity);
    }

    @Override
    public List<MmsManageLocation> searchBYStoreId(MmsStoreInformation storeInfoEntity) {
        return manageLocationFacade.searchBYStoreId(storeInfoEntity);
    }

    @Override
    public List<String> getMmsManageLocationColumnNameList() {
        return manageLocationFacade.getMmsManageLocationColumnNameList();
    }

    @Override
    public List<MmsManageLocation> getMangLocListsByParameter(ColumnNameResolver columnNameResolver, MmsManageLocation mmsManageLocationEntity, String columnValue) {
        return manageLocationFacade.getMangLocListsByParameter(columnNameResolver, mmsManageLocationEntity, columnValue);
    }

}
