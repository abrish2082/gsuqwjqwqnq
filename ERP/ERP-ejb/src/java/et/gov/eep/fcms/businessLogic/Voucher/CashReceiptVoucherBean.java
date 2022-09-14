/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import et.gov.eep.fcms.mapper.Voucher.FmsCashReceiptVoucherFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
//</editor-fold>

@Stateless
public class CashReceiptVoucherBean implements CashReceiptVoucherBeanLocal {

    @EJB
    FmsCashReceiptVoucherFacade cashReceiptVoucherFacade;
//<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public List<String> getPrepareList(int approveStatus) {
        return cashReceiptVoucherFacade.getPrepareList(approveStatus);
    }

    /**
     *
     * @param cashReceiptVoucher
     */
    @Override
    public void create(FmsCashReceiptVoucher cashReceiptVoucher) {
        cashReceiptVoucherFacade.create(cashReceiptVoucher);
    }
    //</editor-fold>

}
