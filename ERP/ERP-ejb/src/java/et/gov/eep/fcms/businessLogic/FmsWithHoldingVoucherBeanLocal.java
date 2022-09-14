/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsWithHoldingVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author AB
 */
@Local
public interface FmsWithHoldingVoucherBeanLocal {
    /**
     *
     * @param withHoldingVoucher
     */
    public void create(FmsWithHoldingVoucher withHoldingVoucher);

    /**
     *
     * @param withHoldingVoucher
     * @return
     */
    public List<FmsWithHoldingVoucher> searchWithHoldVoucheid(FmsWithHoldingVoucher withHoldingVoucher);

    /**
     *
     * @param withHoldingVoucher
     * @return
     */
    public FmsWithHoldingVoucher getSearchWithHoldingVoucherID(FmsWithHoldingVoucher withHoldingVoucher);

    /**
     *
     * @param withHoldingVoucher
     */
    public void edit(FmsWithHoldingVoucher withHoldingVoucher);

    public List<FmsChequePaymentVoucher> getchequePaymentNoList();
//    public List<FmsChequePaymentVoucher> getchequePaymentNoList();

}
