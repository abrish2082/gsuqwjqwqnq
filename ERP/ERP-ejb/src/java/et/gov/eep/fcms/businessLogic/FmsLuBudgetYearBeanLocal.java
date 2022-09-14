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
 * @author AB
 */
@Local
public interface FmsLuBudgetYearBeanLocal {
    
    /**
     *
     * @return
     */
    public List <FmsLuBudgetYear> findAll();

    /**
     *
     * @return
     */
    public List<FmsLuBudgetYear> getLuBudgetYear();
    
    /**
     *
     * @return
     */
    public ArrayList<FmsLuBudgetYear>gertBudgetYear1();

    /**
     *
     * @param budgetYear
     * @return
     */
    public FmsLuBudgetYear findByBudjetYear(FmsLuBudgetYear budgetYear);

    /**
     *
     * @param budgetYear
     * @return
     */
    public FmsLuBudgetYear findByBudjetYearById(FmsLuBudgetYear budgetYear);

   

}
