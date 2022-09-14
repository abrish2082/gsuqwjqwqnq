/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsManageLocationFacade extends AbstractFacade<MmsManageLocation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsManageLocationFacade() {
        super(MmsManageLocation.class);
    }

    /**
     *
     * @param itemRegistration
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">
    public MmsManageLocation getManageLocInfo(MmsItemRegistration itemRegistration) {
        Query query = em.createNamedQuery("MmsManageLocation.findByMatId", MmsManageLocation.class);
        query.setParameter("materialId", itemRegistration.getMaterialId().intValue());
        try {
            MmsManageLocation mmsRemainingQuantity = (MmsManageLocation) query.getResultList().get(0);
            System.out.println("======Faceade============" + mmsRemainingQuantity.getRemainingQuantity());
            return mmsRemainingQuantity;
        } catch (Exception ex) {
            return null;
        }
    }

//    public MmsManageLocation findLocById(MmsManageLocation managedLocationEntity) {
//        System.out.println("............@facade.....");
//        Query query = em.createNamedQuery("MmsManageLocation.findByMatId",MmsManageLocation.class);
//       // query.setParameter("materialId", managedLocationEntity.getMaterialId().getMaterialId());
//       // System.out.println(".......@ facade......" + managedLocationEntity.getMaterialId().getMaterialId());
//
////    public List<MmsManageLocation> getManLocationByLocId(MmsManageLocation manLoc) {
////        System.out.println("............@facade2...............");
////        Integer matId = manLoc.getMaterialId().getMaterialId();
//////commented by sadik and changed locId by matId inside where        Integer locId = manLoc.getLocationId().getLocationId(); 
////        Query query1 = em.createNativeQuery("SELECT * \n"
////                + "                FROM MMS_MANAGE_LOCATION fat   \n"
////                + "                WHERE fat.LOCATION_ID ='" + matId + "' AND fat.MATERIAL_ID = '" + matId + "' ", MmsManageLocation.class);
////        System.out.println(".......size @ facade for loc select......" + query1.getResultList().size());
////        ArrayList<MmsManageLocation> listOf = new ArrayList<>(query1.getResultList());
////
////        return listOf;
////    }
//    }
    public List<MmsManageLocation> findLocListById(MmsManageLocation managedLocationEntity) {
        System.out.println("-------Inside Facede ## ------------");
        //System.out.println("-----------Mat Id @Facade------------"+managedLocationEntity.getMaterialId().getMaterialId());
        Query query = em.createNamedQuery("MmsManageLocation.findByMatId");
        //query.setParameter("materialId", managedLocationEntity.getMaterialId().getMaterialId());
        System.out.println(".......size @facade for LocationList......" + query.getResultList().size());
        ArrayList<MmsManageLocation> listOf = new ArrayList<>(query.getResultList());

        return listOf;
    }

    public MmsManageLocation searchLocationInfoByStore(MmsStoreInformation storeEntity) {
        Query query = em.createNamedQuery("MmsManageLocation.findByStoreId", MmsManageLocation.class);
        query.setParameter("storeId", storeEntity.getStoreId());

        try {
            MmsManageLocation ItemList = (MmsManageLocation) query.getResultList();
            System.out.println("==============@Facade size" + ItemList);
            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsManageLocation> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<MmsManageLocation> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Find by MatId not DepId
    public MmsManageLocation findbyMatId(MmsManageLocation managedLocationEntity) {
        Query query = em.createNamedQuery("MmsManageLocation.findByMatId");
        //query.setParameter("materialId", managedLocationEntity.getMaterialId().getMaterialId());
        try {
            MmsManageLocation getId = (MmsManageLocation) query.getResultList().get(0);
            return getId;
        } catch (Exception ex) {
            return null;
        }

    }

    public MmsManageLocation findId(MmsManageLocation managedLocationEntity) {
        Query query = em.createNamedQuery("MmsManageLocation.findById");
        query.setParameter("id", managedLocationEntity.getId());
        try {
            MmsManageLocation getId = (MmsManageLocation) query.getResultList().get(0);
            return getId;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsManageLocation findById(MmsManageLocation managedLocationEntity) {

        Query query = em.createNamedQuery("MmsManageLocation.findById");
        query.setParameter("id", managedLocationEntity.getId());
        try {
            MmsManageLocation getId = (MmsManageLocation) query.getResultList().get(0);
            return getId;
        } catch (Exception ex) {
            return null;
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsManageLocation> getManLocationByLocId(MmsManageLocation manLoc) {
        System.out.println("............@facade2...............");
        Integer matId = 85;
        //manLoc.getMaterialId().getMaterialId();
//commented by sadik and changed locId by matId inside where        Integer locId = manLoc.getLocationId().getLocationId(); 
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_MANAGE_LOCATION fat   \n"
                + "                WHERE fat.LOCATION_ID ='" + matId + "' AND fat.MATERIAL_ID = '" + matId + "' ", MmsManageLocation.class);
        System.out.println(".......size @ facade for loc select......" + query1.getResultList().size());
        ArrayList<MmsManageLocation> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsManageLocation> searchBYStoreId(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNativeQuery("SELECT mld.*,\n"
                + "  ml.*\n"
                + "FROM mms_manage_location_dtl mld\n"
                + "INNER JOIN mms_manage_location ml\n"
                + "ON mld.manage_location_id= ml.id\n"
                + "WHERE ml.store_id='" + storeInfoEntity.getStoreId() + "' ", MmsManageLocation.class);
        return (List<MmsManageLocation>) query.getResultList();
    }

    public List<String> getMmsManageLocationColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_MANAGE_LOCATION')\n"
                + "   and COLUMN_NAME NOT IN ('ID','STORE_ID','GRN_ID','SIV_ID','RMG_ID')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    // </editor-fold>

    public List<MmsManageLocation> getMangLocListsByParameter(ColumnNameResolver columnNameResolver, MmsManageLocation mmsManageLocationEntity, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsManageLocation> colValueLists = new ArrayList<>();
        if (mmsManageLocationEntity.getColumnName() != null && !mmsManageLocationEntity.getColumnName().equals("")
                && mmsManageLocationEntity.getColumnValue() != null && !mmsManageLocationEntity.getColumnValue().equals("")) {
            System.out.println("when if");  

            Query query = em.createNativeQuery("SELECT * FROM MMS_MANAGE_LOCATION\n"
                   + "   WHERE " + mmsManageLocationEntity.getColumnName().toLowerCase() + "='" + mmsManageLocationEntity.getColumnValue() + "'"
                    + "and " + mmsManageLocationEntity.getProcessedBy() + "='" + mmsManageLocationEntity.getProcessedBy() + "'", MmsManageLocation.class);
            try {
                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("MmsManageLocation.findByProcessedBy");
            query.setParameter("processedBy", mmsManageLocationEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }

    }

}
