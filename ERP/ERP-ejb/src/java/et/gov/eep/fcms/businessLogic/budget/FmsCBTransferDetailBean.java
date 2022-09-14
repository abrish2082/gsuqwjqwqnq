/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbTransferDetail;
import et.gov.eep.fcms.mapper.budget.FmsCbTransferDetailFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>

/**
 *
 * @author PO
 */
@Stateless
public class FmsCBTransferDetailBean implements FmsCBTransferDetailBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">

    @EJB
    FmsCbTransferDetailFacade fmsCbTransferDetailFacade;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public void create(FmsCbTransferDetail fmsCbTransferDetail) {
        fmsCbTransferDetailFacade.create(fmsCbTransferDetail);
    }

    @Override
    public void update(FmsCbTransferDetail fmsCbTransferDetail) {
        fmsCbTransferDetailFacade.edit(fmsCbTransferDetail);
    }

    @Override
    public ArrayList<FmsCbTransferDetail> fetchCBFromByCBDTasktlID(FmsCapitalBudgetTasks capitalBudgetTasks) {
        return fmsCbTransferDetailFacade.fetchCBFromByCBDTasktlID(capitalBudgetTasks);
    }

    @Override
    public ArrayList<FmsCbTransferDetail> findCBTransReqList() {
        return fmsCbTransferDetailFacade.findCBTransReqList();
    }

    @Override
    public FmsCbTransferDetail findByTrasferId(FmsCbTransferDetail cbTransferDetail) {
        return fmsCbTransferDetailFacade.findByTrasferId(cbTransferDetail);
    }
    //</editor-fold>

}
