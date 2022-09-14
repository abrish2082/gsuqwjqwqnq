/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.perdiem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.perDiem.FmsLuCountryBeanLocal;
import et.gov.eep.fcms.entity.perDiem.FmsLuCountry;
import et.gov.eep.fcms.mapper.perDiem.FmsLuCountryFacade;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.businessLogic.ComLuCountryBeanLocal;
import et.gov.eep.fcms.controller.Constants;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "countryRegManagedBean")
@ViewScoped
public class CountryRegManagedBean implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject entities

    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuCountry fmsLuCountry;
    @Inject
    ComLuCountry comLuCountry;
    //Inject @EJB
    @EJB
    FmsLuCountryBeanLocal fmsLuCountryBeanLocal;
    @EJB
    FmsLuCountryFacade fmsLuCountryFacade;
    @EJB
    ComLuCountryBeanLocal comLuCountryBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    DataModel<FmsLuCountry> countryListDataModel;
    DataModel<FmsLuCountry> countryListSearchDataModel;
    Set<String> checkCountryname = new HashSet<>();
    List<FmsLuCountry> countryList1 = new ArrayList<>();
    FmsLuCountry selectCountry;
    ComLuCountry selectCountry1;
    List<ComLuCountry> comLuCountriesList;
    List<FmsLuCountry> fmsLuCountryList;
    List<FmsLuCountry> countryList = new ArrayList<>();
    private NumberConverter numberConverter = new NumberConverter();
    boolean btnaddvisibility = true;
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateCountry = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    boolean disable = false;
//</editor-fold>

    public CountryRegManagedBean() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);

    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    private String saveorUpdateBundle = Constants.getComponentBundle("Create");

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public FmsLuCountry getSelectCountry() {
        return selectCountry;
    }

    public void setSelectCountry(FmsLuCountry selectCountry) {
        this.selectCountry = selectCountry;
    }

    public ComLuCountry getSelectCountry1() {
        return selectCountry1;
    }

    public void setSelectCountry1(ComLuCountry selectCountry1) {
        this.selectCountry1 = selectCountry1;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public ComLuCountry getComLuCountry() {
        if (comLuCountry == null) {
            comLuCountry = new ComLuCountry();
        }
        return comLuCountry;
    }

    public void setComLuCountry(ComLuCountry comLuCountry) {
        this.comLuCountry = comLuCountry;
    }

    public FmsLuCountryBeanLocal getFmsLuCountryBeanLocal() {
        return fmsLuCountryBeanLocal;
    }

    public void setFmsLuCountryBeanLocal(FmsLuCountryBeanLocal fmsLuCountryBeanLocal) {
        this.fmsLuCountryBeanLocal = fmsLuCountryBeanLocal;
    }

    public List<ComLuCountry> getComLuCountriesList() {
        if (comLuCountriesList == null) {
            comLuCountriesList = new ArrayList<>();
        }
        comLuCountriesList = comLuCountryBeanLocal.findAll();
        return comLuCountriesList;
    }

    public void setComLuCountriesList(List<ComLuCountry> comLuCountriesList) {
        this.comLuCountriesList = comLuCountriesList;
    }

    public FmsLuCountry getFmsLuCountry() {
        if (fmsLuCountry == null) {
            fmsLuCountry = new FmsLuCountry();
        }
        return fmsLuCountry;
    }

    public void setFmsLuCountry(FmsLuCountry fmsLuCountry) {
        this.fmsLuCountry = fmsLuCountry;
    }

    public DataModel<FmsLuCountry> getCountryListDataModel() {
        if (countryListDataModel == null) {
            countryListDataModel = new ListDataModel<>();
        }
        return countryListDataModel;
    }

    public void setCountryListDataModel(DataModel<FmsLuCountry> countryListDataModel) {
        this.countryListDataModel = countryListDataModel;
    }

    public DataModel<FmsLuCountry> getCountryListSearchDataModel() {
        if (countryListSearchDataModel == null) {
            countryListSearchDataModel = new ListDataModel<>();
        }
        return countryListSearchDataModel;
    }

    public void setCountryListSearchDataModel(DataModel<FmsLuCountry> countryListSearchDataModel) {
        this.countryListSearchDataModel = countryListSearchDataModel;
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

    public boolean isRenderPnlCreateCountry() {
        return renderPnlCreateCountry;
    }

    public void setRenderPnlCreateCountry(boolean renderPnlCreateCountry) {
        this.renderPnlCreateCountry = renderPnlCreateCountry;
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

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
//</editor-fold>

    //search
    public void searchCountryByParameter() {
        fmsLuCountryList = new ArrayList<>();
        //search by country name
        if (!(comLuCountry.getCountry().isEmpty())) {
            fmsLuCountryList = fmsLuCountryBeanLocal.searchCountryByName(comLuCountry);
        } else {
            //search all
            fmsLuCountryList = fmsLuCountryBeanLocal.searchAllCountry();
        }
        countryListDataModel = new ListDataModel(fmsLuCountryList);
    }

    //search country
    public List<FmsLuCountry> SearchCountry(String couName) {
        countryList = null;
        fmsLuCountry.setCountryName(couName);
        countryList = fmsLuCountryBeanLocal.SearchCountry(fmsLuCountry);
        return countryList;
    }

    //recreate method to assign fmsLuCountryList value to countryListSearchDataModel
    private void recreateCountrySearchModel() {
        countryListSearchDataModel = null;
        countryListSearchDataModel = new ListDataModel<>(new ArrayList<>(fmsLuCountryList));
    }

    //recreate method to assign country list value  to countryListDataModel
    public void recreateModelDetail() {
        countryListDataModel = null;
        countryListDataModel = new ListDataModel(countryList);

    }

    //select event to find all country
    public void getCountryList(SelectEvent event) {
        String selectedCountryName = fmsLuCountry.getCountryName();
        fmsLuCountry.setCountryName(selectedCountryName);
        fmsLuCountry = fmsLuCountryBeanLocal.getAllCountry(fmsLuCountry);
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        btnaddvisibility = false;
    }

    //select event for country
    public void selectCountryRow(SelectEvent event) {
        btnaddvisibility = false;
        disable = true;
        fmsLuCountry = (FmsLuCountry) event.getObject();
        fmsLuCountry = fmsLuCountryBeanLocal.getById(fmsLuCountry);
        comLuCountry = fmsLuCountry.getComLuCountryId();
        renderPnlCreateCountry = true;
        renderPnlManPage = false;
        activeIndex = "0";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //Add country
    public void addCountry() {
        try {
            if (fmsLuCountryFacade.SearchC(fmsLuCountry) == null) {
                fmsLuCountry.setCountryName(fmsLuCountry.getCountryName());
                fmsLuCountry.setLodgingBreakfastAmount(fmsLuCountry.getLodgingBreakfastAmount());
                fmsLuCountry.setLunchDinnerAmount(fmsLuCountry.getLunchDinnerAmount());
                if (checkCountryname.contains(fmsLuCountry.getCountryName())) {
                    JsfUtil.addFatalMessage("Country  " + fmsLuCountry.getCountryName() + "  is Duplicated");
                } else {
                    fmsLuCountry.setComLuCountryId(comLuCountry);
                    countryList.add(fmsLuCountry);
                    checkCountryname.add(fmsLuCountry.getCountryName());
                    recreateModelDetail();
                    fmsLuCountry = null;
                    comLuCountry = null;
                }
            } else {
                JsfUtil.addFatalMessage("Country  " + fmsLuCountry.getComLuCountryId().getCountry() + "  is Found in Database");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Failed! Try again");
        }
    }

    //save country
    public String saveLuCountry() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveLuCountry", dataset)) {
            boolean isDup = fmsLuCountryBeanLocal.searchCountryByID(comLuCountry);
            if (updateStatus == 0 && isDup == false) {
                try {
                    fmsLuCountry.setComLuCountryId(comLuCountry);
                    fmsLuCountryBeanLocal.create(fmsLuCountry);
                    JsfUtil.addSuccessMessage("Registered Successfuly!");
                    clearPage();
                    return null;
                } catch (Exception ex) {
                    JsfUtil.addFatalMessage("Failed to save. Try again Reloading the page.");
                    return null;
                }
            } else if (updateStatus == 0 && isDup == true) {
                JsfUtil.addFatalMessage("Duplication Error. Counrty is Already registered");
            } else {
                fmsLuCountryBeanLocal.edit(fmsLuCountry);
                JsfUtil.addSuccessMessage("Updated Successfully!");
                clearPage();
                return null;
            }
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(SessionBean.getSessionID());
            eventEntry.setUserId(SessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
            eventEntry.setUserLogin(test);
            security.addEventLog(eventEntry, dataset);

        }
        return null;

    }

    //clear
    public String clearPopup() {
        fmsLuCountry = null;
        countryListDataModel = null;
        return null;

    }

    //clear
    private void clearPage() {
        disable = false;
        fmsLuCountry = null;
        comLuCountriesList = null;
        comLuCountry = null;
        countryListDataModel = null;
        updateStatus = 0;
        saveorUpdateBundle = "Create";
    }

    //create and search render
    public void createNewCountry() {
        clearPage();
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateCountry = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateCountry = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
