/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsBeginningBalance;
import et.gov.eep.fcms.mapper.FmsBeginningBalanceFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class BeginningBalanceBean implements BeginningBalanceBeanLocal {

    @EJB
    FmsBeginningBalanceFacade fmsBeginningBalanceFacade;

    @Override
    public void create(FmsBeginningBalance fmsBeginningBalance) {
        fmsBeginningBalanceFacade.create(fmsBeginningBalance);
    }

    @Override
    public void edit(FmsBeginningBalance fmsBeginningBalance) {
        fmsBeginningBalanceFacade.edit(fmsBeginningBalance);
    }

    @Override
    public FmsBeginningBalance Acount_Period(FmsBeginningBalance beginningBalance) {
        return fmsBeginningBalanceFacade.beginningBalancesearch(beginningBalance);
    }
}
