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
import et.gov.eep.prms.businessLogic.FinancialEvaluationBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvaluation;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
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
@Named("financialEvaluationController")
@ViewScoped
public class FinancialEvaluationController implements Serializable {

    @EJB
    FinancialEvaluationBeanLocal finanlEvalutionBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @Inject
    PrmsFinancialEvaluation prmsFinancialEvaluation;
    @Inject
    PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail;
    @Inject
    PrmsThechincalEvaluationDet thechincalEvaluationDet;
    @Inject
    PrmsThechnicalEvaluation prmsThechnicalEvaluation;
    @Inject
    MmsItemRegistration itemRegistration;
    @Inject
    PrmsBid prmsBid;
    @Inject
    PrmsLuVatTypeLookup prmsVatTypeLookup;
    @Inject
    PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    PrmsBidDetail prmsBidDetail;
    @Inject
    DataUpload dataUpload;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    List<PrmsBid> prmsBidList;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    List<PrmsQuotation> prmsQuotationList;
    DataModel<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDMdl;
    DataModel<DocumentModel> fileDataModel;
    DataModel<PrmsFinancialEvaluation> prmsFinancialEvaluationMdl;
    DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    List<PrmsFinancialEvaluation> prmsFinancialEvaluationList;
    List<PrmsThechnicalEvaluation> thechnicalEvaluationList;
    List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList;
    List<PrmsPortEntry> prmsPortEntryList;
    List<PrmsBidDetail> prmsBidDetailList;
    List<FmsLuCurrency> fmsLuCurrencyList;
    List<PrmsFinancialEvaluation> financialSearchParameterLst;
    PrmsFinancialEvaluation prmsFinancialEvaluationSelect;
    DocumentModel documentModel;
    StreamedContent fileDownload;
    private boolean proforma = true;
    private boolean bid = false;
    String fromProforma = "performa";
    private int selectedRowIndex;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private String activeIndex;
    private String icone = "ui-icon-plus";
    String selectedValue = "";
    private boolean item = true;
    private boolean lot = false;
    private boolean packages = false;
    private boolean supplierCode = true;
    private boolean supplierName = true;
    private boolean unitPrice = true;
    private boolean suppLierCodefrmBid = false;
    private boolean unitPriceFrmBid = false;
    private boolean convertedUnitPrice = false;
    private boolean awardType = false;
    private boolean passLimit = false;
    private boolean vatInBid;
    private boolean vatInProforma = true;
    private boolean renderpnlToSearchPage;

    private boolean renderItem = true;
    private boolean renderService = false;
    private boolean renderWork = false;

    private boolean isRenderedIconWorkflow = false;
    private boolean isDecision = false;
    private boolean isCommentGiven = false;
    private boolean isCreateButton;
    private boolean isRowFileSelected;
    Integer requestNotificationCounter = 0;
    private String userName = "";
    List<Integer> documentId = new ArrayList<>();
    byte[] byteData;
    String docFormat;
    String fileName;
    private PrmsLuDmArchive prmsLuDmArchiveSelection;

