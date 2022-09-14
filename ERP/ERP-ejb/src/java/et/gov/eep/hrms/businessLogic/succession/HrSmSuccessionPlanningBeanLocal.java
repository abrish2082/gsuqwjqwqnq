/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author insa
 */
@Local
public interface HrSmSuccessionPlanningBeanLocal {

    public HrSmSuccessorEvaluation getSelectedRequest(int request);

    public void edit(HrSmSuccessionPlans hrSmSuccessionPlans);

    public void create(HrSmSuccessionPlans hrSmSuccessionPlans);

    public ArrayList<HrTdCourses> findAllCourseName();

    public ArrayList<HrLuTrainingCategory> findAllCoursecatagory();

    public HrLuTrainingCategory findCategoryName(HrLuTrainingCategory hrLuTrainingCategory);

    public ArrayList<HrTdCourses> getTrainingCategoryInfo(HrLuTrainingCategory hrLuTrainingCategory);

    public List<HrTdCourses> findCourseName(HrTdCourses hrTdCourses);

    public void saveOrUpdate(HrSmSuccessionPlans hrSmSuccessionPlans);

    public List<HrSmSuccessorEvaluation> findAprovedSuccessor();

    public List<HrLuTrainingCategory> findAll();

    public List<HrSmSuccessionPlans> findAlls();

    public List<HrSmSuccessionPlans> findRequestForApproval();

    public List<HrTdCourses> findByCourseName(HrTdCourses hrTdCourses);

    public List<HrTdCourses> findByCatagory(HrTdCourses hrTrainingCourses);

    public HrSmSuccessionPlans readkmpDetail(Integer id);

    public List<HrSmSuccessionPlans> findPalnnAprroved();

    public List<HrSmSuccessionPlans> findall(HrSmSuccessionPlans hrSmSuccessionPlans);

    public List<HrSmSuccessionPlans> findFistName(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public List<HrSmSuccessionPlans> findall();

    public List<HrSmSuccessionPlans> loadSuccessorLists(int status);

    public List<HrSmSuccessionPlans> loadSuccessorList(int status, int userId);

    public List<SelectItem> filterByAproveStatus();

    public List<HrSmSuccessorEvaluation> findAllSuccessor(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public HrSmSuccessionPlans getSelectedPlanRequest(int request);

    public List<HrSmSuccessionPlans> loadSuccessionRequestList(Status status, int userId);

    public List<HrSmSuccessionPlans> loadSuccessionAprovedtList(Status status, int userId);

}
