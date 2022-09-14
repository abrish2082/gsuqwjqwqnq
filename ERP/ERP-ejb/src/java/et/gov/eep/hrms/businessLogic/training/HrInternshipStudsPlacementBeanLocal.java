/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.organization.HrDepartments;
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
public interface HrInternshipStudsPlacementBeanLocal {

    public void create(HrTdIspStudentPlacement hrTdIspStudentPlacement);

    public List<HrTdIspStudentDetails> findAllStudentsName();

    public void update(HrTdIspStudentPlacement hrTdIspStudentPlacement);

    public ArrayList<HrDepartments> findAllDepartmentsName();

    public HrTdIspStudentPlacement findbyStdId(HrTdIspStudentPlacement hrTdIspStudentPlacement);

    public HrTdIspStudentPlacement getSelectedRequest(int request);

}
