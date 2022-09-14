package et.gov.eep.fcms.mapper.pettyCash;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;

/**
 *
 * @author memube
 */
@Stateless
public class FmsPettyCashReplenishmentFacade extends AbstractFacade<FmsPettyCashReplenishment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsPettyCashReplenishmentFacade() {
        super(FmsPettyCashReplenishment.class);
    }

    /*named query to select faPreparedList from FmsPettyCashReplenishment table by casher id and workflow status 
     passing parameter of fmsCasherAccount and CHECK_REJECT_VALUE
     returen faPreparedList*/
    public List<FmsPettyCashReplenishment> findPCRByCashierIdAndWfCheckRejectValue(FmsCasherAccount fmsCasherAccount, int CHECK_REJECT_VALUE) {
        Query query = em.createNamedQuery("FmsPettyCashReplenishment.findPCRByCashierIdAndWfCheckRejectValue");
        query.setParameter("cashierId", fmsCasherAccount);
        query.setParameter("wfStatus", CHECK_REJECT_VALUE);
        try {
            List<FmsPettyCashReplenishment> faPreparedList = query.getResultList();
            return faPreparedList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select faPreparedList from FmsPettyCashReplenishment table by workflow status 
     passing parameter of wfStatus
     returen faPreparedList*/
    public List<FmsPettyCashReplenishment> findPCRByWfStatus(int wfStatus) {
        Query query = em.createNamedQuery("FmsPettyCashReplenishment.findPCRByWfStatus");
        query.setParameter("wfStatus", wfStatus);
        try {
            List<FmsPettyCashReplenishment> faPreparedList = query.getResultList();
            return faPreparedList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select PettyCashReplenishmentlist from FmsPettyCashReplenishment table by cashierId
     passing parameter fmsCasherAccount
     returen PettyCashReplenishmentlist*/
    public List<FmsPettyCashReplenishment> findPCRByCashierId(FmsCasherAccount fmsCasherAccount) {
        Query query = em.createNamedQuery("FmsPettyCashReplenishment.findByCashierId");
        query.setParameter("cashierId", fmsCasherAccount);
        try {
            List<FmsPettyCashReplenishment> pcrList = query.getResultList();
            return pcrList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*native query to select all list
     from FmsPettyCashReplenishment,FMS_CASHER_ACCOUNT, hr_employees table
     by passing parameter of fmsCasherAccount
     using CHASHERaccount employee id's first letter or name match
     returen selected list value*/
    public List<FmsPettyCashReplenishment> findPCRByCashierName(FmsCasherAccount fmsCasherAccount) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_PETTY_CASH_REPLENISHMENT fpcr INNER JOIN FMS_CASHER_ACCOUNT fc "
                    + "                                         ON fpcr.CHASHER_ID = fc.id "
                    + "                                         INNER JOIN hr_employees hr "
                    + "                                         ON hr.id=fc.EMP_CODE "
                    + "                                         WHERE hr.FIRST_NAME LIKE  '" + fmsCasherAccount.getEmpCode().getFirstName().toUpperCase() + "%'", FmsPettyCashReplenishment.class);
            return (List<FmsPettyCashReplenishment>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

        /*native query to select all list
     from FmsPettyCashReplenishment,FMS_CASHER_ACCOUNT, hr_employees table
     using EMP_CODE
     returen selected list value*/
    public List<FmsPettyCashReplenishment> findAllPCR() {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_PETTY_CASH_REPLENISHMENT fpcr INNER JOIN FMS_CASHER_ACCOUNT fc "
                    + "                                         ON fpcr.CASHIER_ID = fc.id "
                    + "                                         INNER JOIN hr_employees hr "
                    + "                                         ON hr.id=fc.EMP_CODE ", FmsPettyCashReplenishment.class);
            return (List<FmsPettyCashReplenishment>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

         /*native query to select all list
     from FmsPettyCashReplenishment,FMS_PETTY_CASH_REPLENISH_DTL, FMS_DAILY_CASH_REGISTER table
     by passing parameter of fmsCasherAccount
     using casher id and workflow status
     returen selected info value*/
    public FmsPettyCashReplenishment getNonReplenishedByCashierId(FmsCasherAccount fmsCasherAccount) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_PETTY_CASH_REPLENISHMENT FPCR\n"
                    + "INNER JOIN FMS_PETTY_CASH_REPLENISH_DTL FPCRD\n"
                    + "ON FPCR.ID=FPCRD.PETTY_CASH_REPLENISHMENT_ID\n"
                    + "INNER JOIN FMS_DAILY_CASH_REGISTER FDCR\n"
                    + "ON FPCRD.DAILY_CASH_REGISTER_ID=FDCR.ID\n"
                    + "WHERE FDCR.CHASHER_ID = '" + fmsCasherAccount.getId() + "'"
                    + "AND FPCR.WF_STATUS != 10 ", FmsPettyCashReplenishment.class);
            FmsPettyCashReplenishment fmsPettyCashReplenishment = (FmsPettyCashReplenishment) query1.getResultList().get(0);
            return fmsPettyCashReplenishment;
        } catch (Exception e) {
            return null;
        }
    }

}
