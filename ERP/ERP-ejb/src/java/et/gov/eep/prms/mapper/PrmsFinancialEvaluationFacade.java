/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvaluation;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
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
public class PrmsFinancialEvaluationFacade extends AbstractFacade<PrmsFinancialEvaluation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsFinancialEvaluationFacade() {
        super(PrmsFinancialEvaluation.class);
    }

    public List<PrmsQuotation> getProformaNo() {
        List<PrmsQuotation> quotationList = null;
        Query query = em.createNativeQuery("SELECT PRMS_QUOTATION.QUOTATION_NO,\n"
                + "  PRMS_QUOTATION.QUATATION_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUAT_DET_ID,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_QUOTATION_DETAIL.QUAT_DET_ID = PRMS_THECHNICAL_EVALUATION.QUOTATION_DT_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID", PrmsQuotation.class);
        try {
            quotationList = (List<PrmsQuotation>) query.getResultList();
            return quotationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public List<PrmsThechincalEvaluationDet> getSuppCode() {
//        List<PrmsThechincalEvaluationDet> evaluationDets = null;
//        try {
//            Query query = em.createNamedQuery("PrmsThechincalEvaluationDet.findByItemLotPack", PrmsThechincalEvaluationDet.class);
//            evaluationDets = (List<PrmsThechincalEvaluationDet>) query.getResultList();
//            return evaluationDets;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public List<PrmsThechnicalEvaluation> getItemCode() {
        List<PrmsThechnicalEvaluation> evaluationDets = null;
        try {
            Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findByLotItemPackage", PrmsThechnicalEvaluation.class);
            evaluationDets = (List<PrmsThechnicalEvaluation>) query.getResultList();
            return evaluationDets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MmsItemRegistration getItemName(String itemName) {

        MmsItemRegistration itemRegistrationLst = null;
        Query query = em.createNamedQuery("MmsItemRegistration.findByItemName", MmsItemRegistration.class);
        query.setParameter("itemName", itemName);
        try {
            if (query.getResultList().size() > 0) {
                itemRegistrationLst = (MmsItemRegistration) query.getResultList().get(0);
            }

            return itemRegistrationLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsThechnicalEvaluation> getimCode() {
        try {
            Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findAll", PrmsBidDetail.class);
            List<PrmsThechnicalEvaluation> itemLst = (List<PrmsThechnicalEvaluation>) query.getResultList();

            return itemLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public PrmsThechnicalEvaluation getItemNam(String itemName) {

        PrmsThechnicalEvaluation itemRegistrationLst = null;
        Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findByItemName", PrmsThechnicalEvaluation.class);
        query.setParameter("itemName", itemName);
        try {
            if (query.getResultList().size() > 0) {
                itemRegistrationLst = (PrmsThechnicalEvaluation) query.getResultList().get(0);
            }

            return itemRegistrationLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsBid getBidType(String bidType) {
        System.out.println("---in bid type----" + bidType);
        PrmsBid bidTypeObj = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.*,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "WHERE PRMS_BID.REF_NO = '" + bidType + "'", PrmsBid.class);

        try {
            if (query.getResultList().size() > 0) {
                bidTypeObj = (PrmsBid) query.getResultList().get(0);
            }
            System.out.println("----out of bid type---" + bidTypeObj);
            return bidTypeObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public List<PrmsThechnicalEvaluation> getItemList(String ItemList) {
        System.out.println("----In Item----" + ItemList);
        List<PrmsThechnicalEvaluation> ItemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findBySupplierCode", PrmsThechnicalEvaluation.class);
            query.setParameter("supplierName", ItemList);
            ItemLst = (List<PrmsThechnicalEvaluation>) query.getResultList();
            System.out.println("-----Out Of Item---" + ItemLst.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ItemLst;
    }

    public List<PrmsThechincalEvaluationDet> getSupCod(String supCode) {
        System.out.println("----In supplier code----" + supCode);
        List<PrmsThechincalEvaluationDet> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID_DETAIL.ID AS ID1,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.SUPPLIER_CODE,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.ITEM_REGISTRATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.SUPPLIER_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.ID AS ID2,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  PRMS_LU_VAT_TYPE_LOOKUP.VAT_TYPE_ID,\n"
                + "  PRMS_LU_VAT_TYPE_LOOKUP.VAT_RATE\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_THECHINCAL_EVALUATION_DET.SUPPLIER_ID\n"
                + "INNER JOIN PRMS_LU_VAT_TYPE_LOOKUP\n"
                + "ON PRMS_LU_VAT_TYPE_LOOKUP.VAT_TYPE_ID = PRMS_SUPPLY_PROFILE.VAT_TYPE_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID  = PRMS_BID_DETAIL.ITEM_REG_ID\n"
                + "AND MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_THECHINCAL_EVALUATION_DET.ITEM_REGISTRATION_ID\n"
                + "WHERE PRMS_BID.REF_NO = '" + supCode + "'", PrmsThechincalEvaluationDet.class);

        try {
            suppLierList = (List<PrmsThechincalEvaluationDet>) query.getResultList();
            System.out.println("-----Out supplier Code---" + suppLierList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppLierList;

    }

    public PrmsFinancialEvaluation getFinancialId(String id) {
        System.out.println("----in id ---" + id);
        Query query = em.createNamedQuery("PrmsFinancialEvaluation.findById");
        query.setParameter("id", id);
        try {
            PrmsFinancialEvaluation idlst = (PrmsFinancialEvaluation) query.getResultList().get(0);
            System.out.println("---out of id---" + idlst.getId());
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsFinancialEvaluation> getFinancialEvaNo(PrmsFinancialEvaluation prmsFinancialEvaluation) {
        List<PrmsFinancialEvaluation> financialEvaluationList = new ArrayList();
        if (prmsFinancialEvaluation.getColumnName() != null && !prmsFinancialEvaluation.getColumnName().equals("")
                && prmsFinancialEvaluation.getColumnValue() != null && !prmsFinancialEvaluation.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_FINANCIAL_EVALUATION\n"
                    + "where " + prmsFinancialEvaluation.getColumnName().toLowerCase() + " = '" + prmsFinancialEvaluation.getColumnValue() + "'"
                    + "and " + prmsFinancialEvaluation.getPreparedBy() + "='" + prmsFinancialEvaluation.getPreparedBy() + "'", PrmsFinancialEvaluation.class);
            try {
                if (query.getResultList().size() > 0) {
                    financialEvaluationList = query.getResultList();
                }
                return financialEvaluationList;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsFinancialEvaluation.findByPreparedBy");
            query.setParameter("preparedBy", prmsFinancialEvaluation.getPreparedBy());
            financialEvaluationList = query.getResultList();
            return financialEvaluationList;
        }
    }

    public List<PrmsPortEntry> getShipKind() {
        try {
            Query query = em.createNamedQuery("PrmsThechnicalEvaluation.findAll", PrmsPortEntry.class);
            List<PrmsPortEntry> shipKindList = (List<PrmsPortEntry>) query.getResultList();

            return shipKindList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsThechincalEvaluationDet> getBidNum() {
        try {
            Query query = em.createNamedQuery("PrmsThechincalEvaluationDet.findByBidNum", PrmsThechincalEvaluationDet.class);
            List<PrmsThechincalEvaluationDet> evaluationDetList = (List<PrmsThechincalEvaluationDet>) query.getResultList();

            return evaluationDetList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsThechincalEvaluationDet> getItemListInProfrm(String proformNum) {
        System.out.println("----In item code----" + proformNum);
        List<PrmsThechincalEvaluationDet> itemList = null;
        Query query = em.createNativeQuery("SELECT PRMS_QUOTATION.QUOTATION_NO,\n"
                + "  PRMS_QUOTATION.QUATATION_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUAT_DET_ID,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.ITEM_REGISTRATION_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUANTITY\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_QUOTATION_DETAIL.QUAT_DET_ID = PRMS_THECHNICAL_EVALUATION.QUOTATION_DT_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "where prms_quotation.quotation_no = '" + proformNum + "'", PrmsThechincalEvaluationDet.class);
        try {
            itemList = (List<PrmsThechincalEvaluationDet>) query.getResultList();
            System.out.println("-----Out of item Code In Proforma---" + itemList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;

    }

    public List<PrmsQuotationDetail> getItemInQuoatation(String itemName) {
        System.out.println("----In item code----3" + itemName);
        List<PrmsQuotationDetail> itemList = null;
        Query query = em.createNativeQuery("SELECT PRMS_QUOTATION.QUOTATION_NO,\n"
                + "  PRMS_QUOTATION.QUATATION_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUAT_DET_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.SUP_ID,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUANTITY,\n"
                + "  PRMS_QUOTATION_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.ITEM_REGISTRATION_ID\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_QUOTATION_DETAIL.QUAT_DET_ID = PRMS_THECHNICAL_EVALUATION.QUOTATION_DT_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "where prms_quotation.quotation_no='" + itemName + "'", PrmsQuotationDetail.class);
        try {
            itemList = (List<PrmsQuotationDetail>) query.getResultList();
            System.out.println("-----Out of item Code In Proforma---3" + itemList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public List<PrmsBidDetail> getAwardType(String awardType) {
        System.out.println("in bid detail" + awardType);
        List<PrmsBidDetail> awardtypeList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "  PRMS_BID_DETAIL.ID AS ID1,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
                + "  PRMS_BID.REF_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "WHERE PRMS_BID.REF_NO = '" + awardType + "'", PrmsBidDetail.class);
        try {
            awardtypeList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----award gg---3" + awardtypeList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public PrmsBidDetail getPassLimit(String refNo) {
//        System.out.println("---pass Limit----" + refNo);
//        PrmsBidDetail bidTypeObj = null;
//        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
//                + "  PRMS_BID.BID_TYPE,\n"
//                + "  PRMS_BID_DETAIL.ID AS ID1,\n"
//                + "  PRMS_BID_DETAIL.AWARD_TYPE,\n"
//                + " PRMS_BID_DETAIL.PASS_LIMIT,\n"
//                + "  PRMS_BID_DETAIL.QUANTITY,\n"
//                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
//                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
//                + "  PRMS_THECHINCAL_EVALUATION_DET.ITEM_REGISTRATION_ID,\n"
//                + " PRMS_THECHINCAL_EVALUATION_DET.SUPPLIER_CODE\n"
//                + "FROM PRMS_BID\n"
//                + "INNER JOIN PRMS_BID_DETAIL\n"
//                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
//                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
//                + "ON PRMS_BID_DETAIL.ID = PRMS_THECHNICAL_EVALUATION.BID_DET_ID\n"
//                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
//                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
//                + "WHERE PRMS_BID.REF_NO = '" + refNo + "'", PrmsBidDetail.class);
//        try {
//            if (query.getResultList().size() > 0) {
//                bidTypeObj = (PrmsBidDetail) query.getResultList().get(0);
//            }
//            System.out.println("----uuuuuu---" + bidTypeObj);
//            return bidTypeObj;
//        } catch (Exception e) {
//            e.printStackTrace();
        return null;

//        }
    }

    public List<PrmsBidDetail> getQuantityFrmbdl(String quantity) {
        System.out.println("-----Bid no-----" + quantity);
        List<PrmsBidDetail> quantityList = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "  PRMS_BID_DETAIL.ID AS ID1,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
                + "  PRMS_BID_DETAIL.QUANTITY,\n"
                + "  PRMS_BID.REF_NO\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "where PRMS_BID.REF_NO='" + quantity + "'", PrmsBidDetail.class);
        try {
            quantityList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----award gg---3" + quantityList.size());
            return quantityList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<PrmsFinancialEvaluation> getFinancialOnList() {
        Query query = em.createNamedQuery("PrmsFinancialEvaluation.findByEvaluationReq", PrmsFinancialEvaluation.class);
        ArrayList<PrmsFinancialEvaluation> financialEvaluationList = new ArrayList(query.getResultList());
        return financialEvaluationList;
    }

    public List<FmsLuCurrency> getCurrency() {
        Query query = em.createNamedQuery("FmsLuCurrency.findAll", FmsLuCurrency.class);
        ArrayList<FmsLuCurrency> fmsLuCurrencyList = new ArrayList(query.getResultList());
        return fmsLuCurrencyList;
    }

    public List<PrmsFinancialEvaluation> getfinancialNoSeq(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsFinancialEvaluation.findByFinancialNum");
        query.setParameter("financialNum", prefix + "-" + "%" + "/" + eYear);
        List<PrmsFinancialEvaluation> prmsFinancialEvaluationList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            prmsFinancialEvaluationList = query.getResultList();
        }
        return prmsFinancialEvaluationList;
    }

    public List<PrmsFinancialEvaluation> getParamNameList() {
         Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('prms_financial_evaluation') \n"
                + "and column_name not in ('ID','BID_ID','STATUS','BID_TYPE','DESCRIPTION','DOCUMENT_ID','APPROVED_BY','PREPARED_BY','FILE_UPLOAD_REFNO','REMARK','TECHNICAL_EVALUATION_ID')");
        try {
            List<PrmsFinancialEvaluation> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
