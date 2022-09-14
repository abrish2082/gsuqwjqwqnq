package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetTypeBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreReqBeanLocal;
import et.gov.eep.mms.entity.MmsFaAssetType;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
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
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.SelectEvent;
import javax.xml.namespace.QName;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
//</editor-fold>

@Named(value = "fixedAssetRegController")
@ViewScoped
public class FixedAssetRegController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private MmsFixedAssetRegistrationBeanLocal fixedAssetInterface;

    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fixedAssetDtlInterface;
    @EJB
    private MmsFixedAssetTypeBeanLocal assetTypeInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemregistrationInterface;
    @EJB
    private MmsSivBeanLocal sivInterface;
    @EJB
    private MmsSivDtlBeanLocal sivDtlBeanLocal;
    @EJB
    private MmsFixedAssetTypeBeanLocal fixedAssetTypeInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fxAssetRegDtlInterface;
    @EJB
    private HrDepartmentsBeanLocal hrdepartmentInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegInterface;
    @EJB
    MmsStoreReqBeanLocal storeReqInterface;
    @EJB
    HREmployeesBeanLocal hrEmployeeInterface;
    @EJB
    private FmsLuSystemBeanLocal luSystemInterface;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    HREmployeesBeanLocal hrInterface;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inject Entities">
    @Inject
    private MmsFixedassetRegstration fixedAssetRegEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    HrEmployees employeEntity;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntity;
    @Inject
    private MmsFixedassetRegstration fixedAssetRegEntityOLD;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntityOLD;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    HrEmployees hrEmployeesEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private FmsDprOfficeAsset fmsDprEntity;
    @Inject
    FmsGeneralLedger fmsGeneralLedgarEntity;
    @Inject
    private MmsFaAssetType assetTypeEntity;
    @Inject
    private MmsItemRegistration itemRegistrationEntity;
    @Inject
    private MmsSiv sivs;
    @Inject
    private MmsSivDetail sivDetail;
    @Inject
    private FmsLuSystem luSystemEntity;
    @Inject
    private MmsStorereq StoreRequisitionEntity;
    @Inject
    private MmsGrn grnEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    List<MmsSiv> srNoLists;
    List<MmsStorereq> srNoListsWhenOld;
    List<MmsStorereq> srNoListsWhenPrve;
    List<MmsSivDetail> itemCodesWhenNewLists;
     @Inject
    ColumnNameResolver columnNameResolver;
    private List<MmsFixedassetRegstration> mmsFixedassetrColumnNameList;
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare lists and models">
    private DataModel<MmsFixedassetRegstDetail> mmsFixedAssetRegDtlDataModel;
    private DataModel<MmsFixedassetRegstDetail> mmsFixedAssetRegDtlDataModelOLD;
    private DataModel<MmsFixedassetRegstDetail> mmsFixedAssetRegDtlDataModelPrev;
    private DataModel<MmsFixedassetRegstration> mmsFixedAssetRegSearchDataModel;
    List<HrDepartments> departmentNames = new ArrayList<>();
    String sysName = "";
    String sysName2 = "";
    ArrayList<FmsLuSystem> luList;
    List<MmsSiv> sivNoLists;
    List<MmsItemRegistration> ItemCodeList = new ArrayList<>();
    String matCat = "";
    String matSubCat = "";
    String matCat2 = "";
    String matSubCat2 = "";
    List<MmsItemRegistration> Item = null;
    Set<String> checkMaterialCode = new HashSet<>();
    List<MmsFixedassetRegstDetail> newDetailList = new ArrayList<>();
    List<MmsFaAssetType> AssetTypeList = new ArrayList<>();
    List<MmsFixedassetRegstDetail> TagListFromDB = new ArrayList<>();
    List<MmsFixedassetRegstDetail> fixedAssetDtlList = new ArrayList<>();
    List<MmsFixedassetRegstration> allFARInfoList;
    List<MmsItemRegistration> itemCodeListPop;
    ArrayList<HrEmployees> employeeInformations = null;
    List<FmsGeneralLedger> gLCodeList = new ArrayList<>();
    List<MmsItemRegistration> nameList = new ArrayList<>();
    List<FmsLuSystem> subSystemCodeList = new ArrayList<>();
    List<MmsItemRegistration> Item2 = null;
    private List<MmsFixedassetRegstration> mmsFarList;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    private static List<HrDepartments> araListe;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    private String selectOptFARNo;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String CheckItemType = "3";
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateFAR = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchpage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private boolean renderOld = false;
    private boolean renderNew = true;
    boolean renderOldPreviw = false;
    private boolean renderPrevious = false;
    private boolean selectOprDtlRadio = false;
    boolean isRenderCreate;
    private MmsFixedassetRegstration hpSelect;
    Integer deptName;
    Integer deptName2;
    String newFarNo;
    String lastFarNumber = "0";
    String FrNo;
    String newTagNo;
    String lastTagNumber = "0";
    String newTagNo2;
    String lastTagNumber2 = "0";
    String assetType = "";
    String assetType2 = "";
    String unitprice = "Unit Price";
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private String userName;
    String itemStatusWhenNew = "New";
    private String addOrModifyBundle = "Add";
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    String selectedValue = "";
    private boolean renderDecision = false;
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="@PostConstructment">
    @PostConstruct
    public void init() {
       ColumnNameResolverList = fixedAssetInterface.getColumnNameList();
        ItemStausBarChartCreator();
        if (workflow.isApproveStatus()) {
            System.out.println("app or check");
            mmsFarList = fixedAssetInterface.findFarListByWfStatus(Constants.PREPARE_VALUE);
            renderDecision = true;
            isRenderCreate = false;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsFarList = fixedAssetInterface.findFarListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            System.out.println("prep");
            renderDecision = false;
            isRenderCreate = true;
            if (CheckItemType.equals("3")) {
                System.out.println("yeah new");
                fixedAssetRegDtlEntity.setItemStatus(itemStatusWhenNew);
                System.out.println("" + fixedAssetRegDtlEntity.getItemStatus());
            }
            if (mmsFarList.size() > 0) {
                isRenderedIconNitification = true;
            } else {
                isRenderedIconNitification = false;
            }
        }
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("=======SessionBean" + SessionBean.getUserId());
        System.out.println("=======SessionBean" + workFlow.getProcessedBy());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        departmentNames = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);  
        recursive(departmentNames, 0, root);
        root2 = getRoot();
         
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Constructor">
    public FixedAssetRegController() {
    }

