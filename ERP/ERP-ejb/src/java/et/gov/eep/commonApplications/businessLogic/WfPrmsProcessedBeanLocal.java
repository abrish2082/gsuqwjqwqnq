/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface WfPrmsProcessedBeanLocal {

    public void savewf(WfPrmsProcessed wfPrmsProcessed);

    public void edit(WfPrmsProcessed wfPrmsProcessed);
    
}
