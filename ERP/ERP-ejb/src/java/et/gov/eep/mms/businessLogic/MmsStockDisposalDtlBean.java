
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsStockDisposalDtl;
import et.gov.eep.mms.mapper.MmsStockDisposalDtlFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsStockDisposalDtlBean implements MmsStockDisposalDtlBeanLocal {

    @EJB
    MmsStockDisposalDtlFacade stDispFacade;
    
    @Override
    public List<MmsStockDisposalDtl> findAllItemCode() {
        return stDispFacade.findAll();
    }

    @Override
    public MmsStockDisposalDtl findbyitemcode(MmsStockDisposalDtl stockDispDetEntity) {
       return stDispFacade.findbyitemcode(stockDispDetEntity);
    }

   
}
