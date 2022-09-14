/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
import et.gov.eep.hrms.mapper.evaluation.HrEvalCriteriaProbationsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author Ob
 */
@Stateless
public class EvaluationProbationBean implements EvaluationProbationBeanLocal {

    @Inject
    HrEvalCriteriaProbations hrEvalCriteriaProbations;
    @EJB
    HrEvalCriteriaProbationsFacade hrEvalCriteriaProbationsFacade;

    @Override
    public void save(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        hrEvalCriteriaProbationsFacade.create(hrEvalCriteriaProbations);
    }

    @Override
    public void edit(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        hrEvalCriteriaProbationsFacade.edit(hrEvalCriteriaProbations);
    }

    @Override
    public List<HrEvalCriteriaProbations> findByCriteriaName(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        return hrEvalCriteriaProbationsFacade.findByCriteriaName(hrEvalCriteriaProbations);
    }

    @Override
    public List<HrEvalCriteriaProbations> findByCriteriaName() {
        return hrEvalCriteriaProbationsFacade.findAll();
    }

    @Override
    public void saveOrUpdate(HrEvalCriteriaProbations hrEvaluationCriteria) {
        hrEvalCriteriaProbationsFacade.saveOrUpdate(hrEvaluationCriteria);
    }

    @Override
    public List<HrEvalCriteriaProbations> findAll() {
        return hrEvalCriteriaProbationsFacade.findAll();
    }

    @Override
    public HrEvalCriteriaProbations findByCriteriaNameObj(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        return hrEvalCriteriaProbationsFacade.findByCriteriaNameObj(hrEvalCriteriaProbations);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Filter by Status ---"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load only active criteria"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load only inactive criteria"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all criteria"));
        return selectItems;

    }

    @Override
    public List<HrEvalCriteriaProbations> filteredEvaluationCriteriaProbation(int status) {
        return hrEvalCriteriaProbationsFacade.filteredEvaluationCriteriaProbation(status);
    }

    @Override
    public HrEvalCriteriaProbations getSelectedRequest(int criteria) {
        return hrEvalCriteriaProbationsFacade.getSelectedRequest(criteria);
    }

    @Override
    public List<HrEvalCriteriaProbations> findAllCriteria() {
        return hrEvalCriteriaProbationsFacade.findAll();
    }

    @Override
    public HrEvalCriteriaProbations findCriteriaByName(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        return hrEvalCriteriaProbationsFacade.findCriteriaByName(hrEvalCriteriaProbations);
    }

    @Override
    public ArrayList<HrEvalCriteriaProbations> searchCriteriaName(HrEvalCriteriaProbations criteriaName) {
        return hrEvalCriteriaProbationsFacade.searchCriteriaName(criteriaName);
    }
    
    @Override
    public boolean isCriteriaExist(HrEvalCriteriaProbations evaluationCriteria) {
        return hrEvalCriteriaProbationsFacade.isCriteriaExist(evaluationCriteria);
    }

    @Override
    public List<HrEvalCriteriaProbations> findByCriteria(String criteriaName) {
        return hrEvalCriteriaProbationsFacade.findByCriteria(criteriaName);
    }

}
