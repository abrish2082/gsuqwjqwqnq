/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.lookup.HrLuEvaluationCategory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface EvaluationBeanLocal {

    public void save(HrEvaluationCriteria hrEvaluationCriteria);

    public void edit(HrEvaluationCriteria hrEvaluationCriteria);

    public void saveUpdate(HrEvaluationCriteria hrEvaluationCriteria);

    public List<HrLuEvaluationCategory> findAllEvaluationCategory();

    public HrLuEvaluationCategory findCategoryName(HrLuEvaluationCategory hrLuEvaluationCategory);

    public List<SelectItem> filterByStatus();

    public List<HrEvaluationCriteria> findAllCriteria();

    public List<HrEvaluationCriteria> filteredEvaluationCriteria(int status, int category);

    public HrEvaluationCriteria readEvaluationCriteriaDetail(int evalCriteriaId);

    public HrEvaluationCriteria checkCriteriaName(HrEvaluationCriteria hrEvaluationCriteria);

    public ArrayList<HrEvaluationCriteria> searchCriteriaName(HrEvaluationCriteria criteriaName);

    public List<HrEvaluationCriteria> findByName(String criteriaName);

    public boolean isCriteriaExist(HrEvaluationCriteria evaluationCriteria);

}
