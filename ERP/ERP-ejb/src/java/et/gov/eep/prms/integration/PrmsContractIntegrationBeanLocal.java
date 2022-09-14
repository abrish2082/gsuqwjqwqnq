/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.prms.entity.PrmsContract;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface PrmsContractIntegrationBeanLocal {
    public List<PrmsContract>findContractsByWorkFlowStatus(int approvedStatus);
     public PrmsContract getbyCoNo(PrmsContract contractNo);
}
