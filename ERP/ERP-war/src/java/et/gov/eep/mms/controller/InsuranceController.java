package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.businessLogic.FixedAssetRegistrationBeanLocal;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import et.gov.eep.mms.businessLogic.InsuranceBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsFaBuilding;
import et.gov.eep.mms.entity.MmsFaGeothermal;
import et.gov.eep.mms.entity.MmsFaHydropower;
import et.gov.eep.mms.entity.MmsFaSubstation;
import et.gov.eep.mms.entity.MmsFaTransmission;
import et.gov.eep.mms.entity.MmsFaTransport;
import et.gov.eep.mms.entity.MmsFaWind;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsInsurance;
import et.gov.eep.mms.entity.MmsInsuranceDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsFaBuildingFacade;
import et.gov.eep.mms.mapper.MmsFaGeothermalFacade;
import et.gov.eep.mms.mapper.MmsFaHydropowerFacade;
import et.gov.eep.mms.mapper.MmsFaSubstationFacade;
import et.gov.eep.mms.mapper.MmsFaTransmissionFacade;
import et.gov.eep.mms.mapper.MmsFaTransportFacade;
import et.gov.eep.mms.mapper.MmsFaWindFacade;
import et.gov.eep.mms.mapper.MmsFixedassetRegstDetailFacade;
import et.gov.eep.mms.mapper.MmsInsuranceDetailFacade;
import et.gov.eep.mms.mapper.MmsItemRegistrationFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
@Named(value = "insuranceControllers")

