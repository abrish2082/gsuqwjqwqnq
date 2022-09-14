/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.overtime;

import et.gov.eep.hrms.entity.overtime.HrOtPayrollDetail;
import et.gov.eep.hrms.mapper.overtime.HrOtPayrollDetailFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class OT_Payroll_DetailBean implements OT_Payroll_DetailBeanLocal {
    @EJB
    HrOtPayrollDetailFacade hrOtPayrollDetailFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void saveorupdate(HrOtPayrollDetail hrOtPayrollDetail) {
       hrOtPayrollDetailFacade.create(hrOtPayrollDetail);
    }
}
