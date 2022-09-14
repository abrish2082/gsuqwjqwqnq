/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.mapper.Voucher.FmsChequePaymentVoucherFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>

@Stateless
public class ChequePaymentVoucherbean implements ChequePaymentVoucherbeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">

    @EJB
    FmsChequePaymentVoucherFacade chequePaymentVoucher;
    @EJB
    FmsChequePaymentVoucherFacade fmsChequePaymentVoucherFacade;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public List<FmsChequePaymentVoucher> getchequeVoucherLists(FmsChequePaymentVoucher chequeVoucherList) {
        return chequePaymentVoucher.getChequePaymentLists(chequeVoucherList);
    }

    /**
     *
     * @param chequeVoucherList
     * @return
     */
    @Override
    public List<FmsChequePaymentVoucher> getchequeVoucherCapitalLists(FmsChequePaymentVoucher chequeVoucherList) {
        return chequePaymentVoucher.getChequePaymentforCapitalLists(chequeVoucherList);
    }

    /**
     *
     * @param chequePayment
     * @return
     */
    @Override
    public FmsChequePaymentVoucher getApprovedChequePayment(FmsChequePaymentVoucher chequePayment) {
        return chequePaymentVoucher.getApprovedChequePayment(chequePayment);
    }

    /**
     *
     * @param cheqPaymentVoucher
     * @return
     */
    @Override
    public List<FmsChequePaymentVoucher> findAll() {
        return chequePaymentVoucher.findAll();
    }

    @Override
    public void edit(FmsChequePaymentVoucher cheqPaymentVoucher) {
        chequePaymentVoucher.edit(cheqPaymentVoucher);
    }

    /**
     *
     * @param cheqPaymentVoucher
     */
    @Override
    public void create(FmsChequePaymentVoucher cheqPaymentVoucher) {
        chequePaymentVoucher.create(cheqPaymentVoucher);
    }

    /**
     *
     * @param approveStatus
     * @return
     */
    @Override
    public List<String> getPrepareList(int approveStatus) {
        return chequePaymentVoucher.getPrepareList(approveStatus);

    }

    @Override
    public List<FmsChequePaymentVoucher> fetchchequeVoucherLists() {
        return chequePaymentVoucher.fetchChequePaymentLists();
    }
//     @Override
//    public FmsFieldAllowance getByrequestno(FmsFieldAllowance fmsFieldAllowanceEnty) {
//        return allowanceFacade.getByRequstno(fmsFieldAllowanceEnty);
//    }

    @Override
    public FmsChequePaymentVoucher getbycheqno(FmsChequePaymentVoucher chequePaymentVoucherEnty) {
        return fmsChequePaymentVoucherFacade.getbycheqno(chequePaymentVoucherEnty);
    }
    
     @Override
    public List<String> findAllItemStatuses() {
        return fmsChequePaymentVoucherFacade.findAllItemStatuses();
    }
    
     @Override
    public int ConutBYItemType(String get) {
        return fmsChequePaymentVoucherFacade.ConutBYItemType(get);
    }
    //</editor-fold>
    

}
