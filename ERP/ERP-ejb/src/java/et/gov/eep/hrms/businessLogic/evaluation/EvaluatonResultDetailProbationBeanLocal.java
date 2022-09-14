/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaResultDetailProbation;
import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface EvaluatonResultDetailProbationBeanLocal {

   public ArrayList<HrEvaResultDetailProbation> findAll();

     public List<HrEvaResultDetailProbation> fetchCriteria();

     public void edit(HrEvaResultDetailProbation ev);

    public void create(HrEvaResultDetailProbation ev);

    public List<HrEvalCriteriaProbations> findAllCriteria();
}
