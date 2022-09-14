/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrCandidateEmpHistories;
import et.gov.eep.hrms.mapper.recruitment.HrCandidateEmpHistoriesFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Stateless
public class HrCandidateEmpHistoriesBean implements HrCandidateEmpHistoriesBeanLocal {

    @EJB
    HrCandidateEmpHistoriesFacade hrCandidateEmpHistoriesFacade;
    @Inject
    HrCandidateEmpHistories hrCandidateEmpHistories;
    
    @Override
    public void save(HrCandidateEmpHistories hrCandidateEmpHistories) {
        hrCandidateEmpHistoriesFacade.create(hrCandidateEmpHistories);
    }

    @Override
    public void edit(HrCandidateEmpHistories hrCandidateEmpHistories) {
        hrCandidateEmpHistoriesFacade.edit(hrCandidateEmpHistories);
    }
}
