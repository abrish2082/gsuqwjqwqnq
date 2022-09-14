/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdEmpTrainings;
import et.gov.eep.hrms.entity.training.HrTdUnplanTrainingRequest;
import et.gov.eep.hrms.mapper.training.HrTdEmpTrainingsFacade;
import et.gov.eep.hrms.mapper.training.HrTdUnplanTrainingRequestFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi_Pc
 */
@Stateless
public class HrTdEmpTrainingsBean implements HrTdEmpTrainingsBeanLocal {

    @EJB
    HrTdEmpTrainingsFacade hrTdEmpTrainingsFacade;

    @EJB
    HrTdUnplanTrainingRequestFacade hrTdUnplanTrainingRequestFacade;

    @Override
    public void edit(HrTdEmpTrainings hrTdEmpTrainings) {
        hrTdEmpTrainingsFacade.edit(hrTdEmpTrainings);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void create(HrTdEmpTrainings hrTdEmpTrainings) {
        hrTdEmpTrainingsFacade.create(hrTdEmpTrainings);
    }

    @Override
    public List<HrTdAnnualNeedRequests> findBudgetYear() {
        return hrTdEmpTrainingsFacade.findBudgetYear();

    }

    @Override
    public List<HrTdUnplanTrainingRequest> findUBudgetYear() {
        return hrTdEmpTrainingsFacade.findUBudgetYear();
    }

    @Override
    public List<HrTdAnnualTrainingNeeds> findbyID(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        return hrTdEmpTrainingsFacade.findbyID(hrTdAnnualNeedRequests);
    }

    @Override
    public List<HrTdUnplanTrainingRequest> findall() {
        return hrTdEmpTrainingsFacade.findbyAll();
    }

    @Override
    public List<HrTdUnplanTrainingRequest> findAll() {
        return hrTdUnplanTrainingRequestFacade.findAll();
    }

    @Override
    public List<HrTdAnnualTrainingNeeds> findByAnnTraId() {
        return hrTdEmpTrainingsFacade.findByAnnTraId();
    }

    @Override
    public List<HrTdEmpTrainings> findPlannedParticipant(HrTdEmpTrainings hrTdEmpTrainings) {
        return hrTdEmpTrainingsFacade.findPlannedParticipant(hrTdEmpTrainings);
    }

    @Override
    public List<HrTdEmpTrainings> findUnPlannedParticipant(HrTdEmpTrainings hrTdEmpTrainings) {
        return hrTdEmpTrainingsFacade.findUnPlannedParticipant(hrTdEmpTrainings);
    }

    @Override
    public List<HrTdEmpTrainings> findByEmpId(HrEmployees hrEmployees) {
        return hrTdEmpTrainingsFacade.findByEmpId(hrEmployees);
    }

    @Override
    public List<HrTdEmpTrainings> findByEmpIds(HrEmployees hrEmployees) {
         return hrTdEmpTrainingsFacade.findByEmpIds(hrEmployees);
    }
}
