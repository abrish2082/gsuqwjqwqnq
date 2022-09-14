/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsGatePassInfoDtl;
import et.gov.eep.mms.entity.MmsGatePassInformation;
import et.gov.eep.mms.mapper.MmsGatePassInfoDtlFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MMSGatePassInfoDtlBean implements MMSGatePassInfoDtlBeanLocal {
    @EJB
    MmsGatePassInfoDtlFacade gatePassDtlFacade;

    /**
     *
     * @param gatePassEntity
     * @return
     */
    @Override
    public List<MmsGatePassInfoDtl> searchGatePassByParameterPrefix(MmsGatePassInformation gatePassEntity) {
         return gatePassDtlFacade.searchGatePassByParameterPrefix(gatePassEntity);
    }

    /**
     *
     * @param gatePassEntity
     * @return
     */
    @Override
    public List<MmsGatePassInfoDtl> searchGatePassByParameterContains(MmsGatePassInformation gatePassEntity) {
        return gatePassDtlFacade.searchGatePassByParameterContains(gatePassEntity);
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsGatePassInfoDtl> searchAllGatePassInfo() {
        return gatePassDtlFacade.findAll();
    }

    
}
