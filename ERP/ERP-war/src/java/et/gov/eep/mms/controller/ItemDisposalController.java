
package et.gov.eep.mms.controller;
//<editor-fold defaultstate="collapsed" desc="Import">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.entity.organization.HrDepartments;
//import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsDisposalItemBeanLocal;
import et.gov.eep.mms.businessLogic.MmsDisposalItemDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsGrnDetBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsBinCardFacade;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
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
@Named(value = "itemDisposalController")
@ViewScoped
public class ItemDisposalController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private MmsBinCardFacade binCardFacade;
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrdepartmentInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsBinCardBeanLocal binCardInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private MmsDisposalItemBeanLocal disposalInterface;
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrDeptInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fxAssetRegDtlInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegInterface;
    @EJB
    private MmsSivDtlBeanLocal sivInterface;
    @EJB
    private MmsGrnDetBeanLocal genDetinterface;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entitities">
    @Inject
    private MmsDisposalItemDtlBeanLocal disposalDtlInterface;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    private MmsDisposalItems disposalEntity;
    @Inject
    private MmsDisposalItemsDtl disposalDtlEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntity;
    @Inject
    private MmsBinCard binCardEntity;
    @Inject
    private MmsItemRegistration itemRegistration;
    @Inject
    private MmsGrnDetail grndetail;
    @Inject
    private FmsDprOfficeAsset fmsDprEntity;
    @Inject
    private MmsSivDetail sivDtlEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare lists and models">
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    private DataModel<MmsDisposalItemsDtl> dispposalAddDetailDataModel;
    private DataModel<MmsDisposalItems> mmsDispposalSearchInfoDataModel;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<MmsDisposalItems> allReturnInfoList;
    List<MmsDisposalItems> checkerList = new ArrayList<>();
    List<MmsDisposalItems> recreatedList = new ArrayList<>();
    List<MmsStoreInformation> storeInfoList = new ArrayList<>();
    List<MmsDisposalItemsDtl> tagNoDtl = new ArrayList<>();
    List<MmsFixedassetRegstDetail> fixedAssetDtlList = new ArrayList<>();
    List<MmsGrnDetail> grnDtlList = new ArrayList<>();
    int bookvalue = 0;
    List<MmsBinCard> nameList = new ArrayList<>();
    List<MmsBinCard> itemcodeDtl = new ArrayList<>();
    List<MmsBinCard> binCardDtlList = new ArrayList<>();
    private static List<HrDepartments> araListe;
    private List<MmsDisposalItems> mmsDispList;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList = new ArrayList<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare Fields">
    String selectedValue = "";
    private boolean renderDecision = false;
    private MmsDisposalItems hpSelect;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateDisposal = false;
    private boolean isRenderedIconWorkflow = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    Set<String> checkMaterialCode = new HashSet<>();
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private boolean renderFA = false;
    private boolean renderNFA = false;
    private String userName;
    private boolean isRenderedIconNitification = false;
    boolean isRenderCreate;    
    String matSubCat2 = "";
    String matCat2 = "";
    String newDisposalId;
    String lastDisposalId = "0";
    private String addOrModifyBundle = "Add";
    int binlID;
    String matName;
    String unitM;
    String matCode;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {

        if (workflow.isApproveStatus()) {
            
            mmsDispList = disposalInterface.findDispListByWfStatus(Constants.PREPARE_VALUE);
            disposalDtlEntity = new MmsDisposalItemsDtl();
            dispposalAddDetailDataModel = new ArrayDataModel<>();
            System.out.println("app or check");
            isRenderCreate = false;
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsDispList = disposalInterface.findDispListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            System.out.println("prep");
            renderDecision = false;
            isRenderCreate = true;
            if (mmsDispList.size() > 0) {
                isRenderedIconNitification = true;
            } else {
                isRenderedIconNitification = false;
                 ColumnNameResolverList = disposalInterface.getColumnNameList();
            }
        }
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        allDepartmentsList = hrDeptInterface.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
//        ColumnNameResolverList = disposalInterface.getColumnNameList();
//        workFlow.setProcessedBy(SessionBean.getUserId());
//        setUserName(SessionBean.getUserName());
//        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of objects">
          public MmsGrnDetail getGrndetail() {
                if (grndetail == null) {
                    grndetail = new MmsGrnDetail();
                }
                return grndetail;
            }
        public void setGrndetail(MmsGrnDetail grndetail) {
        this.grndetail = grndetail;
    }
         public MmsDisposalItems getDisposalEntity() {
        if (disposalEntity == null) {
            disposalEntity = new MmsDisposalItems();
        }
        return disposalEntity;
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

    public MmsItemRegistration getItemRegistration() {
        if (itemRegistration == null) {
            itemRegistration = new MmsItemRegistration();
        }
        return itemRegistration;
    }
    public void setItemRegistration(MmsItemRegistration itemRegistration) {
        this.itemRegistration = itemRegistration;
    }

    public MmsDisposalItems getHpSelect() {
        return hpSelect;
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

    public void setHpSelect(MmsDisposalItems hpSelect) {
        this.hpSelect = hpSelect;
    }

    public void setDisposalEntity(MmsDisposalItems disposalEntity) {
        this.disposalEntity = disposalEntity;
    }

    public MmsBinCard getBinCardEntity() {
        if (binCardEntity == null) {
            binCardEntity = new MmsBinCard();
        }
        return binCardEntity;
    }

    public void setBinCardEntity(MmsBinCard binCardEntity) {
        this.binCardEntity = binCardEntity;
    }

    public MmsDisposalItemsDtl getDisposalDtlEntity() {
        if (disposalDtlEntity == null) {
            disposalDtlEntity = new MmsDisposalItemsDtl();
        }

        return disposalDtlEntity;
    }

    public void setDisposalDtlEntity(MmsDisposalItemsDtl disposalDtlEntity) {
        this.disposalDtlEntity = disposalDtlEntity;
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

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public FmsDprOfficeAsset getFmsDprEntity() {
        return fmsDprEntity;
    }

    public void setFmsDprEntity(FmsDprOfficeAsset fmsDprEntity) {
        this.fmsDprEntity = fmsDprEntity;
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

//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of Variables">
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

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }
     public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
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
     public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and settter Lists and model ">
    public DataModel<WfMmsProcessed> getWfMmsProcessedDataModel() {
        return WfMmsProcessedDataModel;
    }
    
    public void setWfMmsProcessedDataModel(DataModel<WfMmsProcessed> WfMmsProcessedDataModel) {
        this.WfMmsProcessedDataModel = WfMmsProcessedDataModel;
    }
      public List<MmsDisposalItems> getMmsDispList() {
        return mmsDispList;
    }

    public void setMmsDispList(List<MmsDisposalItems> mmsDispList) {
        this.mmsDispList = mmsDispList;
    }
     public List<HrDepartments> getDepartmentNames() {
        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();
        return DepartmentNames;
    }

    public void setDepartmentNames(List<HrDepartments> DepartmentNames) {
        this.DepartmentNames = DepartmentNames;
    }

    public DataModel<MmsDisposalItemsDtl> getDispposalAddDetailDataModel() {
        if (dispposalAddDetailDataModel == null) {
            dispposalAddDetailDataModel = new ListDataModel<>();
        }
        return dispposalAddDetailDataModel;
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

    public void setDispposalAddDetailDataModel(DataModel<MmsDisposalItemsDtl> dispposalAddDetailDataModel) {
        this.dispposalAddDetailDataModel = dispposalAddDetailDataModel;
    }

    public DataModel<MmsDisposalItems> getMmsDispposalSearchInfoDataModel() {
        if (mmsDispposalSearchInfoDataModel == null) {
            mmsDispposalSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsDispposalSearchInfoDataModel;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public void setMmsDispposalSearchInfoDataModel(DataModel<MmsDisposalItems> mmsDispposalSearchInfoDataModel) {
        this.mmsDispposalSearchInfoDataModel = mmsDispposalSearchInfoDataModel;
    }
     public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderFA() {
        return renderFA;
    }

    public void setRenderFA(boolean renderFA) {
        this.renderFA = renderFA;
    }

    public boolean isRenderNFA() {
        return renderNFA;
    }

    public void setRenderNFA(boolean renderNFA) {
        this.renderNFA = renderNFA;
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
 public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
    public List<ColumnNames> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNames> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Constructor">
    public ItemDisposalController() {
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ADD and RecreateModel">
    public String addDisposalDetail() {

        disposalEntity.addDisposalItemDetail(disposalDtlEntity);
        recreateModelDetail();
        clearPopUp();
        return null;
    }

    private void recreateModelDetail() {
        dispposalAddDetailDataModel = null;
        dispposalAddDetailDataModel = new ListDataModel(new ArrayList<>(disposalEntity.getMmsDisposalItemsDtlList()));
    }

    private void recerateDispSerachModel2() {
        mmsDispposalSearchInfoDataModel = null;
        mmsDispposalSearchInfoDataModel = new ListDataModel(recreatedList);
    }

    private void recerateDispSerachModel() {
        mmsDispposalSearchInfoDataModel = null;
        mmsDispposalSearchInfoDataModel = new ListDataModel(new ArrayList<>(allReturnInfoList));
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        WfMmsProcessedDataModel = new ListDataModel(disposalEntity.getDisposalItemList());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RowSelection and Update">
    public void rowSelect(SelectEvent event) {
        disposalEntity = (MmsDisposalItems) event.getObject();
        disposalEntity.setDisposalId(disposalEntity.getDisposalId());
        disposalEntity = disposalInterface.getSelectedRequest(disposalEntity.getDisposalId());
        populateUI();
        disposalDtlEntity = new MmsDisposalItemsDtl();
    }
    public void EditDisposalInfo() {
        addOrModifyBundle = "Modify";
        disposalDtlEntity = dispposalAddDetailDataModel.getRowData();
        disposalEntity = disposalDtlEntity.getDisposalId();
    }
    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                disposalEntity = (MmsDisposalItems) event.getNewValue();
                System.out.println(" ------ Data ------- " + disposalEntity.getRequisitionDate());
                populateUI();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    public void onRowEdit(RowEditEvent event) {
        int size = disposalEntity.getWfMmsProcessedList().size();
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            System.out.println("=========checker approve=====" + disposalEntity.getWfMmsProcessedList().get(size - 1).getDecision());
            if (disposalEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_APPROVE_VALUE || disposalEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (disposalEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_REJECT_VALUE || disposalEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            workFlow.setProcessedOn(disposalEntity.getWfMmsProcessedList().get(size - 1).getProcessedOn());

        } else {
            if (size > 1) {
                System.out.println("========inside if ======" + workflow.isReadonly());

                workflow.setReadonly(true);
                System.out.println("=========inside if 2====" + workflow.isReadonly());
                workflow.setButtonStatus(true);
            }
        }
        renderPnlCreateDisposal = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = mmsDispposalSearchInfoDataModel.getRowIndex();
        disposalEntity = checkerList.get(rowIndex);
        hrDepartmentsEntity.setDepId(disposalEntity.getDepartment().getDepId());
        storeInfoEntity.setStoreId(disposalEntity.getStoreId().getStoreId());
        recreateModelDetail();
    }
    public void populateUI() {
        renderPnlCreateDisposal = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        setIsRenderedIconNitification(true);
        recreateModelDetail();
        recreateWfDataModel();
        renderPnlCreateDisposal = true;
        renderPnlManPage = false;       
        disposalEntity = disposalInterface.getSelectedRequest(disposalEntity.getDisposalId());
        storeInfoEntity = disposalEntity.getStoreId();
        hrDepartmentsEntity = disposalEntity.getDepartment();
        if (workflow.isPrepareStatus()) {
            workFlow.setProcessedOn(disposalEntity.getProcessedOn());
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search and Save methods">
    public void searchDispInformation1() {
         System.out.println("in search");
        disposalEntity.setRequestedBy(SessionBean.getUserId());
        System.out.println("processor " + disposalEntity.getRequestedBy());
        recreatedList = disposalInterface.getItemdisposalListsByParameter(disposalEntity);
          recerateDispSerachModel2();
//       allReturnInfoList = new ArrayList<>();
//        disposalEntity.setRequestedBy(SessionBean.getUserId());
//         System.out.println("searching");
//        try {
//            if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
//                if (workflow.isApproveStatus()) {
//                    recreatedList = disposalInterface.getItemdisposalListsByParameter(disposalEntity);
//                    recerateDispSerachModel2();
//                }
//                
//            } else {
//                disposalEntity.setRequestedBy(SessionBean.getUserId());
//                System.out.println("try");
//                if ((disposalEntity == null) && (disposalDtlEntity == null)) {
//                    recreatedList = disposalInterface.findAllInfo();
//                    System.out.println("===allfind=====" + recreatedList);
//                    recerateDispSerachModel2();
//                } else if ((disposalEntity != null) && (disposalDtlEntity == null)) {
//                    System.out.println("====disposalNo==" + disposalEntity.getDisposalNo());
//                    String str = disposalEntity.getDisposalNo();
//                    disposalEntity.setDisposalNo(str);
//                    recreatedList = disposalInterface.getItemdisposalListsByParameter(disposalEntity);
//                    checkerList.clear();
//                    checkerList = recreatedList;
//                    recerateDispSerachModel2();
//                    System.out.println("====disposalNo==" + recreatedList);
//                } else {
//                    String str = disposalDtlEntity.getTagNo();
//                    recreatedList = disposalInterface.searchDisposalByParameterPrefixAndItemDispPrep(disposalEntity);
//                    disposalEntity = disposalDtlEntity.getDisposalId();
//                    System.out.println("====disposalNo==" + recreatedList);
//                    recerateDispSerachModel2();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JsfUtil.addFatalMessage("Sorry ,Please Reload the page and try again.");
//        }
    }
    
    
    public String generateDispNo() {
        if (updateStatus == 1) {
            newDisposalId = disposalEntity.getDisposalNo();
        } else {
            MmsDisposalItems lastInsuranceID = disposalInterface.getLastDisposalId();
            if (lastInsuranceID != null) {
                lastDisposalId = lastInsuranceID.getDisposalId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int id = 0;
            if (lastDisposalId.equals("0")) {
                id = 1;
                newDisposalId = "FADisposalNo-" + id + "/" + f.format(now);
            } else {
                
                String[] lastInspNos = lastDisposalId.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                id = Integer.parseInt(lastDatesPaterns[0]);
                id = id + 1;
                newDisposalId = "FADisposalNo-" + id + "/" + f.format(now);
            }
        }
        return newDisposalId;
    }
    public void goBackToSearchpageBtnAction() {
        renderPnlCreateDisposal = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }
    public void createNewDisposalInfo() {
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
        workFlow.setDisposalId(disposalEntity);
        workFlowInterface.create(workFlow);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update Methods">
    public void saveFAItemDisposal() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveFAItemDisposal", dataset)) {
                if (updateStatus == 0) {
                    if (dispposalAddDetailDataModel.getRowCount() > 0) {
                        try {
                            disposalEntity.setDisposalNo(newDisposalId);
                            disposalEntity.setdStatus(Constants.PREPARE_VALUE);
                            workFlow.setDecision(Constants.PREPARE_VALUE);
                            disposalEntity.setRequestedBy(workFlow.getProcessedBy());
                            disposalEntity.setProcessedOn(workFlow.getProcessedOn());
                            disposalEntity.setRemark(workFlow.getCommentGiven());
                            disposalEntity.getDisposalItemList().add(workFlow);
                            disposalEntity.getMmsDisposalItemsDtlList().add(disposalDtlEntity);
                            disposalInterface.create(disposalEntity);
                            JsfUtil.addSuccessMessage(" A New Fixed Asset Disposal is Saved! ");
                            clearPage();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JsfUtil.addFatalMessage("Something Error Occured on Data Saved");
                            
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Put atleast One Record on the Detail Form .");
                        
                    }
                } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                    
                    try {
                        System.out.println("===edit==" + disposalDtlEntity.getDescription());
                        disposalEntity.setRequestedBy(workFlow.getProcessedBy());
                        disposalEntity.setProcessedOn(workFlow.getProcessedOn());
                        disposalEntity.setRemark(workFlow.getCommentGiven());
                        disposalEntity.setdStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(disposalEntity.getdStatus());
                        disposalEntity.getMmsDisposalItemsDtlList().add(disposalDtlEntity);
                        System.out.println("after edit" + disposalDtlEntity.getDescription());
                        disposalInterface.edit(disposalEntity);
                        Wfsave();
                        clearPage();
                        JsfUtil.addSuccessMessage("Fixed Asset Disposal information has been Updated");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                    }
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        disposalEntity.setdStatus(Constants.APPROVE_VALUE);
                        workFlow.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        disposalEntity.setdStatus(Constants.CHECK_APPROVE_VALUE);
                        workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        disposalEntity.setdStatus(Constants.APPROVE_REJECT_VALUE);
                        workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        disposalEntity.setdStatus(Constants.CHECK_REJECT_VALUE);
                        workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    workFlow.setDisposalId(disposalEntity);
                    workFlowInterface.create(workFlow);
                    disposalInterface.edit(disposalEntity);
                    mmsDispList.remove(disposalEntity);
                    clearPage();
                    JsfUtil.addSuccessMessage("Disposal collection Data Updated");
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

    //<editor-fold defaultstate="collapsed" desc="clear page and clearpopup">
    private void clearPopUp() {
        disposalDtlEntity = null;
        fixedAssetRegDtlEntity = null;
        fixedAssetDtlList = null;
        addOrModifyBundle = "Add";
    }

    public String clearPage() {
        storeInfoEntity = null;
        disposalEntity = null;
        disposalDtlEntity = null;
        dispposalAddDetailDataModel = null;
        mmsDispposalSearchInfoDataModel = null;
        hrDepartmentsEntity = null;
        storeInfoEntity = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        workFlow = null;
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Handeler">
    public ArrayList<MmsDisposalItems> searchByDispno(String Insno) {
        ArrayList<MmsDisposalItems> Dispno = null;
        disposalEntity.setDisposalNo(Insno);
        Dispno = disposalInterface.searchByDispNo(disposalEntity);

        saveorUpdateBundle = "Update";
        return Dispno;
    }

    public List<MmsStoreInformation> getStoreInfoList() {
        storeInfoList = storeInfoInterface.findAllStoreInfo();

        return storeInfoList;
    }

    public void setStoreInfoList(List<MmsStoreInformation> storeInfoList) {
        this.storeInfoList = storeInfoList;
    }

    public SelectItem[] getTagNoDtl() {
        tagNoDtl = disposalDtlInterface.findAllTagNo();
        return JsfUtil.getSelectItems(tagNoDtl, true);
    }

    public SelectItem[] getFixedAssetDtlInfo() {
        fixedAssetDtlList = fxAssetRegDtlInterface.findAllTagNo();
        return JsfUtil.getSelectItems(fixedAssetDtlList, true);
    }

    public List<MmsFixedassetRegstDetail> getFixedAssetDtlList() {
        fixedAssetDtlList = fxAssetRegDtlInterface.findAllTagNo();
        return fixedAssetDtlList;
    }

    public void setFixedAssetDtlList(List<MmsFixedassetRegstDetail> fixedAssetDtlList) {
        this.fixedAssetDtlList = fixedAssetDtlList;
    }

    public void handleTagNo(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            fixedAssetRegDtlEntity = (MmsFixedassetRegstDetail) event.getNewValue();
            disposalDtlEntity.setTagNo(fixedAssetRegDtlEntity.getTagNo());
            disposalDtlEntity.setItemName(fixedAssetRegDtlEntity.getItemName());
            disposalDtlEntity.setItemStatus(fixedAssetRegDtlEntity.getItemStatus());
            disposalDtlEntity.setAccountCode(fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            disposalDtlEntity.setUnitPrice(fixedAssetRegDtlEntity.getUnitPrice());
            System.out.println("price " + disposalDtlEntity.getUnitPrice());
            disposalDtlEntity.setQuantity(fixedAssetRegDtlEntity.getItemId().getQuantity());
            System.out.println("-----------quantity---" + fixedAssetRegDtlEntity.getItemId().getQuantity());
            System.out.println("=====quantity ====" + disposalDtlEntity.getQuantity());
            System.out.println("=====net book====" + fmsDprEntity.getNetBookValue());
            List<MmsFixedassetRegstDetail> TransNo = null;
            TransNo = fxAssetRegDtlInterface.findByTagNo2(fixedAssetRegDtlEntity);

        }
    }

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int storeIds = Integer.valueOf(event.getNewValue().toString());
            storeInfoEntity.setStoreId(storeIds);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);
            itemRegistration.setStoreId(storeInfoEntity);
            itemRegistration.setItemGroup(0);
            itemcodeDtl = binCardInterface.getItemCodeByStoreAndItemGroup(itemRegistration);
            disposalEntity.setStoreId(storeInfoEntity);

        }
    }

public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            disposalEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
        }
}

 
    public void handleDtlTagNo(ValueChangeEvent e) {
        fixedAssetRegDtlEntity = (MmsFixedassetRegstDetail) e.getNewValue();

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
        hrDepartmentsEntity = hrDeptInterface.findByDepartmentId(hrDepartmentsEntity);
        disposalEntity.setDepartment(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");
    }

    public List<MmsGrnDetail> getGrnDtlList() {
        grnDtlList = genDetinterface.findAllGrnInfo();
        return grnDtlList;
    }

    public void handleSelectItemCode2(ValueChangeEvent event) {
        String itemid = event.getNewValue().toString();
        int dtlId = Integer.parseInt(itemid);
        MmsGrnDetail grndtl = new MmsGrnDetail();
        grndtl.setGrnDetailId(dtlId);
        grndetail = null;
        grndetail = new MmsGrnDetail();
        grndetail = genDetinterface.findInfoById(grndtl);
        disposalDtlEntity.setItemCode(grndetail.getItemId().getMatCode());
        disposalDtlEntity.setItemName(grndetail.getItemId().getMatName());
        disposalDtlEntity.setQuantity(grndetail.getItemId().getQuantity());
        disposalDtlEntity.setUnitPrice(grndetail.getUnitPrice());
        disposalDtlEntity.setItemStatus(grndetail.getItemId().getMatStatus().toString());
        disposalDtlEntity.setBookValue(bookvalue);
        matName = disposalDtlEntity.getItemName();
        matCode = disposalDtlEntity.getItemCode();
        System.out.println("--------ItemCode---------  " + grndetail.getItemId().getMatCode());
        System.out.println("--------ItemCode---------  " + disposalDtlEntity.getItemCode());
        System.out.println("--------ItemName------  " + grndetail.getItemId().getMatName());
        System.out.println("--------ItemName------  " + disposalDtlEntity.getItemName());
        System.out.println("--------ItemQuantity------  " + disposalDtlEntity.getQuantity());
    }

    public void changeItemDisposal(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            disposalDtlEntity = null;
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {

                setRenderFA(true);
                setRenderNFA(false);

            } else {

                setRenderFA(false);
                setRenderNFA(true);

            }

        }

    }

    public List<MmsBinCard> getItemcodeDtl() {
        return itemcodeDtl;
    }

    public List<MmsBinCard> getBinCardDtlList() {
        binCardDtlList = binCardFacade.findAll();
        return binCardDtlList;
    }

    public void setItemcodeDtl(List<MmsBinCard> itemcodeDtl) {
        this.itemcodeDtl = itemcodeDtl;
    }

    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
//
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(depIds);
            hrDepartmentsEntity = hrDeptInterface.findByDepartmentId(hrDepartmentsEntity);
            disposalEntity.setDepartment(hrDepartmentsEntity);
        }
    }
 
 
    public void handleBincard(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            disposalDtlEntity = (MmsDisposalItemsDtl) event.getNewValue();
            disposalDtlEntity.setItemCode(event.getNewValue().toString());
            binCardEntity = (MmsBinCard) event.getNewValue();
            disposalDtlEntity.setUnitPrice(binCardEntity.getUnitPrice());
            disposalDtlEntity.setItemName(binCardEntity.getMaterialId().getMatName());
            disposalDtlEntity.setItemName(binCardEntity.getMaterialId().getMatName());
            disposalDtlEntity.setBookValue(bookvalue);
            disposalDtlEntity.setItemCode(binCardEntity.getMaterialId().getMatCode());
        }
    }

    public void handleSelectItemCode1(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
            itemRegistration = (MmsItemRegistration) event.getNewValue();
            System.out.println("item id =====" + itemRegistration.getMaterialId());
            binCardEntity = binCardInterface.getItemInfoByItemId(itemRegistration);
            binlID = binCardEntity.getCardNo();
            binlID = binCardEntity.getCardNo();
        }
    }

    public void handleSelectItemCode(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            System.out.println("====handler==");
            disposalDtlEntity.setUnitPrice(binCardEntity.getUnitPrice());
            disposalDtlEntity.setItemCode(event.getNewValue().toString());
            System.out.println("item id =====" + itemRegistration.getMaterialId());
            binCardEntity = binCardInterface.getItemInfoByItemId(itemRegistration);
            binlID = binCardEntity.getCardNo();
            itemRegistration = itemRegInterface.getMmsItemInfoByMatId(itemRegistration);
            System.out.println("====matId===" + itemRegistration.getItemType());
            disposalDtlEntity.setItemId(itemRegistration);
            matCat2 = itemRegistration.getMatCategory().getCatCode();
            itemRegistration.setMaterialId(disposalDtlEntity.getItemId().getMaterialId());
            matSubCat2 = itemRegistration.getMatSubcategory().getSubcatCode();
            disposalDtlEntity.setItemName(itemRegistration.getMatName());

        }
        
    }
//</editor-fold>
   }
