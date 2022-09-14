/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
@Named(value = "earningDeductionController")
@ViewScoped
public class EarningDeductionController implements Serializable{

    /**
     * Creates a new instance of EarningDeductionController
     */
    public EarningDeductionController() {
    }
       @EJB
    private subsidiaryBeanLocal subsidiaryBeanLocal;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    
private boolean value1;  
    private boolean value2;
 
    public boolean isValue1() {
        return value1;
    }
 
    public void setValue1(boolean value1) {
        this.value1 = value1;
    }
 
    public boolean isValue2() {
        return value2;
    }
 
    public void setValue2(boolean value2) {
        this.value2 = value2;
    }
     
    public void addMessage() {
        String summary = value2 ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
    

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if(fmsSubsid1aryLedger1==null){
        fmsSubsid1aryLedger1=new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger1;
    }

    private String selectedSubsidary;

    public String getSelectedSubsidary() {
        return selectedSubsidary;
    }

    public void setSelectedSubsidary(String selectedSubsidary) {
    
        this.selectedSubsidary = selectedSubsidary;
    }

    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
    }
      public int firstSplietedValue(String vToBeSplited) {
        try {
            int fSplitedValue;
            String conc[];
            conc = vToBeSplited.split("-");
            fSplitedValue = Integer.valueOf(conc[0]);
            return fSplitedValue;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public String returnAccountTitle(String vToBeSplited) {
        try {
            String fSplitedValue;
            String conc[];
            conc = vToBeSplited.split("-");
            fSplitedValue = conc[1];
            return fSplitedValue;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void handleSelect(SelectEvent event) {
        try {            
            selectedSubsidary = "h";           
            firstSplietedValue(event.getObject().toString());
            fmsSubsid1aryLedger1.setSubsidiaryId(firstSplietedValue(event.getObject().toString()));
            fmsSubsid1aryLedger1.setAccountTitle(returnAccountTitle(event.getObject().toString()));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    List<FmsSubsidiaryLedger> listOfSubsidayLedger;

    public List<FmsSubsidiaryLedger> getListOfSubsidayLedger() {
       listOfSubsidayLedger= subsidiaryBeanLocal.findAll();
        return listOfSubsidayLedger;
    }

    public List<FmsSubsidiaryLedger> searchSubsidayLedger(String subsidayName) {
        try {
            return listOfSubsidayLedger = subsidiaryBeanLocal.searchSubsidiaryByName(subsidayName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setListOfSubsidayLedger(List<FmsSubsidiaryLedger> listOfSubsidayLedger) {
        this.listOfSubsidayLedger = listOfSubsidayLedger;
    }

}
