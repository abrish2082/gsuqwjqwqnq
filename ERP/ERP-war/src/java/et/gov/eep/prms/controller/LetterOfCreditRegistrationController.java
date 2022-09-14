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
import et.gov.eep.prms.businessLogic.LetterOfCreditRegiBeanLocal;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcRegDetail;
import et.gov.eep.prms.entity.PrmsFileUpload;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import java.io.InputStream;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import org.insa.model.DocumentModel;
import org.primefaces.model.StreamedContent;
import webService.AAA;
import webService.IAdministration;
import securityBean.SessionBean;
import webService.EventEntry;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import securityBean.WorkFlow;
import securityBean.Constants;
import org.insa.client.DmsHandler;
import java.text.ParseException;
// </editor-fold>

//LC Registration  view scoped CDI Named Bean class
@Named(value = "lCRigistrationController")
@ViewScoped

public class LetterOfCreditRegistrationController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    LetterOfCreditRegiBeanLocal letterOfCreditRegiBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsLcRigistration prmsLcRigistration;
    @Inject
    private PrmsForeignExchange prmsForeignExchange;
    @Inject
    PrmsContract prmsContract;
    @Inject
    PrmsPortEntry portOfDischarge;
    @Inject
    PrmsServiceProvider prmsServiceProvider;
    @Inject
    PrmsPortEntry portOfLoading;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    DataUpload dataUpload;
    @Inject
    private PrmsFileUpload prmsFileUpload;
    @Inject
    private WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private WorkFlow workFlow;
    @Inject
    SessionBean sessionBean;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    DataModel<PrmsLcRigistration> prmsLcRigistrationModel;
    DataModel<PrmsLcRegDetail> prmsLcRegDetailModels;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    DataModel<DocumentModel> docValueModel;
    DataModel<PrmsFileUpload> fileUploadDataModel;
    List<PrmsLcRigistration> lcRegSearchParameterLst;
    private List<PrmsContractAmendment> prmsContractAmendmentLst;
    List<PrmsContract> listOfContract;
    List<PrmsServiceProvider> listOfService;
    private List<PrmsPortEntry> listOfPorts;
    List<PrmsLcRigistration> prmsLcRigistrations;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    private List<PrmsForeignExchange> prmsForeignExchangeLsts;
    List<Integer> doLst = new ArrayList<>();
    List<PrmsFileUpload> documentList;
    List<DocumentModel> newDoclist = new ArrayList<>();
    List<Integer> savedDocIds = new ArrayList<>();

    List<ComLuCountry> countryList;
    List<FmsLuCurrency> currencyLists;
    List<PrmsFileUpload> prmsFileUploadList;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Variables">
    private String createOrSearchBundle = "New";
    private String saveorUpdateBundle = "Save";
    private String activeIndex;
    private String icone = "ui-icon-plus";
    private String selectedValue = "";
    private String duplicattion = null;
    private String loggerName;
    private String shipment;
    private String fileName;
    private String fileType;

    int saveStatus = 0;
    int requestNotificationCounter = 0;
    int updateStatus = 0;
    private int selectedRowIndex;
    String LCRegNo = "0";
    String newLCRegNo;
    int docId;
    int savedDocId;
    int noOfDays;

    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private boolean renderDecision = false;
    private boolean isFileSelected = false;
    private boolean isRendercreate;
    private boolean isRenderNotify;

    private Date shipmentDate;
    private byte[] byteFile;

    StreamedContent file;
    DocumentModel documentModel = new DocumentModel();
    DmsHandler dmsHandler = new DmsHandler();
    ComLuCountry comLuCountry;
    PrmsLcRigistration selectPrmsLcRigistration;
    PrmsLuDmArchive prmsLuDmArchiveSelection;

    // </editor-fold>
