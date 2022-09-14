/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.budget.FmsObTransferDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.util.ArrayList;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author PO
 */
@Local
public interface FmsOBTransferDetailBeanLocal {
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 public void create(FmsObTransferDetail fmsObTransferDetail);
    public void update(FmsObTransferDetail fmsObTransferDetail);
    public ArrayList<FmsObTransferDetail> fetchOBFromByOBDTasktlID(FmsOperatingBudgetTasks fmsOperatingBudgetTasks);
    public ArrayList<FmsObTransferDetail> findOBTransReqList();
    public FmsObTransferDetail findByTrasferId(FmsObTransferDetail fmsObTransferDetail);
    //</editor-fold>
    
   
    
}
