/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;



import et.gov.eep.hrms.entity.payroll.FmsPayrollTransaction;
import et.gov.eep.hrms.mapper.payroll.Pay_Transaction_Facade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Musie
 */
@Stateless
public class Pay_Transaction_Logic {

    @EJB
    private Pay_Transaction_Facade transaction_Facade;

    /**
     *
     * @param payrollTransaction
     */
    public void saveOrUpdate(FmsPayrollTransaction payrollTransaction) {
        transaction_Facade.saveOrUpdate(payrollTransaction);
    }

    /**
     *
     * @return
     */
    public List<FmsPayrollTransaction> getListPayrollPreparerDate() {
        return transaction_Facade.getListPayrollPreparerDate();
    }

    /**
     *
     * @param empid
     * @param payrollcodedate
     * @return
     */
    public List<FmsPayrollTransaction> searchByEmpIDName(String empid, String payrollcodedate) {
        return transaction_Facade.searchByEmpIDName(empid, payrollcodedate);
    }

    /**
     *
     * @param paidDate
     * @return
     */
    public List<FmsPayrollTransaction> getListPayTraction(String paidDate) {
        return transaction_Facade.getListPayTraction(paidDate);
    }

    /**
     *
     * @param id
     * @return
     */
    public FmsPayrollTransaction find(Object id) {
        return transaction_Facade.find(id);
    }

    /**
     *
     * @param empId
     * @return
     */
    public FmsPayrollTransaction findPayTractionByEmpId(String empId) {
        return transaction_Facade.findPayTractionByEmpId(empId);
    }

    /**
     *
     * @return
     */
    public List findPayrollperiodDate() {
        return transaction_Facade.findPayrollperiodDate();
    }
    


}
