/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrDisciplineFileUploadBeanLocal {

    public void create(HrDisciplineFileUpload hrDisciplineFileUpload);
    
}
