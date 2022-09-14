/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;

/**
 *
 * @author mubejbl
 */
@Local
public interface bankAccountBeanLocal {

    public void create(FmsBankAccount fmsBankAccount);

    public void edit(FmsBankAccount fmsBankAccount);

    public List<FmsBankAccount> getBranchName();

    public List<FmsBankAccount> searchAllBankAcc();

    public List<FmsBankAccount> getBankName();

    public List<FmsBankAccount> searchBankAcctByAccNumber(FmsBankBranchAccounts fmsBankBranchAccounts);

    public List<FmsBankAccount> getBankID();

    public List<FmsBankAccount> getBankAccountNo();

    public List<FmsBankAccount> getBranchNameById(FmsBankAccount fmsBankAccount);

    public FmsBankAccount getAccountNumberByBranchName(FmsBankAccount fmsBankAccount);

    public FmsBankAccount getBankAccInfo(FmsBankAccount fmsBankAccount);

    public FmsBankAccount getinfoByBranchName(FmsBankAccount fmsBankAccount);

    public ArrayList<FmsBankAccount> searchByBankAndBranchName(FmsBankAccount fmsBankAccount);

    public ArrayList<FmsBankAccount> searchBankAccByBankName(FmsBankAccount fmsBankAccount);
}
