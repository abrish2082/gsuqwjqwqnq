/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrExitInterview;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface ExitInterviewBeanLocal {

    public void saveOrUpdate(HrExitInterview hrExitInterview);

    public void edit(HrExitInterview hrExitInterview);

    public List<HrExitInterview> findAll();

    public HrTerminationRequests findById(HrTerminationRequests hrExitInterview);

    public HrClearance findByEmpId(String hrClearance);

    public HrClearance findByEmpFirstName(String toString);

    public ArrayList<HrExitInterview> findByEmpId(HrEmployees empId);

    public ArrayList<HrExitInterview> findByName(HrEmployees empName);

    public ArrayList<HrExitInterview> findByEmpIdAndName(HrEmployees empId, HrEmployees empName);

    public HrExitInterview getSelectedExitInterview(int exitInterview);

    public boolean checkDuplicate(HrExitInterview hrExitInterview);

    public ArrayList<HrTerminationRequests> findApprovedTerminationByTerminationType();


}
