
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbDisbursement;
import et.gov.eep.fcms.mapper.budget.FmsCbDisbursementFacade;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Stateless
public class FmsCBDisbursementBean implements FmsCBDisbursementBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
 @EJB
    FmsCbDisbursementFacade fmsCbDisbursementFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public void create(FmsCbDisbursement fmsCBDisbursement) {
        fmsCbDisbursementFacade.create(fmsCBDisbursement);
    }

    @Override
    public void edit(FmsCbDisbursement fmsCBDisbursement) {
        fmsCbDisbursementFacade.edit(fmsCBDisbursement);
    }

    @Override
    public List<FmsCbDisbursement> fetchDisbursedBudget(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter, PmsWorkAuthorization authorization) {
        return fmsCbDisbursementFacade.fetchDisbursedBudget(fmsLuBudgetYear, fmsCostCenter, authorization);
    }

    @Override
    public List<FmsCbDisbursement> fetchDisbursedCB(FmsCapitalBudgetDetail capitalBudgetDetail) {
        return fmsCbDisbursementFacade.fetchDisbursedCB(capitalBudgetDetail);
    }

    @Override
    public FmsCbDisbursement fetchCBDisbByTaskId(FmsCapitalBudgetTasks capitalBudgetTasks) {
        return fmsCbDisbursementFacade.fetchCBDisbByTaskId(capitalBudgetTasks);
    }

    @Override
    public FmsCbDisbursement fetchCBDisbByID(FmsCbDisbursement cbDisbursement) {
        return fmsCbDisbursementFacade.fetchCBDisbByID(cbDisbursement);
    }

    //</editor-fold>
    
   

   
}
