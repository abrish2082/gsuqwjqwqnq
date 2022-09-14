/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.mapper.PrmsContractDetailFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class PrmsContractDetailIntegrationBean implements PrmsContractDetailIntegrationBeanLocal {
@EJB
PrmsContractDetailFacade contractDetailFacade;
    @Override
    public PrmsContractDetail getInfoByMatId(MmsItemRegistration itemRegistrationEntity) {
       return contractDetailFacade.findInfoByMaterialId(itemRegistrationEntity);
    }

    
}
