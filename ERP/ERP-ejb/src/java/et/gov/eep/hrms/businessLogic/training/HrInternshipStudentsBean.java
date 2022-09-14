/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mac
 */
@Stateless
public class HrInternshipStudentsBean implements HrInternshipStudentsBeanLocal {

    @EJB
    HrTdIspStudentsFacade hrTdIspStudentsFacade;

    @Inject
    HrTdIspStudents hrTdIspStudents;
    @Inject
    HrTdIspStudentDetails hrTdIspStudentDetails;
    @Inject
    HrTdUniversities hrTdUniversities;

    @Override
    public List<HrTdUniversities> universities() {
        return hrTdIspStudentsFacade.universitiesList();
    }

    @Override
    public List<HrTdIspStudents> findByYearSemister(HrTdIspStudents hrTdIspStudents) {
        return hrTdIspStudentsFacade.findByYearSemister(hrTdIspStudents);
    }

    @Override
    public void create(HrTdIspStudents hrTdIspStudents) {
        hrTdIspStudentsFacade.create(hrTdIspStudents);
    }

    @Override
    public void update(HrTdIspStudents hrTdIspStudents) {
        hrTdIspStudentsFacade.edit(hrTdIspStudents);
    }

    @Override
    public HrTdUniversities findByUniversityId(int UnId) {
        return hrTdIspStudentsFacade.findByUniversityId(UnId);
    }

    @Override
    public List<HrTdUniversities> findByUniversityId(HrTdIspStudents hrTdIspStudents) {
        return hrTdIspStudentsFacade.findByUniversityId(hrTdIspStudents);
    }

    @Override
    public HrTdIspStudents getSelectedRequest(Integer request) {
        return hrTdIspStudentsFacade.getSelectedRequest(request);
    }

    @Override
    public boolean checkDuplicate(HrTdIspStudentDetails hrTdIspStudentDetails) {
        return hrTdIspStudentsFacade.checkDuplicate(hrTdIspStudentDetails);
    }
}
