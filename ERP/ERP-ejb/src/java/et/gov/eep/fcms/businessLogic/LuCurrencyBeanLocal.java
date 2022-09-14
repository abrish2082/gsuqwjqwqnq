/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mora
 */
@Local
public interface LuCurrencyBeanLocal {

    /**
     *
     * @param fmsLuCurrency
     */
    public void create(FmsLuCurrency fmsLuCurrency);

    /**
     *
     * @param fmsLuCurrency
     */
    public void edit(FmsLuCurrency fmsLuCurrency);

    /**
     *
     * @param fmsLuCurrency
     * @return
     */
    public ArrayList<FmsLuCurrency> searchCurrencys(FmsLuCurrency fmsLuCurrency);

    /**
     *
     * @param fmsLuCurrency
     * @return
     */
    public FmsLuCurrency getCurrencyinfo(FmsLuCurrency fmsLuCurrency);

    public ArrayList<FmsLuCurrency> searchCurrencyList(FmsLuCurrency fmsLuCurrency);

    public List<FmsLuCurrency> findAll();
}
