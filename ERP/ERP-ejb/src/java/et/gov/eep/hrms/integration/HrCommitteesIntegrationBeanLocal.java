/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.committee.HrCommittees;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface HrCommitteesIntegrationBeanLocal {
    public List<HrCommittees> findAll();
    public HrCommittees getCommittee(HrCommittees hrCommittees);
}
