/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.LCRegAmendmentBeanLocal;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcRegDetail;
import et.gov.eep.prms.entity.PrmsLcRegDetailAmendment;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsFileUpload;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.businessLogic.LetterOfCreditRegiBeanLocal;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.primefaces.event.FileUploadEvent;
import webService.AAA;
import webService.IAdministration;
import securityBean.SessionBean;
import webService.EventEntry;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import org.insa.model.DocumentModel;
import org.primefaces.model.StreamedContent;
import securityBean.WorkFlow;
import securityBean.Constants;
import org.insa.client.DmsHandler;
import java.text.ParseException;

// </editor-fold>
//LC Registaration Amendment view scoped CDI Named Bean class
@Named("LCRegAmendmentControllers")
@ViewScoped
public class LCRegAmendmentControllers implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    LCRegAmendmentBeanLocal lCRegAmendmentBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @EJB
    LetterOfCreditRegiBeanLocal letterOfCreditRegiBeanLocal;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsLcRigistrationAmend prmsLcRigistrationAmend;
    @Inject
    PrmsLcRigistration prmsLcRigistration;
    @Inject
    PrmsLcRegDetailAmendment prmsLcRegDetailAmendment;
    @Inject
    PrmsContract prmsContract;
    @Inject
    PrmsPortEntry portOfLoading;
    @Inject
    PrmsPortEntry portOfDischarge;
    @Inject
    ComLuCountry comLuCountry;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    private PrmsServiceProvider prmsServiceProvider;
    @Inject
    DataUpload dataUpload;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;

    @Inject
    private PrmsFileUpload prmsFileUpload;
    @Inject
    private WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private WorkFlow workFlow;
    @Inject
    SessionBean sessionBean;
    @Inject
    private PrmsForeignExchange prmsForeignExchange;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare lists and models">
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsLcRegDetailAmendment> prmsLcRegDetailAmendmentModel;
    DataModel<PrmsLcRigistrationAmend> prmsLcRigistrationAmendModel;
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    DataModel<DocumentModel> docValueModel;
    DataModel<PrmsFileUpload> fileUploadDataModel;
    List<PrmsLcRigistrationAmend> lcAmendmentSearchParameterLst;
    List<PrmsLcRigistration> prmsLcRigistrationList;
    List<PrmsLcRegDetail> prmsLcRegDetailList;
    List<PrmsLcRegDetailAmendment> prmsLcRegDetailAmendmentList;
    List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendList;
    private List<PrmsLcRigistrationAmend> AmendLCList;
    List<PrmsContract> listOfContract = new ArrayList<>();
    List<PrmsServiceProvider> listOfService = new ArrayList<>();
    List<ComLuCountry> countryList;
    List<PrmsPortEntry> listOfPorts;
    List<FmsLuCurrency> currencyLists;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    private List<PrmsForeignExchange> prmsForeignExchangeLsts;
    private List<PrmsContractAmendment> prmsContractAmendmentLst;
    List<Integer> doLst = new ArrayList<>();
    List<PrmsFileUpload> documentList;
    List<DocumentModel> newDoclist = new ArrayList<>();
    List<Integer> savedDocIds = new ArrayList<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare variables">
    private String saveorUpdateBundle = "Amend";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String selectedValue = "";
    private String loggerName;
    private String fileName;
    private String fileType;
    String lasLCAmendNo;
    String newLCAmendNo = "0";

    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private boolean documentFee = true;
    private boolean isFileSelected = false;
    private boolean isRendercreate;
    private boolean isRenderNotify;
    private boolean renderDecision = false;

    int updateStatus = 0;
    int requestNotificationCounter = 0;
    int selectedRowIndex;
    int noOfDays;
    int status = 0;
    int savedDocId;
    int docId;
    private byte[] byteFile;
    private Date shipmentDate;
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    private PrmsLcRigistrationAmend prmsLcRigistrationAmendSelect;
    DmsHandler dmsHandler = new DmsHandler();
    StreamedContent file;
    DocumentModel documentModel = new DocumentModel();
