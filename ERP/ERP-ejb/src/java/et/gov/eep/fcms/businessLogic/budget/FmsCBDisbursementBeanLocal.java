
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbDisbursement;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Local
public interface FmsCBDisbursementBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
 public void create(FmsCbDisbursement fmsCBDisbursement);

    public void edit(FmsCbDisbursement fmsCBDisbursement);

    public List<FmsCbDisbursement> fetchDisbursedBudget(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter, PmsWorkAuthorization authorization);

    public List<FmsCbDisbursement> fetchDisbursedCB(FmsCapitalBudgetDetail capitalBudgetDetail);

    public FmsCbDisbursement fetchCBDisbByTaskId(FmsCapitalBudgetTasks capitalBudgetTasks);

    public FmsCbDisbursement fetchCBDisbByID(FmsCbDisbursement cbDisbursement);
    //</editor-fold>
    
   
}
