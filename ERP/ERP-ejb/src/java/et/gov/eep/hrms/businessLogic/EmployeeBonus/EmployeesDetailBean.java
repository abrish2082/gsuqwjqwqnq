/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.EmployeeBonus;

import et.gov.eep.hrms.mapper.EmployeeBonus.HrEmployeesBonusDetailFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class EmployeesDetailBean implements EmployeesDetailBeanLocal {
    @EJB
    HrEmployeesBonusDetailFacade hrEmployeesBonusDetailFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
