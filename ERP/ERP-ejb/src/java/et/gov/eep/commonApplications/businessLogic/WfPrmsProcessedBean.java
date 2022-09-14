/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.mapper.WfPrmsProcessedFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class WfPrmsProcessedBean implements WfPrmsProcessedBeanLocal {
    @EJB
    WfPrmsProcessedFacade wfPrmsProcessedFacade;

    @Override
    public void savewf(WfPrmsProcessed wfPrmsProcessed) {
       wfPrmsProcessedFacade.create(wfPrmsProcessed);
    }

    @Override
    public void edit(WfPrmsProcessed wfPrmsProcessed) {
        wfPrmsProcessedFacade.edit(wfPrmsProcessed); 
    }
   
   
}
