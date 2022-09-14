/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsShelfInfo;
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
public class MmsShelfInfoFacade extends AbstractFacade<MmsShelfInfo> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsShelfInfoFacade() {
        super(MmsShelfInfo.class);
    }

    /**
     *
     * @param shelfInformation
     * @return
     */
    public ArrayList<MmsShelfInfo> getWarehouseNameList(MmsShelfInfo shelfInformation) {
        Query query = em.createNamedQuery("MmsShelfInfo.findByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", shelfInformation.getStoreId());

        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    //find warehouse lists
    public ArrayList<MmsShelfInfo> getWarehouseList(MmsShelfInfo shelfInformation) {
        Query query = em.createNamedQuery("MmsShelfInfo.findWarehouseByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", shelfInformation.getStoreId());

        try {
            ArrayList<MmsShelfInfo> warhouse = new ArrayList(query.getResultList());
            return warhouse;
        } catch (Exception ex) {
            return null;
        }
    }

    //find Closed Shade List
    public ArrayList<MmsShelfInfo> getClosedShadeList(MmsStoreInformation storeInformation) {
        Query query = em.createNamedQuery("MmsShelfInfo.findClosedShadeByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", storeInformation);

        try {
            ArrayList<MmsShelfInfo> warhouse = new ArrayList(query.getResultList());
            return warhouse;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsShelfInfo getShelfInformation(MmsShelfInfo shelfInfo) {
        Query query = em.createNamedQuery("MmsShelfInfo.findByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", shelfInfo.getStoreId());
        query.setParameter("shadeName", "not a Shade");
        try {
            MmsShelfInfo mmsLocationInfos = (MmsShelfInfo) query.getResultList();
            return mmsLocationInfos;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsShelfInfo> searchByParameterStore(MmsShelfInfo shelfInfo) {

        Query query = em.createNamedQuery("MmsShelfInfo.findByparameterStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", shelfInfo.getStoreId());
        //query.setParameter("shadeName", "not a Shade");
        try {

            ArrayList<MmsShelfInfo> InventoryList = new ArrayList(query.getResultList());
            return InventoryList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> getShadeShelfList(MmsShelfInfo shadeInformation) {
        //Select option values -> For Rack=0 And ForOutdoor=1
        Query query = em.createNamedQuery("MmsShelfInfo.findByShadeNameAndStore", MmsShelfInfo.class);

        query.setParameter("storeId", shadeInformation.getStoreId());
        query.setParameter("shadeName", shadeInformation.getShadeName());
        query.setParameter("selectOption2", 0);
        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> getStoreShelfList(MmsShelfInfo rackInformation) {
        //Select option values -> For Rack=0 And ForOutdoor=1
        System.out.println("==========store id=======" + rackInformation.getStoreId());
        Query query = em.createNamedQuery("MmsShelfInfo.findRackByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", rackInformation.getStoreId());
        query.setParameter("shadeName", "not a Shade");
        query.setParameter("selectOption2", 0);

        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> getStoreOutdoorCodeList(MmsStoreInformation storeInformation) {
        //Select option values -> For Rack=0 And ForOutdoor=1
        System.out.println("==========store id=======" + storeInformation.getStoreId());
        Query query = em.createNamedQuery("MmsShelfInfo.findOutdoorByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", storeInformation.getStoreId());
        query.setParameter("shadeName", "not a Shade");
        query.setParameter("selectOption2", 1);

        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> searchByParameterShelfAndStore(MmsShelfInfo mmsShelfInfoEntity, MmsStoreInformation store) {
//       Query query = em.createNamedQuery("MmsShelfInfo.findByStore",MmsShelfInfo.class);
//        query.setParameter("storeName", mmsShelfInfoEntity.getStoreId().getStoreName()+ '%');
//        query.setParameter("shelfCode", mmsShelfInfoEntity.getShelfCode()+ '%');
//        
//         try {
//            
//            ArrayList<MmsShelfInfo> ShelfList = new ArrayList(query.getResultList());
//            return ShelfList;
//        } catch (Exception ex) {
//            return null;
//        }
        try {
            Query query = em.createNativeQuery("SELECT shi.* "
                    + "FROM mms_shelf_info shi "
                    + "INNER JOIN mms_store_information sti "
                    + "ON shi.store_id   = sti.store_id "
                    + "WHERE sti.store_id='" + store.getStoreId() + "' "
                    + "AND shi.rack_code Like '" + mmsShelfInfoEntity.getRackCode() + "%'",
                    MmsShelfInfo.class);

            return (List<MmsShelfInfo>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> searchRackForStoreByStoreId(MmsStoreInformation storeInfoEntity) {
        System.out.println("==============store id@facade======" + storeInfoEntity.getStoreId());
        Query query = em.createNamedQuery("MmsShelfInfo.findRackByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", storeInfoEntity);

        query.setParameter("rackOrOutdoor", 0);
        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> searchRackForClosedShadeByStoreId(MmsStoreInformation storeInfoEntity) {
        System.out.println("==============store id@facade======" + storeInfoEntity.getStoreId());
        Query query = em.createNamedQuery("MmsShelfInfo.findclosedShadeRackByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("rackOrOutdoor", 1);
        query.setParameter("hasArack", 0);
        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> searchClosedShadeShelfByStoreIdAndShadeName(MmsStoreInformation storeInfoEntity, MmsShelfInfo shadeName, int option) {
        System.out.println("==============store id@facade======" + storeInfoEntity.getStoreId());
        System.out.println("===========Shade Name=======" + shadeName.getShadeName());
        System.out.println("==========option========" + option);
        Query query = em.createNamedQuery("MmsShelfInfo.findclosedShadeRackByStoreId", MmsShelfInfo.class);
        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("shadeName", shadeName.getShadeName());
        query.setParameter("selectOption2", option);
        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsShelfInfo getStoreShelfInfoByStoreAndShelfIds(MmsShelfInfo shelfInfo, MmsStoreInformation store) {
        Query query = em.createNamedQuery("MmsShelfInfo.findByStoreIdAndShelfId", MmsShelfInfo.class);
        System.out.println("==========store id@ FACade====" + store.getStoreId());
        System.out.println("==========Shelf id@ FACade====" + shelfInfo.getShelfId());
        query.setParameter("storeId", store);
        query.setParameter("shelfId", shelfInfo.getShelfId());
        query.setParameter("shadeName", "not a Shade");
        try {
            MmsShelfInfo mmsLocationInfos = (MmsShelfInfo) query.getSingleResult();
            System.out.println("=========@Facade returned value=======");
            return mmsLocationInfos;
        } catch (Exception ex) {
            System.out.println("==========@Facade inside Catch=======");
            return null;
        }
    }

    public MmsShelfInfo getClosedShedShelfInfoByStoreAndShelfIds(MmsShelfInfo shelfInfo, MmsStoreInformation store) {
        Query query = em.createNamedQuery("MmsShelfInfo.findByStoreIdAndShelfId", MmsShelfInfo.class);
        System.out.println("==========store id@ FACade====" + store.getStoreId());
        System.out.println("==========Shelf id@ FACade====" + shelfInfo.getShelfId());
        query.setParameter("storeId", store);
        query.setParameter("shelfId", shelfInfo.getShelfId());
        query.setParameter("shadeName", shelfInfo.getShadeName());
        try {
            MmsShelfInfo mmsLocationInfos = (MmsShelfInfo) query.getSingleResult();
            System.out.println("=========@Facade returned value=======");
            return mmsLocationInfos;
        } catch (Exception ex) {
            System.out.println("==========@Facade inside Catch=======");
            return null;
        }
    }

    public List<MmsShelfInfo> searchOutdoorCodes(MmsStoreInformation storeInfoEntity, MmsShelfInfo shelf) {

        Query query = em.createNamedQuery("MmsShelfInfo.findoutdoorCodesByparameters", MmsShelfInfo.class);
        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("outdoorType", shelf.getOutdoorType());
        query.setParameter("rackOrOutdoor", shelf.getRackOrOutdoor());
        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> searchStoreRacks(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity) {

        Query query = em.createNamedQuery("MmsShelfInfo.findStoreRacksByparameters", MmsShelfInfo.class);
        query.setParameter("storeId", storeInfoEntity);

        query.setParameter("rackOrOutdoor", mmsShelfInfoEntity.getRackOrOutdoor());
        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsShelfInfo> searchClosedShadeRackCodes(MmsStoreInformation storeInfoEntity, MmsShelfInfo mmsShelfInfoEntity) {
        System.out.println("==========store id@ FACade====" + storeInfoEntity.getStoreId());
        System.out.println("==========rackoroutdoor@ FACade====" + mmsShelfInfoEntity.getRackOrOutdoor());
        System.out.println("==========has a rack @ FACade====" + mmsShelfInfoEntity.getHasArack());
        Query query = em.createNamedQuery("MmsShelfInfo.findClosedShadeRacksByparameters", MmsShelfInfo.class);
        query.setParameter("storeId", storeInfoEntity);

        query.setParameter("rackOrOutdoor", mmsShelfInfoEntity.getRackOrOutdoor());
        query.setParameter("hasArack", mmsShelfInfoEntity.getHasArack());
        try {
            ArrayList<MmsShelfInfo> shelfInformations = new ArrayList(query.getResultList());
            return shelfInformations;
        } catch (Exception ex) {
            return null;
        }

    }

    public List<String> getMmsShelfInfoColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_SHELF_INFO')\n"
                + "   and COLUMN_NAME NOT IN ('STORE_ID','SHELF_ID')\n"
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

    public List<MmsShelfInfo> getShelfListsByParameter(MmsShelfInfo mmsShelfInfoEntity) {
        System.out.println("it's facade");
        System.out.println("here I facade colName " + mmsShelfInfoEntity.getColumnName() + " colVal " + mmsShelfInfoEntity.getColumnValue());
        List<MmsShelfInfo> colValueLists = new ArrayList<>();
        if (mmsShelfInfoEntity.getColumnName() != null && !mmsShelfInfoEntity.getColumnName().equals("")
                && mmsShelfInfoEntity.getColumnValue() != null && !mmsShelfInfoEntity.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_SHELF_INFO\n"
                    + "   WHERE " + mmsShelfInfoEntity.getColumnName().toLowerCase() + "='" + mmsShelfInfoEntity.getColumnValue() + "'"
                    + "and " + mmsShelfInfoEntity.getProcessedBy() + "='" + mmsShelfInfoEntity.getProcessedBy() + "'", MmsShelfInfo.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                    System.out.println("list of shelf size " + colValueLists.size());
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("MmsShelfInfo.findByProcessedBy");
            query.setParameter("ProcessedBy", mmsShelfInfoEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<MmsShelfInfo> getShelfListsByParameter(ColumnNameResolver columnNameResolver, MmsShelfInfo mmsShelfInfoEntity, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsShelfInfo> colValueLists = new ArrayList<>();
        if (mmsShelfInfoEntity.getColumnName() != null && !mmsShelfInfoEntity.getColumnName().equals("")
                && mmsShelfInfoEntity.getColumnValue() != null && !mmsShelfInfoEntity.getColumnValue().equals("")) {
            System.out.println("when if");

            Query query = em.createNativeQuery("SELECT * FROM MMS_SHELF_INFO\n"                 
                    + "   WHERE " + mmsShelfInfoEntity.getColumnName().toLowerCase() + "='" + mmsShelfInfoEntity.getColumnValue() + "'"
                    + "and " + mmsShelfInfoEntity.getProcessedBy() + "='" + mmsShelfInfoEntity.getProcessedBy() + "'", MmsShelfInfo.class);
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
            Query query = em.createNamedQuery("MmsShelfInfo.findByProcessedBy");
            query.setParameter("ProcessedBy", mmsShelfInfoEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }

    
}

}
