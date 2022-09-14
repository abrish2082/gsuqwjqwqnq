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
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsLocationInfoFacade extends AbstractFacade<MmsLocationInfo> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsLocationInfoFacade() {
        super(MmsLocationInfo.class);
    }

    /**
     *
     * @param mmsLocationInfo
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">
    public MmsLocationInfo getMmsShelfCellInfo(MmsLocationInfo mmsLocationInfo) {
        Query query = em.createNamedQuery("MmsLocationInfo.findByStoreIdandShelfId", MmsLocationInfo.class);
        // query.setParameter("storeId", mmsLocationInfo.getStoreId());
        query.setParameter("shelfId", mmsLocationInfo.getShelfId());
        try {
            MmsLocationInfo mmsLocationInfos = (MmsLocationInfo) query.getResultList().get(0);
            return mmsLocationInfos;
        } catch (Exception ex) {

            return null;
        }
    }

    /**
     *
     * @param mmsLocationInfo
     * @return
     */
    public MmsLocationInfo getMmsShelfCellInfoForWarehouse(MmsLocationInfo mmsLocationInfo) {
        Query query = em.createNamedQuery("MmsLocationInfo.findByStoreIdandShelfIdAndClosedShadename", MmsLocationInfo.class);
//        query.setParameter("storeId", mmsLocationInfo.getStoreId());
        query.setParameter("shelfId", mmsLocationInfo.getShelfId());
        query.setParameter("closedShadeName", mmsLocationInfo.getClosedShadeName());
        try {
            MmsLocationInfo mmsLocationInfos = (MmsLocationInfo) query.getResultList().get(0);
            return mmsLocationInfos;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsLocationInfo> searchCellByShelfId(MmsShelfInfo mmsShelfInfoEntity) {
        Query query = em.createNamedQuery("MmsLocationInfo.findByShelfId", MmsLocationInfo.class);
//        query.setParameter("storeId", mmsLocationInfo.getStoreId());
        query.setParameter("shelfId", mmsShelfInfoEntity);
        //query.setParameter("warehosueName", mmsLocationInfo.getWarehosueName());
        try {
            ArrayList<MmsLocationInfo> cellInformations = new ArrayList(query.getResultList());

            return cellInformations;
        } catch (Exception ex) {
            System.out.println("ex = " + ex);
            return null;
        }
    }

    public List<MmsLocationInfo> searchCellByRackAndShelfId(MmsShelfInfo mmsShelfInfoEntity, MmsLocationInfo cellInfo) {

        Query query = em.createNamedQuery("MmsLocationInfo.findByRackAndShelfId", MmsLocationInfo.class);
//        query.setParameter("storeId", mmsLocationInfo.getStoreId());
        query.setParameter("shelfId", mmsShelfInfoEntity);
        query.setParameter("cellRow", cellInfo.getCellRow());
        try {
            ArrayList<MmsLocationInfo> cellInformations = new ArrayList(query.getResultList());

            return cellInformations;
        } catch (Exception ex) {
            System.out.println("ex = " + ex);
            return null;
        }

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsLocationInfo> searchByParameterStoreAndShelf(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity) {
        try {
            Query query = em.createNativeQuery("SELECT * "
                    + "FROM mms_location_info loc "
                    + "INNER JOIN mms_shelf_info shf "
                    + "ON loc.shelf_id= shf.shelf_id "
                    + "INNER JOIN mms_store_information st "
                    + "ON shf.store_id  = st.store_id "
                    + "WHERE st.store_id='" + storeInfoEntity.getStoreId() + "' "
                    + "AND shf.rack_code LIKE '" + mmsShelfInfoEntity.getRackCode() + "%'", MmsLocationInfo.class);
            return (List<MmsLocationInfo>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsLocationInfo> searchByAllParameters(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity, MmsLocationInfo mmsCellInfoEntity) {

        try {
            Query query = em.createNativeQuery("SELECT * "
                    + "FROM mms_location_info loc "
                    + "INNER JOIN mms_shelf_info shf "
                    + "ON loc.shelf_id= shf.shelf_id "
                    + "INNER JOIN mms_store_information st "
                    + "ON shf.store_id  = st.store_id "
                    + "WHERE st.store_id='" + storeInfoEntity.getStoreId() + "' "
                    + "AND shf.rack_code LIKE '" + mmsShelfInfoEntity.getRackCode() + "%'"
                    + "AND loc.cell_code LIKE '" + mmsCellInfoEntity.getCellCode() + "%'", MmsLocationInfo.class);
            return (List<MmsLocationInfo>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<String> getMmsShelfColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_LOCATION_INFO')\n"
                + "   and COLUMN_NAME NOT IN ('LOCATION_ID','SHELF_ID','PROCESSED_BY')\n"
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
     public List<MmsLocationInfo> getShelfListsByParameter(ColumnNameResolver columnNameResolver, MmsLocationInfo mmsCellInfoEntity, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsLocationInfo> colValueLists = new ArrayList<>();
        if (mmsCellInfoEntity.getColumnName() != null && !mmsCellInfoEntity.getColumnName().equals("")
                && mmsCellInfoEntity.getColumnValue() != null && !mmsCellInfoEntity.getColumnValue().equals("")) {
            System.out.println("when if"); 

            Query query = em.createNativeQuery("SELECT * FROM MMS_LOCATION_INFO\n"                    
                     + "   WHERE " + mmsCellInfoEntity.getColumnName().toLowerCase() + "='" + mmsCellInfoEntity.getColumnValue() + "'", MmsLocationInfo.class);
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
            Query query = em.createNamedQuery("MmsLocationInfo.findAll");
//            query.setParameter("ProcessedBy", mmsCellInfoEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }

    }
// </editor-fold>

}
