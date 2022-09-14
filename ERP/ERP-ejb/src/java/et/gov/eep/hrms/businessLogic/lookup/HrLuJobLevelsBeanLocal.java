/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrLuJobLevelsBeanLocal {

    /**
     *
     * @param hrLuJobLevels
     */
    void create(HrLuJobLevels hrLuJobLevels);

    /**
     *
     * @param hrLuJobLevels
     */
    void edit(HrLuJobLevels hrLuJobLevels);

    /**
     *
     * @param hrLuJobLevels
     */
    void remove(HrLuJobLevels hrLuJobLevels);

    /**
     *
     * @param id
     * @return
     */
    HrLuJobLevels find(Object id);

    /**
     *
     * @return
     */
    List<HrLuJobLevels> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrLuJobLevels> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    public HrLuJobLevels searchJobLevelById(int id);

    public HrLuJobLevels findLevel(HrLuJobLevels hrLuJobLevels);

}
