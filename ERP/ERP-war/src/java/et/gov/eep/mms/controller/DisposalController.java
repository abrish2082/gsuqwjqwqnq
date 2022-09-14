
package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.businessLogic.MmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.mms.businessLogic.MmsDisposalBeanLocal;
import et.gov.eep.mms.businessLogic.MmsDisposalItemBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalDtl;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
//</editor-fold>

/**
 *
 * @author Minab Sahilu
 */
@Named(value = "disposalController")
@ViewScoped
public class DisposalController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsDisposalBeanLocal disposalInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fxAssetRegDtlInterface;
    @EJB
    private MmsDisposalItemBeanLocal dispCollectionInterface;
    @EJB
    MmsLuDmArchiveBeanLocal mmsLuDmArchiveBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntity;
    @Inject
    MmsDisposal dispEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    DataUpload dataUpload;
    @Inject
    private MmsDisposalDtl dispDtlEntity;
    @Inject
    private MmsDisposalItems dispColectionEntity;
    @Inject
    private MmsDisposalItemsDtl dispCollectionDtlEntity;
    @Inject
    private FmsDprOfficeAsset fmsDprEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    MmsLuDmArchive mmsLuDmArchive;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
    
   //<editor-fold defaultstate="collapsed" desc="Declare Lists and Models">
    private DataModel<MmsDisposalDtl> dispposalAddDetailDataModel;
    private DataModel<MmsDisposal> mmsDispposalSearchInfoDataModel;
    DataModel<DocumentModel> DMdocumentModel;
    DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel;
    List<Integer> documentId;
    List<MmsDisposal> allDisposeInfoList;
    List<MmsDisposal> checkerList = new ArrayList<>();
    List<MmsStoreInformation> storeInfoList = new ArrayList<>();
    List<MmsDisposalItems> DispNoList = new ArrayList<>();
    List<MmsDisposalItemsDtl> itemcodeDtl = new ArrayList<>();
    List<MmsDisposalItems> nameList = new ArrayList<>();
    List<MmsLuDmArchive> mmsLuDmArchiveList = new ArrayList<>();
    List<FmsDprOfficeAsset> bookValueList = new ArrayList<>();
    private List<MmsDisposal> mmsDisposalColumnNameList;
     List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
   //<editor-fold defaultstate="collapsed" desc="Declare Fields">
    MmsLuDmArchive mmsLuDmArchiveSelection;
    String selectedValue = "";
    private boolean renderDecision = false;
    boolean isRenderCreate;
    private boolean isFileSelected = false;
    private MmsDisposal hpSelect;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    String disposalmethod = "true";
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateDisposal = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchpage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    Set<String> checkMaterialCode = new HashSet<>();
    private List<MmsDisposal> mmsDisposalList;
    private String userName;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    int refNumber;
    private DocumentModel documentModelSelection;
    private StreamedContent download;
    String newDisposalId;
    String lastDisposalId = "0";
    private String addOrModifyBundle = "Add";
    int bookvalue = 0;
    Integer farDtlId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Constructor">
    public DisposalController() {
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {
        ColumnNameResolverList = disposalInterface.getColumnNameList();
        if (workflow.isApproveStatus()) {
            System.out.println("app or check");
            mmsDisposalList = disposalInterface.findDisposalListByWfStatus(Constants.PREPARE_VALUE);
            renderDecision = true;
            isRenderCreate = false;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsDisposalList = disposalInterface.findDisposalListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            System.out.println("prep");
            renderDecision = false;
            isRenderCreate = true;
            if (mmsDisposalList.size() > 0) {
                isRenderedIconNitification = true;
            } else {
                isRenderedIconNitification = false;
            }
        }
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of variables">
    public String getSelectedValue() {
        return selectedValue;
    }
    
    public boolean isRenderpnlToSearchpage() {
        return renderpnlToSearchpage;
    }
    
    public void setRenderpnlToSearchpage(boolean renderpnlToSearchpage) {
        this.renderpnlToSearchpage = renderpnlToSearchpage;
    }
    
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
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

    public FmsDprOfficeAsset getFmsDprEntity() {
        if (fmsDprEntity == null) {
            fmsDprEntity = new FmsDprOfficeAsset();
        }
        return fmsDprEntity;
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

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
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

    public boolean isRenderPnlCreateDisposal() {
        return renderPnlCreateDisposal;
    }

    public void setRenderPnlCreateDisposal(boolean renderPnlCreateDisposal) {
        this.renderPnlCreateDisposal = renderPnlCreateDisposal;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }
    /**
     * @return the addOrModify
     */
    /**
     * @return the addOrModifyBundle
     */
    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    /**
     * @param addOrModifyBundle the addOrModifyBundle to set
     */
    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of List and Data modl">
    public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }
    
    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }
    
    public DataModel<DocumentModel> getDMdocumentModel() {
        return DMdocumentModel;
    }
    
    public void setDMdocumentModel(DataModel<DocumentModel> DMdocumentModel) {
        this.DMdocumentModel = DMdocumentModel;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public DataModel<MmsLuDmArchive> getMmsLuDmArchivesDModel() {
        return mmsLuDmArchivesDModel;
    }
    
    public void setMmsLuDmArchivesDModel(DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel) {
        this.mmsLuDmArchivesDModel = mmsLuDmArchivesDModel;
    }
    
    public DocumentModel getDocumentModelSelection() {
        return documentModelSelection;
    }
    
    public void setDocumentModelSelection(DocumentModel documentModelSelection) {
        this.documentModelSelection = documentModelSelection;
    }

    public ColumnNameResolver getColumnNameResolver() {
        if(columnNameResolver==null){
            columnNameResolver= new ColumnNameResolver();
        }
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }
    
    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }
    
    public MmsDisposal getHpSelect() {
        return hpSelect;
    }
    
    public void setHpSelect(MmsDisposal hpSelect) {
        this.hpSelect = hpSelect;
    }
    
    public List<MmsDisposal> getMmsDisposalList() {
        return mmsDisposalList;
    }
    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
    public void setMmsDisposalList(List<MmsDisposal> mmsDisposalList) {
        this.mmsDisposalList = mmsDisposalList;
    }
    
    public String getDisposalmethod() {
        return disposalmethod;
    }
    
    public void setDisposalmethod(String disposalmethod) {
        this.disposalmethod = disposalmethod;
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
    public DataModel<MmsDisposalDtl> getDispposalAddDetailDataModel() {
        if (dispposalAddDetailDataModel == null) {
            dispposalAddDetailDataModel = new ListDataModel<>();
        }
        return dispposalAddDetailDataModel;
    }

    public void setDispposalAddDetailDataModel(DataModel<MmsDisposalDtl> dispposalAddDetailDataModel) {
        this.dispposalAddDetailDataModel = dispposalAddDetailDataModel;
    }

    public DataModel<MmsDisposal> getMmsDispposalSearchInfoDataModel() {
        if (mmsDispposalSearchInfoDataModel == null) {
            mmsDispposalSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsDispposalSearchInfoDataModel;
    }

    public void setMmsDispposalSearchInfoDataModel(DataModel<MmsDisposal> mmsDispposalSearchInfoDataModel) {
        this.mmsDispposalSearchInfoDataModel = mmsDispposalSearchInfoDataModel;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of Objects">
    public WfMmsProcessed getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WfMmsProcessed();
        }
        return workFlow;
    }
    
    public void setWorkFlow(WfMmsProcessed workFlow) {
        this.workFlow = workFlow;
    }
    
    public MmsLuDmArchive getMmsLuDmArchive() {
        if (mmsLuDmArchive == null) {
            mmsLuDmArchive = new MmsLuDmArchive();
        }
        return mmsLuDmArchive;
    }
    
    public void setMmsLuDmArchive(MmsLuDmArchive mmsLuDmArchive) {
        this.mmsLuDmArchive = mmsLuDmArchive;
    }
    
    public MmsLuDmArchive getMmsLuDmArchiveSelection() {
        if (mmsLuDmArchiveSelection == null) {
            mmsLuDmArchiveSelection = new MmsLuDmArchive();
        }
        return mmsLuDmArchiveSelection;
    }
    
    public void setMmsLuDmArchiveSelection(MmsLuDmArchive mmsLuDmArchiveSelection) {
        this.mmsLuDmArchiveSelection = mmsLuDmArchiveSelection;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setFmsDprEntity(FmsDprOfficeAsset fmsDprEntity) {
        this.fmsDprEntity = fmsDprEntity;
    }
    
    public MmsFixedassetRegstDetail getFixedAssetRegDtlEntity() {
        if (fixedAssetRegDtlEntity == null) {
            fixedAssetRegDtlEntity = new MmsFixedassetRegstDetail();
        }
        return fixedAssetRegDtlEntity;
    }
    
    public void setFixedAssetRegDtlEntity(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        this.fixedAssetRegDtlEntity = fixedAssetRegDtlEntity;
    }
    
    public MmsDisposal getDispEntity() {
        if (dispEntity == null) {
            dispEntity = new MmsDisposal();
        }
        return dispEntity;
    }
    
    public void setDispEntity(MmsDisposal dispEntity) {
        this.dispEntity = dispEntity;
    }
    
    public MmsDisposalItemsDtl getDispCollectionDtlEntity() {
        if (dispCollectionDtlEntity == null) {
            dispCollectionDtlEntity = new MmsDisposalItemsDtl();
        }
        return dispCollectionDtlEntity;
    }
    
    public void setDispCollectionDtlEntity(MmsDisposalItemsDtl dispCollectionDtlEntity) {
        this.dispCollectionDtlEntity = dispCollectionDtlEntity;
    }
    
    public MmsDisposalItems getDispColectionEntity() {
        if (dispColectionEntity == null) {
            dispColectionEntity = new MmsDisposalItems();
        }
        return dispColectionEntity;
    }
    
    public void setDispColectionEntity(MmsDisposalItems dispColectionEntity) {
        this.dispColectionEntity = dispColectionEntity;
    }
    
    public MmsDisposalDtl getDispDtlEntity() {
        if (dispDtlEntity == null) {
            dispDtlEntity = new MmsDisposalDtl();
        }
        
        return dispDtlEntity;
    }
    
    public void setDispDtlEntity(MmsDisposalDtl dispDtlEntity) {
        this.dispDtlEntity = dispDtlEntity;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add to detail And Recreate Datamodel">
    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        WfMmsProcessedDataModel = new ListDataModel(dispEntity.getDisposalList());
    }

    private void recerateDispSerachModel() {
        mmsDispposalSearchInfoDataModel = null;
        mmsDispposalSearchInfoDataModel = new ListDataModel(new ArrayList<>(allDisposeInfoList));
    }

    private void recreateModelDetail() {
        dispposalAddDetailDataModel = null;
        dispposalAddDetailDataModel = new ListDataModel(new ArrayList<>(dispEntity.getMmsDisposalDtlList()));
    }

    public String addDisposalDetail() {
        System.out.println(".......add............");
        if (dispDtlEntity.getDisposalMethod().equalsIgnoreCase("Sell By Tender") || dispDtlEntity.getDisposalMethod().equalsIgnoreCase("Sell To Staff")) {
            System.out.println("...... inside if......");
            BigDecimal bValue = dispDtlEntity.getBookValue();
            BigDecimal result = dispDtlEntity.getSellingPrice();
            BigDecimal resultt = (result).subtract(bValue);
            if (resultt.compareTo(new BigDecimal(1.0)) > 0) {
                System.out.println("........gain........");
                dispDtlEntity.setDispGain("Gain");
                dispDtlEntity.setGainOrLoss(result);
            } else {
                System.out.println(".........loss..........");
                dispDtlEntity.setDispGain("Loss");
                dispDtlEntity.setGainOrLoss(result);
            }
            System.out.println("--------- Gain Or Loss ---------" + dispDtlEntity.getDispGain());

        }
        System.out.println("....out side if..........");
        dispEntity.addDisposalDetail(dispDtlEntity);
        recreateModelDetail();
        clearPopUp();
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clearpopup and clear page">
    private void clearPopUp() {
        dispDtlEntity = null;
        dispColectionEntity = null;
        itemcodeDtl.clear();
        DispNoList.clear();
        fixedAssetRegDtlEntity = null;
        addOrModifyBundle = "Add";
    }

    public String clearPage() {
        storeInfoEntity = null;
        dispColectionEntity = null;
        DispNoList.clear();
        itemcodeDtl.clear();
        dispEntity = null;
        dispDtlEntity = null;
        dispCollectionDtlEntity = null;
        dispposalAddDetailDataModel = null;
        workFlow = null;
        mmsDispposalSearchInfoDataModel = new ArrayDataModel<>();
        storeInfoEntity = null;
        saveorUpdateBundle = "Save";
        selectedValue = "";
        updateStatus = 0;
        selectedValue = null;
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save and Searching">
    public void searchDispInformation1() {
//        System.out.println("in search");
//        dispEntity.setProposedBy(SessionBean.getUserId());
//        System.out.println("processor " + dispEntity.getProposedBy());
//        allDisposeInfoList = disposalInterface.getDisposeListsByParameter(dispEntity);
//        recerateDispSerachModel();
         String str = dispEntity.getDispNo();
        dispEntity.setDispNo(str);
        dispEntity.setProposedBy(SessionBean.getUserId());
//        if (returnEntity.getFarNo() != null) {
            allDisposeInfoList = disposalInterface.getDisposeListsByParameter(dispEntity);
            System.out.println("ALL Return size===== is null" + dispEntity.getDispNo());
            checkerList.clear();
            checkerList = allDisposeInfoList;
            recerateDispSerachModel();
//         System.out.println("-----------Inside Search----------");
//        String str = dispEntity.getDispNo();
//        dispEntity.setProposedBy(SessionBean.getUserId());
//        dispEntity.setDispNo(str);
//        dispEntity.setProposedBy(SessionBean.getUserId());
//        if (dispEntity.getDispNo()!=null) {
//            
//            allDisposeInfoList = disposalInterface.getDisposeListsByParameter(dispEntity);
//            checkerList.clear();
//            checkerList = allDisposeInfoList;
//            recerateDispSerachModel();
//        } else {
//            System.out.println("--------- Inside else ---------");
//            allDisposeInfoList = disposalInterface.getDisposeListsByParameter(dispEntity);
//            System.out.println("=========== allinfo===" + allDisposeInfoList);
//            recerateDispSerachModel();
//        }
//        if (dispEntity.getReferenceNumber() != null) {
//            mmsLuDmArchive = dispEntity.getReferenceNumber();
//            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
//        }
        recreateFileUpload();
    }
    
    public String generateDispNo() {
        MmsDisposal lastInsuranceID = disposalInterface.getLastDisposalId();
        if (lastInsuranceID != null) {
            lastDisposalId = lastInsuranceID.getDispId().toString();
        }
        
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        
        int id = 0;
        if (lastDisposalId.equals("0")) {
            id = 1;
            newDisposalId = "DisposalNo-" + id + "/" + f.format(now);
        } else {
            
            String[] lastInspNos = lastDisposalId.split("-");
            String lastDatesPatern = lastInspNos[0];
            
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newDisposalId = "DisposalNo-" + id + "/" + f.format(now);
        }
        
        return newDisposalId;
    }
    
    public void createNewDisposalInfo() {
        
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchpage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateDisposal = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
            
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateDisposal = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }
        
    }
    
    public void Wfsave() {
        
        workFlow.setDispId(dispEntity);
        workFlowInterface.create(workFlow);
        
    }
    
    public void editDataTable() {
        addOrModifyBundle = "Modify";
        dispDtlEntity = dispposalAddDetailDataModel.getRowData();
        dispCollectionDtlEntity.setTagNo(dispDtlEntity.getTagNo());
        dispCollectionDtlEntity = dispCollectionInterface.findByTag(dispCollectionDtlEntity);
        System.out.println(" tah no shefooo=====" + dispCollectionDtlEntity);
        
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public void saveDisposal() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveDisposal", dataset)) {
                if (updateStatus == 0) {
                    if (dispposalAddDetailDataModel.getRowCount() > 0) {
                        try {
                            fixedAssetRegDtlEntity.setFarDetId(farDtlId);
                            System.out.println("when save");
                            System.out.println("during ====" + fixedAssetRegDtlEntity.getItemStatus());
                            fixedAssetRegDtlEntity.setItemStatus("Disposed");
                            fxAssetRegDtlInterface.edit(fixedAssetRegDtlEntity);
                            dispEntity.setDispNo(newDisposalId);
                            dispEntity.setStoreId(storeInfoEntity);
                            dispEntity.setDisposalItemId(dispColectionEntity);
                            dispEntity.setDpStatus(Constants.PREPARE_VALUE);
                            mmsLuDmArchive.setFileName(fileName);
                            mmsLuDmArchive.setFileType(docFormat);
                            mmsLuDmArchive.setUploadFile(byteFile);
                            mmsLuDmArchiveBeanLocal.saveFile(mmsLuDmArchive);
                            dispEntity.setReferenceNumber(mmsLuDmArchive);
                            System.out.println("refno");
                            dispEntity.setProposedBy(workFlow.getProcessedBy());
                            dispEntity.setProcessedOn(workFlow.getProcessedOn());
                            dispEntity.getDisposalList().add(workFlow);
                            disposalInterface.create(dispEntity);
                            JsfUtil.addSuccessMessage(" A New Fixed Asset Disposal is Saved! ");
                            clearPage();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JsfUtil.addFatalMessage("Something Error Occured on Data Saveing");
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Put atleast One Record on the Detail Form .");
                        
                    }
                } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                    try {
                        System.out.println("====edit quantity===" + dispDtlEntity.getQuantity());
                        System.out.println("====edit===" + dispDtlEntity.getDisposalMethod());
                        dispEntity.setProposedBy(workFlow.getProcessedBy()); 
                        dispEntity.setProcessedOn(workFlow.getProcessedOn());  
                        dispEntity.setDpStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(dispEntity.getDpStatus());
                        dispEntity.setDisposalItemId(dispColectionEntity);
                        System.out.println("====edit===" + dispDtlEntity.getQuantity());
                        System.out.println("====edit===" + dispDtlEntity.getDisposalMethod());
                        System.out.println("====edit===" + dispDtlEntity.getDispGain());
                        disposalInterface.edit(dispEntity);
                        Wfsave();
                        dispEntity.getMmsDisposalDtlList().add(dispDtlEntity);
                        JsfUtil.addSuccessMessage("Fixed Asset Disposal has been Updated");
                        clearPage();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                        clearPage();
                    }
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        dispEntity.setDpStatus(Constants.APPROVE_VALUE);
                        workFlow.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        dispEntity.setDpStatus(Constants.CHECK_APPROVE_VALUE);
                        workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        dispEntity.setDpStatus(Constants.APPROVE_REJECT_VALUE);
                        workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        dispEntity.setDpStatus(Constants.CHECK_REJECT_VALUE);
                        workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    workFlow.setDispId(dispEntity);
                    workFlowInterface.create(workFlow);
                    disposalInterface.edit(dispEntity);
                    mmsDisposalList.remove(dispEntity);
                    clearPage();
                    JsfUtil.addSuccessMessage("Disposal Data Updated");
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
        
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Handler">
    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public List<MmsStoreInformation> getStoreInfoList() {
        storeInfoList = storeInfoInterface.findAllStoreInfo();

        return storeInfoList;
    }

    public void setStoreInfoList(List<MmsStoreInformation> storeInfoList) {
        this.storeInfoList = storeInfoList;
    }

    public List<MmsDisposalItems> getDispNoList() {
        DispNoList = dispCollectionInterface.findAllbyApproveStatus(Constants.APPROVE_VALUE);
        return DispNoList;
    }

    public void setDispNoList(List<MmsDisposalItems> DispNoList) {
        this.DispNoList = DispNoList;
    }

    public List<MmsDisposalItemsDtl> getItemcodeDtl() {

        return itemcodeDtl;
    }

    public void setItemcodeDtl(List<MmsDisposalItemsDtl> itemcodeDtl) {
        this.itemcodeDtl = itemcodeDtl;
    }

    public List<MmsDisposal> getMmsDisposalColumnNameList() {
        if (mmsDisposalColumnNameList==null) {
            mmsDisposalColumnNameList = new ArrayList<>();
            mmsDisposalColumnNameList = disposalInterface.getMmsDisposeColumnNameList();
            
        }
        return mmsDisposalColumnNameList;
    }

    public void setMmsDisposalColumnNameList(List<MmsDisposal> mmsDisposalColumnNameList) {
        this.mmsDisposalColumnNameList = mmsDisposalColumnNameList;
    }
    
    
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            int storeIds = Integer.valueOf(event.getNewValue().toString());
            storeInfoEntity.setStoreId(storeIds);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);

            dispEntity.setStoreId(storeInfoEntity);

        }
    }
 /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            dispEntity.setColumnName(event.getNewValue().toString());
            dispEntity.setColumnValue(null);
        }
    }
    public void handleSelectDispNo(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            dispColectionEntity.setDisposalNo(event.getNewValue().toString());
            dispEntity.setDispCollection(event.getNewValue().toString());

        }
    }
