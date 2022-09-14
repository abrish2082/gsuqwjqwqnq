package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="imported Liberaries">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.stock.FmsTotalStockValueBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.stock.FmsTotalStockValue;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsNonFixedAssetReturnBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturnDtl;
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
import javax.xml.namespace.QName;
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
 * @Nebiyou Samuel
 */
@Named(value = "nonFixedAssetReturnController")
@ViewScoped
      //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
public class NonFixedAssetReturnController implements Serializable {

    @EJB
    private HrDepartmentsIntegrationBeanLocal hrdepartmentInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private MmsNonFixedAssetReturnBeanLocal nonFxdRetInterface;
    @EJB
    private MmsFixedAssetRegistrationBeanLocal fixedAssetRegistrationBeanLocal;
    @EJB
    private MmsSivDtlBeanLocal sivInterface;
    @EJB
    private MmsSivBeanLocal sivBeanLocal;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegInterface;
    @EJB
    private HREmployeesBeanLocal hrInterface;
    @EJB
    MmsItemRegisrtationBeanLocal itemBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsTotalStockValueBeanLocal fmsTotalStockValueBeanLocal;
    @EJB
    MmsBinCardBeanLocal binCardBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal cSystemJunctionBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    private MmsFixedAssetReturn returnEntity;
    @Inject
    private MmsItemRegistration itemRegEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    private MmsSivDetail sivDtlEntity;
    @Inject
    private MmsSiv sivEntity;
    @Inject
    private HrEmployees employeEntity;
    @Inject
    private MmsNonFixedAssetReturn nonFxdRetEntity;
    @Inject
    private MmsNonFixedAssetReturnDtl nonFxdRetDtlEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsFixedassetRegstration fixedassetRegstration;
    @Inject
    MmsStorereq mmsStorereq;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    private FmsTotalStockValue totalStockValue;
    @Inject
    private MmsBinCard binCard;
    @Inject
    private FmsLuSystem fmsLuSystem;
    @Inject
    private FmsCostCenter fmsCostCenter;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;
    @Inject
    private FmsCostcSystemJunction costcSysJunction;
    @Inject
    FmsGeneralLedger generalLedger;
     ColumnNameResolver columnNameResolver;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare lists and models">
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    List<MmsSivDetail> itemCodeList;
    List<MmsSiv> sivNoLists;
    List<MmsSivDetail> itemCodelistWhenRecierver;
    private DataModel<MmsNonFixedAssetReturnDtl> returnNFAddDetailDataModel;
    private DataModel<MmsNonFixedAssetReturn> mmsreturnNFSearchInfoDataModel;
     private static List<HrDepartments> araListe;
     List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Declare fields">
     String selectedValue = "";
     private boolean renderDecision = false;
     private MmsNonFixedAssetReturn hpSelect;
     private String saveorUpdateBundle = "Save";
     int updateStatus = 0;
     private boolean disableBtnCreate;
     private String createOrSearchBundle = "New";
     private String addOrModifyBundle = "Add";
     private boolean renderPnlCreateReturn = false;
     private boolean renderPnlManPage = true;
     private boolean renderpnlToSearchPage;
     private String icone = "ui-icon-plus";
     private String activeIndex;
     Set<String> checkMaterialCode = new HashSet<>();
     private boolean renderReciver = false;
     private boolean renderOther = false;
     private List<MmsNonFixedAssetReturn> mmsNFAList;
     private boolean isRenderedIconNitification = false;
     private boolean isRenderedIconWorkflow = false;
     private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
     private boolean isCheckedToStore = false;
     boolean isRenderCreate;
     private String userName;
     private TreeNode root;
     private TreeNode root2;
     private TreeNode selectedNode;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Defualt Constructor">
    public NonFixedAssetReturnController() {
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="@PostConstract">
    @PostConstruct
    public void init() {
          ColumnNameResolverList = nonFxdRetInterface.getColumnNameList();
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                 
                mmsNFAList = nonFxdRetInterface.findNFAListByWfStatus(Constants.CHECK_APPROVE_VALUE);
            } else if (workflow.isCheckStatus()) {
               
                mmsNFAList = nonFxdRetInterface.findNFAListForCheckerByWfStatus(Constants.PREPARE_VALUE, Constants.APPROVE_REJECT_VALUE);
            }
            isRenderCreate = false;
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsNFAList = nonFxdRetInterface.findNFAListByWfStatus(Constants.CHECK_REJECT_VALUE);
            renderDecision = false;
            isRenderCreate = true;
            if (mmsNFAList.size() > 0) {
                isRenderedIconNitification = true;
            } else {
                isRenderedIconNitification = false;
               
            }

        }
        lusystems = fmsLuSystemBeanLocal.findAll();
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        DepartmentNames = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);

        recursive(DepartmentNames, 0, root);
        root2 = getRoot();      
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of variables">
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

    public boolean isRenderPnlCreateReturn() {
        return renderPnlCreateReturn;
    }

    public void setRenderPnlCreateReturn(boolean renderPnlCreateReturn) {
        this.renderPnlCreateReturn = renderPnlCreateReturn;
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

    public boolean isRenderReciver() {
        return renderReciver;
    }

    public void setRenderReciver(boolean renderReciver) {
        this.renderReciver = renderReciver;
    }

    public boolean isRenderOther() {
        return renderOther;
    }

    public void setRenderOther(boolean renderOther) {
        this.renderOther = renderOther;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of objects">
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
    
    public MmsNonFixedAssetReturn getNonFxdRetEntity() {
        if (nonFxdRetEntity == null) {
            nonFxdRetEntity = new MmsNonFixedAssetReturn();
        }
        return nonFxdRetEntity;
    }
    
    public void setNonFxdRetEntity(MmsNonFixedAssetReturn nonFxdRetEntity) {
        this.nonFxdRetEntity = nonFxdRetEntity;
    }
      public MmsNonFixedAssetReturn getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsNonFixedAssetReturn hpSelect) {
        this.hpSelect = hpSelect;
    }

    public MmsNonFixedAssetReturnDtl getNonFxdRetDtlEntity() {
        if (nonFxdRetDtlEntity == null) {
            nonFxdRetDtlEntity = new MmsNonFixedAssetReturnDtl();
        }
        return nonFxdRetDtlEntity;
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
    public WfMmsProcessed getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WfMmsProcessed();
        }
        return workFlow;
    }

    public void setWorkFlow(WfMmsProcessed workFlow) {
        this.workFlow = workFlow;
    }

    public MmsSivDetail getSivDtlEntity() {
        if (sivDtlEntity == null) {
            sivDtlEntity = new MmsSivDetail();
        }
        return sivDtlEntity;
    }

    public void setSivDtlEntity(MmsSivDetail sivDtlEntity) {
        this.sivDtlEntity = sivDtlEntity;
    }

    public void setNonFxdRetDtlEntity(MmsNonFixedAssetReturnDtl nonFxdRetDtlEntity) {
        this.nonFxdRetDtlEntity = nonFxdRetDtlEntity;
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

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public MmsFixedassetRegstration getFixedassetRegstration() {
        if (fixedassetRegstration == null) {
            fixedassetRegstration = new MmsFixedassetRegstration();
        }
        return fixedassetRegstration;
    }

    public void setFixedassetRegstration(MmsFixedassetRegstration fixedassetRegstration) {
        this.fixedassetRegstration = fixedassetRegstration;
    }

    public MmsStorereq getMmsStorereq() {
        if (mmsStorereq == null) {
            mmsStorereq = new MmsStorereq();
        }
        return mmsStorereq;
    }

    public void setMmsStorereq(MmsStorereq mmsStorereq) {
        this.mmsStorereq = mmsStorereq;
    }
      public MmsBinCard getBinCard() {
        if (binCard == null) {
            binCard = new MmsBinCard();
        }
        return binCard;
    }

    public void setBinCard(MmsBinCard binCard) {
        this.binCard = binCard;
    }

    public FmsTotalStockValue getTotalStockValue() {
        if (totalStockValue == null) {
            totalStockValue = new FmsTotalStockValue();
        }
        return totalStockValue;
    }

    public void setTotalStockValue(FmsTotalStockValue totalStockValue) {
        this.totalStockValue = totalStockValue;
    }


//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of lists and models">
    public List<MmsNonFixedAssetReturn> getMmsNFAList() {
        return mmsNFAList;
    }
    
    public void setMmsNFAList(List<MmsNonFixedAssetReturn> mmsNFAList) {
        this.mmsNFAList = mmsNFAList;
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
    public List<HrDepartments> getDepartmentNames() {

        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();
        return DepartmentNames;
    }

    public void setDepartmentNames(List<HrDepartments> DepartmentNames) {
        this.DepartmentNames = DepartmentNames;
    }

    public DataModel<MmsNonFixedAssetReturnDtl> getReturnNFAddDetailDataModel() {
        if (returnNFAddDetailDataModel == null) {
            returnNFAddDetailDataModel = new ListDataModel<>();
        }
        return returnNFAddDetailDataModel;
    }

    public void setReturnNFAddDetailDataModel(DataModel<MmsNonFixedAssetReturnDtl> returnNFAddDetailDataModel) {
        this.returnNFAddDetailDataModel = returnNFAddDetailDataModel;
    }

    public DataModel<MmsNonFixedAssetReturn> getMmsreturnNFSearchInfoDataModel() {
        if (mmsreturnNFSearchInfoDataModel == null) {
            mmsreturnNFSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsreturnNFSearchInfoDataModel;
    }

    public void setMmsreturnNFSearchInfoDataModel(DataModel<MmsNonFixedAssetReturn> mmsreturnNFSearchInfoDataModel) {
        this.mmsreturnNFSearchInfoDataModel = mmsreturnNFSearchInfoDataModel;
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

//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clear Page">
    public void createNewNonReturnInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateReturn = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateReturn = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");

        }

    }

    public String clearPage() {
        storeInfoEntity = null;
        sivDtlEntity = null;
        sivEntity = null;
        nonFxdRetEntity = null;
        itemRegEntity = null;
        hrDepartmentsEntity = null;
        nonFxdRetDtlEntity = null;
        returnNFAddDetailDataModel = null;
        mmsreturnNFSearchInfoDataModel = null;
        hrDepartmentsEntity = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        workFlow = null;

        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search and Update">
    String newNFReurntId;
    String lastNFReturnId = "0";
    
    public String generateReturnNo2() {
        MmsNonFixedAssetReturn lastInsuranceID = nonFxdRetInterface.getLastReturnId();
        if (lastInsuranceID != null) {
            lastNFReturnId = lastInsuranceID.getNfaId().toString();
            System.out.println("===========Id==" + lastNFReturnId);
        }
        
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        
        int id = 0;
        if (lastNFReturnId.equals("0")) {
            id = 1;
            newNFReurntId = "SIRNo-" + id + "/" + f.format(now);
        } else {
            
            String[] lastInspNos = lastNFReturnId.split("-");
            String lastDatesPatern = lastInspNos[0];
            
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newNFReurntId = "SIRNo-" + id + "/" + f.format(now);
            System.out.println("=============newNFReurntId " + newNFReurntId);
        }
        
        return newNFReurntId;
    }
    
    int sivDtlID;
    int count = 0;
    int count2 = 0;
    int countedWhenSave = 0;
    
    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            hrDepartmentsEntity.setDepName(event.getNewValue().toString());
            hrDepartmentsEntity = hrdepartmentInterface.getdepartmentInformation(hrDepartmentsEntity);
        }
    }
    List<MmsFixedassetRegstration> listFixedAssetReg;
    
    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        fixedassetRegstration.setDepartment(hrDepartmentsEntity);
        listFixedAssetReg = fixedAssetRegistrationBeanLocal.getFixedAssetInfoByDept(fixedassetRegstration);
        nonFxdRetEntity.setDepartment(hrDepartmentsEntity);
        
    }
    
    public void Wfsave() {
        
        workFlow.setNfaId(nonFxdRetEntity);
        workFlowInterface.create(workFlow);
        
    }
    ArrayList<MmsBinCard> bincardList = new ArrayList<>();
    
    public void editDataTable() {
        addOrModifyBundle = "Modify";
        nonFxdRetDtlEntity = returnNFAddDetailDataModel.getRowData();
        itemRegEntity = nonFxdRetDtlEntity.getItemId();
        
    }
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String MessageText = "";
    
    public boolean isReturnDateAfterRecievedDate(String recievedDate, String ReturnDate) {
        try {
            System.out.println("StringDateManipulation.todayInEC()===" + StringDateManipulation.todayInEC());
            if (df.parse(DateFormatFromConvertor(StringDateManipulation.todayInEC())).before(df.parse(recievedDate)) || df.parse(DateFormatFromConvertor(StringDateManipulation.todayInEC())).before(df.parse(ReturnDate))) {
                MessageText = "Make Sure Recieved date and date Return are Not In the Future";
                return true;
            } else if ((df.parse(ReturnDate).before(df.parse(recievedDate)))) {
                MessageText = "Date Return Can't be Before recieved date";
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
     int countOnSivDtl = 0;
    int countOnEdit = 0;
    int countResult = 0;

    public int getCountedWhenSave() {
        return countedWhenSave;
    }

    public void setCountedWhenSave(int countedWhenSave) {
        this.countedWhenSave = countedWhenSave;
    }

    public String addtoNFAReturnInfoDetail() {

        if (nonFxdRetEntity.getSelectOpt() == 1) {
            nonFxdRetDtlEntity.setItemCode(itemRegEntity.getMatCode());
            nonFxdRetDtlEntity.setUnitPrice(sivDtlEntity.getUnitPrice());
            nonFxdRetDtlEntity.setItemName(itemRegEntity.getMatName());
            nonFxdRetDtlEntity.setUnitOfMeasure(itemRegEntity.getUnitMeasure1());
            nonFxdRetEntity.addNonFixedAssetReturnDetail(nonFxdRetDtlEntity);
            sivEntity.getMmsSivDetailList().add(sivDtlEntity);
            sivDtlEntity.setSivId(sivEntity);

        } else {

            System.out.println("---------- inside Add DEtail -----------");
            nonFxdRetDtlEntity.setItemCode(itemRegEntity.getMatCode());
            nonFxdRetDtlEntity.setUnitPrice(sivDtlEntity.getUnitPrice());
            nonFxdRetDtlEntity.setItemName(itemRegEntity.getMatName());
            nonFxdRetDtlEntity.setUnitOfMeasure(itemRegEntity.getUnitMeasure1());
            nonFxdRetEntity.addNonFixedAssetReturnDetail(nonFxdRetDtlEntity);
            System.out.println("qunt1=====" + sivDtlEntity.getQuantity());
            sivDtlEntity.setQuantity(sivDtlEntity.getQuantity().add(nonFxdRetDtlEntity.getQuantity()));
            System.out.println("qunt=====" + sivDtlEntity.getQuantity());
            System.out.println("countsave=====" + sivDtlEntity.getQuantity());

        }

        clearPopUp();
        return null;
    }
//</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public void saveNFAReturn() {
        
        try {
            System.out.println("rec " + nonFxdRetEntity.getRecieved2Date() + "ret " + nonFxdRetEntity.getReturn2Date());
            if (isReturnDateAfterRecievedDate(nonFxdRetEntity.getRecieved2Date(), nonFxdRetEntity.getReturn2Date()) == true) {
                JsfUtil.addFatalMessage(MessageText);
            } else {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveNFAReturn", dataset)) {
                    System.out.println("security working");
                    if (updateStatus == 0) {
                        System.out.println("2");
                        if (returnNFAddDetailDataModel.getRowCount() > 0) {
                            System.out.println("some row exist");
                            try {
                                System.out.println("1");
                                System.out.println("3");
                                String formattedSRNNo = "SRN-" + nonFxdRetEntity.getNfaNo();
                                nonFxdRetEntity.setNfaNo(formattedSRNNo);
                                nonFxdRetEntity.setRetStatus(Constants.APPROVE_VALUE);
                                workFlow.setProcessedBy(SessionBean.getUserId());
                                nonFxdRetEntity.setRecievedBy(workFlow.getProcessedBy());
                                nonFxdRetEntity.setProcessedOn(workFlow.getProcessedOn());
                                nonFxdRetEntity.getNonFaRetList().add(workFlow);
                                nonFxdRetInterface.create(nonFxdRetEntity);
                                int binsize = bincardList.size();
                                for (int i = 0; i < binsize; i++) {
                                    binCardBeanLocal.edit(bincardList.get(i));
                                }
                                JsfUtil.addSuccessMessage(" A New Non Fixed Asset Return information is Saved! ");
                                clearPage();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JsfUtil.addFatalMessage("Something Error Occured on Saved the Data ");
                                
                            }
                        } else {
                            JsfUtil.addFatalMessage("Please Put atleast One Record on the Detail Form .");
                            
                        }
                        
                    } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                        
                        try {
                            nonFxdRetEntity.setProcessedOn(workFlow.getProcessedOn());
                            nonFxdRetEntity.setRecievedBy(SessionBean.getUserId());
                            nonFxdRetInterface.edit(nonFxdRetEntity);
                            Wfsave();
                            clearPage();
                            JsfUtil.addSuccessMessage("Non Fixed Asset Return has been Updated");
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                            
                        }
                        
                    } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                        System.out.println("12");
                        if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_VALUE);
                            nonFxdRetEntity.setRetStatus(Constants.APPROVE_VALUE);
                            workFlow.setDecision(Constants.APPROVE_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            nonFxdRetEntity.setRetStatus(Constants.CHECK_APPROVE_VALUE);
                            workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            nonFxdRetEntity.setRetStatus(Constants.APPROVE_REJECT_VALUE);
                            workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            nonFxdRetEntity.setRetStatus(Constants.CHECK_REJECT_VALUE);
                            workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                        }
                        workFlow.setNfaId(nonFxdRetEntity);
                        workFlowInterface.create(workFlow);
                        nonFxdRetInterface.edit(nonFxdRetEntity);
                        mmsNFAList.remove(nonFxdRetEntity);
                        clearPage();
                        JsfUtil.addSuccessMessage("Non Fixed Asset Return has been Updated");
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
    
    //<editor-fold defaultstate="collapsed" desc="ClearPopUp & RecreateDatamodel">
    private void recreateModelDetail() {
        returnNFAddDetailDataModel = null;
        returnNFAddDetailDataModel = new ListDataModel(new ArrayList<>(nonFxdRetEntity.getMmsNonFixedAssetReturnDtlList()));
    }

    private void clearPopUp() {
        nonFxdRetDtlEntity = null;
        sivDtlEntity = null;
        itemRegEntity = null;
        sivEntity = null;
        totalStockValue = null;
        binCard = null;
        addOrModifyBundle = "Add";
    }

    List<MmsFixedassetRegstration> listFixedAssetReg2;

    public List<MmsFixedassetRegstration> getListFixedAssetReg2() {
        if (listFixedAssetReg2 == null) {
            listFixedAssetReg2 = new ArrayList<>();
        }
        return listFixedAssetReg2;
    }

    public void setListFixedAssetReg(List<MmsFixedassetRegstration> listFixedAssetReg2) {
        this.listFixedAssetReg2 = listFixedAssetReg2;
    }

    public void handleSelectReturn(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

        }
    }

    public ArrayList<MmsNonFixedAssetReturn> searchByNFAno(String nFAno) {
        ArrayList<MmsNonFixedAssetReturn> NFAno;
        NFAno = null;
        nonFxdRetEntity.setNfaNo(nFAno);
        NFAno = nonFxdRetInterface.searchByReturnNo(nonFxdRetEntity);
        saveorUpdateBundle = "Update";
        return NFAno;
    }

    List<MmsNonFixedAssetReturn> allNFReturnInfoList;
    List<MmsNonFixedAssetReturn> checkerList = new ArrayList<>();
    private boolean disableUpdateBtn = false;

    public boolean isDisableUpdateBtn() {
        return disableUpdateBtn;
    }

    public void setDisableUpdateBtn(boolean disableUpdateBtn) {
        this.disableUpdateBtn = disableUpdateBtn;
    }

    public void searchReturnInformation2() {
//        disableUpdateBtn = true;
        String str = nonFxdRetEntity.getNfaNo();
        nonFxdRetEntity.setNfaNo(str);
        nonFxdRetEntity.setRecievedBy(SessionBean.getUserId());
//        if (nonFxdRetEntity.getNfaNo()!=null) {
            allNFReturnInfoList = nonFxdRetInterface.getNonreturnListsByParameter(nonFxdRetEntity);
            System.out.println("list===" + allNFReturnInfoList);
            checkerList.clear();
            checkerList = allNFReturnInfoList;
//            System.out.println("nonFxdRetEntity===" + nonFxdRetEntity.getNfaNo());
            recerateNFReturnSerachModel();

//        } else {
//            allNFReturnInfoList = nonFxdRetInterface.findAllInfo();
//        }
    }
    
    

    private void recerateNFReturnSerachModel() {
        mmsreturnNFSearchInfoDataModel = null;
        mmsreturnNFSearchInfoDataModel = new ListDataModel(new ArrayList<>(allNFReturnInfoList));
    }

    List<MmsNonFixedAssetReturn> allReturninfoList;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="RowSelection &update">
    public void onRowEdit2(RowEditEvent event) {
        renderPnlCreateReturn = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = mmsreturnNFSearchInfoDataModel.getRowIndex();
        nonFxdRetEntity = checkerList.get(rowIndex);
        hrDepartmentsEntity = nonFxdRetEntity.getDepartment();
        storeInfoEntity = nonFxdRetEntity.getStoreId();
        sivDtlEntity.setUnitPrice(nonFxdRetDtlEntity.getUnitPrice());
        itemRegEntity.setMatName(nonFxdRetDtlEntity.getItemName());
        itemRegEntity.setUnitOfMeasure(nonFxdRetDtlEntity.getUnitOfMeasure());
        recreateModelDetail();

    }

    public ArrayList<MmsStoreInformation> searchStoreInformation2(String storeName) {
        ArrayList<MmsStoreInformation> storeInformations = null;
        storeInfoEntity.setStoreName(storeName);
        storeInformations = storeInfoInterface.searchStoreInformation(storeInfoEntity);
        nonFxdRetEntity.setStoreId(storeInfoEntity);
        return storeInformations;

    }

    List<MmsSivDetail> itemcodeDtl2 = new ArrayList<>();

    public SelectItem[] getItemcodeDtl2() {
        return JsfUtil.getSelectItems(sivInterface.findallItemcodeInfo(), true);

    }

    public SelectItem[] getAllItemIssuedItems() {
        return JsfUtil.getSelectItems(sivInterface.findallItemcodeInfo(), true);
    }

    public void setItemcodeDtl2(List<MmsSivDetail> itemcodeDtl2) {
        this.itemcodeDtl2 = itemcodeDtl2;
    }

    public void handleSelectItemCode(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
            sivDtlEntity = (MmsSivDetail) event.getNewValue();
            System.out.println("============================== unit price+" + sivDtlEntity.getUnitPrice());
            itemRegEntity = sivDtlEntity.getItemId();
            nonFxdRetDtlEntity.setQuantity(sivDtlEntity.getQuantity());
            nonFxdRetDtlEntity.setItemCode(event.getNewValue().toString());
            nonFxdRetDtlEntity.setUnitPrice(sivDtlEntity.getUnitPrice());
            nonFxdRetDtlEntity.setItemName(sivDtlEntity.getItemId().getMatName());
            nonFxdRetDtlEntity.setUnitOfMeasure(sivDtlEntity.getItemId().getUnitMeasure1());
            sivDtlID = sivDtlEntity.getSivDetId();

        }
    }
    List<MmsSivDetail> unitPrice1 = new ArrayList<>();

    public List<MmsSivDetail> getUnitPrice1() {
        unitPrice1 = sivInterface.findallUnitpriceInfo();
        return unitPrice1;
    }

    public void setUnitPrice1(List<MmsSivDetail> unitPrice1) {
        this.unitPrice1 = unitPrice1;
    }

    public void handleSelectUnitPrice(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int unitP = Integer.parseInt(event.getNewValue().toString());
            BigDecimal num = new BigDecimal(unitP);
            sivDtlEntity.setUnitPrice(num);
            nonFxdRetDtlEntity.setUnitPrice(sivDtlEntity.getUnitPrice());
        }
    }
    List<MmsItemRegistration> itemReg1 = new ArrayList<>();

    public List<MmsItemRegistration> getItemReg1() {
        itemReg1 = itemRegInterface.findAllItemInfo();
        return itemReg1;
    }

    public void setItemReg1(List<MmsItemRegistration> itemReg1) {
        this.itemReg1 = itemReg1;
    }
  public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            nonFxdRetEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }
    public void handleSelectItemName(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            itemRegEntity.setMatName(event.getNewValue().toString());
            nonFxdRetDtlEntity.setItemName(event.getNewValue().toString());

        }
    }
    List<MmsItemRegistration> unitMeasure1 = new ArrayList<>();

    public List<MmsItemRegistration> getUnitMeasure1() {
        unitMeasure1 = itemRegInterface.findUnitMeasureInfo();
        return unitMeasure1;
    }

    public void setUnitMeasure1(List<MmsItemRegistration> unitMeasure1) {
        this.unitMeasure1 = unitMeasure1;
    }

    public void handleSelectUnitMeasure(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            itemRegEntity.setUnitOfMeasure(event.getNewValue().toString());
            nonFxdRetDtlEntity.setUnitOfMeasure(event.getNewValue().toString());

        }
    }

    List<MmsStoreInformation> storeInfoList = new ArrayList<>();

    public List<MmsStoreInformation> getStoreInfoList() {
        storeInfoList = storeInfoInterface.findAllStoreInfo();
        return storeInfoList;
    }

    public SelectItem[] getStoreList() {

        return JsfUtil.getSelectItems(storeInfoInterface.findAllStoreInfo(), true);
    }

    List<MmsStoreInformation> storeInfoLst = new ArrayList<>();

    public List<MmsStoreInformation> getStoreInfoLst() {
        storeInfoLst = storeInfoInterface.findAllStoreInfo();
        return storeInfoLst;
    }

    public void setStoreInfoList(List<MmsStoreInformation> storeInfoList) {
        this.storeInfoList = storeInfoList;
    }

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int storeIds = Integer.valueOf(event.getNewValue().toString());
            storeInfoEntity.setStoreId(storeIds);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);
            nonFxdRetEntity.setStoreId(storeInfoEntity);

        }
    }

    public ArrayList<HrEmployees> searchEmployeeInformation(String FirstName) {
        ArrayList<HrEmployees> employeeInformations = null;
        getEmployeEntity().setFirstName(FirstName);
        employeeInformations = hrInterface.searchEmployeeInfo(getEmployeEntity());
        return employeeInformations;
    }

    public HrEmployees getEmployeEntity() {
        if (employeEntity == null) {
            employeEntity = new HrEmployees();
        }
        return employeEntity;
    }

    /**
     * @param employeEntity the employeEntity to set
     */
    public void setEmployeEntity(HrEmployees employeEntity) {
        this.employeEntity = employeEntity;
    }

    public List<MmsSivDetail> getItemCodeList() {
        if (itemCodeList == null) {
            itemCodeList = new ArrayList<>();
        }
        return itemCodeList;
    }

    public void setItemCodeList(List<MmsSivDetail> itemCodeList) {
        this.itemCodeList = itemCodeList;
    }

    public List<MmsSiv> getSivNoLists() {
        if (sivNoLists == null) {
            sivNoLists = new ArrayList<>();
        }
        return sivNoLists;
    }

    public void setSivNoLists(List<MmsSiv> sivNoLists) {
        this.sivNoLists = sivNoLists;
    }

    public List<MmsSivDetail> getItemCodelistWhenRecierver() {
        if (itemCodelistWhenRecierver == null) {
            itemCodelistWhenRecierver = new ArrayList<>();
            itemCodelistWhenRecierver = sivInterface.findallItemcodeInfo();
        }
        return itemCodelistWhenRecierver;
    }

    public void setItemCodelistWhenRecierver(List<MmsSivDetail> itemCodelistWhenRecierver) {
        this.itemCodelistWhenRecierver = itemCodelistWhenRecierver;
    }

    private List<MmsSiv> sivList;
    private List<MmsSiv> sivList2;

    public void searchIssuedItemsForEmployee(ValueChangeEvent event) {

        if (nonFxdRetEntity.getSelectOpt() == 1) {
            System.out.println(".......event.getNewValue().toString()......" + event.getNewValue().toString());
            System.out.println("......lela amarach........" + storeInfoEntity.getStoreId());
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            nonFxdRetEntity.setStoreId(storeInfoEntity);
            sivEntity.setIssuedTo(nonFxdRetEntity.getReturnedBy());
            System.out.println("store id from mmsstore Req==" + nonFxdRetEntity.getStoreId());
            if (hrDepartmentsEntity.getDepName() != null) {
                System.out.println("dep name====" + hrDepartmentsEntity.getDepName());
                System.out.println("dep id====" + hrDepartmentsEntity.getDepId());
                mmsStorereq.setDeptId(hrDepartmentsEntity);
                System.out.println("dep id on store===" + mmsStorereq.getDeptId().getDepId());
                hrDepartmentsEntity = mmsStorereq.getDeptId();
                System.out.println("pass===" + hrDepartmentsEntity.getDepId());
                sivNoLists = sivBeanLocal.getSivNoByDepIdAndStroeId(storeInfoEntity, hrDepartmentsEntity);

            }

            for (int i = 0; i < sivEntity.getMmsSivDetailList().size(); i++) {
                MmsNonFixedAssetReturnDtl returnDet = new MmsNonFixedAssetReturnDtl();
                returnDet.setItemCode(sivEntity.getMmsSivDetailList().get(i).getItemId().getMatCode());
                returnDet.setItemName(sivEntity.getMmsSivDetailList().get(i).getItemId().getMatName());
                returnDet.setUnitOfMeasure(sivEntity.getMmsSivDetailList().get(i).getItemId().getUnitMeasure1());
                returnDet.setQuantity(sivEntity.getMmsSivDetailList().get(i).getQuantity());
                returnDet.setUnitPrice(sivEntity.getMmsSivDetailList().get(i).getUnitPrice());

            }

        } else {

            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            nonFxdRetEntity.setStoreId(storeInfoEntity);
            itemCodeList = nonFxdRetInterface.getItemCodeLists(storeInfoEntity);
            MmsNonFixedAssetReturnDtl returnDet2 = new MmsNonFixedAssetReturnDtl();

        }

    }

    public void changeValueForSiv(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            System.out.println("choiced");
            sivEntity = (MmsSiv) changeEvent.getNewValue();
            itemCodelistWhenRecierver = sivBeanLocal.getItemCodelistWhenRecierver(sivEntity);
        }
    }

    public void searchIssuedItemsForEmployee1(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            nonFxdRetEntity.setStoreId(storeInfoEntity);
            int size = sivList.size();
            for (int i = 0; i < size; i++) {
                sivEntity.getMmsSivDetailList().addAll(sivList.get(i).getMmsSivDetailList()); //to find item id and item code
            }
            MmsNonFixedAssetReturnDtl returnDet2 = new MmsNonFixedAssetReturnDtl();

            System.out.println("---size on the detail-----" + sivEntity.getMmsSivDetailList().size());
            returnDet2.setItemCode(sivEntity.getMmsSivDetailList().get(0).getItemId().getMatCode());
            returnDet2.setItemName(sivEntity.getMmsSivDetailList().get(0).getItemId().getMatName());
            returnDet2.setUnitOfMeasure(sivEntity.getMmsSivDetailList().get(0).getItemId().getUnitMeasure1());
            returnDet2.setQuantity(sivEntity.getMmsSivDetailList().get(0).getQuantity());
            returnDet2.setUnitPrice(sivEntity.getMmsSivDetailList().get(0).getUnitPrice());

        }

    }

    public List<MmsSiv> getSivList() {
        if (sivList == null) {
            sivList = new ArrayList<>();
        }
        return sivList;
    }

    /**
     * @param sivList the sivList to set
     */
    public void setSivList(List<MmsSiv> sivList) {
        this.sivList = sivList;
    }

    /**
     * @return the sivEntity
     */
    public MmsSiv getSivEntity() {
        if (sivEntity == null) {
            sivEntity = new MmsSiv();
        }
        return sivEntity;
    }

    /**
     * @param sivEntity the sivEntity to set
     */
    public void setSivEntity(MmsSiv sivEntity) {
        this.sivEntity = sivEntity;
    }

    public void rowSelect(SelectEvent event) {
        nonFxdRetEntity = (MmsNonFixedAssetReturn) event.getObject();
        nonFxdRetEntity.setNfaId(nonFxdRetEntity.getNfaId());

        populateUI();
    }

    public void getReturnDate(SelectEvent event) {
        System.out.println("Listiner 1 =========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            nonFxdRetEntity.setReturnDate(arrival);

            System.out.println("=======ReturnDate=========" + nonFxdRetEntity.getReturnDate());
        }
    }

    public void getReceivedDate(SelectEvent event) {
        System.out.println("Listiner 2 =========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            nonFxdRetEntity.setRecievedDate(arrival);

            System.out.println("=======RecievedDate=========" + nonFxdRetEntity.getRecievedDate());
        }
    }

    public void changeReturnType(ValueChangeEvent e) {

        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {

                setRenderReciver(true);

                setRenderOther(false);

                nonFxdRetEntity.setSelectOpt(1);
                storeInfoEntity = new MmsStoreInformation();

            } else {

                setRenderReciver(false);

                setRenderOther(true);

                nonFxdRetEntity.setSelectOpt(2);
                storeInfoEntity = new MmsStoreInformation();

            }
        }

    }

    public void handleSelectReturn2(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String status = event.getNewValue().toString();
            if (status.equalsIgnoreCase("Normal")) {
                nonFxdRetDtlEntity.setItemStatus("Normal");
            } else if (status.equalsIgnoreCase("Scrub")) {
                nonFxdRetDtlEntity.setItemStatus("Scrub");

            } else if (status.equalsIgnoreCase("Broken")) {
                nonFxdRetDtlEntity.setItemStatus("Broken");
            } else if (status.equalsIgnoreCase("Damaged")) {

                nonFxdRetDtlEntity.setItemStatus("Damaged");
            }

        }
    }

    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {

                nonFxdRetEntity = (MmsNonFixedAssetReturn) event.getNewValue();

                populateUI();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Failed to process ! Try Again Reloading the Page");
        }
    }

    public void populateUI() {

        renderPnlCreateReturn = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        setIsRenderedIconWorkflow(true);
        nonFxdRetEntity = nonFxdRetInterface.getSelectedRequest(nonFxdRetEntity.getNfaId());
        storeInfoEntity = nonFxdRetEntity.getStoreId();
        System.out.println("====store name==" + nonFxdRetEntity.getStoreId());
        hrDepartmentsEntity = nonFxdRetEntity.getDepartment();
        if (workflow.isPrepareStatus()) {
            workFlow.setProcessedOn(nonFxdRetEntity.getProcessedOn());

        }

        recreateModelDetail();
        recreateWfDataModel();
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        WfMmsProcessedDataModel = new ListDataModel(nonFxdRetEntity.getNonFaRetList());
    }

    public void goBackToSearchpageBtnAction() {

        renderPnlCreateReturn = false;
        renderPnlManPage = true;
        renderpnlToSearchPage = false;
    }

    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (DepartmentNames != null) {
            for (HrDepartments k : DepartmentNames) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }

    public ArrayList<MmsItemRegistration> searchMatInformationByPrefix(String matcode) {
        ArrayList<MmsItemRegistration> itemInformations = null;// to make the previous search  paramaters and results null;
        getItemRegEntity().setMatCode(matcode);

        itemInformations = itemBeanLocal.searchMatInformationByPrefix(matcode);
        return itemInformations;
    }

    public void handleSelectItemNameAuto(SelectEvent event) {

        if (event.getObject() != null) {
//            itemRegEntity = (MmsItemRegistration) event.getObject();
            String matCode = event.getObject().toString();
            itemRegEntity = itemBeanLocal.searchByMaterialCode(matCode);
            nonFxdRetDtlEntity.setItemId(itemRegEntity);
            totalStockValue = fmsTotalStockValueBeanLocal.getWeightedAverageValueByMatCode(matCode);
            nonFxdRetDtlEntity.setUnitPrice(BigDecimal.valueOf(0));
            binCard = binCardBeanLocal.getItemInfoByStoreIdAndItemId(itemRegEntity, storeInfoEntity);
        }
    }

    public void addDetail() {
        nonFxdRetEntity.addNonFixedAssetReturnDetail(nonFxdRetDtlEntity);
        recreateModelDetail();
        clearPopUp();
    }

    public void handleSelectStoreName1(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();

            nonFxdRetEntity.setStoreId(storeInfoEntity);

        }

    }

    List<FmsLuSystem> lusystems;

    public FmsGeneralLedger getGeneralLedger() {
        if (generalLedger == null) {
            generalLedger = new FmsGeneralLedger();
        }
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public FmsSystemJobJunction getFmsSystemJobJunction() {
        if (fmsSystemJobJunction == null) {
            fmsSystemJobJunction = new FmsSystemJobJunction();
        }
        return fmsSystemJobJunction;
    }

    public void setFmsSystemJobJunction(FmsSystemJobJunction fmsSystemJobJunction) {
        this.fmsSystemJobJunction = fmsSystemJobJunction;
    }

    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsCostcSystemJunction getCostcSysJunction() {
        if (costcSysJunction == null) {
            costcSysJunction = new FmsCostcSystemJunction();
        }
        return costcSysJunction;
    }

    public void setCostcSysJunction(FmsCostcSystemJunction costcSysJunction) {
        this.costcSysJunction = costcSysJunction;
    }

    public List<FmsLuSystem> getLusystems() {
        if (lusystems == null) {
            lusystems = new ArrayList<>();
        }
        return lusystems;
    }

    public void setLusystems(List<FmsLuSystem> lusystems) {
        this.lusystems = lusystems;
    }

    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }
    List<FmsCostCenter> costcenterList = new ArrayList<>();
    List<FmsCostcSystemJunction> costcenterJunction = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;

    public List<FmsSystemJobJunction> getSysJobNOList() {
        if (sysJobNOList == null) {
            sysJobNOList = new ArrayList<>();
        }
        return sysJobNOList;
    }

    public void setSysJobNOList(List<FmsSystemJobJunction> sysJobNOList) {
        this.sysJobNOList = sysJobNOList;
    }

    public List<FmsGeneralLedger> getGlList() {
        if (glList == null) {
            glList = new ArrayList<>();
        }
        return glList;
    }

    public void setGlList(List<FmsGeneralLedger> glList) {
        this.glList = glList;
    }

    public List<FmsCostcSystemJunction> getCostcenterJunction() {
        if (costcenterJunction == null) {
            costcenterJunction = new ArrayList<>();
        }
        return costcenterJunction;
    }

    public void setCostcenterJunction(List<FmsCostcSystemJunction> costcenterJunction) {
        this.costcenterJunction = costcenterJunction;
    }
    final Integer projectType = 2;
    final Integer nonProjectType = 2;
    boolean renderJobNo = false;

    public boolean isRenderJobNo() {
        return renderJobNo;
    }

    public void setRenderJobNo(boolean renderJobNo) {
        this.renderJobNo = renderJobNo;
    }

    public void SystemChange(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsLuSystem = (FmsLuSystem) event.getNewValue();
                renderJobNo = false;
                if (fmsLuSystem.getType().equals(projectType)) {
                    glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
                    System.out.println("======project type ==true====" + renderJobNo);
                    renderJobNo = true;
                    sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();

                } else {
                    glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
                }
                costcenterJunction = fmsLuSystem.getFmsCostcSystemJunctionList();

            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void CostCenterChange(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {

                costcSysJunction = (FmsCostcSystemJunction) event.getNewValue();
                fmsCostCenter = costcSysJunction.getFmsCostCenter();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void SysJobNoChange(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                System.out.println("=====event========" + event.getNewValue());
                fmsSystemJobJunction = (FmsSystemJobJunction) event.getNewValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public SelectItem[] getCostCenterBySystemLU() {
        fmsLuSystem = fmsCostCenter.getSystemId();
        List<FmsCostCenter> costcenterList = new ArrayList<>();
        if (fmsLuSystem != null) {
            SelectItem[] listcc = null;
            costcenterList = fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem);
        }
        return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                generalLedger.setGeneralLedgerCode(event.getNewValue().toString());
                generalLedger = fmsGeneralLedgerBeanLocal.getGLDetail(generalLedger);
                generalLedger = (FmsGeneralLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public String addAccountInfoDetail() {
        try {
            Concatenate();
            nonFxdRetEntity.setAccountCode(display_conn);
            clearAccountCodePnl();
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faild to Add Account, Refresh And Try Again.");
        }
        return null;
    }
    private String display_conn;
    private String Conc2 = "";

    public void Concatenate() {
        try {
            String CC = "CC";
            String SL = "SL";
            String CC_display = "CC";
            String SL_display = "SL";

            if (fmsCostCenter.getCostCenterId() != null) {
                CC = fmsCostCenter.getSystemName();
                CC_display = fmsCostCenter.getCostCenterId().toString();
            }
            if (fmsLuSystem.getType().equals(projectType)) {
                display_conn = fmsLuSystem.getSystemCode() + "-" + fmsCostCenter.getSystemName() + "-" + generalLedger.getGeneralLedgerCode() + "-" + fmsSystemJobJunction.getJobNo();
            } else {
                display_conn = fmsLuSystem.getSystemCode() + "-" + fmsCostCenter.getSystemName() + "-" + generalLedger.getGeneralLedgerCode();
            }

        } catch (Exception e) {
        }
    }

    private void clearAccountCodePnl() {
        fmsCostCenter = null;
        fmsLuSystem = null;
        fmsSystemJobJunction = null;
        generalLedger = null;

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
}
//</editor-fold>

