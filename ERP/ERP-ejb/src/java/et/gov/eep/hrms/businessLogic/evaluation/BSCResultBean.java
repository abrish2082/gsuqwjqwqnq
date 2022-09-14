/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.entity.evaluation.HrBscResults;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import et.gov.eep.hrms.mapper.evaluation.HrBscResultsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author munir
 */
@Stateless
public class BSCResultBean implements BSCResultBeanLocal {

    @EJB
    HrBscResultsFacade hrBscResultsFacade;

    @Override
    public List<HrBscSessions> readActiveSession(String toDayInEC) {
        return hrBscResultsFacade.readActiveSession(toDayInEC);
    }

    @Override
    public HrBscSessions findById(HrBscSessions hrBscSessions) {
        return hrBscResultsFacade.findById(hrBscSessions);
    }

    @Override
    public HrBsc selectBSC(int department, int sessionId) {
        return hrBscResultsFacade.selectBSC(department, sessionId);
    }

    @Override
    public List<HrBscResults> readEmployees(int department, int sessionId, int searchCondition) {
        return hrBscResultsFacade.readEmployees(department, sessionId, searchCondition);
    }

}
