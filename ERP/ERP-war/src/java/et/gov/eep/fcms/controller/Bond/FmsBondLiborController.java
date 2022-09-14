/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.SelectItem;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import et.gov.eep.fcms.entity.Bond.FmsBondApplication;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondLiborBeanLocal;
import et.gov.eep.fcms.businessLogic.LuCurrencyBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondLibor;

/**
 *
 * @author mora
 */
@Named(value = "fmsBondLiborController")
@ViewScoped
public class FmsBondLiborController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject bussiness logics with @EJB
    @EJB
    FmsBondLiborBeanLocal bondLiborBeanLocal;
    @EJB
    LuCurrencyBeanLocal luCurrencyBeanLocal;
    //Inject entities
    @Inject
    FmsLuCurrency luCurrency;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsBondLibor BondLibor;
    @Inject
    FmsBondApplication BondApplication;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    int updateStatus = 1;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    boolean disable = false;
    String saveUpdate = "Save";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private Date days;
    DataModel<FmsBondLibor> BondLiborsAddDatamodel;
    private List<FmsBondLibor> BondLibors;
    private FmsBondLibor BondLiborselect;
//</editor-fold>

    /**
     * Creates a new instance of FmsBondLiborController
     */
    public FmsBondLiborController() {

    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsBondLibor getBondLibor() {
        if (BondLibor == null) {
            BondLibor = new FmsBondLibor();
        }
        return BondLibor;
    }

    public void setBondLibor(FmsBondLibor BondLibor) {
        this.BondLibor = BondLibor;
    }

    public FmsLuCurrency getLuCurrency() {
        if (luCurrency == null) {
            luCurrency = new FmsLuCurrency();
        }
        return luCurrency;
    }

    public void setLuCurrency(FmsLuCurrency luCurrency) {

        this.luCurrency = luCurrency;
    }

    public FmsBondApplication getBondApplication() {
        if (BondApplication == null) {
            BondApplication = new FmsBondApplication();
        }
        return BondApplication;
    }

    public void setBondApplication(FmsBondApplication BondApplication) {
        this.BondApplication = BondApplication;
    }

    public List<FmsBondLibor> getBondLibors() {
        if (BondLibors == null) {
            BondLibors = new ArrayList<>();

        }
        return BondLibors;
    }

    public void setBondLibors(List<FmsBondLibor> BondLibors) {
        this.BondLibors = BondLibors;
    }

    public DataModel<FmsBondLibor> getBondLiborsAddDatamodel() {
        if (BondLiborsAddDatamodel == null) {
            BondLiborsAddDatamodel = new ArrayDataModel<>();
        }
        return BondLiborsAddDatamodel;
    }

    public void setBondLiborsAddDatamodel(DataModel<FmsBondLibor> BondLiborsAddDatamodel) {
        this.BondLiborsAddDatamodel = BondLiborsAddDatamodel;
    }

    public Date getDays() {
        return days;
    }

    public void setDays(Date days) {
        this.days = days;
    }

    public FmsBondLibor getBondLiborselect() {
        return BondLiborselect;
    }

    public void setBondLiborselect(FmsBondLibor BondLiborselect) {
        this.BondLiborselect = BondLiborselect;
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

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

//</editor-fold> 
    //save method for Bond libor
    public void saveBondLibor() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveBondLibor", dataset)) {

                if (BondLibor == null) {
                    JsfUtil.addSuccessMessage("Bond Libor not register plase add one");

                } else if (updateStatus == 1) {
                    if (BondLibor.getStartDate().getTime() < BondLibor.getEndDate().getTime()) {
                        Date checkday = BondLibor.getStartDate();
                        String checkccurrency = BondLibor.getFmsLuCurrency().getCurrency();
                        FmsBondLibor checkBondLibor = bondLiborBeanLocal.searchdays(BondLibor.getStartDate(), BondLibor.getFmsLuCurrency().getCurrencyId());
                        if (checkBondLibor == null) {
                            bondLiborBeanLocal.Create(BondLibor);
                            JsfUtil.addSuccessMessage("Bond Libor successfully registered");
                            saveUpdateClear();
                        } else {
                            JsfUtil.addInformationMessage("Bond Libor is already registerd Please Check for update");
                        }
                    } else {
                        JsfUtil.addInformationMessage("Start Date must be before End Date");
                    }

                } else if (updateStatus == 0) {
                    bondLiborBeanLocal.edit(BondLibor);

                    JsfUtil.addSuccessMessage("Bond Libor successfully updated");
                    saveUpdateClear();
                    updateStatus = 1;
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
    }

//update method
    public void updateSchedule() {
        bondLiborBeanLocal.edit(BondLibor);
    }

    //method for Bond libor fetching raw data for update
    public void updateLibor() {
        FmsBondLibor BondLibor = new FmsBondLibor();
        BondLibor = getBondLiborsAddDatamodel().getRowData();

    }

    //libor list add method
    public String addBondLiborInfoDetail() {

        getBondLibors().add(BondLibor);
        recreateModelSrnDetailPop();
        clearPopUp();
        return null;
    }

    //recreate method for assigning value of BondLibors to BondLiborsAddDatamodel
    public void recreateModelSrnDetailPop() {
        BondLiborsAddDatamodel = null;
        BondLiborsAddDatamodel = new ListDataModel(getBondLibors());
    }

    //<editor-fold defaultstate="collapsed" desc="array list and select item methods">
    public ArrayList<FmsBondLibor> searchBondLibor() {
        ArrayList<FmsBondLibor> BondLibors = null;
        BondLibors = (ArrayList<FmsBondLibor>) bondLiborBeanLocal.searchLibor(BondLibor);
        return BondLibors;
    }

    public SelectItem[] getCurrencyList() {
        return JsfUtil.getSelectItems(luCurrencyBeanLocal.findAll(), true);

    }
    //</editor-fold>

    //search method using currency and date and by default
    public void searchBondLiborr() {

        if (luCurrency.getCurrencyId() != null && days != null) {
            BondLibor.setDay(days);
            BondLibors = bondLiborBeanLocal.searchByDateAndCurrncy(BondLibor);
            setBondLibors(BondLibors);
            recreateModelSrnDetailPop();
        } else if (luCurrency.getCurrencyId() != null && days == null) {
            BondLibors = bondLiborBeanLocal.searchCrance(BondLibor);
            setBondLibors(BondLibors);
            recreateModelSrnDetailPop();
        } else {
            BondLibors = bondLiborBeanLocal.searchAll();
            setBondLibors(BondLibors);
            recreateModelSrnDetailPop();
        }
    }

    // calculating time diffrence
    public long Days(Date d1, Date d2) {
        return TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());
    }

    //select event for frendering option for selected event
    public void getLiborno(SelectEvent event) {
        BondLibor = ((FmsBondLibor) event.getObject());
        recreateModelSrnDetailPop();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveUpdate = "Update";
        updateStatus = 0;
    }

    /*value change event for handling currency*/
    public void handleSelectCurrency(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            luCurrency = (FmsLuCurrency) event.getNewValue();
            BondLibor.setFmsLuCurrency(luCurrency);
        }
    }

    /*value change event for handling selected event*/
    public void handleSelectDate(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        }
    }

    /*getting start date as min date*/
    public Date getminOfEndDate() {
        return this.BondLibor.getStartDate();
    }

    //save update clear
    private void saveUpdateClear() {
        BondLibor = null;
        BondLiborsAddDatamodel = null;
    }

    //clear method
    private void clearPopUp() {
        BondLibor = null;
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
        clearPopUp();
    }
}
