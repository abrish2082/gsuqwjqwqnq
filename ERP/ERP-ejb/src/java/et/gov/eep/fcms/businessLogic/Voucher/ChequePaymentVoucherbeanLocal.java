/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>

@Local
public interface ChequePaymentVoucherbeanLocal {
//<editor-fold defaultstate="collapsed" desc="interface methods ">
 public List<FmsChequePaymentVoucher> findAll();

    /**
     *
     * @param cheqPaymentVoucher
     */
    public void edit(FmsChequePaymentVoucher cheqPaymentVoucher);

    /**
     *
     * @param cheqPaymentVoucher
     */
    public void create(FmsChequePaymentVoucher cheqPaymentVoucher);

    /**
     *
     * @param chequeVoucherList
     * @return
     */
    public List<FmsChequePaymentVoucher> getchequeVoucherLists(FmsChequePaymentVoucher chequeVoucherList);

    public List<FmsChequePaymentVoucher> getchequeVoucherCapitalLists(FmsChequePaymentVoucher chequeVoucherList);

    /**
     *
     * @param chequePayment
     * @return
     */
    public FmsChequePaymentVoucher getApprovedChequePayment(FmsChequePaymentVoucher chequePayment);

    /**
     *
     * @param approveStatus
     * @return
     */
    public List<String> getPrepareList(int approveStatus);

    public List<FmsChequePaymentVoucher> fetchchequeVoucherLists();
//    public FmsFieldAllowance getByrequestno(FmsFieldAllowance fmsFieldAllowanceEnty);

    public FmsChequePaymentVoucher getbycheqno(FmsChequePaymentVoucher chequePaymentVoucherentEnty);
    //</editor-fold>
    
   public List<String> findAllItemStatuses();
   public int ConutBYItemType(String get);
}
