/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author muller
 */
@Local
public interface MmsItemRegistrationIntegrationBeanLocal {

    public List<MmsItemRegistration> searchSivMaterialList(String siv_No);

    public List<MmsItemRegistration> searchGrnMaterialList(String grn_No);

    public List<MmsItemRegistration> searchByStoreId(int parseInt);

    public List<MmsItemRegistration> searchByStoreId1(int parseInt, int item_flag);

    public List<MmsItemRegistration> searchRmgListToSave(String rmg_no);

    public MmsItemRegistration searchItemInfoByMatCode(MmsItemRegistration itemRegistration);

    public List<MmsItemRegistration> searchItemWithMatCatAndSubCatOnly(MmsItemRegistration itemRegistration);

    public MmsItemRegistration findbyMaterialId(Integer materialId);

}
