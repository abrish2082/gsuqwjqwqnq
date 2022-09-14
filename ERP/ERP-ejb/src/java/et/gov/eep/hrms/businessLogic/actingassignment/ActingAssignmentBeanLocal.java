/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.actingassignment;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface ActingAssignmentBeanLocal {

    public void save(HrActingAssignments hrActingAssignment);

    public void edit(HrActingAssignments hrActingAssignment);

    public ArrayList<HrJobTypes> SearchByJobTitel(HrJobTypes hrJobTypes);

    public HrJobTypes getByJobTitel(HrJobTypes hrJobTypes);

    public HrActingAssignments getActingAssignmentInfo(int actingAssignment);

    public List<String> searchByStatus(Integer acting);

    public List<String> searchAppReList(Integer actingAssignment);

    public HrActingAssignments getActingAssiInfo(int actingAssignment);

    public List<HrActingAssignments> findAllRequest();

    public List<HrActingAssignments> findByActingType(HrActingAssignments hrActingAssignments);

    public List<HrActingAssignments> findByRequestDate(HrActingAssignments hrActingAssignments);

    public List<HrActingAssignments> findByTwo(HrActingAssignments hrActingAssignments);

    public List<SelectItem> filterByStatus();

    public List<HrActingAssignments> loadActingRequests(int status);

    public HrActingAssignments loadActingRequestDetails(int request);

    public List<HrActingAssignments> loadActingList(Status status, int userId);

    public boolean isRequstExist(HrActingAssignments actingAssignment);

    public List<HrActingAssignments> loadActingRequestList(Status status, int userId);

}
