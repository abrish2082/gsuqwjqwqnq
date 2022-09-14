/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package et.gov.insa.erp.ibfms.controller.admin;
package et.gov.eep.fcms.controller.adminstrator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.controller.Constants;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;

/**
 *
 * @author Abiy
 */
@Named(value = "voucherNumberRangeManagedBean")
@ViewScoped
public class VoucherNumberRangeManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="@EJB and @Inject declaration">
    //@Inject
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuVouchersType luVouchersTypeEnty;
    @Inject
    FmsVouchersNoRange vouchersNoRangeEnty;

    //@EJB
    @EJB
    FmsVouchersNoRangeBeanLocal vouchersNoRangeBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    int updteStatus = 0;
    int dataTableUpdateStatus = 0;
    DataModel<FmsVouchersNoRange> vouchersNoRangesDataModel;
    List<FmsVouchersNoRange> vouchersNoRangesList = new ArrayList<>();
    FmsVouchersNoRange vouchersNoRangeSearch;
    private String saveorUpdateBundle = Constants.getComponentBundle("Create");
    private String StartDate;
    private String EndDate;
    private String currPeriod;
    private String createOrSearchBundle = "New";
    private String headerTitle = "Voucher Number Range Search";
    private String icone = "ui-icon-plus";
    private String saveBtnRend = "Save";
    String username;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private Boolean addBtnRend = true;
    private FmsVouchersNoRange selectedVoucher;

    //</editor-fold>
    
    public VoucherNumberRangeManagedBean() {
    }

    @PostConstruct
    public void getFiscalYear() {
        vouchersNoRangeEnty.setFiscalYearId(fmsAccountingPeriodBeanLocal.getCurretActivePeriod());
        setUsername(SessionBean.getUserName());
        CurrentPeriod();
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FmsLuVouchersType getLuVouchersTypeEnty() {
        if (luVouchersTypeEnty == null) {
            luVouchersTypeEnty = new FmsLuVouchersType();
        }
        return luVouchersTypeEnty;
    }

    public DataModel<FmsVouchersNoRange> getVouchersNoRangesDataModel() {
        if (vouchersNoRangesDataModel == null) {
            vouchersNoRangesDataModel = new ListDataModel<>();
        }
        return vouchersNoRangesDataModel;
    }

    public void setVouchersNoRangesDataModel(DataModel<FmsVouchersNoRange> vouchersNoRangesDataModel) {
        this.vouchersNoRangesDataModel = vouchersNoRangesDataModel;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public FmsVouchersNoRange getSelectedVoucher() {
        return selectedVoucher;
    }

    public void setSelectedVoucher(FmsVouchersNoRange selectedVoucher) {
        this.selectedVoucher = selectedVoucher;
    }

    public Boolean getAddBtnRend() {
        return addBtnRend;
    }

    public void setAddBtnRend(Boolean addBtnRend) {
        this.addBtnRend = addBtnRend;
    }

    public String getSaveBtnRend() {
        return saveBtnRend;
    }

    public void setSaveBtnRend(String saveBtnRend) {
        this.saveBtnRend = saveBtnRend;
    }

    public FmsVouchersNoRange getVouchersNoRangeSearch() {
        if (vouchersNoRangeSearch == null) {
            vouchersNoRangeSearch = new FmsVouchersNoRange();
        }
        return vouchersNoRangeSearch;
    }

    public void setVouchersNoRangeSearch(FmsVouchersNoRange vouchersNoRangeSearch) {
        this.vouchersNoRangeSearch = vouchersNoRangeSearch;
    }

    public FmsVouchersNoRange getVouchersNoRangeEnty() {
        if (vouchersNoRangeEnty == null) {
            vouchersNoRangeEnty = new FmsVouchersNoRange();
        }
        return vouchersNoRangeEnty;
    }

    public void setVouchersNoRangeEnty(FmsVouchersNoRange vouchersNoRangeEnty) {
        this.vouchersNoRangeEnty = vouchersNoRangeEnty;
    }

    public void setLuVouchersTypeEnty(FmsLuVouchersType luVouchersTypeEnty) {
        this.luVouchersTypeEnty = luVouchersTypeEnty;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(String currPeriod) {
        this.currPeriod = currPeriod;
    }

    public int getUpdteStatus() {
        return updteStatus;
    }

    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    //</editor-fold>    
    //recreate method
    public void recreateModelDetail() {
        vouchersNoRangesDataModel = null;
        vouchersNoRangesDataModel = new ListDataModel(vouchersNoRangesList);
    }

    //search
    public String btnSearchVoucherType_Action() {
        vouchersNoRangesList = vouchersNoRangeBeanLocal.searchByVoucherType(vouchersNoRangeSearch);
        recreateModelDetail();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        return null;
    }

    //save
    public void btnSaveVNR() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "btnSaveVNR", dataset)) {
                vouchersNoRangeEnty.setPreparedBy(SessionBean.getUserName());
                addVoucherNoDetail();
                if (updteStatus == 0) {
                    try {
                        for (int i = 0; i < vouchersNoRangesList.size(); i++) {
                            vouchersNoRangeBeanLocal.create(vouchersNoRangesList.get(i));
                        }
                        JsfUtil.addSuccessMessage("Data Created");
                        clearPage();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Occured on Data Created");
                    }
                } else if (updteStatus == 1) {
                    for (int i = 0; i < vouchersNoRangesList.size(); i++) {
                        vouchersNoRangeBeanLocal.edit(vouchersNoRangesList.get(i));
                    }
                    JsfUtil.addSuccessMessage("Data is Updated");
                    clearPage();
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
        } catch (Exception ex) {
        }
    }

    //update subsidiary ledger detail
    public void updateSubsidiaryLedgerDetail() {
        vouchersNoRangeEnty = getVouchersNoRangesDataModel().getRowData();
        dataTableUpdateStatus = 1;

        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Voucher Number Range Search";
        setIcone("ui-icon-search");
    }

    //value chage event for Change vouchers No Range
    public void handleChangevouchersNoRange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            FmsLuVouchersType vouchersType = new FmsLuVouchersType();
            if (event.getNewValue().toString().equalsIgnoreCase("All")) {
                vouchersType.setTypeName("All");
                vouchersType.setId(-1);
            } else {
                vouchersType.setTypeName(event.getNewValue().toString());
                vouchersType = vouchersNoRangeBeanLocal.searchPaymentType(vouchersType);
            }
            vouchersNoRangeSearch.setTypeId(vouchersType);
        }
    }

    //select event
    public void populateVouchField(SelectEvent event) {
        vouchersNoRangeEnty.setId(Integer.parseInt(event.getObject().toString()));
        vouchersNoRangeEnty.getId();
        vouchersNoRangeEnty = vouchersNoRangeBeanLocal.getCurrentVoucherById(vouchersNoRangeEnty);
        dataTableUpdateStatus = 1;
        updteStatus = 1;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Voucher Number Range Registration";
        setIcone("ui-icon-search");
        saveBtnRend = "Update";
        addBtnRend = false;
    }

    //select item
    public SelectItem[] getLuVouchersTypeSearchList() {
        FmsLuVouchersType vouchersType = new FmsLuVouchersType();
        vouchersType.setTypeName("All");
        vouchersType.setId(-1);
        List<FmsLuVouchersType> luVouchersTypesList = vouchersNoRangeBeanLocal.getLuVouchersType();
        luVouchersTypesList.add(vouchersType);
        return JsfUtil.getSelectItems(luVouchersTypesList, true);
    }

    //select item
    public SelectItem[] getLuVouchersTypeList() {
        return JsfUtil.getSelectItems(vouchersNoRangeBeanLocal.getLuVouchersType(), true);
    }

    //current period
    public void CurrentPeriod() {
        StartDate = vouchersNoRangeEnty.getFiscalYearId().getStartDate();
        EndDate = vouchersNoRangeEnty.getFiscalYearId().getEndDate();
        currPeriod = "[" + StartDate + "]" + " To " + "[" + EndDate + "]";
    }

    //snumber format exception
    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //select items
    public SelectItem[] getSystemList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //add voucher
    public String addVoucherNoDetail() {
        getFiscalYear();
        vouchersNoRangeEnty.setStatus(1);
        vouchersNoRangeEnty.setCurrentNo(String.valueOf(Integer.parseInt(vouchersNoRangeEnty.getStartingNo())));
        vouchersNoRangesList.add(vouchersNoRangeEnty);
        recreateModelDetail();
        return clearPopup();
    }

    //clear
    public String clearPopup() {
        dataTableUpdateStatus = 0;
        vouchersNoRangeEnty = null;
        vouchersNoRangesDataModel = null;
        saveBtnRend = "Save";
        return null;
    }

    //clear
    public String clearPage() {
        vouchersNoRangesDataModel = null;
        vouchersNoRangesList = null;
        vouchersNoRangeEnty = null;
        vouchersNoRangeSearch = null;
        return null;
    }

    //create and search button
    public void createNewVNRView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Voucher Number Range Registration";
            setIcone("ui-icon-search");
            addBtnRend = true;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Voucher Number Range Search";
            setIcone("ui-icon-plus");

        }
        clearPopup();
    }
}
