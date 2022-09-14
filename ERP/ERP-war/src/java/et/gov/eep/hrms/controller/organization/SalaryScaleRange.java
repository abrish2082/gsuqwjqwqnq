/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.organization;

import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Administrator
 */
@Named(value = "salaryScaleRange")
@ViewScoped
public class SalaryScaleRange implements Serializable{

    /**
     * Creates a new instance of SalaryScaleRange
     */
    public SalaryScaleRange() {
    }
    
    private List<HrSalaryScales> salaryScalesGeneration;

    /**
     *
     * @return
     */
    public List<HrSalaryScales> getSalaryScalesGeneration() {
        if (salaryScalesGeneration == null) {
            salaryScalesGeneration = new ArrayList<HrSalaryScales>();
        }
        return salaryScalesGeneration;
    }

    /**
     *
     * @param salaryScalesGeneration
     */
    public void setSalaryScalesGeneration(List<HrSalaryScales> salaryScalesGeneration) {
        this.salaryScalesGeneration = salaryScalesGeneration;
    }

    
    
    
}
