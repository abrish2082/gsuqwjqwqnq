/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsBudget;
import et.gov.eep.fcms.mapper.FmsBudgetFacade;
//import et.gov.insa.eep.fms.Entity.FmsBudget;
//import et.gov.insa.eep.fms.mapper.FmsBudgetFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class budgetBean implements budgetBeanLocal {
    @EJB
    private FmsBudgetFacade fmsBudgetFacade;

    /**
     *
     * @param budget1
     */
    @Override
    public void Create(FmsBudget budget1) {
        fmsBudgetFacade.create(budget1);
    }

    /**
     *
     * @param budget1
     */
    @Override
    public void edit(FmsBudget budget1) {
        fmsBudgetFacade.edit(budget1);
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    @Override
    public ArrayList<FmsBudget> searchBudgetYear(FmsBudget budgetYear) {
        return fmsBudgetFacade.searchOperatingBudgetById(budgetYear);
    }

    /**
     *
     * @param budgetYear
     * @return
     */
    @Override
    public FmsBudget getBudgetYearInfo(FmsBudget budgetYear) {
       return fmsBudgetFacade.getbudgetYear(budgetYear);
    }
}
