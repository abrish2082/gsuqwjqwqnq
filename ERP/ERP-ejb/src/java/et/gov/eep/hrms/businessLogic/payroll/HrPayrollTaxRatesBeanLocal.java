/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollTaxRatesBeanLocal {

    /**
     *
     * @param hrPayrollTaxRates
     */
    void create(HrPayrollTaxRates hrPayrollTaxRates);

    /**
     *
     * @param hrPayrollTaxRates
     */
    void edit(HrPayrollTaxRates hrPayrollTaxRates);

    /**
     *
     * @param hrPayrollTaxRates
     */
    void remove(HrPayrollTaxRates hrPayrollTaxRates);

    /**
     *
     * @param id
     * @return
     */
    HrPayrollTaxRates find(Object id);

    /**
     *
     * @return
     */
    List<HrPayrollTaxRates> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrPayrollTaxRates> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    public boolean checkOverlap(HrPayrollTaxRates tr);

    public boolean checkOverlapUpdate(HrPayrollTaxRates tr);

    public boolean checkUnlimited();

    public boolean checkUnlimitedUpdate(HrPayrollTaxRates pr);

    public HrPayrollTaxRates findBySalaryRange(Double AccruedLeaveAmount);
}
