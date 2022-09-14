/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications;

import java.beans.Transient;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import securityBean.Utility;

/**
 *
 * @author meles
 */
@SessionScoped
@Named(value = "localbean")

public class localbean implements Serializable {

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    String save = "Save";
    String update = "Update";
    String add = "Add";
    String Modify = "Modify";
    String engBundle = "/et/gov/eep/hrms/en";
    String amhbundle = "/et/gov/eep/hrms/en_am_ET";
//    String engBundle = "/Local/am_en_US";
//    String amhbundle = "/Local/am";

    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    HttpSession langsession = request.getSession();

    public void LanguageSession(String lang) throws IOException {

//        if (getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
//            langsession.setAttribute("lang", "am");
//        } else {
        langsession.setAttribute("lang", lang);
//        }
        System.out.println("insed LangSession method lang=1==" + lang);
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    @Transient
    public String getSave() {
        if (getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
            save = Utility.getBundleValue(amhbundle, "save");
        } else {
            save = Utility.getBundleValue(engBundle, "save");
        }
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    @Transient
    public String getUpdate() {
        if (getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
            update = Utility.getBundleValue(amhbundle, "Update");
        } else {
            update = Utility.getBundleValue(engBundle, "Update");
        }

        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    @Transient
    public String getAdd() {
        if (getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
            add = Utility.getBundleValue(amhbundle, "Add");
        } else {
            add = Utility.getBundleValue(engBundle, "Add");
        }
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    @Transient
    public String getModify() {
        if (getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
            Modify = Utility.getBundleValue(amhbundle, "Modify");
        } else {
            Modify = Utility.getBundleValue(engBundle, "Modify");
        }
        return Modify;
    }

    public void setModify(String Modify) {
        this.Modify = Modify;
    }

    public HttpSession getLangsession() {
        return langsession;
    }

    public void setLangsession(HttpSession langsession) {
        this.langsession = langsession;
    }

//</editor-fold>
    public void changeLanguage(String language) {
        System.out.println("language=2==" + language);
        if (language.equalsIgnoreCase("ET")) {
            locale = new Locale("am", language);
            try {
                LanguageSession("am");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        } else {
            locale = new Locale("en", language);
            try {
                LanguageSession("en");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
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

    public localbean() {
    }

    class CurrentController<Controller> {

        Controller obj;

        void add(Controller obj) {
            this.obj = obj;
        }

        Controller get() {
            return obj;
        }
    }
}
