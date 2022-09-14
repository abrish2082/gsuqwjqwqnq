package et.gov.eep.hrms.businessLogic.training;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentDetailsFacade;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author MUS
 */
@Stateless
public class HrInternshipStudentDetailsBean implements HrInternshipStudentDetailsBeanLocal {

    @EJB
    HrTdIspStudentsFacade hrTdIspStudentsFacade;

    @Inject
    HrTdIspStudents hrTdIspStudents;

    @EJB
    HrTdIspStudentDetailsFacade hrTdIspStudentDetailsFacade;
    
    @Inject
    HrTdIspStudentDetails hrTdIspStudentDetails;

    @Override
    public void update(HrTdIspStudentDetails hrTdIspStudentDetails) {
        hrTdIspStudentDetailsFacade.saveOrUpdate(hrTdIspStudentDetails);
    }

    @Override
    public ArrayList<HrTdIspStudentPlacement> findByFname(HrTdIspStudentDetails hrTdIspStudentDetails) {
        return hrTdIspStudentDetailsFacade.findByFname(hrTdIspStudentDetails);
    }

    @Override
    public HrTdIspStudentDetails findByStudFnameObj(HrTdIspStudentDetails hrTdIspStudentDetails) {
        return hrTdIspStudentDetailsFacade.findByStudFnameObj(hrTdIspStudentDetails);

    }

    @Override
    public HrTdIspStudentDetails findByidObj(HrTdIspStudentDetails HrTdIspStudentDetails) {
        return hrTdIspStudentDetailsFacade.findByidObj(HrTdIspStudentDetails);
    }
 
    @Override
    public List<HrTdIspStudentDetails> findBystatus() {
        return hrTdIspStudentDetailsFacade.findBystatus();
    }

    @Override
    public List<HrTdIspStudentDetails> findBystatusp() {
        return hrTdIspStudentDetailsFacade.findBystatusp();
    }

    @Override
    public List<HrTdUniversities> universities() {
        return hrTdIspStudentsFacade.universitiesList();
    }

   

    @Override
    public void create(HrTdIspStudents hrInternshipStudents) {
        hrTdIspStudentsFacade.create(hrInternshipStudents);
    }

    @Override
    public void update(HrTdIspStudents hrInternshipStudents) {
        hrTdIspStudentsFacade.edit(hrInternshipStudents);
    }
}
