/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.committee;

import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.mapper.committee.HrCommitteeMembersFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrCommitteeMembersBea implements HrCommitteeMembersBeaLocal {

    @EJB
    HrCommitteeMembersFacade committeeMembersFacade;

    @Override
    public List<HrCommitteeMembers> getCommitteeMembers(HrCommittees hrCommittees) {
        return committeeMembersFacade.getCommitteeMembers(hrCommittees);
    }

    @Override
    public List<HrCommitteeMembers> findAll() {
        return committeeMembersFacade.findAll();
    }

    @Override
    public ArrayList<HrEmployees> searchEmp(String hrEmployee) {
        return committeeMembersFacade.searchEmp(hrEmployee);
    }

    @Override
    public HrEmployees getByFirstName(HrEmployees hrEmployees) {
        return committeeMembersFacade.getByFName(hrEmployees);
    }

    @Override
    public List<HrCommitteeMembers> getActiveCommitteeMembers() {
        return committeeMembersFacade.getActiveCommitteeMembers();
    }
}
