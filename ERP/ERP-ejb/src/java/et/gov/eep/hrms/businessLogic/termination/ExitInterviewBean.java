/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrExitInterview;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.mapper.termination.HrExitInterviewFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ob
 */
@Stateless
public class ExitInterviewBean implements ExitInterviewBeanLocal {

    @EJB
    HrExitInterviewFacade hrExitInterviewFacade;

    @Override
    public void saveOrUpdate(HrExitInterview hrExitInterview) {
        hrExitInterviewFacade.saveOrUpdate(hrExitInterview);
    }

    @Override
    public void edit(HrExitInterview hrExitInterview) {
        hrExitInterviewFacade.edit(hrExitInterview);
    }

    @Override
    public List<HrExitInterview> findAll() {
        return hrExitInterviewFacade.findAll();
    }

    @Override
    public HrTerminationRequests findById(HrTerminationRequests hrExitInterview) {
        return hrExitInterviewFacade.findById(hrExitInterview);
    }

    @Override
    public HrClearance findByEmpId(String hrClearance) {
        return hrExitInterviewFacade.findByEmpId(hrClearance);
    }

    @Override
    public HrClearance findByEmpFirstName(String toString) {
        return hrExitInterviewFacade.findByEmpFirstName(toString);
    }

    @Override
    public ArrayList<HrExitInterview> findByEmpId(HrEmployees empId) {
        return hrExitInterviewFacade.findByEmpId(empId);
    }

    @Override
    public ArrayList<HrExitInterview> findByName(HrEmployees empName) {
        return hrExitInterviewFacade.findByName(empName);
    }

    @Override
    public ArrayList<HrExitInterview> findByEmpIdAndName(HrEmployees empId, HrEmployees empName) {
        return hrExitInterviewFacade.findByEmpIdAndName(empId, empName);
    }

    @Override
    public HrExitInterview getSelectedExitInterview(int exitInterview) {
        return hrExitInterviewFacade.getSelectedExitInterview(exitInterview);
    }

    @Override
    public boolean checkDuplicate(HrExitInterview hrExitInterview) {
        return hrExitInterviewFacade.checkDuplicate(hrExitInterview);
    }

    @Override
    public ArrayList<HrTerminationRequests> findApprovedTerminationByTerminationType() {
        return hrExitInterviewFacade.findApprovedTerminationByTerminationType();
    }

}
