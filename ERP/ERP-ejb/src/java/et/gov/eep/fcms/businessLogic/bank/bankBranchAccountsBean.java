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
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.mapper.bank.FmsBankBranchAccountsFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class bankBranchAccountsBean implements bankBranchAccountsBeanLocal {

    @EJB
    private FmsBankBranchAccountsFacade fmsBankBranchAccountsFacade;

    @Override
    public ArrayList<FmsBankBranchAccounts> searchBankAcctByAccNumber(FmsBankBranchAccounts fmsBankBranchAccounts) {
        return fmsBankBranchAccountsFacade.searchBankAcctByAccNumber(fmsBankBranchAccounts);
    }

    @Override
    public FmsBankBranchAccounts getBankAccDetailInfo(FmsBankBranchAccounts fmsBankBranchAccounts) {
        return fmsBankBranchAccountsFacade.getBankAccDetailInfo(fmsBankBranchAccounts);
    }

    @Override
    public List<FmsBankBranchAccounts> getBankAccountNo() {
        return fmsBankBranchAccountsFacade.findAll();
    }

    @Override
    public List<FmsBankBranchAccounts> getListinfo(FmsBankBranchAccounts bankBranchAccounts) {
        return fmsBankBranchAccountsFacade.getListinfo(bankBranchAccounts);
    }

    @Override
    public void edit(FmsBankBranchAccounts fmsBankBranchAccounts) {
        fmsBankBranchAccountsFacade.edit(fmsBankBranchAccounts);

    }

    @Override
    public List<FmsBankBranchAccounts> getAccountNumberByBankAccountId(FmsBankBranchAccounts fmsBankBranchAccounts) {
        return fmsBankBranchAccountsFacade.getAccountNumberByBankAccountId(fmsBankBranchAccounts);
    }

    @Override
    public List<FmsBankBranchAccounts> getAcctNoFromBankBranchAccounts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FmsBankBranchAccounts getBalanceByAcctNo(FmsBankBranchAccounts fmsBankBranchAccounts) {
        return fmsBankBranchAccountsFacade.getBalanceByAcctNo(fmsBankBranchAccounts);
    }

    @Override
    public boolean findDup(FmsBankBranchAccounts fmsBankBranchAccounts) {
        return fmsBankBranchAccountsFacade.findDup(fmsBankBranchAccounts);
    }
}
