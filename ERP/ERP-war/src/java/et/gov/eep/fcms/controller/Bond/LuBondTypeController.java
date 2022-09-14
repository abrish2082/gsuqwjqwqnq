/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
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
import et.gov.eep.fcms.businessLogic.Bond.LuBondTypeBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsLuBondType;

/**
 *
 * @author mora
 */
@Named(value = "LuBondController")
@ViewScoped
public class LuBondTypeController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject entities
    @Inject
    FmsLuBondType luBondType;
    @Inject
    SessionBean sessionBeanLocal;
    //Inject EJB
    @EJB
    LuBondTypeBeanLocal luBondTypeBeanLocal;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    String saveUpdate = "Save";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    boolean disable = false;
//</editor-fold>

    public LuBondTypeController() {

    }
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public FmsLuBondType getLuBondType() {
        if (luBondType == null) {
            luBondType = new FmsLuBondType();
        }
        return luBondType;
    }

    public void setLuBondType(FmsLuBondType luBondType) {
        this.luBondType = luBondType;
    }

    private void saveUpdateClear() {
        luBondType = null;

    }
    int updateStatus = 0;

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
    //save method for luBond type
    public String saveLuBondType() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveLuBondType", dataset)) {
                if (updateStatus == 1) {
                    luBondTypeBeanLocal.create(null);
                    JsfUtil.addSuccessMessage("Bond type successfully register");
                } else {
                    luBondTypeBeanLocal.edit(luBondType);
                    JsfUtil.addSuccessMessage("Bond type info is Edited");
                    saveUpdate = "Save";
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

    //array list search method  to find luBond from luBondTypeBeanLocal
    public ArrayList<FmsLuBondType> searchType(String name) {
        ArrayList<FmsLuBondType> luBondTypes = null;
        luBondType.setName(name);
        luBondTypes = luBondTypeBeanLocal.searchLuBond(luBondType);
        return luBondTypes;
    }

    //select event for luBond type selected object to find luBond using luBondtype from luBondTypeBeanLocal
    public void getImportationInfo(SelectEvent event) {
        luBondType.setName(event.getObject().toString());
        luBondType = luBondTypeBeanLocal.getLuBond(luBondType);
        updateStatus = 2;
        saveUpdate = "Update";
        createNewAdditionalAmount();
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
