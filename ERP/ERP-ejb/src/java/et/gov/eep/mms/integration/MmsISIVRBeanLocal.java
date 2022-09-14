/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Terminal2
 */
@Local
public interface MmsISIVRBeanLocal {

    public List<MmsIsivReceived> searchISIVRList(MmsStoreInformation mmsStoreInformation);

    public List<MmsNonFixedAssetReturn> searchSRNList(MmsStoreInformation mmsStoreInformation);
}
