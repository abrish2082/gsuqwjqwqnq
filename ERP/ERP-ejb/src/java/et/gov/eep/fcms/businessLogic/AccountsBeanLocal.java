/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsAccounts;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mora1
 */
@Local
public interface AccountsBeanLocal {

    public List<FmsAccounts> findAll();

    public void create(FmsAccounts subsidiary);

    /**
     *
     * @param subsidiary
     */
    public void edit(FmsAccounts subsidiary);

    public List<FmsAccounts> fmsAccountses(FmsAccounts subsidiary);

    public List<FmsAccounts> fmsAccountsesall();

    public FmsAccounts fmsAccountsDitle(FmsAccounts fmsAccounts);

    public FmsAccounts fmsAccountsByID(FmsAccounts fmsAccounts);

    public FmsAccounts fmsAccountsByCode(FmsAccounts fmsAccounts);

}
