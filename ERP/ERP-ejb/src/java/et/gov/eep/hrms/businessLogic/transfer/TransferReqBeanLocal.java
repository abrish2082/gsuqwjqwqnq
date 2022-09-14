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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface TransferReqBeanLocal {

    public void save(HrTransferRequests hrTransferRequests);

    public void edit(HrTransferRequests hrTransferRequests);

    public HrDepartments getSelectDepartement(int departmentid);

    public HrTransferRequests getTransferInfo(int hrTransferRequests);

    public List<String> searchByName(Integer hrTransferRequests);

    public HrJobTypes findByName(int name);

    public List<HrJobTypes> listOfJobType(int departmentId);

    public List<String> searchAppReList(Integer hrTransferRequests);

    public HrLuTransferTypes findTransfertype(HrLuTransferTypes hrLuTransferTypes);

    public ArrayList<HrLuTransferTypes> findAllHrLuTransferTypes();

    public List<HrTransferRequests> getTransferRequestList();

    public HrTransferRequests getSelectedRequest(int requestid);

    public ArrayList<HrTransferRequests> getRequestByStatus(HrTransferRequests hrTransferRequests);

    public List<HrTransferRequests> searchAllRequest();

    public List<HrTransferRequests> findByAll(HrEmployees hrEmployees);

    public List<HrTransferRequests> findByOne(HrEmployees hrEmployees);

    public List<HrTransferRequests> findByEmpName(HrEmployees hrEmployees);

    public List<HrTransferRequests> findByEmpId(HrEmployees hrEmployees);

    public List<SelectItem> filterByStatus();

    public List<HrTransferRequests> loadTransferList(int status);

    public boolean isRequstExist(HrTransferRequests transferRequest);

    public String findApproved(HrTransferRequests transferRequest);

    public boolean isApproved(HrTransferRequests transferRequest);

    public List<HrTransferRequests> loadTransferReqList(Status status, int userId);

    public List<HrTransferRequests> loadTransferList(Status status, int userId);

    public List<HrTransferRequests> loadReqTranList(Status status, int userId);

    public List<HrTransferRequests> loadApproveTranList(Status status, int userId);

}
