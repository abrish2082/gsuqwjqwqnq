/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import javax.ejb.Stateless;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import et.gov.eep.hrms.mapper.payroll.HrPayrollBackPaymentGroupsFacade;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollBackGroup_logic implements HrPayrollBackGroup_logicLocal {

    @EJB
    HrPayrollBackPaymentGroupsFacade hrPayrollBackPaymentGroupsFacade;

    @Override
    public List<HrPayrollBackPaymentGroups> findAll() {

        return hrPayrollBackPaymentGroupsFacade.findAll();
    }

    @Override
    public void create(HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups) {
        hrPayrollBackPaymentGroupsFacade.create(hrPayrollBackPaymentGroups);
    }

    @Override
    public void edit(HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups) {
        hrPayrollBackPaymentGroupsFacade.edit(hrPayrollBackPaymentGroups);
    }

    @Override
    public HrPayrollBackPaymentGroups findById(HrPayrollBackPaymentGroups pg) {
        return hrPayrollBackPaymentGroupsFacade.findById(pg);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
