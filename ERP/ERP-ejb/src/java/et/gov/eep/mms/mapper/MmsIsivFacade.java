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
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsIsivFacade extends AbstractFacade<MmsIsiv> {

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
    public MmsIsivFacade() {
        super(MmsIsiv.class);
    }

    /**
     *
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
    public MmsIsiv getLastTransferNo() {
        Query query = em.createNamedQuery("MmsIsiv.findByIsivIdMaximum");
        MmsIsiv result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsIsiv) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param IsivNums
     * @return
     */
    public MmsIsiv getIsivInfoByIsivNo(MmsIsiv IsivNums) {
        Query query = em.createNamedQuery("MmsIsiv.findByTransferNo");
        query.setParameter("transferNo", IsivNums.getTransferNo());
        try {
            if (query.getResultList().size() > 0) {
                MmsIsiv IsivInfo = (MmsIsiv) query.getResultList().get(0);
                System.out.println("=======INFORmation========" + IsivInfo.getFromStore().getStoreName());
                return IsivInfo;
            }
        } catch (Exception ex) {
            System.out.println("============inside catch========");
            return null;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<MmsIsiv> findIsivNOList() {
        Query query = em.createNamedQuery("MmsIsiv.findISIVNosList");
        query.setParameter("approvedStatus", 1);
        try {
            ArrayList<MmsIsiv> IsivNOList = new ArrayList(query.getResultList());
            return IsivNOList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param isiv
     * @return
     */
    public ArrayList<MmsIsiv> searchByIsivNo(MmsIsiv isiv) {
        Query query = em.createNamedQuery("MmsIsiv.findbyIsisvNumberLike", MmsIsiv.class);
        query.setParameter("transferNo", isiv.getTransferNo() + '%');
        try {
            ArrayList<MmsIsiv> listofIsiv = new ArrayList(query.getResultList());
            return listofIsiv;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param selectedIsIvNo
     * @return
     */
    public MmsIsiv getISivInfoBySivNo(String selectedIsIvNo) {
        Query query = em.createNamedQuery("MmsIsiv.findByISivNo");
        query.setParameter("transferNo", selectedIsIvNo);
        try {
            if (query.getResultList().size() > 0) {
                MmsIsiv sivInfo = (MmsIsiv) query.getResultList().get(0);

                return sivInfo;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public List<MmsIsiv> searchByStoreAndIsivNo(MmsIsiv ivEntity) {
        Query query = em.createNamedQuery("MmsIsiv.findByIsivNoAndFromStoreId", MmsIsiv.class);
        query.setParameter("fromStore", ivEntity.getFromStore());
        query.setParameter("transferNo", ivEntity.getTransferNo() + '%');

        try {
            ArrayList<MmsIsiv> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsiv> searchByStoreID(MmsIsiv ivEntity) {
        Query query = em.createNamedQuery("MmsIsiv.findByFromStore", MmsIsiv.class);
        query.setParameter("fromStore", ivEntity.getFromStore());

        try {
            ArrayList<MmsIsiv> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsiv> searchByStoreIDAndProcessedBy(MmsIsiv ivEntity) {
        Query query = em.createNamedQuery("MmsIsiv.findByFromStoreAndProcessedBy", MmsIsiv.class);
        query.setParameter("fromStore", ivEntity.getFromStore());
        query.setParameter("fromStore", ivEntity.getFromStore());
        query.setParameter("processedBy", ivEntity.getProcessedBy());
        try {
            ArrayList<MmsIsiv> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsIsiv> getapprovedISIVRecievableList(MmsStoreInformation store, int status) {
        Query query = em.createNamedQuery("MmsIsiv.findByApprovedStatusAndStoreId", MmsIsiv.class);
        query.setParameter("StatusWf", status);
        System.out.println("stat=====" + status);
        query.setParameter("toStore", store.getStoreId());
        System.out.println("id ====" + store.getStoreId());
        System.out.println("size=======" + query.getResultList().size());
        try {
            ArrayList<MmsIsiv> mmsIsivs = new ArrayList<>();
            System.out.println("try");
            if (query.getResultList().size() > 0) {
                System.out.println("data existed");
                mmsIsivs = new ArrayList<>(query.getResultList());
                System.out.println("size===" + query.getResultList().size());
                System.out.println("list of values====" + mmsIsivs);
            }
            return mmsIsivs;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public ArrayList<MmsIsiv> getapprovedISIVIssuedList() {
        int status = 1;
        //String type="iSSUED";
        Query query = em.createNamedQuery("MmsIsiv.findByApprovedStatus");
        query.setParameter("approvedStatus", status);
        //query.setParameter("isivType", type);
        try {

            ArrayList<MmsIsiv> mmsIsivs = new ArrayList(query.getResultList());

            System.out.println("----" + query.getResultList());
            return mmsIsivs;
        } catch (Exception ex) {
            return null;
        }

    }

    public MmsIsiv getbyTransferId(MmsIsiv ivEntity) {
        Query query = em.createNamedQuery("MmsIsiv.findByTransferId");
        query.setParameter("transferId", ivEntity.getTransferId());
        try {
            MmsIsiv listofisiv = (MmsIsiv) (query.getResultList().get(0));
            return listofisiv;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsiv> findIsivListByWfStatus(int StatusWf) {
        Query query = em.createNamedQuery("MmsIsiv.findIsivListByWfStatus", MmsIsiv.class);
        query.setParameter("StatusWf", StatusWf);
        try {
            ArrayList<MmsIsiv> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsIsiv> findIsivListByWfStatusAndStoeId(MmsStoreInformation store, int StatusWf) {
        Query query = em.createNamedQuery("MmsIsiv.findIsivListByWfStatusAndStoreId", MmsIsiv.class);
        query.setParameter("StatusWf", StatusWf);
        query.setParameter("fromStore", store.getStoreId());
        try {
            ArrayList<MmsIsiv> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsIsiv> searchISIVList(MmsStoreInformation mmsStoreInformation) {
        try {

            Query query = em.createNativeQuery("SELECT DISTINCT mis.TRANSFER_NO , \n"
                    + "                    mis.TRANSFER_ID FROM MMS_ISIV mis \n"
                    + "                    INNER JOIN MMS_ISIV_DETAIL misd \n"
                    + "                    ON mis.TRANSFER_ID=misd.TRANSFER_ID and mis.FROM_STORE='" + mmsStoreInformation.getStoreId() + "'\n"
                    + "                    where mis.TRANSFER_NO NOT IN (SELECT slc.REF_NO FROM FMS_STOCK_LEDGER_CARD slc WHERE slc.REF_NO is not null ) ", MmsIsiv.class);
            return (List<MmsIsiv>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsIsiv> searchByStoreAndIsivNoAndProcessedBy(MmsIsiv ivEntity) {
        ArrayList<MmsIsiv> result = null;

        try {
            Query query1 = em.createNativeQuery("(SELECT isiv.*\n"
                    + "FROM MMS_ISIV isiv\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.TRANSFER_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf. TRANSFER_ID   IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY ='" + ivEntity.getProcessedBy() + "'\n"
                    + "  GROUP BY (wf.TRANSFER_ID)\n"
                    + "  ) t1\n"
                    + "ON isiv.TRANSFER_ID    =t1.TRANSFER_ID\n"
                    + "WHERE isiv.FROM_STORE ='" + ivEntity.getFromStore().getStoreId() + "'\n"
                    + "AND isiv.TRANSFER_NO LIKE '" + ivEntity.getTransferNo() + " %'\n"
                    + ")",
                    MmsIsiv.class);
            return (List<MmsIsiv>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsiv> searchIsivByStoreAndIsivNoAndProcessedBy(MmsIsiv ivEntity) {
        ArrayList<MmsIsiv> result = null;

        try {
            Query query1 = em.createNativeQuery("SELECT sr.* , wf.*  "
                    + "FROM mms_isiv sr          "
                    + "INNER JOIN wf_mms_processed wf "
                    + "ON sr.transfer_id= wf.transfer_id "
                    + "WHERE sr.from_store ='" + ivEntity.getFromStore().getStoreId() + "' "
                    + "AND wf.transfer_no = '" + ivEntity.getTransferNo() + "%' "
                    + "AND wf.processed_by = '" + ivEntity.getProcessedBy() + "' "
                    + "order BY wf.processed_id DESC",
                    MmsIsiv.class);
            return (List<MmsIsiv>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsIsiv> searchISIVByStoreIdAndProcessedBy(MmsIsiv isiv) {

        ArrayList<MmsIsiv> result = null;

        try {
            Query query1 = em.createNativeQuery("(SELECT isiv.*\n"
                    + "FROM MMS_ISIV isiv\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.TRANSFER_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf. TRANSFER_ID   IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY ='" + isiv.getProcessedBy() + "'\n"
                    + "  GROUP BY (wf.TRANSFER_ID)\n"
                    + "  ) t1\n"
                    + "ON isiv.TRANSFER_ID    =t1.TRANSFER_ID\n"
                    + "WHERE isiv.FROM_STORE ='" + isiv.getFromStore().getStoreId() + "'\n"
                    + ")",
                    MmsIsiv.class);
            return (List<MmsIsiv>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    //</editor-fold>
    public List<String> getMmsIsivColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_ISIV')\n"
                + "   and COLUMN_NAME NOT IN ('DEPARTMENT','TRANSFER_ID','FROM_STORE','TO_STORE','STORE_REQ_ID','PROCESSED_ID')\n"
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



    public List<MmsIsiv> getIsivListsByParameter(ColumnNameResolver columnNameResolver, MmsIsiv isivEntity, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsIsiv> colValueLists = new ArrayList<>();
        if (isivEntity.getColumnName() != null && !isivEntity.getColumnName().equals("")
                && isivEntity.getColumnValue() != null && !isivEntity.getColumnValue().equals("")) {
            System.out.println("when if");
         
            Query query = em.createNativeQuery("SELECT * FROM MMS_ISIV\n"
                    + "   WHERE " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "'"
                    + "and " + isivEntity.getProcessedBy() + "='" + isivEntity.getProcessedBy() + "'", MmsIsiv.class);
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
            Query query = em.createNamedQuery("MmsIsiv.findByProcessedBy");
            query.setParameter("processedBy", isivEntity.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
        
    }

}
