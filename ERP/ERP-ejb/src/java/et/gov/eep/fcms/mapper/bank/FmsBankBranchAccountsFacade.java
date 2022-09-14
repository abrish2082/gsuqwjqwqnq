package et.gov.eep.fcms.mapper.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.commonApplications.mapper.AbstractFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsBankBranchAccountsFacade extends AbstractFacade<FmsBankBranchAccounts> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBankBranchAccountsFacade() {
        super(FmsBankBranchAccounts.class);
    }

    /*named query to select Account value from FmsBankBranchAccounts table using accountNumber*/
    public ArrayList<FmsBankBranchAccounts> searchBankAcctByAccNumber(FmsBankBranchAccounts accountNumber) {
        Query query = em.createNamedQuery("FmsBankBranchAccounts.findByAccountNumberLike");
        query.setParameter("accountNumber", accountNumber.getAccountNumber() + '%');
        try {
            ArrayList<FmsBankBranchAccounts> accounttList = new ArrayList(query.getResultList());
            return accounttList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Account value from FmsBankBranchAccounts table using bankAccountId*/
    public FmsBankBranchAccounts getBankAccDetailInfo(FmsBankBranchAccounts bankAccountId) {
        Query query = em.createNamedQuery("FmsBankBranchAccounts.findByBankAccountId");
        query.setParameter("bankAccountId", bankAccountId.getBankAccountId());
        try {
            FmsBankBranchAccounts bankAccInfo = (FmsBankBranchAccounts) query.getResultList().get(0);
            return bankAccInfo;
        } catch (Exception ex) {

            return null;
        }
    }

    /*named query to select Account value from FmsBankBranchAccounts table using accountNumber*/
    public FmsBankBranchAccounts getBalanceByAcctNo(FmsBankBranchAccounts accountNumber) {
        Query query = em.createNamedQuery("FmsBankBranchAccounts.findByAccountNumber");
        query.setParameter("accountNumber", accountNumber.getAccountNumber());
        try {
            FmsBankBranchAccounts balance = (FmsBankBranchAccounts) query.getResultList().get(0);
            return balance;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Account list value from FmsBankBranchAccounts table using bankAccountId*/
    public List<FmsBankBranchAccounts> getListinfo(FmsBankBranchAccounts bankaccountid) {
        Query query = em.createNamedQuery("FmsBankBranchAccounts.findByBankAccountId");
        query.setParameter("bankAccountId", bankaccountid.getBankAccountId());
        try {
            ArrayList<FmsBankBranchAccounts> accounttList = new ArrayList(query.getResultList());
            return accounttList;
        } catch (Exception ex) {
            return null;
        }
    }
    
/*named query to select Account list value from FmsBankBranchAccounts table using bankAccountId*/
    public List<FmsBankBranchAccounts> getAccountNumberByBankAccountId(FmsBankBranchAccounts bankAccountId) {
        ArrayList<FmsBankBranchAccounts> accountses = null;
        Query query = em.createNamedQuery("FmsBankBranchAccounts.findByBankAccountId");
        query.setParameter("bankAccountId", bankAccountId.getBankAccountId());
        try {
            accountses = new ArrayList(query.getResultList());
            return accountses;
        } catch (Exception ex) {

            return null;
        }
    }
    
/*native query to select all Account list value from FmsBankBranchAccounts table with requirements
    returns all availabel list*/    
    public boolean findDup(FmsBankBranchAccounts fmsBankBranchAccounts) {
        boolean dup;
        try {
            Query query = em.createNativeQuery("SELECT * "
                    + "FROM FMS_BANK_BRANCH_ACCOUNTS fbb "
                    + "WHERE fbb.ACCOUNT_NUMBER = '" + fmsBankBranchAccounts.getAccountNumber()
                    + "' OR fbb.SUBSIDIARY_ID = '" + fmsBankBranchAccounts.getSubsidiaryId() + "'", FmsBankBranchAccounts.class);
            if (query.getResultList().size() > 0) {
                dup = true;
            } else {
                dup = false;
            }
            return dup;
        } catch (Exception ex) {
            return false;
        }
    }

}
