/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.prms.businessLogic.SpecificationBeanLocal;
import et.gov.eep.prms.businessLogic.TechnicalEvaluationBeanLocal;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
import et.gov.eep.prms.entity.PrmsSupplierSpecificationDt;
import et.gov.eep.prms.entity.PrmsTechnicalFileUpload;
import insa.org.et.security.Utility;
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
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.WorkFlow;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

@Named(value = "technicalEvaluationCOntroller")
@ViewScoped
public class TechnicalEvaluationCOntroller implements Serializable {

    @Inject
    SessionBean sessionBean;
    @EJB
    MmsItemRegisrtationBeanLocal itemRegisrtationBeanLocal;
    @EJB
    private SpecificationBeanLocal specificationBeanLocal;
    @EJB
    private VendorRegBeanLocal vendorRegBeanLocal;

    @EJB
    private TechnicalEvaluationBeanLocal technicalEvaluationBeanLocal;
    @EJB
    private WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @Inject
    DataUpload dataUpload;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    @Inject
    private PrmsThechincalEvaluationDet prmsThechincalEvaluationDet;
    @Inject
    private PrmsThechnicalEvaluation prmsThechnicalEvaluation;
    @Inject
    private PrmsBidDetail prmsBidDetail;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    private PrmsQuotationDetail prmsQuotationDetail;
    @Inject
    private PrmsQuotation prmsQuotation;
    @Inject
    WfPrmsProcessed WfPrmsProcessed;
    @Inject
    private PrmsPreminilaryEvalutionDt prmsPreminilaryEvalutionDt;
    @Inject
    private PrmsPreminilaryEvaluation prmsPreminilaryEvaluation;
    @Inject
    private PrmsTechnicalFileUpload prmsTechnicalFileUpload;

    @Inject
    private WorkFlow workFlow;
    StreamedContent content;
    private String userName;
    List<PrmsThechnicalEvaluation> prmsThechnicalEvaluations;
    List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDts;
    List<PrmsBidDetail> prmsBidDetails;
    List<PrmsSupplyProfile> prmsSupplyProfiles;
    private PrmsThechnicalEvaluation evaluationSelection;
    ArrayList< MmsItemRegistration> itemList;
    List<PrmsPreminilaryEvaluation> fmsBidSales;
    List<PrmsThechnicalEvaluation> thechnicalEvaluationLst;
    @Inject
    MmsItemRegistration itemRegistration;
    @Inject
    private PrmsBid prmsBid;

    @Inject
    PrmsServiceAndWorkReg prmsServiceAndWorkReg;
    @Inject
    SessionBean SessionBean;
    DataModel<DocumentModel> documentDM;
    DataModel<WfPrmsProcessed> wfprmsTechnicalModelList;
    private String saveorUpdateBundle = "Save";
    int saveStatus = 0;
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private String activeIndex;
    private boolean isRendercreate;
    int updateStatus = 0;
    private String selectOptPartyName;
    private boolean renderStoreTbale;
    private boolean performa;
    private boolean renderMarketTable;
    private boolean performaDataTable;
    private boolean serviceTable;
    private boolean renderBid;
    private boolean goodsTable;
    private boolean goodsDataTable;
    private boolean performatable;
    private boolean works;
    private boolean worksTable;
    private boolean worksBidNo;
    private boolean goodsBidNo;
    private boolean serviceBidNoForCons;
    private boolean serviceBidNoForNcons;
    private boolean serviceType;
    private boolean consultancy;
    private boolean nonConsultancy;
    private boolean itemName;
    private boolean serviceName;
    private boolean worksName;
    private boolean renderItemUnitMeasure;
    private boolean renderServiceUnitMeasure;
    private boolean renderWorksUnitMeasure;
    private boolean renderItemAwardType;
    private boolean renderServiceAwardType;
    private boolean renderWorksAwardType;
    private boolean renderItemBidderCode;
    private boolean renderServiceBidderCode;
    private boolean renderWorksBidderCode;
    private boolean renderItemAddButon;
    private boolean renderServiceAddButon;
    private boolean renderServiceWorksAddButon;
    private boolean renderpnlToSearchPage;
    private String item = "Bid";
    private String selectedValue = "";
    private boolean renderPnlCreatePr = false;
    private boolean renderPnlManPag = true;
    List<PrmsBidDetail> prmsBidDetailsList;
    List<PrmsQuotationDetail> prmsQuotationDetailList;
    private PrmsSpecification prmsSpecificationList;
    private PrmsSuppSpecification prmsSuppSpecification;
    private PrmsSupplierSpecificationDt prmsSupplierSpecificationDt;
    List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDtList;
    List<MmsItemRegistration> MmsItemRegistrationList;
    ArrayList<MmsItemRegistration> itemListS;
    List<PrmsPurchaseRequest> prmsPurchaseRequestLst;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiList;
    ArrayList<PrmsPreminilaryEvaluation> preminilaryEvaluationsList;
    ArrayList<PrmsPreminilaryEvalutionDt> preminilaryEvaluationsLists;
    List<PrmsPreminilaryEvalutionDt> preminilaryEvaluationsLisst;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiLists;
    DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel;
    boolean renderBidGoods = true;
    boolean renderBidService = false;
    boolean renderBidWork = false;
    private boolean renderDecision = false;
    Integer requestNotificationCounter = 0;
    String logerName;
    Set<String> actionPlnDetlCheck = new HashSet<>();

    private boolean isRowFileSelected;
    boolean isRenderCreate;
    byte[] byteData;
    String docFormat;
    String fileName;
    private PrmsLuDmArchive prmsLuDmArchiveSelection;
    StreamedContent fileDownload;
    String addOrUpdateBundle = "Add";

