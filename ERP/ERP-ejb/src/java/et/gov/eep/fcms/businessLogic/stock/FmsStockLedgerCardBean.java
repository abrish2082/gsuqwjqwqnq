/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.stock;


import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;
import et.gov.eep.fcms.mapper.stock.FmsStockLedgerCardFacade;
/**
 *
 * @author muller
 */
@Stateless
public class FmsStockLedgerCardBean implements FmsStockLedgerCardBeanLocal {

    @EJB
    FmsStockLedgerCardFacade ledgerCardFacade;

    @Override
    public List<FmsStockLedgerCard> getMatCode(FmsStockLedgerCard fmsStockLedgerCard) {
        return ledgerCardFacade.getMatCode(fmsStockLedgerCard);
    }

    @Override
    public List<FmsStockLedgerCard> findByMatCode(FmsStockLedgerCard fmsStockLedgerCard) {
        return ledgerCardFacade.findByMatCode(fmsStockLedgerCard);
    }

    @Override
    public void saveOrUpdate(FmsStockLedgerCard fmsStockLedgerCard) {
        ledgerCardFacade.saveOrUpdate(fmsStockLedgerCard);
    }

    @Override
    public void edit(FmsStockLedgerCard fmsStockLedgerCard) {
        ledgerCardFacade.edit(fmsStockLedgerCard);
    }

    @Override
    public List<FmsStockLedgerCard> findByStoreNo(FmsStockLedgerCard fmsStockLedgerCard) {
        return ledgerCardFacade.findByStoreNo(fmsStockLedgerCard);
    }

    @Override
    public List<FmsStockLedgerCard> findByAll(FmsStockLedgerCard fmsStockLedgerCard) {
        return ledgerCardFacade.findByAll(fmsStockLedgerCard);
    }

    @Override
    public List<FmsStockLedgerCard> findAll(FmsStockLedgerCard fmsStockLedgerCard) {
        return ledgerCardFacade.findAll();
    }

    @Override
    public int saveStockLedger(String statusType, String storeNos) {
        return ledgerCardFacade.saveStockLedger(statusType, storeNos);
    }

    @Override
    public List<FmsStockLedgerCard> searchGRNListToSave(String sivValue, String storeNo) {
        return ledgerCardFacade.searchGRNListToSave(sivValue, storeNo);
    }

    @Override
    public List<FmsStockLedgerCard> searchSIVListToSave(String isiv_no, String storeNo) {
        return ledgerCardFacade.searchSIVListToSave(isiv_no, storeNo);
    }

    @Override
    public List<FmsStockLedgerCard> searchISIVIListToSave(String refNo, String storeNo) {
        return ledgerCardFacade.searchISIVIListToSave(refNo, storeNo);
    }

    @Override
    public List<FmsStockLedgerCard> searchISIVRListToSave(String refNo, String storeNo) {
        return ledgerCardFacade.searchISIVRListToSave(refNo, storeNo);
    }

    @Override
    public List<FmsStockLedgerCard> searchSRNListToSave(String refNo, String storeNo) {
        return ledgerCardFacade.searchSRNListToSave(refNo, storeNo);
    }

    @Override
    public List<FmsStockLedgerCard> searchByStoreId(int storeId) {
        return ledgerCardFacade.searchByStoreId(storeId);
    }

}
