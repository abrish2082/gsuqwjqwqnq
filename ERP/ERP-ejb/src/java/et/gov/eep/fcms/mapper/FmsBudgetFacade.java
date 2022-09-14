/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.fcms.entity.FmsBudget;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Man
 */
@Stateless
public class FmsBudgetFacade extends AbstractFacade<FmsBudget> {

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
    public FmsBudgetFacade() {
        super(FmsBudget.class);
    }

    /**
     *
     * @param operating
     * @return
     */
    public ArrayList<FmsBudget> searchOperatingBudgetById(FmsBudget operating) {
        //accessing e 
        Query query = em.createNamedQuery("FmsBudget.searchByBudgetYear");
        query.setParameter("budgetYear", operating.getBudgetYear() + '%');
        try {
            ArrayList<FmsBudget> operatingBudgetList = new ArrayList(query.getResultList());
            return operatingBudgetList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param operating
     * @return
     */
    public FmsBudget getbudgetYear(FmsBudget operating) {
        //accessing e 
        Query query = em.createNamedQuery("FmsBudget.findByBudgetYear");
        query.setParameter("budgetYear", operating.getBudgetYear());
        try {
            FmsBudget operatingBudgetList = (FmsBudget) query.getResultList().get(0);
            return operatingBudgetList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
