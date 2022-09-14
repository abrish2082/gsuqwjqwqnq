/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.mapper.committee.HrCommitteeMembersFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class HrCommitteeMembersIntegrationBean implements HrCommitteeMembersIntegrationBeanLocal {

  @EJB
    HrCommitteeMembersFacade committeeMembersFacade;

    @Override
    public List<HrCommitteeMembers> getCommitteeMembers(HrCommittees hrCommittees) {
         return committeeMembersFacade.getCommitteeMembers(hrCommittees);
    }
   
}
