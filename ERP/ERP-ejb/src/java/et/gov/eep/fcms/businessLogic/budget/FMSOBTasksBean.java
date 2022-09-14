
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import et.gov.eep.fcms.mapper.budget.FmsOperatingBudgetTasksFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
//</editor-fold>

@Stateless
public class FMSOBTasksBean implements FMSOBTasksBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB">

    @EJB
    FmsOperatingBudgetTasksFacade fmsOperatingBudgetTasksFacade;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public void delete(FmsOperatingBudgetTasks budgetTasks) {
        fmsOperatingBudgetTasksFacade.remove(budgetTasks);
    }

    @Override
    public FmsOperatingBudgetTasks findByOBDtlAndSL(FmsOperatingBudgetDetail budgetDetail, FmsSubsidiaryLedger subsidiaryLedger) {
        return fmsOperatingBudgetTasksFacade.findByOBDtlAndSL(budgetDetail, subsidiaryLedger);
    }

    @Override
    public FmsOperatingBudgetTasks fetchOBTask(FmsOperatingBudgetTasks budgetTasks) {
        return fmsOperatingBudgetTasksFacade.fetchOBTask(budgetTasks);
    }

    @Override
    public List<FmsOperatingBudgetTasks> findByOBudgetReqCode(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetTasksFacade.findByOBudgetReqCode(fmsOperatingBudget1);
    }

    @Override
    public List<FmsOperatingBudgetTasks> findByOBudgetDetailId(FmsOperatingBudgetDetail operatingBudgetDetail) {
        return fmsOperatingBudgetTasksFacade.findByOBudgetDetailId(operatingBudgetDetail);
    }

    @Override
    public void edit(FmsOperatingBudgetTasks budgetTasks) {
        fmsOperatingBudgetTasksFacade.edit(budgetTasks);
    }
    //</editor-fold>

}
