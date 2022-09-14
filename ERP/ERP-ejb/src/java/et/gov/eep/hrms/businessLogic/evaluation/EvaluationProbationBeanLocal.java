/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ob
 */
@Local
public interface EvaluationProbationBeanLocal {

    public void save(HrEvalCriteriaProbations hrEvalCriteriaProbations);

    public void edit(HrEvalCriteriaProbations hrEvalCriteriaProbations);

    public List<HrEvalCriteriaProbations> findByCriteriaName(HrEvalCriteriaProbations hrEvalCriteriaProbations);

    public List<HrEvalCriteriaProbations> findByCriteriaName();

    public void saveOrUpdate(HrEvalCriteriaProbations hrEvaluationCriteria);

    public List<HrEvalCriteriaProbations> findAll();

    public HrEvalCriteriaProbations findByCriteriaNameObj(HrEvalCriteriaProbations hrEvalCriteriaProbations);

    public List<SelectItem> filterByStatus();

    public List<HrEvalCriteriaProbations> filteredEvaluationCriteriaProbation(int status);

    public HrEvalCriteriaProbations getSelectedRequest(int criteria);

    public List<HrEvalCriteriaProbations> findAllCriteria();

    public HrEvalCriteriaProbations findCriteriaByName(HrEvalCriteriaProbations hrEvalCriteriaProbations);

    public ArrayList<HrEvalCriteriaProbations> searchCriteriaName(HrEvalCriteriaProbations criteriaName);

    public boolean isCriteriaExist(HrEvalCriteriaProbations evaluationCriteria);

    public List<HrEvalCriteriaProbations> findByCriteria(String criteriaName);
}
