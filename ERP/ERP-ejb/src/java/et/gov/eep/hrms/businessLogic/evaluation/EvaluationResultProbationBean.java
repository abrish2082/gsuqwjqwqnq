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
import et.gov.eep.hrms.mapper.evaluation.HrEvalCriteriaProbationsFacade;
import et.gov.eep.hrms.mapper.evaluation.HrEvalResultProbationsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ob
 */
@Stateless
public class EvaluationResultProbationBean implements EvaluationResultProbationBeanLocal {

    @EJB
    HrEvalResultProbationsFacade hrEvalResultProbationsFacade;

    @EJB
    HrEvalCriteriaProbationsFacade hrEvalCriteriaProbationsFacade;

    @Inject
    HrEvalResultProbations hrEvalResultProbations;

    @Override
    public void save(HrEvalResultProbations hrEvalResultProbations) {
        hrEvalResultProbationsFacade.create(hrEvalResultProbations);
    }

    @Override
    public void edit(HrEvalResultProbations hrEvalResultProbations) {
        hrEvalResultProbationsFacade.edit(hrEvalResultProbations);
    }

    @Override
    public void saveOrUpdate(HrEvalResultProbations hrEvalResultProbations) {
        hrEvalResultProbationsFacade.saveOrUpdate(hrEvalResultProbations);
    }

    @Override
    public void create(HrEvalResultProbations hrEvalResultProbations) {
        hrEvalResultProbationsFacade.saveOrUpdate(hrEvalResultProbations);
    }

    @Override
    public ArrayList<HrEvalResultProbations> findAll() {
        return hrEvalResultProbationsFacade.findAll();
    }

    @Override
    public List<HrEvalResultProbations> findByName(HrEmployees hrEmployees) {
        return hrEvalResultProbationsFacade.findByName(hrEmployees);
    }

    @Override
    public HrEvalResultProbations getSelectedResult(int result) {
        return hrEvalResultProbationsFacade.getSelectedResult(result);
    }

    @Override
    public List<HrEvalCriteriaProbations> findAllCriteria() {
        return hrEvalCriteriaProbationsFacade.findAll();
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

    @Override
    public ArrayList<HrDepartments> findAllDepartment() {
        return hrEvalResultProbationsFacade.findAllDepartment();
    }

    @Override
    public ArrayList<HrEmployees> searchEmpName(HrEmployees hrEmployees) {
        return hrEvalResultProbationsFacade.searchEmpName(hrEmployees);
    }

    @Override
    public ArrayList<HrEvalResultProbations> findByEmpId(HrEmployees empId) {
        return hrEvalResultProbationsFacade.findByEmpId(empId);
    }

    @Override
    public ArrayList<HrEvalResultProbations> findByFName(HrEmployees empName) {
        return hrEvalResultProbationsFacade.findByFName(empName);
    }

    @Override
    public ArrayList<HrEvalResultProbations> findByEmpIdAndName(HrEmployees empId, HrEmployees empName) {
        return hrEvalResultProbationsFacade.findByEmpIdAndName(empId, empName);
    }

}
