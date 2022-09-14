/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import et.gov.eep.hrms.mapper.displine.HrDisciplineFileUploadFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrDisciplineFileUploadBean implements HrDisciplineFileUploadBeanLocal {
    @EJB 
    HrDisciplineFileUploadFacade hrDisciplineFileUploadFacade;

    @Override
    public void create(HrDisciplineFileUpload hrDisciplineFileUpload) {
        hrDisciplineFileUploadFacade.saveOrUpdate(hrDisciplineFileUpload);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
