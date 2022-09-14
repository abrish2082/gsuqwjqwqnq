/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Mac
 */
@Local
public interface HrInternshipStudentsBeanLocal {

    public List<HrTdUniversities> universities();

    public List<HrTdIspStudents> findByYearSemister(HrTdIspStudents hrTdIspStudents);

    public void create(HrTdIspStudents hrTdIspStudents);

    public void update(HrTdIspStudents hrTdIspStudents);

   public HrTdUniversities findByUniversityId(int UnId); 

    public List<HrTdUniversities> findByUniversityId(HrTdIspStudents hrTdIspStudents);

    public HrTdIspStudents getSelectedRequest(Integer request);

    public boolean checkDuplicate(HrTdIspStudentDetails hrTdIspStudentDetails);

}
