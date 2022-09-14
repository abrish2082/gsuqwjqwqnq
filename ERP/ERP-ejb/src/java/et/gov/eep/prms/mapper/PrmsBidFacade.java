/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.pms.entity.PmsResources;
import et.gov.eep.prms.entity.PrmsAnnualPlanService;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidCriteria;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.prms.entity.PrmsPurchasePlnDetail;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.math.BigDecimal;
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
public class PrmsBidFacade extends AbstractFacade<PrmsBid> {

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
    public PrmsBidFacade() {
        super(PrmsBid.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named Queries">
    /**
     *
     * @param eepBidReg
     * @return
     */
    public ArrayList<PrmsBid> getBidRefc(PrmsBid eepBidReg) {

        Query query = em.createNamedQuery("PrmsBid.findByRefNo");
        query.setParameter("refNo", eepBidReg.getRefNo() + '%');
        try {
            ArrayList<PrmsBid> eepBidRegList = new ArrayList(query.getResultList());

            return eepBidRegList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBid getBidRe(PrmsBid eepBidReg) {

        Query query = em.createNamedQuery("PrmsBid.findByRefNos");
        query.setParameter("refNo", eepBidReg.getRefNo());
        try {
            PrmsBid eepBidRegList = (PrmsBid) (query.getResultList().get(0));

            return eepBidRegList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBidDetail> getLotNum() {
        Query query = em.createNamedQuery("PrmsBidDetail.findAll");
        try {
            ArrayList<PrmsBidDetail> lotNo = new ArrayList<>(query.getResultList());
            return lotNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidDetail> getLotNo(String bidNo) {
        List<PrmsBidDetail> lotList = null;
        try {
            Query query = em.createNamedQuery("PrmsBidDetail.findByRefNo", PrmsBidDetail.class);
            query.setParameter("refNo", bidNo);
            lotList = (List<PrmsBidDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lotList;
    }

    public List<PrmsBidDetail> getBidListForLot(PrmsBid bidNo) {
        List<PrmsBidDetail> prmsBidDetails = null;
        try {
            Query query = em.createNamedQuery("PrmsBidDetail.findByBidNumber", PrmsBidDetail.class);
            query.setParameter("refNo", bidNo.getId());
            prmsBidDetails = (List<PrmsBidDetail>) query.getResultList();
            return prmsBidDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBid> bidNumberList() {
        Query query = em.createNamedQuery("PrmsBid.findAll");
        try {
            ArrayList<PrmsBid> supplier = new ArrayList(query.getResultList());
            return supplier;

        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param toString
     * @return
     */
    public ArrayList<PrmsBid> getByRefNo(String toString) {
        ArrayList<PrmsBid> deptJobs = null;
        try {
            Query query = em.createNamedQuery("PrmsBid.findByRefNo", PrmsBid.class);
            query.setParameter("refNo", toString);
            deptJobs = (ArrayList<PrmsBid>) query.getResultList().get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptJobs;
    }

    public ArrayList<MmsItemRegistration> getItemCodename(String toString) {
        ArrayList<MmsItemRegistration> itemRegistrationList = null;
        try {
            Query query = em.createNamedQuery("MmsItemRegistration.findByMatName", MmsItemRegistration.class);
            query.setParameter("matName", toString);
            itemRegistrationList = (ArrayList<MmsItemRegistration>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemRegistrationList;
    }

    public ArrayList<PrmsBid> getPrNo(String string) {

        ArrayList<PrmsBid> prNoLst = null;
        try {
            Query query = em.createNamedQuery("PrmsBid.findByPurchaseReqNo", PrmsBid.class);
            query.setParameter("prNo", string);
            prNoLst = (ArrayList<PrmsBid>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prNoLst;
    }

    public List<PrmsBid> getBidNo(PrmsBid prmsBid) {
        List<PrmsBid> prmsBidLst = new ArrayList();
        if (prmsBid.getColumnName() != null && !prmsBid.getColumnName().equals("")
                && prmsBid.getColumnValue() != null && !prmsBid.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_BID\n"
                    + "where " + prmsBid.getColumnName().toLowerCase() + " = '" + prmsBid.getColumnValue() + "'"
                    + "and " + prmsBid.getPreparedBy() + "='" + prmsBid.getPreparedBy() + "'", PrmsBid.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsBidLst = query.getResultList();
                }
                return prmsBidLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsBid.findByPreparedBy");
            query.setParameter("preparedBy", prmsBid.getPreparedBy());
            prmsBidLst = query.getResultList();
            return prmsBidLst;
        }
    }

    public List<PrmsBid> getBidOnList() {
        Query query = em.createNamedQuery("PrmsBid.findByBidReq", PrmsBid.class);
        ArrayList<PrmsBid> prmsBidList = new ArrayList(query.getResultList());
        return prmsBidList;
    }

    public List<PrmsBid> getGoods_Service_WorkBidSeqNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsBid.findByRefNoLike");
        query.setParameter("refNos", prefix + "-" + "%" + "/" + eYear);
        List<PrmsBid> bidLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            bidLists = query.getResultList();
        }
        return bidLists;
    }

    public List<PrmsAward> findbyID(PrmsBid eepBidReg) {
        try {
            Query query = em.createNamedQuery("PrmsAward.FINDBYFORIENID", PrmsAward.class);
            query.setParameter("sid", eepBidReg.getId());
            return (List<PrmsAward>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public PrmsServiceAndWorkReg findId(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByServAndWorkId");
        query.setParameter("servAndWorkId", prmsServiceAndWorkReg.getServAndWorkId());
        try {
            PrmsServiceAndWorkReg prmsBidSelectId = (PrmsServiceAndWorkReg) query.getResultList().get(0);
            return prmsBidSelectId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBidCriteria> getCriteriaLst() {
        Query query = em.createNamedQuery("PrmsBidCriteria.findAll");
        try {
            ArrayList<PrmsBidCriteria> prmsBidCriteriaLst = new ArrayList<>(query.getResultList());
            return prmsBidCriteriaLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBid getSelectedId(String id) {
        System.out.println("=====================================Preliminary list size=" + id);

        Query query = em.createNamedQuery("PrmsBid.findById");
        query.setParameter("id", id);
        try {
            PrmsBid prmsBidSelectId = (PrmsBid) query.getResultList().get(0);
            return prmsBidSelectId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<PrmsBidDetail> getByBidCode(Object newValue) {
        List<PrmsBidDetail> purchasePlnDetailLst = null;
        try {
            Query query = em.createNamedQuery("PrmsBidDetail.findByBidID", PrmsBidDetail.class);
            query.setParameter("bidId", newValue);
            purchasePlnDetailLst = (List<PrmsBidDetail>) query.getResultList();
            return purchasePlnDetailLst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchasePlnDetailLst;
    }

    public PrmsBid getBidNoAuto() {
        Query query = em.createNamedQuery("PrmsBid.findByBidNoAutoIncr");
        PrmsBid result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsBid) query.getResultList().get(0);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseReqService> getService(String serviceName) {
        List<PrmsPurchaseReqService> serviceLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseReqService.findByPrNo", PrmsPurchaseReqService.class);
            query.setParameter("prNum", serviceName);
            serviceLst = (List<PrmsPurchaseReqService>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceLst;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Native Queries">
    public List<PrmsPurchasePlan> getPlanNo() {
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_PURCHASE_PLN_DETAIL\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_PURCHASE_PLN_DETAIL.PURCHSE_PLAN_ID,\n"
                + "  MMS_ITEM_REGISTRATION\n"
                + "WHERE PRMS_PURCHASE_PLAN.STATUS = 3", PrmsPurchasePlan.class);
        try {
            ArrayList<PrmsPurchasePlan> prmsPurchasePlanNumLst = new ArrayList(query.getResultList());
            return prmsPurchasePlanNumLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> getItemCodefrmPlan(String itemcode) {
        System.err.println("item code from paln" + itemcode);
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME\n"
                + "FROM PRMS_PURCHASE_PLN_DETAIL\n"
                + "INNER JOIN PRMS_PURCHASE_PLAN\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_PURCHASE_PLN_DETAIL.PURCHSE_PLAN_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_PURCHASE_PLN_DETAIL.ITEM_CODE_ID\n"
                + "where PRMS_PURCHASE_PLAN.PLAN_NO='" + itemcode + "'", MmsItemRegistration.class);
        try {
            ArrayList<MmsItemRegistration> itemRegistrationLst = new ArrayList(query.getResultList());
            System.err.println("item code from paln size" + itemRegistrationLst.size());
            return itemRegistrationLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBid> BidNoFormBidSale() {
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.BID_OPEN_DATE_TIME,\n"
                + "  PRMS_BID.BID_CLOSE_DATE_TIME,\n"
                + "  PRMS_BID.BID_DOC_PRICE,\n"
                + "  PRMS_BID.BID_CATEGORY,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.SUPP_ID,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.BID_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN FMS_CASH_RECEIPT_VOUCHER\n"
                + "ON PRMS_BID.ID = FMS_CASH_RECEIPT_VOUCHER.BID_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = FMS_CASH_RECEIPT_VOUCHER.SUPP_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsBid> BidNoForCheckList() {
        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "PRMS_BID.REF_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_SUBMISSION\n"
                + "ON PRMS_BID.ID = PRMS_BID_SUBMISSION.BID_ID\n"
                + "INNER JOIN PRMS_BIDDER_REGISTRATION\n"
                + "ON PRMS_BIDDER_REGISTRATION.BIDDER_REG_ID = PRMS_BID_SUBMISSION.BIDDER_ID\n"
                + "AND PRMS_BID.ID= PRMS_BIDDER_REGISTRATION.BID_ID\n"
                + "WHERE PRMS_BIDDER_REGISTRATION.BID_ID= PRMS_BID_SUBMISSION.BID_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsBid> getBidNoListForCheckLIst() {
        List<PrmsBid> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.REF_NO ,\n"
                + "  PRMS_BID.ID,\n"
                + "  PRMS_BID_SUBMISSION.BID_SUB_ID,\n"
                + "  PRMS_BID_SUBMISSION.BID_ID,\n"
                + "  PRMS_BIDDER_REGISTRATION.BIDDER_REG_ID,\n"
                + "  PRMS_BIDDER_REGISTRATION.BID_ID AS BID_ID1\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_SUBMISSION\n"
                + "ON PRMS_BID.ID = PRMS_BID_SUBMISSION.BID_ID\n"
                + "INNER JOIN PRMS_BIDDER_REGISTRATION\n"
                + "ON PRMS_BIDDER_REGISTRATION.BIDDER_REG_ID = PRMS_BID_SUBMISSION.BIDDER_ID\n"
                + "AND PRMS_BID.ID = PRMS_BIDDER_REGISTRATION.BID_ID", PrmsBid.class);
        try {
            suppLierList = (List<PrmsBid>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public ArrayList<PrmsBid> BidNoForCheck() {
        Query query = em.createNativeQuery("SELECT DISTINCT  PRMS_BID.* \n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_SUBMISSION\n"
                + "ON PRMS_BID.ID = PRMS_BID_SUBMISSION.BID_ID\n"
                + "INNER JOIN PRMS_BID_SUBMISSION_DETAIL\n"
                + "ON PRMS_BID_SUBMISSION.BID_SUB_ID = PRMS_BID_SUBMISSION_DETAIL.BID_SUB_FID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsBid> BidNoForCheckListByBidId() {

        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "PRMS_BID.REF_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_SUBMISSION\n"
                + "ON PRMS_BID.ID = PRMS_BID_SUBMISSION.BID_ID\n"
                + "INNER JOIN PRMS_BIDDER_REGISTRATION\n"
                + "ON PRMS_BIDDER_REGISTRATION.BIDDER_REG_ID = PRMS_BID_SUBMISSION.BIDDER_ID\n"
                + "AND PRMS_BID.ID= PRMS_BIDDER_REGISTRATION.BID_ID\n"
                + "WHERE PRMS_BIDDER_REGISTRATION.BID_ID= PRMS_BID_SUBMISSION.BID_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsBid> getBidNoLists() {
        List<PrmsBid> suppLierList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.BID_TYPE,\n"
                + "  PRMS_BID.BID_CATEGORY,\n"
                + "  PRMS_BID.PURCHASE_METHD,\n"
                + "  PRMS_BID.BID_DOC_PRICE,\n"
                + "  PRMS_BID.BID_OPEN_DATE_TIME,\n"
                + "  PRMS_BID.BID_CLOSE_DATE_TIME\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BIDDER_REGISTRATION\n"
                + "ON PRMS_BID.ID = PRMS_BIDDER_REGISTRATION.BID_ID\n"
                + "INNER JOIN PRMS_BIDDER_REG_DETAIL\n"
                + "ON PRMS_BIDDER_REGISTRATION.BIDDER_REG_ID = PRMS_BIDDER_REG_DETAIL.BIDDER_REG_ID", PrmsBid.class);
        try {

            suppLierList = (List<PrmsBid>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public List<PrmsBid> getBidNoList() {
        List<PrmsBid> suppLierList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "WHERE PRMS_THECHNICAL_EVALUATION.STATUS = 3", PrmsBid.class);
        try {

            suppLierList = (List<PrmsBid>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public List<PrmsBid> getBidVocherList() {
        List<PrmsBid> suppLierList = null;
        Query query = em.createNativeQuery("SELECT * FROM PRMS_BID BID   WHERE NOT EXISTS"
                + "(SELECT CPV.REFERENCE_NUMBER FROM FMS_CASH_RECEIPT_VOUCHER CPV \n"
                + "WHERE BID.REF_NO=CPV.REFERENCE_NUMBER)", PrmsBid.class);
        try {

            suppLierList = (List<PrmsBid>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public List<MmsItemRegistration> getItemCod(String itemCode) {
        System.out.println("======item Code" + itemCode);
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "MMS_ITEM_REGISTRATION.MAT_NAME\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST_DET\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_PURCHASE_REQUEST_DET.MATERIAL_CODE_ID\n"
                + "where PRMS_PURCHASE_REQUEST.PR_NUMBER='" + itemCode + "'", MmsItemRegistration.class);
        try {
            ArrayList<MmsItemRegistration> mmsItemRegistrations = new ArrayList(query.getResultList());
            System.out.println("======item Code" + mmsItemRegistrations.size());
            return mmsItemRegistrations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsItemRegistration getItemName(MmsItemRegistration itemName) {
        System.out.println("======item Name---" + itemName);
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.UNIT_MEASURE_1\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST_DET\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID  = PRMS_PURCHASE_REQUEST_DET.MATERIAL_CODE_ID\n"
                + "where MMS_ITEM_REGISTRATION.MATERIAL_ID= '" + itemName.getMaterialId() + "'", MmsItemRegistration.class);
        try {
            MmsItemRegistration mmsItemRegistrationObjct = (MmsItemRegistration) (query.getResultList().get(0));
            System.out.println("======item Name====" + mmsItemRegistrationObjct.getMatName());
            return mmsItemRegistrationObjct;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseReqService> searchgetService() {
        Query query = em.createNativeQuery("SELECT distinct PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQ_SERVICE\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE = 'service'", PrmsPurchaseRequest.class);
        try {
            ArrayList<PrmsPurchaseReqService> purchaseRequestList = new ArrayList(query.getResultList());
            return purchaseRequestList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceAndWorkReg> getServiceFrmPr(String servicename) {
        System.out.println("in Noncons size=========" + servicename);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.REQ_QUANTITY,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME\n"
                + "FROM PRMS_PURCHASE_REQ_SERVICE\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "where PRMS_PURCHASE_REQUEST.PR_NUMBER='" + servicename + "'", PrmsServiceAndWorkReg.class);
        try {
            ArrayList<PrmsServiceAndWorkReg> prmsServiceAndWorkRegs = new ArrayList(query.getResultList());
            System.out.println("in Noncons size=========" + prmsServiceAndWorkRegs.size());
            return prmsServiceAndWorkRegs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceAndWorkReg> getServiceNofrConsultancy(String serviceNo) {
        System.out.println("in cons=========" + serviceNo);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME\n"
                + "FROM PRMS_PURCHASE_REQ_SERVICE\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "WHERE PRMS_PURCHASE_REQUEST.PR_NUMBER = '" + serviceNo + "'", PrmsServiceAndWorkReg.class);
        try {
            ArrayList<PrmsServiceAndWorkReg> prmsServiceAndWorkRegs = new ArrayList(query.getResultList());
            System.out.println("in cons size=========" + prmsServiceAndWorkRegs.size());
            return prmsServiceAndWorkRegs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceAndWorkReg getServiceNamefrNonCon(BigDecimal sericeName) {
        System.out.println("======Service Name---" + sericeName);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NO,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.REQ_QUANTITY\n"
                + "FROM PRMS_PURCHASE_REQ_SERVICE\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "where PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID='" + sericeName + "'", MmsItemRegistration.class);
        try {
            PrmsServiceAndWorkReg prmsServiceAndWorkRegObjct = (PrmsServiceAndWorkReg) (query.getResultList().get(0));
            System.out.println("======item Name====" + prmsServiceAndWorkRegObjct.getServiceName());
            return prmsServiceAndWorkRegObjct;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceAndWorkReg getServiceNamfrPr(PrmsServiceAndWorkReg ServiceName) {
        System.out.println("======serice Name---" + ServiceName);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.ID AS ID1,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.REQ_QUANTITY,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQ_SERVICE\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "where PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID='" + ServiceName.getServAndWorkId() + "'", PrmsServiceAndWorkReg.class);
        try {
            PrmsServiceAndWorkReg prmsServiceAndWorkRegObjct = (PrmsServiceAndWorkReg) (query.getResultList().get(0));
            System.out.println("======Service Name====" + prmsServiceAndWorkRegObjct.getServiceName());
            return prmsServiceAndWorkRegObjct;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getWork() {
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQ_SERVICE\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID      = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE = 'work'", PrmsPurchaseRequest.class);
        try {
            List<PrmsPurchaseRequest> prmsPurchaseRequests = new ArrayList(query.getResultList());
            System.out.println("======Service Name====" + prmsPurchaseRequests.size());
            return prmsPurchaseRequests;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceAndWorkReg> getworkNo(String workNo) {
        System.out.println("in work=========" + workNo);
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQ_SERVICE\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "where PRMS_PURCHASE_REQUEST.PR_NUMBER='" + workNo + "'", PrmsServiceAndWorkReg.class);
        try {
            ArrayList<PrmsServiceAndWorkReg> prmsServiceAndWorkRegs = new ArrayList(query.getResultList());
            System.out.println("in worksize=========" + prmsServiceAndWorkRegs.size());
            return prmsServiceAndWorkRegs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceAndWorkReg getWorkName(PrmsServiceAndWorkReg workName) {
        System.out.println("======serice Name---" + workName);
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.REQ_QUANTITY,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.ID AS ID1\n"
                + "FROM PRMS_PURCHASE_REQ_SERVICE\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "where PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID='" + workName.getServAndWorkId() + "'", PrmsServiceAndWorkReg.class);
        try {
            PrmsServiceAndWorkReg prmsServiceAndWorkRegObjct = (PrmsServiceAndWorkReg) (query.getResultList().get(0));
            System.out.println("======Service Name====" + prmsServiceAndWorkRegObjct.getWorkName());
            return prmsServiceAndWorkRegObjct;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsItemRegistration getItemNameFrmPlan(MmsItemRegistration itemId) {
        System.out.println("======serice Nameyyyyy---" + itemId.getMaterialId());
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_CODE,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.UNIT_MEASURE_1,\n"
                + "  PRMS_PURCHASE_PLN_DETAIL.QUANTITY\n"
                + "FROM PRMS_PURCHASE_PLN_DETAIL\n"
                + "INNER JOIN PRMS_PURCHASE_PLAN\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_PURCHASE_PLN_DETAIL.PURCHSE_PLAN_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_PURCHASE_PLN_DETAIL.ITEM_CODE_ID\n"
                + "where MMS_ITEM_REGISTRATION.MATERIAL_ID='" + itemId.getMaterialId() + "'", MmsItemRegistration.class);
        try {
            MmsItemRegistration itemRegistrationObject = (MmsItemRegistration) (query.getResultList().get(0));
            System.out.println("======Service Name====" + itemRegistrationObject.getMatName());
            return itemRegistrationObject;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchasePlan> getServicefrmPlanNo() {
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_ANNUAL_PLAN_SERVICE\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_ANNUAL_PLAN_SERVICE.PURCHSE_PLAN_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_ANNUAL_PLAN_SERVICE.SERVICE_ID", PrmsPurchasePlan.class);
        try {
            ArrayList<PrmsPurchasePlan> prmsPurchasePlanNumLst = new ArrayList(query.getResultList());
            return prmsPurchasePlanNumLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceAndWorkReg> getSericeNameFrmPlan(String sericeName) {
        System.err.println("==========in sevice Name" + sericeName);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_ANNUAL_PLAN_SERVICE\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_ANNUAL_PLAN_SERVICE.PURCHSE_PLAN_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_ANNUAL_PLAN_SERVICE.SERVICE_ID\n"
                + "where PRMS_PURCHASE_PLAN.PLAN_NO='" + sericeName + "' and PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE  = 'service'", PrmsServiceAndWorkReg.class);
        try {
            ArrayList<PrmsServiceAndWorkReg> sericeNameList = new ArrayList(query.getResultList());
            System.out.println("=======out Of Service Name" + sericeNameList.size());
            return sericeNameList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceAndWorkReg getUnitMeasure(PrmsServiceAndWorkReg serviceId) {
        System.out.println("======serice Name---" + serviceId);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO,\n"
                + "  PRMS_ANNUAL_PLAN_SERVICE.ID AS ID1,\n"
                + "  PRMS_ANNUAL_PLAN_SERVICE.QUANTITY,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_ANNUAL_PLAN_SERVICE\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_ANNUAL_PLAN_SERVICE.PURCHSE_PLAN_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_ANNUAL_PLAN_SERVICE.SERVICE_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID= '" + serviceId.getServAndWorkId() + "'", PrmsServiceAndWorkReg.class);
        try {
            PrmsServiceAndWorkReg prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) (query.getResultList().get(0));
            System.out.println("======Service Name====" + prmsServiceAndWorkReg.getUnitMeasures());
            return prmsServiceAndWorkReg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsAnnualPlanService getQuantity(PrmsServiceAndWorkReg seviceId) {
        System.out.println("======quuuu---" + seviceId);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO,\n"
                + "  PRMS_ANNUAL_PLAN_SERVICE.ID AS ID1,\n"
                + "  PRMS_ANNUAL_PLAN_SERVICE.QUANTITY,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_ANNUAL_PLAN_SERVICE\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_ANNUAL_PLAN_SERVICE.PURCHSE_PLAN_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_ANNUAL_PLAN_SERVICE.SERVICE_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID= '" + seviceId.getServAndWorkId() + "'", PrmsAnnualPlanService.class);
        try {
            PrmsAnnualPlanService prmsAnnualPlanService = (PrmsAnnualPlanService) (query.getResultList().get(0));
            System.out.println("======quantity====" + prmsAnnualPlanService.getQuantity());
            return prmsAnnualPlanService;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchasePlan> getWorkPlanNo() {
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_ANNUAL_PLAN_SERVICE\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_ANNUAL_PLAN_SERVICE.PURCHSE_PLAN_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_ANNUAL_PLAN_SERVICE.SERVICE_ID\n"
                + "where prms_service_and_work_reg.registeration_type='work'", PrmsPurchasePlan.class);
        try {
            ArrayList<PrmsPurchasePlan> prmsPurchasePlanNumLst = new ArrayList(query.getResultList());
            return prmsPurchasePlanNumLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchaseRequestDet getQuantityFrmPr(MmsItemRegistration mmsItemRegistration) {
        System.out.println("======item Name---" + mmsItemRegistration.getMaterialId());
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.UNIT_MEASURE_1,\n"
                + "PRMS_PURCHASE_REQUEST_DET.REQ_QUANTITY\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST_DET\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID   = PRMS_PURCHASE_REQUEST_DET.MATERIAL_CODE_ID\n"
                + "WHERE MMS_ITEM_REGISTRATION.MATERIAL_ID= '" + mmsItemRegistration.getMaterialId() + "'", PrmsPurchaseRequestDet.class);
        try {
            PrmsPurchaseRequestDet mmsItemRegistrationObjct = (PrmsPurchaseRequestDet) (query.getResultList().get(0));
            System.out.println("======item Name====" + mmsItemRegistrationObjct.getMaterialCodeId());
            return mmsItemRegistrationObjct;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchaseReqService getQuantityFrmPrService(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        System.out.println("======serice Name---" + prmsServiceAndWorkReg);
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.REQ_QUANTITY,\n"
                + "  PRMS_PURCHASE_REQ_SERVICE.ID AS ID1\n"
                + "FROM PRMS_PURCHASE_REQ_SERVICE\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "where PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID='" + prmsServiceAndWorkReg.getServAndWorkId() + "'", PrmsPurchaseReqService.class);
        try {
            PrmsPurchaseReqService prmsPurchaseReqServiceOb = (PrmsPurchaseReqService) (query.getResultList().get(0));
            System.out.println("======Service Name====" + prmsPurchaseReqServiceOb.getServiceId());
            return prmsPurchaseReqServiceOb;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchasePlnDetail getQuantityFrmPlan(MmsItemRegistration mmsItemRegistration) {
        System.out.println("======Quantity---" + mmsItemRegistration.getQuantity());
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_CODE,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.UNIT_MEASURE_1,\n"
                + "  PRMS_PURCHASE_PLN_DETAIL.QUANTITY\n"
                + "FROM PRMS_PURCHASE_PLN_DETAIL\n"
                + "INNER JOIN PRMS_PURCHASE_PLAN\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_PURCHASE_PLN_DETAIL.PURCHSE_PLAN_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_PURCHASE_PLN_DETAIL.ITEM_CODE_ID\n"
                + "where MMS_ITEM_REGISTRATION.MATERIAL_ID='" + mmsItemRegistration.getMaterialId() + "'", PrmsPurchasePlnDetail.class);
        try {
            PrmsPurchasePlnDetail quantity = (PrmsPurchasePlnDetail) (query.getResultList().get(0));
            System.out.println("======Quantity Size====" + quantity.getQuantity());
            return quantity;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceAndWorkReg> getWorkNameFrmPlan(String workName) {
        System.err.println("==========in sevice Name" + workName);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PRMS_PURCHASE_PLAN.PLAN_NO\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_ANNUAL_PLAN_SERVICE\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_ANNUAL_PLAN_SERVICE.PURCHSE_PLAN_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_ANNUAL_PLAN_SERVICE.SERVICE_ID\n"
                + "where PRMS_PURCHASE_PLAN.PLAN_NO='" + workName + "' "
                + "and PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE  = 'service'", PrmsServiceAndWorkReg.class);
        try {
            ArrayList<PrmsServiceAndWorkReg> sericeNameList = new ArrayList(query.getResultList());
            System.out.println("=======out Of Service Name" + sericeNameList.size());
            return sericeNameList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getServiceNmFrmPr() {
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQ_SERVICE\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID      = PRMS_PURCHASE_REQ_SERVICE.SERVICE_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE = 'service'", PrmsPurchaseRequest.class);
        try {
            List<PrmsPurchaseRequest> prmsPurchaseRequests = new ArrayList(query.getResultList());
            System.out.println("======Service Name====" + prmsPurchaseRequests.size());
            return prmsPurchaseRequests;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PmsCreateProjects> getProjectId() {
        System.out.println("in");
        Query query = em.createNativeQuery("SELECT distinct PMS_CREATE_PROJECTS.PROJECT_ID,\n"
                + "  PMS_CREATE_PROJECTS.PROJECT_NAME\n"
                + "FROM PMS_CREATE_PROJECTS\n"
                + "INNER JOIN PMS_RESOURCES\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PMS_RESOURCES.PROJECT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PMS_RESOURCES.MATERIAL_ID\n"
                + "INNER JOIN PRMS_PROJECT_PLAN\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PRMS_PROJECT_PLAN.PROJECT_NAME", PmsCreateProjects.class);
        try {
            List<PmsCreateProjects> pmsCreateProjectse = new ArrayList(query.getResultList());
            System.out.println("Out" + pmsCreateProjectse.size());
            return pmsCreateProjectse;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> findbycontractId(PrmsBid eepBidReg) {
        System.err.println("item code from paln" + eepBidReg.getId());
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.REF_NO,\n"
                + "  PRMS_CONTRACT.BID_ID,\n"
                + "  PRMS_CONTRACT.CONTRACT_ID,\n"
                + "  PRMS_CONTRACT.CONTRACT_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_CONTRACT\n"
                + "ON PRMS_BID.ID = PRMS_CONTRACT.BID_ID\n"
                + "WHERE PRMS_BID.ID = '" + eepBidReg.getId() + "'", PrmsContract.class);
        try {
            ArrayList<PrmsContract> selectBid = new ArrayList(query.getResultList());
            System.err.println("bid  size" + selectBid.size());
            return selectBid;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> getItemNameFrmProject(String itemName) {
        System.err.println("item code from paln" + itemName);
        Query query = em.createNativeQuery("SELECT PMS_CREATE_PROJECTS.PROJECT_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + " MMS_ITEM_REGISTRATION.MAT_CODE,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID\n"
                + "FROM PMS_CREATE_PROJECTS\n"
                + "INNER JOIN PMS_RESOURCES\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PMS_RESOURCES.PROJECT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PMS_RESOURCES.MATERIAL_ID\n"
                + "INNER JOIN PRMS_PROJECT_PLAN\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PRMS_PROJECT_PLAN.PROJECT_NAME\n"
                + "WHERE PMS_CREATE_PROJECTS.PROJECT_NAME = '" + itemName + "'", MmsItemRegistration.class);
        try {
            ArrayList<MmsItemRegistration> itemRegistrationLst = new ArrayList(query.getResultList());
            System.err.println("item code from paln size" + itemRegistrationLst.size());
            return itemRegistrationLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsItemRegistration getUnitOfMeasureFrmProject(MmsItemRegistration mmsItemRegistration) {
        System.out.println("======unt m---" + mmsItemRegistration.getUnitMeasure1());
        Query query = em.createNativeQuery("SELECT MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.UNIT_MEASURE_1,\n"
                + " MMS_ITEM_REGISTRATION.MAT_CODE,\n"
                + "  PMS_RESOURCES.QUANTITY,\n"
                + "  PMS_CREATE_PROJECTS.PROJECT_ID\n"
                + "FROM PMS_CREATE_PROJECTS\n"
                + "INNER JOIN PMS_RESOURCES\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PMS_RESOURCES.PROJECT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PMS_RESOURCES.MATERIAL_ID\n"
                + "INNER JOIN PRMS_PROJECT_PLAN\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PRMS_PROJECT_PLAN.PROJECT_NAME\n"
                + "where MMS_ITEM_REGISTRATION.MAT_CODE='" + mmsItemRegistration.getMatCode() + "'", MmsItemRegistration.class);
        try {
            MmsItemRegistration unitMeasure = (MmsItemRegistration) (query.getResultList().get(0));
            System.out.println("======Quantity Size====" + unitMeasure.getUnitMeasure1());
            return unitMeasure;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PmsResources getQuantityFrmProjectPln(MmsItemRegistration pmsResources) {
        System.out.println("======Quantity---" + pmsResources.getQuantity());
        Query query = em.createNativeQuery("SELECT MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.UNIT_MEASURE_1,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_CODE,\n"
                + "  PMS_RESOURCES.QUANTITY,\n"
                + "  PMS_CREATE_PROJECTS.PROJECT_ID,\n"
                + "  PMS_RESOURCES.RESOURCE_ID\n"
                + "FROM PMS_CREATE_PROJECTS\n"
                + "INNER JOIN PMS_RESOURCES\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PMS_RESOURCES.PROJECT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PMS_RESOURCES.MATERIAL_ID\n"
                + "INNER JOIN PRMS_PROJECT_PLAN\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PRMS_PROJECT_PLAN.PROJECT_NAME\n"
                + "where MMS_ITEM_REGISTRATION.MAT_CODE='" + pmsResources.getMatCode() + "'", PmsResources.class);
        try {
            PmsResources pmsResourcesobj = (PmsResources) (query.getResultList().get(0));
            System.out.println("======Quantity=== Size====" + pmsResourcesobj.getQuantity());
            return pmsResourcesobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceAndWorkReg> getServiceFrmProjectPlan(String serviceName) {
        System.err.println("item service from paln" + serviceName);
        Query query = em.createNativeQuery("SELECT PMS_CREATE_PROJECTS.PROJECT_ID,\n"
                + "  PMS_RESOURCES.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PMS_CREATE_PROJECTS.PROJECT_NAME\n"
                + "FROM PMS_CREATE_PROJECTS\n"
                + "INNER JOIN PMS_RESOURCES\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PMS_RESOURCES.PROJECT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PMS_RESOURCES.MATERIAL_ID\n"
                + "INNER JOIN PRMS_PROJECT_PLAN\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PRMS_PROJECT_PLAN.PROJECT_NAME\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PMS_RESOURCES.SERV_AND_WORK_ID\n"
                + "WHERE PMS_CREATE_PROJECTS.PROJECT_NAME = '" + serviceName + "'", PrmsServiceAndWorkReg.class);
        try {
            ArrayList<PrmsServiceAndWorkReg> prmsServiceAndWorkRegList = new ArrayList(query.getResultList());
            System.err.println("item service from paln size" + prmsServiceAndWorkRegList.size());
            return prmsServiceAndWorkRegList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceAndWorkReg getUnitMeasAndQuantFrmService(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        Query query = em.createNativeQuery("SELECT PMS_CREATE_PROJECTS.PROJECT_ID,\n"
                + "  PMS_RESOURCES.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PMS_CREATE_PROJECTS.PROJECT_NAME\n"
                + "FROM PMS_CREATE_PROJECTS\n"
                + "INNER JOIN PMS_RESOURCES\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PMS_RESOURCES.PROJECT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PMS_RESOURCES.MATERIAL_ID\n"
                + "INNER JOIN PRMS_PROJECT_PLAN\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PRMS_PROJECT_PLAN.PROJECT_NAME\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PMS_RESOURCES.SERV_AND_WORK_ID\n"
                + "where PMS_RESOURCES.SERV_AND_WORK_ID='" + prmsServiceAndWorkReg.getServAndWorkId() + "'", PrmsServiceAndWorkReg.class);
        try {
            PrmsServiceAndWorkReg prmsServiceAndWorkRegObj = (PrmsServiceAndWorkReg) (query.getResultList().get(0));
            return prmsServiceAndWorkReg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PmsResources getQuantityServiceFrmPrjctPlan(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        Query query = em.createNativeQuery("SELECT PMS_CREATE_PROJECTS.PROJECT_ID,\n"
                + "  PMS_RESOURCES.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PMS_CREATE_PROJECTS.PROJECT_NAME,\n"
                + "  PMS_RESOURCES.RESOURCE_ID,\n"
                + "  PMS_RESOURCES.QUANTITY\n"
                + "FROM PMS_CREATE_PROJECTS\n"
                + "INNER JOIN PMS_RESOURCES\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PMS_RESOURCES.PROJECT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PMS_RESOURCES.MATERIAL_ID\n"
                + "INNER JOIN PRMS_PROJECT_PLAN\n"
                + "ON PMS_CREATE_PROJECTS.PROJECT_ID = PRMS_PROJECT_PLAN.PROJECT_NAME\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PMS_RESOURCES.SERV_AND_WORK_ID\n"
                + "where PMS_RESOURCES.SERV_AND_WORK_ID='" + prmsServiceAndWorkReg.getServAndWorkId() + "'", PmsResources.class);
        try {
            PmsResources pmsResourceObj = (PmsResources) (query.getResultList().get(0));
            return pmsResourceObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBid getBidTypeByItemId(MmsItemRegistration mmsItemRegistration) {
        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVAL_RESULT.ID,\n"
                + "  PRMS_BID.BID_TYPE,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.DATE_REG,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.BID_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVAL_RESULT.BID_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "WHERE MMS_ITEM_REGISTRATION.MATERIAL_ID = '" + mmsItemRegistration.getMaterialId() + "'", PrmsBid.class);
        try {
            PrmsBid prmsBids = new PrmsBid();
            if (query.getResultList().size() > 0) {
                prmsBids = (PrmsBid) (query.getResultList().get(0));
            }
            return prmsBids;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBid getBidTypeByServiceOrWorkId(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVAL_RESULT.ID,\n"
                + "  PRMS_BID.BID_TYPE,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.DATE_REG,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.BID_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID,\n"
                + "  prms_service_and_work_reg.SERVICE_TYPE,\n"
                + "  prms_service_and_work_reg.SERVICE_NAME,\n"
                + "  prms_service_and_work_reg.WORK_NAME\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVAL_RESULT.BID_ID\n"
                + "INNER JOIN prms_service_and_work_reg\n"
                + "ON prms_service_and_work_reg.SERV_AND_WORK_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID\n"
                + "WHERE PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID = '" + prmsServiceAndWorkReg.getServAndWorkId() + "'", PrmsBid.class);
        try {
            PrmsBid prmsBids = new PrmsBid();
            if (query.getResultList().size() > 0) {
                prmsBids = (PrmsBid) (query.getResultList().get(0));
            }
            return prmsBids;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    public List<PrmsBid> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_BID') \n"
                + "and column_name not in ('ID','DESCRIPTION','BID_CRITERIA_ID','CURRENCY_ID','PROJECT_ID','STATUS','FILE_UPLOAD_REFNO','DOCUMENT_ID')");
        try {
            List<PrmsBid> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
