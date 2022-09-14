/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.prms.entity.PrmsServiceProvider;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface ServiceProviderIntegrationBeanLocal {
     public List<PrmsServiceProvider> findAllServiceProviders();
}
