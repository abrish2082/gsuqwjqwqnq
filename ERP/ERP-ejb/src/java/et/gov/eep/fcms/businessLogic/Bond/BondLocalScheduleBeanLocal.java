/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;

/**
 *
 * @author mz Gm
 */
@Local
public interface BondLocalScheduleBeanLocal {

    public void Create(FmsBondLocalSchedule localSchedule);

    public void Eidt(FmsBondLocalSchedule localSchedule);

    public FmsBondLocalSchedule getBondInfo(FmsBondLocalSchedule repaymentSchedule);

    public FmsBondLocalSchedule getSchedule(FmsBondLocalSchedule repaymentSchedule);

    public FmsBondLocalSchedule getSchedule_instlment(FmsBondLocalSchedule repaymentSchedule);

    public ArrayList<FmsBondLocalSchedule> searchStatus(FmsBondLocalSchedule repaymentSchedule);

    public ArrayList<FmsBondLocalSchedule> searchStartEndDate(FmsBondLocalSchedule repaymentSchedule);

    public ArrayList<FmsBondLocalSchedule> searchSchedule(FmsBondLocalSchedule repaymentSchedule);

    public ArrayList<FmsBondLocalSchedule> searchLocalStatusNotPayed(FmsBondLocalSchedule repaymentSchedule);

    public List<FmsBondLocalSchedule> searchpayedSchedule();

    public List<FmsBondLocalSchedule> searchall();

    public List<FmsBondLocalSchedule> findBySerialNo(FmsBondLocalSchedule repaymentSchedule);

    public List<FmsBondLocalSchedule> getByStartAndEndDate(String fromStartDate, String toEndDate, FmsBondLocalSchedule repaymentSchedule);

    public List<FmsBondLocalSchedule> getNumberOfNotPaidStatus(String SerialNo, int NOT_PAID);

}
