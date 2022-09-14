/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.compliantHandling;

import et.gov.eep.hrms.entity.compliantHandling.HrAppealRequestFileUpload;
import et.gov.eep.hrms.mapper.complaintHandling.HrAppealRequestFileUploadFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrAppealRequestFileUploadBean implements HrAppealRequestFileUploadBeanLocal {
@EJB 
HrAppealRequestFileUploadFacade hrAppealRequestFileUploadFacade;
    @Override
    public void remove(HrAppealRequestFileUpload hrAppealRequestFileUpload) {
        hrAppealRequestFileUploadFacade.remove(hrAppealRequestFileUpload); 
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
