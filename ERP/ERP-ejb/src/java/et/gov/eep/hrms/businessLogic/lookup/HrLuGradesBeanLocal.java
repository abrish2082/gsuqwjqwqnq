/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrLuGradesBeanLocal {

    /**
     *
     * @param hrLuGrades
     */
    void create(HrLuGrades hrLuGrades);

    /**
     *
     * @param hrLuGrades
     */
    void edit(HrLuGrades hrLuGrades);

    /**
     *
     * @param hrLuGrades
     */
    void remove(HrLuGrades hrLuGrades);

    /**
     *
     * @param id
     * @return
     */
    HrLuGrades find(Object id);

    /**
     *
     * @return
     */
    List<HrLuGrades> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrLuGrades> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
}
