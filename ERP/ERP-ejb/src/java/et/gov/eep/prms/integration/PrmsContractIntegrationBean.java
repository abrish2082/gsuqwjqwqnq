/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class PrmsContractIntegrationBean implements PrmsContractIntegrationBeanLocal {
@EJB
PrmsContractFacade contractFacade;
    @Override
    public List<PrmsContract> findContractsByWorkFlowStatus(int status) {
      return contractFacade.findContractsListByWfStatus(status);
    }

    @Override
    public PrmsContract getbyCoNo(PrmsContract contractNo) {
       return contractFacade.getContractNoinfo(contractNo);
    }

    
}
