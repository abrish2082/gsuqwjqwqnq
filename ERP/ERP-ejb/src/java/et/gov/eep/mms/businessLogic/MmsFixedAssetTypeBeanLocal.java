
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaAssetType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface MmsFixedAssetTypeBeanLocal {

    /**
     *
     * @return
     */
    public List<MmsFaAssetType> findAll();

    public MmsFaAssetType findAssetTypeByAssetTypeId(MmsFaAssetType assetTypeEntity);
}
