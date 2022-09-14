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
import et.gov.eep.mms.entity.MmsRmg;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsRmgFacade extends AbstractFacade<MmsRmg> {

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
    public MmsRmgFacade() {
        super(MmsRmg.class);
    }

    /**
     *
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">
    public MmsRmg getLastRmgnNo() {
        MmsRmg result = null;
        Query query = em.createNamedQuery("MmsRmg.findIdMaximum", MmsRmg.class);
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsRmg) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param rmg
     * @return
     */
    public List<MmsRmg> findRmgPreparedList() {
        Query query = em.createNamedQuery("MmsRmg.findByStatusPrepared");
//        query.setParameter("employeeName", fmsFieldAllowanceEnty.getEmployeeName() + '%');
        try {
            List<MmsRmg> faPreparedList = query.getResultList();
            return faPreparedList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsRmg> findByStatus(int status) {
        Query query = em.createNamedQuery("MmsRmg.findByStatus");
        query.setParameter("status", status);
        try {
            ArrayList<MmsRmg> faCheckedList = new ArrayList(query.getResultList());
            return faCheckedList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsRmg> findRmgListForCheckerByWfStatus(int preparerStatus, int approverRejectStatus) {
        Query query = em.createNamedQuery("MmsRmg.findRmgListForCheckerByWfStatus", MmsRmg.class);
        query.setParameter("prepared", preparerStatus);
        query.setParameter("approverReject", approverRejectStatus);

        try {
            ArrayList<MmsRmg> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<MmsRmg> searchByRmgNo(MmsRmg rmg) {
        Query query = em.createNamedQuery("MmsRmg.findByrmgNo", MmsRmg.class);
        query.setParameter("rmgNo", rmg.getRmgNo() + '%');
//           query.setParameter("processedBy", rmg.getProcessedBy());
        try {
            ArrayList<MmsRmg> listofRmg = new ArrayList(query.getResultList());
            return listofRmg;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<MmsRmg> searchByRmgNoAndProcessedBy(MmsRmg rmg) {
        Query query = em.createNamedQuery("MmsRmg.findByrmgNoAndProcessedBy", MmsRmg.class);
        query.setParameter("rmgNo", rmg.getRmgNo() + '%');
        query.setParameter("processedBy", rmg.getProcessedBy());

        try {
            ArrayList<MmsRmg> listofRmg = new ArrayList(query.getResultList());
            return listofRmg;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<MmsRmg> searchRMGByProcessedBy(MmsRmg rmg) {
        Query query = em.createNamedQuery("MmsRmg.findByprocessedBy", MmsRmg.class);
        System.out.println("==processed by id==" + rmg.getProcessedBy());
        query.setParameter("processedBy", rmg.getProcessedBy());

        try {
            ArrayList<MmsRmg> listofRmg = new ArrayList(query.getResultList());
            return listofRmg;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<MmsRmg> getapprovedRmgList() {
        int status = 1;
        Query query = em.createNamedQuery("MmsRmg.findByApprovedStatus");
        query.setParameter("approvedStatus", status);
        try {

            ArrayList<MmsRmg> mmsrmgs = new ArrayList(query.getResultList());

            System.out.println("----" + query.getResultList());
            return mmsrmgs;
        } catch (Exception ex) {
            return null;
        }

    }

    public ArrayList<MmsRmg> approvedRmgListByStoreAndStatus(MmsRmg rmg, int status) {

        Query query = em.createNamedQuery("MmsRmg.findByStatusAndStore");
        query.setParameter("recevingStore", rmg.getRecevingStore());
        query.setParameter("status", status);
        try {

            ArrayList<MmsRmg> mmsrmgs = new ArrayList(query.getResultList());

            return mmsrmgs;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsRmg getRmgInfoByRmgId(MmsRmg rmgEntity) {
        Query query = em.createNamedQuery("MmsIsiv.findByTransferId");
        query.setParameter("transferId", rmgEntity.getRmgId());
        try {
            MmsRmg rmgObject = (MmsRmg) (query.getResultList().get(0));
            return rmgObject;
        } catch (Exception ex) {
            return null;
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsRmg> searchRMGList() {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT mr.RMG_NO ,mr.RMG_ID "
                    + "                     FROM MMS_RMG mr "
                    + "                     INNER JOIN MMS_RMG_DETAIL mrd "
                    + "                     ON mr.RMG_ID=mrd.RMG_ID "
                    + "                     INNER JOIN MMS_STOREREQ_DETAIL msrdl "
                    + "                     ON msrdl.ITEM_ID = mrd.ITEM_ID "
                    + "                     INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "                     ON mir.MATERIAL_ID= msrdl.ITEM_ID "
                    + "                     LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
                    + "                     ON msrdl.ITEM_ID = slc.MATERIAL_ID "
                    + "                     WHERE mir.ITEM_GROUP  = 0 ", MmsRmg.class);

            return (List<MmsRmg>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getMmsRmgColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_RMG')\n"
                + "   and COLUMN_NAME NOT IN ('APPROVED_BY','RMG_ID','INSPECTION_ID','INSPECTION_RESULT_NO')\n"
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

    public List<MmsRmg> getRMGListsByParameter(MmsRmg rmgentity) {
        List<MmsRmg> colValueLists = new ArrayList<>();
        if (rmgentity.getColumnName() != null && !rmgentity.getColumnName().equals("")
                && rmgentity.getColumnValue() != null && !rmgentity.getColumnValue().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM MMS_RMG\n"
                    + "   WHERE " + rmgentity.getColumnName().toLowerCase() + "='" + rmgentity.getColumnValue() + "'"
                    + "and " + rmgentity.getProcessedBy() + "='" + rmgentity.getProcessedBy() + "'", MmsRmg.class);
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
            Query query = em.createNamedQuery("MmsRmg.findByprocessedBy");
            query.setParameter("processedBy", rmgentity.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }

    public List<MmsRmg> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, MmsRmg rmgentity, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsRmg> colValueLists = new ArrayList<>();
        if (rmgentity.getColumnName() != null && !rmgentity.getColumnName().equals("")
                && rmgentity.getColumnValue() != null && !rmgentity.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_RMG\n"
                    + "   WHERE " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "'"
                    + "and " + rmgentity.getProcessedBy() + "='" + rmgentity.getProcessedBy() + "'", MmsRmg.class);
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
            Query query = em.createNamedQuery("MmsRmg.findByprocessedBy");
            query.setParameter("processedBy", rmgentity.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }
       // </editor-fold>
}
