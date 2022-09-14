package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;

/**
 *
 * @author merhawi
 */
@Local
public interface BondForeignScheduleBeanLocal {

    public void Create(FmsBondForeignSchedule localSchedule);

    public void Eidt(FmsBondForeignSchedule foreignSchedule);

    public FmsBondForeignSchedule getFmsBondForeignSchedule(FmsBondForeignSchedule schedule);

    public FmsBondForeignSchedule getForeignSchedule_intslment(FmsBondForeignSchedule Fschedule);

    public FmsBondForeignSchedule getForeignSchedule(FmsBondForeignSchedule Fschedule);

    public ArrayList<FmsBondForeignSchedule> searchStatus(FmsBondForeignSchedule repaymentSchedule);

    public ArrayList<FmsBondForeignSchedule> searchForingns(FmsBondForeignSchedule Fschedule);

    public ArrayList<FmsBondForeignSchedule> searchForingns_Bondpayed();

    public ArrayList<FmsBondForeignSchedule> searchForingns_notpayed(FmsBondForeignSchedule application);

    public ArrayList<FmsBondForeignSchedule> searchStartEndDate(FmsBondForeignSchedule application);

    public List<FmsBondForeignSchedule> searchBySerialNoAndDueDate(FmsBondForeignSchedule foreignSchedule);

    public List<FmsBondForeignSchedule> getByStartAndEndDate(String fromStartDate, String toEndDate, FmsBondForeignSchedule foreignSchedule);

}
