package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;
import et.gov.eep.fcms.mapper.Bond.FmsBondForeignScheduleFacade;

/**
 *
 * @author mora
 */
@Stateless
public class BondForeignScheduleBean implements BondForeignScheduleBeanLocal {

    @EJB
    FmsBondForeignScheduleFacade scheduleFacade;

    @Override
    public void Create(FmsBondForeignSchedule foreignSchedule) {
        scheduleFacade.create(foreignSchedule);
    }

    @Override
    public ArrayList<FmsBondForeignSchedule> searchStatus(FmsBondForeignSchedule repaymentSchedule) {
        return scheduleFacade.searchFmsStatus(repaymentSchedule); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FmsBondForeignSchedule getFmsBondForeignSchedule(FmsBondForeignSchedule schedule) {
        return scheduleFacade.getFmsBondForeignSchedule(schedule);
    }

    @Override
    public void Eidt(FmsBondForeignSchedule foreignSchedule) {
        scheduleFacade.edit(foreignSchedule);
    }

    @Override
    public FmsBondForeignSchedule getForeignSchedule(FmsBondForeignSchedule Fschedule) {
        return scheduleFacade.getFmsForeignSchedule(Fschedule);
    }

    @Override
    public ArrayList<FmsBondForeignSchedule> searchForingns(FmsBondForeignSchedule Fschedule) {
        return scheduleFacade.searchForingns(Fschedule);
    }

    @Override
    public ArrayList<FmsBondForeignSchedule> searchForingns_Bondpayed() {
        return scheduleFacade.searchForingns_Bondpayed();
    }

    @Override
    public FmsBondForeignSchedule getForeignSchedule_intslment(FmsBondForeignSchedule Fschedule) {
        return scheduleFacade.getFmsForeignSchedule_instlemet(Fschedule);
    }

    @Override
    public ArrayList<FmsBondForeignSchedule> searchForingns_notpayed(FmsBondForeignSchedule application) {
        return scheduleFacade.searchForingns_notpayed(application);
    }

    @Override
    public ArrayList<FmsBondForeignSchedule> searchStartEndDate(FmsBondForeignSchedule application) {
        return scheduleFacade.searchStartEndDate(application);
    }

    @Override
    public List<FmsBondForeignSchedule> searchBySerialNoAndDueDate(FmsBondForeignSchedule foreignSchedule) {
        return scheduleFacade.searchBySerialNoAndDueDate(foreignSchedule);
    }

    @Override
    public List<FmsBondForeignSchedule> getByStartAndEndDate(String fromStartDate, String toEndDate, FmsBondForeignSchedule foreignSchedule) {
        return scheduleFacade.getByStartAndEndDate(fromStartDate, toEndDate, foreignSchedule);
    }
}