// </editor-fold>

    // callback life cycle method
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
    public PrmsLcRigistrationAmend getPrmsLcRigistrationAmendSelect() {
        return prmsLcRigistrationAmendSelect;
    }

    public void setPrmsLcRigistrationAmendSelect(PrmsLcRigistrationAmend prmsLcRigistrationAmendSelect) {
        this.prmsLcRigistrationAmendSelect = prmsLcRigistrationAmendSelect;
    }

    public PrmsLcRigistrationAmend getPrmsLcRigistrationAmend() {
        if (prmsLcRigistrationAmend == null) {
            prmsLcRigistrationAmend = new PrmsLcRigistrationAmend();
        }
        return prmsLcRigistrationAmend;
    }

    public void setPrmsLcRigistrationAmend(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        this.prmsLcRigistrationAmend = prmsLcRigistrationAmend;
    }

    public PrmsLcRigistration getPrmsLcRigistration() {
        if (prmsLcRigistration == null) {
            prmsLcRigistration = new PrmsLcRigistration();
        }
        return prmsLcRigistration;
    }

    public void setPrmsLcRigistration(PrmsLcRigistration prmsLcRigistration) {
        this.prmsLcRigistration = prmsLcRigistration;
    }

    public PrmsLcRegDetailAmendment getPrmsLcRegDetailAmendment() {
        if (prmsLcRegDetailAmendment == null) {
            prmsLcRegDetailAmendment = new PrmsLcRegDetailAmendment();
        }
        return prmsLcRegDetailAmendment;
    }

    public void setPrmsLcRegDetailAmendment(PrmsLcRegDetailAmendment prmsLcRegDetailAmendment) {
        this.prmsLcRegDetailAmendment = prmsLcRegDetailAmendment;
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

    public void setCurrencyLists(List<FmsLuCurrency> currencyLists) {
        this.currencyLists = currencyLists;
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

    public PrmsFileUpload getPrmsFileUpload() {
        if (prmsFileUpload == null) {
            prmsFileUpload = new PrmsFileUpload();
        }
        return prmsFileUpload;
    }

    public void setPrmsFileUpload(PrmsFileUpload prmsFileUpload) {
        this.prmsFileUpload = prmsFileUpload;
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

    public WfPrmsProcessed getWfPrmsProcessed() {
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public SessionBean getSessionBean() {
        if (sessionBean == null) {
            sessionBean = new SessionBean();
        }
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of lists and models">
    public DataModel<PrmsLcRegDetailAmendment> getPrmsLcRegDetailAmendmentModel() {
        if (prmsLcRegDetailAmendmentModel == null) {
            prmsLcRegDetailAmendmentModel = new ListDataModel<>();

        }
        return prmsLcRegDetailAmendmentModel;
    }

    public void setPrmsLcRegDetailAmendmentModel(DataModel<PrmsLcRegDetailAmendment> PrmsLcRegDetailAmendmentModel) {
        this.prmsLcRegDetailAmendmentModel = PrmsLcRegDetailAmendmentModel;
    }

    public DataModel<PrmsLcRigistrationAmend> getPrmsLcRigistrationAmendModel() {

        return prmsLcRigistrationAmendModel;
    }

    public void setPrmsLcRigistrationAmendModel(DataModel<PrmsLcRigistrationAmend> PrmsLcRigistrationAmendModel) {
        this.prmsLcRigistrationAmendModel = PrmsLcRigistrationAmendModel;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {

        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
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

    public List<PrmsLuDmArchive> getPrmsLuDmArchivesList() {
        if (prmsLuDmArchivesList == null) {
            prmsLuDmArchivesList = new ArrayList<>();
        }
        return prmsLuDmArchivesList;
    }

    public void setPrmsLuDmArchivesList(List<PrmsLuDmArchive> prmsLuDmArchivesList) {
        this.prmsLuDmArchivesList = prmsLuDmArchivesList;
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

    public List<PrmsPortEntry> getListOflistOfPorts() {
        if (listOfPorts == null) {
            listOfPorts = new ArrayList<>();
            listOfPorts = lCRegAmendmentBeanLocal.listOfPorts();
        }
        return listOfPorts;
    }

    public void setListOflistOfPorts(List<PrmsPortEntry> listOfPorts) {
        this.listOfPorts = listOfPorts;
    }

    public List<PrmsLcRigistrationAmend> getAmendLCList() {
        if (AmendLCList == null) {
            AmendLCList = new ArrayList<>();
        }
        return AmendLCList;
    }

    public void setAmendLCList(List<PrmsLcRigistrationAmend> AmendLCList) {
        this.AmendLCList = AmendLCList;
    }

    public List<PrmsServiceProvider> getListOfService() {
        listOfService = lCRegAmendmentBeanLocal.listOfServiceNO();
        return listOfService;
    }

    public void setListOfService(List<PrmsServiceProvider> listOfService) {
        this.listOfService = listOfService;
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

    public List<PrmsForeignExchange> getPrmsForeignExchangeLsts() {
        if (prmsForeignExchangeLsts == null) {
            prmsForeignExchangeLsts = new ArrayList<>();
            prmsForeignExchangeLsts = lCRegAmendmentBeanLocal.findForeign();
        }
        return prmsForeignExchangeLsts;
    }

    public void setPrmsForeignExchangeLsts(List<PrmsForeignExchange> prmsForeignExchangeLsts) {
        this.prmsForeignExchangeLsts = prmsForeignExchangeLsts;
    }

    public List<ComLuCountry> getCountryList() {
        if (countryList == null) {
            countryList = new ArrayList<>();
            countryList = lCRegAmendmentBeanLocal.getCountryList();
        }
        return countryList;
    }

    public void setCountryList(List<ComLuCountry> countryList) {
        this.countryList = countryList;
    }

    public List<FmsLuCurrency> getCurrencyLists() {
        if (currencyLists == null) {
            currencyLists = new ArrayList<>();
            currencyLists = letterOfCreditRegiBeanLocal.getCurrencyName();
        }
        return currencyLists;
    }

    public List<PrmsLcRigistrationAmend> getPrmsLcRigistrationAmendList() {
        if (prmsLcRigistrationAmendList == null) {
            prmsLcRigistrationAmendList = new ArrayList<>();
        }
        return prmsLcRigistrationAmendList;
    }

    public void setPrmsLcRigistrationAmendList(List<PrmsLcRigistrationAmend> PrmsLcRigistrationAmendList) {
        this.prmsLcRigistrationAmendList = PrmsLcRigistrationAmendList;
    }

    public List<PrmsLcRigistration> getPrmsLcRigistrationList() {
        if (prmsLcRigistrationList == null) {
            prmsLcRigistrationList = new ArrayList<>();
            prmsLcRigistrationList = lCRegAmendmentBeanLocal.getLCNoLst();
        }
        return prmsLcRigistrationList;
    }

    public void setPrmsLcRigistrationList(List<PrmsLcRigistration> prmsLcRigistrationList) {
        this.prmsLcRigistrationList = prmsLcRigistrationList;
    }

    public List<PrmsLcRegDetail> getPrmsLcRegDetailList() {
        if (prmsLcRegDetailList == null) {
            prmsLcRegDetailList = new ArrayList<>();
        }
        return prmsLcRegDetailList;
    }

    public void setPrmsLcRegDetailList(List<PrmsLcRegDetail> PrmsLcRegDetailList) {
        this.prmsLcRegDetailList = PrmsLcRegDetailList;
    }

    public List<PrmsLcRegDetailAmendment> getPrmsLcRegDetailAmendmentList() {
        if (prmsLcRegDetailAmendmentList == null) {
            prmsLcRegDetailAmendmentList = new ArrayList<>();
        }
        return prmsLcRegDetailAmendmentList;
    }

    public void setPrmsLcRegDetailAmendmentList(List<PrmsLcRegDetailAmendment> prmsLcRegDetailAmendmentList) {
        this.prmsLcRegDetailAmendmentList = prmsLcRegDetailAmendmentList;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isDocumentFee() {
        return documentFee;
    }

    public void setDocumentFee(boolean documentFee) {
        this.documentFee = documentFee;
    }

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
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

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public boolean isIsRendercreate() {
        return isRendercreate;
    }

    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }

    public boolean isIsRenderNotify() {
        return isRenderNotify;
    }

    public void setIsRenderNotify(boolean isRenderNotify) {
        this.isRenderNotify = isRenderNotify;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public int getRequestNotificationCounter() {
        prmsLcRigistrationAmendList = lCRegAmendmentBeanLocal.getLCAmendLists();
        requestNotificationCounter = prmsLcRigistrationAmendList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="change Events">
    public List<PrmsLcRigistrationAmend> getLcAmendmentSearchParameterLst() {
        if (lcAmendmentSearchParameterLst == null) {
            lcAmendmentSearchParameterLst = new ArrayList<>();
            lcAmendmentSearchParameterLst = lCRegAmendmentBeanLocal.getParamNameList();
        }
        return lcAmendmentSearchParameterLst;
    }

    public void setLcAmendmentSearchParameterLst(List<PrmsLcRigistrationAmend> lcAmendmentSearchParameterLst) {
        this.lcAmendmentSearchParameterLst = lcAmendmentSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsLcRigistrationAmend.setColumnName(event.getNewValue().toString());
            prmsLcRigistrationAmend.setColumnValue(null);
        }

    }

    public void setSelectPortLoad(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            portOfLoading = (PrmsPortEntry) e.getNewValue();
            prmsLcRigistrationAmend.setPortOfLoding(portOfLoading);
        }
    }

    public void setSelectPortDisch(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            portOfDischarge = (PrmsPortEntry) e.getNewValue();
            prmsLcRigistrationAmend.setPortOfDischarge(portOfDischarge);
        }

    }

    public void setSelectSuppliers(ValueChangeEvent e) {
        prmsContract = (PrmsContract) e.getNewValue();
        prmsLcRigistrationAmend.setSupplier(prmsContract.getSuppId().getVendorName());
        prmsContractAmendmentLst = lCRegAmendmentBeanLocal.checkAmendOrNot(prmsContract);
        if (prmsContractAmendmentLst.size() > 0) {
            prmsContractAmendment = lCRegAmendmentBeanLocal.getContractAmendment(prmsContract);
            prmsContract.setContractId(prmsContractAmendment.getContractId().getContractId());
            prmsContract.setSuppId(prmsContractAmendment.getSuppId());
        }
    }

    public void setSelectForiegn(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            prmsForeignExchange = (PrmsForeignExchange) e.getNewValue();
            prmsLcRigistrationAmend.setForeignId(prmsForeignExchange);
        }
    }

    public void valueChangeLCNo(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            prmsLcRigistration = (PrmsLcRigistration) event.getNewValue();
            AmendLCList = lCRegAmendmentBeanLocal.getLCAmendedNoListByLcId(prmsLcRigistration);
            listOfContract = letterOfCreditRegiBeanLocal.listOfContractNO();
            prmsLcRigistrationAmend.setLcNo(String.valueOf(prmsLcRigistration.getLcNo()));
            prmsLcRigistrationAmend.setRigistrationDate(prmsLcRigistration.getRigistrationDate());
            prmsLcRigistrationAmend.setLcExpireDate(prmsLcRigistration.getLcExpireDate());
            prmsLcRigistrationAmend.setServiceProId(prmsLcRigistration.getServiceProId());
            prmsLcRigistrationAmend.setLcAmount(prmsLcRigistration.getLcAmount());
            prmsLcRigistrationAmend.setCurrencyId(prmsLcRigistration.getCurrencyId());
            prmsLcRigistrationAmend.setCountryId(prmsLcRigistration.getCountryId());
            prmsLcRigistrationAmend.setContractId(prmsLcRigistration.getContractId());
            prmsLcRigistrationAmend.setCountryId(prmsLcRigistration.getCountryId());
            prmsLcRigistrationAmend.setLcOpeningDate(prmsLcRigistration.getLcOpeningDate());
            prmsLcRigistrationAmend.setShipmentDate(prmsLcRigistration.getShipmentDate());
            prmsLcRigistrationAmend.setForeignId(prmsForeignExchange);
            prmsLcRigistrationAmend.setPermitAmount(prmsLcRigistration.getPermitAmount());
            prmsLcRigistrationAmend.setPortOfLoding(portOfLoading);
            prmsLcRigistrationAmend.setPortOfDischarge(portOfDischarge);
            prmsLcRigistrationAmend.setSupplier(prmsLcRigistration.getSupplier());
            prmsLcRigistrationAmend.setLcPaymentAfterAcceptance(prmsLcRigistration.getLcPaymentAfterAcceptance());
            prmsLcRigistrationAmend.setRemainingLcPaymentAmount(prmsLcRigistration.getRemainingLcPaymentAmount());
            prmsLcRigistrationAmend.setSourchOfFund(prmsLcRigistration.getSourchOfFund());
            prmsLcRigistrationAmend.setLcType(prmsLcRigistration.getLcType());
            prmsLcRigistrationAmend.setPartialShipment(prmsLcRigistration.getPartialShipment());
            prmsLcRigistrationAmend.setDeliveryTermAfterLcAmount(prmsLcRigistration.getDeliveryTermAfterLcAmount());
            prmsLcRigistrationAmend.setNumberOfDays(prmsLcRigistration.getNumberOfDays());
            prmsLcRigistrationAmend.setProcessedOn(prmsLcRigistration.getProcessedOn());

        }

    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();

        }
    }

    public void calcLcExpireDate(ValueChangeEvent dateEntered) throws ParseException {
        SimpleDateFormat sdt = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
        prmsLcRigistrationAmend.setShipmentDate(sdt.parse(dateEntered.getNewValue().toString()));
        if (prmsLcRigistrationAmend.getNumberOfDays() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(prmsLcRigistrationAmend.getShipmentDate()); // Now use z Selected date.
            c.add(Calendar.DATE, prmsLcRigistrationAmend.getNumberOfDays()); // Adding number of days
            Date finalExpireDate = c.getTime();
            prmsLcRigistrationAmend.setLcExpireDate(finalExpireDate);
        }

    }

    public void calcLcExpireDateByDayNo(ValueChangeEvent dateEntered) throws ParseException {
        noOfDays = Integer.parseInt(dateEntered.getNewValue().toString());
        if (prmsLcRigistrationAmend.getShipmentDate() != null) {
            prmsLcRigistrationAmend.setNumberOfDays(noOfDays);
            Calendar c = Calendar.getInstance();
            c.setTime(prmsLcRigistrationAmend.getShipmentDate()); // Now use z Selected date.
            c.add(Calendar.DATE, prmsLcRigistrationAmend.getNumberOfDays()); // Adding number of days
            Date finalExpireDate = c.getTime();
            prmsLcRigistrationAmend.setLcExpireDate(finalExpireDate);
        }
    }

    public void RequestListChange(ValueChangeEvent eve) {
        prmsLcRigistrationAmend = (PrmsLcRigistrationAmend) eve.getNewValue();
        populateDataForApp();
    }

    public void valueChangeLCNoForBoth(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsLcRigistrationAmend = (PrmsLcRigistrationAmend) (event.getNewValue());
            String amendedNo = prmsLcRigistrationAmend.getLcAmendNo();
            prmsLcRigistrationAmend = lCRegAmendmentBeanLocal.getLcAmendInfoByAmendedNo(amendedNo);
            fmsLuCurrency = prmsLcRigistrationAmend.getCurrencyId();
            prmsForeignExchange = prmsLcRigistrationAmend.getForeignId();
            prmsContract = prmsLcRigistrationAmend.getContractId();
            prmsServiceProvider = prmsLcRigistrationAmend.getServiceProId();
            comLuCountry = prmsLcRigistrationAmend.getCountryId();
            portOfLoading = prmsLcRigistrationAmend.getPortOfLoding();
            portOfDischarge = prmsLcRigistrationAmend.getPortOfDischarge();
            wfPrmsProcessed.setProcessedOn(prmsLcRigistrationAmend.getProcessedOn());

        }

    }

    public void changeDocumentFee(ValueChangeEvent e) {
        if (e.getNewValue().equals("Open LC")) {
            documentFee = false;

        } else {
            documentFee = true;
        }

    }

    public void rowSelect(SelectEvent event) {
        prmsLcRigistrationAmend = (PrmsLcRigistrationAmend) event.getObject();
        populateDataForApp();
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
                JsfUtil.addSuccessMessage("Upload Successfully!!!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public String generateLcRegAmendNo() {
        if (saveorUpdateBundle.equals("Update")) {
            lasLCAmendNo = prmsLcRigistrationAmend.getLcAmendNo();

        } else {
            lasLCAmendNo = getNextLcRegAmendNo();
        }
        return lasLCAmendNo;
    }

    public String getNextLcRegAmendNo() {
        String nextLcAmendNo = lCRegAmendmentBeanLocal.getNextLcRegAmendNo();
        return nextLcAmendNo;
    }

    public SelectItem[] getLCNo() {
        return JsfUtil.getSelectItems(lCRegAmendmentBeanLocal.getLCNoLst(), true);
    }

    public SelectItem[] getLCAmendNo() {
        return JsfUtil.getSelectItems(lCRegAmendmentBeanLocal.getListOfLCAmendNum(), true);
    }

    public void recreatAmendDetModel() {
        prmsLcRegDetailAmendmentModel = null;
        prmsLcRegDetailAmendmentModel = new ListDataModel<>(new ArrayList<>(getPrmsLcRegDetailAmendmentList()));
    }

    public void reCreatAmendMdl() {
        prmsLcRigistrationAmendModel = null;
        prmsLcRigistrationAmendModel = new ListDataModel<>(new ArrayList<>(getPrmsLcRigistrationAmendList()));
    }

    public void clearSearch() {
        prmsLcRigistrationAmend = null;
        prmsLcRegDetailAmendment = null;
        prmsLcRegDetailAmendmentModel = null;
        prmsLuDmArchive = null;
        prmsLuDmArchivesDModel = null;
        prmsLcRigistration = null;
        prmsForeignExchange = null;
        fmsLuCurrency = null;
        prmsContract = null;
        prmsServiceProvider = null;
        comLuCountry = null;
        portOfDischarge = null;
        portOfLoading = null;
        wfPrmsProcessed.setProcessedOn(null);
    }

    public void amendLCReg() {
        saveorUpdateBundle = "Amend";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clear();
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
        renderPnlManPage = true;
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
    }

    public void searchByLCAmendNo() {
        prmsLcRigistrationAmend.setPreparedBy(workFlow.getUserAccount());
        prmsLcRigistrationAmendList = lCRegAmendmentBeanLocal.searchLCNoLCAmendNo(prmsLcRigistrationAmend);
        reCreatAmendMdl();
        prmsLcRigistrationAmend = new PrmsLcRigistrationAmend();
    }

    //to calculate LC remaining amount
    public void remainingLcAmount() {
        if (prmsLcRigistrationAmend.getDeliveryTermAfterLcAmount() != null && prmsLcRigistrationAmend.getLcPaymentAfterAcceptance() != null) {
            prmsLcRigistrationAmend.setRemainingLcPaymentAmount(prmsLcRigistrationAmend.getDeliveryTermAfterLcAmount() - prmsLcRigistrationAmend.getLcPaymentAfterAcceptance());
        }

    }

    public void edit() {
        prmsLcRegDetailAmendment = prmsLcRegDetailAmendmentModel.getRowData();
        selectedRowIndex = prmsLcRegDetailAmendmentModel.getRowIndex();
    }

    public void populateDataForApp() {
        prmsLcRigistrationAmend.setId(prmsLcRigistrationAmend.getId());
        currencyLists = lCRegAmendmentBeanLocal.getCurrencyName();
        fmsLuCurrency = prmsLcRigistrationAmend.getCurrencyId();
        countryList = lCRegAmendmentBeanLocal.getCountryList();
        comLuCountry = prmsLcRigistrationAmend.getCountryId();
        listOfContract = letterOfCreditRegiBeanLocal.listOfContractNO();
        prmsContract = prmsLcRigistrationAmend.getContractId();
        portOfLoading = prmsLcRigistrationAmend.getPortOfLoding();
        portOfDischarge = prmsLcRigistrationAmend.getPortOfDischarge();
        prmsLcRigistration = prmsLcRigistrationAmend.getLcId();
        prmsForeignExchange = prmsLcRigistrationAmend.getForeignId();
        reCreatAmendMdl();
        if (prmsLcRigistrationAmend.getDocumentId() != null) {
            prmsLuDmArchive = prmsLcRigistrationAmend.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
        }
        recreateDmsDataModel();
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsLcRigistrationAmend.getProcessedOn());
        }

        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
    }

    public void recreateDmsDataModel() {
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public void getfile() {
        docValueModel = dataUpload.selectListOfFileByDocId(doLst);
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Please Select File Name");
        }
        return file;
    }

    public void clear() {
        prmsLcRigistrationAmend = null;
        comLuCountry = null;
        fmsLuCurrency = null;
        prmsContract = null;
        portOfDischarge = null;
        portOfLoading = null;
        saveorUpdateBundle = "Amend";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        prmsLcRegDetailAmendmentModel = null;
        prmsLcRigistration = null;
        prmsServiceProvider = null;
        newDoclist = new ArrayList<>();
        docValueModel = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setCommentGiven(null);
        prmsForeignExchange = null;
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchive = null;
        renderpnlToSearchPage = false;
    }

    public void remove() {
        getPrmsFileUpload().setId(getPrmsFileUpload().getId());
        lCRegAmendmentBeanLocal.remove(getPrmsFileUpload());
        prmsLcRigistration.getPrmsFileUploadList().remove(getPrmsFileUpload());
        recreateDmsDataModel();

    }

    public void remove(DocumentModel document) {
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        //   remove();
        recreateDmsDataModel();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update methods">
    public void saveLCAmend() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLCAmend", dataset)) {

                if (saveorUpdateBundle.equals("Amend")) {
                    try {
                        generateLcRegAmendNo();
                        prmsLcRigistrationAmend.setLcAmendNo(lasLCAmendNo);
                        prmsLcRigistrationAmend.setContractId(prmsContract);
                        prmsLcRigistrationAmend.setLcId(prmsLcRigistration);
                        prmsLcRigistrationAmend.setLcAmendNo(prmsLcRigistrationAmend.getLcAmendNo());
                        prmsLcRigistrationAmend.setSupplier(prmsContract.getSuppId().getVendorName());
                        prmsLcRigistrationAmend.setCurrencyId(fmsLuCurrency);
                        prmsLcRigistrationAmend.setCountryId(comLuCountry);
                        prmsLcRigistrationAmend.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        prmsLcRigistrationAmend.setForeignId(prmsForeignExchange);
                        prmsLcRigistrationAmend.setPermitAmount(prmsForeignExchange.getPermitAmount());
                        prmsLcRigistrationAmend.setPortOfLoding(portOfLoading);
                        prmsLcRigistrationAmend.setPortOfDischarge(portOfDischarge);
                        prmsLcRigistrationAmend.setServiceProId(prmsServiceProvider);

                        prmsLcRigistrationAmend.setStatus(Constants.PREPARE_VALUE);
                        prmsLcRigistrationAmend.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                        if (fileName != null) {
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(fileType);
                            prmsLuDmArchive.setUploadFile(byteFile);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        }

                        prmsLcRigistrationAmend.setDocumentId(prmsLuDmArchive);

                        wfPrmsProcessed.setDecision(String.valueOf(prmsLcRigistrationAmend.getStatus()));
                        wfPrmsProcessed.setLcAmendmentId(prmsLcRigistrationAmend);
                        prmsLcRigistrationAmend.getWfPrmsProcessedLists().add(wfPrmsProcessed);

                        lCRegAmendmentBeanLocal.save(prmsLcRigistrationAmend);
                        JsfUtil.addSuccessMessage("LC Amendment Information is  Saved Successfully");
                        clear();

                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addFatalMessage("Some thing is going to error" + e);
                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        prmsLcRigistrationAmend.setCurrencyId(fmsLuCurrency);
                        prmsLcRigistrationAmend.setContractId(prmsContract);
                        prmsLcRigistrationAmend.setCountryId(comLuCountry);
                        prmsLcRigistrationAmend.setForeignId(prmsForeignExchange);
                        prmsLcRigistrationAmend.setPermitAmount(prmsForeignExchange.getPermitAmount());
                        prmsLcRigistrationAmend.setServiceProId(prmsServiceProvider);
                        prmsLcRigistrationAmend.setPortOfLoding(portOfLoading);
                        prmsLcRigistrationAmend.setPortOfDischarge(portOfDischarge);
                        prmsLcRigistrationAmend.setStatus(Constants.PREPARE_VALUE);
                        prmsLcRigistrationAmend.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                        prmsLcRigistrationAmend.setLcAmendNo(prmsLcRigistrationAmend.getLcAmendNo());
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(fileType);
                        prmsLuDmArchive.setUploadFile(byteFile);
                        if (prmsLuDmArchive.getFileName() != null) {
                            prmsLcRigistrationAmend.setDocumentId(prmsLuDmArchive);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);

                        }

                        lCRegAmendmentBeanLocal.edit(prmsLcRigistrationAmend);
                        wfPrmsProcessed.setLcAmendmentId(prmsLcRigistrationAmend);
                        prmsLcRigistrationAmend.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("LC Amendment Information is Updated Successfully");
                        saveorUpdateBundle = "Amend";
                        clear();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Error w/n data Updating" + e);
                    }
                } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        prmsLcRigistrationAmend.setStatus(Constants.APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        prmsLcRigistrationAmend.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        prmsLcRigistrationAmend.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        prmsLcRigistrationAmend.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                    }
                    wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                    lCRegAmendmentBeanLocal.edit(prmsLcRigistrationAmend);
                    JsfUtil.addSuccessMessage("LC Amendment Information is Decided Successfully");
                    saveorUpdateBundle = "Amend";
                    clear();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate Permission, please contact the system Administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //</editor-fold>

}
