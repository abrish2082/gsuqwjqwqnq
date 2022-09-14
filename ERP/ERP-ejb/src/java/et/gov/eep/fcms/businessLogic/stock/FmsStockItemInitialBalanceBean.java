/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.stock;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;
import et.gov.eep.fcms.mapper.stock.FmsStockLedgerCardFacade;
/**
 *
 * @author muller
 */
@Stateless
public class FmsStockItemInitialBalanceBean implements FmsStockItemInitialBalanceBeanLocal {

    @EJB
    FmsStockLedgerCardFacade stockLedgerCardFacade;

    @Override
    public void create(FmsStockLedgerCard fmsStockLedgerCard) {
        stockLedgerCardFacade.create(fmsStockLedgerCard);
    }

    @Override
    public void edit(FmsStockLedgerCard fmsStockLedgerCard) {
        stockLedgerCardFacade.edit(fmsStockLedgerCard);
    }

    @Override
    public void saveorUpdate(FmsStockLedgerCard card) {
        stockLedgerCardFacade.saveOrUpdate(card);
    }

}
