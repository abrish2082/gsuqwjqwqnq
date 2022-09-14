/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.mapper.PrmsServiceProviderFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class ServiceProviderIntegrationBean implements ServiceProviderIntegrationBeanLocal {
    @EJB
    PrmsServiceProviderFacade serviceProviderFacade;
    @Override
    public List<PrmsServiceProvider> findAllServiceProviders() {
        return  serviceProviderFacade.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
