
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.promotion;

import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmpExperiences;
import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.mapper.employee.HrEmpEducationsFacade;
import et.gov.eep.hrms.mapper.employee.HrEmpExperiencesFacade;
import et.gov.eep.hrms.mapper.employee.HrEmpTrainingsFacade;
import et.gov.eep.hrms.mapper.evaluation.HrEvaluationResultsFacade;
import et.gov.eep.hrms.mapper.promotion.HrPromotionRequestsFacade;
import et.gov.eep.hrms.mapper.recruitment.HrAdvertisedJobsFacade;
import et.gov.eep.hrms.mapper.recruitment.HrAdvertisementsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class PromotionApplyBean implements PromotionApplyBeanLocal {

    @EJB
   HrPromotionRequestsFacade promotionRequestsFacade;
    @EJB
    HrAdvertisementsFacade hrAdvertisementsFacade;
    @EJB
    HrAdvertisedJobsFacade advertisedJobsFacade;

    @EJB
    HrEmpExperiencesFacade hrEmpExperiencesFacade;
    @EJB
    HrEmpTrainingsFacade hrEmpTrainingsFacade;
    @EJB
    HrEmpEducationsFacade hrEmpEducationsFacade;

    @EJB
    HrEvaluationResultsFacade hrEvaluationResultFacade;

    @Override
    public List<HrAdvertisements> activeVacancies() {
        return promotionRequestsFacade.activeVacancies();
    }

    @Override
    public List<HrAdvertisedJobs> readAdverJobsQualification(int advertId) {
        return promotionRequestsFacade.readAdverJobsQualification(advertId);
    }

    @Override
    public void saveOrUpdate(HrPromotionRequests promotionRequests) {
        promotionRequestsFacade.saveOrUpdate(promotionRequests);
    }

    @Override
    public void edit(HrPromotionRequests promotionRequests) {
        promotionRequestsFacade.edit(promotionRequests);
    }

    @Override
    public List<HrAdvertisements> findInternalPost() {
        return promotionRequestsFacade.findInternalVacancy();
    }

    @Override
    public HrPromotionRequests findPomoApplied(HrEmployees hrEmployees, HrAdvertisedJobs advertisedJobesEntity) {
        return promotionRequestsFacade.findPomoApplied(hrEmployees, advertisedJobesEntity);
    }

    @Override
    public List<HrEmployees> searchPermanentEmp(String hrEmployees) {
        return promotionRequestsFacade.searchPermanentEmp(hrEmployees);
    }

    @Override
    public HrEmployees getByPermanentEmp(HrEmployees hrEmployees) {
        return promotionRequestsFacade.getPermanentEmp(hrEmployees);
    }

    @Override
    public List<HrPromotionRequests> candidateShortList() {
        return promotionRequestsFacade.findAlls();
    }

    @Override
    public List<HrPromotionRequests> candList(String jobsId) {
        return promotionRequestsFacade.candList(jobsId);
    }

    @Override
    public List<HrAdvertisedJobs> getByHrAdvertisements(HrAdvertisements hrAdvertisements) {
        return promotionRequestsFacade.getByHrAdvertisements(hrAdvertisements);
    }

    @Override
    public List<HrPromotionRequests> getByJobsId(HrAdvertisedJobs advertisedJobsenty) {
        return promotionRequestsFacade.getByJobsId(advertisedJobsenty);

    }

    @Override
    public List<HrEmpEducations> findEmpEducation(HrEmployees hrEmployees) {
        return hrEmpEducationsFacade.findEmpEducation(hrEmployees);
    }

    @Override
    public List<HrEmpExperiences> findEmpExprience(HrEmployees hrEmployees) {
        return hrEmpExperiencesFacade.findEmpExprience(hrEmployees);
    }

    @Override
    public List<HrEmpTrainings> findEmpTrainings(HrEmployees hrEmployees) {
        return hrEmpTrainingsFacade.findEmpTrainings(hrEmployees);
    }

    @Override
    public List<HrEvaluationResults> findEmpEvalution(HrEmployees hrEmployees) {
    return hrEvaluationResultFacade.findEmpEvalution(hrEmployees);
    }

    @Override
    public int checkRequesterIsQualified(int requsterId) {
    return promotionRequestsFacade.checkRequesterIsQualified(requsterId);}

}
