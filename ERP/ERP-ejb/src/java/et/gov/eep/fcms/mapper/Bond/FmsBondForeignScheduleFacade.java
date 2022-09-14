/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondForeignScheduleFacade extends AbstractFacade<FmsBondForeignSchedule> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondForeignScheduleFacade() {
        super(FmsBondForeignSchedule.class);
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using repayment schedule id*/
    public FmsBondForeignSchedule getFmsBondForeignSchedule(FmsBondForeignSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByLRepaymentSchId");
        query.setParameter("lRepaymentSchId", schedule.getLRepaymentSchId());
        try {
            FmsBondForeignSchedule scheduleList = (FmsBondForeignSchedule) (query.getResultList().get(0));
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using serial number and number of installment */
    public FmsBondForeignSchedule getFmsForeignSchedule_instlemet(FmsBondForeignSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByserialNo_inslement");
        query.setParameter("seriaNo", schedule.getSerialNo());
        query.setParameter("noOfInstallmen", schedule.getNoOfInstallmen());
        try {
            FmsBondForeignSchedule scheduleList = (FmsBondForeignSchedule) (query.getResultList().get(0));
            return scheduleList;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using Bond foreign id */
    public FmsBondForeignSchedule getFmsForeignSchedule(FmsBondForeignSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByBondForeignId");
        query.setParameter("BondForeignId", schedule.getBondForeignId());
        try {
            FmsBondForeignSchedule scheduleList = (FmsBondForeignSchedule) (query.getResultList().get(0));
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using status matching the first letter */
    public ArrayList<FmsBondForeignSchedule> searchFmsStatus(FmsBondForeignSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByStatuslike");
        Date SYSDATE;
        SYSDATE = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(SYSDATE); // Now use today date.
        c.add(Calendar.DATE, 15);
        query.setParameter("installmentDueDate", SYSDATE);
        String S = "notpaid";
        query.setParameter("status", S);

        try {
            ArrayList<FmsBondForeignSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using installment due date and status*/
    public ArrayList<FmsBondForeignSchedule> searchFmsStatusmuchurd(FmsBondForeignSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByStatusmuchrd");
        Date SYSDATE;
        SYSDATE = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(SYSDATE); // Now use today date.
        c.add(Calendar.DATE, 30);
        query.setParameter("installmentDueDate", SYSDATE);
        String S = "notpaid";
        query.setParameter("status", S);
        query.setParameter("day", c.getTime());

        try {
            ArrayList<FmsBondForeignSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using serial number */
    public ArrayList<FmsBondForeignSchedule> searchForingns(FmsBondForeignSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findBySerialNo");
        query.setParameter("serialNo", schedule.getSerialNo());
        try {
            ArrayList<FmsBondForeignSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using Bond foreign id */
    public ArrayList<FmsBondForeignSchedule> searchFmsBondId(FmsBondForeignSchedule application) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByBondForeignIdlike");
        query.setParameter("BondTypeId", application.getBondForeignId());
        try {
            ArrayList<FmsBondForeignSchedule> applicationsList = new ArrayList(query.getResultList());
            return applicationsList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using status (payed)*/
    public ArrayList<FmsBondForeignSchedule> searchForingns_Bondpayed() {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByStatus");
        query.setParameter("status", "payed");
        try {
            ArrayList<FmsBondForeignSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using serial number*/
    public ArrayList<FmsBondForeignSchedule> searchForingns_notpayed(FmsBondForeignSchedule application) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findBySerialNo");
        query.setParameter("serialNo", application.getBondForeignId().getSerialNo());
        try {
            ArrayList<FmsBondForeignSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using installment due date bettween start and endDate */
    public ArrayList<FmsBondForeignSchedule> searchStartEndDate(FmsBondForeignSchedule application) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findByStartendDate");
        query.setParameter("StartDate", application.getStartDate());
        query.setParameter("EndDate", application.getEndDate());
        try {
            ArrayList<FmsBondForeignSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
    }

    /*named query to select Bond foreign schedule list from Bond foreign schedule using serial number
     and  installment due date bettween start and endDate  */
    public List<FmsBondForeignSchedule> searchBySerialNoAndDueDate(FmsBondForeignSchedule forignscSchedule) {
        Query query = em.createNamedQuery("FmsBondForeignSchedule.findBySerialNoAndDueDate");
        query.setParameter("serialNo", forignscSchedule.getBondForeignId().getSerialNo());
        query.setParameter("StartDate", forignscSchedule.getStartDate());
        query.setParameter("EndDate", forignscSchedule.getEndDate());
        try {
            ArrayList<FmsBondForeignSchedule> schedules = new ArrayList<>(query.getResultList());
            return schedules;
        } catch (Exception ex) {
            return null;
        }

    }
    /*native query to select all Bond foreign schedule list from Bond foreign schedule using serial number
     and  installment due date bettween start and endDate  */
    public List<FmsBondForeignSchedule> getByStartAndEndDate(String fromStartDate, String toEndDate, FmsBondForeignSchedule forignscSchedule) {

        try {
            Query query = em.createNativeQuery("select * from FMS_BOND_FOREIGN_SCHEDULE B where B.SERIAL_NO ='" + forignscSchedule.getBondForeignId().getSerialNo() + "' "
                    + "AND B.INSTALLMENT_DUE_DATE BETWEEN '" + fromStartDate + "' AND '" + toEndDate + "'", FmsBondForeignSchedule.class);

            return (List<FmsBondForeignSchedule>) query.getResultList();

        } catch (Exception ex) {

            return null;
        }

    }

}
