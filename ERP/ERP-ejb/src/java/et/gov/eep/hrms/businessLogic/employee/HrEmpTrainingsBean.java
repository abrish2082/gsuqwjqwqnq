/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import et.gov.eep.hrms.mapper.employee.HrEmpTrainingsFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi_Pc
 */
@Stateless
public class HrEmpTrainingsBean implements HrEmpTrainingsBeanLocal {

    @EJB
    HrEmpTrainingsFacade  hrEmpTrainingsFacade;
    @Override
    public void creat(HrEmpTrainings hrEmpTrainings) {
        hrEmpTrainingsFacade.saveOrUpdate(hrEmpTrainings);
    }

}
