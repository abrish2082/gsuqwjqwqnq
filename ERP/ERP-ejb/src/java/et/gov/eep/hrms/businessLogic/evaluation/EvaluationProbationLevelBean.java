/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;


import et.gov.eep.hrms.entity.evaluation.HrEvalLevelProbations;
import et.gov.eep.hrms.mapper.evaluation.HrEvalLevelProbationsFacade;
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
public class EvaluationProbationLevelBean implements EvaluationProbationLevelBeanLocal {

    @Inject
    HrEvalLevelProbations hrEvalLevelProbations;
    @EJB
    HrEvalLevelProbationsFacade hrEvalLevelProbationsFacade;

    @Override
    public void save(HrEvalLevelProbations hrEvalLevelProbations) {
        hrEvalLevelProbationsFacade.create(hrEvalLevelProbations);
    }
    @Override
    public void edit(HrEvalLevelProbations HrEvalLevelProbations){
        hrEvalLevelProbationsFacade.edit(hrEvalLevelProbations);
    }

    @Override
    public List<HrEvalLevelProbations> findByLevelName(HrEvalLevelProbations hrEvalLevelProbations) {
        return hrEvalLevelProbationsFacade.findByLevelName(hrEvalLevelProbations);
    }

    @Override
    public List<HrEvalLevelProbations> findByLevelName() {
        return hrEvalLevelProbationsFacade.findAll();
    }

    @Override
    public void saveOrUpdate(HrEvalLevelProbations hrEvalLevel) {
        hrEvalLevelProbationsFacade.saveOrUpdate(hrEvalLevel);
    }

    @Override
    public List<HrEvalLevelProbations> findAll() {
        return hrEvalLevelProbationsFacade.findAll();
    }

    @Override
    public HrEvalLevelProbations findByLevelNameObj(HrEvalLevelProbations hrEvalLevelProbations) {
        return hrEvalLevelProbationsFacade.findByLevelNameObj(hrEvalLevelProbations);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Filter by Status ---"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load only active level"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load only inactive level"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all level"));
        return selectItems;

    }

    @Override
    public List<HrEvalLevelProbations> filteredEvaluationLevelProbation(int status) {
        return hrEvalLevelProbationsFacade.filteredEvaluationLevelProbation(status);
    }
    
    @Override
     public HrEvalLevelProbations getSelectedRequest(int level) {
        return hrEvalLevelProbationsFacade.getSelectedRequest(level);
     }

}
