/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.fcms.mapper.workFlow.WfFcmsProcessedFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author memube
 */
@Stateless
public class WfFcmsProcessedBean implements WfFcmsProcessedBeanLocal {

    @EJB
    WfFcmsProcessedFacade wfFcmsProcessedFacade;

    @Override
    public void create(WfFcmsProcessed wfFcmsProcessed) {
        wfFcmsProcessedFacade.create(wfFcmsProcessed);
    }

    @Override
    public void edit(WfFcmsProcessed wfFcmsProcessed) {
        wfFcmsProcessedFacade.edit(wfFcmsProcessed);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void saveUpdate(WfFcmsProcessed wfFcmsProcessed) {
        wfFcmsProcessedFacade.saveOrUpdate(wfFcmsProcessed);
    }
}
