/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Mac
 */
@Local
public interface HrLuTrainingCategoryBeanLocal {

    public List<HrLuTrainingCategory> findAllCategory();

    public HrLuTrainingCategory fetchCatagory(int id);

    public List<HrTdCourses> findbyID(HrLuTrainingCategory hrLuTrainingCategory);

    public void findAll();


    public void save(HrLuTrainingCategory hrLuTrainingCategory);

    public boolean checkCatagoryName(HrLuTrainingCategory hrLuTrainingCategory);
}
