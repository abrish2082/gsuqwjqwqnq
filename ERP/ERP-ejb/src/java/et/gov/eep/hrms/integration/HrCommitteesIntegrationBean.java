/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.mapper.committee.HrCommitteesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class HrCommitteesIntegrationBean implements HrCommitteesIntegrationBeanLocal {
@EJB
    HrCommitteesFacade committeesFacade;

    @Override
    public List<HrCommittees> findAll() {
       return committeesFacade.findAll();
    }

    @Override
    public HrCommittees getCommittee(HrCommittees hrCommittees) {
        return committeesFacade.getCommittee(hrCommittees);
    }
    
}
