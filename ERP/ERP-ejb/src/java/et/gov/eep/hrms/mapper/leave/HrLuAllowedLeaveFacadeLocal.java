/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.hrms.entity.leave.HrLuAllowedLeave;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author User
 */
@Local
public interface HrLuAllowedLeaveFacadeLocal {

    void create(HrLuAllowedLeave hrLuAllowedLeave);

    void edit(HrLuAllowedLeave hrLuAllowedLeave);

    void remove(HrLuAllowedLeave hrLuAllowedLeave);

    HrLuAllowedLeave find(Object id);

    List<HrLuAllowedLeave> findAll();

    List<HrLuAllowedLeave> findRange(int[] range);

    int count();
    
     public HrLuAllowedLeave findAlloedLeave(Integer hrLuAllowedLeave) ;
    
}
