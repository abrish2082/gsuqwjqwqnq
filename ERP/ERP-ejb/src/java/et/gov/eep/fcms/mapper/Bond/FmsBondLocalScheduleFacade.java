package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondLocalScheduleFacade extends AbstractFacade<FmsBondLocalSchedule> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondLocalScheduleFacade() {
        super(FmsBondLocalSchedule.class);
    }

    /*named query for finding local Schedule from localSchedule  table by installmentDueDate*/
    public FmsBondLocalSchedule fmsBondRepaymentScheduleinfo(FmsBondLocalSchedule repaymentSchedule) {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findByInstallmentDueDatelike");
        Date SYSDATE;
        SYSDATE = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(SYSDATE); // Now use today date.
        c.add(Calendar.DATE, 15);
        query.setParameter("installmentDueDate", SYSDATE);
        String S = "notpaid";
        query.setParameter("status", S);
        try {
            FmsBondLocalSchedule RepaymentSchedulenfo = (FmsBondLocalSchedule) query.getResultList().get(0);
            return RepaymentSchedulenfo;

        } catch (Exception ex) {
            return null;
        }
    }
/*named query for finding local Schedule from localSchedule  table by localBondId*/
    public FmsBondLocalSchedule getFmsLocalSchedule(FmsBondLocalSchedule repaymentSchedule) {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findByLocalBondId");
        query.setParameter("localBondId", repaymentSchedule.getLocalBondId());
        try {
            FmsBondLocalSchedule scheduleList = (FmsBondLocalSchedule) (query.getResultList().get(0));
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }
    
/*named query for finding local Schedule from localSchedule  table by serial number and number of installment*/
    public FmsBondLocalSchedule getFmsLocalSchedule_instlment(FmsBondLocalSchedule repaymentSchedule) {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findBySerialNo_instlment");
        query.setParameter("serialNo", repaymentSchedule.getSerialNo());
        query.setParameter("noOfInstallmen", repaymentSchedule.getNoOfInstallmen());

        try {
            FmsBondLocalSchedule scheduleList = (FmsBondLocalSchedule) (query.getResultList().get(0));
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding local Schedule from localSchedule  table by installment due date and with status not paid*/
    public ArrayList<FmsBondLocalSchedule> searchFmsStatus(FmsBondLocalSchedule repaymentSchedule) {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findByInstallmentDueDatelike");
        Date SYSDATE;
        SYSDATE = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(SYSDATE); // Now use today date.
        c.add(Calendar.DATE, 15);
        query.setParameter("installmentDueDate", SYSDATE);
        String S = "notpaid";
        query.setParameter("status", S);

        try {
            ArrayList<FmsBondLocalSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query for finding local Schedule list from localSchedule  table by serialNo's matching the first letter or word*/
    public ArrayList<FmsBondLocalSchedule> searchLocals(FmsBondLocalSchedule repaymentSchedule) {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findBySerialNoLike");
        query.setParameter("serialNo", repaymentSchedule.getSerialNo() + '%');
        try {
            ArrayList<FmsBondLocalSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

      /*named query for finding local Schedule list from localSchedule  table by serialNo*/
    public ArrayList<FmsBondLocalSchedule> searchLocalStatusNotPayed(FmsBondLocalSchedule repaymentSchedule) {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findBySerialNo");
        query.setParameter("serialNo", repaymentSchedule.getLocalBondId().getSerialNo());
        try {
            ArrayList<FmsBondLocalSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }
  /*named query for finding local Schedule list from localSchedule  table by due date(between start and end date)*/
    public ArrayList<FmsBondLocalSchedule> searchStartEndDate(FmsBondLocalSchedule repaymentSchedule) {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findByDueDate");
        query.setParameter("StartDate", repaymentSchedule.getStartDate());
        query.setParameter("EndDate", repaymentSchedule.getEndDate());
        try {
            ArrayList<FmsBondLocalSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }

    }

      /*named query for finding local Schedule list from localSchedule  table by status*/
    public List<FmsBondLocalSchedule> searchpaydLocalsBond() {
        Query query = em.createNamedQuery("FmsBondLocalSchedule.findByStatus");
        query.setParameter("status", "33");
        try {
            List<FmsBondLocalSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }
    
  /*native query for finding local Schedule list from localSchedule  table by passing due date(between start and end date) and
    repaymentSchedule object return all value*/
    public List<FmsBondLocalSchedule> getByStartAndEndDate(String fromStartDate, String toEndDate, FmsBondLocalSchedule repaymentSchedule) {

        try {
            Query query = em.createNativeQuery("select * from FMS_BOND_LOCAL_SCHEDULE B where B.SERIAL_NO ='" + repaymentSchedule.getSerialNo() + "' "
                    + "AND B.INSTALLMENT_DUE_DATE BETWEEN '" + fromStartDate + "' AND '" + toEndDate + "'", FmsBondLocalSchedule.class);

            return (List<FmsBondLocalSchedule>) query.getResultList();

        } catch (Exception ex) {

            return null;
        }
    }
    
  /*native query for finding local Schedule list from localSchedule  table by passing serial number and status return all value*/
    public List<FmsBondLocalSchedule> getNumberOfNotPaidStatus(String SerialNo, int NOT_PAID) {
        Query query = em.createNativeQuery("SELECT * FROM  fms_Bond_local_schedule where serial_no='" + SerialNo + "' and status='" + NOT_PAID + "'");
        List<FmsBondLocalSchedule> unPaidStatusList = new ArrayList<>();
        unPaidStatusList = query.getResultList();
        return unPaidStatusList;
    }

}
