/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author yohanis
 */

public class Constants {

    /**
     *
     */
    public Constants() {
    }

    /**
     *
     */
    public final static int ROW_ADDED = 1;

    /**
     *
     */
    public final static int ROW_UPDATED = 2;

    /**
     *
     */
    public final static int ROW_DELETED = 3;

    /**
     *
     */
    public final static int ROW_FETCHED = 4;

    private static final String COMPONENT_BUNDLE = "et.gov.eep.fcms.controller.Bundle";

    /**
     *
     * @param key
     * @return
     */
    public static String getComponentBundle(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getViewRoot().getLocale();
        ResourceBundle bndl = ResourceBundle.getBundle(COMPONENT_BUNDLE,
                locale);
        return bndl.getString(key);
    }
    
    /**
     *
     * @return
     */
    public String englishAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        return null;
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
