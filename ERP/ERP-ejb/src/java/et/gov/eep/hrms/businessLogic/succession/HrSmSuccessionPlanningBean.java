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
import et.gov.eep.hrms.mapper.succession.HrSmSuccessionPlansFacade;
import et.gov.eep.hrms.mapper.succession.HrSmSuccessorEvaluationFacade;
import et.gov.eep.hrms.mapper.training.HrLuTrainingCategoryFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author insa
 */
@Stateless
public class HrSmSuccessionPlanningBean implements HrSmSuccessionPlanningBeanLocal {

    @EJB
    HrSmSuccessionPlansFacade hrSmSuccessionPlansFacade;

    @EJB
    HrLuTrainingCategoryFacade hrLuTrainingCategoryFacade;
    
    @EJB
    HrSmSuccessorEvaluationFacade hrSmSuccessorEvaluationFacade;

    @Override
    public HrSmSuccessorEvaluation getSelectedRequest(int request) {
        return hrSmSuccessionPlansFacade.getSelectedRequest(request);
    }

    @Override
    public HrSmSuccessionPlans getSelectedPlanRequest(int request) {
        return hrSmSuccessionPlansFacade.getSelectedPlanRequest(request);
    }

    @Override
    public void edit(HrSmSuccessionPlans hrSmSuccessionPlans) {
        hrSmSuccessionPlansFacade.edit(hrSmSuccessionPlans);
    }

    @Override
    public void create(HrSmSuccessionPlans hrSmSuccessionPlans) {
        hrSmSuccessionPlansFacade.create(hrSmSuccessionPlans);
    }

    @Override
    public void saveOrUpdate(HrSmSuccessionPlans hrSmSuccessionPlans) {
        hrSmSuccessionPlansFacade.saveOrUpdate(hrSmSuccessionPlans);
    }

    @Override
    public ArrayList<HrTdCourses> findAllCourseName() {
        return hrSmSuccessionPlansFacade.findAllCourseName();
    }

    @Override
    public ArrayList<HrLuTrainingCategory> findAllCoursecatagory() {
        return hrSmSuccessionPlansFacade.findAllCoursecatagory();
    }

    @Override
    public ArrayList<HrTdCourses> getTrainingCategoryInfo(HrLuTrainingCategory hrLuTrainingCategory) {
        return hrSmSuccessionPlansFacade.getTrainingCategoryInfo(hrLuTrainingCategory);
    }

    @Override
    public HrLuTrainingCategory findCategoryName(HrLuTrainingCategory hrLuTrainingCategory) {
        return hrSmSuccessionPlansFacade.findCategoryName(hrLuTrainingCategory);
    }

    @Override
    public List<HrTdCourses> findCourseName(HrTdCourses hrTdCourses) {
        return hrSmSuccessionPlansFacade.findCourseName(hrTdCourses);
    }

    @Override
    public List<HrSmSuccessorEvaluation> findAprovedSuccessor() {
        return hrSmSuccessionPlansFacade.findAprovedSuccessor();
    }

    @Override
    public List<HrLuTrainingCategory> findAll() {
        return hrLuTrainingCategoryFacade.findAll();
    }

    @Override
    public List<HrSmSuccessionPlans> findAlls() {
        return hrSmSuccessionPlansFacade.findAll();
    }

    @Override
    public List<HrTdCourses> findByCourseName(HrTdCourses hrTdCourses) {
        return hrSmSuccessionPlansFacade.findByCourseName(hrTdCourses);
    }

    @Override
    public List<HrTdCourses> findByCatagory(HrTdCourses hrTrainingCourses) {
        return hrSmSuccessionPlansFacade.findByCatagory(hrTrainingCourses);
    }

    @Override
    public HrSmSuccessionPlans readkmpDetail(Integer id) {
        return hrSmSuccessionPlansFacade.readkmpDetail(id);
    }

    @Override
    public List<HrSmSuccessionPlans> findPalnnAprroved() {
        return hrSmSuccessionPlansFacade.findPlanApproved();
    }

    @Override
    public List<HrSmSuccessionPlans> findFistName(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        return hrSmSuccessionPlansFacade.findFistName(hrSmSuccessorEvaluation);
    }

    @Override
    public List<HrSmSuccessionPlans> findall(HrSmSuccessionPlans hrSmSuccessionPlans) {
        return hrSmSuccessionPlansFacade.findAll();
    }

    @Override
    public List<HrSmSuccessorEvaluation> findAllSuccessor(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        return hrSmSuccessorEvaluationFacade.findAll();
    }

    @Override
    public List<HrSmSuccessionPlans> findall() {
        return hrSmSuccessionPlansFacade.findAll();
    }

    @Override
    public List<HrSmSuccessionPlans> loadSuccessorLists(int status) {
        return hrSmSuccessionPlansFacade.loadSuccessorLists(status);
    }

    @Override
    public List<HrSmSuccessionPlans> loadSuccessorList(int status, int userId) {
        return hrSmSuccessionPlansFacade.loadSuccessorList(status, userId);
    }

    @Override
    public List<HrSmSuccessionPlans> loadSuccessionRequestList(Status status, int userId) {
        return hrSmSuccessionPlansFacade.loadSuccessionRequestList(status, userId);
    }
 @Override
    public List<HrSmSuccessionPlans> loadSuccessionAprovedtList(Status status, int userId) {
        return hrSmSuccessionPlansFacade.loadSuccessionAprovedList(status, userId);
    }
    @Override
    public List<SelectItem> filterByAproveStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load All List"));
        return selectItems;
    }

    @Override
    public List<HrSmSuccessionPlans> findRequestForApproval() {
        return hrSmSuccessionPlansFacade.findAllRequests();
    }
}
