///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package et.gov.eep.fcms.businessLogic;
//
//import javax.ejb.Stateless;
//
///**
// *
// * @author kaleab
// */
//@Stateless
//public class FmsVatVoucherBean implements FmsVatVoucherBeanLocal {
//
//    // Add business logic below. (Right-click in editor and choose
//    // "Insert Code > Add Business Method")
//}
package et.gov.eep.fcms.businessLogic;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.FmsVatRecieptVoucher;
import java.util.List;
import et.gov.eep.fcms.mapper.FmsVatRecieptVoucherFacade;

@Stateless
public class FmsVatRecieptVoucherBean implements FmsVatRecieptVoucherBeanLocal {

    @EJB
    FmsVatRecieptVoucherFacade fmsVatFacade;

    @Override
    public void create(FmsVatRecieptVoucher fmsVatVoucher) {
        fmsVatFacade.create(fmsVatVoucher);
    }
}
