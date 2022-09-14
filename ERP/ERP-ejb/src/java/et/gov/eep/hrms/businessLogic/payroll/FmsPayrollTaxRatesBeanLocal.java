/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.FmsPayrollTaxRates;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface FmsPayrollTaxRatesBeanLocal {
    
    /**
     *
     * @param fmsPayrollTaxRates
     */
    void create(FmsPayrollTaxRates fmsPayrollTaxRates);

    /**
     *
     * @param fmsPayrollTaxRates
     */
    void edit(FmsPayrollTaxRates fmsPayrollTaxRates);

    /**
     *
     * @param fmsPayrollTaxRates
     */
    void remove(FmsPayrollTaxRates fmsPayrollTaxRates);

    /**
     *
     * @param id
     * @return
     */
    FmsPayrollTaxRates find(Object id);

    /**
     *
     * @return
     */
    List<FmsPayrollTaxRates> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<FmsPayrollTaxRates> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
}
