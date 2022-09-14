/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.Payroll_TaxRate_Logic;
import et.gov.eep.hrms.entity.payroll.FmsPayrollTaxRates;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Administrator
 */
@Named(value = "taxRateController")
@ViewScoped
public class taxRateController implements Serializable{

    /**
     * Creates a new instance of taxRateController
     */
    public taxRateController() {
    }
    
    
    
    
    
    
    public void showDatas(){
        try {
           
        } catch (Exception e) {
        }
    }
    
     private boolean unlimitedValue;

    public boolean isUnlimitedValue() {
        return unlimitedValue;
    }

    public void setUnlimitedValue(boolean unlimitedValue) {
        this.unlimitedValue = unlimitedValue;
    }
    
    
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
    
    
    @EJB
    private Payroll_TaxRate_Logic taxRate_Logic;
    @Inject
    FmsPayrollTaxRates payrollTaxRates;
    DataModel<FmsPayrollTaxRates> hrPayrollPensionsModel;
    private boolean unlimited;
    private boolean disable;
    private String toAmountVal;
    
    private List<FmsPayrollTaxRates>listOfTaxRates;

    public List<FmsPayrollTaxRates> getListOfTaxRates() {
        return listOfTaxRates;
    }

    public void setListOfTaxRates(List<FmsPayrollTaxRates> listOfTaxRates) {
        this.listOfTaxRates = listOfTaxRates;
    }
    
    
    

    @PostConstruct
    private void __init() {
      listOfTaxRates=  taxRate_Logic.getListofTaxRate();

    }

    public FmsPayrollTaxRates getPayrollTaxRates() {
        if (payrollTaxRates == null) {
            payrollTaxRates = new FmsPayrollTaxRates();
        }
        return payrollTaxRates;
    }

    public void setPayrollTaxRates(FmsPayrollTaxRates payrollTaxRates) {
        this.payrollTaxRates = payrollTaxRates;
    }

    public DataModel<FmsPayrollTaxRates> getHrPayrollPensionsModel() {
        if (hrPayrollPensionsModel == null) {
            hrPayrollPensionsModel = new ListDataModel();
        }
        return hrPayrollPensionsModel;
    }

    public void setHrPayrollPensionsModel(DataModel<FmsPayrollTaxRates> hrPayrollPensionsModel) {
        this.hrPayrollPensionsModel = hrPayrollPensionsModel;
    }

    public boolean isUnlimited() {
        return unlimited;
    }

    public void setUnlimited(boolean unlimited) {
        this.unlimited = unlimited;
    }

    public String getToAmountVal() {
        return toAmountVal;
    }

    public void setToAmountVal(String toAmountVal) {
        this.toAmountVal = toAmountVal;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public void recreateModel() {
        hrPayrollPensionsModel = null;
        List<FmsPayrollTaxRates> listDataModel;
        listDataModel = taxRate_Logic.getListofTaxRate();
        int Size = listDataModel.size();
        for (int i = 0; i < Size; i++) {
            if (listDataModel.get(i).getToAmount().toString().contains("-1")) {
                listDataModel.get(i).setToAmoutVal("unlimited");
            } else {
                listDataModel.get(i).setToAmoutVal(listDataModel.get(i).getToAmount().toString());
            }
        }
        hrPayrollPensionsModel = new ListDataModel(listDataModel);

    }

    public String btnSave_Action() {

        try {
            if (isUnlimited() == true) {
                payrollTaxRates.setToAmount(-1);
            } else {
                payrollTaxRates.setToAmount(Integer.parseInt(getToAmountVal()));
            }
            taxRate_Logic.saveOrUpdate(payrollTaxRates);

            cleanpage();
            recreateModel();
            JsfUtil.addSuccessMessage("Saved");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Something Occured on Data Created");
        }

        return null;
    }

    public void addmakeAsCurrentPension() {

        if (isUnlimited() == true) {

            setToAmountVal("Unlimited");
            disable = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("verifyed that the [To Amount] is Unlimited"));
        } else {
            disable = false;
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unverifyed that the [To Amount] is Unlimited"));
        }

    }

    public String editpenstion() {

        return null;
    }

    private void cleanpage() {
        payrollTaxRates = null;
        toAmountVal = null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
