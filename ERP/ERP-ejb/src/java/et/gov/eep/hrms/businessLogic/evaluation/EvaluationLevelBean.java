/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaluationLevels;
import et.gov.eep.hrms.mapper.evaluation.HrEvaluationLevelsFacade;
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
public class EvaluationLevelBean implements EvaluationLevelBeanLocal {

    @EJB
    HrEvaluationLevelsFacade hrEvaluationLevelsFacade;

    @Override
    public void save(HrEvaluationLevels hrEvaluationLevels) {
        hrEvaluationLevelsFacade.create(hrEvaluationLevels);
    }

    @Override
    public void edit(HrEvaluationLevels hrEvaluationLevels) {
        hrEvaluationLevelsFacade.edit(hrEvaluationLevels);
    }

    @Override
    public void saveOrUpdate(HrEvaluationLevels hrEvaluationLevels) {
        hrEvaluationLevelsFacade.saveOrUpdate(hrEvaluationLevels);
    }

    @Override
    public List<HrEvaluationLevels> findByEvaluationLevel() {
        return hrEvaluationLevelsFacade.findAll();
    }

    @Override
    public HrEvaluationLevels getSelectedLevel(int level) {
        return hrEvaluationLevelsFacade.getSelectedLevel(level);
    }

    @Override
    public List<HrEvaluationLevels> findAll() {
        return hrEvaluationLevelsFacade.findAll();
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select by status ---"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load only active criteria"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load only inactive criteria"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all criteria"));
        return selectItems;
    }

    @Override
    public List<HrEvaluationLevels> filteredEvaluationLevel(int status) {
        return hrEvaluationLevelsFacade.filteredEvaluationLevel(status);
    }

}
