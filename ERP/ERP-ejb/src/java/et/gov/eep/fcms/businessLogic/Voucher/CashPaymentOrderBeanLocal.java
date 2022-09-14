/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="other methods ">
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>

@Local
public interface CashPaymentOrderBeanLocal {
//<editor-fold defaultstate="collapsed" desc="interface methods ">

    public List<String> getPrepareList(int approveStatus);

    public List<FmsCashPaymentOrder> getPreparedCPO();

    public void create(FmsCashPaymentOrder cheqPaymentVoucher);

    public void edit(FmsCashPaymentOrder fmsCashPaymentOrder);

    public FmsCashPaymentOrder getCashPaymentOrder(FmsCashPaymentOrder fmsCashPaymentOrder);
    //</editor-fold>

}