/*This method is used to column Name Change Event
     */

//    public void columnNameChangeEvent(ValueChangeEvent event) {
//        if (event.getNewValue() != null) {
//            dispEntity.setColumnName(event.getNewValue().toString());
//            dispEntity.setColumnValue(null);
//        }
//    }
    public void changeEventColumnName(ValueChangeEvent changeEvent) {
         if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            dispEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
        
        }
    }
    public void handleSelectDisposalCollection(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            dispColectionEntity = (MmsDisposalItems) event.getNewValue();
            itemcodeDtl = dispCollectionInterface.getTagNoLists(dispColectionEntity);
        }
    }
    public void handleSelectItemCode(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            dispCollectionDtlEntity = (MmsDisposalItemsDtl) event.getNewValue();
            dispDtlEntity.setTagNo(event.getNewValue().toString());
            dispDtlEntity.setItemName(dispCollectionDtlEntity.getItemName());
            System.out.println("=====net book====" + dispCollectionDtlEntity.getBookValue());
            System.out.println("status   " + dispCollectionDtlEntity.getItemStatus());
            farDtlId = fixedAssetRegDtlEntity.getFarDetId();
            List<MmsFixedassetRegstDetail> TransNo = null;
            TransNo = fxAssetRegDtlInterface.findByTagNo2(fixedAssetRegDtlEntity);
            if (TransNo.isEmpty()) {
            } else {
                int id = TransNo.get(0).getFarDetId();
                fixedAssetRegDtlEntity.setFarDetId(id);              
                for (int i = 0; i < TransNo.size(); i++) {
                    int sizeDepr = TransNo.get(i).getFmsDprOfficeAssetList().size();
                    if (sizeDepr == 0) {                     
                    } else {
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprOfficeAssetList().get(j).getStatus();
                            if (stat == 1) {

                                BigDecimal BookValue = TransNo.get(i).getFmsDprOfficeAssetList().get(j).getNetBookValue();

                                dispDtlEntity.setBookValue(BookValue);

                            }
                        }
                    }

                }
            }

        }
    }

    public void handleSelectDisposal(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String disMethod = event.getNewValue().toString();
            if (disMethod.equalsIgnoreCase("Sell By Tender") || (disMethod.equalsIgnoreCase("Sell To Staff"))) {
                disposalmethod = "false";
            } else {
                disposalmethod = "true";
            }

        }
    }