@ViewScoped
public class InsuranceController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Default Constructor">
    public InsuranceController() {
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrdepartmentInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private InsuranceBeanLocal insuranceInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsSivBeanLocal sivInterface;
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrDeptInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fxAssetRegDtlInterface;
    @EJB
    private MmsFixedassetRegstDetailFacade fixedassetRegstDetailFacade;
    @EJB
    private MmsItemRegistrationFacade itemRegistrationFacade;
    @EJB
    private MmsFaBuildingFacade bldgFacade;
    @EJB
    private MmsFaGeothermalFacade geoFacade;
    @EJB
    private MmsFaHydropowerFacade hydroFacede;
    @EJB
    private MmsFaTransmissionFacade trasmissionFacade;
    @EJB
    private MmsFaTransportFacade transportFacade;
    @EJB
    private MmsFaWindFacade windFacade;
    @EJB
    private MmsFaSubstationFacade subsFacade;
    @EJB
    FixedAssetRegistrationBeanLocal ifrsregistrationBeanLocal;
//</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    MmsInsuranceDetailFacade mmsInsuranceDetailFacade;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    MmsInsurance insuranceEntity;
    @Inject
    MmsInsuranceDetail insuranceDetailEntity;
    @Inject
    private MmsInsuranceDetail insuranceDetailCopy;
    @Inject
    private MmsFaTransmission FaTransmissionEntity; //FaTransmissionEntity
    @Inject
    private MmsFaTransport faTransport;
    @Inject
    private MmsFaHydropower faHydropower;
    @Inject
    private MmsFaSubstation faSubstation;
    @Inject
    private MmsFaBuilding building;
    @Inject
    private MmsFaWind faWind;
    @Inject
    private MmsFaGeothermal faGeothermal;
    @Inject
    private MmsSiv sivEntity;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntity;
    @Inject
    private MmsItemRegistration itemRegistration;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    IfrsFixedAsset fixedassetIfrs;
    @Inject
    ColumnNameResolver columnNameResolver;
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare list and Models">
    private List<HrDepartments> DepartmentNames = new ArrayList<>();
    private DataModel<MmsInsuranceDetail> insuranceAddDetailDataModel;
    private DataModel<MmsInsuranceDetail> insuranceAddDetailDataModelMinor;
    private DataModel<MmsInsurance> mmsInsuranceSearchInfoDataModel;
    private List<MmsInsurance> mmsInsList;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private static List<HrDepartments> araListe;
    List<MmsInsuranceDetail> listCopy = new ArrayList<>();
    List<MmsFaTransmission> TransmissionList = new ArrayList<>();
    private List<MmsInsuranceDetail> insuranceInfoList;
    List<MmsInsurance> allInsuranceInfoList;
    List<MmsInsuranceDetail> CheckListDtl = new ArrayList<>();
    List<MmsInsuranceDetail> CheckListDtl2 = new ArrayList<>();
    List<MmsInsuranceDetail> insDtl;
    List<MmsFixedassetRegstDetail> fixedAssetDtlList = new ArrayList<>();
    List<IfrsFixedAsset> ifrsFixedAssetsList = new ArrayList<>();
    List<MmsInsuranceDetail> InsuraceDtl1 = new ArrayList<>();
    List<MmsFaBuilding> BuildingList = new ArrayList<>();
    List<MmsFaGeothermal> GeoList = new ArrayList<>();
    List<MmsFaHydropower> HydroList = new ArrayList<>();
    List<MmsFaWind> WindList = new ArrayList<>();
    List<MmsFaTransport> TransportList = new ArrayList<>();
    List<MmsFaSubstation> SubsList = new ArrayList<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare Fields">
    private MmsInsurance hpSelect;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderpnlToSeachPage;
    private String addOrModifyBundle = "Add";
    private boolean renderPnlCreateInsurance = false;
    private boolean renderPnlManPage = true;
    private boolean checkDetail = false;
    private boolean newAddDetail = false;
    private boolean singleAddDetail = false;
    private boolean singleAddDFromRowSelect = false;
    private boolean insideAdd = false;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private boolean renderMinor = true;
    private boolean selectOprDtlRadio = false;
    private boolean RenderMajor = false;
    private boolean renderTrans = false;
    private boolean renderSubs = false;
    private boolean renderGeo = false;
    private boolean renderHydro = false;
    private boolean rendertransp = false;
    private boolean renderBldg = false;
    private boolean renderWind = false;
    boolean isRenderCreate;
    String itemStatusWhenNew = "Minor Asset";
    private String CheckItemType = "1";
    String selectedValue = "";
    private boolean renderDecision = false;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    Set<String> checkMaterialCode = new HashSet<>();
    Set<String> checkMaterialCode2 = new HashSet<>();
    private MmsInsuranceDetail selectedInsDetail;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private String userName;
    int index;
    int InsuraceDtl1Size;
    Integer farDtlId;
    boolean tag = false;
    String newInsuranceId;
    String lastInsauranceId = "0";
    private Object event;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {

        if (workflow.isApproveStatus()) {
            System.out.println("app or check");
            mmsInsList = insuranceInterface.findInsListByWfStatus(Constants.PREPARE_VALUE);
            renderDecision = true;
            isRenderCreate = false;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            mmsInsList = insuranceInterface.findInsListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            renderDecision = false;
            isRenderCreate = true;
            if (CheckItemType.equals("1")) {
                System.out.println("yeah minore");
//                insuranceDetailEntity.setSelectOpt(itemStatusWhenNew);
            }
            if (mmsInsList.size() > 0) {
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
        allDepartmentsList = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        ColumnNameResolverList = insuranceInterface.getColumnNameList();
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of variables">
    public void setCheckItemType(String CheckItemType) {
        this.CheckItemType = CheckItemType;
    }
    
    public String getCheckItemType() {
        return CheckItemType;
    }
    public boolean isRenderpnlToSeachPage() {
        return renderpnlToSeachPage;
    }

    public void setRenderpnlToSeachPage(boolean renderpnlToSeachPage) {
        this.renderpnlToSeachPage = renderpnlToSeachPage;
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
    public boolean isRenderMinor() {
        return renderMinor;
    }

    public void setRenderMinor(boolean renderMinor) {
        this.renderMinor = renderMinor;
    }

    public boolean isSelectOprDtlRadio() {
        return selectOprDtlRadio;
    }

    public void setSelectOprDtlRadio(boolean selectOprDtlRadio) {
        this.selectOprDtlRadio = selectOprDtlRadio;
    }

    public boolean isRenderMajor() {
        return RenderMajor;
    }

    public void setRenderMajor(boolean RenderMajor) {
        this.RenderMajor = RenderMajor;
    }

    public boolean isRenderTrans() {
        return renderTrans;
    }

    public void setRenderTrans(boolean renderTrans) {
        this.renderTrans = renderTrans;
    }

    public boolean isRenderSubs() {
        return renderSubs;
    }

    public void setRenderSubs(boolean renderSubs) {
        this.renderSubs = renderSubs;
    }

    public boolean isRenderGeo() {
        return renderGeo;
    }

    public void setRenderGeo(boolean renderGeo) {
        this.renderGeo = renderGeo;
    }

    public boolean isRenderHydro() {
        return renderHydro;
    }

    public void setRenderHydro(boolean renderHydro) {
        this.renderHydro = renderHydro;
    }

    public boolean isRendertransp() {
        return rendertransp;
    }

    public void setRendertransp(boolean rendertrans) {
        this.rendertransp = rendertrans;
    }

    public boolean isSingleAddDFromRowSelect() {
        return singleAddDFromRowSelect;
    }

    public boolean isRenderBldg() {
        return renderBldg;
    }

    public void setRenderBldg(boolean renderBldg) {
        this.renderBldg = renderBldg;
    }

    public boolean isRenderWind() {
        return renderWind;
    }

    public void setRenderWind(boolean renderWind) {
        this.renderWind = renderWind;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public void setSingleAddDFromRowSelect(boolean singleAddDFromRowSelect) {
        this.singleAddDFromRowSelect = singleAddDFromRowSelect;
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

    public boolean isCheckDetail() {
        return checkDetail;
    }

    public void setCheckDetail(boolean checkDetail) {
        this.checkDetail = checkDetail;
    }

    public boolean isNewAddDetail() {
        return newAddDetail;
    }

    public void setNewAddDetail(boolean newAddDetail) {
        this.newAddDetail = newAddDetail;
    }

    public boolean isInsideAdd() {
        return insideAdd;
    }

    public void setInsideAdd(boolean insideAdd) {
        this.insideAdd = insideAdd;
    }

    /**
     *
     * @return
     */
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
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
    public boolean isRenderPnlCreateInsurance() {
        return renderPnlCreateInsurance;
    }

    /**
     *
     * @param renderPnlCreateInsurance
     */
    public void setRenderPnlCreateInsurance(boolean renderPnlCreateInsurance) {
        this.renderPnlCreateInsurance = renderPnlCreateInsurance;
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

    public boolean isSingleAddDetail() {
        return singleAddDetail;
    }

    public void setSingleAddDetail(boolean singleAddDetail) {
        this.singleAddDetail = singleAddDetail;
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

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of List and Models">
    public List<MmsFixedassetRegstDetail> getFixedAssetDtlList() {
        if (fixedAssetDtlList == null) {
            fixedAssetDtlList = new ArrayList<>();
        }
        return fixedAssetDtlList;
    }
    public void setFixedAssetDtlList(List<MmsFixedassetRegstDetail> fixedAssetDtlList) {
        this.fixedAssetDtlList = fixedAssetDtlList;
    }
    public List<MmsInsurance> getMmsInsList() {
        return mmsInsList;
    }

    public void setMmsInsList(List<MmsInsurance> mmsInsList) {
        this.mmsInsList = mmsInsList;
    }
    /**
     *
     * @return
     */
    public DataModel<MmsInsuranceDetail> getInsuranceAddDetailDataModel() {
        if (insuranceAddDetailDataModel == null) {
            insuranceAddDetailDataModel = new ListDataModel<>();
        }
        return insuranceAddDetailDataModel;
    }

    public void setInsuranceAddDetailDataModel(DataModel<MmsInsuranceDetail> insuranceAddDetailDataModel) {
        this.insuranceAddDetailDataModel = insuranceAddDetailDataModel;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public DataModel<MmsInsuranceDetail> getInsuranceAddDetailDataModelMinor() {
        if (insuranceAddDetailDataModelMinor == null) {
            insuranceAddDetailDataModelMinor = new ListDataModel<>();
        }
        return insuranceAddDetailDataModelMinor;

    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public void setInsuranceAddDetailDataModelMinor(DataModel<MmsInsuranceDetail> insuranceAddDetailDataModelMinor) {
        this.insuranceAddDetailDataModelMinor = insuranceAddDetailDataModelMinor;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsInsurance> getMmsInsuranceSearchInfoDataModel() {
        if (mmsInsuranceSearchInfoDataModel == null) {
            mmsInsuranceSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsInsuranceSearchInfoDataModel;
    }

    /**
     *
     * @param mmsInsuranceSearchInfoDataModel
     */
    public void setMmsInsuranceSearchInfoDataModel(DataModel<MmsInsurance> mmsInsuranceSearchInfoDataModel) {
        this.mmsInsuranceSearchInfoDataModel = mmsInsuranceSearchInfoDataModel;
    }
     /**
     *
     * @return
     */
    public List<HrDepartments> getDepartmentNames() {

        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();

        return DepartmentNames;
    }

    public SelectItem[] getTransmissionList() {
        return JsfUtil.getSelectItems(trasmissionFacade.findAll(), true);
   }
    public SelectItem[] getBuildingList() {
        return JsfUtil.getSelectItems(bldgFacade.findAll(), true);
    }
    public SelectItem[] getGeoList() {
        return JsfUtil.getSelectItems(geoFacade.findAll(), true);
    }
    public SelectItem[] getHydroList() {
        return JsfUtil.getSelectItems(hydroFacede.findAll(), true);
    }
    public SelectItem[] getWindList() {
        return JsfUtil.getSelectItems(windFacade.findAll(), true);
    }
    public SelectItem[] getTransportList() {
        return JsfUtil.getSelectItems(transportFacade.findAll(), true);
    }
    public SelectItem[] getSubsList() {
        return JsfUtil.getSelectItems(subsFacade.findAll(), true);
    }

    public String navigateToMmsTransmission() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("transmissionInfo", FaTransmissionEntity);
        return "FixedAssetTransmission";
    }

    public String navigateToMmsSubstaion() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("substaionInfo", faSubstation);
        return "FixedAssetSubstation";
    }

    public String navigateToMmsGeothermal() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("geothermalInfo", faGeothermal);
        return "Geothermal";
    }

    public String navigateToMmsHpower() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("hydroInfo", faHydropower);
        return "FixedAssetHydropower";
    }

    public String navigateToMmsTransport() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("trnasportInfo", faTransport);
        return "transport";
    }

    public String navigateToMmsWind() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("windInfo", faWind);
        return "wind";
    }

    public String navigateToMmsBldg() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("bldgInfo", building);
        return "building";
    }

//</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of Objects">
    
    public MmsFixedassetRegstDetail getFixedAssetRegDtlEntity() {
        if (fixedAssetRegDtlEntity == null) {
            fixedAssetRegDtlEntity = new MmsFixedassetRegstDetail();
        }
        return fixedAssetRegDtlEntity;
    }
    
    public void setFixedAssetRegDtlEntity(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        this.fixedAssetRegDtlEntity = fixedAssetRegDtlEntity;
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
    public MmsInsuranceDetail getSelectedInsDetail() {

        return selectedInsDetail;
    }

    public void setSelectedInsDetail(MmsInsuranceDetail selectedInsDetail) {
        this.selectedInsDetail = selectedInsDetail;
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

    public IfrsFixedAsset getFixedassetIfrs() {
        if (fixedassetIfrs == null) {
            fixedassetIfrs = new IfrsFixedAsset();
        }
        return fixedassetIfrs;
    }

    public void setFixedassetIfrs(IfrsFixedAsset fixedassetIfrs) {
        this.fixedassetIfrs = fixedassetIfrs;
    }
      public MmsInsurance getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsInsurance hpSelect) {
        this.hpSelect = hpSelect;
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

    /**
     *
     * @param storeInfoEntity
     */
    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public MmsInsuranceDetail getInsuranceDetailCopy() {
        if (insuranceDetailCopy == null) {
            insuranceDetailCopy = new MmsInsuranceDetail();
        }
        return insuranceDetailCopy;
    }

    public void setInsuranceDetailCopy(MmsInsuranceDetail insuranceDetailCopy) {
        this.insuranceDetailCopy = insuranceDetailCopy;
    }

    /**
     *
     * @return
     */
    public MmsSiv getsivEntity() {
        if (sivEntity == null) {
            sivEntity = new MmsSiv();
        }
        return sivEntity;
    }

    /**
     *
     * @param sivEntity
     */
    public void setsivEntity(MmsSiv sivEntity) {
        this.sivEntity = sivEntity;
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
    public MmsInsurance getInsuranceEntity() {
        if (insuranceEntity == null) {
            insuranceEntity = new MmsInsurance();
        }
        return insuranceEntity;
    }

    /**
     *
     * @param insuranceEntity
     */
    public void setInsuranceEntity(MmsInsurance insuranceEntity) {
        this.insuranceEntity = insuranceEntity;
    }

    public MmsFaTransmission getFaTransmissionEntity() {
        if (FaTransmissionEntity == null) {
            FaTransmissionEntity = new MmsFaTransmission();
        }
        return FaTransmissionEntity;
    }

    public void setFaTransmissionEntity(MmsFaTransmission FaTransmissionEntity) {
        this.FaTransmissionEntity = FaTransmissionEntity;
    }

    public MmsFaTransport getFaTransport() {
        if (faTransport == null) {
            faTransport = new MmsFaTransport();
        }
        return faTransport;
    }

    public void setFaTransport(MmsFaTransport faTransport) {
        this.faTransport = faTransport;
    }

    public MmsFaHydropower getFaHydropower() {
        if (faHydropower == null) {
            faHydropower = new MmsFaHydropower();
        }
        return faHydropower;
    }

    public void setFaHydropower(MmsFaHydropower faHydropower) {
        this.faHydropower = faHydropower;
    }

    public MmsFaSubstation getFaSubstation() {
        if (faSubstation == null) {
            faSubstation = new MmsFaSubstation();
        }
        return faSubstation;
    }

    public void setFaSubstation(MmsFaSubstation faSubstation) {
        this.faSubstation = faSubstation;
    }

    public MmsFaBuilding getBuilding() {
        if (building == null) {
            building = new MmsFaBuilding();
        }
        return building;
    }

    public void setBuilding(MmsFaBuilding building) {
        this.building = building;
    }

    public MmsFaWind getFaWind() {
        if (faWind == null) {
            faWind = new MmsFaWind();
        }
        return faWind;
    }

    public void setFaWind(MmsFaWind faWind) {
        this.faWind = faWind;
    }

    public MmsFaGeothermal getFaGeothermal() {
        if (faGeothermal == null) {
            faGeothermal = new MmsFaGeothermal();
        }
        return faGeothermal;
    }

    public void setFaGeothermal(MmsFaGeothermal faGeothermal) {
        this.faGeothermal = faGeothermal;
    }
    /**
     *
     * @return
     */
    public MmsInsuranceDetail getInsuranceDetailEntity() {
        if (insuranceDetailEntity == null) {
            insuranceDetailEntity = new MmsInsuranceDetail();
        }
        return insuranceDetailEntity;
    }

    /**
     *
     * @param insuranceDetailEntity
     */
    public void setInsuranceDetailEntity(MmsInsuranceDetail insuranceDetailEntity) {
        this.insuranceDetailEntity = insuranceDetailEntity;
    }


//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handler and event">
    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(depIds);
            hrDepartmentsEntity = hrDeptInterface.findByDepartmentId(hrDepartmentsEntity);
            insuranceEntity.setDepartment(hrDepartmentsEntity);
        }
    }

    public void handleTagNo(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            tag = true;
            fixedAssetRegDtlEntity.setTagNo(event.getNewValue().toString());
            insuranceDetailEntity.setTagNo(event.getNewValue().toString());
            System.out.println("Tag No " + fixedAssetRegDtlEntity.getTagNo());
            fixedAssetRegDtlEntity = fixedassetRegstDetailFacade.findByTag(fixedAssetRegDtlEntity);
            insuranceDetailEntity.setAccountCode(fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            insuranceDetailEntity.setAssetName(fixedAssetRegDtlEntity.getItemName());
            System.out.println("========item Name" + fixedAssetRegDtlEntity.getItemName());
            System.out.println("=============item name 2" + insuranceDetailEntity.getAssetName());
            itemRegistration.setMaterialId(fixedAssetRegDtlEntity.getItemId().getMaterialId());
            itemRegistration = itemRegistrationFacade.getByMaterialId(itemRegistration);
            insuranceDetailEntity.setCatagory(itemRegistration.getMatCategory().getCatName());
            System.out.println("cat Name " + insuranceDetailEntity.getCatagory());
            farDtlId = fixedAssetRegDtlEntity.getFarDetId();
            System.out.println("===========Tag no===========" + event.getNewValue().toString());

        }
    }

    public void handleFaCode(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            fixedassetIfrs.setFaCode(event.getNewValue().toString());
            insuranceDetailEntity.setFaCode(event.getNewValue().toString());
            fixedassetIfrs = ifrsregistrationBeanLocal.findByFaCode(fixedassetIfrs);
            insuranceDetailEntity.setAssetName(fixedassetIfrs.getFaName());
            System.out.println("=====asset name" + fixedassetIfrs.getFaName());
            System.out.println("=======ass2" + insuranceDetailEntity.getAssetName());
        }

    }
 public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            insuranceEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }
    public void handleSelectTransmission(ValueChangeEvent event) {
        insuranceDetailEntity.setTransaction_no(event.getNewValue().toString());
    }

    public void handleSelectGeothermal(ValueChangeEvent event) {
        insuranceDetailEntity.setTransaction_no(event.getNewValue().toString());
    }

    public void handleSelectBuilding(ValueChangeEvent event) {

        insuranceDetailEntity.setTransaction_no(event.getNewValue().toString());
    }

    public void handleSelectTransport(ValueChangeEvent event) {

        insuranceDetailEntity.setTransaction_no(event.getNewValue().toString());
    }

    public void handleSelectWind(ValueChangeEvent event) {

        insuranceDetailEntity.setTransaction_no(event.getNewValue().toString());
    }

    public void handleSelectHydropower(ValueChangeEvent event) {

        insuranceDetailEntity.setTransaction_no(event.getNewValue().toString());
    }

    public void handleSelectSubstation(ValueChangeEvent event) {

        insuranceDetailEntity.setTransaction_no(event.getNewValue().toString());
    }

    public void populateInsDetail(SelectEvent event) {

        index = insuranceAddDetailDataModel.getRowIndex();

        if (tag) {
            insuranceDetailEntity = (MmsInsuranceDetail) event.getObject();
            fixedAssetRegDtlEntity = new MmsFixedassetRegstDetail();

            fixedAssetRegDtlEntity.setTagNo(insuranceDetailEntity.getTagNo());
            fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByTag(fixedAssetRegDtlEntity);

            fixedAssetDtlList.add(fixedAssetRegDtlEntity);
            for (int i = 0; i < fixedAssetDtlList.size(); i++) {
                System.out.println("line " + i + "" + fixedAssetDtlList.get(i).getTagNo());
            }

            if (InsuraceDtl1Size == 1) {

                insuranceDetailCopy = insuranceDetailEntity;
                fixedAssetRegDtlEntity.setTagNo(insuranceDetailCopy.getTagNo());
                fixedAssetRegDtlEntity = fixedassetRegstDetailFacade.findByTag(fixedAssetRegDtlEntity);
                itemRegistration.setMaterialId(fixedAssetRegDtlEntity.getItemId().getMaterialId());
                itemRegistration = itemRegistrationFacade.getByMaterialId(itemRegistration);

                setSingleAddDetail(true);
                setCheckDetail(true);

            } else {
                for (int j = 0; j < InsuraceDtl1.size(); j++) {
                    if (insuranceDetailEntity.getTagNo() == InsuraceDtl1.get(j).getTagNo()) {

                        insuranceDetailCopy = insuranceDetailEntity;
                        InsuraceDtl1.remove(insuranceDetailEntity);

                        fixedAssetRegDtlEntity.setTagNo(insuranceDetailCopy.getTagNo());
                        fixedAssetRegDtlEntity = fixedassetRegstDetailFacade.findByTag(fixedAssetRegDtlEntity);
                        itemRegistration.setMaterialId(fixedAssetRegDtlEntity.getItemId().getMaterialId());

                        itemRegistration = itemRegistrationFacade.getByMaterialId(itemRegistration);

                        setCheckDetail(true);
                    }
                }
            }
        } else {
            insuranceDetailEntity = (MmsInsuranceDetail) event.getObject();

        }

    }

    public void getRegistrationDate(SelectEvent event) {
        System.out.println("Listiner=========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            insuranceEntity.setRegistrationDate(arrival);

            System.out.println("=======RegistrationDate=========" + insuranceEntity.getRegistrationDate());
        }
    }

    public void getAuthorizedDate(SelectEvent event) {
        System.out.println("Listiner=========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            insuranceEntity.setAuthorizedDate(arrival);
            System.out.println("=======AuthorizedDate=========" + insuranceEntity.getAuthorizedDate());
        }
    }

    public void getIssuedDate(SelectEvent event) {
        System.out.println("Listiner=========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            System.out.println("....date....." + arrival);
            insuranceEntity.setIssuedDate(arrival);
            System.out.println("=======IssuedDate=========" + insuranceEntity.getIssuedDate());
        }
    }

    public void changeInsuranceType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {
                System.out.println("mainor assat");
                setRenderMinor(true);
                setRenderMajor(false);
            } else {
                System.out.println("major asst");
                setRenderMinor(false);
                setRenderMajor(true);
            }
            clearPage2();
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
        insuranceEntity.setDepartment(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add to detail And Recreate Datamodel">
    private void recerateInsuranceSerachModel() {

        mmsInsuranceSearchInfoDataModel = null;
        mmsInsuranceSearchInfoDataModel = new ListDataModel(new ArrayList<>(allInsuranceInfoList));
    }

    public String addtoInsuranceInfoDetail() {

        if (checkDetail == true) {
            System.out.println(" ------------- From The Populate Add Detail ---------- ");
            if (checkMaterialCode.contains(insuranceDetailCopy.getTagNo())) {
                JsfUtil.addSuccessMessage("Tag No: Value is Duplicated");
                return null;
            } else {
                System.out.println(".....inside AddDetail......" + insuranceDetailCopy.getTagNo());

                insuranceDetailCopy.setCatagory(itemRegistration.getMatCategory().getCatName());
                insuranceEntity.addInsuranceDetail(insuranceDetailCopy);
                checkMaterialCode.add(insuranceDetailCopy.getTagNo());
                System.out.println("......dtl size...." + insuranceEntity.getMmsInsuranceDetailList().size());

                for (int i = 0; i < insuranceEntity.getMmsInsuranceDetailList().size(); i++) {
                    System.out.println(" ----detail------ " + i + " " + insuranceEntity.getMmsInsuranceDetailList().get(i));
                    CheckListDtl.add(insuranceEntity.getMmsInsuranceDetailList().get(i));

                }
                System.out.println("------- CheckListDtl ------- " + CheckListDtl.size());
                for (int j = 0; j < CheckListDtl.size(); j++) {
                    System.out.println(" ---- CheckListDtl ------  " + j + " " + CheckListDtl.get(j));
                }

                if (singleAddDetail == false) {
                    int sizeCheck = CheckListDtl.size();
                    int sizeIns = InsuraceDtl1.size();

                    for (int l = 0; l < sizeIns; l++) {
                        for (int m = 0; m < sizeCheck; m++) {
                            if (Objects.equals(CheckListDtl.get(m).getInsDetailId(), InsuraceDtl1.get(l).getInsDetailId())) {
                                System.out.println("-------inside ForLoop-------");
                                CheckListDtl.remove(CheckListDtl.get(m));
                                break;
                            } else {
                            }
                        }
                    }

                    System.out.println(" ---- CheckListDtl ------  " + CheckListDtl.size());
                    System.out.println(" ---- CheckListDtl ------  " + CheckListDtl.get(0).getInsDetailId());

                }

                if (singleAddDetail == true) {
                    int sizeCheck = CheckListDtl.size();
                    int sizeIns = InsuraceDtl1.size();

                    for (int l = 0; l < sizeIns; l++) {
                        for (int m = 0; m < sizeCheck; m++) {
                            if (Objects.equals(CheckListDtl.get(m).getInsDetailId(), InsuraceDtl1.get(l).getInsDetailId())) {
                                System.out.println("-------inside ForLoop-------");
                                InsuraceDtl1.remove(InsuraceDtl1.get(l));
                                break;
                            } else {
                            }
                        }
                    }

                    System.out.println(" ---- CheckListDtl ------  " + CheckListDtl.size());
                    System.out.println(" ---- CheckListDtl ------  " + CheckListDtl.get(0).getInsDetailId());

                }

            }

            recreateModelDetail();
            clearPopUp();

        } else {

            setNewAddDetail(true);

            System.out.println(" -------------Inside New Add Detail ---------- ");
            if (checkMaterialCode2.contains(insuranceDetailEntity.getTagNo())) {
                JsfUtil.addSuccessMessage("Tag No: Value is Duplicated");
                return null;
            } else {
                System.out.println(".....inside AddDetail2......" + insuranceDetailEntity.getTagNo());

                if (tag) {
                    insuranceDetailEntity.setCatagory(itemRegistration.getMatCategory().getCatName()); // catagory null for others NullPointerException
                }
                insuranceEntity.addInsuranceDetail(insuranceDetailEntity);
                checkMaterialCode2.add(insuranceDetailEntity.getTagNo());

                System.out.println(" ----------- getMmsInsuranceDetailList() 2 ----------------  " + insuranceEntity.getMmsInsuranceDetailList().size());

                for (int h = 0; h < insuranceEntity.getMmsInsuranceDetailList().size(); h++) {
                    System.out.println(" ---- detail 2 ------ " + h + " " + insuranceEntity.getMmsInsuranceDetailList().get(h));
                    CheckListDtl2.add(insuranceEntity.getMmsInsuranceDetailList().get(h));

                }

                System.out.println("---------------singleAddDFromRowSelect-------------- " + singleAddDFromRowSelect);
                if (singleAddDFromRowSelect == true) {
                    System.out.println(" -------------inside singleAddDFromRowSelect -------- ");
                    setInsideAdd(true);
                    setNewAddDetail(false);
                    int sizeCheck2 = CheckListDtl2.size();
                    int sizeIns2 = InsuraceDtl1.size();

                    for (int l = 0; l < sizeIns2; l++) {
                        for (int m = 0; m < sizeCheck2; m++) {
                            if (Objects.equals(CheckListDtl2.get(m).getInsDetailId(), InsuraceDtl1.get(l).getInsDetailId())) {
                                System.out.println("-------inside ForLoop-------");
                                CheckListDtl2.remove(CheckListDtl2.get(m));

                            }
                        }
                    }
                    System.out.println(" ---- CheckListDtl2 ------   " + CheckListDtl.size());
                    System.out.println(" ---- CheckListDtl2 ------   " + CheckListDtl.get(0).getInsDetailId());
                    System.out.println("......dtl size2...." + insuranceEntity.getMmsInsuranceDetailList().size());
                }
                recreateModelDetail();
                clearPopUp();
                insuranceDetailEntity = new MmsInsuranceDetail();
            }

        }
        return null;
    }

    public String addtoinsuInfoDetail() {
        insuranceEntity.addInsuranceDetail(insuranceDetailCopy);
        recreateModelDetail2();
        clearPopUp();
        return null;
    }

    public String addtoInsuranceInfoDetail2() {
        System.out.println("======add====");
        if (tag) {

            System.out.println("========ins no===" + insuranceEntity.getInsNo());
        }

        insuranceEntity.addInsuranceDetail(insuranceDetailEntity);
        recreateModelDetail2();
        clearPopUp2();
        return null;
    }

    public String addInsuranceDetailMinor() {
        if (insuranceDetailEntity.getSelectOpt() == 1) {
            insuranceDetailEntity.setCatagory(itemRegistration.getMatCategory().getCatName());
            System.out.println("====inside add" + itemRegistration.getMatCategory().getCatName());

            System.out.println("=========2" + insuranceDetailEntity.getCatagory());
            System.out.println("==================" + insuranceDetailEntity);
            insuranceDetailEntity.setAssetName(fixedAssetRegDtlEntity.getItemName());
            System.out.println("=========================6====" + insuranceDetailEntity.getAssetName());
            insuranceDetailEntity.setAccountCode(fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            insuranceDetailEntity.setSelectOpt(1);
        } else {
            insuranceDetailEntity.setSelectOpt(2);
        }
        insuranceEntity.addInsuranceDetail(insuranceDetailEntity);
        recreateModelDetailminor();
        clearPopUp2();
        return null;
    }

    public String addtoInsuranceMajors() {
        insuranceDetailEntity.setAssetName(fixedassetIfrs.getFaName());
        insuranceDetailEntity.setSelectOpt(2);
        insuranceEntity.addInsuranceDetail(insuranceDetailEntity);
        recreateModelDetail2();
        clearPopUp2();
        return null;
    }

    private void recreateModelDetail2() {
        insuranceAddDetailDataModel = null;
        insuranceAddDetailDataModel = new ListDataModel(new ArrayList<>(insuranceEntity.getMmsInsuranceDetailList()));
    }

    private void recreateModelDetailminor() {
        insuranceAddDetailDataModelMinor = null;
        insuranceAddDetailDataModelMinor = new ListDataModel(new ArrayList<>(insuranceEntity.getMmsInsuranceDetailList()));
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        WfMmsProcessedDataModel = new ListDataModel(insuranceEntity.getInsuranceList());
    }

    private void recreateModelDetail() {

        if (checkDetail == true) {

            InsuraceDtl1.add(CheckListDtl.get(0));
            setCheckDetail(false);
            setSingleAddDetail(false);

        }
        if (newAddDetail == true) {
            InsuraceDtl1 = new ArrayList<>();
            System.out.println("------------------Inside newAddDetail --------------------");
            int size = insuranceEntity.getMmsInsuranceDetailList().size();
            System.out.println("......int size........" + size);
            for (int i = 0; i < size; i++) {
                InsuraceDtl1.add(insuranceEntity.getMmsInsuranceDetailList().get(i));
            }
            setNewAddDetail(false);

        }

        if (insideAdd == true) {
            System.out.println("----------Inside insideAdd -----------");
            int size = CheckListDtl2.size();
            for (int i = 0; i < size; i++) {
                InsuraceDtl1.add(CheckListDtl2.get(i));
            }

            setInsideAdd(false);
        }
        insuranceAddDetailDataModel = null;
        insuranceAddDetailDataModel = new ListDataModel(new ArrayList<>(InsuraceDtl1));
        System.out.println("-------------AddDetail DataModel Size ------------" + insuranceAddDetailDataModel.getRowCount());

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="clearpopup">
    private void clearPopUp2() {

        insuranceDetailEntity = null;
        insuranceDetailCopy = null;
        fixedAssetRegDtlEntity = null;
        itemRegistration = null;
        fixedassetIfrs = null;
        FaTransmissionEntity = null;
        faGeothermal = null;
        faHydropower = null;
        faSubstation = null;
        faTransport = null;
        faWind = null;
        building = null;

        fixedAssetDtlList.clear();

    }

    private void clearPopUp() {
        addOrModifyBundle = "Add";
        insuranceDetailEntity = null;
        insuranceDetailCopy = null;
        fixedAssetRegDtlEntity = null;
        itemRegistration = null;
        fixedAssetDtlList.clear();

        if (checkDetail == true) {
            CheckListDtl.clear();
        }

        if (newAddDetail == true) {
            InsuraceDtl1.clear();
        }

    }

    public String clearPage2() {
        storeInfoEntity = null;
        insuranceDetailEntity = null;
        itemRegistration = null;
        fixedassetIfrs = null;
        fixedAssetRegDtlEntity = null;
        return null;
    }

    public String clearPage() {
        storeInfoEntity = null;
        insuranceDetailEntity = null;
        insuranceEntity = null;
        insuranceAddDetailDataModel = null;
        mmsInsuranceSearchInfoDataModel = null;
        insuranceAddDetailDataModelMinor = null;
        insuranceAddDetailDataModel = null;
        hrDepartmentsEntity = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        workFlow = null;
        itemRegistration = null;
        fixedassetIfrs = null;

        fixedAssetRegDtlEntity = null;
        FaTransmissionEntity = null;
        faHydropower = null;
        faSubstation = null;
        faGeothermal = null;
        faTransport = null;
        building = null;
        return null;
    }
//</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Save and Searching">
    /**
     *
     * @param event
     */
    /**
     *
     * @param sivNo
     * @return
     */
    public void editDataTable() {
        addOrModifyBundle = "Modify";
        insuranceDetailEntity = insuranceAddDetailDataModelMinor.getRowData();
        if (insuranceDetailEntity.getSelectOpt() == 1) {
            renderMinor = true;
            RenderMajor = false;
            fixedAssetRegDtlEntity.setTagNo(insuranceDetailEntity.getTagNo());
            fixedassetIfrs.setFaCode(insuranceDetailEntity.getFaCode());
            fixedAssetRegDtlEntity.setItemName(insuranceDetailEntity.getAssetName());
            System.out.println("=======setSelectOpt" + insuranceDetailEntity.getSelectOpt());
            insuranceDetailEntity.setSelectOpt(insuranceDetailEntity.getSelectOpt());
        } else {
            renderMinor = false;
            RenderMajor = true;
            fixedassetIfrs.setFaCode(insuranceDetailEntity.getFaCode());
            insuranceDetailEntity.setSelectOpt(insuranceDetailEntity.getSelectOpt());
            fixedassetIfrs.setFaName(insuranceDetailEntity.getAssetName());
            
        }
    }
    
    public ArrayList<MmsSiv> searchBySivNO(String sivNo) {
        ArrayList<MmsSiv> sivNumber = null;
        sivEntity.setSivNo(sivNo);
        sivNumber = sivInterface.searchBySivNo(sivEntity);
        return sivNumber;
    }
    
    public List<MmsFixedassetRegstDetail> findTagNum() {
        fixedAssetDtlList = fxAssetRegDtlInterface.findAllTagNo();
        return fixedAssetDtlList;
    }
    
    public List<IfrsFixedAsset> findFaCode() {
        ifrsFixedAssetsList = ifrsregistrationBeanLocal.findAllFaCode();
        return ifrsFixedAssetsList;
    }
    
    public List<IfrsFixedAsset> getifrsFixedAssetsList() {
        ifrsFixedAssetsList = ifrsregistrationBeanLocal.findAllFaCode();
        return ifrsFixedAssetsList;
    }
    
    public void setifrsFixedAssetsList(List<IfrsFixedAsset> ifrsFixedAssetsList) {
        this.ifrsFixedAssetsList = ifrsFixedAssetsList;
    }
    
    public ArrayList<MmsInsurance> searchByInsuranceNo(String Insno) {
        ArrayList<MmsInsurance> Insuranceno = null;
        insuranceEntity.setInsNo(Insno);
        Insuranceno = insuranceInterface.searchByInsuranceNo(insuranceEntity);
        
        saveorUpdateBundle = "Update";
        return Insuranceno;
    }
    
    public String generateInsuranceNo() {
        System.out.println("=========controll generate ID==============");
        if (updateStatus == 1) {
            newInsuranceId = insuranceEntity.getInsNo();
        } else {
            MmsInsurance lastInsuranceID = insuranceInterface.getLastInsuranceId();
            if (lastInsuranceID != null) {
                lastInsauranceId = lastInsuranceID.getInsId().toString();
            }
            
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            
            int id = 0;
            if (lastInsauranceId.equals("0")) {
                id = 1;
                newInsuranceId = "InsuranceNo-" + id + "/" + f.format(now);
            } else {
                
                String[] lastInspNos = lastInsauranceId.split("-");
                String lastDatesPatern = lastInspNos[0];
                
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                id = Integer.parseInt(lastDatesPaterns[0]);
                id = id + 1;
                newInsuranceId = "InsuranceNo-" + id + "/" + f.format(now);
            }
        }
        return newInsuranceId;
    }
    
    public void Wfsave() {
        
        workFlow.setInsId(insuranceEntity);
        workFlowInterface.create(workFlow);
        
    }
    public void searchInsuranceInformation1() {

        String str = insuranceEntity.getInsNo();
        insuranceEntity.setInsNo(str);
        insuranceEntity.setAuthorizedBy(SessionBean.getUserId());
//        if (insuranceEntity.getInsNo()!=null) {
            allInsuranceInfoList = insuranceInterface.getInsuranceListsByParameter(insuranceEntity);
            recerateInsuranceSerachModel();

//        } else {
//
//            allInsuranceInfoList = insuranceInterface.searchAllTransmissionsInfoByPreparerId(insuranceEntity.getAuthorizedBy());
//            allInsuranceInfoList = insuranceInterface.findbyAll();
//            System.out.println("===allfind=====" + allInsuranceInfoList);
//            recerateInsuranceSerachModel();
//        }
    }

    public void createNewInsuranceInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {

            System.out.println(" -------inside createOrSearchBundle = New --------- " + createOrSearchBundle);
            renderPnlCreateInsurance = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateInsurance = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public void saveInsurance() {
        System.out.println("==============================");
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveInsurance", dataset)) {
                //      put ur code here...!
                if (updateStatus == 0) {
                    System.out.println("====================================++");
                    if (insuranceAddDetailDataModelMinor.getRowCount() > 0) {
                        try {
                            System.out.println("==========vvvvvvvvvvv------------v");
                            if (tag) {
                                System.out.println("==========vvvvvvvvvvvv");
                                fixedAssetRegDtlEntity.setFarDetId(farDtlId);
                                fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByDetailId(fixedAssetRegDtlEntity);
                                if ("Disposed".equals(fixedAssetRegDtlEntity.getItemStatus())) {
                                    JsfUtil.addFatalMessage(" The Item is Disposed ! ");
                                    clearPage();
                                } else if ("Lost".equals(fixedAssetRegDtlEntity.getItemStatus())) {
                                    JsfUtil.addFatalMessage(" The Item is Lost ! ");
                                    clearPage();
                                } else {
                                    System.out.println("====1====");
                                    insuranceEntity.setInsNo(newInsuranceId);
                                    insuranceEntity.setInsStatus(Constants.PREPARE_VALUE);
                                    System.out.println("=========Status" + insuranceEntity.getInsStatus());
                                    workFlow.setDecision(insuranceEntity.getInsStatus());
                                    insuranceEntity.setAuthorizedBy(SessionBean.getUserId()); // Processed by
                                    workFlow.setProcessedBy(insuranceEntity.getAuthorizedBy());
                                    System.out.println("=========" + insuranceEntity.getAuthorizedBy());
                                    insuranceEntity.setIssued2Date(workFlow.getProcessedOn());
                                    System.out.println("====2====");
                                    insuranceEntity.getInsuranceList().add(workFlow);
                                    System.out.println("====3====");
                                    insuranceInterface.create(insuranceEntity);
                                    System.out.println("=========" + insuranceEntity.getAuthorizedBy());
                                    System.out.println("=========" + workFlow.getProcessedBy());
                                    System.out.println("==========selctionopt=====" + insuranceDetailEntity.getSelectOpt());
                                    JsfUtil.addSuccessMessage(" A New Insurance Data Saved! ");
                                    clearPage();
                                    
                                }
                            } else {
                                
                                insuranceDetailEntity.setFaCode(fixedassetIfrs.getFaCode());
                                System.out.println("=============1===");
                                insuranceDetailEntity.setAssetName(fixedassetIfrs.getFaName());
                                insuranceEntity.setInsStatus(Constants.PREPARE_VALUE);
                                workFlow.setDecision(insuranceEntity.getInsStatus());
                                insuranceEntity.setAuthorizedBy(SessionBean.getUserId());
                                workFlow.setProcessedBy(insuranceEntity.getAuthorizedBy());
                                System.out.println("=========" + insuranceEntity.getAuthorizedBy());
                                System.out.println("=========" + SessionBean.getUserId());
                                insuranceEntity.setIssued2Date(workFlow.getProcessedOn());
                                insuranceEntity.getInsuranceList().add(workFlow);
                                System.out.println("=============2===");
                                insuranceInterface.create(insuranceEntity);
                                System.out.println("==========selctionopt=====" + insuranceDetailEntity.getSelectOpt());
                                JsfUtil.addSuccessMessage(" A New Insurance Data Saved!");
                                clearPage();
                                
                            }
                            
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Something Error Occured on Creating the Data");
                            
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Put atleast One Record on the Detail Form .");
                        
                    }
                    
                } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                    
                    try {
                        insuranceEntity.setInsStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(insuranceEntity.getInsStatus());
                        insuranceEntity.setAuthorizedBy(workFlow.getProcessedBy());// Processed by
                        insuranceEntity.setIssued2Date(workFlow.getProcessedOn()); // Processed on
                        insuranceInterface.edit(insuranceEntity);
                        Wfsave();
                        clearPage();
                        JsfUtil.addSuccessMessage("Fixed Asset Insurance has been Updated");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                        
                    }
                    
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        insuranceEntity.setInsStatus(Constants.APPROVE_VALUE);
                        workFlow.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        insuranceEntity.setInsStatus(Constants.CHECK_APPROVE_VALUE);
                        workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        insuranceEntity.setInsStatus(Constants.APPROVE_REJECT_VALUE);
                        workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        insuranceEntity.setInsStatus(Constants.CHECK_REJECT_VALUE);
                        workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    workFlow.setInsId(insuranceEntity);
                    workFlowInterface.create(workFlow);
                    insuranceInterface.edit(insuranceEntity);
                    mmsInsList.remove(insuranceEntity);
                    clearPage();
                    JsfUtil.addSuccessMessage("Insurance Data Modified");
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
    
    //<editor-fold defaultstate="collapsed" desc="RowSelection and Update">
    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateInsurance = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = mmsInsuranceSearchInfoDataModel.getRowIndex();
        insuranceEntity = allInsuranceInfoList.get(rowIndex);
        hrDepartmentsEntity = insuranceEntity.getDepartment();
        recreateModelDetail();

    }

    public void rowSelect(SelectEvent event) {

        insuranceEntity = (MmsInsurance) event.getObject();
        insuranceEntity.setInsId(insuranceEntity.getInsId());
        insuranceAddDetailDataModelMinor = null;
        InsuraceDtl1 = mmsInsuranceDetailFacade.searchByinsuranceId(insuranceEntity);
        System.out.println("InsuraceDtl1========" + InsuraceDtl1);
        insuranceAddDetailDataModelMinor = new ListDataModel(new ArrayList<>(InsuraceDtl1));
        System.out.println("========getId===" + insuranceEntity.getInsId());
        populateUI();
    }

    public void onSelectEvent(SelectEvent selectEvent) {
        if (selectEvent.getObject() != null) {
            System.out.println("2");
            if (selectEvent.getObject().toString().equalsIgnoreCase("1")) {
                System.out.println("mainor assat");
                setRenderMinor(true);
                setRenderMajor(false);
                insuranceDetailEntity.setSelectOpt(1);
                clearPage();

            } else {
                System.out.println("major asst");
                setRenderMinor(false);
                setRenderMajor(true);
                insuranceDetailEntity.setSelectOpt(2);
                setSelectOprDtlRadio(true);
                clearPage();
            }

        }
    }

    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                insuranceEntity = (MmsInsurance) event.getNewValue();

                populateUI();
            }
        } catch (Exception e) {

            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    public void populateUI() {
        insuranceDetailEntity = insuranceAddDetailDataModelMinor.getRowData();
        if (workflow.isPrepareStatus()) {
            System.out.println("yes i'm preparer");
            workFlow.setProcessedOn(insuranceEntity.getIssued2Date());
            System.out.println("date====" + workFlow.getProcessedOn());
        }
        if (insuranceDetailEntity.getSelectOpt() == 1) {
            renderMinor = true;
            RenderMajor = false;
            recreateModelDetailminor();
        } else if (insuranceDetailEntity.getSelectOpt() == 2) {
            renderMinor = false;
            RenderMajor = true;
            recreateModelDetailminor();
        }
        hrDepartmentsEntity = insuranceEntity.getDepartment();

        insuranceDetailEntity = new MmsInsuranceDetail();
        System.out.println("detail");
        if (InsuraceDtl1Size == 1) {
            setSingleAddDFromRowSelect(true);
        }
        System.out.println("-----------SingleAddDFromRowSelect---------" + singleAddDFromRowSelect);
        recreateModelDetail();
        recreateWfDataModel();
        renderPnlCreateInsurance = true;
        renderpnlToSeachPage = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        setIsRenderedIconWorkflow(true);
        recreateModelDetail2();
        recreateWfDataModel();
        recreateModelDetailminor();
    }

    public void goBackToSearchpageBtnAction() {
        renderPnlCreateInsurance = false;
        renderpnlToSeachPage = false;
        renderPnlManPage = true;
    }
//</editor-fold>
  }
