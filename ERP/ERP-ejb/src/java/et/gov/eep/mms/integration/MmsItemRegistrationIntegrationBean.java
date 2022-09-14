/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.mapper.MmsItemRegistrationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author muller
 */
@Stateless
public class MmsItemRegistrationIntegrationBean implements MmsItemRegistrationIntegrationBeanLocal {

    @EJB
    MmsItemRegistrationFacade itemRegistrationFacade; 

    @Override
    public List<MmsItemRegistration> searchSivMaterialList(String siv_No) {
        return itemRegistrationFacade.searchSivMaterialList(siv_No);
    }

    @Override
    public List<MmsItemRegistration> searchGrnMaterialList(String grn_No) {
        return itemRegistrationFacade.searchGrnMaterialList(grn_No);
    }

    @Override
    public List<MmsItemRegistration> searchByStoreId(int storeId) {
        return itemRegistrationFacade.searchByStoreId(storeId);
    }

    @Override
    public List<MmsItemRegistration> searchByStoreId1(int storeId, int item_flag) {
        return itemRegistrationFacade.searchByStoreId1(storeId, item_flag);
    }

    @Override
    public List<MmsItemRegistration> searchRmgListToSave(String rmg_no) {
        return itemRegistrationFacade.searchRmgListToSave(rmg_no);
    }

    @Override
    public MmsItemRegistration searchItemInfoByMatCode(MmsItemRegistration itemRegistration) {
        return itemRegistrationFacade.getMmsItemInformationByCode(itemRegistration);

    }

    @Override
    public List<MmsItemRegistration> searchItemWithMatCatAndSubCatOnly(MmsItemRegistration itemRegistration) {
         return itemRegistrationFacade.searchItemWithMatCatAndSubCatOnly(itemRegistration);
    }

     @Override
    public MmsItemRegistration findbyMaterialId(Integer materialId) { 
        return itemRegistrationFacade.searchByMateialId(materialId); 
    }

}
