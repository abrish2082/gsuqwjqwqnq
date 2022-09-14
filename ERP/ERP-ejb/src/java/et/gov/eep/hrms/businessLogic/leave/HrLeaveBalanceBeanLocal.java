/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;


import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveAccruedBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author oneqaz
 */
@Local
public interface HrLeaveBalanceBeanLocal {

    public void create(HrLeaveBalance hrLeaveBalance);

    public void saveOrUpdate(HrLeaveBalance hrLeaveBalance);

    public void remove(HrLeaveBalance hrLeaveBalance);

    public HrLeaveBalance find(Object id);

    public List<HrLeaveBalance> findAll();

    public List<HrLeaveBalance> findRange(int[] range);

    public int count();

    public ArrayList<HrLeaveBalance> populateLeaveBalance(HrEmployees employee, HrLuLeaveTypes hrLeaveTypes);

    public boolean checkLeaveBalanceExistance(FmsAccountingPeriod fap, HrLuLeaveTypes hrLeaveTypes);

    public ArrayList<HrLeaveBalance> populateLeaveBalanceByLeaveType(HrLuLeaveTypes hrLeaveTypes, FmsLuBudgetYear budgetYear);

    public ArrayList<Integer> populateDistnictBalance();

    public boolean checkLeaveBalanceExistance(FmsLuBudgetYear fmsLuBudgetYear, HrLuLeaveTypes hrLuLeaveTypes);

    public HrLeaveBalance findEmployeeBalance(HrEmployees emp, FmsLuBudgetYear fmsLuBudgetYear, int i);

    public List<HrEmployees> findEmpALnotGenerated();

    public List<HrLeaveAccruedBalance> findAccruedLeaveByLeaveYear(FmsLuBudgetYear fmsLuBudgetYear);

    public void saveAccruedLeave(HrLeaveAccruedBalance hrLeaveAccruedBalance);

    public HrLeaveAccruedBalance findByBudgetYear(String PrevY);

    public List<HrLeaveBalance> populateLeaveBalanceByLeaveTyp(FmsLuBudgetYear fmsLuBudgetYear);

    

    
    
}
