/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;


/**
 *
 * @author Minab
 */
@Local
public interface MmsManageLocationBeanLocal {

    /**
     *
     * @param ManageLocation
     */
    void create(MmsManageLocation ManageLocation);

    /**
     *
     * @param ManageLocation
     */
    void edit(MmsManageLocation ManageLocation);

    /**
     *
     * @param ManageLocation
     */
    void remove(MmsManageLocation ManageLocation);

    /**
     *
     * @param itemRegistration
     * @return
     */
    public MmsManageLocation getManageLocationInfo(MmsItemRegistration itemRegistration);

    public List<MmsManageLocation> findAllItemInfo();

    //public MmsManageLocation findLocById(MmsManageLocation managedLocationEntity);
     public MmsManageLocation findId(MmsManageLocation managedLocationEntity);
   

//    public List<MmsManageLocation> findLocListById(MmsManageLocation managedLocationEntity);
    
    public MmsManageLocation searchLocationInfoByStore(MmsStoreInformation storeEntity);
    public List<MmsManageLocation> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity);
    public List<MmsManageLocation> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity);
   
   // public MmsManageLocation findbyMatId(MmsManageLocation managedLocationEntity);

    public List<MmsManageLocation> searchBYStoreId(MmsStoreInformation storeInfoEntity);
     public MmsManageLocation findById(MmsManageLocation managedLocationEntity);

    public List<String> getMmsManageLocationColumnNameList();

    public List<MmsManageLocation> getMangLocListsByParameter(ColumnNameResolver columnNameResolver, MmsManageLocation mmsManageLocationEntity,String columnValue);
}
