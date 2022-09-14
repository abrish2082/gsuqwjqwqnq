/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollSummery;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author memube
 */
@Local
public interface HrPayrollSummeryBeanLocal {

    public void edit(HrPayrollSummery hrPayrollSummery);

    public List<HrPayrollSummery> getPayrollMonthlyTrn();
}
