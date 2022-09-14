/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic;

import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.ifrs.mapper.IfrsDepreciationSetupFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class DepreciationSetupBean implements DepreciationSetupBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    IfrsDepreciationSetupFacade depreciationSetupFacade;

    @Override
    public void saveOrUpdate(IfrsDepreciationSetup depreciationSetup) {
        depreciationSetupFacade.saveOrUpdate(depreciationSetup);
    }

    @Override
    public void create(IfrsDepreciationSetup depreciationSetup) {
        depreciationSetupFacade.create(depreciationSetup);
    }

    @Override
    public List<IfrsDepreciationSetup> depreciationList() {
        return depreciationSetupFacade.findAllFixedAssetGroup();
    }

    @Override
    public List<IfrsDepreciationSetup> findAllFixedAssetGroup() {
        return depreciationSetupFacade.findAllFixedAssetGroup();
    }

    @Override
    public IfrsDepreciationSetup getListOfAttributesByCategory(int key) {
        return depreciationSetupFacade.getListOfAttributesByCategory(key);
    }

    @Override
    public IfrsDepreciationSetup getFixedassetgroup(Integer fixedassetgroupId) {
        return depreciationSetupFacade.getFixedassetgroup(fixedassetgroupId);
    }

    @Override
    public List<IfrsDepreciationSetup> depreciationListForStock() {
        return depreciationSetupFacade.depreciationListForStock();
    }

    @Override
    public IfrsDepreciationSetup findById(Integer id) {
        return depreciationSetupFacade.findById(id);
    }
}
