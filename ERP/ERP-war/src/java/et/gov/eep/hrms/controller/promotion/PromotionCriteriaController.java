/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.promotion;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.promotion.HrLuPromoCriteriaNameBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionCriteriaBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionEducCriteriaBeanLocal;
import et.gov.eep.hrms.entity.promotion.HrLuPromoCriteriaName;
import et.gov.eep.hrms.entity.promotion.HrPromotionCriterias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("promotionCriteriaControllers")
@ViewScoped
public class PromotionCriteriaController implements Serializable {
 @Inject
    SessionBean sessionBean;
    @EJB
    HrLuPromoCriteriaNameBeanLocal hrLuPromoCriteriaNameBeanLocal;
    @EJB
    PromotionCriteriaBeanLocal promotionCriteriaBeanLocal;
    @EJB
    PromotionEducCriteriaBeanLocal promotionEducCriteriaBeanLocal;

    @Inject
    HrLuPromoCriteriaName hrLuPromoCriteriaName;

    @Inject
    HrPromotionCriterias hrPromotionCriterias;

    List<HrLuPromoCriteriaName> hrLuPromoCriteriaNamesList;

    int update = 0;
    private String saveOrUpdateButton = "Save";
    private int criteriaValue = 0;
    List<HrPromotionCriterias> criterias = new ArrayList<>();

    List<SelectItem> criteriaTypes = new ArrayList<>();
    private HrPromotionCriterias selectedCriteria;

    @PostConstruct
    public void init() {
        criterias = promotionCriteriaBeanLocal.readAllCriterias();
        calcTotalWeight();
    }

    public HrPromotionCriterias getSelectedCriteria() {
        return selectedCriteria;
    }

    public void setSelectedCriteria(HrPromotionCriterias selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
    }

    public HrPromotionCriterias getHrPromotionCriterias() {
        if (hrPromotionCriterias == null) {
            hrPromotionCriterias = new HrPromotionCriterias();
        }
        return hrPromotionCriterias;
    }

    public void setHrPromotionCriterias(HrPromotionCriterias hrPromotionCriterias) {
        this.hrPromotionCriterias = hrPromotionCriterias;
    }

    public HrLuPromoCriteriaName getHrLuPromoCriteriaName() {
        if (hrLuPromoCriteriaName == null) {
            hrLuPromoCriteriaName = new HrLuPromoCriteriaName();
        }
        return hrLuPromoCriteriaName;
    }

    public void setHrLuPromoCriteriaName(HrLuPromoCriteriaName hrLuPromoCriteriaName) {
        this.hrLuPromoCriteriaName = hrLuPromoCriteriaName;
    }

    public List<HrLuPromoCriteriaName> getHrLuPromoCriteriaNamesList() {
        return hrLuPromoCriteriaNamesList;
    }

    public void setHrLuPromoCriteriaNamesList(List<HrLuPromoCriteriaName> hrLuPromoCriteriaNamesList) {
        this.hrLuPromoCriteriaNamesList = hrLuPromoCriteriaNamesList;
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public List<HrPromotionCriterias> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<HrPromotionCriterias> criterias) {
        this.criterias = criterias;
    }

    public int getCriteriaValue() {
        return criteriaValue;
    }

    public void setCriteriaValue(int criteriaValue) {
        this.criteriaValue = criteriaValue;
    }

    public void valueChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            criteriaValue = Integer.valueOf(event.getNewValue().toString());
        }
    }

    double totalWeight = 0.0;
    double requiredWeight = 0.0;

    public void calcTotalWeight() {
        for (int i = 0; i < criterias.size(); i++) {
            totalWeight += criterias.get(i).getWeight();
        }
    }

    public void savePromotionCriteria() {
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");          
            if (security.checkAccess(sessionBean.getUserName(), "savePromotionCriteria", dataset)) {
//                 put ur code here...!
            requiredWeight = totalWeight + hrPromotionCriterias.getWeight();
            if (requiredWeight <= 100) {
                promotionCriteriaBeanLocal.saveOrUpdate(hrPromotionCriterias);

                if (update == 0) {
                    JsfUtil.addSuccessMessage("Saved Successfully");
                } else {
                    JsfUtil.addSuccessMessage("Update Successful.");
                }
                clearPromotionCriteria();
            } else {
                JsfUtil.addFatalMessage("Total Weight can not be greater than 100");
            }     
            
            } else {
         EventEntry eventEntry = new EventEntry();
         eventEntry.setSessionId(sessionBean.getSessionID());
         eventEntry.setUserId(sessionBean.getUserId());
         QName qualifiedName = new QName("", "project");
         JAXBElement<String> test = new JAXBElement<String> (qualifiedName,String.class,null,sessionBean.getUserName());
         eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
            security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage(hrPromotionCriterias.getLuPromoCriteriaNameId().getDescription() + " Already Exist");
        }
    }

    public void clearPromotionCriteria() {
        hrPromotionCriterias = null;
        totalWeight = 0.0;
        requiredWeight = 0.0;
        saveOrUpdateButton = "Save";
        update = 0;

    }

    public void populate(SelectEvent events) {
        hrPromotionCriterias = null;
        hrPromotionCriterias = (HrPromotionCriterias) events.getObject();
        totalWeight = totalWeight - hrPromotionCriterias.getWeight();
        saveOrUpdateButton = "Update";
        update = 1;
    }

    public SelectItem[] loadCriteria() {
        hrLuPromoCriteriaNamesList = hrLuPromoCriteriaNameBeanLocal.loadCriterias();
        return JsfUtil.getSelectItems(hrLuPromoCriteriaNamesList, true);

    }
}
