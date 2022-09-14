/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.mapper.budget.FmsCapitalBudgetTasksFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>

/**
 *
 * @author PO
 */
@Stateless
public class FmsCBTasksBean implements FmsCBTasksBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">

    @EJB
    FmsCapitalBudgetTasksFacade capitalBudgetTasksFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public void delete(FmsCapitalBudgetTasks capitalBudgetTasks) {
        capitalBudgetTasksFacade.remove(capitalBudgetTasks);
    }

    @Override
    public void edit(FmsCapitalBudgetTasks capitalBudgetTasks) {
        capitalBudgetTasksFacade.edit(capitalBudgetTasks);
    }

    @Override
    public List<FmsCapitalBudgetTasks> findByCBudgetReqCode(FmsCapitalBudget1 capitalBudget1) {
        return capitalBudgetTasksFacade.findByCBudgetReqCode(capitalBudget1);
    }

    @Override
    public List<FmsCapitalBudgetTasks> findByCBudgetDetail(FmsCapitalBudgetDetail capitalBudgetDetail) {
        return capitalBudgetTasksFacade.findByCBudgetDetail(capitalBudgetDetail);
    }

    @Override
    public FmsCapitalBudgetTasks fetchCBTaskData(FmsCapitalBudgetDetail capitalBudgetDetail, FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        return capitalBudgetTasksFacade.fetchCBTaskData(capitalBudgetDetail, fmsSubsidiaryLedger);
    }

    @Override
    public FmsCapitalBudgetTasks fetchCBTask(FmsCapitalBudgetTasks budgetTasks) {
        return capitalBudgetTasksFacade.fetchCBTask(budgetTasks);
    }
    //</editor-fold>

}
