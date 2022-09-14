package et.gov.eep.fcms.mapper.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBankCashDeposit;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsBankCashDepositFacade extends AbstractFacade<FmsBankCashDeposit> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBankCashDepositFacade() {
        super(FmsBankCashDeposit.class);
    }

    /*named query to select CashDeposit value from FmsBankCashDeposit table using transaction reference*/
    public FmsBankCashDeposit searchByDateAcountNumberAndTranRef(FmsBankCashDeposit fmsBankCashDeposit) {
        Query query = em.createNamedQuery("FmsBankCashDeposit.findByTranRef");
        query.setParameter("tranRef", fmsBankCashDeposit.getTranRef());
        try {
            FmsBankCashDeposit cashDepositInfo = (FmsBankCashDeposit) query.getResultList().get(0);
            return cashDepositInfo;
        } catch (Exception ex) {

            return null;
        }
    }

    /*named query to select CashDeposit information from FmsBankCashDeposit table using cashDepositDate's first number or letter*/
    public FmsBankCashDeposit getBankCashDepositInfo(FmsBankCashDeposit cashDepositDate) {
        Query query = em.createNamedQuery("FmsBankCashDeposit.findByCashDepositDateLike");
        query.setParameter("cashDepositDate", cashDepositDate.getCashDepositDate());
        try {
            FmsBankCashDeposit cashDepositInfo = (FmsBankCashDeposit) query.getResultList().get(0);
            return cashDepositInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select CashDeposit date from FmsBankCashDeposit table using accountNumber*/
    public List<FmsBankCashDeposit> getCashDepositDate(FmsBankCashDeposit accountNo) {
        Query query = em.createNamedQuery("FmsBankCashDeposit.findByAccountNumber");
        query.setParameter("accountNumber", accountNo.getAccountNumber());
        try {
            ArrayList<FmsBankCashDeposit> cashDepositList = new ArrayList(query.getResultList());
            return cashDepositList;

        } catch (Exception ex) {

            return null;
        }

    }

    /*named query to select CashDeposit list from FmsBankCashDeposit table using accountNumber, cashDepositDate, and transaction reference*/
    public List<FmsBankCashDeposit> searchAllByAcountNumberDateAndTranRef(FmsBankCashDeposit fmsBankCashDeposit) {
        Query query = em.createNamedQuery("FmsBankCashDeposit.findByAccNoANDdateANDtranRef");
        query.setParameter("cashDepositDate", fmsBankCashDeposit.getCashDepositDate());
        query.setParameter("accountNumber", fmsBankCashDeposit.getAccountNumber());
        query.setParameter("tranRef", fmsBankCashDeposit.getTranRef());
        try {
            ArrayList<FmsBankCashDeposit> cashDepositInfo = new ArrayList(query.getResultList());
            return cashDepositInfo;
        } catch (Exception ex) {

            return null;
        }
    }

    /*named query to select CashDeposit list from FmsBankCashDeposit table using accountNumber and cashDepositDate*/
    public List<FmsBankCashDeposit> getCashDepositTransactions(FmsBankCashDeposit fmsBankCashDeposit) {
        Query query = em.createNamedQuery("FmsBankCashDeposit.findTransactionByAccNoANDdate");
        query.setParameter("cashDepositDate", fmsBankCashDeposit.getCashDepositDate());
        query.setParameter("accountNumber", fmsBankCashDeposit.getAccountNumber());
        try {
            ArrayList<FmsBankCashDeposit> cashDepositTransactins = new ArrayList(query.getResultList());
            return cashDepositTransactins;
        } catch (Exception ex) {

            return null;
        }
    }

    /*named query to select CashDeposit value from FmsBankCashDeposit table using cashDepositDate's first number or letter*/
    public ArrayList<FmsBankCashDeposit> searchCashDepositByDate(FmsBankCashDeposit cashDepositDate) {
        Query query = em.createNamedQuery("FmsBankCashDeposit.findByCashDepositDateLike");
        query.setParameter("cashDepositDate", cashDepositDate.getCashDepositDate().toString() + '%');
        try {
            ArrayList<FmsBankCashDeposit> cashDepositList = new ArrayList(query.getResultList());
            return cashDepositList;

        } catch (Exception ex) {
            return null;
        }

    }
}
