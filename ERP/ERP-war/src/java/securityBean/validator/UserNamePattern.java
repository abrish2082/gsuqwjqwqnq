package securityBean.validator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author 
 */
@FacesValidator("securityBean.validator.UserNamePattern")
public class UserNamePattern implements Validator {

    private static String PATTERN_VALUE
            = "^[A-Za-z0-9_\\.\\p{L}]{3,15}$";

    private Pattern pattern;
    private Matcher matcher;

    public UserNamePattern() {
        pattern = Pattern.compile(PATTERN_VALUE);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        FacesMessage msg
                = new FacesMessage();
        if (value != null && !value.toString().equalsIgnoreCase("")) {

            matcher = pattern.matcher(value.toString());

            if (!matcher.matches()) {
                msg = new FacesMessage("User Name validation failed.",
                        "Invalid user name");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);

            }
        } else {
            msg = new FacesMessage("User Name validation failed.",
                    "Value Required.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }

}
