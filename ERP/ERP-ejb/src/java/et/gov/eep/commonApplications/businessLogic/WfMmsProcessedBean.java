/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.mapper.WfMmsProcessedFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author minab
 */
@Stateless
public class WfMmsProcessedBean implements WfMmsProcessedBeanLocal {

    @EJB
    WfMmsProcessedFacade wfMmsProcessedFacade;

    @Override
    public void create(WfMmsProcessed wfMmsProcessed) {
        System.out.println("inside wf save session bean");
        wfMmsProcessedFacade.create(wfMmsProcessed);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void saveOrUpdate(WfMmsProcessed wfHrProcessed) {
        wfMmsProcessedFacade.saveOrUpdate(wfHrProcessed);
    }

    @Override
    public void remove(WfMmsProcessed wfMmsProcessed) {
       wfMmsProcessedFacade.remove(wfMmsProcessed);
    }
}
