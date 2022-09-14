/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.actingassignment;

import et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author munir
 */
@Local
public interface ActingAssignmentApproveBeanLocal {

    public void edit(HrActingAssignments hrActingAssignment);

    public int approveActingRequest(String empId, String jobCode, int requestId, String processDate, int status, int processType);

    public HrActingAssignments getActingAssiInfo(int actingAssignment);

    public List<HrActingAssignments> findAllRequest();

    public List<HrActingAssignments> findByActingType(HrActingAssignments hrActingAssignments);

    public List<HrActingAssignments> findByRequestDate(HrActingAssignments hrActingAssignments);

    public List<HrActingAssignments> findByTwo(HrActingAssignments hrActingAssignments);

    public List<SelectItem> filterByStatus();

    public List<HrActingAssignments> loadActingRequests(int status);

    public HrActingAssignments loadActingRequestDetails(int requestId);

    public List<String> searchAppReList(Integer actingAssignment);

    public List<HrActingAssignments> findActingList(int status);

    public List<HrActingAssignments> findActingPreparedList();

}
