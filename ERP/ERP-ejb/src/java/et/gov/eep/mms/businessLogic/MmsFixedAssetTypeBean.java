
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaAssetType;
import et.gov.eep.mms.mapper.MmsFaAssetTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsFixedAssetTypeBean implements MmsFixedAssetTypeBeanLocal {
    @EJB
 MmsFaAssetTypeFacade fixedAssetTypeFacade;

    /**
     *
     * @return
     */
    @Override
    public List<MmsFaAssetType> findAll() {
        return fixedAssetTypeFacade.findAll();
    }

    @Override
    public MmsFaAssetType findAssetTypeByAssetTypeId(MmsFaAssetType assetTypeEntity) {
       return fixedAssetTypeFacade.findAssetTypeByAssetTypeId2(assetTypeEntity);
    }

    

    
}
