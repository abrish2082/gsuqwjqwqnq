/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.pettyCash;


import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.FmsPettyCashPaymentVoucher;
import et.gov.eep.fcms.entity.FmsPettyCheqPaymControl;
import et.gov.eep.fcms.mapper.FmsPettyCashPaymentVoucherFacade;
import et.gov.eep.fcms.mapper.FmsPettyCheqPaymControlFacade;
/**
 *
 * @author AB
 */
@Stateless
public class FmsPettyCashPaymentVoucherBean implements FmsPettyCashPaymentVoucherBeanLocal {

    @EJB
    FmsPettyCheqPaymControlFacade PettyCheqPaymControlFacade;
    @EJB
    FmsPettyCashPaymentVoucherFacade PettyCashPaymentFacade;

    @Override
    public void edit(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        PettyCashPaymentFacade.edit(pettyCashPaymentEnty);
    }

    @Override
    public void create(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        PettyCashPaymentFacade.create(pettyCashPaymentEnty);
    }

    @Override
    public FmsPettyCashPaymentVoucher getSearchPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        return PettyCashPaymentFacade.getSearchPettyCashPayment(pettyCashPaymentEnty);
    }

    @Override
    public List<String> getPrepareList(int approveStatus) {
        return PettyCashPaymentFacade.getPrepareList(approveStatus);
    }

    @Override
    public List<FmsPettyCashPaymentVoucher> findAll() {
        return PettyCashPaymentFacade.findpityCash();
    }

    @Override
    public FmsPettyCashPaymentVoucher getPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        return PettyCashPaymentFacade.getPettyCashPayment(pettyCashPaymentEnty);
    }

    @Override
    public FmsPettyCashPaymentVoucher getApprovedPettyCashPayment(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        return PettyCashPaymentFacade.getApprovedPettyCashPayment(pettyCashPaymentEnty);
    }

    @Override
    public void save(FmsPettyCheqPaymControl budgetControlHistory) {
        PettyCheqPaymControlFacade.create(budgetControlHistory);
    }

}
