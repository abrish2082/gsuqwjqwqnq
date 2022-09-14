/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsStoreInformationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author muller
 */
@Stateless
public class MmsStoreInformationIntegrationBean implements MmsStoreInformationIntegrationBeanLocal {

    @EJB
    MmsStoreInformationFacade storeInformationFacade;

    @Override
    public List<MmsStoreInformation> getUnregisteredMartialByStoreName() {
        return storeInformationFacade.getUnregisteredMartialByStoreName();
    }

    @Override
    public List<MmsStoreInformation> findAllStoreInfo() {
        return storeInformationFacade.findStoreJoinedWithBinCard();
    }

    @Override
    public List<MmsStoreInformation> getUnBalancedMartialByStoreName() {
       return storeInformationFacade.getUnBalancedMartialByStoreName();
    }
}
