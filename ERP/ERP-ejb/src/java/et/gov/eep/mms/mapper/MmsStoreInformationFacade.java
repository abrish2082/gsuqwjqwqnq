/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsStoreInformationFacade extends AbstractFacade<MmsStoreInformation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     */
    public MmsStoreInformationFacade() {
        super(MmsStoreInformation.class);
    }

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
     * @param information
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">
    public ArrayList<MmsStoreInformation> searchStoreInformation(MmsStoreInformation information) {
        Query query = em.createNamedQuery("MmsStoreInformation.SearchByStoreName", MmsStoreInformation.class);
        query.setParameter("storeName", information.getStoreName().toUpperCase() + '%');
        try {
            ArrayList<MmsStoreInformation> storeInformations = new ArrayList(query.getResultList());
            return storeInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsStoreInformation> searchStoreInformationByNameAndUserId(MmsStoreInformation information) {
        Query query = em.createNamedQuery("MmsStoreInformation.SearchByStoreNameAndUserId", MmsStoreInformation.class);
        query.setParameter("storeName", information.getStoreName().toUpperCase() + '%');
        // query.setParameter("ProcessedBy", information.getProcessedBy());

        try {
            ArrayList<MmsStoreInformation> storeInformations = new ArrayList(query.getResultList());
            return storeInformations;
        } catch (Exception ex) {
            return null;
        }
    }

//unused currently
    /**
     *
     * @param mmsStoreInformation
     * @return
     */
    public MmsStoreInformation getPapmsStoreInformation(MmsStoreInformation mmsStoreInformation) {
        Query query = em.createNamedQuery("MmsStoreInformation.findByStoreName", MmsStoreInformation.class);
        query.setParameter("storeName", mmsStoreInformation.getStoreName());
        try {
            MmsStoreInformation importationInfo = (MmsStoreInformation) query.getResultList().get(0);
            return importationInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param mmsStoreInformation
     * @return
     */
    public boolean getPapmsStoreInformationDup(MmsStoreInformation mmsStoreInformation) {
        boolean duplicaton;
        Query query = em.createNamedQuery("MmsStoreInformation.findByStoreNameAndStoreNo", MmsStoreInformation.class);
        query.setParameter("storeNames", mmsStoreInformation.getStoreName());
        query.setParameter("storeNos", mmsStoreInformation.getStoreNo());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    public MmsStoreInformation getLastStorenumber() {
        Query query = em.createNamedQuery("MmsStoreInformation.findByStoreIdMaximum");
        MmsStoreInformation result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsStoreInformation) query.getSingleResult();
            }
            System.out.println("=========num on facade=" + result);
            return result;
        } catch (Exception ex) {

            return null;
        }
    }

    public MmsStoreInformation searchByStoreId(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsStoreInformation.findByStoreId");
        query.setParameter("storeId", storeInfoEntity.getStoreId());
        try {
            MmsStoreInformation getStore = (MmsStoreInformation) query.getResultList().get(0);
            return getStore;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStoreInformation> searchStoreByParameterStoreNo(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsStoreInformation.findByStoreNo", MmsStoreInformation.class);
        query.setParameter("storeNo", storeInfoEntity.getStoreNo() + '%');
        try {
            ArrayList<MmsStoreInformation> storeInformations = new ArrayList(query.getResultList());
            return storeInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStoreInformation> searchStoreByParameterStoreNoAndPreparerId(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsStoreInformation.findByStoreNoAndPreparerId", MmsStoreInformation.class);
        query.setParameter("storeNo", storeInfoEntity.getStoreNo() + '%');
//        query.setParameter("processedBy", storeInfoEntity.getProcessedBy());

        try {
            ArrayList<MmsStoreInformation> storeInformations = new ArrayList(query.getResultList());
            return storeInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStoreInformation> searchByParameterStoreNoAndStoreNameAndPreparerId(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsStoreInformation.findByStoreNoAndStoreNameAndPreparerId", MmsStoreInformation.class);
        query.setParameter("storeNo", storeInfoEntity.getStoreNo() + '%');
        query.setParameter("storeName", storeInfoEntity.getStoreName() + '%');
        // query.setParameter("processedBy", storeInfoEntity.getProcessedBy());

        try {
            ArrayList<MmsStoreInformation> storeInformations = new ArrayList(query.getResultList());
            return storeInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStoreInformation> searchAllStoreInfoByPreparerId(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsStoreInformation.findAllByPreparerId", MmsStoreInformation.class);

        query.setParameter("processedBy", storeInfoEntity.getProcessedBy());

        try {
            ArrayList<MmsStoreInformation> storeInformations = new ArrayList(query.getResultList());
            return storeInformations;
        } catch (Exception ex) {
            return null;
        }
    }
// </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="NativeQuery">

    public List<MmsStoreInformation> searchStoreByParameterStoreNo(HrDepartments depId) {
        try {
            Query query1 = em.createNativeQuery("SELECT s.*\n"
                    + "FROM mms_store_information s\n"
                    + "INNER JOIN mms_lu_ware_house w\n"
                    + "ON s.warehouse_id   = w.id\n"
                    + "WHERE s.warehouse_id=\n"
                    + "  (SELECT sdmap.warehouse_id\n"
                    + "  FROM mms_store_to_hr_dep_mapper sdmap\n"
                    + "  WHERE sdmap.department_id='" + depId.getDepId() + "'\n"
                    + "  )", MmsStoreInformation.class);

            return (List<MmsStoreInformation>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }
//ITEM_GROUP = 0 is for Non_fixed items

    public List<MmsStoreInformation> getUnregisteredMartialByStoreName() {
        List<MmsStoreInformation> mmsStoreInformationList;
        try {

            Query query = em.createNativeQuery("SELECT DISTINCT msi.* "
                    + "FROM MMS_BIN_CARD mbc "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mbc.MATERIAL_ID = mir.MATERIAL_ID "
                    + "INNER JOIN MMS_STORE_INFORMATION msi "
                    + "ON msi.STORE_ID = mir.STORE_ID "
                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
                    + "ON mir.MATERIAL_ID = slc.MATERIAL_ID "
                    + "WHERE mir.ITEM_GROUP = 0 "
                    + "AND slc.MATERIAL_ID  IS NULL ORDER BY msi.STORE_NAME ASC", MmsStoreInformation.class);
            mmsStoreInformationList = (List<MmsStoreInformation>) query.getResultList();
            return mmsStoreInformationList;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStoreInformation> findStoreJoinedWithBinCard() {
        List<MmsStoreInformation> mmsStoreList;
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT msi.STORE_ID,msi.* FROM MMS_STORE_INFORMATION msi\n"
                    + "INNER JOIN MMS_BIN_CARD mbc\n"
                    + "on msi.STORE_ID=mbc.STORE_ID", MmsStoreInformation.class);
            mmsStoreList = (List<MmsStoreInformation>) query.getResultList();
            return mmsStoreList;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStoreInformation> getUnBalancedMartialByStoreName() {
        List<MmsStoreInformation> mmsStoreInformationList;
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT mir.MAT_CODE, mir.MAT_NAME,mir.STORE_ID,mibs.BUDGET_YEAR,mibs.OLD_VALUE, mibs.COUNTING_VALUE,mibs.DIFFRENCE \n"
                    + "                    FROM MMS_INVENTORY_BALANCE_SHEET mibs \n"
                    + "                     INNER JOIN MMS_ITEM_REGISTRATION mir \n"
                    + "                     ON mir.MATERIAL_ID=mibs.MATERIAL_ID \n"
                    + "                     INNER JOIN MMS_STORE_INFORMATION msi \n"
                    + "                     ON msi.STORE_ID=mir.STORE_ID \n"
                    + "                     INNER JOIN FMS_STOCK_LEDGER_CARD slc \n"
                    + "                     ON slc.MATERIAL_ID = mir.MATERIAL_ID  WHERE mibs.STATUS=0", MmsStoreInformation.class);
            mmsStoreInformationList = (List<MmsStoreInformation>) query.getResultList();
            return mmsStoreInformationList;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getMmsStoreInfColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_STORE_INFORMATION')\n"
                + "   and COLUMN_NAME NOT IN ('STORE_ID','WAREHOUSE_ID','PROCESSED_BY')\n"
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

    public List<MmsStoreInformation> getStoreListsByParameter(ColumnNameResolver columnNameResolver, MmsStoreInformation storeInfoEntity, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsStoreInformation> colValueLists = new ArrayList<>();
        if (storeInfoEntity.getColumnName() != null && !storeInfoEntity.getColumnName().equals("")
                && storeInfoEntity.getColumnValue() != null && !storeInfoEntity.getColumnValue().equals("")) {
            System.out.println("when if");

            Query query = em.createNativeQuery("SELECT * FROM MMS_STORE_INFORMATION\n"
                    + "   WHERE " + storeInfoEntity.getColumnName().toLowerCase() + "='" + storeInfoEntity.getColumnValue() + "'"
                    + "and " + storeInfoEntity.getProcessedBy() + "='" + storeInfoEntity.getProcessedBy() + "'", MmsStoreInformation.class);
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
            Query query = em.createNamedQuery("MmsStoreInformation.findAllByPreparerId");
            query.setParameter("ProcessedBy", storeInfoEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }

    }
// </editor-fold> 
}
