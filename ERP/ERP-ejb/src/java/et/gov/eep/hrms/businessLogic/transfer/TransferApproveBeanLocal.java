/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.transfer;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author munir
 */
@Local
public interface TransferApproveBeanLocal {

    public void edit(HrTransferRequests hrTransferRequests);

    public void editEmp(HrEmployees hrEmployees);

    public HrTransferRequests getTransferInfo(int hrTransferRequests);

    public List<String> searchAppReList(Integer hrTransferRequests);

    public List<HrTransferRequests> findByEmpName(HrEmployees hrEmployees);

    public List<HrTransferRequests> findByEmpId(HrEmployees hrEmployees);

    public List<HrTransferRequests> findByAll(HrEmployees hrEmployees);

    public List<HrTransferRequests> searchAllRequest();

    public HrTransferRequests getSelectedRequest(int requestid);

    public List<SelectItem> filterByStatus();

    public List<HrTransferRequests> loadTransferList(int status);

    public int updateEmployeeDepartment(String transferId, String employeeId,
            int processType, String fromDeptId, String toDeptId,
            String oldDepName, String processDate, int status);

    public List<HrTransferRequests> findPreparedList();

    public List<HrTransferRequests> searchByStatus(int status);

}
