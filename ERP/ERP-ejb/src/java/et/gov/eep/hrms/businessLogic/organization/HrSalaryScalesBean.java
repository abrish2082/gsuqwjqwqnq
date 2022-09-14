/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.mapper.organization.HrSalaryScalesFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrSalaryScalesBean implements HrSalaryScalesBeanLocal {

    @EJB
    HrSalaryScalesFacade hrSalaryScalesFacade;

    /**
     *
     * @param hrSalaryScales
     */
    @Override
    public void create(HrSalaryScales hrSalaryScales) {
        hrSalaryScalesFacade.create(hrSalaryScales);
    }

    /**
     *
     * @param hrSalaryScales
     */
    @Override
    public void edit(HrSalaryScales hrSalaryScales) {
        hrSalaryScalesFacade.edit(hrSalaryScales);
    }

    /**
     *
     * @param hrSalaryScales
     */
    @Override
    public void remove(HrSalaryScales hrSalaryScales) {
        hrSalaryScalesFacade.remove(hrSalaryScales);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrSalaryScales find(Object id) {
        return hrSalaryScalesFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrSalaryScales> findAll() {
        return hrSalaryScalesFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrSalaryScales> findRange(int[] range) {
        return hrSalaryScalesFacade.findRange(range);
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrSalaryScalesFacade.count();
    }

    /**
     *
     * @param rangId
     * @param stepId
     * @return
     */
    @Override
    public HrSalaryScales getSalary(BigDecimal rangId, BigDecimal stepId) {
        return hrSalaryScalesFacade.getSalary(rangId, stepId);
    }

    /**
     *
     * @param hrSalaryScales
     * @return
     */
    @Override
    public HrSalaryScales findBasicSalary(HrSalaryScales hrSalaryScales) {
        return hrSalaryScalesFacade.findBasicSalary(hrSalaryScales);
    }

    /**
     *
     * @param hrSalaryScales
     * @return
     */
    @Override
    public HrSalaryScales checkStepIdRep(HrSalaryScales hrSalaryScales) {
        return hrSalaryScalesFacade.checkStepIdRep(hrSalaryScales);
    }
}
