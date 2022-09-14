/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
  //<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>

/**
 *
 * @author user
 */
@Local
public interface budgetCodeBeanLocal {
  //<editor-fold defaultstate="collapsed" desc="other methods ">

    public List<FmsBudgetCode> findAll();

    public List<FmsBudgetCode> findAllCapital();

    /**
     *
     * @param budgetCode
     */
    public void create(FmsBudgetCode budgetCode);

    public List<FmsBudgetCode> getFmsBudgetCodeSearchingParameterList();

    /**
     *
     * @param budgetCode
     */
    public void edit(FmsBudgetCode budgetCode);

    /**
     *
     * @param budgetCode
     */
    public void delete(FmsBudgetCode budgetCode);

    /**
     *
     * @param bgtCode
     * @return
     */
    public FmsBudgetCode getBudgetCode(FmsBudgetCode bgtCode);

    public FmsBudgetCode searchBgttCode(FmsBudgetCode bgtCode);

    /**
     *
     * @param budgetCode
     * @return
     */
    public ArrayList<FmsBudgetCode> searchBudgetCode(FmsBudgetCode budgetCode);

    public FmsBudgetCode getSelectedRequest(int operatingId);

    public FmsBudgetCode findBudgetCode(FmsBudgetCode budgetCode);
    //</editor-fold>

    /**
     *
     * @return
     */
}
