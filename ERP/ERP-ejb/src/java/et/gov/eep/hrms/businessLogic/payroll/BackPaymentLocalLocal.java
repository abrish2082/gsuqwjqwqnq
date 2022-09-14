/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BackPaymentLocalLocal {

    /**
     *
     * @param hrPayrollBackPymntsEds
     */
    void create(HrPayrollBackPymntsEds hrPayrollBackPymntsEds);
 
}
