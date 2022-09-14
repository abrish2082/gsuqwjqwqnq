/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mora
 */
@Stateless
public class LuCurrencyBean implements LuCurrencyBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    FmsLuCurrencyFacade currencyFacade;
    // </editor-fold>

    @Override
    public void create(FmsLuCurrency fmsLuCurrency) {
        currencyFacade.create(fmsLuCurrency);
    }

    @Override
    public void edit(FmsLuCurrency fmsLuCurrency) {
        currencyFacade.edit(fmsLuCurrency);
    }

    @Override
    public ArrayList<FmsLuCurrency> searchCurrencys(FmsLuCurrency currency) {
        return currencyFacade.searchFmsLuCurrency(currency);
    }

    @Override
    public List<FmsLuCurrency> findAll() {
        return currencyFacade.findAll();
    }

    @Override
    public ArrayList<FmsLuCurrency> searchCurrencyList(FmsLuCurrency currency) {
        return currencyFacade.getAllCurrencyList(currency);
    }

    @Override
    public FmsLuCurrency getCurrencyinfo(FmsLuCurrency currency) {
        return currencyFacade.getFmsLuCurrencyInfo(currency);
    }

}
