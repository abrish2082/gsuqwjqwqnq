package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetReturnBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetReturnDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedAssetReturnDtl;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsStoreInformation;
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
import javax.jms.Session;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.context.RequestContext;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
//</editor-fold>

/**
 *
 * @author Minab Sahilu
 */
@Named(value = "fixedAssetReturnController")
@ViewScoped
public class FixedAssetReturnController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    private HrDepartmentsIntegrationBeanLocal hrdepartmentInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    private MmsFixedAssetReturnBeanLocal returnInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal fxAssetRegDtlInterface;

    @EJB
    private MmsFixedAssetRegistrationBeanLocal fixedAssetRegistrationBeanLocal;

    @EJB
    private MmsFixedAssetReturnDtlBeanLocal fixedAssetRetDtlInterface;
    @EJB
    HrEmployeesFacade hrEmployeesFacade;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inject Entitities">
    @Inject
    private MmsFixedAssetReturn returnEntity;
    @Inject
    private MmsFixedAssetReturn assetReturn;
    @Inject
    private MmsFixedAssetReturnDtl returnDtlEntity;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsFixedassetRegstDetail fixedAssetRegDtlEntity;
    @Inject
    private MmsFixedassetRegstration fixedassetRegstration;
    @Inject
    private FmsDprOfficeAsset fmsDprEntity;
    @Inject
    WfMmsProcessed wfMmsProcessed;
