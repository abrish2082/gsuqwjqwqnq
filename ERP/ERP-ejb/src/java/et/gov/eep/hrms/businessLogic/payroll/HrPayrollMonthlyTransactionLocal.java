/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrPayrollMonthlyTransactionLocal {

    void create(HrPayrollMonTransactions hrPayrollMonTransactions);

    void edit(HrPayrollMonTransactions hrPayrollMonTransactions);

    void remove(HrPayrollMonTransactions hrPayrollMonTransactions);

    List<HrPayrollMonTransactions> findAll();

    public List<HrPayrollMonTransactions> loadSummery();

    public int finalizeLeaveAdvance(HrPayrollPeriods hrPayrollPeriods);

    public List<HrPayrollPeriods> returnLeaveAdvance(HrEmployees hrEmployees);

    public List<HrPayrollPeriods> usedPayrollDates();

    public List<HrEmployees> findPayedAmount(HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions ed);

    public List<HrPayrollMonTransactions> findPayedAmountAndPayDetail(HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions ed);

    public List<HrPayrollMonTransactions> findEarnings(HrPayrollPeriods hrPayrollPeriods, HrEmployees ed);

    public List<HrPayrollMonTransactions> fetchCourtOrderPayments();

    public List<HrPayrollMonTransactions> fetchCourtOrderPaymentsforCash();

    public HrPayrollMonTransactions getSelectedPayment(Long id);

    public List<HrPayrollMonTransactions> getPayrollMonthlyTrn(HrPayrollMonTransactions hrPayrollMonTransactions);

}
