/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsQuotation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsPaymentRequisitionFacade extends AbstractFacade<PrmsPaymentRequisition> {

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
    public PrmsPaymentRequisitionFacade() {
        super(PrmsPaymentRequisition.class);
    }

    /**
     *
     * @param papmsServiceProvider
     * @return
     */
    public ArrayList<PrmsPaymentRequisition> searchRequestForPayment(PrmsPaymentRequisition papmsServiceProvider) {
        Query query = em.createNamedQuery("PrmsPaymentRequisition.SearchName");
        query.setParameter("reqPaymentNo", papmsServiceProvider.getReqPaymentNo().toLowerCase() + '%');
        try {

            ArrayList<PrmsPaymentRequisition> ServiceProvideList = new ArrayList(query.getResultList());
            return ServiceProvideList;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param prmsPaymentRequisition
     * @return
     */
    public PrmsPaymentRequisition getRequestNo(PrmsPaymentRequisition prmsPaymentRequisition) {
        Query query = em.createNamedQuery("PrmsPaymentRequisition.findByReqPaymentNo");
        query.setParameter("reqPaymentNo", prmsPaymentRequisition.getReqPaymentNo());
        try {
            PrmsPaymentRequisition selectedobj = (PrmsPaymentRequisition) query.getResultList().get(0);

            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPaymentRequisition> searchCheckList(PrmsPaymentRequisition prmsPaymentRequisition) {

        List<PrmsPaymentRequisition> paymentRequestList = new ArrayList();
        if (prmsPaymentRequisition.getColumnName() != null && !prmsPaymentRequisition.getColumnName().equals("")
                && prmsPaymentRequisition.getColumnValue() != null && !prmsPaymentRequisition.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_PAYMENT_REQUISITION "
                    + "WHERE " + prmsPaymentRequisition.getColumnName().toLowerCase() + " = '" + prmsPaymentRequisition.getColumnValue() + "'"
                    + " and " + prmsPaymentRequisition.getPreparedBy() + " = '" + prmsPaymentRequisition.getPreparedBy() + "'", PrmsPaymentRequisition.class);
            try {
                if (query.getResultList().size() > 0) {
                    paymentRequestList = query.getResultList();
                }
                return paymentRequestList;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsPaymentRequisition.findByPreparedBy");
            query.setParameter("preparedBy", prmsPaymentRequisition.getPreparedBy());
            paymentRequestList = query.getResultList();
            return paymentRequestList;
        }
    }

    public PrmsPaymentRequisition getSelectedRequest(Integer id) {
        Query query = em.createNamedQuery("PrmsPaymentRequisition.findByPaymentReqId");
        query.setParameter("paymentReqId", id);
        try {
            PrmsPaymentRequisition selectrequest = (PrmsPaymentRequisition) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPaymentRequisition getLastPaymentNo() {

        Query query = em.createNamedQuery("PrmsPaymentRequisition.findByMaxPaymentId");

        try {
            PrmsPaymentRequisition paymentRequestNo = null;
            if (query.getResultList().size() > 0) {
                paymentRequestNo = (PrmsPaymentRequisition) query.getResultList().get(0);

            }
            return paymentRequestNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //FCMS Integration Point

    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitions() {
        Query query = em.createNativeQuery("SELECT * FROM PRMS_PAYMENT_REQUISITION pr "
                + "WHERE pr.PAYMENT_TYPE = 'Check' AND "
                + "pr.TOTAL_AMOUNT > 10000  AND "
                + "pr.REQ_PAYMENT_NO NOT IN(SELECT fmschk.REFERENCE_NUMBER FROM FMS_CHEQUE_PAYMENT_VOUCHER fmschk)", PrmsPaymentRequisition.class);
        try {
            ArrayList<PrmsPaymentRequisition> paymentRequeList = new ArrayList<>(query.getResultList());
            return paymentRequeList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public PrmsPaymentRequisition getPrmsPaymentReq(String refNo_) {
        Query query = em.createNamedQuery("PrmsPaymentRequisition.findByReqPaymentNo");
        query.setParameter("reqPaymentNo", refNo_);
        try {
            PrmsPaymentRequisition paymentRequisition = (PrmsPaymentRequisition) query.getResultList().get(0);
            return paymentRequisition;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsQuotation> getProformaNo(int approvedStatus) {
        System.err.println("proforma list where purchase order Status is---" + approvedStatus);
//        Query qu = em.createNamedQuery("PrmsQuotation.findByApprovedStatus");
        Query qu = em.createNativeQuery("SELECT * FROM prms_quotation quo\n"
                + "where quo.quatation_id in\n"
                + "(SELECT po.quotation_id FROM prms_purchase_order po \n"
                + "where po.status='" + approvedStatus + "')", PrmsQuotation.class);
//qu.setParameter("quatationId", prmsQuotation.getQuatationId());
        try {
            List<PrmsQuotation> quatationNo = new ArrayList<>();
            if (qu.getResultList().size() > 0) {
                quatationNo = qu.getResultList();
            }
            return quatationNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsContract> getContractNo(PrmsBid prmsBid, int status) {
        System.out.println("prmsBid---- FCD " + prmsBid);
        Query q = em.createNamedQuery("PrmsContract.findByBidIdAndStatus");
        q.setParameter("bidId", prmsBid);
        q.setParameter("status", status);
        try {
//            PrmsBid contractNo = null;
            ArrayList<PrmsContract> contractNos = null;
            if (q.getResultList().size() > 0) {
                contractNos = new ArrayList<>(q.getResultList());
            }
            return contractNos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseOrder> getPONumberList(PrmsQuotation prmsQuotation, int status) {
        System.out.println("----on PRMSQoutation---FCD--");
        Query qu = em.createNamedQuery("PrmsPurchaseOrder.findByQuotationIdAndStatus");
        qu.setParameter("quotationId", prmsQuotation);
        qu.setParameter("status", status);
        try {
            ArrayList<PrmsPurchaseOrder> POLists = null;
            if (qu.getResultList().size() > 0) {
                POLists = new ArrayList<>(qu.getResultList());
            }
            return POLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidDetail> getPRNumbers(PrmsBid prmsBid) {
        System.out.println("---Fcd Faces====" + prmsBid);
        Query ry = em.createNamedQuery("PrmsBidDetail.findByPRID");
        ry.setParameter("prId", prmsBid);
        try {
            ArrayList<PrmsBidDetail> requestNo = null;
            if (ry.getResultList().size() > 0) {
                requestNo = new ArrayList<>(ry.getResultList());

            }
            return requestNo;
        } catch (Exception xe) {
            xe.printStackTrace();
            return null;
        }
    }

    public List<MmsStoreInformation> getAllStoreName() {
        System.out.println("---Facade face Store name---");
        Query que = em.createNamedQuery("MmsStoreInformation.findAll");
        try {
            List<MmsStoreInformation> storeName = new ArrayList<>(que.getResultList());
            System.out.println("Names size ****** " + storeName.size());
            return storeName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsGrn> getGrnNoByStoreId(MmsStoreInformation mmsStoreInformation) {
        System.out.println("-----Facade Erea====");
        Query query = em.createNamedQuery("MmsGrn.findByStoreId");
        query.setParameter("storeId", mmsStoreInformation);
        System.out.println("selected Store id=====" + mmsStoreInformation.getStoreId());

        try {
            List<MmsGrn> grnNoLists = null;
            if (query.getResultList().size() > 0) {
                grnNoLists = new ArrayList<>(query.getResultList());
                System.out.println("no of Grns====" + grnNoLists.size());
            }
            return grnNoLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsGrn> getPoNoByGrnNo(MmsGrn mmsGrn) {
        System.out.println("==Here Facade to populate PO No==");
        Query query = em.createNamedQuery("MmsGrn.findByPoInspectioId");
//        System.out.println("hello insp   " + mmsGrn.getInspectionId().getPurchaseOId());
//        query.setParameter("purchaseOId", mmsGrn.getInspectionId().getPurchaseOId());
        System.out.println("Selected Grn Id------->" + mmsGrn.getGrnId());
//        System.out.println(" " + mmsGrn.getInspectionId());

        try {
            List<MmsGrn> PONoLists = null;
            if (query.getResultList().size() > 0) {
                PONoLists = new ArrayList<>(query.getResultList());
                System.out.println("List of Po Nos=====" + PONoLists.size());
            }
            return PONoLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsBid> getBidContractedOnly(int approvedStatus) {
        System.out.println("list contracted Bidders wwhere contract Status is ---" + approvedStatus);
        Query que = em.createNativeQuery("SELECT * FROM prms_bid where id in \n"
                + "(SELECT bid_id FROM prms_contract where status='" + approvedStatus + "')", PrmsBid.class);
//        que.setParameter("bidId",PrmsContract.class);
        System.out.println("getted");
        try {
            List<PrmsBid> contractedbids = new ArrayList();
            if (que.getResultList().size() > 0) {
                contractedbids = new ArrayList<>(que.getResultList());
                System.out.println("in no===" + contractedbids.size());
            }
            return contractedbids;
        } catch (Exception sx) {
            sx.printStackTrace();
            return null;
        }
    }

    public List<MmsGrn> getGrnNo(MmsInspection mmsInspection) {
        System.out.println("so hre");
//        Query qu = em.createNamedQuery("SELECT m.* FROM mms_grn m\n"
//                + "inner join mms_inspection i\n"
//                + "on i.inspection_id=m.inspection_id\n"
//                + "where i.contract_id is  not NULL ");
        Query qu = em.createNamedQuery("MmsGrn.findByContractId");
        qu.setParameter("contractId", mmsInspection.getContractId());

        try {
            List<MmsGrn> grNo = new ArrayList<>();
            if (qu.getResultList().size() > 0) {
//                grNo = (MmsGrn) qu.getResultList().get(0);
                grNo = qu.getResultList();
                System.out.println("grn size===" + grNo.size());
            }
            return grNo;
        } catch (Exception e) {
            return null;
        }

    }

    public List<PrmsPaymentRequisition> getRequestsOnLists() {
        Query query = em.createNamedQuery("PrmsPaymentRequisition.findByRequestForApproval", PrmsPaymentRequisition.class);
        ArrayList<PrmsPaymentRequisition> paymentRequestList = new ArrayList(query.getResultList());
        return paymentRequestList;
    }

    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid) {
        System.out.println("search bid id " + prmsBid.getId() + " from bid amend");
        Query query = em.createNativeQuery("SELECT * FROM prms_bid_amend bdamd\n"
                + "inner join(select bid_id , max(id) as maxAmendId \n"
                + "FROM prms_bid_amend \n"
                + "GROUP BY bid_id) bdamd2\n"
                + "on bdamd.id=bdamd2.maxAmendId\n"
                + "inner join prms_bid bd\n"
                + "on bd.id=bdamd.bid_id\n"
                + "where bd.id ='" + prmsBid.getId() + "'", PrmsBidAmend.class);
        System.out.println("--- Size no -----" + query.getResultList().size());
//        query.setParameter("", prmsBid.getId());
        try {
            List<PrmsBidAmend> bidAmend = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                bidAmend = query.getResultList();
            }
            return bidAmend;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend) {
        System.out.println("query to get bidAmendement where Bid Id==== " + prmsBidAmend.getBidId());
        Query query = em.createNativeQuery("SELECT * FROM prms_bid_amend bdamd\n"
                + "inner join(SELECT bdamd2.bid_id, max(bdamd2.id) as maxAmendId FROM prms_bid_amend bdamd2\n"
                + "group by bdamd2.bid_id) maxId\n"
                + "on bdamd.id=maxId.maxAmendId\n"
                + "where bdamd.bid_id='" + prmsBidAmend.getBidId().getId() + "'", PrmsBidAmend.class);
        try {
            System.out.println("here try by Bid Id " + prmsBidAmend.getBidId().getId());
            PrmsBidAmend bidAmendInfo = new PrmsBidAmend();
            if (query.getResultList().size() > 0) {
                bidAmendInfo = (PrmsBidAmend) query.getResultList().get(0);
                System.out.println("bid Type " + bidAmendInfo.getBidType() + " at " + bidAmendInfo.getId() + " bidAmendment MaxID");
                System.out.println("Amend Status--- " + bidAmendInfo.getStatus());
            }
            return bidAmendInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsContractAmendment> checkContractIdFromAmended(PrmsContract prmsContract) {
        System.out.println("cont id --" + prmsContract.getContractId());
//        Query query = em.createNamedQuery("PrmsContractAmendment.findByContractId");
//        query.setParameter("contractId", prmsContract.getContractId());
        Query query = em.createNativeQuery("SELECT * FROM prms_contract_amendment contamd\n"
                + "inner join(select contract_id, max (contract_amend_id) as maxContAmendId \n"
                + "from prms_contract_amendment \n"
                + "group by contract_id)contamd2\n"
                + "on contamd.contract_amend_id=contamd2.maxContAmendId\n"
                + "inner join prms_contract cont\n"
                + "on cont.contract_id=contamd.contract_id\n"
                + "where cont.contract_id='" + prmsContract.getContractId() + "'");
        try {
            List<PrmsContractAmendment> amendedContractList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                amendedContractList = query.getResultList();
                System.out.println("size -- " + amendedContractList.size());
            }
            return amendedContractList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsContractAmendment getContractAmendedInfoByContractId(PrmsContract prmsContract) {
        System.out.println("when cont id --- " + prmsContract.getContractId());
        Query q = em.createNativeQuery("SELECT * FROM prms_contract_amendment contamd\n"
                + "inner join(select contract_id, max(contract_amend_id) as maxContAmendId\n"
                + "from prms_contract_amendment \n"
                + "group by contract_id) contamd2\n"
                + "on contamd.contract_amend_id=contamd2.maxContAmendId\n"
                + "where contamd.contract_id = '" + prmsContract.getContractId() + "'", PrmsContractAmendment.class);
        try {
            PrmsContractAmendment contractAmendedInfo = new PrmsContractAmendment();
            if (q.getResultList().size() > 0) {
                contractAmendedInfo = (PrmsContractAmendment) q.getResultList().get(0);
                System.out.println("Supplier Id from Amended Cntract " + contractAmendedInfo.getSuppId());
            }
            return contractAmendedInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsContractCurrencyDetail> getContractAmoutList(PrmsContract prmsContract) {
        System.out.println("where i'm contract id " + prmsContract.getContractId());
        Query query = em.createNativeQuery("SELECT * FROM prms_contract_currency_detail conCurDet\n"
                + "inner join prms_contract cont\n"
                + "on concurdet.contract_id=cont.contract_id\n"
                + "where concurdet.contract_id='" + prmsContract.getContractId() + "'", PrmsContractCurrencyDetail.class);
        try {
            List<PrmsContractCurrencyDetail> contAmoutList = null;
            if (query.getResultList().size() > 0) {
                contAmoutList = query.getResultList();
                System.out.println("List of Amt in Numbr == " + contAmoutList.size());
            }
            return contAmoutList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAmoutList(PrmsContract prmsContract) {
        Query q = em.createNativeQuery("SELECT contcurrdet.amount FROM prms_contract_currency_detail contCurrDet\n"
                + "where contcurrdet.contract_id='" + prmsContract.getContractId() + "'");
        try {
            List<String> contAmounts = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                contAmounts = q.getResultList();
                System.out.println("lsts " + contAmounts.size());
            }
            return contAmounts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsPaymentRequisition> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns \n"
                + "where table_name=UPPER('PRMS_PAYMENT_REQUISITION')\n"
                + "and column_name not in ('PAYMENT_REQ_ID','BANK_ID','BID_ID','BUDGET_TITLE','BUGET_YEAR',\n"
                + "'CONTRACT_ID','DEP_ID','GRN_ID','PO_ID','QUATATION_ID','RQUEST_ID','SUPP_ID','STATUS','PREPARED_REMARK') order by column_name ASC", PrmsPaymentRequisition.class);
        try {
            List<PrmsPaymentRequisition> colNameLists = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                colNameLists = query.getResultList();
            }
            return colNameLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
