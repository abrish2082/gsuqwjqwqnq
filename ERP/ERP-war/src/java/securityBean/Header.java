/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hilwa
 */
@Named("headerBack")
@ViewScoped

public class Header implements Serializable {

    private boolean btnStatus = false;

    private boolean logoutStatus = true;

    public boolean isLogoutStatus() {
        return logoutStatus;
    }

    public void setLogoutStatus(boolean logoutStatus) {
        this.logoutStatus = logoutStatus;
    }

    public boolean isBtnStatus() {
        return btnStatus;
    }

    public void setBtnStatus(boolean btnStatus) {
        this.btnStatus = btnStatus;
    }

    private String loggedName = "Sign In";

    public String getLoggedName() {
        return loggedName;
    }

    public void setLoggedName(String loggedName) {
        this.loggedName = loggedName;
    }
    @Inject
    SessionBean sessionBeanLocal;
    

    public Header() {
        
    }
    
    @PostConstruct
    public void _init() {
        setLogoutStatus(true);
        setBtnStatus(false);
        if (sessionBeanLocal.getUserName() != null) {
            setLogoutStatus(false);
            setBtnStatus(true);
            loggedName = sessionBeanLocal.getUserName();

        }
    }

    public String cmdLkLogOut_action() throws IOException {
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String connURL = Utility.getBundleValue(systemBundle, "ip")
                + ":" + Utility.getBundleValue(systemBundle, "port");

        SessionInvalidate sessionInvalidate = new SessionInvalidate();
        sessionInvalidate.invalidate();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.redirect("https://" + connURL + "/ERP-war/");

        return null;
    }

    public String toChangePassword() throws IOException {
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String connURL = Utility.getBundleValue(systemBundle, "ip")
                + ":" + Utility.getBundleValue(systemBundle, "port");

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.redirect("https://" + connURL + "/ERP-war/ChangePassword.xhtml");
        return null;
    }

    public void langSelector(){
        
    }
}