    @PostConstruct
    public void _init() {
        WfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setLogerName(SessionBean.getUserName());
        prmsThechnicalEvaluations = technicalEvaluationBeanLocal.getTechinicalEvalList();
        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRendercreate = true;
        }
        if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRendercreate = false;
        }
        if (workFlow.isCheckStatus()) {
            renderDecision = true;
            renderDecision = false;
        }
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

    public PrmsLuDmArchive getPrmsLuDmArchive() {
        return prmsLuDmArchive;
    }

    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public StreamedContent getFileDownload() throws Exception {
        if (isRowFileSelected == true) {
            System.out.println("When Downloading");
//            fileDownload = dataUpload.getFile(documentModel);
            fileDownload = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
            System.out.println("you downloaded  " + fileDownload.getName());
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
        }
        return fileDownload;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
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

    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        this.prmsLuDmArchive = prmsLuDmArchive;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isIsRendercreate() {
        return isRendercreate;
    }

    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }

    public boolean isRenderBidGoods() {
        return renderBidGoods;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();

        }
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public PrmsTechnicalFileUpload getPrmsTechnicalFileUpload() {
        if (prmsTechnicalFileUpload == null) {
            prmsTechnicalFileUpload = new PrmsTechnicalFileUpload();
        }
        return prmsTechnicalFileUpload;
    }

    public void setPrmsTechnicalFileUpload(PrmsTechnicalFileUpload prmsTechnicalFileUpload) {
        this.prmsTechnicalFileUpload = prmsTechnicalFileUpload;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (WfPrmsProcessed == null) {
            WfPrmsProcessed = new WfPrmsProcessed();
        }
        return WfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed WfPrmsProcessed) {
        this.WfPrmsProcessed = WfPrmsProcessed;
    }

    public void setRenderBidGoods(boolean renderBidGoods) {
        this.renderBidGoods = renderBidGoods;
    }

    public boolean isRenderBidService() {
        return renderBidService;
    }

    public void setRenderBidService(boolean renderBidService) {
        this.renderBidService = renderBidService;
    }

    public boolean isRenderBidWork() {
        return renderBidWork;
    }

    public void setRenderBidWork(boolean renderBidWork) {
        this.renderBidWork = renderBidWork;
    }

    public ArrayList<MmsItemRegistration> getItemListS() {
        if (itemListS == null) {
            itemListS = new ArrayList<>();
        }
        return itemListS;
    }

    public void setItemListS(ArrayList<MmsItemRegistration> itemListS) {
        this.itemListS = itemListS;
    }

    public ArrayList<MmsItemRegistration> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        return itemList;
    }

    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        if (prmsQuotationDetailList == null) {
            prmsQuotationDetailList = new ArrayList<>();
        }
        return prmsQuotationDetailList;
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

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> prmsQuotationDetailList) {
        this.prmsQuotationDetailList = prmsQuotationDetailList;
    }

    public List<MmsItemRegistration> getRegistrations() {
        if (registrations == null) {
            registrations = new ArrayList<>();
        }
        return registrations;
    }

    public void setRegistrations(List<MmsItemRegistration> registrations) {
        this.registrations = registrations;
    }

    public PrmsSupplierSpecificationDt getPrmsSupplierSpecificationDt() {
        if (prmsSupplierSpecificationDt == null) {
            prmsSupplierSpecificationDt = new PrmsSupplierSpecificationDt();
        }
        return prmsSupplierSpecificationDt;
    }

    public void setPrmsSupplierSpecificationDt(PrmsSupplierSpecificationDt prmsSupplierSpecificationDt) {
        this.prmsSupplierSpecificationDt = prmsSupplierSpecificationDt;
    }

    public PrmsSpecification getPrmsSpecificationList() {
        return prmsSpecificationList;
    }

    public void setPrmsSpecificationList(PrmsSpecification prmsSpecificationList) {
        this.prmsSpecificationList = prmsSpecificationList;
    }

    public PrmsQuotationDetail getPrmsQuotationDetail() {
        if (prmsQuotationDetail == null) {
            prmsQuotationDetail = new PrmsQuotationDetail();
        }
        return prmsQuotationDetail;
    }

    public void setPrmsQuotationDetail(PrmsQuotationDetail prmsQuotationDetail) {
        this.prmsQuotationDetail = prmsQuotationDetail;
    }

    public List<PrmsPreminilaryEvalutionDt> getPrmsPreminilaryEvalutionDtList() {
        if (prmsPreminilaryEvalutionDtList == null) {
            prmsPreminilaryEvalutionDtList = new ArrayList<>();
        }
        return prmsPreminilaryEvalutionDtList;
    }

    public List<PrmsBidDetail> getPrmsBidDetailsList() {
        if (prmsBidDetailsList == null) {
            prmsBidDetailsList = new ArrayList<>();
        }
        return prmsBidDetailsList;
    }
    ArrayList<PrmsBidDetail> prmsBidDetailList;
    List<PrmsBidDetail> prmsBidDetails1ItemList;

    public ArrayList<PrmsBidDetail> getPrmsBidDetailList() {
        if (prmsBidDetailList == null) {
            prmsBidDetailList = new ArrayList<>();
        }
        return prmsBidDetailList;
    }

    public List<PrmsBidDetail> getPrmsBidDetails1ItemList() {
        return prmsBidDetails1ItemList;
    }

    public void setPrmsBidDetails1ItemList(List<PrmsBidDetail> prmsBidDetails1ItemList) {
        this.prmsBidDetails1ItemList = prmsBidDetails1ItemList;
    }

    public void setPrmsSupplyProfiles(List<PrmsSupplyProfile> prmsSupplyProfiles) {
        this.prmsSupplyProfiles = prmsSupplyProfiles;
    }

    public void setPrmsBidDetailList(ArrayList<PrmsBidDetail> prmsBidDetailList) {
        this.prmsBidDetailList = prmsBidDetailList;
    }

    public boolean isRenderBid() {
        return renderBid;
    }

    public void setRenderBid(boolean renderBid) {
        this.renderBid = renderBid;
    }

    public boolean isGoodsTable() {
        return goodsTable;
    }

    public void setGoodsTable(boolean goodsTable) {
        this.goodsTable = goodsTable;
    }

    public boolean isGoodsDataTable() {
        return goodsDataTable;
    }

    public void setGoodsDataTable(boolean goodsDataTable) {
        this.goodsDataTable = goodsDataTable;
    }

    public boolean isPerformatable() {
        return performatable;
    }

    public void setPerformatable(boolean performatable) {
        this.performatable = performatable;
    }

    public PrmsQuotation getPrmsQuotation() {
        if (prmsQuotation == null) {
            prmsQuotation = new PrmsQuotation();
        }
        return prmsQuotation;
    }

    public void setPrmsQuotation(PrmsQuotation prmsQuotation) {
        this.prmsQuotation = prmsQuotation;
    }

    public List<PrmsPreminilaryEvalutionDt> getPrmsPreminilaryEvalutionDts(String code) {
        prmsPreminilaryEvalutionDts = technicalEvaluationBeanLocal.getSupplierCode(code);
        return prmsPreminilaryEvalutionDts;
    }

    public void setPrmsPreminilaryEvalutionDts(List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDts) {
        this.prmsPreminilaryEvalutionDts = prmsPreminilaryEvalutionDts;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public boolean isPerforma() {
        return performa;
    }

    public void setPerforma(boolean performa) {
        this.performa = performa;
    }

    public PrmsThechnicalEvaluation getEvaluationSelection() {
        return evaluationSelection;
    }

    public void setEvaluationSelection(PrmsThechnicalEvaluation evaluationSelection) {
        this.evaluationSelection = evaluationSelection;
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
            System.out.println("Error Msg====" + e.getMessage());
        }
        uploadedDocId = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        System.out.println("Id For Uploaded Doc is===" + uploadedDocId);
        FacesMessage msg = new FacesMessage("Successfully" + event3.getFile().getFileName() + "With Size" + event3.getFile().getSize() + "is Uploaded");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    List<DocumentModel> newDoclist = new ArrayList<DocumentModel>();
    List<Integer> savedDocIds = new ArrayList<>();
    DmsHandler dmsHandler = new DmsHandler();
    DataModel<DocumentModel> docValueModel;

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

    public DmsHandler getDmsHandler() {
        return dmsHandler;
    }

    public void setDmsHandler(DmsHandler dmsHandler) {
        this.dmsHandler = dmsHandler;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void uploadListener(FileUploadEvent choiced) {
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
    /*
     this method is used for selecting single row and redirect page to main page 
     */

    public void rowSelect(SelectEvent event) {

        System.out.println("Oneyy");
        try {
            System.out.println("trey");
            itemListS = new ArrayList<>();
            prmsThechnicalEvaluation = (PrmsThechnicalEvaluation) event.getObject();
            prmsBid = prmsThechnicalEvaluation.getBidId();

//        prmsPreminilaryEvaluation = prmsThechnicalEvaluation.getPreminaryEvaId();
            prmsQuotation = prmsThechnicalEvaluation.getQuotationId();
//        prmsThechincalEvaluationDet.setItemRegistrationId(itemRegistration);
//        itemRegistration = prmsThechincalEvaluationDet.getItemRegistrationId();
//        System.out.println("this is ItemRigstratio"+itemRegistration.getMatCode());
            // prmsBid = prmsBidDetail.getBidId();
//        itemRegistration = prmsBidDetail.getItemRegId();
            populateDateForApp();
            saveorUpdateBundle = "Update";
            String addOrUpdateBundle = "Modify";
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
//    public void rowSelect(SelectEvent event) {
//
//        itemListS = new ArrayList<>();
//        prmsThechnicalEvaluation = (PrmsThechnicalEvaluation) event.getObject();
//        prmsBid = prmsThechnicalEvaluation.getBidId();
//        prmsPreminilaryEvaluation = prmsThechnicalEvaluation.getPreminaryEvaId();
//        prmsQuotation = prmsThechnicalEvaluation.getQuotationId();
//        prmsThechincalEvaluationDet.setItemRegistrationId(itemRegistration);
//        itemRegistration = prmsThechincalEvaluationDet.getItemRegistrationId();
//        System.out.println("this is ItemRigstratio"+itemRegistration);
//        prmsBid = prmsBidDetail.getBidId();
//        
////        itemRegistration = prmsBidDetail.getItemRegId();
//        populateDateForApp();
//        saveorUpdateBundle = "Update";
//    }

    public void populateDateForAppppp() {
//        prmsThechnicalEvaluation.setEvaluationId(prmsThechnicalEvaluation.getEvaluationId());

//        prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode();
//        itemRegistration = prmsThechincalEvaluationDet.getBidDetailId().getItemRegId();
//        itemRegistration = prmsThechincalEvaluationDet.getItemRegistrationId();
//        prmsThechnicalEvaluation = technicalEvaluationBeanLocal.getSelectedRequest(prmsThechnicalEvaluation.getEvaluationId());
        recreateWorkFlow();
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        updateStatus = 1;
        String select = "select";
        select = prmsThechnicalEvaluation.getEvaluationType();
        if (select.equalsIgnoreCase("Bid")) {
            goodsTable = true;
            goodsDataTable = true;
            renderBid = true;
            works = false;
            worksTable = false;
            serviceTable = false;
            renderBidItem = true;
            performa = false;
            performaDataTable = false;
            performatable = false;

        } else {
            goodsDataTable = false;
            renderBidItem = false;
            performa = true;
            goodsTable = false;
            renderBid = false;
            renderBidItem = false;
            performatable = true;
            serviceTable = false;
            works = false;
            worksTable = false;
            serviceBidNoForCons = false;
            serviceBidNoForNcons = false;
        }

        recreateprmsThechincalEvaluationDetsModel();
        recreateWorkFlow();
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsThechnicalEvaluation = (PrmsThechnicalEvaluation) event.getNewValue();
            populateDateForApp();
            saveorUpdateBundle = "Update";
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('ovrNotification').hide();");
        }
    }

    public void populateDateForApp() {
//        prmsThechnicalEvaluation.setEvaluationId(prmsThechnicalEvaluation.getEvaluationId());

//        prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode();
//        itemRegistration = prmsThechincalEvaluationDet.getBidDetailId().getItemRegId();
//        itemRegistration = prmsThechincalEvaluationDet.getItemRegistrationId();
//        prmsThechnicalEvaluation = technicalEvaluationBeanLocal.getSelectedRequest(prmsThechnicalEvaluation.getEvaluationId());
        recreateWorkFlow();
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        updateStatus = 1;
        String select = "select";
        select = prmsThechnicalEvaluation.getEvaluationType();
        if (select.equalsIgnoreCase("Bid")) {
            goodsTable = true;
            goodsDataTable = true;
            renderBid = true;
            works = false;
            worksTable = false;
            serviceTable = false;
            renderBidItem = true;
            performa = false;
            performaDataTable = false;
            performatable = false;

        } else {
            goodsDataTable = false;
            renderBidItem = false;
            performa = true;
            goodsTable = false;
            renderBid = false;
            renderBidItem = false;
            performatable = true;
            serviceTable = false;
            works = false;
            worksTable = false;
            serviceBidNoForCons = false;
            serviceBidNoForNcons = false;
        }

        recreateprmsThechincalEvaluationDetsModel();
        recreateWorkFlow();
    }

    /**
     * *************************************************************************
     *
     *
     *************************************************************************
     */
    public void populateItemList() {

        prmsThechnicalEvaluation.setBidId(prmsBid);
        int size = prmsBid.getPrmsBidDetailList().size();
        itemListS = new ArrayList<>();
        purchaseType = prmsBid.getBidCategory();
        prmsThechnicalEvaluation.setPurchaseType(purchaseType);

        if (purchaseType.equalsIgnoreCase("Goods")) {

            renderBidGoods = true;
            renderBidService = false;
            renderBidWork = false;

            for (int i = 0; i < size; i++) {

                PrmsBidDetail prDtl = new PrmsBidDetail();
                prDtl = prmsBid.getPrmsBidDetailList().get(i);
                MmsItemRegistration item = new MmsItemRegistration();
                item = prDtl.getItemRegId();
                itemListS.add(item);
            }
        }
    }

    /**
     * ************************************************************************
     *
     *************************************************************************
     */
    public void populateSupplierList() {

        preEval = prmsThechincalEvaluationDet.getBidDetailId().getBidId().
                getPrmsPreminilaryEvaluationList();
        int size = preEval.getPrmsPreminilaryEvalutionDtList().size();
        supplierNames.clear();

        for (int i = 0; i < size; i++) {
            supplierNames.add(preEval.getPrmsPreminilaryEvalutionDtList().
                    get(i).getSupplierCode());
        }
    }

    /**
     * *************************************************************************
     *
     *************************************************************************
     */
    public void editItemDataTbl() {

        itemList = new ArrayList<>();
        prmsThechincalEvaluationDet = prmsThechincalEvaluationDetsModel.getRowData();
        populateItemList();
//        populateSupplierList();
        prmsServiceAndWorkReg = prmsThechincalEvaluationDet.getServiceId();
        itemRegistration = prmsThechincalEvaluationDet.getItemRegistrationId();
        prmsPreminilaryEvalutionDt = prmsThechincalEvaluationDet.getPreliminaryDetId();
        itemList.add(itemRegistration);
//        prmsThechincalEvaluationDet=null;
    }

    public void editItemDataTblForPerforma() {
        prmsThechincalEvaluationDet = prmsThechincalEvaluationDetsModel.getRowData();
//        populateSupplierList();
        populateItemList();

        itemRegistration = prmsQuotationDetail.getMaterialCodeId();
        prmsServiceAndWorkReg = prmsThechincalEvaluationDet.getServiceId();
        itemRegistration = prmsThechincalEvaluationDet.getQuotationDetailId().getMaterialCodeId();
        itemList = new ArrayList<>();
        itemList.add(itemRegistration);

//        getItemListByBid(prmsThechincalEvaluationDet.getThechnicalId().getBidId());
    }

    public void recreateWorkFlow() {
        wfprmsTechnicalModelList = null;
        wfprmsTechnicalModelList = new ListDataModel(prmsThechnicalEvaluation.getWfprmsTechnicalEvaluationList());
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

    public List<PrmsBidDetail> getPrmsBidDetails() {

        prmsBidDetails = technicalEvaluationBeanLocal.getBidNo();

        return prmsBidDetails;
    }

    public void setPrmsBidDetails(List<PrmsBidDetail> prmsBidDetails) {
        this.prmsBidDetails = prmsBidDetails;
    }

    private String icone = "ui-icon-plus";
    DataModel<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetsModel;
    private DataModel<PrmsThechnicalEvaluation> prmsThechnicalEvaluationsModel;
    Set<String> addressCheck = new HashSet<>();

    public TechnicalEvaluationCOntroller() {

    }

    public SelectItem[] getList() {
        return JsfUtil.getSelectItems(technicalEvaluationBeanLocal.getBidNo(), true);
    }

    public SelectItem[] getQuotationNo() {
        return JsfUtil.getSelectItems(technicalEvaluationBeanLocal.getQuotationNo(), true);
    }

    public boolean isRenderStoreTbale() {
        return renderStoreTbale;
    }

    public void setRenderStoreTbale(boolean renderStoreTbale) {
        this.renderStoreTbale = renderStoreTbale;
    }

    public boolean isRenderMarketTable() {
        return renderMarketTable;
    }

    public void setRenderMarketTable(boolean renderMarketTable) {
        this.renderMarketTable = renderMarketTable;
    }

    public boolean isPerformaDataTable() {
        return performaDataTable;
    }

    public void setPerformaDataTable(boolean performaDataTable) {
        this.performaDataTable = performaDataTable;
    }

    public boolean isRenderPnlCreatePr() {
        return renderPnlCreatePr;
    }

    public void setRenderPnlCreatePr(boolean renderPnlCreatePr) {
        this.renderPnlCreatePr = renderPnlCreatePr;
    }

    public boolean isRenderPnlManPag() {
        return renderPnlManPag;
    }

    public void setRenderPnlManPag(boolean renderPnlManPag) {
        this.renderPnlManPag = renderPnlManPag;
    }

    public void searchActionBtn() {

        renderPnlCreatePr = false;
        renderPnlManPage = true;

    }

    public boolean isServiceTable() {
        return serviceTable;
    }

    public void setServiceTable(boolean serviceTable) {
        this.serviceTable = serviceTable;
    }

//      public Integer getRequestNotificationCounter() {
//        prmsThechnicalEvaluations = technicalEvaluationBeanLocal.getPurchaseReqOnLists();
//        requestNotificationCounter = prmsPurchaseRequestLst.size();
//        return requestNotificationCounter;
//    }
    public void techevaluationList(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsThechnicalEvaluations = technicalEvaluationBeanLocal.searchByEvalNo(prmsThechnicalEvaluation);
        }
    }

//    public void RequestListChange(ValueChangeEvent event) {
//        if (event.getNewValue() != null) {
//            prmsThechnicalEvaluation = (PrmsThechnicalEvaluation) event.getNewValue();
////            populateDateForApp();
//        }
//    }
    public Integer getRequestNotificationCounter() {
        prmsThechnicalEvaluations = technicalEvaluationBeanLocal.getTechinicalEvalList();
        requestNotificationCounter = prmsPurchaseRequestLst.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public List<PrmsThechnicalEvaluation> getPrmsThechnicalEvaluations() {
        if (prmsThechnicalEvaluations == null) {
            prmsThechnicalEvaluations = new ArrayList<>();
        }
        return prmsThechnicalEvaluations;
    }

    public void setPrmsThechnicalEvaluations(List<PrmsThechnicalEvaluation> prmsThechnicalEvaluations) {
        this.prmsThechnicalEvaluations = prmsThechnicalEvaluations;
    }

    public PrmsThechincalEvaluationDet getPrmsThechincalEvaluationDet() {
        if (prmsThechincalEvaluationDet == null) {
            prmsThechincalEvaluationDet = new PrmsThechincalEvaluationDet();
        }
        return prmsThechincalEvaluationDet;
    }

    public void setPrmsThechincalEvaluationDet(PrmsThechincalEvaluationDet prmsThechincalEvaluationDet) {
        this.prmsThechincalEvaluationDet = prmsThechincalEvaluationDet;
    }

    public PrmsThechnicalEvaluation getPrmsThechnicalEvaluation() {
        if (prmsThechnicalEvaluation == null) {
            prmsThechnicalEvaluation = new PrmsThechnicalEvaluation();
        }
        return prmsThechnicalEvaluation;
    }

    public void setPrmsThechnicalEvaluation(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        this.prmsThechnicalEvaluation = prmsThechnicalEvaluation;
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

    public PrmsPreminilaryEvaluation getPrmsPreminilaryEvaluation() {
        if (prmsPreminilaryEvaluation == null) {
            prmsPreminilaryEvaluation = new PrmsPreminilaryEvaluation();
        }
        return prmsPreminilaryEvaluation;
    }

    public void setPrmsPreminilaryEvaluation(PrmsPreminilaryEvaluation prmsPreminilaryEvaluation) {
        this.prmsPreminilaryEvaluation = prmsPreminilaryEvaluation;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;

    }
    /*
     this method is used to open search  page or create page
     */

    public List<PrmsThechnicalEvaluation> getThechnicalEvaluationLst() {
         if (thechnicalEvaluationLst == null) {
            thechnicalEvaluationLst = new ArrayList<>();
            thechnicalEvaluationLst = technicalEvaluationBeanLocal.getParamNameList();
        }
        return thechnicalEvaluationLst;
    }

    public void setThechnicalEvaluationLst(List<PrmsThechnicalEvaluation> thechnicalEvaluationLst) {
        this.thechnicalEvaluationLst = thechnicalEvaluationLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsThechnicalEvaluation.setColumnName(event.getNewValue().toString());
            prmsThechnicalEvaluation.setColumnValue(null);
        }
    }
    
    public void createNewParty() {

        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        clearSearch();
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

    public void goBackToSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void onContactRowCancel(RowEditEvent event) {

    }

    public void clearSearch() {
        prmsThechnicalEvaluation = null;
        prmsPreminilaryEvalutionDt = null;
        prmsThechincalEvaluationDetsModel = null;
    }

    public DataModel<PrmsThechincalEvaluationDet> getPrmsThechincalEvaluationDetsModel() {
        if (prmsThechincalEvaluationDetsModel == null) {
            prmsThechincalEvaluationDetsModel = new ArrayDataModel<>();
        }
        return prmsThechincalEvaluationDetsModel;
    }

    public void setPrmsThechincalEvaluationDetsModel(DataModel<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetsModel) {
        this.prmsThechincalEvaluationDetsModel = prmsThechincalEvaluationDetsModel;
    }

    public DataModel<PrmsThechnicalEvaluation> getPrmsThechnicalEvaluationsModel() {
        if (prmsThechnicalEvaluationsModel == null) {
            prmsThechnicalEvaluationsModel = new ArrayDataModel<>();
        }
        return prmsThechnicalEvaluationsModel;
    }

    public void setPrmsThechnicalEvaluationsModel(DataModel<PrmsThechnicalEvaluation> prmsThechnicalEvaluationsModel) {
        this.prmsThechnicalEvaluationsModel = prmsThechnicalEvaluationsModel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public void recreateprmsThechincalEvaluationDetsModel() {
        prmsThechincalEvaluationDetsModel = null;
        prmsThechincalEvaluationDetsModel = new ListDataModel(new ArrayList<>(prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList()));

    }

    public void recreateprmsThechnicalEvaluationsModel() {
        prmsThechnicalEvaluationsModel = null;
        prmsThechnicalEvaluationsModel = new ListDataModel(new ArrayList<>(getPrmsThechnicalEvaluations()));
    }

    private void clearprmsThechnicalEvaluation() {
        prmsThechnicalEvaluation = null;
        prmsBidDetail = null;
        prmsBid = null;
        prmsThechincalEvaluationDetsModel = null;
        itemRegistration = null;
        prmsQuotationDetail = null;

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

    public DataModel<WfPrmsProcessed> getWfprmsTechnicalModelList() {
        if (wfprmsTechnicalModelList == null) {
            wfprmsTechnicalModelList = new ArrayDataModel<>();
        }
        return wfprmsTechnicalModelList;
    }

    public void setWfprmsTechnicalModelList(DataModel<WfPrmsProcessed> wfprmsTechnicalModelList) {
        this.wfprmsTechnicalModelList = wfprmsTechnicalModelList;
    }

    public void recreateWorkflowDataModel() {
        if (wfprmsTechnicalModelList == null) {
//            wfprmsTechnicalModelList = new ListDataModel(new ArrayList<>(prmsThechnicalEvaluation.getWfPrmsProcessedCollection()));
        }
    }
    WorkFlow WorkFlow = new WorkFlow();

    public void saveForTechncalEvalution() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveForTechncalEvalution", dataset)) {
                PrmsTechnicalFileUpload hrDisciplineLocal = new PrmsTechnicalFileUpload();

                if (saveorUpdateBundle.equals("Save")) {

                    try {
                        //setPrmsThechnicalEvaluation(prmsThechnicalEvaluation);
//                prmsSuppSpecification.setSuppId(prmsSupplyProfile);
//                prmsSuppSpecification.setBidId(prmsBid);
                        WfPrmsProcessed.setThechEvalId(prmsThechnicalEvaluation);
                        System.out.println("11");
//                WfPrmsProcessed.setDecision(prmsThechnicalEvaluation.getBidId());
                        prmsThechnicalEvaluation.setEvaluationNo(newcheckListNo);

                        prmsThechnicalEvaluation.getWfprmsTechnicalEvaluationList().add(WfPrmsProcessed);
                        prmsThechnicalEvaluation.setStatus(Constants.PREPARE_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.PREPARE_VALUE));
                        prmsThechnicalEvaluation.setPreparedBy(WfPrmsProcessed.getProcessedBy());

//                        prmsThechincalEvaluationDet.setSupplierId(getPrmsPreminilaryEvalutionDt().getSupplierId());
                        System.out.println("10");
                        WfPrmsProcessed.setThechEvalId(prmsThechnicalEvaluation);
                        for (int i = 0; i < savedDocIds.size(); i++) {
                            hrDisciplineLocal = new PrmsTechnicalFileUpload();
                            hrDisciplineLocal.setDocumentId(savedDocIds.get(i));
                            prmsThechnicalEvaluation.add(hrDisciplineLocal);
                        }
                        technicalEvaluationBeanLocal.create(prmsThechnicalEvaluation);
//           wfprmsBeanLocal.saveORUpdate(WfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Technical Evaluation is rigistered!!");

                        clearprmsThechnicalEvaluation();

                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("");
                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {

                        for (int i = 0; i < savedDocIds.size(); i++) {
                            hrDisciplineLocal = new PrmsTechnicalFileUpload();
                            hrDisciplineLocal.setDocumentId(savedDocIds.get(i));
                            prmsThechnicalEvaluation.add(hrDisciplineLocal);
                        }
                        technicalEvaluationBeanLocal.update(prmsThechnicalEvaluation);
                        JsfUtil.addSuccessMessage("Supplier Performance Information is successfully updated!!");
                        clearprmsThechnicalEvaluation();
                        saveorUpdateBundle = "Save";
                        clearprmsThechnicalEvaluation();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                    }

                } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equals("Update")) {

                    if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        prmsThechnicalEvaluation.setStatus(Constants.APPROVE_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        prmsThechnicalEvaluation.setStatus(Constants.CHECK_APPROVE_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));

                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        prmsThechnicalEvaluation.setStatus(Constants.APPROVE_REJECT_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        prmsThechnicalEvaluation.setStatus(Constants.CHECK_REJECT_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                    }
                    if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        prmsThechnicalEvaluation.setStatus(Constants.APPROVE_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
//                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        prmsThechnicalEvaluation.setStatus(Constants.APPROVE_REJECT_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        prmsThechnicalEvaluation.setStatus(Constants.CHECK_REJECT_VALUE);
                        WfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                    }

                    WfPrmsProcessed.setThechEvalId(prmsThechnicalEvaluation);
                    wfPrmsProcessedBeanLocal.savewf(WfPrmsProcessed);
                    technicalEvaluationBeanLocal.update(prmsThechnicalEvaluation);
                    JsfUtil.addSuccessMessage("Supplier Performance Information is successfully updated!!");
//                        clearprmsThechnicalEvaluation();
                    saveorUpdateBundle = "Save";
                    clearprmsThechnicalEvaluation();
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                System.out.println("============else");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ClearPopUp() {
        prmsThechincalEvaluationDet = null;
        prmsThechincalEvaluationDet = new PrmsThechincalEvaluationDet();
        prmsQuotationDetail = null;
        prmsQuotationDetail = new PrmsQuotationDetail();
        itemRegistration = null;
        itemRegistration = new MmsItemRegistration();
        prmsServiceAndWorkReg = null;
        prmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
        prmsSupplyProfile = null;
        prmsPreminilaryEvalutionDt = null;
        itemList = null;

    }
    String purchaseType;

    public String addThechincalEvaluationDet() {
        prmsPreminilaryEvalutionDt = new PrmsPreminilaryEvalutionDt();
        if (actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode()) && actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getItemRegistrationId().getMatName())) {
            JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
            return null;
        } else {
            if (prmsThechincalEvaluationDet.getQuotationDetailId() != null) {
                prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getQuotationDetailId().getSupId().getVendorCode());
            } else {
                prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode());

            }
            prmsThechnicalEvaluation.addThechincalEvaluationDet(prmsThechincalEvaluationDet);
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode());
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getItemRegistrationId().getMatName());

//              prmsSupplyProfile=prmsPreminilaryEvalutionDt.getSupplierId();
//                prmsThechincalEvaluationDet.setSupplierId(prmsSupplyProfile);
        }
        recreateprmsThechincalEvaluationDetsModel();
        ClearPopUp();
        return null;
    }

    public String addThechincalEvaluationDetService() {
        prmsPreminilaryEvalutionDt = new PrmsPreminilaryEvalutionDt();
        if (actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode()) && actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getServiceId().getServiceName())) {
            JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
            return null;
        } else {
            if (prmsThechincalEvaluationDet.getQuotationDetailId() != null) {
                prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getQuotationDetailId().getSupId().getVendorCode());
            } else {
                prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode());

            }
            prmsThechnicalEvaluation.addThechincalEvaluationDet(prmsThechincalEvaluationDet);
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode());
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getServiceId().getServiceName());

