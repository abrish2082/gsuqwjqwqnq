/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.mapper.lookup.HrLuEducLevelsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrLuEducLevelsBean implements HrLuEducLevelsBeanLocal {

    @EJB
    HrLuEducLevelsFacade hrLuEducLevelsFacade;

    /**
     *
     * @param hrLuEducLevels
     */
    @Override
    public void create(HrLuEducLevels hrLuEducLevels) {
        hrLuEducLevelsFacade.create(hrLuEducLevels);
    }

    /**
     *
     * @param hrLuEducLevels
     */
    @Override
    public void edit(HrLuEducLevels hrLuEducLevels) {
        hrLuEducLevelsFacade.edit(hrLuEducLevels);
    }

    /**
     *
     * @param hrLuEducLevels
     */
    @Override
    public void remove(HrLuEducLevels hrLuEducLevels) {
        hrLuEducLevelsFacade.remove(hrLuEducLevels);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrLuEducLevels find(Object id) {
        return hrLuEducLevelsFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrLuEducLevels> findAll() {
        return hrLuEducLevelsFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrLuEducLevels> findRange(int[] range) {
        return hrLuEducLevelsFacade.findRange(range);
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrLuEducLevelsFacade.count();
    }

    @Override
    public HrLuEducLevels searchEduclevelById(int id){
        return hrLuEducLevelsFacade.searchEduclevelById(id);
    }

}
