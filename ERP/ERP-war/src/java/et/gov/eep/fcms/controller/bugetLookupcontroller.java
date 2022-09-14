/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

//import et.gov.insa.eep.fms.Entities.FmsBudget;
//import et.gov.insa.eep.fms.businessLogic.budgetBeanLocal;
import et.gov.eep.fcms.entity.FmsBudget;
import et.gov.eep.fcms.businessLogic.budgetBeanLocal;
import et.gov.eep.commonApplications.utility.JsfUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author user
 */
@Named(value = "bugetLookupcontroller")
@ViewScoped
public class bugetLookupcontroller implements Serializable{
    @EJB
    private budgetBeanLocal budgetBean;
//    @Inject
    FmsBudget budget;
    String SaveUpdate = "Create";
    
    /**
     *
     * @return
     */
    public FmsBudget getBudget() {
       if(budget==null){
          budget=new  FmsBudget();
       }
        return budget;
    }

    /**
     *
     * @param budget
     */
    public void setBudget(FmsBudget budget) {
        this.budget = budget;
    }

    /**
     *
     * @return
     */
    public String getSaveUpdate() {
        return SaveUpdate;
    }

    /**
     *
     * @param SaveUpdate
     */
    public void setSaveUpdate(String SaveUpdate) {
        this.SaveUpdate = SaveUpdate;
    }

    private void saveUpdateClear() {
        budget = null;

    }
    int status=0;

    /**
     *
     * @return
     */
    public String save() {            
        budgetBean.Create(budget);
        JsfUtil.addSuccessMessage("operating Budget successfully register");  
        saveUpdateClear();
        return null;
    }

}
