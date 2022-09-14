/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.mapper.FmsAccountUseFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * 
 * @author AB
 */
@Stateless
public class FmsAccountUseBean implements FmsAccountUseBeanLocal {
    @EJB
    private FmsAccountUseFacade accountUseFacade;

    /**
     *
     * @param bankCode
     * @param date
     * @return
     */
    @Override
    public List<FmsAccountUse> getChequePaymentVouchers(String bankCode, String date) {
        return accountUseFacade.getChequePaymentVouchers(bankCode, date);
    }

    /**
     *
     * @param bankCode
     * @param date
     * @return
     */
    @Override
    public List<FmsAccountUse> getCashReceiptVouchers(String bankCode, String date) {
        return accountUseFacade.getCashReceiptVouchers(bankCode, date);
    }

    /**
     *
     * @param statmentDate
     * @param dateSatment
     * @param bankCode
     * @return
     */
    @Override
    public String getGLBankReco(String statmentDate,String dateSatment, String bankCode) {
        return accountUseFacade.getGLBankReco(statmentDate,dateSatment, bankCode);
    }

    /**
     *
     * @param budgetYear
     * @param reportMonth
     * @return
     */ 
    @Override
    public List getPreviousBalance(String budgetYear, String reportMonth) {
        return accountUseFacade.getPreviousBalance(budgetYear, reportMonth);
    }

    /**
     *
     * @param accountUse
     */
    @Override
    public void create(FmsAccountUse accountUse) {
        accountUseFacade.create(accountUse);
    }

    /**
     *
     * @param accountUse
     */
    @Override
    public void edit(FmsAccountUse accountUse) {
        accountUseFacade.edit(accountUse);
    }


    @Override
    public  List<FmsAccountUse> getlagerd(HashMap hashMap) {
        return accountUseFacade.LedgerReport(hashMap);
    }

    @Override
    public List<FmsAccountUse> getlagerd_SL(HashMap hashMap) {
        return accountUseFacade.LedgerReport_SL(hashMap);
    }

    @Override
    public List<FmsAccountUse> getlagerd_GL(HashMap hashMap) {
        return accountUseFacade.LedgerReport_GL(hashMap);
    }

    @Override
    public List<FmsAccountUse> getlagerd_Sys(HashMap hashMap) {
        return accountUseFacade.LedgerReport_system(hashMap);
    }

    @Override
    public List<FmsAccountUse> gettrilSL(HashMap hashMap) {
        return accountUseFacade.trilReport_SL(hashMap);
    }

    @Override
    public ArrayList<FmsAccountUse> fetchCPOAccUse(FmsCashPaymentOrder fmsCashPaymentOrder) {
        return accountUseFacade.fetchCPOAccUse(fmsCashPaymentOrder);
    }

    @Override
    public ArrayList<FmsAccountUse> fetchCHPVAccUse(FmsChequePaymentVoucher fmsChequePaymentVoucher) {
        return accountUseFacade.fetchCHPVAccUse(fmsChequePaymentVoucher);
    }
    
    @Override
    public FmsAccountUse getAccUse(FmsAccountUse fmsAccountUse) {
        return accountUseFacade.getAccUse(fmsAccountUse);
    }
}
