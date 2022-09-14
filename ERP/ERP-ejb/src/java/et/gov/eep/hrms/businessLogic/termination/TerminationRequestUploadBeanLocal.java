/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrTerminationRequestUpload;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface TerminationRequestUploadBeanLocal {

    public void remove(HrTerminationRequestUpload hrTerminationRequestUpload);
    
}
