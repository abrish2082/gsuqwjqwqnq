package et.gov.eep.fcms.mapper.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsBankAccountFacade extends AbstractFacade<FmsBankAccount> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBankAccountFacade() {
        super(FmsBankAccount.class);
    }

    /*named query to select bankAcc value from FmsBankAccount table using bankId*/
    public FmsBankAccount getBankAccInfo(FmsBankAccount bankId) {
        Query query = em.createNamedQuery("FmsBankAccount.findByBankID");
        query.setParameter("bankId", bankId);
        try {
            FmsBankAccount bankAccInfo = (FmsBankAccount) query.getResultList().get(0);
            return bankAccInfo;
        } catch (Exception ex) {

            return null;
        }
    }

    /*named query to select bankAcc value from FmsBankAccount table using bankId*/
    public FmsBankAccount getAccountNumberByBranchName(FmsBankAccount fmsBankAccount) {
        Query query = em.createNamedQuery("FmsBankAccount.findByBranchName");
        query.setParameter("bankId", fmsBankAccount.getBankId());
        try {
            FmsBankAccount bankAccInfo = (FmsBankAccount) query.getResultList().get(0);
            return bankAccInfo;
        } catch (Exception ex) {

            return null;
        }
    }
/*named query to select bankAcc value from FmsBankAccount table using bankAccountId*/
    public FmsBankAccount getinfoByBranchName(FmsBankAccount fmsBankAccount) {
        Query query = em.createNamedQuery("FmsBankAccount.findByBankAccountId");
        query.setParameter("bankAccountId", fmsBankAccount.getBankAccountId());
        try {
            FmsBankAccount bankAccInfo = (FmsBankAccount) query.getResultList().get(0);
            return bankAccInfo;
        } catch (Exception ex) {

            return null;
        }
    }

    /*named query to select bankAcc list value from FmsBankAccount table using bankId*/
    public ArrayList<FmsBankAccount> getBranchNameByID(FmsBankAccount fmsBankAccount) {
        Query query = em.createNamedQuery("FmsBankAccount.findByBankID");
        query.setParameter("bankId", fmsBankAccount.getBankId());
        try {
            ArrayList<FmsBankAccount> bankAccList = new ArrayList(query.getResultList());
            return bankAccList;

        } catch (Exception ex) {

            return null;
        }

    }

      /*named query to select bankAcc list value from FmsBankAccount table using bankId*/
    public ArrayList<FmsBankAccount> searchBankAccByBankName(FmsBankAccount fmsBankAccount) {
        Query query = em.createNamedQuery("FmsBankAccount.findByBankID");
        query.setParameter("bankId", fmsBankAccount.getBankId());
        try {
            ArrayList<FmsBankAccount> bankAccInfo = new ArrayList(query.getResultList());
            return bankAccInfo;
        } catch (Exception ex) {

            return null;
        }
    }

      /*named query to select bankAcc list value from FmsBankAccount table using bankId and branch name*/
    public ArrayList<FmsBankAccount> searchByBankAndBranchName(FmsBankAccount fmsBankAccount) {
        Query query = em.createNamedQuery("FmsBankAccount.findByBankNameAndBranchName");
        query.setParameter("bankId", fmsBankAccount.getBankId());
        query.setParameter("branchName", fmsBankAccount.getBranchName());
        try {
            ArrayList<FmsBankAccount> bankAccList = new ArrayList(query.getResultList());
            return bankAccList;
        } catch (Exception ex) {

            return null;
        }
    }

      /*native query to select all list value from FmsBankAccount table by passing fmsBankBranchAccounts object,
     return all list value*/
    public List<FmsBankAccount> searchBankAcctByAccNumber(FmsBankBranchAccounts fmsBankBranchAccounts) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_BANK_BRANCH_ACCOUNTS fbba "
                    + "INNER JOIN FMS_BANK_ACCOUNT fba "
                    + "ON fbba.bank_account_id=fba.bank_account_id "
                    + "WHERE fbba.branch_account_id= '" + fmsBankBranchAccounts.getBranchAccountId() + "'", FmsBankAccount.class);
            return (List<FmsBankAccount>) query1.getResultList();

        } catch (Exception ex) {

            return null;
        }

    }
}
