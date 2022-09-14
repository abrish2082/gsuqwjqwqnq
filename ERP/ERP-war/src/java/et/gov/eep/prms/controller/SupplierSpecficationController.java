package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.SupplierSpecficationBeanLocal;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsSupplierSpecificationDt;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.businessLogic.PreliminaryBeanLocal;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSuppSpecificationUpload;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.commons.io.FileUtils;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import securityBean.Constants;
import securityBean.Utility;
import securityBean.WorkFlow;

/**
 * *****************************************************************************
 *
 * @author pc
 * ****************************************************************************
 */
@Named(value = "supplierSpecficationController")
@ViewScoped
public class SupplierSpecficationController implements Serializable {

    @Inject
    SessionBean SessionBean;
    @Inject
    PrmsSuppSpecification LostObj;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private PrmsSupplierSpecificationDt prmsSupplierSpecificationDt;
    @Inject
    private PrmsSpecification prmsSpecification;
    @Inject
    private PrmsPreminilaryEvaluation prmsPreminilaryEvaluation;
    @Inject
    private PrmsBidSubmission prmsBidSubmission;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    private PrmsSuppSpecification prmsSuppSpecification;
    @Inject
    private PrmsPreminilaryEvalutionDt prmsPreminilaryEvalutionDt;
    @Inject
    private PrmsBid prmsBid;
    @Inject
    PrmsBidAmend prmsBidAmend;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
    private PrmsAward prmsAward;
    @Inject
    private PrmsContract prmsContract;
    @Inject
    private PrmsContractDetail prmsContractDetail;
    @Inject
    private PrmsBidDetail prmsBidDetail;
    @Inject
    private DataUpload papmsDataUpload;
    @Inject
    private PrmsSuppSpecificationUpload PapmsSuppSpecificationUpload;
    DataUpload dataUploadSelection;
    @Inject
    WorkFlow workFlow;

