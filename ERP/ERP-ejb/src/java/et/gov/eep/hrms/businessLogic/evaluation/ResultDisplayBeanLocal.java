/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface ResultDisplayBeanLocal {

    public ArrayList<HrEvaluationResults> findAllResult();

    public ArrayList<HrEvaluationResults> findByEmpId(HrEmployees empId);

    public List<HrEvaluationResults> findByName(HrEmployees empName);

    public ArrayList<HrEvaluationResults> findByTwo(HrEmployees empId, HrEmployees empName);

    public HrEvaluationResults getSelectedResult(int result);

}
