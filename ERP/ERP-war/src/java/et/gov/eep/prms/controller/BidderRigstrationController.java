package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.utility.JsfUtil;

import et.gov.eep.hrms.integration.HREmployeesBeanLocal;

import et.gov.eep.prms.businessLogic.BidOpeningListLocal;
import et.gov.eep.prms.businessLogic.BidderRegistrationLocal;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import et.gov.eep.prms.entity.PrmsBidderRegDetail;
import et.gov.eep.prms.entity.PrmsBidderRegistration;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
// </editor-fold>

//Bid Registration view scoped CDI Named Bean class
@Named(value = "bidderRigstrationController")
@ViewScoped
public class BidderRigstrationController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private BidOpeningListLocal bidOpeningListLocal;
    @EJB
    private WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    private BidderRegistrationLocal bidderRegistrationLocal;
    @EJB
    HREmployeesBeanLocal hREmployeesBeanLocal;
    @EJB
    ContractInformationBeanLocal contractInformationBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    DataUpload dataUpload;
    @Inject
    private PrmsBidderRegDetail prmsBidderRegDetail;
    @Inject
    private PrmsBidderRegistration prmsBidderRegistration;
    @Inject
    private PrmsBid prmsBid;
    @Inject
    private FmsVoucher fmsVoucher;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private FmsCashReceiptVoucher fmsCashReceiptVoucher;
    @Inject
    private ComLuCountry comLuCountry;
    @Inject
    private PrmsBidDetail prmsBidDetail;
    @Inject
    SessionBean SessionBean;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    private FmsCashReceiptVoucher fmsCashReceiptVoucher1;
    @Inject
    WorkFlow workFlow;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    DataModel<PrmsBidderRegDetail> prmsprmsBidderRegDetailmodel;
    private DataModel<PrmsBidderRegistration> PrmsPrmsBidderRegistrationDataModel;
    DataModel<DocumentModel> docList;
    List<PrmsBidderRegistration> bidderRegSearchParameterLst;
    List<PrmsBid> prmsBidList;
    private List<PrmsSupplyProfile> prmsSupplyProfilLst;
    List<PrmsBidderRegistration> prmsBidderRegistrations;
    List<FmsCashReceiptVoucher> fmsBidSales;
    List<ComLuCountry> comLuCountrysList;
    List<PrmsBid> prmsBids;
    private List<PrmsBidderRegistration> prmsBidderRegistrationsForstatus;
    List<PrmsBidDetail> prmsBidDetailsList;

    Set<String> actionPlnDetlCheck = new HashSet<>();
    Set<String> addressCheck = new HashSet<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Variables">
    private String saveorUpdateBundle = "Save";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private String addOrModifyBundle = "Add";
    private String icone = "ui-icon-plus";
    String select = "Select";
    private String selectOptPartyName;
    private String duplicattion = null;
    String purchaseType;
    String logerName;
    String newcheckListNo;
    String LastcheckListNo = "0";
    private String userName;
    private String activeIndex;
    private String[] selectedLotName;

    private boolean disableBtnCreate = false;
    private boolean renderPnlManPage = true;
    private boolean renderMarketTable = true;
    private boolean chooseselecttOption = false;
    private boolean chooselocalreader = true;
    private boolean directOfferBid = true;
    private boolean bidDocumentBid;
    private boolean rendorAgent = true;
    private boolean renderBidlot = false;
    private boolean renderpnlToSearchPage;
    private boolean disableServiceAndWorkRdbtnWhenSearch = false;
    private boolean renderPnlCreateServiceRegistration = false;

    int selectStatue = 0;
    int updateStatus = 0;
    int uploadedDocId = -1;

    StreamedContent fileDown;
    DocumentModel dm = new DocumentModel();
    DocumentModel documentModel = new DocumentModel();
    DataUpload uploadSelection;
    StreamedContent content;
    private PrmsBidderRegistration bidderSelection;
    // </editor-fold>

