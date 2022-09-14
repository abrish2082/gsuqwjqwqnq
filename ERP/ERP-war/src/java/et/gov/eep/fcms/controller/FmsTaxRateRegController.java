/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.fcms.mapper.FmsTaxRateRegisterFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;

import et.gov.eep.fcms.entity.FmsTaxRateRegister;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author kaleab
 */
@Named(value = "fmsTaxRateRegController")
@ViewScoped
public class FmsTaxRateRegController implements Serializable {
    @EJB
    private FmsTaxRateRegisterFacade fmsTaxRateRegisterFacade;

    private FmsTaxRateRegister taxRete =  new FmsTaxRateRegister();

    public FmsTaxRateRegister getTaxRete() {
        return taxRete;
    }

    public void setTaxRete(FmsTaxRateRegister taxRete) {
        this.taxRete = taxRete;
    }
    
    public FmsTaxRateRegController() {
    }
   public List<FmsTaxRateRegister> findAll(){
        return this.fmsTaxRateRegisterFacade.findAll();
    }
   
}
