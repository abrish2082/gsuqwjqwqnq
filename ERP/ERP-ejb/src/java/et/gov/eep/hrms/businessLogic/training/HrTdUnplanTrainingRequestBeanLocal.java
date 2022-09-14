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
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author Abdi_Pc
 */
@Local
public interface HrTdUnplanTrainingRequestBeanLocal {

    public List<HrDepartments> findByDeptId(HrDepartments hrDepartments);

    public List<HrLuTrainingCategory> findAll(HrLuTrainingCategory hrLuTrainingCategory);

//    public List<HrTdTrainerProfiles> institutionName(HrTdTrainerProfiles hrTdTrainerProfiles);
    public List<HrTdTrainerProfiles> findAll(HrTdTrainerProfiles hrTdTrainerProfiles);

    public void edit(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest);

    public List<HrTdCourses> findById(HrLuTrainingCategory hrLuTrainingCategory);

    public List<HrTdUnplanTrainingRequest> loadTrainingRequest(int status);

    public HrTdUnplanTrainingRequest getSelectedRequest(Integer id);

    public void saveOrUpdate(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest);

    public void save(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest);

    public List<HrTdCourses> findByBudgetYear(int budgetYear);

    public HrTdUnplanTrainingRequest findBytrainingId(HrTdCourses trainingId);

    public List<HrTdUnplanTraParticipant> searchTrainerByCourse(BigDecimal courseId);

    public List<HrTdAnnualTraParticipants> searchTrainerByCourseid(BigDecimal courseIds, int year);

    public List<HrTdCourses> SearchTraningNeedByBudgetYear1(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public List<HrTdUnplanTrainingRequest> loadTrainingRequestList(Status status);

    public List<HrTdUnplanTrainingRequest> findRequestForApproval();

    public List<HrTdUnplanTrainingRequest> loadTrainingRequestListForTwo(Status status);

}
