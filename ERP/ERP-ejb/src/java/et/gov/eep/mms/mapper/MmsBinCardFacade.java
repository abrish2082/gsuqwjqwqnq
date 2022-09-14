/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsBinCardFacade extends AbstractFacade<MmsBinCard> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsBinCardFacade() {
        super(MmsBinCard.class);
    }
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">

    public List<MmsBinCard> searchBinCardInfoByStore(MmsStoreInformation storeEntity) {
        Query query = em.createNamedQuery("MmsBinCard.findByStorId", MmsBinCard.class);
        query.setParameter("storeId", storeEntity.getStoreId());

        try {
            ArrayList<MmsBinCard> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public MmsBinCard SearchInitalQuantityByMatId(MmsItemRegistration papmsItemRegistration) {
        Query query = em.createNamedQuery("MmsBinCard.findByItemId", MmsBinCard.class);
        query.setParameter("materialId", papmsItemRegistration.getMaterialId());
        try {

            MmsBinCard itemInformations = (MmsBinCard) query.getSingleResult();
            return itemInformations;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsBinCard> getitemCode(MmsBinCard binCardEntity) {

        Query q = em.createNamedQuery("MmsBinCard.findByCardNo");
        q.setParameter("sivId", binCardEntity.getCardNo());
        System.out.println("siv id " + binCardEntity.getCardNo());
        try {
            System.out.println("1");
            ArrayList<MmsBinCard> code = new ArrayList(q.getResultList());
//            if (q.getResultList().size() > 0) {
//                System.out.println("2");
//                code = (MmsBinCard) (q.getResultList().get(0));
//                System.out.println("sr no====" + code.getItemCode());
//            }
            return (List<MmsBinCard>) code;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MmsBinCard getItemInfoByItemId(MmsItemRegistration itemRegEntity) {
        clearCach();
        Query q = em.createNamedQuery("MmsBinCard.findByItemId");
        q.setParameter("materialId", itemRegEntity.getMaterialId());
        System.out.println("selected id=====" + itemRegEntity.getMaterialId());
        try {
            MmsBinCard itemInfo = new MmsBinCard();
            if (q.getResultList().size() > 0) {
                itemInfo = (MmsBinCard) (q.getResultList().get(0));
                System.out.println("item name==" + itemInfo.getMaterialId().getMatName());
                System.out.println("item name==" + itemInfo.getMaterialId().getUnitPrice());
                System.out.println("item name==" + itemInfo.getMaterialId().getUnitMeasure1());
            }
            return itemInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public MmsBinCard getItemInfoByStoreIdAndItemId(MmsItemRegistration itemRegistration, MmsStoreInformation storeInformation) {
        clearCach();
        Query query = em.createNamedQuery("MmsBinCard.findByItemIdAndStoreId");

        query.setParameter("materialId", itemRegistration.getMaterialId());
        query.setParameter("storeId", storeInformation.getStoreId());
        try {
            MmsBinCard binCard = new MmsBinCard();
            if (query.getResultList().size() > 0) {
                binCard = (MmsBinCard) (query.getResultList().get(0));
                System.out.println("item name==" + binCard.getMaterialId().getMatName());
                System.out.println("Store==" + storeInformation.getStoreId());

            }
            return binCard;
//        try {
//            System.out.println("=============facade1==" + itemRegistration.getMaterialId());
//            System.out.println("=============facade2==" + storeInformation.getStoreId());
//            MmsBinCard binCard = (MmsBinCard) query.getResultList().get(0);
//            System.out.println("=============binCard==" + binCard.getCardNo());
//            return binCard;

        } catch (Exception ex) {
            return null;
        }
    }

    public MmsBinCard findItemByMatId(int matId) {
        Query query = em.createNamedQuery("MmsBinCard.findByItemId", MmsBinCard.class);
        query.setParameter("materialId", matId);
        try {
            MmsBinCard itemInformations = (MmsBinCard) query.getSingleResult();
            return itemInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsBinCard findbyitemcode(MmsBinCard bincardEntity) {
        try {
            Query query = em.createNamedQuery("MmsBinCard.findByItemCode");
            query.setParameter("itemCode", bincardEntity.getItemCode());
            System.out.println("============item code facade===" + bincardEntity.getItemCode());
            MmsBinCard itemInformations = (MmsBinCard) query.getResultList().get(0);
            return itemInformations;
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsBinCard> getByItemNameAndStoreId(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard) {
        Integer storeid = binCard.getStoreId().getStoreId();
        String name = itemRegistrationEntity.getMatName();
        try {
            Query query1 = em.createNativeQuery("SELECT mbr.*  "
                    + "FROM MMS_BIN_CARD mbr          "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mbr.MATERIAL_ID =mir.MATERIAL_ID "
                    + "WHERE mbr.STORE_ID ='" + storeid + "' "
                    + "AND mir.MAT_NAME Like '" + name + "%'",
                    MmsBinCard.class);
            return (List<MmsBinCard>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsBinCard> getByAllParameters(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard) {
        try {
            Query query1 = em.createNativeQuery("SELECT mbr.*  "
                    + "FROM MMS_BIN_CARD mbr          "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mbr.MATERIAL_ID =mir.MATERIAL_ID "
                    + "WHERE mbr.STORE_ID ='" + binCard.getStoreId().getStoreId() + "' "
                    + "AND mir.MAT_NAME Like '" + itemRegistrationEntity.getMatName() + "%'"
                    + "AND mir.MAT_CODE Like '" + itemRegistrationEntity.getMatCode() + "%'",
                    MmsBinCard.class);
            return (List<MmsBinCard>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsBinCard> getByItemCodeAndStoreId(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard) {
        Integer storeid = binCard.getStoreId().getStoreId();
        String code = itemRegistrationEntity.getMatCode();
        try {
            Query query1 = em.createNativeQuery("SELECT mbr.*  "
                    + "FROM MMS_BIN_CARD mbr          "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mbr.MATERIAL_ID =mir.MATERIAL_ID "
                    + "WHERE mbr.STORE_ID ='" + storeid + "' "
                    + "AND mir.MAT_CODE Like '" + code + "%'",
                    MmsBinCard.class);
            return (List<MmsBinCard>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsBinCard> getItemCodeByStoreAndItemGroup(MmsItemRegistration itemRegistrationEntity) {
        try {
            Query query1 = em.createNativeQuery("SELECT mbr.*  "
                    + "FROM MMS_BIN_CARD mbr          "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mbr.MATERIAL_ID =mir.MATERIAL_ID "
                    + "WHERE mir.STORE_ID ='" + itemRegistrationEntity.getStoreId().getStoreId() + "' "
                    + "AND mir.ITEM_GROUP = '" + itemRegistrationEntity.getItemGroup() + "' ",
                    MmsBinCard.class);
            return (List<MmsBinCard>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="StoredProcedureQuery">
    public int deductFromBinCard(int storeId, String srNo) {
        System.out.println("========before try=========");
        try {
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("MMS_EDIT_BINCARD_HELPER");
            System.out.println("==========after ================");
            storedProcedure.registerStoredProcedureParameter("STORE_ID", Number.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("SR_NO", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("oRes", String.class, ParameterMode.OUT);
            storedProcedure.setParameter("STORE_ID", storeId);
            storedProcedure.setParameter("SR_NO", srNo);
            System.out.println("==========beforeExecute@facade========" + srNo);
            System.out.println("==========beforeExecute@facade========" + storeId);
            storedProcedure.execute();
            Integer status = Integer.parseInt(storedProcedure.getOutputParameterValue("oRes").toString());
            System.out.println("==========status@facade========" + status);
            if (status == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public int incrementBinCardQuantityForGrn(String GrnNo) {
        try {
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("MMS_UPDATE_BINCARD_GRN");

            storedProcedure.registerStoredProcedureParameter("GRN_NUM", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("oRes", String.class, ParameterMode.OUT);

            storedProcedure.setParameter("GRN_NUM", GrnNo);

            storedProcedure.execute();
            Integer status = Integer.parseInt(storedProcedure.getOutputParameterValue("oRes").toString());

            if (status == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public int incrementBinCardQuantityForISIV(String ISIVNo) {
        try {
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("MMS_UPDATE_BINCARD_ISIV");

            storedProcedure.registerStoredProcedureParameter("ISIV_I_NUM", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("oRes", String.class, ParameterMode.OUT);

            storedProcedure.setParameter("ISIV_I_NUM", ISIVNo);

            storedProcedure.execute();
            Integer status = Integer.parseInt(storedProcedure.getOutputParameterValue("oRes").toString());

            if (status == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }
//</editor-fold> 

    public List<MmsBinCard> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, MmsBinCard mmsBinCard, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsBinCard> colValueLists = new ArrayList<>();
        if (mmsBinCard.getColumnName() != null && !mmsBinCard.getColumnName().equals("")
                && mmsBinCard.getColumnValue() != null && !mmsBinCard.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_BIN_CARD\n"
                   + "   WHERE " + mmsBinCard.getColumnName().toLowerCase() + "='" + mmsBinCard.getColumnValue() + "'" , MmsBinCard.class);
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
            Query query = em.createNamedQuery("MmsBinCard.findAll");
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }

    public List<String> getMmsBinColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_BIN_CARD')\n"
                + "   and COLUMN_NAME NOT IN ('LOCATION_ID','MATERIAL_ID','STORE_ID','CARD_NO')\n"
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
}
