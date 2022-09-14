/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrSalaryScalesBeanLocal {
    
     /**
     *
     * @param hrSalaryScales
     */
    void create(HrSalaryScales hrSalaryScales);

    /**
     *
     * @param hrSalaryScales
     */
    void edit(HrSalaryScales hrSalaryScales);

    /**
     *
     * @param hrSalaryScales
     */
    void remove(HrSalaryScales hrSalaryScales);

    /**
     *
     * @param id
     * @return
     */
    HrSalaryScales find(Object id);

    /**
     *
     * @return
     */
    List<HrSalaryScales> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrSalaryScales> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    /**
     *
     * @param rangId
     * @param stepId
     * @return
     */
    public HrSalaryScales getSalary(BigDecimal rangId, BigDecimal stepId);

    /**
     *
     * @param hrSalaryScales
     * @return
     */
    public HrSalaryScales findBasicSalary(HrSalaryScales hrSalaryScales);

    /**
     *
     * @param hrSalaryScales
     * @return
     */
    public HrSalaryScales checkStepIdRep(HrSalaryScales hrSalaryScales);

  //  public HrSalaryScales searchSalary(String rangId, String stepId);
    
}
