/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollMaintanBackPymt;
import et.gov.eep.hrms.mapper.payroll.HrPayrollMaintanBackPymtFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */ 
@Stateless
public class BackpaymentMaintainPymt implements BackpaymentMaintainPymtLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    HrPayrollMaintanBackPymtFacade hrPayrollMaintanBackPymtFacade;

    /**
     *
     * @param hrPayrollMaintanBackPymt
     */
    @Override
    public void create(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt) {
        hrPayrollMaintanBackPymtFacade.create(hrPayrollMaintanBackPymt);
    }
}
