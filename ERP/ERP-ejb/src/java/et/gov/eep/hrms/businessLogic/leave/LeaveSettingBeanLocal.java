/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;


import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author kibrom
 */
@Local
public interface LeaveSettingBeanLocal {

    public boolean create(HrLeaveSetting leaveSetting);

    public boolean edit(HrLeaveSetting leaveSetting);

    public HrLeaveSetting findLeaveTypeByCode(HrLeaveSetting leaveSetting);

    public ArrayList<HrLeaveSetting> searchLeaveTypeByCode(HrLeaveSetting leaveSetting);

    public HrLeaveSetting findLeaveSettingByLeaveType(HrLuLeaveTypes luLeaveTypes);

    public ArrayList<HrLeaveSetting> filterByGender(String gn);

    public HrLeaveSetting getByLeaveType(HrLuLeaveTypes hrLuLeaveTypes);

    public void saveOrUpdate(HrLeaveSetting hrLeaveSetting);
    

}
