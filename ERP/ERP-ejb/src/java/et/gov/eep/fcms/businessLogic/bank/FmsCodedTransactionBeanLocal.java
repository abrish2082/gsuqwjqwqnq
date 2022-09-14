/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.bank.FmsCodedTransaction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;

/**
 *
 * @author mubejbl
 */
@Local
public interface FmsCodedTransactionBeanLocal {

    public void create(FmsCodedTransaction fmsCodedTransaction);

    public void edit(FmsCodedTransaction fmsCodedTransaction);

    public List<FmsCodedTransaction> getCodedTransactionInfo(FmsLuSystem fmsSystem);

    public List<FmsCodedTransaction> getCodedTransactionsByTranRef(FmsCodedTransaction fmsCodedTransaction);

    public List<FmsCodedTransaction> getCashReceiptVouchers(String bankCode, String date);

    public List<FmsCodedTransaction> getChequePaymentVouchers(String bankCode, String date);

    public String getGLBankReco(String statmentDate, String dateSatment, String bankCode);

}
