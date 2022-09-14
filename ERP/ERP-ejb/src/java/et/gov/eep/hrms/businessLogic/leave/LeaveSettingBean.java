/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import et.gov.eep.hrms.mapper.leave.HrLeaveSettingFacade;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kibrom
 */
@Stateless
public class LeaveSettingBean implements LeaveSettingBeanLocal {

    @EJB
    HrLeaveSettingFacade leaveSetingFacadeLocal;

    @Override
    public boolean create(HrLeaveSetting leaveType) {
        try {
            leaveSetingFacadeLocal.create(leaveType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean edit(HrLeaveSetting leaveType) {
        try {
            leaveSetingFacadeLocal.edit(leaveType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public HrLeaveSetting findLeaveTypeByCode(HrLeaveSetting leaveSetting) {
        return leaveSetingFacadeLocal.findLeaveTypeByCode(leaveSetting);
    }

    @Override
    public ArrayList<HrLeaveSetting> searchLeaveTypeByCode(HrLeaveSetting leaveSetting) {
        return leaveSetingFacadeLocal.searchLeaveTypeByCode(leaveSetting);
    }

    @Override
    public HrLeaveSetting findLeaveSettingByLeaveType(HrLuLeaveTypes luLeaveTypes) {
        return leaveSetingFacadeLocal.findLeaveSettingByLeaveType(luLeaveTypes);
    }

    @Override
    public ArrayList<HrLeaveSetting> filterByGender(String gn) {
        return leaveSetingFacadeLocal.filterByGender(gn);
    }

    @Override
    public HrLeaveSetting getByLeaveType(HrLuLeaveTypes hrLuLeaveTypes) {
        return leaveSetingFacadeLocal.getByLeaveType(hrLuLeaveTypes);
    }

    @Override
    public void saveOrUpdate(HrLeaveSetting hrLeaveSetting) {
   leaveSetingFacadeLocal.saveOrUpdate(hrLeaveSetting); }

}
