/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.FmsTaxRegistration;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author user
 */
@Stateless
public class FmsTaxRegistrationFacade extends AbstractFacade<FmsTaxRegistration> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsTaxRegistrationFacade() {
        super(FmsTaxRegistration.class);
    }       

    /**
     *
     * @param name
     * @return
     */
    public ArrayList<HrEmployees> searchEmployeeName(HrEmployees name) {
       //accessing e 
        Query query = em.createNamedQuery("HrEmployees.searchByFirstNameLike");
        query.setParameter("firstName", name.getFirstName()+'%');         
        try {
            ArrayList<HrEmployees> employeeList = new ArrayList(query.getResultList());           
            return employeeList;
            
        } catch (Exception ex) {
            return null;
        }
    }  

    /**
     *
     * @param name
     * @return
     */
    public HrEmployees getEmployeeName(HrEmployees name) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstName");
        query.setParameter("firstName", name.getFirstName());   
        try {
            HrEmployees getEmployeeName = (HrEmployees)query.getResultList().get(0);           
            return getEmployeeName;
            
        } catch (Exception ex) {
            return null;
        }   
}

    /**
     *
     * @param name
     * @return
     */
    public FmsTaxRegistration getEmployee(FmsTaxRegistration name) {
        Query query = em.createNamedQuery("FmsTaxRegistration.findByCaherId");
        query.setParameter("casherId", name.getCasherId());   
        try {
            FmsTaxRegistration getEmployeeName = (FmsTaxRegistration)query.getResultList().get(0);           
            return getEmployeeName;
            
        } catch (Exception ex) {
            return null;
        }   
} 

    /**
     *
     * @param year
     * @return
     */
    public FmsLuBudgetYear getYear(FmsLuBudgetYear year) {
       //accessing e 
        Query query = em.createNamedQuery("FmsLuBudgetYear.findByBudgetYear");
        query.setParameter("budgetYear", year.getBudgetYear());   
//        System.out.println("--------------"+query.getResultList().size());
        try {
            FmsLuBudgetYear YearList = (FmsLuBudgetYear)query.getResultList().get(0);           
            return YearList;
            
        } catch (Exception ex) {
            return null;
        }   
}
        
    /**
     *
     * @param year
     * @return
     */
    public ArrayList<FmsTaxRegistration> findMonth(FmsLuBudgetYear year) {    
        Query query = em.createNamedQuery("FmsTaxRegistration.findByYearId");
        query.setParameter("yearId", year);
        try {
            ArrayList<FmsTaxRegistration> monthList = new ArrayList(query.getResultList());
            return monthList;
        } catch (Exception ex) {
            return null;
        }
    }   
         
    /**
     *
     * @param month
     * @param year
     * @return
     */
    public ArrayList<FmsTaxRegistration> getMonth(FmsTaxRegistration month,FmsLuBudgetYear year) {         
        Query query = em.createNamedQuery("FmsTaxRegistration.findByMonth");   
        query.setParameter("luBudgetYearId", year.getLuBudgetYearId());
        query.setParameter("month", month.getMonth());
       
        try {
            ArrayList<FmsTaxRegistration> monthList = new ArrayList(query.getResultList());
            return monthList;
        } catch (Exception ex) {
            return null;
        }
    }     
}
