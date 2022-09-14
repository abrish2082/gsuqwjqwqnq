/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.mapper.lookup.HrLuJobLevelsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class HrLuJobLevelsBean implements HrLuJobLevelsBeanLocal {

    @EJB
    HrLuJobLevelsFacade hrLuJobLevelsFacade;

    /**
     *
     * @param hrLuJobLevels
     */
    @Override
    public void create(HrLuJobLevels hrLuJobLevels) {
        hrLuJobLevelsFacade.create(hrLuJobLevels);
    }

    /**
     *
     * @param hrLuJobLevels
     */
    @Override
    public void edit(HrLuJobLevels hrLuJobLevels) {
        hrLuJobLevelsFacade.edit(hrLuJobLevels);
    }

    /**
     *
     * @param hrLuJobLevels
     */
    @Override
    public void remove(HrLuJobLevels hrLuJobLevels) {
        hrLuJobLevelsFacade.remove(hrLuJobLevels);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrLuJobLevels find(Object id) {
        return hrLuJobLevelsFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrLuJobLevels> findAll() {
        return hrLuJobLevelsFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrLuJobLevels> findRange(int[] range) {
        return hrLuJobLevelsFacade.findAll();
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrLuJobLevelsFacade.count();
    }
    
    @Override
    public HrLuJobLevels searchJobLevelById(int id) {
        return hrLuJobLevelsFacade.searchJobLevelById(id);
    }
    @Override
    public HrLuJobLevels findLevel(HrLuJobLevels hrLuJobLevels) {
        return hrLuJobLevelsFacade.findLevel(hrLuJobLevels);
    }
    
}
