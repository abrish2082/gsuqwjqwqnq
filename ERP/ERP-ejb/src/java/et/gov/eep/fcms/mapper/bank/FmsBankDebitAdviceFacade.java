package et.gov.eep.fcms.mapper.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBankDebitAdvice;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsBankDebitAdviceFacade extends AbstractFacade<FmsBankDebitAdvice> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBankDebitAdviceFacade() {
        super(FmsBankDebitAdvice.class);
    }

     /*named query to select debit information from FmsBankDebitAdvice table using debitAdviceId
    returen debit information*/
    public FmsBankDebitAdvice getBankAdviceData(FmsBankDebitAdvice fmsBankDebitAdvice) {
        Query query = em.createNamedQuery("FmsBankDebitAdvice.findByDebitAdviceId");
        query.setParameter("debitAdviceId", fmsBankDebitAdvice.getDebitAdviceId());
        try {
            FmsBankDebitAdvice debitInfo = (FmsBankDebitAdvice) query.getResultList().get(0);
            return debitInfo;
        } catch (Exception ex) {
            return null;
        }
    }

     /*named query to select debit information from FmsBankDebitAdvice table using debitedDate, transaction reference and AccountNumber
    returen debit information*/
    public FmsBankDebitAdvice getAllDataByDateAndAcountNumber(FmsBankDebitAdvice fmsBankDebitAdvice) {
        Query query = em.createNamedQuery("FmsBankDebitAdvice.findByAccNoANDdateANDtranRef");
        query.setParameter("debitedDate", fmsBankDebitAdvice.getDebitedDate());
        query.setParameter("accountNumber", fmsBankDebitAdvice.getAccountNumber());
        query.setParameter("tranRef", fmsBankDebitAdvice.getTranRef());
        try {
            FmsBankDebitAdvice debitInfo = (FmsBankDebitAdvice) query.getResultList().get(0);
            return debitInfo;
        } catch (Exception ex) {
            return null;
        }
    }
 /*named query to select debitAdvice list from FmsBankDebitAdvice table using AccountNumber
    returen debitAdvice list*/
    public List<FmsBankDebitAdvice> getCreditAdviceDate(FmsBankDebitAdvice accountNo) {
        Query query = em.createNamedQuery("FmsBankDebitAdvice.findByAccountNumber");
        query.setParameter("accountNumber", accountNo.getAccountNumber());
        try {
            ArrayList<FmsBankDebitAdvice> debitAdviceList = new ArrayList(query.getResultList());
            return debitAdviceList;

        } catch (Exception ex) {
            return null;
        }

    }
  /*named query to select debit list from FmsBankDebitAdvice table using debitedDate, transactionReference and AccountNumber
    returen debit list*/
    public List<FmsBankDebitAdvice> searchAllByAcountNumberDateAndTranRef(FmsBankDebitAdvice fmsBankDebitAdvice) {
        Query query = em.createNamedQuery("FmsBankDebitAdvice.findByAccNoANDdateANDtranRef");
        query.setParameter("debitedDate", fmsBankDebitAdvice.getDebitedDate());
        query.setParameter("accountNumber", fmsBankDebitAdvice.getAccountNumber());
        query.setParameter("tranRef", fmsBankDebitAdvice.getTranRef());
        try {
            ArrayList<FmsBankDebitAdvice> debitInfo = new ArrayList(query.getResultList());
            return debitInfo;
        } catch (Exception ex) {

            return null;
        }
    }
 /*named query to select debitTransactins list from FmsBankDebitAdvice table using debitedDate and AccountNumber
    returen debitTransactins list*/
    public List<FmsBankDebitAdvice> getDebitAdviceTransactions(FmsBankDebitAdvice fmsBankDebitAdvice) {
        Query query = em.createNamedQuery("FmsBankDebitAdvice.findTransactionByAccNoANDdate");
        query.setParameter("debitedDate", fmsBankDebitAdvice.getDebitedDate());
        query.setParameter("accountNumber", fmsBankDebitAdvice.getAccountNumber());
        try {
            ArrayList<FmsBankDebitAdvice> debitTransactins = new ArrayList(query.getResultList());
            return debitTransactins;
        } catch (Exception ex) {

            return null;
        }
    }

}
