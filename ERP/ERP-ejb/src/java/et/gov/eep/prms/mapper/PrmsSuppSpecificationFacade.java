/**
 * *****************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 * ****************************************************************************
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractAmendmentDt;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author pc
 */
@Stateless
public class PrmsSuppSpecificationFacade extends
        AbstractFacade<PrmsSuppSpecification> {

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
    public PrmsSuppSpecificationFacade() {
        super(PrmsSuppSpecification.class);
    }

    /**
     *
     * @param prmsSuppSpecification
     * @return
     */
    public List<PrmsSuppSpecification> searchSuppSpecification(PrmsSuppSpecification prmsSuppSpecification) {

         if (prmsSuppSpecification.getColumnValue() != null && prmsSuppSpecification.getColumnName() != null
                && !prmsSuppSpecification.getColumnValue().equals("") && !prmsSuppSpecification.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_SUPP_SPECIFICATION\n"
                    + "where " + prmsSuppSpecification.getColumnName().toLowerCase() + " = '" + prmsSuppSpecification.getColumnValue() + "' "
                    + "and " + prmsSuppSpecification.getPreparedBy()+ "='" + prmsSuppSpecification.getPreparedBy()+ "' ", PrmsSuppSpecification.class);
            try {
                List<PrmsSuppSpecification> suppSpecificationLst = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    suppSpecificationLst = query.getResultList();
                   
                }
                return suppSpecificationLst;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsSuppSpecification.findByPreparedBy");
            query.setParameter("preparedBy", prmsSuppSpecification.getPreparedBy());
            return query.getResultList();
        }
    }

    public ArrayList<PrmsSuppSpecification> searchSuppSpecification() {

        Query query = em.createNamedQuery("PrmsSuppSpecification.findAllByStatus");

        try {
            ArrayList<PrmsSuppSpecification> specificationList = new ArrayList(query.getResultList());

            return specificationList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param prmsSuppSpecification
     * @return
     */
//    public PrmsSuppSpecification getCustomerFee(PrmsSuppSpecification prmsSuppSpecification) {
//        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.findByBidDoc");
//        query.setParameter("refNo", prmsSuppSpecification.getSpecificationId());
//        try {
//
//            PrmsSuppSpecification customerFeeInfo = (PrmsSuppSpecification) query.getResultList().get(0);
//
//            return customerFeeInfo;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
    /**
     *
     * @param prmsSuppSpecification
     * @return
     */
    public ArrayList<PrmsSuppSpecification> searchPartyList(PrmsSuppSpecification prmsSuppSpecification) {
        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.serachByCustomer");
        query.setParameter("refNo", prmsSuppSpecification);
        try {
            ArrayList<PrmsSuppSpecification> customerFees = new ArrayList(query.getResultList());
            return customerFees;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @return
     */
    public ArrayList<PrmsSpecification> getBidNo() {
        Query query = em.createNamedQuery("PrmsSpecification.searchBySpecificationbid");
        try {
            ArrayList<PrmsSpecification> prmsSpecificationlist = new ArrayList<>(query.getResultList());

            return prmsSpecificationlist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsSpecification> getBidNo(PrmsPreminilaryEvaluation prmsPreminilaryEvaluation) {
        Query query = em.createNamedQuery("PrmsSpecification.searchBySpecificationbid");
        try {
            ArrayList<PrmsSpecification> prmsSpecificationlist = new ArrayList<>(query.getResultList());

            return prmsSpecificationlist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsSpecification> getAwardDetailList(String matName) {

        List<PrmsSpecification> awardDetailList = null;
        try {
            Query query = em.createNamedQuery("PrmsSpecification.findByBidDetId", PrmsSpecification.class);
            query.setParameter("matName", matName);
            awardDetailList = (List<PrmsSpecification>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return awardDetailList;
    }

    public List<PrmsBid> getBidList() {
        List<PrmsBid> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.ID,\n"
                + "  PRMS_PREMINILARY_EVALUATION.ID   AS ID1,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.ID AS ID2,\n"
                + "PRMS_SUPPLY_PROFILE.VENDOR_NAME AS ID3,\n"
                + "PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID AS ID4,\n "
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUTION_DT\n"
                + "ON PRMS_PREMINILARY_EVALUATION.ID = PRMS_PREMINILARY_EVALUTION_DT.PREMINARY_ID\n", PrmsBid.class);
        try {

            suppLierList = (List<PrmsBid>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public List<PrmsPreminilaryEvalutionDt> getSupplierList(String supplierLists) {
        System.out.println("---in pr Dt----" + supplierLists);
        List<PrmsPreminilaryEvalutionDt> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.REF_NO"
                + "  PRMS_BID.ID"
                + " FROM PRMS_PREMINILARY_EVALUATION.ID "
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID"
                + "INNER JOIN PRMS_PREMINILARY_EVALUTION_DT"
                + "ON PRMS_PREMINILARY_EVALUATION.ID = PRMS_PREMINILARY_EVALUTION_DT.PREMINARY_ID"
                + "WHERE PRMS_BID.REF_NO='" + supplierLists + "'", PrmsPreminilaryEvalutionDt.class);
        try {

            suppLierList = (List<PrmsPreminilaryEvalutionDt>) query.getResultList();
            System.out.println("---in pr Dt out ----" + suppLierList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public List<PrmsBidDetail> getItemList(String itemListS) {
        System.out.println("---in pr Dt----" + itemListS);
        List<PrmsBidDetail> itemList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID_DETAIL.ID AS ID1,\n"
                + "  PRMS_BID_DETAIL.ITEM_REG_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_BID_DETAIL.ITEM_REG_ID\n"
                + "WHERE PRMS_BID.ID='" + itemListS + "'", PrmsBidDetail.class);
        try {

            itemList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("---in pr Dt out ----" + itemList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return itemList;
    }

    public PrmsSuppSpecification getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsSuppSpecification.findBySuppSpecId");
        query.setParameter("suppSpecId", id);
        try {
            PrmsSuppSpecification selectrequest = (PrmsSuppSpecification) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSuppSpecification LastSpecficationNo() {

        Query query = em.createNamedQuery("PrmsSuppSpecification.findByMaxSpecficationNo");
        PrmsSuppSpecification specficationNo = null;
        try {

            if (query.getResultList().size() > 0) {
                specficationNo = (PrmsSuppSpecification) query.getResultList().get(0);
            }
            return specficationNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidDetail> findByType(MmsItemRegistration matName) {
        Query q = em.createNamedQuery("PrmsBidDetail.findByItemRegID");
        q.setParameter("itemRegId", matName);
        try {
            List<PrmsBidDetail> lvl = new ArrayList<>(q.getResultList());
            return lvl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrmsBidDetail findByName(String name) {
        Query q = em.createNamedQuery("PrmsBidDetail.findByItemRegMatName");
        q.setParameter("matName", name);
        try {
            PrmsBidDetail dis = (PrmsBidDetail) q.getResultList().get(0);
            return dis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<MmsItemRegistration> itemList() {

        Query query = em.createNamedQuery("MmsItemRegistration.findAll");
        try {
            ArrayList<MmsItemRegistration> supplier = new ArrayList(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPreminilaryEvaluation> getSupplieList() {
        List<PrmsPreminilaryEvaluation> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_PREMINILARY_EVALUATION.ID AS ID1,\n"
                + "PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.ID,\n"
                + "  PRMS_PREMINILARY_EVALUATION.BID_ID,\n"
                + "  PRMS_PREMINILARY_EVALUATION.ID AS ID1\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID", PrmsPreminilaryEvaluation.class);

        try {

            suppLierList = (List<PrmsPreminilaryEvaluation>) query.getResultList();
            System.out.println("---in pr Dt out ----" + suppLierList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public List<PrmsPreminilaryEvalutionDt> getPrelinmarList(String bidNum) {
        System.out.println("in supplier code" + bidNum);
        List<PrmsPreminilaryEvalutionDt> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "  PRMS_PREMINILARY_EVALUATION.ID   AS ID1,\n"
                + "  PRMS_SUPPLY_PROFILE.ID           AS ID2,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.ID AS ID3,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUTION_DT\n"
                + "ON PRMS_PREMINILARY_EVALUATION.ID = PRMS_PREMINILARY_EVALUTION_DT.PREMINARY_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID\n"
                + "where   PRMS_PREMINILARY_EVALUATION.ID ='" + bidNum + "'", PrmsPreminilaryEvalutionDt.class);
        try {

            suppLierList = (List<PrmsPreminilaryEvalutionDt>) query.getResultList();
            System.out.println("---out of supplier ----" + suppLierList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public List<PrmsBidDetail> getQuotationDEtailList(String QuotationNo) {

        List<PrmsBidDetail> quotationList = null;
        try {
            Query query = em.createNamedQuery("PrmsBidDetail.findByBidNo", PrmsBidDetail.class);
            query.setParameter("bidNo", QuotationNo);
            quotationList = (List<PrmsBidDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotationList;
    }

    public List<PrmsPreminilaryEvalutionDt> getsupplierlist(String bidNo) {
        List<PrmsPreminilaryEvalutionDt> quotationList = null;
        try {
            Query query = em.createNamedQuery("PrmsPreminilaryEvalutionDt.findByBidNo", PrmsPreminilaryEvalutionDt.class);
            query.setParameter("refNo", bidNo);
            quotationList = (List<PrmsPreminilaryEvalutionDt>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotationList;
    }

    public List<PrmsBidDetail> getBidDetailList(String bidNo) {

        List<PrmsBidDetail> quotationList = null;
        try {
            Query query = em.createNamedQuery("PrmsBidDetail.findByRefNo", PrmsBidDetail.class);
            query.setParameter("refNo", bidNo);
            quotationList = (List<PrmsBidDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotationList;
    }

    public List<PrmsPreminilaryEvaluation> getBidList(PrmsPreminilaryEvaluation preminilaryEvaluation) {

        List<PrmsPreminilaryEvaluation> quotationList = null;
        try {
            Query query = em.createNamedQuery("PrmsPreminilaryEvaluation.findByBidNo", PrmsPreminilaryEvaluation.class);
            query.setParameter("refNo", preminilaryEvaluation.getBidId().getRefNo());
            quotationList = (List<PrmsPreminilaryEvaluation>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotationList;
    }

    public ArrayList<PrmsBid> BidNoFormAward() {

        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_AWARD\n"
                + "ON PRMS_BID.ID = PRMS_AWARD.BID_ID", PrmsBid.class);
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

    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo) {
        System.out.println("3");
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_AWARD.SUPP_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_AWARD\n"
                + "ON PRMS_BID.ID = PRMS_AWARD.BID_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_AWARD.SUPP_ID\n"
                + "WHERE PRMS_AWARD.BID_ID = '" + bidNo.getId() + "'", PrmsSupplyProfile.class);
        System.out.println("4");
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

    public ArrayList<PrmsAward> getAwardNo(PrmsBid bidNo) {

        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_AWARD.AWARD_ID,\n"
                + "  PRMS_AWARD.AWARD_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_AWARD\n"
                + "ON PRMS_BID.ID = PRMS_AWARD.BID_ID\n"
                + "WHERE PRMS_AWARD.BID_ID = '" + bidNo.getId() + "'", PrmsAward.class);
        try {
            ArrayList<PrmsAward> awardList = new ArrayList<>();
            awardList = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + awardList.size());
            return awardList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsAwardDetail> getitemName(PrmsSupplyProfile prmsSupplyProfile) {

        Query query = em.createNativeQuery("SELECT PRMS_AWARD_DETAIL.DETAIL_ID,\n"
                + "PRMS_AWARD_DETAIL.DETAIL_ID,\n"
                + "PRMS_AWARD_DETAIL.MATERIAL_ID\n"
                + "FROM PRMS_AWARD_DETAIL\n"
                + "INNER JOIN PRMS_AWARD\n"
                + "ON PRMS_AWARD.AWARD_ID = PRMS_AWARD_DETAIL.AWARD_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_AWARD.SUPP_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_AWARD_DETAIL.MATERIAL_ID\n"
                + "WHERE PRMS_AWARD.SUPP_ID  = '" + prmsSupplyProfile.getId() + "'", PrmsAwardDetail.class);
        try {
            ArrayList<PrmsAwardDetail> awardList = new ArrayList<>();
            awardList = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + awardList.size());
            return awardList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param prmsSupplyProfile
     * @return
     * ************************************************************************
     */
    public ArrayList<PrmsContractDetail> getitemNameFromContract(
            PrmsSupplyProfile prmsSupplyProfile) {

        Query query = em.createNativeQuery(
                "SELECT PRMS_CONTRACT_DETAIL.CONTRACT_DETAIL_ID,\n"
                + "  PRMS_CONTRACT_DETAIL.CONTRACT_DETAIL_ID,\n"
                + "  PRMS_CONTRACT_DETAIL.ITEM_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME\n"
                + "FROM PRMS_CONTRACT_DETAIL\n"
                + "INNER JOIN PRMS_CONTRACT\n"
                + "ON PRMS_CONTRACT.CONTRACT_ID = PRMS_CONTRACT_DETAIL.CONTRACT_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_CONTRACT.SUPP_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_CONTRACT_DETAIL.ITEM_ID\n"
                + "WHERE PRMS_CONTRACT.SUPP_ID          = '" + prmsSupplyProfile.getId() + "'", PrmsContractDetail.class);
        try {
            System.err.println("SUPPLIER ID eNTITY FACADE ... ==>" + prmsSupplyProfile.getId());

            ArrayList<PrmsContractDetail> contractList = new ArrayList<>();
            contractList = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + contractList.size());
            return contractList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param prmsContract
     * @return
     * ************************************************************************
     */
    public ArrayList<PrmsContractDetail> getitemNameFromContract(
            PrmsContract prmsContract) {
        
        System.out.println("[-1-] BID ID" + prmsContract.getBidId().getId());

        Query query = em.createNativeQuery(
                "SELECT PRMS_CONTRACT_DETAIL.CONTRACT_DETAIL_ID,\n"
                + "  PRMS_CONTRACT_DETAIL.CONTRACT_DETAIL_ID,\n"
                + "  PRMS_CONTRACT_DETAIL.ITEM_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME\n,"
                + "MMS_ITEM_REGISTRATION.UNIT_MEASURE_1 as UNIT_MEASURE \n"
                + "FROM PRMS_CONTRACT_DETAIL\n"
                + "INNER JOIN PRMS_CONTRACT\n"
                + "ON PRMS_CONTRACT.CONTRACT_ID = PRMS_CONTRACT_DETAIL.CONTRACT_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_CONTRACT.SUPP_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_CONTRACT_DETAIL.ITEM_ID\n"
                + "WHERE PRMS_CONTRACT.SUPP_ID = '" + prmsContract.getSuppId().getId() + "'"
                + " and PRMS_CONTRACT.BID_ID = '" + prmsContract.getBidId().getId() + "'", 
                PrmsContractDetail.class);

        try {
            System.err.println("SUPPLIER ID eNTITY FACADE ... ==>" + prmsContract.getSuppId().getId());

            ArrayList<PrmsContractDetail> contractList = new ArrayList<>();
            contractList = new ArrayList<>(query.getResultList());
            System.err.println("size -- " + contractList.size());
            return contractList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContract getcontNum(PrmsSupplyProfile prmsSupplyProfile) {
        System.out.println("facade--------");
        Query query = em.createNamedQuery("PrmsContract.findBySuppId");
        System.out.println("hello");
        query.setParameter("suppId", prmsSupplyProfile.getId());
        System.out.println("mine");
        try {
            PrmsContract prmsContract = null;
            if (query.getResultList().size() > 0) {
                prmsContract = (PrmsContract) query.getResultList().get(0);
                System.out.println("supp id--- " + prmsContract.getSuppId());
            }
            return prmsContract;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**************************************************************************
     * 
     * @param prmsSupplyProfile
     * @return 
     *************************************************************************/
    public PrmsContract getcontNumber(PrmsSupplyProfile prmsSupplyProfile) {

        System.out.println("HAHAHA supp id--- ll" + prmsSupplyProfile.getId());        
        PrmsContract prmsContract = new PrmsContract();
        prmsContract.setSuppId(prmsSupplyProfile);        
        Query query = em.createNamedQuery("PrmsContract.findBySuppId");
        query.setParameter("suppId", prmsContract.getSuppId());
        
        try {
            if (query.getResultList().size() > 0) {
                prmsContract = (PrmsContract) query.getResultList().get(0);
                System.out.println("supp id--- ll" +  prmsContract.getSuppId());
            }
            
            return prmsContract;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    public PrmsContract getcontNumber(PrmsSupplyProfile prmsSupplyProfile) {        
//        
//        Query query = em.createNamedQuery("PrmsContract.findBySuppId");     
//        query.setParameter("suppId", prmsSupplyProfile.getId());
//        PrmsContract prmsContract = null;
//        
//        try {
//            if (query.getResultList().size() > 0) {
//                prmsContract = (PrmsContract) query.getResultList().get(0);
//            }
//            return prmsContract;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
    public List<PrmsBidAmend> checkAsBidAmended(PrmsBid prmsBid) {
        System.out.println("here facade " + prmsBid);
        Query q = em.createNativeQuery("SELECT * FROM PRMS_BID_AMEND bdamd\n"
                + "INNER JOIN (SELECT MAX (id) as MaxAmendId\n"
                + "FROM PRMS_BID_AMEND)bdamd2\n"
                + "ON bdamd.ID=bdamd2.MaxAmendId\n"
                + "where bdamd.BID_ID in(SELECT bd.ID FROM PRMS_BID bd)", PrmsBidAmend.class);
        try {
            List<PrmsBidAmend> bidAmendedList = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                bidAmendedList = q.getResultList();
                System.out.println("amended list size " + bidAmendedList.size());
            }
            return bidAmendedList;
        } catch (NullPointerException exception) {
            exception.printStackTrace();
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

    public ArrayList<PrmsContractDetail> getitemNameFromContractAme(
            PrmsSupplyProfile prmsSupplyProfile) {

        Query query = em.createNativeQuery(
                "SELECT  AMED.CONTRACT_AMEND_ID AS CONTRACT_DETAIL_ID, "
                        + "AMED.MATERIAL_ID AS ITEM_ID, SUP.VENDOR_NAME, "
                        + "ITEM.MAT_NAME  FROM PRMS_CONTRACT_AMENDMENT_DT AMED "
                        + "INNER JOIN PRMS_CONTRACT_AMENDMENT AME "
                        + "ON AME.CONTRACT_AMEND_ID = AMED.CONTRACT_AMEND_ID "
                        + "INNER JOIN PRMS_SUPPLY_PROFILE SUP "
                        + "ON SUP.ID = AME.SUPP_ID INNER JOIN MMS_ITEM_REGISTRATION ITEM "
                        + "ON ITEM.MATERIAL_ID = AMED.MATERIAL_ID "
                        + "WHERE AME.SUPP_ID = '" + 
                        prmsSupplyProfile.getId() + "'", PrmsContractDetail.class);
        try {
            System.err.println("SUPPLIER ID eNTITY FACADE ... ==>" + prmsSupplyProfile.getId());

            ArrayList<PrmsContractDetail> contractList = new ArrayList<>();
            contractList = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + contractList.size());
            
            return contractList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsSupplyProfile> findSuppliers() {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findAll");
        try {
            List<PrmsSupplyProfile> sup = new ArrayList(query.getResultList());
            return sup;
        } catch (Exception ex) {
        }
        return null;
    }

    public List<PrmsSuppSpecification> getParamNameList() {
         Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_SUPP_SPECIFICATION') \n"
                + "and column_name not in ('SUPP_SPEC_ID','REMARK','PREMINARY_EVA_ID','SUPP_ID','BID_ID','FILENAME','FILENAME','STATUS','DOCUMENT_ID')");
        try {
            List<PrmsSuppSpecification> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
