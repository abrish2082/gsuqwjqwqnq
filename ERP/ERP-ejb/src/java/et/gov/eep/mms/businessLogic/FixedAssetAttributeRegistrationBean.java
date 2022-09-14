/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;
import et.gov.eep.mms.mapper.IfrsFixedAssetAttributeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 *
 * @author user
 */
@Stateless
public class FixedAssetAttributeRegistrationBean implements FixedAssetAttributeRegistrationBeanLocal {

    @EJB
    private IfrsFixedAssetAttributeFacade fixedassetattributeFacade;

    @Override
    public void saveFixedAssetAttribute(IfrsFixedAssetAttribute fixedassetattribute) {
        fixedassetattributeFacade.saveOrUpdate(fixedassetattribute);
    }

    @Override
    public void edit(IfrsFixedAssetAttribute fixedassetattribute) {
        fixedassetattributeFacade.edit(fixedassetattribute);
    }

    @Override
    public IfrsFixedAssetAttribute getSelectedRequest(Integer id) {
        return fixedassetattributeFacade.getSelectedRequest(id);
    }

    /**
     *
     * @param fixedassetattribute
     * @throws AbstractMethodError
     * @throws Exception
     */
    @Override
    public List<IfrsFixedAssetAttribute> searchStoreByAttribute(IfrsFixedAssetAttribute fixedassetattribute) {
        return fixedassetattributeFacade.searchStoreByAttribute(fixedassetattribute);
    }

    @Override
    public List<IfrsFixedAssetAttribute> searchAllAttributes() {
        return fixedassetattributeFacade.findAll();
    }

    @Override
    public List<IfrsFixedAssetAttribute> getListOfAttributesByAssetGroupId(Integer assetGroupId) {
        return fixedassetattributeFacade.getListOfAttributesByAssetGroupId(assetGroupId);
    }

    @Override
    public IfrsFixedAssetAttribute getIfrsFixedAssetAttributeObject(Integer fixedAttributeId) {
        return fixedassetattributeFacade.getIfrsFixedAssetAttributeObject(fixedAttributeId);
    }

    @Override
    public List<IfrsFixedAssetAttribute> getListOfAttributesByCategory(IfrsDepreciationSetup ifrsDepreciationSetup) {
        return fixedassetattributeFacade.getListOfAttributesByCategory(ifrsDepreciationSetup);
    }

    @Override
    public List<IfrsFixedAssetAttribute> getListOfAttributesBySlCode(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        return fixedassetattributeFacade.getListOfAttributesBySlCode(fmsSubsidiaryLedger);
    }

    @Override
    public boolean getAttributeDup(IfrsFixedAssetAttribute fixedassetattribute) {
        return fixedassetattributeFacade.getAttributeDup(fixedassetattribute);
    }
    }
