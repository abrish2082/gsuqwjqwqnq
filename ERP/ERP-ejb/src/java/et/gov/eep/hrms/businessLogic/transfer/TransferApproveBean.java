/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.transfer;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.hrms.mapper.transfer.HrTransferRequestsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author munir
 */
@Stateless
public class TransferApproveBean implements TransferApproveBeanLocal {

    @EJB
    HrTransferRequestsFacade hrTransferRequestsFacade;
    @EJB
    HrEmployeesFacade hrEmployeesFacade;

    @Override
    public void edit(HrTransferRequests hrTransferRequests) {
        hrTransferRequestsFacade.edit(hrTransferRequests);
    }

    @Override
    public void editEmp(HrEmployees hrEmployees) {
        hrEmployeesFacade.edit(hrEmployees);
    }

    @Override
    public HrTransferRequests getTransferInfo(int hrTransferRequests) {
        return hrTransferRequestsFacade.getTransferInfo(hrTransferRequests);
    }

    @Override
    public List<String> searchAppReList(Integer hrTransferRequests) {
        return hrTransferRequestsFacade.searchAppReList(hrTransferRequests);
    }

    @Override
    public HrTransferRequests getSelectedRequest(int requestid) {
        return hrTransferRequestsFacade.getSelectedRequest(requestid);
    }

    @Override
    public List<HrTransferRequests> searchAllRequest() {
        return hrTransferRequestsFacade.findAll();
    }

    @Override
    public List<HrTransferRequests> findByAll(HrEmployees hrEmployees) {
        return hrTransferRequestsFacade.findByAll(hrEmployees);
    }

    @Override
    public List<HrTransferRequests> findByEmpId(HrEmployees hrEmployees) {
        return hrTransferRequestsFacade.findByEmpId(hrEmployees);
    }

    @Override
    public List<HrTransferRequests> findByEmpName(HrEmployees hrEmployees) {
        return hrTransferRequestsFacade.findByEmpName(hrEmployees);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Rejected List"));
        return selectItems;
    }

    @Override
    public List<HrTransferRequests> loadTransferList(int status) {
        return hrTransferRequestsFacade.loadTransferList(status);
    }

    @Override
    public int updateEmployeeDepartment(String transferId, String employeeId,
            int processType, String fromDeptId, String toDeptId,
            String oldDepName, String processDate, int status) {
        return hrTransferRequestsFacade.updateEmployeeDepartment(transferId,
                employeeId, processType, fromDeptId, toDeptId,
                oldDepName, processDate, status);
    }

    @Override
    public List<HrTransferRequests> findPreparedList() {
        return hrTransferRequestsFacade.findPreparedList();
    }

    @Override
    public List<HrTransferRequests> searchByStatus(int status) {
        return hrTransferRequestsFacade.searchByStatus(status);
    }

}
