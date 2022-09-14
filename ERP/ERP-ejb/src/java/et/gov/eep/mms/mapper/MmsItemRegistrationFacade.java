package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsSubCat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Minab
 */
@Stateless

public class MmsItemRegistrationFacade extends AbstractFacade<MmsItemRegistration> {

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
    public MmsItemRegistrationFacade() {
        super(MmsItemRegistration.class);
    }

    /**
     *
     * @param inspectedby
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
    public ArrayList<MmsItemRegistration> searchByInspectorName(MmsItemRegistration inspectedby) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatNameLike");
        query.setParameter("inspectedBy", inspectedby.getMatName() + '%');
        try {
            ArrayList<MmsItemRegistration> listofInspector = new ArrayList(query.getResultList());
            return listofInspector;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param item
     * @return
     */
    public ArrayList<MmsItemRegistration> searchByMaterialName(MmsItemRegistration item) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatNameLike");
        query.setParameter("matName", item.getMatName() + '%');

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());
            return ItemList;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param item
     * @return
     */
    public MmsItemRegistration getByMaterialName(MmsItemRegistration item) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatName", MmsItemRegistration.class);
        query.setParameter("matName", item.getMatName());

        try {
            MmsItemRegistration itemName = null;
            if (query.getResultList().size() > 0) {
                itemName = (MmsItemRegistration) query.getResultList().get(0);

            }
            return itemName;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsItemRegistration ItemName(String matName) {

        MmsItemRegistration itemRegistrationLst = null;
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatName", MmsItemRegistration.class);
        query.setParameter("matName", matName);
        try {
            if (query.getResultList().size() > 0) {
                itemRegistrationLst = (MmsItemRegistration) query.getResultList().get(0);
            }

            return itemRegistrationLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public MmsItemRegistration getByMaterialId(MmsItemRegistration item) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMaterialId", MmsItemRegistration.class);
        query.setParameter("materialId", item.getMaterialId());

        try {
            MmsItemRegistration itemInfo = (MmsItemRegistration) query.getResultList().get(0);
            return itemInfo;

        } catch (Exception ex) {
            return null;
        }
    }

    public MmsItemRegistration searchByMaterialCode(String matCode) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCode");
        query.setParameter("matCode", matCode);
        try {
            MmsItemRegistration matCodeInfo = (MmsItemRegistration) query.getResultList().get(0);
            return matCodeInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return itemNameLists
     */
    public List<MmsItemRegistration> getItemLists() {

        Query query = em.createNamedQuery("MmsItemRegistration.findAll", MmsItemRegistration.class);

        try {
            List<MmsItemRegistration> itemsLists = (List<MmsItemRegistration>) query.getResultList();

            return itemsLists;
        } catch (Exception ex) {

            return null;
        }
    }

    //Search by Inventory Id
    /**
     *
     * @param itemName
     * @return
     */
    public MmsItemRegistration getPapmsItemInformation(MmsItemRegistration itemName) {
        MmsItemRegistration importationInfo = null;
        String a = "Car";

        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCode", MmsItemRegistration.class);
        query.setParameter("matCode", itemName.getMatCode());

        importationInfo = (MmsItemRegistration) query.getSingleResult();

        if (query.getResultList().isEmpty()) {

        } else {
            importationInfo = (MmsItemRegistration) query.getResultList();
        }
        return importationInfo;
    }

    //for Sadik
    /**
     *
     * @param itemCode
     * @return
     */
    /**
     *
     * @param MaterialInformation
     * @return
     */
    public List<MmsItemRegistration> searchByMaterialCode(MmsItemRegistration MaterialInformation) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCodeLike", MmsItemRegistration.class);
        String code = MaterialInformation.getMatCode();
        query.setParameter("matCode", code + '%');
        try {
            ArrayList<MmsItemRegistration> ItemCodeList = new ArrayList(query.getResultList());
            return ItemCodeList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreAndMaterialCode(MmsItemRegistration MaterialInformation) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreAndMatCodeLike", MmsItemRegistration.class);
        String code = MaterialInformation.getMatCode();

        query.setParameter("matCode", code + '%');
        query.setParameter("storeId", MaterialInformation.getStoreId());
        try {
            ArrayList<MmsItemRegistration> ItemCodeList = new ArrayList(query.getResultList());
            return ItemCodeList;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param itemRegistrationEntity
     * @return
     */
    public ArrayList<MmsItemRegistration> searchItem(String item) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatNameLike");
        query.setParameter("matName", item + '%');
        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());
            return ItemList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> getByMatCodeJoinedWithBinCard(MmsItemRegistration itemRegistrationEntity) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCodeLike", MmsItemRegistration.class);
        query.setParameter("matCode", itemRegistrationEntity.getMatCode() + '%');

        try {
            ArrayList<MmsItemRegistration> ItemCodeList = new ArrayList(query.getResultList());
            return ItemCodeList;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsItemRegistration searchByMateialId(Integer materialId) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMaterialId");
        query.setParameter("materialId", materialId);
        try {
            MmsItemRegistration selectrequest = (MmsItemRegistration) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchAllByPreparerId(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findAllByPreparerId", MmsItemRegistration.class);

        query.setParameter("processedBy", papmsItemRegistration.getProcessedBy());

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsItemRegistration> searchMatInformationByPrefix(String items) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatNameOrMatcodePrefix", MmsItemRegistration.class);
        System.out.println("2 " + items);
        query.setParameter("matCode", items + '%');

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsItemRegistration> searchMatInformationByPrefixObject(MmsItemRegistration itemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCodeLike", MmsItemRegistration.class);
        query.setParameter("matCode", itemRegistration.getMatCode() + '%');
        try {
            ArrayList<MmsItemRegistration> matCVode = new ArrayList(query.getResultList());
            return matCVode;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> findMatCodeList() {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCode");

        try {
            ArrayList<MmsItemRegistration> matCode = new ArrayList(query.getResultList());
            return matCode;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getLastMatCodeByParameter1(MmsItemRegistration papmsItemRegistration, MmsCategory category, MmsSubCat subcat) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCodeMaximum", MmsItemRegistration.class);
        //query.setParameter("storeId", papmsItemRegistration.getStoreId().getStoreId());
        query.setParameter("catId", category.getCatId());
        query.setParameter("subcatId", subcat.getSubCatId());

        try {
            String importationInfo = query.getSingleResult().toString();
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> getByItemCode(String toString) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatCode", MmsItemRegistration.class);
        query.setParameter("matCode", toString);

        try {
            List<MmsItemRegistration> itemNameList = null;
            itemNameList = (List<MmsItemRegistration>) query.getResultList();

            return itemNameList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean checkforDuplication(MmsItemRegistration itemRegistrationEntity) {
        boolean duplicaton;
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreNameAndItemName", MmsItemRegistration.class);
        query.setParameter("storeId", itemRegistrationEntity.getStoreId());
        query.setParameter("matName", itemRegistrationEntity.getMatName());
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

    public List<MmsItemRegistration> searchItemInfoByStore(MmsStoreInformation storeEntity) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStorId", MmsItemRegistration.class);
        query.setParameter("storeId", storeEntity);

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchItemInfoByStoreAndPeparerId(MmsStoreInformation storeEntity, MmsItemRegistration itemRegistration) {
        System.out.println("===========storeid===" + storeEntity.getStoreId());
        System.out.println("===========Processed===" + itemRegistration.getProcessedBy());
        Query query = em.createNamedQuery("MmsItemRegistration.findByStorIdAndPreparerId", MmsItemRegistration.class);
        query.setParameter("storeId", storeEntity);
        query.setParameter("processedBy", itemRegistration.getProcessedBy());

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());
            System.out.println("=========itemlist===" + ItemList);
            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreAndItemCode(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreAndMatCodeLike", MmsItemRegistration.class);
        query.setParameter("storeId", papmsItemRegistration.getStoreId());
        query.setParameter("matCode", papmsItemRegistration.getMatCode() + '%');

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreAndItemCodeAndPeparerId(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreAndMatCodeLikeAndPreparerId", MmsItemRegistration.class);
        query.setParameter("matCode", papmsItemRegistration.getMatCode() + '%');

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreAndItemName(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreNameAndItemName", MmsItemRegistration.class);
        query.setParameter("storeId", papmsItemRegistration.getStoreId());
        query.setParameter("matName", papmsItemRegistration.getMatName() + '%');
        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreAndItemNameAndPreparerId(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreNameAndItemNamePreparerId", MmsItemRegistration.class);
        query.setParameter("matName", papmsItemRegistration.getMatName() + '%');
        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByAllParameters(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByAllParameters", MmsItemRegistration.class);
        query.setParameter("matName", papmsItemRegistration.getMatName() + '%');
        query.setParameter("matCode", papmsItemRegistration.getMatCode() + '%');

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByAllParametersAndPreparerId(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByAllParametersAndPreparerId", MmsItemRegistration.class);
        query.setParameter("matName", papmsItemRegistration.getMatName() + '%');
        query.setParameter("matCode", papmsItemRegistration.getMatCode() + '%');

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public MmsItemRegistration searchItemInfoByStoreId(MmsItemRegistration itemRegEntity) {
        Query query1 = em.createNamedQuery("MmsItemRegistration.findByStorId", MmsItemRegistration.class);
        query1.setParameter("storeId", itemRegEntity.getStoreId());

        try {

            MmsItemRegistration itemInformations = (MmsItemRegistration) query1.getSingleResult();
            return itemInformations;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchItemByStoreId(MmsItemRegistration itemRegEntity) {
        clearCach();
        Query query = em.createNamedQuery("MmsItemRegistration.findByStorId", MmsItemRegistration.class);
        query.setParameter("storeId", itemRegEntity.getStoreId());

        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreAndMaterialName(MmsItemRegistration MaterialInformation) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreAndMatNameLike", MmsItemRegistration.class);
        query.setParameter("matName", MaterialInformation.getMatName().toUpperCase() + '%');
        query.setParameter("storeId", 1);
        query.setParameter("matStatus", 1);
        try {
            ArrayList<MmsItemRegistration> ItemCodeList = new ArrayList(query.getResultList());
            return ItemCodeList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchMaterialInfoByStoreAndMatNameAndItemGroup(MmsItemRegistration papmsMaterialInformation) {
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreItemCategoryAndMatNameLike", MmsItemRegistration.class);
        query.setParameter("matName", papmsMaterialInformation.getMatName().toUpperCase() + '%');
        query.setParameter("storeId", papmsMaterialInformation.getStoreId());
        query.setParameter("matStatus", 1);
        query.setParameter("itemGroup", papmsMaterialInformation.getItemGroup());
        try {
            ArrayList<MmsItemRegistration> ItemCodeList = new ArrayList(query.getResultList());
            return ItemCodeList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreAndGlId(MmsItemRegistration papmsItemRegistration) {
        System.out.println("============store Id===" + papmsItemRegistration.getStoreId().getStoreId());
        Query query = em.createNamedQuery("MmsItemRegistration.findByStoreAndGlId", MmsItemRegistration.class);
        query.setParameter("storeId", papmsItemRegistration.getStoreId());
        try {
            ArrayList<MmsItemRegistration> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NativeQuery">
    public MmsItemRegistration getMmsItemInformationByCode(MmsItemRegistration itemRegistrationEntity) {
        MmsItemRegistration itemInformations = null;
        String matcode = itemRegistrationEntity.getMatCode();
        Query query2 = em.createNativeQuery("SELECT * FROM mms_item_registration WHERE mat_code = '" + matcode + "'", MmsItemRegistration.class);

        try {

            itemInformations = (MmsItemRegistration) query2.getSingleResult();
            return itemInformations;
        } catch (Exception ex) {
            System.err.println("===============exeptionion===============");
            return itemInformations;
        }
    }
    //TodayEdited

    /**
     *
     * @param item
     * @return
     */
    public MmsItemRegistration SearchMatIdByMatCode(MmsItemRegistration itemCode) {

        MmsItemRegistration itemInformations = null;
        //new MmsItemRegistration() ;
        String matcode = itemCode.getMatCode();

        Query query2 = em.createNativeQuery("SELECT * FROM mms_item_registration WHERE mat_code = '" + matcode + "'", MmsItemRegistration.class);
        //Query query=em.createNamedQuery("MmsItemRegistration.findByMatCode",MmsItemRegistration.class);
//        ("============item code @ Facade ======="+itemCode.getMatCode());
        // query.setParameter("matCode",itemCode.getMatCode());

        try {

            itemInformations = (MmsItemRegistration) query2.getSingleResult();

            return itemInformations;
        } catch (Exception ex) {
            System.err.println("===============expetion===============");
            return itemInformations;
        }
    }

    public List<MmsItemRegistration> searchSivMaterialList(String siv_No) {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT mir.MAT_CODE, "
                    + "  mir.MAT_NAME, "
                    + "  mir.MATERIAL_ID, "
                    + "  slc.REF_NO "
                    + "FROM MMS_SIV ms "
                    + "INNER JOIN MMS_SIV_DETAIL msd "
                    + "ON ms.SIV_ID = msd.SIV_ID "
                    + "INNER JOIN MMS_STOREREQ_DETAIL msrdl "
                    + "ON msrdl.ITEM_ID = msd.ITEM_ID  "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mir.MATERIAL_ID = msrdl.ITEM_ID "
                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
                    + "ON msd.ITEM_ID = slc.MATERIAL_ID "
                    + "WHERE mir.ITEM_GROUP = 0 "
                    + "AND  ms.SIV_NO = '" + siv_No + "'"
                    + "AND slc.REF_NO IS NULL ", MmsItemRegistration.class);
            return (List<MmsItemRegistration>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchGrnMaterialList(String grn_No) {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT mir.MAT_CODE, "
                    + "  mir.MAT_NAME, "
                    + "  mir.MATERIAL_ID, "
                    + "  slc.REF_NO "
                    + "FROM MMS_GRN mg "
                    + "INNER JOIN MMS_GRN_DETAIL mgd "
                    + "ON mg.GRN_ID=mgd.GRN_ID "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mir.MAT_CODE = mgd.MATERIAL_CODE "
                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
                    + "ON mgd.GRN_ID = slc.GRN_DETAIL_ID "
                    + "WHERE mir.ITEM_GROUP  = 0 AND slc.GRN_DETAIL_ID IS NULL "
                    + "AND mg.GRN_NO = '" + grn_No + "'  ", MmsItemRegistration.class);

            return (List<MmsItemRegistration>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchByStoreId(int storeId) {
        try {
            Query query1 = em.createNativeQuery("SELECT mir.*,slc.MATERIAL_CODE,mbc.unit_price "
                    + "FROM MMS_BIN_CARD mbc "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mbc.MATERIAL_ID=mir.MATERIAL_ID "
                    + "INNER JOIN MMS_STORE_INFORMATION msi "
                    + "ON msi.STORE_ID=mir.STORE_ID "
                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
                    + "ON mir.MAT_CODE = slc.MATERIAL_CODE "
                    + "WHERE mir.STORE_ID = '" + storeId + "' "
                    //                    + "AND mir.ITEM_FLAG = 1 "
                    //                    + "AND mir.ITEM_GROUP = 1 "
                    + "AND slc.MATERIAL_CODE IS NULL", MmsItemRegistration.class);
            return (List<MmsItemRegistration>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
//New item; item flag = 1

    public List<MmsItemRegistration> searchByStoreId1(int storeId, int item_flag) {
        try {
            Query query1 = em.createNativeQuery("SELECT mir.*,slc.MATERIAL_CODE,mbc.unit_price "
                    + "FROM MMS_BIN_CARD mbc "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mbc.MATERIAL_ID=mir.MATERIAL_ID "
                    + "INNER JOIN MMS_STORE_INFORMATION msi "
                    + "ON msi.STORE_ID=mir.STORE_ID "
                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
                    + "ON mir.MAT_CODE = slc.MATERIAL_CODE "
                    + "WHERE mir.STORE_ID = '" + storeId + "' "
                    + "AND mir.ITEM_FLAG = '" + item_flag + "' "
                    + "AND mir.ITEM_GROUP = 0 "
                    + "AND slc.MATERIAL_CODE IS NULL", MmsItemRegistration.class);
            return (List<MmsItemRegistration>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsItemRegistration> searchIsivListToSave(String isiv_no) {
        List<MmsItemRegistration> luServiceTypes;
        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT mir.MAT_CODE , "
                    + " mir.MAT_NAME, "
                    + " mis.ISIV_NO, "
                    + " mir.MATERIAL_ID,"
                    + " misi.STORE_ID, "
                    + " TO_NUMBER(FMS_CAL_SIV_WEIGHTED_AVERAGE (mir.MAT_CODE)  )AS currentPrice, "
                    + " msrdl.APPROVED_QUANTITY, "
                    + " TO_NUMBER(FMS_CALL_STORE(mir.MAT_CODE)) AS prv_Qantity, "
                    + " TO_NUMBER(FMS_CALL_STORE(mir.MAT_CODE) - msrdl.APPROVED_QUANTITY) AS Cur_Total_Qantity, "
                    + " TO_NUMBER(FMS_CAL_SIV_WEIGHTED_AVERAGE (mir.MAT_CODE) * msrdl.APPROVED_QUANTITY) AS amountInBirr, "
                    + " misd.ITEM_ID as sivDetailId "
                    + "FROM MMS_ITEM_REGISTRATION mir "
                    + "INNER JOIN  MMS_STOREREQ_DETAIL msrdl "
                    + "ON msrdl.ITEM_ID = mir.MATERIAL_ID "
                    + "INNER JOIN MMS_ISIV_DETAIL misd "
                    + "ON misd.ITEM_ID = msrdl.ITEM_ID "
                    + "INNER JOIN MMS_ISIV mis "
                    + "ON mis.TRANSFER_ID=misd.TRANSFER_ID "
                    + "INNER JOIN MMS_STORE_INFORMATION misi "
                    + "ON mir.STORE_ID = misi.STORE_ID "
                    + "WHERE mir.ITEM_GROUP = 0 "
                    + "AND mis.ISIV_NO = '" + isiv_no + "' "
                    + "GROUP BY mir.MAT_CODE, "
                    + " mir.MAT_NAME, "
                    + " mis.ISIV_NO, "
                    + " mis.TRANSFER_ID, "
                    + " misi.STORE_NO, "
                    + " misi.STORE_ID, "
                    + " mir.MATERIAL_ID, "
                    + " msrdl.approved_quantity, "
                    + " misd.ITEM_ID ", "OrderResultsMmsSivDetail");
            luServiceTypes = (List<MmsItemRegistration>) query1.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }

    }

    public List<MmsItemRegistration> searchRmgListToSave(String rmg_no) {
        List<MmsItemRegistration> luServiceTypes;
        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT mir.MAT_CODE, "
                    + "mir.MAT_NAME, mir.MATERIAL_ID, "
                    + "mrmg.RMG_NO, "
                    + "mir.MATERIAL_ID, "
                    + "msi.STORE_NO, "
                    + "msi.STORE_ID, "
                    + "TO_NUMBER(FMS_CAL_SIV_WEIGHTED_AVERAGE (mir.MAT_CODE) )AS currentPrice , "
                    + "mrmgd.QUANTITY AS APPROVED_QNTY, "
                    + "TO_NUMBER(FMS_CALL_STORE(mir.MAT_CODE)) AS prv_Qantity, "
                    + "TO_NUMBER(FMS_CALL_STORE (mir.MAT_CODE)  + mrmgd.QUANTITY) AS Cur_Total_Qantity, "
                    + "TO_NUMBER(FMS_CAL_SIV_WEIGHTED_AVERAGE (mir.MAT_CODE) * mrmgd.QUANTITY) AS amountInBirr, "
                    + "mrmgd.RMG_DET_ID AS rmgDetailId "
                    + "FROM MMS_ITEM_REGISTRATION mir "
                    + "INNER JOIN MMS_RMG_DETAIL mrmgd "
                    + "ON mrmgd.ITEM_ID = mir.MATERIAL_ID "
                    + "INNER JOIN MMS_RMG mrmg "
                    + "ON mrmg.RMG_ID = mrmgd.RMG_ID "
                    + "INNER JOIN MMS_STORE_INFORMATION msi "
                    + "ON mir.STORE_ID = msi.STORE_ID "
                    + "WHERE mir.ITEM_GROUP = 0 "
                    + "AND mrmg.RMG_NO = '" + rmg_no + "' "
                    + "GROUP BY mir.MAT_CODE, "
                    + "mir.MAT_NAME, "
                    + "mrmg.RMG_NO, "
                    + "msi.STORE_NO, "
                    + "msi.STORE_ID, "
                    + "mir.MATERIAL_ID, "
                    + "mrmgd.QUANTITY, "
                    + "mrmgd.RMG_DET_ID ", "OrderResultsMmsSivDetail");
            luServiceTypes = (List<MmsItemRegistration>) query1.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }
    }

    public String getLastMatCodeByParameter(MmsItemRegistration papmsItemRegistration, MmsCategory category, MmsSubCat subcat) {
        System.out.println("======Cat @ facade======" + category.getCatId());
        System.out.println("======subCat @ facade======" + subcat.getSubCatId());
        System.out.println("========store @ facade=====" + papmsItemRegistration.getStoreId().getStoreId());

        System.out.println("======Cat @ facade 2 ======" + papmsItemRegistration.getMatCategory().getCatId());
        System.out.println("======subCat @ facade 2 ======" + papmsItemRegistration.getMatSubcategory().getSubCatId());
        System.out.println("========store @ facade 2 =====" + papmsItemRegistration.getStoreId().getStoreId());
        try {
            Query query1 = em.createNativeQuery("SELECT MAX(MAT_CODE) "
                    + "FROM MMS_ITEM_REGISTRATION "
                    + "WHERE MAT_CATEGORY='" + category + "' "
                    + "AND MAT_SUBCATEGORY='" + subcat + "' "
                    + "AND STORE_ID='" + papmsItemRegistration.getStoreId() + "' "
            );
            String itemInformations = query1.getSingleResult().toString();

            return itemInformations;
        } catch (Exception ex) {
            System.err.println("===============exeptionion===============");
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> findAllJoinedWithBinCard() {
        try {
            Query query1 = em.createNativeQuery("SELECT mir.*,mbr.*  "
                    + "FROM MMS_ITEM_REGISTRATION mir          "
                    + "INNER JOIN MMS_BIN_CARD mbr   "
                    + "ON mbr.MATERIAL_ID =mir.MATERIAL_ID "
            );
            return (List<MmsItemRegistration>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsItemRegistration> getItemInfo(MmsItemRegistration itemRegistrationEntity) {

        try {
            Query query1 = em.createNativeQuery("SELECT itr.*,"
                    + "mbr.UNIT_PRICE, "
                    + "grnm.GRN_NO, "
                    + "grnm.APPROVED_DATE, "
                    + "MMS_STOREREQ.SR_NO, "
                    + "MMS_STOREREQ.REQUESTED_BY "
                    + "FROM mms_item_registration itr "
                    + "INNER JOIN mms_bin_card mbr "
                    + "ON itr.MATERIAL_ID = mbr.MATERIAL_ID "
                    + "INNER JOIN mms_grn_detail mgrn "
                    + "ON itr.MATERIAL_ID = mgrn.ITEM_ID "
                    + "INNER JOIN mms_grn grnm "
                    + "ON grnm.GRN_ID = mgrn.GRN_ID "
                    + "INNER JOIN MMS_STOREREQ_DETAIL SRD "
                    + "ON SRD.ITEM_ID = itr.MATERIAL_ID "
                    + "INNER JOIN MMS_STOREREQ "
                    + "ON MMS_STOREREQ.STORE_REQ_ID = SRD.STORE_REQ_ID where itr.material_id='" + itemRegistrationEntity.getMaterialId() + "' ", MmsItemRegistration.class);
            System.out.println(".......size New @ facade......" + query1.getResultList().size());
            ArrayList<MmsItemRegistration> listOf = new ArrayList<>(query1.getResultList());
            return listOf;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }

    }

    public MmsItemRegistration SearchInfoByMatcodeJoined(MmsItemRegistration itemRegEntity, MmsGrn grnEntity) {
        try {
            Query query1 = em.createNativeQuery("select itr.*,"
                    + "gdl.received_quantity "
                    + "from mms_item_registration itr "
                    + "INNER JOIN mms_grn_detail gdl "
                    + "ON itr.material_id= gdl.item_id "
                    + "INNER join mms_grn grn "
                    + "ON gdl.grn_id = grn.grn_id "
                    + "WHERE itr.mat_code='" + itemRegEntity.getMatCode() + "' "
                    + "AND GRN_NO='" + grnEntity.getGrnNo() + "' ", MmsItemRegistration.class);
            System.out.println(".......size @ facade......" + query1.getResultList().size());
            MmsItemRegistration itemInformations = (MmsItemRegistration) query1.getSingleResult();
            return itemInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsItemRegistration SearchRmgInfoByMatcodeJoined(MmsItemRegistration itemRegEntity, MmsRmg rmgEntity) {
        try {
            Query query1 = em.createNativeQuery("select itr.*,"
                    + "gdl.quantity "
                    + "from mms_item_registration itr "
                    + "INNER JOIN mms_rmg_detail gdl "
                    + "ON itr.material_id= gdl.item_id "
                    + "INNER join mms_rmg rmg "
                    + "ON gdl.rmg_id = rmg.rmg_id "
                    + "WHERE itr.mat_code='" + itemRegEntity.getMatCode() + "' "
                    + "AND RMG_NO='" + rmgEntity.getRmgNo() + "' ", MmsItemRegistration.class);
            System.out.println(".......size @ facade......" + query1.getResultList().size());
            MmsItemRegistration itemInformations = (MmsItemRegistration) query1.getSingleResult();
            return itemInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsItemRegistration SearchIsivRecievedInfoByMatcodeJoined(MmsItemRegistration itemRegEntity, MmsIsivReceived isivEntity) {
        try {
            Query query1 = em.createNativeQuery("select itr.*,"
                    + "gdl.quantity "
                    + "from mms_item_registration itr "
                    + "INNER JOIN mms_isiv_received_dtl gdl "
                    + "ON itr.material_id= gdl.material_id "
                    + "INNER join mms_isiv_received isiv "
                    + "ON gdl.receiving_id = isiv.recieving_id "
                    + "WHERE itr.mat_code='" + itemRegEntity.getMatCode() + "' "
                    + "AND isiv.receiving_transfer_no='" + isivEntity.getReceivingTransferNo() + "' ", MmsItemRegistration.class);
            System.out.println(".......size @ facade......" + query1.getResultList().size());
            MmsItemRegistration itemInformations = (MmsItemRegistration) query1.getSingleResult();
            return itemInformations;
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsItemRegistration> getItemListBySubCatId(MmsSubCat mmsSubCat) {
        Query query = em.createNativeQuery("SELECT DISTINCT MMS_SUB_CAT.SUB_CAT_ID,\n"
                + "  MMS_SUB_CAT.SUB_CAT_NAME,\n"
                + "  MMS_CATEGORY.CAT_ID,\n"
                + "  MMS_CATEGORY.CAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME\n"
                + "FROM MMS_SUB_CAT\n"
                + "INNER JOIN MMS_CATEGORY\n"
                + "ON MMS_CATEGORY.CAT_ID = MMS_SUB_CAT.CAT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_SUB_CAT.SUB_CAT_ID = MMS_ITEM_REGISTRATION.MAT_SUBCATEGORY\n"
                + "AND MMS_CATEGORY.CAT_ID   = MMS_ITEM_REGISTRATION.MAT_CATEGORY\n"
                + "WHERE MMS_SUB_CAT.SUB_CAT_ID='" + mmsSubCat.getSubCatId() + "'", MmsItemRegistration.class);
        try {
            List<MmsItemRegistration> itemRegistrationList = new ArrayList<>(query.getResultList());
            return itemRegistrationList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> getItemNameListFrmEval(Date fromYear, Date toYear) {
        System.out.println("----itemName---from calendar --" + fromYear + " to end date " + toYear);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String firstDate = format.format(fromYear);
        String secondDate = format.format(toYear);

        Query query = em.createNativeQuery("SELECT DISTINCT MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "MMS_ITEM_REGISTRATION.MAT_CODE,\n"
                + "MMS_ITEM_REGISTRATION.MAT_NAME\n"
                + "FROM PRMS_FINANCIAL_EVAL_RESULT\n"
                + "INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "WHERE  PRMS_FINANCIAL_EVAL_RESULT.DATE_REG BETWEEN TO_DATE('" + firstDate + "', 'dd/mm/yyyy')\n"
                + "AND TO_DATE('" + secondDate + "', 'dd/mm/yyyy')", MmsItemRegistration.class);
        try {
            List<MmsItemRegistration> mmsItemRegistrationItemNameList = new ArrayList<>(query.getResultList());
            return mmsItemRegistrationItemNameList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> getItemInfo2(MmsItemRegistration itemRegistrationEntity) {
        System.out.println("......ID at Facade2....." + itemRegistrationEntity.getMaterialId());
        try {

            Query query1 = em.createNativeQuery("SELECT itr.*,"
                    + "mbr.UNIT_PRICE, "
                    + "grnm.GRN_NO, "
                    + "grnm.APPROVED_DATE, "
                    + "MMS_STOREREQ.SR_NO, "
                    + "MMS_STOREREQ.REQUESTED_BY, "
                    + "Siv.SIV_NO "
                    + "FROM mms_item_registration itr "
                    + "INNER JOIN mms_bin_card mbr "
                    + "ON itr.MATERIAL_ID = mbr.MATERIAL_ID "
                    + "INNER JOIN mms_grn_detail mgrn "
                    + "ON itr.MATERIAL_ID = mgrn.ITEM_ID "
                    + "INNER JOIN mms_grn grnm "
                    + "ON grnm.GRN_ID = mgrn.GRN_ID "
                    + "INNER JOIN MMS_STOREREQ_DETAIL SRD "
                    + "ON SRD.ITEM_ID = itr.MATERIAL_ID "
                    + "INNER JOIN MMS_STOREREQ "
                    + "ON MMS_STOREREQ.STORE_REQ_ID = SRD.STORE_REQ_ID "
                    + "INNER JOIN MMS_SIV_DETAIL SivDtl  "
                    + "ON itr.MATERIAL_ID = SivDtl.ITEM_ID "
                    + "INNER JOIN MMS_SIV Siv "
                    + "ON Siv.SIV_ID = SivDtl.SIV_ID "
                    + "INNER JOIN mms_fixedasset_regst_detail frd "
                    + "on frd.item_id = itr.material_id "
                    + "INNER JOIN mms_fixedasset_regstration far "
                    + "on frd.far_id = far.id "
                    + "where itr.material_id='" + itemRegistrationEntity.getMaterialId() + "' AND   far.select_opt = '3' ", MmsItemRegistration.class);
            System.out.println(".......size Old @ facade......" + query1.getResultList().size());
            ArrayList<MmsItemRegistration> listOf = new ArrayList<>(query1.getResultList());
            return listOf;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;

        }

    }

    public List<MmsItemRegistration> searchMaterialInfoByStoreAndMatNameAndItemGroupAndGLID(MmsItemRegistration papmsMaterialInformation) {
        try {
            Query query = em.createNativeQuery("SELECT ir.*,bc.STORE_ID,bc.CURRENT_QUANTITY\n"
                    + "FROM MMS_ITEM_REGISTRATION ir\n"
                    + "INNER JOIN MMS_BIN_CARD bc\n"
                    + "ON bc.MATERIAL_ID=ir.MATERIAL_ID\n"
                    + "WHERE ir.GL_ID   ='" + papmsMaterialInformation.getGeneralLedgerId().getGeneralLedgerId() + "'\n"
                    + "and bc.STORE_ID='" + papmsMaterialInformation.getStoreId().getStoreId() + "'", MmsItemRegistration.class);
            return (List<MmsItemRegistration>) query.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    public List<MmsItemRegistration> searchItemWithMatCatAndSubCatOnly(MmsItemRegistration itemRegistration) {
        Query query = em.createNativeQuery("SELECT * "
                + "FROM MMS_ITEM_REGISTRATION "
                + "WHERE MATERIAL_ID IN ( SELECT MAX(MATERIAL_ID) "
                + "FROM MMS_ITEM_REGISTRATION "
                + "WHERE MAT_CATEGORY = ? AND MAT_SUBCATEGORY = ? "
                + "GROUP BY MAT_CODE )", MmsItemRegistration.class);

        query.setParameter(1, itemRegistration.getMatCategory().getCatId());
        query.setParameter(2, itemRegistration.getMatSubcategory().getSubCatId());
        try {
            List<MmsItemRegistration> itemList = new ArrayList(query.getResultList());
            return itemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> findItemsNotRegisteredOnBincard(MmsStoreInformation store) {
        System.out.println("========store in facade====" + store.getStoreId());
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM MMS_ITEM_REGISTRATION ir "
                    + "WHERE ir.MATERIAL_ID NOT IN "
                    + "  (SELECT bc.MATERIAL_ID "
                    + "  FROM MMS_BIN_CARD bc "
                    + "  WHERE bc.STORE_ID   ='" + store.getStoreId() + "'\n"
                    + "  )",
                    MmsItemRegistration.class);
            return (List<MmsItemRegistration>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }
//</editor-fold> 

    public List<String> getMmsItemRColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_ITEM_REGISTRATION')\n"
                + "   and COLUMN_NAME NOT IN ('STORE_ID','MATERIAL_ID','GL_ID','DEPRECIATION_ID','ID','PREVIOUS_ITEM')\n"
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

    public List<MmsItemRegistration> getIRListsByParameter(MmsItemRegistration papmsItemRegistration) {
        List<MmsItemRegistration> colValueLists = new ArrayList<>();
        if (papmsItemRegistration.getColumnName() != null && !papmsItemRegistration.getColumnName().equals("")
                && papmsItemRegistration.getColumnValue() != null && !papmsItemRegistration.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM MMS_ITEM_REGISTRATION\n"
                    + "   WHERE " + papmsItemRegistration.getColumnName().toLowerCase() + "='" + papmsItemRegistration.getColumnValue() + "'"
                    + "and " + papmsItemRegistration.getProcessedBy() + "='" + papmsItemRegistration.getProcessedBy() + "'", MmsItemRegistration.class);
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
            Query query = em.createNamedQuery("MmsItemRegistration.findByProcessedBy");
            query.setParameter("processedBy", papmsItemRegistration.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }

    public List<MmsItemRegistration> getItemListsByParameter(ColumnNameResolver columnNameResolver, MmsItemRegistration papmsItemRegistration, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsItemRegistration> colValueLists = new ArrayList<>();
        if (papmsItemRegistration.getColumnName() != null && !papmsItemRegistration.getColumnName().equals("")
                && papmsItemRegistration.getColumnValue() != null && !papmsItemRegistration.getColumnValue().equals("")) {
            System.out.println("when if"); 

            Query query = em.createNativeQuery("SELECT * FROM MMS_ITEM_REGISTRATION\n"                    
                     + "   WHERE " + papmsItemRegistration.getColumnName().toLowerCase() + "='" + papmsItemRegistration.getColumnValue() + "'"
                    + "and " + papmsItemRegistration.getProcessedBy() + "='" + papmsItemRegistration.getProcessedBy() + "'", MmsItemRegistration.class);
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
            Query query = em.createNamedQuery("MmsItemRegistration.findByProcessedBy");
            query.setParameter("processedBy", papmsItemRegistration.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }
     public int ConutBYItemType(String get) {
        System.out.println("");
        Query query = em.createNativeQuery("select * from MMS_ITEM_REGISTRATION where ITEM_TYPE='" + get + "'");
        return query.getResultList().size();
    }

    public List<String> findAllItemType() {
        Query query = em.createNativeQuery("select DISTINCT ITEM_TYPE from MMS_ITEM_REGISTRATION where ITEM_TYPE is not null");
        return query.getResultList();
    }

}
