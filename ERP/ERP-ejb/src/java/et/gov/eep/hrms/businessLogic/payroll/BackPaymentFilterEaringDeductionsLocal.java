/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BackPaymentFilterEaringDeductionsLocal {

    void create(HrPayrollBackPymntsEds hrPayrollBackPymntsEds);

    void remove(HrPayrollBackPymntsEds hrPayrollBackPymntsEds);

    List<HrPayrollBackPymntsEds> findAll();

    List<HrPayrollEarningDeductions> loadUnusedEd();

    public boolean generateBackPayment(String payrollFrom, String payrollTo,String groupId);

    public boolean generateIndividualBkPayment(String earningDedCode, String payrollFrom, String payrollTo, String empId, String criterias);
 public boolean generateIndividualBkPaymentWithDays(String earningDedCode, String payrollFrom, String payrollTo, String empId, String criterias,String noDays,String totAmount);

    public boolean deleteIndBk(String empId);

    public boolean deleteGrBk();
}
