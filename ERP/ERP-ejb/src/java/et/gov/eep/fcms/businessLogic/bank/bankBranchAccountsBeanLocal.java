/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;

/**
 *
 * @author mubejbl
 */
@Local
public interface bankBranchAccountsBeanLocal {

    public void edit(FmsBankBranchAccounts fmsBankBranchAccounts);

    public FmsBankBranchAccounts getBankAccDetailInfo(FmsBankBranchAccounts fmsBankBranchAccounts);

    public FmsBankBranchAccounts getBalanceByAcctNo(FmsBankBranchAccounts fmsBankBranchAccounts);

    public List<FmsBankBranchAccounts> getBankAccountNo();

    public List<FmsBankBranchAccounts> getAcctNoFromBankBranchAccounts();

    public List<FmsBankBranchAccounts> getAccountNumberByBankAccountId(FmsBankBranchAccounts fmsBankBranchAccounts);

    public List<FmsBankBranchAccounts> getListinfo(FmsBankBranchAccounts bankBranchAccounts);

    public ArrayList<FmsBankBranchAccounts> searchBankAcctByAccNumber(FmsBankBranchAccounts fmsBankBranchAccounts);

    public boolean findDup(FmsBankBranchAccounts fmsBankBranchAccounts);

}
