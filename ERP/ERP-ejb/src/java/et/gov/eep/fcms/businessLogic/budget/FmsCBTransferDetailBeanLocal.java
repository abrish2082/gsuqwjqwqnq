/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbTransferDetail;
import java.util.ArrayList;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author PO
 */
@Local
public interface FmsCBTransferDetailBeanLocal {
    //<editor-fold defaultstate="collapsed" desc="other methods ">
public void create(FmsCbTransferDetail fmsCbTransferDetail);
    public void update(FmsCbTransferDetail fmsCbTransferDetail);
    public ArrayList<FmsCbTransferDetail> fetchCBFromByCBDTasktlID(FmsCapitalBudgetTasks fmsOperatingBudgetTasks);
    public ArrayList<FmsCbTransferDetail> findCBTransReqList();
    public FmsCbTransferDetail findByTrasferId(FmsCbTransferDetail fmsObTransferDetail);
    //</editor-fold>
    
    
}
