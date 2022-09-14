/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic.hrms;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.ifrs.entity.HrEmployeeSeverance;
import et.gov.eep.ifrs.mapper.HrEmployeeSeveranceFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class SeveranceLiability implements SeveranceLiabilityLocal {
    @EJB
    HrEmployeeSeveranceFacade HrEmployeeSeveranceFacade;

    @Override
    public List<HrEmployeeSeverance> findAll() {
        return HrEmployeeSeveranceFacade.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void save(HrEmployeeSeverance hrEmployeeSeverance) {
        HrEmployeeSeveranceFacade.create(hrEmployeeSeverance);
    }

    @Override
    public List<HrEmployeeSeverance> findBYBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        return HrEmployeeSeveranceFacade. findBYBudgetYear(fmsLuBudgetYear) ;
    }
}
