/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.EmployeeBonus;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonus;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrEmployeesBonusFacade extends AbstractFacade<HrEmployeesBonus> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEmployeesBonusFacade() {
        super(HrEmployeesBonus.class);
    }

    public HrEmployeesBonus findByBgtYear(String bgtYr) {
        Query query = em.createNamedQuery("HrEmployeesBonus.findByBudgetYear");
        query.setParameter("budgetYear", bgtYr);
        try {
            HrEmployeesBonus employeesBonus = (HrEmployeesBonus) query.getResultList().get(0);
            return employeesBonus;
        } catch (Exception ex) {
            return null;
        }

    }

    public List<HrEmployees> searchEmployee(Integer status2, Integer status, HrEmployees hremployees) {
        Query query = em.createNamedQuery("HrEmployeesBonus.statusactive", HrEmployees.class);
        query.setParameter("status", status);
        query.setParameter("status2", status2);;
        try {

            List<HrEmployees> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployeesBonus> loadFiltereddata(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='3' OR STATUS='2' OR STATUS='4')";
        }
        else if (status == 4) {
            queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
        }
        else if (status == 3) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_EMPLOYEES_BONUS "
                + queryStatus
                + "ORDER BY AMOUNT DESC", HrEmployeesBonus.class);
        try {
            return (List<HrEmployeesBonus>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrEmployeesBonus getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("HrEmployeesBonus.findById");
        query.setParameter("id", id);
        try {
            HrEmployeesBonus selectrequest = (HrEmployeesBonus) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrEmployeesBonus> findrequestlist() {
         Query query = em.createNamedQuery("HrEmployeesBonus.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<HrEmployeesBonus>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
