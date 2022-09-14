/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondRepaymentSchedule;

/**
 *
 * @author mora
 */
@Local
public interface FmsBondRepaymentScheduleBeanLocal {

    public void Create(FmsBondRepaymentSchedule repaymentSchedule);

    public FmsBondRepaymentSchedule getBondInfo(FmsBondRepaymentSchedule repaymentSchedule);

    public ArrayList<FmsBondRepaymentSchedule> searchStatus(FmsBondRepaymentSchedule repaymentSchedule);

}
