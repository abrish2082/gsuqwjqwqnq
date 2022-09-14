package et.gov.eep.fcms.mapper.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DataReadQuery;
import org.eclipse.persistence.queries.StoredProcedureCall;
//</editor-fold>

@Stateless
public class FmsVoucherFacade extends AbstractFacade<FmsVoucher> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsVoucherFacade() {
        super(FmsVoucher.class);
    }

    public Collection LedgerReport(HashMap param) {
        Collection collection = null;
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_LEDGER");
        call.useNamedCursorOutputAsResultSet("cur");
        call.addNamedArgumentValue("V_TYPE", param.get("V_TYPE").toString());
        call.addNamedArgumentValue("L_YEAR", "2017");
        call.addNamedArgumentValue("SUBSIDARY_ID", param.get("SUBSIDARY_ID").toString());
        System.out.println("sbu faca  ----------" + param.get("SUBSIDARY_ID").toString());
        System.out.println("sbu faca  -------yeare---" + param.get("L_YEAR").toString());

        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        System.out.print("result size is.........." + query.getResultList().size());
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return collection;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
    public List<FmsVoucher> searchVoucheid(FmsVoucher fmsVoucher) {

        List<FmsVoucher> ChartOfAccountGlList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherCPV", FmsVoucher.class);
            query.setParameter("voucherId", fmsVoucher.getVoucherId() + "%");
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeCRV(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        try {
            fmsVoucher.setType("CRV");
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);

            query.setParameter("voucherId", "%" + fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());

            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();

            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public FmsVoucher getVoucherIDInfo(FmsVoucher fmsVoucher) {
        Query query = em.createNamedQuery("FmsVoucher.findByVoucherId", FmsVoucher.class);
        query.setParameter("voucherId", fmsVoucher.getVoucherId());
        try {
            FmsVoucher fmsVoucher1 = (FmsVoucher) query.getResultList().get(0);
            return fmsVoucher1;
        } catch (Exception ex) {

            return null;
        }

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public boolean getfmsVoucherDup(FmsVoucher fmsVoucher) {
        boolean duplicaton;
        Query query = em.createNamedQuery("FmsVoucher.findVoucheIdDup", FmsVoucher.class);
        query.setParameter("voucherId", fmsVoucher.getVoucherId());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = false;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypePCPV(FmsVoucher fmsVoucher) {

        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("PCPV");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);
            query.setParameter("voucherId", fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeCPO(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("CPO");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);
            query.setParameter("voucherId", "%" + fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeCHPV(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("CHPV");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);
            query.setParameter("voucherId", "%" + fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeJV(FmsVoucher fmsVoucher) {

        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("JV");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);
            query.setParameter("voucherId", "%" + fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeSPV(FmsVoucher fmsVoucher) {
        System.out.println("query 111");
        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("SPV");
        try {
            System.out.println(" try query 111");
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherJV", FmsVoucher.class);
            System.out.println(" try query 111222222");
            query.setParameter("voucherId", fmsVoucher.getVoucherId() + "%");
            System.out.println(" try query 111333333");
            query.setParameter("type", fmsVoucher.getType());
            System.out.println(" try query 1114444444");
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            System.out.println(" try query 111555555");
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param selectedChash
     * @return
     */
    public List<FmsVoucher> getChashRegVoucherInfo(String selectedChash) {

        List<FmsVoucher> VoucherList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherPCPVCasher", FmsVoucher.class);

            query.setParameter("selectedChash", selectedChash);

            VoucherList = (List<FmsVoucher>) query.getResultList();
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     */
    public void searchVoucherPostStatus() {

        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusUPDATE", FmsVoucher.class);
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     *
     * @return
     */
    public ArrayList<FmsVoucher> findVoucherList() {
        Query query = em.createNamedQuery("FmsVoucher.findByVoucherTypeList");
        try {
            ArrayList<FmsVoucher> VoucherList = new ArrayList(query.getResultList());
            return VoucherList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param vouceher
     * @return
     */
    public FmsVoucher getPittyCash(FmsVoucher vouceher) {
        System.out.println("facade");
        Query query = em.createNamedQuery("FmsVoucher.getByVoucherId");
        System.out.println("facade1");
        query.setParameter("voucherId", vouceher.getVoucherId());
        try {
            FmsVoucher voucherList = (FmsVoucher) query.getResultList().get(0);
            System.out.println("give me the result" + query.getResultList());
            return voucherList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public List<FmsVoucher> searchVoucherStates() {

        List<FmsVoucher> VoucherList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusCpo", FmsVoucher.class);

            VoucherList = (List<FmsVoucher>) query.getResultList();
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @return
     */
    public List<FmsVoucher> searchVoucherStatesJv() {

        List<FmsVoucher> VoucherList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusJV", FmsVoucher.class);

            VoucherList = (List<FmsVoucher>) query.getResultList();
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @return
     */
    public List<FmsVoucher> searchVoucherStatesCrv() {

        List<FmsVoucher> VoucherList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusCrv", FmsVoucher.class);

            VoucherList = (List<FmsVoucher>) query.getResultList();
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @return
     */
    public List<FmsVoucher> searchVoucherStatesChpv() {

        List<FmsVoucher> VoucherList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusChpv", FmsVoucher.class);

            VoucherList = (List<FmsVoucher>) query.getResultList();
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<FmsVoucher> searchVoucheIdListCRVWithHold(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherCPVWithHold", FmsVoucher.class);
            query.setParameter("voucherId", fmsVoucher.getVoucherId() + "%");
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheIdListPCPVDailyReg(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherPCPVDailyReg", FmsVoucher.class);
            query.setParameter("voucherId", fmsVoucher.getVoucherId() + "%");
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //findByVoucherPCPVDailyCash
    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheIdListPCPVDailyCash(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherPCPVDailyCash", FmsVoucher.class);
            query.setParameter("voucherId", fmsVoucher.getVoucherId() + "%");
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<FmsVoucher> searchAllJvByType() {
        System.out.println("inside the query");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusJV", FmsVoucher.class);
            System.out.println("inside the query1111111");
            ArrayList<FmsVoucher> jvList = new ArrayList<>(query.getResultList());
            System.out.println("inside the query22222222222");
            return jvList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<FmsVoucher> findAllCHPVs() {
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusChpv");
            List<FmsVoucher> VoucherList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                VoucherList = query.getResultList();
            }
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FmsVoucher> findAllVATS() {
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByvatWithholdStatus");
            query.setParameter("vatWithholdStatus", "UV");
            List<FmsVoucher> VoucherList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                VoucherList = query.getResultList();
            }
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FmsVoucher> findAllWITHHOLDSS() {
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByvatWithholdStatus");
            query.setParameter("vatWithholdStatus", "UW");
            List<FmsVoucher> VoucherList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                VoucherList = query.getResultList();
            }
            return VoucherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FmsVoucher> findAllCPOs() {
        System.out.println("jjj");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusCpo");
            ArrayList<FmsVoucher> VoucherList = new ArrayList(query.getResultList());
            System.out.println("sss " + VoucherList);
            return VoucherList;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<FmsVoucher> findAllCRV() {
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByStatusCrv");
            ArrayList<FmsVoucher> VoucherList = new ArrayList(query.getResultList());
            return VoucherList;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<FmsVoucher> findAllbytype(FmsVoucher fmsVoucher) {
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByType");
            query.setParameter("type", fmsVoucher.getType());
            ArrayList<FmsVoucher> VoucherList = new ArrayList(query.getResultList());
            return VoucherList;
        } catch (Exception e) {
            throw e;
        }
    }

    public PrmsBid getBidInfoById(PrmsBid prmsBid) {
        System.out.println("----" + prmsBid.getId());
        Query q = em.createNamedQuery("PrmsBid.findById");
        q.setParameter("id", prmsBid.getId());
        PrmsBid bidInfo = new PrmsBid();
        if (q.getResultList().size() > 0) {
            bidInfo = (PrmsBid) (q.getResultList().get(0));
            System.out.println("amount ---" + bidInfo.getBidDocPrice());
        }
        return bidInfo;
    }

    public List<PrmsBid> getBidNoLists() {
        Query q = em.createNamedQuery("PrmsBid.findByOpenBidPurchaseMethd");
        try {
            List<PrmsBid> bidnos = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                bidnos = q.getResultList();
            }
            return bidnos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FmsLuPaymentType getPaymentType(FmsLuPaymentType luPaymentType) {
        System.out.println("facade");
        Query q = em.createNamedQuery("FmsLuPaymentType.findByLuPaymentTypeId");
        q.setParameter("luPaymentTypeId", luPaymentType.getLuPaymentTypeId());
        try {
            FmsLuPaymentType payType = new FmsLuPaymentType();
            if (q.getResultList().size() > 0) {
                payType = (FmsLuPaymentType) q.getResultList().get(0);
                System.out.println("type---" + payType.getPayment());
            }
            return payType;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FmsVouchersNoRange getVoucherNo(FmsLuSystem fmsLuSystem) {
        System.out.println("here facade " + fmsLuSystem.getSystemId());
        Query query = em.createNamedQuery("FmsVouchersNoRange.findBySystemId");
        query.setParameter("systemId", fmsLuSystem.getSystemId());
        try {
            FmsVouchersNoRange fmsVouchersNoRange = new FmsVouchersNoRange();
            if (query.getResultList().size() > 0) {
                System.out.println("33");
                fmsVouchersNoRange = (FmsVouchersNoRange) query.getResultList().get(0);
            }
            return fmsVouchersNoRange;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FmsVoucher> searchVoucheidTypeWHV(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("WHV");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);
            query.setParameter("voucherId", "%" + fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FmsVoucher> searchVoucheidTypeVV(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("VV");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);
            query.setParameter("voucherId", "%" + fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FmsVoucher> searchVoucheidTypechpvAndUnpaidVV(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        fmsVoucher.setType("CHPV");
        try {
            Query query = em.createNamedQuery("FmsVoucher.findByVoucherByType", FmsVoucher.class);
//            query.setParameter("voucherId", "%" + fmsVoucher.getVoucherId() + "%");
            query.setParameter("type", fmsVoucher.getType());
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Native Queries">
    public List<FmsVoucher> getVoch1No(FmsVoucher fmsVoucher) {
        System.out.println("fmsVoucher.getColumnValue()===" + fmsVoucher.getColumnValue());
        System.out.println("====fmsVoucher.getColumnName()===" + fmsVoucher.getColumnName());
        if (fmsVoucher.getColumnValue() != null && fmsVoucher.getColumnName() != null
                && !fmsVoucher.getColumnValue().equals("") && !fmsVoucher.getColumnName().equals("")) {
            System.out.println("passd!!!");
            Query query = em.createNativeQuery("SELECT * FROM FMS_VOUCHER\n"
                    + "where " + fmsVoucher.getColumnName().toLowerCase() + " = '" + fmsVoucher.getColumnValue() + "' ", FmsVoucher.class);

            try {
                List<FmsVoucher> fmsVoucherSearchLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    fmsVoucherSearchLists = query.getResultList();
                    System.out.println("==fmsVoucherSearchLists==" + fmsVoucherSearchLists);
                }
                return fmsVoucherSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
           // Query query = em.createNamedQuery("FmsVoucher.findByPreparedBy");
            //  query.setParameter("preparedby", fmsVoucher.getPreparedBy());
            //  return query.getResultList();
            Query query = em.createNamedQuery("FmsVoucher.findByType");
            query.setParameter("type", fmsVoucher.getType());
            ArrayList<FmsVoucher> VoucherList = new ArrayList(query.getResultList());
            System.out.println("VoucherList===" + VoucherList);
            return VoucherList;
        }

    }

    public List<FmsVoucher> getFmsVoucherSearchingParameterList() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('FMS_VOUCHER')\n"
                + "and column_name NOT IN('VOUCHER_ID' ,'SystemNo','STATUS' ,'TYPE_','REASON' , 'ProcessedBy' , 'PREPARED_DATE' , 'PREPARED_BY' , 'PREPARED_REMARK' , 'VAT_WITHOLD_STATUS' , 'PROCESSED_DATE' ) ORDER BY column_name ASC");
        try {
            List<FmsVoucher> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }

    public List<FmsVoucher> searchVoucheIdListCPODailyCash(FmsVoucher fmsVoucher) {
        List<FmsVoucher> ChartOfAccountGlList = null;
        try {
            Query query = em.createNativeQuery("SELECT * FROM FMS_VOUCHER fv "
                    + "                     INNER JOIN FMS_CASH_PAYMENT_ORDER fcpo "
                    + "                     ON fv.VOUCHER_ID = fcpo.VOUCHER_ID "
                    + "                     WHERE fv.VOUCHER_ID LIKE '%" + fmsVoucher.getVoucherId() + "%' "
                    + "                     AND fcpo.AMOUNT_IN_FIGURE <= 10000 "
                    + "                     AND fcpo.CASH_PAYMENT_ORDER_ID NOT IN(Select fdcr.PETTY_CASH_ID FROM FMS_DAILY_CASH_REGISTER fdcr) ", FmsVoucher.class);
            ChartOfAccountGlList = (List<FmsVoucher>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    //</editor-fold>

}
