/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.adminstrator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountTypeBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountType;

/**
 *
 * @author memube
 */
@Named(value = "fmsAccountTypeController")
@ViewScoped
public class FmsAccountTypeController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="@EJB and @Inject declaration">
//@Inject

    @Inject
    private FmsAccountType fmsAccountType;

    //@EJB
    @EJB
    private FmsAccountTypeBeanLocal fmsAccountTypeBeanLocal;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="variable declaration">
    private DataModel<FmsAccountType> accTypeDataModel;
    private List<FmsAccountType> acctTypeList;
    private List<FmsAccountType> selectedAcountType;
    String saveorUpdateBundle = "Save";
    String isSticky = "false";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    int updteStatus = 0;
    boolean isDupName;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateAccType = false;
    private boolean renderPnlManPage = true;
//</editor-fold>

    @PostConstruct
    public void init() {
    }

    /**
     * Creates a new instance of FmsAccountTypeController
     */
    public FmsAccountTypeController() {
    }

    //<editor-fold defaultstate="collapsed" desc="getting and setter">
    public FmsAccountType getFmsAccountType() {
        if (fmsAccountType == null) {
            fmsAccountType = new FmsAccountType();
        }
        return fmsAccountType;
    }

    public void setFmsAccountType(FmsAccountType fmsAccountType) {
        this.fmsAccountType = fmsAccountType;
    }

    public DataModel<FmsAccountType> getAccTypeDataModel() {
        accTypeDataModel = null;
        accTypeDataModel = new ListDataModel();
        return accTypeDataModel;
    }

    public void setAccTypeDataModel(DataModel<FmsAccountType> accTypeDataModel) {
        this.accTypeDataModel = accTypeDataModel;
    }

    public List<FmsAccountType> getAcctTypeList() {
        if (acctTypeList == null) {
            acctTypeList = new ArrayList<>();
        }
        acctTypeList = fmsAccountTypeBeanLocal.findAll();
        return acctTypeList;
    }

    public void setAcctTypeList(List<FmsAccountType> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public List<FmsAccountType> getSelectedAcountType() {
        return selectedAcountType;
    }

    public void setSelectedAcountType(List<FmsAccountType> selectedAcountType) {
        this.selectedAcountType = selectedAcountType;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getUpdteStatus() {
        return updteStatus;
    }

    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
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

    public boolean isRenderPnlCreateAccType() {
        return renderPnlCreateAccType;
    }

    public void setRenderPnlCreateAccType(boolean renderPnlCreateAccType) {
        this.renderPnlCreateAccType = renderPnlCreateAccType;
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

    public String getIsSticky() {
        return isSticky;
    }

    public void setIsSticky(String isSticky) {
        this.isSticky = isSticky;
    }

    public List<FmsAccountType> findAllAcctTypes() {
        return fmsAccountTypeBeanLocal.findAll();
    }

    //</editor-fold>
    
    //save method
    public String saveFcmsAccountTypes() {
//        AAA securityService = new AAA();
//        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//        String systemBundle = "cfg/securityServer";
//        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//        if (security.checkAccess(SessionBean.getUserName(), "saveFcmsBank", dataset)) {
        isDupName = fmsAccountTypeBeanLocal.findDupByAcctType(fmsAccountType);
        if (updteStatus == 0) {//on Save
            if (isDupName == false) {
                try {
                    fmsAccountTypeBeanLocal.create(fmsAccountType);
                    JsfUtil.addSuccessMessage("Registered Successfully!");
                    isSticky = "false";
                    saveUpdateClear();
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Register. Try Again Reloading the Page.");
                }
            } else if (isDupName == true) {
                isSticky = "true";
                JsfUtil.addFatalMessage("Duplication! Account Type: '" + fmsAccountType.getType() + "' is Already Registered.");
            }
        } else {//on Update
            if (isDupName == false) {
                try {
                    fmsAccountTypeBeanLocal.edit(fmsAccountType);
                    JsfUtil.addSuccessMessage("Updated Successfully!");
                    isSticky = "false";
                    saveorUpdateBundle = "Save";
                    saveUpdateClear();
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Update! Try again.");
                }
            } else if (isDupName == true) {
                isSticky = "true";
                JsfUtil.addFatalMessage("Unique Constraint! Account Type: '" + fmsAccountType.getType() + " is Already Registered.");
            }
        }
//        } else {
//            EventEntry eventEntry = new EventEntry();
//            eventEntry.setSessionId(SessionBean.getSessionID());
//            eventEntry.setUserId(SessionBean.getUserId());
//            QName qualifiedName = new QName("", "project");
//            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
//            eventEntry.setUserLogin(test);
//            security.addEventLog(eventEntry,dataset);
//
//        }
        return null;
    }

    //select event
    public void populate(SelectEvent event) {
        fmsAccountType = (FmsAccountType) event.getObject();
        renderPnlCreateAccType = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
    }

    //clear 
    private void saveUpdateClear() {
        fmsAccountType = null;
        accTypeDataModel = null;
    }

    //create and search button
    public void createNewAcctType() {
        saveUpdateClear();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAccType = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAccType = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

}
