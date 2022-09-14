/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.mapper.employee.HrEmpFamiliesFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Benin
 */
@Stateless
public class HrEmpFamilyBean implements HrEmpFamilyBeanLocal {

    @EJB
    HrEmpFamiliesFacade hrEmpFamiliesFacade;
    
    @Override
    public void delete(HrEmpFamilies hrEmpFamilies){
        hrEmpFamiliesFacade.remove(hrEmpFamilies);
    }
}
