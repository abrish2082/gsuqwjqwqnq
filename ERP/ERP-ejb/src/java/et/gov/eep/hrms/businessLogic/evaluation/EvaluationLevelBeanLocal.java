/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaluationLevels;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface EvaluationLevelBeanLocal {

    public void save(HrEvaluationLevels hrEvaluationLevels);

    public void edit(HrEvaluationLevels hrEvaluationLevels);

    public void saveOrUpdate(HrEvaluationLevels hrEvaluationLevels);

    public List<HrEvaluationLevels> findByEvaluationLevel();

    public HrEvaluationLevels getSelectedLevel(int level);

    public List<HrEvaluationLevels> findAll();
    
    public List<SelectItem> filterByStatus();

    public List<HrEvaluationLevels> filteredEvaluationLevel(int status);

}
