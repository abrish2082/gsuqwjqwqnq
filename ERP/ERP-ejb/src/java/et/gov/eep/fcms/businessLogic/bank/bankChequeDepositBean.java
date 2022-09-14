/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.bank.FmsBankChequeDeposit;
import et.gov.eep.fcms.mapper.bank.FmsBankChequeDepositFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class bankChequeDepositBean implements bankChequeDepositBeanLocal {

    @EJB
    private FmsBankChequeDepositFacade fmsBankChequeDepositFacade;

    @Override
    public void create(FmsBankChequeDeposit fmsBankChequeDeposit) {
        fmsBankChequeDepositFacade.create(fmsBankChequeDeposit);
    }

    @Override
    public void edit(FmsBankChequeDeposit fmsBankChequeDeposit) {
        fmsBankChequeDepositFacade.edit(fmsBankChequeDeposit);
    }

    @Override
    public List<FmsBankChequeDeposit> getChequeDepositDate(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.getChequeDepositDate(fmsBankChequeDeposit);
    }

    @Override
    public ArrayList<FmsBankChequeDeposit> searchChequeDepositByDate(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.searchChequeDepositByDate(fmsBankChequeDeposit);
    }

    @Override
    public List<FmsBankChequeDeposit> getChequeDepositTransactionsByChequeNo(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.getChequeDepositTransactionsByChequeNo(fmsBankChequeDeposit);
    }

    @Override
    public FmsBankChequeDeposit getData(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.getAllDataByDateAndAcountNumber(fmsBankChequeDeposit);
    }

    @Override
    public List<FmsBankChequeDeposit> searchAllCheques(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.findAll();
    }

    @Override
    public List<FmsBankChequeDeposit> searchAllByAcountNumberDateAndTranRef(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.searchAllByAcountNumberDateAndTranRef(fmsBankChequeDeposit);
    }

    @Override
    public FmsBankChequeDeposit getBankChequeDepositInfo(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.getBankChequeDepositInfo(fmsBankChequeDeposit);
    }

    @Override
    public List<FmsBankChequeDeposit> getChequeDepositTransactions(FmsBankChequeDeposit fmsBankChequeDeposit) {
        return fmsBankChequeDepositFacade.getChequeDepositTransactions(fmsBankChequeDeposit);
    }
}
