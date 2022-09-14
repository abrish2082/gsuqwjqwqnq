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
import et.gov.eep.fcms.entity.bank.FmsBankCashDeposit;
import et.gov.eep.fcms.mapper.bank.FmsBankCashDepositFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class bankCashDepositBean implements bankCashDepositBeanLocal {

    @EJB
    private FmsBankCashDepositFacade fmsBankCashDepositFacade;

    @Override
    public void create(FmsBankCashDeposit fmsBankCashDeposit) {
        fmsBankCashDepositFacade.create(fmsBankCashDeposit);
    }

    @Override
    public void edit(FmsBankCashDeposit fmsBankCashDeposit) {
        fmsBankCashDepositFacade.edit(fmsBankCashDeposit);
    }

    @Override
    public ArrayList<FmsBankCashDeposit> searchCashDepositByDate(FmsBankCashDeposit fmsBankCashDeposit) {
        return fmsBankCashDepositFacade.searchCashDepositByDate(fmsBankCashDeposit);
    }

    @Override
    public FmsBankCashDeposit getBankCashDepositInfo(FmsBankCashDeposit fmsBankCashDeposit) {
        return fmsBankCashDepositFacade.getBankCashDepositInfo(fmsBankCashDeposit);
    }

    @Override
    public List<FmsBankCashDeposit> getCashDepositDate(FmsBankCashDeposit fmsBankCashDeposit) {
        return fmsBankCashDepositFacade.getCashDepositDate(fmsBankCashDeposit);
    }

    @Override
    public FmsBankCashDeposit getData(FmsBankCashDeposit fmsBankCashDeposit) {
        return fmsBankCashDepositFacade.searchByDateAcountNumberAndTranRef(fmsBankCashDeposit);
    }

    @Override
    public List<FmsBankCashDeposit> getCashDepositTransactions(FmsBankCashDeposit fmsBankCashDeposit) {
        return fmsBankCashDepositFacade.getCashDepositTransactions(fmsBankCashDeposit);
    }

    @Override
    public List<FmsBankCashDeposit> searchAllCashs(FmsBankCashDeposit fmsBankCashDeposit) {
        return fmsBankCashDepositFacade.findAll();
    }

    @Override
    public List<FmsBankCashDeposit> searchAllByAcountNumberDateAndTranRef(FmsBankCashDeposit fmsBankCashDeposit) {
        return fmsBankCashDepositFacade.searchAllByAcountNumberDateAndTranRef(fmsBankCashDeposit);
    }

}
