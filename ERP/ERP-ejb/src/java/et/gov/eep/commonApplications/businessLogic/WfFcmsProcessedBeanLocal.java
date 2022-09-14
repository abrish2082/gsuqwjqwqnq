/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import javax.ejb.Local;

/**
 *
 * @author memube
 */
@Local
public interface WfFcmsProcessedBeanLocal {

    public void create(WfFcmsProcessed wfFcmsProcessed);

    public void edit(WfFcmsProcessed wfFcmsProcessed);

    public void saveUpdate(WfFcmsProcessed wfFcmsProcessed);
}
