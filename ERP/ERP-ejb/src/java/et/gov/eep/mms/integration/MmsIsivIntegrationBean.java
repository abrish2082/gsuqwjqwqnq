/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsIsivFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author muller
 */
@Stateless
public class MmsIsivIntegrationBean implements MmsIsivIntegrationBeanLocal {

    @EJB
    MmsIsivFacade mmsIsivFacade;
    
    @Override
    public List<MmsIsiv> searchISIVList(MmsStoreInformation mmsStoreInformation) {
        return mmsIsivFacade.searchISIVList(mmsStoreInformation);
    }

}
