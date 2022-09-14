
package et.gov.eep.mms.businessLogic;

import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface FixedAssetRegistrationBeanLocal {

    public void createFixedAssetInformation(IfrsFixedAsset fixedasset);

    public IfrsFixedAsset getLastFixAId();

    public List<IfrsFixedAsset> searchFixedAssetByNo(IfrsFixedAsset fixedasset);

    public IfrsFixedAsset getSelectedRequest(Integer id);

    public void edit(IfrsFixedAsset fixedasset);

    public void saveOrUpdateFixedAssetInformation(IfrsFixedAsset fixedasset);

    public List<IfrsFixedAsset> findAll();

    public List<IfrsFixedAsset> findAllRevaluedFA();

    public List<IfrsFixedAsset> findByDepreciationType(IfrsDepreciationSetup depreciationSetup);

    public IfrsFixedAsset findByFaCode(IfrsFixedAsset fixedassetIfrs);

    public List<IfrsFixedAsset> findAllFaCode();

}
