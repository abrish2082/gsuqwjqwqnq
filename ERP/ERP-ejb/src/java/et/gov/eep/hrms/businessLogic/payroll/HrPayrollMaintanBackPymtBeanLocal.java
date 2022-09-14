/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollMaintanBackPymt;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollMaintanBackPymtBeanLocal {
       /**
     *
     * @param hrPayrollMaintanBackPymt
     */
    void create(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt);

    /**
     *
     * @param hrPayrollMaintanBackPymt
     */
    void edit(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt);

    /**
     *
     * @param hrPayrollMaintanBackPymt
     */
    void remove(HrPayrollMaintanBackPymt hrPayrollMaintanBackPymt);

    /**
     *
     * @param id
     * @return
     */
    HrPayrollMaintanBackPymt find(Object id);

    /**
     *
     * @return
     */
    List<HrPayrollMaintanBackPymt> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrPayrollMaintanBackPymt> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
}
