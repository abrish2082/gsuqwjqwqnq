/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

//import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
import et.gov.eep.hrms.entity.evaluation.HrEvalLevelProbations;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ob
 */
@Local
public interface EvaluationProbationLevelBeanLocal {
    
    public void save(HrEvalLevelProbations hrEvalLevelProbations);
    
    public void edit(HrEvalLevelProbations hrEvalLevelProbations);

    public List<HrEvalLevelProbations> findByLevelName(HrEvalLevelProbations hrEvalLevelProbations);

    public List<HrEvalLevelProbations> findByLevelName();

    public void saveOrUpdate(HrEvalLevelProbations hrEvaluationLevelProbations);

    public List<HrEvalLevelProbations> findAll();

    public HrEvalLevelProbations findByLevelNameObj(HrEvalLevelProbations hrEvalLevelProbations);

    public List<SelectItem> filterByStatus();

    public List<HrEvalLevelProbations> filteredEvaluationLevelProbation(int status);

    public HrEvalLevelProbations getSelectedRequest(int level);
    
}
