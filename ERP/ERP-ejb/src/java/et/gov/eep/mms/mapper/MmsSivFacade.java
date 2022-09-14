/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsSivFacade extends AbstractFacade<MmsSiv> {

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
    public MmsSivFacade() {
        super(MmsSiv.class);
    }

    /**
     *
     * @param sivno
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">
    public ArrayList<MmsSiv> searchSivNumber(MmsSiv sivno) {
        Query query = em.createNamedQuery("MmsSiv.findBySivNoLike");
        query.setParameter("sivNo", sivno.getSivNo() + '%');
        try {
            ArrayList<MmsSiv> listofSiv = new ArrayList(query.getResultList());
            return listofSiv;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    public MmsSiv getSivInfoBySivNo(String selectedGrnNo) {
        Query query = em.createNamedQuery("MmsSiv.findBySivNo");
        query.setParameter("sivNo", selectedGrnNo);
        try {
            if (query.getResultList().size() > 0) {
                MmsSiv sivInfo = (MmsSiv) query.getResultList().get(0);

                return sivInfo;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public MmsSiv getLastSiVNo() {
        Query query = em.createNamedQuery("MmsSiv.findBySIVIdMaximum");
        MmsSiv result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsSiv) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public List<MmsSiv> findSivNOList() {
        Query query = em.createNamedQuery("MmsSiv.findBySivNoFixedSr");
        try {
            List<MmsSiv> SivNOList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                SivNOList = query.getResultList();
                System.out.println("size=" + SivNOList.size());
            }
            return SivNOList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsSiv getSivObjectBySivNo(MmsSiv selectedSivNo) {
        MmsSiv sivInfo = null;
        Query query = em.createNamedQuery("MmsSiv.findBySivNo");
        query.setParameter("sivNo", selectedSivNo.getSivNo());
        try {

            sivInfo = (MmsSiv) query.getSingleResult();

            return sivInfo;
        } catch (Exception ex) {
            System.err.println("===============expetion===============");
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<MmsSiv> approvedSivList() {
        Query query = em.createNamedQuery("MmsSiv.findByApprovedStatus");
        query.setParameter("approvedStatus", 1);
        try {
            ArrayList<MmsSiv> papmsSivs = new ArrayList(query.getResultList());

            return papmsSivs;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsSiv> findByStatus() {
        Query query = em.createNamedQuery("MmsSiv.findByStatus");
//        query.setParameter("employeeName", fmsFieldAllowanceEnty.getEmployeeName() + '%');
        try {
            ArrayList<MmsSiv> SivStatusList = new ArrayList(query.getResultList());
            return SivStatusList;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<MmsSiv> approvedSivListForLocationMangement() {
        Query query = em.createQuery("SELECT m FROM MmsSiv m WHERE m.Status ='1'");
        try {
            ArrayList<MmsSiv> papmsSivs = new ArrayList(query.getResultList());

            return papmsSivs;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsSiv getSivInfoBySivId(MmsSiv sivEntity) {
        Query query = em.createNamedQuery("MmsSiv.findBySivId");
        query.setParameter("sivId", sivEntity.getSivId());
        query.setParameter("processedBy", sivEntity.getProcessedBy());
        try {
            MmsSiv sivobj = (MmsSiv) (query.getResultList().get(0));
            return sivobj;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsSiv> searchByStoreId(MmsStoreInformation storeEntity) {

        Query query = em.createNamedQuery("MmsSiv.findByStoreId", MmsSiv.class);
        query.setParameter("storeId", storeEntity);
//        query.setParameter("processedBy", siv.getProcessedBy());
        try {
            ArrayList<MmsSiv> listofsiv = new ArrayList(query.getResultList());
            return listofsiv;
        } catch (Exception e) {
            return null;
        }

    }

    public List<MmsSiv> searchSivByIssuedToAndStoreId(MmsSiv siv) {
        Query query = em.createNamedQuery("MmsSiv.findByIssuedToAndStoreId", MmsSiv.class);
        query.setParameter("storeId", siv.getStoreId());
        System.out.println("======searchesivbyissuedandStoreId" + siv.getStoreId());
        try {
            ArrayList<MmsSiv> listofsiv = null;
            if (query.getResultList().size() > 0) {
                System.out.println("sise");
                listofsiv = new ArrayList(query.getResultList());
                System.out.println("sise=====" + listofsiv.size());
            }
            return listofsiv;
        } catch (Exception e) {
            return null;
        }

    }

    public List<MmsSiv> searchSivByIssuedToAndStoreIdAndProcessedBy(MmsSiv siv) {
        Query query = em.createNamedQuery("MmsSiv.findByIssuedToAndStoreIdAndProcessedBy", MmsSiv.class);
        query.setParameter("issuedTo", siv.getIssuedTo());
        query.setParameter("storeId", siv.getStoreId());
        query.setParameter("processedBy", siv.getProcessedBy());

        ArrayList<MmsSiv> listofsiv = new ArrayList(query.getResultList());
        return listofsiv;

    }

    public List<MmsSiv> searchByStoreId2(MmsSiv sivEntity) {
        Query query = em.createNamedQuery("MmsSiv.findByStoreId2", MmsSiv.class);
        query.setParameter("storeId", sivEntity.getStoreId());

        ArrayList<MmsSiv> listofsiv = new ArrayList(query.getResultList());
        return listofsiv;
    }

    public List<MmsSiv> searchByStoreId2AndProcessedBy(MmsSiv sivEntity) {
        Query query = em.createNamedQuery("MmsSiv.findByStoreId2AndProcessedBy", MmsSiv.class);

        query.setParameter("storeId", sivEntity.getStoreId());
        query.setParameter("processedBy", sivEntity.getProcessedBy());
        ArrayList<MmsSiv> listofsiv = new ArrayList(query.getResultList());
        return listofsiv;
    }

    public ArrayList<MmsSiv> approvedStoreSivList(MmsStoreInformation storeinfo, int Status) {

        Query query = em.createNamedQuery("MmsSiv.findByApprovedStatusAndStore");
        query.setParameter("status", Status);
        query.setParameter("storeId", storeinfo.getStoreId());

        try {

            ArrayList<MmsSiv> mmssivs = new ArrayList(query.getResultList());

            return mmssivs;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsSiv> findSivListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsSiv.findSivListByWfStatus", MmsSiv.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsSiv> listofsiv = new ArrayList(query.getResultList());
            return listofsiv;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<MmsSiv> findAllwithApprovedStatus(MmsStoreInformation storeInfoEntity, int a) {
        Query query = em.createNamedQuery("MmsSiv.findByApprovedStatusAndStore", MmsSiv.class);
        query.setParameter("status", a);
        System.out.println("status======" + a);
        query.setParameter("storeId", storeInfoEntity.getStoreId());
        System.out.println("===========@facade id" + storeInfoEntity.getStoreId());
        ArrayList<MmsSiv> result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsSivDetail> getItemCodelistWhenRecierver(MmsSiv sivNo) {
        System.out.println("here facade21");
        Query q = em.createNamedQuery("MmsSivDetail.findbysivIdforItem");
        q.setParameter("sivId", sivNo.getSivId());
//        System.out.println("=====siv===" + sivNo);
        try {
            List<MmsSivDetail> itemLists = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                itemLists = q.getResultList();
                System.out.println("=====siv==" + itemLists);
            }
            return itemLists;
        } catch (Exception e) {
            return null;
        }
    }

    public MmsSiv findById(int id) {
        Query query = em.createNamedQuery("MmsSivDetail.findBySivId");
        query.setParameter("generalLedgerId", id);
        try {
            MmsSiv acclist = (MmsSiv) (query.getSingleResult());
            return acclist;
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<MmsSiv> findByStatus(int status) {
        Query query = em.createNamedQuery("MmsSiv.findByStatus");
        query.setParameter("Status", status);
        try {
            ArrayList<MmsSiv> sivStatusList = new ArrayList(query.getResultList());
            return sivStatusList;
        } catch (Exception ex) {
            return null;
        }
    }
 // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsSiv> searchBySivNosForRollBack(MmsStoreInformation storeEntity) {

        try {
            Query query1 = em.createNativeQuery("SELECT DISTINCT siv.SIV_NO,siv.SIV_ID  "
                    + "FROM MMS_SIV siv         "
                    + "INNER JOIN MMS_SIV_DETAIL sd "
                    + "ON siv.SIV_ID =sd.SIV_ID "
                    + "WHERE siv.STORE_ID ='" + storeEntity.getStoreId() + "' "
                    + "AND siv.SIV_NO NOT IN (SELECT slc.REF_NO FROM FMS_STOCK_LEDGER_CARD slc  WHERE slc.REF_NO IS NOT NULL and slc.STORE_NO= '" + storeEntity.getStoreNo() + "') ",
                    MmsSiv.class);
            return (List<MmsSiv>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsSiv> getSivNoByDepIdAndStroeId(MmsStoreInformation storeInfoEntity, HrDepartments hrDepartmentsEntity) {
        Query query = em.createNativeQuery("SELECT siv.siv_no FROM mms_siv siv\n"
                + "inner join mms_storereq sr\n"
                + "on siv.store_req_id=sr.store_req_id\n"
                + "where siv.store_id='" + storeInfoEntity.getStoreId() + "' and sr.department='" + hrDepartmentsEntity.getDepId() + "'");
        System.out.println("after===" + storeInfoEntity.getStoreId() + "and==" + hrDepartmentsEntity.getDepId());
        try {
            List<MmsSiv> sivNos = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                sivNos = query.getResultList();
                System.out.println("lists===" + sivNos);
            }
            return sivNos;
        } catch (Exception e) {
            return null;
        }

    }

    public List<MmsSiv> searchByStoreIdAndprocessedBy(MmsStoreInformation storeEntity, MmsSiv siv) {

        try {
            Query query1 = em.createNativeQuery("(SELECT siv.*\n"
                    + "FROM MMS_SIV siv\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.SIV_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf. SIV_ID   IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY ='" + siv.getProcessedBy() + "'\n"
                    + "  GROUP BY (wf.SIV_ID)\n"
                    + "  ) t1\n"
                    + "ON siv.SIV_ID    =t1.SIV_ID\n"
                    + "WHERE siv.STORE_ID ='" + storeEntity.getStoreId() + "'\n"
                    + ")",
                    MmsSiv.class);
            return (List<MmsSiv>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsSiv> searchBySivNoAndStoreId(MmsSiv siv, MmsStoreInformation storeEntity) {

        try {
            Query query1 = em.createNativeQuery("SELECT siv.* , wf.*  "
                    + "FROM mms_siv siv          "
                    + "INNER JOIN wf_mms_processed wf "
                    + "ON siv.siv_id =wf.siv_id "
                    + "WHERE siv.store_id ='" + storeEntity.getStoreId() + "' "
                    + "AND wf.processed_by = '" + siv.getProcessedBy() + "'"
                    + "AND siv.siv_no = '" + siv.getSivNo() + "'"
                    + "order BY wf.processed_id DESC",
                    MmsSiv.class);
            return (List<MmsSiv>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsSiv> searchBySivNoAndStoreIdAndProcessedBy(MmsSiv siv, MmsStoreInformation storeEntity) {
        try {
            Query query1 = em.createNativeQuery("(SELECT siv.*\n"
                    + "FROM MMS_SIV siv\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.SIV_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf. SIV_ID   IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY ='" + siv.getProcessedBy() + "'\n"
                    + "  GROUP BY (wf.SIV_ID)\n"
                    + "  ) t1\n"
                    + "ON siv.SIV_ID    =t1.SIV_ID\n"
                    + "WHERE siv.STORE_ID ='" + storeEntity.getStoreId() + "'\n"
                    + "AND siv.SIV_NO LIKE '" + siv.getSivNo() + "%'\n"
                    + ")",
                    MmsSiv.class);
            return (List<MmsSiv>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsSiv> searchSIVList(MmsStoreInformation storeInformation) {
        clearCach();
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT ms.SIV_NO ,ms.SIV_ID \n"
                    + "                    FROM MMS_SIV ms INNER JOIN MMS_SIV_DETAIL msd \n"
                    + "                    ON ms.SIV_ID=msd.SIV_ID\n"
                    + "                    and ms.STORE_ID='" + storeInformation.getStoreId() + "'\n"
                    + "                    where ms.SIV_NO NOT IN (SELECT slc.REF_NO FROM FMS_STOCK_LEDGER_CARD slc WHERE slc.REF_NO is not null)", MmsSiv.class);

//            Query query = em.createNativeQuery("SELECT DISTINCT ms.SIV_NO ,ms.SIV_ID "
//                    + "FROM MMS_SIV ms INNER JOIN MMS_SIV_DETAIL msd "
//                    + "ON ms.SIV_ID=msd.SIV_ID INNER JOIN MMS_STOREREQ_DETAIL msrdl "
//                    + "ON msrdl.ITEM_ID = msd.ITEM_ID "
//                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
//                    + "ON mir.MATERIAL_ID= msrdl.ITEM_ID "
//                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
//                    + "ON msd.SIV_DET_ID = slc.GRN_DETAIL_ID "
//                    + "WHERE slc.REF_NO IS NULL AND ms.STATUS = 3 "
//                    + "and ms.STORE_ID='" + storeInformation.getStoreId() + "'", MmsSiv.class);
            return (List<MmsSiv>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getMmsSivColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_SIV')\n"
                + "   and COLUMN_NAME NOT IN ('SIV_ID','PROCESSED_ID','STORE_REQ_ID','STORE_ID')\n"
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

    public List<MmsSiv> getSivListsByParameter(ColumnNameResolver columnNameResolver, MmsSiv siv, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsSiv> colValueLists = new ArrayList<>();
        if (siv.getColumnName() != null && !siv.getColumnName().equals("")
                && siv.getColumnValue() != null && !siv.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_SIV\n"
                    + "   WHERE " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "'"
                    + "and " + siv.getProcessedBy() + "='" + siv.getProcessedBy() + "'", MmsSiv.class);
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
            Query query = em.createNamedQuery("MmsSiv.findByProcessedBy");
            query.setParameter("processedBy", siv.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }
   // </editor-fold>
}
