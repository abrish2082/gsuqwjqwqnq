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
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsStorereqFacade extends AbstractFacade<MmsStorereq> {

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
    public MmsStorereqFacade() {
        super(MmsStorereq.class);
    }

    /**
     *
     * @param requestedBy
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">
//    public ArrayList<MmsStorereq> searchByRequesterName(MmsStorereq requestedBy) {
//        Query query = em.createNamedQuery("MmsStorereq.findByRequestedByLike");
//        query.setParameter("requestedBy", requestedBy.getRequestedBy().g + '%');
//        try {
//            ArrayList<MmsStorereq> listofRequester = new ArrayList(query.getResultList());
//            return listofRequester;
//        } catch (Exception ex) {
//            return null;
//        }
//    }
    /**
     *
     * @param srNumber
     * @return
     */
    public List<MmsStorereq> searchBySRNumber(MmsStorereq srNumber) {
        Query query = em.createNamedQuery("MmsStorereq.findBySRNumberLike");
        query.setParameter("srNo", srNumber.getSrNo() + '%');
        try {
            ArrayList<MmsStorereq> listofsrNumber = new ArrayList(query.getResultList());
            return listofsrNumber;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStorereq> getSrNo() {
        Query query = em.createNamedQuery("MmsStorereq.findAll");
        try {

            List<MmsStorereq> prNoLst = (List<MmsStorereq>) query.getResultList();
            return prNoLst;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    public MmsStorereq getSrInfoBySrNo(String selectedGrnNo) {
        Query query = em.createNamedQuery("MmsStorereq.findBySRNumberLike");
        query.setParameter("srNo", selectedGrnNo);
        try {
            if (query.getResultList().size() > 0) {
                MmsStorereq sivInfo = (MmsStorereq) query.getResultList().get(0);

                return sivInfo;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getMaxPaymentRequistion() {
        String result;
        Query query = em.createNamedQuery("MmsStorereq.findBySReqId");
        try {
            result = (String) query.getResultList().get(0);
            if (result == null) {
                result = "Pay-Req-0";
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public MmsStorereq getLastPaymentNo() {
        MmsStorereq result = null;
        Query query = em.createNamedQuery("MmsStorereq.findBySReqIdMaximum");

        try {
            if (query.getResultList().size() > 0) {
                result = (MmsStorereq) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param selectedInspNo
     * @return
     */
    public MmsStorereq getBySrnumber(String selectedInspNo) {
        MmsStorereq result = null;
        Query query = em.createNamedQuery("MmsStorereq.findBySrNo", MmsStorereq.class);
        query.setParameter("srNo", selectedInspNo);

        try {
            if (query.getResultList().size() > 0) {
                result = (MmsStorereq) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param store
     * @param a
     * @return
     */
    public ArrayList<MmsStorereq> findAllwithApprovedStatus(MmsStoreInformation store, int a) {
        ArrayList<MmsStorereq> result = null;
        Query query = em.createNamedQuery("MmsStorereq.findByApprovedStatusAndStore", MmsStorereq.class);
        query.setParameter("status", a);
        query.setParameter("requestStore", store.getStoreId());

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsStorereq> findAllwithApprovedStatusForIsiv(MmsStoreInformation store, int status) {
        clearCach();
        ArrayList<MmsStorereq> result = null;
        Query query = em.createNamedQuery("MmsStorereq.findByApprovedStatusAndStoreForISIV", MmsStorereq.class);
        System.out.println("====Status========" + status);
        System.out.println("====Storeds========" + store.getStoreId());
        query.setParameter("status", status);
        query.setParameter("requestStore", store.getStoreId());
        query.setParameter("toIsiv", 1);

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsStorereq> findAllByStoreId(MmsStorereq sr) {
        ArrayList<MmsStorereq> result = null;
        Query query = em.createNamedQuery("MmsStorereq.findByStoreId", MmsStorereq.class);
        query.setParameter("requestStore", sr.getRequestStore());

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsStorereq searchBySRNO(MmsStorereq sr) {
        Query query = em.createNamedQuery("MmsStorereq.findBySrNo", MmsStorereq.class);
        query.setParameter("srNo", sr.getSrNo());

        try {

            MmsStorereq result = (MmsStorereq) query.getResultList().get(0);

            return result;
        } catch (Exception ex) {

            return null;
        }
    }

    public MmsStorereq searchByStoreReqId(MmsStorereq sr) {
        Query query = em.createNamedQuery("MmsStorereq.findByStoreReqId", MmsStorereq.class);
        query.setParameter("storeReqId", sr.getStoreReqId());

        try {

            MmsStorereq result = (MmsStorereq) query.getSingleResult();

            return result;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsStorereq> searchSRByStoreId(MmsStoreInformation storeEntity) {
        ArrayList<MmsStorereq> result = null;
        Query query = em.createNamedQuery("MmsStorereq.findByStoreId", MmsStorereq.class);
        query.setParameter("requestStore", storeEntity);

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStorereq> searchBySRNoAndStoreId(MmsStorereq sr, MmsStoreInformation storeEntity) {
        Query query = em.createNamedQuery("MmsStorereq.findBySrNoAndStoreId", MmsStorereq.class);
        query.setParameter("srNo", sr.getSrNo() + '%');
        query.setParameter("requestStore", storeEntity);
        try {
            ArrayList<MmsStorereq> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStorereq> findSrListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsStorereq.findSrListByWfStatus", MmsStorereq.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsStorereq> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStorereq> findSrListForCheckerByWfStatus(int preparerStatus, int approverRejectStatus) {
        Query query = em.createNamedQuery("MmsStorereq.findSrListForCheckerByWfStatus", MmsStorereq.class);
        query.setParameter("prepared", preparerStatus);
        query.setParameter("approverReject", approverRejectStatus);

        try {
            ArrayList<MmsStorereq> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsStorereq> findSrList() {
        Query query = em.createNamedQuery("MmsStorereq.findBySrNoFixedSr");
        try {
            System.out.println("find list of sr nos size " + query.getResultList().size());
            List<MmsStorereq> SrNOList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                SrNOList = query.getResultList();
                System.out.println("size=" + SrNOList.size());
            }
            return SrNOList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //getting SR No Lists w/c Pr_Quantity greater than Zero from Mms_StoreReq_Detail
    public List<MmsStorereq> getSrNoListsHavePrQuantity(int pr_QuantitySrDetail) {
        Query query = em.createNativeQuery("SELECT sr.sr_no FROM mms_storereq sr\n"
                + "where sr.store_req_id in (select srd.store_req_id FROM mms_storereq_detail srd where srd.pr_quantity > '" + pr_QuantitySrDetail + "')");

        try {
            List<MmsStorereq> srNoLst = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                srNoLst = query.getResultList();
            }
            return srNoLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsGrnDetail> getMmsDetailInfoByGrnId(MmsGrn mmsGrn) {
        Query q = em.createNamedQuery("MmsGrnDetail.findbyGrnIds");
        q.setParameter("grnId", mmsGrn.getGrnId());
        try {
            List<MmsGrnDetail> grnDetailLists = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                grnDetailLists = q.getResultList();
                System.out.println("Grn Detail List Size----- " + grnDetailLists.size());
            }
            return grnDetailLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsStorereq> searchSRByStoreIdAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq storereq) {

        ArrayList<MmsStorereq> result = null;
        //final int status=storereq.getStatus();

        try {
            Query query1 = em.createNativeQuery("(SELECT sr.*\n"
                    + "FROM mms_storereq sr\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.STORE_REQ_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf.STORE_REQ_ID IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY    ='" + storereq.getProcessedBy() + "' \n"
                    + "  GROUP BY (wf.STORE_REQ_ID)\n"
                    + "  ) t1\n"
                    + "ON sr.store_req_id     =t1.store_req_id\n"
                    + "WHERE sr.request_store ='" + storeEntity.getStoreId() + "'\n"
                    + ")",
                    MmsStorereq.class
            );
            return (List<MmsStorereq>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public List<MmsStorereq> searchBySRNoAndStoreIdAndProcessedBy(MmsStorereq sr, MmsStoreInformation storeEntity) {
        ArrayList<MmsStorereq> result = null;
        //final int status=storereq.getStatus();

        try {
            Query query1 = em.createNativeQuery("(SELECT sr.*\n"
                    + "FROM mms_storereq sr\n"
                    + "INNER JOIN\n"
                    + "  (SELECT DISTINCT(wf.STORE_REQ_ID),\n"
                    + "    MAX( wf.PROCESSED_ID)\n"
                    + "  FROM WF_MMS_PROCESSED wf\n"
                    + "  WHERE wf.STORE_REQ_ID IS NOT NULL\n"
                    + "  AND wf.PROCESSED_BY    ='" + sr.getProcessedBy() + "' \n"
                    + "  GROUP BY (wf.STORE_REQ_ID)\n"
                    + "  ) t1\n"
                    + "ON sr.store_req_id     =t1.store_req_id\n"
                    + "WHERE sr.request_store ='" + storeEntity.getStoreId() + "'\n"
                    + "AND sr.SR_NO    LIKE '" + sr.getSrNo() + "%' \n"
                    + ")",
                    MmsStorereq.class
            );
            return (List<MmsStorereq>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStorereq> searchSRByStoreIdAndStatusNewAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq sr) {
        ArrayList<MmsStorereq> result = null;
        final int status = 3;

        try {
            Query query1 = em.createNativeQuery("SELECT sr.* , wf.*  "
                    + "FROM mms_storereq sr          "
                    + "INNER JOIN wf_mms_processed wf "
                    + "ON sr.store_req_id =wf.store_req_id "
                    + "WHERE sr.request_store ='" + storeEntity.getStoreId() + "' "
                    //                    + "AND wf.processed_by = '" + sr.getProcessedBy() + "'"
                    + "AND sr.status = '" + status + "'"
                    + "order BY wf.processed_id DESC",
                    MmsStorereq.class);
            return (List<MmsStorereq>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStorereq> searchSRByStoreIdAndStatusProcessedAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq sr) {
        ArrayList<MmsStorereq> result = null;
        final int status = 3;

        try {
            Query query1 = em.createNativeQuery("SELECT sr.* , wf.*  "
                    + "FROM mms_storereq sr          "
                    + "INNER JOIN wf_mms_processed wf "
                    + "ON sr.store_req_id =wf.store_req_id "
                    + "WHERE sr.request_store ='" + storeEntity.getStoreId() + "' "
                    //                    + "AND wf.processed_by = '" + sr.getProcessedBy() + "'"
                    + "AND sr.status != '" + status + "'"
                    + "order BY wf.processed_id DESC",
                    MmsStorereq.class);
            return (List<MmsStorereq>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDepartments> DepNameListFromGrn() {
        Query query = em.createNativeQuery("SELECT * FROM hr_departments dpt\n"
                + "inner join mms_grn grn\n"
                + "on dpt.dep_id= grn.dep_id", HrDepartments.class);
        try {

            List<HrDepartments> depNameList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                depNameList = query.getResultList();
                System.out.println("size of Spec Dep't===" + depNameList.size());
            }
            return depNameList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsGrn> searchGrnDepartmentId(HrDepartments hrDepartmentEntity) {
        System.out.println("Selected Dep't Id  " + hrDepartmentEntity.getDepId());
        Query query = em.createNativeQuery("SELECT * FROM mms_grn gr\n"
                + "inner join mms_grn_detail grd\n"
                + "on gr.grn_id=grd.grn_id\n"
                + "inner join hr_departments dpt\n"
                + "on dpt.dep_id=gr.dep_id\n"
                + "where dpt.dep_id='" + hrDepartmentEntity.getDepId() + "' and\n"
                + "grd.remaining_quantity > 0", MmsGrn.class);
        try {

            List<MmsGrn> grnNoList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                grnNoList = query.getResultList();
                System.out.println("list of Grn size " + grnNoList.size());
            }
            return grnNoList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getMmsStorereqColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_STOREREQ')\n"
                + "   and COLUMN_NAME NOT IN ('DEPARTMENT','STORE_REQ_ID','PROJECT_ID','JOB_ID','GL_ID')\n"
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

    public List<MmsStorereq> getSRListsByParameter(MmsStorereq requisition) {
        System.out.println("it's facade");
        System.out.println("here I facade colName " + requisition.getColumnName() + " colVal " + requisition.getColumnValue());
        List<MmsStorereq> colValueLists = new ArrayList<>();
        if (requisition.getColumnName() != null && !requisition.getColumnName().equals("")
                && requisition.getColumnValue() != null && !requisition.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_STOREREQ\n"
                    + "   WHERE " + requisition.getColumnName().toLowerCase() + "='" + requisition.getColumnValue() + "'"
                    + "and " + requisition.getProcessedBy() + "='" + requisition.getProcessedBy() + "'", MmsStorereq.class);
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
            Query query = em.createNamedQuery("MmsStorereq.findByProcessedBy");
            query.setParameter("processedBy", requisition.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }

    }



    public List<MmsStorereq> getSRListsByParameter(ColumnNameResolver columnNameResolver, MmsStorereq requisition, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsStorereq> colValueLists = new ArrayList<>();
        if (columnNameResolver.getCol_Name_FromTable() != null && columnValue != null) {
         
            Query query = em.createNativeQuery("SELECT * FROM MMS_STOREREQ\n"
                    + "   WHERE " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "'"
                    + "and " + requisition.getProcessedBy() + "='" + requisition.getProcessedBy() + "'", MmsStorereq.class);
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
            Query query = em.createNamedQuery("MmsStorereq.findByProcessedBy");
            query.setParameter("processedBy", requisition.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    
    }
    // </editor-fold> 
}
