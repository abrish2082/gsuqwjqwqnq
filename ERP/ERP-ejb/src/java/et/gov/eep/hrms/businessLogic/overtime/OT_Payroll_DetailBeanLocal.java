/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.overtime;

import et.gov.eep.hrms.entity.overtime.HrOtPayrollDetail;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface OT_Payroll_DetailBeanLocal {

    public void saveorupdate(HrOtPayrollDetail hrOtPayrollDetail);
    
}
