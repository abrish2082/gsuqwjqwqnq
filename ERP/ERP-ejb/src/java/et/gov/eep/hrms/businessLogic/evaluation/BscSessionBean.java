/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.evaluation;

import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import et.gov.eep.hrms.mapper.evaluation.HrBscSessionsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class BscSessionBean implements BscSessionBeanLocal {

    @EJB
    HrBscSessionsFacade hrBscSessionsFacade;

    @Override
    public void save(HrBscSessions hrBscSessions) {
        hrBscSessionsFacade.create(hrBscSessions);
    }

    @Override
    public void edit(HrBscSessions hrBscSessions) {
        hrBscSessionsFacade.edit(hrBscSessions);
    }

    @Override
    public HrBscSessions getSelectedRequest(int session) {
        return hrBscSessionsFacade.getSelectedRequest(session);
    }

    @Override
    public List<HrBscSessions> findByYear(int year) {
        return hrBscSessionsFacade.findByYear(year);
    }

    @Override
    public List<HrBscSessions> findAllBscSession() {
        return hrBscSessionsFacade.findAll();
    }
    
    @Override
     public List<HrBscSessions> filteredBscSession(int year, int searchCondition) {
         return hrBscSessionsFacade.filteredBscSession(year, searchCondition);
     }

}