//              prmsSupplyProfile=prmsPreminilaryEvalutionDt.getSupplierId();
//                prmsThechincalEvaluationDet.setSupplierId(prmsSupplyProfile);
        }
        recreateprmsThechincalEvaluationDetsModel();
        ClearPopUp();
        return null;
    }

    public String addThechincalEvaluationDetWork() {
        prmsPreminilaryEvalutionDt = new PrmsPreminilaryEvalutionDt();
        if (actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode()) && actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getServiceId().getServiceName())) {
            JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
            return null;
        } else {
            if (prmsThechincalEvaluationDet.getQuotationDetailId() != null) {
                prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getQuotationDetailId().getSupId().getVendorCode());
            } else {
                prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode());

            }
            prmsThechnicalEvaluation.addThechincalEvaluationDet(prmsThechincalEvaluationDet);
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode());
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getServiceId().getServiceName());

//              prmsSupplyProfile=prmsPreminilaryEvalutionDt.getSupplierId();
//                prmsThechincalEvaluationDet.setSupplierId(prmsSupplyProfile);
        }
        recreateprmsThechincalEvaluationDetsModel();
        ClearPopUp();
        return null;
    }

    public String addThechincalEvaluationForPerforma() {
        prmsThechincalEvaluationDet.setItemRegistrationId(itemRegistration);
        if (actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getSupplierCode()) && actionPlnDetlCheck.contains(prmsThechincalEvaluationDet.getItemRegistrationId().getMatName())) {
            JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
            return null;
        } else {
            if (prmsThechincalEvaluationDet.getQuotationDetailId() != null) {
//            prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getQuotationDetailId().getSupId().getVendorCode());
                prmsThechincalEvaluationDet.setSupplierId(prmsThechincalEvaluationDet.getQuotationDetailId().getSupId());
            } else {
                prmsThechincalEvaluationDet.setSupplierCode(prmsThechincalEvaluationDet.getPreliminaryDetId().getSupplierCode());
//            prmsThechincalEvaluationDet.setSupplierId(prmsThechincalEvaluationDet.getQuotationDetailId().getSupId());
            }
//     suppforPerforma=prmsThechincalEvaluationDet.getSupplierCode();
            prmsThechnicalEvaluation.addThechincalEvaluationDet(prmsThechincalEvaluationDet);
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getSupplierCode());
            actionPlnDetlCheck.add(prmsThechincalEvaluationDet.getItemRegistrationId().getMatName());
        }
        recreateprmsThechincalEvaluationDetsModel();
        ClearPopUp();
        return null;
    }

    public void deleteThechincalEvaluationDet() {
        int rowIndex = prmsThechincalEvaluationDetsModel.getRowIndex();
        PrmsThechincalEvaluationDet thechincalEvaluationDet = prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList().get(rowIndex);
        prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList().remove(rowIndex);
        recreateprmsThechincalEvaluationDetsModel();
        if (saveorUpdateBundle.equals("Update")) {
            technicalEvaluationBeanLocal.deleteThechincalEvaluationDet(thechincalEvaluationDet);
        }
    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = prmsThechincalEvaluationDetsModel.getRowIndex();
        PrmsThechincalEvaluationDet contactPerson = new PrmsThechincalEvaluationDet();

        PrmsThechincalEvaluationDet comContPerson = (PrmsThechincalEvaluationDet) event.getObject();

        boolean found = false;
        for (int i = 0; i < prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList().size(); i++) {
            if (i != rowIndex) {
                if (prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList().get(i).getRemark().equals(comContPerson.getRemark())
                        && prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList().get(i).getSupplierCode().equals(comContPerson.getSupplierCode())) {
                    found = true;
                    break;
                }
            }
        }

        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            comContPerson.setSupplierCode(null);
            prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList().set(rowIndex, comContPerson);
            recreateprmsThechincalEvaluationDetsModel();
        } else {
            prmsThechnicalEvaluation.getPrmsThechincalEvaluationDetList().set(rowIndex, comContPerson);
            recreateprmsThechincalEvaluationDetsModel();
        }
    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "1";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 0;

        int rowIndex = prmsThechnicalEvaluationsModel.getRowIndex();
        prmsThechnicalEvaluation = prmsThechnicalEvaluations.get(rowIndex);

        recreateprmsThechincalEvaluationDetsModel();
        recreateprmsThechnicalEvaluationsModel();
    }

    public void changePrRqType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("service")) {

                performaDataTable = true;
                renderStoreTbale = false;
                renderMarketTable = true;
            } else {

                performaDataTable = false;
                renderStoreTbale = true;
                renderMarketTable = false;

            }
        }

    }

    public void searchThecnEvaluationByEvaluationNo() {
        prmsThechnicalEvaluation.setPreparedBy(workFlow.getUserAccount());
        prmsThechnicalEvaluations = technicalEvaluationBeanLocal.searchByEvalNo(prmsThechnicalEvaluation);
        recreateprmsThechnicalEvaluationsModel();
        prmsThechnicalEvaluation=new PrmsThechnicalEvaluation();
    }

    public boolean isWorks() {
        return works;
    }

    public void setWorks(boolean works) {
        this.works = works;
    }

    public boolean isWorksTable() {
        return worksTable;
    }

    public void setWorksTable(boolean worksTable) {
        this.worksTable = worksTable;
    }

    public boolean isWorksBidNo() {
        return worksBidNo;
    }

    public void setWorksBidNo(boolean worksBidNo) {
        this.worksBidNo = worksBidNo;
    }

    public boolean isGoodsBidNo() {
        return goodsBidNo;
    }

    public void setGoodsBidNo(boolean goodsBidNo) {
        this.goodsBidNo = goodsBidNo;
    }

    public boolean isServiceBidNoForCons() {
        return serviceBidNoForCons;
    }

    public void setServiceBidNoForCons(boolean serviceBidNoForCons) {
        this.serviceBidNoForCons = serviceBidNoForCons;
    }

    public boolean isServiceBidNoForNcons() {
        return serviceBidNoForNcons;
    }

    public void setServiceBidNoForNcons(boolean serviceBidNoForNcons) {
        this.serviceBidNoForNcons = serviceBidNoForNcons;
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

    public boolean isItemName() {
        return itemName;
    }

    public void setItemName(boolean itemName) {
        this.itemName = itemName;
    }

    public boolean isServiceName() {
        return serviceName;
    }

    public void setServiceName(boolean serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isWorksName() {
        return worksName;
    }

    public void setWorksName(boolean worksName) {
        this.worksName = worksName;
    }

    public boolean isRenderItemUnitMeasure() {
        return renderItemUnitMeasure;
    }

    public void setRenderItemUnitMeasure(boolean renderItemUnitMeasure) {
        this.renderItemUnitMeasure = renderItemUnitMeasure;
    }

    public boolean isRenderServiceUnitMeasure() {
        return renderServiceUnitMeasure;
    }

    public void setRenderServiceUnitMeasure(boolean renderServiceUnitMeasure) {
        this.renderServiceUnitMeasure = renderServiceUnitMeasure;
    }

    public boolean isRenderWorksUnitMeasure() {
        return renderWorksUnitMeasure;
    }

    public void setRenderWorksUnitMeasure(boolean renderWorksUnitMeasure) {
        this.renderWorksUnitMeasure = renderWorksUnitMeasure;
    }

    public boolean isRenderItemAwardType() {
        return renderItemAwardType;
    }

    public void setRenderItemAwardType(boolean renderItemAwardType) {
        this.renderItemAwardType = renderItemAwardType;
    }

    public boolean isRenderItemBidderCode() {
        return renderItemBidderCode;
    }

    public void setRenderItemBidderCode(boolean renderItemBidderCode) {
        this.renderItemBidderCode = renderItemBidderCode;
    }

    public boolean isRenderServiceBidderCode() {
        return renderServiceBidderCode;
    }

    public void setRenderServiceBidderCode(boolean renderServiceBidderCode) {
        this.renderServiceBidderCode = renderServiceBidderCode;
    }

    public boolean isRenderWorksBidderCode() {
        return renderWorksBidderCode;
    }

    public void setRenderWorksBidderCode(boolean renderWorksBidderCode) {
        this.renderWorksBidderCode = renderWorksBidderCode;
    }

    public boolean isRenderItemAddButon() {
        return renderItemAddButon;
    }

    public void setRenderItemAddButon(boolean renderItemAddButon) {
        this.renderItemAddButon = renderItemAddButon;
    }

    public boolean isRenderServiceAddButon() {
        return renderServiceAddButon;
    }

    public void setRenderServiceAddButon(boolean renderServiceAddButon) {
        this.renderServiceAddButon = renderServiceAddButon;
    }

    public boolean isRenderServiceWorksAddButon() {
        return renderServiceWorksAddButon;
    }

    public void setRenderServiceWorksAddButon(boolean renderServiceWorksAddButon) {
        this.renderServiceWorksAddButon = renderServiceWorksAddButon;
    }

    public boolean isRenderServiceAwardType() {
        return renderServiceAwardType;
    }

    public void setRenderServiceAwardType(boolean renderServiceAwardType) {
        this.renderServiceAwardType = renderServiceAwardType;
    }

    public boolean isRenderWorksAwardType() {
        return renderWorksAwardType;
    }

    public void setRenderWorksAwardType(boolean renderWorksAwardType) {
        this.renderWorksAwardType = renderWorksAwardType;
    }

    boolean renderBidItem = true;
    boolean renderPerfoma = false;

    public boolean isRenderBidItem() {
        return renderBidItem;
    }

    public void setRenderBidItem(boolean renderBidItem) {
        this.renderBidItem = renderBidItem;
    }

    public boolean isRenderPerfoma() {
        return renderPerfoma;
    }

    public void setRenderPerfoma(boolean renderPerfoma) {
        this.renderPerfoma = renderPerfoma;
    }

    String suppforPerforma;

    public String getSuppforPerforma() {
        return suppforPerforma;
    }

    public void setSuppforPerforma(String suppforPerforma) {
        this.suppforPerforma = suppforPerforma;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public void changeItemService(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Bid")) {
                goodsTable = true;
                goodsDataTable = true;
                renderBid = true;
                works = false;
                worksTable = false;
                serviceTable = false;

                performa = false;
                performaDataTable = false;
                performatable = false;
                renderBidItem = true;
                renderPerfoma = false;
            } else {
                goodsDataTable = false;
                renderBidItem = false;
                renderPerfoma = true;
                performa = true;
                goodsTable = false;
                renderBid = false;
                performatable = true;
                serviceTable = false;
                works = false;
                worksTable = false;
                serviceBidNoForCons = false;
                serviceBidNoForNcons = false;

            }
        }

    }

    public void changePurchaseType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Goods")) {
                goodsBidNo = true;
                worksBidNo = false;
                serviceType = false;
                serviceTable = false;
                goodsTable = true;
                goodsDataTable = true;
                works = false;
                worksTable = false;
                renderBid = true;
                serviceBidNoForCons = false;
                serviceBidNoForNcons = false;
                itemName = true;
                renderItemAwardType = true;
                renderItemUnitMeasure = true;
                renderItemBidderCode = true;
                renderItemAddButon = true;
                renderWorksAwardType = false;
                renderWorksBidderCode = false;
                renderWorksUnitMeasure = false;
                renderServiceAddButon = false;
                worksName = false;
                serviceName = false;
                renderServiceAwardType = false;
                renderServiceUnitMeasure = false;
                renderServiceBidderCode = false;
                renderServiceWorksAddButon = false;
            } else if (select.equalsIgnoreCase("Service")) {
                serviceType = true;
                serviceTable = true;
                goodsTable = false;
                works = false;
                worksTable = false;
                goodsBidNo = false;
                worksBidNo = false;
                renderBid = true;
                serviceBidNoForCons = false;
                serviceBidNoForNcons = false;
                goodsDataTable = true;
                serviceName = true;
                renderServiceAwardType = true;
                renderServiceUnitMeasure = true;
                renderServiceBidderCode = true;
                renderWorksAwardType = false;
                renderWorksBidderCode = false;
                renderWorksUnitMeasure = false;
                renderServiceWorksAddButon = false;
                worksName = false;
                itemName = false;
                renderItemAwardType = false;
                renderItemUnitMeasure = false;
                renderItemBidderCode = false;
                renderItemAddButon = false;
                renderServiceAddButon = true;

            } else if (select.equalsIgnoreCase("Work")) {
                worksBidNo = true;
                worksTable = true;
                goodsBidNo = false;
                renderBid = true;
                serviceType = false;
                works = true;
                serviceTable = false;
                goodsDataTable = true;
                goodsTable = false;
                serviceBidNoForCons = false;
                serviceBidNoForNcons = false;
                worksName = true;
                renderWorksAwardType = true;
                renderWorksBidderCode = true;
                renderWorksUnitMeasure = true;
                renderServiceWorksAddButon = true;
                itemName = false;
                renderItemAwardType = false;
                renderItemUnitMeasure = false;
                renderItemBidderCode = false;
                renderItemAddButon = false;
                serviceName = false;
                renderServiceAwardType = false;
                renderServiceUnitMeasure = false;
                renderServiceBidderCode = false;
                renderServiceAddButon = false;

            }
        }
    }

    public void changePurchaseTypeForPerforma(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Goods")) {

            } else if (select.equalsIgnoreCase("Service")) {

            } else if (select.equalsIgnoreCase("Work")) {

            }
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
                serviceBidNoForCons = true;
                serviceBidNoForNcons = false;

            } else {
                consultancy = false;
                nonConsultancy = true;
                serviceBidNoForNcons = true;
                serviceBidNoForCons = false;

            }
        }

    }

    public void BidDateGetter(ValueChangeEvent value) {

        if (null != value.getNewValue()) {
            prmsBidDetail = technicalEvaluationBeanLocal.getPrlst(value.getNewValue().toString());

        }
    }

    public void getValueofBidNo(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
            prmsBidDetails = technicalEvaluationBeanLocal.getRequestList(event.getNewValue().toString());
//            prmsThechnicalEvaluation.setBidDetId(prmsBidDetail);

        }
    }
    String newcheckListNo;
    String LastcheckListNo = "0";

    public String generateCheckListNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsThechnicalEvaluation.getEvaluationNo();
        } else {
            PrmsThechnicalEvaluation LastCheckNo = technicalEvaluationBeanLocal.LastCheckListNo();
            if (LastCheckNo != null) {
                LastcheckListNo = LastCheckNo.getEvaluationId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newcheckListN = 0;
            if (LastcheckListNo.equals("0")) {
                newcheckListN = 1;
                newcheckListNo = "TECH-" + newcheckListN + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newcheckListN = Integer.parseInt(lastDatesPaterns[0]);
                newcheckListN = newcheckListN + 1;
                newcheckListNo = "TECH-" + newcheckListN + "/" + f.format(now);
            }
        }
        System.out.println(".....new checkListNo......." + newcheckListNo);

        return newcheckListNo;
    }

    public SelectItem[] getReferenceNo() {
        return JsfUtil.getSelectItems(technicalEvaluationBeanLocal.BidNoFormPliminary(), true);

    }

    public PrmsSupplyProfile getPrmsSupplyProfile() {
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public void setItemList(ArrayList<MmsItemRegistration> itemList) {
        this.itemList = itemList;
    }

    public MmsItemRegistration getItemRegistration() {
        if (itemRegistration == null) {
            itemRegistration = new MmsItemRegistration();
        }
        return itemRegistration;
    }

    public void setItemRegistration(MmsItemRegistration itemRegistration) {
        this.itemRegistration = itemRegistration;
    }

    public void getItemName(ValueChangeEvent value) {

        prmsBid = (PrmsBid) value.getNewValue();
        int size = prmsBid.getPrmsBidDetailList().size();
        itemList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            PrmsBidDetail prDtl = new PrmsBidDetail();
            prDtl = prmsBid.getPrmsBidDetailList().get(i);
            MmsItemRegistration item = new MmsItemRegistration();
            item = prDtl.getItemRegId();
            itemList.add(item);
//            prmsThechnicalEvaluation.setBidDetId(prDtl);
        }

    }

    public void BidDateGettter(ValueChangeEvent value) {

        if (null != value.getNewValue()) {
            itemRegistration = (MmsItemRegistration) value.getNewValue();
            int size = prmsBid.getPrmsBidDetailList().size();
            itemList = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                PrmsBidDetail dtl = prmsBid.getPrmsBidDetailList().get(i);
                if (dtl.getItemRegId().equals(itemRegistration)) {

//                    prmsThechnicalEvaluation.setBidDetId(dtl);
//                    prmsBidDetail.setAwardType(dtl.getAwardType());
                }

            }
        }
    }

    public void searchSupplierCode() {
        prmsSupplyProfile = vendorRegBeanLocal.getSelectedRequest(prmsSupplyProfile.getId());
    }

    public void changeEventSuppCode(ValueChangeEvent event) {
        System.out.println("-----bid num------" + event.getNewValue().toString());
        prmsPreminilaryEvalutionDtList = technicalEvaluationBeanLocal.getPrelinmarList(event.getNewValue().toString());

        prmsBidDetailsList = technicalEvaluationBeanLocal.getItemList(event.getNewValue().toString());
        System.out.println(".....@controller...." + prmsBidDetailsList.get(0).getId());

    }
    ArrayList<String> supplierNames = new ArrayList<>();

    public ArrayList<String> getSupplierNames() {
        if (supplierNames == null) {
            supplierNames = new ArrayList<>();
        }
        return supplierNames;
    }

    public void setSupplierNames(ArrayList<String> supplierNames) {
        this.supplierNames = supplierNames;
    }
    PrmsBidDetail bidDtl = new PrmsBidDetail();

    boolean renderMerit = false;
    boolean renderCompliance = false;

    public boolean isRenderMerit() {
        return renderMerit;
    }

    public void setRenderMerit(boolean renderMerit) {
        this.renderMerit = renderMerit;
    }

    public boolean isRenderCompliance() {
        return renderCompliance;
    }

    public void setRenderCompliance(boolean renderCompliance) {
        this.renderCompliance = renderCompliance;
    }
    PrmsPreminilaryEvaluation preEval = new PrmsPreminilaryEvaluation();
    String unitMeasurment = "";

    public String getUnitMeasurment() {
        return unitMeasurment;
    }

    public void setUnitMeasurment(String unitMeasurment) {
        this.unitMeasurment = unitMeasurment;
    }

    public void changeEventItemspecficationList(ValueChangeEvent event) {

        MmsItemRegistration item = new MmsItemRegistration();
        item = (MmsItemRegistration) event.getNewValue();
        prmsThechincalEvaluationDet.setItemRegistrationId(item);
        int sizeOfItem = itemListS.size();
        for (int i = 0; i < sizeOfItem; i++) {
            if (itemListS.get(i).getMatName().equalsIgnoreCase(item.getMatName())) {
                itemRegistration = item;
            }
        }

        prmsBidDetail = new PrmsBidDetail();

        int bidDtlSize = prmsBid.getPrmsBidDetailList().size();
        for (int i = 0; i < bidDtlSize; i++) {
            if (prmsBid.getPrmsBidDetailList().get(i).getItemRegId().equals(item)) {
                bidDtl = new PrmsBidDetail();

                bidDtl = prmsBid.getPrmsBidDetailList().get(i);
                prmsBidDetail = bidDtl;
                String firmSelectionMethod = bidDtl.getFermSeltionMethd();
                if (firmSelectionMethod.equalsIgnoreCase("compliance")) {
                    renderCompliance = true;
                    renderMerit = false;
                } else {
                    renderCompliance = false;
                    renderMerit = true;
                }
                prmsThechincalEvaluationDet.setBidDetailId(bidDtl);
            }
        }

//        PrmsBid bid = new PrmsBid();
//        bid = bidDtl.getBidId();
//
//        preEval = bid.getPrmsPreminilaryEvaluationList();
//        int size = preEval.getPrmsPreminilaryEvalutionDtList().size();
//        supplierNames.clear();
//        for (int i = 0; i < size; i++) {
// 
//          
//            supplierNames.add(preEval.getPrmsPreminilaryEvalutionDtList().get(i).getSupplierCode());
//      
//
//        }
//        System.out.println("....specNames size......" + supplierNames.size());
    }

    public void handleServiceAndWork(ValueChangeEvent event) {

        PrmsServiceAndWorkReg serObj = new PrmsServiceAndWorkReg();
        serObj = (PrmsServiceAndWorkReg) event.getNewValue();
        prmsThechincalEvaluationDet.setServiceId(serObj);
        int sizeService = serviceList.size();

        if (purchaseType.equalsIgnoreCase("Service")) {
            for (int i = 0; i < sizeService; i++) {
                if (serviceList.get(i).getServiceName().equalsIgnoreCase(serObj.getServiceName())) {
                    prmsServiceAndWorkReg = serObj;
                    prmsBidDetail = new PrmsBidDetail();
                    System.out.println("Um" + prmsServiceAndWorkReg.getUnitMeasures());
                    int bidDtlSizee = prmsBid.getPrmsBidDetailList().size();
                    for (int j = 0; j < bidDtlSizee; j++) {
                        if (prmsBid.getPrmsBidDetailList().get(j).getServiceId().equals(prmsServiceAndWorkReg)) {
                            bidDtl = new PrmsBidDetail();
                            bidDtl = prmsBid.getPrmsBidDetailList().get(i);
                        }
//                    if (prmsBid.getPrmsBidDetailList().get(i).getServiceId().equals(serviceAndWorkReg)) {
//                        prmsThechincalEvaluationDet.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i));

                    }
                } else {
                    int sizeWork = workList.size();
                    for (int j = 0; j < sizeWork; i++) {
                        if (workList.get(i).getWorkName().equalsIgnoreCase(serObj.getWorkName())) {
                            prmsServiceAndWorkReg = serObj;
                            if (prmsBid.getPrmsBidDetailList().get(j).getServiceId().equals(prmsServiceAndWorkReg)) {
                                prmsThechincalEvaluationDet.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i));
                            }

                        }

                    }
                }
            }
        }
    }
