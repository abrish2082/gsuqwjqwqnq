/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.integration.GeneralLedgerIntegrationBeanLocal;
import et.gov.eep.fcms.integration.OBIntegrationBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsNeedAssessmentBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSubcatBeanLocal;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.prms.businessLogic.ServiceRegistrationBeanLocal;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;

/**
 *
 * @author Minab
 */
@Named(value = "needAssessmentController")
@ViewScoped
public class NeedAssessmentController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="EJB">

    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    private HrDepartmentsBeanLocal hrdepartmentInterface;
    @EJB
    private MmsSubcatBeanLocal matSubcatInterface;
    @EJB
    private MmsNeedAssessmentBeanLocal needAssessmentinterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private ServiceRegistrationBeanLocal serviceAndWorkInterface;
    @EJB
    private budgetYearLookUpBeanLocal budgetYearLookUpBean;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBean;
    @EJB
    private OBIntegrationBeanLocal oBIntegrationBeanLocal;
    @EJB
    private GeneralLedgerIntegrationBeanLocal generalLedgerInterface;
    @EJB
    OBIntegrationBeanLocal operationalBudgetInterface;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    //</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="Entities">
    @Inject
    private MmsNeedAssessment needAssessmentEntity;
    @Inject
    private MmsNeedAssessmentDtl needAssessmentDtlEntity;
    @Inject
    FmsOperatingBudgetDetail operatingBudgetDetail;
    @Inject

    private MmsItemRegistration itemRegstrationEntity;
    @Inject
    private MmsSubCat subCategoryEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsNeedAssessmentService needAssessmentServiceEntity;
    @Inject
    PrmsServiceAndWorkReg serviceAndWorkEntity;
    @Inject
    FmsOperatingBudget1 fmsOperatingBudget;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetail;
    @Inject
    FmsGeneralLedger fmsGeneralLedgerEntity;
    @Inject
    FmsLuBudgetYear fmsLubudgetYear;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsNeedAssessmentDtl> mmsNeedAssessDetailDataModel;
    private DataModel<MmsNeedAssessmentService> mmsNeedAssessServiceDataModel;
    private DataModel<MmsNeedAssessmentService> mmsNeedAssessWorkDataModel;
    private DataModel<MmsNeedAssessment> mmsNeedSearchInfoDataModel;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<FmsOperatingBudget1> approvedBudgetCodeLists;
    private static List<HrDepartments> araListe;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean disableSearchItem = true;
    private boolean renderForGoods = true;
    private boolean renderForService = false;
    private boolean renderForWork = false;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private String selectedValue = "";

    private String userName;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    private List<MmsNeedAssessment> mmsNsList;
    List<WfMmsProcessed> WfList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    private final int fromRequest = 1;
    private int fromSearchOrFromRequest;// to determine wheather it the request is from workflow or from direct search so 0 is from search and 1 is from workflow request
    private final int fromSearch = 0;
    private boolean renderDecision = true;
    private List<FmsGeneralLedger> generalLedgersList;
    private List<PrmsServiceAndWorkReg> servicelist;
    List<MmsStoreInformation> storeInfoLst = new ArrayList<>();
    List<PrmsServiceAndWorkReg> workList;
    private final int fixedAsset = 1;
    Set<String> checkMaterialname = new HashSet<>();
    Set<String> checkServiceName = new HashSet<>();
    Set<String> checkWorkName = new HashSet<>();
    List<MmsNeedAssessment> allNeedAssesmentinfoList;
    List<MmsItemRegistration> materialNameList = null;
    List<MmsStoreInformation> StoreList;
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    int saveStatus = 0;
    List<MmsItemRegistration> itemNameList;
    private List<FmsGeneralLedger> GeneralLedgerList;
    private MmsNeedAssessmentDtl selectedRowForDetail;
    private MmsNeedAssessmentService selectedServiceRowForDetail;
    private MmsNeedAssessment selectedrow;
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
     private List<String> mmsNeedColumnNameList;
//</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="PostConstruct, recursive">
    /**
     * Creates a new instance of NeedAssessmentController
     */
    public NeedAssessmentController() {
    }

    @PostConstruct
    public void init() {
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                mmsNsList = needAssessmentinterface.findNsListByWfStatus(Constants.CHECK_APPROVE_VALUE);
            } else if (workflow.isCheckStatus()) {
                mmsNsList = needAssessmentinterface.findNsListForCheckerByWfStatus(Constants.PREPARE_VALUE, Constants.APPROVE_REJECT_VALUE);
            }

            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {

            mmsNsList = needAssessmentinterface.findNsListByWfStatus(Constants.CHECK_REJECT_VALUE);
            renderDecision = false;
            if (mmsNsList != null) {
                isRenderedIconNitification = mmsNsList.size() > 0;
            }

        }
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        needAssessmentEntity.setPurchaseType("Goods");
        allDepartmentsList = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);

        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        getMmsNeedColumnNameList();
