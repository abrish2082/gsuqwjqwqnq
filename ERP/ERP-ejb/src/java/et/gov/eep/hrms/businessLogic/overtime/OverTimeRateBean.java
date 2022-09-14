/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.overtime;

import et.gov.eep.hrms.entity.overtime.HrOvertimeRates;
import et.gov.eep.hrms.mapper.overtime.HrOvertimeRatesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class OverTimeRateBean implements OverTimeRateBeanLocal {
    @EJB 
    HrOvertimeRatesFacade hrOvertimeRatesFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<HrOvertimeRates> findAll(HrOvertimeRates hrOvertimeRate) {
       return hrOvertimeRatesFacade.findAll();
    }

    @Override
    public List<HrOvertimeRates> findbyRateTypes(HrOvertimeRates hrOvertimeRate) {
        return hrOvertimeRatesFacade.findbyRateTypes(hrOvertimeRate);
    }

    @Override
    public void SaveOrUpdate(HrOvertimeRates hrOvertimeRate) {
       hrOvertimeRatesFacade.saveOrUpdate(hrOvertimeRate);
    }

    @Override
    public boolean searchdup(HrOvertimeRates hrOvertimeRate) {
        return hrOvertimeRatesFacade.searchdup(hrOvertimeRate);
    }

    @Override
    public HrOvertimeRates getSelectedRequest(int id) {
       return hrOvertimeRatesFacade.getSelectedRequest(id);
    }

    @Override
    public List<HrOvertimeRates> findAll() {
         return hrOvertimeRatesFacade.findAll();
    }

    @Override
    public ArrayList findByRateType(HrOvertimeRates hrOvertimeRate) {
        return hrOvertimeRatesFacade.findByRateType(hrOvertimeRate);
    }
}
