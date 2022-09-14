/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsBeginningBalance;
import et.gov.eep.fcms.entity.FmsTrialBalanceDetail;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DataReadQuery;
import org.eclipse.persistence.queries.StoredProcedureCall;
import et.gov.eep.fcms.entity.FmsLedgerCardResult;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.mapper.admin.FmsAccountingPeriodFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Stateless
public class FmsTrialBalanceDetailFacade extends AbstractFacade<FmsTrialBalanceDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsTrialBalanceDetailFacade() {
        super(FmsTrialBalanceDetail.class);
    }
    @EJB
    FmsBeginningBalanceFacade beginningBalanceFacade;
    @Inject
    FmsAccountingPeriod fmsAccountingPeriod;
    @Inject
    FmsAccountUse accountUse;
    @EJB
    FmsAccountUseFacade accountUseFacade;
    @EJB
    FmsAccountingPeriodFacade accountingPeriodFacade;

    public Collection LedgerReport(HashMap hashMap) {
        System.out.println("welcome   ");
        DataReadQuery databaseQuery = new DataReadQuery();

        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_LEDGER_CARD");
        call.useNamedCursorOutputAsResultSet("cur");
        call.addNamedArgumentValue("MONTH_YEAR", hashMap.get("MONTH_YEAR").toString());
        call.addNamedArgumentValue("SUBSID", hashMap.get("SUBSID").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }
 
    private int getBudgetYear(String aperiod) {
        fmsAccountingPeriod = new FmsAccountingPeriod();
        fmsAccountingPeriod = accountingPeriodFacade.getPeriod(aperiod);
        int budgetYear;
        budgetYear = fmsAccountingPeriod.getAcountigPeriodId();
        return budgetYear;
    }

//    private HashMap getBeginingBalance(String aperiod, String strSubCode) {
//        HashMap bBalance = new HashMap();
//
//        FmsBeginningBalance beginningBalance = new FmsBeginningBalance();
//        long budgetYear = this.getBudgetYear(aperiod);
////        beginningBalance.setAcountigPeriodId(budgetYear);
//        beginningBalance.setGlCode(strSubCode);
//
//        FmsBeginningBalance beginningBalance_result = new FmsBeginningBalance();
//        beginningBalance_result = beginningBalanceFacade.beginningBalance(beginningBalance);
//
//            bBalance.put("reference", beginningBalance_result.getBeginningBalanceId());
//            bBalance.put("description", "Begining Balance");
//            bBalance.put("credit", Math.abs(Double.parseDouble(beginningBalance_result.getCredit().toString())));
//            bBalance.put("debit", Math.abs(Double.parseDouble(beginningBalance_result.getDebit().toString())));
//            bBalance.put("balance", Math.abs(Double.parseDouble(beginningBalance_result.getAmount().toString())));
//
//      
//        return bBalance;
//    }

    public ArrayList<HashMap> getLedgerCardMap(Query _query, String budgetYear, HashMap bbalance, int check) {

        ArrayList<HashMap> ledgerCardMap = new ArrayList<HashMap>();
        if (check == 0) {
            ledgerCardMap.add(bbalance);
            balance += Double.parseDouble(bbalance.get("balance").toString());
        }
        List<Object[]> results = _query.getResultList();
        for (Object[] result : results) {
            HashMap map = new HashMap();
            String voucherID = result[2].toString();
            map.put("date", voucherID.substring(0, voucherID.indexOf('/')));
            map.put("reference", voucherID);
            map.put("description", result[5].toString());
            map.put("slDesc", result[6]);
            map.put("debit", Math.abs(Double.parseDouble(result[0].toString())));
            map.put("credit", Math.abs(Double.parseDouble(result[1].toString())));
            if (Double.parseDouble(bbalance.get("debit").toString()) - Double.parseDouble(bbalance.get("credit").toString()) >= 0) {
                balance += Math.abs(Double.parseDouble(result[0].toString()));
                balance -= Math.abs(Double.parseDouble(result[1].toString()));
            } else {
               balance -= Math.abs(Double.parseDouble(result[0].toString()));
                balance += Math.abs(Double.parseDouble(result[1].toString()));
            }
            map.put("balance", Math.abs(balance));
            ledgerCardMap.add(map);
        }
        return ledgerCardMap;

    }

    private double balance = 0.0;

//    public ArrayList<HashMap> getLedgerCardMap(int subsidiaryCode, String year, String month, int glID, String strSubCode, String aperiod) {
//        ArrayList<HashMap> ledgerCardMap = new ArrayList<HashMap>();
//        String budgetYear = aperiod;
//        HashMap bbalance = this.getBeginingBalance(budgetYear, strSubCode);
//        balance = Double.parseDouble(bbalance.get("balance").toString());
//
////        balance = Double.parseDouble(bbalance.get("balance").toString());
//        Query query;
////
//        return ledgerCardMap;
//    }

//    public List<FmsLedgerCardResult> generateLedgerCard(int glCode, int slCode, String year, String month, String strSubCode, String aperiod) {
//        List<FmsLedgerCardResult> ledgerCardResultslist = new ArrayList<>();
//        ArrayList<HashMap> ledgerCardMap;
//        ledgerCardMap = this.getLedgerCardMap(slCode, year, month, glCode, strSubCode, aperiod);
//        FmsLedgerCardResult fmsLedgerCardResult = null;
//        double totalCredit = 0.0;
//        double totalDebit = 0.0;
//        for (int i = 0; i < ledgerCardMap.size(); i++) {
//            fmsLedgerCardResult = new FmsLedgerCardResult();
//            if (ledgerCardMap.get(i).get("reference").toString().split("-").length > 1) {
//                fmsLedgerCardResult.setDate(ledgerCardMap.get(i).get("reference").toString().split("-")[0]);
//                fmsLedgerCardResult.setReference(ledgerCardMap.get(i).get("reference").toString().split("-")[1]);
////                fmsLedgerCardResult.setVoucherType(voucherTypeLable(ledgerCardMap.get(i).get("reference").toString().split("-")[2]));
//            } else {
//                fmsLedgerCardResult.setDate("2016/05/01");
//            }
//            if (String.valueOf(ledgerCardMap.get(i).get("slDesc")).equalsIgnoreCase("isNull")) {
//                fmsLedgerCardResult.setACC_DESC(String.valueOf(ledgerCardMap.get(i).get("description")));
//            } else {
//                fmsLedgerCardResult.setACC_DESC(String.valueOf(ledgerCardMap.get(i).get("slDesc")));
//            }
//            fmsLedgerCardResult.setCREDIT(Double.parseDouble(ledgerCardMap.get(i).get("credit").toString()));
//            fmsLedgerCardResult.setDEBIT(Double.parseDouble(ledgerCardMap.get(i).get("debit").toString()));
//            fmsLedgerCardResult.setBalance(Double.parseDouble(ledgerCardMap.get(i).get("balance").toString()));
//            totalCredit += fmsLedgerCardResult.getCREDIT();
//            totalDebit += fmsLedgerCardResult.getDEBIT();
//            fmsLedgerCardResult.setTotalCredit(totalCredit);
//            fmsLedgerCardResult.setTotalDebit(totalDebit);
//            ledgerCardResultslist.add(fmsLedgerCardResult);
//        }
//
//        return ledgerCardResultslist;
//    }

    
//        public ArrayList<FmsLedgerCardResult> generateLedger(int glCode, int slCode, String year, String month, String strSubCode, String aperiod) {
//        ArrayList<FmsLedgerCardResult> ledgerCardResultslist = new ArrayList<>();
//        ArrayList<HashMap> ledgerCardMap;
//        ledgerCardMap = this.getLedgerCardMap(slCode, year, month, glCode, strSubCode, aperiod);
//        FmsLedgerCardResult fmsLedgerCardResult = null;
//        double totalCredit = 0.0;
//        double totalDebit = 0.0;
//        for (int i = 0; i < ledgerCardMap.size(); i++) {
//            fmsLedgerCardResult = new FmsLedgerCardResult();
//            if (ledgerCardMap.get(i).get("reference").toString().split("-").length > 1) {
//                fmsLedgerCardResult.setDate(ledgerCardMap.get(i).get("reference").toString().split("-")[0]);
//                fmsLedgerCardResult.setReference(ledgerCardMap.get(i).get("reference").toString().split("-")[1]);
////                fmsLedgerCardResult.setVoucherType(voucherTypeLable(ledgerCardMap.get(i).get("reference").toString().split("-")[2]));
//            } else {
//                fmsLedgerCardResult.setDate("2016/05/01");
//            }
//            if (String.valueOf(ledgerCardMap.get(i).get("slDesc")).equalsIgnoreCase("isNull")) {
//                fmsLedgerCardResult.setACC_DESC(String.valueOf(ledgerCardMap.get(i).get("description")));
//            } else {
//                fmsLedgerCardResult.setACC_DESC(String.valueOf(ledgerCardMap.get(i).get("slDesc")));
//            }
//            fmsLedgerCardResult.setCREDIT(Double.parseDouble(ledgerCardMap.get(i).get("credit").toString()));
//            fmsLedgerCardResult.setDEBIT(Double.parseDouble(ledgerCardMap.get(i).get("debit").toString()));
//            fmsLedgerCardResult.setBalance(Double.parseDouble(ledgerCardMap.get(i).get("balance").toString()));
//            totalCredit += fmsLedgerCardResult.getCREDIT();
//            totalDebit += fmsLedgerCardResult.getDEBIT();
//            fmsLedgerCardResult.setTotalCredit(totalCredit);
//            fmsLedgerCardResult.setTotalDebit(totalDebit);
//            ledgerCardResultslist.add(fmsLedgerCardResult);
//        }
//
//        return ledgerCardResultslist;
//    }

    //         //<editor-fold defaultstate="collapsed" desc="file and trilbalnce fms doc">

    public boolean saveTemplate(byte[] templateByteFile) {

        String sql = "INSERT INTO FMS_FS_REPORT(FS_CODE,REPORT_FILL)"
                + "VALUES(?,?)";
        Query _query;
        _query = em.createNativeQuery(sql);
       
        _query.setParameter(1, "2015_2016");
        _query.setParameter(2, templateByteFile);
        int result = _query.executeUpdate();
        if (result == 1) {
            return true;
        } else {
            return false;
        }

    }

    public byte[] getTemplate(String AccountingPre) {
            String sql = "SELECT REPORT_FILL FROM FMS_FS_REPORT where FS_CODE= ? ";
        Query _query;
        _query = em.createNativeQuery(sql);
       
        _query.setParameter(1, "2015_2016");
        if(_query.getResultList().size()>0)
        {
           // Blob reportFile=(Blob)_query.getResultList().get(0);
            byte[] reportFile1=(byte[])_query.getResultList().get(0);
//            byte[]  reportFile=(byte[])_query.getResultList().get(0);
            System.err.println("passs");
            return reportFile1;
        }
        else
        {
            return null;
        }
    }

  //      // </editor-fold>      

    
}
