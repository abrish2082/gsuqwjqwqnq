/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author mora
 */
@Local
public interface CashReceiptVoucherBeanLocal {

    //<editor-fold defaultstate="collapsed" desc="interface methods ">
 public List<String> getPrepareList(int approveStatus);

    /**
     *
     * @param cheqPaymentVoucher
     */
    public void create(FmsCashReceiptVoucher cheqPaymentVoucher);
    //</editor-fold>
    /**
     *
     * @param approveStatus
     * @return
     */
   

}
