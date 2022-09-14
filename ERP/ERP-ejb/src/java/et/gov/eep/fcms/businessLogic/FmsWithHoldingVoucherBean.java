/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsWithHoldingVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.mapper.FmsWithHoldingVoucherFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author AB
 */
@Stateless
public class FmsWithHoldingVoucherBean implements FmsWithHoldingVoucherBeanLocal {

    @EJB
    FmsWithHoldingVoucherFacade fmsWithHoldingFacade;

    /**
     *
     * @param withHoldingVoucher
     */
    @Override
    public void create(FmsWithHoldingVoucher withHoldingVoucher) {
        fmsWithHoldingFacade.create(withHoldingVoucher);
    }

    /**
     *
     * @param withHoldingVoucher
     * @return
     */
    @Override
    public List<FmsWithHoldingVoucher> searchWithHoldVoucheid(FmsWithHoldingVoucher withHoldingVoucher) {
        return fmsWithHoldingFacade.searchWithHoldVoucheid(withHoldingVoucher);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     *
     * @param withHoldingVoucher
     * @return
     */
    public FmsWithHoldingVoucher getSearchWithHoldingVoucherID(FmsWithHoldingVoucher withHoldingVoucher) {
        return fmsWithHoldingFacade.getSearchWithHoldingVoucherID(withHoldingVoucher);
    }

    /**
     *
     * @param withHoldingVoucher
     */
    @Override
    public void edit(FmsWithHoldingVoucher withHoldingVoucher) {
        fmsWithHoldingFacade.edit(withHoldingVoucher);
    }

    @Override
    public List<FmsChequePaymentVoucher> getchequePaymentNoList() {
      return  fmsWithHoldingFacade.getchequePaymentNoList();
    }
}