//        
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
//</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public List<MmsStoreInformation> getStoreInfoLst() {
        storeInfoLst = storeInfoInterface.findAllStoreInfo();
        return storeInfoLst;
    }

    public void setStoreInfoLst(List<MmsStoreInformation> storeInfoLst) {
        this.storeInfoLst = storeInfoLst;
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

    public List<MmsNeedAssessment> getMmsNsList() {
        return mmsNsList;
    }

    public void setMmsNsList(List<MmsNeedAssessment> mmsNsList) {
        this.mmsNsList = mmsNsList;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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

    public int getFromSearchOrFromRequest() {
        return fromSearchOrFromRequest;
    }

    public void setFromSearchOrFromRequest(int fromSearchOrFromRequest) {
        this.fromSearchOrFromRequest = fromSearchOrFromRequest;
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

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public WorkFlow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkFlow workflow) {
        this.workflow = workflow;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public MmsNeedAssessment getNeedAssessmentEntity() {
        if (needAssessmentEntity == null) {
            needAssessmentEntity = new MmsNeedAssessment();
        }
        return needAssessmentEntity;
    }

    public void setNeedAssessmentEntity(MmsNeedAssessment needAssessmentEntity) {
        this.needAssessmentEntity = needAssessmentEntity;
    }

    public MmsNeedAssessmentDtl getNeedAssessmentDtlEntity() {
        if (needAssessmentDtlEntity == null) {
            needAssessmentDtlEntity = new MmsNeedAssessmentDtl();
        }
        return needAssessmentDtlEntity;
    }

    public void setNeedAssessmentDtlEntity(MmsNeedAssessmentDtl needAssessmentDtlEntity) {
        this.needAssessmentDtlEntity = needAssessmentDtlEntity;
    }

    public MmsNeedAssessmentService getNeedAssessmentServiceEntity() {
        if (needAssessmentServiceEntity == null) {
            needAssessmentServiceEntity = new MmsNeedAssessmentService();
        }
        return needAssessmentServiceEntity;
    }

    public void setNeedAssessmentServiceEntity(MmsNeedAssessmentService needAssessmentServiceEntity) {
        this.needAssessmentServiceEntity = needAssessmentServiceEntity;
    }

    public PrmsServiceAndWorkReg getServiceAndWorkEntity() {
        if (serviceAndWorkEntity == null) {
            serviceAndWorkEntity = new PrmsServiceAndWorkReg();
        }
        return serviceAndWorkEntity;
    }

    public void setServiceAndWorkEntity(PrmsServiceAndWorkReg serviceAndWorkEntity) {
        this.serviceAndWorkEntity = serviceAndWorkEntity;
    }

    public MmsItemRegistration getItemRegstrationEntity() {
        if (itemRegstrationEntity == null) {
            itemRegstrationEntity = new MmsItemRegistration();
        }
        return itemRegstrationEntity;
    }

    public void setItemRegstrationEntity(MmsItemRegistration itemRegstrationEntity) {
        this.itemRegstrationEntity = itemRegstrationEntity;
    }

    public MmsSubCat getSubCategoryEntity() {
        if (subCategoryEntity == null) {
            subCategoryEntity = new MmsSubCat();
        }
        return subCategoryEntity;
    }

    public void setSubCategoryEntity(MmsSubCat subCategoryEntity) {
        this.subCategoryEntity = subCategoryEntity;
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
        if (this.storeInfoEntity == null) {
            this.storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public DataModel<MmsNeedAssessmentDtl> getPapmsNeedAssessDetailDataModel() {
        if (mmsNeedAssessDetailDataModel == null) {
            mmsNeedAssessDetailDataModel = new ListDataModel<>();
        }
        return mmsNeedAssessDetailDataModel;
    }

    public void setPapmsNeedAssessDetailDataModel(DataModel<MmsNeedAssessmentDtl> mmsNeedAssessDetailDataModel) {
        this.mmsNeedAssessDetailDataModel = mmsNeedAssessDetailDataModel;
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

    public List<FmsGeneralLedger> getGeneralLedgersList() {
        if (generalLedgersList == null) {
            generalLedgersList = new ArrayList<>();
        }
        return generalLedgersList;
    }

    public void setGeneralLedgersList(List<FmsGeneralLedger> generalLedgersList) {
        this.generalLedgersList = generalLedgersList;
    }

    public DataModel<MmsNeedAssessmentService> getMmsNeedAssessServiceDataModel() {
        if (mmsNeedAssessServiceDataModel == null) {
            mmsNeedAssessServiceDataModel = new ListDataModel<>();
        }
        return mmsNeedAssessServiceDataModel;
    }

    public void setMmsNeedAssessServiceDataModel(DataModel<MmsNeedAssessmentService> mmsNeedAssessServiceDataModel) {
        this.mmsNeedAssessServiceDataModel = mmsNeedAssessServiceDataModel;
    }

    public DataModel<MmsNeedAssessmentService> getMmsNeedAssessWorkDataModel() {
        if (mmsNeedAssessWorkDataModel == null) {
            mmsNeedAssessWorkDataModel = new ListDataModel<>();
        }
        return mmsNeedAssessWorkDataModel;
    }

    public void setMmsNeedAssessWorkDataModel(DataModel<MmsNeedAssessmentService> mmsNeedAssessWorkDataModel) {
        this.mmsNeedAssessWorkDataModel = mmsNeedAssessWorkDataModel;
    }

    public FmsOperatingBudget1 getFmsOperatingBudget() {
        if (fmsOperatingBudget == null) {
            fmsOperatingBudget = new FmsOperatingBudget1();
        }
        return fmsOperatingBudget;
    }

    public void setFmsOperatingBudget(FmsOperatingBudget1 fmsOperatingBudget) {
        this.fmsOperatingBudget = fmsOperatingBudget;
    }

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        if (fmsOperatingBudgetDetail == null) {
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return fmsOperatingBudgetDetail;
    }

    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        this.fmsOperatingBudgetDetail = fmsOperatingBudgetDetail;
    }

    public FmsGeneralLedger getFmsGeneralLedgerEntity() {
        if (fmsGeneralLedgerEntity == null) {
            fmsGeneralLedgerEntity = new FmsGeneralLedger();
        }
        return fmsGeneralLedgerEntity;
    }

    public void setFmsGeneralLedgerEntity(FmsGeneralLedger fmsGeneralLedgerEntity) {
        this.fmsGeneralLedgerEntity = fmsGeneralLedgerEntity;
    }

    public boolean isRenderForGoods() {
        return renderForGoods;
    }

    public void setRenderForGoods(boolean renderForGoods) {
        this.renderForGoods = renderForGoods;
    }

    public boolean isRenderForService() {
        return renderForService;
    }

    public void setRenderForService(boolean renderForService) {
        this.renderForService = renderForService;
    }

    public boolean isRenderForWork() {
        return renderForWork;
    }

    public void setRenderForWork(boolean renderForWork) {
        this.renderForWork = renderForWork;
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

    public MmsNeedAssessmentDtl getSelectedRowForDetail() {
        return selectedRowForDetail;
    }

    public void setSelectedRowForDetail(MmsNeedAssessmentDtl selectedRowForDetail) {
        this.selectedRowForDetail = selectedRowForDetail;
    }

    public MmsNeedAssessmentService getSelectedServiceRowForDetail() {
        return selectedServiceRowForDetail;
    }

    public void setSelectedServiceRowForDetail(MmsNeedAssessmentService selectedServiceRowForDetail) {
        this.selectedServiceRowForDetail = selectedServiceRowForDetail;
    }

    public boolean isDisableSearchItem() {
        return disableSearchItem;
    }

    public void setDisableSearchItem(boolean disableSearchItem) {
        this.disableSearchItem = disableSearchItem;
    }

    public List<FmsGeneralLedger> getGeneralLedgerList() {
        if (GeneralLedgerList == null) {
            GeneralLedgerList = new ArrayList<>();
        }
        return GeneralLedgerList;
    }

    public void setGeneralLedgerList(List<FmsGeneralLedger> GeneralLedgerList) {
        this.GeneralLedgerList = GeneralLedgerList;
    }

    public FmsLuBudgetYear getFmsLubudgetYear() {
        if (fmsLubudgetYear == null) {
            fmsLubudgetYear = new FmsLuBudgetYear();
        }
        return fmsLubudgetYear;
    }

    public void setFmsLubudgetYear(FmsLuBudgetYear fmsLubudgetYear) {
        this.fmsLubudgetYear = fmsLubudgetYear;
    }

    public List<FmsOperatingBudget1> getApprovedBudgetCodeLists() {
        if (approvedBudgetCodeLists == null) {
            approvedBudgetCodeLists = new ArrayList<>();

        }
        return approvedBudgetCodeLists;
    }

    public void setApprovedBudgetCodeLists(List<FmsOperatingBudget1> approvedBudgetCodeLists) {
        this.approvedBudgetCodeLists = approvedBudgetCodeLists;
    }

    public void getHrDepartmentInfo(SelectEvent event) {
        hrDepartmentsEntity.setDepName(event.getObject().toString());
        hrDepartmentsEntity = hrdepartmentInterface.getdepartmentInformation(hrDepartmentsEntity);
        getNeedAssessmentEntity();
        needAssessmentEntity.setDepartmentId(hrDepartmentsEntity);
    }

    public List<MmsItemRegistration> getItemNameList() {
        if (itemNameList == null) {
            itemNameList = new ArrayList<>();
        }
        return itemNameList;
    }

    public void setItemNameList(List<MmsItemRegistration> itemNameList) {
        this.itemNameList = itemNameList;
    }

    public List<MmsStoreInformation> getStoreList() {

        return StoreList;
    }

    public List<HrDepartments> getDepartmentNames() {

        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();

        return DepartmentNames;
    }

    public List<PrmsServiceAndWorkReg> getServicelist() {
        if (servicelist == null) {
            servicelist = new ArrayList<>();
        }
        return servicelist;
    }

    public void setServicelist(List<PrmsServiceAndWorkReg> servicelist) {
        this.servicelist = servicelist;
    }

    public List<PrmsServiceAndWorkReg> getWorkList() {
        return workList;
    }

    public void setWorkList(List<PrmsServiceAndWorkReg> workList) {
        this.workList = workList;
    }

    public void getServiceInfo(SelectEvent event) {
        serviceAndWorkEntity.setServiceName(String.valueOf(event.getObject()));
        serviceAndWorkEntity = serviceAndWorkInterface.getServiceInfoByName(serviceAndWorkEntity);
    }

    public void getWorkInfo(SelectEvent event) {
        serviceAndWorkEntity.setWorkName(String.valueOf(event.getObject()));
        serviceAndWorkEntity = serviceAndWorkInterface.getWorkInfoByName(serviceAndWorkEntity);
    }

    public List<MmsItemRegistration> ItemInformationList(String itemName) {
        itemRegstrationEntity.setMatName(itemName);
        itemRegstrationEntity.setStoreId(storeInfoEntity);
        // materialNameList = itemRegInterface.searchMaterialInfoByStoreAndMatCode(itemRegEntity);
        materialNameList = itemRegInterface.searchMaterialInfoByStoreAndMatName(itemRegstrationEntity);
        return materialNameList;
    }

    public void getMmsItemInformation(SelectEvent event) {
        itemRegstrationEntity.setMatName(event.getObject().toString());
        itemRegstrationEntity = itemRegInterface.getByMaterialName(itemRegstrationEntity);
        //itemRegEntity = itemRegInterface.getMmsItemInfoByCode(itemRegEntity);
        needAssessmentDtlEntity.setItemId(itemRegstrationEntity);

    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public MmsNeedAssessment getSelectedrow() {
        return selectedrow;
    }

    public void setSelectedrow(MmsNeedAssessment selectedrow) {
        this.selectedrow = selectedrow;
    }

    public DataModel<MmsNeedAssessment> getMmsNeedSearchInfoDataModel() {
        if (mmsNeedSearchInfoDataModel == null) {
            mmsNeedSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsNeedSearchInfoDataModel;
    }

    public void setMmsNeedSearchInfoDataModel(DataModel<MmsNeedAssessment> mmsNeedSearchInfoDataModel) {
        this.mmsNeedSearchInfoDataModel = mmsNeedSearchInfoDataModel;
    }

    public void getBudgetAmount(ValueChangeEvent event) {

        if (null != event.getNewValue()) {

            fmsOperatingBudget = (FmsOperatingBudget1) event.getNewValue();
            if (needAssessmentEntity.getPurchaseType().equals("Goods")) {

                //  needAssessmentDtlEntity.setBudgetCode(fmsOperatingBudget);
            } else {

//               needAssessmentServiceEntity.setBudgetCode(fmsOperatingBudget);
            }

//                
        }
    }

    public List<String> getYearList() {
        int currentYear = Integer.parseInt(StringDateManipulation.todayInEC().split("-")[0]);
        List<String> aa = new ArrayList<>();
        aa.add("--- Select One ---");
        for (int i = currentYear + 1; i >= 2000; i--) {
            aa.add(String.valueOf(i));
        }
        return aa;
    }

    public SelectItem[] getActiveYearList() {
        List<FmsLuBudgetYear> activePeriodList = new ArrayList();

        FmsLuBudgetYear fmsLuBudgetYear = new FmsLuBudgetYear();
        FmsLuBudgetYear fmsLuBudgetYear2 = new FmsLuBudgetYear();
        FmsAccountingPeriod activePer = new FmsAccountingPeriod();
        activePer = accountingPeriodBean.getCurretActivePeriod();
        fmsLuBudgetYear = activePer.getLuBudgetYearId();
        activePeriodList.add(fmsLuBudgetYear);
        int year1 = fmsLuBudgetYear.getLuBudgetYearId() + 1;
        fmsLuBudgetYear2.setLuBudgetYearId(year1);
        fmsLuBudgetYear2 = budgetYearLookUpBean.findBgtYearbyId(fmsLuBudgetYear2);
        activePeriodList.add(fmsLuBudgetYear2);
        return JsfUtil.getSelectItems(activePeriodList, true); 

    }

    public void getMmsMaterualSubCatInformation(SelectEvent event) {
        itemRegstrationEntity = itemRegInterface.getMmsItemInfoByCode(itemRegstrationEntity);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handler and recreate ">
    public void handleSelectYear(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsLubudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLubudgetYear = budgetYearLookUpBean.getYearInfo(fmsLubudgetYear);
            needAssessmentEntity.setBudgetYearId(fmsLubudgetYear);
        }

    }

    public void handleselectOptions(ValueChangeEvent event) {
        clear();
        String opt = event.getNewValue().toString();
        System.out.println("=options=======" + opt);
        if (opt.equals("Goods")) {
            setRenderForGoods(true);
            setRenderForService(false);
            setRenderForWork(false);
            needAssessmentEntity.setPurchaseType("Goods");
        } else if (opt.equals("Service")) {
            setRenderForGoods(false);
            setRenderForService(true);
            setRenderForWork(false);
            needAssessmentEntity.setPurchaseType("Service");

        } else if (opt.equals("Work")) {
            setRenderForGoods(false);
            setRenderForService(false);
            setRenderForWork(true);
            needAssessmentEntity.setPurchaseType("Work");
        } else if (opt.equals("Service/Work")) {
            setRenderForGoods(false);
            setRenderForService(true);

        }

    }

    public void handleSelectDepartment(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
//            
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(depIds);
            needAssessmentEntity.setDepartmentId(hrDepartmentsEntity);
            System.out.println("======Department id=====" + needAssessmentEntity.getDepartmentId().getDepId());

        }
    }

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeInfoEntity.setStoreId(Id);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);
            System.out.println("===============storeInfoEntity=" + storeInfoEntity);
            needAssessmentEntity.setStoreId(storeInfoEntity);
            setDisableSearchItem(false);
//         

        }

    }

    public void handleSelectItemName(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            itemRegstrationEntity = (MmsItemRegistration) event.getNewValue();
            needAssessmentDtlEntity.setItemId(itemRegstrationEntity);
        }
    }

    public void handleSelectService(ValueChangeEvent event) {
        serviceAndWorkEntity = (PrmsServiceAndWorkReg) event.getNewValue();
    }

    public void handleSelectWork(ValueChangeEvent event) {
        serviceAndWorkEntity = (PrmsServiceAndWorkReg) event.getNewValue();
    }

    public void handleSelectAccountTitle(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            fmsGeneralLedgerEntity = (FmsGeneralLedger) event.getNewValue();
            System.out.println("======@controller gl id=====" + fmsGeneralLedgerEntity.getGeneralLedgerId());
            itemRegstrationEntity.setStoreId(storeInfoEntity);
//            itemRegstrationEntity.setFmsGeneralLedger(fmsGeneralLedgerEntity); fix it
            fmsOperatingBudgetDetail = oBIntegrationBeanLocal.fetchUsingDepartmentID(fmsLubudgetYear, hrDepartmentsEntity, fmsGeneralLedgerEntity);

            itemNameList = itemRegInterface.searchByStoreAndGlId(itemRegstrationEntity);
            System.out.println("======item Name list ========" + itemNameList.size());
            System.out.println("========GL========" + fmsGeneralLedgerEntity.getAccountTitle());
            System.out.println("========GL Code========" + fmsGeneralLedgerEntity.getGeneralLedgerCode());

        }
    }

    public void recreateModelDetail() {
        mmsNeedAssessDetailDataModel = null;
        mmsNeedAssessDetailDataModel = new ListDataModel<>(new ArrayList<>(needAssessmentEntity.getMmsNeedAssessmentDtlList()));
    }

    public void recreateServiceModel() {
        mmsNeedAssessServiceDataModel = null;
        mmsNeedAssessServiceDataModel = new ListDataModel<>(new ArrayList<>(needAssessmentEntity.getMmsNeedAssessmentServiceList()));
    }

    public void recreateWorkModel() {
        mmsNeedAssessWorkDataModel = null;
        mmsNeedAssessWorkDataModel = new ListDataModel<>(new ArrayList<>(needAssessmentEntity.getMmsNeedAssessmentServiceList()));
    }

    private void recerateNeedAssesmentSerachModel() {
        mmsNeedSearchInfoDataModel = null;
        mmsNeedSearchInfoDataModel = new ListDataModel(new ArrayList<>(allNeedAssesmentinfoList));
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        for (int i = 0; i < needAssessmentEntity.getWfMmsProcessedList().size(); i++) {
            if (needAssessmentEntity.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (needAssessmentEntity.getWfMmsProcessedList().get(i).getDecision() == 1 || needAssessmentEntity.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    needAssessmentEntity.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (needAssessmentEntity.getWfMmsProcessedList().get(i).getDecision() == 2 || needAssessmentEntity.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    needAssessmentEntity.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                needAssessmentEntity.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }

        WfMmsProcessedDataModel = new ListDataModel(needAssessmentEntity.getWfMmsProcessedList());
    }

    //</editor-fold>  
    //<editor-fold defaultstate="collapsed" desc="Update,clear,Listener,Add,onNodeSelect">
    /*This method is used to GL list Listener
     */
    public void GLListener(ValueChangeEvent event) {
        System.out.println("====================GLlist========");
        if (event.getNewValue() != null) {
            System.out.println("=====Not Null======");
            fmsGeneralLedgerEntity = (FmsGeneralLedger) event.getNewValue();
            System.out.println("=====RegisterationType======" + needAssessmentEntity.getPurchaseType());
            switch (needAssessmentEntity.getPurchaseType()) {
                case "Goods":
                    System.out.println("gl id " + fmsGeneralLedgerEntity.getGeneralLedgerId());
                    itemRegstrationEntity.setGeneralLedgerId(fmsGeneralLedgerEntity);
                    System.out.println("gl id after " + itemRegstrationEntity.getGeneralLedgerId() + "");
                    itemRegstrationEntity.setStoreId(storeInfoEntity);
                    itemRegstrationEntity.setItemGroup(fixedAsset);
                    itemNameList = itemRegInterface.searchMaterialInfoByStoreAndMatNameAndItemGroupAndGLID(itemRegstrationEntity);
                    System.out.println("=====inside goods======" + itemNameList.size());
                    break;
                case "Service":
                    System.out.println("==================service===");
                    System.out.println("gl id service" + fmsGeneralLedgerEntity.getGeneralLedgerId());
                    serviceAndWorkEntity.setGeneralLedgerId(fmsGeneralLedgerEntity);
                    System.out.println("=============GLid====" + serviceAndWorkEntity.getGeneralLedgerId());
                    serviceAndWorkEntity.setRegisterationType("service");
                    System.out.println("=============RegisterationType ====" + serviceAndWorkEntity.getRegisterationType());
                    servicelist = serviceAndWorkInterface.searchServiceByGeneralLedgerIDAndRegistratioType(serviceAndWorkEntity);
                    System.out.println("============selectOneServiceList=====" + servicelist.size());
                    break;
                case "Work":
                    System.out.println("==================Work===");
                    serviceAndWorkEntity.setGeneralLedgerId(fmsGeneralLedgerEntity);
                    serviceAndWorkEntity.setRegisterationType("work");
                    workList = serviceAndWorkInterface.searchServiceByGeneralLedgerIDAndRegistratioType(serviceAndWorkEntity);
                    System.out.println("============selectOneServiceList=====" + workList.size());
                    break;
            }
        }
    }
    /*This method is used to clear Popup
     */

    public void clearPopup() {
        itemRegstrationEntity = new MmsItemRegistration();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        fmsGeneralLedgerEntity = new FmsGeneralLedger();
        fmsOperatingBudget = new FmsOperatingBudget1();
        needAssessmentServiceEntity = new MmsNeedAssessmentService();
        serviceAndWorkEntity = new PrmsServiceAndWorkReg();
        needAssessmentDtlEntity = new MmsNeedAssessmentDtl();
    }
    /*This method is used to Added Assessment Detail Info
     */

    public String addeedAssessmentDtlInfoDetail() {
        if (checkMaterialname.contains(itemRegstrationEntity.getMatName())) {
            JsfUtil.addErrorMessage("material name: Value is Duplicated");
            return null;
        } else {

            needAssessmentDtlEntity.setItemId(itemRegstrationEntity);

            needAssessmentEntity.addToNeedAssessmentDtlInfo(needAssessmentDtlEntity);

            checkMaterialname.add(itemRegstrationEntity.getMatName());
        }
        recreateModelDetail();
        clearPopup();
        return null;
    }
    /*This method is used to Adding Service Asseement Dateils
     */

    public String addServiceAssessmentDtl() {
        System.out.println("==========purchase type========" + needAssessmentEntity.getPurchaseType());
        switch (needAssessmentEntity.getPurchaseType()) {
            case "Service":
                if (checkServiceName.contains(serviceAndWorkEntity.getServiceName())) {
                    JsfUtil.addFatalMessage("Service name: Value is Duplicated");
                    return null;
                } else {
                    System.out.println("=====inside else========");
                    needAssessmentServiceEntity.setServiceId(serviceAndWorkEntity);
                    needAssessmentEntity.addToNeedAssessmentServiceDtl(needAssessmentServiceEntity);
                    checkServiceName.add(serviceAndWorkEntity.getServiceName());
                    recreateServiceModel();
                    clearPopup();
                }
                break;
            case "Work":
                System.out.println("=====inside opt work=========");
                if (checkWorkName.contains(serviceAndWorkEntity.getWorkName())) {
                    JsfUtil.addFatalMessage("Work name: Value is Duplicated");
                    return null;
                } else {
                    System.out.println("==========Inside Work Else===");
                    needAssessmentServiceEntity.setServiceId(serviceAndWorkEntity);
                    needAssessmentEntity.addToNeedAssessmentServiceDtl(needAssessmentServiceEntity);
                    checkWorkName.add(serviceAndWorkEntity.getWorkName());
                    recreateWorkModel();
                    clearPopup();
                }
                break;
        }
        return null;
    }
    /*This method is used to Clear
     */

    public void clear() {

        storeInfoEntity = new MmsStoreInformation();
        needAssessmentEntity = new MmsNeedAssessment();
        needAssessmentDtlEntity = new MmsNeedAssessmentDtl();
        needAssessmentServiceEntity = new MmsNeedAssessmentService();
        hrDepartmentsEntity = new HrDepartments();
        needAssessmentServiceEntity = new MmsNeedAssessmentService();
        fmsOperatingBudget = new FmsOperatingBudget1();
        mmsNeedAssessDetailDataModel = null;
        mmsNeedAssessServiceDataModel = null;
        mmsNeedAssessWorkDataModel = null;
        mmsNeedSearchInfoDataModel = new ListDataModel<>();
        fmsLubudgetYear = new FmsLuBudgetYear();
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        wfMmsProcessed = null;

    }
    /*This method is used to Create New NeedAssessement infoermation 
     */

    public void createNewNeedAssesmentInfo() {
        clear();
        if (createOrSearchBundle.equals("New")) {
            saveorUpdateBundle = "Save";
            disableBtnCreate = false;
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            generalLedgersList = generalLedgerInterface.findActiveGeneralLedgersList();
            needAssessmentEntity.setPurchaseType("Goods");
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to On Node Selection
     */

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        StoreList = storeInfoInterface.searchStoreByParameterDepartmentId(hrDepartmentsEntity);
        System.out.println("===============StoreList==" + StoreList);
        needAssessmentEntity.setDepartmentId(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");

    }

    public List<PrmsServiceAndWorkReg> serviceInfoSearchlist(String serviceName) {
        serviceAndWorkEntity.setServiceName(serviceName);
        servicelist = serviceAndWorkInterface.searchServiceInfoByServiceName(serviceAndWorkEntity);
        return servicelist;
    }

    public List<PrmsServiceAndWorkReg> workInfoSearchlist(String workname) {
        serviceAndWorkEntity.setWorkName(workname);
        workList = serviceAndWorkInterface.searchWorkInfoByWorkName(serviceAndWorkEntity);
        return workList;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save,search,Update,onRowEdit, populateUI">
/*This method is used to Save Workflow
     */
    public void WfSave() {
        wfMmsProcessed.setAssessmetnId(needAssessmentEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save NeedAssessment
     */

    public void saveNeedAssessment() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveNeedAssessment", dataset)) {
                if (updateStatus == 0) {

                    needAssessmentEntity.setDepartmentId(hrDepartmentsEntity);
                    needAssessmentEntity.setCheckedStatus("Not checked");
                    setWorkFlowValues();
                    needAssessmentEntity.addNeedAssesmentIdToWorkflow(wfMmsProcessed);
                    needAssessmentEntity.setStatus(Constants.PREPARE_VALUE);
                    needAssessmentinterface.create(needAssessmentEntity);
                    JsfUtil.addSuccessMessage("Need Assessment is Saved");
                    clear();
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    try {
                        setWorkFlowValues();
                        needAssessmentEntity.setCommentGiven(wfMmsProcessed.getCommentGiven());

                        needAssessmentinterface.edit(needAssessmentEntity);
                        WfSave();
                        JsfUtil.addSuccessMessage("Need Assessment is upadted");
                        saveorUpdateBundle = "Save";
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something Error Occured on Data Modified");
                    }
                    clear();

                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        needAssessmentEntity.setStatus(Constants.APPROVE_VALUE);

                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        needAssessmentEntity.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        needAssessmentEntity.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        needAssessmentEntity.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    wfMmsProcessed.setAssessmetnId(needAssessmentEntity);
                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    needAssessmentinterface.edit(needAssessmentEntity);
                    mmsNsList.remove(needAssessmentEntity);
                    clear();
                    JsfUtil.addSuccessMessage("Need Assessment Data Modified");

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
            }
        } catch (Exception ex) {

        }

    }

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            needAssessmentEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            needAssessmentEntity.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsNeedColumnNameList() {
          mmsNeedColumnNameList = needAssessmentinterface.getMmsNeedColumnNameList();
        if (mmsNeedColumnNameList == null) {
            mmsNeedColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + mmsNeedColumnNameList);
        if (mmsNeedColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsNeedColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsNeedColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsNeedColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsNeedColumnNameList;
    }

    public void setMmsNeedColumnNameList(List<String> mmsNeedColumnNameList) {
        this.mmsNeedColumnNameList = mmsNeedColumnNameList;
    }
    
    
    
    /*This method is used to Update NeedAssessment Detail
     */

    public void updateNeedAssessmentDtlDetail() {
        needAssessmentDtlEntity = getPapmsNeedAssessDetailDataModel().getRowData();
        itemRegstrationEntity = needAssessmentDtlEntity.getItemId();
        checkMaterialname.remove(needAssessmentDtlEntity.getItemId().getMatName());
    }

    /*This method is used to UpdateDetails Sellecting value
     */
    public void updateDetail(SelectEvent event) {
        if (needAssessmentEntity.getPurchaseType().equals("Goods")) {
            needAssessmentDtlEntity = (MmsNeedAssessmentDtl) event.getObject();
            itemRegstrationEntity = needAssessmentDtlEntity.getItemId();

            populateLists();

            itemNameList = new ArrayList<>();
            itemNameList.add(itemRegstrationEntity);
        } else if (needAssessmentEntity.getPurchaseType().equals("Service") || needAssessmentEntity.getPurchaseType().equals("Work")) {
            System.out.println("==========inside update else=====");
            needAssessmentServiceEntity = (MmsNeedAssessmentService) event.getObject();
            serviceAndWorkEntity = needAssessmentServiceEntity.getServiceId();
            fmsGeneralLedgerEntity = serviceAndWorkEntity.getGeneralLedgerId();
            populateLists();
            if (needAssessmentEntity.getPurchaseType().equals("Service")) {
                System.out.println("========Service=====");
                servicelist = new ArrayList<>();
                fmsGeneralLedgerEntity = serviceAndWorkEntity.getGeneralLedgerId();
                servicelist.add(serviceAndWorkEntity);
            } else if (needAssessmentEntity.getPurchaseType().equals("Goods")) {
                System.out.println("======Goods===");
                workList = new ArrayList<>();
                fmsGeneralLedgerEntity.setGeneralLedgerCode(itemRegstrationEntity.getGeneralLedgerId().getGeneralLedgerCode());
                workList.add(serviceAndWorkEntity);
            } else if (needAssessmentEntity.getPurchaseType().equals("work")) {
                System.out.println("=======work======");
                workList = new ArrayList<>();
                fmsGeneralLedgerEntity = serviceAndWorkEntity.getGeneralLedgerId();
                workList.add(serviceAndWorkEntity);

            }
        }

    }
    /*This method is used to set WorkFlow value
     */

    public void setWorkFlowValues() {
        needAssessmentEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
        needAssessmentEntity.setProcessedBy(SessionBean.getUserId());
    }
    /*This method is used to Searching
     */

    public void searchNeedAssesmentByParameter() {
        System.out.println("in search");
        storeInfoEntity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + storeInfoEntity.getProcessedBy());
        allNeedAssesmentinfoList = needAssessmentinterface.getNeedListsByParameter(columnNameResolver, needAssessmentEntity, needAssessmentEntity.getColumnValue());
        recerateNeedAssesmentSerachModel();
       
    }

    public ArrayList<HrDepartments> searchDepartInformation(String depName) {
        ArrayList<HrDepartments> departInformations = null;
        hrDepartmentsEntity.setDepName(depName);
        departInformations = hrdepartmentInterface.getdepartmetInfoByName(hrDepartmentsEntity);
        return departInformations;
    }
    /*This method is used to btn Search Actions
     */

    public void btn_Search_Action() {
        clear();
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
    }
    /*This method is used to On Row Editing
     */

    public void onRowEdit(SelectEvent event) {
        setFromSearchOrFromRequest(fromSearch);
        needAssessmentEntity = (MmsNeedAssessment) event.getObject();
        int size = needAssessmentEntity.getWfMmsProcessedList().size();

        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {

            System.out.println("=========checker approve=====" + needAssessmentEntity.getWfMmsProcessedList().get(size - 1).getDecision());

            if (needAssessmentEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_APPROVE_VALUE || needAssessmentEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (needAssessmentEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_REJECT_VALUE || needAssessmentEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            wfMmsProcessed.setProcessedOn(needAssessmentEntity.getWfMmsProcessedList().get(size - 1).getProcessedOn());

        } else {
        }

        populateUI();

    }
    /*This method is used to Populateing the UI
     */

    public void populateUI() {

        hrDepartmentsEntity = needAssessmentEntity.getDepartmentId();
        fmsLubudgetYear = needAssessmentEntity.getBudgetYearId();
        System.out.println("========inside populate=====");

        renderPnlCreateGatePass = true;

        renderPnlManPage = false;
        System.out.println("=====purchase type======" + needAssessmentEntity.getPurchaseType());
        switch (needAssessmentEntity.getPurchaseType()) {

            case "Goods":
                System.out.println("========inside populate for goods=====");
                storeInfoEntity.setStoreId(needAssessmentEntity.getStoreId().getStoreId());
                setRenderForGoods(true);
                setRenderForService(false);
                setRenderForWork(false);
                //            fmsOperatingBudget = needAssessmentDtlEntity.getBudgetCode();
                recreateModelDetail();
                break;
            case "Service":
                System.out.println("========inside populate for Service=====");
                setRenderForGoods(false);
                setRenderForService(true);
                setRenderForWork(false);
                //            fmsOperatingBudget = needAssessmentServiceEntity.getBudgetCode();
                recreateServiceModel();
                break;
            case "Work":
                setRenderForGoods(false);
                setRenderForService(false);
                setRenderForWork(true);
                //            fmsOperatingBudget = needAssessmentServiceEntity.getBudgetCode();
                recreateWorkModel();
                break;
        }
        if (workflow.isPrepareStatus()) {
            System.out.println("yes i'm preparer");
            wfMmsProcessed.setProcessedOn(needAssessmentEntity.getProcessedOn());
            System.out.println("date====" + needAssessmentEntity.getProcessedOn());
        }
        recreateWfDataModel();
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        setDisableSearchItem(false);

    }
    /*This method is used to Populating List
     */

    public void populateLists() {
        generalLedgersList = new ArrayList<>();
        generalLedgersList.add(fmsGeneralLedgerEntity);
    }
    /*This method is used to on Selact Request
     */

    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {

                needAssessmentEntity = (MmsNeedAssessment) event.getNewValue();
                setFromSearchOrFromRequest(fromRequest);
                setIsRenderedIconWorkflow(true);
                populateUI();

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }
    //</editor-fold>

}
