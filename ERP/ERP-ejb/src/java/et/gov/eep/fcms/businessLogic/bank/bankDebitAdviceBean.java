/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.bank.FmsBankDebitAdvice;
import et.gov.eep.fcms.mapper.bank.FmsBankDebitAdviceFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class bankDebitAdviceBean implements bankDebitAdviceBeanLocal {

    @EJB
    private FmsBankDebitAdviceFacade fmsBankDebitAdviceFacade;

    @Override
    public void create(FmsBankDebitAdvice fmsBankDebitAdvice) {
        fmsBankDebitAdviceFacade.create(fmsBankDebitAdvice);

    }

    @Override
    public void edit(FmsBankDebitAdvice fmsBankDebitAdvice) {
        fmsBankDebitAdviceFacade.edit(fmsBankDebitAdvice);
    }

    @Override
    public List<FmsBankDebitAdvice> getCreditAdviceDate(FmsBankDebitAdvice fmsBankDebitAdvice) {
        return fmsBankDebitAdviceFacade.getCreditAdviceDate(fmsBankDebitAdvice);
    }

    @Override
    public FmsBankDebitAdvice getData(FmsBankDebitAdvice fmsBankDebitAdvice) {
        return fmsBankDebitAdviceFacade.getAllDataByDateAndAcountNumber(fmsBankDebitAdvice);

    }

    @Override
    public List<FmsBankDebitAdvice> searchAllDebits(FmsBankDebitAdvice fmsBankDebitAdvice) {
        return fmsBankDebitAdviceFacade.findAll();
    }

    @Override
    public List<FmsBankDebitAdvice> getDebitAdviceTransactions(FmsBankDebitAdvice fmsBankDebitAdvice) {
        return fmsBankDebitAdviceFacade.getDebitAdviceTransactions(fmsBankDebitAdvice);
    }

    @Override
    public List<FmsBankDebitAdvice> searchAllByAcountNumberDateAndTranRef(FmsBankDebitAdvice fmsBankDebitAdvice) {
        return fmsBankDebitAdviceFacade.searchAllByAcountNumberDateAndTranRef(fmsBankDebitAdvice);
    }

    @Override
    public FmsBankDebitAdvice getBankAdviceData(FmsBankDebitAdvice fmsBankDebitAdvice) {
        return fmsBankDebitAdviceFacade.getBankAdviceData(fmsBankDebitAdvice);

    }
}
