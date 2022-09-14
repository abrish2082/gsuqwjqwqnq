/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.integration;

import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author muller
 */
@Local
public interface MmsGrnIntegrationBeanLocal {
    
    public List<MmsGrn> searchGRNList(MmsStoreInformation mmsStoreInfoprmation);

}
