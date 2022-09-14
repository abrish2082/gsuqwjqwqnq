/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondTypeBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondType;

@Named(value = "bondTypeController")
@ViewScoped
public class BondTypeController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //inject @EJB
    @EJB
    private FmsBondTypeBeanLocal fmsBondTypeBeanLocal;

    //Inject entities
    @Inject
    private FmsBondType fmsBondType;
    @Inject
    SessionBean sessionBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    int updateStatus = 1;
    String saveUpdate = "Create";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsBondType getFmsBondType() {
        if (fmsBondType == null) {
            fmsBondType = new FmsBondType();
        }
        return fmsBondType;
    }

    public void setFmsBondType(FmsBondType fmsBondType) {
        this.fmsBondType = fmsBondType;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="array list">
    public ArrayList<FmsBondType> searchBondType(String BondTypeId) {
        ArrayList<FmsBondType> BondTypes = null;
        fmsBondType.setBondTypeId(BondTypeId);
        BondTypes = fmsBondTypeBeanLocal.searchBondType(fmsBondType);
        return BondTypes;
    }

    public ArrayList<FmsBondType> searchType(String BondTypeId) {
        ArrayList<FmsBondType> BondTypes = null;
        fmsBondType.setBondTypeId(BondTypeId);
        BondTypes = fmsBondTypeBeanLocal.searchBondType(fmsBondType);
        return BondTypes;
    }
//</editor-fold>

    //select event for Bond type
    public void getImportationInfo(SelectEvent event) {
        fmsBondType.setBondTypeId(event.getObject().toString());
        fmsBondType = fmsBondTypeBeanLocal.getBondTypeInfo(fmsBondType);
        updateStatus = 2;
        saveUpdate = "Update";
    }

    //save method for Bond type
    public String saveBondType() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "cfg/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveBondType", dataset)) {

                if (updateStatus == 1) {
                    fmsBondTypeBeanLocal.create(null);
                    JsfUtil.addSuccessMessage("operating Budget successfully register");
                } else {
                    fmsBondTypeBeanLocal.edit(fmsBondType);
                    JsfUtil.addSuccessMessage("operating Budget info is Edited");
                    saveUpdate = "Create";
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
        saveUpdateClear();
        return null;
    }

    //save update clear method
    private void saveUpdateClear() {
        fmsBondType = null;

    }
}
