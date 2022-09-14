/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class PrmsFinancialEvalResultFacade extends AbstractFacade<PrmsFinancialEvalResult> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsFinancialEvalResultFacade() {
        super(PrmsFinancialEvalResult.class);
    }

    public List<PrmsBid> getBidNo() {
        List<PrmsBid> bidNumList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.BID_CATEGORY\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID                          = PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID\n"
                + "WHERE PRMS_BID.AWARD_TYPE                      = 'Lot Base'\n"
                + "AND (PRMS_THECHINCAL_EVALUATION_DET.EVALUATION = 'Pass'\n"
                + "OR PRMS_THECHINCAL_EVALUATION_DET.SCORE        > PRMS_BID_DETAIL.PASS_LIMIT)", PrmsBid.class);
        try {
            bidNumList = (List<PrmsBid>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNumList;
    }

    public List<PrmsQuotation> getProformaNum() {
        List<PrmsQuotation> quotationList = null;
        Query query = em.createNativeQuery("SELECT *\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID", PrmsQuotation.class);
        try {
            quotationList = (List<PrmsQuotation>) query.getResultList();
            return quotationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MmsItemRegistration> getProfomaNumList(String mmsItemRegistration) {
        System.out.println("----In item code----3" + mmsItemRegistration);
        List<MmsItemRegistration> itemList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_QUOTATION_DETAIL.ITEM_REG_ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_QUOTATION_DETAIL.ITEM_REG_ID\n"
                + "WHERE PRMS_QUOTATION.QUOTATION_NO    = '" + mmsItemRegistration + "'", MmsItemRegistration.class);
        try {
            itemList = (List<MmsItemRegistration>) query.getResultList();
            System.out.println("-----Out of item Code In Proforma---3" + itemList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public List<PrmsQuotationDetail> getSupplierList(String supplierName) {
        System.out.println("----In supplier List----3" + supplierName);
        List<PrmsQuotationDetail> supplierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  PRMS_QUOTATION_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_QUOTATION_DETAIL.SUP_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.QUAT_DET_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.ITEM_REG_ID\n"
                + "FROM PRMS_QUOTATION\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_QUOTATION_DETAIL.SUP_ID\n"
                + "where  PRMS_QUOTATION_DETAIL.ITEM_REG_ID='" + supplierName + "'", PrmsQuotationDetail.class
        );
        try {
            supplierList = (List<PrmsQuotationDetail>) query.getResultList();
            System.out.println("out of supplier List" + supplierList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    public List<PrmsBidDetail> getItemLotPack() {
        System.out.println("------in itemLot List------");
        List<PrmsBidDetail> itemLotPacList = null;
        Query query = em.createNativeQuery("SELECT distinct PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID_DETAIL.ID AS ID1,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
                + "  PRMS_FINANCIAL_EVALUATION.ID    AS ID2,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.ID AS ID3,\n"
                + "  PRMS_BID_DETAIL.AWARD_TYPE\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_FINANCIAL_EVALUATION.TECHNICAL_EVALUATION_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_FINANCIAL_EVALUATION.ID = PRMS_FINANCIAL_EVALUA_DETAIL.FINANCIAL_EVALUATION_ID\n"
                + "where  PRMS_BID_DETAIL.AWARD_TYPE='Lot Base'", PrmsBidDetail.class);
        try {
            itemLotPacList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("out of itemLotList" + itemLotPacList.size());
        } catch (Exception e) {
        }
        return itemLotPacList;
    }

    public List<PrmsFinancialEvaluaDetail> getLotNm(PrmsBidDetail prmsBidDetail) {
        System.out.println("------in Lot List------" + prmsBidDetail.getId());
        List<PrmsFinancialEvaluaDetail> lotList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_FINANCIAL_EVALUA_DETAIL.ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID,\n"
                + "PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID,\n"
                + "  PRMS_BID_DETAIL.FERM_SELTION_METHD,\n"
                + "  PRMS_BID_DETAIL.ID AS ID1\n"
                + "FROM PRMS_BID_DETAIL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "WHERE PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID = '" + prmsBidDetail.getId() + "'", PrmsFinancialEvaluaDetail.class);
        try {
            lotList = (List<PrmsFinancialEvaluaDetail>) query.getResultList();
            System.out.println("-----Out of list----" + lotList.size());
        } catch (Exception e) {
        }
        return lotList;
    }

    public List<String> getEvalutionResult(PrmsBid prmsBidDetail) {
        System.out.println("hhhhh" + prmsBidDetail.getId());
        List<String> resultList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID,\n"
                + "PRMS_BID.ID\n"
                + "FROM PRMS_BID_DETAIL\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID\n"
                + "where  prms_bid.id='" + prmsBidDetail.getId() + "'", PrmsBid.class);
        try {
            resultList = (List<String>) query.getResultList();
            System.out.println("siz" + resultList.size());
        } catch (Exception e) {
        }
        return resultList;
    }

    public List<PrmsFinancialEvaluaDetail> getListofUnitPrice(PrmsBidDetail prmsBidDetail, PrmsSupplyProfile prmsSupplyProfile) {
        System.out.println("yyyyy" + prmsBidDetail.getBidId());
        List<PrmsFinancialEvaluaDetail> resultList = null;
        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVALUA_DETAIL.ID, PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID, PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE \n"
                + "FROM PRMS_FINANCIAL_EVALUA_DETAIL \n"
                + "WHERE PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID = '" + prmsBidDetail.getId() + "' AND PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID = '" + prmsSupplyProfile.getId() + "'", PrmsFinancialEvaluaDetail.class);
        try {
            resultList = (List<PrmsFinancialEvaluaDetail>) query.getResultList();
            System.out.println("yyyyy" + resultList.size());
        } catch (Exception e) {
        }
        return resultList;
    }

    public List<PrmsBidDetail> getBidDetailInfoForItemBased(PrmsBid bid) {
        List<PrmsBidDetail> itemBaseList = null;
        Query query = em.createNativeQuery("SELECT bidDtl.*\n"
                + "FROM PRMS_BID_DETAIL bidDtl\n"
                + "INNER JOIN PRMS_BID bid\n"
                + "ON bid.ID = bidDtl.BID_ID\n"
                + "where bid.ref_no ='" + bid.getRefNo() + "' and biddtl.award_type='Item Base'", PrmsBidDetail.class);
        try {

            itemBaseList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----Out of list----" + itemBaseList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseList;
    }

    public List<PrmsBidDetail> getItemBase() {
        List<PrmsBidDetail> itemBaseList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.REF_NO,\n"
                + "  PRMS_BID_DETAIL.AWARD_TYPE,\n"
                + "  PRMS_BID.ID,\n"
                + "  PRMS_BID_DETAIL.ITEM_REG_ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "where PRMS_BID_DETAIL.AWARD_TYPE='Item Base'", PrmsBidDetail.class);
        try {

            itemBaseList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----Out of list----" + itemBaseList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseList;
    }

    public List<PrmsFinancialEvaluaDetail> getItemBaseRank(String suppRank) {
        System.out.println("-----supplier Rank----" + suppRank);
        List<PrmsFinancialEvaluaDetail> itemBaseRankList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "  PRMS_BID_DETAIL.ID AS ID1,\n"
                + "  PRMS_THECHNICAL_EVALUATION.EVALUATION_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.ID AS ID2,\n"
                + "  PRMS_SUPPLY_PROFILE.ID          AS ID3,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_FINANCIAL_EVALUATION.TECHNICAL_EVALUATION_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_FINANCIAL_EVALUATION.ID = PRMS_FINANCIAL_EVALUA_DETAIL.FINANCIAL_EVALUATION_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID  = PRMS_THECHINCAL_EVALUATION_DET.SUPPLIER_ID\n"
                + "AND PRMS_SUPPLY_PROFILE.ID = PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID\n"
                + "where PRMS_BID.ID='" + suppRank + "'", PrmsFinancialEvaluaDetail.class);

        try {

            itemBaseRankList = (List<PrmsFinancialEvaluaDetail>) query.getResultList();
            System.out.println("-----Out of supplier Rank----" + itemBaseRankList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseRankList;

    }

    public List<PrmsFinancialEvalResult> getFinancialResult(PrmsFinancialEvalResult prmsFinancialEvalResult) {
        List<PrmsFinancialEvalResult> financialEvalResultsLst = new ArrayList();
        if (prmsFinancialEvalResult.getColumnName() != null && !prmsFinancialEvalResult.getColumnName().equals("")
                && prmsFinancialEvalResult.getColumnValue() != null && !prmsFinancialEvalResult.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_FINANCIAL_EVAL_RESULT\n"
                    + "where " + prmsFinancialEvalResult.getColumnName().toLowerCase() + " = '" + prmsFinancialEvalResult.getColumnValue() + "'"
                    + "and " + prmsFinancialEvalResult.getPreparedBy() + "='" + prmsFinancialEvalResult.getPreparedBy() + "'", PrmsFinancialEvalResult.class);
            try {
                if (query.getResultList().size() > 0) {
                    financialEvalResultsLst = query.getResultList();
                }
                return financialEvalResultsLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsFinancialEvalResult.findByPreparedBy");
            query.setParameter("preparedBy", prmsFinancialEvalResult.getPreparedBy());
            financialEvalResultsLst = query.getResultList();
            return financialEvalResultsLst;
        }
    }

    public List<PrmsFinancialEvaluaDetail> getItemsForCompliance(PrmsBidDetail bidDetail) {
        List<PrmsFinancialEvaluaDetail> itemBaseList = null;
        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVALUA_DETAIL.ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.QUANTITY,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.ITEM_REGISTRATION_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID\n"
                + "FROM PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "where PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID='" + bidDetail.getId() + "'", PrmsFinancialEvaluaDetail.class);
        try {

            itemBaseList = (List<PrmsFinancialEvaluaDetail>) query.getResultList();
            System.out.println("-----Out of list----" + itemBaseList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseList;
    }

    public List<PrmsBidDetail> getBidDetailInfoForLotBased(PrmsBid bid) {
        System.out.println("-----hhhh----" + bid.getId());
        List<PrmsBidDetail> prmsBidDetailList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID_DETAIL.LOT_NAME,\n"
                + "  PRMS_BID_DETAIL.BID_ID,\n"
                + " PRMS_BID.BID_CATEGORY,\n"
                + "PRMS_BID_DETAIL.FERM_SELTION_METHD,\n"
                + "  PRMS_BID.ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_BID.ID               = PRMS_FINANCIAL_EVALUATION.BID_ID\n"
                + "WHERE PRMS_BID_DETAIL.BID_ID = '" + bid.getId() + "'\n"
                + "AND PRMS_BID.AWARD_TYPE      = 'Lot Base'", PrmsBidDetail.class);
        try {

            prmsBidDetailList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----Out of list----" + prmsBidDetailList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prmsBidDetailList;
    }

    public List<PrmsBidDetail> getBidDetailInfoMaterialForLotBased(PrmsBid bid, String lotNo) {
        List<PrmsBidDetail> itemBaseList = null;
        Query query = em.createNativeQuery("SELECT biddtl.*\n"
                + "FROM PRMS_BID_DETAIL bidDtl\n"
                + "INNER JOIN PRMS_BID bid\n"
                + "ON bid.ID = bidDtl.BID_ID\n"
                + "where bid.ref_no ='" + bid.getRefNo() + "' and biddtl.award_type='Lot Base' and biddtl.lot_name='" + lotNo + "'", PrmsBidDetail.class);
        try {

            itemBaseList = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----Out of list----" + itemBaseList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseList;
    }

    public List<PrmsFinancialEvaluaDetail> getItemsForMerit(PrmsBidDetail bidDetail) {
        List<PrmsFinancialEvaluaDetail> itemBaseList = null;
        System.out.println("............bidDtl id...........@FFF...." + bidDetail.getId());
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_FINANCIAL_EVALUA_DETAIL.ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.QUANTITY,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.ITEM_REGISTRATION_ID,\n"
                + "  PRMS_BID_DETAIL.PASS_LIMIT,\n"
                + "  PRMS_BID_DETAIL.TECHNICAL,\n"
                + "  PRMS_BID_DETAIL.FINANCIAL\n"
                + "FROM PRMS_BID_DETAIL\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID                           = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "WHERE PRMS_BID_DETAIL.PASS_LIMIT               <= PRMS_THECHINCAL_EVALUATION_DET.SCORE\n"
                + "AND PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID = '" + bidDetail.getId() + "'", PrmsFinancialEvaluaDetail.class);
        try {

            itemBaseList = (List<PrmsFinancialEvaluaDetail>) query.getResultList();
            System.out.println("-----Out of list-asdf" + itemBaseList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseList;
    }

    public List<PrmsThechincalEvaluationDet> getItemsForMeritForTech(PrmsBidDetail bidDetail) {
        List<PrmsThechincalEvaluationDet> itemBaseList = null;
        System.out.println("............bidDtl id...........@FFF...." + bidDetail.getId());
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID_DETAIL.TECHNICAL,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION_DETAIL_ID\n"
                + "FROM PRMS_BID_DETAIL\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID                             = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "WHERE PRMS_BID_DETAIL.PASS_LIMIT                 <= PRMS_THECHINCAL_EVALUATION_DET.SCORE\n"
                + "AND PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID = '" + bidDetail.getId() + "'", PrmsThechincalEvaluationDet.class);
        try {

            itemBaseList = (List<PrmsThechincalEvaluationDet>) query.getResultList();
            System.out.println("-----Out of list-asdf" + itemBaseList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseList;
    }

    public List<MmsItemRegistration> getItemBasedItemName(MmsItemRegistration mmsItemRegistration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<PrmsBidDetail> getItemBasedList(PrmsBid prmsBid) {
        List<PrmsBidDetail> selectRefNo = null;
        System.out.println("............bidDtl id...........@FFF...." + prmsBid.getId());
        Query query = em.createNativeQuery("SELECT DISTINCT prms_bid_detail.ID,\n"
                + "  prms_bid_detail.ITEM_REG_ID,\n"
                + "  prms_bid_detail.FERM_SELTION_METHD\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVALUATION.BID_ID\n"
                + "INNER JOIN prms_financial_evalua_detail\n"
                + "ON PRMS_FINANCIAL_EVALUATION.ID = prms_financial_evalua_detail.FINANCIAL_EVALUATION_ID\n"
                + "INNER JOIN prms_bid_detail\n"
                + "ON PRMS_BID.ID    = prms_bid_detail.BID_ID\n"
                + "WHERE PRMS_BID.ID ='" + prmsBid.getId() + "'", PrmsBidDetail.class);
        try {

            selectRefNo = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----list of f" + selectRefNo.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectRefNo;
    }

    public List<PrmsBid> getBidNoFrItemBased() {
        List<PrmsBid> bidNumList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.BID_CATEGORY\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID                          = PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID\n"
                + "WHERE PRMS_BID.AWARD_TYPE                      = 'Item Base'\n"
                + "AND (PRMS_THECHINCAL_EVALUATION_DET.EVALUATION = 'Pass'\n"
                + "OR PRMS_THECHINCAL_EVALUATION_DET.SCORE        > PRMS_BID_DETAIL.PASS_LIMIT)", PrmsBid.class);
        try {
            bidNumList = (List<PrmsBid>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNumList;
    }

    public List<PrmsSupplyProfile> getsupplierId(PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail) {
        List<PrmsSupplyProfile> supplierName = null;
        System.out.println("............bidDtl id...........@FFF...." + prmsFinancialEvaluaDetail.getId());
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SUPPLY_PROFILE.ID\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_THECHINCAL_EVALUATION_DET.SUPPLIER_ID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID = PRMS_THECHINCAL_EVALUATION_DET.BID_DETAIL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID\n"
                + "AND PRMS_BID_DETAIL.ID    = PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID\n"
                + "WHERE PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID = '" + prmsFinancialEvaluaDetail.getId() + "'", PrmsSupplyProfile.class);
        try {

            supplierName = (List<PrmsSupplyProfile>) query.getResultList();
            System.out.println("-----list of f" + supplierName.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierName;
    }

    public List<PrmsFinancialEvaluaDetail> getListOfSpplierPrice(PrmsBid prmsBid, PrmsSupplyProfile prmsSupplyProfile) {
        System.out.println("ggggg" + prmsBid.getId());
        List<PrmsFinancialEvaluaDetail> resultList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID_DETAIL.BID_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE,\n"
                + "PRMS_FINANCIAL_EVALUA_DETAIL.LOT_NO,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.ID,\n"
                + "  PRMS_BID_DETAIL.TECHNICAL,\n"
                + "PRMS_FINANCIAL_EVALUA_DETAIL.LOT_NO,\n"
                + "  PRMS_BID_DETAIL.FINANCIAL\n"
                + "FROM PRMS_BID_DETAIL\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_BID_DETAIL.BID_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_BID_DETAIL.ID                        = PRMS_FINANCIAL_EVALUA_DETAIL.BID_DETAIL_ID\n"
                + "WHERE PRMS_BID.ID                           = '" + prmsBid.getId() + "'\n"
                + "AND PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID = '" + prmsSupplyProfile.getId() + "'", PrmsFinancialEvaluaDetail.class);
        try {
            resultList = (List<PrmsFinancialEvaluaDetail>) query.getResultList();
            System.out.println("xxxx" + resultList.size());
        } catch (Exception e) {
        }
        return resultList;
    }

    public List<PrmsFinancialEvalResult> getFinancialOnList() {
        Query query = em.createNamedQuery("PrmsFinancialEvalResult.findByStatus", PrmsFinancialEvalResult.class);
        ArrayList<PrmsFinancialEvalResult> financialEvalResults = new ArrayList(query.getResultList());
        return financialEvalResults;
    }

    public List<PrmsServiceAndWorkReg> getServiceFrmQuotation(String ServiceNames) {
        System.out.println("in service" + ServiceNames);
        List<PrmsServiceAndWorkReg> serviceName = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_QUOTATION_DETAIL.SERV_AND_WORK_ID AS SERV_AND_WORK_ID1\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_QUOTATION_DETAIL.SERV_AND_WORK_ID\n"
                + "INNER JOIN PRMS_QUOTATION\n"
                + "ON PRMS_QUOTATION.QUATATION_ID     = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "WHERE PRMS_QUOTATION.PURCHASE_TYPE = 'Service'\n"
                + "AND PRMS_QUOTATION.QUOTATION_NO    = '" + ServiceNames + "'", PrmsServiceAndWorkReg.class);
        try {
            serviceName = (List<PrmsServiceAndWorkReg>) query.getResultList();
            System.out.println("-----Out of item Code In Proforma--s-3" + serviceName.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceName;
    }

    public List<PrmsQuotationDetail> getSupplierListFrService(String prmsServiceAndWorkReg) {
        System.out.println("----In supplier List----3" + prmsServiceAndWorkReg);
        List<PrmsQuotationDetail> supplierList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_QUOTATION_DETAIL.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID AS SERV_AND_WORK_ID1,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_QUOTATION_DETAIL.QUAT_DET_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.SUP_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.UNIT_PRICE\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_QUOTATION_DETAIL.SERV_AND_WORK_ID\n"
                + "INNER JOIN PRMS_QUOTATION\n"
                + "ON PRMS_QUOTATION.QUATATION_ID = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "where PRMS_QUOTATION_DETAIL.SERV_AND_WORK_ID='" + prmsServiceAndWorkReg + "'", PrmsQuotationDetail.class);

        try {
            supplierList = (List<PrmsQuotationDetail>) query.getResultList();
            System.out.println("out of supplier List" + supplierList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    public List<PrmsServiceAndWorkReg> getWorkFrmQuotation(String workName) {
        System.out.println("in service" + workName);
        List<PrmsServiceAndWorkReg> serviceName = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_QUOTATION_DETAIL.SERV_AND_WORK_ID AS SERV_AND_WORK_ID1,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "INNER JOIN PRMS_QUOTATION_DETAIL\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_QUOTATION_DETAIL.SERV_AND_WORK_ID\n"
                + "INNER JOIN PRMS_QUOTATION\n"
                + "ON PRMS_QUOTATION.QUATATION_ID     = PRMS_QUOTATION_DETAIL.QUOTATION_ID\n"
                + "WHERE PRMS_QUOTATION.PURCHASE_TYPE = 'Work'\n"
                + "AND PRMS_QUOTATION.QUOTATION_NO    = '" + workName + "'", PrmsServiceAndWorkReg.class);
        try {
            serviceName = (List<PrmsServiceAndWorkReg>) query.getResultList();
            System.out.println("-----Out of item Code In Proforma--s-3" + serviceName.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceName;
    }

    public List<PrmsBidDetail> getItemBasedServiceList(PrmsBid prmsBid) {
        List<PrmsBidDetail> selectRefNo = null;
        System.out.println("............bidDtl id...........@FFF...." + prmsBid.getId());
        Query query = em.createNativeQuery("SELECT DISTINCT prms_bid_detail.ID,\n"
                + "  prms_bid_detail.SERVICE_ID,\n"
                + "  prms_bid_detail.FERM_SELTION_METHD\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVALUATION.BID_ID\n"
                + "INNER JOIN prms_financial_evalua_detail\n"
                + "ON PRMS_FINANCIAL_EVALUATION.ID = prms_financial_evalua_detail.FINANCIAL_EVALUATION_ID\n"
                + "INNER JOIN prms_bid_detail\n"
                + "ON PRMS_BID.ID    = prms_bid_detail.BID_ID\n"
                + "WHERE PRMS_BID.ID ='" + prmsBid.getId() + "'", PrmsBidDetail.class);
        try {

            selectRefNo = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----list of f" + selectRefNo.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectRefNo;
    }

    public List<PrmsBidDetail> getItemBasedWorkList(PrmsBid prmsBid) {
        List<PrmsBidDetail> selectRefNo = null;
        System.out.println("............bidDtl id...........@FFF...." + prmsBid.getId());
        Query query = em.createNativeQuery("SELECT DISTINCT prms_bid_detail.ID,\n"
                + "  prms_bid_detail.SERVICE_ID,\n"
                + "  prms_bid_detail.FERM_SELTION_METHD\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVALUATION.BID_ID\n"
                + "INNER JOIN prms_financial_evalua_detail\n"
                + "ON PRMS_FINANCIAL_EVALUATION.ID = prms_financial_evalua_detail.FINANCIAL_EVALUATION_ID\n"
                + "INNER JOIN prms_bid_detail\n"
                + "ON PRMS_BID.ID    = prms_bid_detail.BID_ID\n"
                + "WHERE PRMS_BID.ID ='" + prmsBid.getId() + "'", PrmsBidDetail.class);
        try {

            selectRefNo = (List<PrmsBidDetail>) query.getResultList();
            System.out.println("-----list of f" + selectRefNo.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectRefNo;
    }

    public List<PrmsFinancialEvalResult> getFromYears() {
        List<PrmsFinancialEvalResult> financialEvalResultYearLst = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVAL_RESULT.DATE_REG,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.ID,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.FINANCIAL_RESULT_NO\n"
                + "FROM PRMS_FINANCIAL_EVAL_RESULT\n"
                + "INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID", PrmsFinancialEvalResult.class);
        try {
            financialEvalResultYearLst = new ArrayList<>(query.getResultList());
            return financialEvalResultYearLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get FinancialEvalResult List By Dates and ItemId
    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndItemId(Date startdate, Date endDate, MmsItemRegistration itemName) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String firstDate = df.format(startdate);
        String secondDate = df.format(endDate);
        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVAL_RESULT.ID,\n"
                + "   PRMS_FINANCIAL_EVAL_RESULT.DATE_REG,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.BID_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.TOTAL_PRICE\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "WHERE PRMS_FINANCIAL_EVAL_RESULT.DATE_REG BETWEEN TO_DATE('" + firstDate + "', 'dd/mm/yyyy')\n"
                + "AND TO_DATE('" + secondDate + "', 'dd/mm/yyyy')\n"
                + "AND PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK = '1'\n"
                + "AND PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID= '" + itemName.getMaterialId() + "'\n"
                + "ORDER BY PRMS_FINANCIAL_EVAL_RESULT.BID_ID DESC", PrmsFinancialEvalResult.class);
        try {
            List<PrmsFinancialEvalResult> financialEvalResults = new ArrayList<>(query.getResultList());
            return financialEvalResults;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get FinancialEvalResult List By Dates and Service Id
    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndServiceId(Date startdate, Date endDate, PrmsServiceAndWorkReg prmsServiceAndWorkReg) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String firstDate = df.format(startdate);
        String secondDate = df.format(endDate);
        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.TOTAL_PRICE,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID,\n"
                + "PRMS_FINANCIAL_EVAL_RESULT.ID,\n"
                + " PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = '" + prmsServiceAndWorkReg.getServAndWorkId() + "'\n"
                + "AND PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK   = 1\n"
                + "AND PRMS_FINANCIAL_EVAL_RESULT.DATE_REG BETWEEN TO_DATE('" + firstDate + "', 'dd/mm/yyyy')\n"
                + "AND TO_DATE('" + secondDate + "', 'dd/mm/yyyy')", PrmsFinancialEvalResult.class);
        try {
            List<PrmsFinancialEvalResult> financialEvalResults = new ArrayList<>(query.getResultList());
            return financialEvalResults;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get FinancialEvalResult List By Dates and Work Id
    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndWorkId(Date startdate, Date endDate, PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateOne = df.format(startdate);
        String dateTwo = df.format(endDate);
        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.TOTAL_PRICE,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.WORK_NAME\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = '" + prmsServiceAndWorkReg.getServAndWorkId() + "'\n"
                + "AND PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK   = 1\n"
                + "AND PRMS_FINANCIAL_EVAL_RESULT.DATE_REG BETWEEN TO_DATE('" + dateOne + "', 'dd/mm/yyyy')\n"
                + "AND TO_DATE('" + dateTwo + "', 'dd/mm/yyyy')", PrmsFinancialEvalResult.class);
        try {
            List<PrmsFinancialEvalResult> financialEvalResults = new ArrayList<>(query.getResultList());
            return financialEvalResults;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsFinancialEvalResult> getfinancialResltNumSeq(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsFinancialEvalResult.findByResultNum");
        query.setParameter("financialResultNo", prefix + "-" + "%" + "/" + eYear);
        List<PrmsFinancialEvalResult> prmsFinancialResultEvaluationList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            prmsFinancialResultEvaluationList = query.getResultList();
        }
        return prmsFinancialResultEvaluationList;
    }

    public List<PrmsFinancialEvalResult> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_FINANCIAL_EVAL_RESULT') \n"
                + "and column_name not in ('ID','FINANCIALEVA_ID','REMARK','QUOTATION_ID','BID_ID','STATUS')");
        try {
            List<PrmsFinancialEvalResult> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
