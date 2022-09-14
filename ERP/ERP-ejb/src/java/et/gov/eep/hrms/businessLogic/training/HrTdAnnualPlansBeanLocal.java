/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualPlans;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Benin
 */
@Local
public interface HrTdAnnualPlansBeanLocal {

    public List<Object[]> readApprovedTraNeeds(int year);

    public List<Object[]> readApprovedAnnTraNeedDetail(int year, int traId);

    public ArrayList<HrTdAnnualNeedRequests> searchApprovedByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public List<HrTdAnnualTrainingNeeds> searchTraningCourseByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public List<HrTdAnnualTrainingNeeds> findByCourse(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds);

    public HrTdAnnualNeedRequests searchById(int request);

    public List<HrTdAnnualNeedRequests> getByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public List<HrTdAnnualNeedRequests> findBudgetYear();

    public void save(HrTdAnnualPlans hrTdAnnualNeedRequests);

    public HrTdAnnualPlans getMaximumId();

    public List<HrTdAnnualPlans> findByYear(int budgetYear);

    public List<HrTdAnnualPlans> findAll();

    public List<HrTdAnnualPlans> findPlanByYear(HrTdAnnualPlans budgetYear);

    public List<HrTdAnnualTraParticipants> viewParticipantDetail();
}
