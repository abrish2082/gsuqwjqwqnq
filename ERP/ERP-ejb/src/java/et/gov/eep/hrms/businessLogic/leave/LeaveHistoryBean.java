/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.hrms.entity.leave.HrLeaveHistory;
import et.gov.eep.hrms.mapper.leave.HrLeaveHistoryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author oneqaz
 */@Stateless
public class LeaveHistoryBean implements LeaveHeastoryBeanLocal {
    @EJB
    HrLeaveHistoryFacade facadeLocal;

    
    @Override
    public void create(HrLeaveHistory hrLeaveHistory) {
        facadeLocal.create(hrLeaveHistory);
    }

    @Override
    public void edit(HrLeaveHistory hrLeaveHistory) {
        facadeLocal.edit(hrLeaveHistory);
    }

    @Override
    public void remove(HrLeaveHistory hrLeaveHistory) {
        facadeLocal.remove(hrLeaveHistory);
    }

    @Override
    public HrLeaveHistory find(Object id) {
        return facadeLocal.find(id);
    }

    @Override
    public List<HrLeaveHistory> findAll() {
        return facadeLocal.findAll();
    }

    @Override
    public List<HrLeaveHistory> findRange(int[] range) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
    

    
    

}
