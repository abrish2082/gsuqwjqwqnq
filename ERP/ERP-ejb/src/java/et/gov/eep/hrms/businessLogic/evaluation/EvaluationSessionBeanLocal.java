/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface EvaluationSessionBeanLocal {

    public List<HrEvaluationSessions> readActiveSession(String toDayInEC);

    public HrEvaluationSessions readEvaluationSessionDetail(int evaSessionId);

    public List<HrEvaluationSessions> findByYear(int year);

    public HrEvaluationSessions findById(HrEvaluationSessions hrEvaluationSessions);

    public void save(HrEvaluationSessions hrEvaluationSessions);

    public void edit(HrEvaluationSessions hrEvaluationSessions);

    public void saveOrUpdate(HrEvaluationSessions hrEvaluationSessions);

    public List<HrEvaluationSessions> filteredEvaluationSession(int year, int searchCondition);

    public boolean isExist(HrEvaluationSessions evaSession);

}
