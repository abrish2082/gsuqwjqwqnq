/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.bank.FmsBankCashDeposit;

/**
 *
 * @author mubejbl
 */
@Local
public interface bankCashDepositBeanLocal {

    public void create(FmsBankCashDeposit fmsBankCashDeposit);

    public void edit(FmsBankCashDeposit fmsBankCashDeposit);

    public ArrayList<FmsBankCashDeposit> searchCashDepositByDate(FmsBankCashDeposit fmsBankCashDeposit);

    public List<FmsBankCashDeposit> searchAllCashs(FmsBankCashDeposit fmsBankCashDeposit);

    public List<FmsBankCashDeposit> getCashDepositDate(FmsBankCashDeposit fmsBankCashDeposit);

    public List<FmsBankCashDeposit> getCashDepositTransactions(FmsBankCashDeposit fmsBankCashDeposit);

    public List<FmsBankCashDeposit> searchAllByAcountNumberDateAndTranRef(FmsBankCashDeposit fmsBankCashDeposit);

    public FmsBankCashDeposit getData(FmsBankCashDeposit fmsBankCashDeposit);

    public FmsBankCashDeposit getBankCashDepositInfo(FmsBankCashDeposit fmsBankCashDeposit);

}
