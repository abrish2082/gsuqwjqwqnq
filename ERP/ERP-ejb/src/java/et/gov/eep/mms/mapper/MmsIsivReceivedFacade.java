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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsIsivReceivedFacade extends AbstractFacade<MmsIsivReceived> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsIsivReceivedFacade() {
        super(MmsIsivReceived.class);
    }
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">    

    public List<MmsIsivReceived> searchByStoreId(MmsIsivReceived isivReceived) {
        Query query = em.createNamedQuery("MmsIsivReceived.findByReceivingStore", MmsIsivReceived.class);
        query.setParameter("receivingStoreId", isivReceived.getReceivingStoreId());

        try {
            ArrayList<MmsIsivReceived> IsivList = new ArrayList(query.getResultList());

            return IsivList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsivReceived> SearchByStoreAndIsivNo(MmsStoreInformation storeEntity, MmsIsivReceived isiv) {

//        Query query = em.createNamedQuery("MmsIsivReceived.findByIsivNoAndReceivingStoreId", MmsIsivReceived.class);
//        query.setParameter("fromStore", isivReceived.getReceivingStoreId());
//        query.setParameter("transferNo", isivReceived. + '%');
        Query query = em.createNamedQuery("MmsIsivReceived.findByIsivNoAndReceivingStoreId", MmsIsivReceived.class);
        query.setParameter("receivingStoreId", isiv.getReceivingStoreId());
        query.setParameter("receivingTransferNo", isiv.getReceivingTransferNo() + '%');
        try {
            ArrayList<MmsIsivReceived> IsivList = new ArrayList(query.getResultList());
            return IsivList;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsIsivReceived getLastTransferNo() {
        Query query = em.createNamedQuery("MmsIsivReceived.findByIsivIdMaximum");
        MmsIsivReceived result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsIsivReceived) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {

            return null;
        }
    }

    public ArrayList<MmsIsivReceived> getapprovedISIVRecievableList(MmsStoreInformation store, int status) {

        System.out.println("============store @FACADE=========" + store.getStoreId());
        Query query = em.createNamedQuery("MmsIsivReceived.findByApprovedStatusAndStoreId");
        query.setParameter("Status", status);
        query.setParameter("toStore", store.getStoreId());

        try {

            ArrayList<MmsIsivReceived> mmsIsivs = new ArrayList(query.getResultList());
            System.out.println("==============@FACADE List========" + mmsIsivs.size());

            return mmsIsivs;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsIsivReceived getByReceivingId(MmsIsivReceived ivEntity) {

        Query query = em.createNamedQuery("MmsIsivReceived.findByRecievingId", MmsIsivReceived.class);
        query.setParameter("recievingId", ivEntity.getRecievingId());
        try {
            MmsIsivReceived listofisiv = (MmsIsivReceived) (query.getSingleResult());

            return listofisiv;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsivReceived> findByStatus(int status) {
        Query query = em.createNamedQuery("MmsIsivReceived.findByStatus");
        query.setParameter("Status", status);
        try {
            ArrayList<MmsIsivReceived> IsivRStatusList = new ArrayList(query.getResultList());
            return IsivRStatusList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsivReceived> searchAllByPreparerId(MmsIsivReceived ivReceivedEntity) {
        Query query = em.createNamedQuery("MmsIsivReceived.findAllByPreparerId", MmsIsivReceived.class);

        query.setParameter("processedBy", ivReceivedEntity.getProcessedBy());

        try {
            ArrayList<MmsIsivReceived> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="NativeQuery">   
    public List<MmsIsivReceived> searchByStoreIdAndProcessedBy(MmsIsivReceived isivReceived) {

        try {
            Query query1 = em.createNativeQuery("(SELECT siv.*\n"
                    + "FROM MMS_ISIV_RECEIVED siv\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.RECIEVING_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf. RECIEVING_ID   IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY ='" + isivReceived.getProcessedBy() + "'\n"
                    + "  GROUP BY (wf.RECIEVING_ID)\n"
                    + "  ) t1\n"
                    + "ON siv.RECIEVING_ID    =t1.RECIEVING_ID\n"
                    + "WHERE siv.RECEIVING_STORE_ID ='" + isivReceived.getReceivingStoreId().getStoreId() + "'\n"
                    + ")",
                    MmsIsivReceived.class);
            return (List<MmsIsivReceived>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsIsivReceived> SearchByStoreAndIsivNoAndProcessedBy(MmsStoreInformation storeEntity, MmsIsivReceived isiv) {

        try {
            Query query1 = em.createNativeQuery("(SELECT siv.*\n"
                    + "FROM MMS_ISIV_RECEIVED siv\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.RECIEVING_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf. RECIEVING_ID   IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY ='" + isiv.getProcessedBy() + "'\n"
                    + "  GROUP BY (wf.RECIEVING_ID)\n"
                    + "  ) t1\n"
                    + "ON siv.RECIEVING_ID    =t1.RECIEVING_ID\n"
                    + "WHERE siv.RECEIVING_STORE_ID ='" + storeEntity.getStoreId() + "'\n"
                    + "AND siv.RECEIVING_TRANSFER_NO LIKE '" + isiv.getReceivingTransferNo() + "%'\n"
                    + ")",
                    MmsIsivReceived.class);
            return (List<MmsIsivReceived>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsivReceived> searchISIVRList(MmsStoreInformation mmsStoreInformation) {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT misr.RECEIVING_TRANSFER_NO,\n"
                    + "                    misr.RECIEVING_ID FROM MMS_ISIV_RECEIVED misr\n"
                    + "                    INNER JOIN MMS_ISIV_RECEIVED_DTL misrd\n"
                    + "                    ON misr.RECIEVING_ID =misrd.RECEIVING_ID AND misr.RECEIVING_STORE_ID='" + mmsStoreInformation.getStoreId() + "'\n"
                    + "                    where misr.RECEIVING_TRANSFER_NO NOT IN (SELECT slc.REF_NO FROM FMS_STOCK_LEDGER_CARD slc WHERE slc.REF_NO is not null )", MmsIsivReceived.class);

//            Query query = em.createNativeQuery("SELECT DISTINCT misr.RECEIVING_TRANSFER_NO, "
//                    + "misr.RECIEVING_ID FROM MMS_ISIV_RECEIVED misr "
//                    + "INNER JOIN MMS_ISIV_RECEIVED_DTL misrd "
//                    + "ON misr.RECIEVING_ID =misrd.RECEIVING_ID "
//                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
//                    + "ON misrd.RECEIVING_DTL_ID = slc.GRN_DETAIL_ID "
//                    + "WHERE slc.REF_NO IS NULL "
//                    + "and misr.RECEIVING_STORE_ID='" + mmsStoreInformation.getStoreId() + "'", MmsIsivReceived.class);
            return (List<MmsIsivReceived>) query.getResultList();

        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsNonFixedAssetReturn> searchSRNList(MmsStoreInformation mmsStoreInformation) {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT misr.NFA_NO,\n"
                    + "                                  misr.NFA_ID FROM MMS_NON_FIXED_ASSET_RETURN misr\n"
                    + "                                  INNER JOIN MMS_NON_FIXED_ASSET_RETURN_DTL misrd\n"
                    + "                                  ON misr.NFA_ID =misrd.NFA_ID AND misr.STORE_ID='" + mmsStoreInformation.getStoreId() + "'\n"
                    + "                                  where misr.NFA_NO NOT IN (SELECT slc.REF_NO FROM FMS_STOCK_LEDGER_CARD slc WHERE slc.REF_NO is not null ) ", MmsNonFixedAssetReturn.class);

//            Query query = em.createNativeQuery("SELECT DISTINCT misr.NFA_NO, \n"
//                    + "                    misr.NFA_ID FROM MMS_NON_FIXED_ASSET_RETURN misr \n"
//                    + "                    INNER JOIN MMS_NON_FIXED_ASSET_RETURN_DTL misrd \n"
//                    + "                    ON misr.NFA_ID =misrd.NFA_ID \n"
//                    + "                    LEFT JOIN FMS_STOCK_LEDGER_CARD slc \n"
//                    + "                    ON misrd.NFA_DTTL_ID = slc.GRN_DETAIL_ID \n"
//                    + "                    WHERE slc.REF_NO IS NULL "
//                    + "                    AND misr.STORE_ID='" + mmsStoreInformation.getStoreId() + "'", MmsNonFixedAssetReturn.class);
            return (List<MmsNonFixedAssetReturn>) query.getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold>

    public List<String> getMmsReceivedColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_ISIV_RECEIVED')\n"
                + "   and COLUMN_NAME NOT IN ('ISSUING_STORE_ID','RECEIVING_STORE_ID','TRANSFER_ID','PROCESSED_ID','RECIEVING_ID','APPROVED_DATE')\n"
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

    public List<MmsIsivReceived> getReceivedListsByParameter(MmsIsivReceived ivReceivedEntity) {
        System.out.println("it's facade");
        System.out.println("here I facade colName " + ivReceivedEntity.getColumnName() + " colVal " + ivReceivedEntity.getColumnValue());
        List<MmsIsivReceived> colValueLists = new ArrayList<>();
        if (ivReceivedEntity.getColumnName() != null && !ivReceivedEntity.getColumnName().equals("")
                && ivReceivedEntity.getColumnValue() != null && !ivReceivedEntity.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_ISIV_RECEIVED\n"
                    + "   WHERE " + ivReceivedEntity.getColumnName().toLowerCase() + "='" + ivReceivedEntity.getColumnValue() + "'"
                    + "and " + ivReceivedEntity.getProcessedBy() + "='" + ivReceivedEntity.getProcessedBy() + "'", MmsIsivReceived.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                    System.out.println("list of Grn size " + colValueLists.size());
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("MmsIsivReceived.findByProcessedBy");
            query.setParameter("processedBy", ivReceivedEntity.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<MmsIsivReceived> getReceivedListsByParameter(ColumnNameResolver columnNameResolver, MmsIsivReceived ivReceivedEntity, String columnValue) {
      System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsIsivReceived> colValueLists = new ArrayList<>();
        if (ivReceivedEntity.getColumnName() != null && !ivReceivedEntity.getColumnName().equals("")
                && ivReceivedEntity.getColumnValue() != null && !ivReceivedEntity.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_ISIV_RECEIVED\n"
                    + "   WHERE " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "'"
                    + "and " + ivReceivedEntity.getProcessedBy() + "='" + ivReceivedEntity.getProcessedBy() + "'", MmsIsivReceived.class);
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
            Query query = em.createNamedQuery("MmsIsivReceived.findByProcessedBy");
            query.setParameter("processedBy", ivReceivedEntity.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }
   
}
