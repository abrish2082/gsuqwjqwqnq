/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityBean;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import webService.AAA;
import webService.IAdministration;

/**
 * @Product Name: CFSS
 * @OWNER = INSA
 * @Developed by = ASP Team
 * @Version = 3.4
 * @author Hilwa
 */
@Named("changePassword")
@SessionScoped

public class ChangePassword implements Serializable {

    String url;
    String userPassword = null;
    boolean lblCurrentPasswd = true;
    boolean txtCurrentPasswd = true;
    

    public boolean isLblCurrentPasswd() {
        return lblCurrentPasswd;
    }

    public void setLblCurrentPasswd(boolean lblCurrentPasswd) {
        this.lblCurrentPasswd = lblCurrentPasswd;
    }

    public boolean isTxtCurrentPasswd() {
        return txtCurrentPasswd;
    }

    public void setTxtCurrentPasswd(boolean txtCurrentPasswd) {
        this.txtCurrentPasswd = txtCurrentPasswd;
    }

    /**
     * <p>
     * Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code
     * inserted here is subject to being replaced.</p>
     */
    @PostConstruct
    private void _init() {

    }

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
    
    private String message = null;
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    private HtmlInputSecret txtConfirmNewPassword = new HtmlInputSecret();

    public HtmlInputSecret getTxtConfirmNewPassword() {
        return txtConfirmNewPassword;
    }

    public void setTxtConfirmNewPassword(HtmlInputSecret his) {
        this.txtConfirmNewPassword = his;
    }
    @Inject
    SessionBean sessionBean;

    /**
     *
     * @return
     */
    boolean checkPasswordConfirmation() {
        boolean validResult = true;
        if (!getNewPassword().equals(
                getConfirmNewPassword())) {
            validResult = false;
//            error(getTxtConfirmNewPassword(),
//                    Validator.getBundleValue("passwordMatch"));
        }
        getTxtConfirmNewPassword().setValid(validResult);
        return validResult;
    }
    AAA securityService = new AAA();

    /**
     *
     * @return
     */
    public String btnChangePassword_action() throws IOException {
//        if (validateChangePassword()) {
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        FacesContext context = FacesContext.getCurrentInstance();
        message = security.checkPasswordPolicy(sessionBean.getUserName(), newPassword, message);
        System.err.println("message ========" + message);
        if (checkPasswordConfirmation() && message == null) {
             String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = Utility.getBundleValue(systemBundle, "dataSet");  

            security.changePassword(sessionBean.getUserName(), currentPassword, newPassword, dataset);
            context.addMessage(null, new FacesMessage("Password Successfuly changed."));
            //return "/homepage.html";
            String connURL = Utility.getBundleValue(systemBundle, "ip")
                    + ":" + Utility.getBundleValue(systemBundle, "port");

            ExternalContext externalContext = context.getExternalContext();
            externalContext.redirect("https://" + connURL + "/ERP-war/");
        }else{
             context.addMessage(null, new FacesMessage(message));
        }
        return null;

//        }
    }

    public String lnkBack_action() {
        return "LoggedIn";
    }

}
