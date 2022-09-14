/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup;
import et.gov.eep.hrms.entity.payroll.HrPayrollAllEmpEdSetups;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.hrms.mapper.payroll.HrPayrollAllEmpEdSetupsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollAllEmpEdSetupsBean implements HrPayrollAllEmpEdSetupsBeanLocal {

    @EJB
    HrPayrollAllEmpEdSetupsFacade hrPayrollAllEmpEdSetupsFacade;

    @Override
    public void create(HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups) {
        hrPayrollAllEmpEdSetupsFacade.create(hrPayrollAllEmpEdSetups);
    }

    @Override
    public void edit(HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups) {
        hrPayrollAllEmpEdSetupsFacade.edit(hrPayrollAllEmpEdSetups);
    }

    @Override
    public void remove(HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups) {
        hrPayrollAllEmpEdSetupsFacade.remove(hrPayrollAllEmpEdSetups);
    }

    @Override
    public HrPayrollAllEmpEdSetups find(Object id) {
        return hrPayrollAllEmpEdSetupsFacade.find(id);
    }

    @Override
    public List<HrPayrollAllEmpEdSetups> findAll() {
        return hrPayrollAllEmpEdSetupsFacade.findAll();
    }

    @Override
    public List<HrPayrollAllEmpEdSetups> findRange(int[] range) {
        return hrPayrollAllEmpEdSetupsFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollAllEmpEdSetupsFacade.count();
    }

    @Override
    public HrPayrollAllEmpEdSetups checkAllEmpEd(HrPayrollEarningDeductions ed, HrPayrollAllEmpEdSetups type) {
        return hrPayrollAllEmpEdSetupsFacade.checkAllEmpEd(ed, type);
    }

    @Override
    public boolean saveAllEmpEd(String activeDate, HrPayrollMaintainEds hrPayrollMaintainEds, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollAllEmpEdSetupsFacade.saveAllEmpEd(activeDate, hrPayrollMaintainEds, hrPayrollEarningDeductions);
    }

    @Override
    public HrPayrollAllEmpEdSetups checkAllEmpEd(HrPayrollEarningDeductions ed) {
        return hrPayrollAllEmpEdSetupsFacade.checkAllEmpEd(ed);
    }

    @Override
    public boolean saveEarnings(HrPayrollPeriods payrollCode) {
        return hrPayrollAllEmpEdSetupsFacade.saveEarnings(payrollCode);
    }

    @Override
    public boolean saveTotalEarnings(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollAllEmpEdSetupsFacade.saveTotalEarnings(payrollCode, hrPayrollEarningDeductions);
    }

    @Override
    public boolean saveIndEarning(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees empId) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndEarning(payrollCode, numberOfDays, salary, empId);
    }

    @Override
    public boolean saveTotIndEarning(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrEmployees empId) {
        return hrPayrollAllEmpEdSetupsFacade.saveTotIndEarning(payrollCode, hrPayrollEarningDeductions, empId);
    }

    @Override
    public boolean saveTotIndDeductions(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrEmployees empId) {
       return hrPayrollAllEmpEdSetupsFacade.saveTotIndDeductions(payrollCode, hrPayrollEarningDeductions, empId);
    }

    @Override
    public boolean saveIndSalary(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees empId, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndSalary(payrollCode, numberOfDays, salary, empId, hrPayrollEarningDeductions);
    }

    @Override
    public boolean saveDeductions(HrPayrollPeriods payrollCode) {
       return hrPayrollAllEmpEdSetupsFacade.saveDeductions(payrollCode);
    }

    @Override
    public boolean saveTotalDed(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollAllEmpEdSetupsFacade.saveTotalDed(payrollCode, hrPayrollEarningDeductions);
    }

    @Override
    public boolean saveTotalTaxableEarnings(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollAllEmpEdSetupsFacade.saveTotalTaxableEarnings(payrollCode, hrPayrollEarningDeductions);
    }

    @Override
    public boolean saveTotalDedFirstMadeBeforeTax(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollAllEmpEdSetupsFacade.saveTotalTaxableEarnings(payrollCode, hrPayrollEarningDeductions);
    }

    @Override
    public boolean saveIndDeductions(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees emp) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndDeductions(payrollCode, numberOfDays, salary, emp);
    }

    @Override
    public boolean deleteMonTrans(HrPayrollPeriods payrollCode) {
        return hrPayrollAllEmpEdSetupsFacade.deleteMonTrans(payrollCode);
    }

    @Override
    public boolean deleteIndMonTrans(HrPayrollPeriods payrollCode, HrEmployees emp) {
        return hrPayrollAllEmpEdSetupsFacade.deleteIndMonTrans(payrollCode, emp);
    }

    @Override
    public boolean saveMonthlySalary(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions grossSalaryCode) {
        return hrPayrollAllEmpEdSetupsFacade.saveMonthlySalary(activeDate, payrollCode, grossSalaryCode);
    }

    @Override
    public boolean saveIndMonthlySalary(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions grossSalaryCode, int numberOfDays, double salary, HrEmployees emp) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndMonthlySalary(activeDate, payrollCode, grossSalaryCode, numberOfDays, salary, emp);
    }

    @Override
    public boolean saveTaxAndPension(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode, HrPayrollEarningDeductions totTabaleEar, HrPayrollEarningDeductions penAddCode, HrPayrollEarningDeductions penDedCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveIndTaxAndPension(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode, HrPayrollEarningDeductions totTabaleEar, HrPayrollEarningDeductions penAddCode, HrPayrollEarningDeductions penDedCode, int numberOfDays, double salary, HrEmployees emp) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndTaxAndPension(activeDate, hrPayrollPeriods, taxCode, totTabaleEar, penAddCode, penDedCode, numberOfDays, salary, emp);
    }

    @Override
    public boolean saveTax(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode, HrPayrollEarningDeductions totTabaleEar) {
        return hrPayrollAllEmpEdSetupsFacade.saveTax(activeDate, hrPayrollPeriods, taxCode, totTabaleEar);
    }

    @Override
    public boolean saveNetPay(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions netPaycode) {
        return hrPayrollAllEmpEdSetupsFacade.saveNetPay(activeDate, hrPayrollPeriods, netPaycode);
    }

    @Override
    public boolean saveIndNeyPay(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions netPaycode, int numberOfDays, double salary, HrEmployees emp) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndNeyPay(activeDate, hrPayrollPeriods, netPaycode, numberOfDays, salary, emp);
    }

    @Override
    public boolean savePension(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions pensionAddCode, HrPayrollEarningDeductions pensionDedCode) {
        return hrPayrollAllEmpEdSetupsFacade.savePension(activeDate, payrollCode, pensionAddCode, pensionDedCode);
    }

    @Override
    public List loadMonthlyPayroll(String payrollId) {
        return hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayroll(payrollId);
    }

    @Override
    public List loadMonthlyPayrollWithSelectedEd(String payrollId, String listOFEd) {
        return hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayrollWithSelectedEd(payrollId, listOFEd);
    }

    @Override
    public List loadOneThirdSal(String payrollId) {
        return hrPayrollAllEmpEdSetupsFacade.loadOneThirdSal(payrollId);
    }

    @Override
    public List loadCourtCase(String payrollId) {
        return hrPayrollAllEmpEdSetupsFacade.loadCourtCase(payrollId);
    }

    @Override
    public List loadMortage(String payrollId) {
        return hrPayrollAllEmpEdSetupsFacade.loadMortage(payrollId);
    }

    @Override
    public List loadFamily(String payrollId) {
        return hrPayrollAllEmpEdSetupsFacade.loadFamily(payrollId);
    }

    @Override
    public List loadEachEmployeesPaySlip(HrPayrollPeriods payrollId, HrEmployees empID) {
        return hrPayrollAllEmpEdSetupsFacade.loadEachEmployeesPaySlip(payrollId, empID);
    }

    @Override
    public List loadEarningDeductions(String payrollId, String edCode) {
        return hrPayrollAllEmpEdSetupsFacade.loadEarningDeductions(payrollId, edCode);
    }

    @Override
    public List<HrPayrollMonTransactions> loadEdSummery(String payrollId, String sqlQuery) {
        return hrPayrollAllEmpEdSetupsFacade.loadEdSummery(payrollId, sqlQuery);
    }

    @Override
    public List<HrPayrollMonTransactions> loadEarnindDed(String payrollId) {
        return hrPayrollAllEmpEdSetupsFacade.loadEarnindDed(payrollId);
    }

    @Override
    public List loadLeaveAdvancePyment(HrEmployees empId) {
        return hrPayrollAllEmpEdSetupsFacade.loadLeaveAdvancePyment(empId);
    }

    @Override
    public boolean saveAllowanceInJobLevel(String month, HrPayrollPeriods pp) {
        return hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobLevel(month, pp);
    }

    @Override
    public boolean saveIndAllowanceInJobLevel(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOFDays) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobLevel(month, pp, emp, numberOFDays);
    }

    @Override
    public boolean saveIndAllowanceInJobLevel(String month, HrPayrollPeriods pp, int numberOfDays, double salary) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobLevel(month, pp, numberOfDays, salary);
    }

    @Override
    public boolean saveAllowanceInJobTitle(String month, HrPayrollPeriods pp) {
        return hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobTitle(month, pp);
    }

    @Override
    public boolean saveIndAllowanceInJobTitle(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOfDays) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobTitle(month, pp, emp, numberOfDays);
    }

    @Override
    public boolean saveIndAllowanceInJobTitle(String month, HrPayrollPeriods pp, int numberOfDays, double salary) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobLevel(month, pp, numberOfDays, salary);
    }

    @Override
    public boolean saveAllowanceInJobLocation(String month, HrPayrollPeriods pp) {
        return hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobLocation(month, pp);
    }

    @Override
    public boolean saveIndAllowanceInJobLocation(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOFDays) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobLocation(month, pp, emp, numberOFDays);
    }

    @Override
    public boolean saveIndAllowanceInJobLocation(String month, HrPayrollPeriods pp, int numberOfDays, double salary) {
        return hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobLevel(month, pp, numberOfDays, salary);
    }

    @Override
    public boolean calcAndSavepymtRemMonht(String month, HrPayrollPeriods pp) {
        return hrPayrollAllEmpEdSetupsFacade.calcAndSavepymtRemMonht(month, pp);
    }

    @Override
    public boolean savePayrollGeneratorsInfo(String date, int emp, String payrollId) {
        return hrPayrollAllEmpEdSetupsFacade.savePayrollGeneratorsInfo(date, emp, payrollId);
    }

    @Override
    public boolean calcIndAndSavepymtRemMonht(String month, HrPayrollPeriods pp, HrEmployees emp) {
        return hrPayrollAllEmpEdSetupsFacade.calcIndAndSavepymtRemMonht(month, pp, emp);
    }

    @Override
    public boolean calcIndAndSavepymtRemMonht(String month, HrPayrollPeriods pp, int numberOfDays, double salary) {
        return hrPayrollAllEmpEdSetupsFacade.calcIndAndSavepymtRemMonht(month, pp, numberOfDays, salary);
    }

    @Override
    public boolean updatePayedEd(String month, HrPayrollPeriods pp) {
        return hrPayrollAllEmpEdSetupsFacade.updatePayedEd(month, pp);
    }

    @Override
    public boolean activateNextPayroll(String month, HrPayrollPeriods pp) {
        return hrPayrollAllEmpEdSetupsFacade.activateNextPayroll(month, pp);
    }

    @Override
    public boolean generatePayrollSummery(String month, HrPayrollPeriods pp) {
        return hrPayrollAllEmpEdSetupsFacade.generatePayrollSummery(month, pp);
    }

    @Override
    public List<HrEmployees> loadEmployees() {
        return hrPayrollAllEmpEdSetupsFacade.loadEmployees();
    }

    @Override
    public List<HrEmployees> loadUngroupedEmployees(HrLuPayrollAePGroup hrLuPayrollAePGroup) {
        return hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployees(hrLuPayrollAePGroup);
    }

    @Override
    public List<HrEmployees> loadUngroupedEmployeesUsingGrade(String gradeId) {
        return hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployeesUsingGrade(gradeId);
    }

    @Override
    public List<HrEmployees> loadUngroupedEmployeesUsingPosition(String jobPosition) {
        return hrPayrollAllEmpEdSetupsFacade.loadUngroupedEmployeesUsingPosition(jobPosition);
    }

    @Override
    public List<HrPayrollMaintainEds> loadGroupedEmployees(HrLuPayrollAePGroup hrLuPayrollAePGroup) {
        return hrPayrollAllEmpEdSetupsFacade.loadGroupedEmployees(hrLuPayrollAePGroup);
    }

    @Override
    public List<HrEmployees> loadMantanedEmployees(String groupId) {
        return hrPayrollAllEmpEdSetupsFacade.loadMantanedEmployees(groupId);
    }

    @Override
    public List<HrEmployees> loadSummery() {
        return hrPayrollAllEmpEdSetupsFacade.loadSummery();
    }

    @Override
    public List<HrEmployees> listOfFilteredEmployees() {
        return hrPayrollAllEmpEdSetupsFacade.listOfFilteredEmployees();
    }

    @Override
    public List<HrPayrollFilterBp> listOfEmpForBk() {
        return hrPayrollAllEmpEdSetupsFacade.listOfEmpForBk();
    }

    @Override
    public boolean saveLeaveAdvance(HrPayrollPeriods prevPayrollCode, HrEmployees hrEmployees, HrPayrollPeriods from, HrPayrollPeriods to, String beginingOfDed, HrPayrollEarningDeductions taxCode, HrPayrollEarningDeductions netPayCode, HrPayrollEarningDeductions penAddCode, HrPayrollEarningDeductions penDedCode, HrPayrollEarningDeductions grossSalCode, HrPayrollEarningDeductions totEarCode, HrPayrollEarningDeductions totalDedCode, HrPayrollEarningDeductions totTaxEarn, HrPayrollEarningDeductions leaveAdvCode) {
        return hrPayrollAllEmpEdSetupsFacade.saveLeaveAdvance(prevPayrollCode, hrEmployees, from, to, beginingOfDed, taxCode, netPayCode, penAddCode, penDedCode, grossSalCode, totEarCode, totalDedCode, totTaxEarn, leaveAdvCode);
    }

}
