/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.mapper.FmsLuBudgetYearFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author AB
 */
@Stateless
public class FmsLuBudgetYearBean implements FmsLuBudgetYearBeanLocal {

    @EJB
    FmsLuBudgetYearFacade budgetYearFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     *
     * @return
     */
    @Override
    public List<FmsLuBudgetYear> findAll(){
        return budgetYearFacade.findAll();
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<FmsLuBudgetYear> getLuBudgetYear() {
        return budgetYearFacade.findAll();
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    @Override
    public FmsLuBudgetYear findByBudjetYear(FmsLuBudgetYear budgetYear) {
        return budgetYearFacade.findByBudjetYear(budgetYear);
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    @Override
     public FmsLuBudgetYear findByBudjetYearById(FmsLuBudgetYear budgetYear ) {
         return budgetYearFacade.findByBudjetYearById(budgetYear);
         
     }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<FmsLuBudgetYear> gertBudgetYear1() {
        return budgetYearFacade.ArrfindAll();
    }
}
