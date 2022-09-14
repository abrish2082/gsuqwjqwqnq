/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.InternalExperience;

import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.hrms.mapper.transfer.HrEmpInternalHistoryFacade;



import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class InternalExperienceBean implements InternalExperienceBeanLocal {

    @EJB
    HrEmpInternalHistoryFacade hrEmpInternalHistoryFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void save(HrEmpInternalHistory hrEmpInternalHistory) {
        hrEmpInternalHistoryFacade.saveOrUpdate(hrEmpInternalHistory);
    }

    @Override
    public List<HrEmpInternalHistory> findByEmpId(HrEmpInternalHistory hrEmpInternalHistory, String empID) {
        return hrEmpInternalHistoryFacade.findByEmpId(hrEmpInternalHistory, empID);
    }

}
