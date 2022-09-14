/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsManageLocationDtlFacade extends AbstractFacade<MmsManageLocationDtl> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsManageLocationDtlFacade() {
        super(MmsManageLocationDtl.class);
    }

    public List<MmsManageLocationDtl> searchLocationInfoByStore(MmsStoreInformation storeEntity) {
        Query query = em.createNativeQuery("SELECT mld.*,\n"
                + "  ml.*\n"
                + "FROM mms_manage_location_dtl mld\n"
                + "INNER JOIN mms_manage_location ml\n"
                + "ON mld.manage_location_id= ml.id\n"
                + "WHERE ml.store_id='" + storeEntity.getStoreId() + "' ", MmsManageLocationDtl.class);
        return (List<MmsManageLocationDtl>) query.getResultList();

    }

    public List<MmsManageLocationDtl> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity, MmsStoreInformation storeEntity) {
        System.out.println("===========Inside Query ========");
//          System.out.println("===========Inside Query Store Id ========"+itemRegistrationEntity.getStoreId().getStoreId());
        System.out.println("===========Inside Query item code ========" + itemRegistrationEntity.getMatCode());

        Query query = em.createNativeQuery("SELECT mld.*,\n"
                + "  ml.*\n"
                + "FROM mms_manage_location_dtl mld\n"
                + "INNER JOIN mms_manage_location ml\n"
                + "ON mld.manage_location_id= ml.id\n"
                + "INNER JOIN mms_item_registration itr\n"
                + "ON mld.material_id =itr.material_id\n"
                + "WHERE ml.store_id='" + storeEntity.getStoreId() + "' "
                + "AND itr.MAT_CODE Like '" + itemRegistrationEntity.getMatCode() + "%'", MmsManageLocationDtl.class);
        return (List<MmsManageLocationDtl>) query.getResultList();
    }

    public List<MmsManageLocationDtl> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity) {
        Query query = em.createNativeQuery("SELECT mld.*,\n"
                + "  ml.*\n"
                + "FROM mms_manage_location_dtl mld\n"
                + "INNER JOIN mms_manage_location ml\n"
                + "ON mld.manage_location_id= ml.id\n"
                + "INNER JOIN mms_item_registration itr\n"
                + "ON mld.material_id =itr.material_id\n"
                + "WHERE ml.store_id='" + itemRegistrationEntity.getStoreId().getStoreId() + "' "
                + "AND itr.MAT_NAME Like '" + itemRegistrationEntity.getMatName() + "%'", MmsManageLocationDtl.class);
        return (List<MmsManageLocationDtl>) query.getResultList();
    }

    public List<MmsManageLocationDtl> searchLocationInfoByCellId(MmsManageLocationDtl location) {
        Query query = em.createNamedQuery("MmsManageLocationDtl.findByCellId");
        query.setParameter("cellId", location.getCellId());
        try {

            List<MmsManageLocationDtl> locationinfo = new ArrayList(query.getResultList());

            return locationinfo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsManageLocationDtl> findLocListById(MmsManageLocationDtl manLocDtlEntity) {
        System.out.println("-------Inside Facede ## ------------");
        System.out.println("-----------Mat Id @Facade------------" + manLocDtlEntity.getMaterialId());
        Query query = em.createNamedQuery("MmsManageLocation.findByMatId");
        query.setParameter("materialId", manLocDtlEntity.getMaterialId());
        System.out.println(".......size @facade for LocationList......" + query.getResultList().size());
        ArrayList<MmsManageLocationDtl> listOf = new ArrayList<>(query.getResultList());

        return listOf;
    }

    public List<MmsManageLocationDtl> getManLocationByLocId(MmsManageLocationDtl manLocDtl) {
        System.out.println("............@facade2...............");
        Integer matId = manLocDtl.getMaterialId().getMaterialId();
        Integer locId = manLocDtl.getCellId().getLocationId();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_MANAGE_LOCATION fat   \n"
                + "                WHERE fat.LOCATION_ID ='" + locId + "' AND fat.MATERIAL_ID = '" + matId + "' ", MmsManageLocation.class);
        System.out.println(".......size @ facade for loc select......" + query1.getResultList().size());
        ArrayList<MmsManageLocationDtl> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsManageLocationDtl> getManLocationByLocId(MmsManageLocationDtl manLocDtl, MmsLocationInfo locInfo) {
        System.out.println("............@facade2...............");
        Integer matId = manLocDtl.getMaterialId().getMaterialId();
        Integer locId = locInfo.getLocationId();
        //Integer locId = manLocDtl.getCellId().getLocationId();
        System.out.println("------ Loc Id On Facade -- --  " + locInfo);
        System.out.println("------ Loc Id On Facade 222-- --  " + locId);
        Query query1 = em.createNativeQuery("select mld.*\n"
                + "from MMS_MANAGE_LOCATION_DTL mld\n"
                + "INNER JOIN  MMS_MANAGE_LOCATION ml \n"
                + "on  mld.manage_location_id = ml.id\n"
                + "where mld.CELL_ID ='" + locInfo.getLocationId() + "' AND mld.MATERIAL_ID = '" + matId + "' ", MmsManageLocationDtl.class);
        //System.out.println(".......size @ facade for loc select......" + query1.getResultList().size());
        ArrayList<MmsManageLocationDtl> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public MmsManageLocationDtl findByDtlId(MmsManageLocationDtl manLocDtlEntity) {
        Query query = em.createNamedQuery("MmsManageLocationDtl.findById");
        query.setParameter("id", manLocDtlEntity.getId());
        try {

            MmsManageLocationDtl locationinfo = new MmsManageLocationDtl();
            locationinfo = (MmsManageLocationDtl) query.getResultList().get(0);
            return locationinfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsManageLocationDtl> getLocationByStoreAndMatId(MmsItemRegistration itemRegistrationEntity, MmsStoreInformation storeEntity) {
        System.out.println("............getLocationByStoreAndMatId...............");

        System.out.println("------ StoreId -- --  " + storeEntity.getStoreId());
        System.out.println("------ MaterialId-- --  " + itemRegistrationEntity.getMaterialId());
        Query query = em.createNativeQuery("SELECT mld.*,\n"
                + "  ml.*\n"
                + "FROM mms_manage_location_dtl mld\n"
                + "INNER JOIN mms_manage_location ml\n"
                + "ON mld.manage_location_id= ml.id\n"
                + "WHERE ml.store_id='" + storeEntity.getStoreId() + "' "
                + "AND mld.material_id= '" + itemRegistrationEntity.getMaterialId() + "'", MmsManageLocationDtl.class);
        return (List<MmsManageLocationDtl>) query.getResultList();

    }

    public MmsManageLocationDtl getItemInfo(MmsItemRegistration itemRegistration, MmsStoreInformation storeInformation) {
        System.out.println("here U "+itemRegistration.getMaterialId()+" jsjf "+storeInformation.getStoreId());
        Query query = em.createNamedQuery("MmsManageLocationDtl.findByMatIdAndStoreId");
        query.setParameter("materialId", itemRegistration);
        query.setParameter("storeId", storeInformation.getStoreId());
        try {
            MmsManageLocationDtl itemInfo = new MmsManageLocationDtl();
            if (query.getResultList().size() > 0) {
                itemInfo = (MmsManageLocationDtl) query.getResultList().get(0);
            }
            return itemInfo;
        } catch (Exception e) {
            return null;
        }

    }

    public List<MmsManageLocationDtl> getMangLocListsByParameter(ColumnNameResolver columnNameResolver, MmsManageLocation mmsManageLocationEntity, String columnValue) {
      System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsManageLocationDtl> colValueLists = new ArrayList<>();
        if (mmsManageLocationEntity.getColumnName() != null && !mmsManageLocationEntity.getColumnName().equals("")
                && mmsManageLocationEntity.getColumnValue() != null && !mmsManageLocationEntity.getColumnValue().equals("")) {
            System.out.println("when if");  

            Query query = em.createNativeQuery("SELECT * FROM MMS_MANAGE_LOCATION\n"
                   + "   WHERE " + mmsManageLocationEntity.getColumnName().toLowerCase() + "='" + mmsManageLocationEntity.getColumnValue() + "'"
                    + "and " + mmsManageLocationEntity.getProcessedBy() + "='" + mmsManageLocationEntity.getProcessedBy() + "'", MmsManageLocationDtl.class);
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
