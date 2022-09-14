/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondRepaymentSchedule;
import et.gov.eep.fcms.mapper.Bond.FmsBondRepaymentScheduleFacade;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondRepaymentScheduleBean implements FmsBondRepaymentScheduleBeanLocal {

    @EJB
    FmsBondRepaymentScheduleFacade scheduleFacade;

    @Override
    public void Create(FmsBondRepaymentSchedule repaymentSchedule) {
        scheduleFacade.create(repaymentSchedule);
    }

    @Override
    public ArrayList<FmsBondRepaymentSchedule> searchStatus(FmsBondRepaymentSchedule repaymentSchedule) {
        return scheduleFacade.searchFmsStatus(repaymentSchedule); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FmsBondRepaymentSchedule getBondInfo(FmsBondRepaymentSchedule repaymentSchedule) {
        return scheduleFacade.fmsBondRepaymentScheduleinfo(repaymentSchedule);//To change body of generated methods, choose Tools | Templates.
    }
}
