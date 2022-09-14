/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollMaintanBackPymt;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BackpaymentMaintainPymtLocal {

    /**
     *
     * @param hrPayrollMaintanBackPymt
     */
    void create(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt);

}
