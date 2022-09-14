/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AB
 */
@Stateless
public class FmsLuBudgetYearFacade extends AbstractFacade<FmsLuBudgetYear> {

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
    public FmsLuBudgetYearFacade() {
        super(FmsLuBudgetYear.class);
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    public FmsLuBudgetYear findByBudjetYear(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsLuBudgetYear.findByBudgetYear");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        try {
            return (FmsLuBudgetYear) query.getResultList().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

//     @Override
    /**
     *
     * @return
     */
    public ArrayList<FmsLuBudgetYear> ArrfindAll() {
        Query query = em.createNamedQuery("FmsLuBudgetYear.findAll");
        try {
            ArrayList<FmsLuBudgetYear> buds = new ArrayList(query.getResultList());
            return buds;
        } catch (Exception ex) {
            return null;
        }        
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    public FmsLuBudgetYear findByBudjetYearById(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsLuBudgetYear.findByLuBudgetYearId");
        query.setParameter("luBudgetYearId", budgetYear.getLuBudgetYearId());
        try {

            FmsLuBudgetYear fmsBudgetYear = (FmsLuBudgetYear) query.getResultList().get(0);
            return fmsBudgetYear;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public List<FmsLuBudgetYear> getActiveYear() {
        Query query = em.createNamedQuery("FmsLuBudgetYear.findByBudgetYearList");
        try {
            ArrayList<FmsLuBudgetYear> BudjetYear = new ArrayList(query.getResultList());
            return BudjetYear;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param operating
     * @return
     */
    public FmsLuBudgetYear getbudgetYear(FmsLuBudgetYear operating) {
        //accessing e 
        Query query = em.createNamedQuery("FmsLuBudgetYear.getByBudgetYearRequest");
        query.setParameter("budgetYear", operating.getBudgetYear());
        try {
            FmsLuBudgetYear budgetYearList = (FmsLuBudgetYear) query.getResultList().get(0);
            return budgetYearList;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    public ArrayList<FmsLuBudgetYear> searchOperatingBudgetyear(FmsLuBudgetYear budgetYear) {
        //accessing e 
        Query query = em.createNamedQuery("FmsLuBudgetYear.searchByBudgetYear");
        query.setParameter("budgetYear", budgetYear.getBudgetYear() + '%');
        try {
            ArrayList<FmsLuBudgetYear> operatingBudgetList = new ArrayList(query.getResultList());
            return operatingBudgetList;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<FmsLuBudgetYear> budgetYearList() {
        //accessing e 
        Query query = em.createNamedQuery("FmsLuBudgetYear.listByBudgetYear");
        try {
            ArrayList<FmsLuBudgetYear> budgetYearList = new ArrayList(query.getResultList());
            return budgetYearList;

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
            FmsLuBudgetYear YearList = (FmsLuBudgetYear) query.getResultList().get(0);
            return YearList;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    public FmsLuBudgetYear getActiveBudgetId(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsLuBudgetYear.findByLuBudgetYearId");
        query.setParameter("luBudgetYearId", budgetYear.getLuBudgetYearId());
        try {
            if (query.getResultList().size() > 0) {
                FmsLuBudgetYear fmsBudgetYear = (FmsLuBudgetYear) query.getResultList().get(0);
                return fmsBudgetYear;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

}
