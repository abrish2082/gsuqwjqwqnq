/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsAward;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;

/**
 *
 * @author user
 */
@Stateless
public class PrmsPurchaseOrderFacade extends AbstractFacade<PrmsPurchaseOrder> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsPurchaseOrderFacade() {
        super(PrmsPurchaseOrder.class);
    }

    public ArrayList<PrmsPurchaseOrder> searchPurchaseOrderByPoNo(PrmsPurchaseOrder purchase) {

        Query query = em.createNamedQuery("PrmsPurchaseOrder.SearchfindByPacNo");
        query.setParameter("pacNo", purchase.getPacNo() + '%');

        try {
            ArrayList<PrmsPurchaseOrder> PurchaseOrderList = new ArrayList(query.getResultList());
            return PurchaseOrderList;
        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsPurchaseOrder getPoId(PrmsPurchaseOrder papmsPurchaseOrder) {
        Query query = em.createNamedQuery("PrmsPurchaseOrder.findByPacNo");
        query.setParameter("pacNo", papmsPurchaseOrder.getPacNo());
        try {
            PrmsPurchaseOrder selectedobj = (PrmsPurchaseOrder) query.getResultList().get(0);

            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getApprovedWithBidPurchaseTypePRList(String status) {
        List<String> awardList = null;
        try {
            Query query = em.createNamedQuery("PapmsAward.findByStatus");
            query.setParameter("status", status);
            awardList = (List<String>) query.getResultList();

            return awardList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param papmsAward
     * @return
     */
    public PrmsAward SearchAwardByAwardId(String papmsAward) {
        Query query = em.createNamedQuery("PapmsAward.findByAwardId");
        query.setParameter("awardId", papmsAward);
        try {
            PrmsAward PurchaseOrderNo = (PrmsAward) query.getResultList().get(0);
            return PurchaseOrderNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsAward> getApprovedWithBidPurchaseTypePRList(PrmsAward papmsAward) {

        Query query = em.createNamedQuery("PapmsAward.findByStatus");

        query.setParameter("status", papmsAward.getStatus());

        try {

            ArrayList<PrmsAward> locationInformations = new ArrayList(query.getResultList());

            return locationInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsAward> getInspectionListByStatus(PrmsAward papmsAward) {
        Query query = em.createNamedQuery("PapmsAward.findByAwardId");
        query.setParameter("awardId", papmsAward.getAwardId());
        try {
            ArrayList<PrmsAward> inspectionReqList = new ArrayList(query.getResultList());
            return inspectionReqList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsAward> getAwardLists() {

        List<PrmsAward> budgetYrsLst = null;
        try {
            Query query = em.createNamedQuery("PrmsAward.findAll", PrmsAward.class);

            budgetYrsLst = (List<PrmsAward>) query.getResultList();
            System.out.println("out facade" + budgetYrsLst.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return budgetYrsLst;
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public ArrayList<HrDepartments> searchdeptName() {

        Query query = em.createNamedQuery("HrDepartments.findAll", HrDepartments.class);

        try {
            ArrayList<HrDepartments> departmentName = new ArrayList(query.getResultList());;

            return departmentName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public ArrayList<PrmsBid> bidNumberList() {
        Query query = em.createNamedQuery("PrmsBid.findAlls");
        try {
            ArrayList<PrmsBid> supplier = new ArrayList(query.getResultList());
            return supplier;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param prmsPurchaseOrder
     * @return
     * ************************************************************************
     */
    public ArrayList<PrmsPurchaseOrder> searchPurchaseOrder(
            PrmsPurchaseOrder prmsPurchaseOrder) {

        System.out.println("Purchase Order Search !!! 2");
        Query query = em.createNamedQuery("PrmsPurchaseOrder.findByPoId");
        query.setParameter("poId", prmsPurchaseOrder.getPoId() + "%");
        query.setParameter("requestedBy", prmsPurchaseOrder.getRequestedBy());

        try {
            ArrayList<PrmsPurchaseOrder> PurchaseOrderList
                    = new ArrayList(query.getResultList());
            System.out.println("3=====" + PurchaseOrderList.size());

            return PurchaseOrderList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param qa
     * @return
     * ************************************************************************
     */
    public List<PrmsPurchaseOrder> findApprovedPoNumbers(int qa) {

        ArrayList<PrmsPurchaseOrder> result = null;
        Query query = em.createNamedQuery("PrmsPurchaseOrder.findAll", PrmsPurchaseOrder.class);
//Query query = em.createNamedQuery("PrmsPurchaseOrder.finAllWithzero", PrmsPurchaseOrder.class);
        //query.setParameter("approvedStatus", qa);

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<PrmsPurchaseOrder> findPreparedPoNumbers() {

        ArrayList<PrmsPurchaseOrder> result = null;
        Query query = em.createNamedQuery("PrmsPurchaseOrder.findAllbyStatus",
                PrmsPurchaseOrder.class);

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param papmsPurchaseOrder
     * @return
     * ************************************************************************
     */
    public List<PrmsPurchaseOrder> searchPurchaseOrderNo(
            PrmsPurchaseOrder papmsPurchaseOrder) {

        Query query = em.createNamedQuery("PrmsPurchaseOrder.SearchfindByPacNo");
        query.setParameter("pacNo", papmsPurchaseOrder.getPacNo() + '%');
        query.setParameter("requestedBy", papmsPurchaseOrder.getRequestedBy());
        ArrayList<PrmsPurchaseOrder> result = null;

        try {
            if (query.getResultList().size() > 0) {

                result = new ArrayList(query.getResultList());
                System.out.println("result====" + result.size());

                return result;
            }

            return null;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseOrder> searchPurchaseOrderNo(int status, int UserId) {
        Query query = em.createNativeQuery("select * from PRMS_PURCHASE_ORDER dr INNER JOIN WF_PRMS_PROCESSED wf on dr.PO_ID=wf.PURCHASE_ORDER_ID "
                + "where dr.status='" + status + "' " + "and wf.PROCESSED_BY='" + UserId + "' ", PrmsPurchaseOrder.class);
        ArrayList<PrmsPurchaseOrder> purchasePlanLst = new ArrayList<>(query.getResultList());

        return purchasePlanLst;
    }

//        try {
//            PrmsPurchaseOrder PurchaseOrderNo = (PrmsPurchaseOrder) query.getResultList().get(0);
//            return PurchaseOrderNo;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
    /**
     * *************************************************************************
     *
     * @return
     * ******************************************************************************
     */
    public PrmsPurchaseOrder getLastPaymentNo() {

        Query query = em.createNamedQuery("PrmsPurchaseOrder.findByMaxBidOfferNum");

        try {
            PrmsPurchaseOrder directPurcObj = (PrmsPurchaseOrder) query.getResultList().get(0);

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param id
     * @return
     * ************************************************************************
     */
    public PrmsPurchaseOrder getSelectedRequest(String id) {

        Query query = em.createNamedQuery("PrmsPurchaseOrder.findByPoId");
        query.setParameter("poId", id);

        try {
            PrmsPurchaseOrder selectrequest = (PrmsPurchaseOrder) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBid> BidNoFormContract() {

        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "PRMS_BID.REF_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_CONTRACT\n"
                + "ON PRMS_BID.ID = PRMS_CONTRACT.BID_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsQuotation> quotationFromResult() {

        Query query = em.createNativeQuery("SELECT * FROM PRMS_QUOTATION", PrmsQuotation.class);
        try {
            List<PrmsQuotation> supplier = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                supplier = query.getResultList();
            }

            System.err.println("hhhh" + supplier.size());

            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromQuotation(String quotationNo) {

        System.out.println("______" + quotationNo);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_QUOTATION_DETAIL.SUP_ID\n"
                + "INNER JOIN PRMS_QUOTATION\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "WHERE PRMS_QUOTATION.QUOTATION_NO= '" + quotationNo + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsFinancialEvlResultyDtl> getSupplierName(PrmsSupplyProfile eepVendorReg) {

        System.out.println("______" + eepVendorReg.getVendorName());
        Query query = em.createNativeQuery("SELECT MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  PRMS_QUOTATION_DETAIL.QUAT_DET_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUANTITY,\n"
                + "  PRMS_QUOTATION_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_QUOTATION\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_FINANCIAL_EVAL_RESULT.QUOTATION_ID\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID  = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "AND MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_QUOTATION_DETAIL.ITEM_REG_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID  = PRMS_FINANCIAL_EVL_RESULTY_DTL.SUPPLIER_ID\n"
                + "AND PRMS_SUPPLY_PROFILE.ID = PRMS_QUOTATION_DETAIL.SUP_ID\n"
                + "where PRMS_SUPPLY_PROFILE.VENDOR_NAME='" + eepVendorReg.getVendorName() + "'", PrmsFinancialEvlResultyDtl.class);
        try {
            ArrayList<PrmsFinancialEvlResultyDtl> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param bidType
     * @return
     * ************************************************************************
     */
    public PrmsBid getBidTyp(String bidType) {

        System.out.println("______" + bidType);
        PrmsBid bidTy = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.BID_TYPE,\n"
                + "  PRMS_BID.BID_CATEGORY\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_CONTRACT\n"
                + "ON PRMS_BID.ID = PRMS_CONTRACT.BID_ID\n"
                + "where PRMS_BID.REF_NO='" + bidType + "'", PrmsBid.class);
        try {
            if (query.getResultList().size() > 0) {
                bidTy = (PrmsBid) query.getResultList().get(0);
            }
            System.out.println("**********===" + bidTy);
            return bidTy;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getPrNum(String prmsQuotation) {
        System.out.println("____++++++++++__" + prmsQuotation);
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  HR_DEPARTMENTS.DEP_NAME,\n"
                + "  HR_DEPARTMENTS.DEP_ID\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST_DET\n"
                + "ON PRMS_PURCHASE_REQUEST_DET.ID = PRMS_QUOTATION_DETAIL.PURCHASE_RQUEST_DET_ID\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID\n"
                + "INNER JOIN HR_DEPARTMENTS\n"
                + "ON HR_DEPARTMENTS.DEP_ID = PRMS_PURCHASE_REQUEST.REQSTR_DEP_ID\n"
                + "where prms_quotation.quotation_no='" + prmsQuotation + "'", PrmsPurchaseRequest.class);
        try {
            ArrayList<PrmsPurchaseRequest> prNumber = new ArrayList<>();
            prNumber = new ArrayList<>(query.getResultList());
            System.err.println("*************====" + prNumber.size());
            return prNumber;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrDepartments> getDeptName(String depName) {
        System.out.println("y******" + depName);
        Query query = em.createNativeQuery("SELECT HR_DEPARTMENTS.DEP_ID,\n"
                + "  HR_DEPARTMENTS.DEP_NAME,\n"
                + " PRMS_PURCHASE_REQUEST.ID\n"
                + "FROM HR_DEPARTMENTS\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON HR_DEPARTMENTS.DEP_ID = PRMS_PURCHASE_REQUEST.REQSTR_DEP_ID\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST_DET\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_PURCHASE_REQUEST_DET.ID = PRMS_QUOTATION_DETAIL.PURCHASE_RQUEST_DET_ID\n"
                + "INNER JOIN PRMS_QUOTATION\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "where prms_quotation.quotation_no='" + depName + "'", HrDepartments.class);
        try {
            List<HrDepartments> depNameList = new ArrayList<>();
            depNameList = new ArrayList<>(query.getResultList());
            System.err.println("*******+++******====" + depNameList.size());
            return depNameList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContractDetail> getItemName(BigDecimal itemName) {
        System.out.println("y******" + itemName);
        Query query = em.createNativeQuery("SELECT PRMS_BID.REF_NO,\n"
                + "  PRMS_CONTRACT.CONTRACT_ID,\n"
                + "  PRMS_CONTRACT.CONTRACT_NO,\n"
                + "  PRMS_CONTRACT_DETAIL.QUANTITY,\n"
                + "  PRMS_CONTRACT_DETAIL.TOTAL_PRICE,\n"
                + "  PRMS_CONTRACT_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_CONTRACT_DETAIL.ITEM_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  PRMS_CONTRACT_DETAIL.CONTRACT_DETAIL_ID,\n"
                + "  PRMS_LU_VAT_TYPE_LOOKUP.VAT_RATE,\n"
                + "  PRMS_CONTRACT.SUPP_ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_CONTRACT\n"
                + "ON PRMS_BID.ID = PRMS_CONTRACT.BID_ID\n"
                + "INNER JOIN PRMS_CONTRACT_DETAIL\n"
                + "ON PRMS_CONTRACT.CONTRACT_ID = PRMS_CONTRACT_DETAIL.CONTRACT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_CONTRACT_DETAIL.ITEM_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_CONTRACT.SUPP_ID\n"
                + "INNER JOIN PRMS_LU_VAT_TYPE_LOOKUP\n"
                + "ON PRMS_LU_VAT_TYPE_LOOKUP.VAT_TYPE_ID = PRMS_SUPPLY_PROFILE.VAT_TYPE_ID\n"
                + "where PRMS_CONTRACT.CONTRACT_ID='" + itemName + "'", PrmsContractDetail.class);
        try {
            List<PrmsContractDetail> itemList = new ArrayList<>();
            itemList = new ArrayList<>(query.getResultList());
            System.err.println("------- [ ITEM NAME IS ] ----====>>>" + itemName);
            System.err.println("*******+++******====" + itemList.size());
            return itemList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<PrmsPurchaseOrder> getPOReauested() {

        Query query = em.createNamedQuery("PrmsPurchaseOrder.findAll", PrmsPurchaseOrder.class);
        ArrayList<PrmsPurchaseOrder> pOReauestlists = new ArrayList<>(query.getResultList());

        return pOReauestlists;
    }

    /**
     * *************************************************************************
     *
     * @param status
     * @return
     * ************************************************************************
     */
    public List<PrmsPurchaseOrder> findPurchaseOrdersListByWfStatus(int status) {

        Query query = em.createNamedQuery("PrmsPurchaseOrder.findByStatus", PrmsPurchaseOrder.class);
        query.setParameter("status", status);

        try {
            ArrayList<PrmsPurchaseOrder> listofPurchaseOrders = new ArrayList(query.getResultList());
            return listofPurchaseOrders;
        } catch (Exception e) {
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

    public List<PrmsPurchaseOrder> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_PURCHASE_ORDER') \n"
                + "and column_name not in ('purchaseOId','CURRENCY','REMARK','AWARD_ID','BID_ID','SUPP_ID','DEP_ID','CONTRACT_ID','PAYMENT_REQ_ID','PURCHASE_REQUEST_ID','APPROVED_STATUS','QUOTATION_ID','WITHHOLD','STATUS')");
        try {
            List<PrmsPurchaseOrder> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
