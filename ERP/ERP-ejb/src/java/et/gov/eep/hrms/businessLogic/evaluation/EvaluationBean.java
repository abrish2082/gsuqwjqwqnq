/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.lookup.HrLuEvaluationCategory;
import et.gov.eep.hrms.mapper.evaluation.HrEvaluationCriteriaFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Stateless
public class EvaluationBean implements EvaluationBeanLocal {

    @EJB
    HrEvaluationCriteriaFacade hrEvaluationCriteriaFacade;

    @Override
    public void save(HrEvaluationCriteria hrEvaluationCriteria) {
        hrEvaluationCriteriaFacade.create(hrEvaluationCriteria);
    }

    @Override
    public void edit(HrEvaluationCriteria hrEvaluationCriteria) {
        hrEvaluationCriteriaFacade.edit(hrEvaluationCriteria);
    }

    @Override
    public void saveUpdate(HrEvaluationCriteria hrEvaluationCriteria) {
        hrEvaluationCriteriaFacade.saveOrUpdate(hrEvaluationCriteria);
    }

    @Override
    public List<HrLuEvaluationCategory> findAllEvaluationCategory() {
        return hrEvaluationCriteriaFacade.findAllEvaluationCategory();
    }

    @Override
    public HrLuEvaluationCategory findCategoryName(HrLuEvaluationCategory hrLuEvaluationCategory) {
        return hrEvaluationCriteriaFacade.findCategoryName(hrLuEvaluationCategory);
    }

    @Override
    public List<HrEvaluationCriteria> findAllCriteria() {
        return hrEvaluationCriteriaFacade.findAll();
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Filter by Status ---"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load only Active Criteria"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load only Inactive Criteria"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all Criteria"));
        return selectItems;
    }

    @Override
    public HrEvaluationCriteria readEvaluationCriteriaDetail(int evalCriteriaId) {
        return hrEvaluationCriteriaFacade.readEvaluationCriteriaDetail(evalCriteriaId);
    }

    @Override
    public HrEvaluationCriteria checkCriteriaName(HrEvaluationCriteria hrEvaluationCriteria) {
        return hrEvaluationCriteriaFacade.checkCriteriaName(hrEvaluationCriteria);
    }

    @Override
    public List<HrEvaluationCriteria> filteredEvaluationCriteria(int status, int category) {
        return hrEvaluationCriteriaFacade.filteredEvaluationCriteria(status, category);
    }

    @Override
    public ArrayList<HrEvaluationCriteria> searchCriteriaName(HrEvaluationCriteria criteriaName) {
        return hrEvaluationCriteriaFacade.searchCriteriaName(criteriaName);
    }

    @Override
    public List<HrEvaluationCriteria> findByName(String criteriaName) {
        return hrEvaluationCriteriaFacade.findByName(criteriaName);
    }

    @Override
    public boolean isCriteriaExist(HrEvaluationCriteria evaluationCriteria) {
        return hrEvaluationCriteriaFacade.isCriteriaExist(evaluationCriteria);
    }
}
