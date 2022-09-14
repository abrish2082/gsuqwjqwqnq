/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.mapper.organization.HrSalaryScaleRangesFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrSalaryScaleRangesBean implements HrSalaryScaleRangesBeanLocal {

    @EJB
    HrSalaryScaleRangesFacade hrSalaryScaleRangesFacade;

    /**
     *
     * @param hrSalaryScaleRanges
     */
    @Override
    public void create(HrSalaryScaleRanges hrSalaryScaleRanges) {
        hrSalaryScaleRangesFacade.create(hrSalaryScaleRanges);
    }

    /**
     *
     * @param hrSalaryScaleRanges
     */
    @Override
    public void edit(HrSalaryScaleRanges hrSalaryScaleRanges) {
        hrSalaryScaleRangesFacade.edit(hrSalaryScaleRanges);
    }

    /**
     *
     * @param hrSalaryScaleRanges
     */
    @Override
    public void remove(HrSalaryScaleRanges hrSalaryScaleRanges) {
        hrSalaryScaleRangesFacade.remove(hrSalaryScaleRanges);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrSalaryScaleRanges find(Object id) {
        return hrSalaryScaleRangesFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrSalaryScaleRanges> findAll() {
        return hrSalaryScaleRangesFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrSalaryScaleRanges> findRange(int[] range) {
        return hrSalaryScaleRangesFacade.findRange(range);
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrSalaryScaleRangesFacade.count();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrSalaryScaleRanges findSalaryScaleRange(BigDecimal id) {
        return hrSalaryScaleRangesFacade.findSalaryScaleRange(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public List<HrSalaryScaleRanges> findAllSalaryScaleRanges(BigDecimal id) {
        return hrSalaryScaleRangesFacade.findAllSalaryScaleRanges(id);
    }
    
    /**
     * munir
     * @return 
     */
    @Override
    public List<HrSalaryScaleRanges> allJobGrades(){
        return hrSalaryScaleRangesFacade.allJobGrades();
    }
}
