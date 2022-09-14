package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.model.TreeNode;
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
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.mms.businessLogic.MMSGatePassInfoDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsGatePassInformationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsGatePassInfoDtl;
import et.gov.eep.mms.entity.MmsGatePassInformation;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author minab
 */
@Named(value = "gatePassInformationController")
@ViewScoped
public class GatePassInformationController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="EJB">

    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    private HrDepartmentsBeanLocal hrdepartmentInterface;
    @EJB
    private MmsGatePassInformationBeanLocal gatePassInterface;
    @EJB
    private MMSGatePassInfoDtlBeanLocal gatePassDetailInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemregistrationInterface;
    @EJB
    private MmsSivBeanLocal sivInterface;
    @EJB
    private MmsSivDtlBeanLocal sivDtlInterface;
    @EJB
    HREmployeesBeanLocal hrInterface;
    @EJB
    private MmsIsivBeanLocal IsivInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Entities">
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private MmsSiv sivs;
    @Inject
    private MmsSivDetail sivDetail;
    @Inject
    HrEmployees employeEntity;
    @Inject
    private MmsIsiv IsivEntity;
    @Inject
    private MmsIsivDetail IsivDetailEntity;
    @Inject
    private MmsGatePassInformation gatePassEntity;
    @Inject
    private MmsGatePassInfoDtl gatePassDtlEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private MmsItemRegistration itemRegistrationEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsGatePassInfoDtl> mmsGatePassInfoDtlDataModel;
    private DataModel<MmsGatePassInformation> mmsGatePassSearchInfoDataModel;
    private DataModel<MmsSivDetail> SivdetailDatamodel;
    private String selectOptGatePassNo, selectOptSIVNo, selectOptISIVNo;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private boolean isRenderedIconNitification = false;
    private boolean renderDecision = false;
    private boolean renderSivNo = false;
    private boolean disableSivNo = true;
    private boolean renderISIVNo = false;
    private boolean disableISIVNo = true;
    private boolean renderTransfer = true;
    private boolean renderforBoth = false;
    private boolean disableTransfer = false;
    private boolean modeoftransport = false;
    private boolean modeoftransportCar = true;
    private boolean disableRequesterInfo = false;
    private boolean renderpnlToSearchPage;
    private String saveorUpdateBundle = "Save";
    private String CkeckMovementType = "3";
    private List<MmsGatePassInformation> mmsGpiList;
    int updateStatus = 0;
    List<WfMmsProcessed> WfList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<MmsIsiv> ApprovedIsivNos;
    private static List<HrDepartments> araListe;
    Set<String> checkMaterialCode = new HashSet<>();
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String userName;
    private String AddorModifiy = "Add";
    private MmsGatePassInformation selectedInfo;
    private MmsGatePassInfoDtl selectedInfoDtl;
    String newGatePassNo;
    String lastGatePassNumber = "0";
    private int updatedtl = 0;
    private List<MmsGatePassInfoDtl> gatePassInfoList;
    private List<MmsGatePassInformation> mmsGatePassInfoColumnNameList;

    List<MmsGatePassInformation> allGatePassinfoList;
    private List<MmsSiv> sivList = new ArrayList<>();
    List<MmsItemRegistration> materialNameList = null;
    List<MmsStoreInformation> StoreList;
    List<MmsItemRegistration> materialCodelist = new ArrayList<>();
    private List<MmsGatePassInfoDtl> ISIVMatList = new ArrayList<>();
    private List<MmsGatePassInfoDtl> MatList = new ArrayList<>();
    List<MmsSivDetail> MaterialsBySivNo = null;
    List<MmsSiv> ApprovedSivNos;
    private List<String> mmsGateColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList1 = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Postconstraction">
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

    public GatePassInformationController() {
    }
    String selectedValue = "";

    @PostConstruct
    public void init() {

        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            System.out.println("app or check");
            renderDecision = true;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            renderDecision = false;
        }
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        allDepartmentsList = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);

        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        getMmsGateColumnNameList();
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

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="getter and setter">
    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public List<MmsGatePassInformation> getMmsGpiList() {
        if (mmsGpiList == null) {
            mmsGpiList = new ArrayList<>();
        }
        return mmsGpiList;
    }

    public void setMmsGpiList(List<MmsGatePassInformation> mmsGpiList) {
        this.mmsGpiList = mmsGpiList;
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

    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
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

    public MmsSiv getSiv() {
        if (sivs == null) {
            sivs = new MmsSiv();
        }
        return sivs;
    }

    public void setSiv(MmsSiv siv) {
        this.sivs = siv;
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

    public MmsIsiv getIsivEntity() {
        if (IsivEntity == null) {
            IsivEntity = new MmsIsiv();
        }
        return IsivEntity;
    }

    public void setIsivEntity(MmsIsiv IsivEntity) {
        this.IsivEntity = IsivEntity;
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

    public MmsIsivDetail getIsivDetailEntity() {
        if (IsivDetailEntity == null) {
            IsivDetailEntity = new MmsIsivDetail();
        }
        return IsivDetailEntity;
    }

    public void setIsivDetailEntity(MmsIsivDetail IsivDetailEntity) {
        this.IsivDetailEntity = IsivDetailEntity;
    }

    public MmsGatePassInformation getGatePassEntity() {
        if (gatePassEntity == null) {
            gatePassEntity = new MmsGatePassInformation();
        }
        return gatePassEntity;
    }

    public void setGatePassEntity(MmsGatePassInformation gatePassEntity) {
        this.gatePassEntity = gatePassEntity;
    }

    public MmsGatePassInfoDtl getGatePassDtlEntity() {
        if (gatePassDtlEntity == null) {
            gatePassDtlEntity = new MmsGatePassInfoDtl();
        }
        return gatePassDtlEntity;
    }

    public void setGatePassDtlEntity(MmsGatePassInfoDtl gatePassDtlEntity) {
        this.gatePassDtlEntity = gatePassDtlEntity;
    }

    public DataModel<MmsGatePassInfoDtl> getMmsGatePassInfoDtlDataModel() {
        if (mmsGatePassInfoDtlDataModel == null) {
            mmsGatePassInfoDtlDataModel = new ListDataModel<>();
        }
        return mmsGatePassInfoDtlDataModel;
    }

    public void setMmsGatePassInfoDtlDataModel(DataModel<MmsGatePassInfoDtl> mmsGatePassInfoDtlDataModel) {
        this.mmsGatePassInfoDtlDataModel = mmsGatePassInfoDtlDataModel;
    }

    public DataModel<MmsGatePassInformation> getMmsGatePassSearchInfoDtlDataModel() {
        if (mmsGatePassSearchInfoDataModel == null) {
            mmsGatePassSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsGatePassSearchInfoDataModel;
    }

    public void setMmsGatePassSearchInfoDtlDataModel(DataModel<MmsGatePassInformation> mmsGatePassSearchInfoDtlDataModel) {
        this.mmsGatePassSearchInfoDataModel = mmsGatePassSearchInfoDtlDataModel;
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

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public boolean isRenderSivNo() {
        return renderSivNo;
    }

    public void setRenderSivNo(boolean renderSivNo) {
        this.renderSivNo = renderSivNo;
    }

    public boolean isDisableSivNo() {
        return disableSivNo;
    }

    public void setDisableSivNo(boolean disableSivNo) {
        this.disableSivNo = disableSivNo;
    }

    public boolean isRenderISIVNo() {
        return renderISIVNo;
    }

    public void setRenderISIVNo(boolean renderISIVNo) {
        this.renderISIVNo = renderISIVNo;
    }

    public boolean isDisableISIVNo() {
        return disableISIVNo;
    }

    public void setDisableISIVNo(boolean disableISIVNo) {
        this.disableISIVNo = disableISIVNo;
    }

    public boolean isRenderTransfer() {
        return renderTransfer;
    }

    public void setRenderTransfer(boolean renderTransfer) {
        this.renderTransfer = renderTransfer;
    }

    public boolean isDisableTransfer() {
        return disableTransfer;
    }

    public void setDisableTransfer(boolean disableTransfer) {
        this.disableTransfer = disableTransfer;
    }

    public boolean isRenderforBoth() {
        return renderforBoth;
    }

    public void setRenderforBoth(boolean renderforBoth) {
        this.renderforBoth = renderforBoth;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isModeoftransport() {
        return modeoftransport;
    }

    public void setModeoftransport(boolean modeoftransport) {
        this.modeoftransport = modeoftransport;
    }

    public boolean isModeoftransportCar() {
        return modeoftransportCar;
    }

    public void setModeoftransportCar(boolean modeoftransportCar) {
        this.modeoftransportCar = modeoftransportCar;
    }

    public DataModel<MmsSivDetail> getSivdetailDatamodel() {
        if (SivdetailDatamodel == null) {
            SivdetailDatamodel = new ListDataModel<>();
        }
        return SivdetailDatamodel;
    }

    public void setSivdetailDatamodel(DataModel<MmsSivDetail> SivdetailDatamodel) {
        this.SivdetailDatamodel = SivdetailDatamodel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getAddorModifiy() {
        return AddorModifiy;
    }

    public void setAddorModifiy(String AddorModifiy) {
        this.AddorModifiy = AddorModifiy;
    }

    public MmsGatePassInfoDtl getSelectedInfoDtl() {
        return selectedInfoDtl;
    }

    public void setSelectedInfoDtl(MmsGatePassInfoDtl selectedInfoDtl) {
        this.selectedInfoDtl = selectedInfoDtl;
    }

    public MmsGatePassInformation getSelectedInfo() {
        return selectedInfo;
    }

    public void setSelectedInfo(MmsGatePassInformation selectedInfo) {
        this.selectedInfo = selectedInfo;
    }

    public boolean isDisableRequesterInfo() {
        return disableRequesterInfo;
    }

    public void setDisableRequesterInfo(boolean disableRequesterInfo) {
        this.disableRequesterInfo = disableRequesterInfo;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public List<HrDepartments> getDepartmentNames() {

        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();

        return DepartmentNames;
    }

    public List<MmsSiv> getApprovedSivNos() {
        if (ApprovedSivNos == null) {
            ApprovedSivNos = new ArrayList<>();
        }
        return ApprovedSivNos;
    }

    public void setApprovedSivNos(List<MmsSiv> ApprovedSivNos) {
        this.ApprovedSivNos = ApprovedSivNos;
    }

    public List<MmsIsiv> getApprovedIsivNos() {
        if (ApprovedIsivNos == null) {
            ApprovedIsivNos = new ArrayList<>();
        }
        return ApprovedIsivNos;
    }

    public void setApprovedIsivNos(List<MmsIsiv> ApprovedIsivNos) {
        this.ApprovedIsivNos = ApprovedIsivNos;
    }

    public List<MmsSivDetail> getMaterialsBySivNo() {
        MaterialsBySivNo = new ArrayList<>();
        return MaterialsBySivNo;
    }

    public void setCountings(List<MmsSivDetail> MaterialListbySiv) {
        this.MaterialsBySivNo = MaterialListbySiv;
    }

    public void getMmsItemInfo(SelectEvent event) {
        itemRegistrationEntity.setMatCode(event.getObject().toString());
        itemRegistrationEntity = itemregistrationInterface.getMmsItemInfoByCode(itemRegistrationEntity);
        gatePassDtlEntity.setItemId(itemRegistrationEntity);

    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
    }

    public List<MmsItemRegistration> ItemInformationList(String itemName) {
        itemRegistrationEntity.setMatName(itemName);
        itemRegistrationEntity.setStoreId(storeInfoEntity);
        // materialNameList = itemBeanLocal.searchMaterialInfoByStoreAndMatCode(itemRegEntity);
        materialNameList = itemregistrationInterface.searchMaterialInfoByStoreAndMatName(itemRegistrationEntity);
        return materialNameList;
    }

    public void getMmsItemInformation(SelectEvent event) {
        itemRegistrationEntity.setMatName(event.getObject().toString());
        itemRegistrationEntity = itemregistrationInterface.getByMaterialName(itemRegistrationEntity);
        //itemRegEntity = itemBeanLocal.getMmsItemInfoByCode(itemRegEntity);
        gatePassDtlEntity.setItemId(itemRegistrationEntity);
    }

    public List<MmsSiv> getSivList() {
        if (sivList == null) {
            sivList = new ArrayList<>();
        }
        return sivList;
    }

    public void setSivList(List<MmsSiv> srList) {
        this.sivList = srList;
    }

    public void getEmployeeInfo(SelectEvent event) {
        gatePassEntity.setSecurityOfficer(event.getObject().toString());
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

    public String getCkeckMovementType() {
        return CkeckMovementType;
    }

    public void setCkeckMovementType(String CkeckMovementType) {
        this.CkeckMovementType = CkeckMovementType;
    }

    public List<MmsGatePassInfoDtl> getGatePassInfoList() {
        if (gatePassInfoList == null) {
            gatePassInfoList = new ArrayList<>();
        }
        return gatePassInfoList;
    }

    public void setGatePassInfoList(List<MmsGatePassInfoDtl> gatePassInfoList) {
        this.gatePassInfoList = gatePassInfoList;
    }

    public List<MmsGatePassInformation> getAllGatePassInfoList() {
        if (allGatePassinfoList == null) {
            allGatePassinfoList = new ArrayList<>();
        }
        return allGatePassinfoList;
    }

    public void setAllPartyList(List<MmsGatePassInformation> allPartyList) {
        this.allGatePassinfoList = allPartyList;
    }

    public String getSelectOptGatePassNo() {
        return selectOptGatePassNo;
    }

    public void setSelectOptGatePassNo(String selectOptGatePassNo) {
        this.selectOptGatePassNo = selectOptGatePassNo;
    }

    public String getSelectOptSIVNo() {
        return selectOptSIVNo;
    }

    public void setSelectOptSIVNo(String selectOptSIVNo) {
        this.selectOptSIVNo = selectOptSIVNo;
    }

    public String getSelectOptISIVNo() {
        return selectOptISIVNo;
    }

    public void setSelectOptISIVNo(String selectOptISIVNo) {
        this.selectOptISIVNo = selectOptISIVNo;
    }

    public List<MmsGatePassInfoDtl> getMatList() {
        return MatList;
    }

    public void setMatList(List<MmsGatePassInfoDtl> MatList) {
        this.MatList = MatList;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public List<MmsGatePassInformation> getMmsGatePassInfoColumnNameList() {
        if (mmsGatePassInfoColumnNameList == null) {
            mmsGatePassInfoColumnNameList = new ArrayList<>();
//            mmsGatePassInfoColumnNameList = gatePassInterface.getMmsGatePassInfoColumnNameList();

        }
        return mmsGatePassInfoColumnNameList;
    }

    public void setMmsGatePassInfoColumnNameList(List<MmsGatePassInformation> mmsGatePassInfoColumnNameList) {
        this.mmsGatePassInfoColumnNameList = mmsGatePassInfoColumnNameList;
    }

    //</editor-fold> 
//<editor-fold defaultstate="collapsed" desc="handler,save,sreach and clear">
    /*This method is used to recursive
     */
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
    /*This method is used to On Node Select
     */

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        gatePassEntity.setDepartment(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");
    }
    /*This method is used to On Select Gpi Request
     */

    public void onSelectGpiRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                gatePassEntity = (MmsGatePassInformation) event.getNewValue();

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    public void recrateModelSIV() {
        mmsGatePassInfoDtlDataModel = null;
        mmsGatePassInfoDtlDataModel = new ListDataModel<>(gatePassEntity.getMmsGatePassInfoDtlList());
    }

    public void recrateModelISIV() {
        mmsGatePassInfoDtlDataModel = null;
        mmsGatePassInfoDtlDataModel = new ListDataModel<>(gatePassEntity.getMmsGatePassInfoDtlList());
    }
    /*This method is used to On get Items By SivNo
     */

    public void getItemsBySivNo(ValueChangeEvent event) {

        sivs = (MmsSiv) event.getNewValue();
        hrDepartmentsEntity = sivs.getStoreReqId().getDeptId();
        gatePassEntity.setDepartment(hrDepartmentsEntity);
        gatePassEntity.setRequestedBy(sivs.getStoreReqId().getRequestedBy().getFirstName());
        gatePassEntity.setRequsitionDate(sivs.getStoreReqId().getRequestedDate());
        setDisableRequesterInfo(true);
        MatList = new ArrayList<>();
        for (int i = 0; i < sivs.getMmsSivDetailList().size(); i++) {
            gatePassDtlEntity = new MmsGatePassInfoDtl();

            gatePassDtlEntity.setQuantity(sivs.getMmsSivDetailList().get(i).getQuantity().intValue());

            gatePassDtlEntity.setItemId(sivs.getMmsSivDetailList().get(i).getItemId());

            MatList.add(gatePassDtlEntity);
            gatePassEntity.addSivInfoDetail(gatePassDtlEntity);

        }

        gatePassEntity.setMmsGatePassInfoDtlList(MatList);

        recrateModelSIV();

    }
    /*This method is used to On get Items By ISivNo
     */

    public void getItemsByISivNo(ValueChangeEvent event) {
        IsivEntity = (MmsIsiv) event.getNewValue();
        hrDepartmentsEntity = IsivEntity.getDepartment();
        gatePassEntity.setRequestedBy(IsivEntity.getStoreReqId().getRequestedBy().getFirstName());
        gatePassEntity.setRequsitionDate(IsivEntity.getStoreReqId().getRequestedDate());

        gatePassEntity.setDepartment(hrDepartmentsEntity);
        setDisableRequesterInfo(true);
        ISIVMatList = new ArrayList<>();
        for (int i = 0; i < IsivEntity.getMmsIsivDetailList().size(); i++) {
            gatePassDtlEntity = new MmsGatePassInfoDtl();
            gatePassDtlEntity.setItemId(IsivEntity.getMmsIsivDetailList().get(i).getItemId());
            gatePassDtlEntity.setQuantity(IsivEntity.getMmsIsivDetailList().get(i).getQuantity().intValue());

            ISIVMatList.add(gatePassDtlEntity);
            gatePassEntity.addISivInfoDetail(gatePassDtlEntity);

        }

        gatePassEntity.setMmsGatePassInfoDtlList(ISIVMatList);

        recrateModelISIV();
    }
    /*This method is used to material Info Search list
     */

    public List<MmsItemRegistration> materialInfoSearchlist(String materialCode) {

        itemRegistrationEntity.setMatCode(materialCode);

        materialCodelist = itemregistrationInterface.searchMaterialInfoByMatCode(itemRegistrationEntity);

        return materialCodelist;
    }
    /*This method is used to handle Select Store Name
     */

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("===========event==" + event.getNewValue());
            storeInfoEntity.setStoreId(Integer.parseInt(event.getNewValue().toString()));
            if (CkeckMovementType.equals("1")) {
                sivList = sivInterface.findAllwithApprovedStatus(storeInfoEntity, Constants.APPROVE_VALUE);
                System.out.println("===========size1==" + sivList.size());
                System.out.println("===========siv lists" + sivList);

            } else if (CkeckMovementType.equals("2")) {
                ApprovedIsivNos = IsivInterface.approvedIsivListByWfStatusAndStoreId(storeInfoEntity, Constants.APPROVE_VALUE);
                System.out.println(" all list=====" + ApprovedIsivNos);
            }
            gatePassEntity.setStoreId(storeInfoEntity);
        }

    }
    /*This method is used to handle Select Mode Of Transport
     */

    public void handleSelectModeOfTransport(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            String mode = event.getNewValue().toString();
            if (mode.equalsIgnoreCase("Other")) {

                setModeoftransport(true);
                setModeoftransportCar(false);
            } else {
                setModeoftransport(false);
                setModeoftransportCar(true);
            }
        }
    }
    /*This method is used to handle Select Department
     */

    public void handleSelectDepartment(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
//            
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(depIds);
            gatePassEntity.setDepartment(hrDepartmentsEntity);
        }
    }
    /*This method is used to handle Select GatePassStatus
     */

    public void handleSelectGatePassStatus(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            gatePassEntity.setGatePassStatus(event.getNewValue().toString());
        }
    }
    /*This method is used to clear Pop Up
     */

    public void clearPopUp() {

        itemRegistrationEntity = new MmsItemRegistration();
        gatePassDtlEntity = new MmsGatePassInfoDtl();
    }
    /*This method is used to add Gate Pass Info Detail
     */

    public String addGatePassInfoDetail() {
        if (checkMaterialCode.contains(gatePassDtlEntity.getItemId().getMatCode())) {
            if (updatedtl == 1) {
                for (int i = 0; i < gatePassEntity.getMmsGatePassInfoDtlList().size(); i++) {
                    System.out.println("======inside for loop======");
                    System.out.println("=====item code dtl=====" + gatePassDtlEntity.getItemId().getMatCode());
                    if (gatePassDtlEntity.getItemId().getMatCode().equals(gatePassEntity.getMmsGatePassInfoDtlList().get(i).getItemId().getMatCode())) {
                        System.out.println("======inside for loop IF ======");
                        System.out.println("======inside for loop if  vlues dtl======" + gatePassDtlEntity.getQuantity());
                        System.out.println("======inside for loop if  vlues list======" + gatePassEntity.getMmsGatePassInfoDtlList().get(i).getQuantity());
                        gatePassEntity.getMmsGatePassInfoDtlList().get(i).setQuantity(gatePassDtlEntity.getQuantity());
                        System.out.println("======inside for loop if  vlues after add======" + gatePassEntity.getMmsGatePassInfoDtlList().get(i).getQuantity());
                    }
                }
            } else if (updatedtl == 0) {
                System.out.println("======inside duplicate======");
                for (int i = 0; i < gatePassEntity.getMmsGatePassInfoDtlList().size(); i++) {
                    System.out.println("======inside for loop======");
                    System.out.println("=====item code dtl=====" + gatePassDtlEntity.getItemId().getMatCode());
                    if (gatePassDtlEntity.getItemId().getMatCode().equals(gatePassEntity.getMmsGatePassInfoDtlList().get(i).getItemId().getMatCode())) {
                        System.out.println("======inside for loop IF ======");
                        System.out.println("======inside for loop if  vlues dtl======" + gatePassDtlEntity.getQuantity());
                        System.out.println("======inside for loop if  vlues list======" + gatePassEntity.getMmsGatePassInfoDtlList().get(i).getQuantity());
                        gatePassEntity.getMmsGatePassInfoDtlList().get(i).setQuantity(gatePassEntity.getMmsGatePassInfoDtlList().get(i).getQuantity() + gatePassDtlEntity.getQuantity());
                        System.out.println("======inside for loop if  vlues after add======" + gatePassEntity.getMmsGatePassInfoDtlList().get(i).getQuantity());
                    }
                }
            }
        } else {

            gatePassEntity.addTOGatePassInfoDetail(gatePassDtlEntity);
            checkMaterialCode.add(gatePassDtlEntity.getItemId().getMatCode());

        }

        recreateModelDetail();
        clearPopUp();
        return null;
    }

    private void recreateModelDetail() {
        mmsGatePassInfoDtlDataModel = null;
        mmsGatePassInfoDtlDataModel = new ListDataModel(new ArrayList<>(gatePassEntity.getMmsGatePassInfoDtlList()));
    }

    private void recerateGatePassSerachModel() {
        mmsGatePassSearchInfoDataModel = null;
        mmsGatePassSearchInfoDataModel = new ListDataModel(new ArrayList<>(allGatePassinfoList));
    }
    /*This method is used to save GatePass Information
     */

    public void save_GatePass_Information() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_GatePass_Information", dataset)) {
                if (updateStatus == 0) {
                    if (null != CkeckMovementType) //generateGatePassNo();
                    {
                        switch (CkeckMovementType) {
                            case "3":
                                System.out.println("============type of transfer======" + gatePassEntity.getTypeOfTransfer());
                                gatePassEntity.setMovementType("Transfer");
                                System.out.println("19");
                                setWorkFlowValues();
                                gatePassInterface.create(gatePassEntity);
                                JsfUtil.addSuccessMessage("GatePass information is Saved Transfer");
                                break;
                            case "2":
                                System.out.println("==========inside save isiv==========");
                                gatePassEntity.setIsivId(IsivEntity);
                                setWorkFlowValues();
                                gatePassInterface.create(gatePassEntity);
                                IsivEntity.setStatusWf(Constants.ISIV_PROCESSED_BY_GATEPASS_INFORMATION);
                                IsivInterface.edit(IsivEntity);
                                JsfUtil.addSuccessMessage("GatePass information is Saved on ISIV");
                                break;
                            case "1":
                                System.out.println("==========inside save siv==========");
                                gatePassEntity.setSivId(sivs);
                                setWorkFlowValues();
                                gatePassInterface.create(gatePassEntity);
                                JsfUtil.addSuccessMessage("GatePass information is Saved SIV");
                                sivs.setStatus(Constants.SIV_PROCESSED_BY_GATEPASS_INFORMATION);
                                sivInterface.edit(sivs);
                                break;
                        }
                    }

                } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                    gatePassEntity.setProcessedBy(SessionBean.getUserId());
                    gatePassEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                    gatePassEntity.setStatus(Constants.PREPARE_VALUE);
                    gatePassInterface.edit(gatePassEntity);
                    JsfUtil.addSuccessMessage("GatePass information is Updated");
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        gatePassEntity.setStatus(Constants.APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        gatePassEntity.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        gatePassEntity.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        gatePassEntity.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
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
    /*This method is used to se tWork Flow Values
     */

    public void setWorkFlowValues() {
        gatePassEntity.setGatePassNo(newGatePassNo);
        gatePassEntity.setDepartment(hrDepartmentsEntity);
        wfMmsProcessed.setGatePassId(gatePassEntity);
        gatePassEntity.setProcessedBy(SessionBean.getUserId());
        gatePassEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
        gatePassEntity.setStatus(Constants.PREPARE_VALUE);

        gatePassEntity.getWfMmsProcessedList().add(wfMmsProcessed);

    }
    /*This method is used to clear Page
     */

    private void clearPage() {
        gatePassEntity = new MmsGatePassInformation();
        gatePassDtlEntity = new MmsGatePassInfoDtl();
        hrDepartmentsEntity = new HrDepartments();
        sivs = new MmsSiv();
        sivDetail = new MmsSivDetail();
        IsivEntity = new MmsIsiv();
        IsivDetailEntity = new MmsIsivDetail();
        storeInfoEntity = new MmsStoreInformation();
        mmsGatePassInfoDtlDataModel = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        wfMmsProcessed = null;

        mmsGatePassSearchInfoDataModel = new ListDataModel<>();
    }
    /*This method is used to Code that affects the rendering of components
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
    }
    /*This method is used to create New Gate PassInfo
     */

    public void createNewGatePassInfo() {
        //
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }

    }
    /*This method is used to btn search Action
     */

    public void btn_search_Action() {
        clearPage();
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        createOrSearchBundle = "New";
    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            gatePassEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            gatePassEntity.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsGateColumnNameList() {
        mmsGateColumnNameList = gatePassInterface.getMmsGatePassInfoColumnNameList();
        if (mmsGateColumnNameList == null) {
            mmsGateColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsRmgColumnNameList==" + mmsGateColumnNameList);
        if (mmsGateColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsGateColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsGateColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsGateColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsGateColumnNameList;
    }

    public void setMmsGateColumnNameList(List<String> mmsGateColumnNameList) {
        this.mmsGateColumnNameList = mmsGateColumnNameList;
    }

    /*This method is used to search Gate Pass By Parameter
     */
    public void searchGatePassByParameter() {
        System.out.println("in search");
        gatePassEntity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + gatePassEntity.getProcessedBy());
        allGatePassinfoList = gatePassInterface.getGatePassListsByParameter(columnNameResolver, gatePassEntity, gatePassEntity.getColumnValue());
        recerateGatePassSerachModel();

    }
    /*This method is used to search Employee Information
     */

    public ArrayList<HrEmployees> searchEmployeeInformation(String FirstName) {
        ArrayList<HrEmployees> employeeInformations = null;
        employeEntity.setFirstName(FirstName);
        employeeInformations = hrInterface.searchEmployeeInfo(employeEntity);
        return employeeInformations;
    }
    /*This method is used to change Check Movement Type
     */

    public void changeCheckMovementType(ValueChangeEvent e) {
        clearPage();
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderSivNo(true);
                setDisableSivNo(false);

                setRenderISIVNo(false);
                setDisableISIVNo(true);

                setRenderTransfer(false);
                setDisableTransfer(true);
                gatePassEntity.setMovementType("SIV");
                setRenderforBoth(true);
            } else if (e.getNewValue().toString().equalsIgnoreCase("2")) {

                setRenderSivNo(false);
                setDisableSivNo(true);
                setRenderISIVNo(true);
                setDisableISIVNo(false);
                setRenderTransfer(false);
                setDisableTransfer(true);
                gatePassEntity.setMovementType("ISIV");
                setRenderforBoth(true);
            } else if (e.getNewValue().toString().equalsIgnoreCase("3")) {

                setRenderSivNo(false);
                setDisableSivNo(true);
                setRenderISIVNo(false);
                setDisableISIVNo(true);
                setRenderTransfer(true);
                setDisableTransfer(false);
                gatePassEntity.setMovementType("Transfer");
                setRenderforBoth(false);
            }

        }
    }
    /*This method is used to on Row Edit
     */

    public void onRowEdit(SelectEvent event) {
        gatePassEntity = (MmsGatePassInformation) event.getObject();
        storeInfoEntity = gatePassEntity.getStoreId();
        switch (gatePassEntity.getMovementType()) {
            case "SIV":
                setCkeckMovementType("1");
                setRenderSivNo(true);
                setDisableSivNo(false);
                setRenderISIVNo(false);
                setDisableISIVNo(true);
                setRenderTransfer(false);
                setDisableTransfer(true);
                sivs = gatePassEntity.getSivId();
                hrDepartmentsEntity = sivs.getStoreReqId().getDeptId();
                sivList = new ArrayList<>();
                sivList.add(sivs);
                break;
            case "ISIV":
                setCkeckMovementType("2");
                setRenderSivNo(false);
                setDisableSivNo(true);
                setRenderISIVNo(true);
                setDisableISIVNo(false);
                setRenderTransfer(false);
                setDisableTransfer(true);
                IsivEntity = gatePassEntity.getIsivId();
                hrDepartmentsEntity = IsivEntity.getStoreReqId().getDeptId();
                ApprovedIsivNos = new ArrayList<>();
                ApprovedIsivNos.add(IsivEntity);
                break;
            case "Transfer":
                setCkeckMovementType("3");
                setRenderSivNo(false);
                setDisableSivNo(true);
                setRenderISIVNo(false);
                setDisableISIVNo(true);
                setRenderTransfer(true);
                setDisableTransfer(false);
                hrDepartmentsEntity = gatePassEntity.getDepartment();
                break;
        }

        if (gatePassEntity.getModeOfTransport().equals("Other")) {
            setModeoftransport(true);
            setModeoftransportCar(false);
        }
        wfMmsProcessed.setProcessedOn(gatePassEntity.getProcessedOn());
        renderPnlCreateGatePass = true;

        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        hrDepartmentsEntity.setDepId(gatePassEntity.getDepartment().getDepId());
        recreateModelDetail();
    }
    /*This method is used to on Row Update
     */

    public void onRowUpdate(SelectEvent event) {
        gatePassDtlEntity = (MmsGatePassInfoDtl) event.getObject();
        AddorModifiy = "Modifiy";
        itemRegistrationEntity = gatePassDtlEntity.getItemId();
        updatedtl = 1;
    }
    /*This method is used to on generate Gate Pass No
     */

    public String generateGatePassNo() {
        if (updateStatus == 1) {
            newGatePassNo = gatePassEntity.getGatePassNo();
        } else {

            MmsGatePassInformation lastGatePassNO = gatePassInterface.getLastGatePassNo();
            if (lastGatePassNO != null) {
                lastGatePassNumber = lastGatePassNO.getGatePassId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newPayment = 0;
            if (lastGatePassNumber.equals("0")) {
                newPayment = 1;
                newGatePassNo = "GPassNo-" + newPayment + "/" + f.format(now);
            } else {

                String[] lastInspNos = lastGatePassNumber.split("-");
                String lastDatesPatern = lastInspNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newGatePassNo = "GPassNo-" + newPayment + "/" + f.format(now);
            }
        }
        return newGatePassNo;
    }
    //</editor-fold>
}
