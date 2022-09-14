
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsStockDisposalDtl;
import java.util.List;
import javax.ejb.Local; 
 

/**
 *
 * @author w_station
 */
@Local
public interface MmsStockDisposalDtlBeanLocal {

    public List<MmsStockDisposalDtl> findAllItemCode(); 

    public MmsStockDisposalDtl findbyitemcode(MmsStockDisposalDtl stockDispDetEntity);
       
    
}