/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.budget.FmsObTransferDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import et.gov.eep.fcms.mapper.budget.FmsObTransferDetailFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>
    


/**
 *
 * @author PO
 */
@Stateless
public class FmsOBTransferDetailBean implements FmsOBTransferDetailBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
@EJB
    FmsObTransferDetailFacade fmsObTransferDetailFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
  @Override
    public void create(FmsObTransferDetail fmsObTransferDetail) {
        fmsObTransferDetailFacade.create(fmsObTransferDetail);
    }

    @Override
    public void update(FmsObTransferDetail fmsObTransferDetail) {
        fmsObTransferDetailFacade.edit(fmsObTransferDetail);
    }

    @Override
    public ArrayList<FmsObTransferDetail> fetchOBFromByOBDTasktlID(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        return fmsObTransferDetailFacade.fetchOBFromByOBDTasktlID(fmsOperatingBudgetTasks);
    }

    @Override
    public ArrayList<FmsObTransferDetail> findOBTransReqList() {
        return fmsObTransferDetailFacade.findOBTransReqList();
    }

    @Override
    public FmsObTransferDetail findByTrasferId(FmsObTransferDetail fmsObTransferDetail) {
        return fmsObTransferDetailFacade.findByTrasferId(fmsObTransferDetail);
    }
    //</editor-fold>
    
    
    

  
}
