/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollPensionRates;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollPensionRatesBeanLocal {

    /**
     *
     * @param hrPayrollPensionRates
     */
    void create(HrPayrollPensionRates hrPayrollPensionRates);

    /**
     *
     * @param hrPayrollPensionRates
     */
    void edit(HrPayrollPensionRates hrPayrollPensionRates);

    /**
     *
     * @param hrPayrollPensionRates
     */
    void remove(HrPayrollPensionRates hrPayrollPensionRates);

    /**
     *
     * @param id
     * @return
     */
    HrPayrollPensionRates find(Object id);

    /**
     *
     * @return
     */
    List<HrPayrollPensionRates> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrPayrollPensionRates> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    /**
     *
     * @return
     */
    public int updatePensionRates();

}