//             prmsBidDetail = new PrmsBidDetail();
//
//        int bidDtlSize = prmsBid.getPrmsBidDetailList().size();
//        for (int i = 0; i < bidDtlSize; i++) {
//            if (prmsBid.getPrmsBidDetailList().get(i).getServiceId().equals(serviceAndWorkReg)) {
//                bidDtl = new PrmsBidDetail();
//                bidDtl = prmsBid.getPrmsBidDetailList().get(i);
//            }
//        }
//        System.out.println("....bid dtl ser Id....." + bidDtl.getId());
//        System.out.println(".....bid dtl ser Name...." + bidDtl.getBidId());
//        System.out.println("....serName ...." + bidDtl.getServiceId().getServiceName());
//        System.out.println("....Work Name ...." + bidDtl.getServiceId().getWorkName());
//        // prmsSpecificationList = bidDtl.getPrmsSpecifications().get(0);
//        System.out.println("......sepc.ser..." + prmsSpecificationList);
//        // itemRegistration = prmsSpecificationList.getMaterialId();
////        prmsThechnicalEvaluation.setEvaluationType(prmsSpecificationList.getSpecification());
//        int size = bidDtl.getPrmsSuppSpecificationList().size();
//        System.out.println("...size supplier Sepc ser...." + size);
//        supplierNames.clear();
//        for (int i = 0; i < size; i++) {
//            PrmsSupplierSpecificationDt specDtl = new PrmsSupplierSpecificationDt();
//            specDtl = bidDtl.getPrmsSuppSpecificationList().get(i);
//            PrmsSuppSpecification spec = new PrmsSuppSpecification();
//            spec = specDtl.getSuppSpecId();
//            if (spec.getPreminaryEvaId() != null) {
//                supplierNames.add(spec.getPreminaryEvaId().getSupplierCode());
//            }
//
//        }
//        System.out.println("....specNames size ser......" + supplierNames.size());

    public void handleSupplierSpecification(ValueChangeEvent event) {
        System.out.println(".......method handleSupplierSpecification........");
        System.out.println(".....value....." + event.getNewValue().toString());
        String supplierCode = event.getNewValue().toString();

        int size = preEval.getPrmsPreminilaryEvalutionDtList().size();
        for (int i = 0; i < size; i++) {
            if (preEval.getPrmsPreminilaryEvalutionDtList().get(i).getSupplierCode().equalsIgnoreCase(supplierCode)) {
                prmsThechincalEvaluationDet.setPreliminaryDetId(preEval.getPrmsPreminilaryEvalutionDtList().get(i));
            }
        }
        //preEval.getPrmsPreminilaryEvalutionDtList().get(i).getSupplierCode()

    }
    List<PrmsServiceAndWorkReg> proformaServiceList = new ArrayList<>();
    List<MmsItemRegistration> registrations = new ArrayList<>();
    boolean renderProformaGoods = true;
    boolean renderProformaService = false;
    boolean renderProformaWork = false;

    public boolean isRenderProformaGoods() {
        return renderProformaGoods;
    }

    public void setRenderProformaGoods(boolean renderProformaGoods) {
        this.renderProformaGoods = renderProformaGoods;
    }

    public boolean isRenderProformaService() {
        return renderProformaService;
    }

    public void setRenderProformaService(boolean renderProformaService) {
        this.renderProformaService = renderProformaService;
    }

    public boolean isRenderProformaWork() {
        return renderProformaWork;
    }

    public void setRenderProformaWork(boolean renderProformaWork) {
        this.renderProformaWork = renderProformaWork;
    }

    public List<PrmsServiceAndWorkReg> getProformaServiceList() {
        if (proformaServiceList == null) {
            proformaServiceList = new ArrayList<>();
        }
        return proformaServiceList;
    }

    public void setProformaServiceList(List<PrmsServiceAndWorkReg> proformaServiceList) {
        this.proformaServiceList = proformaServiceList;
    }

    public void getValueofQuotationNo(ValueChangeEvent event) {
        System.out.println("-----Quotation number is ------" + event.getNewValue().toString());
        if (null != event.getNewValue().toString()) {
            String QuotationNo = event.getNewValue().toString();
            prmsQuotationDetailList = technicalEvaluationBeanLocal.getQuotationDEtailList(QuotationNo);
            prmsThechnicalEvaluation.setQuotationId(prmsQuotationDetailList.get(0).getQuotationId());
            System.out.println("....quotationDetailList size...." + prmsQuotationDetailList.size());
            purchaseType = prmsQuotationDetailList.get(0).getQuotationId().getPurchaseType();
            prmsThechnicalEvaluation.setPurchaseType(purchaseType);
            if (purchaseType.equalsIgnoreCase("Goods")) {
                renderProformaGoods = true;
                renderProformaService = false;
                renderProformaWork = false;
                if (prmsQuotationDetailList.size() > 0) {
                    for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
                        MmsItemRegistration mir = new MmsItemRegistration();
                        mir = prmsQuotationDetailList.get(i).getMaterialCodeId();
                        if (!registrations.contains(mir)) {
                            registrations.add(mir);
                        }
                    }
                }
            } else if (purchaseType.equalsIgnoreCase("Services")) {
                renderProformaGoods = false;
                renderProformaService = true;
                renderProformaWork = false;
                if (prmsQuotationDetailList.size() > 0) {
                    for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
                        PrmsPurchaseReqService PRservice = new PrmsPurchaseReqService();
                        PRservice = prmsQuotationDetailList.get(i).getPurchaseReqServiceId();
                        if (PRservice != null) {
                            PrmsServiceAndWorkReg service = new PrmsServiceAndWorkReg();
                            service = PRservice.getServiceId();
                            if (service != null) {
                                if (!proformaServiceList.contains(service)) {
                                    proformaServiceList.add(service);
                                }
                            }

                        }
                    }
                }
            } else {
                renderProformaGoods = false;
                renderProformaService = false;
                renderProformaWork = true;

                if (prmsQuotationDetailList.size() > 0) {
                    proformaServiceList = new ArrayList<>();
                    for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
                        PrmsPurchaseReqService PRservice = new PrmsPurchaseReqService();
                        PRservice = prmsQuotationDetailList.get(i).getPurchaseReqServiceId();
                        if (PRservice != null) {
                            PrmsServiceAndWorkReg service = new PrmsServiceAndWorkReg();
                            service = PRservice.getServiceId();
                            if (service != null) {
                                if (!proformaServiceList.contains(service)) {
                                    proformaServiceList.add(service);
                                }
                            }

                        }
                    }
                }

            }
        }
    }

    public void getValueofBidNumber(ValueChangeEvent event) {
        prmsBid = (PrmsBid) event.getNewValue();
        int size = prmsBid.getPrmsBidDetailList().size();
        System.out.println("-----Quotation number is ------" + size);

    }

    public void getValueBidNumItem(ValueChangeEvent event) {
        System.out.println("-----BId number is ------" + event.getNewValue().toString());

        System.out.println("========================================= bid det list size=" + prmsBid.getPrmsBidDetailList().size());

//        prmsBid = (PrmsBid) event.getNewValue();
        if (null != event.getNewValue().toString()) {
//     String bidNo = event.getNewValue().toString();
            prmsBidDetailsList = technicalEvaluationBeanLocal.getItemList(event.getNewValue().toString());
        }
    }
    ArrayList<PrmsSupplyProfile> supplierProforma = new ArrayList<>();

    public ArrayList<PrmsSupplyProfile> getSupplierProforma() {
        if (supplierProforma == null) {
            supplierProforma = new ArrayList<>();
        }
        return supplierProforma;
    }

    public void setSupplierProforma(ArrayList<PrmsSupplyProfile> supplierProforma) {
        this.supplierProforma = supplierProforma;
    }

    public void changeEventItemspecfication(ValueChangeEvent event) {

//        String matCode = event.getNewValue().toString();
//        System.out.println(" ________" + matCode);
        MmsItemRegistration materialObj = new MmsItemRegistration();
        materialObj = (MmsItemRegistration) event.getNewValue();
        System.out.println(" ________" + materialObj.getMatName());
        int quotaionDtlSize = prmsQuotationDetailList.size();
        supplierProforma = new ArrayList<>();
        for (int i = 0; i < quotaionDtlSize; i++) {
            if (prmsQuotationDetailList.get(i).getMaterialCodeId().equals(materialObj)) {
                prmsThechincalEvaluationDet.setQuotationDetailId(prmsQuotationDetailList.get(i));
                supplierProforma.add(prmsQuotationDetailList.get(i).getSupId());
            }
        }

    }

    public void handleServiceForPrfoma(ValueChangeEvent event) {
        System.out.println("......service handler for perfoma......");
        PrmsServiceAndWorkReg sandw = new PrmsServiceAndWorkReg();
        sandw = (PrmsServiceAndWorkReg) event.getNewValue();
        int size = proformaServiceList.size();
        for (int i = 0; i < size; i++) {
            if (proformaServiceList.get(i).equals(sandw)) {
                PrmsPurchaseReqService PRservice = new PrmsPurchaseReqService();
                PRservice = proformaServiceList.get(i).getPrmsPurchaseReqServiceList().get(0);
                if (prmsQuotationDetailList.get(i).getPurchaseReqServiceId().equals(PRservice)) {
                    prmsQuotationDetail = prmsQuotationDetailList.get(i);
                }
                prmsThechincalEvaluationDet.setQuotationDetailId(prmsQuotationDetail);
            }
        }

    }
    ArrayList<PrmsServiceAndWorkReg> serviceList;

    public ArrayList<PrmsServiceAndWorkReg> getServiceList() {
        if (serviceList == null) {
            serviceList = new ArrayList<>();
        }
        return serviceList;
    }

    public void setServiceList(ArrayList<PrmsServiceAndWorkReg> serviceList) {
        this.serviceList = serviceList;
    }
    ArrayList<PrmsServiceAndWorkReg> workList;

    public ArrayList<PrmsServiceAndWorkReg> getWorkList() {
        if (workList == null) {
            workList = new ArrayList<>();
        }
        return workList;
    }

    public void setWorkList(ArrayList<PrmsServiceAndWorkReg> workList) {
        this.workList = workList;
    }

    public void getItemListByBid(PrmsBid prmsBid) {

        prmsThechnicalEvaluation.setBidId(prmsBid);
        int size = prmsBid.getPrmsBidDetailList().size();
        itemListS = new ArrayList<>();

        purchaseType = prmsBid.getBidCategory();
        prmsThechnicalEvaluation.setPurchaseType(purchaseType);
        if (purchaseType.equalsIgnoreCase("Goods")) {
            //renderBidItem=true;
            renderBidGoods = true;
            renderBidService = false;
            renderBidWork = false;
            for (int i = 0; i < size; i++) {
                PrmsBidDetail prDtl = new PrmsBidDetail();
                prDtl = prmsBid.getPrmsBidDetailList().get(i);
                MmsItemRegistration item = new MmsItemRegistration();
                item = prDtl.getItemRegId();
                itemListS.add(item);

            }
            System.out.println("....size of Item list...." + itemListS.size());
        } else {
            if (purchaseType.equalsIgnoreCase("Service")) {
                renderBidGoods = false;
                renderBidService = true;
                renderBidWork = false;
                serviceList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    PrmsServiceAndWorkReg service = new PrmsServiceAndWorkReg();
                    service = prmsBid.getPrmsBidDetailList().get(i).getServiceId();
                    serviceList.add(service);
                }
                System.out.println(".....List of Service size......" + serviceList.size());
            } else {
                renderBidGoods = false;
                renderBidService = false;
                renderBidWork = true;
                workList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    PrmsServiceAndWorkReg work = new PrmsServiceAndWorkReg();
                    work = prmsBid.getPrmsBidDetailList().get(i).getServiceId();
                    workList.add(work);
                }
                System.out.println(".....List of work size......" + workList.size());
            }
        }

    }

    public SelectItem[] getBidder() {
        System.out.println("this is biidder code" + getPreminilaryEvaluationsLists());
        return JsfUtil.getSelectItems(this.getPreminilaryEvaluationsLists(), true);

    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiList() {
        if (prmsSupplyProfiList == null) {
            prmsSupplyProfiList = new ArrayList<>();
        }
        return prmsSupplyProfiList;
    }

    public void setPrmsSupplyProfiList(ArrayList<PrmsSupplyProfile> prmsSupplyProfiList) {
        this.prmsSupplyProfiList = prmsSupplyProfiList;
    }

    public ArrayList<PrmsPreminilaryEvaluation> getPreminilaryEvaluationsList() {
        if (preminilaryEvaluationsList == null) {
            preminilaryEvaluationsList = new ArrayList<>();
        }
        return preminilaryEvaluationsList;
    }

    public void setPreminilaryEvaluationsList(ArrayList<PrmsPreminilaryEvaluation> preminilaryEvaluationsList) {
        this.preminilaryEvaluationsList = preminilaryEvaluationsList;
    }

    public ArrayList<PrmsPreminilaryEvalutionDt> getPreminilaryEvaluationsLists() {
        if (preminilaryEvaluationsLists == null) {
            preminilaryEvaluationsLists = new ArrayList<>();

        }
        return preminilaryEvaluationsLists;

    }

    public void setPreminilaryEvaluationsLists(ArrayList<PrmsPreminilaryEvalutionDt> preminilaryEvaluationsLists) {
        this.preminilaryEvaluationsLists = preminilaryEvaluationsLists;
    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiLists() {
        if (prmsSupplyProfiLists == null) {
            prmsSupplyProfiLists = new ArrayList<>();
        }
        return prmsSupplyProfiLists;
    }

    public void setPrmsSupplyProfiLists(ArrayList<PrmsSupplyProfile> prmsSupplyProfiLists) {
        this.prmsSupplyProfiLists = prmsSupplyProfiLists;
    }

    public List<PrmsPreminilaryEvaluation> getFmsBidSales() {
        if (fmsBidSales == null) {
            fmsBidSales = new ArrayList<>();
        }
        return fmsBidSales;
    }

    public void setFmsBidSales(List<PrmsPreminilaryEvaluation> fmsBidSales) {
        this.fmsBidSales = fmsBidSales;
    }

    public List<PrmsPreminilaryEvalutionDt> getPreminilaryEvaluationsLisst() {
        if (preminilaryEvaluationsLisst == null) {
            preminilaryEvaluationsLisst = new ArrayList<>();
        }
        return preminilaryEvaluationsLisst;
    }

    public void setPreminilaryEvaluationsLisst(List<PrmsPreminilaryEvalutionDt> preminilaryEvaluationsLisst) {
        this.preminilaryEvaluationsLisst = preminilaryEvaluationsLisst;
    }

    public void getItemNameFromBidDetail(ValueChangeEvent event) {
        preminilaryEvaluationsLists = null;
        getPreminilaryEvaluationsLists();

        if (null != event.getNewValue()) {

            prmsBid = (PrmsBid) event.getNewValue();
            prmsThechnicalEvaluation.setBidId(prmsBid);

//            prmsPreminilaryEvaluation.setBidId(prmsBid);
//            preminilaryEvaluationsLisst = new ArrayList<>();
////            preminilaryEvaluationsLisst = prmsPreminilaryEvaluation.getPrmsPreminilaryEvalutionDtList();
//            preminilaryEvaluationsLists = technicalEvaluationBeanLocal.getsupplierNameF(prmsBid);
//            System.out.println("this is supplier Name form plimnary " + preminilaryEvaluationsLisst);
            prmsPreminilaryEvaluation = prmsBid.getPrmsPreminilaryEvaluationList();
            int sizes = prmsPreminilaryEvaluation.getPrmsPreminilaryEvalutionDtList().size();
//            int sizes = prmsBid.getPrmsPreminilaryEvaluationList().getPrmsPreminilaryEvalutionDtList().size();
            PrmsPreminilaryEvalutionDt prmssup;
            System.out.println("==========this is bidder code size==" + sizes);
            for (int i = 0; i < sizes; i++) {
                prmssup = new PrmsPreminilaryEvalutionDt();
//                prmssup = prmsBid.getPrmsPreminilaryEvaluationList().getPrmsPreminilaryEvalutionDtList().get(i);
                prmssup = prmsPreminilaryEvaluation.getPrmsPreminilaryEvalutionDtList().get(i);
                preminilaryEvaluationsLists.add(prmssup);
                prmsThechincalEvaluationDet.setSupplierId(prmssup.getSupplierId());

                System.out.println("==========this is bidder code1==" + preminilaryEvaluationsLists.size());
            }
            System.out.println("==========this is bidder code2==" + preminilaryEvaluationsLists.size());
            int size = prmsBid.getPrmsBidDetailList().size();
            purchaseType = prmsBid.getBidCategory();
            prmsThechnicalEvaluation.setPurchaseType(purchaseType);
            if (purchaseType.equalsIgnoreCase("Goods")) {
                //renderBidItem=true;
                renderBidGoods = true;
                renderBidService = false;
                renderBidWork = false;
                for (int i = 0; i < size; i++) {
                    PrmsBidDetail prDtl = new PrmsBidDetail();
                    prDtl = prmsBid.getPrmsBidDetailList().get(i);
                    MmsItemRegistration item = new MmsItemRegistration();
                    item = prDtl.getItemRegId();
                    itemListS.add(item);

                }
                System.out.println("....size of Item list...." + itemListS.size());
            } else {
                if (purchaseType.equalsIgnoreCase("Service")) {
                    renderBidGoods = false;
                    renderBidService = true;
                    renderBidWork = false;
                    serviceList = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        PrmsServiceAndWorkReg service = new PrmsServiceAndWorkReg();
                        service = prmsBid.getPrmsBidDetailList().get(i).getServiceId();
                        serviceList.add(service);
                    }
                    System.out.println(".....List of Service size......" + serviceList.size());
                } else {
                    renderBidGoods = false;
                    renderBidService = false;
                    renderBidWork = true;
                    workList = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        PrmsServiceAndWorkReg work = new PrmsServiceAndWorkReg();
                        work = prmsBid.getPrmsBidDetailList().get(i).getServiceId();
                        workList.add(work);
                    }
                    System.out.println(".....List of work size......" + workList.size());
                }
            }
        }
    }

    public SelectItem[] BidNoFormPiliminaryEvaluation() {
        return JsfUtil.getSelectItems(technicalEvaluationBeanLocal.BidNoFormPiliminaryEvaluation(), true);

    }

