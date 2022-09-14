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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PromotionApplyBeanLocal {

    public List<HrAdvertisements> activeVacancies();

    public List<HrAdvertisedJobs> readAdverJobsQualification(int advertId);

    public void saveOrUpdate(HrPromotionRequests promotionRequests);

    public void edit(HrPromotionRequests promotionRequests);

    public List<HrAdvertisements> findInternalPost();

    public HrPromotionRequests findPomoApplied(HrEmployees hrEmployees, HrAdvertisedJobs advertisedJobesEntity);

    public List<HrEmployees> searchPermanentEmp(String hrEmployees);

    public HrEmployees getByPermanentEmp(HrEmployees hrEmployees);

    public List<HrPromotionRequests> candidateShortList();

    public List<HrPromotionRequests> candList(String jobsId);

    public List<HrAdvertisedJobs> getByHrAdvertisements(HrAdvertisements hrAdvertisements);

    public List<HrPromotionRequests> getByJobsId(HrAdvertisedJobs advertisedJobsenty);

    public List<HrEmpEducations> findEmpEducation(HrEmployees hrEmployees);

    public List<HrEmpExperiences> findEmpExprience(HrEmployees hrEmployees);

    public List<HrEmpTrainings> findEmpTrainings(HrEmployees hrEmployees);

    public List<HrEvaluationResults> findEmpEvalution(HrEmployees hrEmployees);

    public int checkRequesterIsQualified(int requsterId);
 
}
