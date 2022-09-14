/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.bank.FmsBankCreditAdvice;
import et.gov.eep.fcms.mapper.bank.FmsBankCreditAdviceFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class bankCreditAdviceBean implements bankCreditAdviceBeanLocal {

    @EJB
    private FmsBankCreditAdviceFacade fmsBankCreditAdviceFacade;

    @Override
    public void create(FmsBankCreditAdvice fmsBankCreditAdvice) {
        fmsBankCreditAdviceFacade.create(fmsBankCreditAdvice);

    }

    @Override
    public void edit(FmsBankCreditAdvice fmsBankCreditAdvice) {
        fmsBankCreditAdviceFacade.edit(fmsBankCreditAdvice);
    }

    @Override
    public List<FmsBankCreditAdvice> getCreditAdviceDate(FmsBankCreditAdvice fmsBankCreditAdvice) {
        return fmsBankCreditAdviceFacade.getCreditAdviceDate(fmsBankCreditAdvice);
    }

    @Override
    public FmsBankCreditAdvice getAllData(FmsBankCreditAdvice fmsBankCreditAdvice) {
        return fmsBankCreditAdviceFacade.getAllDataByDateAndAcountNumber(fmsBankCreditAdvice);

    }

    @Override
    public List<FmsBankCreditAdvice> getCreditTransactions(FmsBankCreditAdvice fmsBankCreditAdvice) {
        return fmsBankCreditAdviceFacade.getCreditAdviceTransactions(fmsBankCreditAdvice);
    }

    @Override
    public List<FmsBankCreditAdvice> searchAllCredits(FmsBankCreditAdvice fmsBankCreditAdvice) {
        return fmsBankCreditAdviceFacade.findAll();
    }

    @Override
    public List<FmsBankCreditAdvice> searchAllByAcountNumberDateAndTranRef(FmsBankCreditAdvice fmsBankCreditAdvice) {
        return fmsBankCreditAdviceFacade.searchAllByAcountNumberDateAndTranRef(fmsBankCreditAdvice);
    }

}
