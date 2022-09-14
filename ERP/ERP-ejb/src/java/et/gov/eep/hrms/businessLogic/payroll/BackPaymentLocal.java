/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import et.gov.eep.hrms.mapper.payroll.HrPayrollBackPymntsEdsFacade;
import javax.ejb.EJB;

/**
 * 
 * @author user
 */
public class BackPaymentLocal implements BackPaymentLocalLocal {

    @EJB
    HrPayrollBackPymntsEdsFacade hrPayrollBackPymntsEdsFacade;

    /**
     *
     * @param hrPayrollBackPymntsEds
     */
    @Override
    public void create(HrPayrollBackPymntsEds hrPayrollBackPymntsEds) {
        hrPayrollBackPymntsEdsFacade.create(hrPayrollBackPymntsEds);

    }

}
