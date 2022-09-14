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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdi_Pc
 */
@Local
public interface HrTdEmpTrainingsBeanLocal {

    public void edit(HrTdEmpTrainings hrTdEmpTrainings);

    public void create(HrTdEmpTrainings hrTdEmpTrainings);

    public List<HrTdAnnualNeedRequests> findBudgetYear();

    public List<HrTdUnplanTrainingRequest> findUBudgetYear();

    public List<HrTdAnnualTrainingNeeds> findbyID(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public List<HrTdUnplanTrainingRequest> findall();

    public List<HrTdUnplanTrainingRequest> findAll();

    public List<HrTdAnnualTrainingNeeds> findByAnnTraId();

    public List<HrTdEmpTrainings> findPlannedParticipant(HrTdEmpTrainings hrTdEmpTrainings);

    public List<HrTdEmpTrainings> findUnPlannedParticipant(HrTdEmpTrainings hrTdEmpTrainings);

    public List<HrTdEmpTrainings> findByEmpId(HrEmployees hrEmployees);

    public List<HrTdEmpTrainings> findByEmpIds(HrEmployees hrEmployees);
}
