/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaResultDetailProbation;
import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;
import et.gov.eep.hrms.mapper.evaluation.HrEvaResultDetailProbationFacade;
import et.gov.eep.hrms.mapper.evaluation.HrEvalCriteriaProbationsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ob
 */
@Stateless
public class EvaluatonResultDetailProbationBean implements EvaluatonResultDetailProbationBeanLocal {

    @EJB
    HrEvaResultDetailProbationFacade hrEvaResultDetailProbationFacade;
    @EJB
    HrEvalCriteriaProbationsFacade hrEvalCriteriaProbationsFacade;

    @Override
    public ArrayList<HrEvaResultDetailProbation> findAll() {
        return hrEvaResultDetailProbationFacade.findAll();
    }

    @Override
    public void edit(HrEvaResultDetailProbation ev) {
        hrEvaResultDetailProbationFacade.edit(ev);
    }

    @Override
    public void create(HrEvaResultDetailProbation ev) {
        hrEvaResultDetailProbationFacade.create(ev);
    }

    @Override
    public List<HrEvaResultDetailProbation> fetchCriteria() {
        return null;// hrEvaResultDetailProbationFacade.fetchCriteria();
    }

    @Override
    public List<HrEvalCriteriaProbations> findAllCriteria() {
        return hrEvalCriteriaProbationsFacade.findAll();
    }

}
