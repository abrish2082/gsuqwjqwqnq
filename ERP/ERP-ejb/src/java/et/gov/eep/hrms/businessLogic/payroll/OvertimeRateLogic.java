/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.fcms.entity.FmsOtRate;
import et.gov.eep.hrms.mapper.payroll.OvertimeRate_Facade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author sura
 */
@Stateless
public class OvertimeRateLogic {

    @EJB
    private OvertimeRate_Facade overtimeRate_Facade;

    /**
     *
     * @param fmsOtRate
     */
    public void saveOrUpdate(FmsOtRate fmsOtRate) {
        overtimeRate_Facade.saveOrUpdate(fmsOtRate);
    }

    /**
     *
     * @return
     */
    public List<FmsOtRate> findAll() {
        return overtimeRate_Facade.findAll();
    }
}
