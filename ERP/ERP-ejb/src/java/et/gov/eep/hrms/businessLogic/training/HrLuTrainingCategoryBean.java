/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.mapper.training.HrLuTrainingCategoryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mac
 */
@Stateless
public class HrLuTrainingCategoryBean implements HrLuTrainingCategoryBeanLocal {

    @EJB
    HrLuTrainingCategoryFacade hrLuTrainingCategoryFacade;

    @Inject
    HrLuTrainingCategory hrLuTrainingCategory;

    /**
     *
     * @return
     */
    @Override
    public List<HrLuTrainingCategory> findAllCategory() {
        return hrLuTrainingCategoryFacade.findAllCategory();
    }

    @Override
    public HrLuTrainingCategory fetchCatagory(int id) {
        return hrLuTrainingCategoryFacade.find(id);
    }

    @Override
    public List<HrTdCourses> findbyID(HrLuTrainingCategory hrLuTrainingCategory) {
        return hrLuTrainingCategoryFacade.findbyID(hrLuTrainingCategory);
    }

    @Override
    public void findAll() {
        hrLuTrainingCategoryFacade.findAll();
    }

    @Override
    public void save(HrLuTrainingCategory hrLuTrainingCategory) {
        hrLuTrainingCategoryFacade.create(hrLuTrainingCategory);
    }

    @Override
    public boolean checkCatagoryName(HrLuTrainingCategory hrLuTrainingCategory) {
        return hrLuTrainingCategoryFacade.checkCatagoryName(hrLuTrainingCategory);
    }

}
