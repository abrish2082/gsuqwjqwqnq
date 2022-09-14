package et.gov.eep.fcms.mapper.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.fcms.entity.bank.FmsBankChequeDeposit;
import et.gov.eep.commonApplications.mapper.AbstractFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsBankChequeDepositFacade extends AbstractFacade<FmsBankChequeDeposit> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBankChequeDepositFacade() {
        super(FmsBankChequeDeposit.class);
    }

     /*named query to select chequeDeposit information from FmsBankChequeDeposit table using chequeNumber*/
    public FmsBankChequeDeposit getAllDataByDateAndAcountNumber(FmsBankChequeDeposit fmsBankChequeDeposit) {
        Query query = em.createNamedQuery("FmsBankChequeDeposit.findByAccNoANDdateANDchkNo");
        query.setParameter("chequeNumber", fmsBankChequeDeposit.getChequeNumber());
        try {
            FmsBankChequeDeposit chequeDepositInfo = (FmsBankChequeDeposit) query.getResultList().get(0);
            return chequeDepositInfo;
        } catch (Exception ex) {

            return null;
        }
    }

         /*named query to select chequeDeposit information from FmsBankChequeDeposit table using chequeDepositeDate*/
    public FmsBankChequeDeposit getBankChequeDepositInfo(FmsBankChequeDeposit chequeDepositDate) {
        Query query = em.createNamedQuery("FmsBankChequeDeposit.findByChequeDepositDateLike");
        query.setParameter("chequeDepositDate", chequeDepositDate.getChequeDepositDate());
        try {
            FmsBankChequeDeposit chequeDepositInfo = (FmsBankChequeDeposit) query.getResultList().get(0);
            return chequeDepositInfo;
        } catch (Exception ex) {

            return null;
        }
    }
    
    /*named query to select chequeDeposit list from FmsBankChequeDeposit table using chequeDepositDate*/
    public ArrayList<FmsBankChequeDeposit> searchChequeDepositByDate(FmsBankChequeDeposit chequeDepositDate) {
        Query query = em.createNamedQuery("FmsBankChequeDeposit.findByChequeDepositDateLike");
        query.setParameter("chequeDepositDate", chequeDepositDate.getChequeDepositDate());
        try {
            ArrayList<FmsBankChequeDeposit> ChequeDepositInfo = new ArrayList(query.getResultList());
            return ChequeDepositInfo;
        } catch (Exception ex) {

            return null;
        }

    }

    /*named query to select chequeDeposit list from FmsBankChequeDeposit table using accountNumber*/
    public List<FmsBankChequeDeposit> getChequeDepositDate(FmsBankChequeDeposit accountNumber) {
        Query query = em.createNamedQuery("FmsBankChequeDeposit.findByAccountNumber");
        query.setParameter("accountNumber", accountNumber.getAccountNumber());
        try {
            ArrayList<FmsBankChequeDeposit> chequeDepositList = new ArrayList(query.getResultList());
            return chequeDepositList;

        } catch (Exception ex) {

            return null;
        }

    }

        /*named query to select chequeDeposit transaction list from FmsBankChequeDeposit table using accountNumber and chequeDepositeDate*/
    public List<FmsBankChequeDeposit> getChequeDepositTransactionsByChequeNo(FmsBankChequeDeposit fmsBankChequeDeposit) {
        Query query = em.createNamedQuery("FmsBankChequeDeposit.findTransactionByAccNoANDdate");
        query.setParameter("chequeDepositDate", fmsBankChequeDeposit.getChequeDepositDate());
        query.setParameter("accountNumber", fmsBankChequeDeposit.getAccountNumber());
        try {
            ArrayList<FmsBankChequeDeposit> chequeDepositTransactins = new ArrayList(query.getResultList());
            return chequeDepositTransactins;
        } catch (Exception ex) {

            return null;
        }
    }

        /*named query to select chequeDeposit list from FmsBankChequeDeposit table using accountNumber, chequeNumber and ChequeDepositeDate*/
    public List<FmsBankChequeDeposit> searchAllByAcountNumberDateAndTranRef(FmsBankChequeDeposit fmsBankChequeDeposit) {
        Query query = em.createNamedQuery("FmsBankChequeDeposit.findByAccNoANDdateANDchkNo");
        query.setParameter("chequeDepositDate", fmsBankChequeDeposit.getChequeDepositDate());
        query.setParameter("accountNumber", fmsBankChequeDeposit.getAccountNumber());
        query.setParameter("chequeNumber", fmsBankChequeDeposit.getChequeNumber());
        try {
            ArrayList<FmsBankChequeDeposit> chequeDepositInfo = new ArrayList(query.getResultList());
            return chequeDepositInfo;
        } catch (Exception ex) {

            return null;
        }
    }

        /*named query to select chequeDeposit transaction list from FmsBankChequeDeposit table using accountNumber and chequeDepositeDate*/
    public List<FmsBankChequeDeposit> getChequeDepositTransactions(FmsBankChequeDeposit fmsBankCashDeposit) {
        Query query = em.createNamedQuery("FmsBankChequeDeposit.findTransactionByAccNoANDdate");
        query.setParameter("chequeDepositDate", fmsBankCashDeposit.getChequeDepositDate());
        query.setParameter("accountNumber", fmsBankCashDeposit.getAccountNumber());
        try {
            ArrayList<FmsBankChequeDeposit> chequeDepositTransactins = new ArrayList(query.getResultList());
            return chequeDepositTransactins;
        } catch (Exception ex) {

            return null;
        }
    }
}