//    @Inject
//    SessionBean sessionBean;
//    @Inject
//    WorkFlow workflow;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare lists and Model">
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    private DataModel<MmsFixedAssetReturnDtl> returnAddDetailDataModel;
    private DataModel<MmsFixedAssetReturn> mmsreturnSearchInfoDataModel;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private static List<HrDepartments> araListe;
    private DataModel<WfMmsProcessed> WfMmsProcessedDataModel;
    private List<MmsFixedAssetReturn> mmsFarList;
    List<MmsFixedAssetReturn> recreatedList = new ArrayList<>();
    List<FmsDprOfficeAsset> bookValueList = new ArrayList<>();
    List<MmsFixedassetRegstDetail> listFixedAssetRegDtl;
    List<MmsFixedassetRegstration> listFixedAssetReg;
    List<MmsFixedassetRegstDetail> itemcodeDtl;
    List<MmsFixedassetRegstration> nameList = new ArrayList<>();
    List<Integer> ids = new ArrayList<>();
    List<MmsFixedassetRegstration> nameList2 = new ArrayList<>();
    List<MmsFixedAssetReturnDtl> retDtl = new ArrayList<>();
    List<MmsFixedAssetReturn> allReturnInfoList;
    List<MmsFixedAssetReturn> checkerList = new ArrayList<>();
    List<MmsStoreInformation> storeInfoLst = new ArrayList<>();
    List<MmsFixedAssetReturn> allReturninfoList;
    List<MmsFixedAssetReturnDtl> tagNoDtl = new ArrayList<>();
    List<MmsFixedAssetReturn> deptList = new ArrayList<>();
    List<MmsFixedassetRegstration> NameList = new ArrayList<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    private MmsFixedAssetReturn hpSelect;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateReturn = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    Set<String> checkMaterialCode = new HashSet<>();
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private String userName;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkflow = false;
    private String selectedValue = "";
    boolean isRenderCreate;
    private boolean renderDecision = false;
    String tagNumber;
    Integer farDtlId;
    Integer bookvalue = 0;
    String newReturnId;
    String lastReturnId = "0";
    private String addOrModifyBundle = "Add";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {

        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            if (workflow.isApproveStatus()) {
                mmsFarList = returnInterface.findFarListByWfStatus(Constants.CHECK_APPROVE_VALUE);
            } else if (workflow.isCheckStatus()) {
                mmsFarList = returnInterface.findFarListForCheckerByWfStatus(Constants.PREPARE_VALUE, Constants.APPROVE_REJECT_VALUE);
            }
            isRenderCreate = false;
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            mmsFarList = returnInterface.findFarListByWfStatus(Constants.CHECK_REJECT_VALUE);
            renderDecision = false;
            isRenderCreate = true;
            isRenderedIconNitification = mmsFarList.size() > 0;

        }

        wfMmsProcessed.setProcessedBy(workflow.getUserAccount());
        System.out.println(" processed by " + wfMmsProcessed.getProcessedBy());
        setUserName(workflow.getUserName());
        System.out.println("user id ===" + workflow.getUserAccount());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());

        allDepartmentsList = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);

        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        ColumnNameResolverList = returnInterface.getColumnNameList();
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of list and model">
    public List<FmsDprOfficeAsset> getBookValueList() {
        return bookValueList;
    }

    public void setBookValueList(List<FmsDprOfficeAsset> bookValueList) {
        this.bookValueList = bookValueList;
    }

    public WorkFlow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkFlow workflow) {
        this.workflow = workflow;
    }

    public List<MmsFixedassetRegstDetail> getItemcodeDtl() {
        if (itemcodeDtl == null) {
            itemcodeDtl = new ArrayList<>();
        }
        return itemcodeDtl;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public void setItemcodeDtl(List<MmsFixedassetRegstDetail> itemcodeDtl) {
        this.itemcodeDtl = itemcodeDtl;
    }

    public List<MmsFixedAssetReturn> getMmsFarList() {
        return mmsFarList;
    }

    public void setMmsFarList(List<MmsFixedAssetReturn> mmsFarList) {
        this.mmsFarList = mmsFarList;
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

    public DataModel<MmsFixedAssetReturnDtl> getReturnAddDetailDataModel() {
        if (returnAddDetailDataModel == null) {

            returnAddDetailDataModel = new ListDataModel<>();
        }
        return returnAddDetailDataModel;
    }

    public void setReturnAddDetailDataModel(DataModel<MmsFixedAssetReturnDtl> returnAddDetailDataModel) {
        this.returnAddDetailDataModel = returnAddDetailDataModel;
    }

    public DataModel<MmsFixedAssetReturn> getMmsreturnSearchInfoDataModel() {
        if (mmsreturnSearchInfoDataModel == null) {
            mmsreturnSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsreturnSearchInfoDataModel;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public void setMmsreturnSearchInfoDataModel(DataModel<MmsFixedAssetReturn> mmsreturnSearchInfoDataModel) {
        this.mmsreturnSearchInfoDataModel = mmsreturnSearchInfoDataModel;
    }

    public List<MmsFixedassetRegstration> getListFixedAssetReg() {
        if (listFixedAssetReg == null) {
            listFixedAssetReg = new ArrayList<>();
        }
        return listFixedAssetReg;
    }

    public void setListFixedAssetReg(List<MmsFixedassetRegstration> listFixedAssetReg) {
        this.listFixedAssetReg = listFixedAssetReg;
    }

    public List<MmsFixedassetRegstDetail> getListFixedAssetRegDtl() {
        if (listFixedAssetRegDtl == null) {
            listFixedAssetRegDtl = new ArrayList<>();
        }
        return listFixedAssetRegDtl;
    }

    public void setListFixedAssetRegDtl(List<MmsFixedassetRegstDetail> listFixedAssetRegDtl) {
        this.listFixedAssetRegDtl = listFixedAssetRegDtl;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public SessionBean getSessionBean() {
        return SessionBean;
    }

    public void setSessionBean(SessionBean SessionBean) {
        this.SessionBean = SessionBean;
    }

    /**
     *
     * @author User
     */
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of Variables">
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

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
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

    //<editor-fold defaultstate="collapsed" desc="Getter and setter of objects">
    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
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

    public MmsFixedAssetReturn getAssetReturn() {
        if (assetReturn == null) {
            assetReturn = new MmsFixedAssetReturn();
        }
        return assetReturn;
    }

    public void setAssetReturn(MmsFixedAssetReturn assetReturn) {
        this.assetReturn = assetReturn;
    }

    public MmsFixedAssetReturn getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFixedAssetReturn hpSelect) {
        this.hpSelect = hpSelect;
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

    public MmsFixedassetRegstration getFixedassetRegstration() {

        if (fixedassetRegstration == null) {
            fixedassetRegstration = new MmsFixedassetRegstration();
        }
        return fixedassetRegstration;
    }

    public void setFixedassetRegstration(MmsFixedassetRegstration fixedassetRegstration) {
        this.fixedassetRegstration = fixedassetRegstration;
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

    public MmsFixedassetRegstDetail getFixedAssetRegDtlEntity() {
        if (fixedAssetRegDtlEntity == null) {
            fixedAssetRegDtlEntity = new MmsFixedassetRegstDetail();
        }
        return fixedAssetRegDtlEntity;
    }

    public void setFixedAssetRegDtlEntity(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        this.fixedAssetRegDtlEntity = fixedAssetRegDtlEntity;
    }

    public MmsFixedAssetReturn getReturnEntity() {
        if (returnEntity == null) {
            returnEntity = new MmsFixedAssetReturn();
        }
        return returnEntity;
    }

    public void setReturnEntity(MmsFixedAssetReturn returnEntity) {
        this.returnEntity = returnEntity;
    }

    public MmsFixedAssetReturnDtl getReturnDtlEntity() {
        if (returnDtlEntity == null) {
            returnDtlEntity = new MmsFixedAssetReturnDtl();
        }
        return returnDtlEntity;
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

    public void setReturnDtlEntity(MmsFixedAssetReturnDtl returnDtlEntity) {
        this.returnDtlEntity = returnDtlEntity;
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
    //<Defualt non argument Methods>

    public FixedAssetReturnController() {
    }

    //<editor-fold defaultstate="collapsed" desc="Handler">
    public void handleSelectStoreName1(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            int storeIds = Integer.valueOf(event.getNewValue().toString());
            storeInfoEntity.setStoreId(storeIds);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);

            returnEntity.setStoreId(storeInfoEntity);

        }
    }

    public void handleSelectDepartment(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {

            String dept = event.getNewValue().toString();
            int hrid = Integer.parseInt(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(hrid);
            hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
            fixedassetRegstration.setDepartment(hrDepartmentsEntity);
            listFixedAssetReg.clear();
            listFixedAssetReg = fixedAssetRegistrationBeanLocal.getFixedAssetInfoByDept(fixedassetRegstration);
            returnEntity.setDepartment(hrDepartmentsEntity);

        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        System.out.println("==========dep ids====" + hrDepartmentsEntity.getDepId());
        itemcodeDtl = fxAssetRegDtlInterface.getFixedAssetInfoByRecivedBy(hrDepartmentsEntity);
        System.out.println("itemcodeDtl=====" + itemcodeDtl);
        returnEntity.setDepartment(hrDepartmentsEntity);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");

    }

    public void handleSelectReturn(ValueChangeEvent event) {
        String returnby = event.getNewValue().toString();
        System.out.println("return by " + returnby);
        hrEmployees.setFirstName(returnby);
        System.out.println("1=== " + hrEmployees.getFirstName());
        ids = returnInterface.getEmpIdByName(returnby);
        System.out.println("final " + ids);
        listFixedAssetRegDtl = fxAssetRegDtlInterface.getTagNoByRequester1(ids);
        System.out.println(" " + listFixedAssetRegDtl);
    }

    public void handleSelectReturnDtl(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            System.out.println("not null");
            int requestId = Integer.parseInt(event.getNewValue().toString());
            System.out.println("req " + requestId);
            System.out.println("Fro Hr   " + hrEmployees.getId());
            fixedAssetRegDtlEntity.setRequeistedBy(requestId);
            System.out.println("  " + fixedAssetRegDtlEntity.getRequeistedBy());
        }
    }

    public void handleSelectReturndBy(ValueChangeEvent event) {
        fixedAssetRegDtlEntity = (MmsFixedassetRegstDetail) event.getNewValue();
        returnEntity.setMmsFixedAssetReturnDtlList(retDtl);
        System.out.println("======retDt1====" + retDtl);
        System.out.println("======hrEmployees====" + hrEmployees.getEmpId());
        System.out.println("======hrEmployees====" + fixedAssetRegDtlEntity.getTagNo());
    }

    public void handleSelectItemCode1(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String gettingOthersByTagNo = event.getNewValue().toString();
            fixedAssetRegDtlEntity = returnInterface.gettingTagInfo(gettingOthersByTagNo);
            System.out.println("==========" + fixedAssetRegDtlEntity);
            returnDtlEntity.setItemName(fixedAssetRegDtlEntity.getItemName());
            returnDtlEntity.setAccountCode(fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            returnDtlEntity.setNetBookValue(BigDecimal.valueOf(bookvalue));
            tagNumber = fixedAssetRegDtlEntity.getTagNo();
            farDtlId = fixedAssetRegDtlEntity.getFarDetId();
            System.out.println("tag no====" + tagNumber);
        }

    }

    public void handleSelectItemCode(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            fixedAssetRegDtlEntity.setTagNo(event.getNewValue().toString());
            returnDtlEntity.setItemCode(event.getNewValue().toString());
            returnDtlEntity.setItemName(fixedAssetRegDtlEntity.getItemName());
            returnDtlEntity.setDescription(fixedAssetRegDtlEntity.getDescription());
            returnDtlEntity.setAccountCode(fixedAssetRegDtlEntity.getAccountCode().getGeneralLedgerCode());
            System.out.println("------ farDtlId -- --  " + farDtlId);
            System.out.println("------ Item Name --------" + returnDtlEntity.getItemName());
            System.out.println("====book value" + returnDtlEntity.getNetBookValue());
            bookValueList = fixedAssetRegistrationBeanLocal.findBookvalue(fixedAssetRegDtlEntity.getTagNo());
            System.out.println("books list=====" + bookValueList);
            System.out.println("====book value=========" + returnDtlEntity.getNetBookValue());
            returnDtlEntity.setNetBookValue(BigDecimal.valueOf(bookvalue));
            List<MmsFixedassetRegstDetail> TransNo = new ArrayList<>();
            TransNo = fxAssetRegDtlInterface.findByTagNo2(fixedAssetRegDtlEntity);
        }
    }

    public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            returnEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }

    public List<MmsStoreInformation> getStoreInfoLst() {
        storeInfoLst = storeInfoInterface.findAllStoreInfo();
        return storeInfoLst;
    }

    public void setStoreInfoLst(List<MmsStoreInformation> storeInfoLst) {
        this.storeInfoLst = storeInfoLst;
    }

    public SelectItem[] getTagNoDtl() {
        tagNoDtl = fixedAssetRetDtlInterface.findAllTagNo();
        return JsfUtil.getSelectItems(tagNoDtl, true);
    }

    public SelectItem[] getDeptList() {
        deptList = returnInterface.findAllDept();
        return JsfUtil.getSelectItems(deptList, true);
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

    //<editor-fold defaultstate="collapsed" desc="Add to detiles and Recreate">
    private void recerateReturnSerachModel2() {
        mmsreturnSearchInfoDataModel = null;
        mmsreturnSearchInfoDataModel = new ListDataModel(new ArrayList<>(allReturnInfoList));
    }

    private void recerateReturnSerachModel() {
        mmsreturnSearchInfoDataModel = null;
        mmsreturnSearchInfoDataModel = new ListDataModel(new ArrayList<>(allReturnInfoList));
    }

    public void goBackToSearchpageBtnAction() {
        renderPnlCreateReturn = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public String addtoReturnInfoDetail() {
        //fixedAssetRegDtlEntity.setTagNo(returnDtlEntity.getFarId().getFixedARDtId().getTagNo());
        returnEntity.addFixedAssetReturnDetail(returnDtlEntity);
        recreateModelDetail();
        clearPopUp();
        return null;
    }

    private void recreateModelDetail() {
        returnAddDetailDataModel = null;
        returnAddDetailDataModel = new ListDataModel(new ArrayList<>(returnEntity.getMmsFixedAssetReturnDtlList()));
    }

    public void recreateWfDataModel() {
        WfMmsProcessedDataModel = null;
        for (int i = 0; i < returnEntity.getFixedAssetList().size(); i++) {
            if (returnEntity.getFixedAssetList().get(i).getDecision() != null) {
                if (returnEntity.getFixedAssetList().get(i).getDecision() == 1 || returnEntity.getFixedAssetList().get(i).getDecision() == 3) {
                    returnEntity.getFixedAssetList().get(i).setWfDecison("Approved");
                } else if (returnEntity.getFixedAssetList().get(i).getDecision() == 2 || returnEntity.getFixedAssetList().get(i).getDecision() == 4) {
                    returnEntity.getFixedAssetList().get(i).setWfDecison("Rejected");
                }
            } else {
                returnEntity.getFixedAssetList().get(i).setWfDecison("Requested");
            }

        }

        WfMmsProcessedDataModel = new ListDataModel(returnEntity.getFixedAssetList());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Rowselection and update">
    public void createNewReturnInfo() {

        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clearPage();
            renderPnlCreateReturn = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateReturn = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void onSelectRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                returnEntity = (MmsFixedAssetReturn) event.getNewValue();
                populateUI();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to process ! Try Again Reloading the Page");
        }
    }

    public void rowSelect(SelectEvent event) {
        returnEntity = (MmsFixedAssetReturn) event.getObject();

        populateUI();

    }

    public void populateUI() {

        activeIndex = "0";
        setCreateOrSearchBundle(createOrSearchBundle);
        disableBtnCreate = true;
        setIsRenderedIconWorkflow(true);
        recreateWfDataModel();
        renderPnlManPage = false;
        renderPnlCreateReturn = true;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");
        String tag = returnEntity.getMmsFixedAssetReturnDtlList().get(0).getItemCode();
        fixedAssetRegDtlEntity.setRequeistedBy(returnEntity.getReturnedBy());
        hrEmployees.setFirstName(returnEntity.getReturnedBy().toString());
        System.out.println("---- " + hrEmployees.getFirstName());
        returnEntity.setReturnedBy(Integer.parseInt(hrEmployees.getFirstName()));
        System.out.println("-------Returned By popup ----------" + returnEntity.getReturnedBy());
        tagNumber = fixedAssetRegDtlEntity.getTagNo();
        System.out.println("-------Returned By popup tag n ----------" + tagNumber);
        fixedassetRegstration = fixedAssetRegistrationBeanLocal.findByNameAndTagNo(fixedassetRegstration, fixedAssetRegDtlEntity);
        listFixedAssetReg = new ArrayList<>();
        listFixedAssetReg.add(fixedassetRegstration);
        if (workflow.isPrepareStatus()) {
            wfMmsProcessed.setProcessedOn(returnEntity.getProcessedOn());
        }
        System.out.println("-------Returned By ----------" + returnEntity.getReturnedBy());
        hrDepartmentsEntity.setDepId(returnEntity.getDepartment().getDepId());
        hrDepartmentsEntity = returnEntity.getDepartment();
        itemcodeDtl = fxAssetRegDtlInterface.getFixedAssetInfoByRecivedBy(hrDepartmentsEntity);
        if (fixedAssetRegDtlEntity.getRequeistedBy() != null) {
            hrEmployees.setId(fixedAssetRegDtlEntity.getRequeistedBy());
            hrEmployees = hrEmployeesFacade.getById(hrEmployees);
            System.out.println("hrEmployees===1===+" + hrEmployees);
        }
        ids = returnInterface.getEmpIdByName(hrEmployees.getFirstName());
        listFixedAssetRegDtl = fxAssetRegDtlInterface.getTagNoByRequester1(ids);
        storeInfoEntity = returnEntity.getStoreId();
        recreateModelDetail();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="clearpage and clearPopUp">
    private void clearPopUp() {
        fixedAssetRegDtlEntity = null;
        returnDtlEntity = null;
        addOrModifyBundle = "Add";
    }

    public String clearPage() {
        storeInfoEntity = null;
        returnDtlEntity = null;
        returnEntity = null;
        hrEmployees = null;
        fixedAssetRegDtlEntity = null;
        fixedassetRegstration = null;
        hrDepartmentsEntity = null;
        returnAddDetailDataModel = null;
        mmsreturnSearchInfoDataModel = null;
        hrDepartmentsEntity = null;
        wfMmsProcessed = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        return null;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Search and Save methods">
    public void searchReturnInformation1() {
        String str = returnEntity.getFarNo();
        returnEntity.setFarNo(str);
        returnEntity.setProcessedBy(SessionBean.getUserId());
//        if (returnEntity.getFarNo() != null) {
            allReturnInfoList = returnInterface.getReturnListsByParameter(returnEntity);
            System.out.println("ALL Return size===== is null" + returnEntity.getFarNo());
            checkerList.clear();
            checkerList = allReturnInfoList;
            recerateReturnSerachModel();
//        } else {
//            allReturnInfoList = returnInterface.searchAllFarInfoByPreparerId(returnEntity.getProcessedBy());
////            System.out.println("===allfind=====" + allReturnInfoList);
//            checkerList.clear();
//            checkerList = allReturnInfoList;
//            recerateReturnSerachModel();
//        }
    }

    public String generateReturnNo() {
        MmsFixedAssetReturn lastInsuranceID = returnInterface.getLastReturnId();
        if (lastInsuranceID != null) {
            lastReturnId = lastInsuranceID.getFarId().toString();
        }
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        int id = 0;
        if (lastReturnId.equals("0")) {
            id = 1;
            newReturnId = "SRN No-" + id + "/" + f.format(now);
        } else {
            String[] lastInspNos = lastReturnId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newReturnId = "SRN No-" + id + "/" + f.format(now);
        }

        return newReturnId;
    }

    public void WfSave() {
        wfMmsProcessed.setFarId(returnEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }

    public void editDataTable() {
        addOrModifyBundle = "Modify";
        returnDtlEntity = returnAddDetailDataModel.getRowData();

        fixedAssetRegDtlEntity.setTagNo(returnDtlEntity.getFarId().getFixedARDtId().getTagNo());
        fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByTag(fixedAssetRegDtlEntity);
        System.out.println("===============fixedAssetRegDtlEntity tagno===" + fixedAssetRegDtlEntity);

    }

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String MessageText = "";

    public boolean isReturnDateAfterRecievedDate(String recievedDate, String ReturnDate) {
        try {
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save and Update Methods">
    public void saveFAReturn() {

        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveFAReturn", dataset)) {
                System.out.println("1");
                if (isReturnDateAfterRecievedDate(returnEntity.getRcvDate(), returnEntity.getRetDate()) == true) {
                    System.out.println("2");
                    JsfUtil.addFatalMessage(MessageText);
                } else {
                    System.out.println("3");
                    if (updateStatus == 0) {
                        System.out.println("4");
                        if (returnAddDetailDataModel.getRowCount() > 0) {
                            System.out.println("5");
                            try {
                                System.out.println("6");
                                fixedAssetRegDtlEntity.setFarDetId(farDtlId);
                                fixedAssetRegDtlEntity = fxAssetRegDtlInterface.findByDetailId(fixedAssetRegDtlEntity);

                                System.out.println("------ Item Status -- --  " + fixedAssetRegDtlEntity.getItemStatus());

                                if ("Disposed".equalsIgnoreCase(fixedAssetRegDtlEntity.getItemStatus())) {
                                    System.out.println(" -- -- Inside Disposed -- -- ");
                                    JsfUtil.addFatalMessage(" The Item is Already Disposed ! ");
                                } else if ("Lost".equalsIgnoreCase(fixedAssetRegDtlEntity.getItemStatus())) {
                                    System.out.println(" -- -- Inside Lost -- -- ");
                                    JsfUtil.addFatalMessage(" The Item is Already Lost ! ");
                                } else if (fixedAssetRegDtlEntity.getItemStatus() == null && "Normal".equalsIgnoreCase(fixedAssetRegDtlEntity.getItemStatus())
                                        || "Scrub".equalsIgnoreCase(fixedAssetRegDtlEntity.getItemStatus()) || "Broken".equalsIgnoreCase(fixedAssetRegDtlEntity.getItemStatus())
                                        || "Damaged".equalsIgnoreCase(fixedAssetRegDtlEntity.getItemStatus())) {
                                    System.out.println("in else here");
                                    fixedAssetRegDtlEntity.setItemStatus(returnDtlEntity.getItemStatus());
                                    fxAssetRegDtlInterface.edit(fixedAssetRegDtlEntity);
                                    System.out.println("in else end");
                                    System.out.println(".......rowcount....." + returnAddDetailDataModel.getRowCount());
                                    returnEntity.setFarNo(newReturnId);
                                    System.out.println("--------- returnEntity -- -- " + returnEntity.getFarNo());
                                    returnEntity.setReturnedBy(fixedAssetRegDtlEntity.getRequeistedBy());
                                    System.out.println("=========returnEntity ReturnedBy======" + returnEntity.getReturnedBy());
                                    wfMmsProcessed.setProcessedBy(workflow.getUserAccount());
                                    returnEntity.setFixedARDtId(fixedAssetRegDtlEntity);
                                    System.out.println(" save   " + wfMmsProcessed.getProcessedBy());
                                    returnEntity.setProcessedBy(wfMmsProcessed.getProcessedBy());
                                    System.out.println("when save " + returnEntity.getProcessedBy());
                                    returnEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                                    returnEntity.setStatus(Constants.PREPARE_VALUE);
                                    wfMmsProcessed.setDecision(returnEntity.getStatus());
                                    wfMmsProcessed.setFarId(returnEntity);
                                    returnEntity.getFixedAssetList().add(wfMmsProcessed);
                                    returnInterface.create(returnEntity);
                                    JsfUtil.addSuccessMessage(" A New Fixed Asset Return is Saved! ");
                                    clearPage();
                                }
                                System.out.println("no new");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JsfUtil.addFatalMessage("Something Error Occured on creating the data ");

                            }
                        } else {
                            System.out.println("8");
                            JsfUtil.addFatalMessage("Please Add atleast One Record on the Detail Form");

                        }
                    } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                        System.out.println("7");
                        try {
                            returnEntity.setStatus(Constants.PREPARE_VALUE);
                            wfMmsProcessed.setDecision(returnEntity.getStatus());
                            returnEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            returnEntity.setRemark(wfMmsProcessed.getCommentGiven());
                            wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                            returnInterface.edit(returnEntity);
                            WfSave();
                            JsfUtil.addSuccessMessage("Fixed Asset Return  is updated ");
                            clearPage();
                        } catch (Exception e) {

                            JsfUtil.addFatalMessage(" Something Error Occured on On Updating the Data !");

                        }
                    } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                        System.out.println("12");
                        if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_VALUE);
                            returnEntity.setStatus(Constants.APPROVE_VALUE);
                            wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            returnEntity.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                            workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            returnEntity.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                            workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            returnEntity.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                        }
                        wfMmsProcessed.setFarId(returnEntity);
                        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                        returnInterface.edit(returnEntity);
                        mmsFarList.remove(returnEntity);
                        clearPage();
                        JsfUtil.addSuccessMessage("Fixed Asset Return Data Updated Succesfully");
                    }
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

}