//</editor-fold>

     //<editor-fold defaultstate="collapsed" desc="RowSelection and Update">
    public void rowSelect(SelectEvent event) {
        dispEntity = (MmsDisposal) event.getObject();
        dispEntity.setDispId(dispEntity.getDispId());
        dispEntity = disposalInterface.getSelectedRequest(dispEntity.getDispId());
        populateUI();
    }
    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateDisposal = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = mmsDispposalSearchInfoDataModel.getRowIndex();
        dispEntity = checkerList.get(rowIndex);
        storeInfoEntity.setStoreId(dispEntity.getStoreId().getStoreId());
        recreateModelDetail();
    }
    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                dispEntity = (MmsDisposal) event.getNewValue();

                populateUI();
            }
        } catch (Exception e) {

            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    public void populateUI() {
        dispColectionEntity.setDisposalNo(dispEntity.getDispCollection());
        System.out.println("=======disposal No===" + dispEntity.getDispCollection());
        System.out.println("=======disposal No2===" + dispColectionEntity.getDisposalNo());
        renderPnlCreateDisposal = true;
        renderPnlManPage = false;
        renderpnlToSearchpage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        setIsRenderedIconWorkflow(true);
        recreateModelDetail();
        recreateWfDataModel();
        storeInfoEntity = dispEntity.getStoreId();
        dispColectionEntity = dispEntity.getDisposalItemId();
        System.out.println("dispColectionEntity========" + dispColectionEntity);
        if (workflow.isPrepareStatus()) {
            workFlow.setProcessedOn(dispEntity.getProcessedOn());
        }
        if (dispEntity.getReferenceNumber() != null) {
            mmsLuDmArchive = dispEntity.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
        }
        System.out.println("quantiiiiiiiiiii====" + dispDtlEntity.getQuantity());
        recreateFileUpload();
    }

    public void goBackToSeaerchPageBtnAction() {
        renderPnlCreateDisposal = false;
        renderPnlManPage = true;
        renderpnlToSearchpage = false;
    }

    public void recreateFileUpload() {
        mmsLuDmArchivesDModel = null;
        mmsLuDmArchivesDModel = new ListDataModel(mmsLuDmArchiveList);
    }
//</editor-fold>

     //<editor-fold defaultstate="collapsed" desc="FileUpload">
    String docFormat;
    String fileName;
    byte[] byteFile;

    public void uploadHandling(FileUploadEvent event) {
        InputStream inputStreamFile = null;
        docFormat = event.getFile().getFileName().split("\\.")[1];
        fileName = event.getFile().getFileName().split("\\.")[0];
        try {
            inputStreamFile = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStreamFile);
            if (byteFile != null) {
                System.out.println("byte File---" + byteFile);
                System.out.println("File Name " + fileName + " with Format " + docFormat + " is Succesfully Uploaded");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRowSelectFileUploaded(SelectEvent ev) {
        System.out.println("when File Row Selected");
        mmsLuDmArchiveSelection = (MmsLuDmArchive) ev.getObject();
        System.out.println("File Name===" + mmsLuDmArchiveSelection.getFileName());
        isFileSelected = true;
    }

    public StreamedContent getDownload() {
        System.out.println("Dowlaod Here");
        if (isFileSelected == true) {
            System.out.println("yes u selected");
            download = dataUpload.getMmsFileRefNumber(mmsLuDmArchive);
        }
        return download;
    }

    public void setDownload(StreamedContent download) {
        this.download = download;
    }

    public void getDocValue() {
        DMdocumentModel = dataUpload.selectListOfFileByDocId(documentId);
        System.out.println("No of your Doc is " + DMdocumentModel.getRowCount());
    }
//</editor-fold>
    }
