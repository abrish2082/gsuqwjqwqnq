/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrRetirementRequestUpload;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface RetirementRequestuploadBeanLocal {

    public void remove(HrRetirementRequestUpload hrRetirementRequestUpload);
    
}
