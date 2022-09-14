/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller;

import java.io.IOException;
import java.util.Locale;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securityBean.Utility;

/**
 *
 * @author baya
 */
//<editor-fold defaultstate="collapsed" desc="baya">
public class Localization {

    String bundleval = "secBundle";
    String Am_bundle = "et/gov/eep/hrms/Lang_am_ET";
    String eng_bundle = "et/gov/eep/hrms/Lang_en_US";

//<editor-fold defaultstate="collapsed" desc="getter and settr">
    public String getBundleval() {
        return bundleval;
    }

    public void setBundleval(String bundleval) {
        this.bundleval = bundleval;
    }
//</editor-fold>

    public void langSelectorEng() throws IOException {
        System.out.println("inside refresh");

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        bundleval = "secBundle";
        bundleval = Utility.getBundleValue(eng_bundle, "Searching_By");
    }

    public void langSelectorAmharic() throws IOException {
        System.out.println("inside refresh");

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        bundleval = "ambundle";
        bundleval = Utility.getBundleValue(Am_bundle, "Searching_By");
    }
//</editor-fold>

    private static final long serialVersionUID = 2756934361134603857L;
//    private static final Logger LOG = Logger.getLogger(HeaderController.class.getName());
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

//    @PostConstruct
//    public void init() {
//        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
//    }
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage(String language) throws IOException {
        if (language.equalsIgnoreCase("ET")) {
            locale = new Locale("am", language);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } else {
            locale = new Locale("en", language);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }

    }

    public String englishAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        locale = new Locale("am", "ET");
        context.getViewRoot().setLocale(locale);

        return null;
    }

    public String amAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        locale = new Locale("am", "en");
        context.getViewRoot().setLocale(locale);

        return null;

    }

}
