/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.mms.businessLogic.MmsLostFixedAssetBeanLocal;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import javax.faces.bean.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.File;
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
 * @author w_station
 */
@ManagedBean
@ApplicationScoped
public class ImageView {

   @EJB
    private MmsLostFixedAssetBeanLocal fixedAssetBeanLocal;
    @Inject
    MmsLostFixedAsset LostObj;
    File fileContenet;

    public StreamedContent getImageView() throws IOException {        
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            //  rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");// id , lostItemId
                System.out.println("inside imge  String id " +id);
//            hrEmployeesObj = new HrEmployees();
            LostObj.setLostItemId(Integer.parseInt(id));
            //System.out.println("hrEmployeesObj id ===" + LostObj.getId().toString());
//            hrEmployeesObj = hrEmployeeBeanLocal.findById(hrEmployeesObj);
//            LostObj = fixedAssetBeanLocal.searchById(LostObj);
//            //System.out.println("hrEmployeesObj===" + LostObj.getFirstName().toString());
//            if (LostObj != null && LostObj.getPhoto().length > 0) {
//                System.out.println("inside imageclass2222");
//                return new DefaultStreamedContent(new ByteArrayInputStream(LostObj.getPhoto()));
//            }
        }
   
    return null;
}
}