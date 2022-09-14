/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplierSpecificationDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
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
public class PrmsThechnicalEvaluationFacade extends AbstractFacade<PrmsThechnicalEvaluation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsThechnicalEvaluationFacade() {
        super(PrmsThechnicalEvaluation.class);
    }

    public List<PrmsThechnicalEvaluation> searchByEvaluationNo(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        Query query = em.createNamedQuery("PrmsThechnicalEvaluation.searchByEvaluationNo");
        query.setParameter("evaluationNo", prmsThechnicalEvaluation.getEvaluationNo() + "%");
        try {
            List<PrmsThechnicalEvaluation> evaluationsList = new ArrayList(query.getResultList());
            return evaluationsList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidDetail> getBidNo() {
        List<PrmsBidDetail> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsBidDetail.findAll", PrmsBidDetail.class);
            itemLst = (List<PrmsBidDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemLst;

    }

    public List<PrmsQuotation> getQuotationNo() {
        List<PrmsQuotation> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsQuotation.findAlls", PrmsQuotation.class);
            itemLst = (List<PrmsQuotation>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemLst;
    }

    public PrmsBidDetail getPrLsts(String prList) {

        PrmsBidDetail supplierName = null;
        Query query = em.createNamedQuery("PrmsBidDetail.findById", PrmsBidDetail.class);
        query.setParameter("id", prList);
        try {
            if (query.getResultList().size() > 0) {
                supplierName = (PrmsBidDetail) query.getResultList().get(0);
            }
            return supplierName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsBidDetail> getAwardDetailList(String prNumber) { //prnumber event//
        List<PrmsBidDetail> awardDetailList = null;
        try {
            Query query = em.createNamedQuery("PrmsBidDetail.findByIdNo", PrmsBidDetail.class);
            query.setParameter("refNo", prNumber);
            awardDetailList = (List<PrmsBidDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return awardDetailList;

    }

    public List<PrmsPreminilaryEvalutionDt> getSupplierCode(String code) { //prnumber event//
        List<PrmsPreminilaryEvalutionDt> awardDetailList = null;
        try {
            Query query = em.createNamedQuery("PrmsPreminilaryEvalutionDt.findById", PrmsPreminilaryEvalutionDt.class);
            query.setParameter("id", code);
            awardDetailList = (List<PrmsPreminilaryEvalutionDt>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return awardDetailList;
    }

    public PrmsThechnicalEvaluation getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findByEvaluationId");
        query.setParameter("evaluationId", id);

        try {
            PrmsThechnicalEvaluation selectrequest = (PrmsThechnicalEvaluation) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsThechnicalEvaluation LastCheckListNo() {

        Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findByMaxCheckListNum");

        try {
            PrmsThechnicalEvaluation CheckListNo = (PrmsThechnicalEvaluation) query.getResultList().get(0);

            return CheckListNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getVanorsName() {
        try {
            Query query = em.createNamedQuery("PrmsSupplyProfile.findAll", PrmsSupplyProfile.class);
            ArrayList<PrmsSupplyProfile> eepVendorRegList = new ArrayList(query.getResultList());
            return eepVendorRegList;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }

    }

    public List<PrmsBidDetail> getItemList(String itemListS) {
        System.out.println("---in pr Dt----" + itemListS);
        List<PrmsBidDetail> itemList = null;
        Query query = em.createNativeQuery("SELECT  PRMS_BID_DETAIL.ID,PRMS_BID_DETAIL.ITEM_REG_ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_BID_DETAIL.ITEM_REG_ID\n"
                + "WHERE PRMS_BID.REF_NO='" + itemListS + "'", PrmsBidDetail.class);
        try {

            itemList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("---in pr Dt out ----" + itemList.size());
        } catch (Exception ex) {

        }
        return itemList;
    }

    public List<PrmsPreminilaryEvalutionDt> getPrelinmarList(String bidNum) {
        System.out.println("in supplier code" + bidNum);
        List<PrmsPreminilaryEvalutionDt> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_PREMINILARY_EVALUATION.ID,\n"
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
                + "where   PRMS_BID.REF_NO ='" + bidNum + "'", PrmsPreminilaryEvalutionDt.class);
        try {
            suppLierList = (List<PrmsPreminilaryEvalutionDt>) query.getResultList();
            System.out.println("---out of supplier ----" + suppLierList.size());
        } catch (Exception ex) {
        }
        return suppLierList;
    }

    public PrmsSpecification getItemSpecficationList(String specficationLists) {
        System.out.println("---in pr Dt----" + specficationLists);
        Query query = em.createNativeQuery("SELECT  PRMS_BID_DETAIL.ID,PRMS_BID_DETAIL.ITEM_REG_ID,\n"
                + "  PRMS_BID_DETAIL.ITEM_REG_ID,\n"
                + "  PRMS_SPECIFICATION.SPECIFICATION,\n"
                + "  PRMS_SPECIFICATION.BID_DET_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME\n"
                + "FROM PRMS_BID_DETAIL\n"
                + "INNER JOIN PRMS_SPECIFICATION\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_SPECIFICATION.BID_DET_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID  = PRMS_BID_DETAIL.ITEM_REG_ID\n"
                + "AND MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_SPECIFICATION.MATERIAL_ID\n"
                + "WHERE PRMS_SPECIFICATION.BID_DET_ID  ='" + specficationLists + "'", PrmsSpecification.class);

        try {
            PrmsSpecification specficationList = (PrmsSpecification) query.getResultList().get(0);
            return specficationList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<PrmsQuotationDetail> getItemListFromQuotation(String quotationItemLists) {
        System.out.println("---in pr Dt----" + quotationItemLists);
        List<PrmsQuotationDetail> itemList = null;
        Query query = em.createNativeQuery("SELECT  PRMS_QUOTATION_DETAIL.ITEM_REG_ID\n"
                + " PRMS_QUOTATION.QUATATION_ID ,\n"
                + "  PRMS_QUOTATION_DETAIL.QUAT_DET_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUOTATION_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.ITEM_REG_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_QUOTATION_DETAIL.ITEM_REG_ID\n"
                + "where   PRMS_QUOTATION.QUOTATION_NO ='" + quotationItemLists + "'", PrmsQuotationDetail.class);

        try {

            itemList = (List<PrmsQuotationDetail>) query.getResultList();
            System.out.println("---in pr Dt out ----" + itemList.size());
        } catch (Exception ex) {

        }
        return itemList;
    }

    public List<PrmsQuotationDetail> getQuotationDEtailList(String QuotationNo) {

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

    public PrmsSupplierSpecificationDt getSupplierSpecficationList(MmsItemRegistration itemRegistration) {

        PrmsSupplierSpecificationDt supplierName = new PrmsSupplierSpecificationDt();
        Query query = em.createNamedQuery("PrmsSupplierSpecificationDt.findByMaterialId", PrmsSupplierSpecificationDt.class);
        query.setParameter("id", itemRegistration.getMaterialId());
        try {
            if (query.getResultList().size() > 0) {
                supplierName = (PrmsSupplierSpecificationDt) query.getSingleResult();
            }
            return supplierName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidDetail> getBidListItem(String bidNo) {
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

    public PrmsSupplierSpecificationDt getSupplierSpecification(String supplierCode, Integer itemRegId) {

        PrmsSupplierSpecificationDt supplierName = null;
        Query query = em.createNativeQuery("SELECT PRMS_SUPPLIER_SPECIFICATION_DT.*\n"
                + "FROM PRMS_SUPP_SPECIFICATION\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUTION_DT\n"
                + "ON PRMS_PREMINILARY_EVALUTION_DT.ID = PRMS_SUPP_SPECIFICATION.PREMINARY_EVA_ID\n"
                + "INNER JOIN PRMS_SUPPLIER_SPECIFICATION_DT\n"
                + "ON PRMS_SUPP_SPECIFICATION.SUPP_SPEC_ID = PRMS_SUPPLIER_SPECIFICATION_DT.SUPP_SPEC_ID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID                             = PRMS_SUPPLIER_SPECIFICATION_DT.BID_DETAIL_ID\n"
                + "WHERE PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_CODE = '" + supplierCode + "'\n"
                + "AND PRMS_BID_DETAIL.ITEM_REG_ID                   = '" + itemRegId + "'", PrmsSupplierSpecificationDt.class);
//        Query query = em.createNamedQuery("PrmsBidDetail.findById", PrmsBidDetail.class);
//        query.setParameter("id", prList);
        try {
            if (query.getResultList().size() > 0) {
                supplierName = (PrmsSupplierSpecificationDt) query.getResultList().get(0);
            }
            return supplierName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<PrmsBid> BidNoFormPiliminaryEvaluation() {

        Query query = em.createNativeQuery("SELECT PRMS_PREMINILARY_EVALUATION.BID_ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID_DETAIL.BID_ID AS BID_ID1,\n"
                + "  PRMS_BID_DETAIL.ITEM_REG_ID,\n"
                + "  PRMS_BID_DETAIL.SERVICE_ID\n"
                + "FROM PRMS_PREMINILARY_EVALUATION\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsBid> BidNoFormPliminary() {

        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "PRMS_BID.REF_NO,\n"
                + "PRMS_BID.BID_CATEGORY\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsThechnicalEvaluation> getTechinicalEvalList() {
        Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findByRequestForApproval", PrmsThechnicalEvaluation.class);
        ArrayList<PrmsThechnicalEvaluation> TechinicalEvalList = new ArrayList(query.getResultList());
        return TechinicalEvalList;
    }

    public List<PrmsThechnicalEvaluation> searchByEvalNo(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        List<PrmsThechnicalEvaluation> thechnicalEvaluationLst = new ArrayList();
        if (prmsThechnicalEvaluation.getColumnName() != null && !prmsThechnicalEvaluation.getColumnName().equals("")
                && prmsThechnicalEvaluation.getColumnValue() != null && !prmsThechnicalEvaluation.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_THECHNICAL_EVALUATION\n"
                    + "where " + prmsThechnicalEvaluation.getColumnName().toLowerCase() + " = '" + prmsThechnicalEvaluation.getColumnValue() + "'"
                    + "and " + prmsThechnicalEvaluation.getPreparedBy() + "='" + prmsThechnicalEvaluation.getPreparedBy() + "'", PrmsThechnicalEvaluation.class);
            try {
                if (query.getResultList().size() > 0) {
                    thechnicalEvaluationLst = query.getResultList();
                }
                return thechnicalEvaluationLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findByPreparedBy");
            query.setParameter("preparedBy", prmsThechnicalEvaluation.getPreparedBy());
            thechnicalEvaluationLst = query.getResultList();
            return thechnicalEvaluationLst;
        }
    }

    public List<PrmsThechnicalEvaluation> getPurchaseReqOnLists() {
        Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findByRequestForApproval", PrmsThechnicalEvaluation.class);
        ArrayList<PrmsThechnicalEvaluation> purchaseRequestList = new ArrayList(query.getResultList());
        return purchaseRequestList;
    }

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromResult(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT PRMS_PREMINILARY_EVALUTION_DT.*,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_PREMINILARY_EVALUATION.BID_ID,\n"
                + "  PRMS_BID.ID AS ID1\n"
                + "FROM PRMS_PREMINILARY_EVALUTION_DT\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION\n"
                + "ON PRMS_PREMINILARY_EVALUATION.ID = PRMS_PREMINILARY_EVALUTION_DT.PREMINARY_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID\n"
                + "WHERE PRMS_PREMINILARY_EVALUATION.BID_ID= '" + bidNo.getId() + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsPreminilaryEvalutionDt> getsupplierNameF(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT PRMS_PREMINILARY_EVALUTION_DT.*,\n"
                + "  PRMS_PREMINILARY_EVALUATION.BID_ID,\n"
                + "  PRMS_BID.ID AS ID1,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.ID            AS ID2,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID   AS SUPPLIER_ID1,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_CODE AS SUPPLIER_CODE1\n"
                + "FROM PRMS_PREMINILARY_EVALUTION_DT\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION\n"
                + "ON PRMS_PREMINILARY_EVALUATION.ID = PRMS_PREMINILARY_EVALUTION_DT.PREMINARY_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID\n"
                + "WHERE PRMS_PREMINILARY_EVALUATION.BID_ID= '" + bidNo.getId() + "'", PrmsPreminilaryEvalutionDt.class);
        try {
            ArrayList<PrmsPreminilaryEvalutionDt> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getsupplierName(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT PRMS_SUPPLY_PROFILE.*,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.ID AS ID1,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID,\n"
                + "  PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_CODE,\n"
                + "  PRMS_BID.ID AS ID2,\n"
                + "  PRMS_BID.REF_NO\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUTION_DT\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID\n"
                + "INNER JOIN PRMS_PREMINILARY_EVALUATION\n"
                + "ON PRMS_PREMINILARY_EVALUATION.ID = PRMS_PREMINILARY_EVALUTION_DT.PREMINARY_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_PREMINILARY_EVALUATION.BID_ID\n"
                + "WHERE PRMS_PREMINILARY_EVALUATION.BID_ID= '" + bidNo.getId() + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }
//    SELECT * FROM prms_bid_amend bdamd
//INNER JOIN (SELECT BID_ID, MAX (ID) AS bidAmendMaxId 
//FROM PRMS_BID_AMEND
//GROUP BY BID_ID) bdamd2
//on bdamd.ID=bdamd2.bidAmendMaxId
//inner join prms_bid bd
//on bd.id=bdamd.bid_id
//where bd.id=34;
//
//
//SELECT * FROM prms_bid_amend bdamd
//INNER JOIN (SELECT BID_ID, MAX (ID) AS bidAmendMaxId 
//FROM PRMS_BID_AMEND
//GROUP BY BID_ID) bdamd2
//on bdamd.ID=bdamd2.bidAmendMaxId

    public List<PrmsThechnicalEvaluation> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_THECHNICAL_EVALUATION') \n"
                + "and column_name not in ('ID','DESCRIPTION','REMARK','PREMINARY_EVA_ID','QUOTATION_ID','BID_ID','FEXFILEREFNUMBER','STATUS','DOCUMENT_ID')");
        try {
            List<PrmsThechnicalEvaluation> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