//    public void editItemDataTbl() {
//        itemList = new ArrayList<>();
//        prmsThechincalEvaluationDet = prmsThechincalEvaluationDetsModel.getRowData();
////        itemRegistration = prmsBidDetail.getItemRegId();
////        itemRegistration = prmsThechincalEvaluationDet.getBidDetailId().getItemRegId();
//        itemRegistration = prmsThechincalEvaluationDet.getItemRegistrationId();
//        itemList.add(itemRegistration);
//
//    }
//
//    public void editItemDataTblForPerforma() {
//        prmsThechincalEvaluationDet = prmsThechincalEvaluationDetsModel.getRowData();
//        itemRegistration = prmsQuotationDetail.getMaterialCodeId();
//        itemRegistration = prmsThechincalEvaluationDet.getQuotationDetailId().getMaterialCodeId();
//        itemList = new ArrayList<>();
//        itemList.add(itemRegistration);
//        
//        getItemListByBid(prmsThechincalEvaluationDet.getThechnicalId().getBidId());
//    }
    public DataModel<DocumentModel> getDocumentDM() {
        return documentDM;
    }

    public void setDocumentDM(DataModel<DocumentModel> documentDM) {
        this.documentDM = documentDM;
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
    int uploadedDocId = -1;

    StreamedContent file;

    DocumentModel documentModel = new DocumentModel();
    DataUpload uploadSelection;

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }
//    DataModel<DocumentModel> docValueModel;

    public void populateData() {
        DmsHandler dmsHandler = new DmsHandler();
        DocumentModel documentModelInt = new DocumentModel();
        documentModelInt.setUserId(Long.valueOf("2"));
//        documentModelInt.setCatagoryId(Utility.CATAGORY_ID);

        String categoryBundle = "cfg/securityServer";
        documentModel.setApp(Utility.getBundleValue(categoryBundle, "categoryName"));
        DocList docList = dmsHandler.getDocuments(documentModelInt);
        if (docList != null) {
            docValueModel = new ListDataModel(docList.getDocList());
        }
    }
    DocumentModel dm = new DocumentModel();

    public DocumentModel getDm() {
        return dm;
    }

    public void setDm(DocumentModel dm) {
        this.dm = dm;
    }
    DataModel<DocumentModel> docList;

    public DataModel<DocumentModel> getDocList() {
        if (docList == null) {
            docList = new ListDataModel<>();
//           docList = dataUpload.selectListOfFileByDocId(docIdList);
        }
        return docList;
    }

    public void setDocList(DataModel<DocumentModel> docList) {
        this.docList = docList;
    }

    public void remove(DocumentModel document) {
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        populateData();
    }
    StreamedContent fileDown;

    public StreamedContent getFileDown() throws Exception {
        System.out.println("clicked on Dowload");
        fileDown = dataUpload.getFile(dm);
        System.out.println("file--" + fileDown);
        return fileDown;
    }

    public StreamedContent Download() throws Exception {
        System.out.println("When downloading");
        content = dataUpload.getFile(documentModel);
        System.out.println("You Got ==" + content);
        return content;

    }

    public void rowSelectFile(SelectEvent event) {

        System.out.println("file row is select");
        documentModel = (DocumentModel) event.getObject();
        System.out.println("The Selected File name " + documentModel.getName() + " and Format " + documentModel.getDocFormat());
    }

    public void uploadListenerr(FileUploadEvent event) {
        System.out.println("====listener===" + documentDM);

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

    public void onRowSelect(SelectEvent event) {
        setIsRowFileSelected(true);
        System.out.println("file row is select");
        documentModel = (DocumentModel) event.getObject();
        System.out.println("The Selected File name " + documentModel.getName() + " and Format " + documentModel.getDocFormat());
    }

    public void removee(DocumentModel document) {
//        hrDisciplineFileUpload.setId(hrDisciplineFileUpload.getId());
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDmsDataModel();
    }

    public void recreateDmsDataModel() {
        newDoclist.clear();
//        documentList = requestBeanLocal.findDocumentId(disciplineRequests);
        DmsHandler dmsHandler = new DmsHandler();
        DocList docList = new DocList();
        List<String> docId = new ArrayList<>();

        for (int i = 0; i < prmsThechnicalEvaluation.getPrmsTechnicalFileUploadList().size(); i++) {
            docId.add(prmsThechnicalEvaluation.getPrmsTechnicalFileUploadList().get(i).getDocumentId().toString());
        }
        docList.setDocIds(docId);
        DocList docListNew = dmsHandler.getDocumentsById(docList);//  System.out.println("====value of doclist new =============" + docListNew.getDocIds().size());
        System.out.println("====docListNew=============" + docListNew);
        if (docListNew != null) {
            System.out.println("====inside if=============");
            newDoclist = docListNew.getDocList();
            docValueModel = new ListDataModel(docListNew.getDocList());
        } else {
            System.out.println("Null DMS");
        }
    }

    public StreamedContent getFile() throws Exception {
        if (documentModel != null) {
            if (isRowFileSelected == true) {
                System.out.println("When Downloading");
//            fileDownload = dataUpload.getFile(documentModel);
                fileDownload = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
                System.out.println("you downloaded  " + fileDownload.getName());
            } else {
                JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
            }
            return fileDownload;
        }
        return null;
    }
}
