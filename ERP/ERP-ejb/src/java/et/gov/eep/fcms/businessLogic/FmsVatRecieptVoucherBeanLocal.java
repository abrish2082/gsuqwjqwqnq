/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsVatRecieptVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kaleab
 */
@Local
public interface FmsVatRecieptVoucherBeanLocal {
  public void create(FmsVatRecieptVoucher fmsVatVoucher);
 }
