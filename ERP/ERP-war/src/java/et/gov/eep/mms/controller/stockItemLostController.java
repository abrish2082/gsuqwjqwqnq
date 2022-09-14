
package et.gov.eep.mms.controller;

     //<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.businessLogic.MmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HrCommitteeMembersIntegrationBeanLocal;
import et.gov.eep.hrms.integration.HrCommitteesIntegrationBeanLocal;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.businessLogic.StockItemLostBeanLocal;
import et.gov.eep.mms.entity.LostStockDmsUpload;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.entity.MmsStockItemLost;
import et.gov.eep.mms.entity.MmsStockItemLostDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsBinCardFacade;
import et.gov.eep.mms.mapper.MmsStockItemLostFacade;
import java.io.File;
import java.io.FileInputStream;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.commons.io.FileUtils;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.insa.util.Utility;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
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
@Named(value = "stockItemLostController")
@ViewScoped
public class stockItemLostController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrdepartmentInterface;
    @EJB
    private MmsStockItemLostFacade stockItemLostFacede;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private MmsBinCardFacade binCardFacade;
    @EJB
    private StockItemLostBeanLocal mmsStockItemBeanLocal;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private HrCommitteesIntegrationBeanLocal committesInterface;
    @EJB
    private HrCommitteeMembersIntegrationBeanLocal committeMembersInterface;
    @EJB
    MmsLuDmArchiveBeanLocal mmsLuDmArchiveBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entitities">
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsStockItemLostDtl stockLostDtlEntity;
    @Inject
    private MmsStockItemLost stockLostEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private MmsBinCard bincardEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    DataUpload dataUpload;
    @Inject
    HrCommittees hrcommittesEntity;
    @Inject
    HrCommitteeMembers hrcommitteeMembersEntity;
    @Inject
    MmsLuDmArchive mmsLuDmArchive;
    @Inject
    MmsLuDmArchive mmsLuDmArchiveSelection;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Declare Lists and Models">
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    private DataModel<MmsStockItemLostDtl> stkLostAddDetailDataModel;
    private DataModel<MmsStockItemLost> stkLostSearchInfoDataModel;
    Set<String> checkMaterialCode = new HashSet<>();
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private static List<HrDepartments> araListe;
    private List<MmsStockItemLost> mmsLostList;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    DataModel<DocumentModel> DMdocumentModel;
    List<Integer> documentId;
    private List<HrCommittees> committeeList = new ArrayList<>();
    private List<HrCommitteeMembers> committeeMembersList = new ArrayList<>();
    private List<HrCommitteeMembers> selectedMembersList = new ArrayList<>();
    List<MmsBinCard> binCardDtlList = new ArrayList<>();
    List<MmsStoreInformation> storeInfoLst = new ArrayList<>();
    List<MmsStockItemLost> allLostItemInfoList;
    DataModel<MmsLuDmArchive> mmsLuDmArchivesDModel;
    List<MmsLuDmArchive> mmsLuDmArchiveList = new ArrayList<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private MmsLostFixedAsset hpSelect;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateLostItem = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String status = "Prepared";
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private boolean renderDecision = false;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private String userName;
    private String selectedValue = "";
    boolean isRenderCreate;
    int refNumber;
    private DocumentModel documentModelSelection;
    private StreamedContent streamedContent;
    private String[] selectedMembers;
    private String members = "";
    String newLostItemId;
    String lastLostItemId = "0";
    private String addOrModifyBundle = "Add";
    private boolean isFileSelected = false;
    private StreamedContent download;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Defualt Constractor">
    public stockItemLostController() {
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
         ColumnNameResolverList = mmsStockItemBeanLocal.getColumnNameList();
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
      
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                mmsLostList = stockItemLostFacede.findSrListByWfStatus(Constants.CHECK_APPROVE_VALUE);
            } else if (workflow.isCheckStatus()) {
                mmsLostList = stockItemLostFacede.findSrListForCheckerByWfStatus(Constants.PREPARE_VALUE, Constants.APPROVE_REJECT_VALUE);
            }
            isRenderCreate = false;
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsLostList = stockItemLostFacede.findSrListByWfStatus(Constants.CHECK_REJECT_VALUE);
            renderDecision = false;
            isRenderCreate = true;
            if (mmsLostList.size() > 0) {
                isRenderedIconNitification = true;
            } else {
                isRenderedIconNitification = false;
                 
            
            }

        }
         
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        allDepartmentsList = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
       
    }
