/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.promotion;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.promotion.HrLuPromExpRelationBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.HrPromExprinceCriteriaBeanLocal;
import et.gov.eep.hrms.entity.promotion.HrLuPromExpRelation;
import et.gov.eep.hrms.entity.promotion.HrPromExprinceCriteria;
import et.gov.eep.hrms.entity.promotion.HrPromotionEducCriterias;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("promExprienceControllers")
@ViewScoped
public class PromExprienceController implements Serializable {

    @Inject
    SessionBean sessionBean;
    @EJB
    HrLuPromExpRelationBeanLocal hrLuPromExpRelationBeanLocal;
    @EJB
    HrPromExprinceCriteriaBeanLocal hrPromExprinceCriteriaBeanLocal;
    @Inject
    HrLuPromExpRelation hrLuPromExpRelations;
    @Inject
    HrPromExprinceCriteria hrPromExprinceCriteria;
    List<HrPromExprinceCriteria> hrPromExprinceCriterias = new ArrayList<>();
    int update = 0;
    private String saveOrUpdateButton = "Save";
    private HrPromExprinceCriteria selectedRow;

    @PostConstruct
    public void init() {
        hrPromExprinceCriterias = hrPromExprinceCriteriaBeanLocal.findAllExprienceRelations();
    }

    public HrPromExprinceCriteria getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrPromExprinceCriteria selectedRow) {
        this.selectedRow = selectedRow;
    }

    public SelectItem[] getExprienceRelation() {
        return JsfUtil.getSelectItems(hrLuPromExpRelationBeanLocal.findAll(), true);
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

    public HrPromExprinceCriteria getHrPromExprinceCriteria() {
        if (hrPromExprinceCriteria == null) {
            hrPromExprinceCriteria = new HrPromExprinceCriteria();
        }
        return hrPromExprinceCriteria;
    }

    public void setHrPromExprinceCriteria(HrPromExprinceCriteria hrPromExprinceCriteria) {
        this.hrPromExprinceCriteria = hrPromExprinceCriteria;
    }

    public List<HrPromExprinceCriteria> getHrPromExprinceCriterias() {
        return hrPromExprinceCriterias;
    }

    public void setHrPromExprinceCriterias(List<HrPromExprinceCriteria> hrPromExprinceCriterias) {
        this.hrPromExprinceCriterias = hrPromExprinceCriterias;
    }

    public void addExprience() {
        hrPromExprinceCriterias.remove(hrPromExprinceCriteria);
        if (hrPromExprinceCriterias.containsAll(hrPromExprinceCriteria.getLuPromExprienceRelation().getHrPromExprinceCriteriaList())) {
            System.out.println("Data Already Exit");
            JsfUtil.addErrorMessage("DaTa Already Exit");
        } else {
            hrPromExprinceCriterias.add(hrPromExprinceCriteria);
            reset();
        }
    }

    public void reset() {
        hrPromExprinceCriteria = null;
    }

    public void populate(SelectEvent events) {
        hrPromExprinceCriteria = null;
        hrPromExprinceCriteria = (HrPromExprinceCriteria) events.getObject();
        update = 1;
        saveOrUpdateButton = "Update";
    }

    public void savePromExprinceWeight() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "savePromExprinceWeight", dataset)) {
//                 put ur code here...!
                hrPromExprinceCriteriaBeanLocal.saveOrUpdate(hrPromExprinceCriteria);
                if (update == 0) {
                    JsfUtil.addSuccessMessage("Saved Successfully");
                } else {
                    JsfUtil.addSuccessMessage("Updated Successfully");
                }
                clearPromExprinceWeight();

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");

                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage(hrPromExprinceCriteria.getLuPromExprienceRelation().getDescription() + " Already Exisit");
        }
    }

    public void clearPromExprinceWeight() {
        hrPromExprinceCriteria = null;
        saveOrUpdateButton = "Save";
        update = 0;
    }
}
