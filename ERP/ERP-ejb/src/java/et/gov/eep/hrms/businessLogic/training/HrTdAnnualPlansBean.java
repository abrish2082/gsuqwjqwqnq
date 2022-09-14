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
import et.gov.eep.hrms.mapper.training.HrTdAnnualPlansFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualPlansBean implements HrTdAnnualPlansBeanLocal {

    @EJB
    HrTdAnnualPlansFacade hrTdAnnualPlansFacade;

    @Override
    public List<Object[]> readApprovedTraNeeds(int year) {
        return hrTdAnnualPlansFacade.readApprovedTraNeeds(year);
    }

    @Override
    public List<Object[]> readApprovedAnnTraNeedDetail(int year, int traId) {
        return hrTdAnnualPlansFacade.readApprovedAnnTraNeedDetail(year, traId);
    }

    @Override
    public ArrayList<HrTdAnnualNeedRequests> searchApprovedByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        return hrTdAnnualPlansFacade.searchApprovedByYear(hrTdAnnualNeedRequests);
    }

    @Override
    public List<HrTdAnnualTrainingNeeds> searchTraningCourseByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        return hrTdAnnualPlansFacade.searchTraningCourseByYear(hrTdAnnualNeedRequests);
    }

    @Override
    public List<HrTdAnnualTrainingNeeds> findByCourse(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        return hrTdAnnualPlansFacade.findByCourse(hrTdAnnualTrainingNeeds);
    }

    @Override
    public List<HrTdAnnualNeedRequests> getByYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        return hrTdAnnualPlansFacade.getByYear(hrTdAnnualNeedRequests);
    }

    @Override
    public HrTdAnnualNeedRequests searchById(int request) {
        return hrTdAnnualPlansFacade.searchById(request);
    }

    @Override
    public List<HrTdAnnualNeedRequests> findBudgetYear() {
        return hrTdAnnualPlansFacade.findBudgetYear();
    }

    @Override
    public void save(HrTdAnnualPlans hrTdAnnualNeedRequests) {
        hrTdAnnualPlansFacade.saveOrUpdate(hrTdAnnualNeedRequests);
    }

    @Override
    public HrTdAnnualPlans getMaximumId() {
        return hrTdAnnualPlansFacade.getMaximumId();
    }

    @Override
    public List<HrTdAnnualPlans> findByYear(int budgetYear) {
        return hrTdAnnualPlansFacade.findByYear(budgetYear);
    }

    @Override
    public List<HrTdAnnualPlans> findAll() {
        return hrTdAnnualPlansFacade.findAll();
    }

    @Override
    public List<HrTdAnnualPlans> findPlanByYear(HrTdAnnualPlans budgetYear) {
        return hrTdAnnualPlansFacade.findPlanByYear(budgetYear);
    }

    @Override
    public List<HrTdAnnualTraParticipants> viewParticipantDetail() {
       return hrTdAnnualPlansFacade.viewParticipantDetail();
    }

}
