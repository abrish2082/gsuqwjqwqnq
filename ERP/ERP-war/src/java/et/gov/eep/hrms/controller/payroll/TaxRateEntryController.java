/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollTaxRateStatusBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollTaxRatesBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRateStatus;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "taxRateEntryController")
@ViewScoped
public class TaxRateEntryController implements Serializable {

    public TaxRateEntryController() {
    }
    //<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrPayrollTaxRateStatus hrPayrollTaxRateStatus;
    @Inject
    HrPayrollTaxRates payrollTaxRates;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    HrPayrollTaxRatesBeanLocal hrPayrollTaxRatesBeanLocal;
    @EJB
    HrPayrollTaxRateStatusBeanLocal hrPayrollTaxRateStatusLocal;

    private String toAmountVal;
    private String delete = "Delete";
    private String saveOrUpdate = "Save";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean unlimited;
    private boolean disable;
    private boolean disableDelete = false;
    private boolean searchPage = true;
    private boolean newPage = false;
    private HrPayrollTaxRates selectedRow = null;
    private DataModel<HrPayrollTaxRates> hrPayrollPensionsModel;

//    private boolean isUpdated = false;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstract">
    @PostConstruct
    private void init() {
        recreateModel();
        disable = false;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public HrPayrollTaxRateStatus getHrPayrollTaxRateStatus() {
        if (hrPayrollTaxRateStatus == null) {
            hrPayrollTaxRateStatus = new HrPayrollTaxRateStatus();
        }

        return hrPayrollTaxRateStatus;
    }

    public void setHrPayrollTaxRateStatus(HrPayrollTaxRateStatus hrPayrollTaxRateStatus) {
        this.hrPayrollTaxRateStatus = hrPayrollTaxRateStatus;
    }

    public HrPayrollTaxRates getPayrollTaxRates() {
        if (payrollTaxRates == null) {
            payrollTaxRates = new HrPayrollTaxRates();
        }
        return payrollTaxRates;
    }

    public void setPayrollTaxRates(HrPayrollTaxRates payrollTaxRates) {
        this.payrollTaxRates = payrollTaxRates;
    }

    public DataModel<HrPayrollTaxRates> getHrPayrollPensionsModel() {
        if (hrPayrollPensionsModel == null) {
            hrPayrollPensionsModel = new ListDataModel();
        }
        return hrPayrollPensionsModel;
    }

    public void setHrPayrollPensionsModel(DataModel<HrPayrollTaxRates> hrPayrollPensionsModel) {
        this.hrPayrollPensionsModel = hrPayrollPensionsModel;
    }

    public boolean isUnlimited() {
        return unlimited;
    }

    public void setUnlimited(boolean unlimited) {
        this.unlimited = unlimited;
    }

    public String getToAmountVal() {
        return toAmountVal;
    }

    public void setToAmountVal(String toAmountVal) {
        this.toAmountVal = toAmountVal;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
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

    public HrPayrollTaxRates getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrPayrollTaxRates selectedRow) {
        this.selectedRow = selectedRow;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
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

    public void recreateModel() {
        try {
            hrPayrollPensionsModel = null;
            List<HrPayrollTaxRates> listDataModel;
            listDataModel = hrPayrollTaxRatesBeanLocal.findAll();
            int Size = listDataModel.size();
            for (int i = 0; i < Size; i++) {
                if (listDataModel.get(i).getToAmount().toString().contains("-1")) {
                    listDataModel.get(i).setToAmoutVal("unlimited");
                } else {
                    listDataModel.get(i).setToAmoutVal(listDataModel.get(i).getToAmount().toString());
                }
            }
            hrPayrollPensionsModel = new ListDataModel(listDataModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void btnSave_TaxRate() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "btnSave_TaxRate", dataset)) {
                if (isUnlimited() == true) {
                    payrollTaxRates.setToAmount(Double.valueOf(-1));
                } else {
                    payrollTaxRates.setToAmount(Double.valueOf((getToAmountVal())));
                }
                if (isUnlimited() == true) {
                    payrollTaxRates.setToAmount(Double.valueOf(-1));
                    payrollTaxRates.setActiveYearId(hrPayrollTaxRateStatus);

                    if (saveOrUpdate.equalsIgnoreCase("Update")) {
                        if (hrPayrollTaxRatesBeanLocal.checkUnlimitedUpdate(payrollTaxRates)) {
                            JsfUtil.addFatalMessage("You can only have one Unlimited vaue!");
                        } else {
                            hrPayrollTaxRatesBeanLocal.edit(payrollTaxRates);
                            JsfUtil.addSuccessMessage("Successfully Updated");
                            cleanpage();
                            recreateModel();
                        }
                    } else {

                        if (hrPayrollTaxRatesBeanLocal.checkUnlimited()) {
                            JsfUtil.addFatalMessage("You can only have one Unlimited vaue!");
                        } else {
                            hrPayrollTaxRatesBeanLocal.edit(payrollTaxRates);
                            cleanpage();
                            recreateModel();
                            if (saveOrUpdate.equalsIgnoreCase("Save")) {
                                JsfUtil.addSuccessMessage("Successfully Saved");
                                saveOrUpdate = "Save";
                                cleanpage();
                            } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
                                JsfUtil.addSuccessMessage("Successfully Updated");
                                cleanpage();
                                saveOrUpdate = "Save";
                            }
                        }
                    }
                } else {
                    if (payrollTaxRates.getFromAmount() >= payrollTaxRates.getToAmount()) {
                        JsfUtil.addFatalMessage("[To amount] should be greater than [From amount]");
                    } else {
                        payrollTaxRates.setActiveYearId(hrPayrollTaxRateStatus);
                        if (saveOrUpdate.equalsIgnoreCase("Update")) {
                            if (hrPayrollTaxRatesBeanLocal.checkOverlapUpdate(payrollTaxRates)) {
                                JsfUtil.addFatalMessage("Check for values an overlap!");
                            } else {
                                hrPayrollTaxRatesBeanLocal.edit(payrollTaxRates);
                                JsfUtil.addSuccessMessage("Successfully Updated");
                                cleanpage();
                                recreateModel();
                            }
                        } else if (saveOrUpdate.equalsIgnoreCase("Save")) {
                            if (hrPayrollTaxRatesBeanLocal.checkOverlap(payrollTaxRates)) {
                                JsfUtil.addFatalMessage("Check for values an overlap!");
                            } else {
                                hrPayrollTaxRatesBeanLocal.edit(payrollTaxRates);
                                cleanpage();
                                recreateModel();
                                if (saveOrUpdate.equalsIgnoreCase("Save")) {
                                    JsfUtil.addSuccessMessage("Successfully Saved");
                                    saveOrUpdate = "Save";
                                    cleanpage();
                                } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
                                    JsfUtil.addSuccessMessage("Successfully Updated");
                                    saveOrUpdate = "Save";
                                    cleanpage();
                                }
                            }
                        }
                    }
                }
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
            JsfUtil.addErrorMessage("Something Occured on Data Created");
        }
    }

