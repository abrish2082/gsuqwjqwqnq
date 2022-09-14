/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrCandidateLanguages;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrCandidateLanguagesBeanLocal {

    public void save(HrCandidateLanguages hrCandidateLanguages);

    public void edit(HrCandidateLanguages hrCandidateLanguages);
    
}
