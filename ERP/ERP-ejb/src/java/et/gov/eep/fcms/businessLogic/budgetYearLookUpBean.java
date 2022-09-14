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
 * @author user
 */
@Stateless
public class budgetYearLookUpBean implements budgetYearLookUpBeanLocal {
    @EJB
    private FmsLuBudgetYearFacade fmsLuBudgetYearFacade;

    /**
     *
     * @param bgtyear
     */
    @Override
    public void create(FmsLuBudgetYear bgtyear) {
        fmsLuBudgetYearFacade.create(bgtyear);
    }

    /**
     *
     * @param bgtyear
     */
    @Override
    public void edit(FmsLuBudgetYear bgtyear) {
        fmsLuBudgetYearFacade.edit(bgtyear);
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<FmsLuBudgetYear> getBudgetYear() {
        return fmsLuBudgetYearFacade.getActiveYear();
    }

    /**
     *
     * @param bgtYear
     * @return
     */
    @Override
    public FmsLuBudgetYear getBudgetYearInfo(FmsLuBudgetYear bgtYear) {
        return fmsLuBudgetYearFacade.getbudgetYear(bgtYear);
    }

    /**
     *
     * @param budgetyear
     * @return
     */
    @Override
    public ArrayList<FmsLuBudgetYear> searchBudgetYear(FmsLuBudgetYear budgetyear) {
      return fmsLuBudgetYearFacade.searchOperatingBudgetyear(budgetyear);
    }    

    /**
     *
     * @return
     */
    @Override
    public ArrayList<FmsLuBudgetYear> budgetYearList() {
       return fmsLuBudgetYearFacade.budgetYearList();
    } 

    /**
     *
     * @return
     */
    @Override
    public List<FmsLuBudgetYear> allBudgetYear() {
        return fmsLuBudgetYearFacade.ArrfindAll();
    }

    /**
     *
     * @param bgtYear
     * @return
     */
    @Override
    public FmsLuBudgetYear getYearInfo(FmsLuBudgetYear bgtYear) {
        return fmsLuBudgetYearFacade.getYear(bgtYear);
    }

    /**
     *
     * @param budgetyear
     * @return
     */
    @Override
    public FmsLuBudgetYear findBgtYearbyId(FmsLuBudgetYear budgetyear) {
      return fmsLuBudgetYearFacade.getActiveBudgetId(budgetyear);
    }
}

