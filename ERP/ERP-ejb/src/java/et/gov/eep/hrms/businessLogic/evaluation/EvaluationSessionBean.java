/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
import et.gov.eep.hrms.mapper.evaluation.HrEvaluationSessionsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class EvaluationSessionBean implements EvaluationSessionBeanLocal {

    @EJB
    HrEvaluationSessionsFacade hrEvaluationSessionsFacade;

    @Override
    public void save(HrEvaluationSessions hrEvaluationSessions) {
        hrEvaluationSessionsFacade.create(hrEvaluationSessions);
    }

    @Override
    public void edit(HrEvaluationSessions hrEvaluationSessions) {
        hrEvaluationSessionsFacade.edit(hrEvaluationSessions);
    }

    @Override
    public List<HrEvaluationSessions> readActiveSession(String toDayInEC) {
        return hrEvaluationSessionsFacade.readActiveSession(toDayInEC);
    }

    @Override
    public HrEvaluationSessions readEvaluationSessionDetail(int evaSessionId) {
        return hrEvaluationSessionsFacade.readEvaluationSessionDetail(evaSessionId);
    }

    @Override
    public List<HrEvaluationSessions> filteredEvaluationSession(int year, int searchCondition) {
        return hrEvaluationSessionsFacade.filteredEvaluationSession(year, searchCondition);
    }

    @Override
    public List<HrEvaluationSessions> findByYear(int year) {
        return hrEvaluationSessionsFacade.findByYear(year);
    }

    @Override
    public HrEvaluationSessions findById(HrEvaluationSessions hrEvaluationSessions) {
        return hrEvaluationSessionsFacade.findById(hrEvaluationSessions);
    }

    @Override
    public void saveOrUpdate(HrEvaluationSessions hrEvaluationSessions) {
        hrEvaluationSessionsFacade.saveOrUpdate(hrEvaluationSessions);
    }

    @Override
    public boolean isExist(HrEvaluationSessions evaSession) {
        return hrEvaluationSessionsFacade.isExist(evaSession);
    }

}
