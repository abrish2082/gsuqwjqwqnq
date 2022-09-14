/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.entity.training.HrTdUnplanTraParticipant;
import et.gov.eep.hrms.entity.training.HrTdUnplanTrainingRequest;
import et.gov.eep.hrms.mapper.training.HrLuTrainingCategoryFacade;
import et.gov.eep.hrms.mapper.training.HrTdCoursesFacade;
import et.gov.eep.hrms.mapper.training.HrTdTrainerProfilesFacade;
import et.gov.eep.hrms.mapper.training.HrTdUnplanTrainingRequestFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import static javax.ws.rs.core.Response.status;

/**
 *
 * @author Abdi_Pc
 */
@Stateless
public class HrTdUnplanTrainingRequestBean implements HrTdUnplanTrainingRequestBeanLocal {

    @EJB
    HrTdUnplanTrainingRequestFacade hrTdUnplanTrainingRequestFacade;

    @EJB
    HrLuTrainingCategoryFacade hrLuTrainingCategoryFacade;

    @EJB
    HrTdCoursesFacade hrTdCoursesFacade;

    @EJB
    HrTdTrainerProfilesFacade hrTdTrainerProfilesFacade;

    @Override
    public void save(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest) {
        hrTdUnplanTrainingRequestFacade.create(hrTdUnplanTrainingRequest);
    }

    @Override
    public void saveOrUpdate(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest) {
        hrTdUnplanTrainingRequestFacade.saveOrUpdate(hrTdUnplanTrainingRequest);
    }

    @Override
    public List<HrDepartments> findByDeptId(HrDepartments hrDepartments) {
        return hrTdUnplanTrainingRequestFacade.findByDeptId(hrDepartments);
    }

    @Override
    public List<HrLuTrainingCategory> findAll(HrLuTrainingCategory hrLuTrainingCategory) {
        return hrLuTrainingCategoryFacade.findAll();
    }
//
//    @Override
//    public List<HrTdTrainerProfiles> institutionName(HrTdTrainerProfiles hrTdTrainerProfiles) {
//        return hrTdUnplanTrainingRequestFacade.institutionName(hrTdTrainerProfiles);
//    }

    @Override
    public List<HrTdTrainerProfiles> findAll(HrTdTrainerProfiles hrTdTrainerProfiles) {
        return hrTdTrainerProfilesFacade.findAll();

    }

    @Override
    public void edit(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest) {
        hrTdUnplanTrainingRequestFacade.edit(hrTdUnplanTrainingRequest);
    }

    @Override
    public List<HrTdCourses> findById(HrLuTrainingCategory hrLuTrainingCategory) {
        return hrTdUnplanTrainingRequestFacade.findById(hrLuTrainingCategory);
    }

    @Override
    public List<HrTdUnplanTrainingRequest> loadTrainingRequest(int status) {
        return hrTdUnplanTrainingRequestFacade.loadTrainingRequest(status);
    }

    @Override
    public HrTdUnplanTrainingRequest getSelectedRequest(Integer id) {
        return hrTdUnplanTrainingRequestFacade.getSelectedRequest(id);
    }

    @Override
    public List<HrTdCourses> findByBudgetYear(int budgetYear) {
        return hrTdUnplanTrainingRequestFacade.findByBudgetYear(budgetYear);
    }

    @Override
    public HrTdUnplanTrainingRequest findBytrainingId(HrTdCourses trainingId) {
        return hrTdUnplanTrainingRequestFacade.findbyTrainingId(trainingId);
    }

    @Override
    public List<HrTdUnplanTraParticipant> searchTrainerByCourse(BigDecimal courseId) {
        return hrTdUnplanTrainingRequestFacade.searchTrainerByCourse(courseId);
    }

    @Override
    public List<HrTdAnnualTraParticipants> searchTrainerByCourseid(BigDecimal courseIds, int year) {
        return hrTdUnplanTrainingRequestFacade.searchTrainerByCourseid(courseIds, year);
    }

    @Override
    public List<HrTdCourses> SearchTraningNeedByBudgetYear1(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        return hrTdUnplanTrainingRequestFacade.SearchTraningNeedByBudgetYear1(hrTdAnnualNeedRequests);
    }

    @Override
    public List<HrTdUnplanTrainingRequest> loadTrainingRequestList(Status status) {
        return hrTdUnplanTrainingRequestFacade.loadTrainingRequestList(status);
    }

    @Override
    public List<HrTdUnplanTrainingRequest> findRequestForApproval() {
         return hrTdUnplanTrainingRequestFacade.findRequestForApproval();
    }

    @Override
    public List<HrTdUnplanTrainingRequest> loadTrainingRequestListForTwo(Status status) {
        return hrTdUnplanTrainingRequestFacade.loadTrainingRequestListForTwo(status);
    }

}
