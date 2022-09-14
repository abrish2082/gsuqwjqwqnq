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
public interface HrTrainingCoursesBeanLocal {

    public List<HrLuTrainingCategory> findAllCategory();

    public List<HrTdCourses> findByCatagory(HrTdCourses hrTrainingCourses);

    public void update(HrTdCourses hrTrainingCourses);

    public void create(HrTdCourses hrTrainingCourses);

    public HrTdCourses findCourseId(HrTdCourses hrTdCourses);

    public HrLuTrainingCategory searchByCategoryName(HrLuTrainingCategory hrTrainingCourses);

    public boolean isExist(HrTdCourses hrTdCourses);

    public List<HrTdCourses> searchByCatagory(String catagoryName);

    public List<HrTdCourses> findAll();

    public HrLuTrainingCategory checkCatagoryName(HrLuTrainingCategory hrLuTrainingCategory);

    public void save(HrLuTrainingCategory hrLuTrainingCategory);

  
}
