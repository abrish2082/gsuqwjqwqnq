/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrLuSalaryStepsBeanLocal {
    /**
     *
     * @param hrLuSalarySteps
     */
    void create(HrLuSalarySteps hrLuSalarySteps);

    /**
     *
     * @param hrLuSalarySteps
     */
    void edit(HrLuSalarySteps hrLuSalarySteps);

    /**
     *
     * @param hrLuSalarySteps
     */
    void remove(HrLuSalarySteps hrLuSalarySteps);

    /**
     *
     * @param id
     * @return
     */
    HrLuSalarySteps find(Object id);

    /**
     *
     * @return
     */
    List<HrLuSalarySteps> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrLuSalarySteps> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
}
