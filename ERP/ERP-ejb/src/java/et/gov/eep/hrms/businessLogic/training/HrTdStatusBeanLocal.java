/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface HrTdStatusBeanLocal {

    public HrTdIspStudentDetails getSelectedRequest(int request);

    public void saveOrUpdate(HrTdIspStudentDetails hrTdIspStudentDetails);

    public void edit(HrTdIspStudentPlacement hrTdIspStudentPlacement);

    public List<HrTdIspStudents> findByYear(Integer years);

    public List<HrTdIspStudents> findYear();

    public List<HrTdIspStudentDetails> findBysemister(HrTdIspStudents hrTdIspStudents);

    public List<HrTdIspStudentDetails> searchBySemister(HrTdIspStudents hrTdIspStudents);
}
