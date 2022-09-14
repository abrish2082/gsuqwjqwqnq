/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvalResultProbations;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface ApproveProbationBeanLocal {

    public void edit(HrEmployees hrEmployees);

    public void edit(HrEvalResultProbations hrEvalResultProbations);

    public List<HrEvalResultProbations> findByName(HrEmployees hrEmployees);

    public List<HrEvalResultProbations> findAllResult();

    public HrEvalResultProbations getSelectedResult(int result);

    public List<HrEvalResultProbations> findAllRecommendation();

    public List<HrEvalResultProbations> findAllEvaluationDecision(HrEvalResultProbations hrEvalResultProbations);

    public HrDepartments findByDepartmentId(HrDepartments hrDepartments);

    public List<HrEmployees> findEmployee(HrEmployees hrEmployees);

    public ArrayList<HrEvalResultProbations> findByDepName(HrDepartments hrDepartments);

    public List<HrEvalResultProbations> findPreparedList();

}