//    WorkFlow workflow = new WorkFlow();
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateFAR() {
        return renderPnlCreateFAR;
    }

    public void setRenderPnlCreateFAR(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateFAR = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getSelectOptFARNo() {
        return selectOptFARNo;
    }

    public void setSelectOptFARNo(String selectOptFARNo) {
        this.selectOptFARNo = selectOptFARNo;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public boolean isSelectOprDtlRadio() {
        return selectOprDtlRadio;
    }

    public void setSelectOprDtlRadio(boolean selectOprDtlRadio) {
        this.selectOprDtlRadio = selectOprDtlRadio;
    }

    public void setCheckItemType(String CheckItemType) {
        this.CheckItemType = CheckItemType;
    }

    public boolean isRenderOld() {
        return renderOld;
    }

    public void setRenderOld(boolean renderOld) {
        this.renderOld = renderOld;
    }

    public boolean isRenderNew() {
        return renderNew;
    }

    public void setRenderNew(boolean renderNew) {
        this.renderNew = renderNew;
    }

    public boolean isRenderOldPreviw() {
        return renderOldPreviw;
    }

    public void setRenderOldPreviw(boolean renderOldPreviw) {
        this.renderOldPreviw = renderOldPreviw;
    }

    public boolean isRenderPrevious() {
        return renderPrevious;
    }

    public void setRenderPrevious(boolean renderPrevious) {
        this.renderPrevious = renderPrevious;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getFrNo() {
        return FrNo;
    }

    public void setFrNo(String FrNo) {
        this.FrNo = FrNo;
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
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of objects">
    public MmsGrn getGrnEntity() {
        if (grnEntity == null) {
            grnEntity = new MmsGrn();
        }
        return grnEntity;
    }

    public void setGrnEntity(MmsGrn grnEntity) {
        this.grnEntity = grnEntity;
    }

    public HrEmployees getEmployeEntity() {
        if (employeEntity == null) {
            employeEntity = new HrEmployees();
        }
        return employeEntity;
    }

    public void setEmployeEntity(HrEmployees employeEntity) {
        this.employeEntity = employeEntity;
    }

    public FmsLuSystem getLuSystemEntity() {
        if (luSystemEntity == null) {
            luSystemEntity = new FmsLuSystem();
        }
        return luSystemEntity;
    }

    public void setLuSystemEntity(FmsLuSystem luSystemEntity) {
        this.luSystemEntity = luSystemEntity;
    }

    public FmsDprOfficeAsset getFmsDprEntity() {
        return fmsDprEntity;
    }

    public void setFmsDprEntity(FmsDprOfficeAsset fmsDprEntity) {
        this.fmsDprEntity = fmsDprEntity;
    }

    public MmsFixedassetRegstration getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFixedassetRegstration hpSelect) {
        this.hpSelect = hpSelect;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemStatusWhenNew() {
        return itemStatusWhenNew;
    }

    public void setItemStatusWhenNew(String itemStatusWhenNew) {
        this.itemStatusWhenNew = itemStatusWhenNew;
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

    public MmsFaAssetType getAssetTypeEntity() {
        if (assetTypeEntity == null) {
            assetTypeEntity = new MmsFaAssetType();
        }
        return assetTypeEntity;
    }

    public void setAssetTypeEntity(MmsFaAssetType assetTypeEntity) {
        this.assetTypeEntity = assetTypeEntity;
    }

    public HrEmployees getHrEmployeesEntity() {
        if (hrEmployeesEntity == null) {
            hrEmployeesEntity = new HrEmployees();
        }
        return hrEmployeesEntity;
    }

    public void setHrEmployeesEntity(HrEmployees hrEmployeesEntity) {
        this.hrEmployeesEntity = hrEmployeesEntity;
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

    public FmsGeneralLedger getFmsGeneralLedgarEntity() {
        if (fmsGeneralLedgarEntity == null) {
            fmsGeneralLedgarEntity = new FmsGeneralLedger();
        }
        return fmsGeneralLedgarEntity;
    }

    public void setFmsGeneralLedgarEntity(FmsGeneralLedger fmsGeneralLedgarEntity) {
        this.fmsGeneralLedgarEntity = fmsGeneralLedgarEntity;
    }

    public MmsFixedassetRegstration getFixedAssetRegEntityOLD() {
        if (fixedAssetRegEntityOLD == null) {
            fixedAssetRegEntityOLD = new MmsFixedassetRegstration();
        }
        return fixedAssetRegEntityOLD;
    }

    public void setFixedAssetRegEntityOLD(MmsFixedassetRegstration fixedAssetRegEntityOLD) {
        this.fixedAssetRegEntityOLD = fixedAssetRegEntityOLD;
    }

    public MmsFixedassetRegstDetail getFixedAssetRegDtlEntityOLD() {
        if (fixedAssetRegDtlEntityOLD == null) {
            fixedAssetRegDtlEntityOLD = new MmsFixedassetRegstDetail();
        }
        return fixedAssetRegDtlEntityOLD;
    }

    public void setFixedAssetRegDtlEntityOLD(MmsFixedassetRegstDetail fixedAssetRegDtlEntityOLD) {
        this.fixedAssetRegDtlEntityOLD = fixedAssetRegDtlEntityOLD;
    }

    public MmsFixedassetRegstration getFixedAssetRegEntity() {
        if (fixedAssetRegEntity == null) {
            fixedAssetRegEntity = new MmsFixedassetRegstration();
        }
        return fixedAssetRegEntity;
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

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setFixedAssetRegEntity(MmsFixedassetRegstration fixedAssetRegEntity) {
        this.fixedAssetRegEntity = fixedAssetRegEntity;
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

    public MmsItemRegistration getItemRegistrationEntity() {
        if (itemRegistrationEntity == null) {
            itemRegistrationEntity = new MmsItemRegistration();
        }
        return itemRegistrationEntity;
    }

    public void setItemRegistrationEntity(MmsItemRegistration itemRegistrationEntity) {
        this.itemRegistrationEntity = itemRegistrationEntity;
    }

    public MmsSiv getSivs() {
        if (sivs == null) {
            sivs = new MmsSiv();
        }
        return sivs;
    }

    public void setSivs(MmsSiv sivs) {
        this.sivs = sivs;
    }

    public MmsSivDetail getSivDetail() {
        if (sivDetail == null) {
            sivDetail = new MmsSivDetail();
        }
        return sivDetail;
    }

    public void setSivDetail(MmsSivDetail sivDetail) {
        this.sivDetail = sivDetail;
    }

    public MmsStorereq getStoreRequisitionEntity() {
        if (StoreRequisitionEntity == null) {
            StoreRequisitionEntity = new MmsStorereq();
        }
        return StoreRequisitionEntity;
    }

    public void setStoreRequisitionEntity(MmsStorereq StoreRequisitionEntity) {
        this.StoreRequisitionEntity = StoreRequisitionEntity;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of Lists and models">
    public List<MmsFixedassetRegstration> getMmsFarList() {
        return mmsFarList;
    }

    public void setMmsFarList(List<MmsFixedassetRegstration> mmsFarList) {
        this.mmsFarList = mmsFarList;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
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

    public DataModel<MmsFixedassetRegstDetail> getMmsFixedAssetRegDtlDataModel() {
        if (mmsFixedAssetRegDtlDataModel == null) {
            mmsFixedAssetRegDtlDataModel = new ListDataModel<>();
        }
        return mmsFixedAssetRegDtlDataModel;
    }

    public void setMmsFixedAssetRegDtlDataModel(DataModel<MmsFixedassetRegstDetail> mmsFixedAssetRegDtlDataModel) {
        this.mmsFixedAssetRegDtlDataModel = mmsFixedAssetRegDtlDataModel;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public DataModel<MmsFixedassetRegstration> getMmsFixedAssetRegSearchDataModel() {
        if (mmsFixedAssetRegSearchDataModel == null) {
            mmsFixedAssetRegSearchDataModel = new ListDataModel<>();
        }
        return mmsFixedAssetRegSearchDataModel;
    }

    public void setMmsFixedAssetRegSearchDataModel(DataModel<MmsFixedassetRegstration> mmsFixedAssetRegSearchDataModel) {
        this.mmsFixedAssetRegSearchDataModel = mmsFixedAssetRegSearchDataModel;
    }

    public DataModel<MmsFixedassetRegstDetail> getMmsFixedAssetRegDtlDataModelOLD() {
        if (mmsFixedAssetRegDtlDataModelOLD == null) {
            mmsFixedAssetRegDtlDataModelOLD = new ListDataModel<>();
        }
        return mmsFixedAssetRegDtlDataModelOLD;
    }

    public void setMmsFixedAssetRegDtlDataModelOLD(DataModel<MmsFixedassetRegstDetail> mmsFixedAssetRegDtlDataModelOLD) {
        this.mmsFixedAssetRegDtlDataModelOLD = mmsFixedAssetRegDtlDataModelOLD;
    }

    public DataModel<MmsFixedassetRegstDetail> getMmsFixedAssetRegDtlDataModelPrev() {
        if (mmsFixedAssetRegDtlDataModelPrev == null) {
            mmsFixedAssetRegDtlDataModelPrev = new ListDataModel<>();
        }
        return mmsFixedAssetRegDtlDataModelPrev;
    }

    public void setMmsFixedAssetRegDtlDataModelPrev(DataModel<MmsFixedassetRegstDetail> mmsFixedAssetRegDtlDataModelPrev) {
        this.mmsFixedAssetRegDtlDataModelPrev = mmsFixedAssetRegDtlDataModelPrev;
    }

    public String getCheckItemType() {
        return CheckItemType;
    }

    public List<MmsFaAssetType> getAssetsTypeist() {
        AssetTypeList = fixedAssetTypeInterface.findAll();
        return AssetTypeList;
    }

    public List<MmsFixedassetRegstDetail> getTagListFromDB() {
        TagListFromDB = fxAssetRegDtlInterface.findAllTagNo();
        return TagListFromDB;
    }

    public void setTagListFromDB(List<MmsFixedassetRegstDetail> TagListFromDB) {
        this.TagListFromDB = TagListFromDB;
    }

    public List<MmsFixedassetRegstDetail> getFixedAssetDtlList() {
        fixedAssetDtlList = fxAssetRegDtlInterface.findAllTagNoForOld();
        System.out.println("==========fixedAssetDtlList==========" + fixedAssetDtlList.size());
        return fixedAssetDtlList;
    }

    public void setFixedAssetDtlList(List<MmsFixedassetRegstDetail> fixedAssetDtlList) {
        this.fixedAssetDtlList = fixedAssetDtlList;
    }

    public List<MmsItemRegistration> getItemCodeListPop() {
        if (itemCodeListPop == null) {
            itemCodeListPop = new ArrayList<>();
        }
        return itemCodeListPop;
    }

    public void setItemCodeListPop(List<MmsItemRegistration> itemCodeListPop) {
        this.itemCodeListPop = itemCodeListPop;
    }

    public List<FmsGeneralLedger> getGlCodeList() {
        gLCodeList = fmsGeneralLedgerBeanLocal.getGLALL();
        return gLCodeList;
    }

    public List<HrDepartments> getDepartmentNames() {
        if (departmentNames == null) {
            departmentNames = new ArrayList<>();
            departmentNames = hrdepartmentInterface.findAllDepartmentInfo();
        }

        return departmentNames;
    }

    public void setDepartmentNames(List<HrDepartments> departmentNames) {
        this.departmentNames = departmentNames;
    }

    public List<FmsLuSystem> getSystemCodelist() {
        subSystemCodeList = luSystemInterface.findAll();
        return subSystemCodeList;
    }

    public List<MmsItemRegistration> getItemCodeList() {
        ItemCodeList = itemRegInterface.findAllItemInfo();
        return ItemCodeList;
    }

    public SelectItem[] getSIVMaterialList() {

        return JsfUtil.getSelectItems(sivs.getMmsSivDetailList(), true);
    }

    public List<MmsFixedassetRegstration> getMmsFixedassetrColumnNameList() {
        if (mmsFixedassetrColumnNameList == null) {
            mmsFixedassetrColumnNameList = new ArrayList<>();
            mmsFixedassetrColumnNameList = fixedAssetInterface.getMmsFixedAssetRColumnNameList();
        }
        return mmsFixedassetrColumnNameList;
    }

    public void setMmsFixedassetrColumnNameList(List<MmsFixedassetRegstration> mmsFixedassetrColumnNameList) {
        this.mmsFixedassetrColumnNameList = mmsFixedassetrColumnNameList;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handle Selection">
//Handle Selection and event
    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            hrDepartmentsEntity.setDepName(event.getNewValue().toString());
            String name = event.getNewValue().toString();
            Integer id = Integer.parseInt(name);
            hrDepartmentsEntity.setDepId(id);
            hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
            deptName = hrDepartmentsEntity.getDepId();
            deptName2 = hrDepartmentsEntity.getDepId();

            generateTagNo();
            generateTagNo2();
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        System.out.println("node clicked");
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        fixedAssetRegEntity.setDepartment(hrDepartmentsEntity);
        srNoListsWhenOld = fixedAssetInterface.getsrNoListsByTag(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");

    }

    public void SystemChange(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            System.out.println("id===" + luSystemEntity.getSystemId());
            System.out.println("code==" + luSystemEntity.getSystemCode());
            sysName = luSystemEntity.getSystemName();
            System.out.println("code" + sysName);
            sysName2 = luSystemEntity.getSystemName();
            fixedAssetRegEntity.setSystemNo(luSystemEntity);
            generateTagNo();
            generateTagNo2();

        }
    }
public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            fixedAssetRegEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }
    public List<MmsSiv> getSrNoLists() {
        if (srNoLists == null) {
            srNoLists = new ArrayList<>();
        }
        return srNoLists;
    }

    public void setSrNoListsWhenOld(List<MmsStorereq> srNoListsWhenOld) {
        this.srNoListsWhenOld = srNoListsWhenOld;
    }

    public List<MmsSiv> getSivNoLists() {
        sivNoLists = sivInterface.findSivList();
        return sivNoLists;
    }

    public void setSivNoLists(List<MmsSiv> sivNoLists) {
        this.sivNoLists = sivNoLists;
    }

    public List<MmsSivDetail> getItemCodesWhenNewLists() {
        if (itemCodesWhenNewLists == null) {
            itemCodesWhenNewLists = new ArrayList<>();
        }
        return itemCodesWhenNewLists;
    }

    public void setItemCodesWhenNewLists(List<MmsSivDetail> itemCodesWhenNewLists) {
        this.itemCodesWhenNewLists = itemCodesWhenNewLists;
    }

    public void setSrNoLists(List<MmsSiv> srNoLists) {
        this.srNoLists = srNoLists;
    }

    public List<MmsStorereq> getSrNoListsWhenOld() {
        if (srNoListsWhenOld == null) {
            srNoListsWhenOld = new ArrayList<>();
        }
        return srNoListsWhenOld;
    }

    public List<MmsStorereq> getSrNoListsWhenPrve() {
        if (srNoListsWhenPrve == null) {
            srNoListsWhenPrve = new ArrayList<>();
            srNoListsWhenPrve = storeReqInterface.findSrList();
        }
        return srNoListsWhenPrve;
    }

    public void setSrNoListsWhenPrve(List<MmsStorereq> srNoListsWhenPrve) {
        this.srNoListsWhenPrve = srNoListsWhenPrve;
    }

    public void handleSelectSIVNo11(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("after selecteed");
            sivs = (MmsSiv) event.getNewValue();
            System.out.println("sr no " + sivs.getSivId());
            fixedAssetRegDtlEntity.setSivId(sivs);
            sivs = sivInterface.getSivInfoBySivNo(sivs);
            System.out.println("sv1=====" + sivs);//366
            StoreRequisitionEntity = sivs.getStoreReqId();//242
            System.out.println("sv2=====" + StoreRequisitionEntity);
            employeEntity = StoreRequisitionEntity.getRequestedBy();
            System.out.println("sv3=====" + employeEntity);//588
            fixedAssetRegDtlEntity.setSivId(sivs);
            fixedAssetRegDtlEntity.setSrNo(sivs.getSrNo());  //Added
            itemCodesWhenNewLists = fixedAssetInterface.getItemBySivId(sivs);
            System.out.println("========siv item code for new" + itemCodesWhenNewLists);
            fixedAssetRegDtlEntity.setTagNo(newTagNo2);
            String sivNo = event.getNewValue().toString();
            System.out.println("=====item name==" + itemCodesWhenNewLists);

        }

    }

    public void sivChangeValue(ValueChangeEvent changeEvent) {
        if (changeEvent != null) {
            sivs = (MmsSiv) changeEvent.getNewValue();
            System.out.println("siv id===" + sivs.getSivNo());
            sivs = fixedAssetInterface.getFixedSrNo(sivs);
        }
    }

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fixedAssetRegEntity.setColumnName(event.getNewValue().toString());
            fixedAssetRegEntity.setColumnValue(null);
        }
    }

    public void handleSelectSrNo(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            System.out.println("not null");
            StoreRequisitionEntity = (MmsStorereq) event.getNewValue();
            srNoListsWhenOld = fixedAssetInterface.getsrNoListsByTag(hrDepartmentsEntity);
            System.out.println("=======================" + srNoListsWhenOld);
            StoreRequisitionEntity.setSrNo(StoreRequisitionEntity.getSrNo());
            fixedAssetRegDtlEntity.setSrNo(StoreRequisitionEntity.getSrNo());
            System.out.println("===Sr n==" + StoreRequisitionEntity.getSrNo());
            StoreRequisitionEntity.setMmsStorereqDetailList(StoreRequisitionEntity.getMmsStorereqDetailList());
            employeEntity = StoreRequisitionEntity.getRequestedBy();
            System.out.println("=====RequestedBy====" + employeEntity);
            fixedAssetRegDtlEntity.setRequeistedBy(StoreRequisitionEntity.getRequestedBy().getId());

        }
    }

    public void handleSelectSrNoOld(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            System.out.println("not null");
            StoreRequisitionEntity = (MmsStorereq) event.getNewValue();
            StoreRequisitionEntity.setMmsStorereqDetailList(StoreRequisitionEntity.getMmsStorereqDetailList());
            StoreRequisitionEntity = sivs.getStoreReqId();
            System.out.println("sv2=====" + StoreRequisitionEntity);
            employeEntity = StoreRequisitionEntity.getRequestedBy();
            System.out.println("sv3=====" + employeEntity);
            System.out.println("sv4=====" + employeEntity.getId());

        }
    }

    public void handleSelectSIVNo(ValueChangeEvent event) {
        System.out.println("in handler");
        if (null != event.getNewValue().toString()) {
            System.out.println("not null");
            sivs = (MmsSiv) event.getNewValue();
            sivs.setSivNo(sivs.getSivNo());
            fixedAssetRegDtlEntity.setSivNo(sivs.getSivNo());
            System.out.println("===siv n==" + sivs.getSivNo());
            sivs = sivInterface.getSivInfoBySivNo(sivs);
            sivs.setMmsSivDetailList(sivs.getMmsSivDetailList());
            fixedAssetRegDtlEntity.setSivNo(sivs.getSivNo());
            System.out.println("======siv no====" + sivs);
            fixedAssetRegDtlEntity.setSrNo(sivs.getSrNo());  //Added
            System.out.println("======sr no====" + sivs.getSrNo());
            itemRegistrationEntity.setMatCode(sivs.getMmsSivDetailList().get(0).getItemId().getMatCode());
            itemCodeListPop = itemregistrationInterface.searchMaterialInfoByMatCode(itemRegistrationEntity);
        }
    }

    public void AssetTypeChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer assetId = Integer.valueOf(sysno);
            assetTypeEntity.setAssetId(assetId);
            assetTypeEntity = assetTypeInterface.findAssetTypeByAssetTypeId(assetTypeEntity);
            assetType = assetTypeEntity.getAssetType();
            assetType2 = assetTypeEntity.getAssetType();
            fixedAssetRegEntity.setAssetType(assetTypeEntity);
            generateTagNo();
            generateTagNo2();
            for (MmsFaAssetType Assets : AssetTypeList) {
                boolean a = Assets.getAssetId().equals(Integer.valueOf(event.getNewValue().toString()));

                if (Assets.getAssetId().equals(Integer.valueOf(event.getNewValue().toString()))) {
                    assetTypeEntity.setAssetNo(Assets.getAssetNo());
                    break;
                }
            }
        }
    }

    public void handleSelectGeneralLedgarOld(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int fmsIds = Integer.valueOf(event.getNewValue().toString());
            fmsGeneralLedgarEntity.setGeneralLedgerId(fmsIds);
            fmsGeneralLedgarEntity = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedgarEntity);
            fixedAssetRegDtlEntityOLD.setAccountCode(fmsGeneralLedgarEntity);

        }

    }

    public void handleSelectGeneralLedgar(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int fmsIds = Integer.valueOf(event.getNewValue().toString());
            fmsGeneralLedgarEntity.setGeneralLedgerId(fmsIds);
            fmsGeneralLedgarEntity = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedgarEntity);
            fixedAssetRegDtlEntity.setAccountCode(fmsGeneralLedgarEntity);

        }

    }

    public void handleSelectRecievedBy(SelectEvent event) {
        if (null != event.getObject().toString()) {
            String name = event.getObject().toString();
            hrEmployeesEntity.setFirstName(name);
            String nm = hrEmployeesEntity.toString();
            fixedAssetRegEntity.setRecivedBy(employeEntity);

        }
    }

    public void handleTagNo(ValueChangeEvent event) {
        System.out.println("=====handlerTagNo===");
        if (null != event.getNewValue().toString()) {

            fixedAssetRegDtlEntityOLD = (MmsFixedassetRegstDetail) event.getNewValue();
            fixedAssetRegDtlEntityOLD.setTagNo(event.getNewValue().toString());
            fixedAssetRegDtlEntityOLD = fxAssetRegDtlInterface.findByTag(fixedAssetRegDtlEntityOLD);
            System.out.println("==========SrNoDtl=====" + fixedAssetRegDtlEntity.getSrNo());
            fixedAssetRegDtlEntityOLD.setSrNo(fixedAssetRegDtlEntity.getSrNo());
            System.out.println("==========SrNoDtl=====" + fixedAssetRegDtlEntity.getSrNo());
            System.out.println("==========RequeistedBy=====" + fixedAssetRegDtlEntity.getRequeistedBy());
            fixedAssetRegDtlEntityOLD.setRequeistedBy(fixedAssetRegDtlEntity.getRequeistedBy());
            System.out.println("==========RequeistedBy=====" + fixedAssetRegDtlEntityOLD.getRequeistedBy());
            String Id = event.getNewValue().toString();
            System.out.println("-----------Item Id @FAR--------" + fixedAssetRegDtlEntityOLD.getItemId().getMaterialId());
            itemRegistrationEntity.setMaterialId(fixedAssetRegDtlEntityOLD.getItemId().getMaterialId());
            itemRegistrationEntity = itemRegInterface.getMmsItemInfoByMatId(itemRegistrationEntity);
            matCat = itemRegistrationEntity.getMatCode();
            itemRegistrationEntity.setUnitPrice(itemRegistrationEntity.getMmsBinCard().getUnitPrice());
            fixedAssetRegDtlEntityOLD.setItemId(itemRegistrationEntity);
            fixedAssetRegEntity.setFarNo(fixedAssetRegDtlEntityOLD.getFarId().getFarNo());
            FrNo = fixedAssetRegEntity.getFarNo();
            String tagNo = fixedAssetRegDtlEntityOLD.getTagNo();
            fixedAssetRegDtlEntity.setTagNo(tagNo);
            System.out.println("555555=" + tagNo);

        }
    }
    int sivDtlID;

    public void MaterialCodeListprv(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String Id = event.getNewValue().toString();
            System.out.println("======value=========" + Id);
            int id2 = Integer.parseInt(Id);
            itemRegistrationEntity.setMaterialId(id2);
            itemRegistrationEntity = itemRegInterface.getMmsItemInfoByMatId(itemRegistrationEntity);
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            fixedAssetRegDtlEntity.setItemName(itemRegistrationEntity.getMatName());
            fixedAssetRegDtlEntity.setItemStatus(itemRegistrationEntity.getItemType());
            System.out.println("===itemStatus===" + itemRegistrationEntity.getItemType());
            System.out.println("===itemStatus===" + fixedAssetRegDtlEntity.getItemStatus());
            matCat2 = itemRegistrationEntity.getMatCategory().getCatCode();
            System.out.println("category===" + matCat2);
            matSubCat2 = itemRegistrationEntity.getMatSubcategory().getSubcatCode();
            generateTagNo2();

        }
    }

    public void MaterialCodeList(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String Id = event.getNewValue().toString();
            System.out.println("======value=========" + Id);
            int id2 = Integer.parseInt(Id);
            itemRegistrationEntity.setMaterialId(id2);
            itemRegistrationEntity = itemRegInterface.getMmsItemInfoByMatId(itemRegistrationEntity);
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            fixedAssetRegDtlEntity.setItemName(itemRegistrationEntity.getMatName());
            fixedAssetRegDtlEntity.setItemStatus("New");
            System.out.println("===itemStatus===" + itemRegistrationEntity.getItemType());
            matCat2 = itemRegistrationEntity.getMatCategory().getCatCode();
            System.out.println("category===" + matCat2);
            matSubCat2 = itemRegistrationEntity.getMatSubcategory().getSubcatCode();
            System.out.println("sub-category===" + matSubCat2);
            generateTagNo2();
            fixedAssetRegDtlEntity.setTagNo(newTagNo2);
            System.out.println("===========tag new=======" + newTagNo2);
        }
    }

    public void MaterialCodeListNew(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String Id = event.getNewValue().toString();
            System.out.println("======value=========" + Id);
            int id2 = Integer.parseInt(Id);
            itemRegistrationEntity.setMaterialId(id2);
            itemRegistrationEntity = itemRegInterface.getMmsItemInfoByMatId(itemRegistrationEntity);
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            matCat = itemRegistrationEntity.getMatCategory().getCatCode();
            matSubCat = itemRegistrationEntity.getMatSubcategory().getSubcatCode();
            Item = itemRegInterface.getItemInfo(itemRegistrationEntity);
            grnEntity = Item.get(0).getMmsGrnDetail().get(0).getGrnId();
            grnEntity.setGrnNo(Item.get(0).getMmsGrnDetail().get(0).getGrnId().getGrnNo());
            grnEntity.setApprovedDate(Item.get(0).getMmsGrnDetail().get(0).getGrnId().getApprovedDate());
            grnEntity.setGrnNo(grnEntity.getGrnNo());
            grnEntity.setApprovedDate(grnEntity.getApprovedDate());
            fixedAssetRegDtlEntity.setItemName(itemRegistrationEntity.getMatName());
            fixedAssetRegDtlEntity.setUnitPrice(itemRegistrationEntity.getUnitPrice());
        }
    }

    public void MaterialCodeListSIV(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String code = event.getNewValue().toString();
            itemRegistrationEntity = (MmsItemRegistration) event.getNewValue();
            System.out.println("after selecteed");
            itemRegistrationEntity = fixedAssetInterface.getcode(itemRegistrationEntity);
            System.out.println("====code==" + itemRegistrationEntity.getMatCode());
            itemRegistrationEntity.setMatCode(code);
            System.out.println("metcode" + itemRegistrationEntity.getMatCode());
            itemRegistrationEntity = itemRegInterface.SearchMatIDByItemCode(itemRegistrationEntity);
            System.out.println("------- Item reg Id --------" + itemRegistrationEntity.getMaterialId());
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            matCat = itemRegistrationEntity.getMatCategory().getCatCode();
            matSubCat = itemRegistrationEntity.getMatSubcategory().getSubcatCode();
            Item = itemRegInterface.getItemInfo(itemRegistrationEntity);
            System.out.println("--------Size Of Item ----------" + Item.size());
            fixedAssetRegDtlEntity.setItemName(Item.get(0).getMatName());
            itemRegistrationEntity.setMatCategory(Item.get(0).getMatCategory());
            itemRegistrationEntity.setMatSubcategory(Item.get(0).getMatSubcategory());
            itemRegistrationEntity.setUnitPrice(itemRegistrationEntity.getMmsBinCard().getUnitPrice());
            StoreRequisitionEntity.setRequestedBy(Item.get(0).getMmsStoreReqDetail().get(0).getStoreReqId().getRequestedBy());
            grnEntity = Item.get(0).getMmsGrnDetail().get(0).getGrnId();
            grnEntity.setGrnNo(Item.get(0).getMmsGrnDetail().get(0).getGrnId().getGrnNo());
            grnEntity.setApprovedDate(Item.get(0).getMmsGrnDetail().get(0).getGrnId().getApprovedDate());
            grnEntity.setGrnNo(grnEntity.getGrnNo());
            grnEntity.setApprovedDate(grnEntity.getApprovedDate());
            StoreRequisitionEntity.setRequestedBy(StoreRequisitionEntity.getRequestedBy());
            fixedAssetRegDtlEntity.setUnitPrice(itemRegistrationEntity.getUnitPrice());
            System.out.println("---------materialcodesive----" + itemRegistrationEntity.getMatName());
        }
    }

    public ArrayList<HrEmployees> searchEmployeeInformation(String FirstName) {
        ArrayList<HrEmployees> employeeInformations = null;// to make the previous search  paramaters and results null;
        employeEntity.setFirstName(FirstName);
        employeeInformations = hrInterface.searchEmployeeInfo(employeEntity);
        return employeeInformations;
    }

    public void getEmployeeInfo(SelectEvent event) {
        employeEntity.setFirstName(event.getObject().toString());
        employeEntity = hrInterface.getByFirstName(employeEntity);
        fixedAssetRegEntity.setRecivedBy(employeEntity);
    }

    public void changeCheckItemType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderPrevious(true);
                setRenderOld(false);
                setRenderNew(false);
                setRenderOldPreviw(true);
                fixedAssetRegEntity.setSelectOpt(1);
                clearPage2();
            } else if (e.getNewValue().toString().equalsIgnoreCase("2")) {
                setRenderPrevious(false);
                setRenderOld(true);
                setRenderNew(false);
                setRenderOldPreviw(true);
                fixedAssetRegEntity.setSelectOpt(2);
                clearPage2();
            } else {
                setRenderPrevious(false);
                setRenderOld(false);
                setRenderNew(true);
                setRenderOldPreviw(false);
                fixedAssetRegEntity.setSelectOpt(3);
                clearPage2();
            }

        }
    }

    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (departmentNames != null) {
            for (HrDepartments k : departmentNames) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onChangeEvent(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            System.out.println("1");
        }
    }

    public void onSelectEvent(SelectEvent selectEvent) {
        if (selectEvent.getObject() != null) {
            System.out.println("2");
            hrDepartmentsEntity = null;
            luSystemEntity = null;
            assetTypeEntity = null;
            if (selectEvent.getObject().toString().equalsIgnoreCase("1")) {
                setRenderPrevious(true);
                setRenderOld(false);
                setRenderNew(false);
                fixedAssetRegEntity.setSelectOpt(1);

            } else if (selectEvent.getObject().toString().equalsIgnoreCase("2")) {

                setRenderPrevious(false);
                setRenderOld(true);
                setRenderNew(false);
                fixedAssetRegEntity.setSelectOpt(2);

            } else {

                setRenderPrevious(false);
                setRenderOld(false);
                setRenderNew(true);
                fixedAssetRegEntity.setSelectOpt(3);
                setSelectOprDtlRadio(true);

            }

        }
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Add to detail And Recreate Datamodel">
    //Add to detail
    public String addFixedAssetRegDetail() {

        fixedAssetRegDtlEntity.setTagNo(newTagNo2);
        fixedAssetRegDtlEntity.setRequeistedBy(StoreRequisitionEntity.getRequestedBy().getId());
        fixedAssetRegDtlEntity.setSivNo(sivs.getSivNo());
        fixedAssetRegDtlEntity.setAccountCode(fmsGeneralLedgarEntity);
        System.out.println("=======generalLedgerId===" + fmsGeneralLedgarEntity);
        newDetailList.add(fixedAssetRegDtlEntity);
        fixedAssetRegEntity.addToFixedAssetRegDetail(fixedAssetRegDtlEntity);
        System.out.println("===========RequestedBy===" + StoreRequisitionEntity.getRequestedBy().getId());
        fixedAssetRegEntity.addToFixedAssetRegDetail(fixedAssetRegDtlEntity);
        recreateModelDetail();
        clearPopUp();
        return null;
    }

    public String addFixedAssetRegDetailOld() {
        fixedAssetRegDtlEntityOLD.setSivId(sivs);
        fixedAssetRegDtlEntityOLD.setStoreReqId(StoreRequisitionEntity);
        fixedAssetRegDtlEntityOLD.setSelectOptRadio(2);
        fixedAssetRegEntity.addToFixedAssetRegDetail(fixedAssetRegDtlEntityOLD);
        recreateModelDetailOld();
        clearPopUpOld();
        return null;
    }

    public String addFixedAsetDetailAll() {
        if (CheckItemType == "1") {
            System.out.println("==============selection 1");
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            fixedAssetRegDtlEntity.setStoreReqId(StoreRequisitionEntity);
            fixedAssetRegDtlEntity.setSelectOptRadio(1);
            fixedAssetRegEntity.addToFixedAssetRegDetail(fixedAssetRegDtlEntity);
            recreateModelDetail();
            clearPopUpPrev();
        } else if (CheckItemType == "2") {
            System.out.println("==============selection 2");
            fixedAssetRegDtlEntity.setStoreReqId(StoreRequisitionEntity);
            fixedAssetRegDtlEntity.setSelectOptRadio(2);
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            fixedAssetRegEntity.addToFixedAssetRegDetail(fixedAssetRegDtlEntity);
            recreateModelDetail();
            clearPopUpOld();
        } else {
            System.out.println("==============selection 3");
            fixedAssetRegDtlEntityOLD.setSelectOptRadio(3);
            System.out.println("==================newtagno=======" + newTagNo2);
            fixedAssetRegDtlEntity.setRequeistedBy(employeEntity.getId());
            fixedAssetRegDtlEntity.setSivNo(sivs.getSivNo());
            fixedAssetRegDtlEntity.setAccountCode(fmsGeneralLedgarEntity);
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            System.out.println("=======generalLedgerId===" + fmsGeneralLedgarEntity);
            newDetailList.add(fixedAssetRegDtlEntity);
            fixedAssetRegEntity.addToFixedAssetRegDetail(fixedAssetRegDtlEntity);
            recreateModelDetail();
            clearPopUp();
        }

        return null;

    }

    public void editDataTable() {
        addOrModifyBundle = "Modify";
        fixedAssetRegDtlEntity = mmsFixedAssetRegDtlDataModel.getRowData();
        if (CheckItemType == "1") {
            System.out.println("============CheckItemType====pre======");
            itemRegistrationEntity = fixedAssetRegDtlEntity.getItemId();
            StoreRequisitionEntity = fixedAssetRegDtlEntity.getStoreReqId();

            fixedAssetRegDtlEntity.setSelectOptRadio(fixedAssetRegDtlEntity.getSelectOptRadio());
            fixedAssetRegDtlEntity.setTagNo(fixedAssetRegDtlEntity.getTagNo());
            fmsGeneralLedgarEntity = fixedAssetRegDtlEntity.getAccountCode();
            renderPrevious = true;
            renderNew = false;
            renderOld = false;
            renderOldPreviw = true;
        } else if (CheckItemType == "2") {
            System.out.println("==========CheckItemType====used===");
            StoreRequisitionEntity = fixedAssetRegDtlEntity.getStoreReqId();
            fmsGeneralLedgarEntity = fixedAssetRegDtlEntity.getAccountCode();
            fixedAssetRegDtlEntity.setSelectOptRadio(fixedAssetRegDtlEntity.getSelectOptRadio());
            itemRegistrationEntity = fixedAssetRegDtlEntity.getItemId();
            System.out.println("===item===============" + fixedAssetRegDtlEntity.getItemId());
            renderPrevious = false;
            renderNew = false;
            renderOld = true;
            renderOldPreviw = true;
        } else {
            System.out.println("========new=========");
            sivs = fixedAssetRegDtlEntity.getSivId();
            StoreRequisitionEntity = sivs.getStoreReqId();//242 
            System.out.println("sv2=====" + StoreRequisitionEntity);
            employeEntity = StoreRequisitionEntity.getRequestedBy();
            System.out.println("=============employeee=====" + employeEntity);
            StoreRequisitionEntity.setRequestedBy(employeEntity);
            System.out.println("=============employeee=====" + StoreRequisitionEntity.getRequestedBy());
            itemRegistrationEntity = fixedAssetRegDtlEntity.getItemId();
            fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
            itemCodesWhenNewLists = fixedAssetInterface.getItemBySivId(sivs);
            fixedAssetRegDtlEntity.setTagNo(fixedAssetRegDtlEntity.getTagNo());
            System.out.println("=======itemRegistrationEntity new ====" + itemRegistrationEntity);
            fmsGeneralLedgarEntity = fixedAssetRegDtlEntity.getAccountCode();
            StoreRequisitionEntity = fixedAssetRegDtlEntity.getStoreReqId();
            System.out.println("===item===============" + fixedAssetRegDtlEntity.getItemId());
            fixedAssetRegDtlEntity.setSelectOptRadio(fixedAssetRegDtlEntity.getSelectOptRadio());
            renderPrevious = false;
            renderNew = true;
            renderOld = false;
            renderOldPreviw = false;
        }

    }

    public String addFixedAssetRegDetailPrev() {

        fixedAssetRegDtlEntity.getDelivereyDate();
        fixedAssetRegDtlEntity.setItemId(itemRegistrationEntity);
        fixedAssetRegDtlEntity.setTagNo(newTagNo2);
        fixedAssetRegDtlEntity.setStoreReqId(StoreRequisitionEntity);
        fixedAssetRegDtlEntity.setSelectOptRadio(1);
        fixedAssetRegEntity.addToFixedAssetRegDetail(fixedAssetRegDtlEntity);
        recreateModelDetailPrev();
        clearPopUpPrev();
        return null;
    }

    private void recreateModelDetailOld() {
        System.out.println("====datamodel==");
        mmsFixedAssetRegDtlDataModelOLD = null;
        mmsFixedAssetRegDtlDataModelOLD = new ListDataModel(new ArrayList<>(fixedAssetRegEntityOLD.getMmsFixedassetRegstDetailList()));
        System.out.println("====datamodel==" + mmsFixedAssetRegDtlDataModelOLD.getRowCount());
    }

    private void recreateModelDetailPrev() {
        System.out.println("====datamodel==");
        mmsFixedAssetRegDtlDataModelPrev = null;
        mmsFixedAssetRegDtlDataModelPrev = new ListDataModel(new ArrayList<>(fixedAssetRegEntity.getMmsFixedassetRegstDetailList()));
        System.out.println("====datamodel==" + mmsFixedAssetRegDtlDataModelPrev.getRowCount());

    }

    private void recreateModelDetail() {
        System.out.println("====datamodel==");
        mmsFixedAssetRegDtlDataModel = null;
        mmsFixedAssetRegDtlDataModel = new ListDataModel(new ArrayList<>(fixedAssetRegEntity.getMmsFixedassetRegstDetailList()));
        System.out.println("====datamodel==" + mmsFixedAssetRegDtlDataModel.getRowCount());
    }

    private void recreateModelSearch() {
        mmsFixedAssetRegSearchDataModel = null;
        mmsFixedAssetRegSearchDataModel = new ListDataModel(new ArrayList<>(allFARInfoList));

    }

    public void recreateWfDataModel() {
        if ((fixedAssetRegEntity.getSelectOpt() == 3) || (fixedAssetRegEntity.getSelectOpt() == 1)) {
            WfMmsProcessedDataModel = null;
            WfMmsProcessedDataModel = new ListDataModel(fixedAssetRegEntity.getFaRegList());
        } else {
            WfMmsProcessedDataModel = null;
            WfMmsProcessedDataModel = new ListDataModel(fixedAssetRegEntityOLD.getFaRegList());
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clear Popups and Clear Page">
//Clear Popup
    private void clearPopUp() {

        itemRegistrationEntity = null;
        assetTypeEntity = null;
        sivs = null;
        sivDetail = null;
        StoreRequisitionEntity = null;
        newTagNo = null;
        fixedAssetRegDtlEntity = null;
        fmsGeneralLedgarEntity = null;
        ItemCodeList.clear();
        AssetTypeList.clear();
        subSystemCodeList.clear();
        gLCodeList.clear();
        employeEntity = null;

    }

    private void clearPopUpOld() {
        addOrModifyBundle = "Add";
        itemRegistrationEntity = null;
        assetTypeEntity = null;
        sivs = null;
        sivDetail = null;
        StoreRequisitionEntity = null;
        fixedAssetRegDtlEntityOLD = null;
        fmsGeneralLedgarEntity = null;
        ItemCodeList.clear();
        gLCodeList.clear();

    }

    private void clearPopUpPrev() {
        itemRegistrationEntity = null;
        assetTypeEntity = null;
        sivs = null;
        sivDetail = null;
        StoreRequisitionEntity = null;
        fixedAssetRegDtlEntity = null;
        fmsGeneralLedgarEntity = null;
        ItemCodeList.clear();
        AssetTypeList.clear();
        subSystemCodeList.clear();
        gLCodeList.clear();

    }

    public String clearPage2() {
        StoreRequisitionEntity = null;
        fixedAssetRegDtlEntity = null;
        itemRegistrationEntity = null;
        mmsFixedAssetRegDtlDataModel = null;
        return null;
    }

    public void clearPage() {
        employeEntity = null;
        hrEmployeesEntity = null;
        luSystemEntity = null;
        fixedAssetRegEntity = null;
        fixedAssetRegDtlEntity = null;
        hrDepartmentsEntity = null;
        itemRegistrationEntity = null;
        assetTypeEntity = null;
        StoreRequisitionEntity = null;
        mmsFixedAssetRegDtlDataModel = null;
        mmsFixedAssetRegDtlDataModelOLD = null;
        mmsFixedAssetRegDtlDataModelPrev = null;
        mmsFixedAssetRegSearchDataModel = null;
        ItemCodeList.clear();
        AssetTypeList.clear();
        subSystemCodeList.clear();
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        workFlow = null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post Constract">
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update Methods">
    public void Wfsave() {

        if (CheckItemType.equals("2")) {

            workFlow.setFaRegstrationId(fixedAssetRegEntityOLD);
            workFlowInterface.create(workFlow);
        } else {
            workFlow.setFaRegstrationId(fixedAssetRegEntity);
            workFlowInterface.create(workFlow);
        }
    }

    public void saveFARegistration() {
        System.out.println("======saveeeee1===");
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveFARegistration", dataset)) {
                boolean WorkFlow = false;
                System.out.println("==========save methed==2=");
                if (saveorUpdateBundle.equals("Save")) {

                    try {
                        System.out.println("======save methed=3=");
                        if (mmsFixedAssetRegDtlDataModel.getRowCount() > 0) {
                            System.out.println("save ");
                            System.out.println("========================================");
                            fixedAssetRegEntity.setSystemNo(luSystemEntity);
                            fixedAssetRegEntity.setFarNo(newFarNo);
                            fixedAssetRegEntity.setDepartment(hrDepartmentsEntity);
                            fixedAssetRegDtlEntity.setAccountCode(fmsGeneralLedgarEntity);
                            fixedAssetRegEntity.setFaStatus(Constants.PREPARE_VALUE);
                            workFlow.setDecision(fixedAssetRegEntity.getFaStatus());
                            System.out.println("==========++++++++");
                            fixedAssetRegEntity.setApprovedBy(SessionBean.getUserId());
                            workFlow.setProcessedBy(fixedAssetRegEntity.getApprovedBy());
                            System.out.println("=======getuserId     " + SessionBean.getUserId());
                            fixedAssetRegEntity.setDelivererRemark(workFlow.getCommentGiven()); // comment given
                            fixedAssetRegEntity.setProcessedOn(workFlow.getProcessedOn());
                            fixedAssetRegEntity.getFaRegList().add(workFlow);
                            if (renderNew == true) {
                                fixedAssetRegDtlEntity.setTagNo(newTagNo2);
                                System.out.println("=========tag No newTagNo2" + newTagNo2);
                            }
                            fixedAssetInterface.create(fixedAssetRegEntity);
                            System.out.println("=====savemethed===" + fixedAssetRegEntity.getFarNo());
                            System.out.println("=========tag No old" + fixedAssetRegDtlEntity.getTagNo());

                        }
                        JsfUtil.addSuccessMessage("A New Fixed Asset information Has Been Saved");
                        clearPage();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Something Error Occured on Creating The Data ");

                    }

                } else if ((saveorUpdateBundle.equals("Update") && workflow.isPrepareStatus())) {

                    try {
                        fixedAssetRegEntity.setSystemNo(luSystemEntity);
                        fixedAssetRegEntity.setFaStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(fixedAssetRegEntity.getFaStatus());
                        fixedAssetRegEntity.setApprovedBy(workFlow.getProcessedBy()); // Prepared by
                        workFlow.setProcessedBy(fixedAssetRegEntity.getApprovedBy()); // Prepared by
                        fixedAssetRegEntity.setDelivererRemark(workFlow.getCommentGiven()); // comment given
                        fixedAssetRegEntity.setProcessedOn(workFlow.getProcessedOn());
                        System.out.println("23");
                        fixedAssetInterface.edit(fixedAssetRegEntity);
                        Wfsave();

                        JsfUtil.addSuccessMessage("Fixed Asset information has been Updated");
                        clearPage();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                    }
                } else if (saveorUpdateBundle.equals("Update") && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        fixedAssetRegEntity.setFaStatus(Constants.APPROVE_VALUE);
                        workFlow.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        fixedAssetRegEntity.setFaStatus(Constants.CHECK_APPROVE_VALUE);
                        workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        fixedAssetRegEntity.setFaStatus(Constants.APPROVE_REJECT_VALUE);
                        workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        fixedAssetRegEntity.setFaStatus(Constants.CHECK_REJECT_VALUE);
                        workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    workFlow.setFaRegstrationId(fixedAssetRegEntity);
                    workFlowInterface.create(workFlow);
                    fixedAssetInterface.edit(fixedAssetRegEntity);
                    mmsFarList.remove(fixedAssetRegEntity);
                    clearPage();
                    JsfUtil.addSuccessMessage("Lost Data Modified");
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

    //<editor-fold defaultstate="collapsed" desc="Search And Save methods">
    public void searchFARInformation1() {

        String str = fixedAssetRegEntity.getFarNo();
        fixedAssetRegEntity.setFarNo(str);
        fixedAssetRegEntity.setApprovedBy(SessionBean.getUserId());
//        if (fixedAssetRegEntity.getFarNo()!=null) {

            allFARInfoList = fixedAssetInterface.getAssetregstrationListsByParameter(fixedAssetRegEntity);
            recreateModelSearch();

        }
    

    public void createNewFixedAssetRegInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateFAR = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            unitprice = "Unit price";

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateFAR = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            unitprice = "Book Value";

        }
    }

    public String generateFARNo() {
        if (updateStatus == 1) {
            newFarNo = fixedAssetRegEntity.getFarNo();
        } else {
            MmsFixedassetRegstration lastFARNo = fixedAssetInterface.getLastFARNo();

            if (lastFARNo != null) {
                lastFarNumber = lastFARNo.getId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newPayment = 0;
            if (lastFarNumber.equals("0")) {
                newPayment = 1;
                newFarNo = "FARNo-" + newPayment + "/" + f.format(now);
                FrNo = newFarNo;
            } else {

                String[] lastInspNos = lastFarNumber.split("-");
                String lastDatesPatern = lastInspNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newFarNo = "FARNo-" + newPayment + "/" + f.format(now);
                FrNo = newFarNo;
            }

        }

        return newFarNo;
    }

    public String generateTagNo() {
        MmsFixedassetRegstration lastFARNo = fixedAssetInterface.getLastFARNo();

        if (lastFARNo != null) {
            lastTagNumber = lastFARNo.getId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int newPayment = 0;
        if (lastTagNumber.equals("0")) {
            newPayment = 1;
            newTagNo = "EEP-" + matCat + "-" + matSubCat + "/" + newPayment + "/" + f.format(now);;
            newTagNo = "EEP-" + matCat + "-" + matSubCat + "/" + newPayment + "/" + f.format(now);
            System.out.println("newTagNo====" + newTagNo);

        } else {

            String[] lastInspNos = lastTagNumber.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newPayment = Integer.parseInt(lastDatesPaterns[0]);
            newPayment = newPayment + 1;
            newTagNo = "EEP-" + sysName + "-" + deptName + "-" + assetType + "-" + matCat + "-" + matSubCat + "/" + newPayment + "/" + f.format(now);
            System.out.println("newTagNo----" + newTagNo);

        }
        if (CheckItemType.equals("3")) {
            fixedAssetRegDtlEntity.setTagNo(newTagNo);// Added
        }
        return newTagNo;
    }

    public String generateTagNo2() {
        MmsFixedassetRegstration lastFARNo2 = fixedAssetInterface.getLastFARNo();
        if (lastFARNo2 != null) {
            lastTagNumber2 = lastFARNo2.getId().toString();
        }
        DateFormat f2 = new SimpleDateFormat("yyyy");
        Date now2 = new Date();

        int newPayment2 = 0;
        if (lastTagNumber2.equals("0")) {
            newPayment2 = 1;
            newTagNo2 = "EEP/" + matCat2 + "/" + matSubCat2 + "/" + newPayment2 + "/" + f2.format(now2);;
            System.out.println("newTagNo2" + newTagNo2);

        } else {

            String[] lastInspNos = lastTagNumber2.split("-");
            String lastDatesPatern2 = lastInspNos[0];
            String[] lastDatesPaterns2 = lastDatesPatern2.split("/");
            newPayment2 = Integer.parseInt(lastDatesPaterns2[0]);
            newPayment2 = newPayment2 + 1;
            newTagNo2 = "EEP/" + matCat2 + "/" + matSubCat2 + "/" + newPayment2 + "/" + f2.format(now2);
            System.out.println("newTagNo2===" + newTagNo2);
            if (!CheckItemType.equals("2")) {
                fixedAssetRegDtlEntity.setTagNo(newTagNo2);
            }

        }
        return newTagNo2;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RowSelect On Update">
    public void rowSelect(SelectEvent event) {
        System.out.println("data is on row select");
        fixedAssetRegEntity = (MmsFixedassetRegstration) event.getObject();
        fixedAssetRegEntity.setId(fixedAssetRegEntity.getId());
        System.out.println("========getId===" + fixedAssetRegEntity.getId());
        populateUI();
    }

    public void onSelectRequest(ValueChangeEvent event) {
        System.out.println("1");
        try {
            System.out.println("2");
            if (null != event.getNewValue()) {
                System.out.println("3");
                fixedAssetRegEntity = (MmsFixedassetRegstration) event.getNewValue();
                populateUI();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Failed to process! Try Again Reloading the Page");
        }
    }

    public void populateUI() {
        System.out.println("=======populate====");
        luSystemEntity = fixedAssetRegEntity.getSystemNo();
        hrDepartmentsEntity = fixedAssetRegEntity.getDepartment();
        assetTypeEntity = fixedAssetRegEntity.getAssetType();
        if (CheckItemType == "3") {

            setRenderNew(true);
            setRenderOld(false);
            setRenderPrevious(false);
            setCheckItemType("3");
        } else if ((CheckItemType == "2")) {
            unitprice = "Book Value";
            setRenderNew(false);
            setRenderOld(true);
            setRenderPrevious(false);
            fixedAssetRegEntityOLD = fixedAssetRegEntity;
            setCheckItemType("2");
            List<MmsFixedassetRegstDetail> TransNo = null;
            fixedAssetRegEntityOLD.getMmsFixedassetRegstDetailList().get(0).getTagNo();
            MmsFixedassetRegstDetail fARdtlEntity = new MmsFixedassetRegstDetail();
            fARdtlEntity.setTagNo(fixedAssetRegEntityOLD.getMmsFixedassetRegstDetailList().get(0).getTagNo());
            TransNo = fxAssetRegDtlInterface.findByTagNo2(fARdtlEntity);
        } else if (CheckItemType == "2") {
            setRenderNew(false);
            setRenderOld(false);
            setRenderPrevious(true);
            setCheckItemType("1");
        }
        renderPnlManPage = false;
        renderPnlCreateFAR = true;
        renderpnlToSearchpage = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-plus");
        createOrSearchBundle = "New";
        updateStatus = 1;
        activeIndex = "0";
        setCreateOrSearchBundle(createOrSearchBundle);
        disableBtnCreate = true;
        setIsRenderedIconWorkflow(true);
        if (workflow.isPrepareStatus()) {
            workFlow.setProcessedOn(fixedAssetRegEntity.getProcessedOn());
        }
        recreateModelDetail();
        recreateWfDataModel();
    }

    public void goBackToSearchpageBtnAction() {
        renderPnlCreateFAR = false;
        renderPnlManPage = true;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Dashboard">
    private BarChartModel ItemStatusBarModel;
    ChartSeries Chart = new ChartSeries();
    int totalItems = 0;
    List<String> ItemStatsuTypes = new ArrayList<>();
    List<ItemStatus> ItemStatusList = new ArrayList<>();
    ItemStatus itemStatus = new ItemStatus();

    public class ItemStatus implements Serializable {

        private String itemStatus;
        private int total;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

       //</editor-fold>
        public ItemStatus ItemByStatus(String status, int totalCount) {
            this.itemStatus = status;
            this.total = totalCount;
            return this;
        }

    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public BarChartModel getItemStatusBarModel() {
        return ItemStatusBarModel;
    }

    public void setItemStatusBarModel(BarChartModel ItemStatusBarModel) {
        this.ItemStatusBarModel = ItemStatusBarModel;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public ChartSeries getChart() {
        return Chart;
    }

    public void setChart(ChartSeries Chart) {
        this.Chart = Chart;
    }

    public List<String> getItemStatsuTypes() {
        return ItemStatsuTypes;
    }

    public void setItemStatsuTypes(List<String> ItemStatsuTypes) {
        this.ItemStatsuTypes = ItemStatsuTypes;
    }

    public List<ItemStatus> getItemStatusList() {
        return ItemStatusList;
    }

    public void setItemStatusList(List<ItemStatus> ItemStatusList) {
        this.ItemStatusList = ItemStatusList;
    }

//</editor-fold>
    public void ItemStausBarChartCreator() {
        ItemStatsuTypes = fixedAssetDtlInterface.findAllItemStatuses();
        System.out.println("ItemStatsuTypes===" + ItemStatsuTypes);
        for (int i = 0; i < ItemStatsuTypes.size(); i++) {
            int itmeCount = 0;
            itmeCount = fixedAssetDtlInterface.ConutBYItemType(ItemStatsuTypes.get(i));
            ItemStatusList.add(itemStatus.ItemByStatus(ItemStatsuTypes.get(i), itmeCount));
            System.out.println("ItemStatsuTypes.get(i)==" + ItemStatsuTypes.get(i));
            System.out.println("itmeCount==" + itmeCount);
            Chart.setLabel("Item Status");
            Chart.set(ItemStatsuTypes.get(i), itmeCount);
            totalItems=totalItems+itmeCount;
        }
        createBarModel();
    }

    private void createBarModel() {
        ItemStatusBarModel = initBarModel1();
        ItemStatusBarModel.setTitle("Item Grouped By Their Status ");
        ItemStatusBarModel.setLegendPosition("ne");
        Axis xAxis = ItemStatusBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Item Status");
        xAxis.setTickAngle(0);
        Axis yAxis = ItemStatusBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total Item");
        yAxis.setMin(0);
        yAxis.setMax(totalItems);
    }

    private BarChartModel initBarModel1() {
        BarChartModel model = new BarChartModel();
        model.addSeries(Chart);
        return model;
    }
//</editor-fold>
}
