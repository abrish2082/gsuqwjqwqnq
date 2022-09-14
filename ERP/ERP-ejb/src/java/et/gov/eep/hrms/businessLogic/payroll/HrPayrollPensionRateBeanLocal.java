/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollPensionRate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollPensionRateBeanLocal {
     /**
     *
     * @param hrPayrollPensionRate
     */
    void create(HrPayrollPensionRate hrPayrollPensionRate);

    /**
     *
     * @param hrPayrollPensionRate
     */
    void edit(HrPayrollPensionRate hrPayrollPensionRate);

    /**
     *
     * @param hrPayrollPensionRate
     */
    void remove(HrPayrollPensionRate hrPayrollPensionRate);

    /**
     *
     * @param id
     * @return
     */
    HrPayrollPensionRate find(Object id);

    /**
     *
     * @return
     */
    List<HrPayrollPensionRate> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrPayrollPensionRate> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
}
