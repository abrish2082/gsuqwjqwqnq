package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondRepaymentSchedule;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondRepaymentScheduleFacade extends AbstractFacade<FmsBondRepaymentSchedule> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondRepaymentScheduleFacade() {
        super(FmsBondRepaymentSchedule.class);
    }

    /*named query for finding RepaymentSchedule list from FmsBondRepaymentSchedule table by status*/
    public ArrayList<FmsBondRepaymentSchedule> searchFmsStatus(FmsBondRepaymentSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondRepaymentSchedule.findByStatus");
        query.setParameter("RepaymentSchedule", schedule.getRepaymentScheduleId());
        try {
            ArrayList<FmsBondRepaymentSchedule> scheduleList = new ArrayList(query.getResultList());
            return scheduleList;
        } catch (Exception ex) {
            return null;
        }
    }

       /*named query for finding RepaymentSchedule from FmsBondRepaymentSchedule table by status*/
    public FmsBondRepaymentSchedule fmsBondRepaymentScheduleinfo(FmsBondRepaymentSchedule schedule) {
        Query query = em.createNamedQuery("FmsBondRepaymentSchedule.findByStatus");
        query.setParameter("RepaymentSchedule", schedule.getRepaymentScheduleId());
        try {
            FmsBondRepaymentSchedule RepaymentSchedulenfo = (FmsBondRepaymentSchedule) query.getResultList().get(0);
            return RepaymentSchedulenfo;

        } catch (Exception ex) {
            return null;
        }
    }
}
