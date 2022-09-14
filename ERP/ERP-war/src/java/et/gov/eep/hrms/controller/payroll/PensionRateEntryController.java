/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;
//<editor-fold defaultstate="collapsed" desc="Imports">

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPensionRatesBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollPensionRates;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "pensionRateEntryController")
@ViewScoped
public class PensionRateEntryController implements Serializable {

    /**
     * Creates a new instance of PensionRateEntryController
     */
    public PensionRateEntryController() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">
    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrPayrollPensionRates hrPayrollPensionRates;
    @EJB
    HrPayrollPensionRatesBeanLocal hrPayrollPensionRatesLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;

    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean makeActive;
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private String saveOrUpdate = "Save";
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public HrPayrollPensionRates getHrPayrollPensionRates() {
        return hrPayrollPensionRates;
    }

    public void setHrPayrollPensionRates(HrPayrollPensionRates hrPayrollPensionRates) {
        this.hrPayrollPensionRates = hrPayrollPensionRates;
    }

    private List<HrPayrollPensionRates> listOfPensionRates;

    public List<HrPayrollPensionRates> getListOfPensionRates() {
        listOfPensionRates = hrPayrollPensionRatesLocal.findAll();
        return listOfPensionRates;
    }

    public HrPayrollPeriods getHrPayrollPeriods() {
        return hrPayrollPeriods;
    }

    public void setHrPayrollPeriods(HrPayrollPeriods hrPayrollPeriods) {
        this.hrPayrollPeriods = hrPayrollPeriods;
    }

    public void setListOfPensionRates(List<HrPayrollPensionRates> listOfPensionRates) {
        this.listOfPensionRates = listOfPensionRates;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isMakeActive() {
        return makeActive;
    }

    public void setMakeActive(boolean makeActive) {
        this.makeActive = makeActive;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void populate(SelectEvent events) {
        hrPayrollPensionRates = null;
        hrPayrollPensionRates = (HrPayrollPensionRates) events.getObject();
        saveOrUpdate = "Update";
        searchPage = false;
        newPage = true;
        if (hrPayrollPensionRates.getStatus().equalsIgnoreCase("Active")) {
            makeActive = true;
        } else {
            makeActive = false;
        }
        btnNewOrSearch();
    }

    public void savePayrollPeriod() {
        try {
            hrPayrollPeriodsLocal.edit(hrPayrollPeriods);
            JsfUtil.addSuccessMessage("Successfully Saved!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetValues() {
        hrPayrollPensionRates = new HrPayrollPensionRates();
        saveOrUpdate = "Save";
    }

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void savePension() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePension", dataset)) {
                if (makeActive) {
                    hrPayrollPensionRates.setStatus("1");
                } else {
                    hrPayrollPensionRates.setStatus("0");
                }
                if (saveOrUpdate.equalsIgnoreCase("Save")) {
                    hrPayrollPensionRatesLocal.updatePensionRates();
                    hrPayrollPensionRatesLocal.create(hrPayrollPensionRates);
                    JsfUtil.addSuccessMessage("Successfully Saved!");
                } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
                    hrPayrollPensionRatesLocal.updatePensionRates();
                    hrPayrollPensionRatesLocal.edit(hrPayrollPensionRates);
                    JsfUtil.addSuccessMessage("Successfully Updated!");
                }
                resetValues();
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>
}
