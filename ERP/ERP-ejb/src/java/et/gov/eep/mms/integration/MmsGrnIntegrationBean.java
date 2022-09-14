/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsGrnFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author muller
 */
@Stateless
public class MmsGrnIntegrationBean implements MmsGrnIntegrationBeanLocal {

    @EJB
    MmsGrnFacade grnFacade;

    @Override
    public List<MmsGrn> searchGRNList(MmsStoreInformation mmsStoreInformation) {
        return grnFacade.searchGRNList(mmsStoreInformation);
    }
}
