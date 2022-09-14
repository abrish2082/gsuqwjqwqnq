package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.prms.businessLogic.BidOpeningListLocal;
import et.gov.eep.hrms.integration.HrCommitteesIntegrationBeanLocal;
import et.gov.eep.prms.businessLogic.BIdSubmissionBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.businessLogic.InsuranceRegisterationBeanLocal;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import et.gov.eep.prms.entity.PrmsCheckListDetailforlot;
import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
import et.gov.eep.prms.entity.PrmsChecklistFileUpload;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.insa.client.DmsHandler;
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

//Bid Opening Check List view scoped CDI Named Bean class
@Named(value = "bidOpeningCheckListController")
@ViewScoped
public class BidOpeningCheckListController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    InsuranceRegisterationBeanLocal insuranceRegisterationBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    private BidOpeningListLocal bidOpeningListLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @EJB
    private BIdSubmissionBeanLocal bIdSubmissionBeanLocal;
    @EJB
    private HrCommitteesIntegrationBeanLocal committesInterface;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    private PrmsBidOpeningCheckList prmsBidOpeningCheckList;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    private HrCommittees hrCommittees;
    @Inject
    private FmsLuCurrency fmsLuCurrency;
    @Inject
    private PrmsBidOpeningChecklstDt prmsBidOpeningChecklstDt;
    @Inject
    private PrmsChecklistFileUpload prmsChecklistFileUpload;
    @Inject
    private PrmsBidOpeningChecklstDt prmsBidOpeningChecklstDetail;
    @Inject
    private PrmsBid prmsBid;
    @Inject
    private ComLuCountry comLuCountry;
    @Inject
    private PrmsBidDetail prmsBidDetail;
    @Inject
    private PrmsBidSubmissionDetail prmsBidSubmissionDetail;
    @Inject
    private PrmsBidSubmission prmsBidSubmission;
    @Inject
    private PrmsCheckListDetailforlot checkListDetailforlot;
    @Inject
    HrLuBanks hrLuBanks;
    @Inject
    private PrmsSupplyProfile supplier;
    @Inject
    HrCommittees hrcommittesEntity;
    @Inject
    HrCommitteeMembers hrcommitteeMembersEntity;
    @Inject
    HrEmployees hrEmployeesEntity;
    @Inject
    HrLuBankBranches hrLuBankBranches;
    @Inject
    DataUpload dataUpload;
    @Inject
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    @Inject
    PrmsSupplyProfile supplierObj;
    @Inject
    WorkFlow workFlow;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean sessionBean;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Variables">
    private String createOrSearchBundle = "New";
    private String saveorUpdateBundle = "Save";
    private String addOrModifyBundle = "Add";
    private String duplicattion = null;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String fileName;
    private String fileType;
    private String selectOptPartyName;
    String purchaseType;
    String newPayNo;
    String lastPaymentNo = "0";
    String members = "";
    String logerName;
    String LastcheckListDtIdNo = "0";
    String newcheckListNo;
    String LastcheckListNo = "0";
    private String userName;
    private String[] selectedMembers;
    private String[] selectedLotName;

    private boolean renderCurrency = true;
    private boolean renderwithdrawal = false;
    private boolean forLotNumber;
    private boolean renderCurrencyDIscount = false;
    private boolean renderCurrencyDsTable = false;
    private boolean renderConfirmationPopUp;
    private boolean renderPercentage;
    private boolean renderPnlManPage = true;
    private boolean disableServiceAndWorkRdbtnWhenSearch = false;
    private boolean renderPnlCreateServiceRegistration = false;
    private boolean disableBtnCreate = false;
    private boolean renderBidlot = false;
    private boolean renderCheckList = true;
    private boolean disableBidder = false;
    private boolean isFileSelected = false;
    private boolean isRendercreate = true;
    private boolean renderpnlToSearchPage;

    int updateStatus = 0;
    int lastBidCheckDetId = 0;
    int lastCheckLotId = 0;
    int rowIndex = 0, checkSaveUpdate = 0;
    int updateLotDet = 0;
    int lotRowIndex = 0;
    int docId;
    int uploadedDocId = -1;

    Date bidClosingDate;
    Date bidOpeningDate;
    private byte[] byteFile;

    private PrmsBidOpeningCheckList checkListSelection;
    DocumentModel documentModel = new DocumentModel();
    DmsHandler dmsHandler = new DmsHandler();
    StreamedContent file;
    DocumentModel dm = new DocumentModel();
    StreamedContent fileDown;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and Models">
    private List<PrmsSupplyProfile> prmsSupplyProfiles;
    List<PrmsBid> prmsBidList;
    ArrayList<PrmsBid> prmsBids;
    List<PrmsBidSubmission> prmsBidSubmissions;
    ArrayList<PrmsBidDetail> prmsBidDetails;
    ArrayList<HrCommittees> hrCommitteeses;
    private List<PrmsSupplyProfile> prmsSupplyProfilLst;
    List<PrmsBidOpeningCheckList> prmsBidOpeningCheckLists;
    List<PrmsBidOpeningCheckList> bidOpeningCheckListSearchParameterLst;
    private List<FmsLuCurrency> currencyList;
    private ArrayList<PrmsBid> bidList;
    private ArrayList<ComLuCountry> countryList;
    List<HrCommittees> committeeList = new ArrayList<>();
    List<HrCommitteeMembers> committeeMembersList = new ArrayList<>();
    List<HrCommitteeMembers> selectedMembersList = new ArrayList<>();
    List<PrmsBidSubmissionDetail> bidSubmissionDetails;
    List<PrmsBidDetail> prmsBidDetailsList;
    List<PrmsBidDetail> prmsBidDetailForLot = new ArrayList<>();
    private List<PrmsBidOpeningCheckList> prmsBidderRegistrationsForstatus;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    List<DocumentModel> newDoclist = new ArrayList<>();
    List<Integer> savedDocIds = new ArrayList<>();

    DataModel<DocumentModel> docValueModel;
    DataModel<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtsmodel;
    DataModel<PrmsCheckListDetailforlot> prmsCheckListDetailforlots;
    DataModel<PrmsBidOpeningCheckList> prmsBidOpeningCheckListDataModel;
    DataModel<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtsMoDel = new ListDataModel<>();
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    DataModel<DocumentModel> docModel;
    DataModel<DocumentModel> docList;

    Set<String> addressCheck = new HashSet<>();
    Set<String> checkLotInfo = new HashSet<>();
    // </editor-fold>

    //default constructor name
    public BidOpeningCheckListController() {

    }

