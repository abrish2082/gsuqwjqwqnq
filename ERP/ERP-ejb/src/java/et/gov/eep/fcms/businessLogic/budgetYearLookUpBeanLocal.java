/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface budgetYearLookUpBeanLocal {

    /**
     *
     * @param bgtyear
     */
    public void create(FmsLuBudgetYear bgtyear);

    /**
     *
     * @param bgtyear
     */
    public void edit(FmsLuBudgetYear bgtyear);

    /**
     *
     * @return
     */
    public List<FmsLuBudgetYear> getBudgetYear();

    /**
     *
     * @return
     */
    public List<FmsLuBudgetYear> allBudgetYear();

    /**
     *
     * @param bgtYear
     * @return
     */
    public FmsLuBudgetYear getBudgetYearInfo(FmsLuBudgetYear bgtYear);

    /**
     *
     * @param bgtYear
     * @return
     */
    public FmsLuBudgetYear getYearInfo(FmsLuBudgetYear bgtYear);

    /**
     *
     * @param budgetyear
     * @return
     */
    public ArrayList<FmsLuBudgetYear> searchBudgetYear(FmsLuBudgetYear budgetyear);

    /**
     *
     * @return
     */
    public ArrayList<FmsLuBudgetYear> budgetYearList();

    /**
     *
     * @param budgetyear
     * @return
     */
    public FmsLuBudgetYear findBgtYearbyId(FmsLuBudgetYear budgetyear);


}
