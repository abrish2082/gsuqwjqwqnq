
package et.gov.eep.mms.businessLogic;

import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import et.gov.eep.mms.mapper.IfrsFixedAssetFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class FixedAssetRegistrationBean implements FixedAssetRegistrationBeanLocal {

    @EJB
    IfrsFixedAssetFacade fixedassetFacade;

    @Override
    public void createFixedAssetInformation(IfrsFixedAsset fixedasset) {
        fixedassetFacade.create(fixedasset);
    }
  
    @Override
    public IfrsFixedAsset getLastFixAId() {
        return fixedassetFacade.getLastFixAId();
    }
    @Override
    public List<IfrsFixedAsset> findAllRevaluedFA() {
        return fixedassetFacade.findAllRevaluedFA();
    }

    @Override
    public List<IfrsFixedAsset> searchFixedAssetByNo(IfrsFixedAsset fixedasset) {
        return fixedassetFacade.searchFixedAssetByNo(fixedasset);
    }

    @Override
    public IfrsFixedAsset getSelectedRequest(Integer id) {
        return fixedassetFacade.getSelectedRequest(id);
    }

    @Override
    public void edit(IfrsFixedAsset fixedasset) {
        fixedassetFacade.edit(fixedasset);
    }

    @Override
    public void saveOrUpdateFixedAssetInformation(IfrsFixedAsset fixedasset) {
        fixedassetFacade.saveOrUpdate(fixedasset);
    }

    @Override
    public List<IfrsFixedAsset> findAll() {
        return fixedassetFacade.findAll();
    }

    @Override
    public List<IfrsFixedAsset> findByDepreciationType(IfrsDepreciationSetup depreciationSetup) {
        return fixedassetFacade.findByDepreciationType(depreciationSetup);
    }

    @Override
    public IfrsFixedAsset findByFaCode(IfrsFixedAsset fixedassetIfrs) {
       return fixedassetFacade.findByFaCode(fixedassetIfrs);
    }

    @Override
    public List<IfrsFixedAsset> findAllFaCode() {
       return fixedassetFacade.findAll();
    }

   
}
