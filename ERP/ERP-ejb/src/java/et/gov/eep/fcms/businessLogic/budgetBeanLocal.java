/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

//import et.gov.insa.eep.fms.Entities.FmsBudget;
import et.gov.eep.fcms.entity.FmsBudget;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface budgetBeanLocal {

    /**
     *
     * @param budget1
     */
    public void Create(FmsBudget budget1);

    /**
     *
     * @param budget1
     */
    public void edit(FmsBudget budget1);

    /**
     *
     * @param budgetyear
     * @return
     */
    public ArrayList<FmsBudget> searchBudgetYear(FmsBudget budgetyear);

    /**
     *
     * @param budgetYear
     * @return
     */
    public FmsBudget getBudgetYearInfo(FmsBudget budgetYear);
}
