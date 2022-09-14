/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPymntsEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.hrms.mapper.payroll.HrPayrollMonTransactionsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollMonthlyTransaction implements HrPayrollMonthlyTransactionLocal {

    @EJB
    HrPayrollMonTransactionsFacade hrPayrollMonTransactionsFacade;

    @Override
    public void create(HrPayrollMonTransactions hrPayrollMonTransactions) {
        hrPayrollMonTransactionsFacade.create(hrPayrollMonTransactions);

    }

    @Override
    public void edit(HrPayrollMonTransactions hrPayrollMonTransactions) {
        hrPayrollMonTransactionsFacade.edit(hrPayrollMonTransactions);

    }

    @Override
    public void remove(HrPayrollMonTransactions hrPayrollMonTransactions) {
        hrPayrollMonTransactionsFacade.remove(hrPayrollMonTransactions);

    }

    @Override
    public List<HrPayrollMonTransactions> findAll() {
        return hrPayrollMonTransactionsFacade.findAll();

    }

    @Override
    public int finalizeLeaveAdvance(HrPayrollPeriods hrPayrollPeriods) {
        return hrPayrollMonTransactionsFacade.finalizeLeaveAdvance(hrPayrollPeriods);

    }

    @Override
    public List<HrPayrollPeriods> returnLeaveAdvance(HrEmployees hrEmployees) {
        return hrPayrollMonTransactionsFacade.returnLeaveAdvance(hrEmployees);

    }

    @Override
    public List<HrPayrollMonTransactions> loadSummery() {
        return hrPayrollMonTransactionsFacade.loadSummery();

    }

    @Override
    public List<HrPayrollPeriods> usedPayrollDates() {
        return hrPayrollMonTransactionsFacade.usedPayrollDates();

    }

    @Override
    public List<HrEmployees> findPayedAmount(HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions ed) {
        return hrPayrollMonTransactionsFacade.findPayedAmount(hrPayrollPeriods, ed);

    }

    @Override
    public List<HrPayrollMonTransactions> findPayedAmountAndPayDetail(HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions ed) {
        return hrPayrollMonTransactionsFacade.findPayedAmountAndPayDetail(hrPayrollPeriods, ed);

    }

    @Override
    public List<HrPayrollMonTransactions> findEarnings(HrPayrollPeriods hrPayrollPeriods, HrEmployees ed) {
        return hrPayrollMonTransactionsFacade.findEarnings(hrPayrollPeriods, ed);

    }

    @Override
    public List<HrPayrollMonTransactions> fetchCourtOrderPayments() {
        return hrPayrollMonTransactionsFacade.fetchCourtOrderPayments();
    }

    @Override
    public HrPayrollMonTransactions getSelectedPayment(Long id) {
        return hrPayrollMonTransactionsFacade.getSelectedPayment(id);
    }

    @Override
    public List<HrPayrollMonTransactions> fetchCourtOrderPaymentsforCash() {
        return hrPayrollMonTransactionsFacade.fetchCourtOrderPaymentsforCash();
    }

    @Override
    public List<HrPayrollMonTransactions> getPayrollMonthlyTrn(HrPayrollMonTransactions hrPayrollMonTransactions) {
        return hrPayrollMonTransactionsFacade.getPayrollMonthlyTrn(hrPayrollMonTransactions);
    }

}
