/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import et.gov.eep.hrms.businessLogic.training.HrTdPreserviceTraineesBeanLocal;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author insa
 */
@ManagedBean
@ApplicationScoped
public class PhotoUploadController {

    @EJB
    HrTdPreserviceTraineesBeanLocal hrTraineesBeanLocal;

    @Inject
    HrTdPsvcTraineeDetails hrTdPsvcDetails;

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            //  rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            hrTdPsvcDetails.setId(Integer.parseInt(id));
            hrTdPsvcDetails = hrTraineesBeanLocal.searchById(hrTdPsvcDetails);
            if (hrTdPsvcDetails != null && hrTdPsvcDetails.getPhoto().length > 0) {
                return new DefaultStreamedContent(new ByteArrayInputStream(hrTdPsvcDetails.getPhoto()));
            }
        }
        return null;
    }
}
