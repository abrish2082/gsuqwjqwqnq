/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrPayrollBackGroup_logicLocal {

    public List<HrPayrollBackPaymentGroups> findAll();

    void create(HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups);

    void edit(HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups);

    HrPayrollBackPaymentGroups findById(HrPayrollBackPaymentGroups hrPayrollBackPaymentGroups);
}
