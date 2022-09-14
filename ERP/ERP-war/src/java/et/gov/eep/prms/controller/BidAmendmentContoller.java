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
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.businessLogic.BidAmendBeanLocal;
import et.gov.eep.prms.businessLogic.BidRegBeanLocal;
import et.gov.eep.prms.businessLogic.MarketAssesmntBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidCriteria;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidDetailAmend;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@Named("bidAmendmentContoller")
@ViewScoped
public class BidAmendmentContoller implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="declaration and initialization">

    @EJB
    BidAmendBeanLocal amendBeanLocal;
    @EJB
    BidRegBeanLocal bidRegBeanLocal;
    @EJB
    MarketAssesmntBeanLocal marketAssesmntBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @Inject
    PrmsBidAmend prmsBidAmend;
    @Inject
    PrmsBid prmsBid;
    @Inject
    PrmsBidDetailAmend prmsBidDetailAmend;
    @Inject
    MmsItemRegistration mmsItemRegistration;
    @Inject
    PrmsBidDetail prmsBidDetail;
    @Inject
    PrmsServiceAndWorkReg prmsServiceAndWorkReg;
    @Inject
    DataUpload dataUpload;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    DataModel<PrmsBidDetailAmend> bidDetailAmendMdl;
    DataModel<PrmsBidAmend> bidAmendModel;
    DataModel<WfPrmsProcessed> workFlowModel;
    List<PrmsBid> prmsBidLst;
    List<PrmsBidDetail> prmsBidDetailLst;
    List<PrmsBidDetailAmend> prmsBidDetailAmendLst;
    List<PrmsBidAmend> prmsBidAmendLst;
    List<MmsItemRegistration> itemRegistrationList;
    ArrayList<PrmsBidCriteria> prmsBidCriteriaList;
    List<PmsCreateProjects> pmsCreateProjectseList;
    DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    List<PrmsBidAmend> bidAmendmentSearchParameterLst;
    DocumentModel documentModel;
    DataModel<DocumentModel> fileDataModel;
    private PrmsLuDmArchive prmsLuDmArchiveSelection;
    StreamedContent fileDownload;
    String selectedValue = "";
    private String saveorUpdateBundle = "Amend";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private String duplicattion = null;
    private String userName = "";
    byte[] byteData;
    String docFormat;
    String fileName;
    Integer requestNotificationCounter = 0;
    int uploadId;
    int updateStatus = 0;
    private boolean percentInput = true;
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean documentFee = true;
    private boolean serviceName;
    private boolean workName;
    private boolean goodsName;
    private boolean itemNameFrmPr;
    private boolean ItemCodeFrmPr;
    private boolean unitMeasureFrmprFrItem;
    private boolean serviceNoFrmpr;
    private boolean servicNmaeFrmPr;
    private boolean unitMeasureFrmPrFrService;
    private boolean workNofrmpr;
    private boolean worknmeFrmPr;
    private boolean UnitMeasurefrmPrfrWork;
    private boolean prFrItem;
    private boolean prfrWork;
    private boolean prFrConsultancy;
    private boolean prFrNonConsutancy;
    private boolean requestfromPr;
    private boolean requestFromPlan;
    private boolean serviceType;
    private boolean consultancy;
    private boolean nonConsultancy;
    private boolean siteVisite;
    private boolean preMeetingPlace;
    private boolean PrebidMeeting;
    private boolean MinuteNo;
    private boolean currencyfrDocument;
    private boolean passLimit;
    private boolean technical;
    private boolean financial;
    private boolean lotBase;
    private boolean loanAndCredit;
    private boolean planNo;
    private boolean prNo;
    private boolean planNoFrItem;
    private boolean servicefrmPlan;
    private boolean serviceFrmPr;
    private boolean workFrmPlan;
    private boolean itemFromProjectPln;
    private boolean serviceFrmProjectPln;
    private boolean workFrmProjectPln;
    private boolean workTable;
    private boolean serviceTable;
    private boolean itemTable;
    private boolean amendment;
    private boolean clarification;
    private boolean sourceOfFinan = false;
    private boolean governoment = false;
    private boolean compliance;
    private boolean merit;
    private boolean renderpnlToSearchPage;
    private boolean isRenderedIconWorkflow = false;
    private boolean isDecision = false;
    private boolean isCommentGiven = false;
    private boolean isCreateButton;
    private boolean isRowFileSelected;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstruct Method">
    @PostConstruct
    public void init() {
        prmsBidLst = amendBeanLocal.getBidNoLst();
        fmsLuCurrencyList = marketAssesmntBeanLocal.getCurrency();
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setUserName(workFlow.getUserName());
//        getBidAmendNoWithCl();
        System.out.println("" + workFlow.isApproveStatus() + "or" + workFlow.isPrepareStatus() + "or " + workFlow.isCheckStatus());
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

    //<editor-fold defaultstate="collapsed" desc="Set and get method">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchiveDataModel() {
        if (prmsLuDmArchiveDataModel == null) {
            prmsLuDmArchiveDataModel = new ListDataModel<>();
        }
        return prmsLuDmArchiveDataModel;
    }

    public void setPrmsLuDmArchiveDataModel(DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel) {
        this.prmsLuDmArchiveDataModel = prmsLuDmArchiveDataModel;
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

    public void rectreateFileUpload() {
        prmsLuDmArchiveDataModel = null;
        prmsLuDmArchiveDataModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public List<PmsCreateProjects> getPmsCreateProjectseList() {
        return pmsCreateProjectseList;
    }

    public void setPmsCreateProjectseList(List<PmsCreateProjects> pmsCreateProjectseList) {
        this.pmsCreateProjectseList = pmsCreateProjectseList;
    }

    public ArrayList<PrmsBidCriteria> getPrmsBidCriteriaList() {
        if (prmsBidCriteriaList == null) {
            prmsBidCriteriaList = new ArrayList<>();
        }
        return prmsBidCriteriaList;
    }

    public void setPrmsBidCriteriaList(ArrayList<PrmsBidCriteria> prmsBidCriteriaList) {
        this.prmsBidCriteriaList = prmsBidCriteriaList;
    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public DataModel<DocumentModel> getFileDataModel() {
        return fileDataModel;
    }

    public void setFileDataModel(DataModel<DocumentModel> fileDataModel) {
        this.fileDataModel = fileDataModel;
    }

    public StreamedContent getFileDownload() throws Exception {
        if (isRowFileSelected == true) {
            System.out.println("When Downloading");
            fileDownload = dataUpload.getFile(documentModel);
            System.out.println("you downloaded  " + fileDownload.getName());
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
        }
        return fileDownload;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsBidAmend = (PrmsBidAmend) event.getNewValue();
            popUp();
            saveorUpdateBundle = "Update";
        }
    }

    public Integer getRequestNotificationCounter() {
        prmsBidAmendLst = amendBeanLocal.getBidAmendmentOnList();
        requestNotificationCounter = prmsBidAmendLst.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public boolean isItemFromProjectPln() {
        return itemFromProjectPln;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
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

    public void setItemFromProjectPln(boolean itemFromProjectPln) {
        this.itemFromProjectPln = itemFromProjectPln;
    }

    public boolean isServiceFrmProjectPln() {
        return serviceFrmProjectPln;
    }

    public void setServiceFrmProjectPln(boolean serviceFrmProjectPln) {
        this.serviceFrmProjectPln = serviceFrmProjectPln;
    }

    public boolean isWorkFrmProjectPln() {
        return workFrmProjectPln;
    }

    public void setWorkFrmProjectPln(boolean workFrmProjectPln) {
        this.workFrmProjectPln = workFrmProjectPln;
    }

    public boolean isCompliance() {
        return compliance;
    }

    public void setCompliance(boolean compliance) {
        this.compliance = compliance;
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

    public boolean isSourceOfFinan() {
        return sourceOfFinan;
    }

    public void setSourceOfFinan(boolean sourceOfFinan) {
        this.sourceOfFinan = sourceOfFinan;
    }

    public boolean isWorkTable() {
        return workTable;
    }

    public boolean isAmendment() {
        return amendment;
    }

    public void setAmendment(boolean amendment) {
        this.amendment = amendment;
    }

    public boolean isClarification() {
        return clarification;
    }

    public void setClarification(boolean clarification) {
        this.clarification = clarification;
    }

    public boolean isServiceFrmPr() {
        return serviceFrmPr;
    }

    public void setServiceFrmPr(boolean serviceFrmPr) {
        this.serviceFrmPr = serviceFrmPr;
    }

    public void setWorkTable(boolean workTable) {
        this.workTable = workTable;
    }

    public boolean isServiceTable() {
        return serviceTable;
    }

    public void setServiceTable(boolean serviceTable) {
        this.serviceTable = serviceTable;
    }

    public boolean isItemTable() {
        return itemTable;
    }

    public void setItemTable(boolean itemTable) {
        this.itemTable = itemTable;
    }

    public boolean isServicefrmPlan() {
        return servicefrmPlan;
    }

    public void setServicefrmPlan(boolean servicefrmPlan) {
        this.servicefrmPlan = servicefrmPlan;
    }

    public boolean isWorkFrmPlan() {
        return workFrmPlan;
    }

    public void setWorkFrmPlan(boolean workFrmPlan) {
        this.workFrmPlan = workFrmPlan;
    }

    public boolean isPlanNoFrItem() {
        return planNoFrItem;
    }

    public void setPlanNoFrItem(boolean planNoFrItem) {
        this.planNoFrItem = planNoFrItem;
    }

    public boolean isPlanNo() {
        return planNo;
    }

    public void setPlanNo(boolean planNo) {
        this.planNo = planNo;
    }

    public boolean isPrNo() {
        return prNo;
    }

    public void setPrNo(boolean prNo) {
        this.prNo = prNo;
    }

    public boolean isLoanAndCredit() {
        return loanAndCredit;
    }

    public void setLoanAndCredit(boolean loanAndCredit) {
        this.loanAndCredit = loanAndCredit;
    }

    public boolean isLotBase() {
        return lotBase;
    }

    public void setLotBase(boolean lotBase) {
        this.lotBase = lotBase;
    }

    public boolean isPassLimit() {
        return passLimit;
    }

    public void setPassLimit(boolean passLimit) {
        this.passLimit = passLimit;
    }

    public boolean isTechnical() {
        return technical;
    }

    public void setTechnical(boolean technical) {
        this.technical = technical;
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

    public boolean isFinancial() {
        return financial;
    }

    public void setFinancial(boolean financial) {
        this.financial = financial;
    }

    public boolean isCurrencyfrDocument() {
        return currencyfrDocument;
    }

    public void setCurrencyfrDocument(boolean currencyfrDocument) {
        this.currencyfrDocument = currencyfrDocument;
    }

    public boolean isSiteVisite() {
        return siteVisite;
    }

    public void setSiteVisite(boolean siteVisite) {
        this.siteVisite = siteVisite;
    }

    public boolean isPreMeetingPlace() {
        return preMeetingPlace;
    }

    public void setPreMeetingPlace(boolean preMeetingPlace) {
        this.preMeetingPlace = preMeetingPlace;
    }

    public boolean isPrebidMeeting() {
        return PrebidMeeting;
    }

    public void setPrebidMeeting(boolean PrebidMeeting) {
        this.PrebidMeeting = PrebidMeeting;
    }

    public boolean isMinuteNo() {
        return MinuteNo;
    }

    public void setMinuteNo(boolean MinuteNo) {
        this.MinuteNo = MinuteNo;
    }

    public boolean isServiceType() {
        return serviceType;
    }

    public void setServiceType(boolean serviceType) {
        this.serviceType = serviceType;
    }

    public boolean isConsultancy() {
        return consultancy;
    }

    public void setConsultancy(boolean consultancy) {
        this.consultancy = consultancy;
    }

    public boolean isNonConsultancy() {
        return nonConsultancy;
    }

    public void setNonConsultancy(boolean nonConsultancy) {
        this.nonConsultancy = nonConsultancy;
    }

    public boolean isRequestfromPr() {
        return requestfromPr;
    }

    public void setRequestfromPr(boolean requestfromPr) {
        this.requestfromPr = requestfromPr;
    }

    public boolean isRequestFromPlan() {
        return requestFromPlan;
    }

    public void setRequestFromPlan(boolean requestFromPlan) {
        this.requestFromPlan = requestFromPlan;
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

    public boolean isUnitMeasureFrmprFrItem() {
        return unitMeasureFrmprFrItem;
    }

    public void setUnitMeasureFrmprFrItem(boolean unitMeasureFrmprFrItem) {
        this.unitMeasureFrmprFrItem = unitMeasureFrmprFrItem;
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

    public boolean isUnitMeasureFrmPrFrService() {
        return unitMeasureFrmPrFrService;
    }

    public void setUnitMeasureFrmPrFrService(boolean unitMeasureFrmPrFrService) {
        this.unitMeasureFrmPrFrService = unitMeasureFrmPrFrService;
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

    public boolean isUnitMeasurefrmPrfrWork() {
        return UnitMeasurefrmPrfrWork;
    }

    public void setUnitMeasurefrmPrfrWork(boolean UnitMeasurefrmPrfrWork) {
        this.UnitMeasurefrmPrfrWork = UnitMeasurefrmPrfrWork;
    }

    public boolean isServiceName() {
        return serviceName;
    }

    public void setServiceName(boolean serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isWorkName() {
        return workName;
    }

    public void setWorkName(boolean workName) {
        this.workName = workName;
    }

    public boolean isGoodsName() {
        return goodsName;
    }

    public void setGoodsName(boolean goodsName) {
        this.goodsName = goodsName;
    }

    public boolean isDocumentFee() {
        return documentFee;
    }

    public void setDocumentFee(boolean documentFee) {
        this.documentFee = documentFee;
    }

    public boolean isPercentInput() {
        return percentInput;
    }

    public void setPercentInput(boolean percentInput) {
        this.percentInput = percentInput;
    }

    private PrmsBidAmend prmsBidAmendSelect;

    public PrmsBidAmend getPrmsBidAmendSelect() {
        return prmsBidAmendSelect;
    }

    public void setPrmsBidAmendSelect(PrmsBidAmend prmsBidAmendSelect) {
        this.prmsBidAmendSelect = prmsBidAmendSelect;
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

    public DataModel<WfPrmsProcessed> getWorkFlowModel() {
        if (workFlowModel == null) {
            workFlowModel = new ListDataModel<>();
        }
        return workFlowModel;
    }

    public void setWorkFlowModel(DataModel<WfPrmsProcessed> workFlowModel) {
        this.workFlowModel = workFlowModel;
    }

    public DataModel<PrmsBidAmend> getBidAmendModel() {
        if (bidAmendModel == null) {
            bidAmendModel = new ListDataModel<>();

        }
        return bidAmendModel;
    }

    public void setBidAmendModel(DataModel<PrmsBidAmend> bidAmendModel) {
        this.bidAmendModel = bidAmendModel;
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

    public PrmsServiceAndWorkReg getPrmsServiceAndWorkReg() {
        if (prmsServiceAndWorkReg == null) {
            prmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
        }
        return prmsServiceAndWorkReg;
    }

    public void setPrmsServiceAndWorkReg(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        this.prmsServiceAndWorkReg = prmsServiceAndWorkReg;
    }

    public List<PrmsBidAmend> getPrmsBidAmendLst() {
        if (prmsBidAmendLst == null) {
            prmsBidAmendLst = new ArrayList<>();
        }
        return prmsBidAmendLst;
    }

    public void setPrmsBidAmendLst(List<PrmsBidAmend> prmsBidAmendLst) {
        this.prmsBidAmendLst = prmsBidAmendLst;
    }

    public List<PrmsBidDetailAmend> getPrmsBidDetailAmendLst() {
        if (prmsBidDetailAmendLst == null) {
            prmsBidDetailAmendLst = new ArrayList<>();
        }
        return prmsBidDetailAmendLst;
    }

    public void setPrmsBidDetailAmendLst(List<PrmsBidDetailAmend> prmsBidDetailAmendLst) {
        this.prmsBidDetailAmendLst = prmsBidDetailAmendLst;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PrmsBidDetail> getPrmsBidDetailLst() {
        if (prmsBidDetailLst == null) {
            prmsBidDetailLst = new ArrayList<>();
        }
        return prmsBidDetailLst;
    }

    public void setPrmsBidDetailLst(List<PrmsBidDetail> prmsBidDetailLst) {
        this.prmsBidDetailLst = prmsBidDetailLst;
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
    private String icone = "ui-icon-plus";

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

    public PrmsBidDetailAmend getPrmsBidDetailAmend() {
        if (prmsBidDetailAmend == null) {
            prmsBidDetailAmend = new PrmsBidDetailAmend();
        }
        return prmsBidDetailAmend;
    }

    public void setPrmsBidDetailAmend(PrmsBidDetailAmend prmsBidDetailAmend) {
        this.prmsBidDetailAmend = prmsBidDetailAmend;
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

    public List<PrmsBid> getPrmsBidLst() {
        if (prmsBidLst == null) {
            prmsBidLst = new ArrayList();
        }
        return prmsBidLst;
    }

    public void setPrmsBidLst(List<PrmsBid> prmsBidLst) {
        this.prmsBidLst = prmsBidLst;
    }

    public DataModel<PrmsBidDetailAmend> getBidDetailAmendMdl() {
        if (bidDetailAmendMdl == null) {
            bidDetailAmendMdl = new ListDataModel<>();
        }
        return bidDetailAmendMdl;
    }

    public void setBidDetailAmendMdl(DataModel<PrmsBidDetailAmend> bidDetailAmendMdl) {
        this.bidDetailAmendMdl = bidDetailAmendMdl;
    }

    public void recreatModel() {
        bidDetailAmendMdl = null;
        bidDetailAmendMdl = new ListDataModel<>(new ArrayList(prmsBidAmend.getPrmsBidDetailAmendList()));
    }

    public void reCreatAmendMdl() {
        bidAmendModel = null;
        bidAmendModel = new ListDataModel<>(new ArrayList<>(getPrmsBidAmendLst()));
    }

    public void clearSearch() {
        prmsBidAmend = null;
        prmsBidDetailAmend = null;
        bidDetailAmendMdl = null;
        selectedBidCriteria = null;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public ArrayList<PrmsBidCriteria> getBidCriteriaLst() {
        setPrmsBidCriteriaList(bidRegBeanLocal.getBidCriteriaList());
        return prmsBidCriteriaList;
    }

    public void amendBid() {
        saveorUpdateBundle = "Amend";
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

    public void search_BidAmendment() {
        prmsBidAmend.setPreparedBy(workFlow.getUserAccount());
        prmsBidAmendLst = amendBeanLocal.searchBidNoAmnedNo(prmsBidAmend);
        reCreatAmendMdl();
        prmsBidAmend = new PrmsBidAmend();
    }

    public void changePercent(ValueChangeEvent e) {
        if (e.getNewValue().equals("merit")) {
            compliance = false;
            merit = true;

        } else {
            compliance = true;
            merit = false;
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

    }

    public void valueChangeMarketApp(ValueChangeEvent event) {
        String marketApp = event.getNewValue().toString();
        if (event.getNewValue() != null) {
            if (marketApp.equalsIgnoreCase("local")) {
                sourceOfFinan = true;
                governoment = false;
            } else if (marketApp.equalsIgnoreCase("international")) {
                governoment = true;
                sourceOfFinan = false;
            }
        }
    }

    public void valueChangeBidNo(ValueChangeEvent event) {
        prmsBidAmend.getPrmsBidDetailAmendList().clear();
        bidDetailAmendMdl = null;
        prmsBidDetailLst = amendBeanLocal.getBidNo(event.getNewValue().toString());
        prmsBid = amendBeanLocal.getBidNum(event.getNewValue().toString());
        prmsBidAmendLst = amendBeanLocal.getAmendNo(event.getNewValue().toString());
        selectedBidCriteria = prmsBid.getBidCriteriaId().split(",");
        uploadId = prmsBid.getFileUploadRefno();
//        doList.add(uploadId);
//        getDocValue();
        prmsBidAmend.setRefNo(String.valueOf(prmsBid.getRefNo()));
        prmsBidAmend.setBidType(String.valueOf(prmsBid.getBidType()));
        prmsBidAmend.setBidCatId(String.valueOf(prmsBid.getBidCategory()));
        prmsBidAmend.setBidSecurityType(String.valueOf(prmsBid.getBidSecurityType()));
        prmsBidAmend.setBidSecurityPrice((prmsBid.getBidSecurityPrice()));
        prmsBidAmend.setBidDocPrice(prmsBid.getBidDocPrice());
        prmsBidAmend.setIssueDate(prmsBid.getIssueDate());
        prmsBidAmend.setBidOpenDateTime(prmsBid.getBidOpenDateTime());
        prmsBidAmend.setBidCloseDateTime(prmsBid.getBidCloseDateTime());
        prmsBidAmend.setMinuteNo(prmsBid.getMinuteNo());
        prmsBidAmend.setPurchaseMethd(prmsBid.getPurchaseMethd());
        prmsBidAmend.setAwardType(prmsBid.getAwardType());
        prmsBidAmend.setPostQualification(prmsBid.getPostQualification());
        prmsBidAmend.setBidValidity(prmsBid.getBidValidity());
        prmsBidAmend.setSiteVisitDate(prmsBid.getSiteVisit());
        prmsBidAmend.setBidRegDate(prmsBid.getBidRegDate());
        prmsBidAmend.setProjectId(prmsBid.getProjectId());
        prmsBidAmend.setCurrencyId(prmsBid.getCurrencyId());
        prmsBidAmend.setBidCriteriaId(String.valueOf(prmsBid.getBidCriteriaId().split(",")));
        prmsBidAmend.setFileUploadRefno(prmsBid.getFileUploadRefno());
        for (int i = 0; i < prmsBidDetailLst.size(); i++) {
            prmsBidDetailAmend = new PrmsBidDetailAmend();
            prmsBidDetailAmend.setBidAmendId(prmsBidAmend);
            prmsBidDetailAmend.setQuantity(prmsBidDetailLst.get(i).getQuantity());
            prmsBidDetailAmend.setCreditNo((prmsBidDetailLst.get(i).getCreditNo()));
            prmsBidDetailAmend.setFermSeltionMethd((prmsBidDetailLst.get(i).getFermSeltionMethd()));
            prmsBidDetailAmend.setItemRegId(prmsBidDetailLst.get(i).getItemRegId());
            prmsBidDetailAmend.setServiceId(prmsBidDetailLst.get(i).getServiceId());
            prmsBidDetailAmend.setPrId(prmsBidDetailLst.get(i).getPrId());
            prmsBidDetailAmend.setPlanId(prmsBidDetailLst.get(i).getPlanId());
            prmsBidDetailAmend.setServiceType(prmsBidDetailLst.get(i).getServiceType());
            prmsBidDetailAmend.setSourceOfInitation(prmsBidDetailLst.get(i).getSourceOfInitaition());
            prmsBidDetailAmend.setSourceOfFinance(prmsBidDetailLst.get(i).getSourceOfFinance());
            prmsBidDetailAmend.setTechnical(prmsBidDetailLst.get(i).getTechnical());
            prmsBidDetailAmend.setFinancial(prmsBidDetailLst.get(i).getFinancial());
            prmsBidDetailAmend.setPasslimit(prmsBidDetailLst.get(i).getPassLimit());
            prmsBidAmend.getPrmsBidDetailAmendList().add(prmsBidDetailAmend);

        }
        recreatModel();
        String select = prmsBid.getBidCategory();
        String marketApp = prmsBid.getBidType();

        prNo = true;
        planNo = true;
        if (select.equalsIgnoreCase("Goods")) {
            serviceTable = false;
            itemTable = true;
            workTable = false;
            siteVisite = true;
            MinuteNo = true;
            preMeetingPlace = true;
            PrebidMeeting = true;

        } else if (select.equalsIgnoreCase("Service")) {
            serviceTable = true;
            itemTable = false;
            workTable = false;
            siteVisite = true;
            MinuteNo = true;
            preMeetingPlace = true;
            PrebidMeeting = true;
        } else if (select.equalsIgnoreCase("Work")) {
            serviceTable = false;
            itemTable = false;
            workTable = true;
            siteVisite = false;
            MinuteNo = false;
            preMeetingPlace = false;
            PrebidMeeting = false;
        }
        if (marketApp.equalsIgnoreCase("local")) {
            sourceOfFinan = true;
        } else if (marketApp.equalsIgnoreCase("international")) {
            governoment = true;
        }

    }

    public void valueChangeBidNoForBoth(ValueChangeEvent event) {
        System.out.println("=========amend number v1 ====" + event.getNewValue().toString());

        prmsBidAmend = amendBeanLocal.getBidNumber(event.getNewValue().toString());
        System.out.println("=========before ====" + prmsBidAmend.getId());
        prmsBidAmend.setId("");
        System.out.println("=========after ====" + prmsBidAmend.getId());
        recreatModel();

    }

    String newAmendNo;

    public String generateBidAmendNum() {
        if (saveorUpdateBundle.equals("Update")) {
            newAmendNo = prmsBidAmend.getAmendNo();

        } else {

            newAmendNo = getNextAmendRegNo();

        }
        return newAmendNo;
    }
    String newClarNum;

    public String generateBidClarificationNum() {
        if (saveorUpdateBundle.equals("Update")) {
            newClarNum = prmsBidAmend.getClarificationNo();

        } else {

            newClarNum = amendBeanLocal.getNextClarificationSeq();

        }
        return newClarNum;
    }

    public String getNextClarificationSeq() {
        String clarNum = amendBeanLocal.getNextClarificationSeq();
        return clarNum;
    }

    public String getNextAmendRegNo() {
        newAmendNo = amendBeanLocal.getNextAmendRegNo();
        return newAmendNo;
    }

    public SelectItem[] getBidAmendNo() {
        return JsfUtil.getSelectItems(amendBeanLocal.getListOfBidAmendNum(), true);
    }

    public List<PmsCreateProjects> getProjectIds() {
        System.out.println("=========check it ====");
        setPmsCreateProjectseList(bidRegBeanLocal.getProjectId());
        System.out.println("0000000" + pmsCreateProjectseList.size());
        return pmsCreateProjectseList;
    }
    List<FmsLuCurrency> fmsLuCurrencyList;

    public List<FmsLuCurrency> getFmsLuCurrencyList() {
        return fmsLuCurrencyList;
    }

    public void setFmsLuCurrencyList(List<FmsLuCurrency> fmsLuCurrencyList) {
        this.fmsLuCurrencyList = fmsLuCurrencyList;
    }

    public void clear() {
        prmsBidAmend = null;
        prmsBidDetailAmend = null;
        saveorUpdateBundle = "Amend";
        selectedBidCriteria = null;
        wfPrmsProcessed = null;
        bidDetailAmendMdl = null;
        prmsBid = null;
    }
    private String[] selectedBidCriteria;

    public String[] getSelectedBidCriteria() {
        return selectedBidCriteria;
    }

    public void setSelectedBidCriteria(String[] selectedBidCriteria) {
        this.selectedBidCriteria = selectedBidCriteria;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Method">
    public List<PrmsBidAmend> getBidAmendmentSearchParameterLst() {
        if (bidAmendmentSearchParameterLst == null) {
            bidAmendmentSearchParameterLst = new ArrayList<>();
            bidAmendmentSearchParameterLst = amendBeanLocal.getParamNameList();
        }
        return bidAmendmentSearchParameterLst;
    }

    public void setBidAmendmentSearchParameterLst(List<PrmsBidAmend> bidAmendmentSearchParameterLst) {
        this.bidAmendmentSearchParameterLst = bidAmendmentSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsBidAmend.setColumnName(event.getNewValue().toString());
            prmsBidAmend.setColumnValue(null);
        }
    }

    public void uploadListener1(FileUploadEvent choiced) {
        InputStream fileByteFile_ = null;
        docFormat = choiced.getFile().getFileName().split("\\.")[1];
        fileName = choiced.getFile().getFileName().split("\\.")[0];
//        String fileNameWzExtent = choiced.getFile().getFileName();
//        String categoryBundle = "et/gov/eep/commonApplications/securityServer";
        try {
            fileByteFile_ = choiced.getFile().getInputstream();
        } catch (IOException e) {
            System.out.println("Upload Error[from Lisener]==>" + e.getMessage());
        }
        byteData = dataUpload.extractByteArray(fileByteFile_);
//        uploadId = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        System.out.println("Byte Array is===>" + byteData);
        if (byteData != null) {
            System.out.println("Uploaded Successfully");
            JsfUtil.addSuccessMessage("U uploaded " + fileName + " with Format " + docFormat + " Successfully");
        }
    }

    public String save_BidAmendment() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_BidAmendment", dataset)) {
                try {
                    if (saveorUpdateBundle.equals("Amend")) {
                        PrmsBidAmend bidAmend = new PrmsBidAmend();
                        int size = selectedBidCriteria.length;

                        if (prmsBidAmend.getAmendNumRef() != null) {
                            System.out.println("ameded=  v.1>");
                            if (size != 0) {
                                String newbidCreatria = "";
                                for (int i = 0; i < size; i++) {
                                    newbidCreatria = newbidCreatria + selectedBidCriteria[i] + ",";
                                    System.out.println("...........newBidCreateria........" + newbidCreatria);
                                }
                                bidAmend.setBidCriteriaId(newbidCreatria);
                            }
                            bidAmend.setAmendNo(prmsBidAmend.getAmendNo());
                            bidAmend.setAmendNumRef(prmsBidAmend.getAmendNo());
                            bidAmend.setRefNo(prmsBidAmend.getRefNo());
                            bidAmend.setBidType(prmsBidAmend.getBidType());
                            bidAmend.setBidCatId(prmsBidAmend.getBidCatId());
                            bidAmend.setBidSecurityType(prmsBidAmend.getBidSecurityType());
                            bidAmend.setBidSecurityPrice(prmsBidAmend.getBidSecurityPrice());
                            bidAmend.setBidDocPrice(prmsBidAmend.getBidDocPrice());
                            bidAmend.setIssueDate(prmsBidAmend.getIssueDate());
                            bidAmend.setBidOpenDateTime(prmsBid.getBidOpenDateTime());
                            bidAmend.setBidCloseDateTime(prmsBidAmend.getBidCloseDateTime());
                            bidAmend.setMinuteNo(prmsBidAmend.getMinuteNo());
                            bidAmend.setPurchaseMethd(prmsBidAmend.getPurchaseMethd());
                            bidAmend.setAwardType(prmsBidAmend.getAwardType());
                            bidAmend.setPostQualification(prmsBidAmend.getPostQualification());
                            bidAmend.setBidValidity(prmsBidAmend.getBidValidity());
                            bidAmend.setSiteVisitDate(prmsBidAmend.getSiteVisitDate());
                            bidAmend.setBidRegDate(prmsBidAmend.getBidRegDate());
                            bidAmend.setProjectId(prmsBidAmend.getProjectId());
                            bidAmend.setCurrencyId(prmsBidAmend.getCurrencyId());
                            bidAmend.setFileUploadRefno(prmsBidAmend.getFileUploadRefno());
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(docFormat);
                            prmsLuDmArchive.setUploadFile(byteData);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);

                            wfPrmsProcessed.setDecision(wfPrmsProcessed.getDecision());
                            if (prmsBidAmend.getType().equalsIgnoreCase("Amend")) {
                                generateBidAmendNum();
//                                bidAmend.setAmendNo(newAmendNo);
                            } else {
                                generateBidClarificationNum();
//                                bidAmend.setClarificationNo(newAmendNo);
                            }

                            bidAmend.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            bidAmend.setBidRegDate(wfPrmsProcessed.getProcessedOn());
                            bidAmend.setDocumentId(prmsLuDmArchive);
                            bidAmend.setStatus(Constants.PREPARE_VALUE);
                            prmsBidDetailAmend.setBidAmendId(bidAmend);
                            bidAmend.getPrmsBidDetailAmendList().add(prmsBidDetailAmend);
                            amendBeanLocal.save(bidAmend);
                            bidAmend.getWfPrmsProcessedCollection().add(wfPrmsProcessed);
                        } else {
                            System.out.println("save===>");

                            if (size != 0) {
                                String newbidCreatria = "";
                                for (int i = 0; i < size; i++) {
                                    newbidCreatria = newbidCreatria + selectedBidCriteria[i] + ",";
                                    System.out.println("...........newBidCreateria........" + newbidCreatria);
                                }
                                prmsBidAmend.setBidCriteriaId(newbidCreatria);
                            }
                            prmsBidAmend.setBidId(prmsBid);
                            prmsBidAmend.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            prmsBidAmend.setDocumentId(prmsLuDmArchive);
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(docFormat);
                            prmsLuDmArchive.setUploadFile(byteData);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            wfPrmsProcessed.setDecision(wfPrmsProcessed.getDecision());
                            prmsBidAmend.setAmendNo(newAmendNo);
                            prmsBidAmend.setClarificationNo(newClarNum);
                            amendBeanLocal.save(prmsBidAmend);

                        }

                        System.out.println("...........newBidCreateria........" + prmsBidAmend.getId());
                        clear();
                        JsfUtil.addSuccessMessage("Data is Saved");
                    } else if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                        int size = selectedBidCriteria.length;
                        if (size != 0) {
                            String newbidCreatria = "";

                            for (int i = 0; i < size; i++) {

                                newbidCreatria = newbidCreatria + selectedBidCriteria[i] + ",";

                                System.out.println("...........newBidCreateria........" + newbidCreatria);

                            }
                            prmsBidAmend.setBidCriteriaId(newbidCreatria);
                        }
//                        prmsBidAmend.setDocumentId(prmsLuDmArchive);
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(docFormat);
                        prmsLuDmArchive.setUploadFile(byteData);
                        prmsLuDmArchiveBeanLocal.updateFileInfo(prmsLuDmArchive);
                        prmsBidAmend.setAmendNo(newAmendNo);
                        prmsBidAmend.setFileUploadRefno(uploadId);
                        prmsBidAmend.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        prmsBidAmend.setBidRegDate(wfPrmsProcessed.getProcessedOn());
                        prmsBidAmend.setDescription(wfPrmsProcessed.getCommentGiven());
                        amendBeanLocal.edit(prmsBidAmend);
                        clear();
                        JsfUtil.addSuccessMessage("Data is UpDated");
                        saveorUpdateBundle = "Amend";
                    } else if (saveorUpdateBundle.equals("Update") && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            prmsBidAmend.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        }
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            prmsBidAmend.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            prmsBidAmend.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            prmsBidAmend.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        amendBeanLocal.edit(prmsBidAmend);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        wfPrmsProcessed = null;
                        JsfUtil.addSuccessMessage("Data is UpDated");
                    }

                } catch (Exception e) {
                    JsfUtil.addErrorMessage("data is not saved");
                    clear();
                    e.printStackTrace();
                }

                return null;
            } else {

                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
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
    int selectedRowIndex;

    public void addTable() {
        prmsBidAmend.getPrmsBidDetailAmendList().set(getSelectedRowIndex(), prmsBidDetailAmend);
        prmsBidDetailAmend.setBidAmendId(prmsBidAmend);
        recreatModel();
        prmsBidDetailAmend = null;
    }

    public void edit() {

        prmsBidDetailAmend = bidDetailAmendMdl.getRowData();
        selectedRowIndex = bidDetailAmendMdl.getRowIndex();
        String select = prmsBidDetailAmend.getFermSeltionMethd();

        if (select.equalsIgnoreCase("merit")) {
            merit = true;
            compliance = false;
        } else if (select.equalsIgnoreCase("compliance")) {
            merit = false;
            compliance = true;
        }
        String sourceOfInt = prmsBidDetailAmend.getSourceOfInitation();
        String selectPurchaseType = prmsBid.getBidCategory();
        if ("Request from Pr".equals(sourceOfInt)) {
            System.out.println("=========from pr");
            if (selectPurchaseType.equalsIgnoreCase("Goods")) {
                System.out.println("=========Goods from pr");
                planNoFrItem = false;
                prFrItem = true;

                workName = false;
                workFrmPlan = false;

                serviceFrmPr = false;
                servicefrmPlan = false;

            } else if (selectPurchaseType.equalsIgnoreCase("Work")) {
                workName = true;
                workFrmPlan = false;

                planNoFrItem = false;
                prFrItem = false;

                serviceFrmPr = false;
                servicefrmPlan = false;

            } else if (selectPurchaseType.equalsIgnoreCase("Service")) {
                serviceFrmPr = true;
                servicefrmPlan = false;

                planNoFrItem = false;
                prFrItem = false;

                workName = false;
                workFrmPlan = false;
            }
        } else if ("Request from Plan".equals(sourceOfInt)) {
            if (selectPurchaseType.equalsIgnoreCase("Goods")) {
                planNoFrItem = true;
                prFrItem = false;

                workName = true;
                workFrmPlan = false;

                serviceFrmPr = false;
                servicefrmPlan = false;

            } else if (selectPurchaseType.equalsIgnoreCase("Work")) {
                workName = false;
                workFrmPlan = true;

                serviceFrmPr = false;
                servicefrmPlan = false;

                planNoFrItem = false;
                prFrItem = false;

            } else if (selectPurchaseType.equalsIgnoreCase("Service")) {
                serviceFrmPr = false;
                servicefrmPlan = true;

                planNoFrItem = false;
                prFrItem = false;

                workName = false;
                workFrmPlan = false;
            }
        } else if ("Request from Project".equals(sourceOfInt)) {
            if (selectPurchaseType.equalsIgnoreCase("Goods")) {

                planNoFrItem = false;
                prFrItem = false;

                serviceFrmProjectPln = false;
                itemFromProjectPln = true;
                workFrmProjectPln = false;

                workName = false;
                workFrmPlan = false;

                serviceFrmPr = false;
                servicefrmPlan = false;

            } else if (selectPurchaseType.equalsIgnoreCase("Work")) {
                workName = false;
                workFrmPlan = false;

                serviceFrmProjectPln = false;
                itemFromProjectPln = false;
                workFrmProjectPln = true;

                serviceFrmPr = false;
                servicefrmPlan = false;

                planNoFrItem = false;
                prFrItem = false;

            } else if (selectPurchaseType.equalsIgnoreCase("Service")) {
                serviceFrmPr = false;
                servicefrmPlan = false;

                serviceFrmProjectPln = true;
                itemFromProjectPln = false;
                workFrmProjectPln = false;

                planNoFrItem = false;
                prFrItem = false;

                workName = false;
                workFrmPlan = false;

            }

        }
        String sourceOfFinancial = prmsBidDetailAmend.getSourceOfFinance();
        if ("Governoment".equals(sourceOfFinancial)) {
            loanAndCredit = false;
        } else {
            loanAndCredit = true;
        }

    }

    public void changeSourceOfFinType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Governoment")) {
                loanAndCredit = false;

            } else {
                loanAndCredit = true;
            }
        }

    }
    int status = 0;

    public void changeLot(ValueChangeEvent e) {
        if (e.getNewValue().equals("Lot Base")) {
            lotBase = false;

        } else {
            lotBase = true;
        }

    }

    public void changeServiceType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Consultancy")) {
                System.out.println("j");
                consultancy = true;
                nonConsultancy = false;

            } else {
                consultancy = false;
                nonConsultancy = true;

            }
        }
    }

    public void changeWork(ValueChangeEvent e) {
        String select;
        select = e.getNewValue().toString();
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
    List<Integer> doList = new ArrayList<>();

    public void rowSelect(SelectEvent event) {
        prmsBidAmend = (PrmsBidAmend) event.getObject();
        popUp();
        saveorUpdateBundle = "Update";

    }

    public void popUp() {
        prmsBidAmend.setId(prmsBidAmend.getId());
        prmsBidAmend = amendBeanLocal.getSelectedRequest(prmsBidAmend.getId());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        prmsBid = prmsBidAmend.getBidId();
        selectedBidCriteria = prmsBidAmend.getBidCriteriaId().split(",");
        String select = prmsBidAmend.getBidCatId();
        if ("Goods".equals(select)) {
            itemTable = true;
            serviceTable = false;
            workTable = false;
            siteVisite = true;
            preMeetingPlace = true;
            PrebidMeeting = true;
            MinuteNo = true;
        } else if ("Service".equals(select)) {
            siteVisite = true;
            preMeetingPlace = true;
            PrebidMeeting = true;
            MinuteNo = true;
            serviceTable = true;
            workTable = false;
            itemTable = false;
        } else if ("Work".equals(select)) {
            workTable = true;
            itemTable = false;
            siteVisite = false;
            preMeetingPlace = false;
            PrebidMeeting = false;
            MinuteNo = false;
        }
        String marketApp = prmsBidAmend.getBidType();

        if (marketApp.equalsIgnoreCase("local")) {
            sourceOfFinan = true;
            governoment = false;
        } else if (marketApp.equalsIgnoreCase("international")) {
            sourceOfFinan = false;
            governoment = true;

        }
        for (int i = 0; i < prmsBidAmend.getPrmsBidDetailAmendList().size(); i++) {
            String fermSelectionMethod = prmsBidAmend.getPrmsBidDetailAmendList().get(i).getFermSeltionMethd();
            String sourceOfFinance = prmsBidAmend.getPrmsBidDetailAmendList().get(i).getSourceOfFinance();
            if ("compliance".equals(fermSelectionMethod)) {
                compliance = true;
                merit = false;
            }
            if ("merit".equals(fermSelectionMethod)) {
                compliance = false;
                merit = true;
            }
            if ("Financer".equals(sourceOfFinance)) {
                loanAndCredit = true;
            } else {
                loanAndCredit = true;
            }
        }
        String type = prmsBidAmend.getType();
        if (type.equalsIgnoreCase("Amend")) {
            amendment = true;
            clarification = false;
        } else {
            amendment = false;
            clarification = true;
        }
        if (!saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsBidAmend.getBidRegDate());
        }
//        if (prmsBidAmend.getDocumentId() != null) {
//            prmsLuDmArchive = prmsBidAmend.getDocumentId();
//            System.out.println("Pmslu Id " + prmsLuDmArchive);
//            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
//        }
        recreatModel();
        rectreateFileUpload();
    }

    public void getDocValue() {
        fileDataModel = dataUpload.selectListOfFileByDocId(doList);
    }

    public void rowSelectFile(SelectEvent event) {
        isRowFileSelected = true;
        documentModel = (DocumentModel) event.getObject();
    }

    public void changeBidType(ValueChangeEvent e) {

        if (e.getNewValue() != null) {
            if ("Amend".equals(e.getNewValue().toString())) {
                amendment = true;
                clarification = false;
            } else if ("Clarification".equals(e.getNewValue().toString())) {
                amendment = false;
                clarification = true;

            }
        }
    }
    List<PrmsPurchaseRequest> prmsPurchaseRequestLst;

    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestLst() {
        return prmsPurchaseRequestLst;
    }

    public void setPrmsPurchaseRequestLst(List<PrmsPurchaseRequest> prmsPurchaseRequestLst) {
        this.prmsPurchaseRequestLst = prmsPurchaseRequestLst;
    }

    public List<PrmsPurchaseRequest> getPurchaseRequestNo() {
        setPrmsPurchaseRequestLst(bidRegBeanLocal.searchPurchaseReqNo());
        return prmsPurchaseRequestLst;
    }

    public void valueChange(ValueChangeEvent event) {

        itemRegistrationList = bidRegBeanLocal.getItemcode(event.getNewValue().toString());

    }

    public void valueChangeItemCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            String itemId = event.getNewValue().toString();
        }
    }

    public Date registrationDate() {
        return this.prmsBidAmend.getBidRegDate();
    }

    public Date bidOpenddate() {
        return this.prmsBidAmend.getBidCloseDateTime();
    }

    public Date bidOpeningDate() {
        return this.prmsBidAmend.getBidOpenDateTime();
    }
    //</editor-fold>
}
