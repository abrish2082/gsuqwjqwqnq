/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface BscSessionBeanLocal {

    public void save(HrBscSessions hrBscSessions);

    public void edit(HrBscSessions hrBscSessions);

    public HrBscSessions getSelectedRequest(int session);

    public List<HrBscSessions> findByYear(int year);

    public List<HrBscSessions> findAllBscSession();

    public List<HrBscSessions> filteredBscSession(int year, int searchCondition);

}
