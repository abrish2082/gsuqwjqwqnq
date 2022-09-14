/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.bank.FmsBankDebitAdvice;

/**
 *
 * @author mubejbl
 */
@Local
public interface bankDebitAdviceBeanLocal {

    public void create(FmsBankDebitAdvice fmsBankDebitAdvice);

    public void edit(FmsBankDebitAdvice fmsBankDebitAdvice);

    public List<FmsBankDebitAdvice> searchAllDebits(FmsBankDebitAdvice fmsBankDebitAdvice);

    public List<FmsBankDebitAdvice> getCreditAdviceDate(FmsBankDebitAdvice fmsBankDebitAdvice);

    public List<FmsBankDebitAdvice> getDebitAdviceTransactions(FmsBankDebitAdvice fmsBankDebitAdvice);

    public List<FmsBankDebitAdvice> searchAllByAcountNumberDateAndTranRef(FmsBankDebitAdvice fmsBankDebitAdvice);

    public FmsBankDebitAdvice getData(FmsBankDebitAdvice fmsBankDebitAdvice);

    public FmsBankDebitAdvice getBankAdviceData(FmsBankDebitAdvice fmsBankDebitAdvice);
}
