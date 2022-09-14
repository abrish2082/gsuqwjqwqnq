/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

// <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceNotificationToBankBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceRegisterationBeanLocal;
import et.gov.eep.prms.businessLogic.ForeignExchangeBeanLocal;
import et.gov.eep.prms.businessLogic.Bank_Clearance_RegistrationBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanBeanLocal;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.businessLogic.ContainerAgreementBeanLocal;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import java.io.InputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.TreeNode;
import java.io.IOException;
import org.insa.model.DocumentModel;
import org.primefaces.model.StreamedContent;
import securityBean.SessionBean;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import javax.annotation.PostConstruct;
import securityBean.WorkFlow;
import securityBean.Constants;
// </editor-fold>

/**
 *
 * @author Bayisa Bedasa
 */
//Foreign Currency Exchange view scoped CDI Named Bean class
@Named("foreignCurrency")
@ViewScoped
public class ForeignExchangeController implements Serializable {

// <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    ForeignExchangeBeanLocal foreignExchangeBeanLocal;
    @EJB
    MmsItemRegisrtationBeanLocal mmsItemRegisrtationBeanLocal;
    @EJB
    FmsLoanBeanLocal fmsLoanBeanLocal;
    @EJB
    InsuranceRegisterationBeanLocal insuranceRegisterationBeanLocal;
    @EJB
    InsuranceNotificationToBankBeanLocal insuranceNotificationToBankBeanLocal;
    @EJB
    Bank_Clearance_RegistrationBeanLocal bank_Clearance_RegistrationBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @EJB
    ContainerAgreementBeanLocal containerAgreementBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsForeignExchange prmsForeignExchange;
    @Inject
    PrmsPortEntry prmsPortEntry;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    PrmsServiceProviderDetail prmsServiceProviderBranch;
    @Inject
    FmsLoan fmsLoan;
    @Inject
    MmsItemRegistration mmsItemRegistration;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrAddresses hrAddressesCountry;
    @Inject
    FmsLuCurrency fobCurrency;
    @Inject
    FmsLuCurrency currency;
    @Inject
    FmsLuCurrency freightCurrency;
    @Inject
    FmsLuCurrency candfCurrency;
    @Inject
    DataUpload dataUpload;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    @Inject
    WorkFlow workFlow;
    @Inject
    SessionBean sessionBean;
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Declare Variables">
    boolean btnaddvisibility = true;
    private String createOrUpdateButton = "Save";
    private String newOrSearchBundle = "New";
    private String headerTitle = "Search Fenumber";
    private String icone = "ui-icon-plus";
    private String selectedDecision = "";
    String lastFeNo = "0";
    String newFeNo;

    private boolean disableBtnCreate;
    private boolean renderRecording = false;
    private boolean renderSearch = true;
    private boolean renderpnlToSearchPage;
    private boolean whenfobOnly = false;
    private boolean whenNotfobOnly;
    private boolean isRenderDecision = false;
    private boolean isRenderCretae = false;
    private boolean isRowFileSelected = false;
    private String incoterm = "Fob";
    private String currenciesName;
    private String username;
    private int updateStatus = 0;
    int selectedRowIndexprmsForeignExDetail1 = -1;
    int selectedRowIndexprmsForeignExDetail2 = -1;
    int requestNotificationCounter = 0;
    int uploadedDocId;

    byte[] byteData;
    String fileName;
    String docFormat;
    private TreeNode selectedNode1;
    StreamedContent fileDown;

    PrmsForeignExchange foreignExchangeSelection;
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Declare lists and models">
    private DataModel<PrmsForeignExchange> prmsForeignExchangeDModel;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<DocumentModel> documentDM;
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    DataModel<DocumentModel> docList;
    List<PrmsLuDmArchive> prmsLuDmArchiveslitst = new ArrayList<>();
    List<PrmsForeignExchange> prmsForeignExchangeList;
    List<PrmsSupplyProfile> supplierLists;
    List<HrAddresses> listOfHrAddress;
    List<HrAddresses> listOfHrCountry;
    List<PrmsServiceProviderDetail> bankBranch;
    List<MmsItemRegistration> itemNameLists;
    private List<FmsLoan> loanNoFms;
    private List<FmsLoan> fromFmsLoan;
    List<PrmsPortEntry> portDestination;
    List<FmsLuCurrency> currencyNameLists;
    List<Integer> docIdList = new ArrayList<>();
    List<byte[]> byteList = new ArrayList<>();
    List<PrmsForeignExchange> foriengnExSearchParameterLst;

