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
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author PO
 */
@Local
public interface FmsCBTasksBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
public void delete(FmsCapitalBudgetTasks capitalBudgetTasks);

    public void edit(FmsCapitalBudgetTasks capitalBudgetTasks);

    public List<FmsCapitalBudgetTasks> findByCBudgetReqCode(FmsCapitalBudget1 capitalBudget1);

    public List<FmsCapitalBudgetTasks> findByCBudgetDetail(FmsCapitalBudgetDetail capitalBudgetDetail);

    public FmsCapitalBudgetTasks fetchCBTaskData(FmsCapitalBudgetDetail capitalBudgetDetail, FmsSubsidiaryLedger fmsSubsidiaryLedger);

    public FmsCapitalBudgetTasks fetchCBTask(FmsCapitalBudgetTasks budgetTasks);
    //</editor-fold>
    
    
}
