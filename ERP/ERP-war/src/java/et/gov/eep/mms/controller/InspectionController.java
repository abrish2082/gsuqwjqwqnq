/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
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
import org.primefaces.event.*;
import org.apache.commons.io.FileUtils;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.integration.HrCommitteeMembersIntegrationBeanLocal;
import et.gov.eep.hrms.integration.HrCommitteesIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsInspectionBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLuInspInvoiceTypeBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsInspectionDetail;
import et.gov.eep.mms.entity.MmsInspectionDocuments;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLuInspectionInvoiceType;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.integration.PrmsContractDetailIntegrationBeanLocal;
import et.gov.eep.prms.integration.PrmsContractIntegrationBeanLocal;
import et.gov.eep.prms.integration.PurchaseOrderDtlIntegrationBeanLocal;
import et.gov.eep.prms.integration.PurchaseOrderIntegrationBeanLocal;

/**
 *
 * @author minab
 */
@Named(value = "inspectionController")
@ViewScoped
public class InspectionController implements Serializable {

    public class ColumnNames implements Serializable {

        String Table_Col_Name;
        String Parsed_Col_name;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getTable_Col_Name() {
            return Table_Col_Name;
        }

        public void setTable_Col_Name(String Table_Col_Name) {
            this.Table_Col_Name = Table_Col_Name;
        }

        public String getParsed_Col_name() {
            return Parsed_Col_name;
        }

        public void setParsed_Col_name(String Parsed_Col_name) {
            this.Parsed_Col_name = Parsed_Col_name;
        }
//</editor-fold>

    }
    //<editor-fold defaultstate="collapsed" desc="Entities">

    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    MmsInspection inspection;
    @Inject
    MmsInspectionDetail inspectionDetail;
    @Inject
    HrEmployees employeEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    MmsItemRegistration itemregistrationEntity;
    @Inject
    private PrmsPurchaseOrder purchaseOrdereEntity;
    @Inject
    private PrmsPurOrderDetail purchaseOrderDtlEntity;
    @Inject
    private PrmsContract contractEntity;
    @Inject
    private PrmsContractDetail contractDetailEntity;
    @Inject
    DataUpload dataUpload;
    @Inject
    MmsInspectionDocuments documentsUpload;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    HrCommittees hrcommittesEntity;
    @Inject
    HrCommitteeMembers hrcommitteeMembersEntity;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    MmsItemRegisrtationBeanLocal itemBeanLocal;
    @EJB
    PurchaseOrderIntegrationBeanLocal poBeanLocal;
    @EJB
    PrmsContractDetailIntegrationBeanLocal contractDetailBeanLocal;
    @EJB
    PrmsContractIntegrationBeanLocal contractBeanLocal;
    @EJB
    PurchaseOrderDtlIntegrationBeanLocal poDetailBeanLocal;
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    HREmployeesBeanLocal hrInterface;
    @EJB
    MmsInspectionBeanLocal inspectionLocal;
    @EJB
    MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private HrCommitteesIntegrationBeanLocal committesInterface;
    @EJB
    private HrCommitteeMembersIntegrationBeanLocal committeMembersInterface;
    @EJB
    private MmsLuInspInvoiceTypeBeanLocal invoiceTypeLookupInterface;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Fields">
    DataModel<MmsInspectionDetail> papmsInspectionDetailsModel;
    DataModel<MmsInspectionDetail> papmsInspectionOtherDtlModel;
    private DataModel<MmsInspection> mmsInspectionSearchInfoDataModel;
    private DataModel<DocumentModel> docList;
    DocumentModel documentModel1 = new DocumentModel();
    StreamedContent fileDownloadContent;
    List<HrCommittees> committeeList = new ArrayList<>();
    List<HrCommitteeMembers> committeeMembersList = new ArrayList<>();
    private List<MmsLuInspectionInvoiceType> inspectionInvoiceTypes;
    List<WfMmsProcessed> WfList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    String btn_SaveUpdate = "Save";
    int updateStatus = 0;
    private String checkInspectionType = "1";
    private boolean renderPurchase = true;
    private boolean renderOther = false;
    private boolean dissableContractDropdown = false;
    private boolean renderDecision = false;
    private boolean renderRejectedQuantity = false;
    private boolean disablePoDropdown = false;
    private boolean renderContractPanel = false;
    private boolean renderpoPanel = true;
    private boolean renderpnlToSearchPage;
    private boolean disableDonor;
    private boolean disableProducer;

    private String saveorUpdateBundle = "Save";

    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateInspection = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private int uploadedDocId = -1;
    private String userName;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    private List<MmsInspection> mmsInspectionList;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private String selectedValue = "";
    private List<PrmsPurchaseOrder> purchaseOrderList;
    private List<PrmsContract> contractNoList;
    private String AddorModifiy = "Add";
    StreamedContent file;
    List<DocumentModel> newDoclist = new ArrayList<DocumentModel>();
    List<Integer> savedDocIds = new ArrayList<>();
    DmsHandler dmsHandler = new DmsHandler();
    DocumentModel documentModel = new DocumentModel();
    DataModel<DocumentModel> docValueModel;
    List<MmsItemRegistration> itemCodelist = null;
    List<PrmsContractDetail> ContractMaterialLists;
    List<PrmsPurOrderDetail> PoMaterialList;
    List<MmsItemRegistration> materialNameList = null;
    private MmsInspection selectedInspection;
    List<MmsInspection> allInspectionInfoList;
    List<MmsItemRegistration> materialCodelist = null;
    String newPayNo;
    String lastPaymentNo = "0";
    private List<MmsItemRegistration> itemInfoList;
    String members = "";
    private String[] selectedMembers;
    private MmsInspectionDetail selectedInspectionDetail;
    private List<String> mmsInspectionColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList1 = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();

