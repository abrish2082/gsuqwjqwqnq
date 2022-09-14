package securityBean;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;



/**
 *
 * @author admin
 */
@Named("accessDenied")
@SessionScoped
public class AccessDenied implements Serializable{
    
     private String url;
     
     public AccessDenied() {         
        if (FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("continue") != null) {

            url = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("continue");

        }
     }   
    
    
//     public String lnkLogin_action() {
//         FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request =
//                (HttpServletRequest) context.getExternalContext().getRequest();
//        String systemBundle = "et/gov/eep/commonApplications/securityServer";
//        String connURL = Utility.getBundleValue(systemBundle, "ip")
//                    + ":" + Utility.getBundleValue(systemBundle, "port");
//        request.getSession().setAttribute("userAuthorizedResource", null);
//        request.getSession().setAttribute("user", null);
//        request.getSession().setAttribute("sessionTicket", null);
//        request.getSession().setAttribute("clientUser", null);
//        request.getSession().setAttribute("clientIdentity", null);
//        request.getSession().setAttribute("permission", null);
//        request.getSession().setAttribute("securityPage", "https://" + connURL + "/SECMS-war/Login.xhtml?" + "continue=" + url);
//        return "AccessDenied";
//    
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }   
    
}