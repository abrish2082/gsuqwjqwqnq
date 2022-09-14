/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsSivFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author muller
 */
@Stateless
public class MmsSivIntegrationBean implements MmsSivIntegrationBeanLocal {

    @EJB
    MmsSivFacade mmsSivFacade;
    
    @Override
    public List<MmsSiv> searchSIVList(MmsStoreInformation storeInformation) {
        return mmsSivFacade.searchSIVList(storeInformation);
    }
}
