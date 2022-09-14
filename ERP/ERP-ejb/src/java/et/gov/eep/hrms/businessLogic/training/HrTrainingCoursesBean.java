/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.mapper.training.HrLuTrainingCategoryFacade;
import et.gov.eep.hrms.mapper.training.HrTdCoursesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Mac
 */
@Stateless
public class HrTrainingCoursesBean implements HrTrainingCoursesBeanLocal {

    @EJB
    HrLuTrainingCategoryFacade hrLuTrainingCategoryFacade;
    @EJB
    HrTdCoursesFacade hrTrainingCoursesFacade;

    @Override
    public List<HrLuTrainingCategory> findAllCategory() {
        return hrLuTrainingCategoryFacade.findAllCategory();
    }

    @Override
    public HrLuTrainingCategory searchByCategoryName(HrLuTrainingCategory hrTrainingCourses) {
        return hrLuTrainingCategoryFacade.searchByCategoryName(hrTrainingCourses);
    }

    @Override
    public List<HrTdCourses> findByCatagory(HrTdCourses hrTrainingCourses) {
        return hrLuTrainingCategoryFacade.findByCatagory(hrTrainingCourses);
    }

    @Override
    public List<HrTdCourses> searchByCatagory(String catagoryName) {
        return hrLuTrainingCategoryFacade.searchByCatagory(catagoryName);
    }

    @Override
    public List<HrTdCourses> findAll() {
        return hrTrainingCoursesFacade.findAll();
    }

    @Override
    public void create(HrTdCourses hrTrainingCourses) {
        hrTrainingCoursesFacade.create(hrTrainingCourses);
    }

    @Override
    public void update(HrTdCourses hrTrainingCourses) {
        hrTrainingCoursesFacade.edit(hrTrainingCourses);
    }

    @Override
    public HrTdCourses findCourseId(HrTdCourses hrTdCourses) {
        return hrTrainingCoursesFacade.findCourseId(hrTdCourses);
    }

    @Override
    public boolean isExist(HrTdCourses hrTdCourses) {
        return hrTrainingCoursesFacade.isExist(hrTdCourses);

    }

    @Override
    public HrLuTrainingCategory checkCatagoryName(HrLuTrainingCategory hrLuTrainingCategory) {
       return hrTrainingCoursesFacade.checkCatagoryName(hrLuTrainingCategory);
    }

    @Override
    public void save(HrLuTrainingCategory hrLuTrainingCategory) {
        hrLuTrainingCategoryFacade.create(hrLuTrainingCategory);
    }
}
