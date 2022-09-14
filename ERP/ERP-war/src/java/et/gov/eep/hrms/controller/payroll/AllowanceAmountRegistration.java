/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import javax.swing.text.View;

/**
 *
 * @author user
 */
@Named(value = "allowanceAmountRegistration")
@ViewScoped
public class AllowanceAmountRegistration implements Serializable{

    /**
     * Creates a new instance of AllowanceAmountRegistration
     */
    public AllowanceAmountRegistration() {
    }
    
    private String allowanceLabelName="Amount in Birr";

    public String getAllowanceLabelName() {
        return allowanceLabelName;
    }

    public void setAllowanceLabelName(String allowanceLabelName) {
        this.allowanceLabelName = allowanceLabelName;
    }
    
    
    
    
    
}
