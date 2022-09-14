/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRateStatus;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollTaxRateStatusBeanLocal {

    void create(HrPayrollTaxRateStatus hrPayrollTaxRateStatus);

    void edit(HrPayrollTaxRateStatus hrPayrollTaxRateStatus);

    void remove(HrPayrollTaxRateStatus hrPayrollTaxRateStatus);

    HrPayrollTaxRateStatus find(Object id);

    List<HrPayrollTaxRateStatus> findAll();

    List<HrPayrollTaxRateStatus> findRange(int[] range);

    int count();
}
