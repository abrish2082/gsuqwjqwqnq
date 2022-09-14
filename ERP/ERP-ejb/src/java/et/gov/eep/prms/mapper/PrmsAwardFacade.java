package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidderRegistration;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfile;

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
public class PrmsAwardFacade extends AbstractFacade<PrmsAward> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsAwardFacade() {
        super(PrmsAward.class);
    }

    public ArrayList<PrmsAward> searchAwardByAwardId(PrmsAward award) {
        Query query = em.createNamedQuery("PrmsAward.SearchAwardId");
        query.setParameter("awardNo", award.getAwardNo() + '%');
        try {
            ArrayList<PrmsAward> awardList = new ArrayList(query.getResultList());
            return awardList;
        } catch (Exception ex) {
            return null;
        }

    }

    public PrmsAward searchAwardNo(PrmsAward papmsAward) {
        Query query = em.createNamedQuery("PrmsAward.findByAwardId");
        query.setParameter("awardId", papmsAward.getAwardId());
        try {
            PrmsAward awardno = (PrmsAward) query.getResultList().get(0);
            return awardno;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsAward getAwardNoinfo(PrmsAward papmsAward) {
        Query query = em.createNamedQuery("PrmsAward.findByAwardNo");
        query.setParameter("awardNo", papmsAward.getAwardNo());
        try {
            PrmsAward selectedobj = (PrmsAward) query.getResultList().get(0);

            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> searchVendorName(PrmsSupplyProfile eepVendorReg) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByVendorName", PrmsSupplyProfile.class);
        query.setParameter("vendorName", eepVendorReg.getVendorName() + '%');
        try {
            ArrayList<PrmsSupplyProfile> VendorNamelist = new ArrayList(query.getResultList());;

            return VendorNamelist;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> VendorNameList() {

        Query query = em.createNamedQuery("EepVendorReg.findAll");

        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsAward getLastPaymentNo() {

        Query query = em.createNamedQuery("PrmsAward.findByMaxBidOfferNum");

        try {
            PrmsAward directPurcObj = null;
            if (query.getResultList().size() > 0) {
                directPurcObj = (PrmsAward) query.getResultList().get(0);
            }
            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsAward> searchAward(PrmsAward prmsAward) {

        List<PrmsAward> prmsAwardLst = new ArrayList();
        if (prmsAward.getColumnName() != null && !prmsAward.getColumnName().equals("")
                && prmsAward.getColumnValue() != null && !prmsAward.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_AWARD\n"
                    + "where " + prmsAward.getColumnName().toLowerCase() + " = '" + prmsAward.getColumnValue() + "'"
                    + "and " + prmsAward.getPreparedBy() + "='" + prmsAward.getPreparedBy() + "'", PrmsAward.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsAwardLst = query.getResultList();
                }
                return prmsAwardLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsAward.findPreparedBy");
            query.setParameter("preparedBy", prmsAward.getPreparedBy());
            prmsAwardLst = query.getResultList();
            return prmsAwardLst;
        }
    }

    public PrmsAward getSelectedRequest(String id) {
        Query query = em.createNamedQuery("PrmsAward.findByAwardId");
        query.setParameter("awardId", id);
        try {
            PrmsAward selectrequest = (PrmsAward) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBid> BidNoFormFinancialResult() {

        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.*\n"
                + "FROM PRMS_FINANCIAL_EVAL_RESULT\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVAL_RESULT.BID_ID AND PRMS_FINANCIAL_EVAL_RESULT.STATUS=3 ", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromResult(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SUPPLY_PROFILE.*,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVAL_RESULT.BID_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID                        = PRMS_FINANCIAL_EVL_RESULTY_DTL.SUPPLIER_ID\n"
                + "WHERE PRMS_FINANCIAL_EVAL_RESULT.BID_ID= '" + bidNo.getId() + "'"
                + "AND PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK='1'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromPostQual(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SUPPLY_PROFILE.*,\n"
                + "  PRMS_POST_DETAIL.RESULTS\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_POST_DETAIL\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_POST_DETAIL.BIDDER_ID\n"
                + "INNER JOIN PRMS_POST_QUALIFICATION\n"
                + "ON PRMS_POST_QUALIFICATION.POST_ID = PRMS_POST_DETAIL.POST_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID                       = PRMS_POST_QUALIFICATION.BID_ID\n"
                + "WHERE PRMS_POST_QUALIFICATION.BID_ID='" + bidNo.getId() + "'"
                + "AND PRMS_POST_DETAIL.RESULTS         = 'Pass'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getfailedSupplierNameFromFinc(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT  PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_CODE,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_OFFICE,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_MOBILE,\n"
                + "  PRMS_SUPPLY_PROFILE.EMAIL\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.SUPPLIER_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID                                    = PRMS_FINANCIAL_EVAL_RESULT.BID_ID\n"
                + "WHERE PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK != 1\n"
                + "AND PRMS_FINANCIAL_EVAL_RESULT.BID_ID             = '" + bidNo.getId() + "'", PrmsSupplyProfile.class);
//          , 
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getFfailSupplierNameFromPostQual(PrmsBid bidNo) {
        System.out.println("______" + bidNo);
        Query query = em.createNativeQuery("SELECT  PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_CODE,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  PRMS_SUPPLY_PROFILE.VAT_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_TYPE,\n"
                + "  PRMS_SUPPLY_PROFILE.TIN_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_OFFICE,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_MOBILE,\n"
                + "  PRMS_SUPPLY_PROFILE.FAX,\n"
                + "  PRMS_SUPPLY_PROFILE.POBOX,\n"
                + "  PRMS_SUPPLY_PROFILE.EMAIL,\n"
                + "  PRMS_SUPPLY_PROFILE.COUNTRY_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.WEBSITE,\n"
                + "  PRMS_SUPPLY_PROFILE.HOUSE_NO\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_POST_DETAIL\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_POST_DETAIL.BIDDER_ID\n"
                + "INNER JOIN PRMS_POST_QUALIFICATION\n"
                + "ON PRMS_POST_QUALIFICATION.POST_ID = PRMS_POST_DETAIL.POST_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID  = PRMS_POST_QUALIFICATION.BID_ID\n"
                + "WHERE PRMS_POST_QUALIFICATION.BID_ID='" + bidNo.getId() + "'"
                + "AND PRMS_POST_DETAIL.RESULTS    ='Fail'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;
// + "WHERE PRMS_POST_QUALIFICATION.BID_ID='" + bidNo.getId() + "'"
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getFfailSupplierTechnicalEval(PrmsBid bidNo) {
        System.out.println("ths is tech______" + bidNo);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.EVALUATION,\n"
                + "  PRMS_THECHINCAL_EVALUATION_DET.SCORE,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_OFFICE,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_CODE,\n"
                + "  PRMS_SUPPLY_PROFILE.EMAIL,\n"
                + "  PRMS_SUPPLY_PROFILE.FAX,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_MOBILE,\n"
                + "  PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_THECHINCAL_EVALUATION_DET\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_THECHINCAL_EVALUATION_DET.SUPPLIER_ID\n"
                + "INNER JOIN PRMS_THECHNICAL_EVALUATION\n"
                + "ON PRMS_THECHNICAL_EVALUATION.EVALUATION_ID = PRMS_THECHINCAL_EVALUATION_DET.THECHNICAL_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_THECHNICAL_EVALUATION.BID_ID\n"
                + "INNER JOIN PRMS_BID_DETAIL\n"
                + "ON PRMS_BID.ID                               = PRMS_BID_DETAIL.BID_ID\n"
                + "WHERE (PRMS_THECHINCAL_EVALUATION_DET.SCORE  < PRMS_BID_DETAIL.PASS_LIMIT\n"
                + "OR PRMS_THECHINCAL_EVALUATION_DET.EVALUATION = 'Fail')\n"
                + "AND PRMS_THECHNICAL_EVALUATION.BID_ID        ='" + bidNo.getId() + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsFinancialEvaluaDetail> getQuantityAndUnitPrice(PrmsSupplyProfile prmsSupplyProfile) {

        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVALUA_DETAIL.ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.QUANTITY,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.BID_DETAIL_ID\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "ON PRMS_FINANCIAL_EVALUA_DETAIL.ID             = PRMS_FINANCIAL_EVL_RESULTY_DTL.FINANCIAL_EVALUATION_DTL_ID\n"
                + "WHERE PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID = '" + prmsSupplyProfile.getId() + "'", PrmsFinancialEvaluaDetail.class);
        try {
            ArrayList<PrmsFinancialEvaluaDetail> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsAward> searchawardBystatus() {

        Query query = em.createNamedQuery("PrmsAward.findAllByStatus");

        try {
            ArrayList<PrmsAward> searchawardbystus = new ArrayList(query.getResultList());

            return searchawardbystus;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsFinancialEvaluaDetail> getQuantityAndUnitPriceForAward(PrmsAward prmsAward) {
        Query query = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVALUA_DETAIL.*,"
                + "PRMS_FINANCIAL_EVALUA_DETAIL.QUANTITY,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.UNIT_PRICE,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.SERVICE_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.ITEM_REGISTRATION_ID,\n"
                + "  PRMS_FINANCIAL_EVALUA_DETAIL.ID\n"
                + "FROM PRMS_FINANCIAL_EVALUA_DETAIL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVALUATION\n"
                + "ON PRMS_FINANCIAL_EVALUATION.ID = PRMS_FINANCIAL_EVALUA_DETAIL.FINANCIAL_EVALUATION_ID,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT\n"
                + "INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "WHERE PRMS_FINANCIAL_EVALUA_DETAIL.SUPPLIER_ID = '" + prmsAward.getSuppId().getId() + "' AND PRMS_FINANCIAL_EVAL_RESULT.BID_ID = '" + prmsAward.getBidId().getId() + "'", PrmsFinancialEvaluaDetail.class
        );

        try {
            ArrayList<PrmsFinancialEvaluaDetail> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsAward> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_AWARD') \n"
                + "and column_name not in ('AWARD_ID','DOCUMENT_ID','CURRENT_STATUS','FEXFILEREFNUMBER','CURRENCY_ID','SUPP_ID','BID_ID','STATUS','REMARK')");
        try {
            List<PrmsAward> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
