/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.employee;

import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
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
 * @author user
 */
@ManagedBean
@ApplicationScoped
public class Images {
 //<editor-fold defaultstate="collapsed" desc="Initialization">
    
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @Inject
            HrEmployees hrEmployeesObj;
    File fileContenet;
//</editor-fold>

    public StreamedContent getImage() throws IOException {        
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            //  rendering the view. Return a stub StreamedContent so that it will generate right URL.            
            return new DefaultStreamedContent();
        } else {
            // browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
                System.out.println("inside imge  String id " +id);
//            hrEmployeesObj = new HrEmployees();
            hrEmployeesObj.setId(Integer.parseInt(id));
            System.out.println("hrEmployeesObj id ===" + hrEmployeesObj.getId().toString());
//            hrEmployeesObj = hrEmployeeBeanLocal.findById(hrEmployeesObj);
            hrEmployeesObj = hrEmployeeBeanLocal.searchById(hrEmployeesObj);
            System.out.println("hrEmployeesObj===" + hrEmployeesObj.getFirstName().toString());
            if (hrEmployeesObj != null && hrEmployeesObj.getPhoto().length > 0) {
                System.out.println("inside imageclass2222");
                return new DefaultStreamedContent(new ByteArrayInputStream(hrEmployeesObj.getPhoto()));
            }
        }
        return null;
    }

}
