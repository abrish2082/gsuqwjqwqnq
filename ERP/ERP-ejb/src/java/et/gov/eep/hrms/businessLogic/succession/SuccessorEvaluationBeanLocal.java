/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author Behailu
 */
@Local
    public interface SuccessorEvaluationBeanLocal {

    public List<HrSmSuccessorEvaluation> findAll();

    public HrSmSuccessorEvaluation readkmpDetail(Integer id);

    public List<HrSmSuccessorEvaluation> findJobTitle(HrJobTypes hrJobTypes);

    public List<HrSmSuccessorEvaluation> findEmployeename(HrEmployees hrEmployees);

    public void save(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public void edit(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public List<HrSmSuccessorEvaluation> findbyposition(HrSmKmp hrSmKmp);

    public void saveorupdate(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public List<HrSmSuccessorEvaluation> findallEvaluatedsuccessors(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public List<HrSmSuccessorEvaluation> findJobTitleforApproval(HrJobTypes hrJobTypes);

    public List<HrSmSuccessorEvaluation> findEmployeenameforApproval(HrEmployees hrEmployees);

    public List<HrSmSuccessorEvaluation> findallToBeEvaluated(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public List<HrSmSuccessorEvaluation> findbypositionToEvaluate(HrSmKmp hrSmKmp);

    public List<SelectItem> filterByStatus();

    public List<HrSmSuccessorEvaluation> loadEvaluationList(int status);

  

   

  

   
   
    
}
