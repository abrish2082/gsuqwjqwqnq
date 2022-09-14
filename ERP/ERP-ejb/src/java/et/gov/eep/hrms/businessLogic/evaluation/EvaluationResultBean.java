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
public class EvaluationResultBean implements EvaluationResultBeanLocal {

    @EJB
    HrEvaluationResultsFacade hrEvaluationResultFacade;

    @Override
    public void save(HrEvaluationResults hrEvaluationResult) {
        hrEvaluationResultFacade.create(hrEvaluationResult);
    }

    @Override
    public void edit(HrEvaluationResults hrEvaluationResult) {
        hrEvaluationResultFacade.edit(hrEvaluationResult);
    }

    @Override
    public void saveorUpdate(HrEvaluationResults hrEvaluationResult) {
        hrEvaluationResultFacade.saveOrUpdate(hrEvaluationResult);
    }

    @Override
    public List<String> searchByStatus(Integer result) {
        return hrEvaluationResultFacade.searchByStatus(result);
    }

    @Override
    public List<HrEvaluationCriteria> findAllCriteria() {
        return hrEvaluationResultFacade.findAllCriteria();
    }

    @Override
    public List<HrEvaluationLevels> findAllLevel() {
        return hrEvaluationResultFacade.findAllLevel();
    }

    @Override
    public ArrayList<HrEvaluationSessions> fetchSession(HrEvaluationSessions hrEvaluationSessions) {
        return hrEvaluationResultFacade.fetchSession(hrEvaluationSessions);
    }

    @Override
    public HrEvaluationCriteria findByCriteriaId(HrEvaluationCriteria hrEvaluationCriteria) {
        return hrEvaluationResultFacade.findByCriteriaId(hrEvaluationCriteria);
    }

    @Override
    public HrEvaluationLevels findByLevelId(HrEvaluationLevels hrEvaluationLevels) {
        return hrEvaluationResultFacade.findByLevelId(hrEvaluationLevels);
    }

    @Override
    public List<HrEvaluationResults> findByCriteriaName() {
        return hrEvaluationResultFacade.findAll();
    }

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