    @PostConstruct
    public void init() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setUserName(workFlow.getUserName());
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
        System.out.println("" + workFlow.isApproveStatus() + "or" + workFlow.isPrepareStatus() + "or " + workFlow.isCheckStatus());
    }

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

    public List<FmsLuCurrency> getFmsLuCurrencyList() {
        return fmsLuCurrencyList;
    }

    public void setFmsLuCurrencyList(List<FmsLuCurrency> fmsLuCurrencyList) {
        this.fmsLuCurrencyList = fmsLuCurrencyList;
    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }


    /*
     A method to upload file
     */
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

    public DataModel<DocumentModel> getFileDataModel() {
        return fileDataModel;
    }

    public void setFileDataModel(DataModel<DocumentModel> fileDataModel) {
        this.fileDataModel = fileDataModel;
    }

    public void fileRowSelect(SelectEvent file) {
        System.err.println("row file is selected");
        documentModel = (DocumentModel) file.getObject();
        System.out.println("your File name is    " + documentModel.getName() + " and File Ref No is " + documentModel.getId());
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsFinancialEvaluation.setColumnName(event.getNewValue().toString());
            prmsFinancialEvaluation.setColumnValue(null);
        }
    }

    public List<PrmsFinancialEvaluation> getFinancialSearchParameterLst() {
         if (financialSearchParameterLst == null) {
            financialSearchParameterLst = new ArrayList<>();
            financialSearchParameterLst = finanlEvalutionBeanLocal.getParamNameList();
        }
        return financialSearchParameterLst;
          }

    public void setFinancialSearchParameterLst(List<PrmsFinancialEvaluation> financialSearchParameterLst) {
        this.financialSearchParameterLst = financialSearchParameterLst;
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsFinancialEvaluation = (PrmsFinancialEvaluation) event.getNewValue();
            popUp();
            saveorUpdateBundle = "Update";
        }
    }
   
    public Integer getRequestNotificationCounter() {
        prmsFinancialEvaluationList = finanlEvalutionBeanLocal.getFinancialOnList();
        requestNotificationCounter = prmsFinancialEvaluationList.size();
        return requestNotificationCounter;
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

    public List<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDetailList() {
        return prmsFinancialEvaluaDetailList;
    }

    public void setPrmsFinancialEvaluaDetailList(List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList) {
        this.prmsFinancialEvaluaDetailList = prmsFinancialEvaluaDetailList;
    }

    public List<PrmsQuotation> getPrmsQuotationList() {
        return prmsQuotationList;
    }

    public void setPrmsQuotationList(List<PrmsQuotation> prmsQuotationList) {
        this.prmsQuotationList = prmsQuotationList;
    }

    public PrmsFinancialEvaluation getPrmsFinancialEvaluationSelect() {
        return prmsFinancialEvaluationSelect;
    }

    public void setPrmsFinancialEvaluationSelect(PrmsFinancialEvaluation prmsFinancialEvaluationSelect) {
        this.prmsFinancialEvaluationSelect = prmsFinancialEvaluationSelect;
    }

    public boolean isVatInBid() {
        return vatInBid;
    }

    public void setVatInBid(boolean vatInBid) {
        this.vatInBid = vatInBid;
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

    public boolean isVatInProforma() {
        return vatInProforma;
    }

    public void setVatInProforma(boolean vatInProforma) {
        this.vatInProforma = vatInProforma;
    }

    public boolean isUnitPriceFrmBid() {
        return unitPriceFrmBid;
    }

    public void setUnitPriceFrmBid(boolean unitPriceFrmBid) {
        this.unitPriceFrmBid = unitPriceFrmBid;
    }

    public boolean isSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(boolean supplierCode) {
        this.supplierCode = supplierCode;
    }

    public boolean isSupplierName() {
        return supplierName;
    }

    public void setSupplierName(boolean supplierName) {
        this.supplierName = supplierName;
    }

    public boolean isUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(boolean unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isSuppLierCodefrmBid() {
        return suppLierCodefrmBid;
    }

    public void setSuppLierCodefrmBid(boolean suppLierCodefrmBid) {
        this.suppLierCodefrmBid = suppLierCodefrmBid;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isLot() {
        return lot;
    }

    public void setLot(boolean lot) {
        this.lot = lot;
    }

    public boolean isPackages() {
        return packages;
    }

    public void setPackages(boolean packages) {
        this.packages = packages;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public boolean isProforma() {
        return proforma;
    }

    public void setProforma(boolean proforma) {
        this.proforma = proforma;
    }

    public boolean isBid() {
        return bid;
    }

    public void setBid(boolean bid) {
        this.bid = bid;
    }

    public String getFromProforma() {
        return fromProforma;
    }

    public void setFromProforma(String fromProforma) {
        this.fromProforma = fromProforma;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PrmsPortEntry> getPrmsPortEntryList() {
        return prmsPortEntryList;
    }

    public void setPrmsPortEntryList(List<PrmsPortEntry> prmsPortEntryList) {
        this.prmsPortEntryList = prmsPortEntryList;
    }

    public List<PrmsThechnicalEvaluation> getThechnicalEvaluationList() {
        return thechnicalEvaluationList;
    }

    public void setThechnicalEvaluationList(List<PrmsThechnicalEvaluation> thechnicalEvaluationList) {
        this.thechnicalEvaluationList = thechnicalEvaluationList;
    }

    public PrmsThechnicalEvaluation getPrmsThechnicalEvaluation() {
        return prmsThechnicalEvaluation;
    }

    public void setPrmsThechnicalEvaluation(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        this.prmsThechnicalEvaluation = prmsThechnicalEvaluation;
    }

    public PrmsThechincalEvaluationDet getThechincalEvaluationDet() {
        return thechincalEvaluationDet;
    }

    public void setThechincalEvaluationDet(PrmsThechincalEvaluationDet thechincalEvaluationDet) {
        this.thechincalEvaluationDet = thechincalEvaluationDet;
    }

    public PrmsFinancialEvaluation getPrmsFinancialEvaluation() {
        if (prmsFinancialEvaluation == null) {
            prmsFinancialEvaluation = new PrmsFinancialEvaluation();
        }
        return prmsFinancialEvaluation;
    }

    public void setPrmsFinancialEvaluation(PrmsFinancialEvaluation prmsFinancialEvaluation) {
        this.prmsFinancialEvaluation = prmsFinancialEvaluation;
    }

    public PrmsFinancialEvaluaDetail getPrmsFinancialEvaluaDetail() {
        if (prmsFinancialEvaluaDetail == null) {
            prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
        }
        return prmsFinancialEvaluaDetail;
    }

    public void setPrmsFinancialEvaluaDetail(PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail) {
        this.prmsFinancialEvaluaDetail = prmsFinancialEvaluaDetail;
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

    public DataModel<PrmsFinancialEvaluation> getPrmsFinancialEvaluationMdl() {
        if (prmsFinancialEvaluationMdl == null) {
            prmsFinancialEvaluationMdl = new ListDataModel<>();
        }
        return prmsFinancialEvaluationMdl;
    }

    public void setPrmsFinancialEvaluationMdl(DataModel<PrmsFinancialEvaluation> prmsFinancialEvaluationMdl) {
        this.prmsFinancialEvaluationMdl = prmsFinancialEvaluationMdl;
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

    public List<PrmsFinancialEvaluation> getPrmsFinancialEvaluationList() {
        if (prmsFinancialEvaluationList == null) {
            prmsFinancialEvaluationList = new ArrayList<>();
        }
        return prmsFinancialEvaluationList;
    }

    public void setPrmsFinancialEvaluationList(List<PrmsFinancialEvaluation> prmsFinancialEvaluationList) {
        this.prmsFinancialEvaluationList = prmsFinancialEvaluationList;
    }

    public DataModel<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDMdl() {
        if (prmsFinancialEvaluaDMdl == null) {
            prmsFinancialEvaluaDMdl = new ListDataModel<>();
        }
        return prmsFinancialEvaluaDMdl;
    }

    public void setPrmsFinancialEvaluaDMdl(DataModel<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDMdl) {
        this.prmsFinancialEvaluaDMdl = prmsFinancialEvaluaDMdl;
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

    public boolean isRenderItem() {
        return renderItem;
    }

    public void setRenderItem(boolean renderItem) {
        this.renderItem = renderItem;
    }

    public boolean isRenderService() {
        return renderService;
    }

    public void setRenderService(boolean renderService) {
        this.renderService = renderService;
    }

    public boolean isRenderWork() {
        return renderWork;
    }

    public void setRenderWork(boolean renderWork) {
        this.renderWork = renderWork;
    }

    public void onContactRowEdit(RowEditEvent event) {
        int rowIndex = prmsFinancialEvaluaDMdl.getRowIndex();

    }

    public void rowSelect(SelectEvent event) {
        prmsFinancialEvaluation = (PrmsFinancialEvaluation) event.getObject();
        popUp();
        saveorUpdateBundle = "Update";
    }

    public void popUp() {
        prmsBid = prmsFinancialEvaluation.getBidId();
        prmsBid = finanlEvalutionBeanLocal.getBidType(prmsBid.getRefNo());
        for (int i = 0; i < prmsBid.getPrmsBidDetailList().size(); i++) {
            int tckDtl = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().size();
            for (int j = 0; j < tckDtl; j++) {
                int bidOpenCklstSize = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().size();
                for (int k = 0; k < bidOpenCklstSize; k++) {
                    for (int y = 0; y < prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().size(); y++) {
                        prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(y).setCurrency(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(k).getCurrencyId().getCurrency());

                    }
                }
            }
        }
        if (!saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
            System.out.println("is preparer");
            wfPrmsProcessed.setProcessedOn(prmsFinancialEvaluation.getDateReg());
            System.out.println("date=   " + wfPrmsProcessed.getProcessedOn());
        }
        if (prmsFinancialEvaluation.getFileUploadRefno() != null) {
            System.out.println("file upload name" + prmsFinancialEvaluation.getFileUploadRefno());
            uploadId = prmsFinancialEvaluation.getFileUploadRefno();
            documentId.add(uploadId);
            getDocValue();
            System.out.println("when searching doc id==" + uploadId + "doc id from lists==" + documentId.get(0));
            System.out.println("File Name is==" + fileDataModel.toString());
        }
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        setIsRenderedIconWorkflow(true);
        reCreateData();
    }

    public void rectreateFileUpload() {
        prmsLuDmArchiveDataModel = null;
        prmsLuDmArchiveDataModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public void getDocValue() {
        fileDataModel = dataUpload.selectListOfFileByDocId(documentId);
        System.out.println("No of your Doc is " + fileDataModel.getRowCount());
    }

    public StreamedContent Download() throws Exception {
        if (isRowFileSelected == true) {
            System.out.println("When downloading");
            fileDownload = dataUpload.getFile(documentModel);
            System.out.println("You Got ==" + fileDownload.getName());
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
        }
        return fileDownload;
    }

    public void rowSelectFile(SelectEvent event) {
        isRowFileSelected = true;
        System.out.println("file row is select");
        documentModel = (DocumentModel) event.getObject();
        System.out.println("The Selected File name " + documentModel.getName() + " and Format " + documentModel.getDocFormat());
    }

    public List<PrmsBidDetail> getPrmsBidDetailList() {
        return prmsBidDetailList;
    }

    public void setPrmsBidDetailList(List<PrmsBidDetail> prmsBidDetailList) {
        this.prmsBidDetailList = prmsBidDetailList;
    }

    public void valueChangeLisLotItemPack(ValueChangeEvent e) {
        System.out.println("---Supplier Code-----" + e.getNewValue());
        prmsBidDetailList = finanlEvalutionBeanLocal.getByBidCode(e.getNewValue());
//        thechincalEvaluationDetList = finanlEvalutionBeanLocal.getSupCode(e.getNewValue().toString());
        System.out.println("prmsBidDetailList---" + prmsBidDetailList);
//        for (int i = 0; i < thechincalEvaluationDetList.size(); i++) {
//            System.out.println("in technical Evaluation res" + thechincalEvaluationDet.getThechnicalId().getItemRegistraionId());
//            prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
//
//            prmsFinancialEvaluaDetail.setItemRegistrationId(thechincalEvaluationDet.getThechnicalId().getItemRegistraionId());
//            prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
//
//        }
//        reCreateData();
    }

    public void changeToCurrency(ValueChangeEvent changeEvent) {
//        fmsLuCurrency = (FmsLuCurrency) changeEvent.getNewValue();
        fmsLuCurrency.setCurrency(fmsLuCurrency.getCurrency());

    }

    public void clearSearch() {
        prmsFinancialEvaluation = null;
        prmsFinancialEvaluaDetail = null;
        prmsFinancialEvaluaDMdl = null;
    }

    public void createFinancial() {
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

    public void changeEvaluationFrom(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Bid")) {
                supplierCode = false;
                supplierName = false;
                suppLierCodefrmBid = true;
                unitPrice = false;
                unitPriceFrmBid = true;
                proforma = false;
                bid = true;
                awardType = true;
                passLimit = true;
                convertedUnitPrice = true;
                vatInBid = true;
                vatInProforma = false;

            } else {
                supplierCode = true;
                supplierName = true;
                suppLierCodefrmBid = false;
                unitPrice = true;
                unitPriceFrmBid = false;
                proforma = true;
                bid = false;
                awardType = false;
                passLimit = false;
                convertedUnitPrice = false;
                vatInBid = false;
                vatInProforma = true;

            }

        }

    }
    int uploadId;

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
    String items = "Item";

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void changeEvaluationFromTeck(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Item")) {

                item = true;
                lot = false;
                packages = false;

            } else if (select.equalsIgnoreCase("lot")) {

                item = false;
                lot = true;
                packages = false;

            } else {

                item = false;
                lot = false;
                packages = true;

            }

        }

    }

    public void reCreateData() {
        prmsFinancialEvaluaDMdl = null;
        prmsFinancialEvaluaDMdl = new ListDataModel<>(new ArrayList<>(prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList()));
    }

    public void reCreatFinancialData() {
        prmsFinancialEvaluationMdl = null;
        prmsFinancialEvaluationMdl = new ListDataModel<>(new ArrayList<>(getPrmsFinancialEvaluationList()));
    }

    public void clear() {
        prmsFinancialEvaluation = null;
        prmsFinancialEvaluaDMdl = null;
        saveorUpdateBundle = "Save";
        prmsFinancialEvaluaDetail = null;
        prmsBid = null;

    }

    public void addToDataTable() {
        prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
        prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);

        prmsFinancialEvaluaDetail = null;
        reCreateData();
    }
    String newFinancialEvalNo;

    public String generatFinancialEvaluatioNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newFinancialEvalNo = prmsFinancialEvaluation.getFinancialNo();

        } else {
            newFinancialEvalNo = getfinancialNoSeq();
        }
        return newFinancialEvalNo;
    }

    public String getfinancialNoSeq() {
        String financialNo = finanlEvalutionBeanLocal.getfinancialNoSeq();
        return financialNo;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public List<FmsLuCurrency> getCurrency() {
        setFmsLuCurrencyList(finanlEvalutionBeanLocal.getCurrency());
        return fmsLuCurrencyList;
    }

    public String save_FinancialEvaluation() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_FinancialEvaluation", dataset)) {
                try {
                    if (saveorUpdateBundle.equals("Save")) {

                        generatFinancialEvaluatioNo();
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(docFormat);
                        prmsLuDmArchive.setUploadFile(byteData);
                        prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        prmsFinancialEvaluation.setFinancialNo(newFinancialEvalNo);
                        wfPrmsProcessed.setFinancialEvaluationId(prmsFinancialEvaluation);

//                        wfPrmsProcessed.setProcessedBy(prmsFinancialEvaluation.getPreparedBy());
                        prmsFinancialEvaluation.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        wfPrmsProcessed.setDecision(prmsFinancialEvaluation.getRemark());
                        wfPrmsProcessed.setProcessedOn(prmsFinancialEvaluation.getDateReg());
//                        prmsFinancialEvaluation.setDateReg(wfPrmsProcessed.getProcessedOn());
                        prmsFinancialEvaluation.setStatus(Constants.PREPARE_VALUE);
                        prmsFinancialEvaluation.setDocumentId(prmsLuDmArchive);
                        finanlEvalutionBeanLocal.save(prmsFinancialEvaluation);
                        prmsFinancialEvaluation.getWfPrmsProcessedCollection().add(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Data is Created");
                        clear();
                    } else if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                        prmsFinancialEvaluation.setPreparedBy(wfPrmsProcessed.getProcessedBy());
//                        prmsFinancialEvaluation.setDateReg(wfPrmsProcessed.getProcessedOn());
                        prmsFinancialEvaluation.setFileUploadRefno(uploadId);
                        finanlEvalutionBeanLocal.update(prmsFinancialEvaluation);
                        saveorUpdateBundle = "Save";
                        JsfUtil.addSuccessMessage("Data is Updated");
                        clear();
                    } else if (saveorUpdateBundle.equals("Update") && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            prmsFinancialEvaluation.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        }
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            prmsFinancialEvaluation.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            prmsFinancialEvaluation.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            prmsFinancialEvaluation.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        finanlEvalutionBeanLocal.update(prmsFinancialEvaluation);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        wfPrmsProcessed = null;
                        JsfUtil.addSuccessMessage("Data is Updated");

                    }
                } catch (Exception e) {
                    clear();
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Data is Not saved");

                }

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
    double valueOfCurrency;

    public double getValueOfCurrency() {
        return valueOfCurrency;
    }

    public void setValueOfCurrency(double valueOfCurrency) {
        this.valueOfCurrency = valueOfCurrency;
    }
    double ratedValue;

    public double getRatedValue() {
        return ratedValue;
    }

    public void setRatedValue(double ratedValue) {
        this.ratedValue = ratedValue;
    }

    public void Converters() {

        ratedValue = valueOfCurrency * ratedValue;
    }

    public void totalWithVat() {
        for (int i = 0; i < prmsBid.getPrmsBidDetailList().size(); i++) {
            for (int j = 0; j < prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().size(); j++) {

//                for (int k = 0; k < prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().size(); k++) {
                int currencySize = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().size();
                System.out.println("size of fms" + currencySize);
                double inputPrice = prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).getUnitPrice();
                for (int z = 0; z < currencySize; z++) {
                    double total = 0.0;
                    double sumOfPrice = 0.0;
                    double currencyAmount = 0.0;
                    BigDecimal xRate = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(z).getCurrencyId().getXrate();
                    currencyAmount = inputPrice * Double.parseDouble(xRate.toString());
                    String dicounttype = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(z).getDiscountType();
                    if (null != dicounttype) {
                        if ("Percentage".equals(dicounttype)) {
                            double sumOfPercent = 0.0;
                            double totalPercent = 0.0;
                            double discountPercentage = 0.0;
                            double disCountedByAvg = 0.0;
                            String dicountPrct;
                            dicountPrct = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(z).getPercent();
                            discountPercentage = currencyAmount * Double.valueOf(dicountPrct) / 100;
                            disCountedByAvg = currencyAmount - discountPercentage;
                            totalPercent = disCountedByAvg * prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).getQuantity();
                            sumOfPercent = totalPercent * prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).getVat() + totalPercent;
                            prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).setUnitPrice(disCountedByAvg);
                            prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).setTotalPrice(sumOfPercent);
                            System.out.println("percentage-----" + dicountPrct);
                            System.out.println("sumOfPercent-----" + sumOfPercent);
                        } else if ("Currency".equals(dicounttype)) {
                            double sumOfCurrency = 0.0;
                            double discountByCurrency = 0.0;
                            double discountedCurrency = 0.0;
                            double totalCurrency = 0.0;
                            String dicountCrcy;
                            dicountCrcy = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(z).getPercent();
                            discountByCurrency = Double.valueOf(dicountCrcy) * Double.parseDouble(xRate.toString());
                            discountedCurrency = currencyAmount - Double.valueOf(discountByCurrency);
                            totalCurrency = discountedCurrency * prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).getQuantity();
                            sumOfCurrency = totalCurrency * prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).getVat() + totalCurrency;
                            prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).setUnitPrice(discountedCurrency);
                            prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).setTotalPrice(sumOfCurrency);
                            System.out.println("value of currency-----" + dicountCrcy);
                            System.out.println("sumOfCurrency-----" + sumOfCurrency);
                        }
                    } else {
                        System.out.println("percent value out of if-----");
                        total = currencyAmount * prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).getQuantity();
                        sumOfPrice = total * prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).getVat() + total;
                        prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).setTotalPrice(sumOfPrice);
                        prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().get(j).setUnitPrice(currencyAmount);
                        System.out.println("sumOfPrice-----" + sumOfPrice);
                    }
                }
