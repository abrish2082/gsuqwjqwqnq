/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import javax.ejb.Local;

/**
 *
 * @author minab
 */
@Local
public interface WfMmsProcessedBeanLocal {

    public void saveOrUpdate(WfMmsProcessed wfMmsProcessed);
    public void create(WfMmsProcessed wfMmsProcessed);
    public void remove(WfMmsProcessed wfMmsProcessed);
}
