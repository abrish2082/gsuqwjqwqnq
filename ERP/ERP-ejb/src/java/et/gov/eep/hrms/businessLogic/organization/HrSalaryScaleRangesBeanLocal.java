/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrSalaryScaleRangesBeanLocal {

    /**
     *
     * @param hrSalaryScaleRanges
     */
    void create(HrSalaryScaleRanges hrSalaryScaleRanges);

    /**
     *
     * @param hrSalaryScaleRanges
     */
    void edit(HrSalaryScaleRanges hrSalaryScaleRanges);

    /**
     *
     * @param hrSalaryScaleRanges
     */
    void remove(HrSalaryScaleRanges hrSalaryScaleRanges);

    /**
     *
     * @param id
     * @return
     */
    HrSalaryScaleRanges find(Object id);

    /**
     *
     * @return
     */
    List<HrSalaryScaleRanges> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrSalaryScaleRanges> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    /**
     *
     * @param id
     * @return
     */
    HrSalaryScaleRanges findSalaryScaleRange(BigDecimal id);

    /**
     *
     * @param id
     * @return
     */
    List< HrSalaryScaleRanges> findAllSalaryScaleRanges(BigDecimal id);
    
    /**
     * munir
     * @return 
     */
    public List<HrSalaryScaleRanges> allJobGrades();

}
