/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrCandidateSelected;
import et.gov.eep.hrms.entity.recruitment.HrExamPercentages;
import et.gov.eep.hrms.mapper.recruitment.HrCandidateSelectedFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author munir
 */
@Stateless
public class HrCandidateSelectedBean implements HrCandidateSelectedBeanLocal {

    @Inject
    HrCandidateSelected hrCandidateSelected;

    @EJB
    HrCandidateSelectedFacade hrCandidateSelectedFacade;

    @Override
    public List<HrAdvertisements> bachCodes(String type) {
        return hrCandidateSelectedFacade.bachCodes(type);
    }

    @Override
    public List<HrAdvertisedJobs> advertizedJobs(int advertId) {
        return hrCandidateSelectedFacade.advertizedJobs(advertId);
    }

    @Override
    public List<Object[]> readCandidiates(String advertJobId, String sql) {
        return hrCandidateSelectedFacade.readCandidiates(advertJobId, sql);
    }

    @Override
    public void save(HrCandidateSelected hrCandidateSelected) {
        hrCandidateSelectedFacade.create(hrCandidateSelected);
    }

    @Override
    public int shortListCandidates(List<HrCandidateSelected> recommendations) {
        return hrCandidateSelectedFacade.shortListCandidates(recommendations);
    }

    @Override
    public List<HrCandidateSelected> readApprovedCandidates(String advertJobId, String sql) {
        return hrCandidateSelectedFacade.readApprovedCandidates(advertJobId, sql);
    }

    @Override
    public HrExamPercentages selectExamPercentage(int advertId) {
        return hrCandidateSelectedFacade.selectExamPercentage(advertId);
    }
    
    @Override
    public int saveCandidateAssessmentResult(HrCandidateSelected candidateAssessmentResult) {
        return hrCandidateSelectedFacade.saveCandidateAssessmentResult(candidateAssessmentResult);
    }
    
    @Override
    public int saveFilteredCandidate(List<HrCandidateSelected> recommendations) {
        return hrCandidateSelectedFacade.saveFilteredCandidate(recommendations);
    }

}
