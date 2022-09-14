/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrLuEducTypesBeanLocal {

    /**
     *
     * @param hrLuEducTypes
     */
    void create(HrLuEducTypes hrLuEducTypes);

    /**
     *
     * @param hrLuEducTypes
     */
    void edit(HrLuEducTypes hrLuEducTypes);

    /**
     *
     * @param hrLuEducTypes
     */
    void remove(HrLuEducTypes hrLuEducTypes);

    /**
     *
     * @param id
     * @return
     */
    HrLuEducTypes find(Object id);

    /**
     *
     * @return
     */
    List<HrLuEducTypes> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrLuEducTypes> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
     public HrLuEducTypes searchEducTypeById(int id);

}
