/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.entity.evaluation.HrBscResults;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface BSCResultBeanLocal {

    public List<HrBscSessions> readActiveSession(String toDayInEC);

    public HrBscSessions findById(HrBscSessions hrBscSessions);

    public HrBsc selectBSC(int department, int sessionId);

    public List<HrBscResults> readEmployees(int department, int sessionId, int searchCondition);

}
