package et.gov.eep.fcms.mapper.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBankCreditAdvice;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsBankCreditAdviceFacade extends AbstractFacade<FmsBankCreditAdvice> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBankCreditAdviceFacade() {
        super(FmsBankCreditAdvice.class);
    }
    
    /*named query to select creditAdvice information from FmsBankCreditAdvice table using creditDate,AccountNumber and transaction reference
    returen creditAdvice information*/
    public FmsBankCreditAdvice getAllDataByDateAndAcountNumber(FmsBankCreditAdvice fmsBankCreditAdvice) {
        Query query = em.createNamedQuery("FmsBankCreditAdvice.findByAccNoANDdateANDtranRef");
        query.setParameter("creditDate", fmsBankCreditAdvice.getCreditDate());
        query.setParameter("accountNumber", fmsBankCreditAdvice.getAccountNumber());
        query.setParameter("tranRef", fmsBankCreditAdvice.getTranRef());
        try {
            FmsBankCreditAdvice creditInfo = (FmsBankCreditAdvice) query.getResultList().get(0);
            return creditInfo;
        } catch (Exception ex) {

            return null;
        }
    }

        /*named query to select creditAdvice list from FmsBankCreditAdvice table using AccountNumber
    returen creditAdvice list*/
    public List<FmsBankCreditAdvice> getCreditAdviceDate(FmsBankCreditAdvice accountNo) {
        Query query = em.createNamedQuery("FmsBankCreditAdvice.findByAccountNumber");
        query.setParameter("accountNumber", accountNo.getAccountNumber());
        try {
            ArrayList<FmsBankCreditAdvice> creditAdviceList = new ArrayList(query.getResultList());
            return creditAdviceList;

        } catch (Exception ex) {

            return null;
        }

    }

        /*named query to select creditAdvice list from FmsBankCreditAdvice table using creditDate,AccountNumber and transaction reference
    returen creditAdvice list*/
    public List<FmsBankCreditAdvice> searchAllByAcountNumberDateAndTranRef(FmsBankCreditAdvice fmsBankCreditAdvice) {
        Query query = em.createNamedQuery("FmsBankCreditAdvice.findByAccNoANDdateANDtranRef");
        query.setParameter("creditDate", fmsBankCreditAdvice.getCreditDate());
        query.setParameter("accountNumber", fmsBankCreditAdvice.getAccountNumber());
        query.setParameter("tranRef", fmsBankCreditAdvice.getTranRef());
        try {
            ArrayList<FmsBankCreditAdvice> creditInfo = new ArrayList(query.getResultList());
            return creditInfo;
        } catch (Exception ex) {

            return null;
        }
    }

        /*named query to select creditAdvice list from FmsBankCreditAdvice table using creditDate,AccountNumber
    returen creditAdvice list*/
    public List<FmsBankCreditAdvice> getCreditAdviceTransactions(FmsBankCreditAdvice fmsBankCreditAdvice) {
        Query query = em.createNamedQuery("FmsBankCreditAdvice.findTransactionByAccNoANDdate");
        query.setParameter("creditDate", fmsBankCreditAdvice.getCreditDate());
        query.setParameter("accountNumber", fmsBankCreditAdvice.getAccountNumber());
        try {
            ArrayList<FmsBankCreditAdvice> creditTransactins = new ArrayList(query.getResultList());
            return creditTransactins;
        } catch (Exception ex) {

            return null;
        }
    }
}
