/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.termination.HrEmployeeCompensation;
import javax.ejb.Local;

/**
 *
 * @author User
 */
@Local
public interface CompensationBeanLocal {

    public void save(HrEmployeeCompensation hrEmployeeCompensation);

    public HrEmployeeCompensation findByEmpId(HrEmployees employee);
    
}
