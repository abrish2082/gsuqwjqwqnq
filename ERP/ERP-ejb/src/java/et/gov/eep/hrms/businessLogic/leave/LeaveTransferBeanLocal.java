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
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author oneqaz
 */
@Local
public interface LeaveTransferBeanLocal {

    public void saveOrUpdate(HrLeaveTransfer leaveTransfer);

    public void edit(HrLeaveTransfer leaveTransfer);

    public void remove(HrLeaveTransfer leaveTransfer);

    public int count();

    public HrLeaveTransfer findById(int transferId);

    public List<HrLeaveTransfer> findByStatus(int transferId);

    public List<String> getApproveList();

    public List<SelectItem> filterByStatus();

    public List<HrLeaveTransferDetail> getByParentID(HrLeaveTransfer hlt);

    public HrLeaveTransfer findByRequester(HrEmployees employee, int budgetyearID);

    public List<HrLeaveTransfer> findRequestList();

}
