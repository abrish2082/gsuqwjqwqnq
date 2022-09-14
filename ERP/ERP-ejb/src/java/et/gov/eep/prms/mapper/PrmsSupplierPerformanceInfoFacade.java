/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSupplierPerformanceInfo;
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
public class PrmsSupplierPerformanceInfoFacade extends AbstractFacade<PrmsSupplierPerformanceInfo> {

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
    public PrmsSupplierPerformanceInfoFacade() {
        super(PrmsSupplierPerformanceInfo.class);
    }

    /**
     *
     * @param prmsSupplierPerformanceInfo
     * @return
     */
    public ArrayList<PrmsSupplierPerformanceInfo> searchRequestForPayment(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        Query query = em.createNamedQuery("PrmsPaymentRequisition.SearchById");
        query.setParameter("suppInfoId", prmsSupplierPerformanceInfo.getSuppInfoId().toLowerCase() + '%');
        try {

            ArrayList<PrmsSupplierPerformanceInfo> ServiceProvideList = new ArrayList(query.getResultList());
            return ServiceProvideList;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param prmsSupplierPerformanceInfo
     * @return
     */
    public PrmsSupplierPerformanceInfo getRequestNo(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        Query query = em.createNamedQuery("PrmsSupplierPerformanceInfo.findBySuppInfoId");
        query.setParameter("suppInfoId", prmsSupplierPerformanceInfo.getSuppInfoId());
        try {
            PrmsSupplierPerformanceInfo selectedobj = (PrmsSupplierPerformanceInfo) query.getResultList().get(0);
            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public ArrayList<PrmsPurchaseOrder> purchaseOrderList() {
        Query query = em.createNamedQuery("PrmsPurchaseOrder.findAlls");
        try {
            ArrayList<PrmsPurchaseOrder> supplier = new ArrayList(query.getResultList());

            return supplier;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsSupplierPerformanceInfo> searchCheckList(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
         if (prmsSupplierPerformanceInfo.getColumnValue() != null && prmsSupplierPerformanceInfo.getColumnName() != null
                && !prmsSupplierPerformanceInfo.getColumnValue().equals("") && !prmsSupplierPerformanceInfo.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_SUPPLIER_PERFORMANCE_INFO\n"
                    + "where " + prmsSupplierPerformanceInfo.getColumnName().toLowerCase() + " = '" + prmsSupplierPerformanceInfo.getColumnValue() + "' "
                    + "and " + prmsSupplierPerformanceInfo.getPreparedBy()+ "='" + prmsSupplierPerformanceInfo.getPreparedBy()+ "' ", PrmsSupplierPerformanceInfo.class);
            try {
                List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoLst = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    prmsSupplierPerformanceInfoLst = query.getResultList();
                   
                }
                return prmsSupplierPerformanceInfoLst;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsSupplierPerformanceInfo.findByPreparedBy");
            query.setParameter("preparedBy", prmsSupplierPerformanceInfo.getPreparedBy());
            return query.getResultList();
        }
    }

    public PrmsSupplierPerformanceInfo getSelectedRequest(String id) {
        Query query = em.createNamedQuery("PrmsSupplierPerformanceInfo.findBySuppInfoId");
        query.setParameter("suppInfoId", id);
        try {
            PrmsSupplierPerformanceInfo selectrequest = (PrmsSupplierPerformanceInfo) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSupplierPerformanceInfo getLastPaymentNo() {
        Query query = em.createNamedQuery("PrmsSupplierPerformanceInfo.findByMaxSupplierNum");
        try {
            PrmsSupplierPerformanceInfo directPurcObj = (PrmsSupplierPerformanceInfo) query.getResultList().get(0);
            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSupplierPerformanceInfo getLastPerformanceId() {
        Query query1 = em.createNamedQuery("PrmsSupplierPerformanceInfo.findByMaxSupplierNum");
        PrmsSupplierPerformanceInfo result = null;
        try {
            if (query1.getResultList().size() > 0) {
                result = (PrmsSupplierPerformanceInfo) query1.getResultList().get(0);
            } else {
                return result;
            }
            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsQuotationDetail> getQuotationList(String QuotationNo) {
        List<PrmsQuotationDetail> quotationList = null;
        try {
            Query query = em.createNamedQuery("PrmsQuotationDetail.findByQuotationNo", PrmsQuotationDetail.class);
            query.setParameter("quotationNo", QuotationNo);
            quotationList = (List<PrmsQuotationDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotationList;

    }

    public List<PrmsPurchaseOrder> getPoNumberList(PrmsContract prmsCotract) {
        Query qu = em.createNamedQuery("PrmsPurchaseOrder.findByContactId");
        qu.setParameter("contractId", prmsCotract);
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

    public List<PrmsContract> getContratList2(PrmsBid prmsBid) {
        Query qu = em.createNamedQuery("PrmsContract.findByBidId");
        qu.setParameter("bidId", prmsBid);
        try {
            ArrayList<PrmsContract> POLists = null;
            if (qu.getResultList().size() > 0) {
                POLists = new ArrayList<>(qu.getResultList());
            }
            return POLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public List<PrmsSupplierPerformanceInfo> getSupPerfRqLists() {
//        Query query = em.createNamedQuery("PrmsSupplierPerformanceInfo.findBySuppInfoId");
//        ArrayList<PrmsSupplierPerformanceInfo> SupPerfList = new ArrayList<>(query.getResultList());
//        return SupPerfList;
//    }
    public List<PrmsSupplierPerformanceInfo> getPerformanceLst() {
        Query query = em.createNamedQuery("PrmsSupplierPerformanceInfo.findByReqForApproval");
        ArrayList<PrmsSupplierPerformanceInfo> Perflst = new ArrayList<>(query.getResultList());
        return Perflst;
    }

    public List<PrmsSupplierPerformanceInfo> getPerformNos(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        Query query = em.createNamedQuery("PrmsLcRigistration.findBySupPerfNos", PrmsSupplierPerformanceInfo.class);
        query.setParameter("supplierPerfoNo", prmsSupplierPerformanceInfo.getSupplierPerfoNo() + '%');

        try {
            List<PrmsSupplierPerformanceInfo> marketAssessmentLst = new ArrayList(query.getResultList());
            return marketAssessmentLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContract getOthersByContractId(PrmsContract prmsCotract) {
        Query q = em.createNamedQuery("PrmsContract.findByContractId");
        q.setParameter("contractId", prmsCotract.getContractId());
        try {
            PrmsContract contractInfo = new PrmsContract();
            if (q.getResultList().size() > 0) {
                contractInfo = (PrmsContract) q.getResultList().get(0);
            }
            return contractInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract) {
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

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract) {
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

    public List<PrmsSupplierPerformanceInfo> getSupplierPerformanceNo(String prefix, String currentYear) {
        Query query = em.createNamedQuery("PrmsSupplierPerformanceInfo.findBySuppInfoId", PrmsSupplierPerformanceInfo.class);
        query.setParameter("suppInfoId", prefix + "-" + '%' + "/" + currentYear);
        try {
            List<PrmsSupplierPerformanceInfo> supplierPerformanceList = (List<PrmsSupplierPerformanceInfo>) (query.getResultList());
            return supplierPerformanceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsSupplierPerformanceInfo> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_SUPPLIER_PERFORMANCE_INFO') \n"
                + "and column_name not in ('SUPP_INFO_ID','DELIVERY_WRT_TIME','REMARK_WRT_CONDITION','USER_DEPT_COMMENT','CLAIM_ANSWERING','PURCHASE_ITEM_DESCRIBITON','SUPP_ID','PO_ID','BID_ID','CONTRACT_ID','QUOTATION_ID','CLAIM_DESCRIPTION','UNDELIVERED_BALANCE','CLAIM_ID','LC_ID','BID_NO','STATUS','GOODS_ID')");
        try {
            List<PrmsSupplierPerformanceInfo> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
