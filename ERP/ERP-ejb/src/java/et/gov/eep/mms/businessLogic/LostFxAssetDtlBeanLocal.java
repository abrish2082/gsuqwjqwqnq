
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface LostFxAssetDtlBeanLocal {

    public List<MmsLostFixedAssetDetail> findAllInfo();
    
}
