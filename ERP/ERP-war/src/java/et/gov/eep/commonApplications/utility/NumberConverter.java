/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author XX
 */
@Named(value = "numberConverter")
@ViewScoped
public class NumberConverter implements Serializable{

    private org.primefaces.convert.NumberConverter numberConverter = new org.primefaces.convert.NumberConverter();

    public org.primefaces.convert.NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(org.primefaces.convert.NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public NumberConverter() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(500);
        numberConverter.setMaxFractionDigits(2);
    }
}
