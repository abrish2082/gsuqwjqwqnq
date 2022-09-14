/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrTerminationRequestUpload;
import et.gov.eep.hrms.mapper.termination.HrTerminationRequestUploadFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class TerminationRequestUploadBean implements TerminationRequestUploadBeanLocal {

    @EJB
    HrTerminationRequestUploadFacade hrTerminationRequestUploadFacade;

    @Override
    public void remove(HrTerminationRequestUpload hrTerminationRequestUpload) {
       hrTerminationRequestUploadFacade.remove(hrTerminationRequestUpload);
    }

}
