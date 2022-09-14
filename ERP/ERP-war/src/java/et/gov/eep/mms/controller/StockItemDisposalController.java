package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.businessLogic.MmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsGrnDetBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStockDisposalBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStockDisposalDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.LostStockDisposalDmsUpload;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStockDisposal;
import et.gov.eep.mms.entity.MmsStockDisposalDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.io.File;
import java.io.FileInputStream;
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
import javax.faces.context.ExternalContext;
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
import org.apache.commons.io.FileUtils;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.insa.util.Utility;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
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
@Named(value = "stockItemDisposalController")
@ViewScoped
public class StockItemDisposalController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsGrnDetBeanLocal genDetinterface;
    @EJB
    private MmsStockDisposalBeanLocal stockDisposalInterface;
    @EJB
    private MmsStockDisposalDtlBeanLocal stockDispDtlInterface;
    @EJB
    MmsLuDmArchiveBeanLocal mmsLuDmArchiveBeanLocal;
    @EJB
    MmsItemRegisrtationBeanLocal itemBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inject Entitities">
    @Inject
    private MmsGrnDetail grndetail;
    @Inject
    private MmsStockDisposal stockDispEntity;
    @Inject
    private MmsStockDisposalDtl stockDispDetEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    DataUpload dataUpload;
    @Inject
    private MmsStockDisposal disposal;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    LostStockDisposalDmsUpload lostDmsUpload;
    @Inject
    MmsLuDmArchive mmsLuDmArchive;
    @Inject
    private MmsItemRegistration itemRegEntity;
    @Inject
    ColumnNameResolver columnNameResolver;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare lists and models">
    private DataModel<MmsStockDisposalDtl> stockdispAddDetailDataModel;
    private DataModel<MmsStockDisposal> mmsStockDispSearchInfoDataModel;
    private List<MmsStockDisposal> mmsDispList;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    DataModel<DocumentModel> DMdocumentModel;
    DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel;
    List<Integer> documentId;
    List<MmsStockDisposal> storeNameList = new ArrayList<>();
    List<MmsStockDisposalDtl> itemCodeList = new ArrayList<>();
    String matName;
    String unitM;
    String matCode;
    List<MmsGrnDetail> grnDtlList = new ArrayList<>();
    List<MmsStoreInformation> storeInfoList = new ArrayList<>();
    List<MmsLuDmArchive> mmsLuDmArchiveList = new ArrayList<>();
    String newDisposalId;
    String lastDisposalId = "0";
    List<MmsStockDisposal> recreatedList = new ArrayList<>();
    List<MmsStockDisposal> allReturnInfoList;
    List<MmsStockDisposal> checkerList = new ArrayList<>();
    DataModel<DocumentModel> docValueModel;
    DocumentModel documentModel = new DocumentModel();
    List<MmsStockDisposal> mmsStockDisposalsList;
    StreamedContent file;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    String selectedValue = "";
    private boolean renderDecision = false;
    boolean isRenderCreate;
    private MmsStockDisposal hpSelect;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private String addOrModifyBundle = "Add";
    private boolean renderPnlCreateDisposal = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private String userName;
    String disposalmethod = "true";
    Set<String> checkMaterialCode = new HashSet<>();
    private int fromSearchOrFromRequest;
    private final int fromSearch = 0;
    private final int fromRequest = 1;
    int refNumber;
    private DocumentModel documentModelSelection;
    private StreamedContent download;
    private boolean isFileSelected = false;
    MmsLuDmArchive mmsLuDmArchiveSelection;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Defualt constructor">
    public StockItemDisposalController() {
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        if (workflow.isApproveStatus()) {
            System.out.println("app or check");
            mmsDispList = stockDisposalInterface.findDispListByWfStatus(Constants.PREPARE_VALUE);
            renderDecision = true;
            isRenderedIconNitification = true;
            isRenderCreate = false;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            mmsDispList = stockDisposalInterface.findDispListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            renderDecision = false;
            isRenderCreate = true;
            if (mmsDispList.size() > 0) {
                isRenderedIconNitification = true;
            } else {
                isRenderedIconNitification = false;
                ColumnNameResolverList = stockDisposalInterface.getColumnNameList();
              workFlow.setProcessedBy(SessionBean.getUserId());
              setUserName(SessionBean.getUserName());
              System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
            }
        }

    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and saetter of Lists and models">
    public DocumentModel getDocumentModelSelection() {
        return documentModelSelection;
    }
     public void setDocumentModelSelection(DocumentModel documentModelSelection) {
        this.documentModelSelection = documentModelSelection;
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

    public DataModel<DocumentModel> getDMdocumentModel() {
        return DMdocumentModel;
    }

    public void setDMdocumentModel(DataModel<DocumentModel> DMdocumentModel) {
        this.DMdocumentModel = DMdocumentModel;
    }
     public List<MmsStockDisposal> getMmsDispList() {
        return mmsDispList;
    }

    public void setMmsDispList(List<MmsStockDisposal> mmsDispList) {
        this.mmsDispList = mmsDispList;
    }
     public DataModel<WfMmsProcessed> getWfMmsProcessedDataModel() {
        if (WfMmsProcessedDataModel == null) {
            WfMmsProcessedDataModel = new ListDataModel<>();
        }
        return WfMmsProcessedDataModel;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public void setWfMmsProcessedDataModel(DataModel<WfMmsProcessed> WfMmsProcessedDataModel) {
        this.WfMmsProcessedDataModel = WfMmsProcessedDataModel;
    }
     public DataModel<MmsStockDisposalDtl> getStockdispAddDetailDataModel() {
        if (stockdispAddDetailDataModel == null) {
            stockdispAddDetailDataModel = new ListDataModel<>();
        }
        return stockdispAddDetailDataModel;
    }

    public void setStockdispAddDetailDataModel(DataModel<MmsStockDisposalDtl> stockdispAddDetailDataModel) {
        this.stockdispAddDetailDataModel = stockdispAddDetailDataModel;
    }

    public DataModel<MmsStockDisposal> getMmsStockDispSearchInfoDataModel() {
        if (mmsStockDispSearchInfoDataModel == null) {
            mmsStockDispSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsStockDispSearchInfoDataModel;
    }

    public void setMmsStockDispSearchInfoDataModel(DataModel<MmsStockDisposal> mmsStockDispSearchInfoDataModel) {
        this.mmsStockDispSearchInfoDataModel = mmsStockDispSearchInfoDataModel;
    }
     public List<MmsGrnDetail> getGrnDtlList() {
        grnDtlList = genDetinterface.findAllGrnInfo();
        return grnDtlList;
    }

    public void setGrnDtlList(List<MmsGrnDetail> grnDtlList) {
        this.grnDtlList = grnDtlList;
    }

    public List<MmsStoreInformation> getStoreInfoList() {
        storeInfoList = storeInfoInterface.findAllStoreInfo();

        return storeInfoList;
    }

    public void setStoreInfoList(List<MmsStoreInformation> storeInfoList) {
        this.storeInfoList = storeInfoList;
    }

    public SelectItem[] getStoreNameList() {
        storeNameList = stockDisposalInterface.findAllStoreName();
        return JsfUtil.getSelectItems(storeNameList, true);
    }

    public SelectItem[] getItemCodeList() {
        itemCodeList = stockDispDtlInterface.findAllItemCode();
        return JsfUtil.getSelectItems(itemCodeList, true);
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }


//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of variables">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }
    
    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }
     public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getDisposalmethod() {
        return disposalmethod;
    }

    public void setDisposalmethod(String disposalmethod) {
        this.disposalmethod = disposalmethod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
     public int getFromSearchOrFromRequest() {
        return fromSearchOrFromRequest;
    }

    public void setFromSearchOrFromRequest(int fromSearchOrFromRequest) {
        this.fromSearchOrFromRequest = fromSearchOrFromRequest;
    }
     public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
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
     public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }
    
    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }
     public MmsStockDisposal getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsStockDisposal hpSelect) {
        this.hpSelect = hpSelect;
    }
    public MmsStockDisposal getDisposal() {
        if (disposal == null) {
            disposal = new MmsStockDisposal();
        }
        return disposal;
    }

    public void setDisposal(MmsStockDisposal disposal) {
        this.disposal = disposal;
    }

    
    
    
    public MmsItemRegistration getItemRegEntity() {
        if (itemRegEntity == null) {
            itemRegEntity = new MmsItemRegistration();
        }
        return itemRegEntity;
    }

    public void setItemRegEntity(MmsItemRegistration itemRegEntity) {
        this.itemRegEntity = itemRegEntity;
    }

    public MmsGrnDetail getGrndetail() {
        if (grndetail == null) {
            grndetail = new MmsGrnDetail();
        }
        return grndetail;
    }

    public void setGrndetail(MmsGrnDetail grndetail) {
        this.grndetail = grndetail;
    }

    public MmsStockDisposal getStockDispEntity() {
        if (stockDispEntity == null) {
            stockDispEntity = new MmsStockDisposal();
        }
        return stockDispEntity;
    }

    public void setStockDispEntity(MmsStockDisposal stockDispEntity) {
        this.stockDispEntity = stockDispEntity;
    }

    public MmsStockDisposalDtl getStockDispDetEntity() {
        if (stockDispDetEntity == null) {
            stockDispDetEntity = new MmsStockDisposalDtl();

        }
        return stockDispDetEntity;
    }

    public void setStockDispDetEntity(MmsStockDisposalDtl stockDispDetEntity) {
        this.stockDispDetEntity = stockDispDetEntity;
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add to detail and recreateModel">
    public String addStockDisposalDetail() {
        stockDispDetEntity.setItemName(matName);
        stockDispDetEntity.setItemCode(matCode);
        stockDispDetEntity.setUnitMeasure(unitM);
        stockDispEntity.addStockDisposalDetail(stockDispDetEntity);
        recreateModelDetail();
        clearPopUp();
        return null;
    }

    private void recreateModelDetail() {
        stockdispAddDetailDataModel = null;
        stockdispAddDetailDataModel = new ListDataModel(new ArrayList<>(stockDispEntity.getMmsStockDisposalDtlList()));
    }

    private void recerateDispSerachModel2() {

        mmsStockDispSearchInfoDataModel = null;
        mmsStockDispSearchInfoDataModel = new ListDataModel(recreatedList);
    }

    private void recerateDispSerachModel() {
        mmsStockDispSearchInfoDataModel = null;
        mmsStockDispSearchInfoDataModel = new ListDataModel(new ArrayList<>(allReturnInfoList));
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        for (int i = 0; i < stockDispEntity.getStockDispList().size(); i++) {
            if (stockDispEntity.getStockDispList().get(i).getDecision() != null) {
                if (stockDispEntity.getStockDispList().get(i).getDecision() == 1 || stockDispEntity.getStockDispList().get(i).getDecision() == 3) {
                    stockDispEntity.getStockDispList().get(i).setWfDecison("Approved");
                } else if (stockDispEntity.getStockDispList().get(i).getDecision() == 2 || stockDispEntity.getStockDispList().get(i).getDecision() == 4) {
                    stockDispEntity.getStockDispList().get(i).setWfDecison("Rejected");
                }
            } else {
                stockDispEntity.getStockDispList().get(i).setWfDecison("Requested");
            }

        }

        WfMmsProcessedDataModel = new ListDataModel(stockDispEntity.getStockDispList());
    }

    private void clearPopUp() {
        stockDispDetEntity = null;
        grndetail = null;
        itemRegEntity = null;
        addOrModifyBundle = "Add";
    }

    public void onRowEdit(RowEditEvent event) {
        stockDispEntity = (MmsStockDisposal) event.getObject();
        setFromSearchOrFromRequest(fromSearch);
        int size = stockDispEntity.getStockDispList().size();

        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {

            System.out.println("=========checker approve=====" + stockDispEntity.getStockDispList().get(size - 1).getDecision());

            if (stockDispEntity.getStockDispList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (stockDispEntity.getStockDispList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            workFlow.setProcessedOn(stockDispEntity.getStockDispList().get(size - 1).getProcessedOn());
        } else {
            if (size > 1) {
                System.out.println("========inside if ======" + workflow.isReadonly());

                workflow.setReadonly(true);
                System.out.println("=========inside if 2====" + workflow.isReadonly());
                workflow.setButtonStatus(true);
            }
        }

        populateUI();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Cleare page"> 
    //<editor-fold defaultstate="collapsed" desc="clearpage">
    public String clearPage() {
        storeInfoEntity = null;
        stockDispEntity = null;
        stockDispDetEntity = null;
        stockdispAddDetailDataModel = null;
        mmsStockDispSearchInfoDataModel = null;
        storeInfoEntity = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        workFlow = null;
        return null;
    }
//</editor-fold>
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search and Update methods">
    public void searchDispInformation1() {
//        if (stockDispEntity.getStockNo() != null) {
            String stockNo = stockDispEntity.getStockNo();
            stockDispEntity.setStockNo(stockNo);
            allReturnInfoList = stockDisposalInterface.getStockDisposalListsByParameter(stockDispEntity);
            System.out.println("list===" + allReturnInfoList);
            checkerList.clear();
            checkerList = allReturnInfoList;
//        } else {
            stockDispEntity.setPreparedBy(SessionBean.getUserId());
            allReturnInfoList = stockDisposalInterface.getStockDisposalListsByParameter(stockDispEntity);
            System.out.println("=====all list===" + allReturnInfoList);
            recerateDispSerachModel();
        
    
        recerateDispSerachModel();
        if (stockDispEntity.getReferenceNumber() != null) {
            mmsLuDmArchive = stockDispEntity.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
        }
        recreateFileUpload();
    }
    
    public String generateStockDispNo() {
        if (updateStatus == 1) {
            newDisposalId = stockDispEntity.getStockNo();
        } else {
            MmsStockDisposal lastStDispID = stockDisposalInterface.getLastStockDisposalId();
            if (lastStDispID != null) {
                lastDisposalId = lastStDispID.getStockId().toString();
            }
            
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            
            int id = 0;
            if (lastDisposalId.equals("0")) {
                id = 1;
                newDisposalId = "SDNo-" + id + "/" + f.format(now);
            } else {
                
                String[] lastInspNos = lastDisposalId.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                id = Integer.parseInt(lastDatesPaterns[0]);
                id = id + 1;
                newDisposalId = "SDNo-" + id + "/" + f.format(now);
            }
        }
        return newDisposalId;
    }
    
    public void createNewStockDisposalInfo() {
        
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
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
        
        workFlow.setStkId(stockDispEntity);
        workFlowInterface.create(workFlow);
        
    }
    
    public void editDataTable() {
        addOrModifyBundle = "Modify";
        stockDispDetEntity = stockdispAddDetailDataModel.getRowData();
        itemRegEntity = stockDispDetEntity.getItemId();
    }
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String MessageText = "";
    
    public boolean isProposedAfterApprovedDateDate(String proposedDate, String approvedDate) {
        try {
            System.out.println("StringDateManipulation.todayInEC()===" + StringDateManipulation.todayInEC());
            if (df.parse(DateFormatFromConvertor(StringDateManipulation.todayInEC())).before(df.parse(proposedDate)) || df.parse(DateFormatFromConvertor(StringDateManipulation.todayInEC())).before(df.parse(approvedDate))) {
                MessageText = "Make Sure Proposed date and Approved date are Not In the Future";
                return true;
            } else if ((df.parse(approvedDate).before(df.parse(proposedDate)))) {
                MessageText = "Date Approved Can't be Before Proposed date";
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private String DateFormatFromConvertor(String date) {
        String inputDate[] = date.split("-");
        String Outputdate = inputDate[2] + "/" + inputDate[1] + "/" + inputDate[0];
        return Outputdate;
    }
     public ArrayList<MmsStockDisposal> searchByStockno(String Insno) {
        ArrayList<MmsStockDisposal> Dispno = null;
        stockDispEntity.setStockNo(Insno);
        Dispno = stockDisposalInterface.searchByStockDispNo(stockDispEntity);
        saveorUpdateBundle = "Update";
        return Dispno;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public void saveStockDisposal() {
        
        try {
            System.out.println("rec " + stockDispEntity.getPropDate() + "ret " + stockDispEntity.getAppDate());
            if (isProposedAfterApprovedDateDate(stockDispEntity.getPropDate(), stockDispEntity.getAppDate()) == true) {
                JsfUtil.addFatalMessage(MessageText);
            } else {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveStockDisposal", dataset)) {
                    if (updateStatus == 0) {
                        if (stockdispAddDetailDataModel.getRowCount() > 0) {
                            
                            try {
                                
                                stockDispEntity.setStockNo(newDisposalId);
                                stockDispEntity.setStoreId(storeInfoEntity);
                                stockDispEntity.setStkStatus(Constants.PREPARE_VALUE);
                                workFlow.setDecision(Constants.PREPARE_VALUE);
                                mmsLuDmArchive.setFileName(fileName);
                                mmsLuDmArchive.setFileType(docFormat);
                                mmsLuDmArchive.setUploadFile(byteFile);
                                mmsLuDmArchiveBeanLocal.saveFile(mmsLuDmArchive);
                                stockDispEntity.setReferenceNumber(mmsLuDmArchive);
                                stockDispEntity.setProcessedOn(workFlow.getProcessedOn());
                                stockDispEntity.setPreparedBy(workFlow.getProcessedBy());
                                stockDispEntity.setApproverRemark(workFlow.getCommentGiven());
                                stockDispEntity.getStockDispList().add(workFlow);
                                stockDisposalInterface.create(stockDispEntity);
                                JsfUtil.addSuccessMessage(" A New Stock Item Disposal Information is Saved! ");
                                clearPage();
                            } catch (Exception ex) {
                                JsfUtil.addFatalMessage("Something Error Occured on Saved the Data ");
                                
                            }
                        } else {
                            JsfUtil.addFatalMessage("Please Put atleast One Record on the Detail Form.");
                            
                        }
                        
                    } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                        try {
                            System.out.println("=====edit==");
                            stockDispEntity.setPreparedBy(workFlow.getProcessedBy());
                            stockDispEntity.setApproverRemark(workFlow.getCommentGiven());
                            stockDispEntity.setProcessedOn(workFlow.getProcessedOn());
                            stockDispEntity.setStkStatus(Constants.PREPARE_VALUE);
                            workFlow.setDecision(stockDispEntity.getStkStatus());
                            stockDispEntity.getStockDispList().add(workFlow);
                            stockDisposalInterface.edit(stockDispEntity);
                            Wfsave();
                            System.out.println("=====after edit===");
                            JsfUtil.addSuccessMessage("Stock Item Disposal Information has been Updated");
                            clearPage();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                        }
                    } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                        System.out.println("12");
                        if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_VALUE);
                            stockDispEntity.setStkStatus(Constants.APPROVE_VALUE);
                            workFlow.setDecision(Constants.APPROVE_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            stockDispEntity.setStkStatus(Constants.CHECK_APPROVE_VALUE);
                            workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            stockDispEntity.setStkStatus(Constants.APPROVE_REJECT_VALUE);
                            workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            stockDispEntity.setStkStatus(Constants.CHECK_REJECT_VALUE);
                            workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                        }
                        workFlow.setStkId(stockDispEntity);
                        workFlowInterface.create(workFlow);
                        stockDisposalInterface.edit(stockDispEntity);
                        mmsDispList.remove(stockDispEntity);
                        
                        JsfUtil.addSuccessMessage("Stock Disposal Data Updated");
                        clearPage();
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Handlere">
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);
            stockDispEntity.setStoreId(storeInfoEntity);

        }
    }

    public ArrayList<MmsItemRegistration> searchMatInformationByPrefix(String matcode) {
        ArrayList<MmsItemRegistration> itemInformations = null;// to make the previous search  paramaters and results null;
        getItemRegEntity().setMatCode(matcode);

        itemInformations = itemBeanLocal.searchMatInformationByPrefix(matcode);
        return itemInformations;
    }
public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            stockDispEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }

    public void handleSelectItemNameAuto1(SelectEvent event) {
        System.out.println("=======event value====");
        if (event.getObject() != null) {
            itemRegEntity = (MmsItemRegistration) event.getObject();
            System.out.println("=======event value====" + itemRegEntity.getMatCode());
            stockDispDetEntity.setItemId(itemRegEntity);
        }
    }

    public void handleSelectItemNameAuto(SelectEvent event) {
        System.out.println("=======event value====");
        if (event.getObject() != null) {
//            itemRegEntity = (MmsItemRegistration) event.getObject();
            String matCode = event.getObject().toString();

            itemRegEntity = itemBeanLocal.searchByMaterialCode(matCode);
            System.out.println("=======event value====" + itemRegEntity);
            stockDispDetEntity.setItemId(itemRegEntity);
            stockDispDetEntity.setItemCode(itemRegEntity.getMatCode());
            stockDispDetEntity.setItemName(itemRegEntity.getMatName());
            stockDispDetEntity.setUnitMeasure(itemRegEntity.getUnitMeasure1());
            matName = stockDispDetEntity.getItemName();
            unitM = stockDispDetEntity.getUnitMeasure();
            matCode = stockDispDetEntity.getItemCode();

        }
    }

    public void handleSelectItemCode(ValueChangeEvent event) {
        String itemid = event.getNewValue().toString();
        int dtlId = Integer.parseInt(itemid);
        MmsGrnDetail grndtl = new MmsGrnDetail();
        grndtl.setGrnDetailId(dtlId);
        grndetail = null;
        grndetail = new MmsGrnDetail();
        grndetail = genDetinterface.findInfoById(grndtl);
        stockDispDetEntity.setItemCode(grndetail.getItemId().getMatCode());
        stockDispDetEntity.setItemName(grndetail.getItemId().getMatName());
        stockDispDetEntity.setUnitMeasure(grndetail.getItemId().getUnitMeasure1());
        matName = stockDispDetEntity.getItemName();
        unitM = stockDispDetEntity.getUnitMeasure();
        matCode = stockDispDetEntity.getItemCode();
        System.out.println("--------ItemCode---------  " + grndetail.getItemId().getMatCode());
        System.out.println("--------ItemCode---------  " + stockDispDetEntity.getItemCode());
        System.out.println("--------ItemName------  " + grndetail.getItemId().getMatName());
        System.out.println("--------ItemName------  " + stockDispDetEntity.getItemName());
    }

    public void handleSelectItemCode1(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            grndetail = (MmsGrnDetail) event.getNewValue();
            stockDispDetEntity.setItemCode(grndetail.getItemId().getMatCode());
            stockDispDetEntity.setItemName(grndetail.getItemId().getMatName());
            stockDispDetEntity.setUnitMeasure(grndetail.getItemId().getUnitMeasure1());
        }

    }

    public void handleSelectDisposal(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            String disMethod = event.getNewValue().toString();
            if (disMethod.equalsIgnoreCase("Sell By Tender") || (disMethod.equalsIgnoreCase("Sale To Staff"))) {
                disposalmethod = "false";
            } else {
                disposalmethod = "true";
            }

        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Rowselection and Update">
    public void rowSelect(SelectEvent event) {
        stockDispEntity = (MmsStockDisposal) event.getObject();
        stockDispEntity.setStockId(stockDispEntity.getStockId());
        stockDispEntity = stockDisposalInterface.getSelectedRequest(stockDispEntity.getStockId());
        populateUI();
    }
    
    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                stockDispEntity = (MmsStockDisposal) event.getNewValue();
                setFromSearchOrFromRequest(fromRequest);
                populateUI();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }
    
    public void populateUI() {
        renderPnlCreateDisposal = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        storeInfoEntity = stockDispEntity.getStoreId();
        if (workflow.isPrepareStatus()) {
            workFlow.setProcessedOn(stockDispEntity.getProcessedOn());
        }
        recreateModelDetail();
        recreateWfDataModel();
        setIsRenderedIconWorkflow(true);
        if (stockDispEntity.getReferenceNumber() != null) {
            mmsLuDmArchive = stockDispEntity.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
        }
        recreateFileUpload();
    }
     public void recreateFileUpload() {
        mmsLuDmArchivesDModel = null;
        mmsLuDmArchivesDModel = new ListDataModel(mmsLuDmArchiveList);
    }
//</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Other methods">
    public void goBackToSearchpageBtnAction() {
        renderPnlCreateDisposal = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DMS service">
    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public void populateData() {
        DmsHandler dmsHandler = new DmsHandler();
        DocumentModel documentModelInt = new DocumentModel();
        documentModelInt.setUserId(Long.valueOf("2"));
        documentModelInt.setCatagoryId(Utility.CATAGORY_ID);

        DocList docList = dmsHandler.getDocuments(documentModelInt);
        if (docList != null) {
            docValueModel = new ListDataModel(docList.getDocList());
        }
    }

    public void onRowSelect(SelectEvent event) {
        documentModel = (DocumentModel) event.getObject();
    }

    public LostStockDisposalDmsUpload getLostDmsUpload() {
        if (lostDmsUpload == null) {
            lostDmsUpload = new LostStockDisposalDmsUpload();
        }
        return lostDmsUpload;
    }

    public void setLostDmsUpload(LostStockDisposalDmsUpload lostDmsUpload) {
        this.lostDmsUpload = lostDmsUpload;
    }

    public void uploadListener(FileUploadEvent event) {
        try {
            DmsHandler dmsHandler = new DmsHandler();
            documentModel = new DocumentModel();
            documentModel.setDocFormat(event.getFile().getFileName().split("\\.")[1]);
            documentModel.setName(event.getFile().getFileName().split("\\.")[0]);
            documentModel.setUserId(Long.valueOf("2"));
            documentModel.setCatagoryId(Utility.CATAGORY_ID);
            documentModel.setCreater("from client");
            File fileDoc = new File(event.getFile().getFileName());
            FileUtils.writeByteArrayToFile(fileDoc, dmsHandler.extractByteArray1(event.getFile().getInputstream()));
            documentModel.setDate(StringDateManipulation.todayInEC());
            documentModel.setFile(fileDoc);
            documentModel.setByteFile(dmsHandler.extractByteArray1(event.getFile().getInputstream()));

            int x = dmsHandler.saveDocument(documentModel);
            if (x != -1) {
                lostDmsUpload.setDocId(x);
                stockDispEntity.add(lostDmsUpload);
                JsfUtil.addSuccessMessage("Successfully Uploaded !!!");
            } else {
                JsfUtil.addFatalMessage("Sorry Can't Upload The File ");
            }
            populateData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StreamedContent getFile() throws Exception {
        if (documentModel != null) {
            File fileDoc = new File(""
                    + documentModel.getName() + "."
                    + documentModel.getDocFormat());
            FileUtils.writeByteArrayToFile(fileDoc, documentModel.getByteFile());
            InputStream input = new FileInputStream(fileDoc);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            file = new DefaultStreamedContent(input, externalContext.getMimeType(fileDoc.getName()), documentModel.getDocFormat());
            return file;

        }
        return null;
    }

    public void remove(DocumentModel document) {
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        populateData();
    }

    String docFormat;
    String fileName;
    byte[] byteFile;

    public void uploadHandling(FileUploadEvent event) {
        InputStream inputStreamFile = null;
        String docFormat = event.getFile().getFileName().split("\\.")[1];
        String fileName = event.getFile().getFileName().split("\\.")[0];
        try {
            inputStreamFile = event.getFile().getInputstream();
        } catch (Exception e) {
        }
        System.out.println("Doc Id---" + refNumber);
        byteFile = dataUpload.extractByteArray(inputStreamFile);
        if (byteFile != null) {
            System.out.println("byte File---" + byteFile);
            System.out.println("File Name " + fileName + " with Format " + docFormat + " is Succesfully Uploaded");
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

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }

    public void getDocValue() {
        DMdocumentModel = dataUpload.selectListOfFileByDocId(documentId);
        System.out.println("No of your Doc is " + DMdocumentModel.getRowCount());
    }
    // </editor-fold>
}
