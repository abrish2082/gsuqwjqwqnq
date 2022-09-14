/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.hrms.entity.leave.HrLeaveSchedule;
import et.gov.eep.hrms.entity.leave.HrLeaveScheduleTransfer;
import et.gov.eep.hrms.mapper.leave.HrLeaveScheduleTransferFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLeaveScheduleTransferBean implements HrLeaveScheduleTransferBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method") 
    @EJB
    HrLeaveScheduleTransferFacade facadeLocal;

    @Override
    public void saveOrUpdate(HrLeaveScheduleTransfer hrLeaveBalance) {
        facadeLocal.saveOrUpdate(hrLeaveBalance);
    }

    @Override
    public void edit(HrLeaveScheduleTransfer hrLeaveBalance) {
        facadeLocal.edit(hrLeaveBalance);
    }

    @Override
    public void remove(HrLeaveScheduleTransfer hrLeaveBalance) {
        facadeLocal.remove(hrLeaveBalance);
    }

    @Override
    public HrLeaveScheduleTransfer find(Object id) {
        return facadeLocal.find(id);
    }

    @Override
    public List<HrLeaveScheduleTransfer> findAll() {
        return facadeLocal.findAll();
    }

    @Override
    public List<HrLeaveScheduleTransfer> findAllRequests() {
        return facadeLocal.loadScheduleTransfeRequests();
    }

    @Override
    public boolean isTransferRequestExist(HrLeaveSchedule hrleaveschdule) {
        return facadeLocal.isTransferRequestExist(hrleaveschdule);
    }

}
