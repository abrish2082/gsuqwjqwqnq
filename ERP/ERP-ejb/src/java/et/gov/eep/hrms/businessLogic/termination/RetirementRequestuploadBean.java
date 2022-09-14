/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrRetirementRequestUpload;
import et.gov.eep.hrms.mapper.termination.HrRetirementRequestUploadFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class RetirementRequestuploadBean implements RetirementRequestuploadBeanLocal {

    @EJB
    HrRetirementRequestUploadFacade hrRetirementRequestUploadFacade;

    @Override
    public void remove(HrRetirementRequestUpload hrRetirementRequestUpload) {
      hrRetirementRequestUploadFacade.remove(hrRetirementRequestUpload);
    }
}
