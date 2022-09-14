/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBid;
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
public class PrmsSupplyProfileFacade extends AbstractFacade<PrmsSupplyProfile> {

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
    public PrmsSupplyProfileFacade() {
        super(PrmsSupplyProfile.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named Queries">
    /**
     *
     * @param vendorName
     * @return
     */
    public List<PrmsSupplyProfile> searchvendName(PrmsSupplyProfile vendorName) {
         if (vendorName.getColumnValue() != null && vendorName.getColumnName() != null
                && !vendorName.getColumnValue().equals("") && !vendorName.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_SUPPLY_PROFILE\n"
                    + "where " + vendorName.getColumnName().toLowerCase() + " = '" + vendorName.getColumnValue() + "' "
                    + "and " + vendorName.getPreparedBy()+ "='" + vendorName.getPreparedBy()+ "' ", PrmsSupplyProfile.class);
            try {
                List<PrmsSupplyProfile> prmsSupplyProfileLst = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    prmsSupplyProfileLst = query.getResultList();
                   
                }
                return prmsSupplyProfileLst;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsSupplyProfile.findByPreparedBy");
            query.setParameter("preparedBy", vendorName.getPreparedBy());
            return query.getResultList();
        }
    }

    /**
     *
     * @param prmsSupplyProfile
     * @return
     */
    public ArrayList<PrmsSupplyProfile> getVendorName() {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByVendName");
        try {
            ArrayList<PrmsSupplyProfile> vendorName = new ArrayList(query.getResultList());
            return vendorName;

        } catch (Exception ex) {
            return null;
        }

    }

    public PrmsSupplyProfile searchvdCode(PrmsSupplyProfile prmsSupplyProfile) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByVendorName");
        query.setParameter("vendorName", prmsSupplyProfile.getVendorName());
        try {
            PrmsSupplyProfile eepVendorRegList = (PrmsSupplyProfile) (query.getResultList().get(0));
            return eepVendorRegList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsSupplyProfile> findBySuppProfile(PrmsSupplyProfile prmsSupplyProfile) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findAll");
        try {
            ArrayList<PrmsSupplyProfile> prds = new ArrayList(query.getResultList());
            return prds;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public PrmsSupplyProfile findByVendorName(PrmsSupplyProfile prmsSupplyProfile) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByVendNam");
        query.setParameter("vendorName", prmsSupplyProfile.getVendorName());
        try {
            PrmsSupplyProfile vendor = (PrmsSupplyProfile) (query.getResultList().get(0));
            return vendor;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param toString
     * @return
     */
    public ArrayList<PrmsSupplyProfile> biddingCompanyAddress(String toString) {
        ArrayList<PrmsSupplyProfile> deptJobs = null;
        try {
            Query query = em.createNamedQuery("PrmsSupplyProfile.findByVendorName", PrmsSupplyProfile.class);
            query.setParameter("vendorName", toString);
            deptJobs = (ArrayList<PrmsSupplyProfile>) query.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptJobs;
    }

    /**
     *
     * @return
     */
    public List<PrmsSupplyProfile> findSuppliers() {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findAll", PrmsSupplyProfile.class);
        try {
            List<PrmsSupplyProfile> eepVendorRegList = new ArrayList(query.getResultList());
            return eepVendorRegList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean checkVendorRegByNamCode(PrmsSupplyProfile prmsSupplyProfile) {
        boolean duplicaton;
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByNameAndCode");
        query.setParameter("vname", prmsSupplyProfile.getVendorName());
        query.setParameter("vcode", prmsSupplyProfile.getVendorCode());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<PrmsSupplyProfile> getSuppByProfCode(PrmsSupplyProfile eepVendorReg) {
        List<PrmsSupplyProfile> prmsSupplyProfileLst = new ArrayList();
        if (eepVendorReg.getColumnName() != null && !eepVendorReg.getColumnName().equals("")
                && eepVendorReg.getColumnValue() != null && !eepVendorReg.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_SUPPLY_PROFILE\n"
                    + "where " + eepVendorReg.getColumnName().toLowerCase() + " = '" + eepVendorReg.getColumnValue() + "'"
                    + "and " + eepVendorReg.getPreparedBy() + "='" + eepVendorReg.getPreparedBy() + "'", PrmsSupplyProfile.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsSupplyProfileLst = query.getResultList();
                }
                return prmsSupplyProfileLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsSupplyProfile.findByPreparedBy");
            query.setParameter("preparedBy", eepVendorReg.getPreparedBy());
            prmsSupplyProfileLst = query.getResultList();
            return prmsSupplyProfileLst;
        }
    }

    public PrmsSupplyProfile getSelectedId(String id) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findById", PrmsSupplyProfile.class);
        query.setParameter("id", id);
        try {
            PrmsSupplyProfile idlst = (PrmsSupplyProfile) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSupplyProfile getlastSupNo() {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByMaxId");
        PrmsSupplyProfile result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsSupplyProfile) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSupplyProfile getBySuppId(PrmsSupplyProfile prmsSupplyProfile) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findById");
        query.setParameter("id", prmsSupplyProfile.getId());
        try {
            PrmsSupplyProfile catagorie = null;
            if (query.getResultList().size() > 0) {
                catagorie = (PrmsSupplyProfile) query.getResultList().get(0);
            }
            return catagorie;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsSupplyProfile> getSuppByProfileId(String prefix, String currentYear) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.searchByVendorCode", PrmsSupplyProfile.class);
        query.setParameter("vendorCode", prefix + "-" + '%' + "/" + currentYear);
        try {
            List<PrmsSupplyProfile> eepVendorRegList = (List<PrmsSupplyProfile>) (query.getResultList());
            return eepVendorRegList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Native Queries">
    public List<PrmsSupplyProfile> getSupplierList(PrmsBid bid) {
        List<PrmsSupplyProfile> suppLierList = null;
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  PRMS_SUPPLY_PROFILE.AGENT_NAME,\n"
                + "  PRMS_SUPPLY_PROFILE.AGENT_ADDRESS,\n"
                + "  PRMS_SUPPLY_PROFILE.TIN_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_OFFICE,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_MOBILE,\n"
                + "  PRMS_SUPPLY_PROFILE.POBOX,\n"
                + "  PRMS_SUPPLY_PROFILE.FAX,\n"
                + "  PRMS_SUPPLY_PROFILE.EMAIL,\n"
                + "  PRMS_SUPPLY_PROFILE.COUNTRY_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VAT_NO\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_BIDDER_REG_DETAIL\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_BIDDER_REG_DETAIL.SUPP_ID\n"
                + "INNER JOIN PRMS_BIDDER_REGISTRATION\n"
                + "ON PRMS_BIDDER_REGISTRATION.BIDDER_REG_ID = PRMS_BIDDER_REG_DETAIL.BIDDER_REG_ID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_BIDDER_REGISTRATION.BID_ID\n"
                + "WHERE PRMS_BIDDER_REGISTRATION.BID_ID = '" + bid.getId() + "'", PrmsSupplyProfile.class);
        try {

            suppLierList = (List<PrmsSupplyProfile>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo) {
        Query query = em.createNativeQuery(" SELECT PRMS_SUPPLY_PROFILE.*,\n"
                + "  PRMS_BID.ID AS ID1,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_BID_SUBMISSION.BID_ID,\n"
                + "  PRMS_BID_SUBMISSION.BID_SUB_ID,\n"
                + "  PRMS_BID_SUBMISSION_DETAIL.SUPPLIER_ID,\n"
                + "  PRMS_BID_SUBMISSION_DETAIL.BID_SUB_FID,\n"
                + "  PRMS_BID_SUBMISSION_DETAIL.BID_SUB_DT_ID\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN PRMS_BID_SUBMISSION_DETAIL\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_BID_SUBMISSION_DETAIL.SUPPLIER_ID\n"
                + "INNER JOIN PRMS_BID_SUBMISSION\n"
                + "ON PRMS_BID_SUBMISSION.BID_SUB_ID = PRMS_BID_SUBMISSION_DETAIL.BID_SUB_FID\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_BID_SUBMISSION.BID_ID\n"
                + "WHERE PRMS_BID_SUBMISSION.BID_ID = '" + bidNo.getId() + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getSupplierlistByBidId(PrmsBid bidNo) {
        Query query = em.createNativeQuery("SELECT FMS_CASH_RECEIPT_VOUCHER.SUPP_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_TYPE,\n"
                + "  PRMS_SUPPLY_PROFILE.VAT_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.AGENT_ADDRESS,\n"
                + "  PRMS_SUPPLY_PROFILE.AGENT_NAME,\n"
                + "  PRMS_SUPPLY_PROFILE.TIN_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_OFFICE,\n"
                + "  PRMS_SUPPLY_PROFILE.FAX,\n"
                + "  PRMS_SUPPLY_PROFILE.TEL_MOBILE,\n"
                + "  PRMS_SUPPLY_PROFILE.POBOX,\n"
                + "  PRMS_SUPPLY_PROFILE.EMAIL,\n"
                + "  PRMS_SUPPLY_PROFILE.WEBSITE,\n"
                + "  PRMS_SUPPLY_PROFILE.COUNTRY_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.HOUSE_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.FANIANCIAL_LEVEL,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.CASH_RECEIPT_VOUCHER_ID,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.FMS_VOUCHER_VOUCHER_ID,\n"
                + "  FMS_VOUCHER.VOUCHER_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_BID.ID AS ID1,\n"
                + "  FMS_CASH_RECEIPT_VOUCHER.BID_ID\n"
                + "FROM FMS_CASH_RECEIPT_VOUCHER\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = FMS_CASH_RECEIPT_VOUCHER.BID_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = FMS_CASH_RECEIPT_VOUCHER.SUPP_ID\n"
                + "INNER JOIN FMS_VOUCHER\n"
                + "ON FMS_VOUCHER.VOUCHER_ID = FMS_CASH_RECEIPT_VOUCHER.FMS_VOUCHER_VOUCHER_ID\n"
                + "WHERE FMS_CASH_RECEIPT_VOUCHER.BID_ID = '" + bidNo.getId() + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsSupplyProfile> getSuppVendor(PrmsBid bidNo) {
        Query query = em.createNativeQuery("SELECT FMS_BID_SALE.BIDDER_ID,\n"
                + "  FMS_BID_SALE.BID_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "INNER JOIN FMS_BID_SALE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = FMS_BID_SALE.BIDDER_ID\n"
                + "WHERE FMS_BID_SALE.BIDDER_ID = '" + bidNo + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + supplier.size());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsSupplyProfile> getSupplierForSupplierProfile() {
        List<PrmsSupplyProfile> supplierName = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "WHERE PRMS_SUPPLY_PROFILE.PROFILE_FOR = 'Supplier'", PrmsSupplyProfile.class);
        try {
            supplierName = new ArrayList<>(query.getResultList());
            return supplierName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsSupplyProfile> getSupplierForContOrConsultancyProfile() {
        List<PrmsSupplyProfile> contAndConsultancyName = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT PRMS_SUPPLY_PROFILE.ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_SUPPLY_PROFILE\n"
                + "WHERE PRMS_SUPPLY_PROFILE.PROFILE_FOR = 'Consultant' or PRMS_SUPPLY_PROFILE.PROFILE_FOR='Contractor'", PrmsSupplyProfile.class);
        try {
            contAndConsultancyName = new ArrayList<>(query.getResultList());
            return contAndConsultancyName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    public List<PrmsSupplyProfile> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_SUPPLY_PROFILE') \n"
                + "and column_name not in ('ID','VAT_TYPE_ID','BUSINESS','BUS_DISCRIPTION','BUSI_SECTOR','OTHERS_REMARK','COUNTRY_ID','STATUS','REMARK')");
        try {
            List<PrmsSupplyProfile> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

 
    public List<String> findAllSupplierStatuses() {
      Query query = em.createNativeQuery("SELECT DISTINCT vend_info FROM prms_supply_profile WHERE prms_supply_profile.vend_info IS NOT NULL");
        return query.getResultList();
    }

    public int ConutBYSupplierType(String get) {
        System.out.println("gggg"+get);
        Query query = em.createNativeQuery("SELECT * from prms_supply_profile where VEND_INFO='" + get + "'");
        return query.getResultList().size();
    }
    
}
