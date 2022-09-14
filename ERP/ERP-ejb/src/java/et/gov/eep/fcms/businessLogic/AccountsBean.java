/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsAccounts;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.mapper.FmsAccountsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mora1
 */
@Stateless
public class AccountsBean implements AccountsBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    FmsAccountsFacade accountsFacade;

    @Override
    public List<FmsAccounts> findAll() {
        return accountsFacade.findAll();
    }

    @Override
    public void create(FmsAccounts subsidiary) {
        accountsFacade.create(subsidiary);
    }

    /**
     *
     * @param subsidiary
     */
    @Override
    public void edit(FmsAccounts subsidiary) {
        accountsFacade.edit(subsidiary);
    }

    @Override
    public List<FmsAccounts> fmsAccountsesall() {
        return accountsFacade.findAll();
    }
     @Override
    public List<FmsAccounts> fmsAccountses(FmsAccounts subsidiary) {
        return accountsFacade.fmsAccountses(subsidiary);
    }

    @Override
    public FmsAccounts fmsAccountsDitle(FmsAccounts fmsAccounts) {
        return accountsFacade.fmsAccountsDitle(fmsAccounts);
    }

    @Override
    public FmsAccounts fmsAccountsByID(FmsAccounts fmsAccounts) {
        return accountsFacade.fmsAccountsbyID(fmsAccounts);
    }

    @Override
    public FmsAccounts fmsAccountsByCode(FmsAccounts fmsAccounts) {
        return accountsFacade.fmsAccountsbyCode(fmsAccounts);
    }
}
