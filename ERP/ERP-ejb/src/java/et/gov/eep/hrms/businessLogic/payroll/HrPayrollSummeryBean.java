/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollSummery;
import et.gov.eep.hrms.mapper.payroll.HrPayrollSummeryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author memube
 */
@Stateless
public class HrPayrollSummeryBean implements HrPayrollSummeryBeanLocal {

    @EJB
    HrPayrollSummeryFacade hrPayrollSummeryFacade;

    @Override
    public List<HrPayrollSummery> getPayrollMonthlyTrn() {
        return hrPayrollSummeryFacade.getPayrollMonthlyTrn(); 
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void edit(HrPayrollSummery hrPayrollSummery) {
        hrPayrollSummeryFacade.edit(hrPayrollSummery);
    }

}
