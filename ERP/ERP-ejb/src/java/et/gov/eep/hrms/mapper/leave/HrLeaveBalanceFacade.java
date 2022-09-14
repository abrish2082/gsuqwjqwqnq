/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author prg
 */
@Stateless
public class HrLeaveBalanceFacade extends AbstractFacade<HrLeaveBalance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLeaveBalanceFacade() {
        super(HrLeaveBalance.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public ArrayList<Integer> populateDistnictBalance() {
        Query query = em.createNamedQuery("HrLeaveBalance.populateDistnictBalance");
        try {
            ArrayList<Integer> balancess = new ArrayList();
            if (query.getResultList().size() > 0) {
                balancess = new ArrayList(query.getResultList());
            }
            return balancess;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveBalance> populateLeaveBalance(HrEmployees employee, HrLuLeaveTypes hrLeaveTypes) {
        
        try {
            Query query = em.createNamedQuery("HrLeaveBalance.populateBalance");
            query.setParameter("empid", employee.getId());
            query.setParameter("leaveId", hrLeaveTypes.getId());
            ArrayList<HrLeaveBalance> balances = new ArrayList(query.getResultList());
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveBalance> populateActiveLeaveBalance(HrEmployees employee) {
        
        try {
            Query query = em.createNativeQuery("select * from HR_LEAVE_BALANCE lv "
                    + "inner join HR_EMPLOYEES emp on lv.EMPLOYEE_ID=emp.ID where emp.id='" + employee.getId() + "' "
                    + "and( lv.STATUS=1 or lv.TRANSFER_STATUS=1) ORDER BY lv.LEAVE_YEAR_ID", HrLeaveBalance.class);
            ArrayList<HrLeaveBalance> balances = new ArrayList(query.getResultList());
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveBalance> populateLeaveBalanceForComp(HrEmployees employee) {
        Query query = em.createNamedQuery("HrLeaveBalance.populateBalanceForComp");
        query.setParameter("empid", employee.getId());
        try {
            ArrayList<HrLeaveBalance> balances = new ArrayList(query.getResultList());
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public ArrayList<HrLeaveBalance> populateLeaveBalanceByLeaveType(HrLuLeaveTypes hrLeaveTypes, FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("HrLeaveBalance.populateBalanceByLyAnLt");
        query.setParameter("leaveId", hrLeaveTypes.getId());
        query.setParameter("acc", budgetYear.getLuBudgetYearId());
        try {
            ArrayList<HrLeaveBalance> balances = new ArrayList(query.getResultList());
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public boolean checkLeaveBalanceExistance(FmsAccountingPeriod fap, HrLuLeaveTypes hrLeaveTypes) {
        boolean checktest = false;
        Query query = em.createNamedQuery("HrLeaveBalance.checkExistance");
        query.setParameter("accp", fap.getAcountigPeriodId());
        query.setParameter("typeid", hrLeaveTypes.getId());
        try {
            ArrayList<HrLeaveBalance> balances = new ArrayList(query.getResultList());
            if (balances.size() > 0) {
                checktest = true;
            }
            return checktest;
        } catch (Exception e) {
            e.printStackTrace();
            return checktest;
        }
        
    }
    
    public boolean checkLeaveBalanceExistance(FmsLuBudgetYear flby, HrLuLeaveTypes hrLeaveTypes) {
        boolean checktest = false;
        Query query = em.createNamedQuery("HrLeaveBalance.checkExistance");
        query.setParameter("accp", flby.getLuBudgetYearId());
        query.setParameter("typeid", hrLeaveTypes.getId());
        try {
            ArrayList<HrLeaveBalance> balances = new ArrayList(query.getResultList());
            if (balances.size() > 0) {
                checktest = true;
            }
            return checktest;
        } catch (Exception e) {
            e.printStackTrace();
            return checktest;
        }
        
    }
    
    public void expireNotTransferedBalances() {
        
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("HR_LEAVE_TRANSFER_EXPIRE");
        try {
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void expire() {
        
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("HR_LEAVE_EXPIRE");
        try {
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public HrLeaveBalance findEmployeeBalance(HrEmployees emp, FmsLuBudgetYear fmsLuBudgetYear, int i) {
        
        Query query = em.createNamedQuery("HrLeaveBalance.findLeaveBalanceByYearAndType");
        query.setParameter("acc", fmsLuBudgetYear.getLuBudgetYearId());
        query.setParameter("leaveId", i);
        query.setParameter("employeeId", emp.getEmpId());
        try {
            HrLeaveBalance balances = (HrLeaveBalance) query.getSingleResult();
            System.out.println("--balances--" + balances);
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<HrEmployees> findEmpALnotGenerated() {
        try {
            String sqlQry = "SELECT emp.* "
                    + " FROM HR_EMPLOYEES emp "
                    + "WHERE emp.id NOT IN(SELECT lb.EMPLOYEE_ID FROM HR_LEAVE_BALANCE lb)"
                    + "AND emp.EMP_STATUS =01 or emp.EMP_STATUS = 02";
            
            Query query = em.createNativeQuery(sqlQry, HrEmployees.class);
            
            List<HrEmployees> listUsers = query.getResultList();
            return listUsers;
        } catch (Exception ex) {
            ex.printStackTrace();
            //return 0;
        }
        return null;
    }
    
    public List<HrLeaveBalance> populateLeaveBalanceByLeaveTyp(FmsLuBudgetYear fmsLuBudgetYear) {
        Query query = em.createNamedQuery("HrLeaveBalance.populateBalanceByBudgetYear");
        query.setParameter("acc", fmsLuBudgetYear.getLuBudgetYearId());
        try {
            ArrayList<HrLeaveBalance> balances = new ArrayList(query.getResultList());
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
//</editor-fold>

}
