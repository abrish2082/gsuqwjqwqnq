/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrCandidateReferences;
import et.gov.eep.hrms.mapper.recruitment.HrCandidateReferencesFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrCandidateReferencesBean implements HrCandidateReferencesBeanLocal {
    
    @EJB
    HrCandidateReferencesFacade hrCandidateReferencesFacade;

   @Override
    public void save(HrCandidateReferences hrCandidateReferences){
        hrCandidateReferencesFacade.create(hrCandidateReferences);
    }
    @Override
    public void edit(HrCandidateReferences hrCandidateReferences){
        hrCandidateReferencesFacade.edit(hrCandidateReferences);
    }
}
