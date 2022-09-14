/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility;

/**
 *
 * @author user
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author rahel
 */
@FacesConverter("com.nz.util.DateConverter")
public class DateConverter implements Converter{ 
//  String datePattern = "(((19[0-9]{2}|20[0-9]{2})((\\/)|(-))(((0[1-9]|1[0-2])((\\/)|(-))(0[1-9]|[1-2][0-9]|30))|(([1]{1}[3]{1}((\\/)|(-))(([0]{1})[1-6]{1})))))|(((((0[1-9]|[1-2][0-9]|30)((\\/)|(-))(0[1-9]|1[0-2]))|((([0]{1})[1-6]{1})((\\/)|(-))[1]{1}[3]{1}))((\\/)|(-))(19[0-9]{2}|20[0-9]{2}))))";
   String datePattern = "(((19[0-9]{2}|20[0-9]{2})((\\-)|(-))(((0[1-9]|1[0-2])((\\-)|(-))(0[1-9]|[1-2][0-9]|30))|(([1]{1}[3]{1}((\\-)|(-))(([0]{1})[1-6]{1})))))|(((((0[1-9]|[1-2][0-9]|30)((\\-)|(-))(0[1-9]|1[0-2]))|((([0]{1})[1-6]{1})((\\-)|(-))[1]{1}[3]{1}))((\\/)|(-))(19[0-9]{2}|20[0-9]{2}))))";

  
    /**
     *
     * @param facesContext
     * @param uiComponent
     * @param param
     * @return
     */
    @Override
  public String getAsObject(FacesContext facesContext, UIComponent uiComponent, String param)
  {
    String formattedDate = param;
    if ((param != null) && (param.compareTo("") != 0)) {
      if (Pattern.matches(this.datePattern, param)) {
        String separator = "";
        if (param.contains("/"))
          separator = "/";
        else if (param.contains("-")) {
          separator = "-";
        }
        String[] dateFromUI = param.split(separator);
        if (dateFromUI[0].length() == 2) {
          formattedDate = dateFromUI[2] + separator + dateFromUI[1] + separator + dateFromUI[0];
        }
      }
      return formattedDate;
    }
    return "";
  }

    /**
     *
     * @param facesContext
     * @param uiComponent
     * @param obj
     * @return
     */
    @Override
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object obj)
  {
    String formattedDate = String.valueOf(obj);
    if ((obj != null) && (((String)obj).compareTo("") != 0)) {
      if (Pattern.matches(this.datePattern, obj.toString())) {
        formattedDate = obj.toString();

        if (formattedDate.contains("/")) {
          String[] dateFromUI = formattedDate.split("/");
          formattedDate = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
        } else {
          String[] dateFromUI = formattedDate.split("-");
          formattedDate = dateFromUI[2] + "-" + dateFromUI[1] + "-" + dateFromUI[0];
        }
      }
      return formattedDate;
    }
    return "";
}

  

   
}

