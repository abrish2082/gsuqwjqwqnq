/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MUS
 */ 
@Local
public interface HrInternshipStudentDetailsBeanLocal {

    public void update(HrTdIspStudentDetails hrTdIspStudentDetails);

    public ArrayList<HrTdIspStudentPlacement> findByFname(HrTdIspStudentDetails hrTdIspStudentDetails);

    public HrTdIspStudentDetails findByStudFnameObj(HrTdIspStudentDetails hrTdIspStudentDetails);

    public HrTdIspStudentDetails findByidObj(HrTdIspStudentDetails hrTdIspStudentDetails);

    public List<HrTdIspStudentDetails> findBystatus();

    public List<HrTdIspStudentDetails> findBystatusp();

    public List<HrTdUniversities> universities();

    public void create(HrTdIspStudents hrTdIspStudents);

    public void update(HrTdIspStudents hrTdIspStudents);
}