    public void destroyWorld() {
        JsfUtil.addSuccessMessage("Successfully Removed");
    }

    public void addDeleteMessave(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addmakeAsCurrentPension() {
        if (isUnlimited() == true) {
            setToAmountVal("Unlimited");
            disable = true;
        } else {
            disable = false;
        }
    }

    public void populate(SelectEvent events) {
        payrollTaxRates = null;
        payrollTaxRates = (HrPayrollTaxRates) events.getObject();
        saveOrUpdate = "Update";
        searchPage = false;
        newPage = true;
        hrPayrollTaxRateStatus = payrollTaxRates.getActiveYearId();
        if (payrollTaxRates.getActiveYearId().getStatus().equalsIgnoreCase("Active")) {
            disableDelete = true;
        } else {
            disableDelete = false;
        }
        if (payrollTaxRates.getToAmount() == -1) {
            setToAmountVal("Unlimited");
            disable = true;
            setUnlimited(true);
        } else {
            setToAmountVal(payrollTaxRates.getToAmount().toString());
            disable = false;
            setUnlimited(false);
        }
        btnNewOrSearch();
    }

    private void cleanpage() {
        hrPayrollTaxRateStatus = null;
        payrollTaxRates = null;
        toAmountVal = null;
    }
//</editor-fold>
}