    // </editor-fold>
    //default constructor method

    public ForeignExchangeController() {
    }

    //callback lifecycle method
    @PostConstruct
    public void init() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setUsername(workFlow.getUserName());
        if (workFlow.isPrepareStatus()) {
            isRenderDecision = false;
            isRenderCretae = true;
        }
        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            isRenderDecision = true;
            isRenderCretae = false;
            whenNotfobOnly = true;
            whenfobOnly = true;
        }
//        populating();
    }
// <editor-fold defaultstate="collapsed" desc="getter and setter of objects">

    public PrmsServiceProviderDetail getPrmsServiceProviderBranch() {
        if (prmsServiceProviderBranch == null) {
            prmsServiceProviderBranch = new PrmsServiceProviderDetail();
        }
        return prmsServiceProviderBranch;
    }

    public void setPrmsServiceProviderBranch(PrmsServiceProviderDetail prmsServiceProviderBranch) {
        this.prmsServiceProviderBranch = prmsServiceProviderBranch;
    }

    public FmsLoan getFmsLoan() {
        if (fmsLoan == null) {
            fmsLoan = new FmsLoan();
        }
        return fmsLoan;
    }

    public void setFmsLoan(FmsLoan fmsLoan) {
        this.fmsLoan = fmsLoan;
    }

    public PrmsForeignExchange getPrmsForeignExchange() {
        if (prmsForeignExchange == null) {
            prmsForeignExchange = new PrmsForeignExchange();
        }
        return prmsForeignExchange;
    }

    public void setPrmsForeignExchange(PrmsForeignExchange prmsForeignExchange) {
        this.prmsForeignExchange = prmsForeignExchange;
    }

    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public HrAddresses getHrAddressesCountry() {
        if (hrAddressesCountry == null) {
            hrAddressesCountry = new HrAddresses();
        }
        return hrAddressesCountry;
    }

    public void setHrAddressesCountry(HrAddresses hrAddressesCountry) {
        this.hrAddressesCountry = hrAddressesCountry;
    }

    public FmsLuCurrency getFobCurrency() {
        if (fobCurrency == null) {
            fobCurrency = new FmsLuCurrency();
        }
        return fobCurrency;
    }

    public void setFobCurrency(FmsLuCurrency fobCurrency) {
        this.fobCurrency = fobCurrency;
    }

    public FmsLuCurrency getCurrency() {
        if (currency == null) {
            currency = new FmsLuCurrency();
        }
        return currency;
    }

    public void setCurrency(FmsLuCurrency currency) {
        this.currency = currency;
    }

    public FmsLuCurrency getFreightCurrency() {
        if (freightCurrency == null) {
            freightCurrency = new FmsLuCurrency();
        }
        return freightCurrency;
    }

    public void setFreightCurrency(FmsLuCurrency freightCurrency) {
        this.freightCurrency = freightCurrency;
    }

    public FmsLuCurrency getCandfCurrency() {
        if (candfCurrency == null) {
            candfCurrency = new FmsLuCurrency();
        }
        return candfCurrency;
    }

    public void setCandfCurrency(FmsLuCurrency candfCurrency) {
        this.candfCurrency = candfCurrency;
    }

    public PrmsPortEntry getPrmsPortEntry() {
        if (prmsPortEntry == null) {
            prmsPortEntry = new PrmsPortEntry();
        }
        return prmsPortEntry;
    }

    public void setPrmsPortEntry(PrmsPortEntry prmsPortEntry) {
        this.prmsPortEntry = prmsPortEntry;
    }

    public PrmsSupplyProfile getPrmsSupplyProfile() {
        if (prmsSupplyProfile == null) {
            prmsSupplyProfile = new PrmsSupplyProfile();
        }
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public MmsItemRegistration getMmsItemRegistration() {
        if (mmsItemRegistration == null) {
            mmsItemRegistration = new MmsItemRegistration();
        }
        return mmsItemRegistration;
    }

    public void setMmsItemRegistration(MmsItemRegistration mmsItemRegistration) {
        this.mmsItemRegistration = mmsItemRegistration;
    }

    public PrmsLuDmArchive getPrmsLuDmArchive() {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        return prmsLuDmArchive;
    }

    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        this.prmsLuDmArchive = prmsLuDmArchive;
    }

    public PrmsForeignExchange getForeignExchangeSelection() {
        if (foreignExchangeSelection == null) {
            foreignExchangeSelection = new PrmsForeignExchange();

        }
        return foreignExchangeSelection;
    }

    public void setForeignExchangeSelection(PrmsForeignExchange foreignExchangeSelection) {
        this.foreignExchangeSelection = foreignExchangeSelection;
    }

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        if (prmsLuDmArchiveSelection == null) {
            prmsLuDmArchiveSelection = new PrmsLuDmArchive();
        }
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }

    public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public String getCreateOrUpdateButton() {
        return createOrUpdateButton;
    }

    public void setCreateOrUpdateButton(String createOrUpdateButton) {
        this.createOrUpdateButton = createOrUpdateButton;
    }

    public String getNewOrSearchBundle() {
        return newOrSearchBundle;
    }

    public void setNewOrSearchBundle(String newOrSearchBundle) {
        this.newOrSearchBundle = newOrSearchBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderSearch() {
        return renderSearch;
    }

    public void setRenderSearch(boolean renderSearch) {
        this.renderSearch = renderSearch;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isRenderRecording() {
        return renderRecording;
    }

    public void setRenderRecording(boolean renderRecording) {
        this.renderRecording = renderRecording;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public String getCurrenciesName() {
        return currenciesName;
    }

    public void setCurrenciesName(String currenciesName) {
        this.currenciesName = currenciesName;
    }

    public boolean isWhenfobOnly() {
        return whenfobOnly;
    }

    public void setWhenfobOnly(boolean whenfobOnly) {
        this.whenfobOnly = whenfobOnly;
    }

    public boolean isWhenNotfobOnly() {
        return whenNotfobOnly;
    }

    public void setWhenNotfobOnly(boolean whenNotfobOnly) {
        this.whenNotfobOnly = whenNotfobOnly;
    }

    public boolean isIsRenderDecision() {
        return isRenderDecision;
    }

    public void setIsRenderDecision(boolean isRenderDecision) {
        this.isRenderDecision = isRenderDecision;
    }

    public boolean isIsRenderCretae() {
        return isRenderCretae;
    }

    public void setIsRenderCretae(boolean isRenderCretae) {
        this.isRenderCretae = isRenderCretae;
    }

    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }

    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getUploadedDocId() {
        return uploadedDocId;
    }

    public void setUploadedDocId(int uploadedDocId) {
        this.uploadedDocId = uploadedDocId;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public TreeNode getSelectedNode1() {
        return selectedNode1;
    }

    public void setSelectedNode1(TreeNode selectedNode1) {
        this.selectedNode1 = selectedNode1;
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="getter and setter of lists and models">
    public List<PrmsForeignExchange> getPrmsForeignExchangeList() {
        if (prmsForeignExchangeList == null) {
            prmsForeignExchangeList = new ArrayList<>();
        }
        return prmsForeignExchangeList;
    }

    public void setPrmsForeignExchangeList(List<PrmsForeignExchange> prmsForeignExchangeList) {
        this.prmsForeignExchangeList = prmsForeignExchangeList;
    }

    public DataModel<DocumentModel> getDocList() {
        return docList;
    }

    public void setDocList(DataModel<DocumentModel> docList) {
        this.docList = docList;
    }

    public List<MmsItemRegistration> getItemNameLists() {
        if (itemNameLists == null) {
            itemNameLists = new ArrayList<>();
        }
        itemNameLists = foreignExchangeBeanLocal.itemNameLists();
        return itemNameLists;
    }

    public void setItemNameLists(List<MmsItemRegistration> itemNameLists) {
        this.itemNameLists = itemNameLists;
    }

    public List<FmsLuCurrency> getCurrencyNameLists() {
        if (currencyNameLists == null) {
            currencyNameLists = new ArrayList<>();

            currencyNameLists = insuranceRegisterationBeanLocal.fromLuCurrency();
        }
        return currencyNameLists;
    }

    public void setCurrencyNameLists(List<FmsLuCurrency> currencyNameLists) {
        this.currencyNameLists = currencyNameLists;
    }

    public List<PrmsSupplyProfile> getSupplierLists() {
        if (supplierLists == null) {
            supplierLists = new ArrayList<>();
        }
        supplierLists = vendorRegBeanLocal.getVondorName();
        return supplierLists;
    }

    public void setSupplierLists(List<PrmsSupplyProfile> supplierLists) {
        this.supplierLists = supplierLists;
    }

    public List<HrAddresses> getListOfHrAddress() {
        if (listOfHrAddress == null) {
            listOfHrAddress = new ArrayList<>();
        }
        listOfHrAddress = foreignExchangeBeanLocal.listofHrAddress();
        return listOfHrAddress;
    }

    public void setListOfHrAddress(List<HrAddresses> listOfHrAddress) {
        this.listOfHrAddress = listOfHrAddress;
    }

    public List<HrAddresses> getListOfHrCountry() {
        if (listOfHrCountry == null) {
            listOfHrCountry = new ArrayList<>();

        }
        listOfHrCountry = foreignExchangeBeanLocal.listofHrAddress();
        return listOfHrCountry;
    }

    public void setListOfHrCountry(List<HrAddresses> listOfHrCountry) {
        this.listOfHrCountry = listOfHrCountry;
    }

    public List<FmsLoan> getLoanNoFms() {
        if (loanNoFms == null) {
            loanNoFms = new ArrayList<>();
        }
        loanNoFms = fmsLoanBeanLocal.findAll();
        return loanNoFms;
    }

    public void setLoanNoFms(List<FmsLoan> loanNoFms) {
        this.loanNoFms = loanNoFms;
    }

    public List<FmsLoan> getFromFmsLoan() {
        if (fromFmsLoan == null) {
            fromFmsLoan = new ArrayList<>();
        }
        fromFmsLoan = fmsLoanBeanLocal.findAll();
        return fromFmsLoan;
    }

    public void setFromFmsLoan(List<FmsLoan> fromFmsLoan) {
        this.fromFmsLoan = fromFmsLoan;
    }

    public List<PrmsServiceProviderDetail> getBankBranch() {
        if (bankBranch == null) {
            bankBranch = new ArrayList<>();
        }
        bankBranch = insuranceRegisterationBeanLocal.fromServiceProDetail();
        return bankBranch;
    }

    public void setBankBranch(List<PrmsServiceProviderDetail> bankBranch) {
        this.bankBranch = bankBranch;
    }

    public List<PrmsPortEntry> getPortDestination() {
        if (portDestination == null) {
            portDestination = new ArrayList<>();
        }
        portDestination = insuranceNotificationToBankBeanLocal.fromPortEntry();
        return portDestination;

    }

    public void setPortDestination(ArrayList<PrmsPortEntry> portDestination) {
        this.portDestination = portDestination;
    }

    public DataModel<PrmsForeignExchange> getPrmsForeignExchangeDModel() {

        return prmsForeignExchangeDModel;
    }

    public void setPrmsForeignExchangeDModel(DataModel<PrmsForeignExchange> prmsForeignExchangeDModel) {
        this.prmsForeignExchangeDModel = prmsForeignExchangeDModel;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ListDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<DocumentModel> getDocumentDM() {
        return documentDM;
    }

    public void setDocumentDM(DataModel<DocumentModel> documentDM) {
        this.documentDM = documentDM;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Event changes">

    public List<PrmsForeignExchange> getForiengnExSearchParameterLst() {
         if (foriengnExSearchParameterLst == null) {
            foriengnExSearchParameterLst = new ArrayList<>();
            foriengnExSearchParameterLst = foreignExchangeBeanLocal.getParamNameList();
        }
        return foriengnExSearchParameterLst;
    }

    public void setForiengnExSearchParameterLst(List<PrmsForeignExchange> foriengnExSearchParameterLst) {
        this.foriengnExSearchParameterLst = foriengnExSearchParameterLst;
    }
       
        public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsForeignExchange.setColumnName(event.getNewValue().toString());
            prmsForeignExchange.setColumnValue(null);
        }
    }

    public void onDragDrop(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropIndex);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void selectfeanum(SelectEvent event) {
        prmsForeignExchange = (PrmsForeignExchange) event.getObject();
        populateDataForApp();

    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsForeignExchange = (PrmsForeignExchange) event.getNewValue();
            populateDataForApp();
        }
    }

    public void updateBranch(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsServiceProviderBranch.setServiceDtId(new BigDecimal(ev.getNewValue().toString()));
            prmsServiceProviderBranch = insuranceRegisterationBeanLocal.updateServBranch(prmsServiceProviderBranch);
        }
    }

    public void updateLists(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsSupplyProfile.setId(event.getNewValue().toString());
            prmsSupplyProfile = bank_Clearance_RegistrationBeanLocal.getVondorName(prmsSupplyProfile);
        }
    }

    public void updatePortDetination(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsPortEntry.setPortId(new BigDecimal(event.getNewValue().toString()));
            prmsPortEntry = containerAgreementBeanLocal.portNameUpdate(prmsPortEntry);
        }
    }

    public void updateAddress(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrAddresses.setAddressId(Integer.parseInt(event.getNewValue().toString()));
            hrAddresses = foreignExchangeBeanLocal.addressUpdate(hrAddresses);
        }
    }

    public void updatecountry(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrAddressesCountry.setAddressId(Integer.parseInt(event.getNewValue().toString()));
            hrAddressesCountry = foreignExchangeBeanLocal.addressUpdate(hrAddressesCountry);
        }
    }

    public void updateCurrencyNames(ValueChangeEvent change) {
        if (!change.getNewValue().toString().isEmpty()) {
            freightCurrency.setCurrency(change.getNewValue().toString());
            freightCurrency = foreignExchangeBeanLocal.updateCurrencies(freightCurrency);
        }
    }

    public void updateFobCurrencyNames(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fobCurrency.setCurrency(event.getNewValue().toString());
            fobCurrency = foreignExchangeBeanLocal.updateCurrencies(freightCurrency);
        }
    }

    public void whenfobIncoterm(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            if (event.getNewValue().equals(incoterm)) {
                whenfobOnly = false;
                whenNotfobOnly = true;
                currenciesName = "FOB";
            } else if (event.getNewValue().equals("Cpt")) {
                whenNotfobOnly = false;
                currenciesName = "CPT";
//                whenfobOnly = true;
            } else if (event.getNewValue().equals("Ddu")) {
                whenNotfobOnly = false;
                currenciesName = "DDU";
            } else if (event.getNewValue().equals("Ddp")) {
                whenNotfobOnly = false;
                currenciesName = "DDP";
            } else if (event.getNewValue().equals("Cif")) {
                whenNotfobOnly = false;
                currenciesName = "CIF";
            } else if (event.getNewValue().equals("Cip")) {
                whenNotfobOnly = false;
                currenciesName = "CIP";
            } else if (event.getNewValue().equals("Cfr")) {
                whenNotfobOnly = false;
                currenciesName = "CFR";
            } else if (event.getNewValue().equals("Exw")) {
                whenNotfobOnly = false;
                currenciesName = "EXW";
            } else if (event.getNewValue().equals("Fca")) {
                whenNotfobOnly = false;
                currenciesName = "FCA";
            } else if (event.getNewValue().equals("Fas")) {
                whenNotfobOnly = false;
                currenciesName = "FAS";
            } else if (event.getNewValue().equals("Daf")) {
                whenNotfobOnly = false;
                currenciesName = "DAF";
            } else if (event.getNewValue().equals("Des")) {
                whenNotfobOnly = false;
                currenciesName = "DES";
            } else if (event.getNewValue().equals("Deq")) {
                whenNotfobOnly = false;
                currenciesName = "DEQ";
            } else if (event.getNewValue().equals("Dat")) {
                whenNotfobOnly = false;
                currenciesName = "DAT";
            } else if (event.getNewValue().equals("Dap")) {
                whenNotfobOnly = false;
                currenciesName = "DAP";
            } else {
                JsfUtil.addFatalMessage("It's not recognized as Incoterm Lists");
            }

        }
    }

    public void onRowSelect(SelectEvent event) {
        prmsLuDmArchiveSelection = (PrmsLuDmArchive) event.getObject();
        isRowFileSelected = true;
        System.out.println("Is RowFile Selected ..." + isRowFileSelected);
    }

    public void uploadListener(FileUploadEvent event) {
        System.out.println("====listener===" + documentDM);
        InputStream fileByteFile_ = null;
        fileName = event.getFile().getFileName().split("\\.")[0];
        docFormat = event.getFile().getFileName().split("\\.")[1];
        System.out.println("File Name " + fileName);
        System.out.println("File Type " + docFormat);
//        String fileNameWzExtent = event.getFile().getFileName();
//        String categoryBundle = "et/gov/eep/commonApplications/securityServer";
        try {
            fileByteFile_ = event.getFile().getInputstream();
            System.out.println("file Stream " + fileByteFile_);
            byteData = dataUpload.extractByteArray(fileByteFile_);
            for (int i = 0; i < byteList.size(); i++) {
                byteList.add(byteData);
                System.out.println("" + byteList.get(i));
            }

            System.out.println("byte data " + byteData + " byte List " + byteList);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Uploading Error from Lisener==>" + ex.getMessage());
        }
    }

    public void handleDecisionOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedDecision = event.getNewValue().toString();
        }
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="other methods">
    public void switchedNewAndSearchBtn() {
//        clear();
        System.out.println("inside of New Or Search Bundle");
        createOrUpdateButton = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (newOrSearchBundle.equals("New")) {
            clear();
            renderRecording = true;
            renderSearch = false;
            newOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            headerTitle = "Recruitment Registration Request";
        } else if (newOrSearchBundle.equals("Search")) {
            renderRecording = false;
            renderSearch = true;
            newOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackSearchPageBtnAction() {
        renderSearch = true;
        renderRecording = false;
        renderpnlToSearchPage = false;
    }

    public void findForeignExchangeInfo() {
        prmsForeignExchange.setPreparedBy(workFlow.getUserAccount());
        if (prmsForeignExchange.getFeNumber() != null) {
            prmsForeignExchangeDModel = new ListDataModel(new ArrayList(foreignExchangeBeanLocal.findByfenumber(prmsForeignExchange)));
        }
        prmsForeignExchangeList = foreignExchangeBeanLocal.findByfenumber(prmsForeignExchange);
        recreateprmsForeignExchangeDModel();
        prmsForeignExchange=new PrmsForeignExchange();
    }

    public String generateFENo() {
        System.out.println("Fex No generating updating page ");
        if (createOrUpdateButton.equals("Update")) {
            newFeNo = prmsForeignExchange.getFeNumber();
        } else {
            System.out.println("generating new Fex number");
            PrmsForeignExchange lastFeNoObj = foreignExchangeBeanLocal.generateForeignExNo();
            if (lastFeNoObj != null) {
                lastFeNo = lastFeNoObj.getId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            System.out.println("last Fex Num is==" + lastFeNo);

            int newExchanging = 0;
            if (lastFeNo.equals("0")) {
                newExchanging = 1;
                newFeNo = "FEx-" + newExchanging + "/" + f.format(now);

            } else {
                String[] lastInsuranceNos = lastFeNo.split("-");
                String lastDatesPatern = lastInsuranceNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newExchanging = Integer.parseInt(lastDatesPaterns[0]);
                System.out.println("new Last FEx num is ===" + newExchanging);
                newExchanging += 1;
                newFeNo = "FEx-" + newExchanging + "/" + f.format(now);
            }
            System.out.println("New FEx No is" + newFeNo);
        }

        return newFeNo;

    }

    public StreamedContent getFileDown() throws Exception {
        if (isRowFileSelected == true) {
            System.out.println("clicked to Dowload File name " + prmsLuDmArchive.getFileName());
//            fileDown = dataUpload.getFile(dm);
            fileDown = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
            System.out.println("file--" + fileDown);
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
        }
        return fileDown;
    }

    public void setFileDown(StreamedContent fileDown) {
        this.fileDown = fileDown;
    }

    public void removeFile() {
        prmsLuDmArchive = prmsLuDmArchivesDModel.getRowData();
        prmsLuDmArchiveslitst.remove(prmsLuDmArchive);
        recreateComLuDmModel();
    }

    public void populateDataForApp() {
        prmsForeignExchange.setId(prmsForeignExchange.getId());
        prmsServiceProviderBranch = prmsForeignExchange.getBankId();
        hrAddresses = prmsForeignExchange.getCountryofOrigin();
        fmsLoan = prmsForeignExchange.getMethodOfPayment();
        prmsPortEntry = prmsForeignExchange.getPortId();
        fmsLoan = prmsForeignExchange.getLoanNo();
        prmsSupplyProfile = prmsForeignExchange.getSuppId();
        hrAddressesCountry = prmsForeignExchange.getCountry();
        currency = prmsForeignExchange.getCurrency();
        freightCurrency = prmsForeignExchange.getFrightCurrency();
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsForeignExchange.getValidDate());
        }
        if (prmsForeignExchange.getDocumentId() != null) {
            prmsLuDmArchive = prmsForeignExchange.getDocumentId();
            System.out.println("11 " + prmsLuDmArchive);
            prmsLuDmArchiveslitst = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
            for (int filecouter = 0; filecouter < prmsLuDmArchiveslitst.size(); filecouter++) {
                prmsLuDmArchive.setFileName(prmsLuDmArchiveslitst.get(filecouter).getFileName());
                prmsLuDmArchive.setFileType(prmsLuDmArchiveslitst.get(filecouter).getFileType());
                prmsLuDmArchive.setUploadFile(prmsLuDmArchiveslitst.get(filecouter).getUploadFile());
                System.out.println("File name @index " + filecouter + "==" + prmsLuDmArchive.getFileName());
            }
            recreateComLuDmModel();
            prmsForeignExchange = foreignExchangeBeanLocal.findByfenumberObj(prmsForeignExchange);
            renderRecording = true;
            renderSearch = false;
            renderpnlToSearchPage = true;
            createOrUpdateButton = "Update";
            recreateprmsForeignExchangeDModel();
            recreateWfPrmsProcessed();
            recreateFileUpload();
        }
    }

    public void getDocValueModel() {
        docList = dataUpload.selectListOfFileByDocId(docIdList);

    }

    public void recreateprmsForeignExchangeDModel() {
        if (prmsForeignExchangeDModel == null) {
            prmsForeignExchangeDModel = new ListDataModel<>(new ArrayList<>(getPrmsForeignExchangeList()));
        }
    }

    public void recreateComLuDmModel() {
        if (prmsLuDmArchivesDModel == null) {
            System.out.println("when data model " + prmsLuDmArchivesDModel);
            prmsLuDmArchivesDModel = new ListDataModel(new ArrayList<>(prmsLuDmArchiveslitst));
        }
    }

    public void recreateWfPrmsProcessed() {
        wfPrmsProcessedDModel = null;
        wfPrmsProcessedDModel = new ListDataModel<>(prmsForeignExchange.getWfPrmsProcessedLists());
    }

    public void recreateFileUpload() {
        docIdList = null;
        docIdList = new ArrayList();
    }

    public SelectItem[] fromHrAddress() {
        return JsfUtil.getSelectItems(foreignExchangeBeanLocal.fromHrAddress(), true);
    }

    public int getRequestNotificationCounter() {
        prmsForeignExchangeList = foreignExchangeBeanLocal.getForeignExchReqlist();
        requestNotificationCounter = prmsForeignExchangeList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public String clear() {
        prmsForeignExchange = null;
        fmsLoan = null;
        hrAddresses = null;
        hrAddressesCountry = null;
        prmsServiceProviderBranch = null;
        prmsPortEntry = null;
        prmsSupplyProfile = null;
        fobCurrency = null;
        freightCurrency = null;
        prmsLuDmArchivesDModel = null;
        prmsForeignExchangeDModel = null;
        prmsForeignExchangeList = null;
        currency = null;
        wfPrmsProcessedDModel = null;
        docList = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setDecision(null);
        wfPrmsProcessed.setCommentGiven(null);
        createOrUpdateButton = "Save";
        newOrSearchBundle = "Search";
        icone = "ui-icon-search";
        renderpnlToSearchPage = false;
        return null;
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="save or update method">
    public String saveForeignExchangeInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveForeignExchangeInfo", dataset)) {
                if (createOrUpdateButton.equals("Save")) {
                    System.out.println("1");
                    if (updateStatus == 0) {
//                System.out.println("2===check as it cannot save  w/out detail on Table1");
//                if (prmsForeignExDetail1DModel.getRowCount() <= 0 && prmsForeignExDetail2DModel.getRowCount() <= 0) {
//                    JsfUtil.addFatalMessage("Impossible to save without detail data on both Table 1 & Table 2");
//                } else if (prmsForeignExDetail1DModel.getRowCount() > 0 && prmsForeignExDetail2DModel.getRowCount() > 0) {

                        try {
                            generateFENo();
                            prmsForeignExchange.setBankId(prmsServiceProviderBranch);
                            if (!prmsForeignExchange.getIncoterm().equals("Fob")) {
                                prmsForeignExchange.setFrightCurrency(freightCurrency);
                            }
                            prmsForeignExchange.setCurrency(currency);
                            prmsForeignExchange.setStatus(Constants.PREPARE_VALUE);
                            prmsForeignExchange.setValidDate(wfPrmsProcessed.getProcessedOn());
                            prmsForeignExchange.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            prmsForeignExchange.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                            wfPrmsProcessed.setForienId(prmsForeignExchange);
                            wfPrmsProcessed.setDecision(prmsForeignExchange.getStatus().toString());
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(docFormat);
                            prmsLuDmArchive.setUploadFile(byteData);
                            System.out.println("here let Upload File " + prmsLuDmArchive.getUploadFile() + " name " + prmsLuDmArchive.getFileName() + " Type " + prmsLuDmArchive.getFileType());
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            prmsForeignExchange.setDocumentId(prmsLuDmArchive);
                            foreignExchangeBeanLocal.create(prmsForeignExchange);
                            JsfUtil.addSuccessMessage("Foreign Exchange Information Successfully Saved");
                            return clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("Something Error Occured on Data Saved" + e);
                        }
//                    
                        //}
                        //}
                    }
                } else if (createOrUpdateButton.equals("Update")) {
                    if (workFlow.isPrepareStatus()) {
//                        if (prmsForeignExchange.getStatus() == 0 || prmsForeignExchange.getStatus() == 4 || prmsForeignExchange.getStatus() == 2) {
//                            try {

                        prmsForeignExchange.setBankId(prmsServiceProviderBranch);
//              
                        prmsForeignExchange.setFrightCurrency(freightCurrency);

                        prmsForeignExchange.setCurrency(currency);
                        prmsForeignExchange.setValidDate(wfPrmsProcessed.getProcessedOn());
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(docFormat);
                        prmsLuDmArchive.setUploadFile(byteData);
                        prmsLuDmArchiveBeanLocal.updateFileInfo(prmsLuDmArchive);
                        prmsForeignExchange.setDocumentId(prmsLuDmArchive);
                        foreignExchangeBeanLocal.update(prmsForeignExchange);
                        JsfUtil.addSuccessMessage("Foreign Exchange Information Update Successful at " + prmsForeignExchange.getFeNumber());
                        return clear();
//                            } catch (Exception e) {
//                                JsfUtil.addFatalMessage("Something Error Occured on Data Modified");
//                                clear();
//                            }
//                        } else {
//                            workFlow.setReadonly(true);
//                        }

                    } else if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
                        if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            prmsForeignExchange.setStatus(Constants.APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                            prmsForeignExchange.setStatus(Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                            prmsForeignExchange.setStatus(Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                            prmsForeignExchange.setStatus(Constants.CHECK_REJECT_VALUE);
                        }
                        foreignExchangeBeanLocal.update(prmsForeignExchange);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addInformationMessage("Foreign Exchange Information Decided Successful at");
                        clear();
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//              ..... add more information by calling fields defined in the object 

                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {

        }
        return null;
    }
// </editor-fold>

}
