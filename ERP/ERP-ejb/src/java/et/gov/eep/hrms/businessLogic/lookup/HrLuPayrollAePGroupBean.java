/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup;
import et.gov.eep.hrms.mapper.lookup.HrLuPayrollAePGroupFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrLuPayrollAePGroupBean implements HrLuPayrollAePGroupBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    HrLuPayrollAePGroupFacade hrLuPayrollAePGroupFacade;

    @Override
    public List<HrLuPayrollAePGroup> findAll() {
        return hrLuPayrollAePGroupFacade.findAll();
    }

}
