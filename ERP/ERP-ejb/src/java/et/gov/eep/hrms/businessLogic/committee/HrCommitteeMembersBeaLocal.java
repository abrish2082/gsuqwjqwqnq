/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.committee;

import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrCommitteeMembersBeaLocal {

    public List<HrCommitteeMembers> getCommitteeMembers(HrCommittees hrCommittees);

    public List<HrCommitteeMembers> findAll();
    public ArrayList<HrEmployees> searchEmp(String hrEmployee);

    public HrEmployees getByFirstName(HrEmployees hrEmployees);

    public List<HrCommitteeMembers> getActiveCommitteeMembers();

   

   
}
