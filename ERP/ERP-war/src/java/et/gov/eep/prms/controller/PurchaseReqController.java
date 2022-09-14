/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.event.FileUploadEvent;
import org.insa.model.DocumentModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import et.gov.eep.commonApplications.businessLogic.ComLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.prms.businessLogic.PurchaseReqBeanLocal;
import et.gov.eep.prms.businessLogic.BidRegBeanLocal;
import et.gov.eep.prms.businessLogic.MarketAssesmntBeanLocal;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsMarketAssmntService;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.Dictionary;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ComLuDmArchive;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import securityBean.Utility;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
// </editor-fold>

/**
 * Purchase Request Registration page view Scoped Managed Bean class
 *
 * @author Bayisa Bedasa
 */
//Purchase Request page view scoped CDI Named Bean class
@Named("purchaseReqController")
@ViewScoped
public class PurchaseReqController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PurchaseReqBeanLocal purchaseReqBeanLocal;
    @EJB
    MarketAssesmntBeanLocal marketAssmntInfoBeanLocal;
    @EJB
    BidRegBeanLocal bidRegBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    ComLuDmArchiveBeanLocal comLuDmArchiveBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsPurchaseRequest eepPurchaseRequest;
    @Inject
    PrmsPurchaseRequestDet eepPurchaseReqDet;
    @Inject
    MmsItemRegistration mmsItemRegistration;
    @Inject
    MmsStorereq mmsStorereq;
    @Inject
    MmsStorereqDetail mmsStorereqDetail;
    @Inject
    PrmsMarketAssessmentDetail prmsMarketAssessmentDetail;
    @Inject
    PrmsMarketAssmntService prmsMarketAssmntService;
    @Inject
    PrmsMarketAssmntService prmsMarketAssmntServiceWork;
    @Inject
    MmsServiceReg mmsServiceReg;
    @Inject
    PrmsServiceAndWorkReg prmsServiceAndWorkReg;
    @Inject
    PrmsPurchaseReqService prmsPurchaseReqService;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    FmsAccountingPeriod currPeriod;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetail;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    DataUpload dataUpload;
    @Inject
    Dictionary dictionary;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean sessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    ComLuDmArchive comLuDmArchive;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
//</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="Declare Instance Variables">
    private int update = 0;
    private int pr_QuantitySrDetail = 0;
    int rownum;
    int uploadedDocId;
    int updateStatus = 0;
    Integer requestNotificationCounter = 0;
    Integer ViewHistoryCountCounter = 0;
    int itemRowSelectIndex;

    private String saveupdate = "Save";
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String addOrModifyBundle = "Add";
    private String duplicattion = null;
    private String activeIndex;
    private String prno = null;
    private String icone = "ui-icon-plus";
    private String requistorType = "department";
    private String userName;
    private String actors;
    private String actorName;
    private String decision = "";
    private String fileName;
    private String docFormat;
    String newPrNo;
    String lastPrNo = "0";
    private String PurchaseType = "service";//give the default value
    String selectedValue = "";

    private boolean renderDecision = false;
    private boolean renderPnlCreatePr = false;
    private boolean renderPnlManPage = true;
    private boolean renderPnlToSearchPage;
    private boolean isItemchoosed;
    private boolean renderService = false;
    private boolean renderSrNo = false;
    private boolean renderSeriveName = false;
    private boolean renderItemName = false;
    private boolean renderUnitMeasure = false;
    private boolean rederDesription = false;
    private boolean rederItemCode = false;
    private boolean rederServiceCode = false;
    private boolean disableSrNo = true;
    private boolean disableRequisitor;
    private boolean disableBtnCreate = false;
    private boolean viewonly = false;
    private boolean service = false;
    private boolean items = false;
    private boolean mItemName = true;
    private boolean mItemCode = true;
    private boolean mItemUnitMeasure = true;
    private boolean sServiceName = false;
    private boolean sServiceCode = false;
    private boolean sServiceUntMeasure = false;
    private boolean renderStoreTbale;
    private boolean renderDepartmentTbale;
    private boolean renderMarketTable;
    private boolean renderWorkTable;

    private boolean workRadio;
    private boolean isRendercreate;
    private boolean isSelected = false;
    private boolean isRowFileSelected = false;
    private byte[] byteData;

    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;

    DocumentModel documentModel;
    StreamedContent content;
    PrmsPurchaseRequest prmsPurchaseRequestSelect;
    PrmsPurchaseRequestDet purchaseRequestDetSelection;
    private PrmsPurchaseReqService prmsPurchaseReqServiceSelection;
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare Lists and models">    
//Declaring Relational objects
    private DataModel<PrmsPurchaseReqService> purchaseReqServiceMdl;
    private DataModel<PrmsPurchaseRequestDet> eepPurchaseReqDetMdl;
    private DataModel<PrmsPurchaseRequest> purchaseRequestMdl;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<DocumentModel> DMdocumentModel;
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;

    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList;
    List<MmsItemRegistration> itemRegistrationList;
    List<MmsStorereq> mmsStorereqLst;
    List<MmsStorereqDetail> mmsStorereqDetailLst;
    List<PrmsMarketAssessmentDetail> marketAssessmentDetailLst;
    List<PrmsMarketAssessment> markAss;
    List<PrmsPurchaseRequest> prmsPurchaseRequestLst;
    List<PrmsPurchaseRequest> recordedHistoryLst;
    List<PrmsMarketAssmntService> prmsMarketAssmntServiceLst;
    List<PrmsMarketAssmntService> nonConServNameLists;
    List<PrmsMarketAssmntService> workNameList;
    List<PrmsPurchaseReqService> purchaseReqServiceLst;
    List<FmsOperatingBudgetDetail> budgetCodeLists;
    List<FmsOperatingBudgetDetail> budgetCodeListsWhenWork;
    List<MmsStorereq> srListsFromDetail;
    List<Integer> documentId = new ArrayList<>();
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    List<PrmsPurchaseRequest> purchaseReqSearchParameterLst;
    Set<String> duplicatingService = new HashSet<>();
    Set<String> duplicatingWorkName = new HashSet<>();
    Set<String> duplicatingServWhenUpdate = new HashSet<>();
