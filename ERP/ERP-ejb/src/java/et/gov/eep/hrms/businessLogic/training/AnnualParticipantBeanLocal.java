/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface AnnualParticipantBeanLocal {

    public HrTdAnnualTrainingNeeds findByCourse(HrTdCourses hrTdCourses);

    public ArrayList<HrTdAnnualTraParticipants> findAllApproved();

    public ArrayList<HrEmployees> findByEmpId(HrEmployees hrEmployees);

    public ArrayList<HrEmployees> findByEmpName(HrEmployees hrEmployees);

    public List<HrTdAnnualTrainingNeeds> findApproved();

    public HrTdAnnualTrainingNeeds getSelectedRequest(int request);

    public void save(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds);

    public void edit(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds);

    public void saveOrUpdate(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds);

}
