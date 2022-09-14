/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;


import et.gov.eep.hrms.mapper.payroll.GenerateReports;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Musie
 */
@Stateless
public class GenerateReport {

    @EJB
    private GenerateReports generateReports;

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection payrollReport(HashMap hashMap) {

        return generateReports.payrollReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection SummaryReport(HashMap hashMap) {
        return generateReports.SummaryReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportSalary(HashMap hashMap) {
        return generateReports.paySlipReportSalary(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportForAll(HashMap hashMap) {
        return generateReports.paySlipReportForAll(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySubSlipReportForAll(HashMap hashMap) {
        return generateReports.paySubSlipReportForAll(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportAllowance(HashMap hashMap) {
        return generateReports.paySlipReportAllowance(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportEarning(HashMap hashMap) {
        return generateReports.paySlipReportEarning(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportDeduction(HashMap hashMap) {
        return generateReports.paySlipReportDeduction(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportTest(HashMap hashMap) {
        return generateReports.paySlipReportTest(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection payrollBackReport(HashMap hashMap) {
        return generateReports.payrollBackReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeTaxReport(HashMap hashMap) {
        return generateReports.incomeTaxReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection pensionReport(HashMap hashMap) {
        return generateReports.pensionReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection serviceCompensationReport(HashMap hashMap) {
        return generateReports.serviceCompensationReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReport(HashMap hashMap) {
        return generateReports.incomeSubReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReportTow(HashMap hashMap) {
        return generateReports.incomeSubReportTow(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReportThri(HashMap hashMap) {
        return generateReports.incomeSubReportThri(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReportForth(HashMap hashMap) {
        return generateReports.incomeSubReportForth(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection delayedCollectionReport(HashMap hashMap) {
        return generateReports.delayedCollectionReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection representaionReport(HashMap hashMap) {
        return generateReports.representaionReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection settledAgainstReport(HashMap hashMap) {
        return generateReports.settledAgainstReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection halfCollectionReport(HashMap hashMap) {
        return generateReports.halfCollectionReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection bankReconcilationReport(HashMap hashMap) {
        return generateReports.bankReconcilationReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection detailOfOutstandingChequesReport(HashMap hashMap) {
        return generateReports.detailOfOutstandingChequesReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection detailOfOutstandingDepositReport(HashMap hashMap) {
        return generateReports.detailOfOutstandingDepositReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection payDedReport(HashMap hashMap) {
        return generateReports.payDedReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection unpaidSalaryReport(HashMap hashMap) {
        return generateReports.unpaidSalaryReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection stafLoan(HashMap hashMap) {
        return generateReports.stafLaonReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection jvSummeryReport(HashMap hashMap) {
        return generateReports.jvSummeryReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection jvSubReport(HashMap hashMap) {
        return generateReports.jvSubReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection btPayrollReport(HashMap hashMap) {
        return generateReports.btPayrollReport(hashMap);
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection btPayrollDeductionReport(HashMap hashMap) {
        return generateReports.btPayrollDeductionReport(hashMap);
    }
}
