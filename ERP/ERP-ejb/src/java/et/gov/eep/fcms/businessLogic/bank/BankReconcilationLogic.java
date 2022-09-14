/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;


import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.mapper.bank.Bank_Reconciliation_Facade;
/**
 *
 * @author sura
 */
@Stateless
public class BankReconcilationLogic {

    @EJB
    private Bank_Reconciliation_Facade reconciliation_Facade;

    public void saveOrUpdate(FmsBankBranchAccounts bankAccount) {
        reconciliation_Facade.saveOrUpdate(bankAccount);
    }

    public List<FmsBankBranchAccounts> findAll() {
        return reconciliation_Facade.findAll();
    }
}
