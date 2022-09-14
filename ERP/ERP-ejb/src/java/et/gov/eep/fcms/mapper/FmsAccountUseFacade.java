/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DataReadQuery;
import org.eclipse.persistence.queries.StoredProcedureCall;

/**
 *
 * @author mora
 */
@Stateless
public class FmsAccountUseFacade extends AbstractFacade<FmsAccountUse> {

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
    public FmsAccountUseFacade() {
        super(FmsAccountUse.class);
    }

    /**
     *
     * @param bankCode
     * @param date
     * @return
     */
    public List<FmsAccountUse> getChequePaymentVouchers(String bankCode, String date) {
        List<FmsAccountUse> luServiceTypes;
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT fau.VOUCHER_ID AS vid, "
                    + "  fcpv.CHEQUE_NUMBER           AS chqNum, "
                    + "  fau.CREDIT                   AS amount, "
                    + "  fau.FMS_ACCOUNT_USE_ID       AS fmsaccu, "
                    + "  fau.SUBSIDARY_ID             AS MINOR_SUBSIDIARY_ID, "
                    + "  fau.DEBIT                    AS debits "
                    + "FROM FMS_ACCOUNT_USE fau "
                    + "INNER JOIN FMS_CHEQUE_PAYMENT_VOUCHER fcpv "
                    + "ON fau.VOUCHER_ID= fcpv.VOUCHER_ID "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fba "
                    + "ON fau.SUBSIDARY_ID= fba.SUBSIDIARY_ID "
                    + "INNER JOIN FMS_VOUCHER fv "
                    + "ON fv.VOUCHER_ID                   =fau.VOUCHER_ID "
                    + "WHERE fv. STATUS_                  =3 "
                    + "AND SUBSTR( fv.VOUCHER_ID, 1, 10) <='" + date + "' "
                    + "AND fba.bank_account_number        ='" + bankCode + "' "
                    + "AND fau.STATUS                     =0 "
                    + "UNION "
                    + "SELECT DISTINCT fau.VOUCHER_ID AS vid, "
                    + "  JV.REFERENCE_NUMBER          AS chqNum, "
                    + "  fau.CREDIT                   AS amount, "
                    + "  fau.FMS_ACCOUNT_USE_ID       AS fmsaccu, "
                    + "  fau.SUBSIDARY_ID             AS MINOR_SUBSIDIARY_ID, "
                    + "  fau.DEBIT                    AS debits "
                    + "FROM FMS_ACCOUNT_USE fau "
                    + "INNER JOIN FMS_JOURNAL_VOUCHER JV "
                    + "ON JV.VOUCHER_ID= fau.VOUCHER_ID "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fba "
                    + "ON fau.SUBSIDARY_ID= fba.SUBSIDIARY_ID "
                    + "INNER JOIN FMS_VOUCHER fv "
                    + "ON fv.VOUCHER_ID                   =fau.VOUCHER_ID "
                    + "WHERE fv. STATUS_                  =3 "
                    + "AND SUBSTR( fv.VOUCHER_ID, 1, 10) <='" + date + "' "
                    + "AND fba.bank_account_number        ='" + bankCode + "' "
                    + "AND fau.STATUS                     =0", "OrderResultsAcu");
            luServiceTypes = (List<FmsAccountUse>) query.getResultList();
            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param bankCode
     * @param date
     * @return
     */
    public List<FmsAccountUse> getCashReceiptVouchers(String bankCode, String date) {
        List<FmsAccountUse> depositsList;
        try {
//            Query query = em.createNativeQuery("SELECT DISTINCT fau.VOUCHER_ID AS vid, "
//                    + "  fcrv.CHEQUE_NUMBER           AS chqNum, "
//                    + "  fau.DEBIT                    AS debits, "
//                    + "  fau.FMS_ACCOUNT_USE_ID       AS fmsaccu, "
//                    + "  fau.SUBSIDARY_ID             AS MINOR_SUBSIDIARY_ID, "
//                    + "  fau.CREDIT                   AS amount "
//                    + "FROM FMS_CASH_RECEIPT_VOUCHER fcrv "
//                    + "INNER JOIN FMS_ACCOUNT_USE fau "
//                    + "ON fcrv.FMS_VOUCHER_VOUCHER_ID = fau.VOUCHER_ID "
//                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fba "
//                    + "ON fau.SUBSIDARY_ID= fba.SUBSIDIARY_ID "
//                    + "INNER JOIN FMS_VOUCHER fv "
//                    + "ON fv.VOUCHER_ID                  =fau.VOUCHER_ID "
//                    + "WHERE fv.STATUS_                  =3 "
//                    + "AND SUBSTR(fv.VOUCHER_ID, 1, 10) <='" + date + "' "
//                    + "AND fba.bank_account_number       ='" + bankCode + "' "
//                    + "AND fau.STATUS                    =0 ");

            Query query = em.createNativeQuery("SELECT fbCashDep.TRAN_REF AS Reference, "
                    + "fbCashDep.AMOUNT AS Amount,"
                    + "fbCashDep.CASH_DEPOSIT_DATE  AS Date1"
                    + "FROM FMS_BANK_CASH_DEPOSIT fbCashDep"
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fbba "
                    + "ON fbba.ACCOUNT_NUMBER = fbCashDep.ACCOUNT_NUMBER"
                    + "INNER JOIN FMS_CODED_TRANSACTION fct "
                    + "ON fct.TRAN_REF = fbCashDep.TRAN_REF"
                    + "WHERE fbba.ACCOUNT_NUMBER            ='" + bankCode + "' "
                    + "AND fbCashDep.CASH_DEPOSIT_DATE      <='" + date + "' "
                    + "AND fct.status = 1"
                    + "AND fct.recon_status = 0 "
                    + "AND fct.credit > 0"
                    + "AND type = 'cash'");

//                    + "UNION "
//                    + "SELECT DISTINCT fau.VOUCHER_ID AS vid, "
//                    + "  JV.REFERENCE_NUMBER          AS chqNum, "
//                    + "  fau.DEBIT                    AS debits, "
//                    + "  fau.FMS_ACCOUNT_USE_ID       AS fmsaccu, "
//                    + "  fau.SUBSIDARY_ID             AS MINOR_SUBSIDIARY_ID, "
//                    + "  fau.CREDIT                   AS amount "
//                    + "FROM FMS_ACCOUNT_USE fau "
//                    + "INNER JOIN FMS_JOURNAL_VOUCHER JV "
//                    + "ON JV.VOUCHER_ID= fau.VOUCHER_ID "
//                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fba "
//                    + "ON fau.SUBSIDARY_ID= fba.SUBSIDIARY_ID "
//                    + "INNER JOIN FMS_VOUCHER fv "
//                    + "ON fv.VOUCHER_ID                   =fau.VOUCHER_ID "
//                    + "WHERE fv. STATUS_                  =3 "
//                    + "AND SUBSTR( fv.VOUCHER_ID, 1, 10) <='" + date + "' "
//                    + "AND fba.bank_account_number        ='" + bankCode + "' "
//                    + "AND fau.STATUS                     =0", "OrderResultsAcu");
            depositsList = (List<FmsAccountUse>) query.getResultList();
            System.out.println("-----------depositsList----------" + depositsList);
            return depositsList;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param statmentDate
     * @param dateSatment
     * @param bankCode
     * @return
     */
    public String getGLBankReco(String statmentDate, String dateSatment, String bankCode) {
        try {
            Query query = em.createNativeQuery("SELECT NVL((SUM(DEBIT)- SUM(CREDIT)), 0)AS BALANCE "
                    + "FROM FMS_ACCOUNT_USE fau "
                    + "INNER JOIN fms_voucher fv "
                    + "ON fau.VOUCHER_ID=fv.VOUCHER_ID "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fbba "
                    + "ON fau.SUBSIDARY_ID= fbba.SUBSIDIARY_ID "
                    + "WHERE fv.STATUS   =2 "
                    + "AND (SUBSTR( fau.VOUCHER_ID, 1, 10) BETWEEN '" + statmentDate + "/01' AND '" + dateSatment + "') "
                    + "AND fbba.ACCOUNT_NUMBER='" + bankCode + "'");
            return (String) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param budgetYear
     * @param reportMonth
     * @return
     */
    public List getPreviousBalance(String budgetYear, String reportMonth) {
        List luServiceTypes;
        try {
            Query query = em.createNativeQuery("SELECT (SUM(DEBIT)- SUM(CREDIT))AS BALANCE "
                    + "FROM FMS_ACCOUNTS_CLOSING_BALANCE "
                    + "INNER JOIN FMS_BANK_ACCOUNT ON FMS_ACCOUNTS_CLOSING_BALANCE.SUBSIDARY_ID "
                    + "FMS_BANK_ACCOUNT.SL_CODE WHERE FMS_ACCOUNTS_CLOSING_BALANCE.ACCOUNTING_PERIOD=? "
                    + "AND FMS_BANK_ACCOUNT.BANK_ACCOUNT_NUMBER='0172073714100'");

            luServiceTypes = (List) query.getResultList();

            return luServiceTypes;
        } catch (Exception e) {
            return null;
        }

    }

    public List<FmsAccountUse> LedgerReport(HashMap hashMap) {
        List<FmsAccountUse> accountUses;
        System.out.println("welcome   ");
        DataReadQuery databaseQuery = new DataReadQuery();
        System.out.println("month= " + hashMap.get("MONTH_YEAR"));
        System.out.println("monthttttttt= " + hashMap.get("SUBSID"));

        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_LEDGER_CARD");
        call.useNamedCursorOutputAsResultSet("cur");
        call.addNamedArgumentValue("MONTH_YEAR", hashMap.get("MONTH_YEAR"));
        call.addNamedArgumentValue("subId", hashMap.get("SUBSID"));
        System.out.println("month= " + hashMap.get("MONTH_YEAR"));
        databaseQuery.setCall(call);
//                        String system=hashMap.get("system").toString();
        String subsidary_code = hashMap.get("SUBSID").toString();
        String Sys = hashMap.get("SYS").toString();

        String sql = "SELECT * FROM FMS_ACCOUNT_USE WHERE FMS_ACCOUNT_USE.SUBSIDARY_ID LIke '" + Sys + "%" + subsidary_code + "' AND SUBSTR(FMS_ACCOUNT_USE.VOUCHER_ID,1,4)='2017'";
        Query query2 = em.createNativeQuery(sql);
        System.out.println("xxxxxxx " + subsidary_code + " ------------" + sql);

        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query2.getResultList().isEmpty() != true) {
            System.out.println("welcome   " + query.getMaxResults());
            List<Object[]> results = query2.getResultList();
            System.err.println("--size-2-" + results.size());
            ArrayList<FmsAccountUse> fmsAccountUses = new ArrayList();
            FmsAccountUse accountUse = null;
            for (Object[] result : results) {
                accountUse = new FmsAccountUse();
                accountUse.setCredit(new BigDecimal(result[1].toString()));
                accountUse.setDebit(new BigDecimal(result[2].toString()));
                accountUse.setFmsAccountUseId(Integer.parseInt(result[0].toString()));
                accountUse.setSubsidaryId(result[3].toString());
                FmsVoucher fmsVoucher = new FmsVoucher();
                fmsVoucher.setVoucherId(result[5].toString());
                accountUse.setVOUCHEID(fmsVoucher);
                fmsAccountUses.add(accountUse);
            }
            accountUses = query.getResultList();
            System.out.println("welcome xxxxx  " + accountUses.size());
            return fmsAccountUses;
        } else {
            return null;
        }
    }

    public List<FmsAccountUse> LedgerReport_system(HashMap hashMap) {
        List<FmsAccountUse> accountUses;
        System.out.println("welcome   ");
        DataReadQuery databaseQuery = new DataReadQuery();
        String Sys = hashMap.get("SYS").toString();
        String sql = "SELECT * FROM FMS_ACCOUNT_USE WHERE FMS_ACCOUNT_USE.SUBSIDARY_ID LIke '" + Sys + "%' AND SUBSTR(FMS_ACCOUNT_USE.VOUCHER_ID,1,4)='2017'";
        Query query2 = em.createNativeQuery(sql);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query2.getResultList().isEmpty() != true) {
            System.out.println("welcome   " + query.getMaxResults());
            List<Object[]> results = query2.getResultList();
            System.err.println("--size-2-" + results.size());
            ArrayList<FmsAccountUse> fmsAccountUses = new ArrayList();
            FmsAccountUse accountUse = null;
            for (Object[] result : results) {
                accountUse = new FmsAccountUse();
                accountUse.setCredit(new BigDecimal(result[1].toString()));
                accountUse.setDebit(new BigDecimal(result[2].toString()));
                accountUse.setFmsAccountUseId(Integer.parseInt(result[0].toString()));
                accountUse.setSubsidaryId(result[3].toString());
                FmsVoucher fmsVoucher = new FmsVoucher();
                fmsVoucher.setVoucherId(result[5].toString());
                accountUse.setVOUCHEID(fmsVoucher);
                fmsAccountUses.add(accountUse);
            }

            return fmsAccountUses;
        } else {
            return null;
        }
    }

    public List<FmsAccountUse> LedgerReport_GL(HashMap hashMap) {
        List<FmsAccountUse> accountUses;
        System.out.println("welcome   ");
        DataReadQuery databaseQuery = new DataReadQuery();
        System.out.println("month= " + hashMap.get("MONTH_YEAR"));
        System.out.println("monthttttttt= " + hashMap.get("SUBSID"));

        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_LEDGER_CARD");
        call.useNamedCursorOutputAsResultSet("cur");
        call.addNamedArgumentValue("MONTH_YEAR", hashMap.get("MONTH_YEAR"));
        call.addNamedArgumentValue("subId", hashMap.get("SUBSID"));
        System.out.println("month= " + hashMap.get("MONTH_YEAR"));
        databaseQuery.setCall(call);
//                        String system=hashMap.get("system").toString();
        String subsidary_code = hashMap.get("SUBSID").toString();
        String sql = "SELECT * FROM FMS_ACCOUNT_USE WHERE FMS_ACCOUNT_USE.SUBSIDARY_ID LIke '%" + subsidary_code + "%' AND SUBSTR(FMS_ACCOUNT_USE.VOUCHER_ID,1,4)='2017'";
        Query query2 = em.createNativeQuery(sql);
        System.out.println("xxxxxxx " + subsidary_code + " ------------" + sql);

        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query2.getResultList().isEmpty() != true) {
            List<Object[]> results = query2.getResultList();
            ArrayList<FmsAccountUse> fmsAccountUses = new ArrayList();
            FmsAccountUse accountUse = null;
            for (Object[] result : results) {
                accountUse = new FmsAccountUse();
                accountUse.setCredit(new BigDecimal(result[1].toString()));
                accountUse.setDebit(new BigDecimal(result[2].toString()));
                accountUse.setFmsAccountUseId(Integer.parseInt(result[0].toString()));
                accountUse.setSubsidaryId(result[3].toString());
                FmsVoucher fmsVoucher = new FmsVoucher();
                fmsVoucher.setVoucherId(result[5].toString());
                accountUse.setVOUCHEID(fmsVoucher);
                fmsAccountUses.add(accountUse);
            }

            accountUses = query.getResultList();
            System.out.println("welcome xxxxx  " + accountUses.size());
            return fmsAccountUses;
        } else {
            return null;
        }
    }

    public List<FmsAccountUse> LedgerReport_SL(HashMap hashMap) {
        List<FmsAccountUse> accountUses;
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_LEDGER_CARD");
        call.useNamedCursorOutputAsResultSet("cur");
        call.addNamedArgumentValue("MONTH_YEAR", hashMap.get("MONTH_YEAR"));
        call.addNamedArgumentValue("subId", hashMap.get("SUBSID"));
        databaseQuery.setCall(call);
        String subsidary_code = hashMap.get("SUBSID").toString();
        String sql = "SELECT * FROM FMS_ACCOUNT_USE WHERE FMS_ACCOUNT_USE.SUBSIDARY_ID LIke '%" + subsidary_code + "' AND SUBSTR(FMS_ACCOUNT_USE.VOUCHER_ID,1,4)='2017'";
        Query query2 = em.createNativeQuery(sql);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query2.getResultList().isEmpty() != true) {
            List<Object[]> results = query2.getResultList();
            ArrayList<FmsAccountUse> fmsAccountUses = new ArrayList();
            FmsAccountUse accountUse = null;
            for (Object[] result : results) {
                accountUse = new FmsAccountUse();
                accountUse.setCredit(new BigDecimal(result[1].toString()));
                accountUse.setDebit(new BigDecimal(result[2].toString()));
                accountUse.setFmsAccountUseId(Integer.parseInt(result[0].toString()));
                accountUse.setSubsidaryId(result[3].toString());
                FmsVoucher fmsVoucher = new FmsVoucher();
                fmsVoucher.setVoucherId(result[5].toString());
                accountUse.setVOUCHEID(fmsVoucher);
                fmsAccountUses.add(accountUse);

            }

            accountUses = query.getResultList();
            return fmsAccountUses;
        } else {
            return null;
        }
    }

