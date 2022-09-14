/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author prg
 */
@Named(value = "validatorManagedBean")
@ViewScoped
public class ValidatorManagedBean implements Serializable {

    /**
     * Creates a new instance of ValidatorManagedBean
     */
    public ValidatorManagedBean() {
    }

    /**
     *
     * @param input
     * @return
     */
    public boolean Empty(String input) {
        return !input.isEmpty();
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Code(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[A-Za-z]{1,5}[0-9]{1,5}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Code Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    public void Codes(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[A-Za-z0-9-/_ ]{1,10}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Code Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Text(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[ሀ-ፖ A-Za-z- ]{2,50}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Validation failed use more than two characters");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    public void AlphaNumericText(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[A-Za-z- 0-9]{1,100}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Validation failed use Alpha Numeric characters");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Phone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("^\\+?251\\-?\\-?[1-9][0-9]{2}\\-?[0-9]{6}|$|"
                    + "^[0][1-9][0-9]{2}\\-?[0-9]{6}|$|"
                    + "^\\+?[0-9]{1,3}\\-?\\-?[0-9]{3}\\-?[0-9]{6}$")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Phone Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Email(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[a-zA-Z0-9-\\.]+@[a-zA-Z]{2,10}+\\.[a-zA-Z ]"
                    + "{3}+$|^[a-zA-Z0-9-\\.]+@[a-zA-Z]+\\.[a-zA-Z]{2,5}+\\.[a-zA-Z ]{3}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Email Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Website(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("^[w]{3}+\\.[a-zA-Z0-9-]+\\.[a-zA-Z]{2,10}+\\.[a-zA-Z ]"
                    + "{2}+$|^[w]{3}+\\.[a-zA-Z0-9-]+\\.[a-zA-Z ]{2,10}+$")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Website Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Remark(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[a-zA-Z0-9./,\\- ]{0,200}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Float(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[0-9]{1,10}\\\\.?[0-9]{0,10}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Date(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
//            if (!input.matches("[1-9]{1}[0-9]{3}((\\/)|(-))(((([0]{1}[1-9]{1})|"
//                    + "([1]{1}[0-2]{1}))((\\/)|(-))(((([0]{1})|([1-2]{1}))[0-9]{1})|([3]{1}[0])))|"//month
//                    + "([1]{1}[3]{1}((\\/)|(-))(([0]{1})[1-6]{1}|[1-6]{1})))")) {
//                FacesMessage msg = new FacesMessage("Invalid input.", "Date Validation failed");
//                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//                throw new ValidatorException(msg);
//            }
            //((\\/)|(-))  ([0]{1}[0-9]{1})| ([1]{1}[0-3]{1}) ((\\/)|(-))    [1-9]{1}[0-9]{3}

            if (!input.matches("(((([0]{1})|([1-2]{1}))[0-9]{1})|([3]{1}[0]))((\\/)|(-))(([0]{1}[0-9]{1})|([1]{1}[0-3]{1}))((\\/)|(-))([1-9]{1}[0-9]{3})")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Date Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }

        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Percent(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("(([0])||([1-9]{1}[0-9]?))")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void POBOX(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("^[0-9]{1,16}+[a-zA-Z]{1,20}+[0-9 ]+$|^[0-9 ]+$")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "P.O.BOX Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void House(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[0-9]{1,10}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "House no Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Time(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("^[0-1]?[1-9]:[0-5][0-9]$")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Time Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Year(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[0-9]{4}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Year Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    //"[0-9][°,',''] "
    //"[0-9]{°} [0-9]{'} [0-9]{''}"
    //"[0-9][°][0-9]['][0-9]['']"
    public void Location(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[0-9][°][0-9]['][0-9]['']")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Location Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void FullName(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[a-zA-z \\.]{2,50}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Name Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Money(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("(([0]{1}\\.[0-9]{1,8})||([1-9]{1}[0-9]{0,20}\\.[0-9]{1,8})||([1-9]{1}[0-9]{0,20})||([0]))")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Money Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Salary(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("(([1-9]{1}[0-9]{2,20})||([1-9]{1}[0-9]{2,20}\\.[0]?[0-9]{1,3}))")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Salary Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Number(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = String.valueOf(value);
        if (!input.isEmpty()) {
            if (!input.matches("^[0-9 ]+$")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Number Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Double(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[0-9]*\\.[0-9]*|[0-9]*|[0-9]*\\.[0-9]*Ee[0-9]*")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Number Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void List(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[A-Za-z,. ]{2,100}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "List Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void FilePath(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "FilePath Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void Grade(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input;
        input = value.toString();
        if (!input.isEmpty()) {
            if (!input.matches("[A-F+-]{1,3}")) {
                FacesMessage msg = new FacesMessage("Invalid input.", "Grade Validation failed");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    public boolean CompareDate(Date date1, Date date2) {
        if (date1.after(date2)) {
            return false;
        } else {
            return true;
        }
    }
}
