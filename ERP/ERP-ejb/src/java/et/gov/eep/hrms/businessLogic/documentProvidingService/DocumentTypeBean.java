/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.documentProvidingService;

import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentTypes;
import et.gov.eep.hrms.mapper.documentProvidingService.HrDocumentTypesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.DataModel;

/**
 *
 * @author Baya
 */
@Stateless
public class DocumentTypeBean implements DocumentTypeBeanLocal {
@EJB
HrDocumentTypesFacade hrDocumentTypesFacade;
    @Override
    public List<HrDocumentTypes> findAlldocTyps() {
     return hrDocumentTypesFacade.findAll();
    }


//    @Override
//    public HrDocumentTypes getByDocType(HrDocumentTypes hrDocumentTypes) {
//       return hrDocumentTypesFacade.findbytype(hrDocumentTypes);
//    }

    
}
