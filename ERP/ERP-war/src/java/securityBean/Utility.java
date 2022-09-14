package securityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ResourceBundle;

/**
 *
 * @author tamrat
 */
public class Utility {
    public static String getBundleValue(String _bundle, String _key) {
        ResourceBundle _bndl = ResourceBundle.getBundle(_bundle);
        return _bndl.getString(_key);
    }



}
