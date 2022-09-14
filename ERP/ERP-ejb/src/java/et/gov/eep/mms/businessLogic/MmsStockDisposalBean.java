
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStockDisposal;
import java.util.ArrayList;
import et.gov.eep.mms.mapper.MmsStockDisposalFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsStockDisposalBean implements MmsStockDisposalBeanLocal {

    @EJB
    MmsStockDisposalFacade stockDisposalFacade;
    
    @Override
    public List<MmsStockDisposal> searchStockDisposalByParameterPrefix(MmsStockDisposal stockDispEntity) {
       return stockDisposalFacade.searchStockDisposalByParameterPrefix(stockDispEntity);
    }

    @Override
    public MmsStockDisposal getLastStockDisposalId() {
        return stockDisposalFacade.getLastStockDisposalId();
    }

    @Override
    public void create(MmsStockDisposal stockDispEntity) {
        stockDisposalFacade.create(stockDispEntity);
    }

    @Override
    public void edit(MmsStockDisposal stockDispEntity) {
       stockDisposalFacade.edit(stockDispEntity);
    }

    @Override
    public ArrayList<MmsStockDisposal> searchByStockDispNo(MmsStockDisposal stockDispEntity) {
       return stockDisposalFacade.searchByStockDispNo(stockDispEntity);
    }

    @Override
    public List<MmsStockDisposal> findAllStoreName() {
       return stockDisposalFacade.findAll();
    }

    @Override
    public List<MmsStockDisposal> findAllInfo() {
        return stockDisposalFacade.findAll();
    }

    @Override
    public List<MmsStockDisposal> searchByStoreName1(MmsStockDisposal stockDispEntity) {
         return stockDisposalFacade.searchByStoreName1(stockDispEntity);
    }

    @Override
    public MmsStockDisposal getSelectedRequest(Integer stockId) {
       return stockDisposalFacade.getSelectedRequest(stockId);
    }

    @Override
    public List<MmsStockDisposal> searchStockDisposalByParameterPrefixAndDispPrep(MmsStockDisposal stockDispEntity) {
         return stockDisposalFacade.searchStockDisposalByParameterPrefixAndDispPrep(stockDispEntity);
    }

    @Override
    public List<MmsStockDisposal> findDispListByWfStatus(int PREPARE_VALUE) {
       return stockDisposalFacade.findDispListByWfStatus(PREPARE_VALUE);
    }
 @Override
    public List<MmsStockDisposal> findDispListByWfStatus1(int stkStatus) {
       return stockDisposalFacade.findDispListByWfStatus1(stkStatus);
    }

    @Override
    public List<MmsStockDisposal> searchAllDispInfoByPreparerId(Integer preparedBy) {
          return stockDisposalFacade.searchAllDispInfoByPreparerId(preparedBy);
    }

    @Override
    public List<MmsStockDisposal> getStockDisposalListsByParameter(MmsStockDisposal stockDispEntity) {
        return stockDisposalFacade.getStockDisposalListsByParameter(stockDispEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return stockDisposalFacade.getColumnNameList();
    }

  

    
}
