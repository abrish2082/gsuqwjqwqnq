/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.pettyCash;


import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.FmsPettyCashPaymentVoucher;
import et.gov.eep.fcms.entity.FmsPettyCheqPaymControl;
/**
 *
 * @author AB
 */
@Local
public interface FmsPettyCashPaymentVoucherBeanLocal {

    public void edit(FmsPettyCashPaymentVoucher pettyCashPaymentEnty);

    public void create(FmsPettyCashPaymentVoucher pettyCashPaymentEnty);

    public void save(FmsPettyCheqPaymControl budgetControlHistory);

    public FmsPettyCashPaymentVoucher getSearchPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty);

    public FmsPettyCashPaymentVoucher getPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty);

    public FmsPettyCashPaymentVoucher getApprovedPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty);

    public List<String> getPrepareList(int approveStatus);

    public List<FmsPettyCashPaymentVoucher> findAll();
}
