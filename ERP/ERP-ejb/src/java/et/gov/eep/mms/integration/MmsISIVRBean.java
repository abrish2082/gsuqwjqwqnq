/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsIsivReceivedFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Terminal2
 */
@Stateless
public class MmsISIVRBean implements MmsISIVRBeanLocal {

    @EJB
    MmsIsivReceivedFacade mmsIsivReceivedFacade;

    @Override
    public List<MmsIsivReceived> searchISIVRList(MmsStoreInformation mmsStoreInformation) {
        return mmsIsivReceivedFacade.searchISIVRList(mmsStoreInformation);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<MmsNonFixedAssetReturn> searchSRNList(MmsStoreInformation mmsStoreInformation) {
        return mmsIsivReceivedFacade.searchSRNList(mmsStoreInformation);
    }
}