    public List<FmsAccountUse> trilReport_SL(HashMap hashMap) {
        System.out.println(" trilReport_SL query");
        String sys = hashMap.get("SYS").toString();
        String subsidary_code = hashMap.get("SUBSID").toString();
        String sql = "SELECT * FROM FMS_ACCOUNT_USE WHERE FMS_ACCOUNT_USE.SUBSIDARY_ID LIke '" + sys + "%" + subsidary_code + "%' AND SUBSTR(FMS_ACCOUNT_USE.VOUCHER_ID,1,4)='2021'";
        Query query2 = em.createNativeQuery(sql);
        System.out.println(" trilReport_SL query11111");
        if (query2.getResultList().isEmpty() != true) {
            List<Object[]> results = query2.getResultList();
            ArrayList<FmsAccountUse> fmsAccountUses = new ArrayList();
            FmsAccountUse accountUse = null;
            System.out.println("size facade : " + query2.getResultList().size());
            for (Object[] result : results) {
                accountUse = new FmsAccountUse();
                accountUse.setCredit(new BigDecimal(result[1].toString()));
                accountUse.setDebit(new BigDecimal(result[2].toString()));
                accountUse.setFmsAccountUseId(Integer.parseInt(result[0].toString()));
                accountUse.setSubsidaryId(result[3].toString());
                FmsVoucher fmsVoucher = new FmsVoucher();
                fmsVoucher.setVoucherId(result[5].toString());
                accountUse.setVOUCHEID(fmsVoucher);
                fmsAccountUses.add(accountUse);

            }

            return fmsAccountUses;
        } else {
            return null;
        }
    }

