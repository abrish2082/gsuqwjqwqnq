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
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.mapper.bank.FmsBankAccountFacade;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;

/**
 *
 * @author mubejbl
 */
@Stateless
public class bankAccountBean implements bankAccountBeanLocal {

    @EJB
    FmsBankAccountFacade fmsBankAccountFacade;

    @Override
    public void create(FmsBankAccount fmsBankAccount) {

        fmsBankAccountFacade.create(fmsBankAccount);

    }

    @Override
    public void edit(FmsBankAccount fmsBankAccount) {
        fmsBankAccountFacade.edit(fmsBankAccount);

    }

    @Override
    public List<FmsBankAccount> searchBankAcctByAccNumber(FmsBankBranchAccounts fmsBankBranchAccounts) {
        return fmsBankAccountFacade.searchBankAcctByAccNumber(fmsBankBranchAccounts);
    }

    @Override
    public FmsBankAccount getBankAccInfo(FmsBankAccount fmsBankAccount) {
        return fmsBankAccountFacade.getBankAccInfo(fmsBankAccount);
    }

    @Override
    public List<FmsBankAccount> getBranchName() {
        return fmsBankAccountFacade.findAll();
    }

    @Override
    public List<FmsBankAccount> getBankName() {
        return fmsBankAccountFacade.findAll();
    }

    @Override
    public List<FmsBankAccount> getBankID() {
        return fmsBankAccountFacade.findAll();
    }

    @Override
    public List<FmsBankAccount> getBranchNameById(FmsBankAccount fmsBankAccount) {
        return fmsBankAccountFacade.getBranchNameByID(fmsBankAccount);
    }

    @Override
    public FmsBankAccount getAccountNumberByBranchName(FmsBankAccount fmsBankAccount) {
        return fmsBankAccountFacade.getAccountNumberByBranchName(fmsBankAccount);
    }

    @Override
    public List<FmsBankAccount> getBankAccountNo() {
        return fmsBankAccountFacade.findAll();
    }

    @Override
    public FmsBankAccount getinfoByBranchName(FmsBankAccount fmsBankAccount) {
        return fmsBankAccountFacade.getinfoByBranchName(fmsBankAccount);
    }

    @Override
    public List<FmsBankAccount> searchAllBankAcc() {
        return fmsBankAccountFacade.findAll();
    }

    @Override
    public ArrayList<FmsBankAccount> searchByBankAndBranchName(FmsBankAccount fmsBankAccount) {
        return fmsBankAccountFacade.searchByBankAndBranchName(fmsBankAccount);
    }

    @Override
    public ArrayList<FmsBankAccount> searchBankAccByBankName(FmsBankAccount fmsBankAccount) {
        return fmsBankAccountFacade.searchBankAccByBankName(fmsBankAccount);
    }

}
