/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.mapper.payroll.HrPayrollBackPymntsEdsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class BackPaymentFilterEaringDeductions implements BackPaymentFilterEaringDeductionsLocal {

    @EJB
    HrPayrollBackPymntsEdsFacade hrPayrollBackPymntsEdsFacade;

    @Override
    public void create(HrPayrollBackPymntsEds hrPayrollBackPymntsEds) {
        hrPayrollBackPymntsEdsFacade.create(hrPayrollBackPymntsEds);

    }
      @Override
    public void remove(HrPayrollBackPymntsEds hrPayrollBackPymntsEds) {
        hrPayrollBackPymntsEdsFacade.remove(hrPayrollBackPymntsEds);

    }

    @Override
    public List<HrPayrollBackPymntsEds> findAll() {
       
        return hrPayrollBackPymntsEdsFacade.findAll();

    }
    
    
    @Override
    public List<HrPayrollEarningDeductions> loadUnusedEd() {
        return hrPayrollBackPymntsEdsFacade.loadUnusedEd();

    }
     @Override
    public   boolean generateBackPayment(String payrollFrom, String payrollTo,String groupId){
        return hrPayrollBackPymntsEdsFacade.generateBackPayment(payrollFrom, payrollTo,groupId);
    }
   @Override
    public   boolean generateIndividualBkPayment(String earningDedCode,String payrollFrom, String payrollTo,String empId,String criterias){
        return hrPayrollBackPymntsEdsFacade.generateIndividualBkPayment(earningDedCode, payrollFrom,payrollTo,empId,criterias);
    }
    
       @Override
    public   boolean generateIndividualBkPaymentWithDays(String earningDedCode, String payrollFrom, String payrollTo, String empId, String criterias,String noDays,String totAmount){
        return hrPayrollBackPymntsEdsFacade.generateIndividualBkPaymentWithDays(earningDedCode, payrollFrom,payrollTo,empId,criterias,noDays,totAmount);
    }
    
     
    
    @Override
    public   boolean deleteIndBk(String empId) { 
        return hrPayrollBackPymntsEdsFacade.deleteIndBk(empId);
    }
     @Override
    public   boolean deleteGrBk() { 
        return hrPayrollBackPymntsEdsFacade.deleteGrBk();
    }

}