    public ArrayList<FmsAccountUse> fetchCPOAccUse(FmsCashPaymentOrder fmsCashPaymentOrder) {
        Query query = em.createNativeQuery("select * from FMS_ACCOUNT_USE ACC "
                + "WHERE ACC.VOUCHER_ID = ( SELECT CPO.VOUCHER_ID from FMS_CASH_PAYMENT_ORDER CPO WHERE CPO.CASH_PAYMENT_ORDER_ID = '"+fmsCashPaymentOrder.getCashPaymentOrderId()+"' And ACC.CREDIT = '0')",
                FmsAccountUse.class);
        ArrayList<FmsAccountUse> accUseList = new ArrayList(query.getResultList());   
        return accUseList;
    }
    
    public ArrayList<FmsAccountUse> fetchCHPVAccUse(FmsChequePaymentVoucher fmsChequePaymentVoucher) {
        Query query = em.createNativeQuery("select * from FMS_ACCOUNT_USE ACC "
                + "WHERE ACC.VOUCHER_ID = ( SELECT CHPV.VOUCHER_ID from FMS_CHEQUE_PAYMENT_VOUCHER CHPV WHERE CHPV.CHEQUE_PAYMENT_VOUCHER_ID = '"+fmsChequePaymentVoucher.getChequePaymentVoucherId()+"' And ACC.CREDIT = '0')",
                FmsAccountUse.class);
        ArrayList<FmsAccountUse> accUseList = new ArrayList(query.getResultList());
        return accUseList;
    }
    
    public FmsAccountUse getAccUse(FmsAccountUse fmsAccountUse){
        Query query = em.createNamedQuery("FmsAccountUse.findByVOUCHEIDAndAccId");
        query.setParameter("VOUCHEID", fmsAccountUse.getVOUCHEID());
        query.setParameter("fmsAccountUseId", fmsAccountUse.getFmsAccountUseId());
        FmsAccountUse accUse = (FmsAccountUse) (query.getResultList().get(0));
        return accUse;
    }

}