//default constructor method
    public BidderRigstrationController() {
    }

    // life cycle callback method
    @PostConstruct
    public void init() {
        prmsBidderRegistrationsForstatus = bidderRegistrationLocal.searchBidderRegistration();
        setLogerName(SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(workFlow.getUserName());

    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public FmsCashReceiptVoucher getFmsCashReceiptVoucher() {
        if (fmsCashReceiptVoucher == null) {
            fmsCashReceiptVoucher = new FmsCashReceiptVoucher();
        }
        return fmsCashReceiptVoucher;

    }

    public void setFmsCashReceiptVoucher(FmsCashReceiptVoucher fmsCashReceiptVoucher) {
        this.fmsCashReceiptVoucher = fmsCashReceiptVoucher;
    }

    public FmsVoucher getFmsVoucher() {
        if (fmsVoucher == null) {
            fmsVoucher = new FmsVoucher();
        }
        return fmsVoucher;
    }

    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    public PrmsBidDetail getPrmsBidDetail() {
        if (prmsBidDetail == null) {
            prmsBidDetail = new PrmsBidDetail();
        }
        return prmsBidDetail;
    }

    public void setPrmsBidDetail(PrmsBidDetail prmsBidDetail) {
        this.prmsBidDetail = prmsBidDetail;
    }

    public PrmsBidderRegDetail getPrmsBidderRegDetail() {
        if (prmsBidderRegDetail == null) {
            prmsBidderRegDetail = new PrmsBidderRegDetail();
        }
        return prmsBidderRegDetail;
    }

    public void setPrmsBidderRegDetail(PrmsBidderRegDetail prmsBidderRegDetail) {
        this.prmsBidderRegDetail = prmsBidderRegDetail;
    }

    public PrmsBidderRegistration getPrmsBidderRegistration() {
        if (prmsBidderRegistration == null) {
            prmsBidderRegistration = new PrmsBidderRegistration();
        }
        return prmsBidderRegistration;
    }

    public void setPrmsBidderRegistration(PrmsBidderRegistration prmsBidderRegistration) {
        this.prmsBidderRegistration = prmsBidderRegistration;
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

    public PrmsBid getPrmsBid() {
        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
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

    public FmsCashReceiptVoucher getFmsCashReceiptVoucher1() {
        if (fmsCashReceiptVoucher1 == null) {
            fmsCashReceiptVoucher1 = new FmsCashReceiptVoucher();
        }
        return fmsCashReceiptVoucher1;
    }

    public void setFmsCashReceiptVoucher1(FmsCashReceiptVoucher fmsCashReceiptVoucher1) {
        this.fmsCashReceiptVoucher1 = fmsCashReceiptVoucher1;
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

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
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

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<PrmsBidderRegistration> getPrmsBidderRegistrationsForstatus() {
        return prmsBidderRegistrationsForstatus;
    }

    public void setPrmsBidderRegistrationsForstatus(List<PrmsBidderRegistration> prmsBidderRegistrationsForstatus) {
        this.prmsBidderRegistrationsForstatus = prmsBidderRegistrationsForstatus;
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfilLst() {
        if (prmsSupplyProfilLst == null) {
            prmsSupplyProfilLst = new ArrayList<>();
            prmsSupplyProfilLst = bidderRegistrationLocal.findSuppliers();
        }

        return prmsSupplyProfilLst;
    }

    public void setPrmsSupplyProfilLst(List<PrmsSupplyProfile> prmsSupplyProfilLst) {
        this.prmsSupplyProfilLst = prmsSupplyProfilLst;
    }

    public List<PrmsBid> getPrmsBidList() {
        if (prmsBidList == null) {
            prmsBidList = new ArrayList<>();
            prmsBidList = bidderRegistrationLocal.BidNoFormBidSale();
        }

        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    public List<PrmsBid> getPrmsBids() {
        if (prmsBids == null) {
            prmsBids = new ArrayList<>();
            prmsBids = bidderRegistrationLocal.getRistrictedBid();
        }

        return prmsBids;
    }

    public void setPrmsBids(List<PrmsBid> prmsBids) {
        this.prmsBids = prmsBids;
    }

    public List<FmsCashReceiptVoucher> getFmsBidSales() {
        if (fmsBidSales == null) {
            fmsBidSales = new ArrayList<>();
        }
        return fmsBidSales;
    }

    public void setFmsBidSales(List<FmsCashReceiptVoucher> fmsBidSales) {
        this.fmsBidSales = fmsBidSales;
    }

    public List<PrmsBidderRegistration> getPrmsBidderRegistrations() {
        if (prmsBidderRegistrations == null) {
            prmsBidderRegistrations = new ArrayList<>();
        }
        return prmsBidderRegistrations;
    }

    public void setPrmsBidderRegistrations(List<PrmsBidderRegistration> prmsBidderRegistrations) {
        this.prmsBidderRegistrations = prmsBidderRegistrations;
    }

    public List<ComLuCountry> getComLuCountrysList() {
        if (comLuCountrysList == null) {
            comLuCountrysList = new ArrayList<>();
        }
        return comLuCountrysList;
    }

    public void setComLuCountrysList(List<ComLuCountry> comLuCountrysList) {
        this.comLuCountrysList = comLuCountrysList;
    }

    public List<PrmsBidDetail> getPrmsBidDetailsList() {
        if (prmsBidDetailsList == null) {
            prmsBidDetailsList = new ArrayList<>();
        }

        return prmsBidDetailsList;
    }

    public void setPrmsBidDetailsList(List<PrmsBidDetail> prmsBidDetailsList) {
        this.prmsBidDetailsList = prmsBidDetailsList;
    }

    public DataModel<PrmsBidderRegDetail> getPrmsprmsBidderRegDetailmodel() {
        if (prmsprmsBidderRegDetailmodel == null) {
            prmsprmsBidderRegDetailmodel = new ListDataModel<>();
        }
        return prmsprmsBidderRegDetailmodel;
    }

    public void setPrmsprmsBidderRegDetailmodel(DataModel<PrmsBidderRegDetail> prmsprmsBidderRegDetailmodel) {
        this.prmsprmsBidderRegDetailmodel = prmsprmsBidderRegDetailmodel;
    }

    public DataModel<PrmsBidderRegistration> getPrmsPrmsBidderRegistrationDataModel() {
        if (PrmsPrmsBidderRegistrationDataModel == null) {
            PrmsPrmsBidderRegistrationDataModel = new ListDataModel<>();
        }
        return PrmsPrmsBidderRegistrationDataModel;
    }

    public void setPrmsPrmsBidderRegistrationDataModel(DataModel<PrmsBidderRegistration> PrmsPrmsBidderRegistrationDataModel) {
        this.PrmsPrmsBidderRegistrationDataModel = PrmsPrmsBidderRegistrationDataModel;
    }

    public DataModel<DocumentModel> getDocList() {
        if (docList == null) {
            docList = new ListDataModel<>();
        }
        return docList;
    }

    public void setDocList(DataModel<DocumentModel> docList) {
        this.docList = docList;
    }
        // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getAddressInfoButton() {
        return addressInfoButton;
    }

    public void setAddressInfoButton(String addressInfoButton) {
        this.addressInfoButton = addressInfoButton;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
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

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public boolean isDirectOfferBid() {
        return directOfferBid;
    }

    public void setDirectOfferBid(boolean directOfferBid) {
        this.directOfferBid = directOfferBid;
    }

    public boolean isBidDocumentBid() {
        return bidDocumentBid;
    }

    public void setBidDocumentBid(boolean BidDocumentBid) {
        this.bidDocumentBid = BidDocumentBid;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isDisableServiceAndWorkRdbtnWhenSearch() {
        return disableServiceAndWorkRdbtnWhenSearch;
    }

    public void setDisableServiceAndWorkRdbtnWhenSearch(boolean disableServiceAndWorkRdbtnWhenSearch) {
        this.disableServiceAndWorkRdbtnWhenSearch = disableServiceAndWorkRdbtnWhenSearch;
    }

    public boolean isRenderPnlCreateServiceRegistration() {
        return renderPnlCreateServiceRegistration;
    }

    public void setRenderPnlCreateServiceRegistration(boolean renderPnlCreateServiceRegistration) {
        this.renderPnlCreateServiceRegistration = renderPnlCreateServiceRegistration;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderMarketTable() {
        return renderMarketTable;
    }

    public void setRenderMarketTable(boolean renderMarketTable) {
        this.renderMarketTable = renderMarketTable;
    }

    public boolean isChooseselecttOption() {
        return chooseselecttOption;
    }

    public void setChooseselecttOption(boolean chooseselecttOption) {
        this.chooseselecttOption = chooseselecttOption;
    }

    public boolean isChooselocalreader() {
        return chooselocalreader;
    }

    public void setChooselocalreader(boolean chooselocalreader) {
        this.chooselocalreader = chooselocalreader;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isRendorAgent() {
        return rendorAgent;
    }

    public void setRendorAgent(boolean rendorAgent) {
        this.rendorAgent = rendorAgent;
    }

    public boolean isRenderBidlot() {
        return renderBidlot;
    }

    public void setRenderBidlot(boolean renderBidlot) {
        this.renderBidlot = renderBidlot;
    }

    public String[] getSelectedLotName() {
        return selectedLotName;
    }

    public void setSelectedLotName(String[] selectedLotName) {
        this.selectedLotName = selectedLotName;
    }

    public int getSelectStatue() {
        return selectStatue;
    }

    public void setSelectStatue(int selectStatue) {
        this.selectStatue = selectStatue;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public StreamedContent getFileDown() throws Exception {
        fileDown = dataUpload.getFile(dm);
        return fileDown;
    }

    public void setFileDown(StreamedContent fileDown) {
        this.fileDown = fileDown;
    }

    public DocumentModel getDm() {
        return dm;
    }

    public void setDm(DocumentModel dm) {
        this.dm = dm;
    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public PrmsBidderRegistration getBidderSelection() {
        return bidderSelection;
    }

    public void setBidderSelection(PrmsBidderRegistration bidderSelection) {
        this.bidderSelection = bidderSelection;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event changes">

    public List<PrmsBidderRegistration> getBidderRegSearchParameterLst() {
        if (bidderRegSearchParameterLst == null) {
            bidderRegSearchParameterLst = new ArrayList<>();
            bidderRegSearchParameterLst = bidderRegistrationLocal.getParamNameList();
        }
        return bidderRegSearchParameterLst;
    }

    public void setBidderRegSearchParameterLst(List<PrmsBidderRegistration> bidderRegSearchParameterLst) {
        this.bidderRegSearchParameterLst = bidderRegSearchParameterLst;
    }
    
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsBidderRegistration.setColumnName(event.getNewValue().toString());
            prmsBidderRegistration.setColumnValue(null);
        }
    }
    
    public void rowSelect(SelectEvent event) {
        prmsBidDetailsList = new ArrayList<>();
        prmsBidderRegistration = (PrmsBidderRegistration) event.getObject();
        prmsBid = prmsBidderRegistration.getBidId();
        prmsBidDetailsList = prmsBid.getPrmsBidDetailList();
        renderpnlToSearchPage = true;
        renderPnlCreateServiceRegistration = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        updateStatus = 1;

        disableServiceAndWorkRdbtnWhenSearch = true;
        select = prmsBidderRegistration.getBidderType();
        if (select.equalsIgnoreCase("Direct Offer")) {
            chooseselecttOption = false;
            chooselocalreader = true;
            directOfferBid = true;
            bidDocumentBid = false;

            purchaseType = prmsBid.getAwardType();

            if (purchaseType.equalsIgnoreCase("Lot Base")) {
                prmsBidDetailsList = prmsBid.getPrmsBidDetailList();
                prmsBidderRegDetail.getLotName();
                renderBidlot = true;

            } else {
                renderBidlot = false;

            }

        } else if (select.equalsIgnoreCase("Bid Document Sale")) {
            chooseselecttOption = true;
            chooselocalreader = false;
            directOfferBid = false;
            bidDocumentBid = true;
        }

        recreateprmsprmsBidderRegDetailmodel();

    }

    public void handleSectionType(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsBidderRegistration.setBidderType(select);
            select = event.getNewValue().toString();
            if (select.equalsIgnoreCase("Direct Offer")) {
                chooseselecttOption = false;
                chooselocalreader = true;
                directOfferBid = true;
                bidDocumentBid = false;
            } else if (select.equalsIgnoreCase("Bid Document Sale")) {
                chooseselecttOption = true;
                chooselocalreader = false;
                directOfferBid = false;
                bidDocumentBid = true;
            }
        }
    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = prmsprmsBidderRegDetailmodel.getRowIndex();
        PrmsBidderRegDetail contactPerson = new PrmsBidderRegDetail();

        PrmsBidderRegDetail comContPerson = (PrmsBidderRegDetail) event.getObject();

        boolean found = false;
        for (int i = 0; i < prmsBidderRegistration.getPrmsBidderRegDetailList().size(); i++) {
            if (i != rowIndex) {
                if (prmsBidderRegistration.getPrmsBidderRegDetailList().get(i).getTimeSub().equals(comContPerson.getTimeSub())
                        && prmsBidderRegistration.getPrmsBidderRegDetailList().get(i).getDateSub().equals(comContPerson.getDateSub())) {
                    found = true;
                    break;
                }
            }
        }

        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");

            comContPerson.setDateSub(null);
            prmsBidderRegistration.getPrmsBidderRegDetailList().set(rowIndex, comContPerson);
            recreateprmsprmsBidderRegDetailmodel();
        } else {
            prmsBidderRegistration.getPrmsBidderRegDetailList().set(rowIndex, comContPerson);
            recreateprmsprmsBidderRegDetailmodel();
        }
    }

    public void BidDateGetter(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            prmsBid = (PrmsBid) event.getNewValue();
            bidderRegistrationLocal.getBidDates(prmsBid);
            if (prmsBid.getBidType().equalsIgnoreCase("local")) {
                rendorAgent = true;
            } else {
                rendorAgent = false;
            }
            prmsBidderRegistration.setBidId(prmsBid);
            prmsBidDetailsList = prmsBid.getPrmsBidDetailList();
            purchaseType = prmsBid.getAwardType();
            if (purchaseType.equalsIgnoreCase("Lot Base")) {
                renderBidlot = true;
            } else {
                renderBidlot = false;
            }

        }

    }

    public void rowSelectFile(SelectEvent event) {
        documentModel = (DocumentModel) event.getObject();
    }

    public void getSupplierName(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            prmsSupplyProfile.setVendorName(event.getNewValue().toString());
            prmsSupplyProfile = bidderRegistrationLocal.findBySubId(prmsSupplyProfile);

            prmsBidderRegDetail.setSuppId(prmsSupplyProfile);
        }
    }

    public void getVendorName(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            prmsSupplyProfile = (PrmsSupplyProfile) event.getNewValue();

            fmsVoucher = bidderRegistrationLocal.getRecietNo(fmsVoucher);

            prmsBidderRegDetail.setSuppId(prmsSupplyProfile);
        }
    }

    public void getService(ValueChangeEvent event) {
        prmsSupplyProfile = (PrmsSupplyProfile) event.getNewValue();
        int size = prmsSupplyProfile.getFmsBiFmsCashReceiptVouchers().size();
        fmsBidSales = new ArrayList<>();
        fmsBidSales = prmsSupplyProfile.getFmsBiFmsCashReceiptVouchers();

        for (int i = 0; i < fmsBidSales.size(); i++) {

            if (fmsBidSales.get(i).getSuppId().equals(prmsSupplyProfile)) {
                fmsCashReceiptVoucher1 = fmsBidSales.get(i);

                break;
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event3) {
        InputStream fileByteFile_ = null;
        String docFormat = event3.getFile().getFileName().split("\\.")[1];
        String fileName = event3.getFile().getFileName().split("\\.")[0];
        String fileNameWzExtent = event3.getFile().getFileName();
        String categoryBundle = "et/gov/eep/commonApplications/securityServer";
        try {
            fileByteFile_ = event3.getFile().getInputstream();
        } catch (IOException e) {
        }
        uploadedDocId = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        System.out.println("Id For Uploaded Doc is===" + uploadedDocId);
        FacesMessage msg = new FacesMessage("Successfully" + event3.getFile().getFileName() + "With Size" + event3.getFile().getSize() + "is Uploaded");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void uploadListener(FileUploadEvent event) {

        InputStream fileByteFile_ = null;
        String docFormat = event.getFile().getFileName().split("\\.")[1];
        String fileName = event.getFile().getFileName().split("\\.")[0];
        String fileNameWzExtent = event.getFile().getFileName();
        String categoryBundle = "et/gov/eep/commonApplications/securityServer";
        try {
            fileByteFile_ = event.getFile().getInputstream();
        } catch (IOException ex) {
            System.out.println("Upload Error[from Lisener]==>" + ex.getMessage());
        }
        uploadedDocId = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        System.out.println("<=====uploadedDocId from Lisener IS===>" + uploadedDocId + "   fileName1=" + fileName + "   fileName2=" + fileNameWzExtent);
    }

    public void vendorNameValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsBidderRegDetail.setSuppId((prmsSupplyProfile));
        }
    }

    public void onContactRowCancel(RowEditEvent event) {

    }

    public void changeEventSuppCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsBid = (PrmsBid) event.getNewValue();
            prmsBidderRegistration.setBidId(prmsBid);

            prmsSupplyProfilLst = bidderRegistrationLocal.getsupplierlist(prmsBid);

        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Other methods">
    public void createNewParty() {

        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clearBidderSubmission();
            ClearPopUp();
            prmsBidderRegistration.setBidderType("Direct Offer");
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateServiceRegistration = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public String generateBidderRegistNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsBidderRegistration.getBidderNo();

        } else {
            PrmsBidderRegistration LastCheckNo = bidderRegistrationLocal.LastCheckListNo();
            if (LastCheckNo != null) {
                LastcheckListNo = LastCheckNo.getBidderRegId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "Bidder-No-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "Bidder-No-" + newBidNoLast + "/" + f.format(now);
            }
        }
        return newcheckListNo;

    }

    public void searchBidderReg() {
        prmsBidderRegistration.setPreparedBy(workFlow.getUserAccount());
        prmsBidderRegistrations = bidderRegistrationLocal.searchBidderReg(prmsBidderRegistration);

        recreatePrmsPrmsBidderRegistrationDataModel();

    }

    private String clearBidderSubmission() {
        prmsBidderRegistration = new PrmsBidderRegistration();
        prmsBid = null;
        prmsprmsBidderRegDetailmodel = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        return null;

    }

    public void editBidOfferDataTbl() {
        addOrModifyBundle = "Modify";
        selectStatue = 1;
        if (purchaseType.equalsIgnoreCase("Lot Base")) {
            prmsBidderRegDetail = prmsprmsBidderRegDetailmodel.getRowData();
            prmsSupplyProfile = prmsBidderRegDetail.getSuppId();
            prmsSupplyProfilLst = new ArrayList<>();
            prmsSupplyProfilLst.add(prmsSupplyProfile);
            prmsBid = prmsBidderRegistration.getBidId();
            prmsBidDetailsList = prmsBid.getPrmsBidDetailList();
            selectedLotName = prmsBidderRegDetail.getLotName().split(",");
            prmsBidderRegDetail.setLotName(prmsBidderRegDetail.getLotName());
        } else {
            prmsBidderRegDetail = prmsprmsBidderRegDetailmodel.getRowData();
            prmsSupplyProfile = prmsBidderRegDetail.getSuppId();
            prmsSupplyProfilLst = new ArrayList<>();
            prmsSupplyProfilLst.add(prmsSupplyProfile);
        }
        recreateprmsprmsBidderRegDetailmodel();
//        ClearPop();

    }

    public String addBidderDetForDirect() {
        purchaseType = prmsBid.getAwardType();
        if (selectStatue == 0) {
            if (actionPlnDetlCheck.contains(prmsBidderRegDetail.getSuppId().getVendorName())) {
                JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
                return null;
            } else {

                if (purchaseType.equalsIgnoreCase("Lot Base")) {
                    int size = selectedLotName.length;
                    int listSize = prmsBidDetailsList.size();
                    if (size != 0) {
                        String newLotName = "";
                        for (int i = 0; i < size; i++) {
                            if (newLotName.equals("")) {
                                newLotName = selectedLotName[i];
                            } else {
                                newLotName = newLotName + "," + selectedLotName[i];
                            }

                        }
                        prmsBidderRegDetail.setLotName(newLotName);
                        prmsBidderRegDetail.setSuppId(prmsSupplyProfile);
                        prmsBidderRegistration.addBidderDDEtial(prmsBidderRegDetail);
                        actionPlnDetlCheck.add(prmsBidderRegDetail.getSuppId().getVendorName());
                    }
                } else {
                    prmsBidderRegDetail.setSuppId(prmsSupplyProfile);
                    prmsBidderRegistration.addBidderDDEtial(prmsBidderRegDetail);
                    actionPlnDetlCheck.add(prmsBidderRegDetail.getSuppId().getVendorName());
                }
            }
        } else if (selectStatue == 1) {
            if (purchaseType.equalsIgnoreCase("Lot Base")) {
                int size = selectedLotName.length;
                int listSize = prmsBidDetailsList.size();
                if (size != 0) {
                    String newLotName = "";
                    for (int i = 0; i < size; i++) {
                        if (newLotName.equals("")) {
                            newLotName = selectedLotName[i];
                        } else {
                            newLotName = newLotName + "," + selectedLotName[i];
                        }

                    }
                    prmsBidderRegDetail.setLotName(newLotName);
                    prmsBidderRegDetail.setSuppId(prmsSupplyProfile);
                    prmsBidderRegistration.addBidderDDEtial(prmsBidderRegDetail);
                    actionPlnDetlCheck.add(prmsBidderRegDetail.getSuppId().getVendorName());
                }
            } else {
                prmsBidderRegDetail.setSuppId(prmsSupplyProfile);
                prmsBidderRegistration.addBidderDDEtial(prmsBidderRegDetail);
                actionPlnDetlCheck.add(prmsBidderRegDetail.getSuppId().getVendorName());
            }
        }
        recreateprmsprmsBidderRegDetailmodel();
        ClearPop();
        return null;
    }

    public String addBidderDetail() {
        if (actionPlnDetlCheck.contains(prmsBidderRegDetail.getSuppId())) {
            JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
            return null;
        } else {
            prmsBidderRegDetail.setSuppId(prmsSupplyProfile);
            prmsBidderRegDetail.setCashReceiptVoucher(fmsCashReceiptVoucher1);
            prmsBidderRegistration.addBidderDDEtial(prmsBidderRegDetail);
            actionPlnDetlCheck.add(prmsBidderRegDetail.getSuppId().getVendorName());

        }
        recreateprmsprmsBidderRegDetailmodel();
        ClearPopUp();
        return null;
    }

    public StreamedContent Download() throws Exception {
        System.out.println("When downloading");
        content = dataUpload.getFile(documentModel);
        System.out.println("You Got ==" + content);
        return content;

    }

    public void createNewServiceRegInfo() {
        //
        clearBidderSubmission();
        saveorUpdateBundle = "Save";

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            disableServiceAndWorkRdbtnWhenSearch = false;

        }
    }

    public void whenSeachClicked() {
        clearBidderSubmission();
        renderPnlCreateServiceRegistration = false;
        renderPnlManPage = true;
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateServiceRegistration = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void editBidsaleDataTbl() {
        prmsBidderRegDetail = prmsprmsBidderRegDetailmodel.getRowData();
        prmsSupplyProfile = prmsBidderRegDetail.getSuppId();
        fmsCashReceiptVoucher1 = prmsBidderRegDetail.getCashReceiptVoucher();
        prmsSupplyProfilLst = new ArrayList<>();
        prmsSupplyProfilLst.add(prmsSupplyProfile);

    }

    private void ClearPopUp() {
        addOrModifyBundle = "Add";
        prmsBidderRegDetail = null;
        prmsSupplyProfile = null;
        fmsCashReceiptVoucher1 = null;

    }

    private void ClearPop() {
        prmsBidderRegDetail = null;
        prmsSupplyProfile = null;
        selectedLotName = null;
    }

    public SelectItem[] getListOfVendorName() {
        return JsfUtil.getSelectItems(contractInformationBeanLocal.VendorName(), true);

    }

    public SelectItem[] bidReferenceNo() {
        return JsfUtil.getSelectItems(bidOpeningListLocal.BidNo(), true);

    }

    public SelectItem[] bidReferenceNoFromBidSale() {
        return JsfUtil.getSelectItems(bidderRegistrationLocal.BidNoFormBidSale(), true);

    }

    public void recreateprmsprmsBidderRegDetailmodel() {
        prmsprmsBidderRegDetailmodel = null;
        prmsprmsBidderRegDetailmodel = new ListDataModel(new ArrayList<>(prmsBidderRegistration.getPrmsBidderRegDetailList()));

    }

    public void recreatePrmsPrmsBidderRegistrationDataModel() {
        PrmsPrmsBidderRegistrationDataModel = null;
        PrmsPrmsBidderRegistrationDataModel = new ListDataModel(new ArrayList<>(getPrmsBidderRegistrations()));

    }

    public void removeContactPersonInfo() {
        int rowIndex = prmsprmsBidderRegDetailmodel.getRowIndex();
        PrmsBidderRegDetail prmsBidderRegDetail = prmsBidderRegistration.getPrmsBidderRegDetailList().get(rowIndex);
        prmsBidderRegistration.getPrmsBidderRegDetailList().remove(rowIndex);
        recreateprmsprmsBidderRegDetailmodel();
        if (saveorUpdateBundle.equals("Update")) {
            bidderRegistrationLocal.deleteContactPersonInfo(prmsBidderRegDetail);
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update method">
    //SAVE FOR DIRECT OFFER BIDDER REGIST
    public String saveForDirectOfferBidderRegist() {
        if (prmsprmsBidderRegDetailmodel.getRowCount() > 0) {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveForDirectOfferBidderRegist", dataset)) {
                    if (updateStatus == 0) {
                        try {

                            generateBidderRegistNo();
                            prmsBidderRegistration.setBidderNo(newcheckListNo);
                            wfPrmsProcessed.setBidderRegId(prmsBidderRegistration);
                            prmsBidderRegistration.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            prmsBidderRegistration.setStatus(Constants.PREPARE_VALUE);
                            prmsBidderRegistration.setPreparedBy(workFlow.getUserAccount());
                            bidderRegistrationLocal.create(prmsBidderRegistration);
//                     wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            JsfUtil.addSuccessMessage("Bidder information is registered!!");
                            clearBidderSubmission();
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                            return null;
                        }
                    } else {
                        try {
                            bidderRegistrationLocal.update(prmsBidderRegistration);

                            JsfUtil.addSuccessMessage("Bidder information is updated!!");
                            clearBidderSubmission();
                            saveorUpdateBundle = "Save";
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                        }

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
                ex.printStackTrace();
            }
        } else {
            JsfUtil.addFatalMessage("Bidder  Rigistration detail can not be empty !!");
        }
        return null;
    }
// </editor-fold>

}
