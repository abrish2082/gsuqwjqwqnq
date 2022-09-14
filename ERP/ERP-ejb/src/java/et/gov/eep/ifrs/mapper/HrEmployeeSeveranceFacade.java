/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.ifrs.entity.HrEmployeeSeverance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class HrEmployeeSeveranceFacade extends AbstractFacade<HrEmployeeSeverance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEmployeeSeveranceFacade() {
        super(HrEmployeeSeverance.class);
    }

//    public List<HrEmployeeSeverance> findBYBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
//         Query query = em.createNamedQuery("HrEmployeeSeverance.findByBudgetYear");
//         System.out.println("fmsLuBudgetYear.getBudgetYear()=="+fmsLuBudgetYear.getBudgetYear());
//         query.setParameter("budgetYear", fmsLuBudgetYear.getBudgetYear());
//       
//       try {
//            ArrayList<HrEmployeeSeverance> balances = new ArrayList(query.getResultList());
//            return balances;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public List<HrEmployeeSeverance> findBYBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        System.out.println("fmsLuBudgetYear.getBudgetYear()= mapper=" + fmsLuBudgetYear.getBudgetYear());
        Query query = em.createNamedQuery("HrEmployeeSeverance.findByBudgetYear");
        System.out.println("fmsLuBudgetYear.getBudgetYear()==" + fmsLuBudgetYear.getBudgetYear());
        query.setParameter("budgetYear", fmsLuBudgetYear.getBudgetYear());

        try {
            ArrayList<HrEmployeeSeverance> balances = new ArrayList(query.getResultList());
            return balances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
