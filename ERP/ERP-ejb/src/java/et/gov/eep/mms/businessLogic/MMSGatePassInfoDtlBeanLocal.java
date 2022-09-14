/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsGatePassInfoDtl;
import et.gov.eep.mms.entity.MmsGatePassInformation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface MMSGatePassInfoDtlBeanLocal {

    /**
     *
     * @param gatePassEntity
     * @return
     */
    public List<MmsGatePassInfoDtl> searchGatePassByParameterPrefix(MmsGatePassInformation gatePassEntity);

    /**
     *
     * @param gatePassEntity
     * @return
     */
    public List<MmsGatePassInfoDtl> searchGatePassByParameterContains(MmsGatePassInformation gatePassEntity);

    /**
     *
     * @return
     */
    public List<MmsGatePassInfoDtl> searchAllGatePassInfo();
}
