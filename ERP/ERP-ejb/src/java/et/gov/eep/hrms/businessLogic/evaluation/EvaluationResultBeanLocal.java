/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationLevels;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface EvaluationResultBeanLocal {

    public void save(HrEvaluationResults hrEvaluationResult);

    public void edit(HrEvaluationResults hrEvaluationResult);

    public void saveorUpdate(HrEvaluationResults hrEvaluationResult);

    public List<String> searchByStatus(Integer result);

    public List<HrEvaluationCriteria> findAllCriteria();

    public ArrayList<HrEvaluationSessions> fetchSession(HrEvaluationSessions hrEvaluationSessions);

    public HrEvaluationCriteria findByCriteriaId(HrEvaluationCriteria hrEvaluationCriteria);

    public List<HrEvaluationResults> findByCriteriaName();

    public List<HrEvaluationLevels> findAllLevel();

    public HrEvaluationLevels findByLevelId(HrEvaluationLevels hrEvaluationLevels);

    public ArrayList<HrEvaluationResults> findAllResult();

    public ArrayList<HrEvaluationResults> findByEmpId(HrEmployees empId);

    public List<HrEvaluationResults> findByName(HrEmployees empName);

    public ArrayList<HrEvaluationResults> findByTwo(HrEmployees empId, HrEmployees empName);

    public HrEvaluationResults getSelectedResult(int result);

}
