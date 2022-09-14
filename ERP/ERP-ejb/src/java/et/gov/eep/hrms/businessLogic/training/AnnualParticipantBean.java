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
import et.gov.eep.hrms.mapper.training.HrTdAnnualTraParticipantsFacade;
import et.gov.eep.hrms.mapper.training.HrTdAnnualTrainingNeedsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ob
 */
@Stateless
public class AnnualParticipantBean implements AnnualParticipantBeanLocal {

    @EJB
    HrTdAnnualTraParticipantsFacade hrTdAnnualTraParticipantsFacade;
    HrTdAnnualTrainingNeedsFacade hrTdAnnualTrainingNeedsFacade;

    @Override
    public HrTdAnnualTrainingNeeds findByCourse(HrTdCourses hrTdCourses) {
        return hrTdAnnualTraParticipantsFacade.findByCourse(hrTdCourses);
    }

    @Override
    public ArrayList<HrTdAnnualTraParticipants> findAllApproved() {
        return hrTdAnnualTraParticipantsFacade.findAllApproved();
    }

    @Override
    public ArrayList<HrEmployees> findByEmpId(HrEmployees hrEmployees) {
        return hrTdAnnualTraParticipantsFacade.findByEmpId(hrEmployees);
    }

    @Override
    public ArrayList<HrEmployees> findByEmpName(HrEmployees hrEmployees) {
        return hrTdAnnualTraParticipantsFacade.findByEmpName(hrEmployees);
    }

    @Override
    public List<HrTdAnnualTrainingNeeds> findApproved() {
        return hrTdAnnualTraParticipantsFacade.findApproved();
    }

    @Override
    public HrTdAnnualTrainingNeeds getSelectedRequest(int request) {
        return hrTdAnnualTraParticipantsFacade.getSelectedRequest(request);
    }
    @Override
    public void save(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        hrTdAnnualTrainingNeedsFacade.create(hrTdAnnualTrainingNeeds);
    }

    @Override
    public void edit(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        hrTdAnnualTrainingNeedsFacade.edit(hrTdAnnualTrainingNeeds);
    }
    @Override
    public void saveOrUpdate(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        hrTdAnnualTrainingNeedsFacade.saveOrUpdate(hrTdAnnualTrainingNeeds);
    }

}
