/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.prms.businessLogic.BidRegBeanLocal;
import et.gov.eep.prms.businessLogic.MarketAssesmntBeanLocal;
import et.gov.eep.prms.businessLogic.PurchaseReqBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.pms.entity.PmsResources;
import et.gov.eep.prms.entity.PrmsAnnualPlanService;
import et.gov.eep.prms.entity.PrmsBidCriteria;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.prms.entity.PrmsPurchasePlnDetail;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("bidController")
@ViewScoped
public class BidController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="declaration and initialization">
    @EJB
    BidRegBeanLocal bidRegBeanLocal;
    @EJB
    MarketAssesmntBeanLocal marketAssesmntBeanLocal;
    @EJB
    PurchaseReqBeanLocal purchaseReqBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @Inject
    PrmsBid eepBidReg;
    @Inject
    PrmsBidDetail eepBidDetail;
    @Inject
    PrmsPurchaseRequest prmsPurchaseRequest;
    @Inject
    PrmsPurchaseRequestDet prmsPurchaseRequestDet;
    @Inject
    PrmsBidCriteria prmsBidCriteria;
    @Inject
    PrmsPurchasePlan purchasePlan;
    @Inject
    PrmsPurchasePlnDetail purchasePlnDetail;
    @Inject
    MmsItemRegistration mmsItemRegistration;
    @Inject
    PrmsServiceAndWorkReg prmsServiceAndWorkReg;
    @Inject
    PrmsAnnualPlanService prmsAnnualPlanService;
    @Inject
    PrmsPurchaseReqService prmsPurchaseReqService;
    @Inject
    PrmsPurchasePlnDetail prmsPurchasePlnDetail;
    @Inject
    PrmsPurchasePlan prmsPurchasePlan;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    DataUpload dataUpload;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    @Inject
    PmsResources pmsResources;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    @Inject
    PmsCreateProjects pmsCreateProjects;

    DataModel<PrmsBidDetail> prmsBidDetailMdl;
    DataModel<PrmsBid> prmsBidModel;
    DataModel<WfPrmsProcessed> workFlowDataModel;
    DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    List<MmsItemRegistration> itemRegistrationList;
    List<PrmsPurchaseRequest> purchaseRequestList;
    List<PrmsPurchaseRequestDet> purchaseRequestDetList;
    List<PrmsBid> prmsBidList;
    List<PrmsPurchasePlnDetail> purchasePlnDetailLst;
    List<PrmsPurchaseReqService> prmsPurchaseReqServiceList;
    List<PrmsServiceAndWorkReg> prmsServiceAndWorkRegList;
    List<PmsCreateProjects> pmsCreateProjectseList;
    List<FmsLuCurrency> fmsLuCurrencyList;
    Set<String> checkItem = new HashSet<>();
    List<PrmsBid> bidSearchParameterLst;
    Integer requestNotificationCounter = 0;
    String selectedValue = "";
    String userName = "";
    private String saveupdate = "Save";
    List<Integer> documentId = new ArrayList<>();
    private boolean merit;
    private boolean compliance;
    private boolean lotBase = true;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;

    private boolean siteVisite = true;
    private boolean MinuteNo = true;
    private boolean PrebidMeeting = true;
    private boolean preMeetingPlace = true;
    private boolean loanAndCredit;
    private boolean workName;
    private boolean documentFee = true;
    private boolean currencyfrDocument = true;
    private boolean planNoFrItem = false;
    private boolean PlanNoForWork;
    private boolean prFrItem = false;
    private boolean prfrWork;
    private boolean ItemQuantityFrmPr;
    private boolean serviceQuantityFrmPr;
    private boolean serviceAndWorkFrmPr;
    private boolean serviceFrmPr;
    private boolean prFrConsultancy;
    private boolean prFrNonConsutancy;
    private boolean itemNameFrmPr;
    private boolean ItemCodeFrmPr;
    private boolean unitMeasureFrmprFrItem;
    private boolean serviceNoFrmpr;
    private boolean servicNmaeFrmPr;
    private boolean unitMeasureFrmPrFrService;
    private boolean workNofrmpr;
    private boolean worknmeFrmPr;
    private boolean UnitMeasurefrmPrfrWork;

    private boolean itemNamefrmPlan;
    private boolean itemCodefrmPlan;
    private boolean unitMeasureFrmPlan;
    private boolean qunatityFrmPlan;

    private boolean servicefrmPlan;
    private boolean sericeNameFrmPlan;
    private boolean serviceType;
    private boolean serviceUnitMeasure;
    private boolean serviceQuantity;

    private boolean workFrmPlan;
    private boolean workNameFrmPlan;
    private boolean workunitMeasureFrmPlan;
    private boolean workQuantityFrmPlan;

    private boolean reqFromProjectPlan;

    private boolean serviceTable;
    private boolean itemTable;
    private boolean workTable;

    private boolean governoment = false;
    private boolean financialSourc = false;

    private boolean disableAnnualPlan;
    private boolean disablePurchaseReq;

    private boolean workFrmProjectPln;
    private boolean serviceFrmProjectPln;
    private boolean goodsFrmProjectPlan;

    private boolean marketApprochLocal;
    private boolean marketApprochInternational;
    private boolean rendddd = true;
    private boolean er2 = false;
    private boolean isRenderedIconWorkflow = false;
    private boolean isDecision = false;
    private boolean isCommentGiven = false;
    private boolean isCreateButton;
    private boolean isRowFileSelected;

    private boolean renderpnlToSearchPage;
    List<PrmsBidDetail> bidDetailList;
    String workNam;
    String unitMeasure;
    String newBidNum;
    String bidNum = "0";
    byte[] byteData;
    String docFormat;
    String fileName;
    String sourceOfIn = "";
    private String procurementCategory;
    String select;
    int uploadId;
    private String[] selectedBidCriteria;
    private int selectedRowIndex;
    private PrmsLuDmArchive prmsLuDmArchiveSelection;
    private String icone = "ui-icon-plus";
    PrmsBid prmsBidselected;
    List<Integer> documentLists = new ArrayList<>();
    String addOrUpdateBundle = "Add";

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Post Construct ">
    @PostConstruct
    public void init() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        fmsLuCurrencyList = marketAssesmntBeanLocal.getCurrency();
        setUserName(workFlow.getUserName());
        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            isDecision = true;
            isCommentGiven = true;
            isCreateButton = false;

        } else {
            isDecision = false;
            isCommentGiven = false;
            isCreateButton = true;
        }
    }
    //</editor-fold>
    
    

    //<editor-fold defaultstate="collapsed" desc="setter and getter">
    public List<PrmsBid> getBidSearchParameterLst() {
        if (bidSearchParameterLst == null) {
            bidSearchParameterLst = new ArrayList<>();
            bidSearchParameterLst = bidRegBeanLocal.getParamNameList();
        }
             return bidSearchParameterLst;
    }
    public void setBidSearchParameterLst(List<PrmsBid> bidSearchParameterLst) {    
        this.bidSearchParameterLst = bidSearchParameterLst;
    }

    public PrmsLuDmArchiveBeanLocal getPrmsLuDmArchiveBeanLocal() {
        return prmsLuDmArchiveBeanLocal;
    }

    public void setPrmsLuDmArchiveBeanLocal(PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal) {
        this.prmsLuDmArchiveBeanLocal = prmsLuDmArchiveBeanLocal;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getAddOrUpdateBundle() {
        return addOrUpdateBundle;
    }

    public void setAddOrUpdateBundle(String addOrUpdateBundle) {
        this.addOrUpdateBundle = addOrUpdateBundle;
    }

    public PmsCreateProjects getPmsCreateProjects() {
        if (pmsCreateProjects == null) {
            pmsCreateProjects = new PmsCreateProjects();
        }
        return pmsCreateProjects;
    }

    public void setPmsCreateProjects(PmsCreateProjects pmsCreateProjects) {
        this.pmsCreateProjects = pmsCreateProjects;
    }

    public boolean isServiceTable() {
        return serviceTable;
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepBidReg = (PrmsBid) event.getNewValue();
            popUp();
            saveorUpdateBundle = "Update";
        }
    }

    public Integer getRequestNotificationCounter() {
        prmsBidList = bidRegBeanLocal.getBidOnList();
        requestNotificationCounter = prmsBidList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
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

    public PmsResources getPmsResources() {
        if (pmsResources == null) {
            pmsResources = new PmsResources();
        }
        return pmsResources;
    }

    public void setPmsResources(PmsResources pmsResources) {
        this.pmsResources = pmsResources;
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

    public List<FmsLuCurrency> getFmsLuCurrencyList() {
        if (fmsLuCurrencyList == null) {
            fmsLuCurrencyList = new ArrayList<>();
        }
        return fmsLuCurrencyList;
    }

    public void setFmsLuCurrencyList(List<FmsLuCurrency> fmsLuCurrencyList) {
        this.fmsLuCurrencyList = fmsLuCurrencyList;
    }

    public PrmsPurchasePlnDetail getPrmsPurchasePlnDetail() {
        if (prmsPurchasePlnDetail == null) {
            prmsPurchasePlnDetail = new PrmsPurchasePlnDetail();
        }
        return prmsPurchasePlnDetail;
    }

    public void setPrmsPurchasePlnDetail(PrmsPurchasePlnDetail prmsPurchasePlnDetail) {
        this.prmsPurchasePlnDetail = prmsPurchasePlnDetail;
    }

    public void setServiceTable(boolean serviceTable) {
        this.serviceTable = serviceTable;
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

    public boolean isIsCreateButton() {
        return isCreateButton;
    }

    public void setIsCreateButton(boolean isCreateButton) {
        this.isCreateButton = isCreateButton;
    }

    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }

    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }

    public boolean isItemTable() {
        return itemTable;
    }

    public void setItemTable(boolean itemTable) {
        this.itemTable = itemTable;
    }

    public boolean isDisableAnnualPlan() {
        return disableAnnualPlan;
    }

    public void setDisableAnnualPlan(boolean disableAnnualPlan) {
        this.disableAnnualPlan = disableAnnualPlan;
    }

    public boolean isDisablePurchaseReq() {
        return disablePurchaseReq;
    }

    public void setDisablePurchaseReq(boolean disablePurchaseReq) {
        this.disablePurchaseReq = disablePurchaseReq;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public boolean isIsDecision() {
        return isDecision;
    }

    public void setIsDecision(boolean isDecision) {
        this.isDecision = isDecision;
    }

    public boolean isIsCommentGiven() {
        return isCommentGiven;
    }

    public void setIsCommentGiven(boolean isCommentGiven) {
        this.isCommentGiven = isCommentGiven;
    }

    public boolean isMarketApprochLocal() {
        return marketApprochLocal;
    }

    public void setMarketApprochLocal(boolean marketApprochLocal) {
        this.marketApprochLocal = marketApprochLocal;
    }

    public boolean isMarketApprochInternational() {
        return marketApprochInternational;
    }

    public void setMarketApprochInternational(boolean marketApprochInternational) {
        this.marketApprochInternational = marketApprochInternational;
    }

    public boolean isWorkFrmProjectPln() {
        return workFrmProjectPln;
    }

    public void setWorkFrmProjectPln(boolean workFrmProjectPln) {
        this.workFrmProjectPln = workFrmProjectPln;
    }

    public boolean isServiceFrmProjectPln() {
        return serviceFrmProjectPln;
    }

    public void setServiceFrmProjectPln(boolean serviceFrmProjectPln) {
        this.serviceFrmProjectPln = serviceFrmProjectPln;
    }

    public boolean isGoodsFrmProjectPlan() {
        return goodsFrmProjectPlan;
    }

    public void setGoodsFrmProjectPlan(boolean goodsFrmProjectPlan) {
        this.goodsFrmProjectPlan = goodsFrmProjectPlan;
    }

    public boolean isWorkTable() {
        return workTable;
    }

    public void setWorkTable(boolean workTable) {
        this.workTable = workTable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String renderprNo = "PRNo";
    List<SelectItem> evaluationType;

    public List<PmsCreateProjects> getPmsCreateProjectseList() {
        if (pmsCreateProjectseList == null) {
            pmsCreateProjectseList = new ArrayList<>();
        }
        return pmsCreateProjectseList;
    }

    public void setPmsCreateProjectseList(List<PmsCreateProjects> pmsCreateProjectseList) {
        this.pmsCreateProjectseList = pmsCreateProjectseList;
    }

    public PrmsPurchaseReqService getPrmsPurchaseReqService() {
        if (prmsPurchaseReqService == null) {
            prmsPurchaseReqService = new PrmsPurchaseReqService();
        }
        return prmsPurchaseReqService;
    }

    public void setPrmsPurchaseReqService(PrmsPurchaseReqService prmsPurchaseReqService) {
        this.prmsPurchaseReqService = prmsPurchaseReqService;
    }

    public PrmsAnnualPlanService getPrmsAnnualPlanService() {
        return prmsAnnualPlanService;
    }

    public void setPrmsAnnualPlanService(PrmsAnnualPlanService prmsAnnualPlanService) {
        this.prmsAnnualPlanService = prmsAnnualPlanService;
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

    public PrmsPurchasePlan getPrmsPurchasePlan() {
        if (prmsPurchasePlan == null) {
            prmsPurchasePlan = new PrmsPurchasePlan();
        }
        return prmsPurchasePlan;
    }

    public void setPrmsPurchasePlan(PrmsPurchasePlan prmsPurchasePlan) {
        this.prmsPurchasePlan = prmsPurchasePlan;
    }

    public DataModel<WfPrmsProcessed> getWorkFlowDataModel() {
        if (workFlowDataModel == null) {
            workFlowDataModel = new ListDataModel<>();
        }
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfPrmsProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchiveDataModel() {
        if (prmsLuDmArchiveDataModel == null) {
            prmsLuDmArchiveDataModel = new ListDataModel<>();
        }
        return prmsLuDmArchiveDataModel;
    }

    public void setPrmsLuDmArchiveDataModel(DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel) {
        this.prmsLuDmArchiveDataModel = prmsLuDmArchiveDataModel;
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

    public List<PrmsServiceAndWorkReg> getPrmsServiceAndWorkRegList() {
        if (prmsServiceAndWorkRegList == null) {
            prmsServiceAndWorkRegList = new ArrayList<>();
        }
        return prmsServiceAndWorkRegList;
    }

    public void setPrmsServiceAndWorkRegList(List<PrmsServiceAndWorkReg> prmsServiceAndWorkRegList) {
        this.prmsServiceAndWorkRegList = prmsServiceAndWorkRegList;
    }

    public List<PrmsPurchaseReqService> getPrmsPurchaseReqServiceList() {
        if (prmsPurchaseReqServiceList == null) {
            prmsPurchaseReqServiceList = new ArrayList();
        }
        return prmsPurchaseReqServiceList;
    }

    public void setPrmsPurchaseReqServiceList(List<PrmsPurchaseReqService> prmsPurchaseReqServiceList) {
        this.prmsPurchaseReqServiceList = prmsPurchaseReqServiceList;
    }

    public boolean isItemQuantityFrmPr() {
        return ItemQuantityFrmPr;
    }

    public void setItemQuantityFrmPr(boolean ItemQuantityFrmPr) {
        this.ItemQuantityFrmPr = ItemQuantityFrmPr;
    }

    public boolean isMerit() {
        return merit;
    }

    public void setMerit(boolean merit) {
        this.merit = merit;
    }

    public boolean isGovernoment() {
        return governoment;
    }

    public void setGovernoment(boolean governoment) {
        this.governoment = governoment;
    }

    public boolean isFinancialSourc() {
        return financialSourc;
    }

    public void setFinancialSourc(boolean financialSourc) {
        this.financialSourc = financialSourc;
    }

    public boolean isServiceQuantityFrmPr() {
        return serviceQuantityFrmPr;
    }

    public void setServiceQuantityFrmPr(boolean serviceQuantityFrmPr) {
        this.serviceQuantityFrmPr = serviceQuantityFrmPr;
    }

    public boolean isQunatityFrmPlan() {
        return qunatityFrmPlan;
    }

    public void setQunatityFrmPlan(boolean qunatityFrmPlan) {
        this.qunatityFrmPlan = qunatityFrmPlan;
    }

    public boolean isWorkFrmPlan() {
        return workFrmPlan;
    }

    public void setWorkFrmPlan(boolean workFrmPlan) {
        this.workFrmPlan = workFrmPlan;
    }

    public boolean isWorkNameFrmPlan() {
        return workNameFrmPlan;
    }

    public void setWorkNameFrmPlan(boolean workNameFrmPlan) {
        this.workNameFrmPlan = workNameFrmPlan;
    }

    public boolean isWorkunitMeasureFrmPlan() {
        return workunitMeasureFrmPlan;
    }

    public void setWorkunitMeasureFrmPlan(boolean workunitMeasureFrmPlan) {
        this.workunitMeasureFrmPlan = workunitMeasureFrmPlan;
    }

    public boolean isServiceAndWorkFrmPr() {
        return serviceAndWorkFrmPr;
    }

    public void setServiceAndWorkFrmPr(boolean serviceAndWorkFrmPr) {
        this.serviceAndWorkFrmPr = serviceAndWorkFrmPr;
    }

    public boolean isWorkQuantityFrmPlan() {
        return workQuantityFrmPlan;
    }

    public void setWorkQuantityFrmPlan(boolean workQuantityFrmPlan) {
        this.workQuantityFrmPlan = workQuantityFrmPlan;
    }

    public boolean isServiceQuantity() {
        return serviceQuantity;
    }

    public void setServiceQuantity(boolean serviceQuantity) {
        this.serviceQuantity = serviceQuantity;
    }

    public boolean isSericeNameFrmPlan() {
        return sericeNameFrmPlan;
    }

    public void setSericeNameFrmPlan(boolean sericeNameFrmPlan) {
        this.sericeNameFrmPlan = sericeNameFrmPlan;
    }

    public boolean isServiceUnitMeasure() {
        return serviceUnitMeasure;
    }

    public void setServiceUnitMeasure(boolean serviceUnitMeasure) {
        this.serviceUnitMeasure = serviceUnitMeasure;
    }

    public boolean isServicefrmPlan() {
        return servicefrmPlan;
    }

    public void setServicefrmPlan(boolean servicefrmPlan) {
        this.servicefrmPlan = servicefrmPlan;
    }

    public boolean isItemNameFrmPr() {
        return itemNameFrmPr;
    }

    public void setItemNameFrmPr(boolean itemNameFrmPr) {
        this.itemNameFrmPr = itemNameFrmPr;
    }

    public boolean isItemCodeFrmPr() {
        return ItemCodeFrmPr;
    }

    public void setItemCodeFrmPr(boolean ItemCodeFrmPr) {
        this.ItemCodeFrmPr = ItemCodeFrmPr;
    }

    public boolean isReqFromProjectPlan() {
        return reqFromProjectPlan;
    }

    public void setReqFromProjectPlan(boolean reqFromProjectPlan) {
        this.reqFromProjectPlan = reqFromProjectPlan;
    }

    public boolean isCurrencyfrDocument() {
        return currencyfrDocument;
    }

    public void setCurrencyfrDocument(boolean currencyfrDocument) {
        this.currencyfrDocument = currencyfrDocument;
    }

    public boolean isUnitMeasureFrmprFrItem() {
        return unitMeasureFrmprFrItem;
    }

    public void setUnitMeasureFrmprFrItem(boolean unitMeasureFrmprFrItem) {
        this.unitMeasureFrmprFrItem = unitMeasureFrmprFrItem;
    }

    public boolean isWorkName() {
        return workName;
    }

    public void setWorkName(boolean workName) {
        this.workName = workName;
    }

    public boolean isServiceFrmPr() {
        return serviceFrmPr;
    }

    public void setServiceFrmPr(boolean serviceFrmPr) {
        this.serviceFrmPr = serviceFrmPr;
    }

    public boolean isUnitMeasureFrmPrFrService() {
        return unitMeasureFrmPrFrService;
    }

    public void setUnitMeasureFrmPrFrService(boolean unitMeasureFrmPrFrService) {
        this.unitMeasureFrmPrFrService = unitMeasureFrmPrFrService;
    }

    public boolean isUnitMeasurefrmPrfrWork() {
        return UnitMeasurefrmPrfrWork;
    }

    public void setUnitMeasurefrmPrfrWork(boolean UnitMeasurefrmPrfrWork) {
        this.UnitMeasurefrmPrfrWork = UnitMeasurefrmPrfrWork;
    }

    public boolean isServiceNoFrmpr() {
        return serviceNoFrmpr;
    }

    public void setServiceNoFrmpr(boolean serviceNoFrmpr) {
        this.serviceNoFrmpr = serviceNoFrmpr;
    }

    public boolean isServicNmaeFrmPr() {
        return servicNmaeFrmPr;
    }

    public void setServicNmaeFrmPr(boolean servicNmaeFrmPr) {
        this.servicNmaeFrmPr = servicNmaeFrmPr;
    }

    public boolean isWorkNofrmpr() {
        return workNofrmpr;
    }

    public void setWorkNofrmpr(boolean workNofrmpr) {
        this.workNofrmpr = workNofrmpr;
    }

    public boolean isWorknmeFrmPr() {
        return worknmeFrmPr;
    }

    public void setWorknmeFrmPr(boolean worknmeFrmPr) {
        this.worknmeFrmPr = worknmeFrmPr;
    }

    public boolean isPlanNoFrItem() {
        return planNoFrItem;
    }

    public void setPlanNoFrItem(boolean planNoFrItem) {
        this.planNoFrItem = planNoFrItem;
    }

    public boolean isPlanNoForWork() {
        return PlanNoForWork;
    }

    public void setPlanNoForWork(boolean PlanNoForWork) {
        this.PlanNoForWork = PlanNoForWork;
    }

    public boolean isItemNamefrmPlan() {
        return itemNamefrmPlan;
    }

    public void setItemNamefrmPlan(boolean itemNamefrmPlan) {
        this.itemNamefrmPlan = itemNamefrmPlan;
    }

    public boolean isItemCodefrmPlan() {
        return itemCodefrmPlan;
    }

    public void setItemCodefrmPlan(boolean itemCodefrmPlan) {
        this.itemCodefrmPlan = itemCodefrmPlan;
    }

    public boolean isUnitMeasureFrmPlan() {
        return unitMeasureFrmPlan;
    }

    public void setUnitMeasureFrmPlan(boolean unitMeasureFrmPlan) {
        this.unitMeasureFrmPlan = unitMeasureFrmPlan;
    }

    public boolean isPrFrItem() {
        return prFrItem;
    }

    public void setPrFrItem(boolean prFrItem) {
        this.prFrItem = prFrItem;
    }

    public boolean isPrfrWork() {
        return prfrWork;
    }

    public void setPrfrWork(boolean prfrWork) {
        this.prfrWork = prfrWork;
    }

    public boolean isPrFrConsultancy() {
        return prFrConsultancy;
    }

    public void setPrFrConsultancy(boolean prFrConsultancy) {
        this.prFrConsultancy = prFrConsultancy;
    }

    public boolean isPrFrNonConsutancy() {
        return prFrNonConsutancy;
    }

    public void setPrFrNonConsutancy(boolean prFrNonConsutancy) {
        this.prFrNonConsutancy = prFrNonConsutancy;
    }

    public boolean isServiceType() {
        return serviceType;
    }

    public void setServiceType(boolean serviceType) {
        this.serviceType = serviceType;
    }

    public boolean isLoanAndCredit() {
        return loanAndCredit;
    }

    public void setLoanAndCredit(boolean loanAndCredit) {
        this.loanAndCredit = loanAndCredit;
    }

    public boolean isDocumentFee() {
        return documentFee;
    }

    public void setDocumentFee(boolean documentFee) {
        this.documentFee = documentFee;
    }

    public boolean isLotBase() {
        return lotBase;
    }

    public void setLotBase(boolean lotBase) {
        this.lotBase = lotBase;
    }

    public PrmsPurchasePlnDetail getPurchasePlnDetail() {
        return purchasePlnDetail;
    }

    public void setPurchasePlnDetail(PrmsPurchasePlnDetail purchasePlnDetail) {
        this.purchasePlnDetail = purchasePlnDetail;
    }

    public PrmsPurchasePlan getPurchasePlan() {
        return purchasePlan;
    }

    public void setPurchasePlan(PrmsPurchasePlan purchasePlan) {
        this.purchasePlan = purchasePlan;
    }

    public List<PrmsPurchasePlnDetail> getPurchasePlnDetailLst() {
        if (purchasePlnDetailLst == null) {
            purchasePlnDetailLst = new ArrayList<>();
        }
        return purchasePlnDetailLst;
    }

    public void setPurchasePlnDetailLst(List<PrmsPurchasePlnDetail> purchasePlnDetailLst) {
        this.purchasePlnDetailLst = purchasePlnDetailLst;
    }

    public List<SelectItem> getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(List<SelectItem> evaluationType) {
        this.evaluationType = evaluationType;
    }

    public List<PrmsBid> getPrmsBidList() {
        if (prmsBidList == null) {
            prmsBidList = new ArrayList<>();
        }
        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    public DataModel<PrmsBid> getPrmsBidModel() {
        if (prmsBidModel == null) {
            prmsBidModel = new ListDataModel<>();
        }
        return prmsBidModel;
    }

    public void setPrmsBidModel(DataModel<PrmsBid> prmsBidModel) {
        this.prmsBidModel = prmsBidModel;
    }

    public DataModel<PrmsBidDetail> getPrmsBidDetailMdl() {
        if (prmsBidDetailMdl == null) {
            prmsBidDetailMdl = new ListDataModel();
        }
        return prmsBidDetailMdl;
    }

    public void setPrmsBidDetailMdl(DataModel<PrmsBidDetail> prmsBidDetailMdl) {
        this.prmsBidDetailMdl = prmsBidDetailMdl;
    }

    public List<PrmsPurchaseRequestDet> getPurchaseRequestDetList() {
        if (purchaseRequestDetList == null) {
            purchaseRequestDetList = new ArrayList<>();
        }
        return purchaseRequestDetList;
    }

    public void setPurchaseRequestDetList(List<PrmsPurchaseRequestDet> purchaseRequestDetList) {
        this.purchaseRequestDetList = purchaseRequestDetList;
    }

    public List<PrmsPurchaseRequest> getPurchaseRequestList() {
        return purchaseRequestList;
    }

    public void setPurchaseRequestList(List<PrmsPurchaseRequest> purchaseRequestList) {
        this.purchaseRequestList = purchaseRequestList;
    }

    public PrmsPurchaseRequestDet getPrmsPurchaseRequestDet() {
        if (prmsPurchaseRequestDet == null) {
            prmsPurchaseRequestDet = new PrmsPurchaseRequestDet();
        }
        return prmsPurchaseRequestDet;
    }

    public void setPrmsPurchaseRequestDet(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        this.prmsPurchaseRequestDet = prmsPurchaseRequestDet;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public PrmsBidCriteria getPrmsBidCriteria() {
        if (prmsBidCriteria == null) {
            prmsBidCriteria = new PrmsBidCriteria();
        }
        return prmsBidCriteria;
    }

    public void setPrmsBidCriteria(PrmsBidCriteria prmsBidCriteria) {
        this.prmsBidCriteria = prmsBidCriteria;
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

    public boolean isCompliance() {
        return compliance;
    }

    public void setCompliance(boolean compliance) {
        this.compliance = compliance;
    }

    public boolean isSiteVisite() {
        return siteVisite;
    }

    public void setSiteVisite(boolean siteVisite) {
        this.siteVisite = siteVisite;
    }

    public boolean isMinuteNo() {
        return MinuteNo;
    }

    public void setMinuteNo(boolean MinuteNo) {
        this.MinuteNo = MinuteNo;
    }

    public boolean isPrebidMeeting() {
        return PrebidMeeting;
    }

    public void setPrebidMeeting(boolean PrebidMeeting) {
        this.PrebidMeeting = PrebidMeeting;
    }

    public boolean isPreMeetingPlace() {
        return preMeetingPlace;
    }

    public void setPreMeetingPlace(boolean preMeetingPlace) {
        this.preMeetingPlace = preMeetingPlace;
    }

    public PrmsBid getPrmsBidselected() {
        return prmsBidselected;
    }

    public void setPrmsBidselected(PrmsBid prmsBidselected) {
        this.prmsBidselected = prmsBidselected;
    }

    public boolean isRendddd() {
        return rendddd;
    }

    public void setRendddd(boolean rendddd) {
        this.rendddd = rendddd;
    }

    public boolean isEr2() {
        return er2;
    }

    public void setEr2(boolean er2) {
        this.er2 = er2;
    }
    private boolean unitMeasureItem = true;
    private boolean unitMes;

    public boolean isUnitMes() {
        return unitMes;
    }

    public void setUnitMes(boolean unitMes) {
        this.unitMes = unitMes;
    }

    public boolean isUnitMeasureItem() {
        return unitMeasureItem;
    }

    public void setUnitMeasureItem(boolean unitMeasureItem) {
        this.unitMeasureItem = unitMeasureItem;
    }

    public String getWorkNam() {
        return workNam;
    }

    public void setWorkNam(String workNam) {
        this.workNam = workNam;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
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

    public List<MmsItemRegistration> getItemRegistrationList() {
        if (itemRegistrationList == null) {
            itemRegistrationList = new ArrayList<>();
        }
        return itemRegistrationList;
    }

    public void setItemRegistrationList(List<MmsItemRegistration> itemRegistrationList) {
        this.itemRegistrationList = itemRegistrationList;
    }

    public String getSaveupdate() {
        return saveupdate;
    }

    public void setSaveupdate(String saveupdate) {
        this.saveupdate = saveupdate;
    }

    public String getRenderprNo() {
        return renderprNo;
    }

    public void setRenderprNo(String renderprNo) {
        this.renderprNo = renderprNo;
    }

    public PrmsPurchaseRequest getPrmsPurchaseRequest() {
        if (prmsPurchaseRequest == null) {
            prmsPurchaseRequest = new PrmsPurchaseRequest();
        }
        return prmsPurchaseRequest;
    }

    public void setPrmsPurchaseRequest(PrmsPurchaseRequest prmsPurchaseRequest) {
        this.prmsPurchaseRequest = prmsPurchaseRequest;
    }

    public PrmsBid getEepBidReg() {
        if (eepBidReg == null) {
            eepBidReg = new PrmsBid();
        }
        return eepBidReg;
    }

    public void setEepBidReg(PrmsBid eepBidReg) {

        this.eepBidReg = eepBidReg;
    }

    public PrmsBidDetail getEepBidDetail() {
        if (eepBidDetail == null) {
            eepBidDetail = new PrmsBidDetail();
        }
        return eepBidDetail;
    }

    public void setEepBidDetail(PrmsBidDetail eepBidDetail) {
        this.eepBidDetail = eepBidDetail;
    }

    public void reCreatBidRegMdl() {
        prmsBidModel = null;
        prmsBidModel = new ListDataModel(new ArrayList(getPrmsBidList()));
    }

    public String[] getSelectedBidCriteria() {
        return selectedBidCriteria;
    }

    public void setSelectedBidCriteria(String[] selectedBidCriteria) {
        this.selectedBidCriteria = selectedBidCriteria;
    }

    public List<PrmsBidDetail> getBidDetailList() {
        return bidDetailList;
    }

    public void setBidDetailList(List<PrmsBidDetail> bidDetailList) {
        this.bidDetailList = bidDetailList;
    }
    List<PrmsPurchaseRequest> prmsPurchaseRequestLst;

    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestLst() {
        setPrmsPurchaseRequestLst(bidRegBeanLocal.searchPurchaseReqNo());
        return prmsPurchaseRequestLst;
    }

    public void setPrmsPurchaseRequestLst(List<PrmsPurchaseRequest> prmsPurchaseRequestLst) {
        this.prmsPurchaseRequestLst = prmsPurchaseRequestLst;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="method">
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepBidReg.setColumnName(event.getNewValue().toString());
            eepBidReg.setColumnValue(null);
        }
    }
    public void clearSearch() {
        eepBidReg = null;
        eepBidDetail = null;
        prmsBidDetailMdl = null;
        selectedBidCriteria = null;
    }

    public void createNewBid() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            clearSearch();
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackToSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void rowSelect(SelectEvent event) {
        eepBidReg = (PrmsBid) event.getObject();
        popUp();
        saveorUpdateBundle = "Update";
        addOrUpdateBundle = "Modify";
    }

    public void popUp() {
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        isRenderedIconWorkflow = true;
        renderpnlToSearchPage = true;
        selectedBidCriteria = eepBidReg.getBidCriteriaId().split(",");

        fmsLuCurrency = eepBidReg.getCurrencyId();

        String select = eepBidReg.getBidCategory();

        if ("Goods".equals(select)) {

            itemRegistrationList = bidRegBeanLocal.getItemcode(prmsPurchaseRequest.getPrNumber());
            itemRegistrationList = bidRegBeanLocal.getItemCodefrmPlan(prmsPurchasePlan.getPlanNo());
            serviceTable = false;
            workTable = false;
            itemTable = true;
        } else if ("Service".equals(select)) {
            workTable = false;
            itemTable = false;
            serviceTable = true;
            prmsServiceAndWorkRegList = bidRegBeanLocal.getSericeNameFrmPlan(prmsPurchasePlan.getPlanNo());
            prmsServiceAndWorkRegList = bidRegBeanLocal.getServiceFrmPr(prmsPurchaseRequest.getPrNumber());
        } else if ("Work".equals(select)) {
            itemTable = false;
            serviceTable = false;
            workTable = true;
        }
        for (int i = 0; i < eepBidReg.getPrmsBidDetailList().size(); i++) {
            String fermselction = eepBidReg.getPrmsBidDetailList().get(i).getFermSeltionMethd();
            String sourceOfFinance = eepBidReg.getBidType();
            if ("compliance".equals(fermselction)) {
                compliance = true;
                merit = false;

            } else if ("merit".equals(fermselction)) {
                compliance = false;
                merit = true;
            }
            if ("local".equals(sourceOfFinance)) {
                governoment = false;
                financialSourc = true;
            } else {
                financialSourc = false;
                governoment = true;
            }
            String sourceOfIntitiation = eepBidReg.getPrmsBidDetailList().get(i).getSourceOfInitaition();
            if ("Request from Project".equals(sourceOfIntitiation)) {
                disableAnnualPlan = true;
                disablePurchaseReq = true;
            } else {
                disableAnnualPlan = false;
                disablePurchaseReq = false;
            }
        }
        if (eepBidReg.getDocumentId() != null) {
            prmsLuDmArchive = eepBidReg.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
        }
        if (!saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(eepBidReg.getBidRegDate());
        }

        recreatModel();
        reCreatWFModel();
        rectreateFileUpload();
    }

    public void recreatModel() {
        prmsBidDetailMdl = null;
        prmsBidDetailMdl = new ListDataModel(new ArrayList(eepBidReg.getPrmsBidDetailList()));
    }

    public void reCreatWFModel() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel<>(new ArrayList(eepBidReg.getWfPrmsProcessedCollection()));
    }

    public void rectreateFileUpload() {
        prmsLuDmArchiveDataModel = null;
        prmsLuDmArchiveDataModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public void clear() {
        eepBidDetail = null;
        mmsItemRegistration = null;
        prmsServiceAndWorkReg = null;
        purchasePlan = null;
        prmsPurchaseRequest = null;
        prmsAnnualPlanService = null;
        prmsPurchaseRequestDet = null;
        prmsPurchaseReqService = null;
        prmsPurchasePlnDetail = null;
        addOrUpdateBundle = "Add";
    }

    public void addTable() {

        if (checkItem.contains(eepBidDetail.getItemRegId().getMatName())) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
        } else {
            eepBidReg.add(eepBidDetail);
            checkItem.add(eepBidDetail.getItemRegId().getMatName());
            eepBidDetail.setBidId(eepBidReg);
            eepBidDetail.setItemRegId(mmsItemRegistration);
            recreatModel();

            clear();
        }
    }

    public void addItemFrmProjectTable() {
        eepBidReg.add(eepBidDetail);
        mmsItemRegistration = null;
        pmsResources = null;
        eepBidDetail = null;
        recreatModel();

    }

    public void addServiceProjectTable() {
        eepBidReg.add(eepBidDetail);
        eepBidDetail.setServiceId(prmsServiceAndWorkReg);
        eepBidDetail.setBidId(eepBidReg);
        recreatModel();
        clear();

    }

    public void addItemFrmPlanTable() {
        eepBidReg.add(eepBidDetail);
        eepBidDetail.setBidId(eepBidReg);
        eepBidDetail.setItemRegId(mmsItemRegistration);
        recreatModel();
        clear();

    }

    public void addServiceTable() {
        eepBidReg.add(eepBidDetail);
        eepBidDetail.setServiceId(prmsServiceAndWorkReg);
        eepBidDetail.setBidId(eepBidReg);
        recreatModel();
        clear();

    }

    public void addServicePlTable() {
        eepBidReg.add(eepBidDetail);
        eepBidDetail.setServiceId(prmsServiceAndWorkReg);
        eepBidDetail.setBidId(eepBidReg);
        recreatModel();
        clear();

    }

    public void addServiceFrmPlanTable() {
        eepBidReg.add(eepBidDetail);
        eepBidDetail.setServiceId(prmsServiceAndWorkReg);
        eepBidDetail.setBidId(eepBidReg);
        recreatModel();
        clear();

    }

    public void edit() {
        eepBidDetail = prmsBidDetailMdl.getRowData();
        selectedRowIndex = prmsBidDetailMdl.getRowIndex();
        prmsPurchaseRequest = eepBidDetail.getPrId();
        prmsPurchasePlan = eepBidDetail.getPlanId();
        prmsServiceAndWorkReg = eepBidDetail.getServiceId();
        mmsItemRegistration = eepBidDetail.getItemRegId();
        pmsCreateProjects = eepBidReg.getProjectId();
        String sour = eepBidDetail.getSourceOfInitaition();
        String purchaseType = eepBidReg.getBidCategory();
        String sourceOfFinancial = eepBidDetail.getSourceOfFinance();
        select = purchaseType;
        if ("Request from Pr".equals(sour)) {
            if (select.equalsIgnoreCase("Goods")) {
                prFrItem = true;
                planNoFrItem = false;
                workName = false;
                workFrmPlan = false;
                serviceFrmPr = false;
                servicefrmPlan = false;
                prmsPurchaseRequestLst = bidRegBeanLocal.searchPurchaseReqNo();
                eepBidDetail.setPrId(eepBidDetail.getPrId());
                itemRegistrationList = bidRegBeanLocal.getItemcode(prmsPurchaseRequest.getPrNumber());
            } else if (select.equalsIgnoreCase("Work")) {
                workName = true;
                workFrmPlan = false;
                prFrItem = false;
                planNoFrItem = false;
                serviceFrmPr = false;
                servicefrmPlan = false;
                prmsPurchaseRequestLst = bidRegBeanLocal.searchPurchaseReqNo();
                eepBidDetail.setPrId(eepBidDetail.getPrId());
                prmsServiceAndWorkRegList = bidRegBeanLocal.getServiceFrmPr(prmsPurchaseRequest.getPrNumber());
            } else if (select.equalsIgnoreCase("Service")) {
                serviceFrmPr = true;
                servicefrmPlan = false;
                prFrItem = false;
                planNoFrItem = false;
                workName = false;
                workFrmPlan = false;
                prmsPurchaseRequestLst = bidRegBeanLocal.searchPurchaseReqNo();
                eepBidDetail.setPrId(eepBidDetail.getPrId());
                prmsServiceAndWorkRegList = bidRegBeanLocal.getServiceFrmPr(prmsPurchaseRequest.getPrNumber());
            }
        } else if ("Request from Plan".equals(sour)) {
            if (select.equalsIgnoreCase("Goods")) {
                planNoFrItem = true;
                prFrItem = false;
                workName = false;
                workFrmPlan = false;
                serviceFrmPr = false;
                servicefrmPlan = false;
                mmsItemRegistration = eepBidDetail.getItemRegId();
                eepBidDetail.setItemRegId(eepBidDetail.getItemRegId());
                itemRegistrationList = bidRegBeanLocal.getItemCodefrmPlan(prmsPurchasePlan.getPlanNo());
            } else if (select.equalsIgnoreCase("Work")) {
                workName = false;
                workFrmPlan = true;
                serviceFrmPr = false;
                servicefrmPlan = false;
                planNoFrItem = false;
                prFrItem = false;
                prmsServiceAndWorkRegList = bidRegBeanLocal.getWorkNameFrmPlan(prmsPurchasePlan.getPlanNo());
            } else if (select.equalsIgnoreCase("Service")) {
                serviceFrmPr = false;
                servicefrmPlan = true;
                workName = false;
                workFrmPlan = false;
                planNoFrItem = false;
                prFrItem = false;
                prmsServiceAndWorkRegList = bidRegBeanLocal.getSericeNameFrmPlan(prmsPurchasePlan.getPlanNo());
            }
        } else {
            if (select.equalsIgnoreCase("Goods")) {
                goodsFrmProjectPlan = true;
                serviceFrmProjectPln = false;
                workFrmProjectPln = false;
                itemRegistrationList = bidRegBeanLocal.getItemFrmProjectPlan(pmsCreateProjects.getProjectName());
                mmsItemRegistration = eepBidDetail.getItemRegId();
                eepBidDetail.setItemRegId(eepBidDetail.getItemRegId());
            } else if (select.equalsIgnoreCase("Service")) {
                goodsFrmProjectPlan = false;
                serviceFrmProjectPln = true;
                workFrmProjectPln = false;
            } else {
                goodsFrmProjectPlan = false;
                serviceFrmProjectPln = false;
                workFrmProjectPln = true;
            }

        }
        String fermSelection = eepBidDetail.getFermSeltionMethd();
        if (fermSelection.equalsIgnoreCase("compliance")) {
            compliance = true;
            merit = false;

        } else {
            compliance = false;
            merit = true;
        }
        if ("Governoment".equals(sourceOfFinancial)) {
            loanAndCredit = false;
        } else {
            loanAndCredit = true;
        }
    }

    public void clearFrSave() {
        fileDataModel = null;
        eepBidReg = null;
        prmsBidDetailMdl = null;
        prmsBidCriteriaList = null;
        prmsBidCriteria = null;
        selectedBidCriteria = null;
        fmsLuCurrency = null;
    }

    public String generateBidNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newBidNum = eepBidReg.getRefNo();
        } else {
            if (eepBidReg.getBidCategory() != null) {
                newBidNum = getGoods_Service_WorkBidSeqNo(eepBidReg.getBidCategory());
            }
        }
        return newBidNum;
    }

    public String getGoods_Service_WorkBidSeqNo(String ProcurementCategory) {
        String goods_Service_WorkBidNo = bidRegBeanLocal.getGoods_Service_WorkBidSeqNo(ProcurementCategory);
        return goods_Service_WorkBidNo;
    }

    public String save_BidRegistration() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_BidRegistration", dataset)) {
                try {

                    if (saveorUpdateBundle.equals("Save")) {

                        int size = selectedBidCriteria.length;
                        if (size != 0) {
                            String newbidCreatria = "";

                            for (int i = 0; i < size; i++) {
                                if (newbidCreatria.equals("")) {
                                    newbidCreatria = selectedBidCriteria[i];
                                } else {
                                    newbidCreatria = newbidCreatria + "," + selectedBidCriteria[i];
                                }
                            }
                            eepBidReg.setBidCriteriaId(newbidCreatria);
                        }
                        generateBidNo();
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(docFormat);
                        prmsLuDmArchive.setUploadFile(byteData);
                        prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        wfPrmsProcessed.setBidId(eepBidReg);
                        wfPrmsProcessed.setDecision(wfPrmsProcessed.getDecision());
                        eepBidReg.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        eepBidReg.setBidRegDate(wfPrmsProcessed.getProcessedOn());
                        eepBidReg.setRefNo(newBidNum);
                        eepBidReg.setCurrencyId(fmsLuCurrency);
                        eepBidReg.setDocumentId(prmsLuDmArchive);
                        eepBidReg.setStatus(Constants.PREPARE_VALUE);
                        bidRegBeanLocal.save(eepBidReg);

                        eepBidReg.getWfPrmsProcessedCollection().add(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Data is Saved");
                    } else if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                        int size = selectedBidCriteria.length;
                        if (size != 0) {
                            String newbidCreatria = "";

                            for (int i = 0; i < size; i++) {
                                if (newbidCreatria.equals("")) {
                                    newbidCreatria = selectedBidCriteria[i];
                                } else {
                                    newbidCreatria = newbidCreatria + "," + selectedBidCriteria[i];
                                }
                            }
                            eepBidReg.setBidCriteriaId(newbidCreatria);
                        }
                        eepBidReg.setDocumentId(prmsLuDmArchive);
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(docFormat);
                        prmsLuDmArchive.setUploadFile(byteData);
                        prmsLuDmArchiveBeanLocal.updateFileInfo(prmsLuDmArchive);
                        eepBidReg.setBidRegDate(wfPrmsProcessed.getProcessedOn());
                        eepBidReg.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        eepBidReg.setDesription(wfPrmsProcessed.getCommentGiven());
                        bidRegBeanLocal.Edit(eepBidReg);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        saveorUpdateBundle = "Save";
                        wfPrmsProcessed = null;

                        JsfUtil.addSuccessMessage("Data is UpDated");

                    } else if (saveorUpdateBundle.equals("Update") && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            eepBidReg.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        }
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            eepBidReg.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            eepBidReg.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            eepBidReg.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        generateBidNo();
                        eepBidReg.setRefNo(newBidNum);
                        bidRegBeanLocal.Edit(eepBidReg);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        wfPrmsProcessed = null;
                        JsfUtil.addSuccessMessage("Data is Approved");
                    }

                } catch (Exception e) {
                    JsfUtil.addErrorMessage("data is not saved");
                    e.printStackTrace();
                }
                clearFrSave();
                return null;
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);

                //..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public ArrayList<PrmsBid> searchBidRef(String bidRef) {

        ArrayList<PrmsBid> bidRegList = null;
        bidRegList = bidRegBeanLocal.searchBidRefce(eepBidReg);

        eepBidReg.setRefNo(bidRef);
        return bidRegList;
    }

    public void searchByRefNo(SelectEvent event) {

        eepBidReg.setRefNo(event.getObject().toString());
        eepBidReg = bidRegBeanLocal.getBidRe(eepBidReg);
        saveupdate = "update";
        recreatModel();
    }

    public void searchBid() {
        eepBidReg.setPreparedBy(workFlow.getUserAccount());
        prmsBidList = bidRegBeanLocal.searchBidNo(eepBidReg);
        reCreatBidRegMdl();
        eepBidReg=new PrmsBid();
    }

    public SelectItem[] getServiceFrmPr() {
        return JsfUtil.getSelectItems(bidRegBeanLocal.getServiceFrmPr(), true);
    }

    public List<PrmsPurchaseRequest> getWork() {
        setPrmsPurchaseRequestLst(bidRegBeanLocal.searchPrNoFrWork());
        return prmsPurchaseRequestLst;
    }

    public List<PrmsPurchaseReqService> getService() {
        setPrmsPurchaseReqServiceList(bidRegBeanLocal.searchgetService());
        return prmsPurchaseReqServiceList;
    }

    public void valueChange(ValueChangeEvent event) {

        itemRegistrationList = bidRegBeanLocal.getItemcode(event.getNewValue().toString());

    }

    public void valueChangeItemCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsItemRegistration = (MmsItemRegistration) event.getNewValue();
            eepBidDetail.setItemRegId(mmsItemRegistration);
            mmsItemRegistration = bidRegBeanLocal.getItemName(mmsItemRegistration);
            prmsPurchaseRequestDet = bidRegBeanLocal.getQuantityFrmPr(mmsItemRegistration);
            eepBidDetail.setQuantity(prmsPurchaseRequestDet.getReqQuantity());

        }
    }

    public void valueChangeItemCodeFrm(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsItemRegistration = (MmsItemRegistration) event.getNewValue();
            eepBidDetail.setItemRegId(mmsItemRegistration);
            mmsItemRegistration = bidRegBeanLocal.getItemNameFrmPlan(mmsItemRegistration);
            prmsPurchasePlnDetail = bidRegBeanLocal.getQuantityFrmPlan(mmsItemRegistration);
            eepBidDetail.setQuantity(prmsPurchasePlnDetail.getQuantity());

        }
    }

    public void valueChangeWorkfrmProject(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            prmsServiceAndWorkReg = bidRegBeanLocal.getUnitMeasureServiceFrmProjec(prmsServiceAndWorkReg);
            pmsResources = bidRegBeanLocal.getQuantityServiceFrmProjectPlan(prmsServiceAndWorkReg);
            eepBidDetail.setQuantity(pmsResources.getQuantity());

        }
    }

    public void valueChangeUnitMeasAndQtyFrmProject(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            prmsServiceAndWorkReg = bidRegBeanLocal.getUnitMeasureServiceFrmProjec(prmsServiceAndWorkReg);
            pmsResources = bidRegBeanLocal.getQuantityServiceFrmProjectPlan(prmsServiceAndWorkReg);
            eepBidDetail.setQuantity(pmsResources.getQuantity());

        }
    }

    public void valueChangeItemNameFrmProject(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsItemRegistration = (MmsItemRegistration) event.getNewValue();
            mmsItemRegistration = bidRegBeanLocal.getUnitMeasureFrmProjec(mmsItemRegistration);
            pmsResources = bidRegBeanLocal.getQuantityFrmProjectPlan(mmsItemRegistration);
            eepBidDetail.setQuantity(pmsResources.getQuantity());

        }
    }

    public void valueChngServiceUnitMesureAndQnt(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            eepBidDetail.setServiceId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = bidRegBeanLocal.findId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = bidRegBeanLocal.getUnitMeasure(prmsServiceAndWorkReg);
            prmsAnnualPlanService = bidRegBeanLocal.getQuantity(prmsServiceAndWorkReg);
            eepBidDetail.setQuantity(prmsAnnualPlanService.getQuantity());

        }
    }

    public void valueChngWorkUnitMesureAndQnt(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            eepBidDetail.setServiceId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = bidRegBeanLocal.findId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = bidRegBeanLocal.getUnitMeasure(prmsServiceAndWorkReg);
            prmsAnnualPlanService = bidRegBeanLocal.getQuantity(prmsServiceAndWorkReg);
            eepBidDetail.setQuantity(prmsAnnualPlanService.getQuantity());

        }
    }

    public void valueChangeServiceFrmPr(ValueChangeEvent event) {
        prmsServiceAndWorkRegList = bidRegBeanLocal.getServiceFrmPr(event.getNewValue().toString());
    }

    public void valueChangeService(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            eepBidDetail.setServiceId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = bidRegBeanLocal.getServiceNamfrPr(prmsServiceAndWorkReg);
            prmsPurchaseReqService = bidRegBeanLocal.getQuantityFrmPrService(prmsServiceAndWorkReg);
            eepBidDetail.setQuantity(prmsPurchaseReqService.getReqQuantity());

        }
    }

    public void valueChangeServiceCodeFrNonConsultancy(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            BigDecimal serviceNo = new BigDecimal(event.getNewValue().toString());
            prmsServiceAndWorkReg = bidRegBeanLocal.getServiceNamefrNonCon(serviceNo);

        }
    }

    public void valueChangeFrWork(ValueChangeEvent event) {

        prmsServiceAndWorkRegList = bidRegBeanLocal.getWorkNo(event.getNewValue().toString());
    }
    /*radioButton method for selecting local and international
    
     */

    public void valueChangeMarketApp(ValueChangeEvent event) {
        String marketApp = event.getNewValue().toString();
        if (event.getNewValue() != null) {
            if (marketApp.equalsIgnoreCase("local")) {
                financialSourc = true;
                governoment = false;
                marketApprochInternational = false;
                marketApprochLocal = true;
            } else if (marketApp.equalsIgnoreCase("international")) {
                financialSourc = false;
                governoment = true;
                marketApprochInternational = true;
                marketApprochLocal = false;
            }
        }
    }
    /*medhod used for fetching work Name List
    
     */

    public void valueChangeWorkName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            eepBidDetail.setServiceId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = bidRegBeanLocal.getworkName(prmsServiceAndWorkReg);
            prmsPurchaseReqService = bidRegBeanLocal.getQuantityFrmPrService(prmsServiceAndWorkReg);
            eepBidDetail.setQuantity(prmsPurchaseReqService.getReqQuantity());

        }
    }
    /*medhod used for fetching plan number 
    
     */

    public void valueChangePlanNo(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            itemRegistrationList = bidRegBeanLocal.getItemCodefrmPlan(event.getNewValue().toString());

        }
    }
    /*medhod used for fetching service name from annual plan
    
     */

    public void valueChangeSericeNameFrmPlan(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsServiceAndWorkRegList = bidRegBeanLocal.getSericeNameFrmPlan(event.getNewValue().toString());

        }
    }
    /*medhod used for fetching work name from annual plan
    
     */

    public void valueChangeWorkNameFrmPlan(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsServiceAndWorkRegList = bidRegBeanLocal.getWorkNameFrmPlan(event.getNewValue().toString());

        }
    }

    public void changeValueFrItemFrmPrject(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            disableAnnualPlan = true;
            disablePurchaseReq = true;
            itemRegistrationList = bidRegBeanLocal.getItemFrmProjectPlan(event.getNewValue().toString());
            prmsServiceAndWorkRegList = bidRegBeanLocal.getServiceFrmProjectPlan(event.getNewValue().toString());

        }
    }

    public SelectItem[] getItemcode() {
        return JsfUtil.getSelectItems(marketAssesmntBeanLocal.ItemCodeList(), true);

    }

    public SelectItem[] getPrNo() {
        return JsfUtil.getSelectItems(marketAssesmntBeanLocal.getPurchReqNo(), true);
    }

    public SelectItem[] getPlanNo() {
        return JsfUtil.getSelectItems(bidRegBeanLocal.getPlanNo(), true);
    }

    public SelectItem[] getservicePlanNo() {
        return JsfUtil.getSelectItems(bidRegBeanLocal.getServicefrmPlanNo(), true);
    }

    public SelectItem[] getWorkPlanNo() {
        return JsfUtil.getSelectItems(bidRegBeanLocal.getWorkfrmPlanNo(), true);
    }

    public List<PmsCreateProjects> getProjectIds() {
        setPmsCreateProjectseList(bidRegBeanLocal.getProjectId());
        return pmsCreateProjectseList;
    }

    public List<PrmsPurchaseRequest> getPrNos() {
        setPurchaseRequestList(bidRegBeanLocal.getPrNo());
        return purchaseRequestList;
    }

    /**
     * @return the selectedRowIndex
     */
    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    /**
     * @param selectedRowIndex the selectedRowIndex to set
     */
    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }
    ArrayList<PrmsBidCriteria> prmsBidCriteriaList;

    public ArrayList<PrmsBidCriteria> getPrmsBidCriteriaList() {
        if (prmsBidCriteriaList == null) {
            prmsBidCriteriaList = new ArrayList<>();
        }
        return prmsBidCriteriaList;
    }

    public void setPrmsBidCriteriaList(ArrayList<PrmsBidCriteria> prmsBidCriteriaList) {
        this.prmsBidCriteriaList = prmsBidCriteriaList;
    }

    public SelectItem[] getCriteriaLst() {
        return JsfUtil.getSelectItems(bidRegBeanLocal.getBidCriteriaList(), true);
    }

    public ArrayList<PrmsBidCriteria> getBidCriteriaLst() {
        setPrmsBidCriteriaList(bidRegBeanLocal.getBidCriteriaList());
        return prmsBidCriteriaList;
    }

    /* method used for activing and deactiving work, goods and service
    
     */
    public void changeReqType(ValueChangeEvent e) {
        sourceOfIn = e.getNewValue().toString();

        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Request from Pr")) {
                if (procurementCategory.equalsIgnoreCase("Goods")) {

                    prFrItem = true;
                    servicefrmPlan = false;
                    serviceFrmPr = false;
                    workName = false;
                    workFrmPlan = false;
                    planNoFrItem = false;
                    serviceTable = false;
                    workTable = false;
                    itemTable = true;
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = false;
                } else if (procurementCategory.equalsIgnoreCase("Work")) {

                    prFrItem = false;
                    servicefrmPlan = false;
                    serviceFrmPr = false;
                    workName = true;
                    workFrmPlan = false;
                    planNoFrItem = false;
                    serviceTable = false;
                    workTable = true;
                    itemTable = false;
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = false;

                } else if (procurementCategory.equalsIgnoreCase("Service")) {

                    prFrItem = false;
                    servicefrmPlan = false;
                    serviceFrmPr = true;
                    workName = false;
                    workFrmPlan = false;
                    planNoFrItem = false;
                    prFrItem = false;
                    serviceAndWorkFrmPr = true;
                    serviceTable = true;
                    workTable = false;
                    itemTable = false;
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = false;

                }

            } else if (select.equalsIgnoreCase("Request from Plan")) {

                if (procurementCategory.equalsIgnoreCase("Goods")) {

                    serviceTable = false;
                    workTable = false;
                    itemTable = true;
                    prFrItem = false;
                    servicefrmPlan = false;
                    serviceFrmPr = false;
                    workName = false;
                    workFrmPlan = false;
                    planNoFrItem = true;
                    serviceAndWorkFrmPr = false;
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = false;

                } else if (procurementCategory.equalsIgnoreCase("Work")) {

                    serviceTable = false;
                    workTable = true;
                    itemTable = false;
                    prFrItem = false;
                    servicefrmPlan = false;
                    serviceFrmPr = false;
                    workName = false;
                    workFrmPlan = true;
                    planNoFrItem = false;
                    serviceAndWorkFrmPr = false;
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = false;

                } else if (procurementCategory.equalsIgnoreCase("Service")) {

                    serviceTable = true;
                    workTable = false;
                    itemTable = false;
                    prFrItem = false;
                    servicefrmPlan = true;
                    serviceFrmPr = false;
                    workName = false;
                    workFrmPlan = false;
                    planNoFrItem = false;
                    serviceAndWorkFrmPr = false;
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = false;
                }

            } else {
                if (procurementCategory.equalsIgnoreCase("Goods")) {
                    goodsFrmProjectPlan = true;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = false;
                    serviceTable = false;
                    workTable = false;
                    itemTable = true;
                    prFrItem = false;
                    servicefrmPlan = false;
                    serviceFrmPr = false;
                    workName = false;
                    workFrmPlan = false;
                    planNoFrItem = false;
                } else if (procurementCategory.equalsIgnoreCase("Service")) {
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = false;
                    serviceFrmProjectPln = true;
                    serviceTable = false;
                    workTable = false;
                    itemTable = true;
                    prFrItem = false;
                    servicefrmPlan = false;
                    serviceFrmPr = false;
                    workName = false;
                    workFrmPlan = false;
                    planNoFrItem = false;
                } else if (procurementCategory.equalsIgnoreCase("work")) {
                    goodsFrmProjectPlan = false;
                    workFrmProjectPln = true;
                    serviceFrmProjectPln = false;
                    serviceTable = false;
                    workTable = false;
                    itemTable = true;
                    prFrItem = false;
                    servicefrmPlan = false;
                    serviceFrmPr = false;
                    workName = false;
                    workFrmPlan = false;
                    planNoFrItem = false;
                }

            }
        }

    }
    /* method used for activing and deacting items
    
     */

    public void changePlanType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Goods")) {
                planNoFrItem = true;
                prFrItem = false;
                itemCodefrmPlan = true;
                itemNamefrmPlan = true;
                unitMeasureFrmPlan = true;

            } else if (select.equalsIgnoreCase("Service")) {
                planNoFrItem = true;
                prFrItem = false;

            } else if (select.equalsIgnoreCase("Service")) {

            }
        }

    }

    public void changeSourceOfFinType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Governoment")) {
                loanAndCredit = false;

            } else {
                loanAndCredit = true;
            }
        }

    }

    public void changePercent(ValueChangeEvent e) {
        if (e.getNewValue().equals("merit")) {
            merit = true;
            compliance = false;

        } else {
            merit = false;
            compliance = true;
        }

    }

    public void changeLot(ValueChangeEvent e) {
        if (e.getNewValue().equals("Lot Base")) {
            lotBase = false;

        } else {
            lotBase = true;

        }

    }

    public void changeDocumentFee(ValueChangeEvent e) {
        if (e.getNewValue().equals("Open Bid")) {
            documentFee = false;
            currencyfrDocument = false;
        } else {
            documentFee = true;
            currencyfrDocument = true;
        }
        if (e.getNewValue().equals("Open Bid")) {

        } else if (e.getNewValue().equals("Two Step Bid")) {

        } else if (e.getNewValue().equals("Restricted Bid")) {

        } else if (e.getNewValue().equals("Request For Proposal")) {

        } else if (e.getNewValue().equals("diect Purchase")) {

        }
    }

    public void changeWork(ValueChangeEvent e) {

        select = e.getNewValue().toString();
        procurementCategory = select;
        if (select.equalsIgnoreCase("Goods")) {
            siteVisite = true;
            preMeetingPlace = true;
            PrebidMeeting = true;
            MinuteNo = true;
        } else if (select.equalsIgnoreCase("Service")) {

            siteVisite = true;
            preMeetingPlace = true;
            PrebidMeeting = true;
            MinuteNo = true;

        } else if (select.equalsIgnoreCase("Work")) {

            siteVisite = false;
            preMeetingPlace = false;
            PrebidMeeting = false;
            MinuteNo = false;
        }
    }

    public void changePurchaseType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Goods")) {
                itemNameFrmPr = true;
                ItemCodeFrmPr = true;
                unitMeasureFrmprFrItem = true;
                servicNmaeFrmPr = false;
                serviceNoFrmpr = false;
                unitMeasureFrmPrFrService = false;
                workNofrmpr = false;
                worknmeFrmPr = false;
                UnitMeasurefrmPrfrWork = false;
                serviceType = false;
                prFrItem = true;
                workName = false;

            } else if (select.equalsIgnoreCase("Service")) {
                itemNameFrmPr = false;
                ItemCodeFrmPr = false;
                unitMeasureFrmprFrItem = false;
                servicNmaeFrmPr = true;
                serviceNoFrmpr = true;
                unitMeasureFrmPrFrService = true;
                workNofrmpr = false;
                worknmeFrmPr = false;
                UnitMeasurefrmPrfrWork = false;
                serviceType = true;
                prFrItem = false;
                workName = false;

            } else if (select.equalsIgnoreCase("Work")) {
                itemNameFrmPr = false;
                ItemCodeFrmPr = false;
                unitMeasureFrmprFrItem = false;
                servicNmaeFrmPr = false;
                serviceNoFrmpr = false;
                unitMeasureFrmPrFrService = false;
                workNofrmpr = true;
                worknmeFrmPr = true;
                UnitMeasurefrmPrfrWork = true;
                serviceType = false;
                workName = true;
                prFrItem = false;
                prFrItem = false;

            }
        }
    }

    public Date registrationDate() {
        return this.eepBidReg.getBidRegDate();
    }

    public Date bidOpenddate() {
        return this.eepBidReg.getBidCloseDateTime();
    }

    public Date bidOpeningDate() {
        return this.eepBidReg.getBidOpenDateTime();
    }

    public void uploadListener1(FileUploadEvent choiced) {
        InputStream fileByteFile_ = null;
        docFormat = choiced.getFile().getFileName().split("\\.")[1];
        fileName = choiced.getFile().getFileName().split("\\.")[0];
        try {
            fileByteFile_ = choiced.getFile().getInputstream();
        } catch (IOException e) {
        }
        byteData = dataUpload.extractByteArray(fileByteFile_);
        if (byteData != null) {
            JsfUtil.addSuccessMessage("U uploaded " + fileName + " with Format " + docFormat + " Successfully");
        }
    }
    DocumentModel documentModel;
    DataModel<DocumentModel> fileDataModel;
    StreamedContent fileDownload;

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public DataModel<DocumentModel> getFileDataModel() {
        if (fileDataModel == null) {
            fileDataModel = new ListDataModel<>();
        }
        return fileDataModel;
    }

    public void setFileDataModel(DataModel<DocumentModel> fileDataModel) {
        this.fileDataModel = fileDataModel;
    }
    /* method used for counting row select
    
     */

    public void getDocValue() {
        fileDataModel = dataUpload.selectListOfFileByDocId(documentId);

    }
    /* method used for file row select
    
     */

    public void rowSelectFile(SelectEvent event) {
        setIsRowFileSelected(true);
        documentModel = (DocumentModel) event.getObject();
    }
    /* method used for showing dowloading progress 
     */

    public StreamedContent getFileDownload() throws Exception {
        if (isRowFileSelected == true) {
            //            fileDownload = dataUpload.getFile(documentModel);
            fileDownload = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
        }
        return fileDownload;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public void fileRowSelect(SelectEvent file) {
        documentModel = (DocumentModel) file.getObject();
    }

    public void getSelectedDocFile() {
        fileDataModel = dataUpload.selectListOfFileByDocId(documentLists);
    }
    //</editor-fold>
}
