/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetTransferBeanLocal;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
import et.gov.eep.mms.entity.MmsFixedAssetTransferDtl;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
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
@Named(value = "fixedAssetTransferController")
@ViewScoped
public class FixedAssetTransferController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrdepartmentInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsFixedAssetTransferBeanLocal TransferInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fxAssetRegDtlInterface;
    @EJB
    private MmsFixedAssetRegistrationBeanLocal fixedAssetRegistrationBeanLocal;
    @EJB
    HREmployeesBeanLocal hrEmployeeInterface;
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrDeptInterface;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entitities">
    @Inject
    private MmsFixedAssetTransfer TransferEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    private MmsFixedAssetTransferDtl TransferDtlEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity2;
    @Inject
    private HrDepartments hrDepartmentsEntity3;
    @Inject
    private HrDepartments hrDepartmentsEntity4;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntity;
    @Inject
    HrEmployees hrEmployeesEntity;
    @Inject
    HrEmployees hrEmployeesEntity2;
    @Inject
    private MmsFixedassetRegstration fixedAsserRegEntity;
    @Inject
    private FmsDprOfficeAsset fmsDprEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Declare Lists and Models">
    private DataModel<MmsFixedAssetTransferDtl> mmsTransferInfoDtlDataModel;
    private DataModel<MmsFixedAssetTransfer> mmsTransferSearchInfoDataModel;
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    List<HrDepartments> DepartmentNames2 = new ArrayList<>();
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private static List<HrDepartments> araListe;
    ArrayList<HrEmployees> employeeInformations = null;
    ArrayList<HrEmployees> employeeInformations2 = null;
    List<MmsFixedAssetTransfer> allTransferinfoList;
    List<MmsFixedAssetTransfer> checkerList = new ArrayList<>();
    List<MmsFixedassetRegstration> nameList2 = new ArrayList<>();
    List<MmsFixedassetRegstration> nameList = new ArrayList<>();
    List<HrEmployees> HrEmp;
    List<HrEmployees> HrEmp2;
    Integer farDtlId;
    List<FmsDprOfficeAsset> bookValueList = new ArrayList<>();
    Integer bookvalue = 0;
    List<MmsFixedassetRegstDetail> fixedAssetDtlList = new ArrayList<>();
    List<MmsFixedAssetTransfer> allTransferInfoList;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    private String selectedValue = "";
    private boolean renderDecision = false;
    private MmsFixedAssetTransfer hpSelect;
    private boolean renderPerson = false;
    private boolean disablePerson = true;
    private boolean renderDept = false;
    private boolean renderpnlToSearchPage;
    private boolean disableDept = true;
    private String saveorUpdateBundle = "Save";
    private String CkeckMovementType = "2";
    int updateStatus = 0;
    boolean isRenderCreate;
    Set<String> checkMaterialCode = new HashSet<>();
    private boolean disableBtnCreate;
    private boolean renderPnlCreateTransfer = false;
    private boolean renderPnlManPage = true;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private List<MmsFixedAssetTransfer> mmsTrList;
    private String userName;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    String newTransferid;
    String lastTransferId = "0";
    private String addOrModifyBundle = "Add";
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Defult Construct">

    /**
     *
     */
    public FixedAssetTransferController() {
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                mmsTrList = TransferInterface.findTrListByWfStatus(Constants.CHECK_APPROVE_VALUE);
            } else if (workflow.isCheckStatus()) {
                mmsTrList = TransferInterface.findTrListForCheckerByWfStatus(Constants.PREPARE_VALUE, Constants.APPROVE_REJECT_VALUE);
            }
            isRenderCreate = false;
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsTrList = TransferInterface.findTrListByWfStatus(Constants.CHECK_REJECT_VALUE);
            renderDecision = false;
            isRenderCreate = true;
            if (mmsTrList.size() > 0) {
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
         ColumnNameResolverList = TransferInterface.getColumnNameList();
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of variables">
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
     /**
     *
     * @return
     */
    public boolean isRenderPerson() {
        return renderPerson;
    }

    /**
     *
     * @param renderPerson
     */
    public void setRenderPerson(boolean renderPerson) {
        this.renderPerson = renderPerson;
    }

    /**
     *
     * @return
     */
    public boolean isDisablePerson() {
        return disablePerson;
    }

    /**
     *
     * @param disablePerson
     */
    public void setDisablePerson(boolean disablePerson) {
        this.disablePerson = disablePerson;
    }

    /**
     *
     * @return
     */
    public boolean isRenderDept() {
        return renderDept;
    }

    /**
     *
     * @param renderDept
     */
    public void setRenderDept(boolean renderDept) {
        this.renderDept = renderDept;
    }

    /**
     *
     * @return
     */
    public boolean isDisableDept() {
        return disableDept;
    }

    /**
     *
     * @param disableDept
     */
    public void setDisableDept(boolean disableDept) {
        this.disableDept = disableDept;
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
    public String getCkeckMovementType() {
        return CkeckMovementType;
    }

    /**
     *
     * @param CkeckMovementType
     */
    public void setCkeckMovementType(String CkeckMovementType) {
        this.CkeckMovementType = CkeckMovementType;
    }
     /**
     *
     * @return
     */
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    /**
     *
     * @param disableBtnCreate
     */
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    /**
     *
     * @return
     */
    public boolean isRenderPnlCreateTransfer() {
        return renderPnlCreateTransfer;
    }

    /**
     *
     * @param renderPnlCreateTransfer
     */
    public void setRenderPnlCreateTransfer(boolean renderPnlCreateTransfer) {
        this.renderPnlCreateTransfer = renderPnlCreateTransfer;
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

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of objects">
    public MmsFixedassetRegstDetail getFixedAssetRegDtlEntity() {
        if (fixedAssetRegDtlEntity == null) {
            fixedAssetRegDtlEntity = new MmsFixedassetRegstDetail();
        }
        return fixedAssetRegDtlEntity;
    }
    
    public void setFixedAssetRegDtlEntity(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        this.fixedAssetRegDtlEntity = fixedAssetRegDtlEntity;
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
    
    public MmsFixedAssetTransfer getHpSelect() {
        return hpSelect;
    }
    
    public void setHpSelect(MmsFixedAssetTransfer hpSelect) {
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
     public FmsDprOfficeAsset getFmsDprEntity() {
        if (fmsDprEntity == null) {
            fmsDprEntity = new FmsDprOfficeAsset();
        }
        return fmsDprEntity;
    }

    public void setFmsDprEntity(FmsDprOfficeAsset fmsDprEntity) {
        this.fmsDprEntity = fmsDprEntity;
    }
     public HrEmployees getHrEmployeesEntity2() {
        if (hrEmployeesEntity2 == null) {
            hrEmployeesEntity2 = new HrEmployees();
        }
        return hrEmployeesEntity2;
    }

    public void setHrEmployeesEntity2(HrEmployees hrEmployeesEntity2) {
        this.hrEmployeesEntity2 = hrEmployeesEntity2;
    }

    public MmsFixedassetRegstration getFixedAsserRegEntity() {
        if (fixedAsserRegEntity == null) {
            fixedAsserRegEntity = new MmsFixedassetRegstration();
        }
        return fixedAsserRegEntity;
    }

    public void setFixedAsserRegEntity(MmsFixedassetRegstration fixedAsserRegEntity) {
        this.fixedAsserRegEntity = fixedAsserRegEntity;
    }

    public HrDepartments getHrDepartmentsEntity3() {
        if (hrDepartmentsEntity3 == null) {
            hrDepartmentsEntity3 = new HrDepartments();
        }
        return hrDepartmentsEntity3;
    }

    public void setHrDepartmentsEntity3(HrDepartments hrDepartmentsEntity3) {
        this.hrDepartmentsEntity3 = hrDepartmentsEntity3;
    }

    public HrDepartments getHrDepartmentsEntity4() {
        if (hrDepartmentsEntity4 == null) {
            hrDepartmentsEntity4 = new HrDepartments();
        }
        return hrDepartmentsEntity4;
    }

    public void setHrDepartmentsEntity4(HrDepartments hrDepartmentsEntity4) {
        this.hrDepartmentsEntity4 = hrDepartmentsEntity4;
    }

    public HrEmployees getHrEmployeesEntity() {
        if (hrEmployeesEntity == null) {
            this.hrEmployeesEntity = new HrEmployees();
        }
        return hrEmployeesEntity;
    }

    /**
     *
     * @param hrEmployeesEntity
     */
    public void setHrEmployeesEntity(HrEmployees hrEmployeesEntity) {
        this.hrEmployeesEntity = hrEmployeesEntity;
    }

    public MmsFixedAssetTransfer getTransferEntity() {
        if (TransferEntity == null) {
            TransferEntity = new MmsFixedAssetTransfer();
        }
        return TransferEntity;
    }

    /**
     *
     * @param TransferEntity
     */
    public void setTransferEntity(MmsFixedAssetTransfer TransferEntity) {
        this.TransferEntity = TransferEntity;
    }

    /**
     *
     * @return
     */
    public MmsFixedAssetTransferDtl getTransferDtlEntity() {
        if (TransferDtlEntity == null) {
            TransferDtlEntity = new MmsFixedAssetTransferDtl();
        }
        return TransferDtlEntity;
    }

    /**
     *
     * @param TransferDtlEntity
     */
    public void setTransferDtlEntity(MmsFixedAssetTransferDtl TransferDtlEntity) {
        this.TransferDtlEntity = TransferDtlEntity;
    }

    /**
     *
     * @return
     */
    public HrDepartments getHrDepartmentsEntity2() {
        if (hrDepartmentsEntity2 == null) {
            hrDepartmentsEntity2 = new HrDepartments();
        }
        return hrDepartmentsEntity2;
    }

    /**
     *
     * @param hrDepartmentsEntity2
     */
    public void setHrDepartmentsEntity2(HrDepartments hrDepartmentsEntity2) {
        this.hrDepartmentsEntity2 = hrDepartmentsEntity2;
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

//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of List and model">
    public DataModel<WfMmsProcessed> getWfMmsProcessedDataModel() {
        if (WfMmsProcessedDataModel == null) {
            WfMmsProcessedDataModel = new ListDataModel<>();
        }
        return WfMmsProcessedDataModel;
    }
    
    public void setWfMmsProcessedDataModel(DataModel<WfMmsProcessed> WfMmsProcessedDataModel) {
        this.WfMmsProcessedDataModel = WfMmsProcessedDataModel;
    }
    public List<MmsFixedAssetTransfer> getMmsTrList() {
        return mmsTrList;
    }

    public void setMmsTrList(List<MmsFixedAssetTransfer> mmsTrList) {
        this.mmsTrList = mmsTrList;
    }
    /**
     *
     * @return
     */
    public DataModel<MmsFixedAssetTransferDtl> getMmsTransferInfoDtlDataModel() {
        if (mmsTransferInfoDtlDataModel == null) {
            mmsTransferInfoDtlDataModel = new ListDataModel<>();
        }
        return mmsTransferInfoDtlDataModel;
    }

    /**
     *
     * @param mmsTransferInfoDtlDataModel
     */
    public void setMmsTransferInfoDtlDataModel(DataModel<MmsFixedAssetTransferDtl> mmsTransferInfoDtlDataModel) {
        this.mmsTransferInfoDtlDataModel = mmsTransferInfoDtlDataModel;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsFixedAssetTransfer> getMmsTransferSearchInfoDataModel() {
        if (mmsTransferSearchInfoDataModel == null) {
            mmsTransferSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsTransferSearchInfoDataModel;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    /**
     *
     * @param mmsTransferSearchInfoDataModel
     */
    public void setMmsTransferSearchInfoDataModel(DataModel<MmsFixedAssetTransfer> mmsTransferSearchInfoDataModel) {
        this.mmsTransferSearchInfoDataModel = mmsTransferSearchInfoDataModel;
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
     * @param DepartmentNames
     */
    public void setDepartmentNames(List<HrDepartments> DepartmentNames) {
        this.DepartmentNames = DepartmentNames;
    }

    public ArrayList<HrEmployees> searchEmployeeInformation(String FirstName) {

        hrEmployeesEntity.setFirstName(FirstName);

        employeeInformations = hrEmployeeInterface.searchEmployeeInfo(hrEmployeesEntity);
        return employeeInformations;
    }

    public ArrayList<HrEmployees> searchEmployeeInformation2(String FirstName) {

        hrEmployeesEntity2.setFirstName(FirstName);

        employeeInformations2 = hrEmployeeInterface.searchEmployeeInfo(hrEmployeesEntity2);
        return employeeInformations2;
    }

    /**
     *
     * @return
     */
    public List<HrDepartments> getDepartmentNames2() {
        DepartmentNames2 = hrdepartmentInterface.findAllDepartmentInfo();
        return DepartmentNames2;
    }

    /**
     *
     * @param DepartmentNames2
     */
    public void setDepartmentNames2(List<HrDepartments> DepartmentNames2) {
        this.DepartmentNames2 = DepartmentNames2;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }
    


//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add to dtl and recreatModel">
    private void recreateModelDetail() {
        System.out.println("====datamodel==");
        mmsTransferInfoDtlDataModel = null;
        mmsTransferInfoDtlDataModel = new ListDataModel(new ArrayList<>(TransferEntity.getMmsFixedAssetTransferDtlList()));
        System.out.println("====datamodel==" + mmsTransferInfoDtlDataModel.getRowCount());
    }

    public void recreateWfDataModel() {
        System.out.println("====wfdataModel");
        WfMmsProcessedDataModel = null;
        WfMmsProcessedDataModel = new ListDataModel(TransferEntity.getFaTransferList());
    }

    private void recerateTransferSerachModel() {

        mmsTransferSearchInfoDataModel = null;
        mmsTransferSearchInfoDataModel = new ListDataModel(new ArrayList<>(allTransferinfoList));

    }

    public String addtoTransferInfoDetail() {

        System.out.println("---------- Inside Add Method ----------");
        if (checkMaterialCode.contains(TransferDtlEntity.getTagNo())) {

            System.out.println("---------- Inside Fatal --------");
            JsfUtil.addFatalMessage("Tag No: Value is Duplicated");

            return null;
        } else {
            System.out.println("---------- Inside Success --------");
            TransferEntity.addTransferDetail(TransferDtlEntity);
            checkMaterialCode.add(TransferDtlEntity.getTagNo());

        }

        recreateModelDetail();
        clearPopUp();
        return null;
    }

    /**
     *
     * @param e
     */
    public void changeTransferType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
//            clearPage();
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {

                setRenderDept(true);
                setDisableDept(false);

//
                setRenderPerson(false);
                setDisablePerson(true);
                TransferEntity.setSelectOpt(1);

            } else {

                setRenderDept(false);
                setDisableDept(true);
                setRenderPerson(true);
                setDisablePerson(false);
                TransferEntity.setSelectOpt(2);

            }
        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clearpopup and clearPage">
    private void clearPopUp() {
        TransferDtlEntity = null;
        fixedAssetRegDtlEntity = null;
        addOrModifyBundle = "Add";
        fixedAssetDtlList.clear();
    }

    private String clearPage() {
        TransferEntity = null;
        TransferDtlEntity = null;
        hrDepartmentsEntity = null;
        hrDepartmentsEntity2 = null;
        hrDepartmentsEntity3 = null;
        hrDepartmentsEntity4 = null;
        mmsTransferInfoDtlDataModel = null;
        mmsTransferSearchInfoDataModel = null;
        hrEmployeesEntity2 = null;
        hrEmployeesEntity = null;
        hrDepartmentsEntity = null;
        fixedAssetRegDtlEntity = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        workFlow = null;
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Search and Save">
    public void createNewTransferInfo() {
        //
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clearPage();
            renderPnlCreateTransfer = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateTransfer = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    /**
     *
     * @param trNo
     * @return
     */
    public ArrayList<MmsFixedAssetTransfer> searchByTransferNo(String trNo) {
        ArrayList<MmsFixedAssetTransfer> Transferno = null;
        TransferEntity.setTransferNo(trNo);
        Transferno = TransferInterface.searchByTransferNo(TransferEntity);
        
        saveorUpdateBundle = "Update";
        return Transferno;
    }
    
    public void searchTransferInformation1() {
        
        String str = TransferEntity.getTransferNo();
        TransferEntity.setTransferNo(str);
        TransferEntity.setPreparedBy(SessionBean.getUserId());
//        if (TransferEntity.getTransferNo()!=null) {
            
            allTransferinfoList = TransferInterface.getTransferListsByParameter(TransferEntity);
            System.out.println("====trno====" + allTransferInfoList);
//            checkerList.clear();
//            checkerList = allTransferinfoList;
            recerateTransferSerachModel();
//        } else {
//            allTransferinfoList = TransferInterface.searchAlltraInfoByPreparerId(TransferEntity.getPreparedBy());
//            System.out.println("====All====" + allTransferInfoList);
//            checkerList.clear();
//            checkerList = allTransferinfoList;
//            recerateTransferSerachModel();
        }
    

    /**
     *
     * @return
     */
    public String generateLostItemNo() {
        if (updateStatus == 1) {
            newTransferid = TransferEntity.getTransferNo();
        } else {
            MmsFixedAssetTransfer lastLostItemID;
            lastLostItemID = TransferInterface.getLastTransferId();
            if (lastLostItemID != null) {
                lastTransferId = lastLostItemID.getTransferId().toString();
            }
            
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            
            int id = 0;
            if (lastTransferId.equals("0")) {
                id = 1;
                newTransferid = "FixedAssetTransferNo-" + id + "/" + f.format(now);
            } else {
                
                String[] lastInspNos = lastTransferId.split("-");
                String lastDatesPatern = lastInspNos[0];
                
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                id = Integer.parseInt(lastDatesPaterns[0]);
                id = id + 1;
                newTransferid = "FixedAssetTransferNo-" + id + "/" + f.format(now);
            }
        }
        return newTransferid;
    }
    
    /**
     *
     * @return
     */
    public void Wfsave() {
        
        workFlow.setFaTransferId(TransferEntity);
        workFlowInterface.create(workFlow);
        
    }
    
    public void editDataTable() {
        addOrModifyBundle = "Modify";
        TransferDtlEntity = mmsTransferInfoDtlDataModel.getRowData();
        fixedAssetRegDtlEntity.setTagNo(TransferDtlEntity.getTagNo());
        fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByTag(fixedAssetRegDtlEntity);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save and Update Methods">
    public void saveFATransfer() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveFATransfer", dataset)) {
                
                if (updateStatus == 0) {
                    if (mmsTransferInfoDtlDataModel.getRowCount() > 0) {
                        
                        try {
                            fixedAssetRegDtlEntity.setFarDetId(farDtlId);
                            fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByDetailId(fixedAssetRegDtlEntity);
                            
                            if (TransferEntity.getTransferDepartment() != null && TransferEntity.getReceivingDepartment() != null && TransferEntity.getTransferDepartment().equals(TransferEntity.getReceivingDepartment())) {
                                JsfUtil.addFatalMessage(" Departments should be different ");
                                clearPage();
                            } else {
                                if ("Disposed".equals(fixedAssetRegDtlEntity.getItemStatus())) {
                                    JsfUtil.addFatalMessage(" The Item is Disposed ! ");
                                    clearPage();
                                } else if ("Lost".equals(fixedAssetRegDtlEntity.getItemStatus())) {
                                    JsfUtil.addFatalMessage(" The Item is Lost ! ");
                                    clearPage();
                                } else {
                                    fixedAsserRegEntity.setId(fixedAssetRegDtlEntity.getFarId().getId());
                                    fixedAsserRegEntity = fixedAssetRegistrationBeanLocal.findByMasterId(fixedAsserRegEntity);
                                    fixedAssetRegistrationBeanLocal.edit(fixedAsserRegEntity);
                                    TransferEntity.setTransferNo(newTransferid);
                                    if (TransferEntity.getSelectOpt() == 2) {
                                        TransferEntity.setTDepartment(hrDepartmentsEntity3.getDepName());
                                        
                                        TransferEntity.setRDepartment(hrDepartmentsEntity4.getDepName());
                                    }
                                    
                                    TransferEntity.setTrStatus(Constants.PREPARE_VALUE);
                                    workFlow.setDecision(TransferEntity.getTrStatus());
                                    workFlow.setProcessedBy(SessionBean.getUserId());
                                    TransferEntity.setChckDate(workFlow.getProcessedOn());
                                    TransferEntity.setPreparedBy(SessionBean.getUserId());
                                    System.out.println("wf" + TransferEntity.getTransferId());
                                    System.out.println("states" + TransferEntity.getTrStatus());
                                    TransferEntity.getFaTransferList().add(workFlow);
                                    TransferInterface.create(TransferEntity);
                                    JsfUtil.addSuccessMessage(" A New Transfer Data Has Been Saved ! ");
                                    clearPage();
                                }
                                
                            }
                            
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Something Error Occured on Creating the Data");
                            
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Put atleast One Record on the Detail Form .");
                        
                    }
                } else if ((updateStatus == 1 && workflow.isPrepareStatus())) {
                    
                    try {
                        TransferEntity.setTrStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(TransferEntity.getTrStatus());
                        TransferEntity.setChckDate(workFlow.getProcessedOn()); // Processed on
                        TransferEntity.setPreparedBy(SessionBean.getUserId()); // Prepared By
                        TransferInterface.edit(TransferEntity);
                        Wfsave();
                        clearPage();
                        JsfUtil.addSuccessMessage("Fixed Asset Transfer has been Updated");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");
                        
                    }
                    
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        TransferEntity.setTrStatus(Constants.APPROVE_VALUE);
                        workFlow.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        TransferEntity.setTrStatus(Constants.CHECK_APPROVE_VALUE);
                        workFlow.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        TransferEntity.setTrStatus(Constants.APPROVE_REJECT_VALUE);
                        workFlow.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        TransferEntity.setTrStatus(Constants.CHECK_REJECT_VALUE);
                        workFlow.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    workFlow.setTransferFaId(TransferEntity);
                    workFlowInterface.create(workFlow);
                    TransferInterface.edit(TransferEntity);
                    mmsTrList.remove(TransferEntity);
                    clearPage();
                    JsfUtil.addSuccessMessage("Fixed Asset Transfer Data Modified");
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
    
    public List<MmsFixedassetRegstDetail> getFixedAssetDtlList() {
        fixedAssetDtlList = fxAssetRegDtlInterface.findAllTagNo();
        return fixedAssetDtlList;
    }
    
    public void setFixedAssetDtlList(List<MmsFixedassetRegstDetail> fixedAssetDtlList) {
        this.fixedAssetDtlList = fixedAssetDtlList;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Handler">
    public void onNodeSelect1(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        TransferEntity.setTransferDepartment(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDptT').hide()");
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
        hrDepartmentsEntity3.setDepId(key);
        hrDepartmentsEntity3 = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity3);
        TransferEntity.setReceivingDepartment(hrDepartmentsEntity3);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDptR').hide()");
    }

    public void handleSelectDepartment4(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            hrDepartmentsEntity4.setDepName(event.getNewValue().toString());
            TransferEntity.setRDepartment(hrDepartmentsEntity4.getDepName());
        }
    }

    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

//
            int depIds = Integer.valueOf(event.getNewValue().toString());

            hrDepartmentsEntity.setDepId(depIds);
            hrDepartmentsEntity = hrDeptInterface.findByDepartmentId(hrDepartmentsEntity);
            TransferEntity.setTransferDepartment(hrDepartmentsEntity);

            MmsFixedassetRegstration fxR = new MmsFixedassetRegstration();
            fxR.setDepartment(hrDepartmentsEntity);
            nameList2 = fixedAssetRegistrationBeanLocal.getFixedAssetInfoByDept(fxR);
            for (int i = 0; i < nameList2.size(); i++) {
                fixedAssetDtlList = nameList2.get(i).getMmsFixedassetRegstDetailList();
            }
        }
    }

    /**
     *
     * @param event
     */
    public void handleSelectDepartment2(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
//
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity2.setDepId(depIds);
            hrDepartmentsEntity2 = hrDeptInterface.findByDepartmentId(hrDepartmentsEntity2);
            TransferEntity.setReceivingDepartment(hrDepartmentsEntity2);
        }
    }

    public void handleSelectDepartment3(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            hrDepartmentsEntity3.setDepName(event.getNewValue().toString());
            TransferEntity.setTDepartment(hrDepartmentsEntity3.getDepName());

        }
    }

    public void handleTagNo(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {

            fixedAssetRegDtlEntity = (MmsFixedassetRegstDetail) event.getNewValue();
            TransferDtlEntity.setTagNo(fixedAssetRegDtlEntity.getTagNo());
            TransferDtlEntity.setItemName(fixedAssetRegDtlEntity.getItemName());
            System.out.println("========item name==" + fixedAssetRegDtlEntity.getItemName());
            TransferDtlEntity.setDescription(fixedAssetRegDtlEntity.getDescription());
            System.out.println("====Description===" + fixedAssetRegDtlEntity.getDescription());
            System.out.println("====AccountCode===" + fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            TransferDtlEntity.setAccountCode(fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            farDtlId = fixedAssetRegDtlEntity.getFarDetId();
            System.out.println("====book value" + TransferDtlEntity.getBookValue());
            bookValueList = fixedAssetRegistrationBeanLocal.findBookvalue(fixedAssetRegDtlEntity.getTagNo());
            TransferDtlEntity.setBookValue(BigDecimal.valueOf(bookvalue));
            System.out.println("====book value=========" + TransferDtlEntity.getBookValue());
            List<MmsFixedassetRegstDetail> TransNo = new ArrayList<>();           
            TransNo = fxAssetRegDtlInterface.findByTagNo2(fixedAssetRegDtlEntity);

        }
    }
 public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            TransferEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }
 
    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }
    public void handleSelectTransferInfo(SelectEvent event) {
        if (null != event.getObject().toString()) {
            String name = event.getObject().toString();
            hrEmployeesEntity.setFirstName(name);
            TransferEntity.setTransferFrom(hrEmployeesEntity.getFirstName());

        }
    }

    public void handleSelectTransferInfo2(SelectEvent event) {
        if (null != event.getObject().toString()) {
            String name = event.getObject().toString();
            hrEmployeesEntity2 = (HrEmployees) event.getObject();
            hrEmployeesEntity2.setFirstName(name);
            TransferEntity.setTransferTo(hrEmployeesEntity2.getFirstName());
            List<HrEmployees> empName2 = new ArrayList<>();
            empName2.clear();
            empName2 = hrEmployeeInterface.searchEmployeeByName(hrEmployeesEntity2);
            hrDepartmentsEntity4.setDepName(empName2.get(0).getDeptId().getDepName());

        }
    }

    public void handleSelectTransferInfo22(SelectEvent event) {

        String name = event.getObject().toString();
        hrEmployeesEntity = (HrEmployees) event.getObject();
        MmsFixedassetRegstration fxR = new MmsFixedassetRegstration();
        fxR.setRecivedBy(hrEmployeesEntity);
        nameList = fixedAssetRegistrationBeanLocal.getFixedAssetInfoByRecivedBy(fxR);
        for (int i = 0; i < nameList.size(); i++) {

            fixedAssetDtlList = nameList.get(i).getMmsFixedassetRegstDetailList();
        }
        TransferEntity.setTransferFrom(hrEmployeesEntity.getFirstName());
        List<HrEmployees> empName = new ArrayList<>();
        empName = hrEmployeeInterface.searchEmployeeByName(hrEmployeesEntity);
        hrDepartmentsEntity3.setDepName(empName.get(0).getDeptId().getDepName());

    }

    public void getPreparedDate(SelectEvent event) {
        System.out.println("Listiner 1 =========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            TransferEntity.setPreparedDate(arrival);

            System.out.println("=======PreparedDate=========" + TransferEntity.getPreparedDate());
        }
    }

    public void getCheckedDate(SelectEvent event) {
        System.out.println("Listiner 2 =========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            TransferEntity.setCheckedDate(arrival);

            System.out.println("=======ChecekedDate=========" + TransferEntity.getCheckedDate());
        }
    }

    public void getApprovedDate(SelectEvent event) {
        System.out.println("Listiner 3 =========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            TransferEntity.setApprovedDate(arrival);

            System.out.println("=======ApprovedDate=========" + TransferEntity.getApprovedDate());
        }
    }

    public void getRecievedDate(SelectEvent event) {
        System.out.println("Listiner 4 =========");
        if (event.getObject() != null && event.getObject() != "") {
            Date arrival = (Date) event.getObject();
            TransferEntity.setReceivedDate(arrival);

            System.out.println("=======RecievedDate=========" + TransferEntity.getReceivedDate());
        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="RowSelection and update">

    public void rowSelect(SelectEvent event) {
        TransferEntity = (MmsFixedAssetTransfer) event.getObject();
        populateUI();
//        if (TransferEntity.getSelectOpt() == 1) {
//            hrDepartmentsEntity.setDepId(TransferEntity.getTransferDepartment().getDepId());
//            hrDepartmentsEntity2.setDepId(TransferEntity.getReceivingDepartment().getDepId());
//            //setCkeckMovementType(1);
//        } else {
//            hrDepartmentsEntity3.setDepName(TransferEntity.getTDepartment());
//            hrDepartmentsEntity4.setDepName(TransferEntity.getRDepartment());
//            hrEmployeesEntity.setFirstName(TransferEntity.getTransferFrom());
//            hrEmployeesEntity2.setFirstName(TransferEntity.getTransferTo());
//        }
//        renderPnlManPage = false;
//        renderPnlCreateTransfer = true;
//        saveorUpdateBundle = "Update";
//        setIcone("ui-icon-search");
//        createOrSearchBundle = "Search";
//        updateStatus = 1;
//        recreateModelDetail();

    }

    /**
     *
     * @param event
     */
//    public void onRowEdit(RowEditEvent event) {
//        renderPnlCreateTransfer = true;
//        renderPnlManPage = false;
//        activeIndex = "0";
//        saveorUpdateBundle = "Update";
//        createOrSearchBundle = "Search";
//        setIcone("ui-icon-search");
//        disableBtnCreate = true;
//        updateStatus = 1;
//
//        int rowIndex = mmsTransferSearchInfoDataModel.getRowIndex();
//        TransferEntity = allTransferinfoList.get(rowIndex);
//        TransferEntity.getTransferFrom();
//        TransferEntity.getTransferTo();
//        hrDepartmentsEntity2.setDepId(TransferEntity.getReceivingDepartment().getDepId());
//        hrDepartmentsEntity.setDepId(TransferEntity.getTransferDepartment().getDepId());
//
////
//        recreateModelDetail();
//
//    }
    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                TransferEntity = (MmsFixedAssetTransfer) event.getNewValue();

                populateUI();
            }
        } catch (Exception e) {

            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    public void populateUI() {
        System.out.println("===dtl===");
//        TransferEntity = TransferInterface.getSelectedRequest(TransferEntity.getTransferId());
//        TransferEntity.setTransferId(TransferEntity.getTransferId());
        // hrDepartmentsEntity = TransferEntity.getReceivingDepartment();  // department shown here
        if (TransferEntity.getSelectOpt() == 1) {
            renderDept = true;
            System.out.println("=====selectopt=====" + TransferEntity.getSelectOpt());
//            hrDepartmentsEntity.setDepId(TransferEntity.getTransferDepartment().getDepId());
            hrDepartmentsEntity4 = TransferEntity.getTransferDepartment();
            System.out.println("=====tdep=====" + TransferEntity.getTransferDepartment());
//            System.out.println("====hdep======" + hrDepartmentsEntity.getDepId());
//            hrDepartmentsEntity2.setDepId(TransferEntity.getReceivingDepartment().getDepId());
            hrDepartmentsEntity3 = TransferEntity.getReceivingDepartment();
            System.out.println("=====Rdep=====" + TransferEntity.getReceivingDepartment());
            System.out.println("======rdep====" + hrDepartmentsEntity3.getDepId());
            //setCkeckMovementType(1);
        } else if (TransferEntity.getSelectOpt() == 2) {
            renderPerson = true;
            hrDepartmentsEntity3.setDepName(TransferEntity.getTDepartment());
            System.out.println("==========dep" + hrDepartmentsEntity3.getDepName());
            System.out.println("==========dep2" + TransferEntity.getTDepartment());
            hrDepartmentsEntity4.setDepName(TransferEntity.getRDepartment());
            System.out.println("==========dr" + hrDepartmentsEntity4.getDepName());
            System.out.println("==========dr2" + TransferEntity.getRDepartment());
            hrEmployeesEntity.setFirstName(TransferEntity.getTransferFrom());
            System.out.println("==========namer" + hrEmployeesEntity.getFirstName());
            System.out.println("==========namer2" + TransferEntity.getRDepartment());
            hrEmployeesEntity2.setFirstName(TransferEntity.getTransferTo());
            System.out.println("========== namet" + TransferEntity.getTransferDepartment());
            System.out.println("==========namet2" + TransferEntity.getTransferTo());
        }
        renderPnlManPage = false;
        renderPnlCreateTransfer = true;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");
        updateStatus = 1;
        activeIndex = "0";
//        saveorUpdateBundle = "Update";
//        createOrSearchBundle = "Search";
//        setCreateOrSearchBundle(createOrSearchBundle);
//        setIcone("ui-icon-search");
        disableBtnCreate = true;
//        updateStatus = 1;
        setIsRenderedIconWorkflow(true);
        if (workflow.isPrepareStatus()) {
            workFlow.setProcessedOn(TransferEntity.getChckDate());
        }
        recreateModelDetail();
        recreateWfDataModel();
        System.out.println("=======search dtl==");
    }

    public void goBackToSearchpageBtnAction() {
        renderPnlCreateTransfer = false;
        renderPnlManPage = true;
        renderpnlToSearchPage = false;
    }
//</editor-fold>
  }