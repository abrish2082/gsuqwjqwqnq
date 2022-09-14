/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.termination.HrEmployeeCompensation;
import et.gov.eep.hrms.mapper.termination.HrEmployeeCompensationFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class CompensationBean implements CompensationBeanLocal {
@EJB
HrEmployeeCompensationFacade hrEmployeeCompensationFacade;

    @Override
    public void save(HrEmployeeCompensation hrEmployeeCompensation) {
       hrEmployeeCompensationFacade.create(hrEmployeeCompensation);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public HrEmployeeCompensation findByEmpId(HrEmployees employee) {
        System.out.println("employee=  session bean=="+employee);
       return hrEmployeeCompensationFacade.findByEmpId(employee);
    }
}
