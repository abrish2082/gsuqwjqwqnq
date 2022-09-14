package et.gov.eep.fcms.mapper.pettyCash;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.pettyCash.FmsDailyCashRegister;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Stateless
public class FmsDailyCashRegisterFacade extends AbstractFacade<FmsDailyCashRegister> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsDailyCashRegisterFacade() {
        super(FmsDailyCashRegister.class);
    }

    /*named query to select petty cash info from FmsDailyCashRegister table by id
     returen casher info*/
    public FmsDailyCashRegister getDataById(FmsDailyCashRegister dailyCashRegisterEnty) {
        Query query = em.createNamedQuery("FmsDailyCashRegister.findById");
        query.setParameter("id", dailyCashRegisterEnty.getId());
        try {
            FmsDailyCashRegister selectPettyCash = (FmsDailyCashRegister) query.getResultList().get(0);
            return selectPettyCash;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select operating list from FmsDailyCashRegister table by bank chasherId
     returen casher list*/
    public ArrayList<FmsDailyCashRegister> searchAmountAndDateByCasherId(FmsDailyCashRegister cahserId) {
        Query query = em.createNamedQuery("FmsDailyCashRegister.findByChaser", FmsDailyCashRegister.class);
        query.setParameter("chasherId", cahserId.getChasherId());
        try {
            ArrayList<FmsDailyCashRegister> operatingList = new ArrayList(query.getResultList());
            return operatingList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*named query to select operating list from FmsDailyCashRegister table by bank chasherId
     returen casher list*/
    public List<FmsDailyCashRegister> searchAmount(FmsDailyCashRegister cashRegister) {
        Query query = em.createNamedQuery("FmsDailyCashRegister.findByChaser");
        query.setParameter("chasherId", cashRegister.getChasherId());
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    /*named query to select registrationDate list from FmsDailyCashRegister table by bank registration date
     returen registrationDate list*/
    public List<FmsDailyCashRegister> searchByDate(FmsDailyCashRegister dailyCashRegisterEnty) {
        List<FmsDailyCashRegister> listdate;
        Query query = em.createNamedQuery("FmsDailyCashRegister.findByRegistrationDate", FmsDailyCashRegister.class);
        query.setParameter("registrationDate", dailyCashRegisterEnty.getRegistrationDate() + "%");

        try {
            listdate = (List<FmsDailyCashRegister>) query.getResultList();
            return listdate;
        } catch (Exception e) {
            return null;
        }

    }

    /*named query to select cashRegistersList list from FmsDailyCashRegister table by casher id and status
     returen cashRegistersList list*/
    public List<FmsDailyCashRegister> findDailyPaidListByChaserIdAndStatus(FmsCasherAccount casherAccountEnty) {
        try {
            Query query1 = em.createNamedQuery("FmsDailyCashRegister.findByCashierIdAndStatus", FmsDailyCashRegister.class);
            query1.setParameter("chasherId", casherAccountEnty);
            query1.setParameter("status", 1);
            List<FmsDailyCashRegister> cashRegistersList = query1.getResultList();
            return cashRegistersList;
        } catch (Exception e) {
            return null;
        }
    }
    
    /*named query to select cashRegistersList list from FmsDailyCashRegister table by casher id and status
    passing parameter  casherAccountEnty and dailyCashRegStatus
    returen cashRegistersList list*/
      public List<FmsDailyCashRegister> findByCashierIdAndStatus(FmsCasherAccount casherAccountEnty, int dailyCashRegStatus) {
        try {
            Query query1 = em.createNamedQuery("FmsDailyCashRegister.findByCashierIdAndStatus", FmsDailyCashRegister.class);
            query1.setParameter("chasherId", casherAccountEnty);
            query1.setParameter("status", dailyCashRegStatus);
            List<FmsDailyCashRegister> cashRegistersList = query1.getResultList();
            return cashRegistersList;
        } catch (Exception e) {
            return null;
        }
    }
      
    /*named query to select cashRegistersList list from FmsDailyCashRegister table by casher id 
     returen cashRegistersList list*/
    public List<FmsDailyCashRegister> findByCashierIdAndWfStatus1(FmsCasherAccount casherAccountEnty) {
        try {
            Query query1 = em.createNamedQuery("FmsDailyCashRegister.findByCashierIdAndWfStatus", FmsDailyCashRegister.class);
            query1.setParameter("chasherId", casherAccountEnty);
            List<FmsDailyCashRegister> cashRegistersList = query1.getResultList();
            return cashRegistersList;
        } catch (Exception e) {
            return null;
        }
    }

       /*native query to select all list
     from FMS_DAILY_CASH_REGISTER,FMS_PETTY_CASH_REPLENISH_DTL, DAILY_CASH_REGISTER_ID,FMS_PETTY_CASH_REPLENISHMENTtable 
    by passing parameter of fmsCasherAccountand fmsPettyCashReplenishment
    using CHASHER_ID , pettyCashReplenishment id
     returen selected list value*/
    public List<FmsDailyCashRegister> findByCashierIdAndWfStatus(FmsCasherAccount fmsCasherAccount, FmsPettyCashReplenishment fmsPettyCashReplenishment) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_DAILY_CASH_REGISTER FDCR\n"
                    + "INNER JOIN FMS_PETTY_CASH_REPLENISH_DTL FPCRD \n"
                    + "ON FPCRD.DAILY_CASH_REGISTER_ID=FDCR.ID\n"
                    + "INNER JOIN FMS_PETTY_CASH_REPLENISHMENT FPCR\n"
                    + "ON FPCR.ID=FPCRD.PETTY_CASH_REPLENISHMENT_ID\n"
                    + "WHERE FDCR.CHASHER_ID = '" + fmsCasherAccount.getId() + "'"
                    + "AND FPCR.ID = '" + fmsPettyCashReplenishment.getId() + "'", FmsDailyCashRegister.class);
            List<FmsDailyCashRegister> cashRegistersList = query1.getResultList();
            return cashRegistersList;
        } catch (Exception e) {
            return null;
        }
    }

           /*native query to select all list
     from FMS_DAILY_CASH_REGISTER,FMS_CASHER_ACCOUNT, hr_employees table 
    by passing hr employee object 
    using hr first name matching the first letter
     returen selected list value*/
    public List<FmsDailyCashRegister> getListByName(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_DAILY_CASH_REGISTER fdcr  INNER JOIN FMS_CASHER_ACCOUNT fc "
                    + "                                         ON fdcr.CHASHER_ID = fc.id "
                    + "                                         INNER JOIN hr_employees hr "
                    + "                                         ON hr.id=fc.EMP_CODE "
                    + "                                         WHERE hr.FIRST_NAME LIKE  '" + empEnty.getFirstName().toUpperCase() + "%'", FmsDailyCashRegister.class);
            return (List<FmsDailyCashRegister>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
