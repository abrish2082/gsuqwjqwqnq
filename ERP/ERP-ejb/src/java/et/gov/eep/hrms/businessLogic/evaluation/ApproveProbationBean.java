/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvalResultProbations;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.hrms.mapper.evaluation.HrEvalResultProbationsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ob
 */
@Stateless
public class ApproveProbationBean implements ApproveProbationBeanLocal {

    @EJB
    HrEvalResultProbationsFacade hrEvalResultProbationsFacade;
    @EJB
    HrEmployeesFacade hrEmployeesFacade;

    @Override
    public void edit(HrEvalResultProbations hrEvalResultProbations) {
        hrEvalResultProbationsFacade.edit(hrEvalResultProbations);
    }

    @Override
    public void edit(HrEmployees hrEmployees) {
        hrEmployeesFacade.edit(hrEmployees);
    }

    @Override
    public List<HrEvalResultProbations> findByName(HrEmployees hrEmployees) {
        return hrEvalResultProbationsFacade.findByName(hrEmployees);
    }

    @Override
    public List<HrEvalResultProbations> findAllResult() {
        return hrEvalResultProbationsFacade.findAll();
    }

    @Override
    public HrEvalResultProbations getSelectedResult(int result) {
        return hrEvalResultProbationsFacade.getSelectedResult(result);
    }

    @Override
    public List<HrEvalResultProbations> findAllRecommendation() {
        return hrEvalResultProbationsFacade.findAllRecommendation();
    }

    @Override
    public List<HrEvalResultProbations> findAllEvaluationDecision(HrEvalResultProbations hrEvalResultProbations) {
        return hrEvalResultProbationsFacade.findAllEvaluationDecision(hrEvalResultProbations);
    }

    @Override
    public HrDepartments findByDepartmentId(HrDepartments hrDepartments) {
        return hrEvalResultProbationsFacade.findByDepartmentId(hrDepartments);
    }

    @Override
    public List<HrEmployees> findEmployee(HrEmployees hrEmployees) {
        return hrEvalResultProbationsFacade.findEmployee(hrEmployees);
    }

    @Override
    public ArrayList<HrEvalResultProbations> findByDepName(HrDepartments hrDepartments) {
        return hrEvalResultProbationsFacade.findByDepName(hrDepartments);
    }

    public List<HrEvalResultProbations> findPreparedList() {
        return hrEvalResultProbationsFacade.findPreparedList();
}

}
