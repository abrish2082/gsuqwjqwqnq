/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsBidSale;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidderRegistration;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrmsBidderRegistrationFacade extends AbstractFacade<PrmsBidderRegistration> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsBidderRegistrationFacade() {
        super(PrmsBidderRegistration.class);
    }

    public PrmsBidderRegistration getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsBidderRegistration.findByBidderRegId");
        query.setParameter("bidderRegId", id);

        try {
            PrmsBidderRegistration selectrequest = (PrmsBidderRegistration) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsBidderRegistration LastCheckListNo() {

        Query query = em.createNamedQuery("PrmsBidderRegistration.findByMaxBidderNum");
        PrmsBidderRegistration result = null;

        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsBidderRegistration) query.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsBidderRegistration> searchBidderReg(PrmsBidderRegistration prmsBidderRegistration) {
       if (prmsBidderRegistration.getColumnValue() != null && prmsBidderRegistration.getColumnName() != null
                && !prmsBidderRegistration.getColumnValue().equals("") && !prmsBidderRegistration.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_BIDDER_REGISTRATION\n"
                    + "where " + prmsBidderRegistration.getColumnName().toLowerCase() + " = '" + prmsBidderRegistration.getColumnValue() + "' "
                    + "and " + prmsBidderRegistration.getPreparedBy() + "='" + prmsBidderRegistration.getPreparedBy() + "' ", PrmsBidderRegistration.class);
            try {
                List<PrmsBidderRegistration> prmsBidderRegistrationList = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    prmsBidderRegistrationList = query.getResultList();
                }
                return prmsBidderRegistrationList;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsBidderRegistration.findByPreparedBy");
            query.setParameter("preparedBy", prmsBidderRegistration.getPreparedBy());
            return query.getResultList();
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

    public PrmsBid getBidDates(PrmsBid eepBidReg) {
        Query query = em.createNamedQuery("PrmsBid.findByRefNos");
        query.setParameter("refNo", eepBidReg.getRefNo());
        try {
            PrmsBid eepBidRegList = (PrmsBid) (query.getResultList().get(0));
            return eepBidRegList;
        } catch (Exception ex) {
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

    public FmsBidSale getsuppList(String vendorName) {
        Query query = em.createNamedQuery("FmsBidSale.findByRefNos", FmsBidSale.class);
        query.setParameter("vendorName", vendorName);
        try {
            FmsBidSale eepBidRegList = (FmsBidSale) (query.getResultList().get(0));
            return eepBidRegList;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsBid> getRistrictedBid() {
        System.err.println("hfhfg9");
        Query query = em.createNamedQuery("PrmsBid.findByPurchaseMethods");
        try {
            ArrayList<PrmsBid> ristrictedBid = new ArrayList(query.getResultList());
            System.err.println("hhhh" + ristrictedBid.size());
            return ristrictedBid;

        } catch (Exception ex) {
            return null;
        }
    }

    

    public FmsBidSale getRecietNo(FmsBidSale fmsBidSale) {
        Query query = em.createNamedQuery("FmsBidSale.findByRefNos");
        query.setParameter("vendorName", fmsBidSale.getBidderId().getVendorName());
        try {
            FmsBidSale eepBidRegList = (FmsBidSale) (query.getResultList().get(0));
            return eepBidRegList;
        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsSupplyProfile getVendorForDoc(PrmsSupplyProfile prmsSupplyProfForDoc) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByVendNam");
        query.setParameter("vendorName", prmsSupplyProfForDoc.getVendorName());
        try {
            PrmsSupplyProfile vendor = (PrmsSupplyProfile) (query.getResultList().get(0));
            return vendor;
        } catch (Exception ex) {
            return null;
        }

    }

    public FmsBidSale getRecietNumber(FmsBidSale fmsBidSale) {

        System.out.println("_____" + fmsBidSale);
        Query query = em.createNativeQuery(" SELECT FMS_BID_SALE.BID_SALE_ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  FMS_BID_SALE.BID_SALE_ID,\n"
                + "  FMS_BID_SALE.BIDDER_ID,\n"
                + "  FMS_BID_SALE.BID_ID,\n"
                + "  FMS_BID_SALE.CASH_RECEIPT,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.FMS_VOUCHER_VOUCHER_ID,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.CASH_RECEIPT_VOUCHER_ID\n"
                + "FROM FMS_BID_SALE\n"
                + "INNER JOIN FMS_CASH_RECEIPT_VOUCHER\n"
                + "ON FMS_CASH_RECEIPT_VOUCHER.CASH_RECEIPT_VOUCHER_ID = FMS_BID_SALE.CASH_RECEIPT\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID          = FMS_BID_SALE.BID_ID\n"
                + "WHERE FMS_BID_SALE.BIDDER_ID = '" + fmsBidSale.getBidderId().getId() + "' AND FMS_BID_SALE.BID_ID =  '" + fmsBidSale.getBidId().getId() + "'", FmsBidSale.class);
        try {
            FmsBidSale receipt = (FmsBidSale) (query.getResultList().get(0));
            return receipt;
        } catch (Exception ex) {
            return null;
        }
    }

    public FmsVoucher getRecietNo(FmsVoucher fmsVoucher) {
        System.out.println("____" + fmsVoucher);
        Query query = em.createNativeQuery("SELECT FMS_BID_SALE.BID_SALE_ID,\n"
                + "  FMS_BID_SALE.BIDDER_ID,\n"
                + "  FMS_BID_SALE.CASH_RECEIPT,\n"
                + "  FMS_BID_SALE.BID_ID,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.CASH_RECEIPT_VOUCHER_ID,\n"
                + "  FMS_VOUCHER.VOUCHER_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  PRMS_SUPPLY_PROFILE.ID,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.FMS_VOUCHER_VOUCHER_ID\n"
                + "FROM FMS_BID_SALE\n"
                + "INNER JOIN FMS_CASH_RECEIPT_VOUCHER\n"
                + "ON FMS_CASH_RECEIPT_VOUCHER.CASH_RECEIPT_VOUCHER_ID = FMS_BID_SALE.CASH_RECEIPT\n"
                + "INNER JOIN FMS_VOUCHER\n"
                + "ON FMS_VOUCHER.VOUCHER_ID = FMS_CASH_RECEIPT_VOUCHER.FMS_VOUCHER_VOUCHER_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = FMS_BID_SALE.BIDDER_ID\n"
                + "where PRMS_SUPPLY_PROFILE.VENDOR_NAME='" + fmsVoucher.getVoucherId() + "'", FmsVoucher.class);
        try {
            FmsVoucher receipt = (FmsVoucher) (query.getResultList().get(0));
            return receipt;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsSupplyProfile> findSuppliers() {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findAll");
        try {
            List<PrmsSupplyProfile> criteria = new ArrayList(query.getResultList());
            return criteria;
        } catch (Exception ex) {
        }
        return null;
    }

    public ArrayList<PrmsBidderRegistration> searchBidderRegistration() {

        Query query = em.createNamedQuery("PrmsBidderRegistration.findAllByStatus");

        try {
            ArrayList<PrmsBidderRegistration> bissubmision = new ArrayList(query.getResultList());

            return bissubmision;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidderRegistration> getParamNameList() {
       Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_BIDDER_REGISTRATION') \n"
                + "and column_name not in ('BIDDER_REG_ID','CURRENT_STATUS','BID_ID','BID_SALE_ID','FEXFILEREFNUMBER','STATUS')");
        try {
            List<PrmsBidderRegistration> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
