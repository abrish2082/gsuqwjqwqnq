/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrCandidateLanguages;
import et.gov.eep.hrms.mapper.recruitment.HrCandidateLanguagesFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrCandidateLanguagesBean implements HrCandidateLanguagesBeanLocal {
    
    @EJB
    HrCandidateLanguagesFacade hrCandidateLanguagesFacade;

    @Override
    public void save(HrCandidateLanguages hrCandidateLanguages){
        hrCandidateLanguagesFacade.create(hrCandidateLanguages);
    }
    @Override
    public void edit(HrCandidateLanguages hrCandidateLanguages){
        hrCandidateLanguagesFacade.edit(hrCandidateLanguages);
    }
}
