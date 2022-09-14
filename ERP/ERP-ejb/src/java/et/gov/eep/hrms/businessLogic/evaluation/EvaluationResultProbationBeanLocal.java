/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
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
public interface EvaluationResultProbationBeanLocal {

    public void create(HrEvalResultProbations hrEvalResultProbations);

    public List<HrEvalResultProbations> findAll();

    public List<HrEvalResultProbations> findByName(HrEmployees hrEmployees);

    public List<HrEvalCriteriaProbations> findAllCriteria();

    public void save(HrEvalResultProbations hrEvalResultProbations);

    public void edit(HrEvalResultProbations hrEvalResultProbations);

    public HrEvalResultProbations getSelectedResult(int result);

    public void saveOrUpdate(HrEvalResultProbations hrEvalResultProbations);

    public HrDepartments findByDepartmentId(HrDepartments hrDepartments);

    public List<HrEmployees> findEmployee(HrEmployees hrEmployees);

    public ArrayList<HrEvalResultProbations> findByDepName(HrDepartments hrDepartments);

    public ArrayList<HrDepartments> findAllDepartment();

    public ArrayList<HrEmployees> searchEmpName(HrEmployees hrEmployees);

    public ArrayList<HrEvalResultProbations> findByEmpId(HrEmployees empId);

    public ArrayList<HrEvalResultProbations> findByFName(HrEmployees empName);

    public ArrayList<HrEvalResultProbations> findByEmpIdAndName(HrEmployees empId, HrEmployees empName);

}
