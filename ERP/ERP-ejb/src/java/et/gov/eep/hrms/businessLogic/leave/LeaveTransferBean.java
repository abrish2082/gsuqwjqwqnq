/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveTransfer;
import et.gov.eep.hrms.entity.leave.HrLeaveTransferDetail;
import et.gov.eep.hrms.mapper.leave.HrLeaveTransferFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author oneqaz
 */
@Stateless
public class LeaveTransferBean implements LeaveTransferBeanLocal {

    @EJB
    HrLeaveTransferFacade facadeLocal;

    @Override
    public void saveOrUpdate(HrLeaveTransfer leaveTransfer) {
        facadeLocal.saveOrUpdate(leaveTransfer);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Resubmit List"));
        return selectItems;
    }

    @Override
    public void edit(HrLeaveTransfer leaveTransfer) {
        facadeLocal.edit(leaveTransfer);
    }

    @Override
    public void remove(HrLeaveTransfer leaveTransfer) {
        facadeLocal.remove(leaveTransfer);
    }

    @Override
    public List<String> getApproveList() {
        return facadeLocal.getApproveList();
    }

    @Override
    public HrLeaveTransfer findById(int transferId) {
        return facadeLocal.findById(transferId);
    }

    @Override
    public List<HrLeaveTransfer> findByStatus(int transferId) {
        return facadeLocal.findByStatus(transferId);
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<HrLeaveTransferDetail> getByParentID(HrLeaveTransfer hlt) {
        return facadeLocal.getByParentID(hlt);
    }

    @Override
    public HrLeaveTransfer findByRequester(HrEmployees employee, int budgetyearID) {
        return facadeLocal.findByRequester(employee,budgetyearID);
    }

    @Override
    public List<HrLeaveTransfer> findRequestList() {
     return facadeLocal.findRequestList();
    }

}
