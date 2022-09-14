/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.documentProvidingService;

import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentTypes;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.DataModel;

/**
 *
 * @author Baya
 */
@Local
public interface DocumentTypeBeanLocal {

    public List<HrDocumentTypes> findAlldocTyps();

   

}