//life cycle callback method
    @PostConstruct
    public void init() {
        prmsBidderRegistrationsForstatus = bidOpeningListLocal.searchBidderRegistration();
        setLogerName(sessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(sessionBean.getUserId());
        setUserName(workFlow.getUserName());
        currencyList = insuranceRegisterationBeanLocal.fromLuCurrency();
        countryList = bidOpeningListLocal.getCountryName();
        bidList = bidOpeningListLocal.BidNoForCheck();

    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public HrCommittees getHrcommittesEntity() {
        if (hrcommittesEntity == null) {
            hrcommittesEntity = new HrCommittees();
        }
        return hrcommittesEntity;
    }

    public void setHrcommittesEntity(HrCommittees hrcommittesEntity) {
        this.hrcommittesEntity = hrcommittesEntity;
    }

    public HrCommitteeMembers getHrcommitteeMembersEntity() {
        if (hrcommitteeMembersEntity == null) {
            hrcommitteeMembersEntity = new HrCommitteeMembers();
        }
        return hrcommitteeMembersEntity;
    }

    public void setHrcommitteeMembersEntity(HrCommitteeMembers hrcommitteeMembersEntity) {
        this.hrcommitteeMembersEntity = hrcommitteeMembersEntity;
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

    public PrmsSupplyProfile getSupplierObj() {
        if (supplierObj == null) {
            supplierObj = new PrmsSupplyProfile();
        }
        return supplierObj;
    }

    public void setSupplierObj(PrmsSupplyProfile supplierObj) {
        this.supplierObj = supplierObj;
    }

    public HrEmployees getHrEmployeesEntity() {
        if (hrEmployeesEntity == null) {
            hrEmployeesEntity = new HrEmployees();
        }
        return hrEmployeesEntity;
    }

    public void setHrEmployeesEntity(HrEmployees hrEmployeesEntity) {
        this.hrEmployeesEntity = hrEmployeesEntity;
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

    public ComLuCountry getComLuCountry() {
        if (comLuCountry == null) {
            comLuCountry = new ComLuCountry();
        }
        return comLuCountry;
    }

    public void setComLuCountry(ComLuCountry comLuCountry) {
        this.comLuCountry = comLuCountry;
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

    public HrCommittees getHrCommittees() {
        if (hrCommittees == null) {
            hrCommittees = new HrCommittees();
        }
        return hrCommittees;
    }

    public void setHrCommittees(HrCommittees hrCommittees) {
        this.hrCommittees = hrCommittees;
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

    public PrmsBid getPrmsBid() {

        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
    }

    public HrLuBanks getHrLuBanks() {
        if (hrLuBanks == null) {
            hrLuBanks = new HrLuBanks();
        }
        return hrLuBanks;
    }

    public void setHrLuBanks(HrLuBanks hrLuBanks) {
        this.hrLuBanks = hrLuBanks;
    }

    public HrLuBankBranches getHrLuBankBranches() {
        if (hrLuBankBranches == null) {
            hrLuBankBranches = new HrLuBankBranches();
        }
        return hrLuBankBranches;
    }

    public void setHrLuBankBranches(HrLuBankBranches hrLuBankBranches) {
        this.hrLuBankBranches = hrLuBankBranches;
    }

    public PrmsBidOpeningCheckList getPrmsBidOpeningCheckList() {
        if (prmsBidOpeningCheckList == null) {
            prmsBidOpeningCheckList = new PrmsBidOpeningCheckList();
        }
        return prmsBidOpeningCheckList;
    }

    public void setPrmsBidOpeningCheckList(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        this.prmsBidOpeningCheckList = prmsBidOpeningCheckList;
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

    public PrmsBidSubmission getPrmsBidSubmission() {
        if (prmsBidSubmission == null) {
            prmsBidSubmission = new PrmsBidSubmission();
        }
        return prmsBidSubmission;
    }

    public void setPrmsBidSubmission(PrmsBidSubmission prmsBidSubmission) {
        this.prmsBidSubmission = prmsBidSubmission;
    }

    public PrmsCheckListDetailforlot getCheckListDetailforlot() {
        if (checkListDetailforlot == null) {
            checkListDetailforlot = new PrmsCheckListDetailforlot();
        }
        return checkListDetailforlot;
    }

    public void setCheckListDetailforlot(PrmsCheckListDetailforlot checkListDetailforlot) {
        this.checkListDetailforlot = checkListDetailforlot;
    }

    public PrmsChecklistFileUpload getPrmsChecklistFileUpload() {
        if (prmsChecklistFileUpload == null) {
            prmsChecklistFileUpload = new PrmsChecklistFileUpload();
        }
        return prmsChecklistFileUpload;
    }

    public void setPrmsChecklistFileUpload(PrmsChecklistFileUpload prmsChecklistFileUpload) {
        this.prmsChecklistFileUpload = prmsChecklistFileUpload;
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

    public PrmsSupplyProfile getSupplier() {
        if (supplier == null) {
            supplier = new PrmsSupplyProfile();
        }
        return supplier;
    }

    public void setSupplier(PrmsSupplyProfile supplier) {
        this.supplier = supplier;
    }

    public PrmsBidOpeningChecklstDt getPrmsBidOpeningChecklstDetail() {
        if (prmsBidOpeningChecklstDetail == null) {
            prmsBidOpeningChecklstDetail = new PrmsBidOpeningChecklstDt();
        }
        return prmsBidOpeningChecklstDetail;
    }

    public void setPrmsBidOpeningChecklstDetail(PrmsBidOpeningChecklstDt prmsBidOpeningChecklstDetail) {
        this.prmsBidOpeningChecklstDetail = prmsBidOpeningChecklstDetail;
    }

    public PrmsBidOpeningChecklstDt getPrmsBidOpeningChecklstDt() {
        if (prmsBidOpeningChecklstDt == null) {
            prmsBidOpeningChecklstDt = new PrmsBidOpeningChecklstDt();
        }
        return prmsBidOpeningChecklstDt;
    }

    public void setPrmsBidOpeningChecklstDt(PrmsBidOpeningChecklstDt prmsBidOpeningChecklstDt) {
        this.prmsBidOpeningChecklstDt = prmsBidOpeningChecklstDt;
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

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<HrCommitteeMembers> getCommitteeMembersList() {
        if (committeeMembersList == null) {
            committeeMembersList = new ArrayList<>();
        }
        return committeeMembersList;
    }

    public void setCommitteeMembersList(List<HrCommitteeMembers> committeeMembersList) {
        this.committeeMembersList = committeeMembersList;
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

    public List<HrCommitteeMembers> getSelectedMembersList() {
        return selectedMembersList;
    }

    public void setSelectedMembersList(List<HrCommitteeMembers> selectedMembersList) {
        this.selectedMembersList = selectedMembersList;
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

    public List<PrmsBidDetail> getPrmsBidDetailForLot() {
        prmsBidDetailForLot = bidOpeningListLocal.getBidListForLot(prmsBid);
        return prmsBidDetailForLot;
    }

    public void setPrmsBidDetailForLot(List<PrmsBidDetail> prmsBidDetailForLot) {
        this.prmsBidDetailForLot = prmsBidDetailForLot;
    }

    public ArrayList<HrCommittees> getHrCommitteeses() {
        setHrCommitteeses(bidOpeningListLocal.getCommitee());
        return hrCommitteeses;
    }

    public void setHrCommitteeses(ArrayList<HrCommittees> hrCommitteeses) {
        this.hrCommitteeses = hrCommitteeses;
    }

    public List<HrCommitteeMembers> getCommitteeMembers() {
        if (committeeMembersList == null) {
            committeeMembersList = new ArrayList<>();
        }
        return committeeMembersList;
    }

    public List<PrmsBidDetail> getPrmsBidDetailsList() {
        if (prmsBidDetailsList == null) {
            prmsBidDetailsList = new ArrayList<>();
            for (int i = 0; i < prmsBidDetailsList.size(); i++) {
                prmsBidDetail = prmsBidDetailsList.get(i);
                prmsBidDetailsList.add(prmsBidDetail);
            }
        }
        return prmsBidDetailsList;
    }

    public void setPrmsBidDetailsList(List<PrmsBidDetail> prmsBidDetailsList) {
        this.prmsBidDetailsList = prmsBidDetailsList;
    }

    public ArrayList<PrmsBidDetail> getPrmsBidDetails() {
        setPrmsBidDetails(bidOpeningListLocal.getLotNum());
        return prmsBidDetails;
    }

    public void setPrmsBidDetails(ArrayList<PrmsBidDetail> prmsBidDetails) {
        this.prmsBidDetails = prmsBidDetails;
    }

    public ArrayList<PrmsBid> getPrmsBids() {
        if (prmsBids == null) {
            prmsBids = new ArrayList<>();
        }

        return prmsBids;
    }

    public void setPrmsBids(ArrayList<PrmsBid> prmsBids) {
        this.prmsBids = prmsBids;
    }

    public List<PrmsBidSubmission> getPrmsBidSubmissions() {
        if (prmsBidSubmissions == null) {
            prmsBidSubmissions = new ArrayList<>();
        }
        prmsBidSubmissions = bidOpeningListLocal.BidNoListForCheckList();
        PrmsBid bidInfo;
        for (int i = 0; i < prmsBidSubmissions.size(); i++) {
            bidInfo = new PrmsBid();
            bidInfo = prmsBidSubmissions.get(i).getBidId();
            prmsBids.add(bidInfo);
        }
        return prmsBidSubmissions;
    }

    public void setPrmsBidSubmissions(List<PrmsBidSubmission> prmsBidSubmissions) {
        this.prmsBidSubmissions = prmsBidSubmissions;
    }

    public List<PrmsBidOpeningCheckList> getPrmsBidOfferSubmissions() {
        if (prmsBidOpeningCheckLists == null) {
            prmsBidOpeningCheckLists = new ArrayList<>();
        }
        return prmsBidOpeningCheckLists;
    }

    public void setPrmsBidOfferSubmissions(List<PrmsBidOpeningCheckList> prmsBidOpeningCheckLists) {
        this.prmsBidOpeningCheckLists = prmsBidOpeningCheckLists;
    }

    public List<PrmsBidOpeningCheckList> getBidOpeningCheckListSearchParameterLst() {
        if (bidOpeningCheckListSearchParameterLst == null) {
            bidOpeningCheckListSearchParameterLst = new ArrayList<>();
            bidOpeningCheckListSearchParameterLst = bidOpeningListLocal.getBidOpeningCheckListSearchParameterLst();
        }
        return bidOpeningCheckListSearchParameterLst;
    }

    public void setBidOpeningCheckListSearchParameterLst(List<PrmsBidOpeningCheckList> bidOpeningCheckListSearchParameterLst) {
        this.bidOpeningCheckListSearchParameterLst = bidOpeningCheckListSearchParameterLst;
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfiles() {
        if (prmsSupplyProfiles == null) {
            prmsSupplyProfiles = new ArrayList<>();
        }
        return prmsSupplyProfiles;
    }

    public void setPrmsSupplyProfiles(List<PrmsSupplyProfile> prmsSupplyProfiles) {
        this.prmsSupplyProfiles = prmsSupplyProfiles;
    }

    public List<PrmsBidSubmissionDetail> getBidSubmissionDetails() {
        if (bidSubmissionDetails == null) {
            bidSubmissionDetails = new ArrayList<>();
        }
        return bidSubmissionDetails;
    }

    public void setBidSubmissionDetails(List<PrmsBidSubmissionDetail> bidSubmissionDetails) {
        this.bidSubmissionDetails = bidSubmissionDetails;
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfilLst() {
        if (prmsSupplyProfilLst == null) {
            prmsSupplyProfilLst = new ArrayList<>();
            prmsSupplyProfilLst = bidOpeningListLocal.findSuppliers();
        }

        return prmsSupplyProfilLst;
    }

    public void setPrmsSupplyProfilLst(List<PrmsSupplyProfile> prmsSupplyProfilLst) {
        this.prmsSupplyProfilLst = prmsSupplyProfilLst;
    }

    public List<FmsLuCurrency> getCurrencyList() {
        if (currencyList == null) {
            currencyList = new ArrayList<>();
        }
        return currencyList;
    }

    public void setCurrencyList(List<FmsLuCurrency> currencyList) {
        this.currencyList = currencyList;
    }

    public ArrayList<PrmsBid> getBidList() {
        if (bidList == null) {
            bidList = new ArrayList<>();
        }
        return bidList;
    }

    public void setBidList(ArrayList<PrmsBid> bidList) {
        this.bidList = bidList;
    }

    public ArrayList<ComLuCountry> getCountryList() {
        if (countryList == null) {
            countryList = new ArrayList<>();
        }
        return countryList;
    }

    public void setCountryList(ArrayList<ComLuCountry> countryList) {
        this.countryList = countryList;
    }

    public List<DocumentModel> getNewDoclist() {
        return newDoclist;
    }

    public void setNewDoclist(List<DocumentModel> newDoclist) {
        this.newDoclist = newDoclist;
    }

    public List<Integer> getSavedDocIds() {
        return savedDocIds;
    }

    public void setSavedDocIds(List<Integer> savedDocIds) {
        this.savedDocIds = savedDocIds;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    public DataModel<PrmsCheckListDetailforlot> getPrmsCheckListDetailforlots() {
        if (prmsCheckListDetailforlots == null) {
            prmsCheckListDetailforlots = new ArrayDataModel<>();
        }
        return prmsCheckListDetailforlots;
    }

    public void setPrmsCheckListDetailforlots(DataModel<PrmsCheckListDetailforlot> prmsCheckListDetailforlots) {
        this.prmsCheckListDetailforlots = prmsCheckListDetailforlots;
    }

    public DataModel<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDtsmodel() {
        if (prmsBidOpeningChecklstDtsmodel == null) {
            prmsBidOpeningChecklstDtsmodel = new ListDataModel<>();
        }
        return prmsBidOpeningChecklstDtsmodel;
    }

    public void setPrmsBidOpeningChecklstDtsmodel(DataModel<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtsmodel) {
        this.prmsBidOpeningChecklstDtsmodel = prmsBidOpeningChecklstDtsmodel;
    }

    public DataModel<PrmsBidOpeningCheckList> getPrmsBidOpeningCheckListDataModel() {
        if (prmsBidOpeningCheckListDataModel == null) {
            prmsBidOpeningCheckListDataModel = new ArrayDataModel<>();
        }

        return prmsBidOpeningCheckListDataModel;
    }

    public void setPrmsBidOpeningCheckListDataModel(DataModel<PrmsBidOpeningCheckList> prmsBidOpeningCheckListDataModel) {
        this.prmsBidOpeningCheckListDataModel = prmsBidOpeningCheckListDataModel;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }

    public DataModel<DocumentModel> getDocModel() {
        docModel = dataUpload.getDocValueModel();
        return docModel;
    }

    public void setDocModel(DataModel<DocumentModel> docModel) {
        this.docModel = docModel;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }

    public boolean isIsRendercreate() {
        return isRendercreate;
    }

    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isForLotNumber() {
        return forLotNumber;
    }

    public void setForLotNumber(boolean forLotNumber) {
        this.forLotNumber = forLotNumber;
    }

    public boolean isRenderCurrency() {
        return renderCurrency;
    }

    public void setRenderCurrency(boolean renderCurrency) {
        this.renderCurrency = renderCurrency;
    }

    public boolean isRenderPercentage() {
        return renderPercentage;
    }

    public void setRenderPercentage(boolean renderPercentage) {
        this.renderPercentage = renderPercentage;
    }

    public boolean isRenderBidlot() {
        return renderBidlot;
    }

    public void setRenderBidlot(boolean renderBidlot) {
        this.renderBidlot = renderBidlot;
    }

    public boolean isRenderCheckList() {
        return renderCheckList;
    }

    public void setRenderCheckList(boolean renderCheckList) {
        this.renderCheckList = renderCheckList;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderCurrencyDIscount() {
        return renderCurrencyDIscount;
    }

    public void setRenderCurrencyDIscount(boolean renderCurrencyDIscount) {
        this.renderCurrencyDIscount = renderCurrencyDIscount;
    }

    public boolean isRenderCurrencyDsTable() {
        return renderCurrencyDsTable;
    }

    public void setRenderCurrencyDsTable(boolean renderCurrencyDsTable) {
        this.renderCurrencyDsTable = renderCurrencyDsTable;
    }

    public boolean isRenderConfirmationPopUp() {
        return renderConfirmationPopUp;
    }

    public void setRenderConfirmationPopUp(boolean renderConfirmationPopUp) {
        this.renderConfirmationPopUp = renderConfirmationPopUp;
    }

    public boolean isDisableBidder() {
        return disableBidder;
    }

    public void setDisableBidder(boolean disableBidder) {
        this.disableBidder = disableBidder;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderwithdrawal() {
        return renderwithdrawal;
    }

    public void setRenderwithdrawal(boolean renderwithdrawal) {
        this.renderwithdrawal = renderwithdrawal;
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

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
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

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public String getNewPayNo() {
        return newPayNo;
    }

    public void setNewPayNo(String newPayNo) {
        this.newPayNo = newPayNo;
    }

    public String getLastPaymentNo() {
        return lastPaymentNo;
    }

    public void setLastPaymentNo(String lastPaymentNo) {
        this.lastPaymentNo = lastPaymentNo;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastcheckListDtIdNo() {
        return LastcheckListDtIdNo;
    }

    public void setLastcheckListDtIdNo(String LastcheckListDtIdNo) {
        this.LastcheckListDtIdNo = LastcheckListDtIdNo;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getLastCheckLotId() {
        return lastCheckLotId;
    }

    public void setLastCheckLotId(int lastCheckLotId) {
        this.lastCheckLotId = lastCheckLotId;
    }

    public int getLastBidCheckDetId() {
        return lastBidCheckDetId;
    }

    public void setLastBidCheckDetId(int lastBidCheckDetId) {
        this.lastBidCheckDetId = lastBidCheckDetId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getUploadedDocId() {
        return uploadedDocId;
    }

    public void setUploadedDocId(int uploadedDocId) {
        this.uploadedDocId = uploadedDocId;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getCheckSaveUpdate() {
        return checkSaveUpdate;
    }

    public void setCheckSaveUpdate(int checkSaveUpdate) {
        this.checkSaveUpdate = checkSaveUpdate;
    }

    public int getUpdateLotDet() {
        return updateLotDet;
    }

    public void setUpdateLotDet(int updateLotDet) {
        this.updateLotDet = updateLotDet;
    }

    public int getLotRowIndex() {
        return lotRowIndex;
    }

    public void setLotRowIndex(int lotRowIndex) {
        this.lotRowIndex = lotRowIndex;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String[] getSelectedMembers() {
        return selectedMembers;
    }

    public void setSelectedMembers(String[] selectedMembers) {
        this.selectedMembers = selectedMembers;
    }

    public String[] getSelectedLotName() {
        return selectedLotName;
    }

    public void setSelectedLotName(String[] selectedLotName) {
        this.selectedLotName = selectedLotName;
    }

    public Date getBidOpeningDate() {
        return bidOpeningDate;
    }

    public void setBidOpeningDate(Date bidOpeningDate) {
        this.bidOpeningDate = bidOpeningDate;
    }

    public Date getBidClosingDate() {
        return bidClosingDate;
    }

    public void setBidClosingDate(Date bidClosingDate) {
        this.bidClosingDate = bidClosingDate;
    }

    public PrmsBidSubmissionDetail getPrmsBidSubmissionDetail() {
        return prmsBidSubmissionDetail;
    }

    public void setPrmsBidSubmissionDetail(PrmsBidSubmissionDetail prmsBidSubmissionDetail) {
        this.prmsBidSubmissionDetail = prmsBidSubmissionDetail;
    }

    public PrmsBidOpeningCheckList getCheckListSelection() {
        return checkListSelection;
    }

    public void setCheckListSelection(PrmsBidOpeningCheckList checkListSelection) {
        this.checkListSelection = checkListSelection;
    }

    public DmsHandler getDmsHandler() {
        return dmsHandler;
    }

    public void setDmsHandler(DmsHandler dmsHandler) {
        this.dmsHandler = dmsHandler;
    }

    public DocumentModel getDm() {
        return dm;
    }

    public void setDm(DocumentModel dm) {
        this.dm = dm;
    }

    public StreamedContent getFileDown() throws Exception {
        fileDown = dataUpload.getFile(dm);
        return fileDown;
    }

    public void setFileDown(StreamedContent fileDown) {
        this.fileDown = fileDown;
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Pls Select File Name");
        }
        return file;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event Changes">
    public void handleSelectCommittee(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrcommittesEntity = (HrCommittees) event.getNewValue();
            committeeMembersList = bidOpeningListLocal.getCommitteeMembers(hrcommittesEntity);
            prmsBidOpeningCheckList.setCommiteeId(hrcommittesEntity);
        }
    }

    public void changeCurrencyToPercentage(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Currency")) {
                renderCurrency = true;
            } else {
                renderCurrency = false;
            }
        }
    }

    public void changeBidLotNumber(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Currency")) {
                renderCurrency = true;
            } else {
                renderCurrency = false;
            }
        }
    }

    public void BankChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuBanks.setBankName(event.getNewValue().toString());
            hrLuBanks = hrEmployeeBean.findBanks(hrLuBanks);
            prmsBidOpeningChecklstDt.setBankId(hrLuBanks);
        }
    }

    public void BankBranchChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuBankBranches.setBranchName(event.getNewValue().toString());
            hrLuBankBranches = hrEmployeeBean.findBankBranchs(hrLuBankBranches);
            prmsBidOpeningChecklstDt.setBankBranchId(hrLuBankBranches);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateServiceRegistration = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = prmsBidOpeningCheckListDataModel.getRowIndex();
        prmsBidOpeningCheckList = prmsBidOpeningCheckLists.get(rowIndex);
        recreateprmsBidOpeningCheckListDataModel();
        recreateprmsBidOpeningChecklstDtsmodel();
    }

    public void getRequestNo(SelectEvent event) {
        String bidchecklistNo = event.getObject().toString();
        prmsBidOpeningCheckList.setBidCheckListNo(bidchecklistNo);
        prmsBidOpeningCheckList = bidOpeningListLocal.getRequestNo(prmsBidOpeningCheckList);
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    public void setSelectedSupplier(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            prmsSupplyProfile = (PrmsSupplyProfile) e.getNewValue();
            checkListDetailforlot.setSupId(prmsSupplyProfile);
        }
    }

    public void onContactRowEdit(RowEditEvent event) {
        int rowIndex = prmsBidOpeningChecklstDtsmodel.getRowIndex();
        PrmsBidOpeningChecklstDt contactPerson = new PrmsBidOpeningChecklstDt();
        PrmsBidOpeningChecklstDt comContPerson = (PrmsBidOpeningChecklstDt) event.getObject();
        boolean found = false;
        for (int i = 0; i < prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().size(); i++) {
            if (i != rowIndex) {
                if (prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().get(i).getDiscriptionOfSubmission().equals(comContPerson.getDiscriptionOfSubmission())
                        && prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().get(i).getBidFormStatus().equals(comContPerson.getBidFormStatus())) {
                    found = true;
                    break;
                }
            }
        }
        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            comContPerson.setBidFormStatus(null);
            prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().set(rowIndex, comContPerson);
            recreateprmsBidOpeningChecklstDtsmodel();
        } else {
            prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().set(rowIndex, comContPerson);
            recreateprmsBidOpeningChecklstDtsmodel();
        }
    }

    public void changeEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsBids = bidOpeningListLocal.bidNo(event.getNewValue().toString());
        }
    }

    public void BidDateGetter(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsBid.setRefNo(event.getNewValue().toString());
            prmsBid = bidOpeningListLocal.bidGetter(prmsBid);
            bidClosingDate = prmsBid.getBidCloseDateTime();
            bidOpeningDate = prmsBid.getBidOpenDateTime();
        }
    }

    public void rowSelect(SelectEvent event) {
        prmsBidOpeningCheckList = (PrmsBidOpeningCheckList) event.getObject();
        hrcommittesEntity = prmsBidOpeningCheckList.getCommiteeId();
        prmsBid = prmsBidOpeningCheckList.getBidId();
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        committeeMembersList = bidOpeningListLocal.getCommitteeMembers(hrcommittesEntity);
        selectedMembers = prmsBidOpeningCheckList.getCommitteMembers().split(",");
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        renderPnlCreateServiceRegistration = true;
        disableServiceAndWorkRdbtnWhenSearch = true;
        prmsBidDetailsList = prmsBid.getPrmsBidDetailList();
        prmsBidOpeningChecklstDt = checkListDetailforlot.getCheckListId();
        purchaseType = prmsBid.getAwardType();
        if (prmsBidOpeningCheckList.getDocumentId() != null) {
            prmsLuDmArchive = prmsBidOpeningCheckList.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
        }
        if (purchaseType.equalsIgnoreCase("Lot Base")) {
            renderCheckList = true;
            renderBidlot = true;
        } else {
            renderBidlot = false;
            renderCheckList = true;
        }
        recreateprmsBidOpeningChecklstDtsmodel();
        for (int i = 0; i < prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().size(); i++) {
            prmsBidOpeningChecklstDetail = prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().get(i);
            checkListDetailforlot.setCheckListId(prmsBidOpeningChecklstDetail);
            recreateprmsBidOpeningCheckllot();
        }
    }

    public void setSelectedBidder(ValueChangeEvent event) {
        prmsSupplyProfile = (PrmsSupplyProfile) event.getNewValue();
        supplier = prmsSupplyProfile;
    }

    public void changeEventSuppCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsBid.setRefNo(event.getNewValue().toString());
            prmsBid = bidOpeningListLocal.bidGetter(prmsBid);
            bidSubmissionDetails = bidOpeningListLocal.getsupplierlist(event.getNewValue().toString());
            prmsBidDetailsList = bidOpeningListLocal.getLotNo(event.getNewValue().toString());
        }
    }

    public void changeEventGetVendorName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsBid = (PrmsBid) event.getNewValue();
            prmsBidDetailsList = prmsBid.getPrmsBidDetailList();
            prmsBidOpeningCheckList.setBidId(prmsBid);
            prmsSupplyProfilLst = bidOpeningListLocal.getsupplierlist(prmsBid);
            purchaseType = prmsBid.getAwardType();

            if (purchaseType.equalsIgnoreCase("Lot Base")) {
                renderCheckList = true;
                renderBidlot = true;
            } else {
                renderBidlot = false;
                renderCheckList = true;
            }
        }
    }

    public void selectedParamChangeEvent(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            prmsBidOpeningCheckList.setColumnName(changeEvent.getNewValue().toString());
            prmsBidOpeningCheckList.setColumnValue(null);
        }
    }

    public void selectAllBiddersFromSubmission(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsBid = (PrmsBid) event.getNewValue();
            prmsBidSubmission = bIdSubmissionBeanLocal.selectBidSubmissionByBidNo(prmsBid);
            prmsBidOpeningChecklstDt.setSuppId(prmsBidSubmissionDetail.getSupplierId());
        }
    }

    public void changePercent(ValueChangeEvent e) {
        if (e.getNewValue().equals("Currency")) {
            renderCurrency = true;
            renderCurrencyDsTable = true;
        } else {
            renderCurrency = false;
            renderCurrencyDsTable = false;
        }
    }

    public void changePurchaseType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Currency")) {
                renderCurrency = false;
                renderCurrencyDIscount = true;
            } else if (select.equalsIgnoreCase("Percentage")) {

                renderCurrency = true;
                renderCurrencyDIscount = false;
            }
        }
    }

    public void changediscribesubmission(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Withdrawal")) {
                renderwithdrawal = true;
            } else {

                renderwithdrawal = false;
            }
        }
    }

    public void displayBidOpeningDetailLot(ValueChangeEvent e) {
        checkListDetailforlot = (PrmsCheckListDetailforlot) e.getNewValue();

    }

    public void onRowSelect(SelectEvent event) {
        prmsLuDmArchive = (PrmsLuDmArchive) event.getObject();
        isFileSelected = true;
    }

    public void onRowSelectf(SelectEvent event) {
        dm = (DocumentModel) event.getObject();
    }

    public void uploadListener(FileUploadEvent event) {
        InputStream inputStream = null;
        try {
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            inputStream = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStream);
            if (byteFile != null) {
                System.out.println("byte File is  " + byteFile);
                JsfUtil.addSuccessMessage("Upload Successfully File Name " + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public void createNewParty() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clearPrmsBidOpeningCheckList();
            PrmsBidOpeningChecklstDt LastCheckListDtId = new PrmsBidOpeningChecklstDt();
            PrmsCheckListDetailforlot LastCheckListLotId = new PrmsCheckListDetailforlot();
            LastCheckListDtId = bidOpeningListLocal.LastCheckListDtid();
            LastCheckListLotId = bidOpeningListLocal.LastCheckListlotid();
            if (LastCheckListDtId == null) {
                lastBidCheckDetId = 0;
            } else {
                lastBidCheckDetId = Integer.parseInt(String.valueOf(LastCheckListDtId.getBidCheckDtId()));
            }
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

    public List<PrmsBid> getBidNo() {
        setPrmsBidList(bidOpeningListLocal.getBidNoListForCheckLIst());
        return prmsBidList;
    }

    public SelectItem[] getEmployeeName() {
        return JsfUtil.getSelectItems(bidOpeningListLocal.getEmployeeNames(), true);

    }

    public ArrayList<PrmsBidOpeningCheckList> searchRequestForPayment(String bidchecklistNo) {
        ArrayList<PrmsBidOpeningCheckList> serviceProvide = null;
        prmsBidOpeningCheckList.setBidCheckListNo(bidchecklistNo);
        serviceProvide = bidOpeningListLocal.searchRequestForPayment(prmsBidOpeningCheckList);
        return serviceProvide;
    }

    public List<HrCommittees> getCommittees() {
        committeeList = committesInterface.findAll();
        return committeeList;
    }

    public SelectItem[] bidReferenceNo() {
        return JsfUtil.getSelectItems(bidOpeningListLocal.BidNo(), true);
    }

    public SelectItem[] bidReferenceNoFromBidSale() {
        return JsfUtil.getSelectItems(bidOpeningListLocal.BidNoForCheck(), true);

    }

    public SelectItem[] fromLuCurrency() {
        return JsfUtil.getSelectItems(insuranceRegisterationBeanLocal.fromLuCurrency(), true);
    }

    public SelectItem[] getBranchByBank() {
        if (hrLuBanks.getBankName() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(hrEmployeeBean.searchBankBranchInfo(hrLuBanks), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--First Select Bank --");
            return items;
        }
    }

    public SelectItem[] getListOfBanks() {
        return JsfUtil.getSelectItems(hrEmployeeBean.findAllBanks(), true);
    }

    public SelectItem[] getCountry() {
        return JsfUtil.getSelectItems(bidOpeningListLocal.getCountryName(), true);
    }

    private String clearPrmsBidOpeningCheckList() {
        prmsBidOpeningCheckList = null;
        prmsBidOpeningChecklstDt = null;
        prmsBidOpeningChecklstDtsmodel = null;
        prmsBid = null;
        hrLuBanks = null;
        hrLuBankBranches = null;
        selectedMembers = null;
        prmsCheckListDetailforlots = null;
        committeeMembersList = null;
        hrcommittesEntity = new HrCommittees();
        hrcommitteeMembersEntity = new HrCommitteeMembers();
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        return null;

    }

    public void recreateprmsBidOpeningChecklstDtsmodel() {
        prmsBidOpeningChecklstDtsmodel = null;
        prmsBidOpeningChecklstDtsmodel = new ListDataModel(new ArrayList<>(prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList()));

    }

    public void recreateprmsBidOpeningCheckllotIst() {
        prmsCheckListDetailforlots = null;
        prmsCheckListDetailforlots = new ListDataModel(new ArrayList<>(prmsBidOpeningChecklstDt.getCheckListDetailforlots()));

    }

    public void recreateprmsBidOpeningCheckllot() {
        prmsCheckListDetailforlots = null;
        prmsCheckListDetailforlots = new ListDataModel(new ArrayList<>(prmsBidOpeningChecklstDetail.getCheckListDetailforlots()));

    }

    public void recreateprmsBidOpeningCheckListDataModel() {
        prmsBidOpeningCheckListDataModel = null;
        prmsBidOpeningCheckListDataModel = new ListDataModel(new ArrayList<>(getPrmsBidOfferSubmissions()));
    }

    public String addBidCheckListDetail() {
        if (checkSaveUpdate == 0) {
            prmsBidOpeningChecklstDt.setSuppId(prmsSupplyProfile);
            if (!addressCheck.contains(prmsBidOpeningChecklstDt.getSuppId().getId())) {
                supplierObj = new PrmsSupplyProfile();
                supplierObj = prmsSupplyProfile;
                prmsBidOpeningChecklstDt.setSuppId(prmsSupplyProfile);
                lastBidCheckDetId = lastBidCheckDetId + 1;
                BigDecimal nextId = new BigDecimal(lastBidCheckDetId);
                prmsBidOpeningChecklstDt.setBidCheckDtId(nextId);
                prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().add(prmsBidOpeningChecklstDt);
                prmsBidOpeningChecklstDt.setBidCheckListId(prmsBidOpeningCheckList);
                addressCheck.add(prmsBidOpeningChecklstDt.getSuppId().getId());
                recreateprmsBidOpeningChecklstDtsmodel();
                ClearPopUp();
            } else {
                JsfUtil.addFatalMessage("Supplier can not be duplicated !!");
            }
            return null;
        } else {
            checkSaveUpdate = 1;
            prmsBidOpeningChecklstDt.setBankId(hrLuBanks);
            prmsBidOpeningChecklstDt.setBankBranchId(hrLuBankBranches);
            supplierObj = new PrmsSupplyProfile();
            supplierObj = prmsSupplyProfile;
            prmsBidOpeningChecklstDt.setSuppId(prmsSupplyProfile);
            lastBidCheckDetId = lastBidCheckDetId + 1;
            BigDecimal nextId = new BigDecimal(lastBidCheckDetId);
            prmsBidOpeningChecklstDt.setBidCheckDtId(nextId);
            prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().set(rowIndex, prmsBidOpeningChecklstDt);
            prmsBidOpeningChecklstDt.setBidCheckListId(prmsBidOpeningCheckList);
            recreateprmsBidOpeningChecklstDtsmodel();
            ClearPopUp();
            return null;
        }
    }

    public void addBidCheckListLotList() {
        if (updateLotDet == 0) {
            checkListDetailforlot.setSupId(prmsBidOpeningChecklstDt.getSuppId());
            if (!(checkLotInfo.contains(checkListDetailforlot.getSupId().getId()) && checkLotInfo.contains(checkListDetailforlot.getBidDitId().getId()))) {
                prmsBidOpeningChecklstDt.getCheckListDetailforlots().add(checkListDetailforlot);
                checkListDetailforlot.setCheckListId(prmsBidOpeningChecklstDt);
                recreateprmsBidOpeningCheckllotIst();
                checkLotInfo.add(checkListDetailforlot.getSupId().getId());
                checkLotInfo.add(checkListDetailforlot.getBidDitId().getId());
                ClearPopp();
            } else {
                JsfUtil.addFatalMessage("Supplier and Lot can not be duplicated !!");
            }
        } else {
            prmsBidOpeningChecklstDt.getCheckListDetailforlots().set(lotRowIndex, checkListDetailforlot);
            recreateprmsBidOpeningCheckllotIst();
            ClearPopp();
        }
    }

    public void addDetail() {
        getPrmsBidOpeningChecklstDt();
        int rowIndex = prmsBidOpeningChecklstDtsMoDel.getRowIndex() + 1;
        prmsBidOpeningChecklstDt = prmsBidOpeningCheckList.getPrmsBidOpeningChecklstDtList().get(rowIndex);
        checkListDetailforlot.setSupId(prmsBidOpeningChecklstDt.getSuppId());
        ClearPopUp();
    }

    private void ClearPopUp() {
        addOrModifyBundle = "Add";
        prmsBidOpeningChecklstDt = null;
        prmsBidOpeningCheckListDataModel = null;
        prmsSupplyProfile = null;
        hrLuBankBranches = null;
        hrLuBanks = null;
    }

    private void ClearPopp() {
        prmsBidDetailForLot = null;
        checkListDetailforlot = null;
    }

    public void searchBidCheckListByChekListNo() {
        prmsBidOpeningCheckList.setPreparedBy(workFlow.getUserAccount());
        prmsBidOpeningCheckLists = bidOpeningListLocal.searchCheckList(prmsBidOpeningCheckList);
        recreateprmsBidOpeningCheckListDataModel();
    }

    public SelectItem[] getSuppName() {
        return JsfUtil.getSelectItems(vendorRegBeanLocal.getVondorName(), true);
    }

    public String generateCheckListNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsBidOpeningCheckList.getBidCheckListNo();
        } else {
            PrmsBidOpeningCheckList LastCheckNo = bidOpeningListLocal.LastCheckListNo();
            if (LastCheckNo != null) {
                LastcheckListNo = LastCheckNo.getBidOpenCheckListId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "Ch-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "Ch-" + newBidNoLast + "/" + f.format(now);
            }
        }
        return newcheckListNo;
    }

    public void currencyVissiblity() {
        if (prmsBidOpeningChecklstDt.getDiscountType().equalsIgnoreCase("Currency")) {
            renderCurrency = true;
        } else {
            renderCurrency = false;
        }
    }

    public void editItemDataTbl() {
        addOrModifyBundle = "Modify";
        checkSaveUpdate = 1;
        rowIndex = prmsBidOpeningChecklstDtsmodel.getRowIndex();
        prmsBidOpeningChecklstDt = prmsBidOpeningChecklstDtsmodel.getRowData();
        prmsSupplyProfile = prmsBidOpeningChecklstDt.getSuppId();
        hrLuBanks = prmsBidOpeningChecklstDt.getBankId();
        hrLuBankBranches = prmsBidOpeningChecklstDt.getBankBranchId();
        prmsSupplyProfilLst = new ArrayList<>();
        prmsSupplyProfilLst.add(prmsSupplyProfile);
        String select = "select";
        if (select.equalsIgnoreCase("Currency")) {
            renderCurrency = false;
            renderCurrencyDIscount = true;
        } else if (select.equalsIgnoreCase("Percentage")) {

            renderCurrency = true;
            renderCurrencyDIscount = false;
        }
        ClearPopp();
    }

    public void editDataTbl() {
        addOrModifyBundle = "Modify";
        updateLotDet = 1;
        checkListDetailforlot = prmsCheckListDetailforlots.getRowData();
        prmsBidOpeningChecklstDt = checkListDetailforlot.getCheckListId();
        prmsBidDetailsList = prmsBidOpeningCheckList.getBidId().getPrmsBidDetailList();
        lotRowIndex = prmsCheckListDetailforlots.getRowIndex();
        prmsSupplyProfilLst = new ArrayList<>();
        prmsSupplyProfilLst.add(prmsSupplyProfile);

    }

    public void selectForDetailDataT() {
        prmsBidOpeningChecklstDt = prmsBidOpeningChecklstDtsmodel.getRowData();

    }

    public void recreateDmsDataModel() {
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateServiceRegistration = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update method">
    public void actionForConfirmationForBidChe() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(sessionBean.getUserName(), "actionForConfirmationForBidChe", dataset)) {
                if (selectedMembers.length > 2) {
                    if (updateStatus == 0) {
                        try {
                            generateCheckListNo();

                            setPrmsBidOpeningCheckList(prmsBidOpeningCheckList);
                            prmsBidOpeningCheckList.setBidCheckListNo(newcheckListNo);

                            for (int i = 0; i < selectedMembers.length; i++) {
                                if (members.equals("")) {
                                    members = selectedMembers[i];
                                } else {
                                    members = members + "," + selectedMembers[i];
                                }
                            }
                            prmsBidOpeningCheckList.setCommitteMembers(members);
                            prmsBidOpeningCheckList.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            prmsBidOpeningCheckList.setStatus(Constants.PREPARE_VALUE);
                            prmsBidOpeningCheckList.setPreparedBy(workFlow.getUserAccount());
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(fileType);
                            prmsLuDmArchive.setUploadFile(byteFile);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            prmsBidOpeningCheckList.setDocumentId(prmsLuDmArchive);
                            bidOpeningListLocal.create(prmsBidOpeningCheckList);
                            JsfUtil.addSuccessMessage("Bid check list successfully saved ");
                            clearPrmsBidOpeningCheckList();
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                        }
                    } else {
                        try {
                            for (int i = 0; i < selectedMembers.length; i++) {
                                if (members.equals("")) {
                                    members = selectedMembers[i];
                                } else {
                                    members = members + "," + selectedMembers[i];
                                }
                            }
                            prmsBidOpeningCheckList.setCommitteMembers(members);
                            prmsLuDmArchive.setFileType(fileType);
                            prmsLuDmArchive.setUploadFile(byteFile);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            prmsBidOpeningCheckList.setDocumentId(prmsLuDmArchive);
                            bidOpeningListLocal.update(prmsBidOpeningCheckList);
                            JsfUtil.addSuccessMessage("Bid check list is updated!!");
                            clearPrmsBidOpeningCheckList();
                            saveorUpdateBundle = "Save";
                            clearPrmsBidOpeningCheckList();
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                        }
                    }
                } else {
                    JsfUtil.addFatalMessage("Failed to save. Select at Least Three Committee members");
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
            ex.printStackTrace();
        }

    }
// </editor-fold>

}
