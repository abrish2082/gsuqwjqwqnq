/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;
import et.gov.eep.fcms.mapper.Bond.FmsBondLocalScheduleFacade;

/**
 *
 * @author mz
 */
@Stateless
public class BondLocalScheduleBean implements BondLocalScheduleBeanLocal {

    @EJB
    FmsBondLocalScheduleFacade localScheduleFacade;

    @Override
    public void Create(FmsBondLocalSchedule localSchedule) {
        localScheduleFacade.create(localSchedule);
    }

    @Override
    public ArrayList<FmsBondLocalSchedule> searchStatus(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.searchFmsStatus(repaymentSchedule); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FmsBondLocalSchedule getBondInfo(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.fmsBondRepaymentScheduleinfo(repaymentSchedule);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Eidt(FmsBondLocalSchedule localSchedule) {
        localScheduleFacade.edit(localSchedule); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<FmsBondLocalSchedule> searchSchedule(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.searchLocals(repaymentSchedule);
    }

    @Override
    public FmsBondLocalSchedule getSchedule(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.getFmsLocalSchedule(repaymentSchedule);
    }

    @Override
    public List<FmsBondLocalSchedule> searchpayedSchedule() {
        return localScheduleFacade.searchpaydLocalsBond();

    }

    @Override
    public FmsBondLocalSchedule getSchedule_instlment(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.getFmsLocalSchedule_instlment(repaymentSchedule);
    }

    @Override
    public ArrayList<FmsBondLocalSchedule> searchStartEndDate(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.searchStartEndDate(repaymentSchedule);
    }

    @Override
    public List<FmsBondLocalSchedule> searchall() {
        return localScheduleFacade.findAll();
    }

    @Override
    public List<FmsBondLocalSchedule> findBySerialNo(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.searchLocals(repaymentSchedule);
    }

    @Override
    public ArrayList<FmsBondLocalSchedule> searchLocalStatusNotPayed(FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.searchLocalStatusNotPayed(repaymentSchedule);
    }

    @Override
    public List<FmsBondLocalSchedule> getByStartAndEndDate(String fromStartDate, String toEndDate, FmsBondLocalSchedule repaymentSchedule) {
        return localScheduleFacade.getByStartAndEndDate(fromStartDate, toEndDate, repaymentSchedule);
    }

    @Override
    public List<FmsBondLocalSchedule> getNumberOfNotPaidStatus(String SerialNo, int NOT_PAID) {
        return localScheduleFacade.getNumberOfNotPaidStatus(SerialNo, NOT_PAID);
    }
}