    /**
     *
     */
    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="PostConstraction">
    public InspectionController() {
    }

    @PostConstruct
    public void init() {
        if (workflow.isApproveStatus()) {
            System.out.println("app or check");
            mmsInspectionList = inspectionLocal.findInspectionListByWfStatus(Constants.PREPARE_VALUE);
            renderDecision = true;
            setRenderRejectedQuantity(true);
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            purchaseOrderList = poBeanLocal.findPurchaseOrdersByWorkFlowStatus(Constants.APPROVE_VALUE);
            contractNoList = contractBeanLocal.findContractsByWorkFlowStatus(Constants.APPROVE_VALUE);
            mmsInspectionList = inspectionLocal.findInspectionListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            renderDecision = false;
            if (mmsInspectionList != null) {
                isRenderedIconNitification = mmsInspectionList.size() > 0;
            }
//            if (contractNoList.size() > 0 || purchaseOrderList.size() > 0) {
//                isRenderedIconNitification = true;
//            } else {
//                isRenderedIconNitification = false;
//            }
            System.out.println("prep");
            renderDecision = false;
            setRenderRejectedQuantity(false);
        }
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        getMmsInspectionColumnNameList();
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="gettre and setter">
    public MmsInspectionDocuments getDocumentsUpload() {
        if (documentsUpload == null) {
            documentsUpload = new MmsInspectionDocuments();
        }
        return documentsUpload;
    }

    public void setDocumentsUpload(MmsInspectionDocuments documentsUpload) {
        this.documentsUpload = documentsUpload;
    }

    public boolean isRenderRejectedQuantity() {
        return renderRejectedQuantity;
    }

    public void setRenderRejectedQuantity(boolean renderRejectedQuantity) {
        this.renderRejectedQuantity = renderRejectedQuantity;
    }

    public DataModel<MmsInspection> getMmsInspectionSearchInfoDataModel() {
        if (mmsInspectionSearchInfoDataModel == null) {
            mmsInspectionSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsInspectionSearchInfoDataModel;
    }

    public void setMmsInspectionSearchInfoDataModel(DataModel<MmsInspection> mmsInspectionSearchInfoDataModel) {
        this.mmsInspectionSearchInfoDataModel = mmsInspectionSearchInfoDataModel;
    }

    public DataModel<WfMmsProcessed> getWfMmsProcessedDataModel() {
        if (WfMmsProcessedDataModel == null) {
            WfMmsProcessedDataModel = new ListDataModel<>();
        }
        return WfMmsProcessedDataModel;
    }

    public void setWfMmsProcessedDataModel(DataModel<WfMmsProcessed> WfMmsProcessedDataModel) {
        this.WfMmsProcessedDataModel = WfMmsProcessedDataModel;
    }

    public String getBtn_SaveUpdate() {
        return btn_SaveUpdate;
    }

    public void setBtn_SaveUpdate(String btn_SaveUpdate) {
        this.btn_SaveUpdate = btn_SaveUpdate;
    }

    public List<MmsInspection> getMmsInspectionList() {
        return mmsInspectionList;
    }

    public void setMmsInspectionList(List<MmsInspection> mmsInspectionList) {
        this.mmsInspectionList = mmsInspectionList;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public DataModel<DocumentModel> getDocList() {

        return docList;
    }

    public void setDocList(DataModel<DocumentModel> docList) {
        this.docList = docList;
    }

    public DocumentModel getDocumentModel1() {
        return documentModel1;
    }

    public void setDocumentModel1(DocumentModel documentModel1) {
        this.documentModel1 = documentModel1;
    }

    public StreamedContent getFileDownloadContent() throws Exception {
        fileDownloadContent = dataUpload.getFile(documentModel1);
        return fileDownloadContent;
    }

    public void setFileDownloadContent(StreamedContent fileDownloadContent) {
        this.fileDownloadContent = fileDownloadContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddorModifiy() {
        return AddorModifiy;
    }

    public void setAddorModifiy(String AddorModifiy) {
        this.AddorModifiy = AddorModifiy;
    }

    public PrmsPurchaseOrder getPurchaseOrdereEntity() {
        if (purchaseOrdereEntity == null) {
            purchaseOrdereEntity = new PrmsPurchaseOrder();
        }
        return purchaseOrdereEntity;
    }

    public void setPurchaseOrdereEntity(PrmsPurchaseOrder purchaseOrdereEntity) {
        this.purchaseOrdereEntity = purchaseOrdereEntity;
    }

    public MmsInspection getSelectedInspection() {
        return selectedInspection;
    }

    public void setSelectedInspection(MmsInspection selectedInspection) {
        this.selectedInspection = selectedInspection;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public MmsInspectionDetail getSelectedInspectionDetail() {
        return selectedInspectionDetail;
    }

    public void setSelectedInspectionDetail(MmsInspectionDetail selectedInspectionDetail) {
        this.selectedInspectionDetail = selectedInspectionDetail;
    }

    public String getCheckInspectionType() {
        return checkInspectionType;
    }

    public void setCheckInspectionType(String checkInspectionType) {
        this.checkInspectionType = checkInspectionType;
    }

    public boolean isRenderPurchase() {
        return renderPurchase;
    }

    public void setRenderPurchase(boolean renderPurchase) {
        this.renderPurchase = renderPurchase;
    }

    public boolean isRenderOther() {
        return renderOther;
    }

    public void setRenderOther(boolean renderOther) {
        this.renderOther = renderOther;
    }

    public List<PrmsPurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PrmsPurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

    public List<PrmsContract> getContractNoList() {
        return contractNoList;
    }

    public void setContractNoList(List<PrmsContract> contractNoList) {
        this.contractNoList = contractNoList;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateInspection() {
        return renderPnlCreateInspection;
    }

    public void setRenderPnlCreateInspection(boolean renderPnlCreateInspection) {
        this.renderPnlCreateInspection = renderPnlCreateInspection;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public void setInspectionAttributes() {
        generateInspectionNo();
        inspection.setInspectionNo(newPayNo);
        inspection.setStoreId(storeInfoEntity);
        concatCommitteMembers();
        inspection.setCommitteMembers(members);
    }

    public void setWorkFlowValues() {
        inspection.setStatus(Constants.PREPARE_VALUE);
        inspection.setProcessedBy(SessionBean.getUserId());
        inspection.setProcessedOn(wfMmsProcessed.getProcessedOn());
    }

    public void setMmsInspectionOtherDtlModel(DataModel<MmsInspectionDetail> papmsInspectionDetailModel) {
        this.papmsInspectionOtherDtlModel = papmsInspectionDetailModel;
    }

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

    public List<HrCommittees> getCommittees() {
        committeeList = committesInterface.findAll();
        return committeeList;
    }

    public MmsInspection getInspection() {
        if (inspection == null) {
            inspection = new MmsInspection();
        }
        return inspection;
    }

    /**
     *
     * @param inspection
     */
    public void setInspection(MmsInspection inspection) {
        this.inspection = inspection;
    }

    /**
     *
     * @return
     */
    public MmsInspectionDetail getInspectionDetail() {
        if (inspectionDetail == null) {
            inspectionDetail = new MmsInspectionDetail();
        }

        return inspectionDetail;
    }

    /**
     *
     * @param inspectionDetail
     */
    public void setInspectionDetail(MmsInspectionDetail inspectionDetail) {
        this.inspectionDetail = inspectionDetail;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsInspectionDetail> getMmsInspectionDetailsModel() {
        if (papmsInspectionDetailsModel == null) {
            papmsInspectionDetailsModel = new ListDataModel<>();
        }
        return papmsInspectionDetailsModel;
    }

    /**
     *
     * @param papmsInspectionDetailsModel
     */
    public void setMmsInspectionDetailsModel(DataModel<MmsInspectionDetail> papmsInspectionDetailsModel) {
        this.papmsInspectionDetailsModel = papmsInspectionDetailsModel;
    }

    public DataModel<MmsInspectionDetail> getMmsInspectionOtherDtlModel() {
        if (papmsInspectionOtherDtlModel == null) {
            papmsInspectionOtherDtlModel = new ListDataModel<>();
        }
        return papmsInspectionOtherDtlModel;
    }

    /**
     *
     * @param papmsInspectionDetailModel
     */
    public List<HrCommitteeMembers> getCommitteeMembers() {
        committeeMembersList = committeMembersInterface.getCommitteeMembers(hrcommittesEntity);

        return committeeMembersList;
    }

    public String[] getSelectedMembers() {
        return selectedMembers;
    }

    public void setSelectedMembers(String[] selectedMembers) {
        this.selectedMembers = selectedMembers;
    }

    public List<MmsItemRegistration> getItemInfoList() {
        if (itemInfoList == null) {
            itemInfoList = new ArrayList<>();
        }
        return itemInfoList;
    }

    public void setItemInfoList(List<MmsItemRegistration> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

    public MmsItemRegistration getItemregistrationEntity() {
        if (itemregistrationEntity == null) {
            itemregistrationEntity = new MmsItemRegistration();
        }
        return itemregistrationEntity;
    }

    /**
     *
     * @param itemregistrationEntity
     */
    public void setItemregistrationEntity(MmsItemRegistration itemregistrationEntity) {
        this.itemregistrationEntity = itemregistrationEntity;
    }

    /**
     *
     * @param itemName
     * @return
     */
    public void getPapmsStoreInfo(SelectEvent event) {
        storeInfoEntity.setStoreName(event.getObject().toString());
        storeInfoEntity = storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
        inspection.setStoreId(storeInfoEntity);

    }

    public PrmsContract getContractEntity() {
        if (contractEntity == null) {
            contractEntity = new PrmsContract();
        }
        return contractEntity;
    }

    public void setContractEntity(PrmsContract contractEntity) {
        this.contractEntity = contractEntity;
    }

    public boolean isRenderContractPanel() {
        return renderContractPanel;
    }

    public void setRenderContractPanel(boolean renderContractPanel) {
        this.renderContractPanel = renderContractPanel;
    }

    public boolean isRenderpoPanel() {
        return renderpoPanel;
    }

    public void setRenderpoPanel(boolean renderpoPanel) {
        this.renderpoPanel = renderpoPanel;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public PrmsContractDetail getContractDetailEntity() {
        if (contractDetailEntity == null) {
            contractDetailEntity = new PrmsContractDetail();
        }
        return contractDetailEntity;
    }

    public void setContractDetailEntity(PrmsContractDetail contractDetailEntity) {
        this.contractDetailEntity = contractDetailEntity;
    }

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public boolean isDissableContractDropdown() {
        return dissableContractDropdown;
    }

    public void setDissableContractDropdown(boolean dissableContractDropdown) {
        this.dissableContractDropdown = dissableContractDropdown;
    }

    public boolean isDisablePoDropdown() {
        return disablePoDropdown;
    }

    public void setDisablePoDropdown(boolean disablePoDropdown) {
        this.disablePoDropdown = disablePoDropdown;
    }

    public PrmsPurOrderDetail getPurchaseOrderDtlEntity() {
        if (purchaseOrderDtlEntity == null) {
            purchaseOrderDtlEntity = new PrmsPurOrderDetail();
        }
        return purchaseOrderDtlEntity;
    }

    public void setPurchaseOrderDtlEntity(PrmsPurOrderDetail purchaseOrderDtlEntity) {
        this.purchaseOrderDtlEntity = purchaseOrderDtlEntity;
    }

    public boolean isDisableDonor() {
        return disableDonor;
    }

    public void setDisableDonor(boolean disableDonor) {
        this.disableDonor = disableDonor;
    }

    public boolean isDisableProducer() {
        return disableProducer;
    }

    public void setDisableProducer(boolean disableProducer) {
        this.disableProducer = disableProducer;
    }

    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public List<WfMmsProcessed> getWfList() {
        return WfList;
    }

    public void setWfList(List<WfMmsProcessed> WfList) {
        this.WfList = WfList;
    }

    public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        return WorkflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfMmsProcessed> WorkflowDataModel) {
        this.WorkflowDataModel = WorkflowDataModel;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

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

    public List<PrmsPurOrderDetail> getPoMaterialLists() {
        if (PoMaterialList == null) {
            PoMaterialList = new ArrayList<>();
        }
        return PoMaterialList;
    }

    public void setPoMaterialList(List<PrmsPurOrderDetail> PoMaterialList) {
        this.PoMaterialList = PoMaterialList;
    }

    public List<MmsItemRegistration> getPOMaterialList() {
        PoMaterialList = purchaseOrdereEntity.getPrmsPurOrderDetailList();
        for (int i = 0; i < PoMaterialList.size(); i++) {
            itemInfoList.add(PoMaterialList.get(i).getItemId());
        }
        return itemInfoList;

    }

    public SelectItem[] getGRNMaterialList() {

        return JsfUtil.getSelectItems(purchaseOrdereEntity.getPrmsPurOrderDetailList(), true);
    }

    public List<PrmsContractDetail> getContractMaterialLists() {
        if (ContractMaterialLists == null) {
            ContractMaterialLists = new ArrayList<>();
        }
        return ContractMaterialLists;
    }

    public void setContractMaterialLists(List<PrmsContractDetail> ContractMaterialLists) {
        this.ContractMaterialLists = ContractMaterialLists;
    }

    public List<MmsItemRegistration> getContractMaterialList() {
        ContractMaterialLists = contractEntity.getPrmsContractDetailList();
        for (int i = 0; i < ContractMaterialLists.size(); i++) {
            itemInfoList.add(ContractMaterialLists.get(i).getItemId());

        }
        return itemInfoList;

    }

    public void getEmployeeInfo(SelectEvent event) {
        inspection.setApprovedBy(event.getObject().toString());
    }

    public void getStoreInfo(SelectEvent e) {
        inspection.setStoreName(e.getObject().toString());
    }

    public List<String> getMmsInspectionColumnNameList() {
        mmsInspectionColumnNameList = inspectionLocal.getMmsInspectionColumnNameList();
        if (mmsInspectionColumnNameList == null) {
            mmsInspectionColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsRmgColumnNameList==" + mmsInspectionColumnNameList);
        if (mmsInspectionColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsInspectionColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsInspectionColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsInspectionColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }

        return mmsInspectionColumnNameList;
    }

    public void setMmsInspectionColumnNameList(List<String> mmsInspectionColumnNameList) {
        this.mmsInspectionColumnNameList = mmsInspectionColumnNameList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="save,search,handelar">
    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    /*This method is used to On Select Request
     */
    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                inspection = (MmsInspection) event.getNewValue();
                setIsRenderedIconWorkflow(true);

                populateUI();
            }
        } catch (Exception e) {

            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            inspection.setColumnName(columnNameResolver.getCol_Name_FromTable());
            inspection.setColumnValue(null);

        }
    }
    
    /*This method is used to Save Work flows
     */

    public void wfSave() {
        System.out.println("inside wf save");
        wfMmsProcessed.setInspectionId(inspection);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save Inspection Informations
     */

    public void saveInspection() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveInspection", dataset)) {

                if (updateStatus == 0) {
                    if (renderPurchase == true && papmsInspectionDetailsModel.getRowCount() <= 0) {
                        JsfUtil.addErrorMessage("saving without data is not allowed");
                    } else if (renderPurchase == true && papmsInspectionDetailsModel.getRowCount() > 0) {
                        try {

                            inspection.setSelectOption(Integer.parseUnsignedInt(checkInspectionType));
                            setInspectionAttributes();
                            inspection.addInspectionIdToWorkflow(wfMmsProcessed);

                            setWorkFlowValues();
                            saveDocId();
                            inspectionLocal.create(inspection);
                            if (inspection.getPurchaseOId() != null) {
                                purchaseOrdereEntity.setStatus(Constants.PURCHASE_ORDER_PROCESSED_BY_MMS_INSPECTION);
                            }
                            if (inspection.getContractId() != null) {
                                contractEntity.setStatus(Constants.PRMS_CONTRACT_PROCESSED_BY_MMS_INSPECTION);
                            }

                            JsfUtil.addSuccessMessage("Inspection Data is Saved " + inspection.getInspectionNo());
                            clearPage();
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                            clearPage();

                        }

                    }

                    if (renderOther == true && papmsInspectionOtherDtlModel.getRowCount() <= 0) {
                        JsfUtil.addErrorMessage("saving without data is not allowed");
                    } else if (renderOther == true && papmsInspectionOtherDtlModel.getRowCount() > 0) {
                        try {
                            setInspectionAttributes();
                            inspection.addInspectionIdToWorkflow(wfMmsProcessed);
                            setWorkFlowValues();
                            saveDocId();
                            inspectionLocal.create(inspection);
                            JsfUtil.addSuccessMessage("Inspection Data is Saved " + newPayNo);
                            clearPage();
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                            clearPage();

                        }
                    }

                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    {
                        try {

                            concatCommitteMembers();
                            saveDocId();
                            inspection.setCommitteMembers(members);
                            inspection.setStatus(Constants.PREPARE_VALUE);
                            inspectionLocal.edit(inspection);
                            JsfUtil.addSuccessMessage("Inspection Information Data Updated");
                            btn_SaveUpdate = "Save";
                            mmsInspectionList.remove(inspection);
                            clearPage();
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Updated");
                            clearPage();

                        }

                    }
                } else if (updateStatus == 1 && (workflow.isApproveStatus())) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);

                        inspection.setStatus(Constants.APPROVE_VALUE);

                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        inspection.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    }
                    wfMmsProcessed.setInspectionId(inspection);

                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    inspectionLocal.edit(inspection);

                    mmsInspectionList.remove(inspection);
                    clearPage();
                    JsfUtil.addSuccessMessage("Inspection Data Updated");

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

        }
    }
    /*This method is used to Concatinating the Committe Members
     */

    public void concatCommitteMembers() {
        for (int i = 0; i < selectedMembers.length; i++) {
            if (members.equals("")) {
                members = selectedMembers[i];
            } else {
                members = members + "," + selectedMembers[i];
            }

        }
    }
    /*This method is used to Save Docment Id
     */

    public void saveDocId() {
        if (docValueModel != null && docValueModel.getRowCount() > 0) {
            System.out.println("=======Inside if savedocid=====");
            for (int i = 0; i < savedDocIds.size(); i++) {
                documentsUpload = new MmsInspectionDocuments();
                documentsUpload.setDocumentId(savedDocIds.get(i));
                inspection.Add(documentsUpload);
            }
        }

    }
    /*This method is used to clear the page when the save mathed executed
     */

    public String clearSave() {
        inspection = null;
        inspectionDetail = null;

        return null;
    }

    /*This method is used to Clear the page
     */
    public String clearPage() {
        inspection = null;
        inspectionDetail = null;
        papmsInspectionDetailsModel = null;
        papmsInspectionOtherDtlModel = null;
        storeInfoEntity = null;
        updateStatus = 0;
        purchaseOrdereEntity = null;
        contractEntity = null;
        hrcommittesEntity = null;
        hrcommitteeMembersEntity = null;
        mmsInspectionSearchInfoDataModel = new ListDataModel<>();
        wfMmsProcessed = null;
        setSelectedValue(null);
        docValueModel = null;
        return null;
    }

    /*This method is used to On Row Select
     */
    public void onRowSelect(SelectEvent event) {
        documentModel = (DocumentModel) event.getObject();
    }
    /*This method is used to Handle the selcted Committee
     */

    public void handleSelectCommittee(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrcommittesEntity.setCommitteeName(event.getNewValue().toString());
            hrcommittesEntity = committesInterface.getCommittee(hrcommittesEntity);
            inspection.setCommitteeId(hrcommittesEntity);

        }
    }
    /*This method is used to Add Inspection Derail
     */

    public void addInspectionInfoDetail() {

        if (workflow.isApproveStatus()) {
            if (inspectionDetail.getRejectedQuantity().compareTo(inspectionDetail.getQuantity()) <= 0) {
                inspectionDetail.setQuantity(inspectionDetail.getPoQuantity().subtract(inspectionDetail.getRejectedQuantity()));
                addPurchaseDetail();
            } else {
                JsfUtil.addFatalMessage("Rejected Quantity Can Not be Greater Than Requested Quantity");
            }

        } else {
            inspectionDetail.setQuantity(inspectionDetail.getPoQuantity());
            addPurchaseDetail();
        }
    }
    /*This method is used to Add Purchase Detail
     */

    public void addPurchaseDetail() {

        inspection.addInspectionDetialInfo(inspectionDetail);
        recreateInspectionDataModel();

        clearPopup();
    }
    /*This method is used to Add Inspaction Information For Other
     */

    public void addInspectionInfoforOther() {

        inspection.addInspectionDetialInfo(inspectionDetail);
        recreateInspectionDataForOther();

        clearPopup();

    }

    private void recreateInspectionDataModel() {
        papmsInspectionDetailsModel = null;
        papmsInspectionDetailsModel = new ListDataModel(new ArrayList<>(inspection.getMmsInspectionDetailList()));
    }

    private void recreateInspectionDataForOther() {
        papmsInspectionOtherDtlModel = null;
        papmsInspectionOtherDtlModel = new ListDataModel(new ArrayList<>(inspection.getMmsInspectionDetailList()));
    }
    /*This method is used to Clear Popup
     */

    private void clearPopup() {
        inspectionDetail = null;
        itemregistrationEntity = null;
        purchaseOrderDtlEntity = null;
        contractDetailEntity = null;
    }
    /*This method is used to Update Inspaction Detail
     */

    public void updateInspectionDetail(SelectEvent event) {
        AddorModifiy = "Modifiy";
        inspectionDetail = (MmsInspectionDetail) event.getObject();
        itemregistrationEntity = inspectionDetail.getItemId();
        itemInfoList = new ArrayList<>();
        itemInfoList.add(itemregistrationEntity);
    }

    /*This method is used to Searching By Inspaction No
     */
    public ArrayList<MmsInspection> searchByInspectionNo(String inspnumber) {
        ArrayList<MmsInspection> inspNo = null;
        inspection.setInspectionNo(inspnumber);
        inspNo = inspectionLocal.searchByinspectionNumber(inspection);
        return inspNo;

    }

    /*This method is used to selecting event by Inspaction Name
     */
    public void getByInspectorName(SelectEvent event) {
        inspection = (MmsInspection) event.getObject();
        inspection.getInspector1();
        recreateInspectionDataModel();
        updateStatus = 1;
        btn_SaveUpdate = "Update";

    }
    /*This method is used to selecting event by Inspaction No
     */

    public void getByInspectionNo(SelectEvent event) {
        inspection = (MmsInspection) event.getObject();
        inspection.getInspectionNo();
        recreateInspectionDataModel();
        updateStatus = 1;
        btn_SaveUpdate = "Update";
    }
    /*This method is used to Searching Employee Information
     */

    public ArrayList<HrEmployees> searchEmployeeInformation(String FirstName) {
        ArrayList<HrEmployees> employeeInformations = null;// to make the previous search  paramaters and results null;
        employeEntity.setFirstName(FirstName);
        employeeInformations = hrInterface.searchEmployeeInfo(employeEntity);
        return employeeInformations;
    }
    /*This method is used to Generate Inspaction No
     */

    public String generateInspectionNo() {
        if (updateStatus == 1) {
            newPayNo = inspection.getInspectionNo();
        } else {
            MmsInspection lastPaymentNoObj = inspectionLocal.getLastinspectionNo();
            if (lastPaymentNoObj != null) {
                lastPaymentNo = lastPaymentNoObj.getInspectionId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newPayment = 0;
            if (lastPaymentNo.equals("0")) {
                newPayment = 1;
                newPayNo = "Insp-" + newPayment + "/" + f.format(now);
            } else {

                String[] lastInspNos = lastPaymentNo.split("-");
                String lastDatesPatern = lastInspNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newPayNo = "Insp-" + newPayment + "/" + f.format(now);

            }
        }
        return newPayNo;

    }

    /*This method is used to Searching Item Name
     */
    public ArrayList<MmsItemRegistration> searchItemName(String itemName) {
        ArrayList<MmsItemRegistration> itemNames = null;
        itemregistrationEntity.setMatName(itemName);
        itemNames = itemBeanLocal.searchByMaterialName(itemregistrationEntity);
        return itemNames;
    }

    public void getItemCode(SelectEvent event) {
        itemregistrationEntity = (MmsItemRegistration) event.getObject();

    }

    public List<MmsItemRegistration> materialInfoSearchlist(String materialCode) {
        itemregistrationEntity.setMatCode(materialCode);
        materialCodelist = itemBeanLocal.searchMaterialInfoByMatCode(itemregistrationEntity);
        return materialCodelist;
    }

    public void getMmsItemInfo(SelectEvent event) {
        itemregistrationEntity.setMatCode(event.getObject().toString());
        itemregistrationEntity = itemBeanLocal.getMmsItemInfoByCode(itemregistrationEntity);
        inspectionDetail.setItemId(itemregistrationEntity);

    }
    /*This method is used to Go Back Searching Button Action
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateInspection = false;
        renderPnlManPage = true;
    }
    /*This method is used to Create New Inspaction Information
     */

    public void createNewInspectionInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateInspection = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateInspection = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }

    }
    /*This method is used to btn_Search_Action
     */

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateInspection = false;
        renderPnlManPage = true;
    }

    private void recerateInspectionSerachModel() {
        mmsInspectionSearchInfoDataModel = null;
        mmsInspectionSearchInfoDataModel = new ListDataModel(new ArrayList<>(allInspectionInfoList));
    }
    /*This method is used to searchInspectionInformation
     */

    public void searchInspectionInformation1() {

        System.out.println("in search");
        inspection.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + inspection.getProcessedBy());
        allInspectionInfoList = inspectionLocal.searchByCol_NameAndCol_Value(columnNameResolver, inspection, inspection.getColumnValue());
        recerateInspectionSerachModel();
//        if (workflow.isApproveStatus()) {
//            allInspectionInfoList = inspectionLocal.findAllByStatus(Constants.APPROVE_VALUE);
//            recerateInspectionSerachModel();
//        }
//        inspection.setProcessedBy(SessionBean.getUserId());
//        if (!inspection.getInspectionNo().isEmpty()) {
//
//            allInspectionInfoList = inspectionLocal.searchByinspectionNumber(inspection);
//
//            recerateInspectionSerachModel();
//
//        } else {
//            allInspectionInfoList = inspectionLocal.searchAllByPreparerId(inspection);
//            recerateInspectionSerachModel();
//        }
    }
    /*This method is used to On Row Editing
     */

    public void onRowEdit(SelectEvent event) {
        inspection = (MmsInspection) event.getObject();
        populateUI();

    }
    /*This method is used to populate
     */

    public void populateUI() {
        renderpnlToSearchPage = true;
        if (inspection.getSelectOption() == 1) {
            setCheckInspectionType("1");
            setRenderPurchase(true);
            setRenderOther(false);
        } else {
            setCheckInspectionType("2");
            setRenderPurchase(false);
            setRenderOther(true);
            setRenderpoPanel(false);
        }

        if (inspection.getContractId() != null) {
            System.out.println("========inside contract======");
            contractEntity = inspection.getContractId();
            contractNoList = new ArrayList<>();
            contractNoList.add(contractEntity);
            setDisablePoDropdown(true);
            setDissableContractDropdown(false);
            setRenderContractPanel(true);
            setRenderpoPanel(false);

        } else if (inspection.getPurchaseOId() != null) {
            System.out.println("========inside PO======");
            purchaseOrdereEntity = inspection.getPurchaseOId();
            purchaseOrderList = new ArrayList<>();
            purchaseOrderList.add(purchaseOrdereEntity);
            setDisablePoDropdown(false);
            setDissableContractDropdown(true);
            setRenderContractPanel(false);
            setRenderpoPanel(true);
        }
        hrcommittesEntity = inspection.getCommitteeId();
        selectedMembers = inspection.getCommitteMembers().split(",");
        System.out.println("===========storeName====" + inspection.getStoreId().getStoreName());
        storeInfoEntity = inspection.getStoreId();
        storeInfoEntity.setStoreName(inspection.getStoreId().getStoreName());
        renderPnlCreateInspection = true;

        renderPnlManPage = false;
        wfMmsProcessed.setProcessedOn(inspection.getProcessedOn());
        saveorUpdateBundle = "Update";

        updateStatus = 1;

        if (renderOther == true) {
            recreateInspectionDataForOther();
        } else {
            recreateInspectionDataModel();
        }
        recreateWfDataModel();
    }
    /*This method is used to Recreare Workflow DataModel
     */

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        for (int i = 0; i < inspection.getWfMmsProcessedList().size(); i++) {
            if (inspection.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (inspection.getWfMmsProcessedList().get(i).getDecision() == 1 || inspection.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    inspection.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (inspection.getWfMmsProcessedList().get(i).getDecision() == 2 || inspection.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    inspection.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                inspection.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }

        WfMmsProcessedDataModel = new ListDataModel(inspection.getWfMmsProcessedList());
    }
    /*This method is used to Change Checking Inspaction Type
     */

    public void changeCheckInspectionType(ValueChangeEvent e) {
        inspection.setSelectOption(1);
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {

                setRenderPurchase(true);
                setRenderOther(false);
                inspection.setSelectOption(1);
                clearPage();
            } else {
                setRenderPurchase(false);
                setRenderOther(true);
                inspection.setSelectOption(2);
            }

        }
    }
    /*This method is used to handle the selcting PONo
     */

    public void handleSelectPoNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            purchaseOrdereEntity = (PrmsPurchaseOrder) event.getNewValue();

            setDissableContractDropdown(true);

            inspection.setPurchaseONo(purchaseOrdereEntity.getPacNo());
            inspection.setPurchaseOId(purchaseOrdereEntity);
            getPOMaterialList();

        }

    }

    public List<MmsItemRegistration> ItemInformationList(String itemName) {
        itemregistrationEntity.setMatName(itemName);
        itemregistrationEntity.setStoreId(storeInfoEntity);
        materialNameList = itemBeanLocal.searchMaterialInfoByStoreAndMatName(itemregistrationEntity);
        return materialNameList;
    }

    public void getMmsItemInformation(SelectEvent event) {
        itemregistrationEntity.setMatName(event.getObject().toString());
        itemregistrationEntity = itemBeanLocal.getByMaterialName(itemregistrationEntity);
        inspectionDetail.setItemId(itemregistrationEntity);

    }

    public void handleSelectContractNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            setRenderContractPanel(true);
            setRenderpoPanel(false);
            setDisablePoDropdown(true);

            contractEntity = (PrmsContract) event.getNewValue();
            System.out.println("=====contract list==" + contractEntity.getPrmsContractDetailList().size());
            inspection.setContractId(contractEntity);
            getContractMaterialList();
//        

        }

    }

    public List<MmsLuInspectionInvoiceType> getListOfInvoiceType() {
        inspectionInvoiceTypes = invoiceTypeLookupInterface.findAll();
        return inspectionInvoiceTypes;
    }

    public void handleSelectInvoiceType(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            if (event.getNewValue().toString().equalsIgnoreCase("Donation")) {
                setDisableProducer(true);
                setDisableDonor(false);
            } else {
                setDisableProducer(false);
                setDisableDonor(true);
            }

        }
    }
    /*This method is used to Purchase order material list
     */

    public void MaterialCodeListInspection(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            itemregistrationEntity = (MmsItemRegistration) event.getNewValue();
            inspectionDetail.setItemId(itemregistrationEntity);
            if (inspection.getPurchaseOId() != null) {
                for (int i = 0; i < itemregistrationEntity.getPrmsPurOrderDetailList().size(); i++) {
                    inspectionDetail.setPoQuantity(BigDecimal.valueOf(itemregistrationEntity.getPrmsPurOrderDetailList().get(i).getQuantity()));
                    inspectionDetail.setUnitPrice(itemregistrationEntity.getPrmsPurOrderDetailList().get(i).getUnitPrice());
                }

            } else if (inspection.getContractId() != null) {
                for (int i = 0; i < itemregistrationEntity.getPrmsContractDetailList().size(); i++) {
                    inspectionDetail.setPoQuantity(BigDecimal.valueOf(itemregistrationEntity.getPrmsContractDetailList().get(i).getQuantity()));
                    inspectionDetail.setUnitPrice(itemregistrationEntity.getPrmsContractDetailList().get(i).getUnitPrice());
                }

            }

        }
    }

    public void handleSelectMaterialListFromContract(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer id = Integer.parseInt(event.getNewValue().toString());

            itemregistrationEntity.setMaterialId(id);
            contractDetailEntity.setItemId(itemregistrationEntity);
            contractDetailEntity = contractDetailBeanLocal.getInfoByMatId(itemregistrationEntity);
            itemregistrationEntity = contractDetailEntity.getItemId();
            inspectionDetail.setItemId(itemregistrationEntity);
            inspectionDetail.setContractQuantity(contractDetailEntity.getQuantity().intValue());

        }
    }
    /*method for Donor and production
     */

    public List<MmsItemRegistration> materialSearchAutoComplete(String materialCode) {

        itemregistrationEntity.setMatCode(materialCode);

        itemCodelist = itemBeanLocal.searchMaterialInfoByMatCode(itemregistrationEntity);

        return itemCodelist;
    }

    /**
     *
     * @param storeName
     * @return
     */
    public ArrayList<MmsStoreInformation> searchStoreInformation(String storeName) {
        ArrayList<MmsStoreInformation> storeInformations = null;// to make the previous search  paramaters and results null;
        storeInfoEntity.setStoreName(storeName);
        storeInformations = storeInfoInterface.searchStoreInformation(storeInfoEntity);
        return storeInformations;
    }
    /*This method is used to ReceateDataModel
     */

    public void recreateDataModel() {
        newDoclist.clear();
        DmsHandler dmsHandler = new DmsHandler();
        DocList docList = new DocList();
        List<String> docId = new ArrayList<>();
        for (int i = 0; i < inspection.getInspectionDocumentsUploadList().size(); i++) {
            docId.add(inspection.getInspectionDocumentsUploadList().get(i).getDocumentId().toString());
        }
        docList.setDocIds(docId);
        DocList docListNew = dmsHandler.getDocumentsById(docList);
        System.out.println("=============docListNew===" + docListNew);
        if (docListNew != null) {
            newDoclist = docListNew.getDocList();
            docValueModel = new ListDataModel(docListNew.getDocList());
            System.out.println("=============docValueModel===" + docValueModel);
        }
    }
    /*This method is used to UploadListener
     */

    public void uploadListener(FileUploadEvent event) {
        try {
            documentModel = new DocumentModel();
            documentModel.setDocFormat(event.getFile().getFileName().split("\\.")[1]);
            documentModel.setName(event.getFile().getFileName().split("\\.")[0]);
            documentModel.setUserId(Long.valueOf("2"));
            String categoryBundle = "et/gov/eep/commonApplications/securityServer";
            documentModel.setApp(insa.org.et.security.Utility.getBundleValue(categoryBundle, "categoryName"));
            documentModel.setCreater("Sadik");
            File fileDoc = new File(event.getFile().getFileName());
            FileUtils.writeByteArrayToFile(fileDoc, dmsHandler.extractByteArray1(event.getFile().getInputstream()));
            documentModel.setDate(et.gov.eep.mms.controller.StringDateManipulation.todayInEC());
            documentModel.setFile(fileDoc);
            documentModel.setByteFile(dmsHandler.extractByteArray1(event.getFile().getInputstream()));
            int savedDocId = dmsHandler.saveDocument(documentModel);
            System.out.println("=======savedDocId======" + savedDocId);
            if (savedDocId != -1) {
                savedDocIds.add(savedDocId);
                newDoclist.add(documentModel);
                setDocValueModel(new ListDataModel(newDoclist));
                JsfUtil.addSuccessMessage("Upload Successfully!!!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*This method is used to getFile
     */

    public StreamedContent getFile() throws Exception {
        if (documentModel != null) {
            File fileDoc = new File(""
                    + documentModel.getName() + "."
                    + documentModel.getDocFormat());
            FileUtils.writeByteArrayToFile(fileDoc, documentModel.getByteFile());
            InputStream input = new FileInputStream(fileDoc);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            file = new DefaultStreamedContent(input, externalContext.getMimeType(fileDoc.getName()),
                    documentModel.getName() + "." + documentModel.getDocFormat());
            return file;

        }
        return null;
    }
    /*This method is used to Remove Document
     */

    public void remove(DocumentModel document) {
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDataModel();
    }
     //</editor-fold> 
}
