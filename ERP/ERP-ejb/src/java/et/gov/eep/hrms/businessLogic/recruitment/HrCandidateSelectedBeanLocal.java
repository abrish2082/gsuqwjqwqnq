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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrCandidateSelectedBeanLocal {

    public List<HrAdvertisements> bachCodes(String type);

    public List<HrAdvertisedJobs> advertizedJobs(int advertId);

    public List<Object[]> readCandidiates(String advertJobId, String sql);

    public void save(HrCandidateSelected hrCandidateSelected);

    public int shortListCandidates(List<HrCandidateSelected> recommendations);

    public List<HrCandidateSelected> readApprovedCandidates(String advertJobId, String sql);

    public HrExamPercentages selectExamPercentage(int advertId);

    public int saveCandidateAssessmentResult(HrCandidateSelected candidateAssessmentResult);

    public int saveFilteredCandidate(List<HrCandidateSelected> recommendations);
}
