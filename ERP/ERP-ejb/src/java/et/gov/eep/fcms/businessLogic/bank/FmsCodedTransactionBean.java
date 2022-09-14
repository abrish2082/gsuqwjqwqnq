/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.bank.FmsCodedTransaction;
import et.gov.eep.fcms.mapper.bank.FmsCodedTransactionFacade;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsCodedTransactionBean implements FmsCodedTransactionBeanLocal {

    @EJB
    private FmsCodedTransactionFacade fmsCodedTransactionFacade;

    @Override
    public void create(FmsCodedTransaction fmsCodedTransaction) {
        fmsCodedTransactionFacade.create(fmsCodedTransaction);
    }

    @Override
    public void edit(FmsCodedTransaction fmsCodedTransaction) {
        fmsCodedTransactionFacade.edit(fmsCodedTransaction);
    }

    @Override
    public List<FmsCodedTransaction> getCodedTransactionInfo(FmsLuSystem fmsSystem) {
        return fmsCodedTransactionFacade.getCodedTransactionInfo(fmsSystem);
    }

    @Override
    public List<FmsCodedTransaction> getCodedTransactionsByTranRef(FmsCodedTransaction fmsCodedTransaction) {
        return fmsCodedTransactionFacade.getCodedTransactionsByTranRef(fmsCodedTransaction);
    }

    @Override
    public List<FmsCodedTransaction> getCashReceiptVouchers(String bankCode, String date) {
        return fmsCodedTransactionFacade.getCashReceiptVouchers(bankCode, date);
    }

    @Override
    public List<FmsCodedTransaction> getChequePaymentVouchers(String bankCode, String date) {
        return fmsCodedTransactionFacade.getChequePaymentVouchers(bankCode, date);
    }

    @Override
    public String getGLBankReco(String statmentDate, String dateSatment, String bankCode) {
        return fmsCodedTransactionFacade.getGLBankReco(statmentDate, dateSatment, bankCode);
    }

}
