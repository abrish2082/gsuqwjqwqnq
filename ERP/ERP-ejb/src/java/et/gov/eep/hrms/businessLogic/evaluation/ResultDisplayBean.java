/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.mapper.evaluation.HrEvaluationResultsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class ResultDisplayBean implements ResultDisplayBeanLocal {

    @EJB
    HrEvaluationResultsFacade hrEvaluationResultFacade;

    @Override
    public ArrayList<HrEvaluationResults> findAllResult() {
        return hrEvaluationResultFacade.findAllResult();
    }

    @Override
    public ArrayList<HrEvaluationResults> findByEmpId(HrEmployees empId) {
        return hrEvaluationResultFacade.findByEmpId(empId);
    }

    @Override
    public List<HrEvaluationResults> findByName(HrEmployees empName) {
        return hrEvaluationResultFacade.findByName(empName);
    }

    @Override
    public ArrayList<HrEvaluationResults> findByTwo(HrEmployees empId, HrEmployees empName) {
        return hrEvaluationResultFacade.findByTwo(empId, empName);
    }

    @Override
    public HrEvaluationResults getSelectedResult(int result) {
        return hrEvaluationResultFacade.getSelectedResult(result);
    }

}
