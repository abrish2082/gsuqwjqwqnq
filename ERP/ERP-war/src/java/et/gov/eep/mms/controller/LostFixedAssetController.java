
package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.businessLogic.MmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLostFixedAssetBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsLostFixedAssetDetailFacade;
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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author Minab Sahilu
 */
@Named(value = "lostFixedAssetController")
@ViewScoped
public class LostFixedAssetController implements Serializable {

    /**
     * Creates a new instance of LostFixedAssetController
     */
    public LostFixedAssetController() {
    }

    //<editor-fold defaultstate="collapsed" desc="Inject Ejbs">
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrdepartmentInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsLostFixedAssetBeanLocal lostFxAssetInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fxAssetRegDtlInterface;
    @EJB
    private MmsFixedAssetRegistrationBeanLocal registrationBeanLocal;
    @EJB
    private MmsLostFixedAssetDetailFacade lostDetailFacade;
    @EJB
    MmsLuDmArchiveBeanLocal mmsLuDmArchiveBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entity">
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private MmsLostFixedAsset lostFxAssetEntity;
    @Inject
    private MmsLostFixedAssetDetail lostFxAssetDetailEntity;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntity;
    @Inject
    private FmsDprOfficeAsset fmsDprEntity;
    @Inject
    DataUpload dataUpload;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    MmsLuDmArchive mmsLuDmArchive;
    @Inject
    MmsLuDmArchive mmsLuDmArchiveSelection;
    private MmsLostFixedAssetDetail selectedLostfixedAssetDetail;
    ColumnNameResolver columnNameResolver;
    //    @EJB
//    private MmsFixedassetRegstration fixedassetRegstration;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    List<FmsDprOfficeAsset> bookValueList = new ArrayList<>();
    List<Integer> documentId;
    private DataModel<MmsLostFixedAssetDetail> lostFxAssetAddDetailDataModel;
    private DataModel<MmsLostFixedAsset> mmsLostItemSearchInfoDataModel;
    DataModel<DocumentModel> DMdocumentModel;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private List<MmsLostFixedAsset> mmsLostList;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    private static List<HrDepartments> araListe;
    List<MmsStoreInformation> storeInfoLst = new ArrayList<>();
    Integer farDtlId;
    List<MmsFixedassetRegstDetail> fixedAssetDtlList = new ArrayList<>();
    private List<MmsLostFixedAssetDetail> lostItemInfoList;
    String newLostItemId;
    String lastLostItemId = "0";
    private String addOrModifyBundle="Add";
    List<MmsLostFixedAsset> allLostItemInfoList;
    DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel;
    List<MmsLuDmArchive> mmsLuDmArchiveList = new ArrayList<>();
    private List<MmsLostFixedAsset> lostFixedAssetColumnNameLists;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare Fields">
    boolean isRenderCreate;
    private String selectedValue = "";
    private boolean renderDecision = false;
    private MmsLostFixedAsset hpSelect;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateLostItem = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String status = "Prepared";
    Set<String> checkMaterialCode = new HashSet<>();
     private boolean isRenderedIconNitification = false;
    private String userName;
    int refNumber;
    private StreamedContent streamedContent;
    boolean renderForGraphics = true;
    boolean renderForGraphicsDb = false;
    private boolean isRenderedIconWorkflow = false;
    private DocumentModel documentModelSelection;
    private boolean isCheckedToStore = false;
    private boolean isFileSelected = false;
    private StreamedContent download;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">