    @EJB
    PreliminaryBeanLocal premininaryBeanLocal;
    @EJB
    private SupplierSpecficationBeanLocal supplierSpecficationBeanLocal;
    @EJB
    SupplierSpecficationBeanLocal suppspecBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String selectedValue = "";
    private String addOrModifyBundle = "Add";
    String LastcheckListNo = "0";
    private String saveorUpdateBundle = "Save";
    String newcheckListNo;
    String refNo;
    private String selectOptPartyName;
    private String duplicattion = null;
    private String activeIndex;
    String logerName;
    private String userName;
    private String fileName;
    private String fileType;
    private byte[] byteFile;
    Set<String> addressCheck = new HashSet<>();
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    boolean renderDecision;
    private boolean isFileSelected = false;
    boolean isRenderCreate;
    int updateStatus = 0;
    List<PrmsBid> prmsBids;
    List<PrmsSpecification> prmsSpecificationList;
    List<PrmsSupplyProfile> supplierlist = new ArrayList<>();
    List<PrmsBidSubmission> prmsBidSubmissionsList;
    List<PrmsPreminilaryEvaluation> bidList;
    List<PrmsAward> prmsAwardsList;
    List<PrmsContract> prmContractsList;
    List<PrmsSuppSpecificationUpload> prmsSuppUploadList;
    List<PrmsContractDetail> prmsContractDetailList;
    List<PrmsSuppSpecification> prmsSuppSpecifications;
    List<PrmsBid> prmsBidsList;
    List<PrmsBidAmend> prmsBidAmendsList;
    private List<PrmsContractAmendment> contractListFromAmendment;
    List<PrmsSpecification> prmsSpecificationsList;
    List<PrmsBidDetail> prmsBidDetailsList;
    List<PrmsBid> bids;
    List<PrmsSuppSpecification> supplierSupSearchParameterLst;
    private List<PrmsSuppSpecification> SuppSpecificationLis1;
    Set<MmsItemRegistration> checkEvaluation = new HashSet<>();
    private PrmsSuppSpecification selectSupplierSpecfication;
    DataModel<PrmsSupplierSpecificationDt> prmsSupplierSpecificationDtsModel;
    private DataModel<PrmsSuppSpecification> prmsSuppSpecificationsModel;
    private List<PrmsSupplyProfile> prmsSupplyProfilLst;
    int requestNotificationCounter = 0;
    int saveStatus = 0;
    private int selectedRowIndex;
    int uploadedDocId;
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    @Inject
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    @Inject
    DataUpload dataUpload;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SupplierSpecficationController() {
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public PrmsSuppSpecification getSelectSupplierSpecfication() {
        return selectSupplierSpecfication;
    }

    public void setSelectSupplierSpecfication(
            PrmsSuppSpecification selectSupplierSpecfication) {
        this.selectSupplierSpecfication = selectSupplierSpecfication;
    }

    public List<PrmsSuppSpecification> getSuppSpecificationLis1() {
        if (SuppSpecificationLis1 == null) {
            SuppSpecificationLis1 = new ArrayList<>();
        }
        return SuppSpecificationLis1;
    }

    public void setSuppSpecificationLis1(
            List<PrmsSuppSpecification> SuppSpecificationLis1) {
        this.SuppSpecificationLis1 = SuppSpecificationLis1;
    }

    public String getNewcheckListNo() {
        return newcheckListNo;
    }

    public void setNewcheckListNo(String newcheckListNo) {
        this.newcheckListNo = newcheckListNo;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public String getLastcheckListNo() {
        return LastcheckListNo;
    }

    public void setLastcheckListNo(String LastcheckListNo) {
        this.LastcheckListNo = LastcheckListNo;
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

    public PrmsPreminilaryEvalutionDt getPrmsPreminilaryEvalutionDt() {
        if (prmsPreminilaryEvalutionDt == null) {
            prmsPreminilaryEvalutionDt = new PrmsPreminilaryEvalutionDt();
        }
        return prmsPreminilaryEvalutionDt;
    }

    public void setPrmsPreminilaryEvalutionDt(PrmsPreminilaryEvalutionDt prmsPreminilaryEvalutionDt) {
        this.prmsPreminilaryEvalutionDt = prmsPreminilaryEvalutionDt;
    }

    public DataUpload getPapmsDataUpload() {
        if (papmsDataUpload == null) {
            papmsDataUpload = new DataUpload();
        }
        return papmsDataUpload;
    }

    public void setPapmsDataUpload(DataUpload papmsDataUpload) {
        this.papmsDataUpload = papmsDataUpload;
    }

    public PrmsSuppSpecificationUpload getPapmsSuppSpecificationUpload() {

        if (PapmsSuppSpecificationUpload == null) {
            PapmsSuppSpecificationUpload = new PrmsSuppSpecificationUpload();
        }

        return PapmsSuppSpecificationUpload;
    }

    public void setPapmsSuppSpecificationUpload(PrmsSuppSpecificationUpload PapmsSuppSpecificationUpload) {
        this.PapmsSuppSpecificationUpload = PapmsSuppSpecificationUpload;
    }

    public DataUpload getDataUploadSelection() {
        return dataUploadSelection;
    }

    public void setDataUploadSelection(DataUpload dataUploadSelection) {
        this.dataUploadSelection = dataUploadSelection;
    }

    public List<PrmsPreminilaryEvaluation> getBidList() {
        bidList = supplierSpecficationBeanLocal.getSupplierList();

        return bidList;
    }

    public void setBidList(List<PrmsPreminilaryEvaluation> bidList) {
        this.bidList = bidList;
    }

    public PrmsPreminilaryEvaluation getPrmsPreminilaryEvaluation() {
        if (prmsPreminilaryEvaluation == null) {
            prmsPreminilaryEvaluation = new PrmsPreminilaryEvaluation();
        }
        return prmsPreminilaryEvaluation;
    }

    public void setPrmsPreminilaryEvaluation(PrmsPreminilaryEvaluation prmsPreminilaryEvaluation) {
        this.prmsPreminilaryEvaluation = prmsPreminilaryEvaluation;
    }

    public List<PrmsSupplyProfile> getSupplierlist() {
        return supplierlist;
    }

    public void setSupplierlist(List<PrmsSupplyProfile> supplierlist) {
        this.supplierlist = supplierlist;
    }

    public List<PrmsBidSubmission> getPrmsBidSubmissionsList() {
        if (prmsBidSubmissionsList == null) {
            prmsBidSubmissionsList = new ArrayList<>();
        }
        return prmsBidSubmissionsList;
    }

    public void setPrmsBidSubmissionsList(List<PrmsBidSubmission> prmsBidSubmissionsList) {
        this.prmsBidSubmissionsList = prmsBidSubmissionsList;
    }

    public PrmsBidSubmission getPrmsBidSubmission() {
        if (prmsBidSubmission == null) {
            prmsBidSubmission = new PrmsBidSubmission();
        }
        return prmsBidSubmission;
    }

    public void setPrmsBidSubmission(PrmsBidSubmission prmsBidSubmission) {
        this.prmsBidSubmission = prmsBidSubmission;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public List<PrmsSpecification> getPrmsSpecificationList() {
        if (prmsSpecificationList == null) {
            prmsSpecificationList = new ArrayList<>();
        }
        return prmsSpecificationList;
    }

    public SelectItem[] getMateName() {
        return JsfUtil.getSelectItems(this.getPrmsSpecificationList(), true);
    }

    public void setPrmsSpecificationList(
            List<PrmsSpecification> prmsSpecificationList) {

        this.prmsSpecificationList = prmsSpecificationList;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
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

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {

        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ArrayDataModel<>();
        }

        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(
            DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {

        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    @PostConstruct
    public void init() {

        SuppSpecificationLis1 = supplierSpecficationBeanLocal.searchSuppSpecification();
        setLogerName(SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(workFlow.getUserName());
//        Date currentDate = Calendar.getInstance().getTime();
//        wfPrmsProcessed.setProcessedOn(currentDate);

        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRenderCreate = true;
        }
        if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
        if (workFlow.isCheckStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
    }

    public PrmsSupplierSpecificationDt getPrmsSupplierSpecificationDt() {
        if (prmsSupplierSpecificationDt == null) {
            prmsSupplierSpecificationDt = new PrmsSupplierSpecificationDt();
        }
        return prmsSupplierSpecificationDt;
    }

    public void setPrmsSupplierSpecificationDt(
            PrmsSupplierSpecificationDt prmsSupplierSpecificationDt) {

        this.prmsSupplierSpecificationDt = prmsSupplierSpecificationDt;
    }

    public PrmsSpecification getPrmsSpecification() {

        if (prmsSpecification == null) {
            prmsSpecification = new PrmsSpecification();
        }

        return prmsSpecification;
    }

    public void setPrmsSpecification(PrmsSpecification prmsSpecification) {
        this.prmsSpecification = prmsSpecification;
    }

    public PrmsSuppSpecification getPrmsSuppSpecification() {

        if (prmsSuppSpecification == null) {
            prmsSuppSpecification = new PrmsSuppSpecification();
        }

        return prmsSuppSpecification;
    }

    public void setPrmsSuppSpecification(PrmsSuppSpecification prmsSuppSpecification) {
        this.prmsSuppSpecification = prmsSuppSpecification;
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

    public PrmsBidAmend getPrmsBidAmend() {
        if (prmsBidAmend == null) {
            prmsBidAmend = new PrmsBidAmend();
        }
        return prmsBidAmend;
    }

    public void setPrmsBidAmend(PrmsBidAmend prmsBidAmend) {
        this.prmsBidAmend = prmsBidAmend;
    }

    /**
     * @return the prmsContractAmendment
     */
    public PrmsContractAmendment getPrmsContractAmendment() {
        if (prmsContractAmendment == null) {
            prmsContractAmendment = new PrmsContractAmendment();
        }
        return prmsContractAmendment;
    }

    /**
     * @param prmsContractAmendment the prmsContractAmendment to set
     */
    public void setPrmsContractAmendment(PrmsContractAmendment prmsContractAmendment) {
        this.prmsContractAmendment = prmsContractAmendment;
    }

    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
    }

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

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
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

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    public List<PrmsBid> getPrmsBids() {
        if (prmsBids == null) {
            prmsBids = new ArrayList<>();
        }
        return prmsBids;
    }

    public void setPrmsBids(List<PrmsBid> prmsBids) {
        this.prmsBids = prmsBids;
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

    public MmsItemRegistration getMmsItemRegistration() {
        if (mmsItemRegistration == null) {
            mmsItemRegistration = new MmsItemRegistration();
        }
        return mmsItemRegistration;
    }

    public void setMmsItemRegistration(MmsItemRegistration mmsItemRegistration) {
        this.mmsItemRegistration = mmsItemRegistration;
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

    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;

    public DataModel<PrmsSupplierSpecificationDt>
            getPrmsSupplierSpecificationDtsModel() {

        if (prmsSupplierSpecificationDtsModel == null) {
            prmsSupplierSpecificationDtsModel = new ArrayDataModel<>();
        }

        return prmsSupplierSpecificationDtsModel;
    }

    public void setPrmsSupplierSpecificationDtsModel(
            DataModel<PrmsSupplierSpecificationDt> prmsSupplierSpecificationDtsModel) {

        this.prmsSupplierSpecificationDtsModel = prmsSupplierSpecificationDtsModel;
    }

    public DataModel<PrmsSuppSpecification> getPrmsSuppSpecificationsModel() {
        if (prmsSuppSpecificationsModel == null) {
            prmsSuppSpecificationsModel = new ListDataModel(new ArrayList<>(getPrmsSuppSpecifications()));
        }
        return prmsSuppSpecificationsModel;
    }

    public void setPrmsSuppSpecificationsModel(DataModel<PrmsSuppSpecification> prmsSuppSpecificationsModel) {
        this.prmsSuppSpecificationsModel = prmsSuppSpecificationsModel;
    }

    public List<PrmsSuppSpecification> getPrmsSuppSpecifications() {
        if (prmsSuppSpecifications == null) {
            prmsSuppSpecifications = new ArrayList<>();
        }
        return prmsSuppSpecifications;
    }

    public void setPrmsSuppSpecifications(List<PrmsSuppSpecification> prmsSuppSpecifications) {
        this.prmsSuppSpecifications = prmsSuppSpecifications;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public List<PrmsSuppSpecification> getSupplierSupSearchParameterLst() {
        if (supplierSupSearchParameterLst == null) {
            supplierSupSearchParameterLst = new ArrayList<>();
            supplierSupSearchParameterLst = supplierSpecficationBeanLocal.getParamNameList();
        }
        return supplierSupSearchParameterLst;
    }

    public void setSupplierSupSearchParameterLst(List<PrmsSuppSpecification> supplierSupSearchParameterLst) {
        this.supplierSupSearchParameterLst = supplierSupSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsSuppSpecification.setColumnName(event.getNewValue().toString());
            prmsSuppSpecification.setColumnValue(null);
        }
    }

    public void createNewParty() {

        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
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

//    public void newBtnAction() {
//        renderPnlManPage = false;
//        renderPnlCreateParty = true;
//    }
//
//    public void searchBtnAction() {
//        renderPnlManPage = true;
//        renderPnlCreateParty = false;
//    }
    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = prmsSuppSpecificationsModel.getRowIndex();
        prmsSuppSpecification = prmsSuppSpecifications.get(rowIndex);
        recreateprmsSuppSpecificationsModel();
        recreateprmsSupplierSpecificationDtsModel();
    }

    public void onContactRowCancel(RowEditEvent event) {

    }

    public void deleteSuppSpecDetailList() {
        int rowIndex = prmsSupplierSpecificationDtsModel.getRowIndex();
        PrmsSupplierSpecificationDt prmsSupplierSpecificationDetail = prmsSuppSpecification.getPrmsSupplierSpecificationDtList().get(rowIndex);
        prmsSuppSpecification.getPrmsSupplierSpecificationDtList().remove(rowIndex);
        recreateprmsSupplierSpecificationDtsModel();
        if (saveorUpdateBundle.equals("Update")) {
            supplierSpecficationBeanLocal.deleteSuppSpecDetailList(prmsSupplierSpecificationDetail);
        }
    }

    public void recreateprmsSuppSpecificationsModel() {
        prmsSuppSpecificationsModel = null;
        prmsSuppSpecificationsModel = new ListDataModel(new ArrayList<>(getPrmsSuppSpecifications()));
    }

    public void recreateprmsSupplierSpecificationDtsModel() {
        prmsSupplierSpecificationDtsModel = null;
        prmsSupplierSpecificationDtsModel = new ListDataModel(new ArrayList<>(prmsSuppSpecification.getPrmsSupplierSpecificationDtList()));
    }

    private void clearprmsSuppSpecification() {
        prmsSuppSpecification = null;
        prmsSupplierSpecificationDtsModel = null;
        prmsBid = null;
        prmsSupplyProfile = null;
        prmsAward = null;
        prmsContract = null;
        mmsItemRegistration = null;
        prmsContractDetail = null;
        wfPrmsProcessed = null;
        newDoclist = new ArrayList<>();
        savedDocIds = new ArrayList<>();
        docValueModel = null;
    }

    public void removeContactPersonInfo() {
        int rowIndex = prmsSupplierSpecificationDtsModel.getRowIndex();
        PrmsSupplierSpecificationDt prmsSupplSpecificationDt = prmsSuppSpecification.getPrmsSupplierSpecificationDtList().get(rowIndex);
        prmsSuppSpecification.getPrmsSupplierSpecificationDtList().remove(rowIndex);
        prmsSupplierSpecificationDt = prmsSupplierSpecificationDtsModel.getRowData();
        selectedRowIndex = prmsSupplierSpecificationDtsModel.getRowIndex();
        recreateprmsSupplierSpecificationDtsModel();
        if (saveorUpdateBundle.equals("Update")) {
            supplierSpecficationBeanLocal.deleteSuppSpecDetailList(prmsSupplSpecificationDt);
            prmsSupplierSpecificationDt = prmsSupplierSpecificationDtsModel.getRowData();
            selectedRowIndex = prmsSupplierSpecificationDtsModel.getRowIndex();
        }
    }

    public void edit() {

        prmsSupplierSpecificationDt
                = prmsSupplierSpecificationDtsModel.getRowData();
        selectedRowIndex = prmsSupplierSpecificationDtsModel.getRowIndex();
    }

    public void handleDecision(ValueChangeEvent eve) {
        if (eve.getNewValue() != null) {
            selectedValue = eve.getNewValue().toString();
        }
    }

    public void saveSupplierSpecification() {

        try {
            AAA securityService = new AAA();
            IAdministration security
                    = securityService.getMetadataExchangeHttpBindingIAdministration();//  String systemBundle = "cfg/securityServer";
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(),
                    "saveSupplierSpecification", dataset)) {
                PrmsSuppSpecificationUpload suppLocal = new PrmsSuppSpecificationUpload();

                try {
                    if (saveorUpdateBundle.equals("Save")) {
                        Date currentDate = Calendar.getInstance().getTime();
                        setPrmsSuppSpecification(prmsSuppSpecification);
                        generateCheckListNo();
                        prmsSuppSpecification.setSuppSpecNo(newcheckListNo);
                        prmsSuppSpecification.setSuppId(prmsSupplyProfile);
                        prmsSuppSpecification.setPreparedBy(String.valueOf(SessionBean.getUserId()));  // Date currentDate = Calendar.getInstance().getTime();
                        prmsSuppSpecification.setStatus(Constants.PREPARE_VALUE);
                        prmsSuppSpecification.setDateRigistered(currentDate);
                        wfPrmsProcessed.setDecision(String.valueOf(prmsSuppSpecification.getStatus()));//-
                        wfPrmsProcessed.setSuppSpecId(prmsSuppSpecification);
//                        wfPrmsProcessed.setProcessedOn(currentDate);
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(fileType);
                        prmsLuDmArchive.setUploadFile(byteFile);
                        prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        prmsSuppSpecification.setDocumentId(prmsLuDmArchive);
                        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());

                        for (int i = 0; i < savedDocIds.size(); i++) {
                            suppLocal = new PrmsSuppSpecificationUpload();
                            suppLocal.setDocumentId(savedDocIds.get(i));
                            prmsSuppSpecification.add(suppLocal);
                        }

                        supplierSpecficationBeanLocal.create(prmsSuppSpecification);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);

                        JsfUtil.addSuccessMessage("Supplier Specification is Registred!!");
                        clearprmsSuppSpecification();

                    } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {
                        for (int i = 0; i < savedDocIds.size(); i++) {
                            suppLocal = new PrmsSuppSpecificationUpload();
                            suppLocal.setDocumentId(savedDocIds.get(i));
                            prmsSuppSpecification.add(suppLocal);
                        }
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(fileType);
                        prmsLuDmArchive.setUploadFile(byteFile);
                        prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        prmsSuppSpecification.setDocumentId(prmsLuDmArchive);
                        supplierSpecficationBeanLocal.update(prmsSuppSpecification);
                        JsfUtil.addSuccessMessage("Supplier Specification is successfuly Updated!!");
                        saveorUpdateBundle = "Save";
                        clearprmsSuppSpecification();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName,
                        String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                //..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SelectItem[] bidNumberList() {
        return JsfUtil.getSelectItems(prmsSpecificationList, true);
    }

    public List<PrmsSupplyProfile> getSuppName() {
        return vendorRegBeanLocal.getVondorName();
    }

    public SelectItem[] getSuppNameee() {
        return JsfUtil.getSelectItems(vendorRegBeanLocal.getVondorName(), true);
    }

    public void searchSupplierSpecification() {
        prmsSuppSpecification.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
        prmsSuppSpecifications = supplierSpecficationBeanLocal.searchSuppSpecification(prmsSuppSpecification);
        recreateprmsSuppSpecificationsModel();
        prmsSuppSpecification = new PrmsSuppSpecification();
    }

    public String addSuppSpecDetail() {

        if (checkEvaluation.contains(prmsContractDetail.getItemId())) {
            JsfUtil.addFatalMessage("Duplicated!. Try again");
            return null;
        } else {
            prmsSuppSpecification.addSuppSpecDetail(prmsSupplierSpecificationDt);
            checkEvaluation.add(prmsContractDetail.getItemId());
            recreateprmsSupplierSpecificationDtsModel();
            ClearPopUp();
        }

        return null;
    }

    private void ClearPopUp() {

        prmsSupplierSpecificationDt = null;
        prmsContractDetail = null;
        addOrModifyBundle = "Add";
    }

    public void addBidderDetail() {

        if (!checkEvaluation.contains(prmsContractDetail.getItemId())) {
            prmsContractDetail.getItemId();
            prmsSupplierSpecificationDt.setMaterialId(prmsContractDetail.getItemId());
            prmsSuppSpecification.addSuppSpecDetail(prmsSupplierSpecificationDt);
            checkEvaluation.add(prmsSupplierSpecificationDt.getMaterialId());
            prmsSupplierSpecificationDt = null;
            recreateprmsSupplierSpecificationDtsModel();
        }

        ClearPopUp();
    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = prmsSupplierSpecificationDtsModel.getRowIndex();
        PrmsSupplierSpecificationDt contactPerson = new PrmsSupplierSpecificationDt();
        PrmsSupplierSpecificationDt comContPerson = (PrmsSupplierSpecificationDt) event.getObject();

        boolean found = false;
        for (int i = 0; i < prmsSuppSpecification.getPrmsSupplierSpecificationDtList().size(); i++) {

            if (i != rowIndex) {
                if (prmsSuppSpecification.getPrmsSupplierSpecificationDtList().get(i).getUnitMeasure().equals(comContPerson.getUnitMeasure())
                        && prmsSuppSpecification.getPrmsSupplierSpecificationDtList().get(i).getSupplierSpcification().equals(comContPerson.getSupplierSpcification())) {
                    found = true;
                    break;
                }
            }
        }

        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            comContPerson.setSupplierSpcification(null);
            prmsSuppSpecification.getPrmsSupplierSpecificationDtList().set(rowIndex, comContPerson);
            recreateprmsSupplierSpecificationDtsModel();
        } else {
            prmsSuppSpecification.getPrmsSupplierSpecificationDtList().set(rowIndex, comContPerson);
            recreateprmsSupplierSpecificationDtsModel();
        }
    }

    public List<PrmsSpecification> bidNolist() {
        setPrmsSpecificationList(supplierSpecficationBeanLocal.getBidNoSep());
        return prmsSpecificationList;
    }

    public List<PrmsBid> getPrmsBidsList() {
        if (prmsBidsList == null) {
            prmsBidsList = new ArrayList<>();
        }
        return prmsBidsList;
    }

    public void setPrmsBidsList(List<PrmsBid> prmsBidsList) {
        this.prmsBidsList = prmsBidsList;
    }

    public List<PrmsBidAmend> getPrmsBidAmendsList() {
        if (prmsBidAmendsList == null) {
            prmsBidAmendsList = new ArrayList<>();
        }
        return prmsBidAmendsList;
    }

    public void setPrmsBidAmendsList(List<PrmsBidAmend> prmsBidAmendsList) {
        this.prmsBidAmendsList = prmsBidAmendsList;
    }

    /**
     * @return the contractListFromAmendment
     */
    public List<PrmsContractAmendment> getContractListFromAmendment() {
        if (contractListFromAmendment == null) {
            contractListFromAmendment = new ArrayList<>();
        }
        return contractListFromAmendment;
    }

    /**
     * @param contractListFromAmendment the contractListFromAmendment to set
     */
    public void setContractListFromAmendment(List<PrmsContractAmendment> contractListFromAmendment) {
        this.contractListFromAmendment = contractListFromAmendment;
    }

    List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDtList;

    public List<PrmsPreminilaryEvalutionDt> getPrmsPreminilaryEvalutionDtList() {
        if (prmsPreminilaryEvalutionDtList == null) {
            prmsPreminilaryEvalutionDtList = new ArrayList<>();
        }
        return prmsPreminilaryEvalutionDtList;
    }

    public void setPrmsPreminilaryEvalutionDtList(List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDtList) {
        this.prmsPreminilaryEvalutionDtList = prmsPreminilaryEvalutionDtList;
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

    public List<PrmsSpecification> getPrmsSpecificationsList() {
        return prmsSpecificationsList;
    }

    public void setPrmsSpecificationsList(List<PrmsSpecification> prmsSpecificationsList) {
        this.prmsSpecificationsList = prmsSpecificationsList;
    }

    public SelectItem[] getBidNo1() {
        return JsfUtil.getSelectItems(supplierSpecficationBeanLocal.getSupplierList(), true);
    }

    public void getValueofItem(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            String matName = event.getNewValue().toString();
            prmsSpecificationList = supplierSpecficationBeanLocal.getAwardDetailList(matName);
        }
    }

    public List<PrmsBid> getBids() {
        bids = supplierSpecficationBeanLocal.getBidNoList();
        return bids;
    }

    public void setBids(List<PrmsBid> bids) {
        this.bids = bids;
    }

    public List<PrmsBid> getBidNo() {
        return prmsBids;
    }

    public int getRequestNotificationCounter() {

        SuppSpecificationLis1 = supplierSpecficationBeanLocal.searchSuppSpecification();
        requestNotificationCounter = SuppSpecificationLis1.size();

        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {

        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void RequestListChange(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            prmsSuppSpecification = (PrmsSuppSpecification) event.getNewValue();
            populateWorkFlow();
        }
    }

    public void populateWorkFlow() {

        renderPnlCreateParty = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        prmsBid = prmsSuppSpecification.getBidId();
        prmsSupplyProfile = prmsSuppSpecification.getSuppId();
        prmsSupplyProfilLst = supplierSpecficationBeanLocal.getsupplierlist(prmsBid);
        prmsAwardsList = prmsSupplyProfile.getPrmsAwardList();
        wfPrmsProcessed.setProcessedOn(prmsSuppSpecification.getDateRigistered());
        if (prmsSuppSpecification.getDocumentId() != null) {
//            docId = prmsLcRigistration.getDocumentId();
            prmsLuDmArchive = prmsSuppSpecification.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
//            doLst.add(docId);
//            getfile();
        }
        recreateDataModel();
        saveStatus = 1;
        recreatworkflow();
    }

    public void rowSelect(SelectEvent event) {

        prmsSuppSpecification = (PrmsSuppSpecification) event.getObject();
        renderpnlToSearchPage = true;
//       prmsContract.setSuppId(prmsSuppSpecification.getSuppId());
        prmsContract = supplierSpecficationBeanLocal.getcontNumber(prmsSuppSpecification.getSuppId());
        recreateprmsSupplierSpecificationDtsModel();
        populateWorkFlow();
        recreatworkflow();
        recreateDmsDataModel();
    }

    public String generateCheckListNo() {

        PrmsSuppSpecification LastCheckNo = supplierSpecficationBeanLocal.LastSpecficationNo();
        if (LastCheckNo != null) {
            LastcheckListNo = LastCheckNo.getSuppSpecId().toString();
        }
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        int newcheckListN = 0;

        if (LastcheckListNo.equals("0")) {
            newcheckListN = 1;
            newcheckListNo = "SUPSPEC-" + newcheckListN + "/" + f.format(now);
        } else {
            String[] lastInspNos = LastcheckListNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newcheckListN = Integer.parseInt(lastDatesPaterns[0]);
            newcheckListN = newcheckListN + 1;
            newcheckListNo = "SUPSPEC-" + newcheckListN + "/" + f.format(now);
        }
        return newcheckListNo;
    }

    public String generateSpecficationNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsSuppSpecification.getSuppSpecNo();
        } else {
            PrmsSuppSpecification lastSpecficationNo = supplierSpecficationBeanLocal.LastSpecficationNo();
            if (lastSpecficationNo != null) {
                LastcheckListNo = lastSpecficationNo.getSuppSpecId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "SUPSPEC-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "SUPSPEC-" + newBidNoLast + "/" + f.format(now);
            }
        }

        return newcheckListNo;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public SelectItem[] itemList() {
        return JsfUtil.getSelectItems(supplierSpecficationBeanLocal.itemList(), true);
    }

    public SelectItem[] bidReferenceNo() {
        return JsfUtil.getSelectItems(supplierSpecficationBeanLocal.BidNoFormAward(), true);
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfilLst() {
        if (prmsSupplyProfilLst == null) {
            prmsSupplyProfilLst = new ArrayList<>();
        }
        prmsSupplyProfilLst = supplierSpecficationBeanLocal.findSuppliers();
        return prmsSupplyProfilLst;
    }

    public void setPrmsSupplyProfilLst(List<PrmsSupplyProfile> prmsSupplyProfilLst) {
        this.prmsSupplyProfilLst = prmsSupplyProfilLst;
    }

    public PrmsAward getPrmsAward() {
        return prmsAward;
    }

    public void setPrmsAward(PrmsAward prmsAward) {
        this.prmsAward = prmsAward;
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

    public List<PrmsAward> getPrmsAwardsList() {
        return prmsAwardsList;
    }

    public void setPrmsAwardsList(List<PrmsAward> prmsAwardsList) {
        this.prmsAwardsList = prmsAwardsList;
    }

    public List<PrmsContract> getPrmContractsList() {
        if (prmContractsList == null) {
            prmContractsList = new ArrayList<>();
        }
        return prmContractsList;
    }

    public void setPrmContractsList(List<PrmsContract> prmContractsList) {
        this.prmContractsList = prmContractsList;
    }

    public List<PrmsSuppSpecificationUpload> getPrmsSuppUploadList() {
        return prmsSuppUploadList;
    }

    public void setPrmsSuppUploadList(List<PrmsSuppSpecificationUpload> prmsSuppUploadList) {
        this.prmsSuppUploadList = prmsSuppUploadList;
    }

    public PrmsContractDetail getPrmsContractDetail() {

        if (prmsContractDetail == null) {
            prmsContractDetail = new PrmsContractDetail();
        }
        return prmsContractDetail;
    }

    public void setPrmsContractDetail(PrmsContractDetail prmsContractDetail) {
        this.prmsContractDetail = prmsContractDetail;
    }

    public List<PrmsContractDetail> getPrmsContractDetailList() {
        if (prmsContractDetailList == null) {
            prmsContractDetailList = new ArrayList<>();
        }
        return prmsContractDetailList;
    }

    public void setPrmsContractDetailList(
            List<PrmsContractDetail> prmsContractDetailList) {
        this.prmsContractDetailList = prmsContractDetailList;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public void editBidOfferDataTbl() {
        addOrModifyBundle = "Modify";
        prmsSupplierSpecificationDt = prmsSupplierSpecificationDtsModel.getRowData();
        prmsContractDetailList = supplierSpecficationBeanLocal.getitemNameFromContract(prmsSupplyProfile);
        mmsItemRegistration = prmsSupplierSpecificationDt.getMaterialId();
        prmsContractDetail.setItemId(mmsItemRegistration);
    }

    // <editor-fold defaultstate="collapsed" desc="DMS service ">
    List<PrmsSuppSpecificationUpload> documentList;
    StreamedContent file;
    DocumentModel documentModel = new DocumentModel();
    DataModel<PrmsSuppSpecificationUpload> fileUploadDataModel;// Here was the Error
    DataModel<DocumentModel> docValueModel;
    List<Integer> savedDocIds = new ArrayList<>();
    DmsHandler dmsHandler = new DmsHandler();
    List<DocumentModel> newDoclist = new ArrayList<DocumentModel>();

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void onRowSelect(SelectEvent event) {
        //documentModel = (DocumentModel) event.getObject();
        prmsLuDmArchive = (PrmsLuDmArchive) event.getObject();
        isFileSelected = true;
    }

    public DataModel<PrmsSuppSpecificationUpload> getFileUploadDataModel() {
        return fileUploadDataModel;
    }

    public void setFileUploadDataModel(
            DataModel<PrmsSuppSpecificationUpload> fileUploadDataModel) {
        this.fileUploadDataModel = fileUploadDataModel;
    }

    public void recreateDmsDataModel() {

//        newDoclist.clear();
//        DmsHandler dmsHandler = new DmsHandler();
//        DocList docList = new DocList();
//        List<String> docId = new ArrayList<>();
//
//        for (int i = 0; i < prmsSuppSpecification.getPrmsSuppSpecificationUploadList().size(); i++) {
//            docId.add(prmsSuppSpecification.getPrmsSuppSpecificationUploadList().get(i).getDocumentId().toString());
//        }
//
//        docList.setDocIds(docId);
//        DocList docListNew = dmsHandler.getDocumentsById(docList);
//
//        if (docListNew != null) {
//            newDoclist = docListNew.getDocList();
//            docValueModel = new ListDataModel(docListNew.getDocList());
//        }
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public void uploadListener(FileUploadEvent event) {
        InputStream inputStream = null;
        try {
//            documentModel = new DocumentModel();
//            documentModel.setDocFormat(event.getFile().getFileName().split("\\.")[1]);
//            documentModel.setName(event.getFile().getFileName().split("\\.")[0]);
//            documentModel.setUserId(Long.valueOf("2"));
//            String categoryBundle = "et/gov/eep/commonApplications/securityServer";
//            documentModel.setApp(Utility.getBundleValue(categoryBundle, "categoryName"));
//            documentModel.setCreater("Tehetena");
//            File fileDoc = new File(event.getFile().getFileName());
//            FileUtils.writeByteArrayToFile(fileDoc, dmsHandler.extractByteArray1(event.getFile().getInputstream()));
//            documentModel.setDate(et.gov.eep.mms.controller.StringDateManipulation.todayInEC());
//            documentModel.setFile(fileDoc);
//            documentModel.setByteFile(dmsHandler.extractByteArray1(event.getFile().getInputstream()));
//            int savedDocId = dmsHandler.saveDocument(documentModel);
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            inputStream = event.getFile().getInputstream();
            byteFile = papmsDataUpload.extractByteArray(inputStream);
            if (byteFile != null) {
                System.out.println("byte File is  " + byteFile);
//                savedDocIds.add(savedDocId);
//                newDoclist.add(documentModel);
//                setDocValueModel(new ListDataModel(newDoclist));
                JsfUtil.addSuccessMessage("Upload Successfully File Name " + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            System.out.println("Selected Dile Name is " + prmsLuDmArchive.getFileName());
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Pls Select File Name");
        }
//        if (documentModel != null) {
//            File fileDoc = new File(""
//                    + documentModel.getName() + "."
//                    + documentModel.getDocFormat());
//            FileUtils.writeByteArrayToFile(fileDoc, documentModel.getByteFile());
//            InputStream input = new FileInputStream(fileDoc);
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            file = new DefaultStreamedContent(input, externalContext.getMimeType(fileDoc.getName()), documentModel.getName() + "." + documentModel.getDocFormat());
//            return file;
//        }
        return file;
    }

    public void remove(DocumentModel document) {
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDmsDataModel();
    }

    public void recreateDataModel() {
        fileUploadDataModel = null;
        fileUploadDataModel = new ListDataModel(new ArrayList(
                prmsSuppSpecification.getPrmsSuppSpecificationUploadList()));
    }

    public void onRowSelect1(SelectEvent event) {
        PapmsSuppSpecificationUpload = (PrmsSuppSpecificationUpload) event.getObject();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="WorkFlow">
    public void recreatworkflow() {
        wfPrmsProcessedDModel = null;
        wfPrmsProcessedDModel = new ListDataModel(new ArrayList(prmsSuppSpecification.getPrmsWorkFlowProccedList()));
    }

    public void saveWorkFlowInformation() {
        wfPrmsProcessed.setSuppSpecId(prmsSuppSpecification);
        wfPrmsProcessed.setDecision(String.valueOf(prmsSuppSpecification.getStatus()));
        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="New Page">// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getter Setter">// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Value Change Listener">
    public void whenBidNoChanged(ValueChangeEvent changed) {

        if (changed.getNewValue().toString().isEmpty()) {
            prmsBid = (PrmsBid) changed.getNewValue();
        }
    }

    public void changeEventSuppNamee(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            prmsBid = (PrmsBid) event.getNewValue();
            prmsBidAmendsList = supplierSpecficationBeanLocal.checkAsBidAmended(prmsBid);
            prmsSuppSpecification.setBidId(prmsBid);
            prmsSupplyProfilLst = supplierSpecficationBeanLocal.getsupplierlist(prmsBid);
            prmsAwardsList = supplierSpecficationBeanLocal.getAwardNo(prmsBid);
        }
    }

    public void changesuplyName(ValueChangeEvent event) {

        if (null != event.getNewValue()) {

            prmsBid = (PrmsBid) event.getNewValue();
            prmsSuppSpecification.setBidId(prmsBid);
            prmsSupplyProfilLst = supplierSpecficationBeanLocal.getsupplierlist(prmsBid);
            prmsAwardsList = supplierSpecficationBeanLocal.getAwardNo(prmsBid);
        }
    }

    public void changeSppName(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsSupplyProfile = (PrmsSupplyProfile) event.getNewValue();
            // eepBidReg = (PrmsBid) event.getNewValue();     
            prmsContract = supplierSpecficationBeanLocal.getcontNumber(prmsSupplyProfile);
            prmsContract.setBidId(prmsBid);
            prmsContractDetailList = supplierSpecficationBeanLocal.getitemNameFromContract(prmsContract);
            System.out.println("size " + prmsContractDetailList.size());
            if (getContractListFromAmendment().size() > 0) {
                System.out.println(">0");
                setPrmsContractAmendment(supplierSpecficationBeanLocal.getContractAmendedInfoByContractId(prmsContract));
                prmsContract.setSuppId(getPrmsContractAmendment().getSuppId());
                prmsContract.setPaymenttype(getPrmsContractAmendment().getPaymenttype());
                prmsContract.setContractamount(getPrmsContractAmendment().getContractamount());
                prmsContractDetailList = supplierSpecficationBeanLocal.getitemNameFromContractAme(prmsSupplyProfile);

            } else {
                System.out.println("else");
                prmsContractDetailList = supplierSpecficationBeanLocal.getitemNameFromContractAme(prmsSupplyProfile);
            }
        }
    }

    public void display(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            mmsItemRegistration = (MmsItemRegistration) event.getNewValue();
            prmsSupplierSpecificationDt.setUnitMeasure(mmsItemRegistration.getUnitMeasure1());
        }
    }
    // </editor-fold>   

    public PrmsLuDmArchive getPrmsLuDmArchive() {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        return prmsLuDmArchive;
    }

    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        this.prmsLuDmArchive = prmsLuDmArchive;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
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

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        if (prmsLuDmArchiveSelection == null) {
            prmsLuDmArchiveSelection = new PrmsLuDmArchive();
        }
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }

    public DataUpload getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }
}
