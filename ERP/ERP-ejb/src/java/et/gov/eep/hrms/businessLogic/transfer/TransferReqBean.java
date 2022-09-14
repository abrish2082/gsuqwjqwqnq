/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.transfer;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuTransferTypes;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.hrms.mapper.transfer.HrTransferRequestsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class TransferReqBean implements TransferReqBeanLocal {

    @EJB
    HrTransferRequestsFacade hrTransferRequestsFacade;
    @EJB
    HrEmployeesFacade hrEmployeesFacade;

    @Override
    public void save(HrTransferRequests hrTransferRequests) {
        hrTransferRequestsFacade.create(hrTransferRequests);
    }

    @Override
    public void edit(HrTransferRequests hrTransferRequests) {
        hrTransferRequestsFacade.edit(hrTransferRequests);
    }

    @Override
    public HrDepartments getSelectDepartement(int departmentid) {
        return hrTransferRequestsFacade.getSelectDepartement(departmentid);
    }

    @Override
    public HrTransferRequests getTransferInfo(int hrTransferRequests) {
        return hrTransferRequestsFacade.getTransferInfo(hrTransferRequests);
    }

    @Override
    public List<String> searchByName(Integer hrTransferRequests) {
        return hrTransferRequestsFacade.searchByName(hrTransferRequests);
    }

    @Override
    public HrJobTypes findByName(int name) {
        return hrTransferRequestsFacade.findByName(name);
    }

    @Override
    public List<HrJobTypes> listOfJobType(int departmentId) {
        return hrTransferRequestsFacade.listOfJobType(departmentId);
    }

    @Override
    public List<String> searchAppReList(Integer hrTransferRequests) {
        return hrTransferRequestsFacade.searchAppReList(hrTransferRequests);
    }

    @Override
    public HrLuTransferTypes findTransfertype(HrLuTransferTypes hrLuTransferTypes) {
        return hrTransferRequestsFacade.findTransfertype(hrLuTransferTypes);
    }

    @Override
    public ArrayList<HrLuTransferTypes> findAllHrLuTransferTypes() {
        return hrTransferRequestsFacade.findAllHrLuTransferTypes();
    }

    @Override
    public List<HrTransferRequests> getTransferRequestList() {
        return hrTransferRequestsFacade.findAll();
    }

    @Override
    public ArrayList<HrTransferRequests> getRequestByStatus(HrTransferRequests hrTransferRequests) {
        return hrTransferRequestsFacade.findByStatus(hrTransferRequests);
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
    public List<HrTransferRequests> findByOne(HrEmployees hrEmployees) {
        return hrTransferRequestsFacade.findByOne(hrEmployees);
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
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Resubmit List"));
        return selectItems;
    }

    @Override
    public List<HrTransferRequests> loadTransferList(int status) {
        return hrTransferRequestsFacade.loadTransferList(status);
    }

    @Override
    public boolean isRequstExist(HrTransferRequests transferRequest) {
        return hrTransferRequestsFacade.isRequstExist(transferRequest);
    }

    @Override
    public boolean isApproved(HrTransferRequests transferRequest) {
        return hrTransferRequestsFacade.isApproved(transferRequest);
    }

    @Override
    public String findApproved(HrTransferRequests transferRequest) {
        return hrTransferRequestsFacade.findApproved(transferRequest);
    }

    @Override
    public List<HrTransferRequests> loadTransferReqList(Status status, int userId) {
        return hrTransferRequestsFacade.loadTransferReqList(status, userId);
    }

    @Override
    public List<HrTransferRequests> loadTransferList(Status status, int userId) {
        return hrTransferRequestsFacade.loadTransferList(status, userId);
    }

    @Override
    public List<HrTransferRequests> loadReqTranList(Status status, int userId) {
        return hrTransferRequestsFacade.loadReqTranList(status, userId);
    }

    @Override
    public List<HrTransferRequests> loadApproveTranList(Status status, int userId) {
        return hrTransferRequestsFacade.loadApproveTranList(status, userId);
    }

}