    @PostConstruct
    public void init() {

        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                mmsLostList = lostFxAssetInterface.findLostListByWfStatus(Constants.CHECK_APPROVE_VALUE);
            } else if (workflow.isCheckStatus()) {
                mmsLostList = lostFxAssetInterface.findLostListForCheckerByWfStatus(Constants.PREPARE_VALUE, Constants.APPROVE_REJECT_VALUE);
            }
            isRenderCreate = false;
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsLostList = lostFxAssetInterface.findLostListByWfStatus(Constants.CHECK_REJECT_VALUE);
            renderDecision = false;
            isRenderCreate = true;
            if (mmsLostList.size() > 0) {
                isRenderedIconNitification = true;
            } else {
                isRenderedIconNitification = false;
            }

        }
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        allDepartmentsList = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        ColumnNameResolverList = lostFxAssetInterface.getColumnNameList();
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of Variables">
    public String getSelectedValue() {
        return selectedValue;
    }
    
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }
    
    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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
     public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

     /**
     *
     * @return
     */
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    /**
     *
     * @param saveorUpdateBundle
     */
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }
    
    /**
     *
     * @return
     */
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }
      public boolean isRenderForGraphics() {
        return renderForGraphics;
    }

    public void setRenderForGraphics(boolean renderForGraphics) {
        this.renderForGraphics = renderForGraphics;
    }

    public boolean isRenderForGraphicsDb() {
        return renderForGraphicsDb;
    }

    public void setRenderForGraphicsDb(boolean renderForGraphicsDb) {
        this.renderForGraphicsDb = renderForGraphicsDb;
    }

    /**
     *
     * @param createOrSearchBundle
     */
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
     /**
     *
     * @return
     */
    public String getIcone() {
        return icone;
    }

    /**
     *
     * @param icone
     */
    public void setIcone(String icone) {
        this.icone = icone;
    }

    /**
     *
     * @return
     */
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    /**
     *
     * @param renderPnlManPage
     */
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    /**
     *
     * @return
     */
    public boolean isRenderPnlCreateLostItem() {
        return renderPnlCreateLostItem;
    }
    /**
     *
     * @param renderPnlCreateLostItem
     */
    public void setRenderPnlCreateLostItem(boolean renderPnlCreateLostItem) {
        this.renderPnlCreateLostItem = renderPnlCreateLostItem;
    }
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

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of Lists and models">
    public List<MmsStoreInformation> getStoreInfoLst() {
        storeInfoLst = storeInfoInterface.findAllStoreInfo();
        return storeInfoLst;
    }
    
    public void setStoreInfoLst(List<MmsStoreInformation> storeInfoLst) {
        this.storeInfoLst = storeInfoLst;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public List<MmsFixedassetRegstDetail> getFixedAssetDtlList() {
        fixedAssetDtlList = fxAssetRegDtlInterface.findAllTagNo();
        return fixedAssetDtlList;
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
    
    public void setFixedAssetDtlList(List<MmsFixedassetRegstDetail> fixedAssetDtlList) {
        this.fixedAssetDtlList = fixedAssetDtlList;
    }
     public DataModel<MmsLuDmArchive> getMmsLuDmArchivesDModel() {
        return mmsLuDmArchivesDModel;
    }

    public void setMmsLuDmArchivesDModel(DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel) {
        this.mmsLuDmArchivesDModel = mmsLuDmArchivesDModel;
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
     public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }

    public List<MmsLostFixedAsset> getMmsLostList() {
        return mmsLostList;
    }

    public void setMmsLostList(List<MmsLostFixedAsset> mmsLostList) {
        this.mmsLostList = mmsLostList;
    }
     public List<FmsDprOfficeAsset> getBookValueList() {
        return bookValueList;
    }

    public void setBookValueList(List<FmsDprOfficeAsset> bookValueList) {
        this.bookValueList = bookValueList;
    }
     /**
     *
     * @return
     */
    public DataModel<MmsLostFixedAssetDetail> getLostFxAssetAddDetailDataModel() {
        if (lostFxAssetAddDetailDataModel == null) {
            lostFxAssetAddDetailDataModel = new ListDataModel<>();
        }
        return lostFxAssetAddDetailDataModel;
    }

    /**
     *
     * @param lostFxAssetAddDetailDataModel
     */
    public void setLostFxAssetAddDetailDataModel(DataModel<MmsLostFixedAssetDetail> lostFxAssetAddDetailDataModel) {
        this.lostFxAssetAddDetailDataModel = lostFxAssetAddDetailDataModel;
    }
    /**
     *
     * @return
     */
    public List<HrDepartments> getDepartmentNames() {

        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();

        return DepartmentNames;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsLostFixedAsset> getMmsLostItemSearchInfoDtlDataModel() {
        if (mmsLostItemSearchInfoDataModel == null) {
            mmsLostItemSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsLostItemSearchInfoDataModel;
    }

    /**
     *
     * @param mmsLostItemSearchInfoDtlDataModel
     */
    public void setMmsLostItemSearchInfoDtlDataModel(DataModel<MmsLostFixedAsset> mmsLostItemSearchInfoDtlDataModel) {
        this.mmsLostItemSearchInfoDataModel = mmsLostItemSearchInfoDtlDataModel;
    }

    public DataModel<DocumentModel> getDMdocumentModel() {
        return DMdocumentModel;
    }

    public void setDMdocumentModel(DataModel<DocumentModel> DMdocumentModel) {
        this.DMdocumentModel = DMdocumentModel;
    }

    public DocumentModel getDocumentModelSelection() {
        return documentModelSelection;
    }

    public void setDocumentModelSelection(DocumentModel documentModelSelection) {
        this.documentModelSelection = documentModelSelection;
    }

    public List<MmsLostFixedAsset> getLostFixedAssetColumnNameLists() {
        if (lostFixedAssetColumnNameLists == null) {
            lostFixedAssetColumnNameLists = new ArrayList<>();
            lostFixedAssetColumnNameLists = lostFxAssetInterface.getLostFixedAssetColumnNameLists();
        }
        return lostFixedAssetColumnNameLists;
    }

    public void setLostFixedAssetColumnNameLists(List<MmsLostFixedAsset> lostFixedAssetColumnNameLists) {
        this.lostFixedAssetColumnNameLists = lostFixedAssetColumnNameLists;
    }




//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of Objects">
    public MmsFixedassetRegstDetail getFixedAssetRegDtlEntity() {
        if (fixedAssetRegDtlEntity == null) {
            fixedAssetRegDtlEntity = new MmsFixedassetRegstDetail();
        }
        return fixedAssetRegDtlEntity;
    }
    
    public void setFixedAssetRegDtlEntity(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        this.fixedAssetRegDtlEntity = fixedAssetRegDtlEntity;
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

    public MmsLostFixedAssetDetail getSelectedLostfixedAssetDetail() {
        return selectedLostfixedAssetDetail;
    }

    public void setSelectedLostfixedAssetDetail(MmsLostFixedAssetDetail selectedLostfixedAssetDetail) {
        this.selectedLostfixedAssetDetail = selectedLostfixedAssetDetail;
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
   
    public FmsDprOfficeAsset getFmsDprEntity() {
        if (fmsDprEntity == null) {
            fmsDprEntity = new FmsDprOfficeAsset();
        }
        return fmsDprEntity;
    }

    public void setFmsDprEntity(FmsDprOfficeAsset fmsDprEntity) {
        this.fmsDprEntity = fmsDprEntity;
    }
    public MmsLostFixedAsset getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsLostFixedAsset hpSelect) {
        this.hpSelect = hpSelect;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }
    
    public WfMmsProcessed getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WfMmsProcessed();
        }
        return workFlow;
    }

    public void setWorkFlow(WfMmsProcessed workFlow) {
        this.workFlow = workFlow;
    }
     public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
     /**
     *
     * @param storeInfoEntity
     */
    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }
    /**
     *
     * @return
     */
    public HrDepartments getHrDepartmentsEntity() {
        if (hrDepartmentsEntity == null) {
            hrDepartmentsEntity = new HrDepartments();
        }
        return hrDepartmentsEntity;
    }

    /**
     *
     * @param hrDepartmentsEntity
     */
    public void setHrDepartmentsEntity(HrDepartments hrDepartmentsEntity) {
        this.hrDepartmentsEntity = hrDepartmentsEntity;
    }

    /**
     *
     * @return
     */
    public MmsLostFixedAsset getLostFxAssetEntity() {
        if (lostFxAssetEntity == null) {
            lostFxAssetEntity = new MmsLostFixedAsset();
        }
        return lostFxAssetEntity;
    }

    /**
     *
     * @param lostFxAssetEntity
     */
    public void setLostFxAssetEntity(MmsLostFixedAsset lostFxAssetEntity) {
        this.lostFxAssetEntity = lostFxAssetEntity;
    }

    /**
     *
     * @return
     */
    public MmsLostFixedAssetDetail getLostFxAssetDetailEntity() {
        if (lostFxAssetDetailEntity == null) {
            lostFxAssetDetailEntity = new MmsLostFixedAssetDetail();
        }
        return lostFxAssetDetailEntity;
    }

    /**
     *
     * @param lostFxAssetDetailEntity
     */
    public void setLostFxAssetDetailEntity(MmsLostFixedAssetDetail lostFxAssetDetailEntity) {
        this.lostFxAssetDetailEntity = lostFxAssetDetailEntity;
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

    



//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add to Detail and Recreate">
    /**
     *
     * @return
     */
    public String addlostItemDetail() {

        lostFxAssetEntity.addlostItemDetail(lostFxAssetDetailEntity);
        recreateModelDetail();
        clearPopUp();
        return null;
    }

    private void recreateModelDetail() {
        lostFxAssetAddDetailDataModel = null;
        lostFxAssetAddDetailDataModel = new ListDataModel(new ArrayList<>(lostFxAssetEntity.getMmsLostFixedAssetDetailList()));
        fixedAssetRegDtlEntity = null;
    }

    private void recerateLostItemSerachModel() {
        System.out.println("------ Inside Recreate ---------");
        mmsLostItemSearchInfoDataModel = null;
        mmsLostItemSearchInfoDataModel = new ListDataModel(new ArrayList<>(allLostItemInfoList));
        System.out.println("---- List Size ------" + allLostItemInfoList.size());
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        WfMmsProcessedDataModel = new ListDataModel(lostFxAssetEntity.getFaLostList());
    }

    public String addtoLostItemInfoDetail() {

        if (checkMaterialCode.contains(lostFxAssetDetailEntity.getTagNo())) {
            JsfUtil.addSuccessMessage("Tag No: Value is Duplicated");
            return null;
        } else {
//            lostFxAssetDetailEntity.setBookValue(fmsDprEntity.getNetBookValue());
            lostFxAssetEntity.addlostItemDetail(lostFxAssetDetailEntity);
            checkMaterialCode.add(lostFxAssetDetailEntity.getTagNo());
        }

        recreateModelDetail();
        clearPopUp();
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save and Searching">
    /**
     *
     * @return
     */
    public void Wfsave() {
        
        workFlow.setLostItemId(lostFxAssetEntity);
        workFlowInterface.create(workFlow);
        
    }
    
    public void editDataTable() {
        addOrModifyBundle = "Modify";
        lostFxAssetDetailEntity = lostFxAssetAddDetailDataModel.getRowData();
        fixedAssetRegDtlEntity.setTagNo(lostFxAssetDetailEntity.getTagNo());
        fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByTag(fixedAssetRegDtlEntity);
        checkMaterialCode.remove(lostFxAssetDetailEntity.getTagNo());
    }
    
    public void onRowPopulate(SelectEvent event) {
        lostFxAssetDetailEntity = (MmsLostFixedAssetDetail) event.getObject();
        fixedAssetRegDtlEntity.setTagNo(lostFxAssetDetailEntity.getTagNo());
    }
    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public String generateLostItemNo() {
        if (updateStatus == 1) {
            newLostItemId = lostFxAssetEntity.getLostItemNo();
        } else {
            MmsLostFixedAsset lastLostItemID = lostFxAssetInterface.getLastLostItemId();
            if (lastLostItemID != null) {
                lastLostItemId = lastLostItemID.getLostItemId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            
            int id = 0;
            if (lastLostItemId.equals("0")) {
                id = 1;
                newLostItemId = "LostItemNo-" + id + "/" + f.format(now);
            } else {
                
                String[] lastInspNos = lastLostItemId.split("-");
                String lastDatesPatern = lastInspNos[0];
                
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                id = Integer.parseInt(lastDatesPaterns[0]);
                id = id + 1;
                newLostItemId = "LostItemNo-" + id + "/" + f.format(now);
            }
        }
        return newLostItemId;
    }
    
    /**
     *
     * @param Itemno
     * @return
     */
    public ArrayList<MmsLostFixedAsset> searchByLostItemNo(String Itemno) {
        ArrayList<MmsLostFixedAsset> LostItemno = null;
        lostFxAssetEntity.setLostItemNo(Itemno);
        LostItemno = lostFxAssetInterface.searchByLostItemNo(lostFxAssetEntity);
        
        saveorUpdateBundle = "Update";
        return LostItemno;
    }
    
    /**
     *
     * @param event
     */
    public void getByLostItemNo(SelectEvent event) {
        
        lostFxAssetEntity.setLostItemNo(event.getObject().toString());
        lostFxAssetEntity = lostFxAssetInterface.getLostInfoByItemNo(lostFxAssetEntity);
        hrDepartmentsEntity.setDepId(lostFxAssetEntity.getDepartment().getDepId());
        //recreateModelDetail();
        recerateLostItemSerachModel();
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        
    }
    
    public void searchLostItemInformation1() {
        
        System.out.println("-----------Inside Search----------");
        String str = lostFxAssetEntity.getLostItemNo();
        lostFxAssetEntity.setLostItemNo(str);
        lostFxAssetEntity.setEmployeeName(SessionBean.getUserId());
        if (lostFxAssetEntity.getLostItemNo()!=null) {
            
            System.out.println("-------- Inside If ------- ");
            allLostItemInfoList = lostFxAssetInterface.getLostListsByParameter(lostFxAssetEntity);
            //   hrDepartmentsEntity.setDepId(lostFxAssetEntity.getDepartment().getDepId());
            System.out.println("-------LostItemInfoList -----" + allLostItemInfoList.size());
            recerateLostItemSerachModel();
            
        } else {
            System.out.println("--------- Inside else ---------");
            allLostItemInfoList = lostFxAssetInterface.getLostListsByParameter(lostFxAssetEntity);
//            allLostItemInfoList = lostFxAssetInterface.searchAllLostInfoByPreparerId(lostFxAssetEntity.getEmployeeName());
            System.out.println("=========== allinfo===" + allLostItemInfoList);
            recerateLostItemSerachModel();
        }
        if (lostFxAssetEntity.getReferenceNumber() != null) {
            mmsLuDmArchive = lostFxAssetEntity.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
//            refNumber = lostFxAssetEntity.getReferenceNumber();
//            documentId.add(refNumber);
//            getDocValue();
        }
        recerateLostItemSerachModel();
        
        recreateFileUpload();
    }
    
    /**
     *
     */
    public void updateLostFixedAssetDetail() {
        lostFxAssetDetailEntity = getLostFxAssetAddDetailDataModel().getRowData();
        lostFxAssetEntity = lostFxAssetDetailEntity.getLostItemId();
        
    }
    
    /**
     *
     */
    public void createNewLostItemInfo() {
        
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateLostItem = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
            
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateLostItem = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }
        
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public void saveLostFA() {
        System.out.println("when save");
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveLostFA", dataset)) {
                if (updateStatus == 0) {
                    System.out.println("1");
                    if (lostFxAssetAddDetailDataModel.getRowCount() > 0) {
                        System.out.println("2");
                        try {
                            fixedAssetRegDtlEntity.setFarDetId(farDtlId);
                            fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByDetailId(fixedAssetRegDtlEntity);
                            fixedAssetRegDtlEntity.setItemStatus("Lost");
                            fxAssetRegDtlInterface.edit(fixedAssetRegDtlEntity);
                            lostFxAssetEntity.setLostItemNo(newLostItemId);
                            System.out.println("3");
                            System.out.println("4");
                            lostFxAssetEntity.setLostStatus(Constants.PREPARE_VALUE);
                            workFlow.setDecision(lostFxAssetEntity.getLostStatus());
                            System.out.println("5");
                            lostFxAssetEntity.setEmployeeName(workFlow.getProcessedBy());
                            System.out.println("6");
                            lostFxAssetEntity.setApproved2Date(workFlow.getProcessedOn()); // Processed on
                            workFlow.setLostItemId(lostFxAssetEntity);
                            lostFxAssetEntity.getMmsLostFixedAssetDetailList().add(lostFxAssetDetailEntity);
                            lostFxAssetEntity.getFaLostList().add(workFlow);
                            System.out.println("7");
                            lostFxAssetInterface.create(lostFxAssetEntity);
                            JsfUtil.addSuccessMessage(" A New Lost Item Information is Saved ! ");
                            clearPage();
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Something Error Occured on Saving the Data ");
                            
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Put atleast One Record on the Detail Form .");
                        
                    }
                    
                } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                    
                    try {
                        lostFxAssetEntity.setLostStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(lostFxAssetEntity.getLostStatus());
                        lostFxAssetEntity.setEmployeeName(SessionBean.getUserId()); // processed by
                        lostFxAssetEntity.setApproved2Date(workFlow.getProcessedOn()); // Processed on
                        lostFxAssetInterface.edit(lostFxAssetEntity);
                        lostFxAssetEntity.getMmsLostFixedAssetDetailList().add(lostFxAssetDetailEntity);
                        Wfsave();
                        clearPage();
                        JsfUtil.addSuccessMessage(" Lost Fixed Asset Information has been Updated");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                        
                    }
                    
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        lostFxAssetEntity.setLostStatus(Constants.APPROVE_VALUE);
                        workFlow.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        lostFxAssetEntity.setLostStatus(Constants.CHECK_APPROVE_VALUE);
                        workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        lostFxAssetEntity.setLostStatus(Constants.APPROVE_REJECT_VALUE);
                        workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        lostFxAssetEntity.setLostStatus(Constants.CHECK_REJECT_VALUE);
                        workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    workFlow.setLostItemId(lostFxAssetEntity);
                    workFlowInterface.create(workFlow);
                    lostFxAssetInterface.edit(lostFxAssetEntity);
                    mmsLostList.remove(lostFxAssetEntity);
                    clearPage();
                    JsfUtil.addSuccessMessage("Lost Data Modified");
                }
                
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="clearpage and clearpopup">
    public String clearPage() {
        storeInfoEntity = null;
        lostFxAssetDetailEntity = null;
        lostFxAssetEntity = null;
        lostFxAssetAddDetailDataModel = null;
        mmsLostItemSearchInfoDataModel = null;
        hrDepartmentsEntity = null;
        fixedAssetRegDtlEntity = null;
        fixedAssetDtlList.clear();
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        workFlow = null;
        return null;
    }

    private void clearPopUp() {
        lostFxAssetDetailEntity = null;
        fixedAssetRegDtlEntity = null;
        addOrModifyBundle = "Add";
        fixedAssetDtlList.clear();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Handler">
    /**
     *
     * @return
     */
    /**
     *
     * @param event
     */
    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
//
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(depIds);
            lostFxAssetEntity.setDepartment(hrDepartmentsEntity);
        }
    }
   public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            System.out.println("changeEvent.getNewValue().toString()==="+changeEvent.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            lostFxAssetEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase()+":";
    }
    public void handleTagNo(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            fixedAssetRegDtlEntity = (MmsFixedassetRegstDetail) event.getNewValue();
            lostFxAssetDetailEntity.setTagNo(fixedAssetRegDtlEntity.getTagNo());
            lostFxAssetDetailEntity.setItemName(fixedAssetRegDtlEntity.getItemName());
            System.out.println("Item Name" + fixedAssetRegDtlEntity.getItemName());
            lostFxAssetDetailEntity.setDescription(fixedAssetRegDtlEntity.getDescription());
            lostFxAssetDetailEntity.setAccountCode(fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            farDtlId = fixedAssetRegDtlEntity.getFarDetId();
            System.out.println("------ Item Name --------" + lostFxAssetDetailEntity.getItemName());
            lostFxAssetDetailEntity.setBookValue(fmsDprEntity.getNetBookValue());
            System.out.println("====book value" + lostFxAssetDetailEntity.getBookValue());
            bookValueList = registrationBeanLocal.findBookvalue(fixedAssetRegDtlEntity.getTagNo()); 
            System.out.println("====book value=========" + bookValueList);
            List<MmsFixedassetRegstDetail> TransNo = new ArrayList<>(); 
            TransNo = fxAssetRegDtlInterface.findByTagNo2(fixedAssetRegDtlEntity);
            if (TransNo.isEmpty()) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {
                int id = TransNo.get(0).getFarDetId();
                fixedAssetRegDtlEntity.setFarDetId(id);
                fmsDprEntity.setOfAssetId(fixedAssetRegDtlEntity);
                for (int i = 0; i < TransNo.size(); i++) {
                    int sizeDepr = TransNo.get(i).getFmsDprOfficeAssetList().size();
                    if (sizeDepr == 0) {
                       
                    } else {
                        for (int j = 0; j < sizeDepr; j++) {

                            int stat = TransNo.get(i).getFmsDprOfficeAssetList().get(j).getStatus();
                            if (stat == 1) {

                                BigDecimal BookValue = TransNo.get(i).getFmsDprOfficeAssetList().get(j).getNetBookValue();

                                lostFxAssetDetailEntity.setBookValue(BookValue);

                            }
                        }
                    }
                }
            }
        }
    }

    public void handleSelectStoreName1(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int storeIds = Integer.valueOf(event.getNewValue().toString());
            storeInfoEntity.setStoreId(storeIds);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);
            lostFxAssetEntity.setStoreId(storeInfoEntity);

        }
    }
 
    public void getAcceptedDate(SelectEvent event) {
        System.out.println("Listiner=========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            lostFxAssetEntity.setAcceptedDate(arrival);         
            System.out.println("=======Dateeeee=========" + lostFxAssetEntity.getAcceptedDate());
        }
    }
    public void getApprovedDate(SelectEvent event) {
        System.out.println("Listiner=========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            lostFxAssetEntity.setApprovedDate(arrival);           
            System.out.println("=======Dateeeee=========" + lostFxAssetEntity.getApprovedDate());
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RowSelection and update">
    public void rowSelect(SelectEvent event) {
        lostFxAssetEntity = (MmsLostFixedAsset) event.getObject();
        lostFxAssetEntity.setLostItemId(lostFxAssetEntity.getLostItemId());
        lostFxAssetEntity = lostFxAssetInterface.getSelectedRequest(lostFxAssetEntity.getLostItemId());
        populateUI();
    }

    public void getRegistrationDate(SelectEvent event) {
        System.out.println("Listiner=========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            lostFxAssetEntity.setRegistrationDate(arrival);         
            System.out.println("=======Dateeeee=========" + lostFxAssetEntity.getRegistrationDate());
        }
    }

    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                lostFxAssetEntity = (MmsLostFixedAsset) event.getNewValue();

                populateUI();
            }
        } catch (Exception e) {

            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    /**
     *
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateLostItem = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = mmsLostItemSearchInfoDataModel.getRowIndex();
        lostFxAssetEntity = allLostItemInfoList.get(rowIndex);
        storeInfoEntity = lostFxAssetEntity.getStoreId();
        hrDepartmentsEntity = lostFxAssetEntity.getDepartment();
        lostFxAssetEntity.getStatus();
        recreateModelDetail();
    }
    public void populateUI() {
        hrDepartmentsEntity = lostFxAssetEntity.getDepartment();      
        renderPnlCreateLostItem = true;
        renderPnlManPage = false;
        renderpnlToSearchPage=true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        setIsRenderedIconWorkflow(true);
        recreateModelDetail();
        recreateWfDataModel();
        storeInfoEntity = lostFxAssetEntity.getStoreId();
        lostFxAssetEntity.setRegionName(lostFxAssetEntity.getRegionName());
        System.out.println("=====regionName=====" + lostFxAssetEntity.getRegionName());
        hrDepartmentsEntity = lostFxAssetEntity.getDepartment();
        System.out.println("====department===" + lostFxAssetEntity.getDepartment());
        if (workflow.isPrepareStatus()) {
            workFlow.setProcessedOn(lostFxAssetEntity.getApproved2Date());
        }
        if (lostFxAssetEntity.getReferenceNumber() != null) {
            mmsLuDmArchive = lostFxAssetEntity.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
        }

        recreateFileUpload();
    }

    public void recreateFileUpload() {
        mmsLuDmArchivesDModel = null;
        mmsLuDmArchivesDModel = new ListDataModel<MmsLuDmArchive>();
    }

    public void goBackToSearchpageBtnAction() {
        renderPnlCreateLostItem = false;
        renderpnlToSearchPage=false;
        renderPnlManPage = true;
    }
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

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        lostFxAssetEntity.setDepartment(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="DNS service">
    String fileName;
    String docFormat;
    byte[] byteFile;

    public void uploadHandling(FileUploadEvent event) {
        InputStream inputStreamFile = null;
        String docFormat = event.getFile().getFileName().split("\\.")[1];
        String fileName = event.getFile().getFileName().split("\\.")[0];
        try {
            inputStreamFile = event.getFile().getInputstream();
        } catch (Exception e) {
        }      
        byteFile = dataUpload.extractByteArray(inputStreamFile);
        if (byteFile != null) {
            System.out.println("byte File---" + byteFile);
            System.out.println("File Name " + fileName + " with Format " + docFormat + " is Succesfully Uploaded");
        }
    }

    public void onRowSelectFileUploaded(SelectEvent ev) {
        System.out.println("when File Row Selected");     
        mmsLuDmArchiveSelection = (MmsLuDmArchive) ev.getObject();
        System.out.println("File Name===" + documentModelSelection.getName());
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

    public StreamedContent downloading() throws Exception {
        System.out.println("when Download");       
        streamedContent = dataUpload.getMmsFileRefNumber(mmsLuDmArchive);
        return streamedContent;
    }

    public void getDocValue() {
        DMdocumentModel = dataUpload.selectListOfFileByDocId(documentId);
        System.out.println("No of your Doc is " + DMdocumentModel.getRowCount());
    }

//</editor-fold>
   }
