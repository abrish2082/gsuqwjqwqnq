/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrLuEducLevelsBeanLocal {

    /**
     *
     * @param hrLuEducLevels
     */
    void create(HrLuEducLevels hrLuEducLevels);

    /**
     *
     * @param hrLuEducLevels
     */
    void edit(HrLuEducLevels hrLuEducLevels);

    /**
     *
     * @param hrLuEducLevels
     */
    void remove(HrLuEducLevels hrLuEducLevels);

    /**
     *
     * @param id
     * @return
     */
    HrLuEducLevels find(Object id);

    /**
     *
     * @return
     */
    List<HrLuEducLevels> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrLuEducLevels> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
     public HrLuEducLevels searchEduclevelById(int id);
}
