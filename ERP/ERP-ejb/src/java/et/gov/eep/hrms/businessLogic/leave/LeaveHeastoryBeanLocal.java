/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;
import et.gov.eep.hrms.entity.leave.HrLeaveHistory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author oneqaz
 */
@Local
public interface LeaveHeastoryBeanLocal {

    public void create(HrLeaveHistory hrLeaveHistory);

    public void edit(HrLeaveHistory hrLeaveHistory);

    public void remove(HrLeaveHistory hrLeaveHistory);

    public HrLeaveHistory find(Object id);

    public List<HrLeaveHistory> findAll();

    public List<HrLeaveHistory> findRange(int[] range);

    public int count();

    
    
}
