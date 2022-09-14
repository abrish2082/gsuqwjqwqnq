
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFixedAssetReturnDtl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsFixedAssetReturnDtlBeanLocal {

    public List<MmsFixedAssetReturnDtl> findAllTagNo();

    public List<MmsFixedAssetReturnDtl> searchBytag1(MmsFixedAssetReturnDtl returnDtlEntity);

    public List<MmsFixedAssetReturnDtl> findAllInfo();
    
}
