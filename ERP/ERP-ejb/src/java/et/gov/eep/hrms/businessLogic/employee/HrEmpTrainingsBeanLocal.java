/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import javax.ejb.Local;

/**
 *
 * @author Abdi_Pc
 */
@Local
public interface HrEmpTrainingsBeanLocal {

    public void creat(HrEmpTrainings hrEmpTrainings);
    
}
