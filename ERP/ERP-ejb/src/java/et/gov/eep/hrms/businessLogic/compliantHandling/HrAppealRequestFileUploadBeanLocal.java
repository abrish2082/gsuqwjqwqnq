/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.compliantHandling;

import et.gov.eep.hrms.entity.compliantHandling.HrAppealRequestFileUpload;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrAppealRequestFileUploadBeanLocal {

    public void remove(HrAppealRequestFileUpload hrAppealRequestFileUpload);
    
}
