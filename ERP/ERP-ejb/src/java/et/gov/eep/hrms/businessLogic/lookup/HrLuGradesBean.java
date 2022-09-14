/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.mapper.lookup.HrLuGradesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrLuGradesBean implements HrLuGradesBeanLocal {

    @EJB
    HrLuGradesFacade hrLuGradesFacade;

    /**
     *
     * @param hrLuGrades
     */
    @Override
    public void create(HrLuGrades hrLuGrades) {
        hrLuGradesFacade.create(hrLuGrades);
    }

    /**
     *
     * @param hrLuGrades
     */
    @Override
    public void edit(HrLuGrades hrLuGrades) {
        hrLuGradesFacade.edit(hrLuGrades);
    }

    /**
     *
     * @param hrLuGrades
     */
    @Override
    public void remove(HrLuGrades hrLuGrades) {
        hrLuGradesFacade.remove(hrLuGrades);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrLuGrades find(Object id) {
        return hrLuGradesFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrLuGrades> findAll() {
        return hrLuGradesFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrLuGrades> findRange(int[] range) {
        return hrLuGradesFacade.findRange(range);
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrLuGradesFacade.count();
    }
}
