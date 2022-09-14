package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.integration.OBIntegrationBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreReqBeanLocal;
import et.gov.eep.mms.businessLogic.MmsProjectBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsProject;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
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
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.stock.FmsTotalStockValue;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.pms.businessLogic.PmsCreateProjectBeanLocal;
import et.gov.eep.pms.businessLogic.PmsWorkAuthorizationBeanLocal;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.pms.entity.PmsWorkAuthorization;

//</editor-fold>
/**
 *
 * @author Minab
 */
@Named(value = "storeRequisitionController")
@ViewScoped
public class StoreRequisitionController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity's">
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private FmsLuSystem fmsLuSystem;
    @Inject
    MmsProject project;
    @Inject
    private HrDepartments hrDepartmentEntity;
    @Inject
    private MmsStoreInformation storeEntity;
    @Inject
    HrEmployees employeEntity;
    @Inject
    MmsStorereq requisition;
    @Inject
    MmsStorereqDetail requisitionDetail;
    @Inject
    MmsItemRegistration itemregistrationEntity;
    @Inject
    PmsCreateProjects projectEntity;
    @Inject
    PmsWorkAuthorization pmsWorkAuthorization;
    @Inject
    FmsOperatingBudget1 operationalBudget;
    @Inject
    FmsOperatingBudgetDetail operatingBudgetDetail;
    @Inject
    FmsGeneralLedger generalLedger;
    @Inject
    MmsStoreInformation toStoreEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    private FmsCostCenter fmsCostCenter;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;
    @Inject
    private FmsCostcSystemJunction costcSysJunction;
    @Inject
    private FmsTotalStockValue totalStockValue;
    @Inject
    private MmsBinCard binCard;
    @Inject
    MmsGrn mmsGrn;
    @Inject
    MmsGrnDetail mmsGrnDetail;
    @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB's">
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    OBIntegrationBeanLocal operationalBudgetInterface;
    @EJB
    PmsCreateProjectBeanLocal projectBeanLocal;
    @EJB
    MmsItemRegisrtationBeanLocal itemBeanLocal;
    @EJB
    HrDepartmentsIntegrationBeanLocal hrDepartmentsInterface;

    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsProjectBeanLocal projectLocalBean;
    @EJB
    MmsStoreReqBeanLocal requisitionLocal;
    @EJB
    MmsStoreInformationBeanLocal storeInterface;
    @EJB
    HREmployeesBeanLocal hrInterface;
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
    PmsWorkAuthorizationBeanLocal workAuthorizationBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Fields">
    List<FmsCostCenter> costcenterList = new ArrayList<>();
    List<FmsCostcSystemJunction> costcenterJunction = new ArrayList<>();
    List<PmsWorkAuthorization> pmsJobNos;
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<WfMmsProcessed> WfList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    DataModel<MmsStorereqDetail> requisitionDetailsModel;
    private DataModel<MmsStorereq> mmsSRSearchInfoDataModel;
    private List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private List<MmsStoreInformation> StoreList;
    private List<MmsStoreInformation> toStoreList;
    private List<MmsStoreInformation> StoreListSearch;
    private List<PmsCreateProjects> projectList;
    private List<MmsStorereq> allRequistionInfoList;
    private List<MmsStorereq> mmsStorereqColumnNameList;
    private List<MmsItemRegistration> materialNameList = null;
    private MmsStorereq selectedSr;
    private MmsStorereqDetail selectedSrDetail;
    private List<MmsItemRegistration> materialCodelist = null;
    private static List<HrDepartments> araListe;
    private List<MmsGrn> mmsGrnList;
    private List<MmsGrnDetail> mmsGrnDetailsList;
    ArrayList<MmsItemRegistration> itemInformations1 = new ArrayList<>();
    private static final String oilAndLubricantsSubCatCode = "09";
    private static final int toStoreCheckedValue = 1;
    private static final int toStoreUnCheckedValue = 0;
    boolean showProjectPanle = false;
    int updateStatus = 0;
    private String btn_SaveUpdate = "Save";
    private String saveorUpdateBundle = "Save";
    private String AddorModify = "Add";
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private boolean disableBtnCreate = false;
    private boolean disableBtnAccountCode = false;
    private boolean renderPlateNo = false;
    private boolean renderDecision = false;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateSR = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private boolean disableItemcode = true;
    private boolean renderApprovedQuantity = false;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String newPayNo;
    private String lastPaymentNo = "0";
    private String userName;
    private int userId;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    private List<MmsStorereq> mmsSrList;
    private List<FmsGeneralLedger> generalLedgersList;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private boolean isCheckedToStore = false;
    private boolean isCheckedSpecIdentification = true;
    private boolean isHideGrNo = false;
    private boolean isRenderedDepartment = false;
    private String isSelectedToStores = "";
    private final int fromSearch = 0;
    private int fromSearchOrFromRequest;// to determine wheather it the request is from workflow or from direct search so 0 is from search and 1 is from workflow request
    private final int fromRequest = 1;
    private String selectedValue = "";
    List<FmsLuSystem> lusystems;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    boolean renderJobNo = false;
    private String display_conn;
    private String Conc2 = "";
    List<WfMmsProcessed> workflowList;
    BigDecimal requestQty;//Request Qty
    BigDecimal receivedQty;//Item Balance
    private List<String> mmsStoreColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     *
     */
    public StoreRequisitionController() {

    }

    @PostConstruct
    public void init() {
        StoreList = storeInterface.findAllStoreInfo();
        generalLedgersList = fmsGeneralLedgerBeanLocal.findAll();
        toStoreList = storeInterface.findAllStoreInfo();
        disableBtnCreate = false;
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                mmsSrList = requisitionLocal.findSrListByWfStatus(Constants.CHECK_APPROVE_VALUE);
                setRenderApprovedQuantity(true);
            } else if (workflow.isCheckStatus()) {
                mmsSrList = requisitionLocal.findSrListForCheckerByWfStatus(Constants.PREPARE_VALUE, Constants.APPROVE_REJECT_VALUE);
            }

            renderDecision = true;
            isRenderedIconNitification = true;
            disableBtnAccountCode = true;
        } else if (workflow.isPrepareStatus()) {
            setRenderApprovedQuantity(false);
            mmsSrList = requisitionLocal.findSrListByWfStatus(Constants.CHECK_REJECT_VALUE);

            renderDecision = false;
            if (mmsSrList != null) {
                isRenderedIconNitification = mmsSrList.size() > 0;
            }

        }
        pmsJobNos = workAuthorizationBeanLocal.findAll();
        glList = fmsGeneralLedgerBeanLocal.findAll();
        lusystems = fmsLuSystemBeanLocal.findAll();
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        setUserId(SessionBean.getUserId());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        allDepartmentsList = hrDepartmentsInterface.findAll();
        System.out.println("dept size =" + allDepartmentsList.size());
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        getMmsStoreColumnNameList();
    }

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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Department tree Handling">
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

    public SelectItem[] getGLList() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void GLListener(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            generalLedger = (FmsGeneralLedger) event.getNewValue();
//            itemregistrationEntity.setFmsGeneralLedger(generalLedger);
            itemregistrationEntity.setStoreId(storeEntity);
            if (requisition.getSrType().equals("fixed")) {
                itemregistrationEntity.setItemGroup(1);
            } else if (requisition.getSrType().equals("nonfixed")) {
                itemregistrationEntity.setItemGroup(0);
            }
            materialNameList = itemBeanLocal.searchMaterialInfoByStoreAndMatNameAndItemGroupAndGLID(itemregistrationEntity);
            operatingBudgetDetail = operationalBudgetInterface.fetchUsingDepartmentAndGL(hrDepartmentEntity, generalLedger);
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentEntity.setDepId(key);
        hrDepartmentEntity = hrDepartmentsInterface.findByDepartmentId(hrDepartmentEntity);

        requisition.setDeptId(hrDepartmentEntity);
        StoreList = storeInterface.searchStoreByParameterDepartmentId(hrDepartmentEntity);
        if (isSelectedToStores.equals("specific Identification")) {
            mmsGrnList = requisitionLocal.searchGrnDepartmentId(hrDepartmentEntity);
            System.out.println("============mmsGrnList======");
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide();");
    }

    public int getUserId() {
        return userId;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter And Setters">
    public PmsWorkAuthorization getPmsWorkAuthorization() {
        if (pmsWorkAuthorization == null) {
            pmsWorkAuthorization = new PmsWorkAuthorization();
        }
        return pmsWorkAuthorization;
    }

    public void setPmsWorkAuthorization(PmsWorkAuthorization pmsWorkAuthorization) {
        this.pmsWorkAuthorization = pmsWorkAuthorization;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public ColumnNames getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ColumnNames columnNames) {
        this.columnNames = columnNames;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public FmsSystemJobJunction getFmsSystemJobJunction() {
        if (fmsSystemJobJunction == null) {
            fmsSystemJobJunction = new FmsSystemJobJunction();
        }
        return fmsSystemJobJunction;
    }

    public void setFmsSystemJobJunction(FmsSystemJobJunction fmsSystemJobJunction) {
        this.fmsSystemJobJunction = fmsSystemJobJunction;
    }

    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
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

    public List<FmsGeneralLedger> getGeneralLedgersList() {
        return generalLedgersList;
    }

    public void setGeneralLedgersList(List<FmsGeneralLedger> generalLedgersList) {
        this.generalLedgersList = generalLedgersList;
    }

    public List<MmsItemRegistration> getMaterialNameList() {
        if (materialNameList == null) {
            materialNameList = new ArrayList<>();
        }
        return materialNameList;
    }

    public void setMaterialNameList(List<MmsItemRegistration> materialNameList) {
        this.materialNameList = materialNameList;
    }

    public FmsOperatingBudgetDetail getOperatingBudgetDetail() {
        if (operatingBudgetDetail == null) {
            operatingBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return operatingBudgetDetail;
    }

    public void setOperatingBudgetDetail(FmsOperatingBudgetDetail operatingBudgetDetail) {
        this.operatingBudgetDetail = operatingBudgetDetail;
    }

    public FmsGeneralLedger getGeneralLedger() {
        if (generalLedger == null) {
            generalLedger = new FmsGeneralLedger();
        }
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public boolean isRenderApprovedQuantity() {
        return renderApprovedQuantity;
    }

    public void setRenderApprovedQuantity(boolean renderApprovedQuantity) {
        this.renderApprovedQuantity = renderApprovedQuantity;
    }

    public MmsStoreInformation getToStoreEntity() {
        if (toStoreEntity == null) {
            toStoreEntity = new MmsStoreInformation();
        }
        return toStoreEntity;
    }

    public void setToStoreEntity(MmsStoreInformation toStoreEntity) {
        this.toStoreEntity = toStoreEntity;
    }

    public int getFromSearchOrFromRequest() {
        return fromSearchOrFromRequest;
    }

    public void setFromSearchOrFromRequest(int fromSearchOrFromRequest) {
        this.fromSearchOrFromRequest = fromSearchOrFromRequest;
    }

    public boolean isIsCheckedToStore() {
        return isCheckedToStore;
    }

    public void setIsCheckedToStore(boolean isCheckedToStore) {
        this.isCheckedToStore = isCheckedToStore;
    }

    public boolean isIsCheckedSpecIdentification() {
        return isCheckedSpecIdentification;
    }

    public void setIsCheckedSpecIdentification(boolean isCheckedSpecIdentification) {
        this.isCheckedSpecIdentification = isCheckedSpecIdentification;
    }

    public boolean isIsHideGrNo() {
        return isHideGrNo;
    }

    public void setIsHideGrNo(boolean isHideGrNo) {
        this.isHideGrNo = isHideGrNo;
    }

    public String getIsSelectedToStores() {
        return isSelectedToStores;
    }

    public void setIsSelectedToStores(String isSelectedToStores) {
        this.isSelectedToStores = isSelectedToStores;
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

    public List<MmsStorereq> getMmsSrList() {
        return mmsSrList;
    }

    public void setMmsSrList(List<MmsStorereq> mmsSrList) {
        this.mmsSrList = mmsSrList;
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

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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

    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
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

    /**
     *
     * @return
     */
    public MmsStorereq getRequisition() {
        if (requisition == null) {
            requisition = new MmsStorereq();
        }
        return requisition;
    }

    /**
     *
     * @param requisition
     */
    public void setRequisition(MmsStorereq requisition) {
        this.requisition = requisition;
    }

    /**
     *
     * @return
     */
    public MmsStorereqDetail getRequisitionDetail() {
        if (requisitionDetail == null) {
            requisitionDetail = new MmsStorereqDetail();
        }
        return requisitionDetail;
    }

    /**
     *
     * @param requisitionDetail
     */
    public void setRequisitionDetail(MmsStorereqDetail requisitionDetail) {
        this.requisitionDetail = requisitionDetail;
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

    public boolean isRenderPlateNo() {
        return renderPlateNo;
    }

    public void setRenderPlateNo(boolean renderPlateNo) {
        this.renderPlateNo = renderPlateNo;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsStorereqDetail> getRequisitionDetailsModel() {
        if (requisitionDetailsModel == null) {
            requisitionDetailsModel = new ListDataModel<>();
        }
        return requisitionDetailsModel;
    }

    /**
     *
     * @param requisitionDetailsModel
     */
    public void setRequisitionDetailsModel(DataModel<MmsStorereqDetail> requisitionDetailsModel) {
        this.requisitionDetailsModel = requisitionDetailsModel;
    }

    /**
     *
     * @return
     */
    public MmsProject getProject() {
        if (project == null) {
            project = new MmsProject();
        }
        return project;
    }

    /**
     *
     * @param project
     */
    public void setProject(MmsProject project) {
        this.project = project;
    }

    /**
     *
     * @return
     */
    public boolean isShowProjectPanle() {
        return showProjectPanle;
    }

    /**
     *
     * @param showProjectPanle
     */
    public void setShowProjectPanle(boolean showProjectPanle) {
        this.showProjectPanle = showProjectPanle;
    }

    public MmsStoreInformation getStoreEntity() {
        if (storeEntity == null) {
            storeEntity = new MmsStoreInformation();

        }
        return storeEntity;
    }

    public void setStoreEntity(MmsStoreInformation storeEntity) {
        this.storeEntity = storeEntity;
    }

    /**
     *
     * @return
     */
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

    public PmsCreateProjects getProjectEntity() {
        if (projectEntity == null) {
            projectEntity = new PmsCreateProjects();
        }
        return projectEntity;
    }

    public void setProjectEntity(PmsCreateProjects projectEntity) {
        this.projectEntity = projectEntity;
    }

    public FmsOperatingBudget1 getOperationalBudget() {
        if (operationalBudget == null) {
            operationalBudget = new FmsOperatingBudget1();
        }
        return operationalBudget;
    }

    public void setOperationalBudget(FmsOperatingBudget1 operationalBudget) {
        this.operationalBudget = operationalBudget;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getAddorModify() {
        return AddorModify;
    }

    public void setAddorModify(String AddorModify) {
        this.AddorModify = AddorModify;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isDisableBtnAccountCode() {
        return disableBtnAccountCode;
    }

    public void setDisableBtnAccountCode(boolean disableBtnAccountCode) {
        this.disableBtnAccountCode = disableBtnAccountCode;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateSR() {
        return renderPnlCreateSR;
    }

    public void setRenderPnlCreateSR(boolean renderPnlCreateSR) {
        this.renderPnlCreateSR = renderPnlCreateSR;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public DataModel<MmsStorereq> getMmsSRSearchInfoDataModel() {
        if (mmsSRSearchInfoDataModel == null) {
            mmsSRSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsSRSearchInfoDataModel;
    }

    public void setMmsSRSearchInfoDataModel(DataModel<MmsStorereq> mmsSRSearchInfoDataModel) {
        this.mmsSRSearchInfoDataModel = mmsSRSearchInfoDataModel;
    }

    public MmsStorereq getSelectedSR() {
        return selectedSr;
    }

    public void setSelectedSR(MmsStorereq selectedSr) {
        this.selectedSr = selectedSr;
    }

    public MmsStorereqDetail getSelectedSRDetail() {
        return selectedSrDetail;
    }

    public void setSelectedSRDetail(MmsStorereqDetail selectedSrDetail) {
        this.selectedSrDetail = selectedSrDetail;
    }

    public boolean isDisableItemcode() {
        return disableItemcode;
    }

    public void setDisableItemcode(boolean disableItemcode) {
        this.disableItemcode = disableItemcode;
    }

    public HrDepartments getHrDepartmentEntity() {
        if (hrDepartmentEntity == null) {
            hrDepartmentEntity = new HrDepartments();
        }
        return hrDepartmentEntity;
    }

    public void setHrDepartmentEntity(HrDepartments hrDepartmentEntity) {
        this.hrDepartmentEntity = hrDepartmentEntity;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public String getMinAppDate() {
        return this.requisition.getRequestedDate();
    }

    /**
     *
     * @return
     */
    public String getBtn_SaveUpdate() {
        return btn_SaveUpdate;
    }

    /**
     *
     * @param btn_SaveUpdate
     */
    public void setBtn_SaveUpdate(String btn_SaveUpdate) {
        this.btn_SaveUpdate = btn_SaveUpdate;
    }

    public List<MmsStoreInformation> getToStoreList() {
        return toStoreList;
    }

    public void setTotoreList(List<MmsStoreInformation> TotoreList) {
        this.toStoreList = TotoreList;
    }

    public List<MmsStoreInformation> getStoreListSearch() {
        StoreListSearch = storeInterface.findAllStoreInfo();
        return StoreListSearch;
    }

    public void setStoreListSearch(List<MmsStoreInformation> StoreListSearch) {
        this.StoreListSearch = StoreListSearch;
    }

    public List<MmsStorereq> getMmsStorereqColumnNameList() {
        if (mmsStorereqColumnNameList == null) {
            mmsStorereqColumnNameList = new ArrayList<>();
//            mmsStorereqColumnNameList = requisitionLocal.getMmsStorereqColumnNameList();
        }
        return mmsStorereqColumnNameList;
    }

    public void setMmsStorereqColumnNameList(List<MmsStorereq> mmsStorereqColumnNameList) {
        this.mmsStorereqColumnNameList = mmsStorereqColumnNameList;
    }

    public List<MmsStoreInformation> getStoreList() {

        return StoreList;
    }

    public List<PmsCreateProjects> getProjectList() {
        projectList = projectBeanLocal.findAllProjects();
        return projectList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<PmsWorkAuthorization> getPmsJobNos() {
        if (pmsJobNos == null) {
            pmsJobNos = new ArrayList<>();
        }

        return pmsJobNos;
    }

    public void setPmsJobNos(List<PmsWorkAuthorization> pmsJobNos) {
        this.pmsJobNos = pmsJobNos;
    }

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

    public boolean isRenderJobNo() {
        return renderJobNo;
    }

    public void setRenderJobNo(boolean renderJobNo) {
        this.renderJobNo = renderJobNo;
    }

    public boolean isIsRenderedDepartment() {
        return isRenderedDepartment;
    }

    public void setIsRenderedDepartment(boolean isRenderedDepartment) {
        this.isRenderedDepartment = isRenderedDepartment;
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
        if (event.getNewValue() != null) {
            generalLedger = (FmsGeneralLedger) event.getNewValue();
            requisition.setGeneralLedger(generalLedger);
        }

    }

    public List<MmsGrn> getMmsGrnList() {
        if (mmsGrnList == null) {
            mmsGrnList = new ArrayList<>();
        }
        return mmsGrnList;
    }

    public void setMmsGrnList(List<MmsGrn> mmsGrnList) {
        this.mmsGrnList = mmsGrnList;
    }

    public List<MmsGrnDetail> getMmsGrnDetailsList() {
        if (mmsGrnDetailsList == null) {
            mmsGrnDetailsList = new ArrayList<>();
        }
        return mmsGrnDetailsList;
    }

    public void setMmsGrnDetailsList(List<MmsGrnDetail> mmsGrnDetailsList) {
        this.mmsGrnDetailsList = mmsGrnDetailsList;
    }

    public MmsGrn getMmsGrn() {
        if (mmsGrn == null) {
            mmsGrn = new MmsGrn();
        }
        return mmsGrn;
    }

    public void setMmsGrn(MmsGrn mmsGrn) {
        this.mmsGrn = mmsGrn;
    }

    public MmsGrnDetail getMmsGrnDetail() {
        if (mmsGrnDetail == null) {
            mmsGrnDetail = new MmsGrnDetail();
        }
        return mmsGrnDetail;
    }

    public void setMmsGrnDetail(MmsGrnDetail mmsGrnDetail) {
        this.mmsGrnDetail = mmsGrnDetail;
    }

    public ArrayList<MmsItemRegistration> getItemInformations1(String matCodes) {
        if (itemInformations1 == null) {
            itemInformations1 = new ArrayList<>();
//            itemInformations1 = itemBeanLocal.searchMatInformationByPrefix(matCodes);
        }
        return itemInformations1;
    }

    public ArrayList<MmsItemRegistration> searchMatInformationByPrefix(String matcode) {
        ArrayList<MmsItemRegistration> itemInformations = new ArrayList<>();// to make the previous search  paramaters and results null;
//        itemInformations = itemBeanLocal.searchMatInformationByPrefix(matcode);
        System.out.println("---- " + itemInformations.size());
        return itemInformations;
    }

    public void setItemInformations1(ArrayList<MmsItemRegistration> itemInformations1) {
        this.itemInformations1 = itemInformations1;
    }

    public List<WfMmsProcessed> getWorkflowList() {
        return workflowList;
    }

    public void setWorkflowList(List<WfMmsProcessed> workflowList) {
        this.workflowList = workflowList;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Sequence Number Generator">
    /**
     *
     * @return
     */
    public String generateRequisitionNo() {
        if (updateStatus == 1) {
            newPayNo = requisition.getSrNo();
        } else {
            MmsStorereq lastPaymentNoObj = requisitionLocal.getLastPaymentNo();
            if (lastPaymentNoObj != null) {
                lastPaymentNo = lastPaymentNoObj.getStoreReqId().toString();
            }
//
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newPayment = 0;
            if (lastPaymentNo.equals("0")) {
                newPayment = 1;
                newPayNo = "SR-" + newPayment + "/" + f.format(now);
            } else {

                String[] lastInspNos = lastPaymentNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                //split in to '1'  And 'YYYY'
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                System.err.println("--uuu--" + newPayment);
                newPayment = newPayment + 1;
                newPayNo = "SR-" + newPayment + "/" + f.format(now);

            }
        }

        return newPayNo;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save Method">
//      public void handleselectOptionswf(ValueChangeEvent event) {
//        if (null != event.getNewValue()) {
//            selectedValue = event.getNewValue().toString();
//        }
//        filterbyStatus();
//    }
    public void WfSave() {
        wfMmsProcessed.setStoreReqId(requisition);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }

    public void saveStoreRequisiton() {
        System.out.println("===Inside Save==============");
//        if(!requisitionDetailsModel.isRowAvailable()){
//            JsfUtil.addErrorMessage("Saving without data in the table is not allowed");
//        }
        //     else{
        try {
            System.out.println("Wf" + wfMmsProcessed.getProcessedBy());

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveStoreRequisiton", dataset)) {
                if (updateStatus == 0 && workflow.isPrepareStatus()) {
                    System.out.println("===inside update==0========");
                    if (requisitionDetailsModel.getRowCount() <= 0) {
                        JsfUtil.addFatalMessage("Saving without Detail data is not allowed");
                    } else {
                        try {
                            System.out.println("=======inside the second try===============");

                            generateRequisitionNo();
                            requisition.setSrNo(newPayNo);
                            requisition.setGeneralLedger(generalLedger);
                            wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                            requisition.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            requisition.setProcessedBy(wfMmsProcessed.getProcessedBy());
                            requisition.addRequisitionIdToWorkflow(wfMmsProcessed);
                            requisition.setStatus(Constants.PREPARE_VALUE);

                            if (isSelectedToStores.equals("specific Identification")) {
//                                checkReqGreaterThanStoreQty();

                                requisition.setSrCases(isSelectedToStores);

                                System.out.println("I'm Registared to " + requisition.getSrCases());
                                requisition.setDeptId(hrDepartmentEntity);
                                requisitionDetail.setGrnId(mmsGrn);

//                                for (int index = 0; index < mmsGrnDetailsList.size(); index++) {
//                                    requisition.getMmsStorereqDetailList().get(index).setItemId(mmsGrnDetailsList.get(index).getItemId());
//                                    requisition.getMmsStorereqDetailList().get(index).setGrnId(mmsGrn);
//                                }
                            } else if (isSelectedToStores.equals("store")) {
                                requisition.setSrCases(isSelectedToStores);

                            } else {
                                isSelectedToStores = "regular";
                                requisition.setSrCases(isSelectedToStores);
                                requisition.setDeptId(hrDepartmentEntity);
                            }
                            requisitionDetail.setItemId(itemregistrationEntity);
                            requisitionDetail.setStoreReqId(requisition);
                            requisition.addRequisitionDetialInfo(requisitionDetail);
                            requisitionLocal.create(requisition);

                            JsfUtil.addSuccessMessage("Store Requisition Data Is Saved" + requisition.getSrNo());

                            // return
                            clearPage();

                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Error Occured on Data Saved");

                            //return null;
                        }
                    }
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    try {

                        requisition.setGeneralLedger(generalLedger);
                        requisition.setProcessedOn(wfMmsProcessed.getProcessedOn());

                        requisition.setStatus(Constants.PREPARE_VALUE);
                        if (isSelectedToStores.equals("specific Identification")) {
                            requisition.setSrCases(isSelectedToStores);
                            System.out.println("I'm Registared to " + requisition.getSrCases());
                            requisition.setDeptId(hrDepartmentEntity);
                            requisitionDetail.setGrnId(mmsGrn);
//                                }
                        } else if (isSelectedToStores.equals("store")) {
                            requisition.setSrCases(isSelectedToStores);

                        } else {
                            isSelectedToStores = "regular";
                            requisition.setSrCases(isSelectedToStores);
                            requisition.setDeptId(hrDepartmentEntity);
                        }
                        requisitionDetail.setItemId(itemregistrationEntity);
                        requisitionDetail.setStoreReqId(requisition);
                        requisition.addRequisitionDetialInfo(requisitionDetail);
                        requisitionLocal.edit(requisition);
                        wfMmsProcessedBeanLocal.saveOrUpdate(wfMmsProcessed);

                        JsfUtil.addSuccessMessage("Store Requisition Data is Modified");
                        saveorUpdateBundle = "Save";

                        //return
                        clearPage();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something Error Occured on Data Modified");

                    }
                    //return null;
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        requisition.setStatus(Constants.APPROVE_VALUE);

                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        requisition.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        requisition.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        requisition.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    requisition.addRequisitionIdToWorkflow(wfMmsProcessed);
                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    requisitionLocal.edit(requisition);
                    mmsSrList.remove(requisition);
                    clearPage();
                    JsfUtil.addSuccessMessage("Store Requisition Data Modified");

                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
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

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search, Event Handlers and other Methods">
   /*This method is used to clearSave
     */
    public String clearSave() {
        requisition = null;
        requisitionDetail = null;
        return null;
    }
    /*This method is used to SystemChange
     */

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
    /*This method is used to CostCenterChange
     */

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
    /*This method is used to SystemJobNo Change
     */

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

    public String addAccountInfoDetail() {
        try {
            Concatenate();
            requisition.setCostCenter(display_conn);
            clearAccountCodePnl();
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faild to Add Account, Refresh And Try Again.");
        }
        return null;
    }

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

    /*This method is used to ClearPage
     */
    public String clearPage() {
        setIsCheckedToStore(false);
        requisition = new MmsStorereq();
        requisitionDetail = new MmsStorereqDetail();
        hrDepartmentEntity = new HrDepartments();
        requisitionDetailsModel = new ListDataModel<>();
        storeEntity = new MmsStoreInformation();
        updateStatus = 0;
        projectEntity = new PmsCreateProjects();
        employeEntity = new HrEmployees();
        pmsWorkAuthorization = new PmsWorkAuthorization();
        mmsSRSearchInfoDataModel = new ListDataModel<>();
        requisitionDetailsModel = null;
        generalLedger = new FmsGeneralLedger();
        operatingBudgetDetail = new FmsOperatingBudgetDetail();
        totalStockValue = new FmsTotalStockValue();
        wfMmsProcessed.setProcessedOn(null);
        disableBtnAccountCode = false;
        wfMmsProcessed = null;
        toStoreEntity = null;
        setSelectedValue(null);
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        mmsGrn = new MmsGrn();
        itemregistrationEntity = new MmsItemRegistration();
        return null;
    }
    /*This method is used to clearPage2
     */

    public String clearPage2() {
        setIsCheckedToStore(false);
        requisition = new MmsStorereq();
        requisitionDetail = new MmsStorereqDetail();
        hrDepartmentEntity = new HrDepartments();
        requisitionDetailsModel = new ListDataModel<>();
        updateStatus = 0;
        projectEntity = new PmsCreateProjects();
        employeEntity = new HrEmployees();
        requisitionDetailsModel = null;
        generalLedger = new FmsGeneralLedger();
        operatingBudgetDetail = new FmsOperatingBudgetDetail();
        totalStockValue = new FmsTotalStockValue();
        wfMmsProcessed.setProcessedOn(null);
        disableBtnAccountCode = false;
        toStoreEntity = null;
        setSelectedValue(null);
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsSystemJobJunction = new FmsSystemJobJunction();

        return null;
    }
    /*This method is used to add Requisition Info Detail
     */

    public String addRequisitionInfoDetail() {
        boolean result = requisitionDetail.getQuantity().compareTo(BigDecimal.ZERO) > 1;
        System.out.println("=========Result======" + result);

        if (requisitionDetail.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("=======inside else==========");
            requisitionDetail.setApprovedQuantity((requisitionDetail.getQuantity().abs()));
            addDetail();
        } else {
            JsfUtil.addFatalMessage("Requested Quantity Cannot be 0");
        }
        return null;
    }
    /*This method is used to add Detail
     */

    public void addDetail() {
        if (isSelectedToStores.equals("specific Identification")) {
            if (mmsGrnDetail.getReceivedQuantity() != null) {
                receivedQty = mmsGrnDetail.getReceivedQuantity();
                requestQty = requisitionDetail.getQuantity();//Request Qty
            }
            requisitionDetail.setGrnId(mmsGrn);
        } else {
            if (binCard.getCurrentQuantity() != null) {
                receivedQty = binCard.getCurrentQuantity();
                requestQty = requisitionDetail.getQuantity();//Request Qty
            }
        }
        if (receivedQty != null) {
            if ((receivedQty.intValue()) >= (requestQty.intValue())) {
                requisitionDetail.setStoreReqId(requisition);
                requisitionDetail.setItemId(itemregistrationEntity);
                System.out.println("size " + requisition.getMmsStorereqDetailList().size());
                if (requisition.getMmsStorereqDetailList().size() > 0) {
                    for (int countIndex = 0; countIndex < requisition.getMmsStorereqDetailList().size(); countIndex++) {
                        System.out.println("=== " + requisition.getMmsStorereqDetailList().get(countIndex).getItemId());
                        if (!requisition.getMmsStorereqDetailList().get(countIndex).getItemId().getMatCode().equals(requisitionDetail.getItemId().getMatCode())) {
                            requisition.getMmsStorereqDetailList().add(requisitionDetail);
                        } else {
                            JsfUtil.addFatalMessage("Duplication Not Allowed");
                        }
                    }
                } else {
                    requisition.getMmsStorereqDetailList().add(requisitionDetail);
                }
                recreateRequisitionDataModel();
                setRenderPlateNo(false);
                clearPopup();
            } else {
                JsfUtil.addFatalMessage("Check as Request Qty can't be More than Item Balance");
            }
        } else {
            JsfUtil.addFatalMessage("Check as Item Balance have a Value");
        }
    }
    /*This method is used to recreate Requisition DataModel
     */

    private void recreateRequisitionDataModel() {
        requisitionDetailsModel = null;
        requisitionDetailsModel = new ListDataModel(new ArrayList(requisition.getMmsStorereqDetailList()));
    }
    /*This method is used to clearPopup
     */

    private void clearPopup() {
        itemregistrationEntity = null;
        requisitionDetail = null;
        binCard = null;
        totalStockValue = null;
        mmsGrnDetail = null;
        mmsGrn = null;

    }
    /*This method is used to clearAccountCodePnl
     */

    private void clearAccountCodePnl() {
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        generalLedger = new FmsGeneralLedger();
        costcSysJunction = new FmsCostcSystemJunction();
        costcenterJunction = new ArrayList<>();
        glList = new ArrayList<>();

    }

    /*This method is used to updateRequisitionDetail
     */
    public void updateRequisitionDetail() {
        requisitionDetail = getRequisitionDetailsModel().getRowData();
    }
    /*This method is used to search By SrNumber
     */

    public ArrayList<MmsStorereq> searchBySrNumber(String srno) {
        ArrayList<MmsStorereq> srnumber = null;
        requisition.setSrNo(srno);
        //srnumber = requisitionLocal.searchBySRNo(requisition);
        btn_SaveUpdate = "Update";
        return srnumber;
    }

    /*This method is used to search Project JobNo
     */
    public ArrayList<MmsProject> searchProjectJobNo(String jobNo) {
        ArrayList<MmsProject> projects = null;
        project.setJobNo(jobNo);
        projects = projectLocalBean.searchProjectJobNo(project);
        return projects;
    }

    /*This method is used to search Employee Information
     */
    public ArrayList<HrEmployees> searchEmployeeInformation(String FirstName) {
        ArrayList<HrEmployees> employeeInformations = null;// to make the previous search  paramaters and results null;
        employeEntity.setFirstName(FirstName);
        employeeInformations = hrInterface.searchEmployeeInfo(employeEntity);
        return employeeInformations;
    }

    /*This method is used to get Employee Info
     */
    public void getEmployeeInfo(SelectEvent event) {
        employeEntity.setFirstName(event.getObject().toString());
        employeEntity = hrInterface.getByFirstName(employeEntity);
        requisition.setRequestedBy(employeEntity);
    }

    /*This method is used to get By Requester Name
     */
    public void getByRequesterName(SelectEvent event) {
        requisition = (MmsStorereq) event.getObject();
        recreateRequisitionDataModel();
        updateStatus = 1;
        btn_SaveUpdate = "Update";

    }

    /*This method is used to get By Job No
     */
    public void getByJobNo(SelectEvent event) {
        project = (MmsProject) event.getObject();
        project.getJobNo();

    }

    /*This method is used to handle select Options
     */
    public void handleselectOptions(ValueChangeEvent event) {

        String opt = event.getNewValue().toString();
        System.out.println("============option======" + opt);
        if (opt.equalsIgnoreCase("project")) {
            showProjectPanle = true;
        } else {
            showProjectPanle = false;
        }

    }
    /*This method is used to handle select Option SrType
     */

    public void handleselectOptionSrType(ValueChangeEvent event) {

        String opt = event.getNewValue().toString();

    }

    /*This method is used to search Item Name
     */
    public ArrayList<MmsItemRegistration> searchItemName(String itemName) {
        ArrayList<MmsItemRegistration> itemNames = null;
        itemregistrationEntity.setMatName(itemName);
        itemNames = itemBeanLocal.searchByMaterialName(itemregistrationEntity);
        return itemNames;
    }
    /*This method is used to material Info Search list
     */

    public List<MmsItemRegistration> materialInfoSearchlist(String materialCode) {

        itemregistrationEntity.setMatCode(materialCode);
        itemregistrationEntity.setStoreId(storeEntity);

        materialCodelist = itemBeanLocal.searchMaterialInfoByStoreAndMatCode(itemregistrationEntity);

        return materialCodelist;
    }
    /*This method is used to handle Select ItemName
     */

    public void handleSelectItemName(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            itemregistrationEntity = (MmsItemRegistration) event.getNewValue();
            requisitionDetail.setItemId(itemregistrationEntity);
            totalStockValue = fmsTotalStockValueBeanLocal.getWeightedAverageValueByMatCode(itemregistrationEntity.getMatCode());

        }
    }
    /*This method is used to handle Select Item NameAuto
     */

    public void handleSelectItemNameAuto(SelectEvent event) {
        System.out.println("=======event value====");
        if (event.getObject() != null) {
            itemregistrationEntity = (MmsItemRegistration) event.getObject();
            System.out.println("=======event value====" + itemregistrationEntity.getMatCode());
            requisitionDetail.setItemId(itemregistrationEntity);
            if (isSelectedToStores.equals("specific Identification")) {
                mmsGrnDetailsList = requisitionLocal.getMmsDetailInfoByGrnId(mmsGrn);
                for (int rowIndex = 0; rowIndex < mmsGrnDetailsList.size(); rowIndex++) {
                    mmsGrnDetail.setUnitPrice(mmsGrnDetailsList.get(rowIndex).getUnitPrice());
                    requisitionDetail.setUnitPrice(mmsGrnDetail.getUnitPrice());
                    System.out.println("=== " + requisitionDetail.getUnitPrice());
                    mmsGrnDetail.setReceivedQuantity(mmsGrnDetailsList.get(rowIndex).getReceivedQuantity());
                    requisitionDetail.setQuantity(mmsGrnDetail.getReceivedQuantity());
                    System.out.println("=== " + requisitionDetail.getQuantity());
                }

            } else {
                binCard = binCardBeanLocal.getItemInfoByStoreIdAndItemId(itemregistrationEntity, storeEntity);
                System.out.println("=================bincard=====" + binCard.getCurrentQuantity());
                binCard.setCurrentQuantity(binCard.getCurrentQuantity());
                System.out.println("=================bincard=====" + binCard.getCurrentQuantity());

            }

        }
    }
    /*This method is used to get Item Code
     */

    public void getItemCode(SelectEvent event) {
        itemregistrationEntity = (MmsItemRegistration) event.getObject();
        requisitionDetail.setItemId(itemregistrationEntity);

    }
    /*This method is used to get Mms ItemInfo
     */

    public void getMmsItemInfo(SelectEvent event) {
        itemregistrationEntity.setMatCode(event.getObject().toString());
        itemregistrationEntity = itemBeanLocal.getMmsItemInfoByCode(itemregistrationEntity);

    }
    /*This method is used to handle Select StoreName
     */

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            storeEntity = (MmsStoreInformation) event.getNewValue();
            requisition.setRequestStore(storeEntity);
        }

    }
    /*This method is used to handle Select Search Store Name
     */

    public void handleSelectSearchStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            storeEntity = (MmsStoreInformation) event.getNewValue();
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeEntity.setStoreId(Id);

            requisition.setRequestStore(storeEntity);
            setDisableItemcode(false);
        }

    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()=="+event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            requisition.setColumnName(columnNameResolver.getCol_Name_FromTable());
            requisition.setColumnValue(null);
        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsStoreColumnNameList() {
        mmsStoreColumnNameList = requisitionLocal.getMmsStorereqColumnNameList();
        if (mmsStoreColumnNameList == null) {
            mmsStoreColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + mmsStoreColumnNameList);
        if (mmsStoreColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsStoreColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsStoreColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsStoreColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsStoreColumnNameList;
    }

    public void setMmsStoreColumnNameList(List<String> mmsStoreColumnNameList) {
        this.mmsStoreColumnNameList = mmsStoreColumnNameList;
    }

    /*This method is used to handle Select To StoreName
     */
    public void handleSelectToStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            toStoreEntity = (MmsStoreInformation) event.getNewValue();

            requisition.setToStore(toStoreEntity);

        }

    }
    /*This method is used to OnCheckToStore
     */

    public void onCheckToStore(ValueChangeEvent event) {
        clearPopup();
        if (event.getNewValue() != null) {
            System.out.println("check ---" + event.getNewValue().toString());

            if (event.getNewValue().toString().equalsIgnoreCase("store")) {//checked
                System.out.println("==========store checked======");
                setIsCheckedToStore(true);
                setIsCheckedSpecIdentification(true);
                requisition.setToIsiv(toStoreCheckedValue);
                getToStoreList().remove(storeEntity);
                isHideGrNo = false;
                hrDepartmentEntity = new HrDepartments();
                isSelectedToStores = "store";
                requisition.setSrCases(isSelectedToStores);
            } else if (event.getNewValue().toString().equalsIgnoreCase("specific Identification")) {//specific Identificationification checked
                System.out.println("==========specific Identification checked======");
                isSelectedToStores = "specific Identification";
                setIsCheckedSpecIdentification(false);
                setIsCheckedToStore(false);
                setIsHideGrNo(true);
                requisition.setToIsiv(toStoreUnCheckedValue);
                getToStoreList().add(storeEntity);
                disableBtnAccountCode = false;
                isHideGrNo = true;
                itemregistrationEntity = new MmsItemRegistration();
                requisition.setSrCases(isSelectedToStores);
            } else {//unchecked
                System.out.println("==========unchecked both======");
                setIsCheckedToStore(false);
                setIsCheckedSpecIdentification(true);
                requisition.setToIsiv(toStoreUnCheckedValue);
                getToStoreList().add(storeEntity);
                disableBtnAccountCode = false;
                isHideGrNo = false;
            }
        } else {
            isSelectedToStores = "regular";
            requisition.setSrCases(isSelectedToStores);
            allDepartmentsList = hrDepartmentsInterface.findAll();
            System.out.println("Spec dep't size =" + allDepartmentsList.size());
            root = new DefaultTreeNode("Root", null);
            recursive(allDepartmentsList, 0, root);
            root2 = getRoot();
        }
    }
    /*This method is used to handle Select JOB
     */

    public void handleSelectJOB(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            pmsWorkAuthorization = (PmsWorkAuthorization) event.getNewValue();
            requisition.setJobId(pmsWorkAuthorization);
        }
    }
    /*This method is used to go Back Search Button Action
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateSR = false;
        renderPnlManPage = true;
    }
    /*This method is used to createNewSRInfo
     */

    public void createNewSRInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateSR = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateSR = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }

    }
    /*This method is used to btn Search Action
     */

    public void btn_Search_Action() {
        clearPage2();
        renderPnlCreateSR = false;
        renderPnlManPage = true;
    }
    /*This method is used to search SR Information
     */

    public void searchSRInformation() {
        System.out.println("in search");
        requisition.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + requisition.getProcessedBy());
        allRequistionInfoList = requisitionLocal.getSRListsByParameter(columnNameResolver, requisition, requisition.getColumnValue());
        recerateSrDetail(); 
    }
    private void recerateSrDetail() {
        mmsSRSearchInfoDataModel = null;
        mmsSRSearchInfoDataModel = new ListDataModel(new ArrayList<>(allRequistionInfoList));

    }
    /*This method is used to grnNo Change Event
     */

    public void grnNoChangeEvent(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            mmsGrn = (MmsGrn) changeEvent.getNewValue();
            System.out.println("---- " + mmsGrn);
            mmsGrnDetailsList = requisitionLocal.getMmsDetailInfoByGrnId(mmsGrn);
            for (int grdtIndex = 0; grdtIndex < mmsGrnDetailsList.size(); grdtIndex++) {
                itemInformations1 = new ArrayList<>();
                String matCode = mmsGrnDetailsList.get(grdtIndex).getItemId().getMatCode();
                itemregistrationEntity = mmsGrnDetailsList.get(grdtIndex).getItemId();
                itemInformations1.add(itemregistrationEntity);
                System.out.println("So here " + itemInformations1.size());
                getItemInformations1(matCode);
            }
        }
    }
    /*This method is used to recerate SrDetail
     */

    
    /*This method is used to OnRowEditTemp
     */

    public void onRowEditTemp(SelectEvent event) {
        requisition = (MmsStorereq) event.getObject();
        storeEntity = requisition.getRequestStore();
        StoreList = new ArrayList<>();
        StoreList.add(storeEntity);
        renderPnlCreateSR = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        updateStatus = 1;

        recreateRequisitionDataModel();
        setUserId(requisition.getProcessedBy());
        setUserName(SessionBean.getUserName());// change this 
        wfMmsProcessed.setProcessedOn(requisition.getProcessedOn());
    }
    /*This method is used to OnRowEdit
     */

    public void onRowEdit(SelectEvent event) {
        setFromSearchOrFromRequest(fromSearch);
        requisition = (MmsStorereq) event.getObject();
        populateUI();

    }
    /*This method is used to UpdateSRDetail
     */

    public void updateSRDetail(SelectEvent event) {
        AddorModify = "Modify";
        itemInformations1 = new ArrayList<>();
        requisitionDetail = (MmsStorereqDetail) event.getObject();
        itemregistrationEntity = requisitionDetail.getItemId();
        if (isSelectedToStores.equals("specific Identification")) {
            mmsGrnList = requisitionLocal.searchGrnDepartmentId(hrDepartmentEntity);
            mmsGrn = requisitionDetail.getGrnId();
            mmsGrnDetailsList = requisitionLocal.getMmsDetailInfoByGrnId(mmsGrn);
            for (int grdtIndex = 0; grdtIndex < mmsGrnDetailsList.size(); grdtIndex++) {
                itemInformations1 = new ArrayList<>();
                String matCode = mmsGrnDetailsList.get(grdtIndex).getItemId().getMatCode();
                itemregistrationEntity = mmsGrnDetailsList.get(grdtIndex).getItemId();
                receivedQty = mmsGrnDetailsList.get(grdtIndex).getReceivedQuantity();
                mmsGrnDetail.setReceivedQuantity(receivedQty);
                itemInformations1.add(itemregistrationEntity);
                System.out.println("So here " + itemInformations1.size());
                getItemInformations1(matCode);
            }
        } else {
            binCard.setCurrentQuantity(receivedQty);
        }
    }
    /*This method is used to OnSelectRequest
     */

    public void onSelectRequest(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            requisition = (MmsStorereq) event.getNewValue();
            setFromSearchOrFromRequest(fromRequest);
            populateUI();

        }
    }
    /*This method is used to populateUI
     */

    public void populateUI() {

        storeEntity = requisition.getRequestStore();
        if (requisition.getToStore() != null) {
            toStoreEntity = requisition.getToStore();
            setIsCheckedToStore(true);
            toStoreList = new ArrayList<>();
            toStoreList.add(toStoreEntity);
        } else {
            hrDepartmentEntity = requisition.getDeptId();

        }
        StoreList = new ArrayList<>();
        StoreList.add(storeEntity);
        generalLedger = requisition.getGeneralLedger();
        pmsWorkAuthorization = requisition.getJobId();
        employeEntity = requisition.getRequestedBy();//requester
        renderPnlCreateSR = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        if (requisition.getSrCases().equals("store") || requisition.getSrCases().equals("specific Identification")) {
            isSelectedToStores = requisition.getSrCases();
        }
        isSelectedToStores = requisition.getSrCases();
        if (requisition.getSrCases().equals("specific Identification")) {
            isHideGrNo = true;
            isCheckedSpecIdentification = false;
            isSelectedToStores = requisition.getSrCases();
            hrDepartmentEntity = requisition.getDeptId();
            allDepartmentsList = requisitionLocal.DepNameListFromGrn();
            root = new DefaultTreeNode("Root", null);
            recursive(allDepartmentsList, 0, root);
            root2 = getRoot();
//            }
        } else {
            isHideGrNo = false;
            isCheckedSpecIdentification = true;
            disableBtnAccountCode = true;
        }
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        saveorUpdateButtonHandler();

        updateStatus = 1;
        setIsRenderedIconWorkflow(true);
        recreateRequisitionDataModel();
        recreateWfDataModel();
        if (fromSearchOrFromRequest == fromSearch) {
            setUserName(SessionBean.getUserName());// change this 
            wfMmsProcessed.setProcessedOn(requisition.getProcessedOn());
        }
    }
    /*This method is used to save or Update Button Handler
     */

    public void saveorUpdateButtonHandler() {
        if (workflow.isPrepareStatus()) {
            if (requisition.getStatus().equals(Constants.APPROVE_VALUE) || requisition.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
                disableBtnCreate = true;
            }
        } else if (workflow.isCheckStatus()) {
            if (requisition.getStatus().equals(Constants.APPROVE_VALUE)) {
                disableBtnCreate = true;
            }
        }

    }
    /*This method is used to recreate WorkFlow DataModel {

     */

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        for (int i = 0; i < requisition.getWfMmsProcessedList().size(); i++) {
            if (requisition.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (requisition.getWfMmsProcessedList().get(i).getDecision() == 1 || requisition.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    requisition.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (requisition.getWfMmsProcessedList().get(i).getDecision() == 2 || requisition.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    requisition.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                requisition.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }

        WfMmsProcessedDataModel = new ListDataModel(requisition.getWfMmsProcessedList());
    }
    /*This method is used to checkReqGreaterThanStoreQty {
     */

    public void checkReqGreaterThanStoreQty() {
        if (isSelectedToStores.equals("specific Identification")) {
            for (int rowCount = 0; rowCount < mmsGrnDetailsList.size(); rowCount++) {
                requestQty = requisitionDetail.getQuantity();//Request Qty
                receivedQty = mmsGrnDetailsList.get(rowCount).getReceivedQuantity();//Item Balance 
            }
        } else {
            requestQty = requisitionDetail.getQuantity();//Request Qty
            receivedQty = binCard.getCurrentQuantity();//Item Balance
        }
        if (receivedQty != null) {
            if ((requestQty.intValue()) > (receivedQty.intValue())) {
                System.out.println("Warning: " + requestQty + " > " + receivedQty);
                JsfUtil.addFatalMessage("Request Qty can't be More than Item Balance");
            }
        }
    }
     //</editor-fold>
}