// default constructor method
    public LetterOfCreditRegistrationController() {
    }

    // call back lifecycle method
    @PostConstruct
    public void initial() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setLoggerName(workFlow.getUserName());
        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRendercreate = true;
            isRenderNotify = false;
        } else if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
        } else if (workFlow.isCheckStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public PrmsLcRigistration getPrmsLcRigistration() {
        if (prmsLcRigistration == null) {
            prmsLcRigistration = new PrmsLcRigistration();
        }
        return prmsLcRigistration;
    }

    public void setPrmsLcRigistration(PrmsLcRigistration prmsLcRigistration) {
        this.prmsLcRigistration = prmsLcRigistration;
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

    public FmsLuCurrency getFmsLuCurrency() {
        if (fmsLuCurrency == null) {
            fmsLuCurrency = new FmsLuCurrency();
        }
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public PrmsServiceProvider getPrmsServiceProvider() {
        if (prmsServiceProvider == null) {
            prmsServiceProvider = new PrmsServiceProvider();
        }
        return prmsServiceProvider;
    }

    public void setPrmsServiceProvider(PrmsServiceProvider prmsServiceProvider) {
        this.prmsServiceProvider = prmsServiceProvider;
    }

    public PrmsContract getPrmsContract() {
        if (prmsContract == null) {
            prmsContract = new PrmsContract();
        }
        return prmsContract;
    }

    public void setPrmsContract(PrmsContract prmsContract) {
        this.prmsContract = prmsContract;
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

    public PrmsPortEntry getPortOfDischarge() {
        if (portOfDischarge == null) {
            portOfDischarge = new PrmsPortEntry();
        }
        return portOfDischarge;
    }

    public void setPortOfDischarge(PrmsPortEntry portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public PrmsPortEntry getPortOfLoading() {
        if (portOfLoading == null) {
            portOfLoading = new PrmsPortEntry();
        }
        return portOfLoading;
    }

    public void setPortOfLoading(PrmsPortEntry portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    /**
     * @return the wfPrmsProcessed
     */
    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    /**
     * @param wfPrmsProcessed the wfPrmsProcessed to set
     */
    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    /**
     * @return the workFlow
     */
    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    /**
     * @param workFlow the workFlow to set
     */
    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    /**
     * @return the prmsFileUpload
     */
    public PrmsFileUpload getPrmsFileUpload() {
        if (prmsFileUpload == null) {
            prmsFileUpload = new PrmsFileUpload();
        }
        return prmsFileUpload;
    }

    /**
     * @param prmsFileUpload the prmsFileUpload to set
     */
    public void setPrmsFileUpload(PrmsFileUpload prmsFileUpload) {
        this.prmsFileUpload = prmsFileUpload;
    }

    /**
     * @return the prmsForeignExchange
     */
    public PrmsForeignExchange getPrmsForeignExchange() {
        if (prmsForeignExchange == null) {
            prmsForeignExchange = new PrmsForeignExchange();
        }
        return prmsForeignExchange;
    }

    /**
     * @param prmsForeignExchange the prmsForeignExchange to set
     */
    public void setPrmsForeignExchange(PrmsForeignExchange prmsForeignExchange) {
        this.prmsForeignExchange = prmsForeignExchange;
    }

    public PrmsContractAmendment getPrmsContractAmendment() {
        if (prmsContractAmendment == null) {
            prmsContractAmendment = new PrmsContractAmendment();
        }
        return prmsContractAmendment;
    }

    public void setPrmsContractAmendment(PrmsContractAmendment prmsContractAmendment) {
        this.prmsContractAmendment = prmsContractAmendment;
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

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }

    public PrmsLcRigistration getSelectPrmsLcRigistration() {
        return selectPrmsLcRigistration;
    }

    public void setSelectPrmsLcRigistration(PrmsLcRigistration selectPrmsLcRigistration) {
        this.selectPrmsLcRigistration = selectPrmsLcRigistration;
    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of list and model">
    public DataModel<PrmsLcRegDetail> getPrmsLcRegDetailModels() {
        if (prmsLcRegDetailModels == null) {
            prmsLcRegDetailModels = new ListDataModel();
        }
        return prmsLcRegDetailModels;
    }

    public void setPrmsLcRegDetailModels(DataModel<PrmsLcRegDetail> prmsLcRegDetailModels) {
        this.prmsLcRegDetailModels = prmsLcRegDetailModels;
    }

    /**
     * @return the wfPrmsProcessedDModel
     */
    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ListDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    /**
     * @param wfPrmsProcessedDModel the wfPrmsProcessedDModel to set
     */
    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<PrmsLcRigistration> getPrmsLcRigistrationModel() {
        if (prmsLcRigistrationModel == null) {
            prmsLcRigistrationModel = new ListDataModel<>();
        }
        return prmsLcRigistrationModel;
    }

    public void setPrmsLcRigistrationModel(DataModel<PrmsLcRigistration> prmsLcRigistrationModel) {
        this.prmsLcRigistrationModel = prmsLcRigistrationModel;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public DataModel<PrmsFileUpload> getFileUploadDataModel() {
        return fileUploadDataModel;
    }

    public void setFileUploadDataModel(DataModel<PrmsFileUpload> fileUploadDataModel) {
        this.fileUploadDataModel = fileUploadDataModel;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        if (prmsLuDmArchivesDModel == null) {
            prmsLuDmArchivesDModel = new ListDataModel<>();
        }
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }

    public List<PrmsContractAmendment> getPrmsContractAmendmentLst() {
        if (prmsContractAmendmentLst == null) {
            prmsContractAmendmentLst = new ArrayList<>();
        }
        return prmsContractAmendmentLst;
    }

    public void setPrmsContractAmendmentLst(List<PrmsContractAmendment> prmsContractAmendmentLst) {
        this.prmsContractAmendmentLst = prmsContractAmendmentLst;
    }

    public List<PrmsLuDmArchive> getPrmsLuDmArchivesList() {
        if (prmsLuDmArchivesList == null) {
            prmsLuDmArchivesList = new ArrayList<>();
        }
        return prmsLuDmArchivesList;
    }

    public void setPrmsLuDmArchivesList(List<PrmsLuDmArchive> prmsLuDmArchivesList) {
        this.prmsLuDmArchivesList = prmsLuDmArchivesList;
    }

    public List<PrmsLcRigistration> getPrmsLcRigistrations() {
        if (prmsLcRigistrations == null) {
            prmsLcRigistrations = new ArrayList<>();
        }
        return prmsLcRigistrations;
    }

    public void setPrmsLcRigistrations(List<PrmsLcRigistration> prmsLcRigistrations) {
        this.prmsLcRigistrations = prmsLcRigistrations;
    }

    public List<FmsLuCurrency> getCurrencyLists() {
        if (currencyLists == null) {
            currencyLists = new ArrayList<>();
            currencyLists = letterOfCreditRegiBeanLocal.getCurrencyName();
        }
        return currencyLists;
    }

    public void setCurrencyLists(List<FmsLuCurrency> currencyLists) {
        this.currencyLists = currencyLists;
    }

    public List<PrmsContract> getListOfContract() {
        if (listOfContract == null) {
            listOfContract = new ArrayList<>();
            listOfContract = letterOfCreditRegiBeanLocal.listOfContractNO();
        }

        return listOfContract;
    }

    public void setListOfContract(List<PrmsContract> listOfContract) {
        this.listOfContract = listOfContract;
    }

    /**
     * @return the listOfPorts
     */
    public List<PrmsPortEntry> getListOfPorts() {
        if (listOfPorts == null) {
            listOfPorts = new ArrayList<>();
            listOfPorts = letterOfCreditRegiBeanLocal.listOfPorts();
        }
        return listOfPorts;
    }

    /**
     * @param listOfPorts the listOfPorts to set
     */
    public void setListOfPorts(List<PrmsPortEntry> listOfPorts) {
        this.listOfPorts = listOfPorts;
    }

    public List<PrmsServiceProvider> getListOfService() {
        if (listOfService == null) {
            listOfService = new ArrayList<>();
            listOfService = letterOfCreditRegiBeanLocal.listOfServiceNO();

        }
        return listOfService;
    }

    public void setListOfService(List<PrmsServiceProvider> listOfService) {
        this.listOfService = listOfService;
    }

    public List<ComLuCountry> getCountryList() {
        if (countryList == null) {
            countryList = new ArrayList<>();
            countryList = letterOfCreditRegiBeanLocal.getCountryList();

        }
        return countryList;
    }

    public void setCountryList(List<ComLuCountry> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the prmsForeignExchangeLsts
     */
    public List<PrmsForeignExchange> getPrmsForeignExchangeLsts() {
        if (prmsForeignExchangeLsts == null) {
            prmsForeignExchangeLsts = new ArrayList<>();
            prmsForeignExchangeLsts = letterOfCreditRegiBeanLocal.findForeign();
        }
        return prmsForeignExchangeLsts;
    }

    /**
     * @param prmsForeignExchangeLsts the prmsForeignExchangeLsts to set
     */
    public void setPrmsForeignExchangeLsts(List<PrmsForeignExchange> prmsForeignExchangeLsts) {
        this.prmsForeignExchangeLsts = prmsForeignExchangeLsts;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * @return the selectedValue
     */
    public String getSelectedValue() {
        return selectedValue;
    }

    /**
     * @param selectedValue the selectedValue to set
     */
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    /**
     * @return the renderDecision
     */
    public boolean isRenderDecision() {
        return renderDecision;
    }

    /**
     * @param renderDecision the renderDecision to set
     */
    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }

    /**
     * @return the isRendercreate
     */
    public boolean isIsRendercreate() {
        return isRendercreate;
    }

    /**
     * @param isRendercreate the isRendercreate to set
     */
    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }

    public boolean isIsRenderNotify() {
        return isRenderNotify;
    }

    public void setIsRenderNotify(boolean isRenderNotify) {
        this.isRenderNotify = isRenderNotify;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event changes">

    public List<PrmsLcRigistration> getLcRegSearchParameterLst() {
         if (lcRegSearchParameterLst == null) {
            lcRegSearchParameterLst = new ArrayList<>();
            lcRegSearchParameterLst = letterOfCreditRegiBeanLocal.getParamNameList();
        }
        return lcRegSearchParameterLst;
    }

    public void setLcRegSearchParameterLst(List<PrmsLcRigistration> lcRegSearchParameterLst) {
        this.lcRegSearchParameterLst = lcRegSearchParameterLst;
    }
    
    
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsLcRigistration.setColumnName(event.getNewValue().toString());
            prmsLcRigistration.setColumnValue(null);
        }
    }

    public void rowSelect(SelectEvent event) {
        prmsLcRigistration = (PrmsLcRigistration) event.getObject();
        populateDataForApp();

    }

    public void setSelectSuppliers(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            prmsContract = (PrmsContract) e.getNewValue();
            prmsLcRigistration.setSupplier(prmsContract.getSuppId().getVendorName());
            prmsContractAmendmentLst = letterOfCreditRegiBeanLocal.checkAmendOrNot(prmsContract);
            if (prmsContractAmendmentLst.size() > 0) {
                prmsContractAmendment = letterOfCreditRegiBeanLocal.getContractAmendment(prmsContract);
                prmsContract.setContractId(prmsContractAmendment.getContractId().getContractId());
                prmsContract.setSuppId(prmsContractAmendment.getSuppId());
            }

        }

    }

    public void setSelectPortDisch(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            portOfDischarge = (PrmsPortEntry) e.getNewValue();
            prmsLcRigistration.setPortOfDischarge(portOfDischarge);
        }

    }

    public void setSelectPortLoad(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            portOfLoading = (PrmsPortEntry) e.getNewValue();
            prmsLcRigistration.setPortOfLoding(portOfLoading);
        }

    }

    public void setSelectForiegn(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            prmsForeignExchange = (PrmsForeignExchange) e.getNewValue();
            prmsLcRigistration.setForeignId(prmsForeignExchange);
            prmsLcRigistration.setPermitAmount(prmsForeignExchange.getPermitAmount());
        }

    }

    public void onRowSelect(SelectEvent event) {
        prmsLuDmArchive = (PrmsLuDmArchive) event.getObject();
        isFileSelected = true;
    }

    public void uploadListener(FileUploadEvent event) {
        InputStream inputStream = null;
        try {
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            inputStream = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStream);
            if (byteFile != null) {
                System.out.println("Byte File is  " + byteFile);
                JsfUtil.addSuccessMessage("Upload Successfully File Name " + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();

        }
    }

    public void RequestListChange(ValueChangeEvent eve) {
        if (eve.getNewValue() != null) {
            prmsLcRigistration = (PrmsLcRigistration) eve.getNewValue();
            populateDataForApp();
        }
    }

    public void calcLcExpireDate(ValueChangeEvent dateEntered) throws ParseException {
        SimpleDateFormat sdt = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
        prmsLcRigistration.setShipmentDate(sdt.parse(dateEntered.getNewValue().toString()));
        if (prmsLcRigistration.getNumberOfDays() != null) {
            System.out.println("when Last Shipment Date Action");
            System.out.println("here U may " + prmsLcRigistration.getShipmentDate());
            System.out.println("yes not " + prmsLcRigistration.getNumberOfDays());
            Calendar c = Calendar.getInstance();
            c.setTime(prmsLcRigistration.getShipmentDate()); // Now use z Selected date.
            c.add(Calendar.DATE, prmsLcRigistration.getNumberOfDays()); // Adding number of days
            Date finalExpireDate = c.getTime();
            System.out.println("finally " + finalExpireDate);
            prmsLcRigistration.setLcExpireDate(finalExpireDate);
        }

    }

    public void calcLcExpireDateByDayNo(ValueChangeEvent dateEntered) throws ParseException {
        noOfDays = Integer.parseInt(dateEntered.getNewValue().toString());
        if (prmsLcRigistration.getShipmentDate() != null) {
            System.out.println("when Number of Days Action");
            prmsLcRigistration.setNumberOfDays(noOfDays);
            Calendar c = Calendar.getInstance();
            c.setTime(prmsLcRigistration.getShipmentDate()); // Now use z Selected date.
            c.add(Calendar.DATE, prmsLcRigistration.getNumberOfDays()); // Adding number of days
            Date finalExpireDate = c.getTime();
            System.out.println("finally " + finalExpireDate);
            prmsLcRigistration.setLcExpireDate(finalExpireDate);
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Other methods">
    public void createNewLCReg() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clearRegistration();
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void searchByLCInfoNo() {
        prmsLcRigistration.setPreparedBy(workFlow.getUserAccount());
        prmsLcRigistrations = letterOfCreditRegiBeanLocal.searchByLCregNo(prmsLcRigistration);
        reCreatLcRegModel();
        prmsLcRigistration=new PrmsLcRigistration();

    }

    public SelectItem[] getListOfAllContractNo() {
        listOfContract = letterOfCreditRegiBeanLocal.listOfContractNO();
        return JsfUtil.getSelectItems(listOfContract, true);
    }

    public String generateLCRegNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newLCRegNo = prmsLcRigistration.getLcNo();

        } else {
            newLCRegNo = getNextLcRegNo();
        }
        return newLCRegNo;

    }

    public String getNextLcRegNo() {
        newLCRegNo = letterOfCreditRegiBeanLocal.getNextLcRegNo();
        return newLCRegNo;
    }

    public int getRequestNotificationCounter() {
        prmsLcRigistrations = letterOfCreditRegiBeanLocal.getLCLists();
        requestNotificationCounter = prmsLcRigistrations.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void populateDataForApp() {
        prmsLcRigistration.setLcId(prmsLcRigistration.getLcId());
        currencyLists = letterOfCreditRegiBeanLocal.getCurrencyName();
        fmsLuCurrency = prmsLcRigistration.getCurrencyId();
        countryList = letterOfCreditRegiBeanLocal.getCountryList();
        comLuCountry = prmsLcRigistration.getCountryId();
        listOfContract = letterOfCreditRegiBeanLocal.listOfContractNO();
        prmsContract = prmsLcRigistration.getContractId();
        listOfPorts = letterOfCreditRegiBeanLocal.listOfPorts();
        portOfDischarge = prmsLcRigistration.getPortOfDischarge();
        portOfLoading = prmsLcRigistration.getPortOfLoding();
        prmsForeignExchange = prmsLcRigistration.getForeignId();
        prmsServiceProvider = prmsLcRigistration.getServiceProId();
        reCreatLcRegModel();
        if (prmsLcRigistration.getDocumentId() != null) {
            prmsLuDmArchive = prmsLcRigistration.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
        }
        recreateDmsDataModel();
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsLcRigistration.getProcessedOn());
        }

        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";

    }

    public void remainingLcAmount() {
        if (prmsLcRigistration.getDeliveryTermAfterLcAmount() != null && prmsLcRigistration.getLcPaymentAfterAcceptance() != null) {
            prmsLcRigistration.setRemainingLcPaymentAmount(prmsLcRigistration.getDeliveryTermAfterLcAmount() - prmsLcRigistration.getLcPaymentAfterAcceptance());
        }

    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Please Select File Name");
        }

        return file;
    }

    public void getfile() {
        docValueModel = dataUpload.selectListOfFileByDocId(doLst);
    }

    public void recreateDmsDataModel() {
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public void reCreatLcRegModel() {
        prmsLcRigistrationModel = null;
        prmsLcRigistrationModel = new ListDataModel(new ArrayList(getPrmsLcRigistrations()));
    }

    public void remove() {
        getPrmsFileUpload().setId(getPrmsFileUpload().getId());
        letterOfCreditRegiBeanLocal.remove(getPrmsFileUpload());
        prmsLcRigistration.getPrmsFileUploadList().remove(getPrmsFileUpload());
        recreateDmsDataModel();

    }

    public void remove(DocumentModel document) {
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDmsDataModel();
    }

    public void clearRegistration() {
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveorUpdateBundle = "Save";
        prmsLcRigistration = null;
        prmsContract = null;
        fmsLuCurrency = null;
        portOfDischarge = null;
        portOfLoading = null;
        comLuCountry = null;
        newDoclist = new ArrayList<>();
        docValueModel = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setCommentGiven(null);
        prmsForeignExchange = null;
        prmsLuDmArchivesDModel = null;
        prmsServiceProvider = null;
        prmsLuDmArchive = null;
        renderpnlToSearchPage = false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update method">
    public void saveLCInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLCInfo", dataset)) {
                if (saveorUpdateBundle.equals("Save")) {
                    try {
                        generateLCRegNo();
                        prmsLcRigistration.setLcNo(newLCRegNo);
                        prmsLcRigistration.setContractId(prmsContract);
                        prmsLcRigistration.setSupplier(prmsContract.getSuppId().getVendorName());
                        prmsLcRigistration.setCurrencyId(fmsLuCurrency);
                        prmsLcRigistration.setCountryId(comLuCountry);
                        prmsLcRigistration.setPortOfDischarge(portOfDischarge);
                        prmsLcRigistration.setPortOfLoding(portOfLoading);

                        prmsLcRigistration.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        prmsLcRigistration.setForeignId(prmsForeignExchange);
                        prmsLcRigistration.setServiceProId(prmsServiceProvider);
                        if (fileName != null) {
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(fileType);
                            prmsLuDmArchive.setUploadFile(byteFile);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        }

                        prmsLcRigistration.setDocumentId(prmsLuDmArchive);
                        prmsLcRigistration.setStatus(Constants.PREPARE_VALUE);

                        prmsLcRigistration.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                        wfPrmsProcessed.setLcId(prmsLcRigistration);
                        wfPrmsProcessed.setDecision(String.valueOf(prmsLcRigistration.getStatus()));
                        prmsLcRigistration.getWfPrmsProcessedLists().add(wfPrmsProcessed);

                        letterOfCreditRegiBeanLocal.save(prmsLcRigistration);
                        JsfUtil.addSuccessMessage("LC Information is Saved Successfully");
                        clearRegistration();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addFatalMessage("some thing is going to error" + e);
                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        prmsLcRigistration.setPortOfDischarge(portOfDischarge);
                        prmsLcRigistration.setPortOfLoding(portOfLoading);
                        prmsLcRigistration.setCurrencyId(fmsLuCurrency);
                        prmsLcRigistration.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        prmsLcRigistration.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                        prmsLcRigistration.setForeignId(prmsForeignExchange);
                        prmsLcRigistration.setServiceProId(prmsServiceProvider);
                        prmsLcRigistration.setContractId(prmsContract);
                        prmsLcRigistration.setSupplier(prmsContract.getSuppId().getVendorName());

                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(fileType);
                        prmsLuDmArchive.setUploadFile(byteFile);
                        if (prmsLuDmArchive.getFileName() != null) {
                            prmsLcRigistration.setDocumentId(prmsLuDmArchive);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        }

                        letterOfCreditRegiBeanLocal.update(prmsLcRigistration);
                        wfPrmsProcessed.setLcId(prmsLcRigistration);
                        prmsLcRigistration.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("LC Information is Updated Successfully");
                        saveorUpdateBundle = "Save";
                        clearRegistration();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("error== w/n data modification" + e);
                        clearRegistration();
                    }
                } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        prmsLcRigistration.setStatus(Constants.APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        prmsLcRigistration.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        prmsLcRigistration.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        prmsLcRigistration.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                    }
                    wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                    letterOfCreditRegiBeanLocal.update(prmsLcRigistration);
                    JsfUtil.addSuccessMessage("LC Information is Decided Successfully");
                    saveorUpdateBundle = "Save";
                    clearRegistration();

                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {

        }
    }
    // </editor-fold>

}