//</editor-fold >

    //life cycle callback method
    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsBeanLocal.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        wfPrmsProcessed.setProcessedBy(workflow.getUserAccount());
        setUserName(workflow.getUserName());
        if (workflow.isPrepareStatus()) {
            renderDecision = false;
            isRendercreate = true;
        } else if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            renderDecision = true;
            isRendercreate = false;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters of objects">
    //Getters and Setters for each relational Entities object
    public PrmsPurchaseReqService getPrmsPurchaseReqService() {
        if (prmsPurchaseReqService == null) {
            prmsPurchaseReqService = new PrmsPurchaseReqService();
        }
        return prmsPurchaseReqService;
    }

    public void setPrmsPurchaseReqService(PrmsPurchaseReqService prmsPurchaseReqService) {
        this.prmsPurchaseReqService = prmsPurchaseReqService;
    }

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        if (fmsOperatingBudgetDetail == null) {
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return fmsOperatingBudgetDetail;
    }

    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        this.fmsOperatingBudgetDetail = fmsOperatingBudgetDetail;
    }

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public FmsAccountingPeriod getCurrPeriod() {
        if (currPeriod == null) {
            currPeriod = new FmsAccountingPeriod();
        }
        return currPeriod;
    }

    public void setCurrPeriod(FmsAccountingPeriod currPeriod) {
        this.currPeriod = currPeriod;
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

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public ComLuDmArchive getComLuDmArchive() {
        if (comLuDmArchive == null) {
            comLuDmArchive = new ComLuDmArchive();
        }
        return comLuDmArchive;
    }

    public void setComLuDmArchive(ComLuDmArchive comLuDmArchive) {
        this.comLuDmArchive = comLuDmArchive;
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

    public MmsServiceReg getMmsServiceReg() {
        if (mmsServiceReg == null) {
            mmsServiceReg = new MmsServiceReg();
        }
        return mmsServiceReg;
    }

    public void setMmsServiceReg(MmsServiceReg mmsServiceReg) {
        this.mmsServiceReg = mmsServiceReg;
    }

    public PrmsServiceAndWorkReg getPrmsServiceAndWorkReg() {
        if (prmsServiceAndWorkReg == null) {
            prmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
        }
        return prmsServiceAndWorkReg;
    }

    public void setPrmsServiceAndWorkReg(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        this.prmsServiceAndWorkReg = prmsServiceAndWorkReg;
    }

    public MmsStorereq getMmsStorereq() {
        if (mmsStorereq == null) {
            mmsStorereq = new MmsStorereq();
        }
        return mmsStorereq;
    }

    public void setMmsStorereq(MmsStorereq mmsStorereq) {
        this.mmsStorereq = mmsStorereq;
    }

    public MmsStorereqDetail getMmsStorereqDetail() {
        if (mmsStorereqDetail == null) {
            mmsStorereqDetail = new MmsStorereqDetail();
        }
        return mmsStorereqDetail;
    }

    public void setMmsStorereqDetail(MmsStorereqDetail mmsStorereqDetail) {
        this.mmsStorereqDetail = mmsStorereqDetail;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public PrmsMarketAssessmentDetail getPrmsMarketAssessmentDetail() {
        if (prmsMarketAssessmentDetail == null) {
            prmsMarketAssessmentDetail = new PrmsMarketAssessmentDetail();
        }
        return prmsMarketAssessmentDetail;
    }

    public void setPrmsMarketAssessmentDetail(PrmsMarketAssessmentDetail prmsMarketAssessmentDetail) {
        this.prmsMarketAssessmentDetail = prmsMarketAssessmentDetail;
    }

    public PrmsMarketAssmntService getPrmsMarketAssmntService() {
        if (prmsMarketAssmntService == null) {
            prmsMarketAssmntService = new PrmsMarketAssmntService();
        }
        return prmsMarketAssmntService;
    }

    public void setPrmsMarketAssmntService(PrmsMarketAssmntService prmsMarketAssmntService) {
        this.prmsMarketAssmntService = prmsMarketAssmntService;
    }

    public PrmsMarketAssmntService getPrmsMarketAssmntServiceWork() {
        if (prmsMarketAssmntServiceWork == null) {
            prmsMarketAssmntServiceWork = new PrmsMarketAssmntService();
        }
        return prmsMarketAssmntServiceWork;
    }

    public void setPrmsMarketAssmntServiceWork(PrmsMarketAssmntService prmsMarketAssmntServiceWork) {
        this.prmsMarketAssmntServiceWork = prmsMarketAssmntServiceWork;
    }

    public PrmsPurchaseRequestDet getEepPurchaseReqDet() {
        if (eepPurchaseReqDet == null) {
            eepPurchaseReqDet = new PrmsPurchaseRequestDet();
        }
        return eepPurchaseReqDet;
    }

    public void setEepPurchaseReqDet(PrmsPurchaseRequestDet eepPurchaseReqDet) {
        this.eepPurchaseReqDet = eepPurchaseReqDet;
    }

    public PrmsPurchaseRequest getEepPurchaseRequest() {
        if (eepPurchaseRequest == null) {
            eepPurchaseRequest = new PrmsPurchaseRequest();
        }
        return eepPurchaseRequest;
    }

    public void setEepPurchaseRequest(PrmsPurchaseRequest eepPurchaseRequest) {
        this.eepPurchaseRequest = eepPurchaseRequest;
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

    public Dictionary getDictionary() {
        if (dictionary == null) {
            dictionary = new Dictionary();
        }
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters and setters of Lists and models">
    public static List<HrDepartments> getAraListe() {
        return araListe;
    }

    public static void setAraListe(List<HrDepartments> araListe) {
        PurchaseReqController.araListe = araListe;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        if (allDepartmentsList == null) {
            allDepartmentsList = new ArrayList<>();
        }
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public List<PrmsPurchaseReqService> getPurchaseReqServiceLst() {
        if (purchaseReqServiceLst == null) {
            purchaseReqServiceLst = new ArrayList<>();
        }
        return purchaseReqServiceLst;
    }

    public void setPurchaseReqServiceLst(List<PrmsPurchaseReqService> purchaseReqServiceLst) {
        this.purchaseReqServiceLst = purchaseReqServiceLst;
    }

    public List<PrmsMarketAssessment> getItemNameAss() {
        if (markAss == null) {
            markAss = new ArrayList<>();
            markAss = purchaseReqBeanLocal.getItemNameAss();
        }

        return markAss;
    }

    public List<PrmsMarketAssmntService> getNonConServNameLists() {
        if (nonConServNameLists == null) {
            nonConServNameLists = new ArrayList<>();
            nonConServNameLists = purchaseReqBeanLocal.getServNameNon();
        }
        return nonConServNameLists;
    }

    public void setNonConServNameLists(List<PrmsMarketAssmntService> nonConServNameLists) {
        this.nonConServNameLists = nonConServNameLists;
    }

    public List<PrmsMarketAssmntService> getWorkNameList() {
        if (workNameList == null) {
            workNameList = new ArrayList<>();
            workNameList = purchaseReqBeanLocal.getworkNames();
        }
        return workNameList;
    }

    public void setWorkNameList(List<PrmsMarketAssmntService> workNameList) {
        this.workNameList = workNameList;
    }

    public List<MmsStorereq> getSrLst() {
        setMmsStorereqLst(purchaseReqBeanLocal.getSrNo());
        return mmsStorereqLst;
    }

    public List<PrmsMarketAssessmentDetail> getSrNo() {
        return marketAssessmentDetailLst;
    }

    public List<FmsOperatingBudgetDetail> getBudgetCodeLists() {
        if (hrDepartments != null) {
            currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
            budgetCodeLists = purchaseReqBeanLocal.getBudgetCodeListsByDepId(hrDepartments, currPeriod);
            System.out.println("budget code Lists when Service===" + budgetCodeLists);
            return budgetCodeLists;

        } else {
            JsfUtil.addFatalMessage("Try again");
            return null;
        }

    }

    public void setBudgetCodeLists(List<FmsOperatingBudgetDetail> budgetCodeLists) {
        this.budgetCodeLists = budgetCodeLists;
    }

    public List<FmsOperatingBudgetDetail> getBudgetCodeListsWhenWork() {
        if (hrDepartments != null) {
            currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
            budgetCodeListsWhenWork = purchaseReqBeanLocal.getBudgetCodeListsByDepIdWhenWork(hrDepartments, currPeriod);
            System.out.println("budget code Lists when Work===" + budgetCodeListsWhenWork);
            return budgetCodeListsWhenWork;
        } else {
            JsfUtil.addFatalMessage("Try again");
            return null;
        }
    }

    public void setBudgetCodeListsWhenWork(List<FmsOperatingBudgetDetail> budgetCodeListsWhenWork) {
        this.budgetCodeListsWhenWork = budgetCodeListsWhenWork;
    }

    public List<MmsStorereq> getSrListsFromDetail() {
        if (srListsFromDetail == null) {
            srListsFromDetail = new ArrayList<>();
            srListsFromDetail = purchaseReqBeanLocal.getSrNoListsHavePrQuantity(pr_QuantitySrDetail);//to get list of Sr_Nos Having Pr_Quantity in MmsStoreReqDetail
        }
        return srListsFromDetail;
    }

    public void setSrListsFromDetail(List<MmsStorereq> srListsFromDetail) {
        this.srListsFromDetail = srListsFromDetail;
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

    public List<PrmsMarketAssmntService> getPrmsMarketAssmntServiceLst() {
        if (prmsMarketAssmntServiceLst == null) {
            prmsMarketAssmntServiceLst = new ArrayList<>();
        }
        return prmsMarketAssmntServiceLst;
    }

    public void setPrmsMarketAssmntServiceLst(List<PrmsMarketAssmntService> prmsMarketAssmntServiceLst) {
        this.prmsMarketAssmntServiceLst = prmsMarketAssmntServiceLst;
    }

    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestLst() {
        if (prmsPurchaseRequestLst == null) {
            prmsPurchaseRequestLst = new ArrayList<>();
        }
        return prmsPurchaseRequestLst;
    }

    public void setPrmsPurchaseRequestLst(List<PrmsPurchaseRequest> prmsPurchaseRequestLst) {
        this.prmsPurchaseRequestLst = prmsPurchaseRequestLst;
    }

    public List<PrmsPurchaseRequest> getRecordedHistoryLst() {
        if (recordedHistoryLst == null) {
            recordedHistoryLst = new ArrayList<>();
        }
        return recordedHistoryLst;
    }

    public void setRecordedHistoryLst(List<PrmsPurchaseRequest> recordedHistoryLst) {
        this.recordedHistoryLst = recordedHistoryLst;
    }

    public List<MmsStorereqDetail> getMmsStorereqDetailLst() {
        if (mmsStorereqDetailLst == null) {
            mmsStorereqDetailLst = new ArrayList<>();
        }
        return mmsStorereqDetailLst;
    }

    public void setMmsStorereqDetailLst(List<MmsStorereqDetail> mmsStorereqDetailLst) {
        this.mmsStorereqDetailLst = mmsStorereqDetailLst;
    }

    public List<MmsStorereq> getMmsStorereqLst() {
        return mmsStorereqLst;
    }

    public void setMmsStorereqLst(List<MmsStorereq> mmsStorereqLst) {
        this.mmsStorereqLst = mmsStorereqLst;
    }

    public List<PrmsMarketAssessmentDetail> getMarketAssessmentDetailLst() {
        return marketAssessmentDetailLst;
    }

    public void setMarketAssessmentDetailLst(List<PrmsMarketAssessmentDetail> marketAssessmentDetailLst) {
        this.marketAssessmentDetailLst = marketAssessmentDetailLst;
    }

    public List<MmsItemRegistration> getItemRegistrationList() {
        if (itemRegistrationList == null) {
            itemRegistrationList = new ArrayList<>();

        }
        return itemRegistrationList;
    }

    public void setItemRegistrationList(List<MmsItemRegistration> itemRegistrationList) {
        this.itemRegistrationList = itemRegistrationList;
    }

    public DataModel<PrmsPurchaseReqService> getPurchaseReqServiceMdl() {
        if (purchaseReqServiceMdl == null) {
            purchaseReqServiceMdl = new ListDataModel<>();
        }
        return purchaseReqServiceMdl;
    }

    public void setPurchaseReqServiceMdl(DataModel<PrmsPurchaseReqService> purchaseReqServiceMdl) {
        this.purchaseReqServiceMdl = purchaseReqServiceMdl;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {

        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<DocumentModel> getDMdocumentModel() {
        return DMdocumentModel;
    }

    public void setDMdocumentModel(DataModel<DocumentModel> DMdocumentModel) {
        this.DMdocumentModel = DMdocumentModel;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }

    public DataModel<PrmsPurchaseRequest> getPurchaseRequestMdl() {
        if (purchaseRequestMdl == null) {
            purchaseRequestMdl = new ListDataModel<>();
        }
        return purchaseRequestMdl;
    }

    public void setPurchaseRequestMdl(DataModel<PrmsPurchaseRequest> purchaseRequestMdl) {
        this.purchaseRequestMdl = purchaseRequestMdl;
    }

    public DataModel<PrmsPurchaseRequestDet> getEepPurchaseReqDetMdl() {
        if (eepPurchaseReqDetMdl == null) {
            eepPurchaseReqDetMdl = new ListDataModel<>();
        }
        return eepPurchaseReqDetMdl;
    }

    public void setEepPurchaseReqDetMdl(DataModel<PrmsPurchaseRequestDet> eepPurchaseReqDetMdl) {
        this.eepPurchaseReqDetMdl = eepPurchaseReqDetMdl;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters and setters of variables">
    public PrmsPurchaseReqService getPrmsPurchaseReqServiceSelection() {
        if (prmsPurchaseReqServiceSelection == null) {
            prmsPurchaseReqServiceSelection = new PrmsPurchaseReqService();
        }
        return prmsPurchaseReqServiceSelection;
    }

    public void setPrmsPurchaseReqServiceSelection(PrmsPurchaseReqService prmsPurchaseReqServiceSelection) {
        this.prmsPurchaseReqServiceSelection = prmsPurchaseReqServiceSelection;
    }

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }

    public PrmsPurchaseRequest getPrmsPurchaseRequestSelect() {
        return prmsPurchaseRequestSelect;
    }

    public void setPrmsPurchaseRequestSelect(PrmsPurchaseRequest prmsPurchaseRequestSelect) {
        this.prmsPurchaseRequestSelect = prmsPurchaseRequestSelect;
    }

    public PrmsPurchaseRequestDet getPurchaseRequestDetSelection() {
        return purchaseRequestDetSelection;
    }

    public void setPurchaseRequestDetSelection(PrmsPurchaseRequestDet purchaseRequestDetSelection) {
        this.purchaseRequestDetSelection = purchaseRequestDetSelection;

    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void setViewHistoryCountCounter(Integer ViewHistoryCountCounter) {
        this.ViewHistoryCountCounter = ViewHistoryCountCounter;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public int getItemRowSelectIndex() {
        return itemRowSelectIndex;
    }

    public void setItemRowSelectIndex(int itemRowSelectIndex) {
        this.itemRowSelectIndex = itemRowSelectIndex;
    }

    public int getPr_QuantitySrDetail() {
        return pr_QuantitySrDetail;
    }

    public void setPr_QuantitySrDetail(int pr_QuantitySrDetail) {
        this.pr_QuantitySrDetail = pr_QuantitySrDetail;
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

    public String getRequistorType() {
        return requistorType;
    }

    public void setRequistorType(String requistorType) {
        this.requistorType = requistorType;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String PurchaseType) {
        this.PurchaseType = PurchaseType;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getPrno() {
        return prno;
    }

    public void setPrno(String prno) {
        this.prno = prno;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSaveupdate() {
        return saveupdate;
    }

    public void setSaveupdate(String saveupdate) {
        this.saveupdate = saveupdate;
    }

    public String getDecision() {
        if (decision == null) {
            decision = new String();
        }
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public boolean ismItemName() {
        return mItemName;
    }

    public void setmItemName(boolean mItemName) {
        this.mItemName = mItemName;
    }

    public boolean ismItemCode() {
        return mItemCode;
    }

    public void setmItemCode(boolean mItemCode) {
        this.mItemCode = mItemCode;
    }

    public boolean ismItemUnitMeasure() {
        return mItemUnitMeasure;
    }

    public void setmItemUnitMeasure(boolean mItemUnitMeasure) {
        this.mItemUnitMeasure = mItemUnitMeasure;
    }

    public boolean issServiceName() {
        return sServiceName;
    }

    public void setsServiceName(boolean sServiceName) {
        this.sServiceName = sServiceName;
    }

    public boolean issServiceCode() {
        return sServiceCode;
    }

    public void setsServiceCode(boolean sServiceCode) {
        this.sServiceCode = sServiceCode;
    }

    public boolean issServiceUntMeasure() {
        return sServiceUntMeasure;
    }

    public void setsServiceUntMeasure(boolean sServiceUntMeasure) {
        this.sServiceUntMeasure = sServiceUntMeasure;
    }

    public boolean isDisableRequisitor() {
        return disableRequisitor;
    }

    public void setDisableRequisitor(boolean disableRequisitor) {
        this.disableRequisitor = disableRequisitor;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }

    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }

    public boolean isDisableSrNo() {
        return disableSrNo;
    }

    public void setDisableSrNo(boolean disableSrNo) {
        this.disableSrNo = disableSrNo;
    }

    public boolean isRenderPnlCreatePr() {
        return renderPnlCreatePr;
    }

    public void setRenderPnlCreatePr(boolean renderPnlCreatePr) {
        this.renderPnlCreatePr = renderPnlCreatePr;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public boolean isRenderMarketTable() {
        return renderMarketTable;
    }

    public void setRenderMarketTable(boolean renderMarketTable) {
        this.renderMarketTable = renderMarketTable;
    }

    public boolean isRenderWorkTable() {
        return renderWorkTable;
    }

    public void setRenderWorkTable(boolean renderWorkTable) {
        this.renderWorkTable = renderWorkTable;
    }

    public boolean isRenderStoreTbale() {
        return renderStoreTbale;
    }

    public void setRenderStoreTbale(boolean renderStoreTbale) {
        this.renderStoreTbale = renderStoreTbale;
    }

    public boolean isRenderDepartmentTbale() {
        return renderDepartmentTbale;
    }

    public void setRenderDepartmentTbale(boolean renderDepartmentTbale) {
        this.renderDepartmentTbale = renderDepartmentTbale;
    }

    public boolean isRenderService() {
        return renderService;
    }

    public void setRenderService(boolean renderService) {
        this.renderService = renderService;
    }

    public boolean isRenderSrNo() {
        return renderSrNo;
    }

    public void setRenderSrNo(boolean renderSrNo) {
        this.renderSrNo = renderSrNo;
    }

    public boolean isWorkRadio() {
        return workRadio;
    }

    public void setWorkRadio(boolean workRadio) {
        this.workRadio = workRadio;
    }

    public boolean isIsRendercreate() {
        return isRendercreate;
    }

    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }

    public boolean isIsItemchoosed() {
        return isItemchoosed;
    }

    public void setIsItemchoosed(boolean isItemchoosed) {
        this.isItemchoosed = isItemchoosed;
    }

    public boolean isRenderSeriveName() {
        return renderSeriveName;
    }

    public void setRenderSeriveName(boolean renderSeriveName) {
        this.renderSeriveName = renderSeriveName;
    }

    public boolean isRenderItemName() {
        return renderItemName;
    }

    public void setRenderItemName(boolean renderItemName) {
        this.renderItemName = renderItemName;
    }

    public boolean isRenderUnitMeasure() {
        return renderUnitMeasure;
    }

    public void setRenderUnitMeasure(boolean renderUnitMeasure) {
        this.renderUnitMeasure = renderUnitMeasure;
    }

    public boolean isRederDesription() {
        return rederDesription;
    }

    public void setRederDesription(boolean rederDesription) {
        this.rederDesription = rederDesription;
    }

    public boolean isRederItemCode() {
        return rederItemCode;
    }

    public void setRederItemCode(boolean rederItemCode) {
        this.rederItemCode = rederItemCode;
    }

    public boolean isRederServiceCode() {
        return rederServiceCode;
    }

    public void setRederServiceCode(boolean rederServiceCode) {
        this.rederServiceCode = rederServiceCode;
    }

    public boolean isViewonly() {
        return viewonly;
    }

    public void setViewonly(boolean viewonly) {
        this.viewonly = viewonly;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public boolean isItems() {
        return items;
    }

    public void setItems(boolean items) {
        this.items = items;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public TreeNode getSelectedNode() {

        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<PrmsPurchaseRequest> getPurchaseReqSearchParameterLst() {
        if (purchaseReqSearchParameterLst == null) {
            purchaseReqSearchParameterLst = new ArrayList<>();
            purchaseReqSearchParameterLst = purchaseReqBeanLocal.getParameterList();
        }
        return purchaseReqSearchParameterLst;
    }

    public void setPurchaseReqSearchParameterLst(List<PrmsPurchaseRequest> purchaseReqSearchParameterLst) {
        this.purchaseReqSearchParameterLst = purchaseReqSearchParameterLst;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="change Events">
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepPurchaseRequest.setColumnName(event.getNewValue().toString());
            eepPurchaseRequest.setColumnValue(null);
        }
    }

    public void rowSelect(SelectEvent event) {
        eepPurchaseRequest = (PrmsPurchaseRequest) event.getObject();
        renderPnlToSearchPage = true;
        getActors();
        populateDateForApp();
    }

    public void PopUpGoods(SelectEvent evented) {
        isItemchoosed = true;
        eepPurchaseReqDet = (PrmsPurchaseRequestDet) evented.getObject();
        prmsMarketAssessmentDetail = eepPurchaseReqDet.getMarktAssDtlId();

        marketAssessmentDetailLst = purchaseReqBeanLocal.getFilteredItemByGLId(fmsOperatingBudgetDetail);

        itemRowSelectIndex = getEepPurchaseReqDetMdl().getRowIndex();
        recreatModel();

    }

    public void popUpServices(SelectEvent ev) {
        prmsServiceAndWorkReg = prmsPurchaseReqService.getServiceId();
        prmsPurchaseReqService = (PrmsPurchaseReqService) ev.getObject();
    }

    public void changePrRqType(ValueChangeEvent e) {
        eepPurchaseRequest.setRequistor(requistorType);
        if (null != e.getNewValue()) {
            requistorType = e.getNewValue().toString();
            if (requistorType.equalsIgnoreCase("department")) {
                service = true;
                workRadio = false;
                renderStoreTbale = false;
                renderMarketTable = true;
                renderWorkTable = true;
                PurchaseType = "service";
            } else if (requistorType.equalsIgnoreCase("sr")) {
                workRadio = false;
                renderStoreTbale = true;
                renderMarketTable = false;
                renderWorkTable = false;
                service = false;
            }
        }
    }

    public void getServiceInfoByGL(ValueChangeEvent eve) {
        if (!eve.getNewValue().toString().isEmpty()) {
            fmsOperatingBudgetDetail = (FmsOperatingBudgetDetail) eve.getNewValue();
            prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
            prmsMarketAssmntService = purchaseReqBeanLocal.getServiceInfoByGL(fmsOperatingBudgetDetail);

            prmsPurchaseReqService.setServiceId(prmsMarketAssmntService.getServiceId());
            prmsPurchaseReqService.setServiceType(prmsMarketAssmntService.getServiceId().getServiceType());
            prmsPurchaseReqService.setUnitMeasure(prmsMarketAssmntService.getServiceId().getUnitMeasures());
            prmsPurchaseReqService.setUnitPrice(prmsMarketAssmntService.getUnitPrice());
        }
    }

    public void getWorkInfoByGL(ValueChangeEvent eve) {
        if (eve.getNewValue() != null) {
            fmsOperatingBudgetDetail = (FmsOperatingBudgetDetail) eve.getNewValue();
            prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
            prmsMarketAssmntService = purchaseReqBeanLocal.getWorkInfoByGL(fmsOperatingBudgetDetail);

            prmsPurchaseReqService.setServiceId(prmsMarketAssmntService.getServiceId());
            prmsPurchaseReqService.setUnitMeasure(prmsMarketAssmntService.getServiceId().getUnitMeasures());
            prmsPurchaseReqService.setUnitPrice(prmsMarketAssmntService.getUnitPrice());
        }
    }

    public void changeEventServiceNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            for (int i = 0; i < marketAssessmentDetailLst.size(); i++) {
                eepPurchaseReqDet = new PrmsPurchaseRequestDet();
                eepPurchaseReqDet.setServiceId(marketAssessmentDetailLst.get(i).getServiceId());
                eepPurchaseReqDet.setMatcode(String.valueOf(marketAssessmentDetailLst.get(i).getServiceId().getServiceNo()));
                eepPurchaseReqDet.setMatname(String.valueOf(marketAssessmentDetailLst.get(i).getServiceId().getName()));
                eepPurchaseReqDet.setUnitPrice(marketAssessmentDetailLst.get(i).getUnitPrice());
                eepPurchaseReqDet.setReqQuantity(marketAssessmentDetailLst.get(i).getQuantity());
                eepPurchaseReqDet.setUnitMeasre(String.valueOf(marketAssessmentDetailLst.get(i).getServiceId().getDescription()));
                eepPurchaseRequest.getPrmsPurchaseRequestDetList().add(eepPurchaseReqDet);
            }
            recreatModel();

        }

    }

    public void changeWorkNames(ValueChangeEvent changeWork) {
        if (null != changeWork.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) changeWork.getNewValue();
            prmsMarketAssmntServiceWork = purchaseReqBeanLocal.workNameChanging(prmsServiceAndWorkReg);
            prmsPurchaseReqService.setUnitPrice(prmsMarketAssmntServiceWork.getUnitPrice());
            prmsPurchaseReqService.setUnitMeasure(prmsServiceAndWorkReg.getUnitMeasures());

        }
        reCreatService();
    }
    int rowIndex;

    public void serviceRowSelected(SelectEvent ev) {
        isSelected = true;
        addOrModifyBundle = "Modify";
        prmsPurchaseReqService = (PrmsPurchaseReqService) ev.getObject();
//        rownum = eepPurchaseRequest.getPrmsPurchaseReqServiceList().indexOf((PrmsPurchaseReqService) ev.getObject());

        if (PurchaseType.equals("service")) {
            duplicatingService.remove(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode());
            eepPurchaseRequest.getPrmsPurchaseReqServiceList().remove(prmsPurchaseReqService);

        } else if (PurchaseType.equals("work")) {
            duplicatingWorkName.remove(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode());
            eepPurchaseRequest.getPrmsPurchaseReqServiceList().remove(prmsPurchaseReqService);
        }

        prmsServiceAndWorkReg = prmsPurchaseReqService.getServiceId();
        fmsOperatingBudgetDetail = prmsPurchaseReqService.getBudgetCode();
        reCreatService();
    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreatePr = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = purchaseRequestMdl.getRowIndex();
        eepPurchaseRequest = prmsPurchaseRequestLst.get(rowIndex);

        recreat();

    }

    public void rowSelectFile(SelectEvent event) {

        prmsLuDmArchive = (PrmsLuDmArchive) event.getObject();
        setIsRowFileSelected(true);
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepPurchaseRequest = (PrmsPurchaseRequest) event.getNewValue();
            populateDateForApp();
            getActors();
        }
    }

    public void storeDetailPopUp(SelectEvent selected) {
        if (selected.getObject() != null) {
            eepPurchaseReqDet = (PrmsPurchaseRequestDet) selected.getObject();
            mmsStorereq = eepPurchaseReqDet.getStDetailId();
        }
    }

    public void getBudgetAmount(ValueChangeEvent event) {
        if (!(event.getNewValue().toString().isEmpty()) && event.getNewValue() != null) {
            fmsOperatingBudgetDetail = (FmsOperatingBudgetDetail) event.getNewValue();

            marketAssessmentDetailLst = purchaseReqBeanLocal.getFilteredItemByGLId(fmsOperatingBudgetDetail);
        }
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (event.getTreeNode() != null) {
            selectedNode = event.getTreeNode();
            int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
            System.out.println(selectedNode.getData().toString());
            hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
            eepPurchaseRequest.setReqstrDepId(hrDepartments);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').hide();");
            if (PurchaseType.equals("service")) {
                getBudgetCodeLists();
            } else if (PurchaseType.equals("work")) {
                getBudgetCodeListsWhenWork();
            }
            prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);

        }
    }

    public void searchByMarketId(SelectEvent event) {
        eepPurchaseRequest.setPrNumber(event.getObject().toString());
        eepPurchaseRequest = purchaseReqBeanLocal.getPurchReqList(eepPurchaseRequest);
        recreatModel();
        update = 1;
        saveupdate = "Update";
    }

    public void valueChangeServiceName(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            String sRNo = event.getNewValue().toString();
            prmsMarketAssmntServiceLst = purchaseReqBeanLocal.getServiceNo(sRNo);
        }
    }

    public void ChangeServiceName(ValueChangeEvent event) {
        if (null != event.getNewValue().toString() && (allDepartmentsList != null)) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            prmsPurchaseReqService.setServiceId(prmsServiceAndWorkReg);

            prmsMarketAssmntService = purchaseReqBeanLocal.getUP(prmsServiceAndWorkReg);
            prmsPurchaseReqService.setUnitPrice(prmsMarketAssmntService.getUnitPrice());

            prmsPurchaseReqService.setUnitMeasure(prmsServiceAndWorkReg.getUnitMeasures());

        }
    }

    public void valueChange(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            String sRNo = event.getNewValue().toString();
            hrDepartments = purchaseReqBeanLocal.getdepName(sRNo);
            mmsStorereq.setDeptId(hrDepartments);
            mmsStorereqDetailLst = purchaseReqBeanLocal.getSrNo(sRNo);
            for (int i = 0; i < mmsStorereqDetailLst.size(); i++) {
                eepPurchaseReqDet = new PrmsPurchaseRequestDet();
                eepPurchaseReqDet.setPurchaseReqId(eepPurchaseRequest);
                eepPurchaseReqDet.setStDetailId(mmsStorereqDetailLst.get(i).getStoreReqId());
                eepPurchaseReqDet.setMaterialCodeId(mmsStorereqDetailLst.get(i).getItemId());
                eepPurchaseReqDet.setUnitPrice((mmsStorereqDetailLst.get(i).getUnitPrice()));
                eepPurchaseReqDet.setReqQuantity(Integer.parseInt(mmsStorereqDetailLst.get(i).getQuantity().toString()));
                eepPurchaseRequest.getPrmsPurchaseRequestDetList().add(eepPurchaseReqDet);
            }
            recreatModel();

        }
    }

    public void handleFileUpload(FileUploadEvent event3) {
        InputStream fileByteFile_ = null;
        fileName = event3.getFile().getFileName().split("\\.")[0];
        docFormat = event3.getFile().getFileName().split("\\.")[1];
        try {
            fileByteFile_ = event3.getFile().getInputstream();
            byteData = dataUpload.extractByteArray(fileByteFile_);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Msg====" + e.getMessage());
        }
//        uploadedDocId = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        if (byteData != null) {
            FacesMessage msg = new FacesMessage("Successfully " + event3.getFile().getFileName() + " With Size" + event3.getFile().getSize() + " is Uploaded");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void changeItemService(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            prmsPurchaseReqService = new PrmsPurchaseReqService();
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
            PurchaseType = e.getNewValue().toString();
            eepPurchaseRequest.getPrmsPurchaseReqServiceList().clear();
            purchaseReqServiceMdl = new ListDataModel<>(new ArrayList<>(eepPurchaseRequest.getPrmsPurchaseReqServiceList()));
            duplicatingService = new HashSet<>();
            duplicatingWorkName = new HashSet<>();
            if (PurchaseType.equalsIgnoreCase("service")) {
                fmsOperatingBudgetDetail = null;
                mItemCode = false;
                mItemName = false;
                mItemUnitMeasure = false;
                sServiceName = true;
                sServiceCode = true;
                sServiceUntMeasure = true;
                service = true;
                items = false;
                workRadio = false;

            } else if (PurchaseType.equalsIgnoreCase("work")) {
                fmsGeneralLedger = null;
                workRadio = true;
                items = false;
                service = false;

            }
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="other methods">
    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }

    public String getActors() {

        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = Utility.getBundleValue(systemBundle, "dataSet");
        if (eepPurchaseRequest.getStatus() == 3 || eepPurchaseRequest.getStatus() == 4
                || eepPurchaseRequest.getStatus() == 1 || eepPurchaseRequest.getStatus() == 2) {
            System.out.println("decision is " + eepPurchaseRequest.getStatus() + " So viewing only Allowed for You!!");
            viewonly = true;
        } else {
            viewonly = false;
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        for (int countSize = 0; countSize < eepPurchaseRequest.getWfPrmsProcessedLists().size(); countSize++) {
            if (countSize >= 0) {
                int actorId = eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).getProcessedBy();
                decision = eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).getDecision();
                actorName = security.getUserName(actorId, dataset);
                String comment = eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).getCommentGiven();
                Date processedDate = eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).getProcessedOn();
                if (decision.equals("3") || decision.equals("4")) {
                    eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).setDecision("Approver");
                    System.out.println("workFlow id " + eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).getProcessedId());
                    if (decision.equals("3")) {
                        wfPrmsProcessed.setDecision("Approved");
                    } else if (decision.equals("4")) {
                        wfPrmsProcessed.setDecision("Rejected");
                    }
                    wfPrmsProcessed.setCommentGiven(comment);
                    wfPrmsProcessed.setProcessedOn(processedDate);
                }
                if (decision.equals("1") || decision.equals("2")) {
                    eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).setDecision("Checker");
                    if (decision.equals("1")) {
                        wfPrmsProcessed.setDecision("Approved");
                    } else if (decision.equals("2")) {
                        wfPrmsProcessed.setDecision("Rejected");
                    }
                }
                if (decision.equals("0")) {
                    eepPurchaseRequest.getWfPrmsProcessedLists().get(countSize).setDecision("Preparer");
                }
            }
        }
        return actors;
    }

    public void clearAddedGoods() {
        prmsMarketAssessmentDetail = null;
        prmsPurchaseReqService = null;
        eepPurchaseReqDet = null;
        mmsItemRegistration = null;
        fmsOperatingBudgetDetail = null;
    }

    public void addServOrWorkToPRDetailTable() {

        if (isSelected == false) {
            if (PurchaseType.equals("service")) {
                if (!duplicatingService.contains(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode())) {
                    duplicatingService.add(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode());

                    prmsPurchaseReqService.setServiceId(prmsMarketAssmntService.getServiceId());
                    prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
                    prmsPurchaseReqService.setPurchaseReqId(eepPurchaseRequest);
                    eepPurchaseRequest.getPrmsPurchaseReqServiceList().add(prmsPurchaseReqService);
                    reCreatService();
                    clearOnServDtl();
                } else {
                    JsfUtil.addFatalMessage("Duplicating Budget code is Impossible When Service");
                }
            } else if (PurchaseType.equals("work")) {
                if (!duplicatingWorkName.contains(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode())) {
                    duplicatingWorkName.add(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode());

                    prmsPurchaseReqService.setServiceId(prmsMarketAssmntService.getServiceId());
                    prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
                    prmsPurchaseReqService.setPurchaseReqId(eepPurchaseRequest);
                    eepPurchaseRequest.getPrmsPurchaseReqServiceList().add(prmsPurchaseReqService);
                    reCreatService();
                    clearOnServDtl();
                } else {
                    JsfUtil.addFatalMessage("Duplicating Budget code is Impossible When Work");
                }
            }
        } else if (isSelected == true) {
            if (PurchaseType.equals("service")) {
                if (!duplicatingService.contains(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode())) {
                    duplicatingService.add(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode());
                    prmsMarketAssmntService = purchaseReqBeanLocal.getServiceInfoByGL(fmsOperatingBudgetDetail);

                    prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
                    prmsPurchaseReqService.setServiceId(prmsMarketAssmntService.getServiceId());
                    prmsPurchaseReqService.setPurchaseReqId(eepPurchaseRequest);
                    eepPurchaseRequest.getPrmsPurchaseReqServiceList().add(prmsPurchaseReqService);
                    reCreatService();
                    clearOnServDtl();
                } else {
                    JsfUtil.addFatalMessage("This Budget code is Already Registared When Service");
                }

            } else if (PurchaseType.equals("work")) {
                if (!duplicatingWorkName.contains(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode())) {
                    duplicatingWorkName.add(prmsPurchaseReqService.getBudgetCode().getGeneralLedger().getGeneralLedgerCode());
                    prmsMarketAssmntService = purchaseReqBeanLocal.getWorkInfoByGL(fmsOperatingBudgetDetail);

                    prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
                    prmsPurchaseReqService.setServiceId(prmsMarketAssmntService.getServiceId());
                    prmsPurchaseReqService.setPurchaseReqId(eepPurchaseRequest);
                    eepPurchaseRequest.getPrmsPurchaseReqServiceList().add(prmsPurchaseReqService);
                    reCreatService();
                    clearOnServDtl();
                } else {
                    JsfUtil.addFatalMessage("This Budget code is Already Registared When Work");
                }
            }
        }

    }

    public String generatePrNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newPrNo = eepPurchaseRequest.getPrNumber();
        } else {
            if (requistorType != null) {
                newPrNo = getPrDeptOrStoreSeqNo(requistorType);
            }
        }
        return newPrNo;
    }

    public String getPrDeptOrStoreSeqNo(String requisitorType) {
        String prDept_Store_No = purchaseReqBeanLocal.getPrDeptOrStoreSeqNo(requisitorType);
        return prDept_Store_No;
    }

    public void populateDateForApp() {
        eepPurchaseRequest.setId(eepPurchaseRequest.getId());
        eepPurchaseRequest = purchaseReqBeanLocal.getSelectedRequest(eepPurchaseRequest.getId());

        hrDepartments = eepPurchaseRequest.getReqstrDepId();
        PurchaseType = eepPurchaseRequest.getPurchaseType();

        prmsPurchaseReqService.setServiceType(prmsPurchaseReqService.getServiceType());
        if (!saveorUpdateBundle.equals("Update") && workflow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(eepPurchaseRequest.getDateRequested());
        }
        renderPnlManPage = false;
        renderPnlCreatePr = true;
        saveorUpdateBundle = "Update";
        requistorType = eepPurchaseRequest.getRequistor();
        disableRequisitor = true;
        if (requistorType.equals("department")) {
            if (eepPurchaseRequest.getPurchaseType().equals("service")) {
                service = true;
                workRadio = false;
                items = false;
                reCreatService();
                //to gain budget code registared in previous for duplication handling purpose on service
                for (int k = 0; k < eepPurchaseRequest.getPrmsPurchaseReqServiceList().size(); k++) {
                    duplicatingService.add(eepPurchaseRequest.getPrmsPurchaseReqServiceList().get(k).getBudgetCode().getGeneralLedger().getGeneralLedgerCode());
                }
            } else if (eepPurchaseRequest.getPurchaseType().equals("work")) {
                //to gain budget code registared in previous for duplication handling purpose on work
                for (int k = 0; k < eepPurchaseRequest.getPrmsPurchaseReqServiceList().size(); k++) {
                    duplicatingWorkName.add(eepPurchaseRequest.getPrmsPurchaseReqServiceList().get(k).getBudgetCode().getGeneralLedger().getGeneralLedgerCode());
                }
                workRadio = true;
                items = false;
                service = false;
                reCreatService();
            }
            renderMarketTable = true;
            renderStoreTbale = false;

        } else if (requistorType.equalsIgnoreCase("sr")) {
            mmsStorereq = eepPurchaseReqDet.getStDetailId();
            renderMarketTable = false;
            renderStoreTbale = true;
            service = false;
            workRadio = false;
            recreatModel();
        }
        if (eepPurchaseRequest.getDocumentId() != null) {
            prmsLuDmArchive = eepPurchaseRequest.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
        }
        recreateWorkFlow();
        recreateFileUpload();
    }

    public ArrayList<PrmsPurchaseRequest> searchPurchaseReq(String purchaseReNo) {

        ArrayList<PrmsPurchaseRequest> eepPurchaseRequests = null;
        eepPurchaseRequests = purchaseReqBeanLocal.searchPurchaseReqNo(eepPurchaseRequest);

        eepPurchaseRequest.setPrNumber(purchaseReNo);
        return eepPurchaseRequests;
    }

    public void getDocValue() {
        DMdocumentModel = dataUpload.selectListOfFileByDocId(documentId);
    }

    public void clearSearch() {
        eepPurchaseReqDet = null;
        eepPurchaseReqDetMdl = null;
        eepPurchaseRequest = null;
    }

    public void createNewPr() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderPnlToSearchPage = false;
        wfPrmsProcessed.setProcessedBy(workflow.getUserAccount());
        setUserName(workflow.getUserName());
        if (createOrSearchBundle.equals("New")) {
            clear();
            clearOnServDtl();
            disableRequisitor = false;
            renderPnlCreatePr = true;
            renderPnlManPage = false;
            service = true;
            workRadio = false;
            renderStoreTbale = false;
            renderMarketTable = true;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreatePr = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }

    }

    public void goBackSearchButtonAction() {
        renderPnlToSearchPage = false;
        renderPnlCreatePr = false;
        renderPnlManPage = true;
    }

    public void removeService() {
        int rowIndex = purchaseReqServiceMdl.getRowIndex();
        prmsPurchaseReqService = eepPurchaseRequest.getPrmsPurchaseReqServiceList().get(rowIndex);
        eepPurchaseRequest.getPrmsPurchaseReqServiceList().remove(rowIndex);
        reCreatService();
    }

    public void delete() {
        int rowIndex = purchaseReqServiceMdl.getRowIndex();
        prmsPurchaseReqService = purchaseReqServiceMdl.getRowData();
    }

    public void removeItem() {
        int rowIndex = eepPurchaseReqDetMdl.getRowIndex();
        eepPurchaseReqDet = eepPurchaseRequest.getPrmsPurchaseRequestDetList().get(rowIndex);
        eepPurchaseRequest.getPrmsPurchaseRequestDetList().remove(rowIndex);
        recreatModel();

    }

    public SelectItem[] getItemCod() {
        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.getItemCodes(), true);
    }

    public void searchPurchaseRequisitionInfo() {
        eepPurchaseRequest.setPreparedBy(workflow.getUserAccount());
        prmsPurchaseRequestLst = purchaseReqBeanLocal.searchByPrNo(eepPurchaseRequest);
        recreat();
        eepPurchaseRequest = new PrmsPurchaseRequest();
    }

    public List<PrmsMarketAssmntService> getServiceName() {
        setPrmsMarketAssmntServiceLst(purchaseReqBeanLocal.getServName());
        return prmsMarketAssmntServiceLst;
    }

    public List<PrmsMarketAssmntService> getServiceNameNon() {
        setPrmsMarketAssmntServiceLst(purchaseReqBeanLocal.getServNameNon());
        return prmsMarketAssmntServiceLst;
    }

    public SelectItem[] itemCode() {

        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.ItemCodeList(), true);
    }

    public SelectItem[] deprtmnt() {
        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.depList(), true);
    }

    public SelectItem[] serviceNo() {
        return JsfUtil.getSelectItems(purchaseReqBeanLocal.getServiceNo(), true);
    }

    public SelectItem[] ItemName() {
        return JsfUtil.getSelectItems(purchaseReqBeanLocal.getItemName(), true);
    }

    public SelectItem[] serviceName() {
        return JsfUtil.getSelectItems(purchaseReqBeanLocal.getServiceName(), true);
    }

    public Integer getRequestNotificationCounter() {
        prmsPurchaseRequestLst = purchaseReqBeanLocal.getPurchaseReqOnLists();
        requestNotificationCounter = prmsPurchaseRequestLst.size();
        return requestNotificationCounter;
    }

    public Integer getViewHistoryCountCounter() {
        recordedHistoryLst = purchaseReqBeanLocal.getViewHistoryLists();
        ViewHistoryCountCounter = recordedHistoryLst.size();
        return ViewHistoryCountCounter;
    }

    public StreamedContent getContent() throws Exception {
        if (isRowFileSelected == true) {
            content = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Please Select Row File U want to Download");
        }
        return content;
    }

    public Date approveddate() {
        return this.eepPurchaseRequest.getPrDate();
    }

    public void reCreatService() {
        purchaseReqServiceMdl = null;
        purchaseReqServiceMdl = new ListDataModel<>(new ArrayList<>(eepPurchaseRequest.getPrmsPurchaseReqServiceList()));
    }

    public void clearOnServDtl() {
        prmsPurchaseReqService = null;
        prmsServiceAndWorkReg = null;
        prmsMarketAssmntService = null;
        prmsMarketAssmntServiceWork = null;
        fmsOperatingBudgetDetail = null;
        isSelected = false;
        addOrModifyBundle = "Add";
    }

    public void recreat() {
        purchaseRequestMdl = null;
        purchaseRequestMdl = new ListDataModel(new ArrayList(getPrmsPurchaseRequestLst()));
    }

    public void recreatModel() {
        eepPurchaseReqDetMdl = null;
        eepPurchaseReqDetMdl = new ListDataModel(new ArrayList<>(eepPurchaseRequest.getPrmsPurchaseRequestDetList()));
    }

    public void recreateWorkFlow() {
        wfPrmsProcessedDModel = null;
        wfPrmsProcessedDModel = new ListDataModel(eepPurchaseRequest.getWfPrmsProcessedLists());
    }

    public void recreateFileUpload() {
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public void clear() {
        eepPurchaseRequest = null;
        saveorUpdateBundle = "Save";
        eepPurchaseReqDet = null;
        mmsItemRegistration = null;
        prmsMarketAssessmentDetail = null;
        purchaseRequestMdl = null;
        eepPurchaseReqDetMdl = null;
        purchaseReqServiceMdl = null;
        prmsPurchaseRequestLst = null;
        prmsLuDmArchivesDModel = null;
        hrDepartments = null;
        mmsStorereq = null;
        fmsOperatingBudgetDetail = null;
        PurchaseType = "service";
        requistorType = "department";
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
        if (workflow.isPrepareStatus()) {
            disableRequisitor = false;
        } else if (!workflow.isPrepareStatus()) {
            disableRequisitor = true;
        }
        service = true;
        workRadio = false;
        renderStoreTbale = false;
        renderMarketTable = true;
        wfPrmsProcessedDModel = null;
        renderPnlToSearchPage = false;
        DMdocumentModel = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setDecision(null);
        wfPrmsProcessed.setCommentGiven(null);
        addOrModifyBundle = "Add";

    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed"  desc="Save or Update methods">
    public String savePurchaseRequisitionInfo() {
        try {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBean.getUserName(), "savePurchaseRequisitionInfo", dataset)) {

                    if (saveorUpdateBundle.equals("Save")) {
                        generatePrNo();
                        eepPurchaseRequest.setPrNumber(newPrNo);
                        eepPurchaseRequest.setDateRequested(wfPrmsProcessed.getProcessedOn());
                        eepPurchaseRequest.setStatus(Constants.PREPARE_VALUE);
                        if (purchaseReqServiceMdl.getRowCount() <= 0 || eepPurchaseReqDetMdl.getRowCount() <= 0) {
                            JsfUtil.addFatalMessage("Atleast One Detail Data Should Added");
                        } else {
                            if (requistorType.equalsIgnoreCase("department")) {
                                eepPurchaseRequest.setRequistor(requistorType);
                                eepPurchaseRequest.setReqstrDepId(hrDepartments);
                                prmsPurchaseReqService.setPurchaseReqId(eepPurchaseRequest);

                                if (PurchaseType.equalsIgnoreCase("service")) {
                                    prmsPurchaseReqService.setServiceId(prmsServiceAndWorkReg);
                                    eepPurchaseRequest.setPurchaseType("service");
                                    prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
                                } else if (PurchaseType.equals("work")) {
                                    prmsPurchaseReqService.setServiceId(prmsServiceAndWorkReg);
                                    eepPurchaseRequest.setPurchaseType("work");
                                    prmsPurchaseReqService.setBudgetCode(fmsOperatingBudgetDetail);
                                }

                            } else if (requistorType.equalsIgnoreCase("sr")) {
                                eepPurchaseRequest.setRequistor("sr");
                                eepPurchaseRequest.setPurchaseType("Goods");
                                eepPurchaseReqDet.setStDetailId(mmsStorereq);
                                eepPurchaseReqDet.setPurchaseReqId(eepPurchaseRequest);
                            }
                            eepPurchaseRequest.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                            wfPrmsProcessed.setPurchaseReqId(eepPurchaseRequest);
                            wfPrmsProcessed.setDecision(String.valueOf(eepPurchaseRequest.getStatus()));
                            eepPurchaseRequest.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(docFormat);
                            prmsLuDmArchive.setUploadFile(byteData);
                            if (prmsLuDmArchive.getFileName() != null) {
                                eepPurchaseRequest.setDocumentId(prmsLuDmArchive);
                                prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            }

                            purchaseReqBeanLocal.save(eepPurchaseRequest);
                            JsfUtil.addSuccessMessage("Purchase Request Information is Successfully Saved");
                            clear();
                        }
                    } else if (saveorUpdateBundle.equals("Update") && workflow.isPrepareStatus()) {

                        if (purchaseReqServiceMdl.getRowCount() < 1 && eepPurchaseReqDetMdl.getRowCount() < 1) {
                            JsfUtil.addFatalMessage("Atleast One Detail Data Should Added");
                        } else {
                            wfPrmsProcessed.setProcessedBy(workflow.getUserAccount());
                            eepPurchaseRequest.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            eepPurchaseRequest.setDateRequested(wfPrmsProcessed.getProcessedOn());
                            eepPurchaseRequest.setStatus(Constants.PREPARE_VALUE);

                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(docFormat);
                            prmsLuDmArchive.setUploadFile(byteData);
                            if (prmsLuDmArchive.getFileName() != null) {
                                prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                                eepPurchaseRequest.setDocumentId(prmsLuDmArchive);
                            }

                            wfPrmsProcessed.setDecision(String.valueOf(eepPurchaseRequest.getStatus()));
                            purchaseReqBeanLocal.edit(eepPurchaseRequest);
                            JsfUtil.addSuccessMessage("Purchase Request Information is Successfully Updated");
                            saveorUpdateBundle = "Save";
                            clear();
                        }

                    } else if (saveorUpdateBundle.equals("Update") && !workflow.isPrepareStatus()) {
                        if (selectedValue.equalsIgnoreCase("Approved") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_VALUE);
                            eepPurchaseRequest.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Approved") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            eepPurchaseRequest.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            eepPurchaseRequest.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            eepPurchaseRequest.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        wfPrmsProcessed.setPurchaseReqId(eepPurchaseRequest);
                        wfPrmsProcessedBeanLocal.edit(wfPrmsProcessed);
                        purchaseReqBeanLocal.edit(eepPurchaseRequest);

                        JsfUtil.addSuccessMessage("Data is Decided at" + eepPurchaseRequest.getPrNumber());
                        saveorUpdateBundle = "Save";
                        clear();

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
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("some thing occured");
        }
        return null;
    }
    //</editor-fold >
}