//    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of objects">
    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }
      public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
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

    public WorkFlow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkFlow workflow) {
        this.workflow = workflow;
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
      public DocumentModel getDocumentModelSelection() {
        return documentModelSelection;
    }

    public void setDocumentModelSelection(DocumentModel documentModelSelection) {
        this.documentModelSelection = documentModelSelection;
    }

    public MmsStockItemLostDtl getStockLostDtlEntity() {

        if (stockLostDtlEntity == null) {
            stockLostDtlEntity = new MmsStockItemLostDtl();
        }
        return stockLostDtlEntity;
    }

    public void setStockLostDtlEntity(MmsStockItemLostDtl stockLostDtlEntity) {
        this.stockLostDtlEntity = stockLostDtlEntity;
    }
    public MmsStockItemLost getStockLostEntity() {
        if (stockLostEntity == null) {
            stockLostEntity = new MmsStockItemLost();
        }
        return stockLostEntity;
    }

    public void setStockLostEntity(MmsStockItemLost stockLostEntity) {
        this.stockLostEntity = stockLostEntity;
    }

    public MmsBinCard getBincardEntity() {
        if (bincardEntity == null) {
            bincardEntity = new MmsBinCard();
        }
        return bincardEntity;
    }

    public void setBincardEntity(MmsBinCard bincardEntity) {
        this.bincardEntity = bincardEntity;
    }

    public HrDepartments getHrDepartmentsEntity() {
        if (hrDepartmentsEntity == null) {
            hrDepartmentsEntity = new HrDepartments();
        }
        return hrDepartmentsEntity;
    }

    public void setHrDepartmentsEntity(HrDepartments hrDepartmentsEntity) {
        this.hrDepartmentsEntity = hrDepartmentsEntity;
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
     public HrCommitteeMembers getHrcommitteeMembersEntity() {
        if (hrcommitteeMembersEntity == null) {
            hrcommitteeMembersEntity = new HrCommitteeMembers();
        }
        return hrcommitteeMembersEntity;
    }

    public void setHrcommitteeMembersEntity(HrCommitteeMembers hrcommitteeMembersEntity) {
        this.hrcommitteeMembersEntity = hrcommitteeMembersEntity;
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
    


//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }
    
    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
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

    public boolean isRenderPnlCreateLostItem() {
        return renderPnlCreateLostItem;
    }

    public void setRenderPnlCreateLostItem(boolean renderPnlCreateLostItem) {
        this.renderPnlCreateLostItem = renderPnlCreateLostItem;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
     public String[] getSelectedMembers() {
        return selectedMembers;
    }

    public void setSelectedMembers(String[] selectedMembers) {
        this.selectedMembers = selectedMembers;
    }
     /**
     * to render Add and Modify Button
     *
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

    public MmsLostFixedAsset getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsLostFixedAsset hpSelect) {
        this.hpSelect = hpSelect;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of Lists and Models">
    public List<MmsStockItemLost> getMmsLostList() {
        return mmsLostList;
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

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public void setMmsLostList(List<MmsStockItemLost> mmsLostList) {
        this.mmsLostList = mmsLostList;
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
     public DataModel<MmsStockItemLostDtl> getStkLostAddDetailDataModel() {
        if (stkLostAddDetailDataModel == null) {
            stkLostAddDetailDataModel = new ListDataModel<>();
        }
        return stkLostAddDetailDataModel;
    }

    public void setStkLostAddDetailDataModel(DataModel<MmsStockItemLostDtl> stkLostAddDetailDataModel) {
        this.stkLostAddDetailDataModel = stkLostAddDetailDataModel;
    }
    public DataModel<MmsStockItemLost> getStkLostSearchInfoDataModel() {
        if (stkLostSearchInfoDataModel == null) {
            stkLostSearchInfoDataModel = new ListDataModel<>();
        }
        return stkLostSearchInfoDataModel;
    }

    public void setStkLostSearchInfoDataModel(DataModel<MmsStockItemLost> stkLostSearchInfoDataModel) {
        this.stkLostSearchInfoDataModel = stkLostSearchInfoDataModel;
    }
     public DataModel<DocumentModel> getDMdocumentModel() {
        return DMdocumentModel;
    }

    public void setDMdocumentModel(DataModel<DocumentModel> DMdocumentModel) {
        this.DMdocumentModel = DMdocumentModel;
    }
     public List<HrCommitteeMembers> getSelectedMembersList() {
        if (selectedMembersList == null) {
            selectedMembersList = new ArrayList<>();

        }
        return selectedMembersList;
    }

    public void setSelectedMembersList(List<HrCommitteeMembers> selectedMembersList) {
        this.selectedMembersList = selectedMembersList;
    }
     public List<HrCommitteeMembers> getCommitteeMembers() {
        committeeMembersList = committeMembersInterface.getCommitteeMembers(hrcommittesEntity);

        return committeeMembersList;
    }
      public List<HrCommittees> getCommittees() {
        committeeList = committesInterface.findAll();
        return committeeList;
    }
    

//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RowSelection and update">
    public void rowSelect(SelectEvent event) {
        System.out.println("===========rowselect");
        stockLostEntity = (MmsStockItemLost) event.getObject();
        stockLostEntity.setLostStockId(stockLostEntity.getLostStockId());
        stockLostEntity = stockItemLostFacede.getSelectedRequest(stockLostEntity.getLostStockId());
        populateUI();
    }

    public void populateUI() {
        System.out.println("======populateUi===");
        hrcommittesEntity = stockLostEntity.getCommitteeId();
        System.out.println("------------");
        storeInfoEntity = stockLostEntity.getStoreId();      
        hrDepartmentsEntity = stockLostEntity.getDepartment();
        selectedMembers = stockLostEntity.getCommitteMembers().split(",");
        wfMmsProcessed.setProcessedOn(stockLostEntity.getProcessedOn());
        renderPnlManPage = false;
        renderPnlCreateLostItem = true;
        renderpnlToSearchPage=true;
        setIsRenderedIconWorkflow(true);
        recreateWfDataModel();
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        setIcone("ui-icon-plus");
        createOrSearchBundle = "New";
        if (stockLostEntity.getReferenceNumber() != null) {
            mmsLuDmArchive = stockLostEntity.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
        }
        recreateModelDetail();
        recreateFileUpload();

    }

    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                stockLostEntity = (MmsStockItemLost) event.getNewValue();
                populateUI();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
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
        stockLostEntity.setDepartment(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add to Detail and Recreat">
    public String addtoLostItemInfoDetail() {
        if (checkMaterialCode.contains(stockLostDtlEntity.getItemCode())) {
            JsfUtil.addSuccessMessage("Item Code: Value is Duplicated");
            return null;
        } else {
            stockLostEntity.addStockDetail(stockLostDtlEntity);
            checkMaterialCode.add(stockLostDtlEntity.getItemCode());
        }
        recreateModelDetail();
        clearPopUp();
        return null;
    }

    private void recerateStockLostItemSerachModel() {
        stkLostSearchInfoDataModel = null;
        stkLostSearchInfoDataModel = new ListDataModel(new ArrayList<>(allLostItemInfoList));
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        for (int i = 0; i < stockLostEntity.getStockLostList().size(); i++) {
            if (stockLostEntity.getStockLostList().get(i).getDecision() != null) {
                if (stockLostEntity.getStockLostList().get(i).getDecision() == 1 || stockLostEntity.getStockLostList().get(i).getDecision() == 3) {
                    stockLostEntity.getStockLostList().get(i).setWfDecison("Approved");
                } else if (stockLostEntity.getStockLostList().get(i).getDecision() == 2 || stockLostEntity.getStockLostList().get(i).getDecision() == 4) {
                    stockLostEntity.getStockLostList().get(i).setWfDecison("Rejected");
                }
            } else {
                stockLostEntity.getStockLostList().get(i).setWfDecison("Requested");
            }

        }

        WfMmsProcessedDataModel = new ListDataModel(stockLostEntity.getStockLostList());
    }

    private void recreateModelDetail() {
        stkLostAddDetailDataModel = null;
        stkLostAddDetailDataModel = new ListDataModel(new ArrayList<>(stockLostEntity.getMmsStockItemLostDtlList()));
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="clearePoPup and clearPage">
    private void clearPopUp() {
        stockLostDtlEntity = null;
        bincardEntity = null;
        binCardDtlList = null;
        addOrModifyBundle = "Add";
    }

    public String clearPage() {
        storeInfoEntity = null;
        stockLostDtlEntity = null;
        stockLostEntity = null;
        hrcommittesEntity = new HrCommittees();
        hrcommitteeMembersEntity = new HrCommitteeMembers();
        stkLostAddDetailDataModel = null;
        stkLostSearchInfoDataModel = null;
        hrDepartmentsEntity = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        wfMmsProcessed = null;
        setSelectedValue(null);
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handler">
    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
//
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(depIds);
            stockLostEntity.setDepartment(hrDepartmentsEntity);
        }
    }

    public void handleSelectStoreName1(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int storeIds = Integer.valueOf(event.getNewValue().toString());
            storeInfoEntity.setStoreId(storeIds);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);
            stockLostEntity.setStoreId(storeInfoEntity);

        }
    }
 public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            stockLostEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }

    public void handleSelectCommittee(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrcommittesEntity.setCommitteeName(event.getNewValue().toString());
            hrcommittesEntity = committesInterface.getCommittee(hrcommittesEntity);
            stockLostEntity.setCommitteeId(hrcommittesEntity);
        }
    }

    public void handleBincard(ValueChangeEvent event) {
        bincardEntity = (MmsBinCard) event.getNewValue();
        stockLostDtlEntity.setItemName(bincardEntity.getMaterialId().getMatName());
        stockLostDtlEntity.setBookValue(bincardEntity.getUnitPrice()); //setUnitPrice not BookValue
        stockLostDtlEntity.setItemCode(bincardEntity.getMaterialId().getMatCode());
        stockLostDtlEntity.setQuantity(bincardEntity.getCurrentQuantity().intValue());
        System.out.println("========quantity=====" + bincardEntity.getCurrentQuantity());
        System.out.println("========quantity=====" + stockLostDtlEntity.getQuantity());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search and Update">
    public void commiteeMembers() {
        for (String selectedMember : selectedMembers) {
            if (members.equals("")) {
                members = selectedMember;
            } else {
                members = members + "," + selectedMember;
            }
        }
        stockLostEntity.setCommitteMembers(members);
    }
    
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
    
    public void searchLostItemInformation1() {
        System.out.println("---------inside search---");
        String str = stockLostEntity.getLostItemNo();
        stockLostEntity.setLostItemNo(str);
        stockLostEntity.setProcessedBy(SessionBean.getUserId());
//        if (stockLostEntity.getLostItemNo()!=null) {
            System.out.println("find by item no====" + allLostItemInfoList);
            allLostItemInfoList = mmsStockItemBeanLocal.getStockListsByParameter(stockLostEntity);
            System.out.println("-------LostItemInfoList -----" + allLostItemInfoList.size());
            recerateStockLostItemSerachModel();
            
//        } else {
//            System.out.println("--------- Inside else ---------");
//            allLostItemInfoList = mmsStockItemBeanLocal.searchAllLostInfoByPreparerId(stockLostEntity.getProcessedBy());
//            System.out.println("=========== allinfo===" + allLostItemInfoList);
//            recerateStockLostItemSerachModel();
//        }
        if (stockLostEntity.getReferenceNumber() != null) {
            mmsLuDmArchive = stockLostEntity.getReferenceNumber();
            mmsLuDmArchiveList = mmsLuDmArchiveBeanLocal.getFileList(mmsLuDmArchive);
        }
        recreateFileUpload();
    }
    public String generateLostItemNo() {
        if (updateStatus == 1) {
            newLostItemId = stockLostEntity.getLostItemNo();
        } else {
            MmsStockItemLost lastLostItemID = mmsStockItemBeanLocal.getLastStockItemNo();
            if (lastLostItemID != null) {
                lastLostItemId = lastLostItemID.getLostStockId().toString();
            }
            
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            
            int id;
            if (lastLostItemId.equals("0")) {
                id = 1;
                newLostItemId = "StockLostItemNo-" + id + "/" + f.format(now);
            } else {
                
                String[] lastInspNos = lastLostItemId.split("-");
                String lastDatesPatern = lastInspNos[0];
                
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                id = Integer.parseInt(lastDatesPaterns[0]);
                id = id + 1;
                newLostItemId = "StockLostItemNo-" + id + "/" + f.format(now);
            }
        }
        return newLostItemId;
    }
    
    public void WfSave() {
        wfMmsProcessed.setLostStockId(stockLostEntity);
        workFlowInterface.create(wfMmsProcessed);
    }
    
    public void editDataTable() {
        addOrModifyBundle = "Modify";
        stockLostDtlEntity = stkLostAddDetailDataModel.getRowData();
        System.out.println("stockLostDtlEntity.getItemCode()===" + stockLostDtlEntity.getItemCode());
        bincardEntity.setItemCode(stockLostDtlEntity.getItemCode());
        bincardEntity = binCardFacade.findbyitemcode(bincardEntity);
        System.out.println("========item code    ==" + bincardEntity);
        checkMaterialCode.remove(stockLostDtlEntity.getItemCode());
    }
    public List<MmsStoreInformation> getStoreInfoLst() {
        storeInfoLst = storeInfoInterface.findAllStoreInfo();
        return storeInfoLst;
    }

    public List<MmsBinCard> getBinCardDtlList() {
        binCardDtlList = binCardFacade.findAll();
        return binCardDtlList;
    }

    public List<HrDepartments> getDepartmentNames() {

        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();

        return DepartmentNames;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update Methods">
    public void saveStockItemLost() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveStockItemLost", dataset)) {
                if (updateStatus == 0) {
                    
                    if (stkLostAddDetailDataModel.getRowCount() > 0) {
                        
                        try {
                            stockLostEntity.setStockStatus(Constants.PREPARE_VALUE);
                            wfMmsProcessed.setDecision(stockLostEntity.getStockStatus());
                            stockLostEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            System.out.println("=1=");
                            stockLostEntity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                            stockLostEntity.setProcessedBy(SessionBean.getUserId());
                            stockLostEntity.setLostItemNo(newLostItemId);
                            commiteeMembers();
                            System.out.println("===2=====");
                            stockLostEntity.getStockLostList().add(wfMmsProcessed);
                            mmsStockItemBeanLocal.create(stockLostEntity);
                            System.out.println("===create=");
                            JsfUtil.addSuccessMessage("Lost Stock Item information is Saved");
                            clearPage();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JsfUtil.addFatalMessage(" Something Error Occured on Creating the Data ");
                            
                        }
                        
                    } else {
                        
                        JsfUtil.addFatalMessage("Please put atleast one record on the detail form");
                        
                    }
                    
                } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                    
                    try {
                        stockLostEntity.setStockStatus(Constants.PREPARE_VALUE);
                        wfMmsProcessed.setDecision(stockLostEntity.getStockStatus());
                        stockLostEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        stockLostEntity.setProcessedBy(SessionBean.getUserId());
                        mmsStockItemBeanLocal.edit(stockLostEntity);
                        WfSave();
                        
                        JsfUtil.addSuccessMessage("Lost Stock Item information is Updated");
                        clearPage();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage(" Something Error Occured on Updating the Data ");
                        
                    }
                    
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        stockLostEntity.setStockStatus(Constants.APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        stockLostEntity.setStockStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        stockLostEntity.setStockStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        stockLostEntity.setStockStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    WfSave();
                    mmsStockItemBeanLocal.edit(stockLostEntity);
                    mmsLostList.remove(stockLostEntity);
                    
                    clearPage();
                    JsfUtil.addSuccessMessage("Lost Data Modified");
                    clearPage();
                }
                clearPage();
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
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Other Methods">
    public void goBackToSearchpageBtnAction() {
        renderPnlCreateLostItem = false;
        renderpnlToSearchPage=false;
        renderPnlManPage = true;
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DMS service">
    DocumentModel documentModel = new DocumentModel();
    StreamedContent file;

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    DataModel<DocumentModel> docValueModel;

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

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void onRowSelect(SelectEvent event) {
        documentModel = (DocumentModel) event.getObject();
    }
    @Inject
    LostStockDmsUpload lostDmsUpload;

    public void recreateFileUpload() {
        mmsLuDmArchivesDModel = null;
        mmsLuDmArchivesDModel = new ListDataModel<MmsLuDmArchive>();
    }

    public LostStockDmsUpload getLostDmsUpload() {
        if (lostDmsUpload == null) {
            lostDmsUpload = new LostStockDmsUpload();

        }
        return lostDmsUpload;
    }

    public void setLostDmsUpload(LostStockDmsUpload lostDmsUpload) {
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
                stockLostEntity.add(lostDmsUpload);
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

    public StreamedContent downloading() throws Exception {
        System.out.println("when Download");
        streamedContent = dataUpload.getMmsFileRefNumber(mmsLuDmArchive);
        return streamedContent;
    }

    public StreamedContent getDownload() {
        System.out.println("Dowlaod Here");
        if (isFileSelected == true) {
            System.out.println("yes u selected");
            download = dataUpload.getMmsFileRefNumber(mmsLuDmArchive);
        }
        return download;
    }

    public void getDocValue() {
        DMdocumentModel = dataUpload.selectListOfFileByDocId(documentId);
        System.out.println("No of your Doc is " + DMdocumentModel.getRowCount());
    }
    // </editor-fold>
   }