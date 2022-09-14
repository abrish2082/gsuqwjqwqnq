/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.mapper.lookup.HrLuSalaryStepsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrLuSalaryStepsBean implements HrLuSalaryStepsBeanLocal {

     @EJB
    HrLuSalaryStepsFacade hrLuSalaryStepsFacade;

    /**
     *
     * @param hrLuSalarySteps
     */
    @Override
    public void create(HrLuSalarySteps hrLuSalarySteps) {
        hrLuSalaryStepsFacade.create(hrLuSalarySteps);
    }

    /**
     *
     * @param hrLuSalarySteps
     */
    @Override
    public void edit(HrLuSalarySteps hrLuSalarySteps) {
        hrLuSalaryStepsFacade.edit(hrLuSalarySteps);
    }

    /**
     *
     * @param hrLuSalarySteps
     */
    @Override
    public void remove(HrLuSalarySteps hrLuSalarySteps) {
        hrLuSalaryStepsFacade.remove(hrLuSalarySteps);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrLuSalarySteps find(Object id) {
        return hrLuSalaryStepsFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrLuSalarySteps> findAll() {
        return hrLuSalaryStepsFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrLuSalarySteps> findRange(int[] range) {
        return hrLuSalaryStepsFacade.findRange(range);
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
       return hrLuSalaryStepsFacade.count();
    }
}
