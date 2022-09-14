/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.mapper.budget.FmsBudgetCodeFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class budgetCodeBean implements budgetCodeBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">

    @EJB
    private FmsBudgetCodeFacade fmsBudgetCodeFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public List<FmsBudgetCode> getFmsBudgetCodeSearchingParameterList() {
        return fmsBudgetCodeFacade.getFmsBudgetCodeSearchingParameterList();
    }

    @Override
    public List<FmsBudgetCode> findAll() {
        return fmsBudgetCodeFacade.findAll();
    }

    @Override
    public List<FmsBudgetCode> findAllCapital() {
        return fmsBudgetCodeFacade.findAllCapital();
    }

    /**
     *
     * @param budgetCode
     */
    @Override
    public void create(FmsBudgetCode budgetCode) {
        fmsBudgetCodeFacade.create(budgetCode);
    }

    /**
     *
     * @param budgetCode
     */
    @Override
    public void edit(FmsBudgetCode budgetCode) {
        fmsBudgetCodeFacade.edit(budgetCode);
    }

    /**
     *
     * @param budgetCode
     */
    @Override
    public void delete(FmsBudgetCode budgetCode) {
        fmsBudgetCodeFacade.remove(budgetCode);
    }

    /**
     *
     * @param bgtCode
     * @return
     */
    @Override
    public FmsBudgetCode getBudgetCode(FmsBudgetCode bgtCode) {
        return fmsBudgetCodeFacade.getBudgetCode(bgtCode);
    }

    /**
     *
     * @param budgetCode
     * @return
     */
    @Override
    public ArrayList<FmsBudgetCode> searchBudgetCode(FmsBudgetCode budgetCode) {
        return fmsBudgetCodeFacade.searchBudgetCode(budgetCode);
    }

    @Override
    public FmsBudgetCode searchBgttCode(FmsBudgetCode bgtCode) {
        return fmsBudgetCodeFacade.searchBgtCode(bgtCode);
    }

    @Override
    public FmsBudgetCode getSelectedRequest(int operatingId) {
        return fmsBudgetCodeFacade.getSelectedRequest(operatingId);
    }

    @Override
    public FmsBudgetCode findBudgetCode(FmsBudgetCode budgetCode) {
        return fmsBudgetCodeFacade.findBudgetCode(budgetCode);
    }
    //</editor-fold>

    /**
     *
     * @return
     */
}
