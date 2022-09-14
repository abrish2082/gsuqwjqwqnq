/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

/**
 *
 * @author mora
 */
@Named(value = "FmsTypeController")
@ViewScoped
public class FmsBondTypeController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject EJB

    @EJB
    private FmsBondTypeBeanLocal beanLocal;
    //Inject entities
    @Inject
    private FmsBondType BondType;
    @Inject
    SessionBean sessionBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean disableBtnCreate;
    boolean disable = false;
    String saveUpdate = "Save";
    int updateStatus = 1;
    DataModel<FmsBondType> BondtypesDatamodel;
    private List<FmsBondType> Bondtypes;
//</editor-fold>

    List<FmsBondType> BondTypes() {
        if (Bondtypes == null) {
            Bondtypes = new ArrayList<>();
        }
        return Bondtypes;
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public FmsBondType getBondType() {
        if (BondType == null) {
            BondType = new FmsBondType();
        }
        return BondType;
    }

    public DataModel<FmsBondType> getBondtypesDatamodel() {
        if (BondtypesDatamodel == null) {
            BondtypesDatamodel = new ListDataModel<>();
        }
        return BondtypesDatamodel;
    }

    public void setBondtypesDatamodel(DataModel<FmsBondType> BondtypesDatamodel) {
        this.BondtypesDatamodel = BondtypesDatamodel;
    }

    public List<FmsBondType> getBondLiborlist() {
        return Bondtypes;
    }

    public void setBondLiborlist(List<FmsBondType> Bondtypes) {
        this.Bondtypes = Bondtypes;
    }

    public void setBondType(FmsBondType BondType) {
        this.BondType = BondType;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    //</editor-fold> 
    //search Bond local by Bond type
    public ArrayList<FmsBondType> searchBondType(String BondTypeId) {
        ArrayList<FmsBondType> BondTypes = null;
        BondType.setBondTypeId(BondTypeId);
        BondTypes = beanLocal.searchBondType(BondType);
        return BondTypes;
    }

  //select event to get Bond type info from Bond local
    public void getImportationInfo(SelectEvent event) {
        BondType.setBondTypeId(event.getObject().toString());
        BondType = beanLocal.getBondTypeInfo(BondType);
        saveUpdate = "Update";
        updateStatus = 2;
    }

    // save method for Bond type
    public String saveFmsBondType() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "cfg/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveFmsBondType", dataset)) {
                if (updateStatus == 1) {
                    beanLocal.create(BondType);
                    JsfUtil.addSuccessMessage("operating Budget successfully register");
                } else {
                    beanLocal.edit(BondType);
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
        BondType = null;

    }

    //create and search render method
    public void createNewAdditionalAmount() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
