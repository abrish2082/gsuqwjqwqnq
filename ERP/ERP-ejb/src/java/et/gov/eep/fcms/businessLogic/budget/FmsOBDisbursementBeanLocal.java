/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Local
public interface FmsOBDisbursementBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
 public void create(FmsObDisbursement fmsOBDisbursement);

    public void edit(FmsObDisbursement fmsOBDisbursement);

    public List<FmsObDisbursement> fetchDisbursedOB(FmsOperatingBudgetDetail fmsOperatingBudgetDetail);

    public FmsObDisbursement fetchOBDisbByTaskId(FmsOperatingBudgetTasks fmsOperatingBudgetTasks);

    public FmsObDisbursement fetchOBDisbByID(FmsObDisbursement fmsObDisbursement);
    //</editor-fold>
    
   
}
