package et.gov.eep.fcms.mapper.stock;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;

/**
 *
 * @author muller
 */
@Stateless
public class FmsStockLedgerCardFacade extends AbstractFacade<FmsStockLedgerCard> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsStockLedgerCardFacade() {
        super(FmsStockLedgerCard.class);
    }

    /*named query to select materialCode list from FmsStockLedgerCard table by material code 
     passing parameter of fmsStockLedgerCard
     returen material code list*/
    public List<FmsStockLedgerCard> getMatCode(FmsStockLedgerCard fmsStockLedgerCard) {
        Query query = em.createNamedQuery("FmsStockLedgerCard.findByMaterialCode");
        query.setParameter("materialCode", fmsStockLedgerCard.getMaterialCode());

        try {
            List<FmsStockLedgerCard> matCode = (List<FmsStockLedgerCard>) query.getResultList();
            return matCode;

        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select materialCode list from FmsStockLedgerCard table by material code 
     passing parameter of fmsStockLedgerCard
     returen material code list*/
    public List<FmsStockLedgerCard> findByMatCode(FmsStockLedgerCard fmsStockLedgerCard) {
        Query query = em.createNamedQuery("FmsStockLedgerCard.findByMatCode");
        query.setParameter("materialCode", fmsStockLedgerCard.getMaterialCode().toUpperCase() + '%');

        try {
            List<FmsStockLedgerCard> matCode = (List<FmsStockLedgerCard>) query.getResultList();

            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return matCode;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select store name list from FmsStockLedgerCard table by store number 
     passing parameter of fmsStockLedgerCard
     returen store name list*/
    public List<FmsStockLedgerCard> findByStoreNo(FmsStockLedgerCard fmsStockLedgerCard) {
        Query query = em.createNamedQuery("FmsStockLedgerCard.findByStoreNo");
        query.setParameter("storeNo", fmsStockLedgerCard.getStoreNo() + '%');
        try {

            List<FmsStockLedgerCard> storeName = (List<FmsStockLedgerCard>) query.getResultList();

            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return storeName;
            }
            return null;

        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select materialCode list and store number from FmsStockLedgerCard table by material code 
     and store number first letters or numbers match
     passing parameter of fmsStockLedgerCard
     returen storeNameAndMatCode list*/
    public List<FmsStockLedgerCard> findByAll(FmsStockLedgerCard fmsStockLedgerCard) {
        Query query = em.createNamedQuery("FmsStockLedgerCard.findByAll");
        query.setParameter("materialCode", fmsStockLedgerCard.getMaterialCode().toUpperCase() + '%');
        query.setParameter("storeNo", fmsStockLedgerCard.getStoreNo() + '%');
        try {
            List<FmsStockLedgerCard> storeNameAndMatCode = (List<FmsStockLedgerCard>) query.getResultList();
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return storeNameAndMatCode;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /*native query to select MATERIAL_NAME, MATERIAL_CODE,GRN_NO,RECEIVED_QUANTITY,GRN_DETAIL_ID,STORE_NO and GL_ID list
     from FMS_STOCK_LEDGER_CARD,MMS_GRN_DETAIL, MMS_GRN_DETAIL,MMS_GRN,MMS_ITEM_REGISTRATION, MMS_STORE_INFORMATION
     FMS_TOTAL_STOCK_VALUE,FMS_STOCK_QUANTITY table
     by passing parameter of sivValue and store number
     using criteries
     returen selected list value*/
    public List<FmsStockLedgerCard> searchGRNListToSave(String sivValue, String storeNo) {
        List<FmsStockLedgerCard> luServiceTypes;
        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT fslc.MATERIAL_NAME,fslc.MATERIAL_CODE,\n"
                    + "mgd.ITEM_ID AS MATERIAL_ID,\n"
                    + "  mg.GRN_NO,round((mgd.UNIT_PRICE),4) As U_PRICE,  mgd.RECEIVED_QUANTITY AS APPROVED_QNTY, TO_NUMBER(SQ.QUANTITY) AS prv_Qantity, \n"
                    + "  TO_NUMBER(SQ.QUANTITY + mgd.RECEIVED_QUANTITY) AS Cur_Total_Qantity, \n"
                    + "  round(TO_NUMBER(tsv.WEIGHT_AVG_PRIC * mgd.RECEIVED_QUANTITY),2) AS amountInBirr,\n"
                    + "                                         ROUND((TO_NUMBER( ((round((mgd.UNIT_PRICE),4) * mgd.RECEIVED_QUANTITY)+(TO_NUMBER(tsv.TOTAL_QTY) * round(TO_NUMBER(tsv.WEIGHT_AVG_PRIC),4)))\n"
                    + "                                          /TO_NUMBER((tsv.TOTAL_QTY)+mgd.RECEIVED_QUANTITY))),4) AS WT_AVG_PR,\n"
                    + " mgd.GRN_DETAIL_ID AS sivDetailId, \n"
                    + " msi.STORE_NO,\n"
                    + "TR.GL_ID,\n"
                    + "  TO_NUMBER((SQ.QUANTITY)+mgd.RECEIVED_QUANTITY)as totalApp,\n"
                    + "  mg.PROCESSED_ON,SUBSTR(mg.GRN_NO,1,3)as grnType,\n"
                    + "  ROUND(((TO_NUMBER( ((round((mgd.UNIT_PRICE),4) * mgd.RECEIVED_QUANTITY)+(TO_NUMBER(tsv.TOTAL_QTY) * round(TO_NUMBER(tsv.WEIGHT_AVG_PRIC),4)))\n"
                    + "                                          /TO_NUMBER((tsv.TOTAL_QTY)+mgd.RECEIVED_QUANTITY)))-tsv.WEIGHT_AVG_PRIC)*mgd.RECEIVED_QUANTITY,4)as ivaAmount\n"
                    + "                                        FROM FMS_STOCK_LEDGER_CARD fslc\n"
                    + "                                        INNER JOIN MMS_GRN_DETAIL mgd\n"
                    + "                                        ON mgd.ITEM_ID = fslc.MATERIAL_ID \n"
                    + "                                        INNER JOIN MMS_GRN mg \n"
                    + "                                        ON mg.GRN_ID=mgd.GRN_ID \n"
                    + "                                        inner join MMS_ITEM_REGISTRATION TR\n"
                    + "                                        ON TR.MATERIAL_ID=mgd.ITEM_ID\n"
                    + "                                        INNER JOIN MMS_STORE_INFORMATION msi \n"
                    + "                                        ON mg.STORE_ID = msi.STORE_ID \n"
                    + "                                        INNER JOIN FMS_TOTAL_STOCK_VALUE tsv \n"
                    + "                                        ON tsv.MATERIAL_CODE = fslc.MATERIAL_ID\n"
                    + "                                        INNER JOIN FMS_STOCK_QUANTITY SQ\n"
                    + "                                        ON SQ.MAT_CODE = tsv.MATERIAL_CODE\n"
                    + "                                        AND mg.GRN_NO =  '" + sivValue + "' and SQ.STORE_NO = '" + storeNo + "' ", "OrderResultsMmsSivDetail");
            luServiceTypes = (List<FmsStockLedgerCard>) query1.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select MATERIAL_NAME, MATERIAL_CODE,ITEM_ID,QUANTITY,WEIGHT_AVG_PRIC,SIV_DET_ID, STORE_NO and GL_ID list
     from FMS_STOCK_LEDGER_CARD,MMS_SIV_DETAIL,MMS_SIV,FMS_TOTAL_STOCK_VALUE, FMS_STOCK_QUANTITY,MMS_GRN,MMS_ITEM_REGISTRATION, MMS_STORE_INFORMATION
     FMS_TOTAL_STOCK_VALUE,FMS_STOCK_QUANTITY table
     by passing parameter of sivValue and store number
     using criteries
     returen selected list value*/
    public List<FmsStockLedgerCard> searchSIVListToSave(String sivValue, String storeNo) {
        List<FmsStockLedgerCard> luServiceTypes;
        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT fslc.MATERIAL_NAME, fslc.MATERIAL_CODE, msd.ITEM_ID AS MATERIAL_ID, ms.SIV_NO, \n"
                    + "                round(TO_NUMBER(tsv.WEIGHT_AVG_PRIC),2)AS U_PRICE, msd.QUANTITY AS APPROVED_QNTY, \n"
                    + "                TO_NUMBER(SQ.QUANTITY) AS prv_Qantity, \n"
                    + "                TO_NUMBER(SQ.QUANTITY - msd.QUANTITY) AS Cur_Total_Qantity,\n"
                    + "                round(TO_NUMBER(tsv.WEIGHT_AVG_PRIC * msd.QUANTITY),2) AS amountInBirr, \n"
                    + "                round(TO_NUMBER(tsv.WEIGHT_AVG_PRIC),2) AS WT_AVG_PR, \n"
                    + "                msd.SIV_DET_ID AS sivDetailId,msi.STORE_NO, mir.GL_ID,\n"
                    + "                TO_NUMBER((SQ.QUANTITY)- msd.QUANTITY)as totalApp,ms.PROCESSED_ON,SUBSTR(ms.SIV_NO,1,3)as grnType\n"
                    + "                 FROM FMS_STOCK_LEDGER_CARD fslc\n"
                    + "                 INNER JOIN MMS_SIV_DETAIL msd\n"
                    + "                 ON msd.ITEM_ID = fslc.MATERIAL_ID\n"
                    + "                 INNER JOIN MMS_SIV ms\n"
                    + "                 ON ms.SIV_ID=msd.SIV_ID\n"
                    + "                 INNER JOIN MMS_ITEM_REGISTRATION mir \n"
                    + "                 ON mir.MATERIAL_ID=msd.ITEM_ID\n"
                    + "                 INNER JOIN MMS_STORE_INFORMATION msi\n"
                    + "                 ON ms.STORE_ID= msi.STORE_ID\n"
                    + "                 INNER JOIN FMS_TOTAL_STOCK_VALUE tsv\n"
                    + "                 ON tsv.MATERIAL_CODE = fslc.MATERIAL_ID\n"
                    + "                 INNER JOIN FMS_STOCK_QUANTITY SQ\n"
                    + "                 ON SQ.MAT_CODE = tsv.MATERIAL_CODE\n"
                    + "                 WHERE  ms.SIV_NO  = '" + sivValue + "' AND SQ.STORE_NO = '" + storeNo + "'", "OrderResultsMmsSivDetail");
            luServiceTypes = (List<FmsStockLedgerCard>) query1.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select MATERIAL_NAME, MATERIAL_CODE,ITEM_ID,TRANSFER_NO,QUANTITY,WEIGHT_AVG_PRIC,PROCESSED_ON, STORE_NO,ISIV_NO and GL_ID list
     from FMS_STOCK_LEDGER_CARD,MMS_ISIV_DETAIL,MMS_ISIV,FMS_TOTAL_STOCK_VALUE, FMS_STOCK_QUANTITY,MMS_GRN,MMS_ITEM_REGISTRATION, MMS_STORE_INFORMATION
     FMS_TOTAL_STOCK_VALUE,FMS_STOCK_QUANTITY table
     by passing parameter of referece number and store number
     using criteries
     returen selected list value*/
    public List<FmsStockLedgerCard> searchISIVIListToSave(String refNo, String storeNo) {
        List<FmsStockLedgerCard> luServiceTypes;
        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT fslc.MATERIAL_NAME, fslc.MATERIAL_CODE, msd.ITEM_ID AS MATERIAL_ID,\n"
                    + "  ms.TRANSFER_NO AS SIV_NO, ROUND(TO_NUMBER(tsv.WEIGHT_AVG_PRIC ),2)AS U_PRICE, \n"
                    + "  msd.QUANTITY AS APPROVED_QNTY, TO_NUMBER(SQ.QUANTITY) AS prv_Qantity, \n"
                    + "  TO_NUMBER(SQ.QUANTITY - msd.QUANTITY) AS Cur_Total_Qantity,\n"
                    + "  ROUND(TO_NUMBER(tsv.WEIGHT_AVG_PRIC * msd.QUANTITY),2) AS amountInBirr, \n"
                    + "  ROUND(TO_NUMBER(tsv.WEIGHT_AVG_PRIC),2) AS WT_AVG_PR, msd.MAT_TRAN_DET_ID AS sivDetailId,\n"
                    + "  msi.STORE_NO AS STORE_NO, TR.GL_ID, TO_NUMBER((SQ.QUANTITY)+msd.QUANTITY)AS totalApp, ms.PROCESSED_ON,SUBSTR(ms.ISIV_NO,1,3)AS grnType, 0 AS ivaAmount\n"
                    + "  FROM FMS_STOCK_LEDGER_CARD fslc\n"
                    + "  INNER JOIN MMS_ISIV_DETAIL msd\n"
                    + "  ON msd.ITEM_ID = fslc.MATERIAL_ID\n"
                    + "  INNER JOIN MMS_ISIV ms\n"
                    + "  ON ms.TRANSFER_ID=msd.TRANSFER_ID\n"
                    + "  INNER JOIN MMS_ITEM_REGISTRATION TR\n"
                    + "  ON TR.MATERIAL_ID=msd.ITEM_ID\n"
                    + "  INNER JOIN MMS_STORE_INFORMATION msi\n"
                    + "  ON ms.FROM_STORE = msi.STORE_ID\n"
                    + "  INNER JOIN FMS_TOTAL_STOCK_VALUE tsv\n"
                    + "  ON tsv.MATERIAL_CODE = fslc.MATERIAL_ID\n"
                    + "  INNER JOIN FMS_STOCK_QUANTITY SQ\n"
                    + "  ON SQ.MAT_CODE = tsv.MATERIAL_CODE\n"
                    + "  WHERE  ms.TRANSFER_NO  = '" + refNo + "' AND SQ.STORE_NO = '" + storeNo + "'", "OrderResultsMmsSivDetail");
            luServiceTypes = (List<FmsStockLedgerCard>) query1.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }
    }

      /*native query to select MATERIAL_NAME, MATERIAL_CODE,MATERIAL_ID,RECEIVING_TRANSFER_NO,QUANTITY,WEIGHT_AVG_PRIC,PROCESSED_ON, STORE_NO,RECEIVING_TRANSFER_NO list
     from FMS_STOCK_LEDGER_CARD,MMS_ISIV_RECEIVED_DTL,MMS_ISIV_DETAIL,MMS_ISIV,FMS_TOTAL_STOCK_VALUE, FMS_STOCK_QUANTITY,MMS_GRN,MMS_ITEM_REGISTRATION, MMS_STORE_INFORMATION
     FMS_TOTAL_STOCK_VALUE,FMS_STOCK_QUANTITY table
     by passing parameter of referece number and store number
     using criteries
     returen selected list value*/
    public List<FmsStockLedgerCard> searchISIVRListToSave(String refNo, String storeNo) {
        List<FmsStockLedgerCard> luServiceTypes;
        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT fslc.MATERIAL_NAME, fslc.MATERIAL_CODE, msd.MATERIAL_ID AS MATERIAL_ID,\n"
                    + "  ms.RECEIVING_TRANSFER_NO AS SIV_NO, ROUND(TO_NUMBER(tsv.WEIGHT_AVG_PRIC),2)AS U_PRICE, \n"
                    + "  msd.QUANTITY AS APPROVED_QNTY, TO_NUMBER(SQ.QUANTITY) AS prv_Qantity,\n"
                    + "  TO_NUMBER(SQ.QUANTITY + msd.QUANTITY) AS Cur_Total_Qantity,\n"
                    + "  ROUND(TO_NUMBER(tsv.WEIGHT_AVG_PRIC * msd.QUANTITY),2) AS amountInBirr, \n"
                    + "  TO_NUMBER(tsv.WEIGHT_AVG_PRIC) AS WT_AVG_PR, msd.MATERIAL_ID AS sivDetailId,\n"
                    + "  msi.STORE_NO AS STORE_NO, TR.GL_ID, TO_NUMBER((SQ.QUANTITY)+msd.QUANTITY)AS totalApp,\n"
                    + "  ms.PROCESSED_ON, SUBSTR(ms.RECEIVING_TRANSFER_NO,1,3)AS grnType,0 AS ivaAmount\n"
                    + "  FROM FMS_STOCK_LEDGER_CARD fslc\n"
                    + "  INNER JOIN MMS_ISIV_RECEIVED_DTL msd\n"
                    + "  ON msd.MATERIAL_ID = fslc.MATERIAL_ID\n"
                    + "  INNER JOIN MMS_ISIV_RECEIVED ms\n"
                    + "  ON ms.RECIEVING_ID=msd.RECEIVING_ID\n"
                    + "  INNER JOIN MMS_ISIV misi\n"
                    + "  ON ms.TRANSFER_ID=misi.TRANSFER_ID\n"
                    + "  INNER JOIN MMS_STORE_INFORMATION msi\n"
                    + "  ON misi.TO_STORE = msi.STORE_ID\n"
                    + "  INNER JOIN MMS_ITEM_REGISTRATION TR\n"
                    + "  ON TR.MATERIAL_ID=msd.MATERIAL_ID\n"
                    + "  INNER JOIN FMS_TOTAL_STOCK_VALUE tsv\n"
                    + "  ON tsv.MATERIAL_CODE= fslc.MATERIAL_ID\n"
                    + "  INNER JOIN FMS_STOCK_QUANTITY SQ\n"
                    + "  ON SQ.MAT_CODE = tsv.MATERIAL_CODE\n"
                    + "  WHERE  ms.RECEIVING_TRANSFER_NO  = '" + refNo + "' AND SQ.STORE_NO = '" + storeNo + "' ", "OrderResultsMmsSivDetail");
            luServiceTypes = (List<FmsStockLedgerCard>) query1.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }
    }
    
     /*native query to select MATERIAL_NAME, MATERIAL_CODE,MATERIAL_ID,ITEM_ID,NFA_NO,RECEIVING_TRANSFER_NO,QUANTITY,WEIGHT_AVG_PRIC,PROCESSED_ON, STORE_NO,RECEIVING_TRANSFER_NO list
     from FMS_STOCK_LEDGER_CARD,MMS_NON_FIXED_ASSET_RETURN_DTL,MMS_NON_FIXED_ASSET_RETURN,FMS_TOTAL_STOCK_VALUE, FMS_STOCK_QUANTITY,MMS_GRN,MMS_ITEM_REGISTRATION, MMS_STORE_INFORMATION
     FMS_TOTAL_STOCK_VALUE,FMS_STOCK_QUANTITY table
     by passing parameter of referece number and store number
     using criteries
     returen selected list value*/
    public List<FmsStockLedgerCard> searchSRNListToSave(String refNo, String storeNo) {
        List<FmsStockLedgerCard> luServiceTypes;
        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT fslc.MATERIAL_NAME, fslc.MATERIAL_CODE, msd.ITEM_ID AS MATERIAL_ID,\n"
                    + "                    ms.NFA_NO AS SIV_NO, ROUND(TO_NUMBER(tsv.WEIGHT_AVG_PRIC),2)AS U_PRICE, \n"
                    + "                    msd.QUANTITY AS APPROVED_QNTY, TO_NUMBER(SQ.QUANTITY) AS prv_Qantity,\n"
                    + "                    TO_NUMBER(SQ.QUANTITY + msd.QUANTITY) AS Cur_Total_Qantity,\n"
                    + "                    ROUND(TO_NUMBER(tsv.WEIGHT_AVG_PRIC * msd.QUANTITY),2) AS amountInBirr, \n"
                    + "                    TO_NUMBER(tsv.WEIGHT_AVG_PRIC) AS WT_AVG_PR, msd.ITEM_ID AS sivDetailId,\n"
                    + "                    msi.STORE_NO AS STORE_NO, TR.GL_ID, TO_NUMBER((SQ.QUANTITY)+msd.QUANTITY)AS totalApp,\n"
                    + "                    ms.PROCESSED_ON, SUBSTR(ms.NFA_NO,1,3)AS grnType,0 AS ivaAmount\n"
                    + "                    FROM FMS_STOCK_LEDGER_CARD fslc\n"
                    + "                    INNER JOIN MMS_NON_FIXED_ASSET_RETURN_DTL msd\n"
                    + "                    ON msd.ITEM_ID = fslc.MATERIAL_ID\n"
                    + "                    INNER JOIN MMS_NON_FIXED_ASSET_RETURN ms\n"
                    + "                    ON ms.NFA_ID=msd.NFA_ID                   \n"
                    + "                    INNER JOIN MMS_STORE_INFORMATION msi\n"
                    + "                    ON ms.STORE_ID = msi.STORE_ID\n"
                    + "                    INNER JOIN MMS_ITEM_REGISTRATION TR\n"
                    + "                    ON TR.MATERIAL_ID=msd.ITEM_ID\n"
                    + "                    INNER JOIN FMS_TOTAL_STOCK_VALUE tsv\n"
                    + "                    ON tsv.MATERIAL_CODE = fslc.MATERIAL_ID\n"
                    + "                    INNER JOIN FMS_STOCK_QUANTITY SQ\n"
                    + "                    ON SQ.MAT_CODE = tsv.MATERIAL_CODE\n"
                    + "                    WHERE  ms.NFA_NO  = '" + refNo + "' AND SQ.STORE_NO = '" + storeNo + "' ", "OrderResultsMmsSivDetail");
            luServiceTypes = (List<FmsStockLedgerCard>) query1.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select all list from FMS_STOCK_LEDGER_CARD table by passing store id
    return all list*/
    public List<FmsStockLedgerCard> searchByStoreId(int storeId) {
        try {
            Query query1 = em.createNativeQuery("SELECT slc.* FROM FMS_STOCK_LEDGER_CARD slc WHERE slc.ID = "
                    + "(SELECT MAX(slcc.ID) FROM FMS_STOCK_LEDGER_CARD slcc "
                    + "WHERE slc.STORE_NO = '" + storeId + "' "
                    + "and  slc.MATERIAL_ID=slcc.MATERIAL_ID)", FmsStockLedgerCard.class);

            return (List<FmsStockLedgerCard>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

        /*storedProcedure Query to select list from FMS_SAVE_STOCK_LEDGER table
    by passing store number and status type using statusType,storeNos and oRes
        return in value*/
    public int saveStockLedger(String statusType, String storeNos) {
        try {
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("FMS_SAVE_STOCK_LEDGER");
            storedProcedure.registerStoredProcedureParameter("statusType", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("storeNos", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("oRes", String.class, ParameterMode.OUT);
            storedProcedure.setParameter("statusType", statusType);
            storedProcedure.setParameter("storeNos", storeNos);
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
}
