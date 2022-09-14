/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.bank.FmsBankChequeDeposit;

/**
 *
 * @author mubejbl
 */
@Local
public interface bankChequeDepositBeanLocal {

    public void create(FmsBankChequeDeposit fmsBankChequeDeposit);

    public void edit(FmsBankChequeDeposit fmsBankChequeDeposit);

    public ArrayList<FmsBankChequeDeposit> searchChequeDepositByDate(FmsBankChequeDeposit fmsBankChequeDeposit);

    public List<FmsBankChequeDeposit> searchAllCheques(FmsBankChequeDeposit fmsBankChequeDeposit);

    public List<FmsBankChequeDeposit> getChequeDepositDate(FmsBankChequeDeposit fmsBankChequeDeposit);

    public List<FmsBankChequeDeposit> getChequeDepositTransactionsByChequeNo(FmsBankChequeDeposit fmsBankChequeDeposit);

    public List<FmsBankChequeDeposit> getChequeDepositTransactions(FmsBankChequeDeposit fmsBankChequeDeposit);

    public List<FmsBankChequeDeposit> searchAllByAcountNumberDateAndTranRef(FmsBankChequeDeposit fmsBankChequeDeposit);

    public FmsBankChequeDeposit getData(FmsBankChequeDeposit fmsBankChequeDeposit);

    public FmsBankChequeDeposit getBankChequeDepositInfo(FmsBankChequeDeposit fmsBankChequeDeposit);
}
