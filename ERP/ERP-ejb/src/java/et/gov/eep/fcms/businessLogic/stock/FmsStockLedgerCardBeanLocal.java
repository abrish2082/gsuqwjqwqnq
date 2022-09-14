/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.stock;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.stock.FmsStockLedgerCard;

/**
 *
 * @author muller
 */
@Local
public interface FmsStockLedgerCardBeanLocal {

    public void saveOrUpdate(FmsStockLedgerCard fmsStockLedgerCard);

    public void edit(FmsStockLedgerCard fmsStockLedgerCard);

    public int saveStockLedger(String statusType, String storeNos);

    public List<FmsStockLedgerCard> getMatCode(FmsStockLedgerCard fmsStockLedgerCard);

    public List<FmsStockLedgerCard> findByMatCode(FmsStockLedgerCard fmsStockLedgerCard);

    public List<FmsStockLedgerCard> findByStoreNo(FmsStockLedgerCard fmsStockLedgerCard);

    public List<FmsStockLedgerCard> findByAll(FmsStockLedgerCard fmsStockLedgerCard);

    public List<FmsStockLedgerCard> findAll(FmsStockLedgerCard fmsStockLedgerCard);

    public List<FmsStockLedgerCard> searchGRNListToSave(String sivValue, String storeNo);

    public List<FmsStockLedgerCard> searchSIVListToSave(String sivValue, String storeNo);

    public List<FmsStockLedgerCard> searchISIVIListToSave(String sivValue, String storeNo);//

    public List<FmsStockLedgerCard> searchISIVRListToSave(String sivValue, String storeNo);

    public List<FmsStockLedgerCard> searchSRNListToSave(String sivValue, String storeNo);

    public List<FmsStockLedgerCard> searchByStoreId(int storeId);
}
