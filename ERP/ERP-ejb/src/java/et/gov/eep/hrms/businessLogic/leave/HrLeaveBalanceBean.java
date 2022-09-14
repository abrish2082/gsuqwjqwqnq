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
import et.gov.eep.hrms.mapper.leave.HrLeaveAccruedBalanceFacade;
import et.gov.eep.hrms.mapper.leave.HrLeaveBalanceFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author oneqaz
 */
@Stateless
public class HrLeaveBalanceBean implements HrLeaveBalanceBeanLocal {

    @EJB
    HrLeaveBalanceFacade facadeLocal;
    @EJB
    HrLeaveAccruedBalanceFacade hrLeaveAccruedBalanceFacade;

    @Override
    public void create(HrLeaveBalance hrLeaveBalance) {
        facadeLocal.create(hrLeaveBalance);
    }

    @Override
    public void saveOrUpdate(HrLeaveBalance hrLeaveBalance) {
        facadeLocal.saveOrUpdate(hrLeaveBalance);
    }

    @Override
    public void remove(HrLeaveBalance hrLeaveBalance) {
        facadeLocal.remove(hrLeaveBalance);
    }

    @Override
    public HrLeaveBalance find(Object id) {
        return facadeLocal.find(id);
    }

    @Override
    public List<HrLeaveBalance> findAll() {
        return facadeLocal.findAll();
    }

    @Override
    public List<HrLeaveBalance> findRange(int[] range) {
        return null;
    }

    @Override
    public ArrayList<Integer> populateDistnictBalance() {
        return facadeLocal.populateDistnictBalance();
    }

    @Override
    public ArrayList<HrLeaveBalance> populateLeaveBalance(HrEmployees employee, HrLuLeaveTypes hrLeaveTypes) {
        return facadeLocal.populateLeaveBalance(employee, hrLeaveTypes);
    }

    @Override
    public ArrayList<HrLeaveBalance> populateLeaveBalanceByLeaveType(HrLuLeaveTypes hrLeaveTypes, FmsLuBudgetYear budgetYear) {
        return facadeLocal.populateLeaveBalanceByLeaveType(hrLeaveTypes, budgetYear);
    }

    @Override
    public boolean checkLeaveBalanceExistance(FmsAccountingPeriod fap, HrLuLeaveTypes hrLeaveTypes) {
        return facadeLocal.checkLeaveBalanceExistance(fap, hrLeaveTypes);
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean checkLeaveBalanceExistance(FmsLuBudgetYear fmsLuBudgetYear, HrLuLeaveTypes hrLuLeaveTypes) {
        return facadeLocal.checkLeaveBalanceExistance(fmsLuBudgetYear, hrLuLeaveTypes);
    }

    @Override
    public HrLeaveBalance findEmployeeBalance(HrEmployees emp, FmsLuBudgetYear fmsLuBudgetYear, int i) {
           return facadeLocal.findEmployeeBalance(emp, fmsLuBudgetYear, i);}

    @Override
    public List<HrEmployees> findEmpALnotGenerated() {
   return facadeLocal.findEmpALnotGenerated();
    }

    @Override
    public List<HrLeaveAccruedBalance> findAccruedLeaveByLeaveYear(FmsLuBudgetYear fmsLuBudgetYear) {
        return hrLeaveAccruedBalanceFacade.findAccruedLeaveByLeaveYear(fmsLuBudgetYear);
    }

    @Override
    public void saveAccruedLeave(HrLeaveAccruedBalance hrLeaveAccruedBalance) {
        hrLeaveAccruedBalanceFacade.create(hrLeaveAccruedBalance);
    }

    @Override
    public HrLeaveAccruedBalance findByBudgetYear(String PrevY) {
       return hrLeaveAccruedBalanceFacade.findByBudgetYear(PrevY);
    }

    @Override
    public List<HrLeaveBalance> populateLeaveBalanceByLeaveTyp(FmsLuBudgetYear fmsLuBudgetYear) {
       return facadeLocal.populateLeaveBalanceByLeaveTyp(fmsLuBudgetYear);
    }
}
