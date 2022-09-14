package et.gov.eep.fcms.mapper.bank;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.fcms.entity.bank.FmsCodedTransaction;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsCodedTransactionFacade extends AbstractFacade<FmsCodedTransaction> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCodedTransactionFacade() {
        super(FmsCodedTransaction.class);
    }

    /*named query to select codedTaransaction list from FmsCodedTransaction table using status and subsidiaryId's first letter match
     returen codedTaransaction List*/
    public List<FmsCodedTransaction> getCodedTransactionInfo(FmsLuSystem fmsSystem) {
        Query query = em.createNamedQuery("FmsCodedTransaction.findByStatusAndSystemCode");
        query.setParameter("status", 0);
        query.setParameter("subsidiaryId", fmsSystem.getSystemCode() + '%');
        try {
            return (List<FmsCodedTransaction>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select codedTaransaction list from FmsCodedTransaction table using status and transaction reference
     returen codedTaransaction List*/
    public List<FmsCodedTransaction> getCodedTransactionsByTranRef(FmsCodedTransaction fmsCodedTransaction) {
        Query query = em.createNamedQuery("FmsCodedTransaction.findByStatusANDtranRef");
        query.setParameter("status", 0);
        query.setParameter("tranRef", fmsCodedTransaction.getTranRef());
        try {
            return (List<FmsCodedTransaction>) query.getResultList();
        } catch (Exception ex) {

            return null;
        }
    }

    /*native query to select codedTaransaction Id,DebitAdviceTransactinReference,DebitAdviceAmount,DebitAdviceDebitedDate
     from FMS_CODED_TRANSACTION, FMS_BANK_DEBIT_ADVICE,FMS_BANK_BRANCH_ACCOUNTS table 
     using stated criteria by passing bankCode and date
     returen selected List info*/
    public List<FmsCodedTransaction> getChequePaymentVouchers(String bankCode, String date) {
        List<FmsCodedTransaction> withdrawalsList;
        try {
            Query query = em.createNativeQuery(
                    "SELECT fct.CODED_TRANSACTON_ID  AS ctId, "
                    + "fbDebitAdv.TRAN_REF      AS Ref_No, "
                    + "fbDebitAdv.AMOUNT        AS Amounts, "
                    + "to_char(fbDebitAdv.DEBITED_DATE) as dates "
                    + "FROM FMS_CODED_TRANSACTION fct "
                    + "INNER JOIN FMS_BANK_DEBIT_ADVICE fbDebitAdv "
                    + "ON fct.TRAN_REF= fbDebitAdv.TRAN_REF "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fbba  "
                    + "ON fbba.ACCOUNT_NUMBER = fbdEBITAdv.ACCOUNT_NUMBER "
                    + "WHERE fbba.ACCOUNT_NUMBER                = '" + bankCode + "' "
                    + "AND fbDebitAdv.DEBITED_DATE              <= TO_DATE('" + date + "','yyyy/mm/dd') "
                    + "AND fct.status                           = 1 "
                    + "AND fct.recon_status                     = 0 "
                    + "AND fct.DEBIT                            != 0 "
                    + "UNION "
                    + "SELECT DISTINCT fau.account_id AS ctId, "
                    + "fcpv.CHEQUE_NUMBER    AS Ref_No, "
                    + "fau.DEBIT             AS Amounts, "
                    + "SUBSTR(fv.VOUCHER_ID, 1, 10) as dates "
                    + "FROM FMS_ACCOUNT_USE fau "
                    + "INNER JOIN FMS_CHEQUE_PAYMENT_VOUCHER fcpv "
                    + "ON fau.VOUCHER_ID= fcpv.VOUCHER_ID "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fba "
                    + "ON fau.SUBSIDARY_ID= fba.SUBSIDIARY_ID "
                    + "INNER JOIN FMS_VOUCHER fv "
                    + "ON fv.VOUCHER_ID =fau.VOUCHER_ID "
                    + "WHERE fv.STATUS                    =3 "
                    + "AND SUBSTR( fv.VOUCHER_ID, 1, 10)  <='" + date + "' "
                    + "AND fba.ACCOUNT_NUMBER             ='" + bankCode + "' "
                    + "and fau.DEBIT                            !=0 "
                    + "AND fau.STATUS                     =0", "OrderResultsAcu");
            withdrawalsList = (List<FmsCodedTransaction>) query.getResultList();
            return withdrawalsList;
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select codedTaransactionId,DebitAdviceTransactinReference,DebitAdviceAmount,DebitAdviceDebitedDate
     from FMS_CODED_TRANSACTION, FMS_BANK_CASH_DEPOSIT ,FMS_BANK_BRANCH_ACCOUNTS table 
     using stated criteria by passing bankCode and date
     returen selected List info*/
    public List<FmsCodedTransaction> getCashReceiptVouchers(String bankCode, String date) {
        List<FmsCodedTransaction> depositsList;
        try {
            Query query = em.createNativeQuery("SELECT fct.CODED_TRANSACTON_ID  AS ctId, "
                    + "fbCashDep.TRAN_REF           AS Ref_No, "
                    + "fbCashDep.AMOUNT             AS Amount, "
                    + "fbCashDep.CASH_DEPOSIT_DATE  AS Dates "
                    + "FROM FMS_CODED_TRANSACTION fct "
                    + "INNER JOIN FMS_BANK_CASH_DEPOSIT fbCashDep  "
                    + "ON fct.TRAN_REF = fbCashDep.TRAN_REF "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fbba "
                    + "ON fbba.ACCOUNT_NUMBER = fbCashDep.ACCOUNT_NUMBER "
                    + "WHERE fbba.ACCOUNT_NUMBER            = '" + bankCode + "' "
                    + "AND fbCashDep.CASH_DEPOSIT_DATE      <= TO_DATE('" + date + "','yyyy/mm/dd') "
                    + "AND fct.status                       = 1 "
                    + "AND fct.recon_status                 = 0 "
                    + "AND fct.credit                       !=0 "
                    + "UNION "
                    + "SELECT fct.CODED_TRANSACTON_ID    AS ctId, "
                    + "fbChequeDep.CHEQUE_NUMBER         AS Ref_No, "
                    + "fbChequeDep.AMOUNT                AS Amount, "
                    + "fbChequeDep.CHEQUE_DEPOSIT_DATE   AS Dates "
                    + "FROM FMS_CODED_TRANSACTION fct "
                    + "INNER JOIN FMS_BANK_CHEQUE_DEPOSIT fbChequeDep "
                    + "ON fct.TRAN_REF= fbChequeDep.cheque_number "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fbba "
                    + "ON fbba.ACCOUNT_NUMBER = fbChequeDep.ACCOUNT_NUMBER "
                    + "WHERE fbba.ACCOUNT_NUMBER                = '" + bankCode + "' "
                    + "AND fbChequeDep.CHEQUE_DEPOSIT_DATE      <= TO_DATE('" + date + "','yyyy/mm/dd') "
                    + "AND fct.status                           = 1  "
                    + "AND fct.recon_status                     = 0 "
                    + "AND fct.credit                           !=0 "
                    + "UNION "
                    + "SELECT fct.CODED_TRANSACTON_ID    AS ctId, "
                    + "fbCreditAdv.TRAN_REF              AS Ref_No, "
                    + "fbCreditAdv.AMOUNT                AS Amount, "
                    + "fbCreditAdv.CREDIT_DATE           AS Dates "
                    + "FROM FMS_CODED_TRANSACTION fct "
                    + "INNER JOIN FMS_BANK_CREDIT_ADVICE fbCreditAdv "
                    + "ON fct.TRAN_REF= fbCreditAdv.TRAN_REF "
                    + "INNER JOIN FMS_BANK_BRANCH_ACCOUNTS fbba "
                    + "ON fbba.ACCOUNT_NUMBER = fbCreditAdv.ACCOUNT_NUMBER "
                    + "WHERE fbba.ACCOUNT_NUMBER                = '" + bankCode + "' "
                    + "AND fbcreditadv.CREDIT_DATE              <= TO_DATE('" + date + "','yyyy/mm/dd') "
                    + "AND fct.status                           = 1  "
                    + "AND fct.recon_status                     = 0 "
                    + "AND fct.credit                           !=0 ", "OrderResultsAcu");

            depositsList = (List<FmsCodedTransaction>) query.getResultList();
            return depositsList;
        } catch (Exception e) {
            return null;
        }

    }

    /*native query to select diffrence of (sum of Debit and Credit balance)
     from FMS_ACCOUNT_USE, fms_voucher ,FMS_BANK_BRANCH_ACCOUNTS table 
     using stated criteria by passing statement date and bankCode
     returen selected List info*/
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
}
