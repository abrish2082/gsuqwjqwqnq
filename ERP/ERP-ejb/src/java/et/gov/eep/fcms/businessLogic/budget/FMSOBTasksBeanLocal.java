
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


@Local
public interface FMSOBTasksBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
public void delete(FmsOperatingBudgetTasks budgetTasks);
    
    public void edit(FmsOperatingBudgetTasks budgetTasks);
    
    public FmsOperatingBudgetTasks findByOBDtlAndSL(FmsOperatingBudgetDetail budgetDetail, FmsSubsidiaryLedger subsidiaryLedger);
    
    public FmsOperatingBudgetTasks fetchOBTask(FmsOperatingBudgetTasks budgetTasks);
    
    public List<FmsOperatingBudgetTasks> findByOBudgetReqCode(FmsOperatingBudget1 fmsOperatingBudget1);
    
    public List<FmsOperatingBudgetTasks> findByOBudgetDetailId(FmsOperatingBudgetDetail operatingBudgetDetail);
    //</editor-fold>
    
    
}
