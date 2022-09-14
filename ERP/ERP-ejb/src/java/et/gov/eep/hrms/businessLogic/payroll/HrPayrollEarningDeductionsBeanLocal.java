/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollEarningDeductionsBeanLocal {

    public HrPayrollEarningDeductions find(Object id);

    public List<HrPayrollEarningDeductions> findAll();

    public List<HrPayrollEarningDeductions> findRange(int[] range);

    public List<HrPayrollEarningDeductions> loadListOfEarnings();

    public List<HrPayrollEarningDeductions> loadOnlyEarnings();

    public List<HrPayrollEarningDeductions> loadAllEar();

    public List<HrPayrollEarningDeductions> loadAllowancesForLocation();

    public List<HrPayrollEarningDeductions> loadEdForLocation();

    public List<HrPayrollEarningDeductions> loadEdForJObTitle();

    public List<HrPayrollEarningDeductions> listOfOtTypes();

    public List<HrPayrollEarningDeductions> listOfEarnngForAllEmp();

    public List<HrPayrollEarningDeductions> listOfDeductionsForAllEmp();

    public List<HrPayrollEarningDeductions> loadAllEmployeesEd();

    public List<HrPayrollEarningDeductions> loadListOfDeductions();

    public List<HrPayrollEarningDeductions> loadEarningAndDeductions();

    public List<HrPayrollEarningDeductions> loadOnlyDeductions();

    public List<HrPayrollEarningDeductions> loadAllEmpEarnings();

    public List<HrPayrollEarningDeductions> loadAllEmpDeductions();

    public String returnNumberOfMonths(String sDate, String eDate);

    public HrPayrollEarningDeductions cheakErningDeductionCriterias(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public HrPayrollEarningDeductions cheakItemCode(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public HrPayrollEarningDeductions cheakItemCodeForUpdate(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public HrPayrollEarningDeductions findCriteriaInfo(String criteria);

    int count();

    public List<HrPayrollEarningDeductions> taxTypeLists();

    public HrPayrollEarningDeductions getTaxType(HrPayrollEarningDeductions taxType);

    public HrPayrollEarningDeductions findByCode(HrPayrollEarningDeductions code);

    public void edit(HrPayrollEarningDeductions hrPayrollEarningDeductions);

    public void create(HrPayrollEarningDeductions hrPayrollEarningDeductions);
}
