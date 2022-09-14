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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollAllEmpEdSetupsBeanLocal {

    public void create(HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups);

    public void edit(HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups);

    public void remove(HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups);

    public HrPayrollAllEmpEdSetups find(Object id);

    public List<HrPayrollAllEmpEdSetups> findAll();

    public List<HrPayrollAllEmpEdSetups> findRange(int[] range);

    public int count();

    public HrPayrollAllEmpEdSetups checkAllEmpEd(HrPayrollEarningDeductions ed, HrPayrollAllEmpEdSetups type);

    public boolean saveAllEmpEd(String activeDate, HrPayrollMaintainEds hrPayrollMaintainEds, HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public HrPayrollAllEmpEdSetups checkAllEmpEd(HrPayrollEarningDeductions ed);

    public boolean saveEarnings(HrPayrollPeriods payrollCode);

    public boolean saveTotalEarnings(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public boolean saveIndEarning(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees empId);

    public boolean saveTotIndEarning(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrEmployees empId);

    public boolean saveTotIndDeductions(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions, HrEmployees empId);

    public boolean saveIndSalary(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees empId, HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public boolean saveDeductions(HrPayrollPeriods payrollCode);

    public boolean saveTotalDed(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions
    );

    public boolean saveTotalTaxableEarnings(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public boolean saveTotalDedFirstMadeBeforeTax(HrPayrollPeriods payrollCode, HrPayrollEarningDeductions hrPayrollEarningDeductions
    );

    public boolean saveIndDeductions(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees emp
    );

    public boolean deleteMonTrans(HrPayrollPeriods payrollCode);

    public boolean deleteIndMonTrans(HrPayrollPeriods payrollCode, HrEmployees emp);

    public boolean saveMonthlySalary(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions grossSalaryCode);

    public boolean saveIndMonthlySalary(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions grossSalaryCode, int numberOfDays, double salary, HrEmployees emp);

    public boolean saveTaxAndPension(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions totTabaleEar, HrPayrollEarningDeductions penAddCode, HrPayrollEarningDeductions penDedCode
    );

    public boolean saveIndTaxAndPension(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions totTabaleEar, HrPayrollEarningDeductions penAddCode, HrPayrollEarningDeductions penDedCode, int numberOfDays, double salary, HrEmployees emp
    );

    public boolean saveTax(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions totTabaleEar
    );

    public boolean saveNetPay(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions netPaycode);

    public boolean saveIndNeyPay(String activeDate, HrPayrollPeriods hrPayrollPeriods, HrPayrollEarningDeductions netPaycode, int numberOfDays, double salary, HrEmployees emp);

    public boolean savePension(String activeDate, HrPayrollPeriods payrollCode, HrPayrollEarningDeductions pensionAddCode, HrPayrollEarningDeductions pensionDedCode);

    public List loadMonthlyPayroll(String payrollId);

    public List loadMonthlyPayrollWithSelectedEd(String payrollId, String listOFEd);

    List loadOneThirdSal(String payrollId);

    public List loadCourtCase(String payrollId);

    public List loadMortage(String payrollId);

    public List loadFamily(String payrollId);

    List loadEachEmployeesPaySlip(HrPayrollPeriods payrollId, HrEmployees empID);

    public List loadEarningDeductions(String payrollId, String edCode);

    public List<HrPayrollMonTransactions> loadEdSummery(String payrollId, String sqlQuery);

    public List<HrPayrollMonTransactions> loadEarnindDed(String payrollId);

    public List loadLeaveAdvancePyment(HrEmployees empId);

    public boolean saveAllowanceInJobLevel(String month, HrPayrollPeriods pp);

    public boolean saveIndAllowanceInJobLevel(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOFDays
    );

    public boolean saveIndAllowanceInJobLevel(String month, HrPayrollPeriods pp, int numberOfDays, double salary);

    public boolean saveAllowanceInJobTitle(String month, HrPayrollPeriods pp);

    public boolean saveIndAllowanceInJobTitle(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOfDays
    );

    public boolean saveIndAllowanceInJobTitle(String month, HrPayrollPeriods pp, int numberOfDays, double salary);

    public boolean saveAllowanceInJobLocation(String month, HrPayrollPeriods pp);

    public boolean saveIndAllowanceInJobLocation(String month, HrPayrollPeriods pp, HrEmployees emp, int numberOFDays
    );

    public boolean saveIndAllowanceInJobLocation(String month, HrPayrollPeriods pp, int numberOfDays, double salary);

    public boolean calcAndSavepymtRemMonht(String month, HrPayrollPeriods pp
    );

    public boolean savePayrollGeneratorsInfo(String date, int emp, String payrollId
    );

    public boolean calcIndAndSavepymtRemMonht(String month, HrPayrollPeriods pp, HrEmployees emp
    );

    public boolean calcIndAndSavepymtRemMonht(String month, HrPayrollPeriods pp, int numberOfDays, double salary
    );

    public boolean updatePayedEd(String month, HrPayrollPeriods pp);

    public boolean activateNextPayroll(String month, HrPayrollPeriods pp);

    public boolean generatePayrollSummery(String month, HrPayrollPeriods pp);

    public List<HrEmployees> loadEmployees();

    public List<HrEmployees> loadUngroupedEmployees(HrLuPayrollAePGroup hrLuPayrollAePGroup);

    public List<HrEmployees> loadUngroupedEmployeesUsingGrade(String gradeId);

    public List<HrEmployees> loadUngroupedEmployeesUsingPosition(String jobPosition);

//      public List<HrEmployees> loadGroupedEmployees(HrLuPayrollAePGroup hrLuPayrollAePGroup) ;
    public List<HrPayrollMaintainEds> loadGroupedEmployees(HrLuPayrollAePGroup hrLuPayrollAePGroup);

//      public List<HrPayrollMaintainBackPay> loadMantanedEmployees() ;
    public List<HrEmployees> loadMantanedEmployees(String groupId);

    public List<HrEmployees> loadSummery();

    public List<HrEmployees> listOfFilteredEmployees();

    public List<HrPayrollFilterBp> listOfEmpForBk();

//    public boolean saveLeaveAdvance(HrPayrollPeriods prevPayrollCode, HrEmployees hrEmployees, HrPayrollPeriods from, HrPayrollPeriods to, String beginingOfDed);
    public boolean saveLeaveAdvance(HrPayrollPeriods prevPayrollCode, HrEmployees hrEmployees,
            HrPayrollPeriods from, HrPayrollPeriods to, String beginingOfDed,
            HrPayrollEarningDeductions taxCode,
            HrPayrollEarningDeductions netPayCode,
            HrPayrollEarningDeductions penAddCode,
            HrPayrollEarningDeductions penDedCode,
            HrPayrollEarningDeductions grossSalCode,
            HrPayrollEarningDeductions totEarCode,
            HrPayrollEarningDeductions totalDedCode,
            HrPayrollEarningDeductions totTaxEarn,
            HrPayrollEarningDeductions leaveAdvCode);
}
