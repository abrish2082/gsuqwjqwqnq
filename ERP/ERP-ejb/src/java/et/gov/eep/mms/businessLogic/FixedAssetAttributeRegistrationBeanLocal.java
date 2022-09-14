/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;
 
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup; 
import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;
//import et.gov.eep.ifrs.entity.IfrsFixedAssetGroup;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface FixedAssetAttributeRegistrationBeanLocal {

    //public List<IfrsFixedAssetAttribute> getListOfAttributesByCategory(IfrsFixedAssetAttribute assetGroup);
    public void saveFixedAssetAttribute(IfrsFixedAssetAttribute fixedassetattribute);
    
    public void edit(IfrsFixedAssetAttribute fixedassetattribute);

    public IfrsFixedAssetAttribute getSelectedRequest(Integer id);

    public List<IfrsFixedAssetAttribute> getListOfAttributesByAssetGroupId(Integer assetGroupId);

    public List<IfrsFixedAssetAttribute> searchStoreByAttribute(IfrsFixedAssetAttribute fixedassetattribute);

    public List<IfrsFixedAssetAttribute> searchAllAttributes();

    public IfrsFixedAssetAttribute getIfrsFixedAssetAttributeObject(Integer fixedAttributeId);

    public List<IfrsFixedAssetAttribute> getListOfAttributesByCategory(IfrsDepreciationSetup ifrsDepreciationSetup);

    public List<IfrsFixedAssetAttribute> getListOfAttributesBySlCode(FmsSubsidiaryLedger fmsSubsidiaryLedger);

    public boolean getAttributeDup(IfrsFixedAssetAttribute fixedassetattribute);

}