//                }
            }
        }
    }

    List<PrmsThechincalEvaluationDet> thechincalEvaluationDetList;

    public List<PrmsThechincalEvaluationDet> getThechincalEvaluationDetList() {
        if (thechincalEvaluationDetList == null) {
            thechincalEvaluationDetList = new ArrayList();
        }

        return thechincalEvaluationDetList;
    }

    public void setThechincalEvaluationDetList(List<PrmsThechincalEvaluationDet> thechincalEvaluationDetList) {
        this.thechincalEvaluationDetList = thechincalEvaluationDetList;
    }

    public SelectItem[] getThechincalEvaluationList() {

        return JsfUtil.getSelectItems(thechincalEvaluationDetList, true);
    }

    public void listOfSupplierCode(PrmsThechincalEvaluationDet tecDetail) {

    }

    public List<PrmsBid> getPrmsBidList() {
        return prmsBidList;
    }

    //select * from prmsTechimcalEvaluestiondet where bnidId=tecDetail
    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    public List<PrmsBid> getBidNo() {
        setPrmsBidList(finanlEvalutionBeanLocal.getBidNoList());
        return prmsBidList;
    }
    String refNo;

    public PrmsBidDetail getPrmsBidDetail() {
        if (prmsBidDetail == null) {
            prmsBidDetail = new PrmsBidDetail();
        }
        return prmsBidDetail;
    }

    public void setPrmsBidDetail(PrmsBidDetail prmsBidDetail) {
        this.prmsBidDetail = prmsBidDetail;
    }

    public boolean isConvertedUnitPrice() {
        return convertedUnitPrice;
    }

    public void setConvertedUnitPrice(boolean convertedUnitPrice) {
        this.convertedUnitPrice = convertedUnitPrice;
    }

    public boolean isAwardType() {
        return awardType;
    }

    public void setAwardType(boolean awardType) {
        this.awardType = awardType;
    }

    public boolean isPassLimit() {
        return passLimit;
    }

    public void setPassLimit(boolean passLimit) {
        this.passLimit = passLimit;
    }
    boolean renderlotNo = false;
    boolean renderIncoTerm = false;

    public boolean isRenderlotNo() {
        return renderlotNo;
    }

    public void setRenderlotNo(boolean renderlotNo) {
        this.renderlotNo = renderlotNo;
    }

    public boolean isRenderIncoTerm() {
        return renderIncoTerm;
    }

    public void setRenderIncoTerm(boolean renderIncoTerm) {
        this.renderIncoTerm = renderIncoTerm;
    }
    FmsLuCurrency fmsLuCurrency = new FmsLuCurrency();
    String percent;

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public void changeEventSuppCode(ValueChangeEvent event) {
        refNo = event.getNewValue().toString();

        prmsBid = finanlEvalutionBeanLocal.getBidType(refNo);
        prmsFinancialEvaluation = new PrmsFinancialEvaluation();
        String bidType = prmsBid.getBidType();
        System.out.println(".....bidType...." + bidType);
        if (bidType.equalsIgnoreCase("international")) {
            System.out.println("....international bid.........");
            renderIncoTerm = true;
        } else {
            System.out.println("....Local bid.........");
            renderIncoTerm = false;
        }
        String purchaseType = prmsBid.getBidCategory();
        if ("Goods".equals(purchaseType)) {
            renderItem = true;
            renderService = false;
            renderWork = false;
            int bidDtlSize = prmsBid.getPrmsBidDetailList().size();
            if (bidDtlSize > 0) {
                String award = prmsBid.getAwardType();
                if (award.equalsIgnoreCase("Lot Base")) {
                    renderlotNo = true;
                } else {
                    renderlotNo = false;
                }
                System.out.println("........bidDtlSize.........." + bidDtlSize);
                for (int i = 0; i < bidDtlSize; i++) {
                    int tckDtl = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().size();
                    System.out.println("........techDtlSize......." + tckDtl);
                    for (int j = 0; j < tckDtl; j++) {

                        int bidOpenCklstSize = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().size();
                        System.out.println("........bidOpenCklstSize......." + bidOpenCklstSize);

                        for (int k = 0; k < bidOpenCklstSize; k++) {
                            fmsLuCurrency = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(k).getCurrencyId();
                            percent = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(k).getPercent();
                        }
                        if (prmsBid.getPrmsBidDetailList().get(i).getFermSeltionMethd().equalsIgnoreCase("compliance")) {
                            if (prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getEvaluation().equalsIgnoreCase("Pass")) {
                                prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
                                prmsFinancialEvaluaDetail.setUnitPrice(prmsFinancialEvaluaDetail.getUnitPrice());
                                prmsFinancialEvaluaDetail.setTotalPrice(prmsFinancialEvaluaDetail.getTotalPrice());
                                prmsFinancialEvaluaDetail.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId());
                                prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);
                                prmsFinancialEvaluaDetail.setItemRegistrationId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getItemRegistrationId());
                                prmsFinancialEvaluaDetail.setLotNo(prmsBid.getPrmsBidDetailList().get(i).getLotName());
                                prmsFinancialEvaluaDetail.setSupplierCode(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getSupplierCode());
                                prmsFinancialEvaluaDetail.setQuantity(prmsBid.getPrmsBidDetailList().get(i).getQuantity());
                                prmsFinancialEvaluaDetail.setSupplierId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getSupplierId());
                                prmsFinancialEvaluaDetail.setVat(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getVatTypeId().getVatRate());
                                prmsFinancialEvaluaDetail.setCurrencyId(fmsLuCurrency);
                                prmsFinancialEvaluaDetail.setDiscountPrice(Double.valueOf(percent));
                                prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
                            }
                        } else {
                            double score = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getScore();
                            double passLimit = prmsBid.getPrmsBidDetailList().get(i).getPassLimit();
                            if (score > passLimit) {
                                prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
                                prmsFinancialEvaluaDetail.setUnitPrice(prmsFinancialEvaluaDetail.getUnitPrice());
                                prmsFinancialEvaluaDetail.setTotalPrice(prmsFinancialEvaluaDetail.getTotalPrice());
                                prmsFinancialEvaluaDetail.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId());
                                prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);
                                prmsFinancialEvaluaDetail.setItemRegistrationId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getItemRegistrationId());
                                prmsFinancialEvaluaDetail.setLotNo(prmsBid.getPrmsBidDetailList().get(i).getLotName());
                                prmsFinancialEvaluaDetail.setSupplierCode(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getSupplierCode());
                                prmsFinancialEvaluaDetail.setQuantity(prmsBid.getPrmsBidDetailList().get(i).getQuantity());
                                prmsFinancialEvaluaDetail.setSupplierId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId());
                                prmsFinancialEvaluaDetail.setVat(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getVatTypeId().getVatRate());
                                prmsFinancialEvaluaDetail.setCurrencyId(fmsLuCurrency);
                                prmsFinancialEvaluaDetail.setDiscountPrice(Double.valueOf(percent));
                                prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
                            }

                        }
                    }
                }
            }

        } else if ("Service".equals(purchaseType)) {
            renderItem = false;
            renderService = true;
            renderWork = false;
            int bidDtlSize = prmsBid.getPrmsBidDetailList().size();
            if (bidDtlSize > 0) {
                String award = prmsBid.getAwardType();
                if (award.equalsIgnoreCase("Lot Base")) {
                    renderlotNo = true;
                } else {
                    renderlotNo = false;
                }
                System.out.println("........bidDtlSize.........." + bidDtlSize);
                for (int i = 0; i < bidDtlSize; i++) {
                    int tckDtl = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().size();
                    System.out.println("........techDtlSize......." + tckDtl);
                    for (int j = 0; j < tckDtl; j++) {

                        int bidOpenCklstSize = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().size();
                        System.out.println("........bidOpenCklstSize......." + bidOpenCklstSize);

                        for (int k = 0; k < bidOpenCklstSize; k++) {
                            fmsLuCurrency = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(k).getCurrencyId();
                            percent = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(k).getPercent();
                        }

                        if (prmsBid.getPrmsBidDetailList().get(i).getFermSeltionMethd().equalsIgnoreCase("compliance")) {
                            if (prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getEvaluation().equalsIgnoreCase("Pass")) {
                                prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
                                prmsFinancialEvaluaDetail.setUnitPrice(prmsFinancialEvaluaDetail.getUnitPrice());
                                prmsFinancialEvaluaDetail.setTotalPrice(prmsFinancialEvaluaDetail.getTotalPrice());
                                prmsFinancialEvaluaDetail.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId());
                                prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);
                                prmsFinancialEvaluaDetail.setItemRegistrationId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getItemRegistrationId());
                                prmsFinancialEvaluaDetail.setServiceId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId().getServiceId());
                                prmsFinancialEvaluaDetail.setLotNo(prmsBid.getPrmsBidDetailList().get(i).getLotName());
                                prmsFinancialEvaluaDetail.setSupplierCode(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getSupplierCode());
                                prmsFinancialEvaluaDetail.setQuantity(prmsBid.getPrmsBidDetailList().get(i).getQuantity());
                                prmsFinancialEvaluaDetail.setSupplierId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId());
                                prmsFinancialEvaluaDetail.setVat(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getVatTypeId().getVatRate());
                                prmsFinancialEvaluaDetail.setCurrencyId(fmsLuCurrency);
                                prmsFinancialEvaluaDetail.setDiscountPrice(Double.valueOf(percent));

                                prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
                            }
                        } else {
                            double score = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getScore();
                            double passLimit = prmsBid.getPrmsBidDetailList().get(i).getPassLimit();
                            if (score > passLimit) {
                                prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
                                prmsFinancialEvaluaDetail.setUnitPrice(prmsFinancialEvaluaDetail.getUnitPrice());
                                prmsFinancialEvaluaDetail.setTotalPrice(prmsFinancialEvaluaDetail.getTotalPrice());
                                prmsFinancialEvaluaDetail.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId());
                                prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);
                                prmsFinancialEvaluaDetail.setItemRegistrationId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getItemRegistrationId());
                                prmsFinancialEvaluaDetail.setServiceId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId().getServiceId());
                                prmsFinancialEvaluaDetail.setLotNo(prmsBid.getPrmsBidDetailList().get(i).getLotName());
                                prmsFinancialEvaluaDetail.setSupplierCode(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getSupplierCode());
                                prmsFinancialEvaluaDetail.setQuantity(prmsBid.getPrmsBidDetailList().get(i).getQuantity());
                                prmsFinancialEvaluaDetail.setSupplierId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId());
                                prmsFinancialEvaluaDetail.setVat(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getVatTypeId().getVatRate());
                                prmsFinancialEvaluaDetail.setCurrencyId(fmsLuCurrency);
                                prmsFinancialEvaluaDetail.setDiscountPrice(Double.valueOf(percent));
                                prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
                            }
                        }
                    }
                }
            }
        } else {
            renderItem = false;
            renderService = false;
            renderWork = true;
            int bidDtlSize = prmsBid.getPrmsBidDetailList().size();
            if (bidDtlSize > 0) {
                String award = prmsBid.getAwardType();
                if (award.equalsIgnoreCase("Lot Base")) {
                    renderlotNo = true;
                } else {
                    renderlotNo = false;
                }
                System.out.println("........bidDtlSize.........." + bidDtlSize);
                for (int i = 0; i < bidDtlSize; i++) {
                    int tckDtl = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().size();
                    System.out.println("........techDtlSize......." + tckDtl);
                    for (int j = 0; j < tckDtl; j++) {

                        int bidOpenCklstSize = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().size();
                        System.out.println("........bidOpenCklstSize......." + bidOpenCklstSize);

                        for (int k = 0; k < bidOpenCklstSize; k++) {
                            fmsLuCurrency = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(k).getCurrencyId();
                            percent = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getPrmsBidOpeningChecklstDtList().get(k).getPercent();
                        }
                        if (prmsBid.getPrmsBidDetailList().get(i).getFermSeltionMethd().equalsIgnoreCase("compliance")) {
                            if (prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getEvaluation().equalsIgnoreCase("Pass")) {
                                prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
                                prmsFinancialEvaluaDetail.setUnitPrice(prmsFinancialEvaluaDetail.getUnitPrice());
                                prmsFinancialEvaluaDetail.setTotalPrice(prmsFinancialEvaluaDetail.getTotalPrice());
                                prmsFinancialEvaluaDetail.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId());
                                prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);
                                prmsFinancialEvaluaDetail.setServiceId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId().getServiceId());
                                prmsFinancialEvaluaDetail.setLotNo(prmsBid.getPrmsBidDetailList().get(i).getLotName());
                                prmsFinancialEvaluaDetail.setSupplierCode(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getSupplierCode());
                                prmsFinancialEvaluaDetail.setQuantity(prmsBid.getPrmsBidDetailList().get(i).getQuantity());
                                prmsFinancialEvaluaDetail.setSupplierId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId());
                                prmsFinancialEvaluaDetail.setVat(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getVatTypeId().getVatRate());
                                prmsFinancialEvaluaDetail.setCurrencyId(fmsLuCurrency);
                                prmsFinancialEvaluaDetail.setDiscountPrice(Double.valueOf(percent));
                                prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
                            }
                        } else {
                            double score = prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getScore();
                            double passLimit = prmsBid.getPrmsBidDetailList().get(i).getPassLimit();
                            if (score > passLimit) {
                                prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
                                prmsFinancialEvaluaDetail.setUnitPrice(prmsFinancialEvaluaDetail.getUnitPrice());
                                prmsFinancialEvaluaDetail.setTotalPrice(prmsFinancialEvaluaDetail.getTotalPrice());
                                prmsFinancialEvaluaDetail.setBidDetailId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId());
                                prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);
                                prmsFinancialEvaluaDetail.setServiceId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getBidDetailId().getServiceId());
                                prmsFinancialEvaluaDetail.setLotNo(prmsBid.getPrmsBidDetailList().get(i).getLotName());
                                prmsFinancialEvaluaDetail.setSupplierCode(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getSupplierCode());
                                prmsFinancialEvaluaDetail.setQuantity(prmsBid.getPrmsBidDetailList().get(i).getQuantity());
                                prmsFinancialEvaluaDetail.setSupplierId(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId());
                                prmsFinancialEvaluaDetail.setVat(prmsBid.getPrmsBidDetailList().get(i).getPrmsThechincalEvaluationDets().get(j).getPreliminaryDetId().getSupplierId().getVatTypeId().getVatRate());
                                prmsFinancialEvaluaDetail.setCurrencyId(fmsLuCurrency);
                                prmsFinancialEvaluaDetail.setDiscountPrice(Double.valueOf(percent));
                                prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
                            }
                        }

                    }
                }
            }
        }

        reCreateData();

    }
    List<MmsItemRegistration> itemList;

    public List<MmsItemRegistration> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        return itemList;
    }

    public void setItemList(List<MmsItemRegistration> itemList) {
        this.itemList = itemList;
    }

    public void valueChangListnrItemCod(ValueChangeEvent event) {
        System.out.println("----Bid Num----" + event.getNewValue().toString());
        prmsThechnicalEvaluation = finanlEvalutionBeanLocal.ItemNam(event.getNewValue().toString());

    }

    public void search_FinancialEvaluation() {
        prmsFinancialEvaluation.setPreparedBy(workFlow.getUserAccount());
        prmsFinancialEvaluationList = finanlEvalutionBeanLocal.searchFinacialByNo(prmsFinancialEvaluation);
        reCreatFinancialData();
         prmsFinancialEvaluation = new PrmsFinancialEvaluation();
    }
    List<PrmsQuotationDetail> prmsQuotationDetailList;

    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        return prmsQuotationDetailList;
    }

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> prmsQuotationDetailList) {
        this.prmsQuotationDetailList = prmsQuotationDetailList;
    }

    public PrmsLuVatTypeLookup getPrmsVatTypeLookup() {
        if (prmsVatTypeLookup == null) {
            prmsVatTypeLookup = new PrmsLuVatTypeLookup();
        }
        return prmsVatTypeLookup;
    }

    public void setPrmsVatTypeLookup(PrmsLuVatTypeLookup prmsVatTypeLookup) {
        this.prmsVatTypeLookup = prmsVatTypeLookup;
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

    public void valueChangeListItemInProform(ValueChangeEvent changeEvent) {
        System.out.println("----item Code in Proforma----" + changeEvent.getNewValue().toString());
        if (null != changeEvent.getNewValue()) {
            thechincalEvaluationDetList = finanlEvalutionBeanLocal.getItemListInProforma(changeEvent.getNewValue().toString());
            prmsQuotationDetailList = finanlEvalutionBeanLocal.getItemInQuatation(changeEvent.getNewValue().toString());
            for (int i = 0; i < thechincalEvaluationDetList.size(); i++) {
                for (int j = 0; j < prmsQuotationDetailList.size(); j++) {

                    prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
                    prmsFinancialEvaluaDetail.setFinancialEvaluationId(prmsFinancialEvaluation);
                    prmsFinancialEvaluaDetail.setItemRegistrationId(thechincalEvaluationDetList.get(i).getItemRegistrationId());
                    prmsFinancialEvaluaDetail.setSupplierId(prmsQuotationDetailList.get(i).getSupId());
                    prmsFinancialEvaluaDetail.setQuantity(prmsQuotationDetailList.get(j).getQuantity());
                    prmsFinancialEvaluaDetail.setUnitPrice(prmsQuotationDetailList.get(j).getUnitPrice());
                    //  totalPrice = prmsQuotationDetailList.get(j).getQuantity() * prmsQuotationDetailList.get(j).getUnitPrice();
//                    vat = ((prmsFinancialEvaluaDetail.getSupplierId().getVatTypeId().getVatRate() * totalPrice) + (totalPrice));
//                    vats = prmsFinancialEvaluaDetail.getSupplierId().getVatTypeId().getVatRate();
                    prmsFinancialEvaluation.getPrmsFinancialEvaluaDetailList().add(prmsFinancialEvaluaDetail);
                    System.out.println("----quantity ----" + prmsQuotationDetailList.get(j).getQuantity());
                    System.out.println("----unit price ----" + prmsQuotationDetailList.get(j).getUnitPrice());
                    System.out.println("----supplier Id ----" + prmsQuotationDetailList.get(j).getSupId());
                    // System.out.println("----vat ----" + ((prmsFinancialEvaluaDetail.getSupplierId().getVatTypeId().getVatRate() * totalPrice) + (totalPrice)));
                    System.out.println("----Total ----" + prmsQuotationDetailList.get(j).getQuantity() * prmsQuotationDetailList.get(j).getUnitPrice());
                }
            }
        }

        reCreateData();
    }

    public List<PrmsQuotation> getProformaList() {
        setPrmsQuotationList(finanlEvalutionBeanLocal.getProformaList());
        return prmsQuotationList;
    }

    public List<PrmsPortEntry> getKindOfShopment() {
        setPrmsPortEntryList(finanlEvalutionBeanLocal.getShipKind());
        return prmsPortEntryList;
    }

    public void ItemNa(ValueChangeEvent value) {
        if (null != value.getNewValue()) {
            prmsThechnicalEvaluation = (PrmsThechnicalEvaluation) value.getNewValue();
            int size = thechnicalEvaluationList.size();
            for (int i = 0; i < size; i++) {
                prmsThechnicalEvaluation = thechnicalEvaluationList.get(i);

            }
        }
    }

}
