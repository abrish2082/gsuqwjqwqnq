/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.actingassignment;

import et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments;
import et.gov.eep.hrms.mapper.actingAssignment.HrActingAssignmentFacade;
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
public class ActingAssignmentApproveBean implements ActingAssignmentApproveBeanLocal {

    @EJB
    HrActingAssignmentFacade hrActingAssignmentFacade;

    @Override
    public void edit(HrActingAssignments hrActingAssignment) {
        hrActingAssignmentFacade.edit(hrActingAssignment);
    }

    @Override
    public int approveActingRequest(String empId, String jobCode, int requestId, String processDate, int status, int processType) {
        return hrActingAssignmentFacade.approveActingRequest(empId, jobCode, requestId, processDate, status, processType);
    }

    @Override
    public HrActingAssignments getActingAssiInfo(int actingAssignment) {
        return hrActingAssignmentFacade.getActingAssignmentInfo(actingAssignment);
    }

    @Override
    public List<HrActingAssignments> findAllRequest() {
        return hrActingAssignmentFacade.findAllRequest();
    }

    @Override
    public List<String> searchAppReList(Integer actingAssignment) {
        return hrActingAssignmentFacade.searchAppReList(actingAssignment);
    }

    @Override
    public List<HrActingAssignments> findByActingType(HrActingAssignments hrActingAssignments) {
        return hrActingAssignmentFacade.findByActingType(hrActingAssignments);
    }

    @Override
    public List<HrActingAssignments> findByRequestDate(HrActingAssignments hrActingAssignments) {
        return hrActingAssignmentFacade.findByRequestDate(hrActingAssignments);
    }

    @Override
    public List<HrActingAssignments> findByTwo(HrActingAssignments hrActingAssignments) {
        return hrActingAssignmentFacade.findByTwo(hrActingAssignments);
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
    public List<HrActingAssignments> loadActingRequests(int status) {
        return hrActingAssignmentFacade.loadActingRequests(status);
    }

    @Override
    public List<HrActingAssignments> findActingList(int status) {
        return hrActingAssignmentFacade.findActingList(status);
    }

    @Override
    public HrActingAssignments loadActingRequestDetails(int requestId) {
        return hrActingAssignmentFacade.loadActingRequestDetails(requestId);
    }

    @Override
    public List<HrActingAssignments> findActingPreparedList() {
        return hrActingAssignmentFacade.findActingPreparedList();

    }

}
